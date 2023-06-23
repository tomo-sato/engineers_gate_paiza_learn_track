package jp.dcworks.app.paiza_learn_track.chunk;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.dcworks.app.paiza_learn_track.entity.TeamUsers;
import jp.dcworks.app.paiza_learn_track.service.TeamUsersService;
import lombok.extern.log4j.Log4j2;

/**
 * team_users　Writterクラス。
 *
 * @author tomo-sato
 */
@Component
@StepScope
@Log4j2
public class TeamUsersWritter implements ItemWriter<TeamUsers> {

	@Autowired
	private TeamUsersService teamUsersService;

	@Override
	public void write(List<? extends TeamUsers> items) throws Exception {
		log.info("TeamUsersWritter:{}", items);
		log.info("=========");

		// データ登録を行う。
		teamUsersService.saveAll(items);
	}
}
