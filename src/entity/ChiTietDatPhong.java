package entity;

import java.time.LocalTime;
import java.util.Objects;

public class ChiTietDatPhong {
	private DonDatPhong donDatPhong;
	private LocalTime gioRa;
	private LocalTime gioVao;
	private Phong phong;

	public ChiTietDatPhong() {
		super();
	}

	public ChiTietDatPhong(DonDatPhong donDatPhong, Phong phong, LocalTime gioVao) {
		super();
		this.donDatPhong = donDatPhong;
		this.phong = phong;
		this.gioVao = gioVao;
	}

	public ChiTietDatPhong(DonDatPhong donDatPhong, Phong phong, LocalTime gioVao, LocalTime gioRa) {
		super();
		this.donDatPhong = donDatPhong;
		this.phong = phong;
		this.gioVao = gioVao;
		this.gioRa = gioRa;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietDatPhong other = (ChiTietDatPhong) obj;
		return Objects.equals(donDatPhong, other.donDatPhong) && Objects.equals(gioVao, other.gioVao)
				&& Objects.equals(phong, other.phong);
	}

	public DonDatPhong getDonDatPhong() {
		return donDatPhong;
	}

	public LocalTime getGioRa() {
		return gioRa;
	}

	public LocalTime getGioVao() {
		return gioVao;
	}

	public Phong getPhong() {
		return phong;
	}

	@Override
	public int hashCode() {
		return Objects.hash(donDatPhong, gioVao, phong);
	}

	public void setDonDatPhong(DonDatPhong donDatPhong) {
		this.donDatPhong = donDatPhong;
	}

	public void setGioRa(LocalTime gioRa) {
		this.gioRa = gioRa;
	}

	public void setGioVao(LocalTime gioVao) {
		this.gioVao = gioVao;
	}

	public void setPhong(Phong phong) {
		this.phong = phong;
	}

	@Override
	public String toString() {
		return "ChiTietDatPhong [donDatPhong=" + donDatPhong + ", phong=" + phong + ", gioVao=" + gioVao + ", gioRa="
				+ gioRa + "]";
	}

}
