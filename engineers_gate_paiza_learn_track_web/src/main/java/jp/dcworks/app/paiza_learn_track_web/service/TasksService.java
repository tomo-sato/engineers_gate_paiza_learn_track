package jp.dcworks.app.paiza_learn_track_web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.dcworks.app.paiza_learn_track_library.entity.Tasks;
import jp.dcworks.app.paiza_learn_track_library.repository.TasksRepository;
import jp.dcworks.app.paiza_learn_track_web.mybatis.TasksMapper;
import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.TasksMap;

/**
 * 課題サービスクラス。
 *
 * @author tomo-sato
 */
@Service
public class TasksService {

	/** Mapperインターフェース。 */
	@Autowired
	private TasksMapper tasksMapper;
	/** リポジトリインターフェース。 */
	@Autowired
	private TasksRepository tasksRepository;

	/**
	 * tasks テーブルより task_types_id:1.paiza でグルーピングした学習時間（時）の合計を取得する。
	 *
	 * @return
	 */
	public Double findGroupBySumLearningHours() {
		return tasksMapper.findGroupBySumLearningHours();
	}

	/**
	 * tasks テーブルより レッスンでグルーピングした結果を取得する。
	 *
	 * @return
	 */
	public List<TasksMap> findGroupByLesson() {
		return tasksMapper.findGroupByLesson();
	}

	/**
	 * tasks テーブルより id でデータを取得する。
	 *
	 * @param id
	 * @return
	 */
	public Tasks findById(Long id) {
		return tasksRepository.findById(id).orElse(null);
	}
}
