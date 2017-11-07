package org.tec.datos1.flow.debug;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.tec.datos1.flow.CodeParser;
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
					
					DiagramView.selectMethod(((MethodInvocation) step.getElement()).getName().toString());
					DiagramView.setLineNumber(currentLine);
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
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
			if ( !DiagramView.getMethodSelector().getText().equalsIgnoreCase(
					((MethodDeclaration)ASTStorage.getMethodByLine(currentLine).getElement())
					.getName().toString())) {
				DiagramView.Select();
			}
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
