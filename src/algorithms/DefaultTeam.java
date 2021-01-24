package algorithms;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/***************************************************************
 * TME 1: calcul de diamètre et de cercle couvrant minimum.    *
 *   - Trouver deux points les plus éloignés d'un ensemble de  *
 *     points donné en entrée.                                 *
 *   - Couvrir l'ensemble de poitns donné en entrée par un     *
 *     cercle de rayon minimum.                                *
 *                                                             *
 * class Circle:                                               *
 *   - Circle(Point c, int r) constructs a new circle          *
 *     centered at c with radius r.                            *
 *   - Point getCenter() returns the center point.             *
 *   - int getRadius() returns the circle radius.              *
 *                                                             *
 * class Line:                                                 *
 *   - Line(Point p, Point q) constructs a new line            *
 *     starting at p ending at q.                              *
 *   - Point getP() returns one of the two end points.         *
 *   - Point getQ() returns the other end point.               *
 ***************************************************************/
import supportGUI.Circle;
import supportGUI.Line;

public class DefaultTeam {

    // calculDiametre: ArrayList<Point> --> Line
    //   renvoie une paire de points de la liste, de distance maximum.
    public Line calculDiametre(ArrayList<Point> points) {
        if (points.size() < 3) {
            return null;
        }

        Point p = points.get(0);
        Point q = points.get(1);

        /*******************
         * PARTIE A ECRIRE *
         *******************/

        return new Line(p, q);
    }

    // calculCercleMin: ArrayList<Point> --> Circle
    //   renvoie un cercle couvrant tout point de la liste, de rayon minimum.
    public Circle calculCercleMin(ArrayList<Point> points) {

//        Circle circle = welzl(points, new ArrayList<Point>());
        Circle circle = calculCercleMinNaif(points);
        return circle;

    }

    public Circle naifCercleMin(ArrayList<Point> points) {
        if (points.size() < 1) return null;
        ArrayList<Point> rest = (ArrayList<Point>) points.clone();
        Point dummy = rest.get(0);
        Point p = dummy;
        for (Point s : rest) if (dummy.distance(s) > dummy.distance(p)) p = s;
        Point q = p;
        for (Point s : rest) if (p.distance(s) > p.distance(q)) q = s;
        double cX = .5 * (p.x + q.x);
        double cY = .5 * (p.y + q.y);
        double cRadius = .5 * p.distance(q);
        rest.remove(p);
        rest.remove(q);
        while (!rest.isEmpty()) {
            Point s = rest.remove(0);
            double distanceFromCToS = Math.sqrt((s.x - cX) * (s.x - cX) + (s.y - cY) * (s.y - cY));
            if (distanceFromCToS <= cRadius) continue;
            double cPrimeRadius = .5 * (cRadius + distanceFromCToS);
            double alpha = cPrimeRadius / (double) (distanceFromCToS);
            double beta = (distanceFromCToS - cPrimeRadius) / (double) (distanceFromCToS);
            double cPrimeX = alpha * cX + beta * s.x;
            double cPrimeY = alpha * cY + beta * s.y;
            cRadius = cPrimeRadius;
            cX = cPrimeX;
            cY = cPrimeY;
        }

        Circle circle = new Circle(new Point((int) cX, (int) cY), (int) cRadius);
        return circle;
//        if (points.isEmpty()) {
//            return null;
//        }
//        Circle c = null;
//        Circle cc = null;
//        int diam = Integer.MAX_VALUE;
//        for (Point p : points) {
//            for (Point q : points) {
//                if (p != null && q != null && !p.equals(q)) {
//                    cc = new Circle(new Point(((p.x + q.x) / 2),
//                            ((p.y + q.y) / 2)), ((int) (p.distance(q)) / 2));
//                    if (allIsInCircle(cc, points) && cc.getRadius() < diam) {
//                        diam = cc.getRadius();
//                        c = cc;
//                    }
//                }
//            }
//        }
//        return c;
    }


    private ArrayList<Point> exercice1(ArrayList<Point> points) {
        if (points.size() < 4) return points;

        ArrayList<Point> enveloppe = new ArrayList<>();

        for (Point p : points) {
            for (Point q : points) {
                if (p.equals(q)) continue;
                Point ref = p;
                for (Point r : points) {
                    if (crossProduct(p, q, p, r) != 0) {
                        ref = r;
                        break;
                    }
                }
                if (ref.equals(p)) {
                    enveloppe.add(p);
                    enveloppe.add(q);
                    continue;
                }
                double signeRef = crossProduct(p, q, p, ref);
                boolean estCode = true;
                for (Point r : points) {
                    if (signeRef * crossProduct(p, q, p, r) < 0) {
                        estCode = false;
                        break;
                    }
                }
                if (estCode) {
                    enveloppe.add(p);
                    enveloppe.add(q);
                }
            }
        }
        return enveloppe;
    }

//    public Circle welzl(ArrayList<Point> points) {
//
//        return b_minidisk(points, new ArrayList<Point>());
//
//    }




    /**
     * ========= Circle calculCercleMinNaif ==============
     */

    public Circle calculCercleMinNaif(ArrayList<Point> inputPoints) {

        ArrayList<Point> points = (ArrayList<Point>) inputPoints.clone();
        if (points.size()<1) return null;
        double cX,cY,cRadius,cRadiusSquared;
        for (Point p: points){
            for (Point q: points){
                cX = .5*(p.x+q.x);
                cY = .5*(p.y+q.y);
                cRadiusSquared = 0.25*((p.x-q.x)*(p.x-q.x)+(p.y-q.y)*(p.y-q.y));
                boolean allHit = true;
                for (Point s: points)
                    if ((s.x-cX)*(s.x-cX)+(s.y-cY)*(s.y-cY)>cRadiusSquared){
                        allHit = false;
                        break;
                    }
                if (allHit) return new Circle(new Point((int)cX,(int)cY),(int)Math.sqrt(cRadiusSquared));
            }
        }
        double resX=0;
        double resY=0;
        double resRadiusSquared=Double.MAX_VALUE;
        for (int i=0;i<points.size();i++){
            for (int j=i+1;j<points.size();j++){
                for (int k=j+1;k<points.size();k++){
                    Point p=points.get(i);
                    Point q=points.get(j);
                    Point r=points.get(k);
                    //si les trois sont colineaires on passe
                    if ((q.x-p.x)*(r.y-p.y)-(q.y-p.y)*(r.x-p.x)==0) continue;
                    //si p et q sont sur la meme ligne, ou p et r sont sur la meme ligne, on les echange
                    if ((p.y==q.y)||(p.y==r.y)) {
                        if (p.y==q.y){
                            p=points.get(k); //ici on est certain que p n'est sur la meme ligne de ni q ni r
                            r=points.get(i); //parce que les trois points sont non-colineaires
                        } else {
                            p=points.get(j); //ici on est certain que p n'est sur la meme ligne de ni q ni r
                            q=points.get(i); //parce que les trois points sont non-colineaires
                        }
                    }
                    //on cherche les coordonnees du cercle circonscrit du triangle pqr
                    //soit m=(p+q)/2 et n=(p+r)/2
                    double mX=.5*(p.x+q.x);
                    double mY=.5*(p.y+q.y);
                    double nX=.5*(p.x+r.x);
                    double nY=.5*(p.y+r.y);
                    //soit y=alpha1*x+beta1 l'equation de la droite passant par m et perpendiculaire a la droite (pq)
                    //soit y=alpha2*x+beta2 l'equation de la droite passant par n et perpendiculaire a la droite (pr)
                    double alpha1=(q.x-p.x)/(double)(p.y-q.y);
                    double beta1=mY-alpha1*mX;
                    double alpha2=(r.x-p.x)/(double)(p.y-r.y);
                    double beta2=nY-alpha2*nX;
                    //le centre c du cercle est alors le point d'intersection des deux droites ci-dessus
                    cX=(beta2-beta1)/(double)(alpha1-alpha2);
                    cY=alpha1*cX+beta1;
                    cRadiusSquared=(p.x-cX)*(p.x-cX)+(p.y-cY)*(p.y-cY);
                    if (cRadiusSquared>=resRadiusSquared) continue;
                    boolean allHit = true;
                    for (Point s: points)
                        if ((s.x-cX)*(s.x-cX)+(s.y-cY)*(s.y-cY)>cRadiusSquared){
                            allHit = false;
                            break;
                        }
                    if (allHit) {resX=cX;resY=cY;resRadiusSquared=cRadiusSquared;}
                }
            }
        }
        return new Circle(new Point((int)resX,(int)resY),(int)Math.sqrt(resRadiusSquared));
    }


    /**
     * == :: methodes Utils :: ==
     */


    /**
     * ##################################################
     */


    public Line calculDiametreOptimise(ArrayList<Point> points) {
        if (points.size() < 3) {
            return null;
        }

        Point p = points.get(1);
        Point q = points.get(2);

        /*******************
         * PARTIE A ECRIRE *
         *******************/
        return new Line(p, q);
    }

    public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points) {
        if (points.size() < 3) {
            return null;
        }

        ArrayList<Point> enveloppe = new ArrayList<Point>();

        enveloppe.add(points.get(0));
        enveloppe.add(points.get(1));
        enveloppe.add(points.get(2));

        /*******************
         * PARTIE A ECRIRE *
         *******************/
        return points;
    }

    private double crossProduct(Point p, Point q, Point s, Point t) {
        return ((q.x - p.x) * (t.y - s.y) - (q.y - p.y) * (t.x - s.x));
    }



    public Circle welzl(ArrayList<Point> p ,ArrayList<Point> r){

        Circle result = b_minidisk((ArrayList<Point>) p.clone(),(ArrayList<Point>)r);
        return result;
    }

    public Circle b_minidisk(ArrayList<Point> p, ArrayList<Point> r){
        Circle d = new Circle(new Point(),30);
        if(p.isEmpty() || r.size() == 3){
            d = b_md(p,r);
        }
        else {
            int index = new Random().nextInt(p.size() > 1 ? p.size() - 1 : p.size());
            Point rp = p.get(index);
            ArrayList<Point> pprime = (ArrayList<Point>)p.clone();
            pprime.remove(index);
            // r.add(rp);
            d = b_minidisk(pprime,r);
            if(d != null && !isInCircle(d,rp)){
                ArrayList<Point> rprime = (ArrayList<Point>) r.clone();
                rprime.add(rp);
                d = b_minidisk(pprime,rprime);
            }
        }
        return d;
    }

//    public boolean isInCircle(Circle circle,Point p){
//        return (( Math.pow((double)(p.x - circle.getCenter().x),(double)2)) + Math.pow((double)(p.y - circle.getCenter().y),(double)2)) < Math.pow((double)circle.getRadius(),(double)2) ;
//    }

    public boolean isInCircle(Circle D, Point p) {
        return (((p.x - D.getCenter().x) * (p.x - D.getCenter().x)
                + (p.y - D.getCenter().y) * (p.y - D.getCenter().y)) < (D.getRadius() * D.getRadius()));
    }

    public boolean allIsInCircle(Circle circle, ArrayList<Point> points){
        for (Point p : points){
            if(!isInCircle(circle,p)){
                return false;
            }
        }
        return true;
    }

    public static Circle circleFromPoints(final Point p1, final Point p2, final Point p3)
    {
        final double offset = Math.pow(p2.x,2) + Math.pow(p2.y,2);
        final double bc =   ( Math.pow(p1.x,2) + Math.pow(p1.y,2) - offset )/2.0;
        final double cd =   (offset - Math.pow(p3.x, 2) - Math.pow(p3.y, 2))/2.0;
        final double det =  (p1.x - p2.x) * (p2.y - p3.y) - (p2.x - p3.x)* (p1.y - p2.y);

        if (Math.abs(det) < 0) { throw new IllegalArgumentException("Yeah, lazy."); }

        final double idet = 1/det;

        final double centerx =  (bc * (p2.y - p3.y) - cd * (p1.y - p2.y)) * idet;
        final double centery =  (cd * (p1.x - p2.x) - bc * (p2.x - p3.x)) * idet;
        final double radius =
                Math.sqrt( Math.pow(p2.x - centerx,2) + Math.pow(p2.y-centery,2));

        return new Circle(new Point((int)centerx,(int)centery),(int)radius);
    }


    public Circle b_md(ArrayList<Point> p, ArrayList<Point> r){
        if (p.isEmpty() && r.size() == 0){
            return new Circle(new Point(0, 0), 10);
        }
        Circle d = null;
        if (r.size() == 1) {
            d = new Circle(r.get(0), 0);
        }
        if (r.size() == 2) {
            double cx = (r.get(0).x + r.get(1).x) / 2;
            double cy = (r.get(0).y + r.get(1).y) / 2;
            double dd = r.get(0).distance(r.get(1)) / 2;
            Point pt = new Point((int) cx, (int) cy);
            d = new Circle(pt, (int) Math.ceil(dd));
        } else if(r.size() == 3) {
                d = circleFromPoints(r.get(0), r.get(1), r.get(2));
        }
        return d;
    }




}
