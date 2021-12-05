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
	    ArrayList<Point> bestFvs = new ArrayList<Point>();
	    for (int i = 0; i < 10; i++) {
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
		    Collections.shuffle(points, new Random(System.nanoTime()));
		}
	  
	 // remove 1 add 0 ****************************
	    boolean fvsAmeliorer = false;
	    boolean finishedAllPossibilities = false;
	    while (!finishedAllPossibilities) {
    		ArrayList<Point> newFvs = fvs;
    		ArrayList<Point> newPointsNotInFvs = this.pointsNotInFvs;
	        for (int j = 0; j < fvs.size(); j++) {
	          //  for (int k = 1; k < fvs.size(); k++) {
	        		if (this.pointsNotInFvs != null) {
	        			for (int i = 0; i < this.pointsNotInFvs.size(); i++) {
	        				ArrayList<Point> tmpFvs = (ArrayList<Point>) fvs.clone(); 
	        				removeElement(tmpFvs, fvs.get(j));
	        				tmpFvs.add(this.pointsNotInFvs.get(i));
	        				if (ev.isValid(points, tmpFvs, edgeThreshold)) {
	        					fvsAmeliorer = true;
	        					newFvs = tmpFvs;
	        					removeElement(newPointsNotInFvs, this.pointsNotInFvs.get(i));
	        					newPointsNotInFvs.add(fvs.get(j));
	        				//	newPointsNotInFvs.add(fvs.get(k));
	        					// to break
	        					i = this.pointsNotInFvs.size();
	        					j = fvs.size();
	        				//	k = fvs.size();
	        				}
	        				tmpFvs = null;
	    				}
	        		}
	        //	}
	    	}
	        if (fvs.size() == newFvs.size()) break;
	        fvs = newFvs;
	        this.pointsNotInFvs = newPointsNotInFvs;
	        if (!fvsAmeliorer) {
	        	finishedAllPossibilities = true;
	        }
	       // System.out.println("glouton fvs size : "+ fvs.size());
	    }
	   // System.out.println("2 fin glouton"+ fvs.size()); 
	    //-------------------------------------		
	    
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
  
  public ArrayList<Point> calculFVS(ArrayList<Point> points, int edgeThreshold) {
	try {int cpt = 0;
		Evaluation ev = new Evaluation();
	    ArrayList<Point> fvs = new ArrayList<Point>();
	    
	    // first non optimal solution
	   // System.out.println("################");
	    fvs = glouton(points, edgeThreshold);
	   // System.out.println("################2");
	    ArrayList<Point> fvs1 = (ArrayList<Point>) fvs.clone();
	    ArrayList<Point> fvsmem = (ArrayList<Point>) fvs.clone();
	    ArrayList<Point> memPointsNotInFvs = (ArrayList<Point>) this.pointsNotInFvs.clone();
	    
	    ArrayList<Point> bestFvs = new ArrayList<Point>();
	    //-----------------------------------------------------------
	    //-------------------------------------------------------
	    
	    
	    int loopT = 5;
	    
	    for (int y = 0; y < loopT; y++) {
	    	fvs1 = (ArrayList<Point>) fvsmem.clone();
	    	ArrayList<Point> pointsNotInFvs1 = (ArrayList<Point>) this.pointsNotInFvs.clone();
	    	Collections.shuffle(fvs1, new Random(System.nanoTime()));
	    	Collections.shuffle(this.pointsNotInFvs, new Random(System.nanoTime()));
	    	ArrayList<Point> fvs12 = (ArrayList<Point>) fvs1.clone();
	    	ArrayList<Point> pointsNotInFvs2 = (ArrayList<Point>) this.pointsNotInFvs.clone();

		    //-----------------------------------------------------
		     boolean fvsAmeliorer = false;
		     boolean finishedAllPossibilities = false;
		    while (!finishedAllPossibilities) {
	    		ArrayList<Point> newFvs = (ArrayList<Point>) fvs1.clone();
	    		ArrayList<Point> newPointsNotInFvs = (ArrayList<Point>) memPointsNotInFvs.clone();
		        for (int j = 0; j < (fvs1.size()-1); j++) {
		            for (int k = 1; k < fvs1.size(); k++) {
		        		if (fvs1.get(j) != fvs1.get(k) && this.pointsNotInFvs != null) {
		        			for (int i = 0; i < this.pointsNotInFvs.size(); i++) {
		        				ArrayList<Point> tmpFvs = (ArrayList<Point>) fvs1.clone(); 
		        				removeTwoElement(tmpFvs, fvs1.get(j), fvs1.get(k));
		        				tmpFvs.add(this.pointsNotInFvs.get(i));
		        				if (ev.isValid(points, tmpFvs, edgeThreshold)) {
		        					fvsAmeliorer = true;
		        					newFvs = tmpFvs;
		        					removeElement(newPointsNotInFvs, this.pointsNotInFvs.get(i));
		        					newPointsNotInFvs.add(fvs1.get(j));
		        					newPointsNotInFvs.add(fvs1.get(k));
		        					// to break
		        					i = this.pointsNotInFvs.size();
		        					j = fvs.size();
		        					k = fvs.size();
		        				}
		        				tmpFvs = null;
		    				}
		        		}
		        	}
		    	}
		        if (fvs1.size() == newFvs.size()) break;
		        fvs1 = newFvs;
		        pointsNotInFvs1 = newPointsNotInFvs;
		        if (!fvsAmeliorer) {
		        	finishedAllPossibilities = true;
		        }
		    }
		        System.out.println("fvs1 size : "+ fvs1.size());
		        		        
		        fvsAmeliorer = false;
			    finishedAllPossibilities = false;
			    while (!finishedAllPossibilities) {
		    		ArrayList<Point> newFvs2 = (ArrayList<Point>) fvs12.clone();
		    		ArrayList<Point> newPointsNotInFvs2 = (ArrayList<Point>) memPointsNotInFvs.clone();
			        for (int j = (fvs12.size()-2); j >= 0; j--) {
			            for (int k = (fvs12.size()-1); k >= 1; k--) {
			        		if (fvs12.get(j) != fvs12.get(k) && this.pointsNotInFvs != null) {
			        			for (int i = 0; i < this.pointsNotInFvs.size(); i++) {
			        				ArrayList<Point> tmpFvs = (ArrayList<Point>) fvs12.clone(); 
			        				removeTwoElement(tmpFvs, fvs12.get(j), fvs12.get(k));
			        				tmpFvs.add(this.pointsNotInFvs.get(i));
			        				if (ev.isValid(points, tmpFvs, edgeThreshold)) {
			        					fvsAmeliorer = true;
			        					newFvs2 = tmpFvs;
			        					removeElement(newPointsNotInFvs2, this.pointsNotInFvs.get(i));
			        					newPointsNotInFvs2.add(fvs12.get(j));
			        					newPointsNotInFvs2.add(fvs12.get(k));
			        					// to break
			        					i = this.pointsNotInFvs.size();
			        					j = -1;
			        					k = -1;
			        				}
			        				tmpFvs = null;
			    				}
			        		}
			        	}
			    	}
			        if (fvs12.size() == newFvs2.size()) break;
			        fvs12 = newFvs2;
			        pointsNotInFvs2 = newPointsNotInFvs2;
			        if (!fvsAmeliorer) {
			        	finishedAllPossibilities = true;
			        }
			    
			        
		    }//___________________________________
			    System.out.println("fvs12 size : "+ fvs12.size());
		    //-------------------------------------		
		    if (y == 0) {
		    	if (fvs1.size() < fvs12.size())
		    		bestFvs = (ArrayList<Point>) fvs1.clone();
		    	else 
		    		bestFvs = (ArrayList<Point>) fvs12.clone();
		    	
		    } else if (fvs1.size() < bestFvs.size()){
		    	if (fvs1.size() < fvs12.size())
		    		bestFvs = (ArrayList<Point>) fvs1.clone();
		    	else 
		    		bestFvs = (ArrayList<Point>) fvs12.clone();
		    }
		    System.out.println("best : "+ bestFvs.size());
		    if (bestFvs.size() <= 79) y = loopT;
		    if ((y == (loopT-1)) && bestFvs.size()>= 85 ) loopT++;
		   // System.out.println("size 2 : "+ fvs12.size());
		   // Collections.shuffle(points, new Random(System.nanoTime()));
		}


	    
	    return bestFvs;	
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
