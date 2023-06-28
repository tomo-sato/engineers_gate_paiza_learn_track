package jp.dcworks.app.paiza_learn_track.chunk;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.dcworks.app.paiza_learn_track.entity.TeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track.service.TeamUserTaskProgressService;
import lombok.extern.log4j.Log4j2;

/**
 * team_user_task_progress　Writterクラス。
 *
 * @author tomo-sato
 */
@Component
@StepScope
@Log4j2
public class TeamUserTaskProgressWritter implements ItemWriter<TeamUserTaskProgress> {

	/** チームユーザー課題進捗 */
	@Autowired
	private TeamUserTaskProgressService teamUserTaskProgressService;

	@Override
	public void write(List<? extends TeamUserTaskProgress> items) throws Exception {
		log.info("TeamUserTaskProgressWritter:{}", items);
		log.info("=========");

		// データ登録を行う。
		teamUserTaskProgressService.saveAll(items);
	}
}
