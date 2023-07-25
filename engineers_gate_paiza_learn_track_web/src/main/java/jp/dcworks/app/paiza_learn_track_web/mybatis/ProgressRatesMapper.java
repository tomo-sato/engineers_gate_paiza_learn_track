package jp.dcworks.app.paiza_learn_track_web.mybatis;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.ProgressRatesMap;

/**
 * 課題進捗率テーブル関連のマッパーインターフェース。
 *
 * @author tomo-sato
 */
@Mapper
public interface ProgressRatesMapper {

	/**
	 * progress_rates テーブルより team_users_id でグルーピングした学習進捗率を取得する。
	 *
	 * @param reportDate 集計日
	 * @param sumLearningMinutes 学習時間合計
	 * @return
	 */
	List<ProgressRatesMap> getProgressRate(@Param("report_date") Date reportDate, @Param("sum_learning_minutes") Double sumLearningMinutes);
}
