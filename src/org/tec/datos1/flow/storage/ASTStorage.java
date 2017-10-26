package org.tec.datos1.flow.storage;



import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class ASTStorage {
	
	static ASTStorage root;
	String Name;
	ASTNode Element;
	List<ASTStorage> Children;
	public Boolean then;
	//ASTStorage Parent;
	static CompilationUnit compilationUnit;
	
	
	public ASTStorage(ASTNode Element,ASTStorage Parent,String Name) {
		this.Element = Element;
		this.Children = new ArrayList<ASTStorage>();
		//this.Parent = Parent;
		this.Name = Name;
	}
	
	public ASTStorage(ASTNode Element,String Name) {
		this.Element = Element;
		this.Children = new ArrayList<ASTStorage>();
		this.Name = Name;
	}
	
	public ASTStorage(ASTNode Element,ASTStorage Parent,Boolean then,String Name) {
		this(Element,Parent,Name);
		this.then = then;
	}
	
	public static ASTStorage getRoot(){
		return root;
	}
	
	public static void setRoot(ASTStorage Root) {
		root = Root;
	}
	
	public void addChild(ASTStorage Child) {
		Children.add(Child);
	}
	
//	public void setParent(ASTStorage parent) {
//		this.Parent = parent;
//	}
//	
//	public ASTStorage getParent() {
//		return this.Parent;
//	}

	public ASTNode getElement() {
		return this.Element;
	}
	public List<ASTStorage> getChildren(){
		return Children;
	}
	public void setThen() {
		this.then = true;
	}
	public String getName() {
		return this.Name;
	}

	public static void setCompUnit(CompilationUnit compUnit) {
		compilationUnit = compUnit;
	}
	
	public ASTStorage findLine(Integer lineNumber) {
		
		if (Element != null && (compilationUnit.getLineNumber(Element.getStartPosition()) == lineNumber)) {
			return this;
		}else if (Children.size() != 0) {
			for (ASTStorage child : Children) {
				ASTStorage tempNode = child.findLine(lineNumber);
				if ( tempNode != null)
					return tempNode;
			
			}
		}else{
			return null;
		}
		return null;

	}
	
	public void print() {
		if (Element == null) {
			if (this.then) {
				
			}else {
				System.out.println("Else: ");
			}

		} else {
			String[] clazz_aux = Element.getClass().toString().split("\\.");
			String clazz = clazz_aux[clazz_aux.length - 1];
			
			System.out.print(compilationUnit.getLineNumber(Element.getStartPosition()) + " ");

			if (clazz.equalsIgnoreCase("WhileStatement")) {

				WhileStatement While = (WhileStatement) Element;

				System.out.println("While(" + While.getExpression() + ")");

			} else if (clazz.equalsIgnoreCase("DoStatement")) {

				DoStatement Do = (DoStatement) Element;

				System.out.println("Do While:" + Do.getExpression());

			} else if (clazz.equalsIgnoreCase("EnhancedForStatement")) {
				EnhancedForStatement EnhancedFor = (EnhancedForStatement) Element;

				System.out.println("EnhancedFor: " + EnhancedFor.getExpression());

			} else if (clazz.equalsIgnoreCase("ForStatement")) {
				ForStatement For = (ForStatement) Element;

				System.out.println("For(" + For.getExpression() + ")");

			} else if (clazz.equalsIgnoreCase("IfStatement")) {
				IfStatement If = (IfStatement) Element;

				System.out.println("If(" + If.getExpression() + ")");


			} else if (clazz.equalsIgnoreCase("TryStatement")) {
				TryStatement Try = (TryStatement) Element;

				System.out.println("Try:");

			} else if (clazz.equalsIgnoreCase("ExpressionStatement")) {

				ExpressionStatement Expression = (ExpressionStatement) Element;

				System.out.println(Expression.getExpression());

			} else if (clazz.equalsIgnoreCase("VariableDeclarationStatement")) {
				VariableDeclarationStatement Variable = (VariableDeclarationStatement) Element;
				
				System.out.print(Variable.toString());
				
			} else if (clazz.equalsIgnoreCase("MethodInvocation")) {
				
				MethodInvocation Method = (MethodInvocation) Element;
				
				System.out.println(Method);
				
			} else if (clazz.equalsIgnoreCase("MethodDeclaration")) {
				
				MethodDeclaration Method = (MethodDeclaration) Element;
				
				System.out.println("Method: " + Method.getName() + " | Return Type: " + Method.getReturnType2() +
						" | Parameters: " + Method.parameters());
			
			} else {
				System.out.println("OTRO: " + clazz);
			}
		}
		
		for (ASTStorage child : Children) {
			child.print();

		}
	}
	
}
