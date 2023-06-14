package jp.dcworks.app.paiza_learn_track.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;

@Configuration
@EnableBatchProcessing
@Log4j2
public class BatchConfig {

	/* Job生成Factory */
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	/* Step生成Factory */
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private ItemReader<String> reader;

	@Autowired
	private ItemProcessor<String, String> processor;

	@Autowired
	private ItemWriter<String> writer;

	// ChunkのStepを生成
	@Bean
	Step step() {
		// Builderの取得
		return stepBuilderFactory.get("SampleChunkStep")
			.<String, String>chunk(3)	// チャンクの設定
			.reader(reader) 			// readerセット
			.processor(processor) 		// processorセット
			.writer(writer) 			// writerセット
			.build(); 					// Stepの生成
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
