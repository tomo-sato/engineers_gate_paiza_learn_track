package jp.dcworks.app.paiza_learn_track_library.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * オリジナル課題進捗管理Entityクラス。
 *
 * @author tomo-sato
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "original_task_progress")
public class OriginalTaskProgress extends EntityBase {

	/** ID */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** チームユーザーID */
	@Column(name = "team_users_id", nullable = false)
	private Long teamUsersId;

	/** チャプターID */
	@Column(name = "chapter_id", nullable = false)
	private Integer chapterId;

	/** 課題進捗率（%）：画面から入力された値を登録。 */
	@Column(name = "task_progress_rate", nullable = false)
	private Double taskProgressRate;

	/** 集計日（yyyy-MM-dd） */
	@Column(name = "report_date", nullable = false)
	private Date reportDate;
}
