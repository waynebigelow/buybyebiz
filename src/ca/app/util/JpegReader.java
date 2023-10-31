package ca.app.util;

import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.common.byteSources.ByteSourceFile;
import org.apache.sanselan.formats.jpeg.JpegImageParser;
import org.apache.sanselan.formats.jpeg.segments.UnknownSegment;

public class JpegReader {

	public static final int COLOR_TYPE_RGB = 1;
	public static final int COLOR_TYPE_CMYK = 2;
	public static final int COLOR_TYPE_YCCK = 3;
	private int colorType = COLOR_TYPE_RGB;
	private boolean hasAdobeMarker = false;

	public BufferedImage readImage(File file) throws IOException, ImageReadException {
		colorType = COLOR_TYPE_RGB;
		hasAdobeMarker = false;
		
		ImageInputStream stream = ImageIO.createImageInputStream(file);
		Iterator<ImageReader> iter = ImageIO.getImageReaders(stream);
		while (iter.hasNext()) {
			ImageReader reader = iter.next();
			reader.setInput(stream);
			
			BufferedImage image;
			ICC_Profile profile = null;
			try {
				LogUtil.logTrace(getClass(),"trying for RGB");
				image = reader.read(0);
				LogUtil.logTrace(getClass(),"success, file was RGB");
			} catch (IIOException e) {
				LogUtil.logTrace(getClass(),"RGB failed, attempting CMYK");
				colorType = COLOR_TYPE_CMYK;
				
				LogUtil.logTrace(getClass(),"Checking adobe marker");
				checkAdobeMarker(file);
				LogUtil.logTrace(getClass(),"Done adobe marker");
				
				LogUtil.logTrace(getClass(),"Getting ICC profile");
				profile = Sanselan.getICCProfile(file);
				LogUtil.logTrace(getClass(),"Done getting ICC profile");
				
				LogUtil.logTrace(getClass(),"Reading raster");
				WritableRaster raster = (WritableRaster) reader.readRaster(0, null);
				LogUtil.logTrace(getClass(),"Done reading raster");
				
				if (colorType == COLOR_TYPE_YCCK){
					LogUtil.logTrace(getClass(),"ColorType is YCCK, converting to CMYK");
					convertYcckToCmyk(raster);
					LogUtil.logTrace(getClass(),"Conversion to CMYK complete");
				}
				
				if (hasAdobeMarker){
					LogUtil.logTrace(getClass(),"Adobe marker is true, correcting inverted colors");
					convertInvertedColors(raster);
					LogUtil.logTrace(getClass(),"Adbobe marker fixed");
				}
				
				LogUtil.logTrace(getClass(),"Converting to RGB");
				image = convertCmykToRgb(raster, profile);
				LogUtil.logTrace(getClass(),"Conversion complete");
			}
			
			LogUtil.logTrace(getClass(),"Returning converted bufferedImage");
			return image;
		}
		
		LogUtil.logTrace(getClass(),"No Readers.");
		return null;
	}

	public void checkAdobeMarker(File file) throws IOException, ImageReadException {
		JpegImageParser parser = new JpegImageParser();
		ByteSource byteSource = new ByteSourceFile(file);
		
		@SuppressWarnings("rawtypes")
		ArrayList segments = parser.readSegments(byteSource, new int[] { 0xffee }, true);
		if (segments != null && segments.size() >= 1) {
			UnknownSegment app14Segment = (UnknownSegment) segments.get(0);
			
			byte[] data = app14Segment.bytes;
			if (data.length >= 12 && data[0] == 'A' && data[1] == 'd' && data[2] == 'o' && data[3] == 'b' && data[4] == 'e'){
				hasAdobeMarker = true;
				int transform = app14Segment.bytes[11] & 0xff;
				if (transform == 2) {
					colorType = COLOR_TYPE_YCCK;
				}
			}
		}
	}

	public static void convertYcckToCmyk(WritableRaster raster) {
		int height = raster.getHeight();
		int width = raster.getWidth();
		int stride = width * 4;
		int[] pixelRow = new int[stride];
		
		for (int h = 0; h < height; h++) {
			raster.getPixels(0, h, width, 1, pixelRow);
	
			for (int x = 0; x < stride; x += 4) {
				int y = pixelRow[x];
				int cb = pixelRow[x + 1];
				int cr = pixelRow[x + 2];
				
				int c = (int) (y + 1.402 * cr - 178.956);
				int m = (int) (y - 0.34414 * cb - 0.71414 * cr + 135.95984);
				y = (int) (y + 1.772 * cb - 226.316);
				
				if (c < 0) c = 0; else if (c > 255) c = 255;
				if (m < 0) m = 0; else if (m > 255) m = 255;
				if (y < 0) y = 0; else if (y > 255) y = 255;
				
				pixelRow[x] = 255 - c;
				pixelRow[x + 1] = 255 - m;
				pixelRow[x + 2] = 255 - y;
			}
			
			raster.setPixels(0, h, width, 1, pixelRow);
		}
	}

	public static void convertInvertedColors(WritableRaster raster) {
		int height = raster.getHeight();
		int width = raster.getWidth();
		int stride = width * 4;
		int[] pixelRow = new int[stride];
		
		for (int h = 0; h < height; h++) {
			raster.getPixels(0, h, width, 1, pixelRow);
			
			for (int x = 0; x < stride; x++) {
				pixelRow[x] = 255 - pixelRow[x];
			}
			
			raster.setPixels(0, h, width, 1, pixelRow);
		}
	}

	public static BufferedImage convertCmykToRgb(Raster cmykRaster, ICC_Profile cmykProfile) throws IOException {
		if (cmykProfile == null){
			LogUtil.logTrace(JpegReader.class,"Color profile is null, loading ICC file");
			cmykProfile = ICC_Profile.getInstance(JpegReader.class.getClassLoader().getResourceAsStream("ISOcoated_v2_300_eci.icc"));
			LogUtil.logTrace(JpegReader.class,"ICC file loaded.");
		}
		
		if (cmykProfile.getProfileClass() != ICC_Profile.CLASS_DISPLAY) {
			LogUtil.logTrace(JpegReader.class,"Profile class != display.");
			byte[] profileData = cmykProfile.getData();
			LogUtil.logTrace(JpegReader.class,"Cloned the profile.");
			
			if (profileData[ICC_Profile.icHdrRenderingIntent] == ICC_Profile.icPerceptual) {
				LogUtil.logTrace(JpegReader.class,"profile is icPerceptual.");
				LogUtil.logTrace(JpegReader.class,"Converting to big Endian.");
				intToBigEndian(ICC_Profile.icSigDisplayClass, profileData, ICC_Profile.icHdrDeviceClass);
				LogUtil.logTrace(JpegReader.class,"Done converting.");
				
				LogUtil.logTrace(JpegReader.class,"Setting cmyk profile.");
				cmykProfile = ICC_Profile.getInstance(profileData);
				LogUtil.logTrace(JpegReader.class,"cmyk profile set.");
			}
		}
		
		LogUtil.logTrace(JpegReader.class,"Creating new color space.");
		ICC_ColorSpace cmykCS = new ICC_ColorSpace(cmykProfile);
		LogUtil.logTrace(JpegReader.class,"Color space created with cmyk profile.");
		
		LogUtil.logTrace(JpegReader.class,"Creating BufferedImage.");
		BufferedImage rgbImage = new BufferedImage(cmykRaster.getWidth(), cmykRaster.getHeight(), BufferedImage.TYPE_INT_RGB);
		LogUtil.logTrace(JpegReader.class,"BufferedImage created.");
		
		LogUtil.logTrace(JpegReader.class,"Creating writable raster.");
		WritableRaster rgbRaster = rgbImage.getRaster();
		LogUtil.logTrace(JpegReader.class,"Raster ceated.");
		
		LogUtil.logTrace(JpegReader.class,"Getting Colorspace.");
		ColorSpace rgbCS = rgbImage.getColorModel().getColorSpace();
		LogUtil.logTrace(JpegReader.class,"Got colorspace.");
		
		LogUtil.logTrace(JpegReader.class,"Creating ColorConvertOp.");
		ColorConvertOp cmykToRgb = new ColorConvertOp(cmykCS, rgbCS, null);
		LogUtil.logTrace(JpegReader.class,"ColorConvertOp created.");
		
		LogUtil.logTrace(JpegReader.class,"Filtering...");
		cmykToRgb.filter(cmykRaster, rgbRaster);
		LogUtil.logTrace(JpegReader.class,"Done filtering, returning converted bufferedImage");
		
		return rgbImage;
	}

	static void intToBigEndian(int value, byte[] array, int index) {
		array[index]   = (byte) (value >> 24);
		array[index+1] = (byte) (value >> 16);
		array[index+2] = (byte) (value >>  8);
		array[index+3] = (byte) (value);
	}
}