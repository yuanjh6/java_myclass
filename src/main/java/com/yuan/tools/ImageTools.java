package com.yuan.tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;


public class ImageTools {
	static int GRAYBIT = 4;
	/**
	 * 读取图片文件
	 * @param file
	 * @return
	 */
	public static BufferedImage readImage(File file){
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	/**
	 * 获得信息指纹
	 * @param pixels 灰度值矩阵
	 * @param avg 平均值
	 * @return 返回01串的十进制表示
	 */
	public static byte[] getFingerPrint(double[][] pixels, double avg) {
		
		int width = pixels[0].length;
		int height = pixels.length;
		byte[] bytes = new byte[height*width];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if(pixels[i][j] >= avg){
					bytes[i*height + j] = 1;
				}else {
					bytes[i*height + j] = 0;
				}
			}
		}
//	    int fingerprint = 0;
//	    for(int i = 0; i < bytes.length; i++){
//	    	fingerprint += (bytes[bytes.length-i-1] << i);
//	    }
//		return fingerprint;
		return bytes;
	}
	/**
	 * 缩小图片
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage reduceSize(BufferedImage image, int width,
			int height) {
		
		BufferedImage new_image = null;
		double width_times = (double) width / image.getWidth();
		double height_times = (double) height / image.getHeight();
		if (image.getType() == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = image.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(width,height);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			new_image = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			new_image = new BufferedImage(width, height, image.getType());
		Graphics2D g = new_image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(image, AffineTransform.getScaleInstance(width_times, height_times));
		g.dispose();
		return new_image;
	}
	/**
	 * 得到平均值
	 * @param pixels 灰度值矩阵
	 * @return
	 */
	public static double getAverage(double[][] pixels) {
		int width = pixels[0].length;
		int height = pixels.length;
		int count = 0;
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				count += pixels[i][j];
			}
		}
		return count / (width*height);
	}

	/**
	 * 得到灰度值
	 * @param image
	 * @return
	 */
	public static double[][] getGrayValue(BufferedImage image) {
		int width = image.getWidth();  
	    int height = image.getHeight();  
		double[][] pixels = new double[height][width];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
	        	pixels[i][j] = computeGrayValue(image.getRGB(i, j)); 
	        }  
	    }  
		return pixels;
	}
	/**
	 * 计算灰度值
	 * @param pixels
	 * @return
	 */
	public static double computeGrayValue(int pixel) {
		int red = (pixel >> 16) & 0xFF;
		int green = (pixel >> 8) & 0xFF;
		int blue = (pixel) & 255;
		return  0.3 * red + 0.59 * green + 0.11 * blue;
	}
	
	/**
	 * 求一维的灰度直方图
	 * 
	 * @param img
	 * @return
	 */
	public static double[] getHistogram2(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		int series = (int) Math.pow(2, GRAYBIT); // GRAYBIT=4;用12位的int表示灰度值，前4位表示red,中间4们表示green,后面4位表示blue
		int greyScope = 256 / series;
		double[] hist = new double[series * series * series];
		int r, g, b, index;
		int pix[] = new int[w * h];
		pix = img.getRGB(0, 0, w, h, pix, 0, w);
		for (int i = 0; i < w * h; i++) {
			r = pix[i] >> 16 & 0xff;
			r = r / greyScope;
			g = pix[i] >> 8 & 0xff;
			g = g / greyScope;
			b = pix[i] & 0xff;
			b = b / greyScope;
			index = r << (2 * GRAYBIT) | g << GRAYBIT | b;
			hist[index]++;
		}
		for (int i = 0; i < hist.length; i++) {
			hist[i] = hist[i] / (w * h);
			// System.out.println(hist[i] + "  ");
		}
		return hist;
	}

	/**
	 * 用一维直方图求图像的相似度
	 * 
	 * @param n
	 * @param str1
	 * @param str2
	 * @throws IOException
	 */
	public static double indentification2(String srcPath, String destPath)
			throws IOException {
		BufferedImage srcImg = ImageIO.read(new File(srcPath));
		BufferedImage destImg = ImageIO.read(new File(destPath));
		double[] histR = getHistogram2(srcImg);
		double[] histD = getHistogram2(destImg);
		double p = (double) 0.0;
		for (int i = 0; i < histR.length; i++) {
			p += Math.sqrt(histR[i] * histD[i]);
		}
		return p;
	}
	

}
