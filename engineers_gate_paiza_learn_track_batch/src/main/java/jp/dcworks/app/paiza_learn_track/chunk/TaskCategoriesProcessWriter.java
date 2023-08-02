package jp.dcworks.app.paiza_learn_track.chunk;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.dcworks.app.paiza_learn_track.dto.CsvTasks;
import jp.dcworks.app.paiza_learn_track.service.TaskCategoriesService;
import jp.dcworks.app.paiza_learn_track_library.entity.TaskCategories;
import lombok.extern.log4j.Log4j2;

/**
 * task_categories　Processorクラス。
 *
 * @author tomo-sato
 */
@Component
@StepScope
@Log4j2
public class TaskCategoriesProcessWriter implements ItemProcessor<CsvTasks, TaskCategories>, ItemWriter<TaskCategories> {

	/** 課題カテゴリーサービスクラス */
	@Autowired
	private TaskCategoriesService taskCategoriesService;

	@Override
	public TaskCategories process(CsvTasks item) throws Exception {
		log.info("TaskCategoriesProcessor:{}", item);

		String strTaskCategoriesId = item.getTaskCategoriesId();
		Long taskCategoriesId = NumberUtils.toLong(strTaskCategoriesId);

		TaskCategories taskCategories = taskCategoriesService.findById(taskCategoriesId);
		if (taskCategories == null) {

			TaskCategories retTaskCategories = new TaskCategories();
			retTaskCategories.setId(taskCategoriesId);
			retTaskCategories.setName(item.getTaskCategoriesName());

			return retTaskCategories;
		}

		return null;
	}

	@Override
	public void write(List<? extends TaskCategories> items) throws Exception {
		log.info("TaskCategoriesWritter:{}", items);
		log.info("=========");

		taskCategoriesService.saveAll(items);
	}
}
