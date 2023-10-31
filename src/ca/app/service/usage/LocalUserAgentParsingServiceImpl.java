package ca.app.service.usage; 

import ca.app.model.usage.UserAgent;

public class LocalUserAgentParsingServiceImpl implements UserAgentParsingService {
	
	private static nl.bitwalker.useragentutils.UserAgent ua = null;
	
	@Override
	public UserAgent parseUserAgent(String rawUserAgent) {
		if (ua == null) {
			ua = nl.bitwalker.useragentutils.UserAgent.parseUserAgentString(rawUserAgent);
		}
		
		UserAgent userAgent = new UserAgent();
		userAgent.setRaw(rawUserAgent);
		userAgent.setName(ua.getBrowser().getName());
		userAgent.setOsName(ua.getOperatingSystem().getName());

		return userAgent;
	}
}