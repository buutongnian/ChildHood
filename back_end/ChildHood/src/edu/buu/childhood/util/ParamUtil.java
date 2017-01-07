package edu.buu.childhood.util;

public abstract class ParamUtil {
	public abstract static class VCode {
		public static String generate(String vcode) {
			return "{vcode:'" + vcode + "'}";
		}

		public static String getVcode() {
			return String.valueOf((int) (Math.random() * 900000) + 100000);
		}
	}
}
