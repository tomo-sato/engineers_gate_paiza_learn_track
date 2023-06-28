package jp.dcworks.app.paiza_learn_track.chunk;

import java.util.Date;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.dcworks.app.paiza_learn_track.AppConst;
import jp.dcworks.app.paiza_learn_track.dto.CsvTeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track.entity.TeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track.service.TeamUserTaskProgressService;
import lombok.extern.log4j.Log4j2;

/**
 * team_user_task_progress　Processorクラス。
 *
 * @author tomo-sato
 */
@Component
@StepScope
@Log4j2
public class TeamUserTaskProgressProcessor implements ItemProcessor<CsvTeamUserTaskProgress, TeamUserTaskProgress> {

	/** 集計日（yyyy-MM-dd） */
	private Date reportDate;

	@Autowired
	private TeamUserTaskProgressService teamUserTaskProgressService;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		// ExecutionContextから集計日を取得
		reportDate = (Date) stepExecution.getExecutionContext().get(AppConst.EXECUTION_CONTEXT_KEY_REPORTDATE);
	}

	@Override
	public TeamUserTaskProgress process(CsvTeamUserTaskProgress item) throws Exception {
		log.info("TeamUserTaskProgressProcessor:{}", item);

		// DB保存形式のエンティティに変換。
		TeamUserTaskProgress teamUserTaskProgress = teamUserTaskProgressService.convert(reportDate, item);

		return teamUserTaskProgress;
	}
}
