package ca.app.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import flexjson.JSONDeserializer;

public class ReCaptchaUtil {
	public static String getScript(String lang) {
		StringBuilder sb = new StringBuilder();
		sb.append("<script type=\"text/javascript\">");
		sb.append("var recaptcha1;");
		sb.append("var recaptcha2;");
		sb.append("var onloadCallback = function() {");
		sb.append("	recaptcha1 = grecaptcha.render('recaptcha1', {");
		sb.append("		'sitekey' : '"+ProjectUtil.getProperty("recaptcha.public.key")+"',");
		sb.append("		'theme' : 'light'");
		sb.append("	});");
		sb.append("	recaptcha2 = grecaptcha.render('recaptcha2', {");
		sb.append("		'sitekey' : '"+ProjectUtil.getProperty("recaptcha.public.key")+"',");
		sb.append("		'theme' : 'light'");
		sb.append("	});");
		sb.append("};");
		sb.append("function resetRecaptcha1(){");
		sb.append("	grecaptcha.reset(recaptcha1);");
		sb.append("}");
		sb.append("function resetRecaptcha2(){");
		sb.append("	grecaptcha.reset(recaptcha2);");
		sb.append("}");
		sb.append("</script>");
		sb.append("<script src=\""+ProjectUtil.getProperty("recaptcha.api.url")+"?onload=onloadCallback&render=explicit&hl="+lang+"\" async defer></script>");
		sb.append("<div id=\"recaptchaDiv\" style=\"padding-top:5px;padding-bottom:5px;\">");
		sb.append("	<p class=\"instructionText\">");
		sb.append("		reCAPTCHA is a service that protects BuyByeBiz from spam and abuse. It uses advanced risk analysis techniques to tell humans and bots apart.");
		sb.append("	</p>");
		sb.append("	<div id=\"recaptcha1\"></div>");
		sb.append("</div>");
		return sb.toString();
	}
	
	public static boolean isValid(String remoteAddr, String response) throws Exception {
		URL url = new URL(ProjectUtil.getProperty("recaptcha.verify.url") +
				"?secret="+ProjectUtil.getProperty("recaptcha.secret.key")+
				"&response="+response+
				"&remoteip="+remoteAddr);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		String line = "";
		String outputString = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		while ((line = reader.readLine()) != null) {
			outputString += line;
		}
		conn.disconnect();
		
		JSONDeserializer<ReCaptchaResponse> parser = new JSONDeserializer<ReCaptchaResponse>();
		parser.use(null, ReCaptchaResponse.class);
		ReCaptchaResponse reCaptchaResponse = (ReCaptchaResponse)parser.deserialize(outputString);

		return reCaptchaResponse.isSuccess();
	}
}