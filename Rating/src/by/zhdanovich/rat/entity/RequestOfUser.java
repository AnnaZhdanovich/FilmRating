package by.zhdanovich.rat.entity;

/**
 * Class {@code RequestOfUser} describes the request to administrator left by
 * the user.
 */
public class RequestOfUser {
	/**
	 * Identification number of the request.
	 */
	private int idRequest;
	/**
	 * The context of the  request.
	 */
	private String text;
	/**
	 * The status of the role.
	 */
	private StatusOfRequest status;
	/**
	 * The object {@code User} that has written this request.
	 * {@see by.zhdanovich.rat.entity.User}
	 */
	private User user;
	/**
	 * The date when it was  written the request.
	 */
	private String date;

	public RequestOfUser() {
		user = new User();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getIdRequest() {
		return idRequest;
	}

	public void setIdRequest(int idRequest) {
		this.idRequest = idRequest;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public StatusOfRequest getStatus() {
		return status;
	}

	public void setStatus(StatusOfRequest status) {
		this.status = status;
	}

	/**
	 * It contains the possible states of a comment.
	 * 
	 * @author Anna
	 *
	 */
	public enum StatusOfRequest {
		NEW, ALLOWED, REJECTED
	}

	/**
	 * Overridden method class {@code Object} to obtain the object hash code.
	 * 
	 * @return int type value.
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + idRequest;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	/**
	 * Overridden method class {@code Object} of determining the equality of the
	 * current object to another.
	 * 
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
		RequestOfUser other = (RequestOfUser) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (idRequest != other.idRequest)
			return false;
		if (status != other.status)
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
	 * Overridden method class {@code Object} returns an object definition to
	 * describe row.
	 * 
	 * @return string class object.
	 */
	@Override
	public String toString() {
		return "RequestOfUser [idRequest=" + idRequest + ", text=" + text + ", status=" + status + ", user=" + user
				+ ", date=" + date + "]";
	}

}
