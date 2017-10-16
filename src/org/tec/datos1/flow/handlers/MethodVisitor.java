package org.tec.datos1.flow.handlers;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;

import org.tec.datos1.flow.storage.ASTStorage;

public class MethodVisitor extends ASTVisitor {
    ASTStorage Root = new ASTStorage(null, "Root");
    List<MethodDeclaration> methods = new ArrayList<>();

    @Override
    public boolean visit(MethodDeclaration methodNode) {
    	//methods.add(node);
    	try {
    			
    			ASTStorage storageMethod = new ASTStorage(methodNode,Root,methodNode.getName().toString());
    			Root.addChild(storageMethod);
    			Block b1 = (Block)methodNode.getBody();
    			addChildren(storageMethod, b1.statements());
    			storageMethod.print(0);
    			System.out.println(getRoot());
    			
    	}catch(Exception ex) { ex.printStackTrace();}

    	methods.add(methodNode);
        return super.visit(methodNode);
    }
   
    public List<MethodDeclaration> getMethods() {
    	return methods;
    }
    public List<ASTStorage> getRoot() {
    	return Root.getChildren();
    }
    
    public static String addChildren(ASTStorage parent, List<Block> Statements) {
    	
    	for (Object statement : Statements) {
			ASTNode child = (ASTNode) statement;
			addChildrenAux(parent,child);
			//ASTNode.nodeClassForType(node2.getNodeType());
    	}
    	return null; 
    }
    
	@SuppressWarnings("unchecked")
	public static void addChildrenAux(ASTStorage parent, ASTNode child) {
		String[] clazz_aux = child.getClass().toString().split("\\.");
		String clazz = clazz_aux[clazz_aux.length - 1];

		if (clazz.equalsIgnoreCase("WhileStatement")) {
			WhileStatement While = (WhileStatement) child;
			ASTStorage WhileStorage = new ASTStorage(While,parent,While.getExpression().toString());
			parent.addChild(WhileStorage);
			//System.out.println("While: \n" + While.getExpression());
			
			Block block = (Block) While.getBody();

			addChildren(WhileStorage, block.statements());

		} else if (clazz.equalsIgnoreCase("DoStatement")) {
			DoStatement Do = (DoStatement) child;
			ASTStorage DoStorage = new ASTStorage(Do,parent, Do.getExpression().toString());
			parent.addChild(DoStorage);
			//System.out.println("Do: \n" + Do.getExpression());
			Block block = (Block) Do.getBody();

			addChildren(DoStorage, block.statements());

		} else if (clazz.equalsIgnoreCase("EnhancedForStatement")) {
			EnhancedForStatement EnhancedFor = (EnhancedForStatement) child;
			ASTStorage EnhancedForStorage = new ASTStorage(EnhancedFor,parent,EnhancedFor.getExpression().toString());
			parent.addChild(EnhancedForStorage);
			//System.out.println("EnhancedFor: \n" + EnhancedFor.getExpression());
			Block block = (Block) EnhancedFor.getBody();

			addChildren(EnhancedForStorage, block.statements());

		} else if (clazz.equalsIgnoreCase("ForStatement")) {
			ForStatement For = (ForStatement) child;
			ASTStorage ForStorage = new ASTStorage(For,parent,For.getExpression().toString());
			parent.addChild(ForStorage);
			//System.out.println("For: \n" + For.getExpression());
			Block block = (Block) For.getBody();

			addChildren(ForStorage, block.statements());

		} else if (clazz.equalsIgnoreCase("IfStatement")) {
			IfStatement If = (IfStatement) child;
			ASTStorage IfStorage = new ASTStorage(If,parent,If.getExpression().toString());
			parent.addChild(IfStorage);
			ASTStorage thenStorage = new ASTStorage(null,parent,true,If.getExpression().toString());
			IfStorage.addChild(thenStorage);
			Block b1 = (Block) If.getThenStatement();
			addChildren(thenStorage, b1.statements());

			if (If.getElseStatement() instanceof Block) {
				//System.out.println("If: \n" + If.getThenStatement() + "Else: \n" + If.getElseStatement());
				ASTStorage elseStorage = new ASTStorage(null,parent,false,If.getExpression().toString());
				IfStorage.addChild(elseStorage);
				Block b2 = (Block) If.getElseStatement();
				addChildren(elseStorage, b2.statements());
			} else {
				//System.out.println("If: \n" + If.getThenStatement() + "Else ");
				ASTStorage elseStorage = new ASTStorage(null,parent,false, If.getExpression().toString());
				IfStorage.addChild(elseStorage);
				IfStatement If2 = (IfStatement) If.getElseStatement();
				addChildrenAux(elseStorage, If2);
			}

		} else if(clazz.equalsIgnoreCase("TryStatement")){
			TryStatement Try = (TryStatement) child;
			ASTStorage TryStorage = new ASTStorage(Try,parent,"Try");
			parent.addChild(TryStorage);
			
			//System.out.println("For: \n");
			Block block = (Block) Try.getBody();

			addChildren(TryStorage, block.statements());

		} else if (clazz.equalsIgnoreCase("ExpressionStatement")) {
			
			ExpressionStatement Expression = (ExpressionStatement) child;
			
			try{
				MethodInvocation methodInvocation = (MethodInvocation) Expression.getExpression();
				ASTStorage MethoInvocationStorage = new ASTStorage(methodInvocation, parent, methodInvocation.toString());
				parent.addChild(MethoInvocationStorage);
				
//				IMethodBinding binding = methodInvocation.resolveMethodBinding().getMethodDeclaration();
//				ICompilationUnit unit = (ICompilationUnit) binding.getJavaElement().getAncestor( IJavaElement.COMPILATION_UNIT );
//				CompilationUnit parse = Handler.parse(unit);
//				MethodDeclaration decl = (MethodDeclaration)parse.findDeclaringNode( binding.getKey() );
				
				//System.out.println(decl.getBody());
				
			}catch(Exception ex) {
				ASTStorage ExpressionStorage = new ASTStorage(Expression,parent,Expression.toString());
				parent.addChild(ExpressionStorage);
			}
			
			//System.out.println("Expression:" + Expression.getExpression());
		
		}else if (clazz.equalsIgnoreCase("VariableDeclarationFragment")) {
			VariableDeclarationFragment Variable = (VariableDeclarationFragment) child;
			ASTStorage VariableStorage = new ASTStorage(Variable,parent,Variable.toString());
			parent.addChild(VariableStorage);
		}else {
			System.out.println("OTRO: " + clazz);
		}

    }
    
}