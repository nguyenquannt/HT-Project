package utils;

public class NhanVien {
	private static entity.NhanVien nhanVien;

	public static entity.NhanVien getNhanVien() {
		return nhanVien;
	}

	public static void setNhanVien(entity.NhanVien nhanVien) {
		NhanVien.nhanVien = nhanVien;
	}
}
