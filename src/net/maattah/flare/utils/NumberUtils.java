package net.maattah.flare.utils;

public class NumberUtils {

	public static boolean isInteger(String value) {
	    try {
	        Integer.parseInt(value);
	    } catch (NumberFormatException e) {
	        return false;
	    }
	    return true;
	}
}
