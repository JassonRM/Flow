package org.tec.datos1.flow.debug;

import org.eclipse.debug.core.DebugException;
import org.eclipse.jdt.debug.core.IJavaThread;

public class DebugStepper {
	
	private static  IJavaThread debugThread;
	

	public static IJavaThread getDebugThread() {
		return debugThread;
	}

	public static void setDebugThread(IJavaThread DebugThread) {
		debugThread = DebugThread;
	}
	
	public static void stepInto() {
		try {
			debugThread.stepInto();
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}
	
	public static void stepOver() {
		try {
			debugThread.stepOver();
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}
	
	public static void resume() {
		try {
			debugThread.resume();
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}
	
	
}
