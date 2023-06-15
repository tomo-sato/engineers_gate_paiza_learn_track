package jp.dcworks.app.paiza_learn_track.chunk;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class TeamUserTaskProcessor implements ItemProcessor<CsvTeamUserTaskProgress, TeamUserTaskProgress> {

	@Autowired
	private TeamUserTaskProgressService teamUserTaskProgressService;

	@Override
	public TeamUserTaskProgress process(CsvTeamUserTaskProgress item) throws Exception {
		log.info("Processor:{}", item.toString());

		// DB保存形式のエンティティに変換。
		TeamUserTaskProgress teamUserTaskProgress = teamUserTaskProgressService.convert(item);

		return teamUserTaskProgress;
	}
}
