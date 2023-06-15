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
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import jp.dcworks.app.paiza_learn_track.dto.CsvTeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track.entity.TeamUserTaskProgress;
import lombok.extern.log4j.Log4j2;

@Configuration
@EnableBatchProcessing
@Log4j2
public class BatchConfig {

	/** Job生成Factory */
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	/** Step生成Factory */
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private ItemProcessor<CsvTeamUserTaskProgress, TeamUserTaskProgress> processor;

	@Autowired
	private ItemWriter<TeamUserTaskProgress> writer;

	// ChunkのStepを生成
	@Bean
	Step step() throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
		// Builderの取得
		return stepBuilderFactory.get("SampleChunkStep")
			.<CsvTeamUserTaskProgress, TeamUserTaskProgress>chunk(10)	// チャンクの設定
			.reader(reader()) 					// readerセット
			.processor(processor) 				// processorセット
			.writer(writer) 					// writerセット
			.build(); 							// Stepの生成
	}

	// Jobを生成
	@Bean
	Job job(Step step) throws Exception {
		log.info("step:{}", step.getName());
		return jobBuilderFactory.get("SampleJob")
				.start(step)
				.build();
	}

	@Bean
	FlatFileItemReader<CsvTeamUserTaskProgress> reader() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return new FlatFileItemReaderBuilder<CsvTeamUserTaskProgress>()
				.name("CsvTeamUserTaskProgress")
				.resource(new FileSystemResource("inputs/paiza_202306141414.csv"))
				.encoding("Shift_JIS")
				.linesToSkip(1) // ヘッダーをスキップ
				.delimited()
				.names(new String[] {
					"emailAddress",
					"courseId",
					"courseName",
					"courseCompletionFlag",
					"lessonId",
					"lessonName",
					"lessonCompletionFlag",
					"chapterId",
					"chapterName",
					"chapterCompletionFlag",
					"chapterStartDatetime",
					"chapterLastAccessDatetime",
				})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<CsvTeamUserTaskProgress>() {{
					setTargetType(CsvTeamUserTaskProgress.class);
				}})
				.build();
	}
}
