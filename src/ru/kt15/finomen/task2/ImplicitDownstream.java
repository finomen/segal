package ru.kt15.finomen.task2;

import java.util.Arrays;

import ru.kt15.finomen.math.LinearEquationSolver;

public class ImplicitDownstream implements Schema {

	public double[][] calculate(double[] startValues, double kappa, double mu, int stepsCount) {
        double T[][] = new double[stepsCount][startValues.length];
        double dx = 1. / startValues.length;
        double dt = 1. / stepsCount;
        double uu = mu * dt / dx;
        double ae = kappa * dt / (dx * dx);
        int n = startValues.length;
        //formula
        /*
         *  uu = u * dt / dx
         *  ae = kappa * dt / (dx ^ 2)
         *  T[x][t] = T[x][t+1] * (1 - uu + 2ae) + T[x-1][t+1] * (-ae + uu) + T[x+1][t+1] * (-ae)
         */
        T[0] = Arrays.copyOf(startValues, n);
        for (int i = 1; i < stepsCount; i++) {
            T[i] = LinearEquationSolver.solve(-ae + uu, 1 - uu + 2 * ae, -ae, T[i - 1]);
        }
        return T;
    }

	@Override
	public String name() {
		return "ImplicitDownstream";
	}

}
