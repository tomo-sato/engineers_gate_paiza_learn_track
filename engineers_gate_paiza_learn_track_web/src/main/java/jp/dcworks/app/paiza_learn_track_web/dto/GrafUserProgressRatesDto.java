package jp.dcworks.app.paiza_learn_track_web.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import lombok.Data;

/**
 * 講座別進捗一覧画面グラフDTOクラス。
 *
 * @author tomo-sato
 */
@Data
public class GrafUserProgressRatesDto {

	/** グラフ出力データ */
	private List<Series> series;

	/** グラフのメタデータ */
	private Annotations annotations;

	/** グラフの開始日 */
	private Date minDate;

	/** グラフの終了日 */
	private Date maxDate;

	@Data
	public static class Series {
		private String name;
		private List<SeriesData> data = new ArrayList<SeriesData>();

		public Series(String name) {
			this.name = name;
		}

		@Data
		public static class SeriesData {
			private String x;
			private int y;

			public SeriesData(String x, int y) {
				this.x = x;
				this.y = y;
			}
		}

		public void addData(SeriesData seriesData) {
			this.data.add(seriesData);
		}
	}

	@Data
	public static class Annotations {

		/** 軸のタイプ：タイプ1（強調表示） */
		public static final int AXIS_TYPE_1 = 1;
		/** 軸のタイプ：タイプ2 */
		public static final int AXIS_TYPE_2 = 2;

		private List<Xaxis> xaxis = new ArrayList<Xaxis>();
		private List<Points> points = new ArrayList<Points>();

		public void setAnnotations(Date x, int y, String text, int axisType) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			Xaxis xaxis = new Xaxis(x, sdf.format(x), axisType);
			Points points = new Points(x, y, text, axisType);

			this.xaxis.add(xaxis);
			this.points.add(points);
		}

		@Data
		public static class Xaxis {
			private long x;
			private Label label;

			public Xaxis(Date x, String labelText, int axisType) {
				this.x = x.getTime();
				this.label = new Label(axisType);
				this.label.setText(labelText);
			}

			@Data
			public static class Label {
				private String borderColor;
				private String position = "bottom";
				private String orientation = "horizontal";
				private Style style;
				private String text;

				public Label(int axisType) {
					Style style = new Style();

					switch (axisType) {
						case AXIS_TYPE_1:
							this.borderColor = "#FF0000";
							style.setBackground("#FF0000");
							style.setFontSize("20px");
							this.style = style;
							break;
						case AXIS_TYPE_2:
							this.borderColor = "#B3424A";
							style.setBackground("#B3424A");
							style.setFontSize("14px");
							this.style = style;
							break;
						default:
							this.borderColor = "#FF0000";
							style.setBackground("#FF0000");
							style.setFontSize("20px");
							this.style = style;
							break;
					}
				}

				@Data
				public static class Style {
					private String color = "#fff";
					private String background;
					private String fontSize;
				}
			}
		}

		@Data
		public static class Points {
			private long x;
			private int y;
			private Marker marker = new Marker();
			private Label label;

			public Points(Date x, int y, String text, int axisType) {
				this.x = x.getTime();
				this.y = y;
				this.label = new Label(axisType);
				this.label.setText(text);
			}

			@Data
			public static class Marker {
				private int size = 0;
			}

			@Data
			public static class Label {
				private String borderColor;
				private int offsetY;
				private Style style;
				private String text;

				public Label(int axisType) {
					Style style = new Style();

					switch (axisType) {
						case AXIS_TYPE_1:
							this.borderColor = "#FF0000";
							this.offsetY = -20;
							style.setBackground("#FF0000");
							style.setFontSize("20px");
							this.style = style;
							break;
						case AXIS_TYPE_2:
							this.borderColor = "#B3424A";
							this.offsetY = 40;
							style.setBackground("#B3424A");
							style.setFontSize("14px");
							this.style = style;
							break;
						default:
							this.borderColor = "#FF0000";
							this.offsetY = -20;
							style.setBackground("#FF0000");
							style.setFontSize("20px");
							this.style = style;
							break;
					}
				}

				@Data
				public static class Style {
					private String color = "#fff";
					private String background;
					private String fontSize;
				}
			}
		}
	}
}
