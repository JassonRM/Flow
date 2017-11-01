package org.tec.datos1.flow.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Method implements Widget{
	String text;
	Point input;
	Point output;
	Rectangle focus;
	
	/**
	 * Constructor para el grafico de un metodo
	 * @param text Texto que va a contener el metodo
	 * @param input Punto de entrada del metodo
	 */
	public Method(String text, Point input) {
		this.input = input;
		this.output = new Point(input.x, input.y + 40);
		this.text = text;
	}
	/**
	 * Constructor para el grafico de un metodo
	 * @param gc Graphical Context en el cual se dibuja
	 * @param text Texto que va a contener el metodo
	 * @param x Coordenada en x del punto de entrada del metodo
	 * @param y Coordenada en y del punto de entrada del metodo
	 */
	public Method(String text, int x, int y) {
		this(text, new Point(x, y));
	}
	
	/**
	 * @return Punto de salida del proceso
	 */
	public Point getOutput() {
		return output;
	}
	
	/**
	 * @return Punto de entrada del proceso
	 */
	public Point getInput() {
		return input;
	}
	
	//Todavia no funcionan bien
//	public void execute() {
//		Display display = Display.getCurrent();
//		Color red = display.getSystemColor(SWT.COLOR_RED);
//		gc.setForeground(red);
//		gc.setLineWidth(3);
//		this.focus = new Rectangle(input.x - 20 - gc.stringExtent(text).x / 2, input.y, gc.stringExtent(text).x + 40, 40);
//		gc.drawRectangle(focus);
//		Color black = display.getSystemColor(SWT.COLOR_BLACK);
//		gc.setForeground(black);
//		gc.setLineWidth(1);
//	}
//	
//	public void end() {
//		this.focus = null;
//	}

	/**
	 * Dibuja el metodo en el contexto grafico
	 * @param gc Contexto grafico en el que se va a dibujar
	 */
	@Override
	public void draw(GC gc) {
		Rectangle rectangle = new Rectangle(input.x - 10 - gc.stringExtent(text).x / 2, input.y, gc.stringExtent(text).x + 20, 40);
		gc.drawRectangle(rectangle);
		Rectangle externalRectangle = new Rectangle(input.x - 20 - gc.stringExtent(text).x / 2, input.y, gc.stringExtent(text).x + 40, 40);
		gc.drawRectangle(externalRectangle);
		gc.drawText(text, input.x - gc.stringExtent(text).x / 2, input.y + (40 - gc.stringExtent(text).y) / 2);
	}
	@Override
	public void fix(int x) {
		input = new Point(input.x + x, input.y);
		output = new Point(output.x + x, output.y);
	}
	public int getWidth() {
		Shell shell = new Shell();
		GC gc = new GC(shell);
		return gc.stringExtent(text).x + 20;
	}
	
}
