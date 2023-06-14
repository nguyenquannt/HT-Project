package ui;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.Utils;

public class TrangChu_GUI extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	public TrangChu_GUI() {
		setBounds(0, 0, Utils.getScreenWidth(), Utils.getBodyHeight());
		setLayout(null);

		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(Utils.getImageIcon("tranhchu.png"));
		lblBackground.setBounds(0, 0, Utils.getScreenWidth(), Utils.getBodyHeight());

		this.add(lblBackground);
	}
}
