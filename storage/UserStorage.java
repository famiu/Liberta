package storage;

import java.io.*;
import java.util.*;
import java.time.*;

import entity.*;

public class UserStorage {
    private static HashMap<String, UserAccount> users = new HashMap<>();

    static{
        StorageUtility.ensurePath("data", "users.txt");
        //If database has any information, then store it in users hashmap.
        getAllUser();
    }

    public UserStorage(){
        
    }

    //Return all user from the database
    public static HashMap<String,UserAccount> getAllUser(){
        if(users.isEmpty()){
            try(Scanner r = new Scanner(new File("data/users.txt"))){
                String line;
                String key = "";
                while(r.hasNextLine()){
                    line = r.nextLine();
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

    public static UserAccount getUser(String username){
        return users.get(username);
    }

    //Add user to the hashmap
    public static void addUser(UserAccount newUser){
        if(users.containsKey(newUser.getUsername())){
            System.out.println("User is already in database");
            return;
        }
        users.put(newUser.getUsername(), newUser);
        addUserToDatabase(newUser);
    }

    //Delete user from the hashmap
    public static void deleteUser(String username){
        if(users.containsKey(username)){
            PostStorage.deletePostsByAuthor(username);
            users.remove(username);
            updateUserDatabase();
        }else{
            System.out.println("User is not found");
        }
    }

    //Add post ID to a user
    public static void addPost(String username, int postId){
        if(users.containsKey(username)){
            users.get(username).addPost(postId);
            updateUserDatabase();
        }else{
            System.out.println("User is not found");
        }
    }

    //Delete post ID from a user
    public static void deletePost(String username, int postId){
        if(users.containsKey(username)){
            users.get(username).removePost(postId);
            updateUserDatabase();
        }else{
            System.out.println("User is not found");
        }
    }

    //Delete all post IDs from a user
    public static void deletePosts(String username){
        if(users.containsKey(username)){
            users.get(username).getPostIds().clear();
            updateUserDatabase();
        }else{
            System.out.println("User is not found");
        }
    }

    //Add the new user information to the end of the users.txt file
    private static void addUserToDatabase(UserAccount newUser){
        try(FileWriter w = new FileWriter("data/users.txt",true)){
            w.write("Username: "+newUser.getUsername()+"\n");
            w.write("Password: "+newUser.getPassword()+"\n");
            w.write("Display Name: "+newUser.getDisplayName()+"\n");
            w.write("Email: "+newUser.getEmail()+"\n");
            w.write("Date of birth: "+newUser.getDateOfBirth()+"\n");
            w.write("Bio: "+newUser.getBio()+"\n");
            w.write("Post Id: ");
            for(int id: newUser.getPostIds()){
                w.write(id+" ");
            }
            w.write("\n");
            w.write("===============================================\n");
        }catch (IOException e) {
        System.out.println("Error reading file.");
        }
    }

    //Clear all information from the databse and add all user from the hashmap to the database
    private static void updateUserDatabase(){
        try(FileWriter fw = new FileWriter("data/users.txt")){
            fw.write("");
        }catch(IOException e){
            System.out.println("Unable to clear the user database");
        }
        users.forEach((key,value)->{
            addUserToDatabase(value);
        });
    }

    //Check whether a user already exist
    public static boolean checkUser(String username){
        return users.containsKey(username);
    }

    //Check whether a user password match
    public static boolean checkPassword(String username, String password){
        return users.get(username).getPassword().equals(password);
    }
}
