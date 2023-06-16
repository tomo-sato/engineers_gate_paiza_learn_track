package jp.dcworks.app.paiza_learn_track.service;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.dcworks.app.paiza_learn_track.dto.CsvTeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track.entity.TeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track.repository.TeamUserTaskProgressRepository;
import jp.dcworks.app.paiza_learn_track.util.NumberUtil;
import lombok.extern.log4j.Log4j2;

/**
 * チームユーザー課題進捗サービスクラス。
 *
 * @author tomo-sato
 */
@Log4j2
@Service
public class TeamUserTaskProgressService {

	/** リポジトリインターフェース。 */
	@Autowired
	private TeamUserTaskProgressRepository repository;

	/**
	 * データコンバーター。
	 * @param csvTeamUserTaskProgress
	 * @return
	 * @throws ParseException
	 */
	public TeamUserTaskProgress convert(CsvTeamUserTaskProgress csvTeamUserTaskProgress) throws ParseException {
		TeamUserTaskProgress teamUserTaskProgress = new TeamUserTaskProgress();
		teamUserTaskProgress.setEmailAddress(csvTeamUserTaskProgress.getEmailAddress());
		teamUserTaskProgress.setCourseId(NumberUtil.toInteger(csvTeamUserTaskProgress.getCourseId()));
		teamUserTaskProgress.setCourseName(csvTeamUserTaskProgress.getCourseName());
		teamUserTaskProgress.setCourseCompletionFlag(csvTeamUserTaskProgress.getCourseCompletionFlag());
		teamUserTaskProgress.setLessonId(csvTeamUserTaskProgress.getLessonId());
		teamUserTaskProgress.setLessonName(csvTeamUserTaskProgress.getLessonName());
		teamUserTaskProgress.setLessonCompletionFlag(csvTeamUserTaskProgress.getLessonCompletionFlag());
		teamUserTaskProgress.setChapterId(NumberUtil.toInteger(csvTeamUserTaskProgress.getChapterId()));
		teamUserTaskProgress.setChapterName(csvTeamUserTaskProgress.getChapterName());
		teamUserTaskProgress.setChapterCompletionFlag(csvTeamUserTaskProgress.getChapterCompletionFlag());
		teamUserTaskProgress.setChapterStartDatetime(DateUtils.parseDate(csvTeamUserTaskProgress.getChapterStartDatetime(), "yyyy-MM-dd HH:mm:ss Z"));
		teamUserTaskProgress.setChapterLastAccessDatetime(DateUtils.parseDate(csvTeamUserTaskProgress.getChapterLastAccessDatetime(), "yyyy-MM-dd HH:mm:ss Z"));
		return teamUserTaskProgress;
	}

	/**
	 * データ登録を行う。
	 * @param itemsList
	 */
	public void saveAll(List<? extends TeamUserTaskProgress> itemsList) {
		repository.saveAll(itemsList);
	}
}
