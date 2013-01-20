package ru.kt15.finomen.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ProgressBar;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Scale;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.Point;

import ru.kt15.finomen.task1.Adams;
import ru.kt15.finomen.task1.EquationSystem;
import ru.kt15.finomen.task1.EulerExplicit;
import ru.kt15.finomen.task1.EulerImplicit;
import ru.kt15.finomen.task1.EulerRealImplicit;
import ru.kt15.finomen.task1.Function;
import ru.kt15.finomen.task1.ProgressHandler;
import ru.kt15.finomen.task1.RungeKutta;
import ru.kt15.finomen.task1.Solver;

public class GraphWidget extends Group {

	private Button btnRungekutta;
	private Button btnAdams;
	private Button btnEulerImplicit;
	private Button btnEulerExplicit;
	private ProgressBar progressBar;
	private final double r;
		
	private final AbstractDrawable[] graphs = new AbstractDrawable[4];
	private Chart chart;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public GraphWidget(Composite parent, int style, double r, Chart cchart) {
		super(parent, style);
		this.r = r;
		this.chart = cchart;
		
		setText(String.format("R=%f", r));
		
		setLayout(new GridLayout(2, false));
		
		btnEulerImplicit = new Button(this, SWT.CHECK);
		btnEulerImplicit.setEnabled(false);
		btnEulerImplicit.setText("Euler implicit");
		
		btnEulerExplicit = new Button(this, SWT.CHECK);
		btnEulerExplicit.setEnabled(false);
		
		btnEulerExplicit.setText("Euler explicit");
		
		btnRungekutta = new Button(this, SWT.CHECK);
		btnRungekutta.setEnabled(false);
		btnRungekutta.setText("Runge-Kutta");
		
		btnAdams = new Button(this, SWT.CHECK);
		btnAdams.setEnabled(false);
		btnAdams.setText("Adams");
		
		progressBar = new ProgressBar(this, SWT.NONE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

		btnEulerExplicit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (btnEulerExplicit.getSelection()) {
					chart.addDrawable(graphs[0]);
				} else {
					chart.removeDrawable(graphs[0]);
				}
				chart.setScale(new Scale(-30, 30));
				chart.getView().lookToBox(chart.getScene().getGraph().getBounds());
				chart.getView().shoot();
				
			}
		});
		
		btnEulerImplicit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (btnEulerImplicit.getSelection()) {
					chart.getScene().add(graphs[1]);
				} else {
					chart.removeDrawable(graphs[1]);
				}
				chart.setScale(new Scale(-30, 30));
				chart.getView().lookToBox(chart.getScene().getGraph().getBounds());
				chart.getView().shoot();
			}
		});
		
		btnRungekutta.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (btnRungekutta.getSelection()) {
					chart.addDrawable(graphs[2]);
				} else {
					chart.removeDrawable(graphs[2]);
				}
				chart.setScale(new Scale(-30, 30));
				chart.getView().lookToBox(chart.getScene().getGraph().getBounds());
				chart.getView().shoot();
			}
		});
		
		btnAdams.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {			
				if (btnAdams.getSelection()) {
					chart.addDrawable(graphs[3]);
				} else {
					chart.removeDrawable(graphs[3]);
				}
				chart.setScale(new Scale(-30, 30));
				chart.getView().lookToBox(chart.getScene().getGraph().getBounds());
				chart.getView().shoot();
			}
		});
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	private void setEnabled(final boolean eulerExplicit, final boolean eulerImplicit, final boolean rungeKutta, final boolean adams) {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				btnEulerImplicit.setEnabled(eulerImplicit);
				btnEulerExplicit.setEnabled(eulerExplicit);
				btnRungekutta.setEnabled(rungeKutta);
				btnAdams.setEnabled(adams);
			}
			
		});		
	}
	
	public void calculate(double timeStep, double maxTime, int eulerImplicitSteps) {
		setEnabled(false, false, false, false);
		getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				progressBar.setEnabled(true);
			}
		});
		Function[] system = EquationSystem.get(r);
		
		ProgressHandler handler = new ProgressHandler() {

			@Override
			public void handleProgress(final int step, final int count) {
				getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						progressBar.setMaximum(count);
						progressBar.setSelection(step);
					}
				});
			}
			
		};
		
		Solver solver = new EulerExplicit(timeStep, maxTime, new double[] {1,1,1}, system);
		solver.setProgressHandler(handler);
		solver.run();
		fillGraph(0, solver, maxTime);
		setEnabled(true, false, false, false);
		
		
		solver = new EulerImplicit(eulerImplicitSteps, timeStep, maxTime, new double[] {1,1,1}, system);
		solver.setProgressHandler(handler);
		solver.run();
		fillGraph(1, solver, maxTime);
		setEnabled(true, true, false, false);
		
		solver = new RungeKutta(timeStep, maxTime, new double[] {1,1,1}, system);
		solver.setProgressHandler(handler);
		solver.run();
		fillGraph(2, solver, maxTime);
		setEnabled(true, true, true, false);
		
		solver = new Adams(timeStep, maxTime, new double[] {1,1,1}, system);
		solver.setProgressHandler(handler);
		solver.run();
		fillGraph(3, solver, maxTime);
		setEnabled(true, true, true, true);
		
		getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				progressBar.setEnabled(false);
			}
		});
	}

	private void fillGraph(int g, Solver solver, double maxTime) {
		if (graphs[g] != null) {
			graphs[g].dispose();
		}
		graphs[g] = new LineStrip();
		
		Color c = Color.random();
		
		double step = maxTime / 5000;
	
		for (double t = 0; t < maxTime; t += step) {
			double x = solver.y(0, t);
			double y = solver.y(1, t);
			double z = solver.y(2, t);
			((LineStrip)graphs[g]).add(new Point(new Coord3d(x, y, z), c));
		}
	}
	
	@Override
	public void dispose() {
		for (int i = 0; i < 4; ++i) {
			if (graphs[i] != null) {
				chart.removeDrawable(graphs[i]);
				graphs[i].dispose();
			}
		}
	}
}
