package org.tec.datos1.flow.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class Process {
	
	String text;
	Point input;
	Point output;
	GC gc;
	
	/**
	 * Constructor para el gr�fico de un proceso
	 * @param gc Graphical Context en el cual se dibuja
	 * @param text Texto que va a contener el proceso
	 * @param input Punto de entrada del proceso
	 */
	public Process(GC gc, String text, Point input) {
		this.input = input;
		this.output = new Point(input.x, input.y + 40);
		this.text = text;
		this.gc = gc;
		
		Rectangle rectangle = new Rectangle(input.x - 10 - gc.stringExtent(text).x / 2, input.y, gc.stringExtent(text).x + 20, 40);
		gc.drawRectangle(rectangle);
		gc.drawText(text, input.x - gc.stringExtent(text).x / 2, input.y + (40 - gc.stringExtent(text).y) / 2);
	}
	/**
	 * Constructor para el gr�fico de un proceso
	 * @param gc Graphical Context en el cual se dibuja
	 * @param text Texto que va a contener el proceso
	 * @param x Coordenada en x del punto de entrada del proceso
	 * @param y Coordenada en y del punto de entrada del proceso
	 */
	public Process(GC gc, String text, int x, int y) {
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
	}
	
	public void end() {
		Display display = Display.getCurrent();
		Color black = display.getSystemColor(SWT.COLOR_BLACK);
		gc.setForeground(black);
	}
}