package org.tec.datos1.flow.parts;



import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.tec.datos1.flow.graphics.If;
import org.tec.datos1.flow.graphics.Line;
import org.tec.datos1.flow.graphics.Method;
import org.tec.datos1.flow.graphics.Process;
import org.tec.datos1.flow.graphics.While;


public class DiagramView {
	@Inject
	public DiagramView(Composite parent) {
		//Todavia no funciona el scroll
		ScrolledComposite canvas = new ScrolledComposite(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		canvas.setExpandHorizontal(true);
		canvas.setExpandVertical(true);
		canvas.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
		canvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				Rectangle clientArea = parent.getClientArea();
				Method metodo = new Method(e.gc, "System.out.println(\"Hola mundo\")", clientArea.width / 2, 20);
				Process process = new Process(e.gc, "x = 1", clientArea.width / 2, 100);
				Line line1 = new Line(e.gc, metodo.getOutput(), process.getInput());
				If condicional = new If(e.gc, "a <= 1", clientArea.width / 2, 200);
				Line line2 = new Line(e.gc, process.getOutput(), condicional.getInput());
				While ciclo = new While(e.gc, "cont <= 100", clientArea.width * 2 / 3, 400);
				Line line3 = new Line(e.gc, condicional.getOutputFalse(), ciclo.getInput());
				Line line4 = new Line(e.gc, ciclo.getOutputTrue(), ciclo.getInputReturn());
			}
			
		});
		
	}
}
