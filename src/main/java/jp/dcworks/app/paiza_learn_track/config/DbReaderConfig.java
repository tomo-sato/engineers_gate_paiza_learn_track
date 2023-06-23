package jp.dcworks.app.paiza_learn_track.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;

import jp.dcworks.app.paiza_learn_track.entity.TeamUserTaskProgress;
import jp.dcworks.app.paiza_learn_track.repository.TeamUserTaskProgressRepository;
import lombok.extern.log4j.Log4j2;

/**
 * CSV読み込み定義クラス。
 *
 * @author tomo-sato
 */
@Configuration
@Log4j2
public class DbReaderConfig {

	/** リポジトリインターフェース。 */
	@Autowired
	private TeamUserTaskProgressRepository teamUserTaskProgressRepository;

	@Bean
	@StepScope
	RepositoryItemReader<TeamUserTaskProgress> readTeamUserTaskProgress() {

		// ソート設定。
		Map<String, Direction> sort = new HashMap<>();
		sort.put("id", Direction.ASC);

		RepositoryItemReader<TeamUserTaskProgress> reader = new RepositoryItemReader<TeamUserTaskProgress>();
		reader.setRepository(teamUserTaskProgressRepository);
		reader.setMethodName("findAll");
		reader.setPageSize(5);
		reader.setSort(sort);

		return reader;
	}
}
