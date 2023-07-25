package jp.dcworks.app.paiza_learn_track_web.mybatis.entity;

import java.util.Date;

import lombok.Data;

/**
 * MyBatisチームユーザー課題進捗Entityクラス。
 *
 * @author tomo-sato
 */
@Data
public class TeamUserTaskProgressMap {

	/** チームユーザーID */
	private Long teamUsersId;

	/** 名前 */
	private String name;

	/** 課題ID */
	private String lastTasksId;

	/** 講座名 */
	private String courseName;

	/** レッスン名 */
	private String lessonName;

	/** チャプター名 */
	private String chapterName;

	/** チャプター最終受講日時 */
	private Date chapterLastAccessDatetime;

	/** 集計日（yyyy-MM-dd） */
	private Date reportDate;
}
