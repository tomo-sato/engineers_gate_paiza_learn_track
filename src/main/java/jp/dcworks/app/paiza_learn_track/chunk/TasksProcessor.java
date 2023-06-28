package jp.dcworks.app.paiza_learn_track.chunk;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.dcworks.app.paiza_learn_track.dto.CsvTasks;
import jp.dcworks.app.paiza_learn_track.entity.Tasks;
import jp.dcworks.app.paiza_learn_track.service.TasksService;
import lombok.extern.log4j.Log4j2;

/**
 * tasks　Processorクラス。
 *
 * @author tomo-sato
 */
@Component
@StepScope
@Log4j2
public class TasksProcessor implements ItemProcessor<CsvTasks, Tasks> {

	@Autowired
	private TasksService tasksService;

	@Override
	public Tasks process(CsvTasks item) throws Exception {
		log.info("TasksProcessor:{}", item);

		// DB保存形式のエンティティに変換。
		Tasks teamUserTaskProgress = tasksService.convert(item);

		return teamUserTaskProgress;
	}
}
