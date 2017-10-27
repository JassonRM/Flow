package org.tec.datos1.flow.graphics;

import java.util.LinkedList;

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

public class ASTStorageParser {
	LinkedList<Widget> scene;
	Point lastOutput;
	
	/**
	 * Convierte un arbol ASTStorage a LinkedList
	 * @param storage Arbol AST que se va a convertir
	 * @return LinkedLIst<Widget> con los widgets a dibujar
	 */
	public LinkedList<Widget> parse(ASTStorage storage){
		scene = new LinkedList<Widget>();
		
		lastOutput = new Point(200, 20);
		
		//Codigo que hace magia
		draw(storage, lastOutput.x, lastOutput.y);
		
		return scene;
	}
	
	/**
	 * Anade los elementos en orden a la lista de acuerdo al arbol ASTStorage
	 * @param storage Arbol a analizar
	 * @param x Posicion en X
	 * @param y Posicion en Y
	 */
	public void draw(ASTStorage storage, int x, int y) {
		ASTNode element = storage.getElement();
		System.out.println(element);
		if (element == null) {
			if (storage.getName().equals("Root") && storage.then) {
				storage.getChildren();
				System.out.println("Cuerpo if");
			}else {
				System.out.println("Else: ");
			}

		} else {
			String[] clazz_aux = element.getClass().toString().split("\\.");
			String clazz = clazz_aux[clazz_aux.length - 1];
			
			//System.out.print(compilationUnit.getLineNumber(Element.getStartPosition()) + " ");

			if (clazz.equalsIgnoreCase("WhileStatement")) {

				WhileStatement While = (WhileStatement) element;

				System.out.println("While(" + While.getExpression() + ")");

			} else if (clazz.equalsIgnoreCase("DoStatement")) {

				DoStatement Do = (DoStatement) element;
				

				System.out.println("Do While:" + Do.getExpression());

			} else if (clazz.equalsIgnoreCase("EnhancedForStatement")) {
				EnhancedForStatement EnhancedFor = (EnhancedForStatement) element;

				System.out.println("EnhancedFor: " + EnhancedFor.getExpression());

			} else if (clazz.equalsIgnoreCase("ForStatement")) {
				ForStatement For = (ForStatement) element;

				System.out.println("For(" + For.getExpression() + ")");

			} else if (clazz.equalsIgnoreCase("IfStatement")) {
				IfStatement If = (IfStatement) element;
				
				If dibujoIf = new If(If.getExpression().toString(),x,y);
				
				System.out.println("If(" + If.getExpression() + ")");


			} else if (clazz.equalsIgnoreCase("TryStatement")) {
				TryStatement Try = (TryStatement) element;

				System.out.println("Try:");

			} else if (clazz.equalsIgnoreCase("ExpressionStatement")) {

				ExpressionStatement Expression = (ExpressionStatement) element;

				//System.out.println(Expression.getExpression());

			} else if (clazz.equalsIgnoreCase("VariableDeclarationStatement")) {
				VariableDeclarationStatement Variable = (VariableDeclarationStatement) element;
				
				System.out.print(Variable.toString());
				
			} else if (clazz.equalsIgnoreCase("MethodInvocation")) {
				
				MethodInvocation method = (MethodInvocation) element;
				
				Method methodDiagram = new Method(method.getName().toString(), x, y);
				scene.add(methodDiagram);
				y += 30;
				
				
				System.out.println(method);
				
			} else if (clazz.equalsIgnoreCase("MethodDeclaration")) {
				
//				MethodDeclaration Method = (MethodDeclaration) element;
				
//				System.out.println("Method: " + Method.getName() + " | Return Type: " + Method.getReturnType2() +
//						" | Parameters: " + Method.parameters());
			
			} else {
				System.out.println("OTRO: " + clazz);
			}
		}
		
		for (ASTStorage child : storage.getChildren()) {
			draw(child, x, y+100);

		}
	}

}
