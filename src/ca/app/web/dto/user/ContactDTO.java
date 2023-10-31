package ca.app.web.dto.user;

public class ContactDTO {

	private String displayName;
	private String telephone;
	
	public ContactDTO(UserDTO user) {
		if (user != null) {
			this.displayName = user.getDisplayName();
			this.telephone = user.getTelephone();
		}
	}

	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}