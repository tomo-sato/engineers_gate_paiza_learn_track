package jp.dcworks.app.paiza_learn_track_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("jp.dcworks.app.paiza_learn_track_library.entity")
public class EngineersGatePaizaLearnTrackWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(EngineersGatePaizaLearnTrackWebApplication.class, args);
	}

}
