package ru.kt15.finomen.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

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
		
		TabItem tbtmCW = new TabItem(tabFolder, SWT.NONE);
		tbtmCW.setText("CW");
		
		Composite composite3 = new CW(tabFolder, SWT.NONE);
		tbtmCW.setControl(composite3);
		
		shlTasks.open();
		shlTasks.layout();
		while (!shlTasks.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
