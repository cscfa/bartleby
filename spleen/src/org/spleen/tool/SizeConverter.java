package org.spleen.tool;

public class SizeConverter {

	public static final double K = 1024;
	public static final double M = 1048576;
	public static final double G = 1073741824;
	public static final double T = 1099511627776d;
	public static final double E = 1125899906842624d;
	
	public SizeConverter() {
		super();
	}
	
	public static double convert(double value, double multiplicator){
		return value * multiplicator;
	}

}
