package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lophoc")
public class Lophoc {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private org.bson.types.ObjectId id;
	private String malop;
	private String tenlop;
	public Lophoc() {
		// TODO Auto-generated constructor stub
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
		return "Lophoc [malop=" + malop + ", tenlop=" + tenlop + "]";
	}

}
