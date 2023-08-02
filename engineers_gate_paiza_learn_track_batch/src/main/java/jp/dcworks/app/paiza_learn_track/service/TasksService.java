package jp.dcworks.app.paiza_learn_track.service;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.dcworks.app.paiza_learn_track.AppConst;
import jp.dcworks.app.paiza_learn_track.dto.CsvTasks;
import jp.dcworks.app.paiza_learn_track_library.entity.Tasks;
import jp.dcworks.app.paiza_learn_track_library.repository.TasksRepository;
import jp.dcworks.app.paiza_learn_track_library.util.NumberUtil;

/**
 * チームユーザー課題進捗サービスクラス。
 *
 * @author tomo-sato
 */
@Service
public class TasksService {

	@PersistenceContext
	private EntityManager entityManager;

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
			learningMinutes = AppConst.CHAPTER_DURATION_MINUTES + (AppConst.QUESTION_ANSWER_TIME_MINUTES * exerciseNum);
		}

		Tasks teamUserTaskProgress = new Tasks();
		teamUserTaskProgress.setTaskCategoriesId(NumberUtil.toInteger(csvTeamUserTaskProgress.getTaskCategoriesId()));
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

	@Transactional
	public void truncate() {
		entityManager.createNativeQuery("TRUNCATE TABLE tasks").executeUpdate();
	}
}
