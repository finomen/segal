package ru.kt15.finomen.gui;

import org.eclipse.swt.widgets.Composite;
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

public class CW extends Composite {

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
		
		Spinner T = new Spinner(grpControl, SWT.BORDER);
		T.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		T.setMaximum(4000);
		T.setSelection(650);
		
		Label lblNewLabel = new Label(grpControl, SWT.NONE);
		lblNewLabel.setText("Steps");
		
		Spinner steps = new Spinner(grpControl, SWT.BORDER);
		steps.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		steps.setMaximum(10000);
		steps.setSelection(1000);
		
		Label lblTimeStep = new Label(grpControl, SWT.NONE);
		lblTimeStep.setText("Time step");
		
		Spinner timeStep = new Spinner(grpControl, SWT.BORDER);
		timeStep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		timeStep.setSelection(1);
		timeStep.setDigits(1);
		
		Label lblNodes = new Label(grpControl, SWT.NONE);
		lblNodes.setText("Nodes");
		
		Spinner nodes = new Spinner(grpControl, SWT.BORDER);
		nodes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		nodes.setMaximum(1000);
		nodes.setSelection(500);
		
		Label lblNodeStep = new Label(grpControl, SWT.NONE);
		lblNodeStep.setText("Node step");
		
		Spinner nodeStep = new Spinner(grpControl, SWT.BORDER);
		nodeStep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		nodeStep.setMaximum(100000);
		nodeStep.setDigits(5);
		nodeStep.setSelection(5);
		
		Button btnCalculate = new Button(grpControl, SWT.NONE);
		btnCalculate.setText("Calculate");
		
		ProgressBar progressBar = new ProgressBar(grpControl, SWT.NONE);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
