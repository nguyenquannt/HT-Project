package entity;

import java.util.Objects;

public class Tinh {
	public static String getTinhLabel() {
		return "Tỉnh/Thành phố";
	}

	private String id;
	private String tinh;

	public Tinh() {
		super();
	}

	public Tinh(String id) {
		super();
		setId(id);
	}

	public Tinh(String id, String tinh) {
		super();
		setId(id);
		setTinh(tinh);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tinh other = (Tinh) obj;
		return Objects.equals(id, other.id);
	}

	public String getId() {
		return id;
	}

	public String getTinh() {
		return tinh;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTinh(String tinh) {
		this.tinh = tinh;
	}

	@Override
	public String toString() {
		return "Tinh [id=" + id + ", tinh=" + tinh + "]";
	}
}
