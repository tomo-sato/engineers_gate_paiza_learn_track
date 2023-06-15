package jp.dcworks.app.paiza_learn_track.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * チームユーザー課題進捗Entityクラス。
 *
 * @author tomo-sato
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "team_user_task_progress")
public class TeamUserTaskProgress extends EntityBase {

	/** ID */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** メールアドレス */
	@Column(name = "email_address", nullable = false)
	private String emailAddress;

	/** 講座ID */
	@Column(name = "course_id", nullable = false)
	private Integer courseId;

	/** 講座名 */
	@Column(name = "course_name", nullable = false)
	private String courseName;

	/** 講座完了フラグ */
	@Column(name = "course_completion_flag", nullable = false)
	private String courseCompletionFlag;

	/** レッスンID */
	@Column(name = "lesson_id", nullable = false)
	private String lessonId;

	/** レッスン名 */
	@Column(name = "lesson_name", nullable = false)
	private String lessonName;

	/** レッスン完了フラグ */
	@Column(name = "lesson_completion_flag", nullable = false)
	private String lessonCompletionFlag;

	/** チャプターID */
	@Column(name = "chapter_id", nullable = false)
	private Integer chapterId;

	/** チャプター名 */
	@Column(name = "chapter_name", nullable = false)
	private String chapterName;

	/** チャプター完了フラグ */
	@Column(name = "chapter_completion_flag", nullable = false)
	private String chapterCompletionFlag;

	/** チャプター受講開始日時 */
	@Column(name = "chapter_start_datetime", nullable = false)
	private Date chapterStartDatetime;

	/** チャプター最終受講日時 */
	@Column(name = "chapter_last_access_datetime", nullable = false)
	private Date chapterLastAccessDatetime;
}
