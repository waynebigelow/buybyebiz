package ca.app.util;

public class ReCaptchaResponse {
	private boolean success;
	private String[] errorCodes;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String[] getErrorCodes() {
		return errorCodes;
	}
	public void setErrorCodes(String[] errorCodes) {
		this.errorCodes = errorCodes;
	}
}