package ru.kt15.finomen.task1;

public class Adams extends Solver {
	private int rkProgress = 0;
	public Adams(double h, double size, double[] v0, Function...system) {
		super(h, size, v0, system);
	}
	
	@Override
	public void run() {
		RungeKutta rk = new RungeKutta(h, max, v0, system);

		rk.setProgressHandler(new ProgressHandler() {
			@Override
			public void handleProgress(int step, int count) {
				Adams.this.progress(rkProgress = step, count + (int)(max / h));
			}
		});
		
		rk.run();
				
		for (int i = 0; i < system.length; ++i) {
			result[1][i] = rk.y(i, h);
		}
		
		for (int ix = 1; ix < max / h; ++ix) {		
			double[] yp = new double[system.length];
			
			for (int i = 0; i < system.length; ++i) {
				yp[i] = result[ix][i] + h * (3 / 2 * system[i].value(ix * h, result[ix]) - 1 / 2 * system[i].value(ix * h - h, result[ix - 1]));
			}
			
			for (int i = 0; i < system.length; ++i) {
				result[ix + 1][i] = result[ix][i] + 0.5 * h * (system[i].value(ix * h, result[ix]) + system[i].value(ix * h + h, yp));
			}
			
			progress(rkProgress + ix, rkProgress + (int)(max / h));
		}
	}
	
	@Override
	public String name() {
		return "Adams";
	}
}
