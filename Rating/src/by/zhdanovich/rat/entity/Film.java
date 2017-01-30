package by.zhdanovich.rat.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.zhdanovich.rat.entity.User.Status;

/**
 * Class {@code Film} describes the film.
 */
public class Film implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Identification number of the film.
	 */
	private int idFilm;
	/**
	 * The name of the film.
	 */
	private String title;
	/**
	 * The rating of the film.
	 */
	private float rating;
	/**
	 * The country of the film.
	 */
	private Country country;
	/**
	 * The year when was release of the film.
	 */
	private String year;
	/**
	 * The description of the film.
	 */
	private String description;
	/**
	 * The poster of the film.
	 */
	private String poster;
	/**
	 * The assessments of the film.
	 */
	private List<Assessment> listAssessment;
	/**
	 * The comments of the film.
	 */
	private List<Comment> listComment;
	/**
	 * The personality of the film.
	 */
	private List<Personality> listPersonality;
	/**
	 * The genres of the film.
	 */
	private List<Genre> listGenre;
	/**
	 * The date when the film was added.
	 */
	private Date date;
	/**
	 * The status of the film
	 */
	private Status statusOfFilm;

	public Film() {
		this.listPersonality = new ArrayList<Personality>();
		this.listComment = new ArrayList<Comment>();
		this.listGenre = new ArrayList<Genre>();
		this.listAssessment = new ArrayList<Assessment>();
		this.country = new Country();
	}

	public Status getStatusOfFilm() {
		return statusOfFilm;
	}

	public void setStatusOfFilm(Status statusOfFilm) {
		this.statusOfFilm = statusOfFilm;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Genre> getListGenre() {
		return listGenre;
	}

	public void setListGenre(List<Genre> listGenre) {
		this.listGenre = listGenre;
	}

	public List<Personality> getListPersonality() {
		return listPersonality;
	}

	public void setListPersonality(List<Personality> listPersonality) {
		this.listPersonality = listPersonality;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIdFilm() {
		return idFilm;
	}

	public void setIdFilm(int idFilm) {
		this.idFilm = idFilm;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public List<Assessment> getListAssessment() {
		return listAssessment;
	}

	public void setListAssessment(List<Assessment> listAssessment) {
		this.listAssessment = listAssessment;
	}

	public List<Comment> getListComment() {
		return listComment;
	}

	public void setListComment(List<Comment> listComment) {
		this.listComment = listComment;
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
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((listAssessment == null) ? 0 : listAssessment.hashCode());
		result = prime * result + ((listComment == null) ? 0 : listComment.hashCode());
		result = prime * result + ((listGenre == null) ? 0 : listGenre.hashCode());
		result = prime * result + ((listPersonality == null) ? 0 : listPersonality.hashCode());
		result = prime * result + ((poster == null) ? 0 : poster.hashCode());
		result = (int) (prime * result + rating);
		result = prime * result + ((statusOfFilm == null) ? 0 : statusOfFilm.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + idFilm;
		result = prime * result + ((year == null) ? 0 : year.hashCode());
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
		Film other = (Film) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (listAssessment == null) {
			if (other.listAssessment != null)
				return false;
		} else if (!listAssessment.equals(other.listAssessment))
			return false;
		if (listComment == null) {
			if (other.listComment != null)
				return false;
		} else if (!listComment.equals(other.listComment))
			return false;
		if (listGenre == null) {
			if (other.listGenre != null)
				return false;
		} else if (!listGenre.equals(other.listGenre))
			return false;
		if (listPersonality == null) {
			if (other.listPersonality != null)
				return false;
		} else if (!listPersonality.equals(other.listPersonality))
			return false;
		if (poster == null) {
			if (other.poster != null)
				return false;
		} else if (!poster.equals(other.poster))
			return false;
		if (rating != other.rating)
			return false;
		if (statusOfFilm != other.statusOfFilm)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (idFilm != other.idFilm)
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
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
		return "Film [title=" + title + ", uid=" + idFilm + ", rating=" + rating + ", country=" + country + ", year="
				+ year + ", description=" + description + ", poster=" + poster + ", listAssessment=" + listAssessment
				+ ", listComment=" + listComment + ", listPersonality=" + listPersonality + ", listGenre=" + listGenre
				+ ", data=" + date + ", statusOfFilm=" + statusOfFilm + "]";
	}

}
