package jp.dcworks.app.paiza_learn_track_web.dto;

import java.util.Date;

import lombok.Data;

/**
 * 講座別進捗一覧画面DTOクラス。
 *
 * @author tomo-sato
 */
@Data
public class UserProgressRatesDto {

	/** 課題ID */
	private Long maxTasksId;

	/** 課題種別ID（※1.paiza、2.オリジナル課題） */
	private Integer taskTypesId;

	/** 講座ID */
	private Integer courseId;

	/** 講座名 */
	private String courseName;

	/** レッスン名 */
	private String lessonName;

	/** 学習時間合計（時） */
	private Double sumTotalLearningHours;

	/** 学習時間実績（時） */
	private Double sumAchievedLearningHours;

	/** 進捗率 */
	private Double taskProgressRate;

	/** チャプター最終受講日時 */
	private Date chapterLastAccessDatetime;
}
