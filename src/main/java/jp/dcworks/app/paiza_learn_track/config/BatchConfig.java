package jp.dcworks.app.paiza_learn_track.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.extern.log4j.Log4j2;

@EnableScheduling
@Configuration
@EnableBatchProcessing
@Log4j2
public class BatchConfig {

	@Autowired
	private ItemReader<String> reader;

	@Autowired
	private ItemProcessor<String, String> processor;

	@Autowired
	private ItemWriter<String> writer;

	// ChunkのStepを生成
	@Bean
	public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		log.info("hogehoge");
		return new StepBuilder("SampleChunkStep", jobRepository)	//Builderの取得
			.<String, String>chunk(3, transactionManager)		//チャンクの設定
			.reader(reader) 			//readerセット
			.processor(processor) 		//processorセット
			.writer(writer) 			//writerセット
			.taskExecutor(taskExecutor())
			.build(); 					//Stepの生成
	}

	// Jobを生成
	@Bean
	public Job chunkJob(JobRepository jobRepository, Step step) throws Exception {
		log.info("hogehoge2");
		return new JobBuilder("SampleChunkJob", jobRepository)	//Builderの取得
			.start(step)				//最初のStep
			.build();					//Jobの生成
	}

	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		taskExecutor.setConcurrencyLimit(4);
		return taskExecutor;
	}
}
