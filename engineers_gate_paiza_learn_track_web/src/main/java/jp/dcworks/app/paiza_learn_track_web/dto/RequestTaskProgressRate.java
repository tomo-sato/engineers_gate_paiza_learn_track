package jp.dcworks.app.paiza_learn_track_web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * 進捗率更新DTOクラス。
 *
 * @author tomo-sato
 */
@Data
public class RequestTaskProgressRate implements Serializable  {

	/** 進捗率 */
	@NotBlank(message = "進捗率を入力してください。")
	@Size(max = 5, message = "進捗率は最大5文字です。")
	private String taskProgressRate;

	/** 集計日（yyyy-MM-dd） */
	@NotBlank(message = "パラメータ不正。")
	private String reportDate;
}
