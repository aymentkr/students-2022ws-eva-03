package whz.pti.eva.common;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * The Class BaseEntity.
 *
 * @param <PK> the generic type of the primary key
 */
@MappedSuperclass
public abstract class BaseEntity<PK extends Serializable> {

	/** The id. */
	@Id
	@GeneratedValue
	private PK id;
	
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public PK getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(PK id) {
		this.id = id;
	}
	
	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		if (getId() != null) {
			return getId().hashCode();
		}
		return super.hashCode();
	}
	
	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof BaseEntity)) return false;
		@SuppressWarnings("rawtypes")
		BaseEntity other = (BaseEntity) obj;
		return this.getId() != null && this.getId().equals(other.getId());
	}
}
