package ru.kt15.finomen.task2;

public class ExplicitDownstream implements Schema {

	public double[][] calculate(double[] startValues, double kappa, double mu, int stepsCount) {
		double T[][] = new double[stepsCount][startValues.length];
        double dx = 1. / startValues.length;
        double dt = 1. / stepsCount;
        double uu = mu * dt / dx;
        double ae = kappa * dt / (dx * dx);

        int n = startValues.length;
        T[0] = startValues;
        //formula
        /*
         *    uu = u * dt / dx
         *     ae = kappa * dt / (dx ^ 2)
         * T[t+1][x] = T[t][x] * (1 - uu - 2*ae) + T[t][x-1] * (ae) + T[t][x+1] * (ae + uu)
         */
        for (int time = 0; time < stepsCount - 1; time++) {
            for (int x = 1; x < n - 1; x++) {
                T[time + 1][x] = T[time][x] * (1 - uu - 2 * ae)
                               + T[time][x - 1] * (ae)
                               + T[time][x + 1] * (ae + uu);
            }
            T[time + 1][0] = T[time][0] * (1 - uu - ae)
                           + T[time][1] * (ae + uu);
            T[time + 1][n - 1] = T[time][n - 1] * (1 - ae)
                               + T[time][n - 2] * (ae);
        }
        return T;
    }

	@Override
	public String name() {
		return "ExplicitDownstream";
	}

}
