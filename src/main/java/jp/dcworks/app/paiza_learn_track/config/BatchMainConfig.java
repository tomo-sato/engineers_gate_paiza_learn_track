package jp.dcworks.app.paiza_learn_track.config;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Log4j2
public class BatchMainConfig {

	/** Job生成Factory */
	private final JobBuilderFactory jobBuilderFactory;

	/** Step生成Factory */
	private final StepBuilderFactory stepBuilderFactory;

	private final FlatFileItemReader<CsvTeamUserTaskProgress> csvTeamUserTaskProgressReader;
	private final ItemProcessor<CsvTeamUserTaskProgress, TeamUserTaskProgress> csvTeamUserTaskProgressProcessor;
	private final ItemWriter<TeamUserTaskProgress> csvTeamUserTaskProgressWriter;

	private final FlatFileItemReader<CsvTasks> csvTasksReader;
	private final ItemProcessor<CsvTasks, Tasks> csvTasksProcessor;
	private final ItemWriter<Tasks> csvTasksWriter;

	// ChunkのStepを生成
	@Bean
	Step step1() {
		// Builderの取得
		return stepBuilderFactory.get("SampleChunkStep1")
			.<CsvTasks, Tasks>chunk(10)		// チャンクの設定
			.reader(csvTasksReader) 		// readerセット
			.processor(csvTasksProcessor) 	// processorセット
			.writer(csvTasksWriter)			// writerセット
			.build(); 						// Stepの生成
	}

	// ChunkのStepを生成
	@Bean
	Step step2() {
		// Builderの取得
		return stepBuilderFactory.get("SampleChunkStep2")
			.<CsvTeamUserTaskProgress, TeamUserTaskProgress>chunk(10)	// チャンクの設定
			.reader(csvTeamUserTaskProgressReader) 						// readerセット
			.processor(csvTeamUserTaskProgressProcessor) 				// processorセット
			.writer(csvTeamUserTaskProgressWriter)						// writerセット
			.build(); 													// Stepの生成
	}

	// Jobを生成
	@Bean
	Job job() {
		return jobBuilderFactory.get("SampleJob")
				.start(step1())
				.next(step2())
				.build();
	}
}
