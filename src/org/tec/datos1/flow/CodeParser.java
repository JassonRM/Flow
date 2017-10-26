package org.tec.datos1.flow;


import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.tec.datos1.flow.handlers.MethodVisitor;
import org.tec.datos1.flow.storage.ASTStorage;


public class CodeParser {
	
	
    public static void execute() throws ExecutionException {
    	
    	IWorkbenchPart workbenchPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
    	ICompilationUnit IcUnit = null;
    	try{
    		IFile file = (IFile) workbenchPart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
    		IcUnit = (ICompilationUnit) JavaCore.create(file);
    	}catch(Exception e) {
    		System.err.println("Debe tener una clase abierta");
    	}
    	try {
    		createAST(IcUnit);
    		
    	}catch(JavaModelException exeption){}
    	
    }

    private static void createAST(ICompilationUnit IcUnit) throws JavaModelException {
    	
            CompilationUnit parse = parse(IcUnit);
            ASTStorage.setCompUnit(parse);
            MethodVisitor visitor = new MethodVisitor();
            parse.accept(visitor);
            
    }
    

    /**
     * Reads a ICompilationUnit and creates the AST DOM for manipulating the
     * Java source file
     *
     * @param unit
     * @return
     */

    public static CompilationUnit parse(ICompilationUnit unit) {
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(unit);
        parser.setResolveBindings(true);
        
        return (CompilationUnit) parser.createAST(null); // parse
    }
    
    

}
