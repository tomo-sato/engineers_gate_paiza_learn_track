package jp.dcworks.app.paiza_learn_track.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

	/** ID */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 講座ID */
	@Column(name = "course_id", nullable = false)
	private Integer courseId;

	/** 講座名 */
	@Column(name = "course_name", nullable = false)
	private String courseName;

	/** レッスンID */
	@Column(name = "lesson_id", nullable = false)
	private String lessonId;

	/** レッスン名 */
	@Column(name = "lesson_name", nullable = false)
	private String lessonName;

	/** チャプターID */
	@Column(name = "chapter_id", nullable = false)
	private Integer chapterId;

	/** チャプター名 */
	@Column(name = "chapter_name", nullable = false)
	private String chapterName;

	/** 演習課題数（※csvファイルに記載がないので、目でpaizaのサイトを見て手動メンテ） */
	@Column(name = "exercise_num", nullable = false)
	private Integer exerciseNum;

	/** 課題種別ID（※1.paiza、2.オリジナル課題） */
	@Column(name = "task_types_id", nullable = false)
	private Integer taskTypesId;

	/** 学習時間（時）：算出した学習時間及び、割り当てた学習時間を登録（整数4桁、少数2桁） */
	@Column(name = "learning_hours", nullable = false)
	private Double learningHours;
}
