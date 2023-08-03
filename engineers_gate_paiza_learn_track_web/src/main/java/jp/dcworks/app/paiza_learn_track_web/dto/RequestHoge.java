package jp.dcworks.app.paiza_learn_track_web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 進捗率更新DTOクラス。
 *
 * @author tomo-sato
 */
@Data
public class RequestHoge implements Serializable  {

	/** 集計日（yyyy-MM-dd） */
	@NotBlank(message = "パラメータ不正。")
	private String reportDate;

	private String weeklyStudyDuration;
}