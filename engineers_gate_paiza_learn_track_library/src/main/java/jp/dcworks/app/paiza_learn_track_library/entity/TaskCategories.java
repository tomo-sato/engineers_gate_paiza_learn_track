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
 * 課題カテゴリーテーブルEntityクラス。
 *
 * @author tomo-sato
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "task_categories")
public class TaskCategories extends EntityBase {

	/** ID */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 名前 */
	@Column(name = "name", nullable = true)
	private String name;
}
