package com.lyy.hitogether.util;

import cn.sharesdk.socialization.SocializationCustomPlatform.UserGender;

public class CommonUtils {

	public static UserGender bmobGender2Mob(String gender) {
		if (gender.equals("0")) {
			return UserGender.Female;
		} else if (gender.equals("1")) {
			return UserGender.Male;
		} else {

			return UserGender.Unknown;
		}
	}

}
