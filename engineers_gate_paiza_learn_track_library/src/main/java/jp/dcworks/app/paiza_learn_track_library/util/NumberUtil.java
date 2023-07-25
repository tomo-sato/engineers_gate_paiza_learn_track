package jp.dcworks.app.paiza_learn_track_library.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 数値操作に関する機能を提供します.
 *
 * @author tomo-sato
 */
public class NumberUtil {

	/**
	 * 引数の文字列を数値に変換します。
	 * <p>
	 * ※null、ブランクに対しては、nullを返却します。
	 * </p>
	 *
	 * @param str 変換対象の文字列。
	 * @return 返還後の{@link Integer}オブジェクト。
	 */
	public static Integer toInteger(String str) {
		return toInteger(str, null);
	}

	/**
	 * 引数の文字列を数値に変換します。
	 * <p>
	 * ※null、ブランクに対しては、{@code defaultValue}を返却します。
	 * </p>
	 *
	 * @param str 変換対象の文字列。
	 * @param defaultValue デフォルト値。
	 * @return 返還後の{@link Integer}オブジェクト。
	 */
	public static Integer toInteger(String str, Integer defaultValue) {
		if (StringUtils.isBlank(str)) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(str);
		} catch (final NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * 引数の文字列を数値に変換します。
	 * <p>
	 * ※null、ブランクに対しては、nullを返却します。
	 * </p>
	 *
	 * @param str 変換対象の文字列。
	 * @return 返還後の{@link Long}オブジェクト。
	 */
	public static Long toLong(String str) {
		return toLong(str, null);
	}

	/**
	 * 引数の文字列を数値に変換します。
	 * <p>
	 * ※null、ブランクに対しては、{@code defaultValue}を返却します。
	 * </p>
	 *
	 * @param str 変換対象の文字列。
	 * @param defaultValue デフォルト値。
	 * @return 返還後の{@link Long}オブジェクト。
	 */
	public static Long toLong(String str, Long defaultValue) {
		if (StringUtils.isBlank(str)) {
			return defaultValue;
		}
		try {
			return Long.parseLong(str);
		} catch (final NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * 引数の文字列を数値に変換します。
	 * <p>
	 * ※null、ブランクに対しては、nullを返却します。
	 * </p>
	 *
	 * @param str 変換対象の文字列。
	 * @return 返還後の{@link Double}オブジェクト。
	 */
	public static Double toDouble(String str) {
		return toDouble(str, null);
	}

	/**
	 * 引数の文字列を数値に変換します。
	 * <p>
	 * ※null、ブランクに対しては、{@code defaultValue}を返却します。
	 * </p>
	 *
	 * @param str 変換対象の文字列。
	 * @param defaultValue デフォルト値。
	 * @return 返還後の{@link Double}オブジェクト。
	 */
	public static Double toDouble(String str, Double defaultValue) {
		if (StringUtils.isBlank(str)) {
			return defaultValue;
		}
		try {
			return Double.parseDouble(str);
		} catch (final NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * プリミティブとオブジェクトの値比較を行います。
	 *
	 * @param constValue 定数。
	 * @param targetValue 比較対象の値。（※null許可）
	 * @return true.値が一致。false.一致しない。
	 */
	public static boolean equals(int constValue, Integer targetValue) {
		if (targetValue == null) {
			return false;
		}
		return constValue == targetValue.intValue();
	}

	/**
	 * プリミティブとオブジェクトの値比較を行います。
	 *
	 * @param constValue 定数。
	 * @param targetValue 比較対象の値。（※null許可）
	 * @return true.値が一致。false.一致しない。
	 */
	public static boolean equals(long constValue, Long targetValue) {
		if (targetValue == null) {
			return false;
		}
		return constValue == targetValue.intValue();
	}
}
