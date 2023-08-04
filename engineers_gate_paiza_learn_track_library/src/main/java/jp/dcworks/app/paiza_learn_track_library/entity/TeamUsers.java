package jp.dcworks.app.paiza_learn_track_library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
