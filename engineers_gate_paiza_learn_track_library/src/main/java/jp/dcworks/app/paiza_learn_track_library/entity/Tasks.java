package jp.dcworks.app.paiza_learn_track_library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 課題Entityクラス。
 *
 * @author tomo-sato
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tasks")
public class Tasks extends EntityBase {

	/** 課題種別ID：1.paiza */
	public static final int TASK_TYPES_ID_PAIZA = 1;
	/** 課題種別ID：2.オリジナル課題 */
	public static final int TASK_TYPES_ID_ORIGINAL = 2;

	/** ID */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 課題カテゴリーID */
	@Column(name = "task_categories_id", nullable = true)
	private Integer taskCategoriesId;

	/** 講座ID */
	@Column(name = "course_id", nullable = true)
	private Integer courseId;

	/** 講座名 */
	@Column(name = "course_name", nullable = false)
	private String courseName;

	/** レッスンID */
	@Column(name = "lesson_id", nullable = true)
	private String lessonId;

	/** レッスン名 */
	@Column(name = "lesson_name", nullable = true)
	private String lessonName;

	/** チャプターID */
	@Column(name = "chapter_id", nullable = false)
	private Integer chapterId;

	/** チャプター名 */
	@Column(name = "chapter_name", nullable = true)
	private String chapterName;

	/** 演習課題数（※csvファイルに記載がないので、目でpaizaのサイトを見て手動メンテ） */
	@Column(name = "exercise_num", nullable = false)
	private Integer exerciseNum;

	/** 課題種別ID（※1.paiza、2.オリジナル課題） */
	@Column(name = "task_types_id", nullable = false)
	private Integer taskTypesId;

	/** 学習時間（分）：算出した学習時間及び、割り当てた学習時間を登録 */
	@Column(name = "learning_minutes", nullable = false)
	private Integer learningMinutes;
}
