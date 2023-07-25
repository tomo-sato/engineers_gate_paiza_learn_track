package jp.dcworks.app.paiza_learn_track.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import jp.dcworks.app.paiza_learn_track.AppConst;
import jp.dcworks.app.paiza_learn_track.dto.CsvTasks;
import jp.dcworks.app.paiza_learn_track.dto.CsvTeamUserTaskProgress;

/**
 * CSV読み込み定義クラス。
 *
 * @author tomo-sato
 */
@Configuration
@StepScope
public class CsvReaderConfig {

	/** 起動引数：取り込みファイル（受講生データCSV） */
	@Value(AppConst.JOB_PARAMETERS_INPUT_CSV)
	private String inputCsvFileName;

	/**
	 *　受講生データをCSVファイルから読み取る。
	 * @return
	 */
	@Bean
	@StepScope
	FlatFileItemReader<CsvTeamUserTaskProgress> csvTeamUserTaskProgressReader() {

		FlatFileItemReader<CsvTeamUserTaskProgress> builder = new FlatFileItemReaderBuilder<CsvTeamUserTaskProgress>()
				.name("CsvTeamUserTaskProgress")
				.resource(new FileSystemResource("inputs/" + this.inputCsvFileName))
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
	FlatFileItemReader<CsvTasks> csvTasksReader() {

		FlatFileItemReader<CsvTasks> builder = new FlatFileItemReaderBuilder<CsvTasks>()
				.name("CsvTeamUserTaskProgress")
				.resource(new FileSystemResource("inputs/tasks_master.csv"))
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
