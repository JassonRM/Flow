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
	
	public static void stepInto() {
		try {
			
			
			debugThread.stepInto();
			update();
			
//			Thread p = (Thread)debugThread.getThreadObject();
//			
//			System.out.println(p.getStackTrace()[0].getClassName());
//			System.out.println(p.getStackTrace()[0].getMethodName());
//			System.out.println(p.getStackTrace()[0].getLineNumber());
			
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
	
	public static void update(){
		
		try {
			
			IStackFrame Frame = null;
			
			
			//solucionar...
			while (Frame == null) {
				 
				//auxThread.suspend();
				Frame = debugThread.getTopStackFrame();
				//System.out.println(Frame);
				}
			System.out.println(Frame.getLineNumber());
			
			
			
			
			
		} catch (DebugException e) {
			// TODO Auto-generated catch block
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
