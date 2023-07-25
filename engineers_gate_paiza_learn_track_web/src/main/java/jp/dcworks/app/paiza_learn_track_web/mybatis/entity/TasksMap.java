package jp.dcworks.app.paiza_learn_track_web.mybatis.entity;

import lombok.Data;

/**
 * MyBatis課題Entityクラス。
 *
 * @author tomo-sato
 */
@Data
public class TasksMap {

	/** 課題ID */
	private Long maxTasksId;

	/** 講座ID */
	private Integer courseId;

	/** 講座名 */
	private String courseName;

	/** レッスンID */
	private String lessonId;

	/** レッスン名 */
	private String lessonName;

	/** 課題種別ID（※1.paiza、2.オリジナル課題） */
	private Integer taskTypesId;

	/** 学習時間合計（時） */
	private Double sumLearningMinutes;
}
