package org.tec.datos1.flow.handlers;

import org.eclipse.swt.widgets.Shell;
import org.tec.datos1.flow.CodeParser;
import org.tec.datos1.flow.debug.DebugStepper;
import org.tec.datos1.flow.parts.DiagramView;
import org.tec.datos1.flow.storage.ASTStorage;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.e4.core.di.annotations.Execute;

public class StepInHandler {
	/**
	 * Se ejecuta al presionar el boton
	 * @param shell Ventana donde se va a ejecutar la accion
	 */
	@Execute
	public void execute(Shell shell) {
		if (DebugStepper.getDebugThread() != null)
			DebugStepper.stepInto();
		try {
			CodeParser.execute();
			List<String> methods = ASTStorage.getMethods();
			String[] array = new String[methods.size()];
			int cont = 0;
			for(String method : methods) {
				array[cont] = method;
				cont++;
			}
			DiagramView.setMethods(array);
			
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
