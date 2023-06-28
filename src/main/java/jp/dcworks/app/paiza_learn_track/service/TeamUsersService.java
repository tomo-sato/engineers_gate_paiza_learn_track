package jp.dcworks.app.paiza_learn_track.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.dcworks.app.paiza_learn_track.entity.TeamUsers;
import jp.dcworks.app.paiza_learn_track.repository.TeamUsersRepository;

/**
 * チームユーザー課題進捗サービスクラス。
 *
 * @author tomo-sato
 */
@Service
public class TeamUsersService {

	/** リポジトリインターフェース。 */
	@Autowired
	private TeamUsersRepository repository;

	/**
	 * メールアドレスで検索をする。
	 * @param emailAddress メールアドレス
	 */
	public List<TeamUsers> findByEmailAddress(String emailAddress) {
		return (List<TeamUsers>) repository.findByEmailAddress(emailAddress);
	}

	/**
	 * データ登録を行う。
	 * @param itemsList
	 */
	public void saveAll(List<? extends TeamUsers> itemsList) {
		repository.saveAll(itemsList);
	}

}
