package jp.dcworks.app.paiza_learn_track_web.mybatis;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.TaskCategoriesMappingEntity;

/**
 * 課題カテゴリーテーブル関連のマッパーインターフェース。
 *
 * @author tomo-sato
 */
@Mapper
public interface TaskCategoriesMapper {

	/**
	 * task_categoriesをベースに目安時間等を取得する。
	 *
	 * @param reportDate 集計日
	 * @param teamUsersId ユーザーID
	 * @return
	 */
	List<TaskCategoriesMappingEntity> findCategory(@Param("report_date") Date reportDate, @Param("team_users_id") Long teamUsersId);
}
