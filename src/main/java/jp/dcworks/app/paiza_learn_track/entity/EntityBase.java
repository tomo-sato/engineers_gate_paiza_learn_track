package jp.dcworks.app.paiza_learn_track.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * Entity基底クラス。
 * ※共通のカラムを定義する。
 *
 * @author tomo-sato
 */
@Data
@MappedSuperclass
public class EntityBase implements Serializable {

	/** 作成日時 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	private Date created;

	/** 更新日時 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated")
	private Date updated;

	@PrePersist
	public void onPrePersist() {
		// 初回登録時をフック。「作成日時」「更新日時」に現在日時をセット。
		setCreated(new Date());
		setUpdated(new Date());
	}

	@PreUpdate
	public void onPreUpdate() {
		// 更新時をフック。「更新日時」に現在日時をセット。
		setUpdated(new Date());
	}
}
