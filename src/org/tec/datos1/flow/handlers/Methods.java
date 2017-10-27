package org.tec.datos1.flow.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;

public class Methods extends ASTVisitor{
	static List<ICompilationUnit> classes = new ArrayList<ICompilationUnit>();
	private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";

	
	
	public static ICompilationUnit findClass(String clazz) {
		clazz = clazz + ".java";
		for(ICompilationUnit current : classes) {
			
			String[] lista = current.getResource().getProjectRelativePath().toString().split("/");
			int pos = lista.length;
			if (clazz.equals(lista[pos-3] + "." +lista[pos-2] + "." + lista[pos-1])) {
				System.out.println(clazz);
				return current;
			}
		}
		return null;
	}
	
	
	public static void load() {
		if (classes.size() != 0) return;
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        // Get all projects in the workspace
        IProject[] projects = root.getProjects();
        // Loop over all projects
        for (IProject project : projects) {
            try {
                if (project.isNatureEnabled(JDT_NATURE)) {
                    analyseMethods(project);
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    private static void analyseMethods(IProject project) throws JavaModelException {
        IPackageFragment[] packages = JavaCore.create(project)
                .getPackageFragments();
        // parse(JavaCore.create(project));
        for (IPackageFragment mypackage : packages) {
            if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
                createAST(mypackage);
            }

        }
    }

    private static void createAST(IPackageFragment mypackage)
            throws JavaModelException {
        for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
        	classes.add(unit);
        }
    }
}