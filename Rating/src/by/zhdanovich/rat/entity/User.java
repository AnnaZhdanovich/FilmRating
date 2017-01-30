package by.zhdanovich.rat.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Class {@code User} describes user.
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * First name of the user.
	 */
	private String firstName;
	/**
	 * The login of the user.
	 */
	private String login;
	/**
	 * The password of the user.
	 */
	private String password;
	/**
	 * The email of the user.
	 */
	private String email;
	/**
	 * The role of the user.
	 */
	private Role role;
	/**
	 * Identification number of the user.
	 */
	private int idUser;
	/**
	 * The status of the user.
	 */
	private Status status;
	/**
	 * The date of user registration.
	 */
	private Date dateReg;
	/**
	 * The avatar of the the user.
	 */
	private String image;
	/**
	 * The rating of the user
	 */
	private int rating;
	/**
	 * A list of all film assessments set by the user
	 */
	private List<Assessment> listAssessment;
	/**
	 * A list of all film comments set by the user
	 */
	private List<Comment> listComment;

	private int amountOfAssessment;

	private int amountOfComment;

	public User() {

	}

	public int getAmountOfAssessment() {
		return amountOfAssessment;
	}

	public void setAmountOfAssessment(int amountOfAssessment) {
		this.amountOfAssessment = amountOfAssessment;
	}

	public int getAmountOfComment() {
		return amountOfComment;
	}

	public void setAmountOfComment(int amountOfComment) {
		this.amountOfComment = amountOfComment;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getDateReg() {
		return dateReg;
	}

	public void setDateReg(Date dateReg) {
		this.dateReg = dateReg;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public void setListReviews(List<Comment> listReviews) {
		this.listComment = listReviews;
	}

	public enum Role {
		ADMIN, USER
	}

	public enum Status {
		BLOCK, UNBLOCK
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
		result = prime * result + amountOfAssessment;
		result = prime * result + amountOfComment;
		result = prime * result + ((dateReg == null) ? 0 : dateReg.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((listAssessment == null) ? 0 : listAssessment.hashCode());
		result = prime * result + ((listComment == null) ? 0 : listComment.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + rating;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + idUser;
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
		User other = (User) obj;
		if (amountOfAssessment != other.amountOfAssessment)
			return false;
		if (amountOfComment != other.amountOfComment)
			return false;
		if (dateReg == null) {
			if (other.dateReg != null)
				return false;
		} else if (!dateReg.equals(other.dateReg))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
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
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (rating != other.rating)
			return false;
		if (role != other.role)
			return false;
		if (status != other.status)
			return false;
		if (idUser != other.idUser)
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
		return "User [firstName=" + firstName + ", login=" + login + ", password=" + password + ", email=" + email
				+ ", role=" + role + ", uid=" + idUser + ", status=" + status + ", dataReg=" + dateReg + ", image="
				+ image + ", rating=" + rating + ", listAssessment=" + listAssessment + ", listComment=" + listComment
				+ ", amountOfAssessment=" + amountOfAssessment + ", amountOfComment=" + amountOfComment + "]";
	}

}
