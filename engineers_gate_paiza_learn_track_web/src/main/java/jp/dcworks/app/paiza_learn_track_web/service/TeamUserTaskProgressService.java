package jp.dcworks.app.paiza_learn_track_web.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.dcworks.app.paiza_learn_track_web.mybatis.TeamUserTaskProgressMapper;
import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.TeamUserTaskProgressMap;

/**
 * チームユーザー課題進捗サービスクラス。
 *
 * @author tomo-sato
 */
@Service
public class TeamUserTaskProgressService {

	/** Mapperインターフェース。 */
	@Autowired
	private TeamUserTaskProgressMapper teamUserTaskProgressMapper;

	/**
	 * team_user_task_progress テーブルより受講生の最終着手課題を取得する。
	 *
	 * @param reportDate 集計日
	 * @return
	 */
	public List<TeamUserTaskProgressMap> getLastAccessLesson(Date reportDate) {
		return teamUserTaskProgressMapper.getLastAccessLesson(reportDate);
	}

	/**
	 * team_user_task_progress テーブルより受講生の最終着手課題を取得する。
	 *
	 * @param reportDate 集計日
	 * @return Map<Long{チームユーザーID}, TeamUserTaskProgressMap>
	 */
	public Map<Long, TeamUserTaskProgressMap> getLastAccessLessonMap(Date reportDate) {
		List<TeamUserTaskProgressMap> list = teamUserTaskProgressMapper.getLastAccessLesson(reportDate);

		Map<Long, TeamUserTaskProgressMap> retMap = new HashMap<Long, TeamUserTaskProgressMap>();
		for (TeamUserTaskProgressMap item : list) {
			Long id = item.getTeamUsersId();
			retMap.put(id, item);
		}

		return retMap;
	}
}
