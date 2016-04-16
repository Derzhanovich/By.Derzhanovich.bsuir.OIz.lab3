package main;

import java.util.ArrayList;

public class Point {
	
	private ArrayList<Point> pixels = new ArrayList<Point>();

	private int x;
	private int y;
	private int red;
	private int green;
	private int blue;
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void getY(int y){
		this.y = y;
	}
	
	public void setRed(int red){
		this.red = red;
	}
	
	public void setGreen(int green){
		this.green = green;
	}
	
	public void setBlue(int blue){
		this.blue = blue;
	}
	
	public int getRed(){
		return red;
	}
	
	public int getGreen(){
		return green;
	}
	
	public int getBlue(){
		return blue;
	}
	
	public ArrayList<Point> getListPixel(){
		return pixels;
	}
	
	public void setPixel(int x, int y){
		Point pixel = new Point(x,y);
		pixels.add(pixel);
	}
}
