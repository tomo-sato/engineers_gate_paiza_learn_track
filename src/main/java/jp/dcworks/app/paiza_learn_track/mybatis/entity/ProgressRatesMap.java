package jp.dcworks.app.paiza_learn_track.mybatis.entity;

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

	/** メールアドレス */
	private String emailAddress;

	/** 講座ID */
	private Integer courseId;

	/** 講座名 */
	private String courseName;

	/** レッスンID */
	private String lessonId;

	/** レッスン名 */
	private String lessonName;

	/** 学習時間実績（時） */
	private Double achievedLearningHours;

	/** 学習時間合計（時）：カリキュラムとして定義している学習時間の合計 */
	private Double totalLearningHours;

	/** 課題進捗率（%）：『（「学習時間実績（時）」 / 「学習時間合計（時）） * 100」』の算出結果を登録。 */
	private Double taskProgressRate;
}
