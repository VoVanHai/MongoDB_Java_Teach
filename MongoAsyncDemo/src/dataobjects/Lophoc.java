package dataobjects;

public class Lophoc {
	private String malop;
	private String tenlop;
	public Lophoc() {
	}
	public Lophoc(String malop, String tenlop) {
		super();
		this.malop = malop;
		this.tenlop = tenlop;
	}
	public String getMalop() {
		return malop;
	}
	public void setMalop(String malop) {
		this.malop = malop;
	}
	public String getTenlop() {
		return tenlop;
	}
	public void setTenlop(String tenlop) {
		this.tenlop = tenlop;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((malop == null) ? 0 : malop.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lophoc other = (Lophoc) obj;
		if (malop == null) {
			if (other.malop != null)
				return false;
		} else if (!malop.equals(other.malop))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return malop+"\t"+tenlop;
	}
}
