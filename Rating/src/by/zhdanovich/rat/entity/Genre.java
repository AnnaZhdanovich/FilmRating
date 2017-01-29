package by.zhdanovich.rat.entity;

import java.io.Serializable;

/**
 * Class {@code Genre} describes the genre of the film.
 */
public class Genre implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Identification number of the genre.
	 */
	private int idGenre;
	/**
	 * The name of the genre.
	 */
	private String name;

	
	public  Genre(){		
	}
	
	public int getIdGenre() {
		return idGenre;
	}

	public void setIidGenre(int idGenre) {
		this.idGenre = idGenre;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Overridden method class {@code Object} to obtain the object hash code.
	 * 
	 * @see java.lang.Object
	 * @return int type value.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + idGenre;
		return result;
	}

	/**
	 * Overridden method class {@code Object} of determining the equality of the
	 * current object to another.
	 * 
	 * @see java.lang.Object
	 * @return boolean type value.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genre other = (Genre) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (idGenre != other.idGenre)
			return false;
		return true;
	}

	/**
	 * overridden method class {@code Object} returns an object definition to
	 * describe row.
	 * 
	 * @see java.lang.Object
	 * @return string class object.
	 */
	@Override
	public String toString() {
		return "Genre [uid=" + idGenre + ", name=" + name + "]";
	}

}
