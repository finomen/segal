package ru.kt15.finomen.task1;

public class EulerRealImplicit extends Solver {
	public EulerRealImplicit(int steps, double h, double size, double[] v0, Function...system) {
		super(h, size, v0, system);
	}
	
	@Override
	public void run() {
		EquationSystemSolver solver = new EquationSystemSolver();
		for (int ix = 0; ix < max / h; ++ix) {
			
			/*
			double[] vn = result[ix];
			double[] vnn = new double[vn.length];
			
			for (int s = 0; s < steps; ++s) {
				for (int i = 0; i < system.length; ++i) {
					vnn[i] = result[ix][i] + h * system[i].value(ix * h, vn);
				}
				
				vn = vnn;
				progress(ix * steps + s, (int) (max / h) * steps);
			}
			
			result[ix + 1] = vn;
			*/
			progress(ix, (int) (max / h));
			result[ix + 1] = solver.solve(result[ix], h, system);
		}
		
		solver.free();
	}

	@Override
	public String name() {
		return "EulerImplicit";
	}
}
