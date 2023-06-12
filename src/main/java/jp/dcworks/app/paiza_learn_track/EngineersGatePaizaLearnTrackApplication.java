package jp.dcworks.app.paiza_learn_track;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class EngineersGatePaizaLearnTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(EngineersGatePaizaLearnTrackApplication.class, args);
	}
}
