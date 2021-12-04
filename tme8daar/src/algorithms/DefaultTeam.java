package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DefaultTeam {

	private ArrayList<Point> pointsNotInFvs = new ArrayList<Point>();
	
  public Point mostConnectedPoint(ArrayList<Point> points, int edgeThreshold){
	  Evaluation ev = new Evaluation();
	  int numberOfNeighbor = -1;
	  Point pointMost = null;
	  for (Point point : points) {
		if (ev.neighbor(point, points, edgeThreshold).size() > numberOfNeighbor) {
			numberOfNeighbor = ev.neighbor(point, points, edgeThreshold).size();
			pointMost = point;
		}
	  }
	  return pointMost;
  }
	
  public ArrayList<Point> glouton(ArrayList<Point> points, int edgeThreshold){
		Evaluation ev = new Evaluation();
	    ArrayList<Point> fvs = new ArrayList<Point>();
	    ArrayList<Point> tmpPoints = (ArrayList<Point>) points.clone();
	    
	    while (!(ev.isValid(points, fvs, edgeThreshold))) {
	    	Point pointMost = mostConnectedPoint(tmpPoints, edgeThreshold);
	    	fvs.add(pointMost);
	    	removeElement(tmpPoints, pointMost);
	    }
	    pointsNotInFvs = tmpPoints;
	    return fvs;
  }
  
  public ArrayList<Point> calculFVS(ArrayList<Point> points, int edgeThreshold) {
	try {int cpt = 0;
		Evaluation ev = new Evaluation();
	    ArrayList<Point> fvs = new ArrayList<Point>();
	    
	    // first non optimal solution
	    fvs = glouton(points, edgeThreshold);
	    
	    boolean fvsAmeliorer = false;
	    boolean finishedAllPossibilities = false;
	    while (!finishedAllPossibilities) {
    		ArrayList<Point> newFvs = fvs;
    		ArrayList<Point> newPointsNotInFvs = this.pointsNotInFvs;
	    	//outerloop:
	        for (int j = 0; j < (fvs.size()-1); j++) {
	            for (int k = 1; k < fvs.size(); k++) {
	        		if (fvs.get(j) != fvs.get(k) && this.pointsNotInFvs != null) {
	        			for (int i = 0; i < this.pointsNotInFvs.size(); i++) {
	        				ArrayList<Point> tmpFvs = (ArrayList<Point>) fvs.clone(); 
	        			//	removeElement(tmpFvs, fvs.get(j));
	        			//	removeElement(tmpFvs, fvs.get(k));
	        				removeTwoElement(tmpFvs, fvs.get(j), fvs.get(k));
	        				tmpFvs.add(this.pointsNotInFvs.get(i));
	        				if (ev.isValid(points, tmpFvs, edgeThreshold)) {
	        					fvsAmeliorer = true;
	        					newFvs = tmpFvs;
	        					removeElement(newPointsNotInFvs, this.pointsNotInFvs.get(i));
	        					newPointsNotInFvs.add(fvs.get(j));
	        					newPointsNotInFvs.add(fvs.get(k));
	        					// to break
	        					i = this.pointsNotInFvs.size();
	        					j = fvs.size();
	        					k = fvs.size();
	        				//	break outerloop;
	        				}
	    				}
	        		}
	        	/*	if(fvsAmeliorer) {
	        			break;
	        		}*/
	        	}
	    		/*if(fvsAmeliorer) {
	    			break;
	    		}*/
	    	}
	        if (fvs.size() == newFvs.size()) break;
	        fvs = newFvs;
	        this.pointsNotInFvs = newPointsNotInFvs;
	        if (!fvsAmeliorer) {
	        	finishedAllPossibilities = true;
	        }
	        System.out.println("fvs size : "+ fvs.size());
	    }
	    return fvs;	
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
  }
  
  public ArrayList<Point> removeElement(ArrayList<Point> points, Point p)
  {
	  for (int i = points.size() - 1; i >= 0; i--) {
		    if (points.get(i) == p) {
		        points.remove(i);
		        i = -1; // break
		    }
		}
	  return points;
  }
  
  public ArrayList<Point> removeTwoElement(ArrayList<Point> points, Point p, Point h)
  {
	  int cpt = 0;
	  for (int i = points.size() - 1; i >= 0; i--) {
		    if (points.get(i) == p) {
		        points.remove(i);
		        cpt++;
		        if (cpt == 2) {
		        	i = -1; //break
		        }
		    } else if (points.get(i) == h) {
		        points.remove(h);
		        cpt++;
		        if (cpt == 2) {
		        	i = -1; //break
		        }
		    }
		}
	  return points;
  }
}
