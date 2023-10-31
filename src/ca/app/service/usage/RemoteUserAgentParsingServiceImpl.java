package ca.app.service.usage; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import ca.app.model.usage.UserAgent;
import ca.app.util.LogUtil;
import flexjson.JSONDeserializer;

/**
 * This implementation is based on sending a URL request to www.useragentstring.com.
 * 
 * Not sure how reliable, the service is though. Coded this on a Thursday.  Looked at 
 * it again on Monday and it was throwing 500 errors. Saw a posting on StackOverflow
 * that the guy had plans to re-write his service. Submitting this implementation
 * just in case we need it later.
 * 
 * If we do use it later, we need to pair it with some sort of local cache. Each time
 * a new userAgent is parsed from the remote service we'd add it to the cache.  Then
 * if we don't get a hit in the cache, we'd go out to the remote service.
 * 
 * @author wayne
 *
 */
public class RemoteUserAgentParsingServiceImpl implements UserAgentParsingService {
	
	@Override
	public UserAgent parseUserAgent(String rawUserAgent) {
		String responseStr = doRemoteLookup(rawUserAgent);
		UserAgent userAgent = deserializeUserAgentResponse(responseStr);

		return userAgent;
	}

	private String doRemoteLookup(String rawUserAgent) {
		StringBuilder buff = new StringBuilder();
		
		try {
			String urlStr = "http://www.useragentstring.com/"
				+ "?uas=" + URLEncoder.encode(rawUserAgent,"UTF-8")
				+ "&getJSON=all";
			
			LogUtil.logDebug(getClass(), "User-Agent remote lookup: req=" + urlStr);
			
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				buff.append(inputLine);
			}
			
			in.close();
		} catch (UnsupportedEncodingException ex) {
			LogUtil.logException(getClass(), "", ex);
		} catch (MalformedURLException ex) {
			LogUtil.logException(getClass(), "", ex);
		} catch (IOException ex) {
			LogUtil.logException(getClass(), "", ex);
		}
		
		LogUtil.logDebug(getClass(), "User-Agent remote lookup: res=" + buff.toString());
		return buff.toString();
	}
	
	private UserAgent deserializeUserAgentResponse(String response) {
		UserAgent userAgent = new UserAgent();
		
		JSONDeserializer<HashMap<String,String>> jd = new JSONDeserializer<HashMap<String,String>>();
		Object obj = jd.deserialize(response);

		if (obj instanceof HashMap) {
			@SuppressWarnings("unchecked")
			HashMap<String,String> map = (HashMap<String,String>)obj;

			userAgent.setType(map.get("agent_type"));
			userAgent.setName((String)map.get("agent_name"));
			userAgent.setVersion((String)map.get("agent_version"));
			userAgent.setOsName((String)map.get("os_name"));
			userAgent.setOsVersionName((String)map.get("os_versionName"));
			userAgent.setOsVersionNum((String)map.get("os_versionNum"));
		}
		
		return userAgent;
	}
}