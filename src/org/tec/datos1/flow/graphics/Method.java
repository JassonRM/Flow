package org.tec.datos1.flow.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class Method {
	String text;
	Point input;
	Point output;
	GC gc;
	Rectangle focus;
	
	/**
	 * Constructor para el gráfico de un método
	 * @param gc Graphical Context en el cual se dibuja
	 * @param text Texto que va a contener el método
	 * @param input Punto de entrada del método
	 */
	public Method(GC gc, String text, Point input) {
		this.input = input;
		this.output = new Point(input.x, input.y + 40);
		this.text = text;
		this.gc = gc;
		
		Rectangle rectangle = new Rectangle(input.x - 10 - gc.stringExtent(text).x / 2, input.y, gc.stringExtent(text).x + 20, 40);
		gc.drawRectangle(rectangle);
		Rectangle externalRectangle = new Rectangle(input.x - 20 - gc.stringExtent(text).x / 2, input.y, gc.stringExtent(text).x + 40, 40);
		gc.drawRectangle(externalRectangle);
		gc.drawText(text, input.x - gc.stringExtent(text).x / 2, input.y + (40 - gc.stringExtent(text).y) / 2);
	}
	/**
	 * Constructor para el gráfico de un método
	 * @param gc Graphical Context en el cual se dibuja
	 * @param text Texto que va a contener el método
	 * @param x Coordenada en x del punto de entrada del método
	 * @param y Coordenada en y del punto de entrada del método
	 */
	public Method(GC gc, String text, int x, int y) {
		this(gc, text, new Point(x, y));
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
	public void execute() {
		Display display = Display.getCurrent();
		Color red = display.getSystemColor(SWT.COLOR_RED);
		gc.setForeground(red);
		gc.setLineWidth(3);
		this.focus = new Rectangle(input.x - 20 - gc.stringExtent(text).x / 2, input.y, gc.stringExtent(text).x + 40, 40);
		gc.drawRectangle(focus);
		Color black = display.getSystemColor(SWT.COLOR_BLACK);
		gc.setForeground(black);
		gc.setLineWidth(1);
	}
	
	public void end() {
		this.focus = null;
	}
}
