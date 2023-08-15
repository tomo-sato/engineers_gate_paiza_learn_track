package jp.dcworks.app.paiza_learn_track_web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.dcworks.app.paiza_learn_track_library.entity.OriginalTaskProgress;
import jp.dcworks.app.paiza_learn_track_library.entity.ProgressRates;
import jp.dcworks.app.paiza_learn_track_library.entity.Tasks;
import jp.dcworks.app.paiza_learn_track_library.entity.TeamUsers;
import jp.dcworks.app.paiza_learn_track_web.AppConst;
import jp.dcworks.app.paiza_learn_track_web.dto.ResponseProgressRatesDto;
import jp.dcworks.app.paiza_learn_track_web.dto.RequestTaskProgressRate;
import jp.dcworks.app.paiza_learn_track_web.dto.RequestWeeklyStudyDuration;
import jp.dcworks.app.paiza_learn_track_web.dto.ResponseUserProgressRatesDto;
import jp.dcworks.app.paiza_learn_track_web.dto.ResponseUserProgressRatesDto.UserProgressRateDetail;
import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.ProgressRatesMappingEntity;
import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.TaskCategoriesMappingEntity;
import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.TasksMappingEntity;
import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.TeamUserTaskProgressMappingEntity;
import jp.dcworks.app.paiza_learn_track_web.service.OriginalTaskProgressService;
import jp.dcworks.app.paiza_learn_track_web.service.ProgressRatesService;
import jp.dcworks.app.paiza_learn_track_web.service.TaskCategoriesService;
import jp.dcworks.app.paiza_learn_track_web.service.TasksService;
import jp.dcworks.app.paiza_learn_track_web.service.TeamUserTaskProgressService;
import jp.dcworks.app.paiza_learn_track_web.service.TeamUsersService;
import lombok.extern.log4j.Log4j2;

/**
 * ホームコントローラー。
 *
 * @author tomo-sato
 */
@Log4j2
@Controller
@RequestMapping("/")
public class HomeController {

	/** 課題サービス */
	@Autowired
	TasksService tasksService;
	/** 課題カテゴリーサービス */
	@Autowired
	TaskCategoriesService taskCategoriesService;
	/** 課題進捗率サービス */
	@Autowired
	ProgressRatesService progressRatesService;
	/** チームユーザー課題進捗サービス */
	@Autowired
	TeamUserTaskProgressService teamUserTaskProgressService;
	/** オリジナル課題進捗管理サービス */
	@Autowired
	OriginalTaskProgressService originalTaskProgressService;
	/** チームユーザーサービス */
	@Autowired
	TeamUsersService teamUsersService;

	/**
	 * [GET]講座別一覧画面のアクション。
	 *
	 * @param reportDate 集計日（yyyy-MM-dd）
	 * @param model 画面にデータを送るためのオブジェクト
	 * @return
	 * @throws ParseException
	 */
	@GetMapping(path = {"", "/"})
	public String index(@RequestParam(name = "reportDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportDate, Model model) throws ParseException {

		// 学習時間（時）の合計を取得する。
		Double sumLearningHours = tasksService.findGroupBySumLearningHours();
		// 受講生の進捗率を取得。
		List<ProgressRatesMappingEntity> progressRatesList = progressRatesService.getProgressRate(reportDate, sumLearningHours);
		// 受講生の最終着手課題を取得する。
		Map<Long, TeamUserTaskProgressMappingEntity> lastAccessLessonMap = teamUserTaskProgressService.getLastAccessLessonMap(reportDate);

		// 画面表示用にデータ整形。
		List<ResponseProgressRatesDto> progressRatesDtoList = convertProgressRatesDto(progressRatesList, lastAccessLessonMap);

		model.addAttribute("reportDate", reportDate);
		model.addAttribute("progressRatesDtoList", progressRatesDtoList);
		return "index";
	}

	/**
	 * [GET]講座別進捗一覧画面のアクション。
	 *
	 * @param reportDate 集計日（yyyy-MM-dd）
	 * @param teamUsersId ユーザーID
	 * @param model 画面にデータを送るためのオブジェクト
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/detail/{teamUsersId}/{weeklyStudyDuration}")
	public String detail(@RequestParam(name = "reportDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportDate,
			@PathVariable Long teamUsersId,
			@PathVariable Integer weeklyStudyDuration,
			Model model) throws ParseException {

		// 全課題と、ユーザー情報を紐付ける。
		ResponseUserProgressRatesDto userProgressRatesDto = convertUserProgressRatesDto(teamUsersId, reportDate, weeklyStudyDuration);

		model.addAttribute("reportDate", reportDate);
		model.addAttribute("weeklyStudyDuration", weeklyStudyDuration);
		model.addAttribute("userProgressRatesDto", userProgressRatesDto);

		if (!model.containsAttribute("requestTaskProgressRate")) {
			model.addAttribute("requestTaskProgressRate", new RequestTaskProgressRate());
		}
		return "detail";
	}

	@PostMapping("/reloadWeeklyStudyDuration/{teamUsersId}")
	public String reloadWeeklyStudyDuration(@Validated @ModelAttribute RequestWeeklyStudyDuration requestTaskProgressRate,
			@PathVariable Long teamUsersId,
			BindingResult result,
			RedirectAttributes redirectAttributes) throws ParseException {

		// 日付がないとそもそも機能しないので、チェック無しで取得して、だめなら落とす。
		String strReportDate = requestTaskProgressRate.getReportDate();
		String strWeeklyStudyDuration = requestTaskProgressRate.getWeeklyStudyDuration();

		if (strWeeklyStudyDuration.isBlank()) {
			strWeeklyStudyDuration = AppConst.DEFAULT_WEEKLY_STUDY_DURATION.toString();
		}

		return "redirect:/detail/" + teamUsersId + "/" + strWeeklyStudyDuration + "?reportDate=" + strReportDate;
	}


	/**
	 * [POST]講座別進捗一覧画面、オリジナル課題進捗率登録アクション。
	 *
	 * @param requestTaskProgressRate リクエストデータ
	 * @param teamUsersId ユーザーID
	 * @param maxTasksId 課題ID
	 * @param result
	 * @param redirectAttributes
	 * @return
	 * @throws ParseException
	 */
	@PostMapping("/registRate/{teamUsersId}/{maxTasksId}")
	public String registRate(@Validated @ModelAttribute RequestTaskProgressRate requestTaskProgressRate,
			@PathVariable Long teamUsersId,
			@PathVariable Long maxTasksId,
			BindingResult result,
			RedirectAttributes redirectAttributes) throws ParseException {

		log.info("進捗率登録処理のアクションが呼ばれました。：requestTaskProgressRate={}", requestTaskProgressRate);

		// 日付がないとそもそも機能しないので、チェック無しで取得して、だめなら落とす。
		String strReportDate = requestTaskProgressRate.getReportDate();

		// バリデーション。
		if (result.hasErrors()) {
			log.warn("バリデーションエラーが発生しました。：requestTaskProgressRate={}, result={}", requestTaskProgressRate, result);

			redirectAttributes.addFlashAttribute("validationErrors", result);
			redirectAttributes.addFlashAttribute("requestTaskProgressRate", requestTaskProgressRate);

			// 入力画面へリダイレクト。
			return "redirect:/detail/" + teamUsersId + "?reportDate=" + strReportDate;
		}

		// 課題IDから、課題を抽出しチャプターIDを取得する。
		Tasks tasks = tasksService.findById(maxTasksId);
		log.info("課題データ取得。：tasks={}", tasks);

		if (tasks == null) {
			log.warn("課題データが取得できませんでした。：maxTasksId={}", maxTasksId);

			// 入力画面へリダイレクト。
			return "redirect:/detail/" + teamUsersId;
		}
		Integer chapterId = tasks.getChapterId();
		String strTaskProgressRate = requestTaskProgressRate.getTaskProgressRate();
		Double taskProgressRate = NumberUtils.toDouble(strTaskProgressRate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date reportDate = sdf.parse(strReportDate);

		// オリジナル課題進捗管理テーブルにデータを登録する。
		OriginalTaskProgress originalTaskProgress = originalTaskProgressService.save(teamUsersId, chapterId, taskProgressRate, reportDate);
		log.info("オリジナル課題進捗管理を登録しました。：originalTaskProgress={}", originalTaskProgress);

		// 課題進捗率テーブルにデータを登録する。
		progressRatesService.save(tasks, originalTaskProgress, reportDate);

		return "redirect:/detail/" + teamUsersId + "/10?reportDate=" + strReportDate;
	}

	/**
	 * DTOコンバータ
	 *
	 * @param teamUsersId
	 * @param reportDate
	 * @param weeklyStudyDuration
	 * @return
	 */
	private ResponseUserProgressRatesDto convertUserProgressRatesDto(Long teamUsersId, Date reportDate, Integer weeklyStudyDuration) {

		// ユーザー情報取得
		TeamUsers teamUsers = teamUsersService.getTeamUsers(teamUsersId);

		// ユーザーID、集計日で検索した結果を取得する。
		Map<String, ProgressRates> progressRatesAllLessonMap = progressRatesService.findByTeamUsersIdAndReportDateOrderMap(teamUsersId, reportDate);

		// 学習時間（時）の合計を取得する。
		Double sumLearningHours = tasksService.findGroupBySumLearningHours();
		// 受講生の進捗率を取得。
		ProgressRatesMappingEntity progressRatesMap = progressRatesService.getProgressRate(reportDate, sumLearningHours, teamUsersId);
		// 受講生の最終着手課題を取得する。
		Map<Long, TeamUserTaskProgressMappingEntity> lastAccessLessonMap = teamUserTaskProgressService.getLastAccessLessonMap(reportDate);
		TeamUserTaskProgressMappingEntity lastAccessLesson = lastAccessLessonMap.get(teamUsersId);

		// tasks テーブルより レッスンでグルーピングした結果を表出するリストのベースとする。（ここで取得した結果が全課題。）
		List<TasksMappingEntity> tasksMapList = tasksService.findGroupByLesson();

		// 進捗率
		Double progressRate = progressRatesMap.getProgressRate();
		// 経過日数
		Integer elapsedDays = progressRatesMap.getElapsedDays();
		// 学習終了予測日数
		Integer predictedEndDuration = (int) (elapsedDays / (progressRate / 100)) - elapsedDays;
		// 学習終了予測日
		Date currentDate = new Date();
		long millisecondsToAdd = predictedEndDuration * 24 * 60 * 60 * 1000L;
		// 終了予測
		Date predictedEndDate = new Date(currentDate.getTime() + millisecondsToAdd);


		List<TaskCategoriesMappingEntity> taskCategoriesMapList = taskCategoriesService.findCategory(reportDate, teamUsersId);

		// 残作業時間の合計を算出
		BigDecimal totalAchievedLearningHours = new BigDecimal(0);
		for (TaskCategoriesMappingEntity taskCategoriesMap : taskCategoriesMapList) {
			BigDecimal remainingHours = taskCategoriesMap.getRemainingHours();
			totalAchievedLearningHours = totalAchievedLearningHours.add(remainingHours);
		}


		// 終了予測を取得するため、現在日時を取得。
		Date now = new Date();
		Date tmpTotalDate = null;
		Date tmpTotalDate2 = null;

		// 終了予測を設定する。
		// TODO tomo-sato 変数名とか適当すぎてヤバいので、要修正。
		for (TaskCategoriesMappingEntity taskCategoriesMap : taskCategoriesMapList) {
			// 「残作業時間の割合」を算出。（「残作業時間」 / 「残作業時間の合計」）
			BigDecimal remainingHours = taskCategoriesMap.getRemainingHours();

			if (remainingHours.compareTo(BigDecimal.ZERO) == 0) {
				taskCategoriesMap.setProgressEstimateHours(NumberUtils.toScaledBigDecimal(0.0, 1, RoundingMode.HALF_EVEN));
				taskCategoriesMap.setWeeklyTimeEstimateHours(NumberUtils.toScaledBigDecimal(0.0, 1, RoundingMode.HALF_EVEN));
			} else {
				BigDecimal achievedLearningRate = remainingHours.divide(totalAchievedLearningHours, 4, RoundingMode.HALF_EVEN);

				// 実績から必要な残学習時間を算出。（「学習終了予測日数」 * 「残作業時間の割合」）
				Double progressEstimateHours = (double) ((predictedEndDuration * achievedLearningRate.doubleValue()) * 24);
				taskCategoriesMap.setProgressEstimateHours(NumberUtils.toScaledBigDecimal(progressEstimateHours, 1, RoundingMode.HALF_EVEN));

				// 週10時間ペースの場合。
				achievedLearningRate = remainingHours.divide(new BigDecimal((double) weeklyStudyDuration / 7), 4, RoundingMode.HALF_EVEN);
				progressEstimateHours = (double) achievedLearningRate.doubleValue() * 24;
				taskCategoriesMap.setWeeklyTimeEstimateHours(NumberUtils.toScaledBigDecimal(progressEstimateHours, 1, RoundingMode.HALF_EVEN));

			}

			// 現在日時もしくは、累積している終了日時に加算
			if (tmpTotalDate == null) {
				long millisecondsToAddd = (long) (taskCategoriesMap.getProgressEstimateHours().doubleValue() * 60 * 60 * 1000L);
				long millisecondsToAddd2 = (long) (taskCategoriesMap.getWeeklyTimeEstimateHours().doubleValue() * 60 * 60 * 1000L);
				tmpTotalDate = new Date(now.getTime() + millisecondsToAddd);
				tmpTotalDate2 = new Date(now.getTime() + millisecondsToAddd2);
			} else {
				long millisecondsToAddd = (long) (taskCategoriesMap.getProgressEstimateHours().doubleValue() * 60 * 60 * 1000L);
				long millisecondsToAddd2 = (long) (taskCategoriesMap.getWeeklyTimeEstimateHours().doubleValue() * 60 * 60 * 1000L);
				tmpTotalDate = new Date(tmpTotalDate.getTime() + millisecondsToAddd);
				tmpTotalDate2 = new Date(tmpTotalDate2.getTime() + millisecondsToAddd2);
			}
			taskCategoriesMap.setProgressEstimateDate(tmpTotalDate);
			taskCategoriesMap.setWeeklyTimeEstimateDate(tmpTotalDate2);
		}


		ResponseUserProgressRatesDto retdto = new ResponseUserProgressRatesDto();
		retdto.setTeamUsers(teamUsers);
		retdto.setProgressRatesMap(progressRatesMap);
		retdto.setLastAccessLesson(lastAccessLesson);
		retdto.setPredictedEndDuration(predictedEndDuration);
		retdto.setPredictedEndDate(predictedEndDate);
		retdto.setTaskCategoriesMapList(taskCategoriesMapList);

		List<UserProgressRateDetail> retList = new ArrayList<UserProgressRateDetail>();

		for (TasksMappingEntity item : tasksMapList) {
			String lessonId = item.getLessonId();

			UserProgressRateDetail userProgressRatesDto = new UserProgressRateDetail();
			userProgressRatesDto.setMaxTasksId(item.getMaxTasksId());
			userProgressRatesDto.setTaskTypesId(item.getTaskTypesId());
			userProgressRatesDto.setCourseId(item.getCourseId());
			userProgressRatesDto.setCourseName(item.getCourseName());
			userProgressRatesDto.setLessonName(item.getLessonName());
			userProgressRatesDto.setSumTotalLearningHours(item.getSumLearningMinutes());

			if (progressRatesAllLessonMap.containsKey(lessonId)) {
				ProgressRates progressRates = progressRatesAllLessonMap.get(lessonId);

				userProgressRatesDto.setSumAchievedLearningHours(progressRates.getAchievedLearningHours());
				userProgressRatesDto.setTaskProgressRate(progressRates.getTaskProgressRate());
				userProgressRatesDto.setChapterLastAccessDatetime(progressRates.getChapterLastAccessDatetime());
			}

			retList.add(userProgressRatesDto);
		}

		retdto.setUserProgressRateDetailList(retList);

		return retdto;
	}

	/**
	 * DTOコンバータ
	 *
	 * @param progressRatesList
	 * @param lastAccessLessonMap
	 * @return
	 */
	private List<ResponseProgressRatesDto> convertProgressRatesDto(List<ProgressRatesMappingEntity> progressRatesList,
			Map<Long, TeamUserTaskProgressMappingEntity> lastAccessLessonMap) {

		List<ResponseProgressRatesDto> retList = new ArrayList<ResponseProgressRatesDto>();

		for (ProgressRatesMappingEntity item : progressRatesList) {
			Long teamUsersId = item.getTeamUsersId();

			Double progressRate = item.getProgressRate();
			Integer elapsedDays = (item.getElapsedDays() == null) ? 0 : item.getElapsedDays();
			Integer predictedEndDuration = (int) (elapsedDays / (progressRate / 100)) - elapsedDays;

			ResponseProgressRatesDto progressRatesDto = new ResponseProgressRatesDto();
			progressRatesDto.setTeamUsersId(teamUsersId);
			progressRatesDto.setName(item.getName());
			progressRatesDto.setProgressRate(progressRate);
			progressRatesDto.setLearningStartDate(item.getLearningStartDate());
			progressRatesDto.setElapsedDays(elapsedDays);

			// 学習狩猟予測日数
			progressRatesDto.setPredictedEndDuration(predictedEndDuration);

			// 学習終了予測日
			Date currentDate = new Date();
			long millisecondsToAdd = predictedEndDuration * 24 * 60 * 60 * 1000L;
			Date predictedEndDate = new Date(currentDate.getTime() + millisecondsToAdd);
			progressRatesDto.setPredictedEndDate(predictedEndDate);

			if (lastAccessLessonMap.containsKey(teamUsersId)) {
				TeamUserTaskProgressMappingEntity teamUserTaskProgressMap = lastAccessLessonMap.get(teamUsersId);
				progressRatesDto.setCourseName(teamUserTaskProgressMap.getCourseName());
				progressRatesDto.setLessonName(teamUserTaskProgressMap.getLessonName());
				progressRatesDto.setChapterName(teamUserTaskProgressMap.getChapterName());
				progressRatesDto.setChapterLastAccessDatetime(teamUserTaskProgressMap.getChapterLastAccessDatetime());
			}

			retList.add(progressRatesDto);
		}

		return retList;
	}
}
