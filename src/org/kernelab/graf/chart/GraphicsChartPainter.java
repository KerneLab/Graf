package org.kernelab.graf.chart;

import java.awt.Graphics;

public interface GraphicsChartPainter extends GraphicsNodePainter
{
	public void paintLine(Graphics g, int x1, int y1, int x2, int y2);
}
