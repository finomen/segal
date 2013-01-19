package ru.kt15.finomen.task1;

public class EulerImplicit extends Solver {
	private final int steps;
	
	public EulerImplicit(int steps, double h, double size, double[] v0, Function...system) {
		super(h, size, v0, system);
		this.steps = steps;
	}
	
	@Override
	public void run() {
		for (int ix = 0; ix < max / h; ++ix) {		
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
		}
	}

	@Override
	public String name() {
		return "EulerImplicit";
	}
}
