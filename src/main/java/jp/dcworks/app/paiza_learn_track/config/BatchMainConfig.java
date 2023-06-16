package jp.dcworks.app.paiza_learn_track.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.dcworks.app.paiza_learn_track.dto.CsvTeamUserTaskProgress;
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

	// ChunkのStepを生成
	@Bean
	Step step() throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
		// Builderの取得
		return stepBuilderFactory.get("SampleChunkStep")
			.<CsvTeamUserTaskProgress, TeamUserTaskProgress>chunk(10)	// チャンクの設定
			.reader(csvTeamUserTaskProgressReader) 						// readerセット
			.processor(csvTeamUserTaskProgressProcessor) 				// processorセット
			.writer(csvTeamUserTaskProgressWriter)						// writerセット
			.build(); 													// Stepの生成
	}

	// Jobを生成
	@Bean
	Job job(Step step) throws Exception {
		log.info("step:{}", step.getName());
		return jobBuilderFactory.get("SampleJob")
				.start(step)
				.build();
	}
}
