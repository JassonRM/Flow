package org.tec.datos1.flow.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;
import org.tec.datos1.flow.CodeParser;
import org.tec.datos1.flow.parts.DiagramView;
import org.tec.datos1.flow.storage.ASTStorage;

public class Load {

	@Execute
	public void execute(Shell shell) {
		try {
			CodeParser.execute();
			List<String> methods = ASTStorage.getMethods();
			String[] array = new String[methods.size()];
			int cont = 0;
			for(String method : methods) {
				array[cont] = method;
				cont++;
			}
			DiagramView.setMethods(array);
			
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
}
