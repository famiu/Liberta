package storage;

import java.io.*;
import java.util.*;
import java.time.LocalDate;

import entity.*;

public class UserStorage {
    private static HashMap<String, UserAccount> users = new HashMap<>();

    static{
        // Checks whether the folder exists, if not it creates it
        File a = new File("data");
        if(!a.exists()) a.mkdir();
        File b = new File("data/users.txt");
        if(!b.exists()){
            try{
                b.createNewFile();
            } catch(Exception e){
                System.out.println("Failed to create users.txt");
            }
        }
        //If database has any information, then store it in users hashmap.
        getAllUser();
    }

    public UserStorage(){
        
    }

    //Return all user from the database
    public static HashMap<String,UserAccount> getAllUser(){
        if(users.isEmpty()){
            try(BufferedReader r = new BufferedReader(new FileReader("data/users.txt"))){
                String line;
                String key = "";
                while((line=r.readLine())!=null){
                    String comp;
                    if(line.length()>3) comp = line.substring(0,3);
                    else comp = "";
                    if(comp.equals("Use")){
                        key = line.substring(10);
                        users.put(key, new UserAccount());
                        users.get(key).setUsername(key);
                    }else if(comp.equals("Ema")){
                        users.get(key).setEmail(line.substring(7));;
                    }else if(comp.equals("Dis")){
                        users.get(key).setDisplayName(line.substring(14));;
                    }else if(comp.equals("Dat")){
                        users.get(key).setDateOfBirth(LocalDate.parse(line.substring(15)));;
                    }else if(comp.equals("Pos")){
                        String[] ids = line.substring(9).split(" ");
                        System.out.println(ids);
                        for(String id: ids){
                            try{
                                users.get(key).addPost(Integer.parseInt(id));
                            }catch(Exception e){
                                continue;
                            }
                        }
                    }else if(comp.equals("Pas")){
                        users.get(key).setPassword(line.substring(10));
                    }else if(comp.equals("Bio")){
                        users.get(key).setBio(line.substring(5));
                    }
                }
            }catch (IOException e) {
            System.out.println("Error reading file.");
            }
        }
        return users;
    }

    //Add user to the hashmap
    public void addUser(UserAccount newUser){
        if(users.containsKey(newUser.getUsername())){
            System.out.println("User is already in database");
            return;
        }
        users.put(newUser.getUsername(), newUser);
        addUserToDatabase(newUser);
    }

    //Delete user from the hashmap
    public void deleteUser(String username){
        if(users.containsKey(username)){
            users.remove(username);
            updateUserDatabase();
        }else{
            System.out.println("User is not found");
        }
    }

    //Add the new user information to the end of the users.txt file
    private void addUserToDatabase(UserAccount newUser){
        try(BufferedWriter w = new BufferedWriter(new FileWriter("data/users.txt",true))){
            w.newLine();
            w.write("Username: "+newUser.getUsername());
            w.newLine();
            w.write("Password: "+newUser.getPassword());
            w.newLine();
            w.write("Display Name: "+newUser.getDisplayName());
            w.newLine();;
            w.write("Email: "+newUser.getEmail());
            w.newLine();
            w.write("Date of birth: "+newUser.getDateOfBirth());
            w.newLine();
            w.write("Bio: "+newUser.getBio());
            w.newLine();
            w.write("Post Id: ");
            for(int id: newUser.getPostIds()){
                w.write(id+" ");
            }
            w.newLine();
            w.write("===============================================");
        }catch (IOException e) {
        System.out.println("Error reading file.");
        }
    }

    //Clear all information from the databse and add all user from the hashmap to the database
    public void updateUserDatabase(){
        try(FileWriter fw = new FileWriter("data/users.txt")){
            fw.write("");
        }catch(IOException e){
            System.out.println("Unable to clear the user database");
        }
        users.forEach((key,value)->{
            addUserToDatabase(value);
        });
    }
}