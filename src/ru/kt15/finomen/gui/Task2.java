package ru.kt15.finomen.gui;

import org.eclipse.swt.widgets.Composite;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import swing2swt.layout.BoxLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Spinner;

public class Task2 extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Task2(Composite parent, int style) {
		super(parent, style);
		setLayout(new BorderLayout(0, 0));
		
		Group grpControl = new Group(this, SWT.NONE);
		grpControl.setText("Control");
		grpControl.setLayoutData(BorderLayout.WEST);
		grpControl.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		Group grpScheme = new Group(grpControl, SWT.NONE);
		grpScheme.setText("Scheme");
		grpScheme.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		Button btnExplicitUpstream = new Button(grpScheme, SWT.RADIO);
		btnExplicitUpstream.setText("Explicit upstream");
		
		Button btnExplicitDownstream = new Button(grpScheme, SWT.RADIO);
		btnExplicitDownstream.setText("Explicit downstream");
		
		Button btnImplicitUpstream = new Button(grpScheme, SWT.RADIO);
		btnImplicitUpstream.setText("Implicit upstream");
		
		Button btnImplicitDownstream = new Button(grpScheme, SWT.RADIO);
		btnImplicitDownstream.setText("Implicit downstream");
		
		Combo presets = new Combo(grpControl, SWT.NONE);
		presets.setItems(new String[] {"Simple", "Front shift", "Front blur", "Implicit upstream only", "\u03BC=0"});
		presets.setText("<custom>");
		
		Group grpInitialConditions = new Group(grpControl, SWT.NONE);
		grpInitialConditions.setText("Initial conditions");
		grpInitialConditions.setLayout(new GridLayout(2, false));
		
		Combo combo = new Combo(grpInitialConditions, SWT.NONE);
		combo.setItems(new String[] {"Peak", "Stage left", "Stage right"});
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		combo.select(0);
		
		Label lblWidth = new Label(grpInitialConditions, SWT.NONE);
		lblWidth.setText("Width");
		
		Spinner spinner = new Spinner(grpInitialConditions, SWT.BORDER);
		spinner.setSelection(10);
		spinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Group grpParams = new Group(grpControl, SWT.NONE);
		grpParams.setText("Params");
		grpParams.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(grpParams, SWT.NONE);
		lblNewLabel.setText("Steps");
		
		Spinner spinner_1 = new Spinner(grpParams, SWT.BORDER);
		GridData gd_spinner_1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_spinner_1.widthHint = 85;
		spinner_1.setLayoutData(gd_spinner_1);
		
		Label lblNodes = new Label(grpParams, SWT.NONE);
		lblNodes.setText("Nodes");
		
		Spinner spinner_2 = new Spinner(grpParams, SWT.BORDER);
		spinner_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label label = new Label(grpParams, SWT.NONE);
		label.setText("\u03F0");
		
		Spinner spinner_3 = new Spinner(grpParams, SWT.BORDER);
		spinner_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		spinner_3.setDigits(2);
		
		Label label_1 = new Label(grpParams, SWT.NONE);
		label_1.setText("\u03BC");
		
		Spinner spinner_4 = new Spinner(grpParams, SWT.BORDER);
		spinner_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		spinner_4.setDigits(1);
		
		Button btnCalculate = new Button(grpControl, SWT.NONE);
		btnCalculate.setText("Calculate");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
