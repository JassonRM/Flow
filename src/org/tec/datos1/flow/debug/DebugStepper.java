package org.tec.datos1.flow.debug;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.tec.datos1.flow.parts.DiagramView;
import org.tec.datos1.flow.storage.ASTStorage;

public class DebugStepper {
	
	private static  IJavaThread debugThread;

	public static IJavaThread getDebugThread() {
		return debugThread;
	}

	public static void setDebugThread(IJavaThread DebugThread) {
		debugThread = DebugThread;
	}
	/**
	 * Este método se encarga de ejecutar la acción de 
	 * StepInto del debugger de eclipse
	 */
	public static void stepInto() {
		try {
			ASTStorage step = ASTStorage.getRoot().findLine(update());
			if (step.getElement() instanceof MethodInvocation) {
				debugThread.stepInto();
				int currentLine = update();
				DiagramView.setLineNumber(currentLine);
			}
			else {
				stepOver();
			}
			
		} catch (Exception e) {
			stepOver();
		}
		
	}
	/**
	 * Este método se encarga de ejecutar la acción de 
	 * StepOver del debugger de eclipse
	 */
	public static void stepOver() {
		try {
			debugThread.stepOver();
			int currentLine = update();
			DiagramView.setLineNumber(currentLine);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Este método se encarga de obtener la línea en la que 
	 * se encuentra el debugger en dicho instante.
	 */
	public static int update(){
		try {
			IStackFrame Frame = null;
			while (Frame == null) {
				Frame = debugThread.getTopStackFrame();
				
			}
			return Frame.getLineNumber();
		} catch (DebugException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
