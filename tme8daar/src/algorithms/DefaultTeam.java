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
		this.pointsNotInFvs = null;
	    Evaluation ev = new Evaluation();
	    ArrayList<Point> fvs = new ArrayList<Point>(); 
	    ArrayList<Point> bestFvs = new ArrayList<Point>(); 
	    for (int i = 0; i < 100; i++) {
	    	Collections.shuffle(points, new Random(System.nanoTime()));
	    	ArrayList<Point> tmpPoints = (ArrayList<Point>) points.clone();
		    while (!(ev.isValid(points, bestFvs, edgeThreshold))) {
		    	Point pointMost = mostConnectedPoint(tmpPoints, edgeThreshold);
		    	bestFvs.add(pointMost);
		    	removeElement(tmpPoints, pointMost);
		    }
		    if (i == 0) {
		    	fvs = (ArrayList<Point>) bestFvs.clone();
		    	pointsNotInFvs = null;
		    	pointsNotInFvs = tmpPoints;
		    } else if (bestFvs.size() < fvs.size()){
		    	fvs = (ArrayList<Point>) bestFvs.clone();
		    	pointsNotInFvs = null;
		    	pointsNotInFvs = tmpPoints;
		    }
		}
	    return fvs;
  }
  
  //--------------------------------------------------
  public ArrayList<PointWithWeight> setWeights(ArrayList<Point> points, int edgeThreshold){
	  Evaluation ev = new Evaluation();
	  ArrayList<PointWithWeight> result = new ArrayList<PointWithWeight>();
	  for (Point point : points) {
		int weight = ev.neighbor(point, points, edgeThreshold).size();
		PointWithWeight p = new PointWithWeight(point, weight);
		result.add(p);
	  }
	  return result;  
  } 
  
  public ArrayList<Point> feedback_2_approximation(ArrayList<Point> points, int edgeThreshold){
	  Evaluation ev = new Evaluation();
	  ArrayList<PointWithWeight> pointsW = setWeights(points, edgeThreshold);
	  ArrayList<PointWithWeight> fvs = new ArrayList<PointWithWeight>();
	  ArrayList<Point> points_ = (ArrayList<Point>) points.clone();
	  ArrayList<Point> fvs_ = new ArrayList<Point>();
	  for (PointWithWeight pointW : pointsW) {
		if (pointW.getWeight() == 0) {
			fvs.add(pointW);
			fvs_.add(pointW.getP());
			removeElementW(pointsW, pointW);
			removeElement(points_, pointW.getP());
		}
	  }
	  int i = 0;
	  while(pointsW.size() > 0) {
		  i++;
		  if (!(ev.isValid(points, fvs_, edgeThreshold))) {
			  
		  } else {
			  
		  }
	  }
	  return null;
  }
  
  //--------------------------------------------------

  public ArrayList<Point> remove2add1(ArrayList<Point> points, int edgeThreshold){
	Evaluation ev = new Evaluation();
	ArrayList<Point> bestFvs = glouton(points, edgeThreshold);
	for (int y = 0; y < 5; y++) {
		ArrayList<Point> naifFvs = glouton(points, edgeThreshold);
		ArrayList<Point> memPointsNotInFvs = (ArrayList<Point>) this.pointsNotInFvs.clone();
	    boolean fvsAmeliorer = false;
	    boolean finishedAllPossibilities = false;
    	Collections.shuffle(naifFvs, new Random(System.nanoTime()));
    	Collections.shuffle(this.pointsNotInFvs, new Random(System.nanoTime()));
	    while(!finishedAllPossibilities) {
    		ArrayList<Point> newFvs = (ArrayList<Point>) naifFvs.clone();
    		ArrayList<Point> newPointsNotInFvs = (ArrayList<Point>) memPointsNotInFvs.clone();
    		int j=0, k=1;
    		while(j < (naifFvs.size()-1) || k < naifFvs.size()) {
    				if (naifFvs.get(j) != naifFvs.get(k) && this.pointsNotInFvs != null) {
    					int i=0;
    					while(i < this.pointsNotInFvs.size()) {
    						ArrayList<Point> tmpFvs = (ArrayList<Point>) naifFvs.clone();
    						removeTwoElement(tmpFvs, naifFvs.get(j), naifFvs.get(k));
    						tmpFvs.add(this.pointsNotInFvs.get(i));
    						if (ev.isValid(points, tmpFvs, edgeThreshold)) {
	        					fvsAmeliorer = true;
	        					newFvs = (ArrayList<Point>) tmpFvs.clone();
	        					removeElement(newPointsNotInFvs, this.pointsNotInFvs.get(i));
	        					newPointsNotInFvs.add(naifFvs.get(j));
	        					newPointsNotInFvs.add(naifFvs.get(k));
	        					// to break
	        					i = this.pointsNotInFvs.size();
	        					j = naifFvs.size();
	        					k = naifFvs.size();
    						}
    						tmpFvs = null;
    						i++;
    					}
    				}
    			j++;k++;
    		}
	        if (naifFvs.size() == newFvs.size()) break;
	        naifFvs = (ArrayList<Point>) newFvs.clone();
	        this.pointsNotInFvs = (ArrayList<Point>) newPointsNotInFvs.clone();
	        if (!fvsAmeliorer) {
	        	finishedAllPossibilities = true;
	        }
	    }
	    	if (naifFvs.size() < bestFvs.size())
	    		bestFvs = (ArrayList<Point>) naifFvs.clone();
	    	System.out.println("size : "+ bestFvs.size());
	  }
	  return bestFvs;
  }
  
  public ArrayList<Point> calculFVS(ArrayList<Point> points, int edgeThreshold) {
		Evaluation ev = new Evaluation();
	    ArrayList<Point> fvs = new ArrayList<Point>();	    
	    fvs = remove2add1(points, edgeThreshold);
	    return fvs;	
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
  
  public ArrayList<Point> removeThreeElement(ArrayList<Point> points, Point p, Point h, Point x)
  {
	  int cpt = 0;
	  for (int i = points.size() - 1; i >= 0; i--) {
		    if (points.get(i) == p) {
		        points.remove(i);
		        cpt++;
		        if (cpt == 3) {
		        	i = -1; //break
		        }
		    } else if (points.get(i) == h) {
		        points.remove(h);
		        cpt++;
		        if (cpt == 3) {
		        	i = -1; //break
		        }
		    } else if (points.get(i) == x) {
		        points.remove(x);
		        cpt++;
		        if (cpt == 3) {
		        	i = -1; //break
		        }
		    }
		}
	  return points;
  }
  
  public ArrayList<PointWithWeight> removeElementW(ArrayList<PointWithWeight> points, PointWithWeight p)
  {
	  for (int i = points.size() - 1; i >= 0; i--) {
		    if (points.get(i) == p) {
		        points.remove(i);
		        i = -1; // break
		    }
		}
	  return points;
  }
}
