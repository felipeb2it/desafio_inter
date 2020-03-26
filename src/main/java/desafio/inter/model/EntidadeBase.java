package desafio.inter.model;

import java.io.Serializable;


/**
 * Base class for all JPA entities.
 * 
 * @author Rodrigo Uchôa (http://rodrigouchoa.wordpress.com)
 *
 */
public abstract class EntidadeBase<T extends Serializable> {

	public abstract T getId();
	public abstract void setId(Integer id);

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
	        return true;
	      if (obj == null)
	        return false;
	      if (!this.getClass().equals(obj.getClass()))
	        return false;
	      return (this.getId() != null) && this.getId().equals(((EntidadeBase<?>) obj).getId());
	}
	
	@Override
	public int hashCode() {
		return getId() == null ? super.hashCode() : getId().hashCode();
	}
	
}
