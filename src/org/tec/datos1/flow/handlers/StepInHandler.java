package org.tec.datos1.flow.handlers;

import org.eclipse.swt.widgets.Shell;
import org.tec.datos1.flow.debug.DebugStepper;
import org.eclipse.e4.core.di.annotations.Execute;

public class StepInHandler {
	@Execute
	public void execute(Shell shell) {
		if (DebugStepper.getDebugThread() != null)
			DebugStepper.stepInto();
	}
}
