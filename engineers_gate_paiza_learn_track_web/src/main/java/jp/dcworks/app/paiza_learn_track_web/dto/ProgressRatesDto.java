package jp.dcworks.app.paiza_learn_track_web.dto;

import java.util.Date;

import lombok.Data;

/**
 * 講座別一覧画面DTOクラス。
 *
 * @author tomo-sato
 */
@Data
public class ProgressRatesDto {

	/** チームユーザーID */
	private Long teamUsersId;

	/** 名前 */
	private String name;

	/** 学習進捗率 */
	private Double progressRate;

	/** 講座名 */
	private String courseName;

	/** レッスン名 */
	private String lessonName;

	/** チャプター名 */
	private String chapterName;

	/** チャプター最終受講日時 */
	private Date chapterLastAccessDatetime;

	/** 学習開始日 */
	private Date learningStartDate;

	/** 経過日数 */
	private Integer elapsedDays;

	/** 学習終了予測日 */
	private Date predictedEndDate;

	/** 学習終了予測日数 */
	private Integer predictedEndDuration;
}
