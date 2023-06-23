package jp.dcworks.app.paiza_learn_track.config;

import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.dcworks.app.paiza_learn_track.dto.CsvTasks;
import jp.dcworks.app.paiza_learn_track.dto.CsvTeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track.entity.Tasks;
import jp.dcworks.app.paiza_learn_track.entity.TeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track.entity.TeamUsers;
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

	/** 受講生データ読み取り：受講生データをCSVファイルから読み取る。 */
	private final MyBatisPagingItemReader<String> dbTeamUserTaskProgressReader;
	/** 受講生データ読み取り：読み取ったCSVデータをエンティティに変換。 */
	private final ItemProcessor<String, TeamUsers> dbTeamUsersProcessor;
	/** 受講生データ読み取り：エンティティをDBに登録。 */
	private final ItemWriter<TeamUsers> dbTeamUsersWriter;

	/**
	 * [chunk]課題データ読み取りステップ。
	 * @return
	 */
	@Bean
	Step csvTasksImportStep() {
		// Builderの取得
		return stepBuilderFactory.get("csvTasksImportStep")
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
			.<CsvTeamUserTaskProgress, TeamUserTaskProgress>chunk(CHUNK_SIZE)
			.reader(csvTeamUserTaskProgressReader)
			.processor(csvTeamUserTaskProgressProcessor)
			.writer(csvTeamUserTaskProgressWriter)
			.build();
	}

	/**
	 * [chunk]受講生データ読み取りステップ。
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
	 * [job]バッチメイン処理。
	 * @return
	 */
	@Bean
	Job job() {
		return jobBuilderFactory.get("paizaLearnTrackJob")
				.start(csvTasksImportStep())
				.next(csvTeamUserTaskProgressStep())
				.next(dbTeamUsersStep())
				.build();
	}
}
