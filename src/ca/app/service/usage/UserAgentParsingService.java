package ca.app.service.usage;

import ca.app.model.usage.UserAgent;

public interface UserAgentParsingService {
	
	public UserAgent parseUserAgent(String rawUserAgent);
}