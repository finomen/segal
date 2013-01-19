package ru.kt15.finomen.task2;

public interface Schema {
	public double[][] calculate(double[] startValues, double kappa, double mu, int stepsCount);
	String name();
}
