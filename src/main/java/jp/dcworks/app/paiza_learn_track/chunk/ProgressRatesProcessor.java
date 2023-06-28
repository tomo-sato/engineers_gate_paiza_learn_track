package jp.dcworks.app.paiza_learn_track.chunk;

import java.util.Date;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.dcworks.app.paiza_learn_track.AppConst;
import jp.dcworks.app.paiza_learn_track.entity.ProgressRates;
import jp.dcworks.app.paiza_learn_track.mybatis.entity.ProgressRatesMap;
import jp.dcworks.app.paiza_learn_track.service.ProgressRatesService;
import lombok.extern.log4j.Log4j2;

/**
 * progress_rates　Processorクラス。
 *
 * @author tomo-sato
 */
@Component
@StepScope
@Log4j2
public class ProgressRatesProcessor implements ItemProcessor<ProgressRatesMap, ProgressRates> {

	/** 集計日（yyyy-MM-dd） */
	private Date reportDate;

	@Autowired
	private ProgressRatesService progressRatesService;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		// ExecutionContextから集計日を取得
		reportDate = (Date) stepExecution.getExecutionContext().get(AppConst.EXECUTION_CONTEXT_KEY_REPORTDATE);
	}

	@Override
	public ProgressRates process(ProgressRatesMap item) throws Exception {
		log.info("ProgressRatesProcessor:{}", item);

		// DB保存形式のエンティティに変換。
		ProgressRates progressRates = progressRatesService.convert(reportDate, item);

		return progressRates;
	}
}
