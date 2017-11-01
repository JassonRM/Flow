package org.tec.datos1.flow.graphics;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

public class Line implements Widget{
	Point start;
	Point finish;
	boolean jump;
	String text= "";
	int width;
	
	/**
	 * Constructor para el grafico de una linea
	 * @param start Punto de inicio de la linea
	 * @param finish Punto final de la linea
	 */
	public Line(Point start, Point finish) {
		this.start = start;
		this.finish = finish;
	}
	
	public Line(Point start, Point finish, int width) {
		this.start = start;
		this.finish = finish;
		this.width = width;
	}
	
	public Line(Point start, Point finish, boolean jump) {
		this(start, finish);
		this.jump = jump;
	}
	
	public Line(Point start, Point finish, boolean jump, String text) {
		this(start, finish);
		this.jump = jump;
		this.text = text;
	}	
	
	public Line(Point start, Point finish, boolean jump, String text, int width) {
		this(start, finish, width);
		this.jump = jump;
		this.text = text;
	}	
	
	public Line(Point start, Point finish, String text) {
		this(start, finish);
		this.text = text;
	}
	
	/**
	 * Dibuja el elemento en el contexto grafico
	 * @param gc Contexto grafico en el cual se va a dibujar
	 */
	public void draw(GC gc) {
		if (jump){
			gc.drawText(text, this.start.x + 10, this.start.y - 15);
			gc.drawLine(this.start.x, this.start.y, this.start.x + this.width / 2 + 20, this.start.y);
			gc.drawLine(this.start.x + this.width / 2 + 20, this.start.y, this.start.x + this.width / 2 + 20, this.finish.y);
			gc.drawLine(this.start.x + this.width / 2 + 20, this.finish.y,	this.finish.x, this.finish.y);
		} else if(this.finish.y < this.start.y) {
			gc.drawLine(this.start.x, this.start.y, this.start.x, this.start.y + 10);
			gc.drawLine(this.start.x, this.start.y + 10, this.start.x - this.width / 2 - 20, this.start.y + 10);
			gc.drawLine(this.start.x - this.width / 2 - 20, this.start.y + 10, this.start.x - this.width / 2 - 20, this.finish.y);
			gc.drawLine(this.start.x - this.width / 2 - 20, this.finish.y, this.finish.x, this.finish.y);
		} else if (this.start.x != this.finish.x) {
			if (this.finish.x < this.start.x) {
				gc.drawText(text, start.x - 30, start.y - 15);
			} else {
				gc.drawText(text, this.start.x + 10, this.start.y - 15);
			}
			gc.drawLine(this.start.x, this.start.y, this.finish.x, this.start.y);
			gc.drawLine(this.finish.x, this.start.y, this.finish.x, this.finish.y);
		} else {
			gc.drawText(text, this.start.x + 5, this.start.y + 5);
			gc.drawLine(this.start.x, this.start.y, this.finish.x, this.finish.y);
		}
	}
	@Override
	public void fix(int x) {
		start = new Point(start.x + x, start.y);
		finish = new Point(finish.x + x, finish.y);
	}
}
