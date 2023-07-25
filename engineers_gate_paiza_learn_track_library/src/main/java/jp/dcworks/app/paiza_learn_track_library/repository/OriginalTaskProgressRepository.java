package jp.dcworks.app.paiza_learn_track_library.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import jp.dcworks.app.paiza_learn_track_library.entity.OriginalTaskProgress;

/**
 * オリジナル課題進捗管理リポジトリインターフェース。
 *
 * @author tomo-sato
 */
public interface OriginalTaskProgressRepository extends PagingAndSortingRepository<OriginalTaskProgress, Long>, CrudRepository<OriginalTaskProgress, Long> {

	/**
	 * ユーザーID、チャプターID、集計日で絞り込み。
	 *
	 * @param teamUsersId ユーザーID
	 * @param chapterId チャプターID
	 * @param reportDate 集計日
	 * @return
	 */
	Optional<OriginalTaskProgress> findByTeamUsersIdAndChapterIdAndReportDate(Long teamUsersId, Integer chapterId, Date reportDate);
}
