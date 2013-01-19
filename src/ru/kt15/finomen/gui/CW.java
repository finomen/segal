package ru.kt15.finomen.gui;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.ProgressMonitor;

import org.eclipse.swt.widgets.Composite;

import ru.kt15.finomen.cw.CalculationResults;
import ru.kt15.finomen.cw.Calculator;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import swing2swt.layout.BoxLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.mouse.camera.CameraMouseController;
import org.jzy3d.chart.controllers.thread.camera.CameraThreadController;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapHotCold;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.maths.Scale;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CW extends Composite {

	private ProgressBar progressBar;
	private Spinner nodeStep;
	private Spinner nodes;
	private Spinner timeStep;
	private Spinner steps;
	private Spinner t;
	
	private AbstractDrawable concentrationScatter;
	private Shape concentrationSurface;
	private Shape temperatureSurface;
	private Shape energySurface;
	private Button btnConcentartionScatter;
	private Button btnConcentrationSurface;
	private Button btnTemperatureSurface;
	private Button btnEnergySurface;
	private Chart chart;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CW(Composite parent, int style) {
		super(parent, style);
		setLayout(new BorderLayout(0, 0));
		
		Group grpControl = new Group(this, SWT.NONE);
		grpControl.setText("Control");
		grpControl.setLayoutData(BorderLayout.WEST);
		grpControl.setLayout(new GridLayout(2, false));
		
		Label lblT = new Label(grpControl, SWT.NONE);
		lblT.setText("T");
		
		t = new Spinner(grpControl, SWT.BORDER);
		t.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		t.setMaximum(4000);
		t.setSelection(650);
		
		Label lblNewLabel = new Label(grpControl, SWT.NONE);
		lblNewLabel.setText("Steps");
		
		steps = new Spinner(grpControl, SWT.BORDER);
		steps.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		steps.setMaximum(10000);
		steps.setSelection(1000);
		
		Label lblTimeStep = new Label(grpControl, SWT.NONE);
		lblTimeStep.setText("Time step");
		
		timeStep = new Spinner(grpControl, SWT.BORDER);
		timeStep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		timeStep.setSelection(1);
		timeStep.setDigits(1);
		
		Label lblNodes = new Label(grpControl, SWT.NONE);
		lblNodes.setText("Nodes");
		
		nodes = new Spinner(grpControl, SWT.BORDER);
		nodes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		nodes.setMaximum(1000);
		nodes.setSelection(500);
		
		Label lblNodeStep = new Label(grpControl, SWT.NONE);
		lblNodeStep.setText("Node step");
		
		nodeStep = new Spinner(grpControl, SWT.BORDER);
		nodeStep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		nodeStep.setMaximum(100000);
		nodeStep.setDigits(5);
		nodeStep.setSelection(5);
		
		Button btnCalculate = new Button(grpControl, SWT.NONE);
		btnCalculate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				solve();
			}
		});
		btnCalculate.setText("Calculate");
		
		progressBar = new ProgressBar(grpControl, SWT.NONE);
		
		Group grpDisplay = new Group(grpControl, SWT.NONE);
		grpDisplay.setText("Display");
		grpDisplay.setLayout(new FillLayout(SWT.VERTICAL));
		GridData gd_grpDisplay = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_grpDisplay.widthHint = 234;
		grpDisplay.setLayoutData(gd_grpDisplay);
		
		btnConcentartionScatter = new Button(grpDisplay, SWT.CHECK);
		btnConcentartionScatter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (btnConcentartionScatter.getSelection()) {
					chart.addDrawable(concentrationScatter);
					chart.setScale(new Scale(0, 30));
					chart.getView().lookToBox(chart.getScene().getGraph().getBounds());
					chart.getView().shoot();
				} else {
					chart.removeDrawable(concentrationScatter);
				}
			}
		});
		btnConcentartionScatter.setEnabled(false);
		btnConcentartionScatter.setText("Concentartion scatter");
		
		btnConcentrationSurface = new Button(grpDisplay, SWT.CHECK);
		btnConcentrationSurface.setEnabled(false);
		btnConcentrationSurface.setText("Concentration surface");
		btnConcentrationSurface.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (btnConcentrationSurface.getSelection()) {
					chart.addDrawable(concentrationSurface);
					chart.setScale(new Scale(0, 30));
					chart.getView().lookToBox(chart.getScene().getGraph().getBounds());
					chart.getView().shoot();
				} else {
					chart.removeDrawable(concentrationSurface);
				}
			}
		});
		
		btnTemperatureSurface = new Button(grpDisplay, SWT.CHECK);
		btnTemperatureSurface.setEnabled(false);
		btnTemperatureSurface.setText("Temperature surface");
		
		btnTemperatureSurface.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (btnTemperatureSurface.getSelection()) {
					chart.addDrawable(temperatureSurface);
					chart.setScale(new Scale(0, 30));
					chart.getView().lookToBox(chart.getScene().getGraph().getBounds());
					chart.getView().shoot();
				} else {
					chart.removeDrawable(temperatureSurface);
				}
			}
		});
		
		btnEnergySurface = new Button(grpDisplay, SWT.CHECK);
		btnEnergySurface.setEnabled(false);
		btnEnergySurface.setText("Energy surface");
		
		btnEnergySurface.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (btnEnergySurface.getSelection()) {
					chart.addDrawable(energySurface);
					chart.setScale(new Scale(0, 30));
					chart.getView().lookToBox(chart.getScene().getGraph().getBounds());
					chart.getView().shoot();
				} else {
					chart.removeDrawable(energySurface);
				}
			}
		});
		
		chart = new Chart(Quality.Advanced, "awt");
		
		chart.getAxeLayout().setMainColor(Color.GREEN);
		chart.getAxeLayout().setXAxeLabel("step");
		chart.getAxeLayout().setYAxeLabel("x");
		chart.getAxeLayout().setZAxeLabel("temp");
		chart.setAxeDisplayed(true);
		
		chart.getView().setBackgroundColor(Color.BLACK);
		
	    CameraMouseController mouse   = new CameraMouseController();
        
        chart.addController(mouse);
        
        
        CameraThreadController thread = new CameraThreadController();
        mouse.addSlaveThreadController(thread);
        chart.addController(thread);

        thread.start();
		
		Composite chartComposite = new Composite(this, SWT.EMBEDDED);
		chartComposite.setLayoutData(BorderLayout.CENTER);
		java.awt.Frame chartFrame = SWT_AWT.new_Frame(chartComposite);
		
		chartFrame.add((Component)chart.getCanvas());		
		chart.setScale(new Scale(-1, 1));

		
	}

	private void solve() {
		chart.getScene().getGraph().getAll().clear();
		btnConcentartionScatter.setEnabled(false);
		btnConcentartionScatter.setSelection(false);
		
		btnConcentrationSurface.setEnabled(false);
		btnConcentrationSurface.setSelection(false);
		
		btnTemperatureSurface.setEnabled(false);
		btnTemperatureSurface.setSelection(false);
		
		btnEnergySurface.setEnabled(false);
		btnEnergySurface.setSelection(false);
		
		progressBar.setEnabled(true);
		
		final int steps = this.steps.getSelection();
				
		final int nodes = this.nodes.getSelection();
		final double timeStep = readSpinner(this.timeStep);
		final double nodeStep = readSpinner(this.nodeStep);
		final double T = readSpinner(this.t);
		
		progressBar.setMaximum(steps * 5);
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				final CalculationResults values = Calculator.calculate(
                        T, timeStep, steps, nodeStep, nodes,
                        new Calculator.ProgressListener() {
                            public void progessUpdated(final int progress) {
                            	getDisplay().asyncExec(new Runnable() {
                					@Override
                					public void run() {
                						progressBar.setSelection(progress);
                					}
                					
                				});
                            }
                        });
				
				System.out.println("Concentration scatter...");
				ArrayList<Coord3d> as = new ArrayList<Coord3d>();
				
				for (int i = 0; i < steps; ++ i) {
					getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							progressBar.setSelection(progressBar.getSelection() + 1);
						}
					});
					
					for (int j = 0; j < nodes; ++j) {
						double a = values.getDensities()[i][j];
						a = 1 / (100 * a);
						for (double z = 0; z < 1; z += a) {
							as.add(new Coord3d(i, j, z));
						}
					}
				}
				
				concentrationScatter = new Scatter(as.toArray(new Coord3d[0]), Color.YELLOW);
				
				System.out.println("Concentration surface...");
				concentrationSurface = Builder.buildOrthonormal(new OrthonormalGrid(new Range(0, steps), steps, new Range(0, nodes), nodes), new Mapper() {			
					@Override
					public double f(double x, double y) {
						if ((int)y == 0) {
							getDisplay().asyncExec(new Runnable() {
								@Override
								public void run() {
									progressBar.setSelection(progressBar.getSelection() + 1);
								}
							});
						}
						return values.getDensities()[Math.min(steps - 1, (int)x)][Math.min(nodes - 1, (int)y)];
					}
				});
				
				concentrationSurface.setColorMapper(new ColorMapper(new ColorMapRainbow(), concentrationSurface.getBounds().getZmin(), concentrationSurface.getBounds().getZmax()));
				concentrationSurface.setWireframeDisplayed(false);
				
				System.out.println("Energy surface...");
				energySurface = Builder.buildOrthonormal(new OrthonormalGrid(new Range(0, steps), steps, new Range(0, nodes), nodes), new Mapper() {			
					@Override
					public double f(double x, double y) {
						if ((int)y == 0) {
							getDisplay().asyncExec(new Runnable() {
								@Override
								public void run() {
									progressBar.setSelection(progressBar.getSelection() + 1);
								}
							});
						}
						return values.getEnergies()[Math.min(steps - 1, (int)x)][Math.min(nodes - 1, (int)y)] * 40;
					}
				});
				
				energySurface.setColorMapper(new ColorMapper(new ColorMapHotCold(), energySurface.getBounds().getZmin(), energySurface.getBounds().getZmax()));
				energySurface.setWireframeDisplayed(false);
				
				System.out.println("Temperature surface...");
				temperatureSurface = Builder.buildOrthonormal(new OrthonormalGrid(new Range(0, steps), steps, new Range(0, nodes), nodes), new Mapper() {			
					@Override
					public double f(double x, double y) {
						if ((int)y == 0) {
							getDisplay().asyncExec(new Runnable() {
								@Override
								public void run() {
									progressBar.setSelection(progressBar.getSelection() + 1);
								}
							});
						}
						
						return values.getTemperatures()[Math.min(steps - 1, (int)x)][Math.min(nodes - 1, (int)y)] / T;
					}
				});
				
				temperatureSurface.setColorMapper(new ColorMapper(new ColorMapHotCold(), temperatureSurface.getBounds().getZmin(), temperatureSurface.getBounds().getZmax()));
				temperatureSurface.setWireframeDisplayed(false);
				
				System.out.println("Done");
				
				getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						progressBar.setEnabled(false);
						btnConcentartionScatter.setEnabled(true);
						btnConcentrationSurface.setEnabled(true);
						btnTemperatureSurface.setEnabled(true);
						btnEnergySurface.setEnabled(true);
					}
					
				});
			}
			
		}).start();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	private static double readSpinner(Spinner spinner) {
		return spinner.getSelection() * 1.0 / Math.pow(10, spinner.getDigits());
	}

}
