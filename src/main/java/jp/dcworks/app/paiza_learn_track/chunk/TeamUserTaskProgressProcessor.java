package jp.dcworks.app.paiza_learn_track.chunk;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.dcworks.app.paiza_learn_track.AppConst;
import jp.dcworks.app.paiza_learn_track.dto.CsvTeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track.entity.TeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track.service.TeamUserTaskProgressService;
import lombok.extern.log4j.Log4j2;

/**
 * team_user_task_progress　Processorクラス。
 *
 * @author tomo-sato
 */
@Component
@StepScope
@Log4j2
public class TeamUserTaskProgressProcessor implements ItemProcessor<CsvTeamUserTaskProgress, TeamUserTaskProgress> {

	/** 起動引数：集計日（yyyy-MM-dd） */
	@Value(AppConst.JOB_PARAMETERS_REPORT_DATE)
	private String reportDateStr;

	/** チームユーザー課題進捗 */
	@Autowired
	private TeamUserTaskProgressService teamUserTaskProgressService;

	@Override
	public TeamUserTaskProgress process(CsvTeamUserTaskProgress item) throws Exception {
		log.info("TeamUserTaskProgressProcessor:{}, {}", item, this.reportDateStr);

		SimpleDateFormat sdf = new SimpleDateFormat(AppConst.DATE_FORMAT);
		Date reportDate = sdf.parse(reportDateStr);

		// DB保存形式のエンティティに変換。
		TeamUserTaskProgress teamUserTaskProgress = teamUserTaskProgressService.convert(reportDate, item);

		return teamUserTaskProgress;
	}
}
