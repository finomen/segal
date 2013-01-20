package ru.kt15.finomen.task1;

import java.util.Locale;

public class EquationSystem {
	public static Function[] get(final double r) {
		return new Function[] { new Function() {
			@Override
			public double value(double x, double... args) {
				return -10 * args[0] + 10 * args[1]; //-10
			}

			@Override
			public String equation(double h, double[] x) {
				return String.format(Locale.US, "10 (y - x) == (x - %f) / %f", x[0], h);
			}

		}, new Function() {
			@Override
			public double value(double x, double... args) {
				return -args[2] * args[0] + r * args[0] - args[1];
			}
			
			@Override
			public String equation(double h, double[] x) {
				return String.format(Locale.US, "-x z + %f x - y == (y - %f) / %f", r, x[1], h);
			}

		}, new Function() {
			@Override
			public double value(double x, double... args) {
				return args[0] * args[1] - 8.0 / 3 * args[2];
			}
			
			@Override
			public String equation(double h,double[] x) {
				return String.format(Locale.US, "x y - 8/3 z == (z - %f) / %f", x[2], h);
			}
		} };
	}
}
