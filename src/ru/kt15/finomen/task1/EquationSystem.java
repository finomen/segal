package ru.kt15.finomen.task1;

public class EquationSystem {
	public static Function[] get(final double r) {
		return new Function[] { new Function() {
			@Override
			public double value(double x, double... args) {
				return -10 * args[0] + 10 * args[1];
			}
		}, new Function() {
			@Override
			public double value(double x, double... args) {
				return -args[2] * args[0] + r * args[0] - args[1];
			}
		}, new Function() {
			@Override
			public double value(double x, double... args) {
				return args[0] * args[1] - 8.0 / 3 * args[2];
			}
		} };
	}
}
