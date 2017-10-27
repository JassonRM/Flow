package org.tec.datos1.flow.graphics;

import org.eclipse.swt.graphics.GC;

public interface Widget {
	/**
	 * Dibuja el widget en el contexto grafico
	 * @param gc Contexto grafico en el que se va a dibujar
	 */
	public void draw(GC gc);
}
