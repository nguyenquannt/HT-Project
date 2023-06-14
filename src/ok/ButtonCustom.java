// 
// Decompiled by Procyon v0.5.36
// 

package ok;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Cursor;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComponent;

public class ButtonCustom extends JButton
{
    private static final long serialVersionUID = 1L;
    private Color background;
    private Color colorHover;
    private Color colorPressed;
    private boolean mouseOver;
    
    public ButtonCustom() {
        this.background = new Color(69, 191, 71);
        this.colorHover = new Color(76, 206, 78);
        this.colorPressed = new Color(63, 175, 65);
        this.mouseOver = false;
        this.init();
    }
    
    public ButtonCustom(final String string) {
        this.background = new Color(69, 191, 71);
        this.colorHover = new Color(76, 206, 78);
        this.colorPressed = new Color(63, 175, 65);
        this.mouseOver = false;
        this.setText(string);
        this.init();
    }
    
    public Color getColorHover() {
        return this.colorHover;
    }
    
    public Color getColorPressed() {
        return this.colorPressed;
    }
    
    private void init() {
        this.setContentAreaFilled(false);
        this.setCursor(new Cursor(12));
        this.setBackground(this.background);
        this.setForeground(Color.WHITE);
        this.setOpaque(true);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                ButtonCustom.this.mouseOver = true;
            }
            
            @Override
            public void mouseExited(final MouseEvent e) {
                ButtonCustom.this.mouseOver = false;
            }
            
            @Override
            public void mousePressed(final MouseEvent e) {
            }
            
            @Override
            public void mouseReleased(final MouseEvent e) {
                if (ButtonCustom.this.mouseOver) {
                }
                else {
                }
            }
        });
    }
    
   
    
    public void setColorHover(final Color colorHover) {
        this.colorHover = colorHover;
    }
    
    public void setColorPressed(final Color colorPressed) {
        this.colorPressed = colorPressed;
    }
}
