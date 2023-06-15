package jp.dcworks.app.paiza_learn_track.dto;

import lombok.Data;

/**
 * CSVファイルから変換した受講生データクラス。
 *
 * @author tomo-sato
 */
@Data
public class CsvTeamUserTaskProgress {
	/** メールアドレス */
	private String emailAddress;

	/** 講座ID */
	private String courseId;

	/** 講座名 */
	private String courseName;

	/** 講座完了フラグ */
	private String courseCompletionFlag;

	/** レッスンID */
	private String lessonId;

	/** レッスン名 */
	private String lessonName;

	/** レッスン完了フラグ */
	private String lessonCompletionFlag;

	/** チャプターID */
	private String chapterId;

	/** チャプター名 */
	private String chapterName;

	/** チャプター完了フラグ */
	private String chapterCompletionFlag;

	/** チャプター受講開始日時 */
	private String chapterStartDatetime;

	/** チャプター最終受講日時 */
	private String chapterLastAccessDatetime;
}
