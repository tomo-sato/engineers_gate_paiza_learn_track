package jp.dcworks.app.paiza_learn_track_web.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.dcworks.app.paiza_learn_track_library.entity.OriginalTaskProgress;
import jp.dcworks.app.paiza_learn_track_library.repository.OriginalTaskProgressRepository;

/**
 * オリジナル課題進捗管理サービスクラス。
 *
 * @author tomo-sato
 */
@Service
public class OriginalTaskProgressService {

	/** リポジトリインターフェース。 */
	@Autowired
	private OriginalTaskProgressRepository originalTaskProgressRepository;

	/**
	 * オリジナル課題進捗管理を登録する。
	 *
	 * @param teamUsersId ユーザーID
	 * @param chapterId チャプターID
	 * @param taskProgressRate 進捗率
	 * @param reportDate 集計日
	 * @return
	 */
	public OriginalTaskProgress save(Long teamUsersId, Integer chapterId, Double taskProgressRate, Date reportDate) {

		OriginalTaskProgress originalTaskProgress =
				originalTaskProgressRepository.findByTeamUsersIdAndChapterIdAndReportDate(teamUsersId, chapterId, reportDate).orElse(null);

		if (originalTaskProgress == null) {
			originalTaskProgress = new OriginalTaskProgress();
			originalTaskProgress.setTeamUsersId(teamUsersId);
			originalTaskProgress.setChapterId(chapterId);
			originalTaskProgress.setReportDate(reportDate);
		}

		originalTaskProgress.setTaskProgressRate(taskProgressRate);
		return originalTaskProgressRepository.save(originalTaskProgress);
	}
}
