// 
// Decompiled by Procyon v0.5.36
// 

package ok;

import java.awt.geom.Ellipse2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

public class Background extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    public Background() {
        this.init();
    }
    
    private void init() {
        this.setOpaque(false);
        this.setBackground(new Color(245, 245, 245));
        this.setForeground(new Color(118, 118, 118));
    }
    
    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final int x = 0;
        final int y = 40;
        final int width = this.getWidth();
        final int height = this.getHeight();
        final int iconSpace = 7;
        final int totalIconSpace = iconSpace * 2;
        final int iconSize = y * 2;
        final int iconX = (width - (iconSize + totalIconSpace)) / 2;
        final int iconY = 0;
        final Area area = new Area(new Rectangle2D.Double(x, y, width, height - y));
        area.subtract(new Area(new Ellipse2D.Double(iconX, iconY - iconSpace, iconSize + totalIconSpace, iconSize + totalIconSpace)));
        area.add(new Area(new Ellipse2D.Double(iconX + iconSpace, 0.0, iconSize, iconSize)));
        g2.setColor(this.getBackground());
        g2.fill(area);
        g2.dispose();
        super.paintComponent(g);
    }
}
