package by.zhdanovich.rat.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Class {@code Assessment} describes the assessment of the film left by the
 * user.
 */
public class Assessment implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * The value of the assessment.
	 */
	private int value;
	/**
	 * The date when it was set the assessment.
	 */
	private Date date;
	/**
	 * The object {@code User} that has set this assessment.
	 * {@see by.zhdanovich.rat.entity.User}
	 */
	private User user;
	/**
	 * The object {@code Film} that placed this assessment
	 * {@see by.zhdanovich.rat.entity.Film}
	 */
	private Film film;

	public Assessment() {
		user = new User();
		film = new Film();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
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
		return "Assessment [value=" + value + ", data=" + date + ", user=" + user + ", film=" + film + "]";
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
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((film == null) ? 0 : film.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + value;
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
		Assessment other = (Assessment) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (film == null) {
			if (other.film != null)
				return false;
		} else if (!film.equals(other.film))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

}
