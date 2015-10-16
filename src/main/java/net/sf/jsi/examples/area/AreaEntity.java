package net.sf.jsi.examples.area;

import java.math.BigDecimal;

import net.sf.jsi.Rectangle;

import com.google.common.base.Strings;


public class AreaEntity {
	
	private String city;
	private String name;
	private String coords;
		
	public AreaEntity() {
		
	}
	
	public AreaEntity(String city, String name, String coords) {
		this.city = city;
		this.name = name;
		this.coords = coords;
		
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoords() {
		return coords;
	}

	public void setCoords(String coords) {
		this.coords = coords;
	}
	
	public Rectangle mbr() {
		if(Strings.isNullOrEmpty(coords)) {
			return null;
		}
		
		double minX = 18100000f, minY = 91f, maxX = -1f, maxY = -1f;
		String[] coordArray = coords.split(",");
		for(String coord : coordArray) {
			String[] xyArray = coord.split(" ");
			double x = Double.valueOf(xyArray[0]).doubleValue();
			double y = Double.valueOf(xyArray[1]).doubleValue();
			if(x < minX) {
				minX = x;
			}
			if(x > maxX) {
				maxX = x;
			}
			if(y < minY) {
				minY = y;
			}
			if(y > maxY) {
				maxY = y;
			}
		}
		
		return new Rectangle(minFloat(minX), minFloat(minY), maxFloat(maxX), maxFloat(maxY));
	}
	
	private float minFloat(double minValue) {
		String v = String.valueOf(minValue);
		int pIndex = v.indexOf(".");
		v = v.replaceAll("\\.", "");
		if(v.length() <= 8) {
			return (float)minValue;
		}
		v = v.substring(0, 8);
		if(pIndex > 0) {
			v = v.substring(0, pIndex) + "." + v.substring(pIndex);
			return Float.valueOf(v).floatValue();
		}
		else {
			return Float.valueOf(v).floatValue();
		}
	}
	
	private float maxFloat(double maxValue) {
		String v = String.valueOf(maxValue);
		int pIndex = v.indexOf(".");
		v = v.replaceAll("\\.", "");
		if(v.length() <= 8) {
			return (float)maxValue;
		}
		int eight = Integer.valueOf(v.substring(7, 8)).intValue();
		int nineV = Integer.valueOf(v.substring(8, 9)).intValue();
		if(nineV > 0) {
			eight++;
		}
		v = v.substring(0, 7) + eight;
		if(pIndex > 0) {
			v = v.substring(0, pIndex) + "." + v.substring(pIndex);
			return Float.valueOf(v).floatValue();
		}
		else {
			return Float.valueOf(v).floatValue();
		}
	}
	
	public boolean isPoinIn(double x, double y) {
		if(coords.contains(x + " " + y)) {
			return true;
		}
		
		Ray ray = new Ray();
		ray.x = x;
		ray.y = y;
		
		String[] coordArray = coords.split(",");
		String[] baseXY = coordArray[0].split(" ");
		double firstX =  Double.valueOf(baseXY[0]).doubleValue();
		double baseX = firstX;
		double firstY =  Double.valueOf(baseXY[1]).doubleValue();
		double baseY = firstY;
		int intersectCount = 0;
		for(int i = 1; i < coordArray.length; i++) {
			String[] xy = coordArray[i].split(" ");
			double x1 = Double.valueOf(xy[0]).doubleValue();
			double y1 = Double.valueOf(xy[1]).doubleValue();
			Line line = new Line(x1, y1, baseX, baseY);
			if(line.isIntersect(ray)) {
				intersectCount++;
			}
			baseX = x1;
			baseY = y1;			
		}
		
		return intersectCount % 2 == 1;
	}
	
	private static class Ray {
		private double x;
		private double y;
		
		public boolean isIntersect(double x, double y) {
			return x >= this.x && y == this.y;
		}
	}
	
	private static class Line {
		private double k;
		private double b;
		
		public Line(double x1, double y1, double x2, double y2) {
			k = (y1 - y2) / (x1 - x2);
			b = y1 - k * x1;
		}
		
		public boolean isIntersect(Ray ray) {
			double rayY = ray.y;
			double lineX = (rayY - b) / k;
			return ray.isIntersect(lineX, rayY);
		}
	}
}
