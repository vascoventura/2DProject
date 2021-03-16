package java2dproject;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Form implements Shape{
	
	GeneralPath path;
	String name;
	Color color;
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Form(String name, float x, float y) {
		if(name == "t") {
			this.name = name;
			path = new GeneralPath();
			float x0 = x;
		    float y0 = y;
		    float x1 = x + 30f;
		    float y1 = y;
		    float x2 = x + 30f;
		    float y2 = y + 10f;
		    float x3 = x + 20f;
		    float y3 = y + 10f;
		    float x4 = x + 20f;
		    float y4 = y + 20f;
		    float x5 = x + 10f;
		    float y5 = y + 20f;
		    float x6 = x + 10f;
		    float y6 = y + 10f;
		    float x7 = x;
		    float y7 = y + 10f;
		    path.moveTo(x0, y0);
		    path.lineTo(x1, y1);
		    path.lineTo(x2, y2);
		    path.lineTo(x3, y3);
		    path.lineTo(x4, y4);
		    path.lineTo(x5, y5);
		    path.lineTo(x6, y6);
		    path.lineTo(x7, y7);
		    path.lineTo(x0, y0);			
		} else if (name == "s") {
			this.name = name;
			path = new GeneralPath();
			float x0 = x;
		    float y0 = y;
		    float x1 = x + 10f;
		    float y1 = y;
		    float x2 = x + 10f;
		    float y2 = y + 10f;
		    float x3 = x + 20f;
		    float y3 = y + 10f;
		    float x4 = x + 20f;
		    float y4 = y + 30f;
		    float x5 = x + 10f;
		    float y5 = y + 30f;
		    float x6 = x + 10f;
		    float y6 = y + 20f;
		    float x7 = x;
		    float y7 = y + 20f;
		    path.moveTo(x0, y0);
		    path.lineTo(x1, y1);
		    path.lineTo(x2, y2);
		    path.lineTo(x3, y3);
		    path.lineTo(x4, y4);
		    path.lineTo(x5, y5);
		    path.lineTo(x6, y6);
		    path.lineTo(x7, y7);
		    path.lineTo(x0, y0);			
		} else if (name == "o") {
			this.name = name;
			path = new GeneralPath();
			float x0 = x;
		    float y0 = y;
		    float x1 = x + 20f;
		    float y1 = y;
		    float x2 = x + 20f;
		    float y2 = y + 20f;
		    float x3 = x;
		    float y3 = y + 20f;
		    path.moveTo(x0, y0);
		    path.lineTo(x1, y1);
		    path.lineTo(x2, y2);
		    path.lineTo(x3, y3);
		    path.lineTo(x0, y0);			
		} else if (name == "l") {
			this.name = name;
			path = new GeneralPath();
			float x0 = x;
		    float y0 = y;
		    float x1 = x + 10f;
		    float y1 = y;
		    float x2 = x + 10f;
		    float y2 = y + 20f;
		    float x3 = x + 20f;
		    float y3 = y + 20f;
		    float x4 = x + 20f;
		    float y4 = y + 30f;
		    float x5 = x;
		    float y5 = y + 30f;
		    path.moveTo(x0, y0);
		    path.lineTo(x1, y1);
		    path.lineTo(x2, y2);
		    path.lineTo(x3, y3);
		    path.lineTo(x4, y4);
		    path.lineTo(x5, y5);
		    path.lineTo(x0, y0);			
		} else if (name == "i") {
			this.name = name;
			path = new GeneralPath();
			float x0 = x;
		    float y0 = y;
		    float x1 = x + 40f;
		    float y1 = y;
		    float x2 = x + 40f;
		    float y2 = y + 10f;
		    float x3 = x;
		    float y3 = y + 10f;
		    path.moveTo(x0, y0);
		    path.lineTo(x1, y1);
		    path.lineTo(x2, y2);
		    path.lineTo(x3, y3);
		    path.lineTo(x0, y0);			
		} else if (name == "x") {
			this.name = name;
			path = new GeneralPath();
			float x0 = x;
		    float y0 = y;
		    float x1 = x+10f;
		    float y1 = y;
		    float x2 = x + 15f;
		    float y2 = y + 13f;
		    float x3 = x + 20f;
		    float y3 = y;
		    float x4 = x + 30f;
		    float y4 = y;
		    float x5 = x+ 17f;
		    float y5 = y + 15f;
		    float x6 = x + 30f;
		    float y6 = y + 30f;
		    float x7 = x + 20f;
		    float y7 = y + 30f;
		    float x8 = x + 15f;
		    float y8 = y + 17f;
		    float x9 = x+10f;
		    float y9 = y+30f;
		    float x10 = x;
		    float y10 = y + 30f;
		    float x11 = x + 13f;
		    float y11 = y + 15f;
		    
		    path.moveTo(x0, y0);
		    path.lineTo(x1, y1);
		    path.lineTo(x2, y2);
		    path.lineTo(x3, y3);
		    path.lineTo(x4, y4);
		    path.lineTo(x5, y5);
		    path.lineTo(x6, y6);
		    path.lineTo(x7, y7);
		    path.lineTo(x8, y8);
		    path.lineTo(x9, y9);
		    path.lineTo(x10, y10);
		    path.lineTo(x11, y11);
		    path.lineTo(x0, y0);
		    
		} else if (name == "v") {
			this.name = name;
			path = new GeneralPath();
			float x0 = x;
		    float y0 = y;
		    float x1 = x + 10f;
		    float y1 = y;
		    float x2 = x + 20f;
		    float y2 = y + 20f;
		    float x3 = x + 45f;
		    float y3 = y - 7f;
		    float x4 = x + 55f;
		    float y4 = y - 7f;
		    float x5 = x + 20f;
		    float y5 = y + 30f;
		    path.moveTo(x0, y0);
		    path.lineTo(x1, y1);
		    path.lineTo(x2, y2);
		    path.lineTo(x3, y3);
		    path.lineTo(x4, y4);
		    path.lineTo(x5, y5);
		    path.lineTo(x0, y0);			
		}
	}

	public boolean contains(Rectangle2D rect) {
	    return path.contains(rect);
	  }

	  public boolean contains(Point2D point) {
	    return path.contains(point);
	  }

	  public boolean contains(double x, double y) {
	    return path.contains(x, y);
	  }

	  public boolean contains(double x, double y, double w, double h) {
	    return path.contains(x, y, w, h);
	  }

	  public Rectangle getBounds() {
	    return path.getBounds();
	  }

	  public Rectangle2D getBounds2D() {
	    return path.getBounds2D();
	  }

	  public PathIterator getPathIterator(AffineTransform at) {
	    return path.getPathIterator(at);
	  }

	  public PathIterator getPathIterator(AffineTransform at, double flatness) {
	    return path.getPathIterator(at, flatness);
	  }

	  public boolean intersects(Rectangle2D rect) {
	    return path.intersects(rect);
	  }

	  public boolean intersects(double x, double y, double w, double h) {
	    return path.intersects(x, y, w, h);
	  }
}