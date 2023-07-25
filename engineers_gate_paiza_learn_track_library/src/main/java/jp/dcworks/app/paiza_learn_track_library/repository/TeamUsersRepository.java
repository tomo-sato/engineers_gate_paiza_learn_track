package jp.dcworks.app.paiza_learn_track_library.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import jp.dcworks.app.paiza_learn_track_library.entity.TeamUsers;

/**
 * チームユーザー関連リポジトリインターフェース。
 *
 * @author tomo-sato
 */
public interface TeamUsersRepository extends PagingAndSortingRepository<TeamUsers, Long>, CrudRepository<TeamUsers, Long> {

	/**
	 * メールアドレスで検索をする。
	 * @param emailAddress メールアドレス
	 */
	List<TeamUsers> findByEmailAddress(String emailAddress);
}
