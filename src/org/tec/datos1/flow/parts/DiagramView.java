package org.tec.datos1.flow.parts;



import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.tec.datos1.flow.graphics.If;
import org.tec.datos1.flow.graphics.Line;
import org.tec.datos1.flow.graphics.Method;
import org.tec.datos1.flow.graphics.Process;
import org.tec.datos1.flow.graphics.While;


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
		
		canvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				//Codigo que indica que se va a dibujar
			}
		});
	}
	
	public void draw() { // Recibe la clase que se va a dibujar
		//Guarda la clase que se va a dibujar
		canvas.redraw();
	}
}
