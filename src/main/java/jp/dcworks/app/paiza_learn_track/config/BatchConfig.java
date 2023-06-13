package jp.dcworks.app.paiza_learn_track.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

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


//	@Autowired
//	private ItemReader<String> reader;
//
//	@Autowired
//	private ItemProcessor<String, String> processor;
//
//	@Autowired
//	private ItemWriter<String> writer;
//
//	// ChunkのStepを生成
//	@Bean
//	Step chunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//		log.info("hogehoge");
//		return new StepBuilder("SampleChunkStep", jobRepository)	//Builderの取得
//			.<String, String>chunk(3, transactionManager)		//チャンクの設定
//			.reader(reader) 			//readerセット
//			.processor(processor) 		//processorセット
//			.writer(writer) 			//writerセット
//			.taskExecutor(taskExecutor())
//			.build(); 					//Stepの生成
//	}

	// Jobを生成
	@Bean
	public Job chunkJob(JobRepository jobRepository, PlatformTransactionManager txManager) throws Exception {
//		log.info("hogehoge2");
//		return new JobBuilder("SampleChunkJob", jobRepository)	//Builderの取得
//			.start(step)				//最初のStep
//			.build();					//Jobの生成
//
//		Step step1 = new StepBuilder("sampleStep", jobRepository)
//				.tasklet((contribution, chunkContext) -> {
//					System.out.println("ほげほげ");
//					return RepeatStatus.FINISHED;
//				}, txManager)
//				.build();
//
//		System.out.println("ふがふが");
//
//		return new JobBuilder("sampleJob", jobRepository)
//			.start(step1)
//			.build();
		Step step1 = stepBuilderFactory.get("sampleStep")
				.tasklet((contribution, chunkContext) -> {
					System.out.println("ほげほげ");
					return RepeatStatus.FINISHED;
				})
				.build();

		System.out.println("ふがふが");

		return jobBuilderFactory.get("sampleJob")
				.start(step1)
				.build();
	}
//
//	@Bean
//	TaskExecutor taskExecutor() {
//		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
//		taskExecutor.setConcurrencyLimit(4);
//		return taskExecutor;
//	}
}
