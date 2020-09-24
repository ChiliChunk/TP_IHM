import fr.dgac.ivy.*;

import java.awt.geom.Point2D;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Figure listFigure = new Figure();
        listFigure.populate();
        Scanner in = new Scanner(System.in);
        Ivy bus =  new Ivy("myAgent", "", null);
        Stroke stroke =  new Stroke();
        try{
            bus.start("127.255.255.255:2010");
            bus.bindMsg("Palette:MousePressed x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    System.out.println(args[0]);
                    System.out.println(args[1]);
                    try{
                        bus.sendMsg("Palette:CreerEllipse x="+args[0]+" y="+args[1]+" longueur=1 hauteur=1 couleurContour=green" );
                    }catch (Exception e){
                        System.out.println("Catch mouse pressed");
                    }
                    stroke.getListePoint().clear();
                    stroke.addPoint(Integer.parseInt(args[0]) , Integer.parseInt(args[1]));
                }
            } );
            bus.bindMsg("Palette:MouseDragged x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    try{
                        bus.sendMsg("Palette:CreerEllipse x="+args[0]+" y="+args[1]+" longueur=1 hauteur=1 couleurContour=220:220:220" );
                    }
                    catch (Exception e){

                    }
                    stroke.addPoint(Integer.parseInt(args[0]) , Integer.parseInt(args[1]));

                }
            });

            bus.bindMsg("Palette:MouseReleased x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    try{
                        bus.sendMsg("Palette:CreerEllipse x="+args[0]+" y="+args[1]+" longueur=1 hauteur=1 couleurContour=red" );
                    }catch (Exception e){

                    }
                    stroke.addPoint(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
                    stroke.normalize();
                    System.out.println("Entrez le nom de la figure");
                    String s = in.nextLine();
                    System.out.println("Vous avez entré "+s);
                    listFigure.put(stroke ,s);
                    listFigure.save();
                    for (Point2D.Double p : stroke.getListePoint()){
                        System.out.println(p);
                        try {
                            bus.sendMsg("Palette:CreerEllipse x="+(int)p.x+" y="+(int)p.y+" longueur=1 hauteur=1 couleurContour=blue");
                        } catch (IvyException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }catch (Exception e){
            System.out.println("In Catch");
            System.out.println(e.getMessage());
        }
    }
}
