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

		size = draw(storage, size, this.scene);
		
		this.size = size;
		
		return this.scene;
	}
	
	/**
	 * Anade los elementos en orden a la lista de acuerdo al arbol ASTStorage
	 * @param storage Arbol a analizar
	 * @param x Posicion en X
	 * @param y Posicion en Y
	 */
	public DiagramSize draw(List<ASTStorage> storage, DiagramSize size, LinkedList<Widget> scene) {
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
					While whileDiagram = new While(While.getExpression().toString(), input, storageElement.getLineNumber());
					size.lastOutput = whileDiagram.getOutputTrue();
					scene.add(whileDiagram);
					
					DiagramSize bodySize = new DiagramSize();
					bodySize.lastOutput = size.lastOutput;
					bodySize.maxWidth = whileDiagram.getWidth();
					bodySize = draw(storageElement.getChildren(), bodySize, scene);
					size.lastOutput = bodySize.lastOutput;
					
					Line returnLine = new Line(size.lastOutput, whileDiagram.getInputReturn(), bodySize.maxWidth, LineType.RETURN);
					scene.add(returnLine);
					
					bodySize.maxWidth += 40;
					
					if(bodySize.maxWidth > size.maxWidth) {
						size.maxWidth = bodySize.maxWidth;
					}
					size.lastOutput = new Point(size.lastOutput.x, size.lastOutput.y + 40);

					Line falseLine = new Line(whileDiagram.getOutputFalse(), size.lastOutput, LineType.JUMP, "false", bodySize.maxWidth - whileDiagram.getWidth());
					scene.add(falseLine);
	
				} else if (clazz.equalsIgnoreCase("DoStatement")) {
					DoStatement Do = (DoStatement) element;
					size = draw(storageElement.getChildren(), size, scene);
					
					Point newInput = new Point(size.lastOutput.x, size.lastOutput.y + spacing);
					Line line = new Line(size.lastOutput, newInput, LineType.NONE);
					scene.add(line);
					While doDrawing = new While(Do.getExpression().toString(), newInput, storageElement.getLineNumber());
					scene.add(doDrawing);
					
					size.lastOutput = doDrawing.getInputReturn();
					input.y -= 10;
					Line returnLine = new Line(size.lastOutput, input, LineType.DORETURN);
					scene.add(returnLine);
					
					size.lastOutput = doDrawing.outputTrue;
					
					
					
					
				} else if (clazz.equalsIgnoreCase("EnhancedForStatement")) {
					EnhancedForStatement EnhancedFor = (EnhancedForStatement) element;
	
					While whileDiagram = new While(EnhancedFor.getParameter().toString() + ":" + EnhancedFor.getExpression(), input, storageElement.getLineNumber());
					size.lastOutput = whileDiagram.getOutputTrue();
					if(whileDiagram.getWidth() > size.maxWidth) {
						size.maxWidth = whileDiagram.getWidth();
					}
					scene.add(whileDiagram);
					
					DiagramSize bodySize = new DiagramSize();
					bodySize.lastOutput = size.lastOutput;
					bodySize.maxWidth = 0;
					bodySize = draw(storageElement.getChildren(), bodySize, scene);
					size.lastOutput = bodySize.lastOutput;
					
					Line returnLine = new Line(size.lastOutput, whileDiagram.getInputReturn(), bodySize.maxWidth, LineType.RETURN);
					scene.add(returnLine);
					bodySize.maxWidth += 40;
					
					if(bodySize.maxWidth > size.maxWidth) {
						size.maxWidth = bodySize.maxWidth;
					}
					size.lastOutput = new Point(size.lastOutput.x, size.lastOutput.y + 40);

					Line falseLine = new Line(whileDiagram.getOutputFalse(), size.lastOutput, LineType.JUMP, "", bodySize.maxWidth - whileDiagram.getWidth());
					scene.add(falseLine);
	
				} else if (clazz.equalsIgnoreCase("ForStatement")) {
					ForStatement For = (ForStatement) element;
	
					While whileDiagram = new While(For.getExpression().toString(), input, storageElement.getLineNumber());
					size.lastOutput = whileDiagram.getOutputTrue();
					if(whileDiagram.getWidth() > size.maxWidth) {
						size.maxWidth = whileDiagram.getWidth();
					}
					scene.add(whileDiagram);
					
					DiagramSize bodySize = new DiagramSize();
					bodySize.lastOutput = size.lastOutput;
					bodySize.maxWidth = 0;
					bodySize = draw(storageElement.getChildren(), bodySize, scene);
					size.lastOutput = bodySize.lastOutput;
					
					Line returnLine = new Line(size.lastOutput, whileDiagram.getInputReturn(), bodySize.maxWidth, LineType.RETURN);
					scene.add(returnLine);
					bodySize.maxWidth += 40;
					
					if(bodySize.maxWidth > size.maxWidth) {
						size.maxWidth = bodySize.maxWidth;
					}
					size.lastOutput = new Point(size.lastOutput.x, size.lastOutput.y + 40);

					Line falseLine = new Line(whileDiagram.getOutputFalse(), size.lastOutput, LineType.JUMP, "false", bodySize.maxWidth - whileDiagram.getWidth());
					scene.add(falseLine);
	
				} else if (clazz.equalsIgnoreCase("IfStatement")) {
					IfStatement If = (IfStatement) element;
					If ifDrawing = new If(If.getExpression().toString(), input, storageElement.getLineNumber());
					if(ifDrawing.getWidth() > size.maxWidth) {
						size.maxWidth = ifDrawing.getWidth();
					}
					scene.add(ifDrawing);
					
					LinkedList<Widget> falseScene = new LinkedList<Widget>();
					DiagramSize falseSize = new DiagramSize();
					falseSize.lastOutput = new Point(ifDrawing.getOutputFalse().x + 20, ifDrawing.getOutputFalse().y);
					falseSize.maxWidth = 0;
					falseSize = draw(storageElement.getChildren().get(1).getChildren(), falseSize, falseScene); //False
					if (falseSize.lastOutput.x - falseSize.maxWidth / 2 <= input.x) {
						for(Widget widget : falseScene) {
							widget.fix(input.x - (falseSize.lastOutput.x - falseSize.maxWidth / 2) + 20);
						}
						falseSize.lastOutput.x += input.x - (falseSize.lastOutput.x - falseSize.maxWidth / 2) + 20;
					}
					Line elseLine = new Line(ifDrawing.getOutputFalse(), new Point(falseSize.lastOutput.x, ifDrawing.getOutputFalse().y), "false");
					scene.add(elseLine);
					scene.addAll(falseScene);
					
					
					
					LinkedList<Widget> trueScene = new LinkedList<Widget>();
					DiagramSize trueSize = new DiagramSize();
					trueSize.lastOutput =  new Point(ifDrawing.getOutputTrue().x - 20, ifDrawing.getOutputTrue().y);
					trueSize.maxWidth = 0;
					trueSize = draw(storageElement.getChildren().get(0).getChildren(), trueSize, trueScene); //True
			
					if (trueSize.lastOutput.x + trueSize.maxWidth / 2 >= input.x) {
						for(Widget widget : trueScene) {
							widget.fix(input.x - (trueSize.lastOutput.x + trueSize.maxWidth / 2) - 20);
						}
						trueSize.lastOutput.x += input.x - (trueSize.lastOutput.x + trueSize.maxWidth / 2) - 20;
					}
					Line ifLine = new Line(ifDrawing.getOutputTrue(), new Point(trueSize.lastOutput.x, ifDrawing.getOutputTrue().y), "true");
					scene.add(ifLine);
					scene.addAll(trueScene);
					
					if(trueSize.maxWidth > falseSize.maxWidth) {
						if(size.maxWidth < 2 * (trueSize.maxWidth + this.spacing)) {
							size.maxWidth = 2 * (trueSize.maxWidth + this.spacing);
						}
					} else {
						if(size.maxWidth < 2 * (falseSize.maxWidth + this.spacing)) {
							size.maxWidth = 2 * (falseSize.maxWidth + this.spacing);
						}
					}
					
					int outputY;
					if (trueSize.lastOutput.y >= falseSize.lastOutput.y) {
						outputY = trueSize.lastOutput.y;
					} else {
						outputY = falseSize.lastOutput.y;
					}
					size.lastOutput = new Point(input.x, outputY + 10);
					Line trueLine = new Line(trueSize.lastOutput, size.lastOutput, LineType.JOIN);
					Line falseLine = new Line(falseSize.lastOutput, size.lastOutput, LineType.JOIN);
					scene.add(trueLine);
					scene.add(falseLine);
	
				} else if (clazz.equalsIgnoreCase("TryStatement")) {
					TryStatement Try = (TryStatement) element;
	
					System.out.println("Try:");
	
				} else if (clazz.equalsIgnoreCase("ExpressionStatement")) {
	
					ExpressionStatement expression = (ExpressionStatement) element;

					Process statement = new Process(expression.toString().replaceAll("\n", ""), input, storageElement.getLineNumber());
					size.lastOutput = statement.getOutput();
					if(statement.getWidth() > size.maxWidth) {
						size.maxWidth = statement.getWidth();
					}
					scene.add(statement);
	
				} else if (clazz.equalsIgnoreCase("VariableDeclarationStatement")) {
					VariableDeclarationStatement variable = (VariableDeclarationStatement) element;
					Process statement = new Process(variable.toString().replaceAll("\n", ""), input, storageElement.getLineNumber());
					size.lastOutput = statement.getOutput();
					if(statement.getWidth() > size.maxWidth) {
						size.maxWidth = statement.getWidth();
					}
					scene.add(statement);
					
				} else if (clazz.equalsIgnoreCase("MethodInvocation")) {			
					MethodInvocation method = (MethodInvocation) element;
					String argumentos = method.arguments().toString().replace('[', '(').replace(']', ')');
					Method methodDiagram = new Method(method.getName().toString() + argumentos, input, storageElement.getLineNumber());
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
