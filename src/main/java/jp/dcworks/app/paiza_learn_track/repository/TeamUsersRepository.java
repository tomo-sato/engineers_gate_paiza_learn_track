package jp.dcworks.app.paiza_learn_track.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import jp.dcworks.app.paiza_learn_track.entity.TeamUsers;

/**
 * チームユーザー関連リポジトリインターフェース。
 *
 * @author tomo-sato
 */
public interface TeamUsersRepository extends PagingAndSortingRepository<TeamUsers, Long>, CrudRepository<TeamUsers, Long> {
}
