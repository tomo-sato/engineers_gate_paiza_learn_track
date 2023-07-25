package jp.dcworks.app.paiza_learn_track.chunk;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.dcworks.app.paiza_learn_track.dto.CsvTasks;
import jp.dcworks.app.paiza_learn_track.service.TasksService;
import jp.dcworks.app.paiza_learn_track_library.entity.Tasks;
import lombok.extern.log4j.Log4j2;

/**
 * tasks　Processorクラス。
 *
 * @author tomo-sato
 */
@Component
@StepScope
@Log4j2
public class TasksProcessWriter implements ItemProcessor<CsvTasks, Tasks>, ItemWriter<Tasks> {

	/** チームユーザー課題進捗サービスクラス */
	@Autowired
	private TasksService tasksService;

	@Override
	public Tasks process(CsvTasks item) throws Exception {
		log.info("TasksProcessor:{}", item);

		// DB保存形式のエンティティに変換。
		Tasks teamUserTaskProgress = tasksService.convert(item);

		return teamUserTaskProgress;
	}

	@Override
	public void write(List<? extends Tasks> items) throws Exception {
		log.info("TasksWritter:{}", items);
		log.info("=========");

		// データ登録を行う。
		tasksService.saveAll(items);
	}
}
