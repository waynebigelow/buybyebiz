package ca.app.security.channel;

import javax.servlet.http.HttpServletRequest;

import ca.app.util.LogUtil;
import ca.app.util.StringUtil;

public class ChannelProcessorUtil {
	
	public static boolean isSecureRequest(HttpServletRequest request) {
		// Check if we are behind an Apache that is configured to add headers when the request is secure.
		boolean isSsl = StringUtil.convertStringToBoolean(request.getHeader("X-Forwarded-Arg"), false);
		
		// Check if we are behind a Load Balancer that is configured to add headers regarding the protocol.
		boolean isProtoHttps = false;
		String protoHeader = request.getHeader("X-Forwarded-Proto");
		if (protoHeader != null && "https".equals(protoHeader)) {
			isProtoHttps = true;
		}
		
		LogUtil.logDebug(ChannelProcessorUtil.class, "Channel security: [X-Forwarded-Ssl:" + isSsl + "][X-Forwarded-Proto:" + protoHeader + "][request.isSecure():" + request.isSecure() + "][" + request.getRequestURL() + "]");
		
		// Too Noisy
		//LogUtil.logDebug(ChannelProcessorUtil.class, "Headers: " + RequestUtil.getRequestHeaders(request));
		
		return isSsl || isProtoHttps;
	}
}