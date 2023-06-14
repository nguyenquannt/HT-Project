package utils;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JTable;

public class Utils {
	private static final Class<?> _class = Utils.class;
	public static final Rectangle boundsPnlBody = new Rectangle(0, 65, 1086, 508);
	public static final String dangXuatMenuItem = "Đăng xuất";
	private static final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	public static final Color labelTextField = new Color(150, 150, 150);
	public static final Color lineTextField = new Color(149, 166, 248);
	public static final String nhanVienMenuItem = "Nhân viên";
	public static final Color phongCho = getRGBA(4, 191, 173, 0.85f);
	public static final Color phongDangSuDung = getRGBA(242, 68, 68, 0.85f);
	public static final Color phongTam = new Color(115, 120, 234);
	public static final Color phongTrong = new Color(125, 214, 112, 191);
	public static final Color primaryColor = new Color(140, 177, 180);
	public static final Color lightColor = new Color(255, 255, 255);
	public static final String quanLyDatPhongMenuItem = "Quản lý đặt phòng";
	public static final String quanLyDatPhongTruocMenuItem = "Quản lý đặt phòng trước";
	public static final String quanLyDichVuMenuItem = "Quản lý dịch vụ";
	public static final String quanLyKhachHangMenuItem = "Quản lý khách hàng";
	public static final String quanLyNhanVienMenuItem = "Quản lý nhân viên";
	public static final String quanLyPhongMenuItem = "Quản lý phòng";
	public static final Color secondaryColor = new Color(242, 246, 252);
	public static final String themNhanVienMenuItem = "Thêm nhân viên";
	public static final String thoatMenuItem = "Thoát";
	public static final String thongKeDoanhThuMenuItem = "Doanh thu";
	public static final String thongKeHoaDonMenuItem = "Hóa đơn";
	public static final String thongKeKhachHangMenuItem = "Khách hàng";
	public static final String thongKeMenuItem = "Thống kê";
	public static final String thongTinCaNhanMenuItem = "Thông tin cá nhân";
	public static final String trangChuMenuItem = "Trang chủ";
	public static final String troGiupItem = "Trợ giúp";
	public static final String thongkenhanvien = "Thống kê nhân viên";

	/**
	 * Convert int to String
	 * 
	 * @param number
	 * @return
	 */
	public static String convertIntToString(int number) {
		if (number < 10)
			return "0" + number;
		return number + "";
	}

	/**
	 * Convert LocalTime to String
	 * 
	 * @param time
	 * @return
	 */
	public static String convertLocalTimeToString(LocalTime time) {
		return String.format("%s:%s", convertIntToString(time.getHour()), convertIntToString(time.getMinute()));
	}

	/**
	 * Chuyển tiền tệ kiểu chuỗi sang double
	 * 
	 * @param tien
	 * @return
	 */
	public static double convertStringToTienTe(String tien) {
		return Double.parseDouble(tien.substring(0, tien.length() - 2));
	}

	/**
	 * Định dạng Date
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(LocalDate date) {
		return String.format("%s/%s/%d", convertIntToString(date.getDayOfMonth()),
				convertIntToString(date.getMonthValue()), date.getYear());
	}

	/**
	 * Format kiểu tiền tệ
	 * 
	 * @param soTien
	 * @return
	 */
	public static String formatTienTe(double soTien) {
		Locale locale = new Locale("vi", "vn");
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
		return currencyFormatter.format(soTien);
	}

	/**
	 * Get chiều cao phần thân
	 * 
	 * @return
	 */
	public static int getBodyHeight() {
		return getScreenHeight() - getHeaderHeight();
	}

	/**
	 * Get chiều cao phần header
	 * 
	 * @return
	 */
	public static int getHeaderHeight() {
		return 65;
	}

	/**
	 * Get ImageIcon
	 * 
	 * @param iconName
	 * @return
	 */
	public static ImageIcon getImageIcon(String iconName) {
		return new ImageIcon(_class.getResource("/" + iconName));
	}

	/**
	 * Get left căn giữa component với màn hình
	 * 
	 * @param width
	 * @return
	 */
	public static int getLeft(int width) {
		return (int) Math.ceil((Utils.getScreenWidth() - width - 14) / 2);
	}

	/**
	 * Get left căn giữa component với một width bất kỳ
	 * 
	 * @param widthParent
	 * @param width
	 * @return
	 */
	public static int getLeft(int widthParent, int width) {
		return (int) Math.ceil((widthParent - width) / 2);
	}

	/**
	 * Chuyển String sang LocalDate
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate getLocalDate(String date) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(date.contains("/") ? "dd/MM/yyyy" : "dd-MM-yyyy");
		String dates[] = date.split(date.contains("/") ? "/" : "-");
		for (int i = 0; i < 2; ++i)
			if (dates[i].length() == 1)
				dates[i] = '0' + dates[i];
		if (dates[2].length() == 2)
			dates[2] = "19" + dates[2];
		return LocalDate
				.parse(String.format(date.contains("/") ? "%s/%s/%s" : "%s-%s-%s", dates[0], dates[1], dates[2]), dtf);
	}

	/**
	 * Get màu RGBA
	 * 
	 * @param color
	 * @param alpha
	 * @return
	 */
	public static Color getOpacity(Color color, float alpha) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int a = color.getAlpha();

		return new Color(r, g, b, Math.round(a * alpha));
	}

	/**
	 * Get màu RGBA
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 * @return
	 */
	public static Color getRGBA(int r, int g, int b, float a) {
		return new Color(r, g, b, Math.round(a * 255));
	}

	/**
	 * Get chiều cao màn hình
	 * 
	 * @return
	 */
	public static int getScreenHeight() {
		return (int) dimension.getHeight() + 7;
	}

	/**
	 * Get độ dài màn hình
	 * 
	 * @return
	 */
	public static int getScreenWidth() {
		return (int) dimension.getWidth() + 14;
	}

	/**
	 * Get các từ tiếng Việt hoa có dấu
	 * 
	 * @return
	 */
	public static String getVietnameseDiacriticCharacters() {
		return "ẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴ";
	}

	/**
	 * Get các từ tiếng Việt thường có dấu
	 * 
	 * @return
	 */
	public static String getVietnameseDiacriticCharactersLower() {
		return getVietnameseDiacriticCharacters().toLowerCase();
	}

	/**
	 * Kiểm tra một chuỗi có thể chuyển thành double được không?
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isDouble(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Kiểm tra một chuỗi có thể là số hay không?
	 * 
	 * @param numString chuỗi cần kiểm tra
	 * @return true nếu là số
	 */
	public static boolean isInteger(String numString) {
		try {
			Integer.parseInt(numString);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Kiểm tra số điện thoại có hợp lệ không? Số điện thoại bắt đầu bằng số 0 và 9
	 * chữ số
	 * 
	 * @param soDienThoai số điện thoại cần kiểm tra
	 * @return true nếu số điện thoại hợp lệ
	 */
	public static boolean isSoDienThoai(String soDienThoai) {
		Pattern pattern = Pattern.compile("0[0-9]{9}");
		Matcher matcher = pattern.matcher(soDienThoai);
		return matcher.matches();
	}

	/**
	 * Mở file
	 * 
	 * @param pathname
	 */
	public static void openFile(String pathname) {
		InputStream manualAsStream = _class.getClassLoader().getResourceAsStream(pathname);

		Path tempOutput;
		try {
			tempOutput = Files.createTempFile("PTUD_HDSD", ".pdf");
			tempOutput.toFile().deleteOnExit();

			Files.copy(manualAsStream, tempOutput, StandardCopyOption.REPLACE_EXISTING);

			File userManual = new File(tempOutput.toFile().getPath());
			if (userManual.exists()) {
				Desktop.getDesktop().open(userManual);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * scroll to row and column Jtable
	 * 
	 * @param tbl
	 * @param row
	 * @param col
	 */
	public static void scrollToVisiable(JTable tbl, int row, int col) {
		tbl.scrollRectToVisible(tbl.getCellRect(row, col, true));
	}
}
