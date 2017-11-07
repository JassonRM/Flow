package org.tec.datos1.flow.parts;



import java.util.LinkedList;
import javax.inject.Inject;

import org.eclipse.jdt.core.dom.MethodDeclaration;
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
	private static Canvas canvas;
	static LinkedList<Widget> diagram;
	static DiagramSize diagramSize;
	static Combo methodSelector;
	private static Integer lineNumber = -1;
	
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
		canvas = new Canvas(container, SWT.NONE);
		canvas.setSize(1000, 1000);
		container.setContent(canvas);



		diagram = new LinkedList<Widget>();
		

		//Anade todo al selector
		methodSelector.addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				Select();
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
					widget.draw(e.gc, lineNumber);
				}
			}
		});
	}
	
	public static void Select() {
		try {
			ASTStorageParser astParser = new ASTStorageParser();
			if (lineNumber == -1) {
				diagram = astParser.parse(ASTStorage.getMethod(methodSelector.getText()).getChildren());
			}else {
				ASTStorage storage = ASTStorage.getMethodByLine(lineNumber);
				diagram = astParser.parse(storage.getChildren());
				methodSelector.setText(((MethodDeclaration)storage.getElement()).getName().toString());
			}
			diagramSize = astParser.getSize();
			canvas.setSize(diagramSize.maxWidth + 60, diagramSize.lastOutput.y + 40);
			if (diagramSize.maxWidth / 2 > 100) {
				for(Widget widget : diagram) {
					widget.fix(diagramSize.maxWidth / 2 - 80);
				}
			}
			canvas.redraw();
			lineNumber = -1;
		} catch (Exception e1) {
			System.err.println("No se pudo parsear el arbol");
			e1.printStackTrace();
			canvas.redraw();
		}
		
		
		
	}

	public static void setMethods(String[] methods) {
		methodSelector.setItems(methods);
	}

	public static void selectMethod(String method) {
		String[] methods = methodSelector.getItems();
		for(int index = 0; index < methods.length ; index++) {
			if (methods[index].equals(method)) {
				methodSelector.select(index);
				Select();
				
				
			}
		}
	}

	public static Integer getLineNumber() {
		return lineNumber;
	}


	public static void setLineNumber(Integer LineNumber) {
		lineNumber = LineNumber;
		canvas.redraw();
		
		
	}
	public static Combo getMethodSelector() {
		return methodSelector;
	}
}
