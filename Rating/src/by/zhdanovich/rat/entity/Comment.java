package by.zhdanovich.rat.entity;

import java.io.Serializable;

/**
 * Class {@code Comment} describes the comment of the film left by the user.
 * 
 */
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Identification number of the comment.
	 */
	private int idComment;
	/**
	 * The context of the comment.
	 */
	private String text;
	/**
	 * The date when it was written the comment.
	 */
	private String date;
	/**
	 * The object {@code User} that has written this comment.
	 * {@see by.zhdanovich.rat.entity.User}
	 */
	private User user;
	/**
	 * The object {@code Film} that written this comment.
	 * {@see by.zhdanovich.rat.entity.Film}
	 */
	private Film film;

	private StatusOfComment statusOfComment;

	public Comment() {
		user = new User();
		film = new Film();
	}

	public int getIdComment() {
		return idComment;
	}

	public void setIdComment(int idComment) {
		this.idComment = idComment;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String data) {
		this.date = data;
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

	public StatusOfComment getStatusofComment() {
		return statusOfComment;
	}

	public void setStatusofComment(StatusOfComment statusofComment) {
		this.statusOfComment = statusofComment;
	}

	/**
	 * It contains the possible states of a comment.
	 * 
	 * @author Anna
	 */
	public enum StatusOfComment {
		NEW, ALLOWED, REJECTED
	}

	/**
	 * Overridden method class {@code Object} to obtain the object hash code.
	 * 
	 * @see java.lang.Object
	 * @return int type value.
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((film == null) ? 0 : film.hashCode());
		result = prime * result + idComment;
		result = prime * result + ((statusOfComment == null) ? 0 : statusOfComment.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Comment other = (Comment) obj;
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
		if (idComment != other.idComment)
			return false;
		if (statusOfComment != other.statusOfComment)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
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
		return "Comment [text=" + text + ", data=" + date + ", user=" + user + ", film=" + film + ", idComment="
				+ idComment + ", statusofComment=" + statusOfComment + "]";
	}

}
