package org.tec.datos1.flow.parts;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class DiagramView {
	@Inject
	public DiagramView(Composite parent) {
		Label label = new Label(parent, SWT.None);
		label.setText("Hola");
	}
}
