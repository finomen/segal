package ru.kt15.finomen.task1;

public class EulerExplicit extends Solver {	
	public EulerExplicit(double h, double size, double[] v0, Function...system) {
		super(h, size, v0, system);
	}
	
	@Override
	public String name() {
		return "EulerExplicit";
	}

	@Override
	public void run() {
		for (int ix = 0; ix < max / h; ++ix) {		
			for (int i = 0; i < system.length; ++i) {
				result[ix + 1][i] = result[ix][i] + h * system[i].value(ix * h, result[ix]);
				progress(ix, (int) (max / h));
			}
		}
	}
}
