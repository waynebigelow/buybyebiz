package ca.app.util;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.ImageIcon;

import org.apache.sanselan.ImageReadException;

public class ImageUtil {
		
	public static byte[] cropImage(byte[] photo,int x, int y, int w, int h) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		try {
			Image image = new ImageIcon(photo).getImage();
			BufferedImage outImage = toBufferedImage(image);
			int width = outImage.getWidth();
			int height = outImage.getHeight();

			if (x < 0){
				x = 0;
			} else if (x > width){
				x = width - 1;
			}
			
			if (y < 0){
				y = 0;
			} else if (y > height){
				y = height - 1;
			}
			
			if( w < 0){
				w = 0;
			} else if ( w > width){
				w = width - 1;
			}
			
			if (h < 0){
				h = 0;
			} else if (h > height){
				h = height - 1;
			}

			// These offset calculations are to make sure that the crop
			// box doesn't fall outside the boundaries of the original image.
			// if it does, it will just take the difference between the crop
			// x and y
			if (x + w > width) {
				w = width - x;
			}
			if (y + h > height) {
				h = height - y;
			}
			
			BufferedImage img = outImage.getSubimage(x,y,w,h);
			ImageIO.write(img,"jpg",baos);
			baos.close();
		} catch(Exception e) {
			LogUtil.logException(ImageUtil.class, "Exception", e);
		}
		return baos.toByteArray();
	}
	
	public static byte[] resizeImage(byte[] photo, int maxX, int maxY) {
		try {
			Image image = new ImageIcon(photo).getImage();
			BufferedImage outImage = toBufferedImage(image);
			
			int width = 0;
			int height = 0;
			if (outImage.getHeight() > maxY || outImage.getWidth() > maxX) {
				double ratio = 1.0;
				double secondRatio = 1.0;
				
				if (outImage.getWidth() > maxX) {
					ratio = (double) maxX / outImage.getWidth();
					height = (int)(outImage.getHeight() * ratio);
					width = maxX;
					if (height > maxY) {
						secondRatio = (double) maxY / height;
						height = maxY;
						width = (int)(width * secondRatio);
					}
				} else {
					ratio = (double) maxY / outImage.getHeight();
					width = (int)(outImage.getWidth() * ratio);
					height = maxY;
				}
				photo = resize(width, height, photo);	
			} else {
				photo = resize(outImage.getWidth(), outImage.getHeight(), photo);
			}
	
		} catch(Exception e) {
			LogUtil.logException(ImageUtil.class, "Exception", e);		 
		}
		
		return photo;
	}
	
	public static byte[] resize(int width, int height, byte[]photo) throws IOException {
		ImageIcon imageIcon = new ImageIcon(photo);
		BufferedImage bufferedResizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedResizedImage.createGraphics();

		// See:http://java.sun.com/developer/JDCTechTips/2004/tt0518.html#1
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING  , RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING , RenderingHints.VALUE_RENDER_QUALITY );
		//g2d.setRenderingHint(RenderingHints.KEY_DITHERING , RenderingHints.VALUE_DITHER_ENABLE );
		//g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL  , RenderingHints.VALUE_STROKE_PURE );
		
		g2d.drawImage(imageIcon.getImage(), 0, 0, width, height, null);
		g2d.dispose();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedResizedImage,"jpg",baos);
		baos.close();
		imageIcon.getImage().flush();
		return baos.toByteArray();
	}
	
	public static byte[] resizeWithMatte(int width, int height, byte[]photo, int canvasWidth, int canvasHeight) throws IOException {
		int xOffset = (canvasWidth - width) / 2;
		return resizeWithMatte(width, height, photo, canvasWidth, canvasHeight, xOffset, 0);
	}
	
	public static byte[] resizeWithMatte(int width, int height, byte[]photo, int canvasWidth, int canvasHeight, int xOffset, int yOffset) throws IOException {
		ImageIcon imageIcon = new ImageIcon(photo);
		BufferedImage bufferedResizedImage = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedResizedImage.createGraphics();
		//we might have some tinkering to do here....
		//see:http://java.sun.com/developer/JDCTechTips/2004/tt0518.html#1
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING  , RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING , RenderingHints.VALUE_RENDER_QUALITY );
		//g2d.setRenderingHint(RenderingHints.KEY_DITHERING , RenderingHints.VALUE_DITHER_ENABLE );
		//g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL  , RenderingHints.VALUE_STROKE_PURE );
		
		// Draw the resized image
		g2d.drawImage(imageIcon.getImage(), xOffset, yOffset, width, height, null);
		g2d.dispose();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedResizedImage,"jpg",baos);
		baos.close();
		imageIcon.getImage().flush();
		return baos.toByteArray();
	}
	
	public static void resizePhoto(File photo, int maxWidth, int maxHeight) {
		LogUtil.logDebug(ImageUtil.class, "Resizing using imageMagick: " + photo.getName() + " in folder: " + photo.getParentFile().getAbsolutePath());
		try {
			String command = "";
			if(ProjectUtil.getProperty("imagemagick.home") != null){
				command = ProjectUtil.getProperty("imagemagick.home");
			}
			command += "convert " + photo.getName() + " -resize " + maxWidth + "x" + maxHeight + " " + photo.getName();
			int returnValue = ProcessUtils.processCommand(command, photo.getParentFile().getAbsolutePath());
			if(returnValue != 0) {
				throw new RuntimeException("ImageMagick Error");
			}
		}catch (Exception e) {
			LogUtil.logException(ImageUtil.class, "Resize failed.  Falling back to manual resize. Check to see if ImageMagick  is installed, and verify that it is on the PATH", e);
			manualResizePhoto(photo, maxWidth, maxHeight);
		}
	}
	@Deprecated
	private static void manualResizePhoto(File photo,int maxX, int maxY) {
		try{
			byte[] photoBytes = FileUtil.getBytesFromFile(photo);
			//load the image
			Image image = new ImageIcon(photoBytes).getImage();
			///get a bufferedimage
			BufferedImage outImage = toBufferedImage(image);
			if(outImage.getHeight() > maxY || outImage.getWidth() > maxX) {
				int width = 0;
				int height = 0;
				double ratio = 1.0;
				double secondRatio = 1.0;
				//do the width first
				if(outImage.getWidth() > maxX) {//if the image is too wide...
					ratio = (double) maxX / outImage.getWidth();
					height = (int)(outImage.getHeight() * ratio);
					width = maxX;
					if(height > maxY) {//the new height might still be too tall, so check that out too
						//this is where it gets sticky, we already determined the x ratio to get this far,
						//so we'll
						secondRatio = (double) maxY / height;
						height = maxY;
						width = (int)(width * secondRatio);
					}
				}else {//if the image is too tall...
					ratio = (double) maxY / outImage.getHeight();
					width = (int)(outImage.getWidth() * ratio);
					height = maxY;
				}
				resize(photo,width,outImage.getWidth(),outImage.getHeight());
			}else {				
				resize(photo,outImage.getWidth(),outImage.getWidth(),outImage.getHeight());
			}

		}catch(Exception e){
		    LogUtil.logException(ImageUtil.class, "Exception", e);		 
		}
	}
	
	public static void resizePortraitPhoto(File photo, int maxY) {
		try{
			byte[] photoBytes = FileUtil.getBytesFromFile(photo);
			//load the image
			Image image = new ImageIcon(photoBytes).getImage();
			///get a bufferedimage
			BufferedImage outImage = toBufferedImage(image);
			double ratio = 0.0;
			ratio = (double) maxY / outImage.getHeight();
			int height = maxY;
			int width = (int)(outImage.getWidth() * ratio);
			FileUtil.writeBytesToFile(resize(width,height,photoBytes),photo.getAbsolutePath());
		}catch(Exception e){
		    LogUtil.logException(ImageUtil.class, "Exception", e);		 
		}
	}

	public static void resizeLandscapePhoto(File photo, int maxX) {
		try{
			byte[] photoBytes = FileUtil.getBytesFromFile(photo);
			//load the image
			Image image = new ImageIcon(photoBytes).getImage();
			///get a bufferedimage
			BufferedImage outImage = toBufferedImage(image);
			double ratio = 0.0;
			ratio = (double) maxX / outImage.getWidth();
			int height = (int)(outImage.getHeight() * ratio);
			int width = maxX;
			FileUtil.writeBytesToFile(resize(width,height,photoBytes),photo.getAbsolutePath());
		}catch(Exception e){
		    LogUtil.logException(ImageUtil.class, "Exception", e);		 
		}
	}
	
	public static void resize(File originalFile, int newWidth, int iWidth, int iHeight) throws IOException { 

		ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath()); 
		Image i = ii.getImage(); 
		Image resizedImage = null; 
		if(newWidth != iWidth){	
			if (iWidth > iHeight) { 
			resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH); 
			} else { 
			resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight, newWidth, Image.SCALE_SMOOTH); 
			} 
		}else{//we're not resizing, just rewriting the jpg to optimize it.
			resizedImage = i.getScaledInstance(iWidth, iHeight, Image.SCALE_SMOOTH);
		}
		
		// This code ensures that all the pixels in the image are loaded. 
		Image temp = new ImageIcon(resizedImage).getImage(); 

		// Create the buffered image. 
		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null), 
		BufferedImage.TYPE_INT_RGB); 

		// Copy image to buffered image. 
		Graphics g = bufferedImage.createGraphics(); 

		// Clear background and paint the image. 
		g.setColor(Color.white); 
		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null)); 
		g.drawImage(temp, 0, 0, null); 
		g.dispose(); 

		// Soften. 
		float softenFactor = 0.05f; 
		float[] softenArray = {0, softenFactor, 0, softenFactor, 1-(softenFactor*4), softenFactor, 0, softenFactor, 0}; 
		Kernel kernel = new Kernel(3, 3, softenArray); 
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null); 
		bufferedImage = cOp.filter(bufferedImage, null); 

		
		
		// Write the jpeg to a file. 
		FileImageOutputStream out = new FileImageOutputStream(originalFile);
		ImageWriter encoder = (ImageWriter)ImageIO.getImageWritersByFormatName("JPEG").next();
		JPEGImageWriteParam param = new JPEGImageWriteParam(null);

		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(1);

		encoder.setOutput(out);
		encoder.write((IIOMetadata) null, new IIOImage(bufferedImage,null,null), param);

		out.close();
		} 

	
		
	public static BufferedImage toBufferedImage(Image image) {
			 if (image instanceof BufferedImage) {
				 return (BufferedImage)image;
			 }
    
			 // This code ensures that all the pixels in the image are loaded
			 image = new ImageIcon(image).getImage();
    
			 // Determine if the image has transparent pixels; for this method's
			 // implementation, see e661 Determining If an Image Has Transparent Pixels
			 boolean hasAlpha = hasAlpha(image);
 
			 //boolean hasAlpha = false;
    
			 // Create a buffered image with a format that's compatible with the screen
			 BufferedImage bimage = null;
			 GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			 try {
				 // Determine the type of transparency of the new buffered image
				 int transparency = Transparency.OPAQUE;
				 if (hasAlpha) {
					 transparency = Transparency.BITMASK;
				 }
    				 // Create the buffered image
				 GraphicsDevice gs = ge.getDefaultScreenDevice();
				 GraphicsConfiguration gc = gs.getDefaultConfiguration();
				 bimage = gc.createCompatibleImage(
					 image.getWidth(null), image.getHeight(null), transparency);
			 } catch (HeadlessException e) {
				//no ui toolkit
			 }
    
			 if (bimage == null) {
				 // Create a buffered image using the default color model
				 int type = BufferedImage.TYPE_INT_RGB;
				 if (hasAlpha) {
					 type = BufferedImage.TYPE_INT_ARGB;
				 }
				 bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
			 }
    
			 // Copy image to  buffered image
			 Graphics g = bimage.createGraphics();
    
			 // Paint the image onto the buffered image
			 g.drawImage(image, 0, 0, null);
			 g.dispose();
			 image.flush();
			 return bimage;
		 }
 
//			This method returns true if the specified image has transparent pixels
		 public static boolean hasAlpha(Image image) {
			 // If buffered image, the color model is readily available
			 if (image instanceof BufferedImage) {
				 BufferedImage bimage = (BufferedImage)image;
				 return bimage.getColorModel().hasAlpha();
			 }
    
			 // Use a pixel grabber to retrieve the image's color model;
			 // grabbing a single pixel is usually sufficient
			  PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
			 try {
				 pg.grabPixels();
			 } catch (InterruptedException e) {
			 }
    
			 // Get the image's color model
			 ColorModel cm = pg.getColorModel();
			 if(cm == null){
				 return false;
			 }
			 return cm.hasAlpha();
		 }
		 
		 public static Map<String,Integer> getXY(byte[] photo){
			 Map<String,Integer> imgMeta = new HashMap<String,Integer>();
			 ImageIcon imageIcon = new ImageIcon(photo);
			 imgMeta.put("height",imageIcon.getIconHeight());
			 imgMeta.put("width",imageIcon.getIconWidth());
			 return imgMeta;
		 }
		
			/**
			 * Deprecated, ImageMagick does a much better job of this.  however, if
			 * it's not installed, this will still kick in.
			 * Compresses an image.  The acceptable quality values should be
			 * 1.0 - best quality largest file size
			 * 0.9 - good quality, large file size
			 * 0.6 - average quality medium file size
			 * 0.3 - average quality small file size
			 * 0.1 - horrible, don't use unless it's for a thumbnail
			 * @param image
			 * @param fileName
			 * @param quality
			 * @throws IOException
			 */
			@Deprecated
			public static void compressImage(File imageFile, float quality) { 
				LogUtil.logDebug(ImageUtil.class, "Compressing : " + imageFile.getName()); 

				try{
					BufferedImage image = ImageIO.read(imageFile);
					Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpeg"); 
					if (!writers.hasNext()) throw new IllegalStateException("No writers found"); 
					ImageWriter writer = (ImageWriter) writers.next(); 
					ImageWriteParam param = writer.getDefaultWriteParam(); 
					param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); 
					param.setCompressionQuality(quality); 
					FileImageOutputStream output = new FileImageOutputStream(imageFile); 
					writer.setOutput(output); 
					writer.write(null, new IIOImage(image, null,null), param);
				}catch(Exception e){
					LogUtil.logException(ImageUtil.class, "Failed to compress file: " + imageFile.getName(),e);	
				}
			} 
			
			public static void optimizeImage(File imageFile, int quality) {
				LogUtil.logDebug(ImageUtil.class, "Optimizing using imageMagick: " + imageFile.getName());
				try {
					String command = "";
					
					if(ProjectUtil.getProperty("imagemagick.home") != null) {
						command =ProjectUtil.getProperty("imagemagick.home");
					}
							
					command += "mogrify -quality " + quality + " " + imageFile.getAbsoluteFile();
					int returnValue = ProcessUtils.processCommand(command, imageFile.getParentFile().getAbsolutePath());
					if(returnValue != 0) {
						throw new RuntimeException("ImageMagick Error");
					}
					ProcessUtils.processCommand(command, imageFile.getParentFile().getAbsolutePath());
				}catch (Exception e) {
					LogUtil.logException(ImageUtil.class, "Optimization failed.  Falling back to old compression. Check to see if ImageMagick  is installed, and verify that it is on the PATH", e);
					compressImage(imageFile, 1.0F);
				}
			}

	/**
	 * Returns the max dimension possible so that neither dimension exceeds the maximum allowable.
	 */
	public static Dimension getMaxSizeToFillArea(int width, int height, int maxWidth, int maxHeight) {
		int newHeight = 0;
		int newWidth = 0;
		
		newWidth = maxWidth;
		newHeight = (maxWidth * height) / width;
		if (newHeight<=maxHeight && newWidth<=maxWidth) {
			return new Dimension(newWidth, newHeight);
		}
		
		newWidth = (maxHeight * width) / height;
		newHeight = maxHeight;
		if (newHeight<=maxHeight && newWidth<=maxWidth) {
			return new Dimension(newWidth, newHeight);
		}
		
		return new Dimension(newWidth, newHeight);
	}
	
	/**
	 * Returns the min dimension possible so that both dimension exceed or equal the maximum allowable.
	 */
	public static Dimension getMinSizeToFillArea(int width, int height, int maxWidth, int maxHeight) {
		int newHeight = 0;
		int newWidth = 0;
		
		newWidth = maxWidth;
		newHeight = (maxWidth * height) / width;
		if (newHeight>=maxHeight && newWidth>=maxWidth) {
			return new Dimension(newWidth, newHeight);
		}
		
		newWidth = (maxHeight * width) / height;
		newHeight = maxHeight;
		if (newHeight>=maxHeight && newWidth>=maxWidth) {
			return new Dimension(newWidth, newHeight);
		}
		
		return new Dimension(newWidth, newHeight);
	}
	
	public static Dimension getZoomedDimensions(int width, int height, int zoomPercentage) {
		int newHeight = 0;
		int newWidth = 0;
		
		newHeight = (height * zoomPercentage) / 100;
		newWidth = (width * zoomPercentage) / 100;
		
		return new Dimension(newWidth, newHeight);
	}
	
	public static byte[] rotate(File imageFile, int angle) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try{
			BufferedImage imageToRotate = ImageIO.read(imageFile);
			BufferedImage rotatedImage = new BufferedImage(imageToRotate.getHeight(null), imageToRotate.getWidth(null), imageToRotate.getType()); 
			 
		    Graphics2D g2d = (Graphics2D) rotatedImage.getGraphics(); 
		    g2d.rotate(Math.toRadians(angle)); 
		    if(angle == 90){
		    	g2d.drawImage(imageToRotate, 0, -rotatedImage.getWidth(null), null);
		    }else{
		    	g2d.drawImage(imageToRotate, -rotatedImage.getHeight(null), 0, null);
		    }
		    g2d.dispose();
		    
			ImageIO.write(rotatedImage,"jpg",baos);
		}catch(Exception e){
			LogUtil.logException(ImageUtil.class, "Failed to rotate file: " + imageFile.getName(),e);	
		}
		return baos.toByteArray();
    }    
	
	public static void convertToRGB(File photo) throws IOException{
		JpegReader jpr = new JpegReader();
		try {
			LogUtil.logTrace(ImageUtil.class,"checking color profile");
			BufferedImage img = jpr.readImage(photo);
			LogUtil.logTrace(ImageUtil.class,"done checking color profile");
			LogUtil.logTrace(ImageUtil.class,"resaving file as RGB");
			ImageIO.write(img, "jpg", photo);
			LogUtil.logTrace(ImageUtil.class,"RGB conversion complete");
		} catch (ImageReadException e) {
			LogUtil.logException(ImageUtil.class, "Convert to RGB Failed, file will remain unchanged", e);
		}

	}
	
/**


experimental, needs work...
	public static File rotate(File imageFile, int angle) throws IOException {
		LogUtil.logDebug(ImageUtil.class, "Rotating using imageMagick: " + imageFile.getName());
		try {
			String command = "";
			if(ProjectUtil.getProperty(ProjectUtil.IMAGEMAGICK_HOME) != null){
				command = ProjectUtil.getProperty(ProjectUtil.IMAGEMAGICK_HOME);
			}
			command += "convert -rotate " + angle + " " + imageFile.getAbsoluteFile() + " " + imageFile.getAbsoluteFile();
			ProcessUtils.processCommand(command, imageFile.getParentFile().getAbsolutePath());
		}catch (Exception e) {
			LogUtil.logException(ImageUtil.class, "Image rotation failed for " + imageFile, e);
		}
		return imageFile;
    }  
	*/
}
