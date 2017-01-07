package edu.buu.childhood.util;

import java.util.Date;

import edu.buu.childhood.common.C;

public abstract class AppKey {
	public static boolean check(String encryptedKey, Date now) {
		long time = now.getTime();
		long decode = Long.parseLong(EncryptionDes.decode(encryptedKey));
		if ((time - (decode - C.def.APPKEY)) < C.def.TIMEOUT_APPKEY) {
			return true;
		}
		return false;
	}

	public static String generateStr() {
		long time = (new Date()).getTime();
		return EncryptionDes.encode(String.valueOf(C.def.APPKEY + time));
	}
}
