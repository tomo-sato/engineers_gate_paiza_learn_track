package jp.dcworks.app.paiza_learn_track.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;

/**
 * CSV読み込み定義クラス。
 *
 * @author tomo-sato
 */
@Configuration
@Log4j2
public class DbReaderConfig {

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Bean
	@StepScope
	MyBatisPagingItemReader<String> readTeamUserTaskProgress() {
		return new MyBatisPagingItemReaderBuilder<String>()
				.sqlSessionFactory(sqlSessionFactory)
				.queryId("jp.dcworks.app.paiza_learn_track.mybatis.TeamUserTaskProgressMapper.findGroupByEmailAddress")
				.pageSize(5)
				.build();
	}
}
