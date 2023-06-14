// 
// Decompiled by Procyon v0.5.36
// 

package ok;

import javax.swing.ImageIcon;
import javax.swing.LayoutStyle;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import utils.Utils;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.text.StyledDocument;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.SimpleAttributeSet;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.Frame;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JFrame;
import org.jdesktop.animation.timing.Animator;
import javax.swing.JDialog;

public class JDialogCustom extends JDialog
{
    private static final long serialVersionUID = 1L;
    private Animator animator;
    private Background background1;
    private ButtonCustom btnCancel;
    private ButtonCustom btnOK;
    private final JFrame fram;
    private Glass glass;
    private JLabel lblIcon;
    private JLabel lblTitle;
    private MessageType messageType;
    private boolean show;
    private JTextPane txpMessage;
    private Type type;
    
    public JDialogCustom(final JFrame fram) {
        super(fram, true);
        this.messageType = MessageType.CANCEL;
        this.type = Type.confirm;
        this.fram = fram;
        this.initComponents();
        this.init();
    }
    
    public JDialogCustom(final JFrame fram, final Type type) {
        super(fram, true);
        this.messageType = MessageType.CANCEL;
        this.type = Type.confirm;
        this.type = type;
        this.fram = fram;
        this.initComponents();
        this.init();
    }
    
    private void btnOKActionPerformed(final ActionEvent evt) {
        this.messageType = MessageType.OK;
        this.closeMessage();
    }
    
    public void closeMessage() {
        this.startAnimator(false);
    }
    
    private void cmdCancelActionPerformed(final ActionEvent evt) {
        this.messageType = MessageType.CANCEL;
        this.closeMessage();
    }
    
    public ButtonCustom getBtnCancel() {
        return this.btnCancel;
    }
    
    public ButtonCustom getBtnOK() {
        return this.btnOK;
    }
    
    public MessageType getMessageType() {
        return this.messageType;
    }
    
    private void init() {
        this.setBackground(new Color(0, 0, 0, 0));
        final StyledDocument doc = this.txpMessage.getStyledDocument();
        final SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, 1);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        this.txpMessage.setOpaque(false);
        this.txpMessage.setBackground(new Color(0, 0, 0, 0));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                JDialogCustom.this.closeMessage();
            }
        });
        (this.animator = new Animator(300, (TimingTarget)new TimingTargetAdapter() {
            public void end() {
                if (!JDialogCustom.this.show) {
                    JDialogCustom.this.dispose();
                    JDialogCustom.this.glass.setVisible(false);
                }
            }
            
            public void timingEvent(final float fraction) {
                final float f = JDialogCustom.this.show ? fraction : (1.0f - fraction);
                JDialogCustom.this.glass.setAlpha(f - f * 0.3f);
                JDialogCustom.this.setOpacity(f);
            }
        })).setResolution(0);
        this.animator.setAcceleration(0.5f);
        this.animator.setDeceleration(0.5f);
        this.setOpacity(0.0f);
        this.glass = new Glass();
    }
    
    private void initComponents() {
        this.background1 = new Background();
        this.btnCancel = new ButtonCustom();
        this.btnOK = new ButtonCustom();
        this.lblIcon = new JLabel();
        this.lblTitle = new JLabel();
        this.txpMessage = new JTextPane();
        this.setDefaultCloseOperation(2);
        this.setUndecorated(true);
        this.background1.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        if (this.type.equals(Type.warning)) {
            this.btnCancel.setVisible(false);
        }
        this.btnCancel.setFocusable(false);
        this.btnCancel.setBackground(new Color(245, 71, 71));
        this.btnCancel.setText("Cancel");
        this.btnCancel.setColorHover(new Color(255, 74, 74));
        this.btnCancel.setColorPressed(new Color(235, 61, 61));
        this.btnCancel.setFont(new Font("sansserif", 0, 14));
        this.btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JDialogCustom.this.cmdCancelActionPerformed(evt);
            }
        });
        this.btnOK.setText("OK");
        this.btnOK.setFocusable(false);
        this.btnOK.setFont(new Font("sansserif", 0, 14));
        this.btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JDialogCustom.this.btnOKActionPerformed(evt);
            }
        });
        this.lblIcon.setHorizontalAlignment(0);
        final ImageIcon imageIcon = Utils.getImageIcon(this.type.equals(Type.confirm) ? "questionIcon.png" : "warning_50x50.png");
        this.lblIcon.setIcon(imageIcon);
        this.lblTitle.setFont(new Font("sansserif", 1, 18));
        this.lblTitle.setForeground(new Color(245, 71, 71));
        this.lblTitle.setHorizontalAlignment(0);
        this.lblTitle.setText("Message Title");
        this.txpMessage.setEditable(false);
        this.txpMessage.setFont(new Font("sansserif", 0, 14));
        this.txpMessage.setForeground(new Color(76, 76, 76));
        this.txpMessage.setText("Message Text\nSimple");
        this.txpMessage.setFocusable(false);
        final GroupLayout background1Layout = new GroupLayout(this.background1);
        this.background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(background1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(background1Layout.createSequentialGroup().addComponent(this.btnCancel, -1, 195, 32767).addGap(this.type.equals(Type.warning) ? 96 : 3, this.type.equals(Type.warning) ? 96 : 3, this.type.equals(Type.warning) ? 96 : 3).addComponent(this.btnOK, -1, 196, 32767).addGap(this.type.equals(Type.warning) ? 96 : 0, this.type.equals(Type.warning) ? 96 : 0, this.type.equals(Type.warning) ? 96 : 0)).addComponent(this.lblIcon, -1, -1, 32767).addComponent(this.lblTitle, -1, -1, 32767).addComponent(this.txpMessage));
        background1Layout.setVerticalGroup(background1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup().addComponent(this.lblIcon, -2, 74, -2).addGap(20, 20, 20).addComponent(this.lblTitle).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.txpMessage, -2, 60, -2).addGap(18, 18, 18).addGroup(background1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.btnCancel, -2, 50, -2).addComponent(this.btnOK, -2, 50, -2))));
        final GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.background1, -1, -1, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.background1, -1, -1, 32767));
        this.pack();
    }
    
    public void setType(final Type type) {
        this.type = type;
        this.initComponents();
        this.init();
    }
    
    public void showMessage(final String title, final String message) {
        this.fram.setGlassPane(this.glass);
        this.glass.setVisible(true);
        this.lblTitle.setText(title);
        this.txpMessage.setText(message);
        this.setLocationRelativeTo(this.fram);
        this.startAnimator(true);
        this.setVisible(true);
    }
    
    private void startAnimator(final boolean show) {
        if (this.animator.isRunning()) {
            final float f = this.animator.getTimingFraction();
            this.animator.stop();
            this.animator.setStartFraction(1.0f - f);
        }
        else {
            this.animator.setStartFraction(0.0f);
        }
        this.show = show;
        this.animator.start();
    }
    
    public enum MessageType
    {
        CANCEL("CANCEL", 0), 
        OK("OK", 1);
        
        private MessageType(final String name, final int ordinal) {
        }
    }
    
    public enum Type
    {
        confirm("confirm", 0), 
        warning("warning", 1);
        
        private Type(final String name, final int ordinal) {
        }
    }
}
