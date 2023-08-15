package jp.dcworks.app.paiza_learn_track_web.dto;

import java.util.Date;
import java.util.List;

import jp.dcworks.app.paiza_learn_track_library.entity.TeamUsers;
import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.ProgressRatesMappingEntity;
import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.TaskCategoriesMappingEntity;
import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.TeamUserTaskProgressMappingEntity;
import lombok.Data;

/**
 * 講座別進捗一覧画面DTOクラス。
 *
 * @author tomo-sato
 */
@Data
public class ResponseUserProgressRatesDto {

	/** チームユーザー情報 */
	private TeamUsers teamUsers;

	/** 受講生の進捗率 */
	private ProgressRatesMappingEntity progressRatesMap;

	/** 最終学習講座 */
	private TeamUserTaskProgressMappingEntity lastAccessLesson;

	/** 学習終了予測日数 */
	private Integer predictedEndDuration;

	/** 終了予測日 */
	private Date predictedEndDate;

	/** 課題カテゴリーリスト */
	private List<TaskCategoriesMappingEntity> taskCategoriesMapList;

	/** 進捗詳細リスト */
	private List<UserProgressRateDetail> userProgressRateDetailList;

	/**
	 * 進捗詳細DTOクラス。
	 *
	 * @author tomo-sato
	 */
	@Data
	public static class UserProgressRateDetail {

		/** 課題ID */
		private Long maxTasksId;

		/** 課題種別ID（※1.paiza、2.オリジナル課題） */
		private Integer taskTypesId;

		/** 講座ID */
		private Integer courseId;

		/** 講座名 */
		private String courseName;

		/** レッスン名 */
		private String lessonName;

		/** 学習時間合計（時） */
		private Double sumTotalLearningHours;

		/** 学習時間実績（時） */
		private Double sumAchievedLearningHours;

		/** 進捗率 */
		private Double taskProgressRate;

		/** チャプター最終受講日時 */
		private Date chapterLastAccessDatetime;
	}
}
