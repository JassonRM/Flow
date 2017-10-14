package org.tec.datos1.flow.storage;



import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;

public class ASTStorage {
	String Name;
	ASTNode Element;
	List<ASTStorage> Children;
	Boolean then;
	ASTStorage Parent;
	
	
	
	public ASTStorage(ASTNode Element,ASTStorage Parent,String Name) {
		this.Element = Element;
		this.Children = new ArrayList<ASTStorage>();
		this.Parent = Parent;
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
	
	
	public void addChild(ASTStorage Child) {
		Children.add(Child);
	}
	
	public void setParent(ASTStorage parent) {
		this.Parent = parent;
	}
	
	public ASTStorage getParent() {
		return this.Parent;
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
	public void print(int Level) {
		
		if (Element == null){
			if (this.then) {
				System.out.println("ThenStatement");
			}
			else {
				System.out.println("ElseStatement");
			}
			
		}else {
			System.out.println("NIVEL: " + Level);
			System.out.println(Element.getClass());
			Level++;
		}
		
		for (ASTStorage child : Children) {
			child.print(Level);
			
		}
	}
	
}
