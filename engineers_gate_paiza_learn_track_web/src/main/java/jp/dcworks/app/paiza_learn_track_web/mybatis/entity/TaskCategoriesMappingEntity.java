package jp.dcworks.app.paiza_learn_track_web.mybatis.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.apache.commons.lang3.math.NumberUtils;

import lombok.Data;

/**
 * MyBatis課題Entityクラス。
 *
 * @author tomo-sato
 */
@Data
public class TaskCategoriesMappingEntity {

	/** 課題カテゴリーID */
	private Long taskCategoriesId;

	/** 講座名 */
	private String taskCategoriesName;

	/** 学習時間合計（時） */
	private Double sumLearningHours;

	/** 学習時間合計（時）実績 */
	private Double sumAchievedLearningHours;

	/** 進捗率 */
	private Double progressRate;

	// ----- 以下、DBでデータ抽出後に設定する項目 -----

	/** 終了予測（進捗ペース）の日にち */
	private Date progressEstimateDate;

	/** 終了予測（進捗ペース）の時間 */
	private BigDecimal progressEstimateHours;

	/** 終了予測（進捗ペース）の割合 */
	private BigDecimal progressEstimateRate;

	/** 終了予測（週10時間ペース）の日にち */
	private Date weeklyTimeEstimateDate;

	/** 終了予測（週10時間ペース）の時間 */
	private BigDecimal weeklyTimeEstimateHours;

	/**
	 * 残作業時間（時）を返す。
	 * @return
	 */
	public BigDecimal getRemainingHours() {
		Double ret = (this.sumAchievedLearningHours != null) ? this.sumLearningHours - this.sumAchievedLearningHours : this.sumLearningHours;
		return NumberUtils.toScaledBigDecimal(ret, 1, RoundingMode.HALF_EVEN);
	}
}
