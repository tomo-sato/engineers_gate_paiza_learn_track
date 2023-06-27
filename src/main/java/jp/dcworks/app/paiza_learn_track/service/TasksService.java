package jp.dcworks.app.paiza_learn_track.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.dcworks.app.paiza_learn_track.dto.CsvTasks;
import jp.dcworks.app.paiza_learn_track.entity.Tasks;
import jp.dcworks.app.paiza_learn_track.repository.TasksRepository;
import jp.dcworks.app.paiza_learn_track.util.NumberUtil;
import lombok.extern.log4j.Log4j2;

/**
 * チームユーザー課題進捗サービスクラス。
 *
 * @author tomo-sato
 */
@Log4j2
@Service
public class TasksService {

	/** チャプター時間（分）：1チャプター約6分計算。 */
	public static final int CHAPTER_DURATION_MINUTES = 6;
	/** 問題回答時間（分）：1問約15分計算。 */
	public static final int QUESTION_ANSWER_TIME_MINUTES = 15;

	/** リポジトリインターフェース。 */
	@Autowired
	private TasksRepository repository;

	/**
	 * データコンバーター。
	 * @param csvTeamUserTaskProgress
	 * @return
	 * @throws ParseException
	 */
	public Tasks convert(CsvTasks csvTeamUserTaskProgress) throws ParseException {
		int taskTypesId = NumberUtil.toInteger(csvTeamUserTaskProgress.getTaskTypesId()).intValue();
		int exerciseNum = NumberUtil.toInteger(csvTeamUserTaskProgress.getExerciseNum(), 0).intValue();
		// 学習時間（時）
		int learningMinutes = NumberUtil.toInteger(csvTeamUserTaskProgress.getLearningMinutes(), 0).intValue();

		if (taskTypesId == Tasks.TASK_TYPES_ID_PAIZA) {
			learningMinutes = CHAPTER_DURATION_MINUTES + (QUESTION_ANSWER_TIME_MINUTES * exerciseNum);
		}

		Tasks teamUserTaskProgress = new Tasks();
		teamUserTaskProgress.setCourseId(NumberUtil.toInteger(csvTeamUserTaskProgress.getCourseId()));
		teamUserTaskProgress.setCourseName(csvTeamUserTaskProgress.getCourseName());
		teamUserTaskProgress.setLessonId(csvTeamUserTaskProgress.getLessonId());
		teamUserTaskProgress.setLessonName(csvTeamUserTaskProgress.getLessonName());
		teamUserTaskProgress.setChapterId(NumberUtil.toInteger(csvTeamUserTaskProgress.getChapterId()));
		teamUserTaskProgress.setChapterName(csvTeamUserTaskProgress.getChapterName());
		teamUserTaskProgress.setExerciseNum(exerciseNum);
		teamUserTaskProgress.setTaskTypesId(taskTypesId);
		teamUserTaskProgress.setLearningMinutes(learningMinutes);
		return teamUserTaskProgress;
	}

	/**
	 * データ登録を行う。
	 * @param itemsList
	 */
	public void saveAll(List<? extends Tasks> itemsList) {
		repository.saveAll(itemsList);
	}
}
