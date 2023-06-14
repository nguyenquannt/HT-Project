package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import utils.Utils;

public class SplashScreen_GUI extends JWindow {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblLoading;
	private JLabel lblPhanTram;
	private JProgressBar prg;

	/**
	 * Create the frame.
	 */
	public SplashScreen_GUI() {
		setBounds(0, 0, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("FLEY HOTEL");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 40));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(0, 20, 450, 60);
		contentPane.add(lblTitle);
		setLocationRelativeTo(null);

		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(Utils.getImageIcon("anhchaynen.png"));
		lblIcon.setBounds(97, 100, 256, 256);
		contentPane.add(lblIcon);

		prg = new JProgressBar();
		prg.setBounds(0, 389, 450, 11);
		contentPane.add(prg);

		lblLoading = new JLabel("Loading...");
		lblLoading.setForeground(Color.WHITE);
		lblLoading.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLoading.setBounds(10, 355, 350, 24);
		contentPane.add(lblLoading);

		lblPhanTram = new JLabel("0%");
		lblPhanTram.setForeground(Color.WHITE);
		lblPhanTram.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblPhanTram.setBounds(390, 355, 50, 24);
		contentPane.add(lblPhanTram);

		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(Utils.getImageIcon("SplashScreen.png"));
		lblBackground.setBounds(0, 0, 450, 400);
		contentPane.add(lblBackground);
	}

	public void handleOpen(JFrame jFrame) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				for (int i = 0; i <= 10; ++i) {
					try {
						prg.setValue(i * 10);
						lblPhanTram.setText(i * 10 + "%");

						switch (i) {
						case 1:
							lblLoading.setText("Turning On Modules...");
							break;
						case 2:
							lblLoading.setText("Loading Modules...");
							break;
						case 5:
							lblLoading.setText("Connecting to Database...");
							break;
						case 7:
							lblLoading.setText("Connection Successfull!");
							break;
						case 8:
							lblLoading.setText("Launching Application...");
							break;
						default:
							break;
						}
						sleep(100);

						if (i == 10) {
							jFrame.setAlwaysOnTop(true);
							jFrame.setVisible(true);
							dispose();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};

		thread.start();
	}

}
