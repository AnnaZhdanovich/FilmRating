package by.zhdanovich.rat.entity;

import java.io.Serializable;

/**
 * Class {@code Personality} describes the actor or the producer of the film.
 */
public class Personality implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Identification number of the personality.
	 */
	private int idPersonality;
	/**
	 * First Name of the personality.
	 */
	private String firstName;
	/**
	 * Last Name of the personality.
	 */
	private String lastName;
	/**
	 * The role of the personality.
	 */
	private RoleOfActor role;

	
	public Personality(){
		
	}
	

	public int getIdPersonality() {
		return idPersonality;
	}

	public void setIdPersonality(int idPersonality) {
		this.idPersonality = idPersonality;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public RoleOfActor getRole() {
		return role;
	}

	public void setRole(RoleOfActor role) {
		this.role = role;
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
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + idPersonality;
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
		Personality other = (Personality) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (role != other.role)
			return false;
		if (idPersonality != other.idPersonality)
			return false;
		return true;
	}

	/**
	 * Overridden method class {@code Object} returns an object definition to
	 * describe row.
	 * 
	 * @see java.lang.Object
	 * @return string class object.
	 */
	@Override
	public String toString() {
		return "Personality [uid=" + idPersonality + ", firstName=" + firstName + ", lastName=" + lastName + ", role=" + role
				+ "]";
	}
	
	public enum RoleOfActor {
		ACTOR, PRODUCER;
	}

}
