package jp.dcworks.app.paiza_learn_track.chunk;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.dcworks.app.paiza_learn_track.entity.TeamUsers;
import jp.dcworks.app.paiza_learn_track.service.TeamUsersService;
import jp.dcworks.app.paiza_learn_track.util.CollectionUtil;
import lombok.extern.log4j.Log4j2;

/**
 * team_users　Processorクラス。
 *
 * @author tomo-sato
 */
@Component
@StepScope
@Log4j2
public class TeamUsersProcessor implements ItemProcessor<String, TeamUsers> {

	/** チームユーザー課題進捗サービスクラス */
	@Autowired
	private TeamUsersService teamUsersService;

	@Override
	public TeamUsers process(String item) throws Exception {
		log.info("TeamUsersProcessor:{}", item);

		String emailAddress = item;
		List<TeamUsers> teamUsersList = teamUsersService.findByEmailAddress(emailAddress);
		if (CollectionUtil.isEmpty(teamUsersList)) {

			TeamUsers teamUsers = new TeamUsers();
			teamUsers.setEmailAddress(emailAddress);

			return teamUsers;
		}

		return null;
	}
}
