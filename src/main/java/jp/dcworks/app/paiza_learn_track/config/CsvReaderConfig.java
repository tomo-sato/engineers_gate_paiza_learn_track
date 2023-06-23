package jp.dcworks.app.paiza_learn_track.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import jp.dcworks.app.paiza_learn_track.dto.CsvTasks;
import jp.dcworks.app.paiza_learn_track.dto.CsvTeamUserTaskProgress;
import lombok.extern.log4j.Log4j2;

/**
 * CSV読み込み定義クラス。
 *
 * @author tomo-sato
 */
@Configuration
@Log4j2
public class CsvReaderConfig {

	/**
	 *　受講生データをCSVファイルから読み取る。
	 * @return
	 */
	@Bean
	@StepScope
	FlatFileItemReader<CsvTeamUserTaskProgress> readCsvTeamUserTaskProgress() {

		FlatFileItemReader<CsvTeamUserTaskProgress> builder = new FlatFileItemReaderBuilder<CsvTeamUserTaskProgress>()
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
		return builder;
	}

	/**
	 *　課題データをCSVファイルから読み取る。
	 * @return
	 */
	@Bean
	@StepScope
	FlatFileItemReader<CsvTasks> readTasks() {

		FlatFileItemReader<CsvTasks> builder = new FlatFileItemReaderBuilder<CsvTasks>()
				.name("CsvTeamUserTaskProgress")
				.resource(new FileSystemResource("inputs/tasks_202306161823.csv"))
				.encoding("Shift_JIS")
				.linesToSkip(1) // ヘッダーをスキップ
				.delimited()
				.names(new String[] {
					"courseId",
					"courseName",
					"lessonId",
					"lessonName",
					"chapterId",
					"chapterName",
					"exerciseNum",
					"taskTypesId",
					"learningMinutes",
				})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<CsvTasks>() {{
					setTargetType(CsvTasks.class);
				}})
				.build();
		return builder;
	}
}
