package jp.dcworks.app.paiza_learn_track_library.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import jp.dcworks.app.paiza_learn_track_library.entity.ProgressRates;

/**
 * チームユーザー課題進捗関連リポジトリインターフェース。
 *
 * @author tomo-sato
 */
public interface ProgressRatesRepository extends PagingAndSortingRepository<ProgressRates, Long>, CrudRepository<ProgressRates, Long> {

	/**
	 * ユーザーID、集計日で検索した結果を取得する。
	 *
	 * @param teamUsersId
	 * @param reportDate
	 * @return
	 */
	List<ProgressRates> findByTeamUsersIdAndReportDate(Long teamUsersId, Date reportDate);

	/**
	 * UKで検索をする。
	 *
	 * @param teamUsersId
	 * @param courseId
	 * @param lessonId
	 * @param reportDate
	 * @return
	 */
	Optional<ProgressRates> findByTeamUsersIdAndCourseIdAndLessonIdAndReportDate(Long teamUsersId, Integer courseId, String lessonId, Date reportDate);
}
