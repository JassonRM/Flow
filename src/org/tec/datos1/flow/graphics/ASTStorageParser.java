package org.tec.datos1.flow.graphics;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.swt.graphics.Point;
import org.tec.datos1.flow.graphics.Widget;
import org.tec.datos1.flow.storage.ASTStorage;
import org.tec.datos1.flow.storage.DiagramSize;

public class ASTStorageParser {
	LinkedList<Widget> scene;
	int spacing = 30;
	DiagramSize size;
	
	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}

	/**
	 * Convierte un arbol ASTStorage a LinkedList
	 * @param storage Arbol AST que se va a convertir
	 * @return LinkedLIst<Widget> con los widgets a dibujar
	 */
	public LinkedList<Widget> parse(List<ASTStorage> storage){
		scene = new LinkedList<Widget>();
		
		DiagramSize size = new DiagramSize();
		size.lastOutput = new Point(100, 20);
		size.maxWidth = 0;
		
		Start start = new Start(size.lastOutput);
		scene.add(start);
		size.lastOutput = start.getOutput();
		size.maxWidth = start.getWidth();

		size = draw(storage, size);
		
		this.size = size;
		
		return scene;
	}
	
	/**
	 * Anade los elementos en orden a la lista de acuerdo al arbol ASTStorage
	 * @param storage Arbol a analizar
	 * @param x Posicion en X
	 * @param y Posicion en Y
	 */
	public DiagramSize draw(List<ASTStorage> storage, DiagramSize size) {
		for(ASTStorage storageElement : storage) {
			ASTNode element = storageElement.getElement();
			Point input = new Point(size.lastOutput.x, size.lastOutput.y + spacing);
			Line union = new Line(size.lastOutput, input);
			scene.add(union);
			if (element == null) {
				if (storageElement.getName().equals("Root") && storageElement.then) {
					storageElement.getChildren();
					System.out.println("Cuerpo if");
				}else {
					System.out.println("Else: ");
				}
	
			} else {
				String[] clazz_aux = element.getClass().toString().split("\\.");
				String clazz = clazz_aux[clazz_aux.length - 1];
	
				if (clazz.equalsIgnoreCase("WhileStatement")) {
	
					WhileStatement While = (WhileStatement) element;
					While whileDiagram = new While(While.getExpression().toString(), input);
					size.lastOutput = whileDiagram.getOutputTrue();
					scene.add(whileDiagram);
					
					DiagramSize bodySize = new DiagramSize();
					bodySize.lastOutput = size.lastOutput;
					bodySize.maxWidth = whileDiagram.getWidth();
					bodySize = draw(storageElement.getChildren(), bodySize);
					size.lastOutput = bodySize.lastOutput;
					
					Line returnLine = new Line(size.lastOutput, whileDiagram.getInputReturn(), bodySize.maxWidth);
					scene.add(returnLine);
					
					bodySize.maxWidth += 40;
					
					if(bodySize.maxWidth > size.maxWidth) {
						size.maxWidth = bodySize.maxWidth;
					}
					size.lastOutput = new Point(size.lastOutput.x, size.lastOutput.y + 40);

					Line falseLine = new Line(whileDiagram.getOutputFalse(), size.lastOutput, true, "false", bodySize.maxWidth - whileDiagram.getWidth());
					scene.add(falseLine);
	
				} else if (clazz.equalsIgnoreCase("DoStatement")) {
					DoStatement Do = (DoStatement) element;
					size = draw(storageElement.getChildren(), size);
					If ifDrawing = new If(Do.toString().replaceAll("\n", ""), input);
					size.lastOutput = ifDrawing.getOutputTrue();
					scene.add(ifDrawing);
					break;
					
				} else if (clazz.equalsIgnoreCase("EnhancedForStatement")) {
					EnhancedForStatement EnhancedFor = (EnhancedForStatement) element;
	
					System.out.println("EnhancedFor: " + EnhancedFor.getExpression());
	
				} else if (clazz.equalsIgnoreCase("ForStatement")) {
					ForStatement For = (ForStatement) element;
	
					While whileDiagram = new While(For.getExpression().toString(), input);
					size.lastOutput = whileDiagram.getOutputTrue();
					if(whileDiagram.getWidth() > size.maxWidth) {
						size.maxWidth = whileDiagram.getWidth();
					}
					scene.add(whileDiagram);
					
					DiagramSize bodySize = new DiagramSize();
					bodySize.lastOutput = size.lastOutput;
					bodySize.maxWidth = 0;
					bodySize = draw(storageElement.getChildren(), bodySize);
					size.lastOutput = bodySize.lastOutput;
					
					Line returnLine = new Line(size.lastOutput, whileDiagram.getInputReturn(), bodySize.maxWidth);
					scene.add(returnLine);
					bodySize.maxWidth += 40;
					
					if(bodySize.maxWidth > size.maxWidth) {
						size.maxWidth = bodySize.maxWidth;
					}
					size.lastOutput = new Point(size.lastOutput.x, size.lastOutput.y + 40);

					Line falseLine = new Line(whileDiagram.getOutputFalse(), size.lastOutput, true, "false", bodySize.maxWidth - whileDiagram.getWidth());
					scene.add(falseLine);
	
				} else if (clazz.equalsIgnoreCase("IfStatement")) {
					IfStatement If = (IfStatement) element;
					If ifDrawing = new If(If.getExpression().toString(), input);
					size.lastOutput = ifDrawing.getOutputTrue();
					if(ifDrawing.getWidth() > size.maxWidth) {
						size.maxWidth = ifDrawing.getWidth();
					}
					scene.add(ifDrawing);
					
					DiagramSize falseSize = new DiagramSize();
					//Dibujar los cuerpos del if
	
	
				} else if (clazz.equalsIgnoreCase("TryStatement")) {
					TryStatement Try = (TryStatement) element;
	
					System.out.println("Try:");
	
				} else if (clazz.equalsIgnoreCase("ExpressionStatement")) {
	
					ExpressionStatement Expression = (ExpressionStatement) element;
	
					//System.out.println(Expression.getExpression());
	
				} else if (clazz.equalsIgnoreCase("VariableDeclarationStatement")) {
					VariableDeclarationStatement variable = (VariableDeclarationStatement) element;
					Process statement = new Process(variable.toString().replaceAll("\n", ""), input);
					size.lastOutput = statement.getOutput();
					if(statement.getWidth() > size.maxWidth) {
						size.maxWidth = statement.getWidth();
					}
					scene.add(statement);
					
				} else if (clazz.equalsIgnoreCase("MethodInvocation")) {			
					MethodInvocation method = (MethodInvocation) element;
					Method methodDiagram = new Method(method.getName().toString(), input);
					size.lastOutput = methodDiagram.getOutput();
					if(methodDiagram.getWidth() > size.maxWidth) {
						size.maxWidth = methodDiagram.getWidth();
					}
					scene.add(methodDiagram);
				} else {
					System.out.println("OTRO: " + clazz);
				}
				
			}
		}
		return size;
	}

	public DiagramSize getSize() {
		return this.size;
	}
}
