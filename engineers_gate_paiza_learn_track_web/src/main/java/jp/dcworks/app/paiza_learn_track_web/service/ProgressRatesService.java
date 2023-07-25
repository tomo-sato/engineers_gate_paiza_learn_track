package jp.dcworks.app.paiza_learn_track_web.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.dcworks.app.paiza_learn_track_library.entity.OriginalTaskProgress;
import jp.dcworks.app.paiza_learn_track_library.entity.ProgressRates;
import jp.dcworks.app.paiza_learn_track_library.entity.Tasks;
import jp.dcworks.app.paiza_learn_track_library.repository.ProgressRatesRepository;
import jp.dcworks.app.paiza_learn_track_web.mybatis.ProgressRatesMapper;
import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.ProgressRatesMap;

/**
 * 課題進捗率サービスクラス。
 *
 * @author tomo-sato
 */
@Service
public class ProgressRatesService {

	/** Mapperインターフェース。 */
	@Autowired
	private ProgressRatesMapper progressRatesMapper;
	/** リポジトリインターフェース。 */
	@Autowired
	private ProgressRatesRepository progressRatesRepository;

	/**
	 * progress_rates テーブルより team_users_id でグルーピングした学習進捗率を取得する。
	 *
	 * @param reportDate 集計日
	 * @param sumLearningMinutes 学習時間合計
	 * @return
	 */
	public List<ProgressRatesMap> getProgressRate(Date reportDate, Double sumLearningMinutes) {
		return progressRatesMapper.getProgressRate(reportDate, sumLearningMinutes);
	}

	/**
	 * ユーザーID、集計日で検索した結果を取得する。
	 *
	 * @param teamUsersId ユーザーID
	 * @param reportDate 集計日
	 * @return Map<String{レッスンID}, ProgressRates>
	 */
	public Map<String, ProgressRates> findByTeamUsersIdAndReportDateOrderMap(Long teamUsersId, Date reportDate) {
		List<ProgressRates> list = progressRatesRepository.findByTeamUsersIdAndReportDate(teamUsersId, reportDate);

		Map<String, ProgressRates> retMap = new LinkedHashMap<String, ProgressRates>();
		for (ProgressRates item : list) {
			String lessonId = item.getLessonId();
			retMap.put(lessonId, item);
		}

		return retMap;
	}

	public void save(Tasks tasks, OriginalTaskProgress originalTaskProgress, Date reportDate) {
		Double totalLearningHours = (double) (tasks.getLearningMinutes() / 60);
		Double taskProgressRate = originalTaskProgress.getTaskProgressRate();
		Double achievedLearningHours = totalLearningHours * (taskProgressRate / 100);
		Long teamUsersId = originalTaskProgress.getTeamUsersId();
		Integer courseId = tasks.getCourseId();
		String lessonId = tasks.getLessonId();

		// UKで検索をかける。
		ProgressRates progressRates = progressRatesRepository.findByTeamUsersIdAndCourseIdAndLessonIdAndReportDate(teamUsersId, courseId, lessonId, reportDate).orElse(null);

		if (progressRates == null) {
			progressRates = new ProgressRates();
			progressRates.setTeamUsersId(teamUsersId);
			progressRates.setCourseId(courseId);
			progressRates.setCourseName(tasks.getCourseName());
			progressRates.setLessonId(lessonId);
			progressRates.setLessonName(tasks.getLessonName());
			progressRates.setReportDate(reportDate);
		}
		progressRates.setAchievedLearningHours(achievedLearningHours);
		progressRates.setTotalLearningHours(totalLearningHours);
		progressRates.setTaskProgressRate(taskProgressRate);
		progressRates.setChapterLastAccessDatetime(reportDate);
		progressRates.setOriginalTaskProgressId(null);

		progressRatesRepository.save(progressRates);
	}
}
