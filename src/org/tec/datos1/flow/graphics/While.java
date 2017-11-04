package org.tec.datos1.flow.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class While implements Widget{
	String text;
	Point input;
	Point outputTrue;
	Point outputFalse;
	Point inputReturn;
	Rectangle focus;
	int line;
	
	/**
	 * Constructor para el grafico de un condicional
	 * @param text Texto que va a contener el condicional
	 * @param input Punto de entrada del condicional
	 */
	public While(String text, Point input, int line) {
		Shell shell = new Shell();
		GC gc = new GC(shell);
		
		
		this.input = input;
		this.outputTrue = new Point(input.x, input.y + 60);
		this.outputFalse = new Point(input.x + 30 + gc.stringExtent(text).x / 2, input.y + gc.stringExtent(text).y / 2 + 20);
		this.inputReturn = new Point(input.x - 30 - gc.stringExtent(text).x / 2, input.y + gc.stringExtent(text).y / 2 + 20);
		
		this.text = text;
		this.line = line;
	}
	/**
	 * Constructor para el grafico de un proceso
	 * @param gc Graphical Context en el cual se dibuja
	 * @param text Texto que va a contener el proceso
	 * @param x Coordenada en x del punto de entrada del condicional
	 * @param y Coordenada en y del punto de entrada del condicional
	 */
	public While(String text, int x, int y, int line) {
		this(text, new Point(x, y), line);
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
	
	/**
	 * Dibuja el grafico en el contexto grafico
	 * param gc Contexto grafico en el que se va a dibujar
	 */
	@Override
	public void draw(GC gc) {
		
		int [] shape = new int[] {input.x, input.y, outputFalse.x, outputFalse.y, outputTrue.x, outputTrue.y, inputReturn.x, inputReturn.y};
		
		gc.drawPolygon(shape);
		gc.drawText(text, input.x - gc.stringExtent(text).x / 2, input.y + 20);
	}
	@Override
	public void fix(int x) {
		input = new Point(input.x + x, input.y);
		outputTrue = new Point(outputTrue.x + x, outputTrue.y);
		outputFalse = new Point(outputFalse.x + x, outputFalse.y);
		inputReturn = new Point(inputReturn.x + x, inputReturn.y);
	}
	public int getWidth() {
		return outputFalse.x - inputReturn.x;
	}
}
