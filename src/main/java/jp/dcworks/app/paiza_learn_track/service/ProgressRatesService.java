package jp.dcworks.app.paiza_learn_track.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.dcworks.app.paiza_learn_track.entity.ProgressRates;
import jp.dcworks.app.paiza_learn_track.mybatis.entity.ProgressRatesMap;
import jp.dcworks.app.paiza_learn_track.repository.ProgressRatesRepository;
import lombok.extern.log4j.Log4j2;

/**
 * 課題進捗率サービスクラス。
 *
 * @author tomo-sato
 */
@Log4j2
@Service
public class ProgressRatesService {

	/** リポジトリインターフェース。 */
	@Autowired
	private ProgressRatesRepository repository;

	/**
	 * データコンバーター。
	 * @param targetDate
	 * @param progressRatesMap
	 * @return
	 */
	public ProgressRates convert(Date targetDate, ProgressRatesMap progressRatesMap) {

		ProgressRates progressRates = new ProgressRates();
		progressRates.setTeamUsersId(progressRatesMap.getTeamUsersId());
		progressRates.setCourseId(progressRatesMap.getCourseId());
		progressRates.setCourseName(progressRatesMap.getCourseName());
		progressRates.setLessonId(progressRatesMap.getLessonId());
		progressRates.setLessonName(progressRatesMap.getLessonName());
		progressRates.setReportDate(targetDate);
		progressRates.setAchievedLearningHours(progressRatesMap.getAchievedLearningHours());
		progressRates.setTotalLearningHours(progressRatesMap.getTotalLearningHours());
		progressRates.setTaskProgressRate(progressRatesMap.getTaskProgressRate());

		return progressRates;
	}

	/**
	 * データ登録を行う。
	 * @param itemsList
	 */
	public void saveAll(List<? extends ProgressRates> itemsList) {
		repository.saveAll(itemsList);
	}
}
