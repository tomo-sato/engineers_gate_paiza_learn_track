package jp.dcworks.app.paiza_learn_track.chunk;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.dcworks.app.paiza_learn_track.entity.ProgressRates;
import jp.dcworks.app.paiza_learn_track.service.ProgressRatesService;
import lombok.extern.log4j.Log4j2;

/**
 * progress_rates　Writterクラス。
 *
 * @author tomo-sato
 */
@Component
@StepScope
@Log4j2
public class ProgressRatesWritter implements ItemWriter<ProgressRates> {

	@Autowired
	private ProgressRatesService progressRatesService;

	@Override
	public void write(List<? extends ProgressRates> items) throws Exception {
		log.info("ProgressRatesWritter:{}", items);
		log.info("=========");

		// データ登録を行う。
		progressRatesService.saveAll(items);

	}
}
