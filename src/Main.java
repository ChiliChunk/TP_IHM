import fr.dgac.ivy.*;

public class Main {
    public static void main(String[] args) {
        Ivy bus =  new Ivy("myAgent", "Ceci est un message", null);

        try{
            bus.start("127.255.255.255:2010");
            bus.sendMsg("Test");
            bus.bindMsg("Palette:(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    System.out.println("agent : " + client.getApplicationName());
                    System.out.println(args[0]);
                }
            });
            System.out.println("envoyé");

        }catch (Exception e){
            System.out.println("In Catch");
            System.out.println(e.getMessage());
        }
    }
}
