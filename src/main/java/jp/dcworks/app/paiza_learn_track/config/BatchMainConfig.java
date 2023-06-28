package jp.dcworks.app.paiza_learn_track.config;

import java.util.Date;

import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.dcworks.app.paiza_learn_track.AppConst;
import jp.dcworks.app.paiza_learn_track.dto.CsvTasks;
import jp.dcworks.app.paiza_learn_track.dto.CsvTeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track.entity.ProgressRates;
import jp.dcworks.app.paiza_learn_track.entity.Tasks;
import jp.dcworks.app.paiza_learn_track.entity.TeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track.entity.TeamUsers;
import jp.dcworks.app.paiza_learn_track.mybatis.entity.ProgressRatesMap;
import jp.dcworks.app.paiza_learn_track.service.TasksService;
import lombok.RequiredArgsConstructor;

/**
 * バッチメインの定義クラス。
 *
 * @author tomo-sato
 */
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchMainConfig {

	@Autowired
	private TasksService tasksService;

	/** チャンクサイズ */
	private static final int CHUNK_SIZE = 10;

	/** Job生成Factory */
	private final JobBuilderFactory jobBuilderFactory;

	/** Step生成Factory */
	private final StepBuilderFactory stepBuilderFactory;

	/** 課題データ読み取り：課題データをCSVファイルから読み取る。 */
	private final FlatFileItemReader<CsvTasks> csvTasksReader;
	/** 課題データ読み取り：読み取ったCSVデータをエンティティに変換。 */
	private final ItemProcessor<CsvTasks, Tasks> csvTasksProcessor;
	/** 課題データ読み取り：エンティティをDBに登録。 */
	private final ItemWriter<Tasks> csvTasksWriter;

	/** 受講生データ読み取り：受講生データをCSVファイルから読み取る。 */
	private final FlatFileItemReader<CsvTeamUserTaskProgress> csvTeamUserTaskProgressReader;
	/** 受講生データ読み取り：読み取ったCSVデータをエンティティに変換。 */
	private final ItemProcessor<CsvTeamUserTaskProgress, TeamUserTaskProgress> csvTeamUserTaskProgressProcessor;
	/** 受講生データ読み取り：エンティティをDBに登録。 */
	private final ItemWriter<TeamUserTaskProgress> csvTeamUserTaskProgressWriter;

	/** 受講生メールアドレス読み取り：受講生データをDBから読み取る。 */
	private final MyBatisPagingItemReader<String> dbTeamUserTaskProgressReader;
	/** 受講生メールアドレス読み取り：読み取ったDBデータをエンティティに変換。 */
	private final ItemProcessor<String, TeamUsers> dbTeamUsersProcessor;
	/** 受講生メールアドレス読み取り：エンティティをDBに登録。 */
	private final ItemWriter<TeamUsers> dbTeamUsersWriter;

	/** 課題進捗率読み取り：課題進捗率データをDBから読み取る。 */
	private final MyBatisPagingItemReader<ProgressRatesMap> dbProgressRatesReader;
	/** 課題進捗率読み取：読み取ったDBデータをエンティティに変換。 */
	private final ItemProcessor<ProgressRatesMap, ProgressRates> dbProgressRatesProcessor;
	/** 課題進捗率読み取：エンティティをDBに登録。 */
	private final ItemWriter<ProgressRates> dbProgressRatesWriter;

	/**
	 * [chunk]課題データ読み取りステップ。
	 * @return
	 */
	@Bean
	Step csvTasksImportStep() {
		// Builderの取得
		return stepBuilderFactory.get("csvTasksImportStep")
			.listener(new StepExecutionListener() {

				@Override
				public void beforeStep(StepExecution stepExecution) {
					// データ削除
					tasksService.truncate();
				}

				@Override
				public ExitStatus afterStep(StepExecution stepExecution) {
					return null;
				}
			})
			.<CsvTasks, Tasks>chunk(CHUNK_SIZE)
			.reader(csvTasksReader)
			.processor(csvTasksProcessor)
			.writer(csvTasksWriter)
			.build();
	}

	/**
	 * [chunk]受講生データ読み取りステップ。
	 * @return
	 */
	@Bean
	Step csvTeamUserTaskProgressStep() {
		// Builderの取得
		return stepBuilderFactory.get("csvTeamUserTaskProgressImportStep")
			.listener(new StepExecutionListener() {

				@Override
				public void beforeStep(StepExecution stepExecution) {
					// 集計日を取得
					stepExecution.getExecutionContext().put(AppConst.EXECUTION_CONTEXT_KEY_REPORTDATE, new Date());
				}

				@Override
				public ExitStatus afterStep(StepExecution stepExecution) {
					return null;
				}
			})
			.<CsvTeamUserTaskProgress, TeamUserTaskProgress>chunk(CHUNK_SIZE)
			.reader(csvTeamUserTaskProgressReader)
			.processor(csvTeamUserTaskProgressProcessor)
			.writer(csvTeamUserTaskProgressWriter)
			.build();
	}

	/**
	 * [chunk]受講生メールアドレス読み取りステップ。
	 * @return
	 */
	@Bean
	Step dbTeamUsersStep() {
		// Builderの取得
		return stepBuilderFactory.get("dbTeamUsersStep")
			.<String, TeamUsers>chunk(CHUNK_SIZE)
			.reader(dbTeamUserTaskProgressReader)
			.processor(dbTeamUsersProcessor)
			.writer(dbTeamUsersWriter)
			.build();
	}

	/**
	 * [chunk]課題進捗率読み取りステップ。
	 * @return
	 */
	@Bean
	Step dbProgressRatesStep() {
		// Builderの取得
		return stepBuilderFactory.get("dbProgressRatesStep")
			.listener(new StepExecutionListener() {

				@Override
				public void beforeStep(StepExecution stepExecution) {
					// 集計日を取得
					stepExecution.getExecutionContext().put(AppConst.EXECUTION_CONTEXT_KEY_REPORTDATE, new Date());
				}

				@Override
				public ExitStatus afterStep(StepExecution stepExecution) {
					return null;
				}
			})
			.<ProgressRatesMap, ProgressRates>chunk(CHUNK_SIZE)
			.reader(dbProgressRatesReader)
			.processor(dbProgressRatesProcessor)
			.writer(dbProgressRatesWriter)
			.build();
	}

	/**
	 * [job]バッチメイン処理。
	 * @return
	 */
	@Bean
	Job job() {
		return jobBuilderFactory.get("paizaLearnTrackJob")
			.incrementer(new RunIdIncrementer())
			.start(csvTasksImportStep())
			.next(csvTeamUserTaskProgressStep())
			.next(dbTeamUsersStep())
			.next(dbProgressRatesStep())
			.build();
	}
}
