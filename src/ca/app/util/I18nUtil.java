package ca.app.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class I18nUtil {
	
	/**
	 * To simply return the matched records, use replaceText=null.
	 * @param srcFilename
	 * @param findText
	 * @param replaceText
	 */
	public static void findAndReplace(String srcFilename, String findText, String replaceText) {
		File fileIn = new File(srcFilename);
		System.out.println(srcFilename + "\n");
		try {
			BufferedReader input = new BufferedReader(new FileReader(fileIn));
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					if (findText!=null && line.indexOf(findText)>=0) {
						if (replaceText!=null) {
							line = line.replaceAll(findText, replaceText);
						}
						System.out.println(line);
					}
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("\n");
	}
	
	public static void checkSingleQuotes(String srcFilename) {
		File fileIn = new File(srcFilename);
		System.out.println(srcFilename);
		try {
			BufferedReader input = new BufferedReader(new FileReader(fileIn));
			int lineNum = 0;
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					lineNum++;
					int idx = line.indexOf("{0}");
					if (idx>=0) {
						if (hasOddNumberOfChars(line,'\'')) {
							System.out.println("[" + lineNum + "]" + line);
						}
					}
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("\n");
	}
	
	public static void checkCurlyBraces(String srcFilename) {
		File fileIn = new File(srcFilename);
		System.out.println(srcFilename);
		try {
			BufferedReader input = new BufferedReader(new FileReader(fileIn));
			int lineNum = 0;
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					lineNum++;
					if (lineNum==878) {
						System.out.println("line: [" + line + "]");
					}
					
					int braceCount = 0;
					for (int i=0; i<line.length(); i++) {
						char c = line.charAt(i);
						if (c == '{') {
							braceCount++;
						}
						if (c == '}') {
							braceCount--;
						}
						if (lineNum==878) {
							System.out.println("char: [" + c + "][" + braceCount + "]");
						}
						if (braceCount<0 || braceCount>1) {
							System.out.println("Pattern of curly braces are messed up on line: " + lineNum);
						}
					}
					if (braceCount!=0) {
						System.out.println("Pattern of curly braces are messed up on line: " + lineNum);
					}
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("\n");
	}
	
	public static boolean hasOddNumberOfChars(String line, char chr) {
		int count = 0;
		int idx = line.indexOf(chr);
		while (idx>=0) {
			count++;
			line = line.substring(idx+1);
			idx = line.indexOf(chr);
		}
		return count%2==1;
	}
	
	public static void transformCharset(String srcCharsetName, String srcFilename, String trgCharsetName, String trgFilename) {

		try {
			Charset srcCharset = Charset.forName(srcCharsetName);
			Charset trgCharset = Charset.forName(trgCharsetName);

			File fileIn = new File(srcFilename);
			byte[] bytes = FileUtil.getBytesFromFile(fileIn);			
			ByteBuffer inputBuffer = ByteBuffer.wrap(bytes);

			CharBuffer data = srcCharset.decode(inputBuffer);

			ByteBuffer outputBuffer = trgCharset.encode(data);
			byte[] outputData = outputBuffer.array();

			FileUtil.writeBytesToFile(outputData, trgFilename);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static void generateUntranslatedFile(String localeCode) {
		File base = new File("./src/resources.properties");

		File lang = new File("./src/resources_" + localeCode + ".properties");
		File todo = new File("./src/todo_" + localeCode + ".properties");
		
		makeUntranslatedFile(buildLangMap(base),buildLangMap(lang),todo);
	}
	
	public static void makeUntranslatedFile(Map<String,String> base, Map<String,String> lang, File fout) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fout));

			try {
				int lineCount = 0;
				int wordCount = 0;
				for (String key : base.keySet()) {
					if (!lang.containsKey(key)) {
						String value = base.get(key);
						out.write(key.trim() + " = " + value + "\n");
						lineCount++;
						wordCount += countWords(value);
					}
				}
				System.out.println(fout.getName() + " - untranslated: [lines=" + lineCount + "][words=" + wordCount + "]");
			} finally {
				out.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	

	public static void handleRegionalDifferences(String srcFilePath, String trgFilePath, String trgPrefix, String srcWord, String trgWord) {
		// There are 2 Use Cases:
		// 1) The src and trg files are different
		//    - Identify the word in the src file, copy the entry to the trg file and replace the src word with the trg word.
		//    - What if the entry already exists in the trg file?
		//    - Make sure that you run it enough times to handle capitalization (I.e. color->colour and Color->Colour)
		// 2) The src and trg files are the same
		//    - Use the find/replace functionality in eclipse
		
		
		File srcFile = new File(srcFilePath);
		File trgFile = new File(trgFilePath);

		Map<String,String> srcMap = buildLangMap(srcFile);
		Map<String,String> trgMap = buildLangMap(trgFile);
		
		for (String key : srcMap.keySet()) {
			String srcValue = srcMap.get(key);
			if (srcValue.indexOf(srcWord)>0) {
				String trgValue = "";
				if (trgMap.containsKey(trgPrefix+key)) {
					trgValue = trgMap.get(trgPrefix+key);
					if (trgValue.indexOf(srcWord)>0) {
						trgValue = trgValue.replaceAll(srcWord, trgWord);
						System.out.println("Updated value in TRG file: " + trgPrefix+key + " = " + trgValue);
					}
				} else {
					trgValue = srcValue.replaceAll(srcWord, trgWord);
					System.out.println("Added value to TRG file: " + trgPrefix+key + " = " + trgValue);
				}
				trgMap.put(trgPrefix+key, trgValue);
			}
		}
		
		outputLangMap(trgMap, trgFile);
		
	}
	
	public static int countWords(String line) {
		int wordCount = 0;
		Pattern p = Pattern.compile("^(.*)\\s(.*)$");
		
		Matcher m = p.matcher(line);
		while (m.matches()) {
			line = m.group(1);
			String word = m.group(2);
			wordCount += ((word.trim().length()>0)?1:0);
			m = p.matcher(line);
		}
		if (line.trim().length()>0) {
			wordCount++;
		}
		
		return wordCount;
	}
	
	public static Map<String,String> buildLangMap(File file) {
		Map<String,String> map = new LinkedHashMap<String,String>();
		
		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					int idx = line.indexOf("=");
					if (idx>0) {
						String key = line.substring(0,idx).trim();
						String val = line.substring(idx+1).trim();
						map.put(key, val);
					}
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return map;
	}
	
	private static void outputLangMap(Map<String,String> langMap, File fileOut) {
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(fileOut));
			
			for (String key : langMap.keySet()) {
				String value = langMap.get(key);
				output.write(key + " = " + value + "\n");
			}
			
			output.close();
		} catch (IOException ex) {
			
		}
		
	}
	
	public static void checkKeyCounts(String filename) {
		System.out.println("\nFile: " + filename);
		File file = new File(filename);
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		
		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					int idx = line.indexOf("=");
					if (idx>0) {
						String key = line.substring(0,idx).trim();
						
						int cnt = 1;
						if (map.get(key)!=null) {
							cnt = map.get(key)+1;
						}
						map.put(key, cnt);
					}
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		for (String key : map.keySet()) {
			int cnt = map.get(key);
			if (cnt>1) {
				System.out.println("[" + cnt + "][" + key + "]");
			}
		}
	}
	
	public static void generateBundle(String fout, boolean isDecorated) {
		File fileIn = new File("./src/resources.properties");
		File fileOut = new File(fout);
		
		try {
			BufferedReader input = new BufferedReader(new FileReader(fileIn));
			BufferedWriter output = new BufferedWriter(new FileWriter(fileOut));
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					
					int idx = line.indexOf("=");
					if (idx>0 && isDecorated) {
						String key = line.substring(0,idx).trim();
						String val = line.substring(idx+1).trim();
						output.write(key + " = [" + val + "]\n");
					} else {
						output.write(line + "\n");
					}
				}
			} finally {
				input.close();
				output.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
}