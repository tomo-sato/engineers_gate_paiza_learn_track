package jp.dcworks.app.paiza_learn_track_web.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.dcworks.app.paiza_learn_track_web.mybatis.TeamUserTaskProgressMapper;
import jp.dcworks.app.paiza_learn_track_web.mybatis.entity.TeamUserTaskProgressMappingEntity;

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
	public List<TeamUserTaskProgressMappingEntity> getLastAccessLesson(Date reportDate) {
		return teamUserTaskProgressMapper.getLastAccessLesson(reportDate);
	}

	/**
	 * team_user_task_progress テーブルより受講生の最終着手課題を取得する。
	 *
	 * @param reportDate 集計日
	 * @return Map<Long{チームユーザーID}, TeamUserTaskProgressMap>
	 */
	public Map<Long, TeamUserTaskProgressMappingEntity> getLastAccessLessonMap(Date reportDate) {
		List<TeamUserTaskProgressMappingEntity> list = teamUserTaskProgressMapper.getLastAccessLesson(reportDate);

		Map<Long, TeamUserTaskProgressMappingEntity> retMap = new HashMap<Long, TeamUserTaskProgressMappingEntity>();
		for (TeamUserTaskProgressMappingEntity item : list) {
			Long id = item.getTeamUsersId();
			retMap.put(id, item);
		}

		return retMap;
	}
}
