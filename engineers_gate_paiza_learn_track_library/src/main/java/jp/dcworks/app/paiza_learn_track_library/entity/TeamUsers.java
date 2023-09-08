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
 * チームユーザーEntityクラス。
 *
 * @author tomo-sato
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "team_users")
public class TeamUsers extends EntityBase {

	/** ID */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** メールアドレス */
	@Column(name = "email_address", nullable = false)
	private String emailAddress;

	/** 名前 */
	@Column(name = "name", nullable = true)
	private String name;

	/** Githubアカウント */
	@Column(name = "github_account", nullable = true)
	private String githubAccount;
}
