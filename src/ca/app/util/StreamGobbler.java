package ca.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamGobbler implements Runnable {
	String name;
	InputStream is;
	Thread thread;

	public StreamGobbler (String name, InputStream is) {
		this.name = name;
		this.is = is;
	}

	public void start () {
		thread = new Thread (this);
		thread.start ();
	}

	public void run () {
		try {
			InputStreamReader isr = new InputStreamReader (is);
			BufferedReader br = new BufferedReader (isr);

			while (true) {
				String s = br.readLine ();
				if (s == null) break;
				LogUtil.logInfo(this.getClass(),"[" + name + "] " + s);
			}

			is.close ();

		} catch (Exception ex) {
			LogUtil.logInfo(this.getClass(),"Problem reading stream " + name + "... :" + ex);
			ex.printStackTrace ();
		}
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public InputStream getInputStream() {
		return is;
	}
	
	public void setInputStream(InputStream is) {
		this.is = is;
	}
}