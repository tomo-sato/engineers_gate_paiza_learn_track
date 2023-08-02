package jp.dcworks.app.paiza_learn_track_library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import jp.dcworks.app.paiza_learn_track_library.entity.TaskCategories;

/**
 * 課題カテゴリー関連リポジトリインターフェース。
 *
 * @author tomo-sato
 */
public interface TaskCategoriesRepository extends PagingAndSortingRepository<TaskCategories, Long>, CrudRepository<TaskCategories, Long> {
}
