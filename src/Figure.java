import java.io.*;
import java.sql.SQLOutput;
import java.util.HashMap;

public class Figure<Stroke,String> extends HashMap<Stroke,String> {


    public Figure(){
        super();
    }


    public void save(){
        try {
            FileOutputStream fos = new FileOutputStream("figures.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void populate(){
        try{
            this.putAll(this.getExistingData());
        }catch (Exception e){
            System.out.println("Error while populating hashmap");
        }
    }

    private Figure<Stroke,String> getExistingData(){

        try {
            FileInputStream fis = new FileInputStream("figures.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Figure<Stroke,String> content = (Figure<Stroke, String>) ois.readObject();
            ois.close();
            fis.close();
            return content;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
