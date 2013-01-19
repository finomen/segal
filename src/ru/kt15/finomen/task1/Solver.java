package ru.kt15.finomen.task1;

public abstract class Solver implements Runnable {
	protected final double h, max;
	protected final double v0[];
	protected final double[][] result;
	protected ProgressHandler progress;
	protected final Function[] system;
	
	public Solver(double h, double size, double[] v0, Function[] system) {
		this.h = h;
		this.max = size;
		this.v0 = v0;
		this.system = system;
		result = new double[(int) (max / h) + 2][v0.length];
		result[0] = v0;		
	}
	
	public final double y(int id, double t) {
		double corr = t - (int) (t / h) * h;
		return (result[(int) (t / h)][id] * (h - corr) + result[(int) (t / h) + 1][id] * corr) / h;
	}
	
	public abstract String name();
	
	protected final void progress(int step, int count) {
		if (progress != null) {
			progress.handleProgress(step, count);
		}
	}
	
	public final void setProgressHandler(ProgressHandler handler) {
		progress = handler;
	}
}
