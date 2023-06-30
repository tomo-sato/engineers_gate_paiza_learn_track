package jp.dcworks.app.paiza_learn_track.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.dcworks.app.paiza_learn_track.AppConst;
import jp.dcworks.app.paiza_learn_track.mybatis.entity.ProgressRatesMap;

/**
 * CSV読み込み定義クラス。
 *
 * @author tomo-sato
 */
@Configuration
@StepScope
public class DbReaderConfig {

	/** 起動引数：集計日（yyyy-MM-dd） */
	@Value(AppConst.JOB_PARAMETERS_REPORT_DATE)
	private String reportDateStr;

	/** ページサイズ */
	private static final int PAGE_SIZE = 5;

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Bean
	@StepScope
	MyBatisPagingItemReader<String> dbTeamUserTaskProgressReader() {
		return new MyBatisPagingItemReaderBuilder<String>()
				.sqlSessionFactory(sqlSessionFactory)
				.queryId("jp.dcworks.app.paiza_learn_track.mybatis.TeamUserTaskProgressMapper.findGroupByEmailAddress")
				.pageSize(PAGE_SIZE)
				.build();
	}

	@Bean
	@StepScope
	MyBatisPagingItemReader<ProgressRatesMap> dbProgressRatesReader() {

		Map<String, Object> parameter = new HashMap<>();
		// 集計日で絞り込み。
		parameter.put("report_date", this.reportDateStr);

		return new MyBatisPagingItemReaderBuilder<ProgressRatesMap>()
				.sqlSessionFactory(sqlSessionFactory)
				.queryId("jp.dcworks.app.paiza_learn_track.mybatis.ProgressRatesMapper.getProgressRate")
				.parameterValues(parameter)
				.pageSize(PAGE_SIZE)
				.build();
	}
}
