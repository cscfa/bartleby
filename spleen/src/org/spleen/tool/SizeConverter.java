package org.spleen.tool;

/**
 * SizeConverter tool class.
 * 
 * SizeConverter is a tool class that provide
 * easy size convertion between human readable
 * size and byte.
 * 
 * @author vallance
 *
 */
public class SizeConverter {

	/**
	 * Kilo.
	 * 
	 * Kilo multiplicator.
	 */
	public static final double K = 1024;
	/**
	 * Mega.
	 * 
	 * Mega multiplicator.
	 */
	public static final double M = 1048576;
	/**
	 * Giga.
	 * 
	 * Giga multiplicator.
	 */
	public static final double G = 1073741824;
	/**
	 * Tera.
	 * 
	 * Tera multiplicator.
	 */
	public static final double T = 1099511627776d;
	/**
	 * Peta.
	 * 
	 * Peta multiplicator.
	 */
	public static final double E = 1125899906842624d;
	
	/**
	 * Default constructor.
	 * 
	 * SizeConverter default constructor.
	 */
	public SizeConverter() {
		super();
	}
	
	/**
	 * Convert.
	 * 
	 * Convert a base number to selected multiplicator.
	 * 
	 * @param value				The base number
	 * @param multiplicator		The multiplicator
	 * @return					The result of the base multiply be the multiplicator operation
	 */
	public static double convert(double value, double multiplicator){
		return value * multiplicator;
	}

}
