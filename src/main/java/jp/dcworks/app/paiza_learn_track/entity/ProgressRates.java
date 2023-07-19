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
 * 課題進捗率Entityクラス。
 *
 * @author tomo-sato
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "progress_rates")
public class ProgressRates extends EntityBase {

	/** ID */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** チームユーザーID */
	@Column(name = "team_users_id", nullable = false)
	private Long teamUsersId;

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

	/** 学習時間実績（時） */
	@Column(name = "achieved_learning_hours", nullable = false)
	private Double achievedLearningHours;

	/** 学習時間合計（時）：カリキュラムとして定義している学習時間の合計 */
	@Column(name = "total_learning_hours", nullable = false)
	private Double totalLearningHours;

	/** 課題進捗率（%）：『（「学習時間実績（時）」 / 「学習時間合計（時）） * 100」』の算出結果を登録。 */
	@Column(name = "task_progress_rate", nullable = false)
	private Double taskProgressRate;

	/** 集計日（yyyy-MM-dd） */
	@Column(name = "report_date", nullable = false)
	private Date reportDate;

	/** チャプター最終受講日時 */
	@Column(name = "chapter_last_access_datetime", nullable = false)
	private Date chapterLastAccessDatetime;

	/** オリジナル課題進捗管理ID */
	@Column(name = "original_task_progress_id", nullable = true)
	private Long originalTaskProgressId;
}
