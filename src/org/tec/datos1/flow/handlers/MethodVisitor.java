package org.tec.datos1.flow.handlers;



import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.tec.datos1.flow.storage.ASTStorage;


public class MethodVisitor extends ASTVisitor {
    ASTStorage Root = new ASTStorage(null, "Root");
    
    /**
     * Este método se ejecuta cuando se encuentra una declaración de
     * método durante el parseo del código
     */
    @SuppressWarnings("unchecked")
	@Override
    public boolean visit(MethodDeclaration methodNode) {
    		
   
    	if (ASTStorage.getRoot() == null) {
    		ASTStorage.setRoot(Root);
    		} 
    	try {
    			ASTStorage storageMethod = new ASTStorage(methodNode,methodNode.getName().toString());
    			Root.addChild(storageMethod);
    			Block b1 = (Block)methodNode.getBody();
    			storageMethod.addChildren(b1.statements());
    			
    	}catch(Exception ex) {ex.printStackTrace();}

        return super.visit(methodNode);
    }
   

    
}