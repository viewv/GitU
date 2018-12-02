package io.zxnnet.model;

import java.io.*;
import java.util.ArrayList;

public class StorgeData implements Serializable {

    private String fileName;

    public StorgeData(String filename){
        String defaultpath = System.getProperty("user.home") +
                File.separator + "temp";
        filename =defaultpath + File.separator + filename + ".ssr";
        this.fileName = filename;
    }

    public void store(ArrayList<File> repo){
        try{
            FileOutputStream file =  new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(file);
            oos.writeObject(repo);
            file.close();
            oos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<File> rebuild(){
        try{
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(file);
            ArrayList<File> repo = (ArrayList<File>)ois.readObject();

            file.close();
            ois.close();
            return repo;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
