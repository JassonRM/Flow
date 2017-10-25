package org.tec.datos1.flow.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class While implements Widget{
	String text;
	Point input;
	Point outputTrue;
	Point outputFalse;
	Point inputReturn;
	Rectangle focus;
	int[] shape;
	
	/**
	 * Constructor para el gr�fico de un condicional
	 * @param text Texto que va a contener el condicional
	 * @param input Punto de entrada del condicional
	 */
	public While(String text, Point input) {
		this.input = input;
//		this.outputTrue = new Point(input.x, input.y + gc.stringExtent(text).y + 40);
//		this.outputFalse = new Point(input.x + 30 + gc.stringExtent(text).x / 2, input.y + gc.stringExtent(text).y / 2 + 20);
//		this.inputReturn = new Point(input.x - 30 - gc.stringExtent(text).x / 2, input.y + gc.stringExtent(text).y / 2 + 20);
		
		this.text = text;
		
		shape = new int[] {input.x, input.y, outputFalse.x, outputFalse.y, outputTrue.x, outputTrue.y, inputReturn.x, inputReturn.y};
	}
	/**
	 * Constructor para el gr�fico de un proceso
	 * @param gc Graphical Context en el cual se dibuja
	 * @param text Texto que va a contener el proceso
	 * @param x Coordenada en x del punto de entrada del condicional
	 * @param y Coordenada en y del punto de entrada del condicional
	 */
	public While(String text, int x, int y) {
		this(text, new Point(x, y));
	}
	
	/**
	 * @return Punto de entrada del condicional
	 */
	public Point getInput() {
		return input;
	}
	
	/**
	 * @return Punto de entrada del condicional
	 */
	public Point getInputReturn() {
		return inputReturn;
	}
	
	/**
	 * @return Punto de salida en caso de que se cumpla la condici�n
	 */
	public Point getOutputTrue() {
		return outputTrue;
	}
	
	/**
	 * @return Punto de salida en caso de que no se cumpla la condici�n
	 */
	public Point getOutputFalse() {
		return outputFalse;
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
	@Override
	public void draw(GC gc) {
		this.outputTrue = new Point(input.x, input.y + gc.stringExtent(text).y + 40);
		this.outputFalse = new Point(input.x + 30 + gc.stringExtent(text).x / 2, input.y + gc.stringExtent(text).y / 2 + 20);
		this.inputReturn = new Point(input.x - 30 - gc.stringExtent(text).x / 2, input.y + gc.stringExtent(text).y / 2 + 20);
		
		gc.drawPolygon(shape);
		gc.drawText(text, input.x - gc.stringExtent(text).x / 2, input.y + 20);
	}
}
