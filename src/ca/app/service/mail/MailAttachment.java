package ca.app.service.mail;

import java.io.Serializable;

public class MailAttachment implements Serializable {

	/**
	 * Currently, the only mail attachments that we support start out as html and are converted to 
	 * PDF in the ImmediateMailQueueMDP. If the need arises, this class can be altered to include
	 * paths to system files.
	 */
	
	private static final long serialVersionUID = 7564072633513304953L;
	
	private String filename;
	private String body;
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
}