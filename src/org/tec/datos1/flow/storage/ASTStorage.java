package org.tec.datos1.flow.storage;



import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
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
	static CompilationUnit compilationUnit;
	
	
	public ASTStorage(ASTNode Element,ASTStorage Parent,String Name) {
		this.Element = Element;
		this.Children = new ArrayList<ASTStorage>();
		this.Name = Name;
	}
	
	public ASTStorage(ASTNode Element,String Name) {
		this.Element = Element;
		this.Children = new ArrayList<ASTStorage>();
		this.Name = Name;
	}
	
	public ASTStorage(ASTNode Element,Boolean then,String Name) {
		this(Element,Name);
		this.then = then;
	}
	
	public static ASTStorage getRoot(){
		return root;
	}
	
	public static void setRoot(ASTStorage Root) {
		root = Root;
	}
	
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
	
	public static CompilationUnit getCompUnit() {
		return compilationUnit;
	}
	
	public static List<String> getMethods() {
		List<String> result = new ArrayList<>();
		if (root == null) {
			return null;
		}
		for (ASTStorage method :root.getChildren()) {
			result.add(method.getName());
		}
		return result;
	}
	
	public static ASTStorage getMethod(String Method) {
		for (ASTStorage method :root.getChildren()) {
			if (method.getName().equals(Method)) return method;
		}
		return null;
	}
	
	public static ASTStorage getMethodByLine(Integer Line) {
		ASTStorage result = null;
		for (ASTStorage method :root.getChildren()) {
			if (method.getLineNumber() > Line) {
				return result;
			}
			result = method;
		}
		return result;
	}
	
	
	
	
	public Integer getLineNumber() {
		return compilationUnit.getLineNumber(Element.getStartPosition());
	}
	
	public void addChild(ASTStorage Child) {
		Children.add(Child);
	}
	/**parseado
	 * Este método busca el ASTStorage correspondiente a una linea 
	 * específica dentro del código 
	 * @param lineNumber
	 * @return
	 */
	public ASTStorage findLine(Integer lineNumber) {
		
		if (Element != null && (compilationUnit.getLineNumber(Element.getStartPosition()) == lineNumber)) {
			return this;
		}else if (Children.size() != 0) {
			for (ASTStorage child : Children) {
				ASTStorage tempNode = child.findLine(lineNumber);
				if ( tempNode != null)
					return tempNode;
			}
		}
		return null;
	}
	
	
    /**
     * Este metodo se encarga de descomponer el AST de eclipse en una estructura 
     * más simple 
     * @param parent Nodo padre al que se le agregaran los componentes hijos
     * @param Statements Lista de nodos hijos por agregar 
     */
    public void addChildren(List<Block> Statements) {
    	
    	for (Object statement : Statements) {
			ASTNode child = (ASTNode) statement;
			this.addChildrenAux(child);
    	}
    }
    
    public void deleteChildren() {
    	this.Children.clear();
    	ASTStorage.root = null;
    }
    
    /**
     * Este metodo auxiliar asiste al metodo addChildren para complir su función
     * @param parent Nodo padre al que se le agregaran los componentes hijos
     * @param Statements Nodo hijo por agregar 
     */
	@SuppressWarnings("unchecked")
	public void addChildrenAux(ASTNode child) {
		if (child == null) {return;}
		String[] clazz_aux = child.getClass().toString().split("\\.");
		String clazz = clazz_aux[clazz_aux.length - 1];

		if (clazz.equalsIgnoreCase("WhileStatement")) {
			
			WhileStatement While = (WhileStatement) child;
			ASTStorage WhileStorage = new ASTStorage(While,While.getExpression().toString());
			this.addChild(WhileStorage);
			
			Block block = (Block) While.getBody();
			
			WhileStorage.addChildren(block.statements());

		} else if (clazz.equalsIgnoreCase("DoStatement")) {
			DoStatement Do = (DoStatement) child;
			ASTStorage DoStorage = new ASTStorage(Do, Do.getExpression().toString());
			this.addChild(DoStorage);
			//System.out.println("Do: \n" + Do.getExpression());
			Block block = (Block) Do.getBody();

			DoStorage.addChildren(block.statements());

		} else if (clazz.equalsIgnoreCase("EnhancedForStatement")) {
			EnhancedForStatement EnhancedFor = (EnhancedForStatement) child;
			ASTStorage EnhancedForStorage = new ASTStorage(EnhancedFor,EnhancedFor.getExpression().toString());
			this.addChild(EnhancedForStorage);
			Block block = (Block) EnhancedFor.getBody();

			EnhancedForStorage.addChildren(block.statements());

		} else if (clazz.equalsIgnoreCase("ForStatement")) {
			ForStatement For = (ForStatement) child;
			ASTStorage ForStorage = new ASTStorage(For,For.getExpression().toString());
			this.addChild(ForStorage);
			Block block = (Block) For.getBody();

			ForStorage.addChildren(block.statements());

		} else if (clazz.equalsIgnoreCase("IfStatement")) {
			IfStatement If = (IfStatement) child;
			ASTStorage IfStorage = new ASTStorage(If,If.getExpression().toString());
			this.addChild(IfStorage);
			ASTStorage thenStorage = new ASTStorage(null,true,If.getExpression().toString());
			IfStorage.addChild(thenStorage);
			Block b1 = (Block) If.getThenStatement();
			thenStorage.addChildren(b1.statements());

			if (If.getElseStatement() instanceof Block) {
				ASTStorage elseStorage = new ASTStorage(null,false,If.getExpression().toString());
				IfStorage.addChild(elseStorage);
				Block b2 = (Block) If.getElseStatement();
				elseStorage.addChildren(b2.statements());
			} else {
				ASTStorage elseStorage = new ASTStorage(null,false, If.getExpression().toString());
				IfStorage.addChild(elseStorage);
				IfStatement If2 = (IfStatement) If.getElseStatement();
				elseStorage.addChildrenAux(If2);
			}

		} else if(clazz.equalsIgnoreCase("TryStatement")){
			TryStatement Try = (TryStatement) child;
			ASTStorage TryStorage = new ASTStorage(Try,"Try");
			this.addChild(TryStorage);
			
			Block block = (Block) Try.getBody();

			TryStorage.addChildren(block.statements());

		} else if (clazz.equalsIgnoreCase("ExpressionStatement")) {
			
			ExpressionStatement Expression = (ExpressionStatement) child;
			try{
				
				MethodInvocation methodInvocation = (MethodInvocation) Expression.getExpression();
				String methodClass = methodInvocation.resolveMethodBinding().getDeclaringClass().getQualifiedName();
				if (methodClass.split("\\.")[0].equals("java")) {
					ASTStorage ExpressionStorage = new ASTStorage(Expression,Expression.toString());
					this.addChild(ExpressionStorage);
					return;
				}
				ASTStorage MethoInvocationStorage = new ASTStorage(methodInvocation, methodInvocation.toString());
				this.addChild(MethoInvocationStorage);
				
			}catch(Exception ex) {
				ASTStorage ExpressionStorage = new ASTStorage(Expression,Expression.toString());
				this.addChild(ExpressionStorage);
			}
			
		}else if (clazz.equalsIgnoreCase("VariableDeclarationStatement")) {
			VariableDeclarationStatement Variable = (VariableDeclarationStatement) child;
			ASTStorage VariableStorage = new ASTStorage(Variable,Variable.toString());
			this.addChild(VariableStorage);
		}else {}

    }
	
}
