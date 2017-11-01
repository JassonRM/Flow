package org.tec.datos1.flow.parts;



import java.util.LinkedList;
import javax.inject.Inject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.tec.datos1.flow.graphics.ASTStorageParser;
import org.tec.datos1.flow.graphics.Widget;
import org.tec.datos1.flow.storage.ASTStorage;
import org.tec.datos1.flow.storage.DiagramSize;


public class DiagramView {
	Canvas canvas;
	LinkedList<Widget> diagram;
	DiagramSize diagramSize;
	static Combo methodSelector;
	
	/**
	 * Crea la vista para el plugin
	 * @param parent Componente de la interfaz en el cual se va a generar los graficos
	 */
	@Inject
	public DiagramView(Composite parent) {
		GridLayout layout = new GridLayout();
		GridData diagramLayout = new GridData();
		diagramLayout.grabExcessVerticalSpace = true;
		diagramLayout.grabExcessHorizontalSpace = true;
		
		GridData comboLayout = new GridData();
		comboLayout.widthHint = 200;
		
		parent.setLayout(layout);
		
		methodSelector = new Combo(parent, SWT.NONE);
		methodSelector.setLayoutData(comboLayout);
		
		ScrolledComposite container = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		container.setLayoutData(diagramLayout);
		this.canvas = new Canvas(container, SWT.NONE);
		canvas.setSize(1000, 1000);
		container.setContent(canvas);


		ASTStorageParser astParser = new ASTStorageParser();
		diagram = new LinkedList<Widget>();
		
		//Anade todo al selector		
		methodSelector.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					diagram = astParser.parse(ASTStorage.getMethod(methodSelector.getText()).getChildren());
					diagramSize = astParser.getSize();
					canvas.setSize(diagramSize.maxWidth + 60, diagramSize.lastOutput.y + 40);
					if (diagramSize.maxWidth / 2 > 100) {
						for(Widget widget : diagram) {
							widget.fix(diagramSize.maxWidth / 2 - 80);
						}
					}
				} catch (Exception e1) {
					System.err.println("No se pudo parsear el arbol");
					e1.printStackTrace();
				}
				canvas.redraw();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				System.out.println("Default selected");
			}
		});

		canvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				for(Widget widget : diagram) {
					widget.draw(e.gc);
				}
			}
		});
	}


	/**
	 * Actualiza el diagrama y la pantalla
	 */
	public void draw() {
		canvas.redraw();
	}
	
	public static void setMethods(String[] methods) {
		methodSelector.setItems(methods);
	}
}
