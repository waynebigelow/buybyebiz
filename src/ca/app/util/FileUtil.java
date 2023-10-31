package ca.app.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
	
public class FileUtil {
	public static File writeBytesToFile(byte[] data, String fileName) throws IOException{
		OutputStream out = new FileOutputStream(fileName);
		out.write(data);
		out.close();
		return new File(fileName);
	}
	 	
	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		long length = file.length();

		if (length > Integer.MAX_VALUE) {

		}

		byte[] bytes = new byte[(int)length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
					 && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
		}

		if (offset < bytes.length) {
				is.close();
				throw new IOException("Could not completely read file "+file.getName());
		}

		is.close();
		return bytes;
	}
		
	public static void copy(String src, String dst) throws IOException{
		File srcFile = new File(src);
		File dstFile = new File(dst);
		copy(srcFile,dstFile);
	}

	public static void copy(File src, File dst) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);
 
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}
	
	public static void copyFileToStream(String fileName, OutputStream output) {
		InputStream is = null;
		OutputStream os = null;
		int ch;
		
		try {
			is = new BufferedInputStream(new FileInputStream(fileName));
			
			if (output instanceof BufferedOutputStream) {
				os = output;
			} else {
				os = new BufferedOutputStream(output);
			}
			
			while ((ch = is.read()) != -1) {
				os.write(ch);
			}
			os.flush();
			is.close();
			
		} catch (Exception ex) {
			LogUtil.logException(FileUtil.class, "Exception", ex);
		}
	}
	
	public static void bufferFileToStream(String fileName, OutputStream outStream, int bufferSize) {
		byte[] byteBuffer = new byte[bufferSize];
		
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(fileName));
			
			int length = 0;
			while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
				outStream.write(byteBuffer,0,length);
			}
			
			in.close();
			outStream.close();
		} catch (IOException ex) {
			LogUtil.logException(FileUtil.class, "Exception", ex);
		}
	}
	
	public static String getFileNameMinusExtension(String fileName) {
		return fileName.substring(0,fileName.lastIndexOf("."));
	}
	
	public static String getFileNameExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}
	
	public static String getUniqueFilename(String filename) {
		int cnt = 2;
		String name = getFileNameMinusExtension(filename);
		String ext = getFileNameExtension(filename);
		while ((new File(filename)).exists()) {
			filename = name + "[" + cnt + "]." + ext;
			cnt++;
		}
		return filename;
	}
}