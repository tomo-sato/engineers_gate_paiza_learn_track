package jp.dcworks.app.paiza_learn_track_web.mybatis;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.TeamUserTaskProgressMap;

/**
 * チームユーザー課題進捗テーブル関連のマッパーインターフェース。
 *
 * @author tomo-sato
 */
@Mapper
public interface TeamUserTaskProgressMapper {

	/**
	 * team_user_task_progress テーブルより受講生の最終着手課題を取得する。
	 *
	 * @param reportDate 集計日
	 * @return
	 */
	List<TeamUserTaskProgressMap> getLastAccessLesson(@Param("report_date") Date reportDate);
}
