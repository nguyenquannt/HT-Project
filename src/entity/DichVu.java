package entity;

import java.util.Objects;

public class DichVu {
	private String maDichVu;
	private String tenDichVu;
	private int soLuong;
	private String donViTinh;
	private double giaMua;
	private boolean daNgungKinhDoanh;
	private String loaiDichVu;

	public DichVu() {
		super();
	}

	public DichVu(String maDichVu) {
		super();
		this.maDichVu = maDichVu;
	}

	public DichVu(String maDichVu, String tenDichVu, int soLuong, String donViTinh, double giaMua,
			boolean daNgungKinhDoanh, String loaiDichVu) {
		super();
		this.maDichVu = maDichVu;
		this.tenDichVu = tenDichVu;
		this.soLuong = soLuong;
		this.donViTinh = donViTinh;
		this.giaMua = giaMua;
		this.daNgungKinhDoanh = daNgungKinhDoanh;
		this.loaiDichVu = loaiDichVu;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DichVu other = (DichVu) obj;
		return Objects.equals(maDichVu, other.maDichVu);
	}

	public boolean getDaNgungKinhDoanh() {
		return daNgungKinhDoanh;
	}

	public String getDonViTinh() {
		return donViTinh;
	}

	public double getGiaBan() {
		return getGiaMua() * 1.5;
	}

	public double getGiaMua() {
		return giaMua;
	}

	public String getMaDichVu() {
		return maDichVu;
	}

	public int getSoLuong() {
		return soLuong;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maDichVu);
	}

	public void setDaNgungKinhDoanh(boolean daNgungKinhDoanh) {
		this.daNgungKinhDoanh = daNgungKinhDoanh;
	}

	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}

	public void setGiaMua(double giaMua) {
		this.giaMua = giaMua;
	}

	public void setMaDichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public String getLoaiDichVu() {
		return loaiDichVu;
	}

	public void setLoaiDichVu(String loaiDichVu) {
		this.loaiDichVu = loaiDichVu;
	}

	public String getTenDichVu() {
		return tenDichVu;
	}

	public void setTenDichVu(String tenDichVu) {
		this.tenDichVu = tenDichVu;
	}

	@Override
	public String toString() {
		return "DichVu [maDichVu=" + maDichVu + ", tenDichVu=" + tenDichVu + ", soLuong=" + soLuong + ", donViTinh="
				+ donViTinh + ", giaMua=" + giaMua + ", daNgungKinhDoanh=" + daNgungKinhDoanh + ", loaiDichVu="
				+ loaiDichVu + "]";
	}

}
