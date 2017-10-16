package org.tec.datos1.flow.graphics;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

public class Line {
	Point start;
	Point finish;
	
	public Line(GC gc, Point start, Point finish) {
		this.start = start;
		this.finish = finish;
		
		//Falta implementar la flecha de salida de un while
		if(finish.y < start.y) {
			gc.drawLine(start.x, start.y, start.x, start.y + 10);
			gc.drawLine(start.x, start.y + 10, 20, start.y + 10);
			gc.drawLine(20, start.y + 10, 20, finish.y);
			gc.drawLine(20, finish.y, finish.x, finish.y);
		} else if (start.x != finish.x) {
			gc.drawLine(start.x, start.y, finish.x, start.y);
			gc.drawLine(finish.x, start.y, finish.x, finish.y);
		} else {
			gc.drawLine(start.x, start.y, finish.x, finish.y);
		}
	}
}
