package jp.dcworks.app.paiza_learn_track.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.dcworks.app.paiza_learn_track_library.entity.TaskCategories;
import jp.dcworks.app.paiza_learn_track_library.repository.TaskCategoriesRepository;

/**
 * 課題カテゴリーサービスクラス。
 *
 * @author tomo-sato
 */
@Service
public class TaskCategoriesService {

	/** リポジトリインターフェース。 */
	@Autowired
	private TaskCategoriesRepository repository;

	/**
	 * IDで検索。
	 * @param taskCategoriesId 課題カテゴリーID
	 * @return
	 */
	public TaskCategories findById(Long taskCategoriesId) {
		return repository.findById(taskCategoriesId).orElse(null);
	}

	/**
	 * データ登録を行う。
	 * @param itemsList
	 */
	public void saveAll(List<? extends TaskCategories> itemsList) {
		repository.saveAll(itemsList);
	}
}
