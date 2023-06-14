package jp.dcworks.app.paiza_learn_track.chunk;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@StepScope
@Log4j2
public class SampleWritter implements ItemWriter<String> {

	@Override
	public void write(List<? extends String> items) throws Exception {
		log.info("writer:{}", items);
		log.info("=========");
	}
}
