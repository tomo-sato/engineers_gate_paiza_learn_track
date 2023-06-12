package jp.dcworks.app.paiza_learn_track.chunk;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@StepScope
@Log4j2
public class SampleReader implements ItemReader<String> {

	//配列
	private String[] input = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

	private int index = 0;

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		System.out.println("hoge read");
		if (input.length > index) {
			//配列の文字列を取得
			String message = input[index++];
			log.info("Read:{}", message);

			return message;
		}
		return null;
	}
}
