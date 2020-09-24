import fr.dgac.ivy.*;

import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        Ivy bus =  new Ivy("myAgent", "", null);

        try{
            bus.start("127.255.255.255:2010");
            bus.bindMsg("Palette:MousePressed(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    try{
                        System.out.println(args[0]);
                        bus.sendMsg("Palette:CreerEllipse" +args[0] + " longueur=1 hauteur=1 couleurContour=green" );
                    }catch (Exception e){
                        System.out.println("Catch mouse pressed");
                    }
                }
            } );
            bus.bindMsg("Palette:MouseDragged(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    try{
                        bus.sendMsg("Palette:CreerEllipse" +args[0] + " longueur=1 hauteur=1 couleurContour=220:220:220" );
                    }
                    catch (Exception e){

                    }

                }
            });

            bus.bindMsg("Palette:MouseReleased(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    try{
                        bus.sendMsg("Palette:CreerEllipse" +args[0] + " longueur=1 hauteur=1 couleurContour=red" );
                    }catch (Exception e){

                    }
                }
            });
        }catch (Exception e){
            System.out.println("In Catch");
            System.out.println(e.getMessage());
        }
    }
}
