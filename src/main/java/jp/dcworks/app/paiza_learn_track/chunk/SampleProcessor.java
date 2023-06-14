package jp.dcworks.app.paiza_learn_track.chunk;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import jp.dcworks.app.paiza_learn_track.dto.TeamUsers;
import lombok.extern.log4j.Log4j2;

@Component
@StepScope
@Log4j2
public class SampleProcessor implements ItemProcessor<TeamUsers, TeamUsers> {

	@Override
	public TeamUsers process(TeamUsers item) throws Exception {
		log.info("Processor:{}", item.toString());
		return item;
	}
}
