package jp.dcworks.app.paiza_learn_track.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Bean
	public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("testStep", jobRepository)
				.tasklet((contribution, chunkContext) -> {
						System.out.println("ほげほげ");
						return RepeatStatus.FINISHED;
					}, transactionManager)
				.build();
	}

	@Bean
	public Job job(JobRepository jobRepository, Step step) {

		System.out.println("ふがふが：" + step.getName());

		return new JobBuilder("job", jobRepository)
			.start(step)
			.build();
	}
}
