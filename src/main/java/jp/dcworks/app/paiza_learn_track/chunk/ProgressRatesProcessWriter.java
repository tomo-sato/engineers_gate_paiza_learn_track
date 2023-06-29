package jp.dcworks.app.paiza_learn_track.chunk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class ProgressRatesProcessWriter implements ItemProcessor<ProgressRatesMap, ProgressRates>, ItemWriter<ProgressRates> {

	/** 起動引数：集計日（yyyy-MM-dd） */
	@Value(AppConst.JOB_PARAMETERS_REPORT_DATE)
	private String reportDateStr;

	/** 課題進捗率サービスクラス */
	@Autowired
	private ProgressRatesService progressRatesService;

	@Override
	public ProgressRates process(ProgressRatesMap item) throws Exception {
		log.info("ProgressRatesProcessor:{}, {}", item, this.reportDateStr);

		SimpleDateFormat sdf = new SimpleDateFormat(AppConst.FORMAT_DATE);
		Date reportDate = sdf.parse(reportDateStr);

		// DB保存形式のエンティティに変換。
		ProgressRates progressRates = progressRatesService.convert(reportDate, item);

		return progressRates;
	}

	@Override
	public void write(List<? extends ProgressRates> items) throws Exception {
		log.info("ProgressRatesWritter:{}", items);
		log.info("=========");

		// データ登録を行う。
		progressRatesService.saveAll(items);

	}
}
