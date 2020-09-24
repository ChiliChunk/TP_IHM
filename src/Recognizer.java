import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyMessageListener;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Recognizer {
    private static Figure listFigure;

    public static void main(String[] args) {
        listFigure = new Figure();
        listFigure.populate();
        Ivy bus =  new Ivy("recognizer", "", null);
        Stroke stroke =  new Stroke();
        try{
            bus.start("127.255.255.255:2010");
            bus.bindMsg("Palette:MousePressed x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    stroke.getListePoint().clear();
                    stroke.addPoint(Integer.parseInt(args[0]) , Integer.parseInt(args[1]));
                }
            } );
            bus.bindMsg("Palette:MouseDragged x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    stroke.addPoint(Integer.parseInt(args[0]) , Integer.parseInt(args[1]));

                }
            });

            bus.bindMsg("Palette:MouseReleased x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    stroke.addPoint(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
                    stroke.normalize();
                    System.out.println("Vous avez trassé un " + Recognizer.getClosestStrokeName(stroke));
                }
            });

        }catch (Exception e){
            System.out.println("In Catch");
            System.out.println(e.getMessage());
        }

    }

    public static String getClosestStrokeName(Stroke input){
        ArrayList<Integer> distances = new ArrayList<>();
        for (Object o : listFigure.keySet()){
            Stroke s = (Stroke) o;
            int cptPoints = 0;
            int currentDistance = 0;
            for(Point2D.Double p : s.getListePoint()){
                currentDistance += Math.abs(p.x - input.getListePoint().get(cptPoints).x);
                currentDistance += Math.abs(p.y - input.getListePoint().get(cptPoints).y);
                cptPoints += 1;
            }
            distances.add(currentDistance);
        }
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0 ; i < distances.size() ; i++){
            if(distances.get(i)<min){
                index = i;
            }
        }
        List values = new ArrayList(listFigure.values());
        System.out.println(values.get(index));
        return (String) values.get(index);
    }
}
