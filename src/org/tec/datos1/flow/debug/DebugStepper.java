package org.tec.datos1.flow.debug;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.tec.datos1.flow.CodeParser;
import org.tec.datos1.flow.handlers.Methods;
import org.tec.datos1.flow.storage.ASTStorage;

public class DebugStepper {
	
	private static  IJavaThread debugThread;
	static boolean b = true;

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
				MethodInvocation mI = (MethodInvocation)step.getElement();
				ICompilationUnit unit = Methods.findClass(mI.resolveMethodBinding().getDeclaringClass().getQualifiedName());
				CodeParser.executeSpecific(unit);
				ASTStorage.getMethod(mI.getName().toString()).print();
			}
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
 			if (b) {
 				CodeParser.execute();
 				b = false;
 			}
 			
 			Methods.load();
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
			//System.out.println(Frame.getName());
			//System.out.println(Frame.getModelIdentifier());
			
			
		} catch (DebugException e) {
			e.printStackTrace();
		}
		return 0;
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
