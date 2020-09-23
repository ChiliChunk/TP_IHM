import fr.dgac.ivy.*;

public class Main {
    public static void main(String[] args) {
        Ivy bus =  new Ivy("myAgent", "Palette:CreerRectangle", null);

        try{
            bus.start("127.255.255.255:2010");
            bus.bindMsg("Palette:MouseMoved(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
//                    System.out.println("agent : " + client.getApplicationName());
                    System.out.println("Le  pointeur  de  la  souris  se  trouve  à  la position ("+ args[0]+")");
                }
            });
//            bus.sendMsg("Palette:CreerRectangle");
        }catch (Exception e){
            System.out.println("In Catch");
            System.out.println(e.getMessage());
        }
    }
}
