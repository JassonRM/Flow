package org.tec.datos1.flow.parts;



import java.util.LinkedList;

import javax.inject.Inject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.tec.datos1.flow.CodeParser;
import org.tec.datos1.flow.debug.DebugListener;
import org.tec.datos1.flow.graphics.ASTStorageParser;
import org.tec.datos1.flow.graphics.If;
import org.tec.datos1.flow.graphics.Line;
import org.tec.datos1.flow.graphics.Method;
import org.tec.datos1.flow.graphics.Process;
import org.tec.datos1.flow.graphics.While;
import org.tec.datos1.flow.graphics.Widget;
import org.tec.datos1.flow.storage.ASTStorage;


public class DiagramView {
	Canvas canvas;
	LinkedList<Widget> diagram; // Almacena todos los dibujos que se van a hacer
	//Variable con clase que se va a dibujar
	
	@Inject
	public DiagramView(Composite parent) {
		//Aqui debe llamar a la funcion de AST y pasarle la referencia a esta instancia
		
//		Composite selector = new Composite(parent, SWT.NONE);
//		selector.setSize(parent.getBounds().width, 30);
//		Composite drawing = new Composite(parent, SWT.NONE);
//		drawing.setSize(parent.getBounds().width, parent.getBounds().height - 30);
		
		GridLayout layout = new GridLayout();
		parent.setLayout(layout);
		
		Combo methodSelector = new Combo(parent, SWT.NONE);
		Combo selector2 = new Combo(parent, SWT.NONE);
		
		
		ScrolledComposite container = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		this.canvas = new Canvas(container, SWT.NONE);
		canvas.setSize(500, 500);
		container.setContent(canvas);


		ASTStorageParser astParser = new ASTStorageParser();
		diagram = new LinkedList<Widget>();
		
		//Anade todo al selector
		String[] methods = {"Main", "Print", "getHelp"};
		methodSelector.setItems(methods);
		methodSelector.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Changed selection");
				try {
					CodeParser.execute();
					diagram =  astParser.parse(ASTStorage.getRoot());
				} catch (Exception e1) {
					System.err.println("No se pudo parsear el arbol");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Default selected");
			}
		});

		canvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawLine(0, 0, 500, 500);
				e.gc.drawLine(0, 500, 500, 0);
				for(Widget widget : diagram) {
					System.out.println(widget);
					widget.draw(e.gc);
				}
			}
		});
	}


	
	public void draw() { // Recibe la clase que se va a dibujar
		//Guarda la clase que se va a dibujar
		canvas.redraw();
	}
}
