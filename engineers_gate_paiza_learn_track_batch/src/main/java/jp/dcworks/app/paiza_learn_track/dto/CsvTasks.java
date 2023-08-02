package jp.dcworks.app.paiza_learn_track.dto;

import lombok.Data;

/**
 * CSVファイルから変換した課題データクラス。
 *
 * @author tomo-sato
 */
@Data
public class CsvTasks {
	/** 講座ID */
	private String courseId;

	/** 講座名 */
	private String courseName;

	/** 課題カテゴリーID */
	private String taskCategoriesId;

	/** 課題カテゴリー名 */
	private String taskCategoriesName;

	/** レッスンID */
	private String lessonId;

	/** レッスン名 */
	private String lessonName;

	/** チャプターID */
	private String chapterId;

	/** チャプター名 */
	private String chapterName;

	/** 演習課題数（※csvファイルに記載がないので、目でpaizaのサイトを見て手動メンテ） */
	private String exerciseNum;

	/** 課題種別ID（※1.paiza、2.オリジナル課題） */
	private String taskTypesId;

	/** 学習時間（分）：算出した学習時間及び、割り当てた学習時間を登録 */
	private String learningMinutes;
}
