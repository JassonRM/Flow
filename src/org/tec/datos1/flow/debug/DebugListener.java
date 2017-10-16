package org.tec.datos1.flow.debug;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.IStep;
import org.eclipse.debug.core.model.LineBreakpoint;
import org.eclipse.jdt.core.dom.Message;
import org.eclipse.jdt.debug.core.IJavaBreakpoint;
import org.eclipse.jdt.debug.core.IJavaBreakpointListener;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.debug.core.IJavaLineBreakpoint;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jdt.debug.core.IJavaType;
import org.tec.datos1.flow.handler.Handler;

public class DebugListener implements IJavaBreakpointListener{

	@Override
	public void addingBreakpoint(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void breakpointHasCompilationErrors(IJavaLineBreakpoint arg0, Message[] arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void breakpointHasRuntimeException(IJavaLineBreakpoint arg0, DebugException arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int breakpointHit(IJavaThread thread, IJavaBreakpoint breakpoint) {
		//thread.terminateEvaluation();
		try {
			Handler.thread = thread;
			IStep stepper = (IStep)thread;
			stepper.stepInto();
			System.out.println(thread.getName() + thread.isSuspended() + thread.canTerminate());
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.err.println("EXCEPCIÔN");
		}
		
		ILineBreakpoint lineBreak = (ILineBreakpoint)breakpoint;
		int numLinea = 0; 
		try {
			numLinea = lineBreak.getLineNumber();
		}catch(CoreException e) {}
		
		System.out.println("Breakpoint:" + numLinea);
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void breakpointInstalled(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void breakpointRemoved(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int installingBreakpoint(IJavaDebugTarget arg0, IJavaBreakpoint arg1, IJavaType arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
