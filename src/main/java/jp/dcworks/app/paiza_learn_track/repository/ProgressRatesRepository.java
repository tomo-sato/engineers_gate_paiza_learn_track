package jp.dcworks.app.paiza_learn_track.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import jp.dcworks.app.paiza_learn_track.entity.ProgressRates;

/**
 * チームユーザー課題進捗関連リポジトリインターフェース。
 *
 * @author tomo-sato
 */
public interface ProgressRatesRepository extends PagingAndSortingRepository<ProgressRates, Long>, CrudRepository<ProgressRates, Long> {
}
