package org.tec.datos1.flow.parts;



import javax.inject.Inject;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.debug.core.JDIDebugModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.tec.datos1.flow.debug.DebugListener;
import org.tec.datos1.flow.graphics.If;
import org.tec.datos1.flow.graphics.Line;
import org.tec.datos1.flow.graphics.Method;
import org.tec.datos1.flow.graphics.Process;
import org.tec.datos1.flow.graphics.While;
import org.tec.datos1.flow.handlers.Handler;
import org.tec.datos1.flow.storage.ASTStorage;


public class DiagramView {
	Canvas canvas;
	//Variable con clase que se va a dibujar
	
	@Inject
	public DiagramView(Composite parent) {
		//Aqui debe llamar a la funcion de AST y pasarle la referencia a esta instancia
		
		ScrolledComposite container = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		this.canvas = new Canvas(container, SWT.NONE);
		canvas.setSize(1000, 1000);
		container.setContent(canvas);
		
		Handler handler = new Handler();	
		
		canvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				try {
					handler.execute(new ExecutionEvent());
					MethodDeclaration metodo = (MethodDeclaration) ASTStorage.getRoot().getElement();
					Method dibujo = new Method(metodo.getName().toString(), 200, 20);
					dibujo.draw(e.gc);
				} catch (ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
	}
	
	public void draw() { // Recibe la clase que se va a dibujar
		//Guarda la clase que se va a dibujar
		canvas.redraw();
	}
}
