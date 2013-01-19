package ru.kt15.finomen.task1;

public class RungeKutta extends Solver {
	public RungeKutta(double h, double size, double[] v0, Function...system) {
		super(h, size, v0, system);
	}
	
	public void run() {
		double[] k1 = new double[v0.length];
		double[] k2 = new double[v0.length];
		double[] k3 = new double[v0.length];
		double[] k4 = new double[v0.length];
		
		for (int ix = 0; ix < max / h; ++ix) {
			for (int i = 0; i < system.length; ++i) {
				k1[i] = h * system[i].value(ix * h, result[ix]);
			}
			
			k2 = calculate(ix * h + h/2, result[ix], 0.5, k1);
			k3 = calculate(ix * h + h/2, result[ix], 0.5, k2);
			k4 = calculate(ix * h + h, result[ix], 1, k3);
			
			for (int i = 0; i < system.length; ++i) {
				result[ix + 1][i] = result[ix][i] + (k1[i] + 2 * k2[i] + 2 * k3[i] + k4[i]) / 6;
			}
			
			progress(ix, (int) (max / h));
		}
	}
	
	private double[] calculate(double d, double[] es, double e, double[] k1) {
		double[] result = new double[es.length];
		double[] ys = new double[es.length];
				
		for (int i = 0; i < es.length; ++i) {
			ys[i] = es[i] + e * k1[i];
		}
		
		for (int i = 0; i < es.length; ++i) {
			result[i] = h * system[i].value(d, ys);
		}
		return result;
	}

	@Override
	public String name() {
		return "RungeKutta";
	}
}
