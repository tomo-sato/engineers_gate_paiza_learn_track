package jp.dcworks.app.paiza_learn_track;

/**
 * アプリケーション定数定義クラス。
 *
 * @author tomo-sato
 */
public class AppConst {

	/** 起動引数：集計日（yyyy-MM-dd） */
	public static final String JOB_PARAMETERS_REPORT_DATE = "#{jobParameters['report_date']}";
	/** 起動引数：取り込みファイル（受講生データCSV） */
	public static final String JOB_PARAMETERS_INPUT_CSV = "#{jobParameters['input_csv']}";

	/** コンテキストキー：「集計日」を受け渡す為のキー。 */
	public static final String EXECUTION_CONTEXT_KEY_REPORTDATE = "reportDate";

	/** 日付フォーマット。 */
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	/** 日時フォーマット。 */
	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss Z";

	/** チャプター時間（分）：1チャプター約6分計算。 */
	public static final int CHAPTER_DURATION_MINUTES = 6;
	/** 問題回答時間（分）：1問約15分計算。 */
	public static final int QUESTION_ANSWER_TIME_MINUTES = 15;
}
