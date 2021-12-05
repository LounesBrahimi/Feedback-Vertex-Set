package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DefaultTeam {

  /*
   * Membre indiquant l'ensemble de points qui ne sont pas inclus dans le fvs
   */
  private ArrayList<Point> pointsNotInFvs = new ArrayList<Point>();

  /*
   * Methode principale
   */
  public ArrayList<Point> calculFVS(ArrayList<Point> points, int edgeThreshold) {
	return remove2add1(points, edgeThreshold);	
  }
  
  /*
   *  Methode qui trouve le point du graphe avec le maximum de voisins
   */
  private Point mostConnectedPoint(ArrayList<Point> points, int edgeThreshold){
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
  
  /*
   * Methode qui implemente un algorithme naif pour calculer le fvs en supprimant les points
   * avec le plus grand nombre de voisins
   * */
  private ArrayList<Point> glouton(ArrayList<Point> points, int edgeThreshold){
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

  /*
   * Methode qui recupere un fvs primaire genere grace à la methode glouton
   * puis supprime 2 points de ce dernier le remplace par un point non inclu pour tester si
   * le fvs est toujours valide, l'ensemble du fvs est randomiser à chaque tour de boucle
   * pour inclure une notion d'alea pour avoir de nouveaux resultats peutetre meilleurs
   * que les anciens, l'algorithme prend biensur le meilleur resultat
   * */
  private ArrayList<Point> remove2add1(ArrayList<Point> points, int edgeThreshold) {
	  Evaluation ev = new Evaluation();
	    ArrayList<Point> fvsW = new ArrayList<Point>();
	    ArrayList<Point> fvsF = new ArrayList<Point>();
	    ArrayList<Point> firstSolution = glouton(points, edgeThreshold);
	    
	    fvsF = (ArrayList<Point>) firstSolution.clone();
	    
	    for (int y = 0; y < 4; y++) {
	    	fvsW = (ArrayList<Point>) firstSolution.clone();
			Collections.shuffle(fvsW, new Random(System.nanoTime()));
		    boolean fvsAmeliorer = false;
		    boolean finishedAllPossibilities = false;
		    while (!finishedAllPossibilities) {
	    		ArrayList<Point> newFvs = fvsW;
	    		ArrayList<Point> newPointsNotInFvs = this.pointsNotInFvs;
		        for (int j = 0; j < (fvsW.size()-1); j++) {
		            for (int k = 1; k < fvsW.size(); k++) {
		        		if (fvsW.get(j) != fvsW.get(k) && this.pointsNotInFvs != null) {
		        			for (int i = 0; i < this.pointsNotInFvs.size(); i++) {
		        				ArrayList<Point> tmpFvs = (ArrayList<Point>) fvsW.clone(); 
		        				removeTwoElement(tmpFvs, fvsW.get(j), fvsW.get(k));
		        				tmpFvs.add(this.pointsNotInFvs.get(i));
		        				if (ev.isValid(points, tmpFvs, edgeThreshold)) {
		        					fvsAmeliorer = true;
		        					newFvs = tmpFvs;
		        					removeElement(newPointsNotInFvs, this.pointsNotInFvs.get(i));
		        					newPointsNotInFvs.add(fvsW.get(j));
		        					newPointsNotInFvs.add(fvsW.get(k));
		        					// to break
		        					i = this.pointsNotInFvs.size();
		        					j = fvsW.size();
		        					k = fvsW.size();
		        				}
		    				}
		        		}
		        	}
		    	}
		        if (fvsW.size() == newFvs.size()) break;
		        fvsW = newFvs;
		        this.pointsNotInFvs = newPointsNotInFvs;
		        if (!fvsAmeliorer) {
		        	finishedAllPossibilities = true;
		        }
		    }
		   if (fvsW.size() < fvsF.size()) {
			   fvsF = (ArrayList<Point>) fvsW.clone();
		   }
		}
	    return fvsF;		  
  }
    
  /*
   * Methode permettant de suprimmer un point d'un ensemble de points
   * */
  private ArrayList<Point> removeElement(ArrayList<Point> points, Point p)
  {
	  for (int i = points.size() - 1; i >= 0; i--) {
		    if (points.get(i) == p) {
		        points.remove(i);
		        i = -1; // break
		    }
		}
	  return points;
  }
  
  /*
   * Methode qui supprime deux points d'un ensemble de points
   */
  private ArrayList<Point> removeTwoElement(ArrayList<Point> points, Point p, Point h)
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