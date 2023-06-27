package jp.dcworks.app.paiza_learn_track.service;

import java.util.Calendar;
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
	 * @param progressRatesMap
	 * @return
	 */
	public ProgressRates convert(ProgressRatesMap progressRatesMap) {

		// 現在の日付と時刻を取得
		Date date = new Date();

		// Calendarオブジェクトを作成し、Dateから再生成する
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		// 年月日の情報を取得
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		// 年月日だけで新しいDateオブジェクトを再生成
		Calendar newCalendar = Calendar.getInstance();
		newCalendar.set(year, month, day, 0, 0, 0);
		Date nowDate = newCalendar.getTime();

		ProgressRates progressRates = new ProgressRates();
		progressRates.setTeamUsersId(progressRatesMap.getTeamUsersId());
		progressRates.setCourseId(progressRatesMap.getCourseId());
		progressRates.setCourseName(progressRatesMap.getCourseName());
		progressRates.setLessonId(progressRatesMap.getLessonId());
		progressRates.setLessonName(progressRatesMap.getLessonName());
		progressRates.setReportDate(nowDate);
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
