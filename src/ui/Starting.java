package ui;

import javax.swing.SwingUtilities;

public class Starting {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				SplashScreen_GUI splashScreen = new SplashScreen_GUI();
				DangNhap_GUI dangNhap_GUI = new DangNhap_GUI();

				splashScreen.setVisible(true);
				splashScreen.handleOpen(dangNhap_GUI);

//				try {
//					UIManager.setLookAndFeel(new FlatIntelliJLaf());
//				} catch (UnsupportedLookAndFeelException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

			}
		});
	}
}
