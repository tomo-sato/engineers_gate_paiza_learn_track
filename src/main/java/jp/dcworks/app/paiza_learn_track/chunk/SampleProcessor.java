package jp.dcworks.app.paiza_learn_track.chunk;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@StepScope
@Log4j2
public class SampleProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		item = item.toLowerCase();
		log.info("Processor:{}", item);
		return item;
	}
}
