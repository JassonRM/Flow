package org.tec.datos1.flow.handlers;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.e4.core.di.annotations.Execute;

public class StepOverHandler {
	@Execute
	public void execute(Shell shell) {
		System.out.println("Stepped over");
	}
	
}
