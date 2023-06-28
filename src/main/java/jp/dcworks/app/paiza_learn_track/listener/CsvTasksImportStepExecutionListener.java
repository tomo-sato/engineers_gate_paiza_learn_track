package jp.dcworks.app.paiza_learn_track.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.dcworks.app.paiza_learn_track.service.TasksService;

/**
 * 課題データ読み取りStepExecutionListenerクラス。
 *
 * @author tomo-sato
 */
@Component
@StepScope
public class CsvTasksImportStepExecutionListener implements StepExecutionListener {

	/** チームユーザー課題進捗サービスクラス */
	@Autowired
	private TasksService tasksService;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		// データクリーニング処理。（truncateでデータ削除。）
		tasksService.truncate();
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}
}
