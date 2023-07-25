package jp.dcworks.app.paiza_learn_track_web.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.TasksMap;

/**
 * 課題テーブル関連のマッパーインターフェース。
 *
 * @author tomo-sato
 */
@Mapper
public interface TasksMapper {

	/**
	 * tasks テーブルより グルーピングした学習時間（時）の合計を取得する。
	 *
	 * @return
	 */
	Double findGroupBySumLearningHours();

	/**
	 * tasks テーブルより レッスンでグルーピングした結果を取得する。
	 *
	 * @return
	 */
	List<TasksMap> findGroupByLesson();
}
