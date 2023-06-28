package jp.dcworks.app.paiza_learn_track.chunk;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.dcworks.app.paiza_learn_track.entity.Tasks;
import jp.dcworks.app.paiza_learn_track.service.TasksService;
import lombok.extern.log4j.Log4j2;

/**
 * tasks　Writterクラス。
 *
 * @author tomo-sato
 */
@Component
@StepScope
@Log4j2
public class TasksWritter implements ItemWriter<Tasks> {

	@Autowired
	private TasksService tasksService;

	@Override
	public void write(List<? extends Tasks> items) throws Exception {
		log.info("TasksWritter:{}", items);
		log.info("=========");

		// データ登録を行う。
		tasksService.saveAll(items);
	}
}
