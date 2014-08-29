package dhbk.maas.utils;

import java.text.SimpleDateFormat;

public class FormatTime {

	public static String formatDateFromMiliseconds (long milis) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss") ;
		return  sdf.format(milis) ;
	}
}
