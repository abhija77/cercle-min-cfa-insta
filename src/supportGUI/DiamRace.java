//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package supportGUI;

import algorithms.DefaultTeam;

import java.awt.Point;
import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class DiamRace {
    private static int width = 0;
    private static int height = 0;
    private static String title = "Diameter Racer";
    private static FramedDiamRace framedGUI;
//    private static String filename = "input.points";

    public DiamRace() {
    }

    public static void main(String[] args) {

//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//               DiamRace.framedGUI = new FramedDiamRace(DiamRace.width, DiamRace.height, DiamRace.title);
//            }
//        });
//        synchronized(Variables.lock) {
//            try {
//                Variables.lock.wait();
//            } catch (InterruptedException var3) {
//                var3.printStackTrace();
//            }
//        }

//        System.out.println(args[0]);
//
        readFile();
    }

    public static void readFile() {

        /*for(int a = 1;a < 11;a++){
            ArrayList<Point> points = RandomPointsGenerator.generatePoints(a);
            (new DefaultTeam()).calculCercleMin(points);
        }*/

        long ttms = System.currentTimeMillis();

        for (int i = 1; i < 21; i++){
            String filename = "./samples/test-"+i+".points";
            System.out.println("Fichier : " + filename);



            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
                ArrayList<Point> points = new ArrayList<>();
                try {
                    String line;
                    while((line = input.readLine()) != null) {
                        String[] coordinates = line.split("\\s+");
                        points.add(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
                    }
                    long ms  = System.currentTimeMillis();
                    (new DefaultTeam()).calculCercleMin(points);
                    long ms2 = System.currentTimeMillis();
                    long totalMs = ms2 - ms;
                    System.out.println("Durée : "+totalMs+" ms");
//                    try{
//                        BufferedWriter writer = new BufferedWriter(new FileWriter("./tests_welzl.csv", true));
//                        writer.append(totalMs+"\n");
//                        writer.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    framedGUI.drawPoints(points);
//                    synchronized(Variables.lock2) {
//                        Variables.lock2.notify();
//                    }
                } catch (IOException var16) {
                    System.err.println("Exception: interrupted I/O.");
                } finally {
                    try {
                        input.close();
                    } catch (IOException var14) {
                        System.err.println("I/O exception: unable to close " + filename);
                    }

                }
            } catch (FileNotFoundException var18) {
                System.err.println("Input file not found.");
            }
        }
        long ttms2 = System.currentTimeMillis();
        System.out.println("La durée de traitement total pour les 1664 fichiers est de : "+(ttms2 - ttms)+" ms");

    }
}
