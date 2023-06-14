// 
// Decompiled by Procyon v0.5.36
// 

package ok;

import java.awt.Color;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.JComponent;

public class Glass extends JComponent
{
    private static final long serialVersionUID = 1L;
    private float alpha;
    
    public Glass() {
        this.alpha = 0.0f;
    }
    
    public float getAlpha() {
        return this.alpha;
    }
    
    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D g2 = (Graphics2D)g.create();
        g2.setComposite(AlphaComposite.getInstance(3, this.alpha));
        g2.setColor(Color.gray);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.dispose();
        super.paintComponent(g);
    }
    
    public void setAlpha(final float alpha) {
        this.alpha = alpha;
        this.repaint();
    }
}
