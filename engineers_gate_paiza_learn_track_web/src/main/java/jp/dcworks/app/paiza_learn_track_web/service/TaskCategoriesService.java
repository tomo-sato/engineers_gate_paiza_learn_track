package jp.dcworks.app.paiza_learn_track_web.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.dcworks.app.paiza_learn_track_library.repository.TaskCategoriesRepository;
import jp.dcworks.app.paiza_learn_track_web.mybatis.TaskCategoriesMapper;
import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.TaskCategoriesMap;

/**
 * 課題カテゴリーサービスクラス。
 *
 * @author tomo-sato
 */
@Service
public class TaskCategoriesService {

	/** Mapperインターフェース。 */
	@Autowired
	private TaskCategoriesMapper taskCategoriesMapper;
	/** リポジトリインターフェース。 */
	@Autowired
	private TaskCategoriesRepository tasksRepository;

	/**
	 * task_categories テーブルより カテゴリーでグルーピングした結果を取得する。
	 *
	 * @param reportDate 集計日
	 * @param teamUsersId ユーザーID
	 * @return
	 */
	public List<TaskCategoriesMap> findCategory(Date reportDate, Long teamUsersId) {
		return taskCategoriesMapper.findCategory(reportDate, teamUsersId);
	}
}
