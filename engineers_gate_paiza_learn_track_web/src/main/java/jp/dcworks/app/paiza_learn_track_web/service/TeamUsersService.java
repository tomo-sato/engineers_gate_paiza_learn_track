package jp.dcworks.app.paiza_learn_track_web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.dcworks.app.paiza_learn_track_library.entity.TeamUsers;
import jp.dcworks.app.paiza_learn_track_library.repository.TeamUsersRepository;

/**
 * チームユーザーサービスクラス。
 *
 * @author tomo-sato
 */
@Service
public class TeamUsersService {

	/** リポジトリインターフェース。 */
	@Autowired
	private TeamUsersRepository teamUsersRepository;

	/**
	 * ユーザーIDで検索する。
	 *
	 * @param id ユーザーID
	 * @return
	 */
	public TeamUsers getTeamUsers(Long id) {
		TeamUsers teamUsers = teamUsersRepository.findById(id).orElse(null);
		return teamUsers;
	}
}
