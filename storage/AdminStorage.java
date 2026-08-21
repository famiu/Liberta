package storage;

import java.io.*;
import java.util.*;
import java.time.LocalDate;

import entity.*;

public class AdminStorage {
    private static HashMap<String, AdminAccount> admins = new HashMap<>();

    static{
        // Checks whether the folder exists, if not it creates it
        File a = new File("data");
        if(!a.exists()) a.mkdir();
        File b = new File("data/admins.txt");
        if(!b.exists()){
            try{
                b.createNewFile();
            } catch(Exception e){
                System.out.println("Failed to create admins.txt");
            }
        }
        //If database has any information, then store it in admins hashmap.
        getAllAdmin();
    }

    public AdminStorage(){
        
    }

    //Return all Admin from the database
    public static HashMap<String,AdminAccount> getAllAdmin(){
        if(admins.isEmpty()){
            try(BufferedReader r = new BufferedReader(new FileReader("data/admins.txt"))){
                String line;
                String key = "";
                while((line=r.readLine())!=null){
                    String comp;
                    if(line.length()>3) comp = line.substring(0,3);
                    else comp = "";
                    if(comp.equals("Use")){
                        key = line.substring(10);
                        admins.put(key, new AdminAccount());
                        admins.get(key).setUsername(key);
                    }else if(comp.equals("Pas")){
                        admins.get(key).setPassword(line.substring(10));
                    }
                }
            }catch (IOException e) {
            System.out.println("Error reading file.");
            }
        }
        return admins;
    }

    //Add Admin to the hashmap
    public static void addAdmin(AdminAccount newAdmin){
        if(admins.containsKey(newAdmin.getUsername())){
            System.out.println("Admin is already in database");
            return;
        }
        admins.put(newAdmin.getUsername(), newAdmin);
        addAdminToDatabase(newAdmin);
    }

    //Delete Admin from the hashmap
    public static void deleteAdmin(String Adminname){
        if(admins.containsKey(Adminname)){
            admins.remove(Adminname);
            updateAdminDatabase();
        }else{
            System.out.println("Admin is not found");
        }
    }

    //Add the new Admin information to the end of the admins.txt file
    private static void addAdminToDatabase(AdminAccount newAdmin){
        try(BufferedWriter w = new BufferedWriter(new FileWriter("data/admins.txt",true))){
            w.newLine();
            w.write("Username: "+newAdmin.getUsername());
            w.newLine();
            w.write("Password: "+newAdmin.getPassword());
            w.newLine();
            w.write("===============================================");
        }catch (IOException e) {
        System.out.println("Error reading file.");
        }
    }

    //Clear all information from the databse and add all Admin from the hashmap to the database
    private static void updateAdminDatabase(){
        try(FileWriter fw = new FileWriter("data/admins.txt")){
            fw.write("");
        }catch(IOException e){
            System.out.println("Unable to clear the Admin database");
        }
        admins.forEach((key,value)->{
            addAdminToDatabase(value);
        });
    }

    //Check whether a Admin already exist
    public static boolean checkAdmin(String Adminname){
        return admins.containsKey(Adminname);
    }

    //Check whether a Admin password match
    public static boolean checkPassword(String Adminname, String password){
        return admins.get(Adminname).getPassword().equals(password);
    }
}