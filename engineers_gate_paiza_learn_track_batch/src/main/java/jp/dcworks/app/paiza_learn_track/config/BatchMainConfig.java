package jp.dcworks.app.paiza_learn_track.config;

import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import jp.dcworks.app.paiza_learn_track.chunk.ProgressRatesProcessWriter;
import jp.dcworks.app.paiza_learn_track.chunk.TaskCategoriesProcessWriter;
import jp.dcworks.app.paiza_learn_track.chunk.TasksProcessWriter;
import jp.dcworks.app.paiza_learn_track.chunk.TeamUserTaskProgressProcessWritter;
import jp.dcworks.app.paiza_learn_track.chunk.TeamUsersProcessWritter;
import jp.dcworks.app.paiza_learn_track.dto.CsvTasks;
import jp.dcworks.app.paiza_learn_track.dto.CsvTeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track.listener.CsvTasksImportStepExecutionListener;
import jp.dcworks.app.paiza_learn_track.mybatis.entity.ProgressRatesMap;
import jp.dcworks.app.paiza_learn_track_library.entity.ProgressRates;
import jp.dcworks.app.paiza_learn_track_library.entity.TaskCategories;
import jp.dcworks.app.paiza_learn_track_library.entity.Tasks;
import jp.dcworks.app.paiza_learn_track_library.entity.TeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track_library.entity.TeamUsers;
import lombok.RequiredArgsConstructor;

/**
 * バッチメインの定義クラス。
 *
 * @author tomo-sato
 */
@Configuration
@RequiredArgsConstructor
public class BatchMainConfig {

	/** チャンクサイズ */
	private static final int CHUNK_SIZE = 100;

	/** 課題データ読み取り：StepExecutionListener */
	private final CsvTasksImportStepExecutionListener csvTasksImportStepExecutionListener;
	/** 課題データ読み取り：課題データをCSVファイルから読み取る。 */
	private final FlatFileItemReader<CsvTasks> csvTasksReader;
	/** 課題データ読み取り：読み取ったCSVデータをエンティティに変換。エンティティをDBに登録。 */
	private final TasksProcessWriter ｔasksProcessWriter;
	/** 課題データ読み取り：読み取ったCSVデータをエンティティに変換。エンティティをDBに登録。 */
	private final TaskCategoriesProcessWriter taskCategoriesProcessWriter;

	/** 受講生データ読み取り：受講生データをCSVファイルから読み取る。 */
	private final FlatFileItemReader<CsvTeamUserTaskProgress> csvTeamUserTaskProgressReader;
	/** 受講生データ読み取り：読み取ったCSVデータをエンティティに変換。エンティティをDBに登録。 */
	private final TeamUserTaskProgressProcessWritter teamUserTaskProgressProcessWritter;

	/** 受講生メールアドレス読み取り：受講生データをDBから読み取る。 */
	private final MyBatisPagingItemReader<String> dbTeamUserTaskProgressReader;
	/** 受講生メールアドレス読み取り：読み取ったDBデータをエンティティに変換。エンティティをDBに登録。 */
	private final TeamUsersProcessWritter teamUsersProcessWritter;

	/** 課題進捗率読み取り：課題進捗率データをDBから読み取る。 */
	private final MyBatisPagingItemReader<ProgressRatesMap> dbProgressRatesReader;
	/** 課題進捗率読み取：読み取ったDBデータをエンティティに変換。エンティティをDBに登録。 */
	private final ProgressRatesProcessWriter progressRatesProcessWriter;

	/**
	 * [chunk]csv課題マスタデータ読み取りステップ。
	 * @return
	 */
	@Bean
	public Step csvTasksImportStep(JobRepository jobRepository, PlatformTransactionManager txManager) {
		// Builderの取得
		return new StepBuilder("csvTasksImportStep", jobRepository)
			.listener(csvTasksImportStepExecutionListener)
			.<CsvTasks, Tasks>chunk(CHUNK_SIZE, txManager)
			.reader(csvTasksReader)
			.processor(ｔasksProcessWriter)
			.writer(ｔasksProcessWriter)
			.build();
	}

	/**
	 * [chunk]csv課題マスタデータ読み取りステップ。
	 * @return
	 */
	@Bean
	public Step csvTaskCategoriesImportStep(JobRepository jobRepository, PlatformTransactionManager txManager) {
		// Builderの取得
		return new StepBuilder("csvTasksImportStep", jobRepository)
			.<CsvTasks, TaskCategories>chunk(CHUNK_SIZE, txManager)
			.reader(csvTasksReader)
			.processor(taskCategoriesProcessWriter)
			.writer(taskCategoriesProcessWriter)
			.build();
	}

	/**
	 * [chunk]csv受講生学習状況データ読み取りステップ。
	 * @return
	 */
	@Bean
	public Step csvTeamUserTaskProgressStep(JobRepository jobRepository, PlatformTransactionManager txManager) {

		// Builderの取得
		return new StepBuilder("csvTeamUserTaskProgressStep", jobRepository)
			.<CsvTeamUserTaskProgress, TeamUserTaskProgress>chunk(CHUNK_SIZE, txManager)
			.reader(csvTeamUserTaskProgressReader)
			.processor(teamUserTaskProgressProcessWritter)
			.writer(teamUserTaskProgressProcessWritter)
			.build();
	}

	/**
	 * [chunk]受講生メールアドレス登録ステップ。
	 * @return
	 */
	@Bean
	public Step dbTeamUsersStep(JobRepository jobRepository, PlatformTransactionManager txManager) {
		// Builderの取得
		return new StepBuilder("dbTeamUsersStep", jobRepository)
			.<String, TeamUsers>chunk(CHUNK_SIZE, txManager)
			.reader(dbTeamUserTaskProgressReader)
			.processor(teamUsersProcessWritter)
			.writer(teamUsersProcessWritter)
			.build();
	}

	/**
	 * [chunk]課題進捗率登録ステップ。
	 * @return
	 */
	@Bean
	public Step dbProgressRatesStep(JobRepository jobRepository, PlatformTransactionManager txManager) {
		// Builderの取得
		return new StepBuilder("dbProgressRatesStep", jobRepository)
			.<ProgressRatesMap, ProgressRates>chunk(CHUNK_SIZE, txManager)
			.reader(dbProgressRatesReader)
			.processor(progressRatesProcessWriter)
			.writer(progressRatesProcessWriter)
			.build();
	}

	/**
	 * [job]バッチメイン処理。
	 * @return
	 */
	@Bean
	public Job job(JobRepository jobRepository, PlatformTransactionManager txManager,
			@Qualifier("csvTasksImportStep") Step csvTasksImportStep,
			@Qualifier("csvTeamUserTaskProgressStep") Step csvTeamUserTaskProgressStep,
			@Qualifier("dbTeamUsersStep") Step dbTeamUsersStep,
			@Qualifier("dbProgressRatesStep") Step dbProgressRatesStep) {

		return new JobBuilder("paizaLearnTrackJob", jobRepository)
			.incrementer(new RunIdIncrementer())
			.start(csvTasksImportStep)
//			.next(csvTaskCategoriesImportStep())
			.next(csvTeamUserTaskProgressStep)
			.next(dbTeamUsersStep)
			.next(dbProgressRatesStep)
			.build();
	}
}
