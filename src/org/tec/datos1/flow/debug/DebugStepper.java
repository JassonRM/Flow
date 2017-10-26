package org.tec.datos1.flow.debug;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jdt.debug.core.IJavaThread;

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
			debugThread.stepInto();
			update();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Este método se encarga de ejecutar la acción de 
	 * StepOver del debugger de eclipse
	 */
	public static void stepOver() {
		try {
			debugThread.stepOver();
			update();
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Este método se encarga de obtener la línea en la que 
	 * se encuentra el debugger en dicho instante.
	 */
	public static void update(){
		try {
			IStackFrame Frame = null;
			
			while (Frame == null) {
				Frame = debugThread.getTopStackFrame();
			}
			System.out.println(Frame.getLineNumber());
			System.out.println(Frame.getName());
			System.out.println(Frame.getModelIdentifier());
			
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Este método se encarga de ejecutar la acción de 
	 * Resume del debugger de eclipse
	 */
	public static void resume() {
		try {
			debugThread.resume();
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}
	
	
}
