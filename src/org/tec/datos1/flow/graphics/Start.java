package org.tec.datos1.flow.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class Start implements Widget{
	Point input;
	Point output;
	int width = 80;
	
	/**
	 * Constructor para el grafico de un proceso
	 * @param text Texto que va a contener el proceso
	 * @param input Punto de entrada del proceso
	 */
	public Start(Point input) {
		this.input = input;
		this.output = new Point(input.x, input.y + 40);
	}
	/**
	 * Constructor para el grafico de un proceso
	 * @param gc Graphical Context en el cual se dibuja
	 * @param text Texto que va a contener el proceso
	 * @param x Coordenada en x del punto de entrada del proceso
	 * @param y Coordenada en y del punto de entrada del proceso
	 */
	public Start(int x, int y) {
		this(new Point(x, y));
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
	
	/**
	 * Dibuja el grafico en el contexto grafico
	 * @param gc Contexto grafico en el cual se va a dibujar
	 */
	@Override
	public void draw(GC gc, int line) {
		gc.drawOval(input.x - this.width / 2, input.y, this.width, 40);
		gc.drawText("Start", input.x - gc.stringExtent("Start").x / 2, input.y + (40 - gc.stringExtent("Start").y) / 2);
	}
	@Override
	public void fix(int x) {
		input = new Point(input.x + x, input.y);
		output = new Point(output.x + x, output.y);
	}
	
	public int getWidth() {
		return this.width;
	}
}
