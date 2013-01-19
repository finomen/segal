package ru.kt15.finomen.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Composite;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import swing2swt.layout.BoxLayout;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.MultiColorScatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class MainWindow {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shlTasks = new Shell();
		shlTasks.setSize(450, 300);
		shlTasks.setText("Tasks");
		shlTasks.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tabFolder = new TabFolder(shlTasks, SWT.NONE);
		
		TabItem tbtmTask1 = new TabItem(tabFolder, SWT.NONE);
		tbtmTask1.setText("Task1");
		
		Composite composite1 = new Task1(tabFolder, SWT.NONE);
		tbtmTask1.setControl(composite1);
		
		TabItem tbtmTask2 = new TabItem(tabFolder, SWT.NONE);
		tbtmTask2.setText("Task2");
		
		Composite composite2 = new Task2(tabFolder, SWT.NONE);
		tbtmTask2.setControl(composite2);
		
		shlTasks.open();
		shlTasks.layout();
		while (!shlTasks.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
