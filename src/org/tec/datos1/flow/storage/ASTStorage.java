package org.tec.datos1.flow.storage;



import java.util.ArrayList;
import java.util.List;
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
	private static ASTStorage root;
	private String Name;
	private ASTNode Element;
	private List<ASTStorage> Children;
	private  Boolean then;
	private static CompilationUnit compilationUnit;
	
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
			if (method.getName() == Method) return method;
		}
		return null;
	}
	
	public void addChild(ASTStorage Child) {
		Children.add(Child);
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
    
    
    /**
     * Este metodo auxiliar asiste al metodo addChildren para complir su función
     * @param parent Nodo padre al que se le agregaran los componentes hijos
     * @param Statements Nodo hijo por agregar 
     */
	@SuppressWarnings("unchecked")
	public void addChildrenAux(ASTNode child) {
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
		}else {
			System.out.println("OTRO: " + clazz);
		}

    }
	
	/**
	 * Este método se encarga de mostrar todo lo que se encuentra dentro del arbol
	 */
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
