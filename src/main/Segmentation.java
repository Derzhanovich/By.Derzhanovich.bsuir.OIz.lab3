package main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Segmentation {
	private ArrayList<Point> centroid = new ArrayList<Point>();
	private ArrayList<Point> newCentroid;
	private int width;
	private int height;
	private int x;
	private int y;
	boolean changeCentroid = true;
	BufferedImage image;
	Point point;
	private int red;
	private int green;
	private int blue;
	private int lastRed;
	private int lastGreen;
	private int lastBlue;
	private int pixel;
	
	public Segmentation(BufferedImage image, int numberClaster) {
		width = image.getWidth();
		height = image.getHeight();
		
		this.image = image;
		
		for (int count = 0; count < numberClaster; count++) {
			x = (int) (Math.random() * image.getWidth());
			y = (int) (Math.random() * image.getHeight());
			
			int centoid = image.getRGB(x, y);
			int redCentroid = (centoid >> 16) & 0xff;
			int greenCentoid = (centoid >> 8) & 0xff;
			int blueCentoid = centoid & 0xff;

			point = new Point(x, y);
			point.setRed(redCentroid);
			point.setBlue(blueCentoid);
			point.setGreen(greenCentoid);
			centroid.add(point);
		}
	}

	public BufferedImage bind() {
		int claster = 0;
		if (changeCentroid) {
			for (int countX = 0; countX < width; countX++) {
				for (int countY = 0; countY < height; countY++) {
					pixel = image.getRGB(countX, countY);

					int redPixel = (pixel >> 16) & 0xff;
					int greenPixel = (pixel >> 8) & 0xff;
					int bluePixel = pixel & 0xff;

					int min = Integer.MAX_VALUE;;
					
					for (int countCentroid = 0; countCentroid < centroid.size(); countCentroid++) {
						int redCentroid = centroid.get(countCentroid).getRed();
						int greenCentoid = centroid.get(countCentroid).getGreen();
						int blueCentoid = centroid.get(countCentroid).getBlue();

						int minRed = Math.abs(redCentroid - redPixel);
						int minGreen = Math.abs(greenCentoid - greenPixel);
						int minBlue = Math.abs(blueCentoid - bluePixel);

						int temp = minRed + minGreen + minBlue;

						if (min > temp) {
							min = temp;
							claster = countCentroid;
						}
					}
					centroid.get(claster).setPixel(countX, countY);
				}
			}
			newCentroid();
		}

		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int countCentroid = 0; countCentroid < centroid.size(); countCentroid++) {
			for (int countPixel = 0; countPixel < centroid.get(countCentroid).getListPixel().size(); countPixel++) {
				Point pointPixel = centroid.get(countCentroid).getListPixel().get(countPixel);
				centroid.get(countCentroid).getRed();
				int colorPixel = (centroid.get(countCentroid).getRed() << 16)
						| (centroid.get(countCentroid).getGreen() << 8)
						| centroid.get(countCentroid).getBlue();
				newImage.setRGB(pointPixel.getX(), pointPixel.getY(), colorPixel);
			}
		}
		return newImage;
	}

	public void newCentroid() {
		int sumRed = 0;
		int sumGreen = 0;
		int sumBlue = 0;
		int newRed = 0;
		int newGreen = 0;
		int newBlue = 0;
		
		newCentroid = new ArrayList<Point>();
		
		for (int countCentroid = 0; countCentroid < centroid.size(); countCentroid++) {
			for (int countPixel = 0; countPixel < centroid.get(countCentroid).getListPixel().size(); countPixel++) {
				Point pointPixel = centroid.get(countCentroid).getListPixel().get(countPixel);
				
				pixel = image.getRGB(pointPixel.getX(), pointPixel.getY());

				int redPixel = (pixel >> 16) & 0xff;
				int greenPixel = (pixel >> 8) & 0xff;
				int bluePixel = pixel & 0xff;

				sumRed += redPixel;
				sumGreen += greenPixel;
				sumBlue += bluePixel;
			}
			
			newRed = sumRed / centroid.get(countCentroid).getListPixel().size();
			newGreen = sumGreen	/ centroid.get(countCentroid).getListPixel().size();
			newBlue = sumBlue / centroid.get(countCentroid).getListPixel().size();

			Point point = new Point(0, 0);
			point.setRed(newRed);
			point.setGreen(newGreen);
			point.setBlue(newBlue);
			newCentroid.add(point);

			sumRed = 0;
			sumGreen = 0;
			sumBlue = 0;
		}

		int check = 0;
		changeCentroid = false;
		for (int count = 0; count < newCentroid.size(); count++) {
			red = newCentroid.get(count).getRed();
			green = newCentroid.get(count).getGreen();
			blue = newCentroid.get(count).getBlue();
			
			for (int count1 = 0; count1 < centroid.size(); count1++) {
				lastRed = centroid.get(count1).getRed();
				lastGreen = centroid.get(count1).getGreen();
				lastBlue = centroid.get(count1).getBlue();

				if (red != lastRed || green != lastGreen || blue != lastBlue) {
					check++;
				}
			}

			if (check == centroid.size()) {
				changeCentroid = true;
				centroid.clear();
				centroid.addAll(newCentroid);
			}
		}
		bind();
	}
}