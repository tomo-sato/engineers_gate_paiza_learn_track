package jp.dcworks.app.paiza_learn_track_web.mybatis.entity;

import java.util.Date;

import lombok.Data;

/**
 * MyBatis課題進捗率Entityクラス。
 *
 * @author tomo-sato
 */
@Data
public class ProgressRatesMap {

	/** チームユーザーID */
	private Long teamUsersId;

	/** 名前 */
	private String name;

	/** 学習開始日 */
	private Date learningStartDate;

	/** 経過日数 */
	private Integer elapsedDays;

	/** 学習進捗率 */
	private Double progressRate;
}
