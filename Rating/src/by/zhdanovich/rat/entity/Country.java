package by.zhdanovich.rat.entity;

import java.io.Serializable;

/**
 * Class {@code Country} describes the country of film.
 */
public class Country implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Identification number of the country.
	 */
	private int idCountry;
	/**
	 * The name of the country.
	 */
	private String name;

	public Country() {

	}

	public int getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(int uid) {
		this.idCountry = uid;
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
		result = prime * result + idCountry;
		return result;
	}

	/**
	 * overridden method class {@code Object} returns an object definition to
	 * describe row.
	 * 
	 * @see java.lang.Object
	 * @return string class object.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (idCountry != other.idCountry)
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
		return "Country [uid=" + idCountry + ", name=" + name + "]";
	}

}
