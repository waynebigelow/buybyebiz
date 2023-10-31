package ca.app.util;

import java.io.File;
import java.io.IOException;

public class ProcessUtils {
	public static int processCommand(String command, String workDir) throws IOException, InterruptedException {
		int processExitCode = -99;
		
		File workingDirectory = new File(workDir);
		
		try {
			Process p = Runtime.getRuntime().exec(command, null, workingDirectory);
			LogUtil.logDebug(ProcessUtils.class, "Executing : " + command + " Work Directory : " + workingDirectory);
		
			StreamGobbler s1 = new StreamGobbler ("stdin", p.getInputStream());
			StreamGobbler s2 = new StreamGobbler ("stderr", p.getErrorStream());
			
			s1.start ();
			s2.start ();
			
			processExitCode = p.waitFor();
		} catch (IOException ex) {
			LogUtil.logException(ProcessUtils.class, "Error processing request", ex);
			throw ex;
		} catch (InterruptedException ex) {
			LogUtil.logException(ProcessUtils.class, "Error processing request", ex);
			throw ex;
		}
		
		return processExitCode;
	}
}