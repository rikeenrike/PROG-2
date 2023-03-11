package latest_code;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LATEST_CODE {
    static Scanner collect;
    static JSONObject folder;
    static JSONArray list;
    static JSONObject info;
    static JSONParser jsonParser;

    static String addStud(){

        String input;
        while (true) {
            System.out.print("[A]dd Student\n[D]elete Student\n[V]iew Student\n\nPlease Enter an Operation: ");
            input = collect.next();

            if (input.matches("(?i)A|D|V")) 
                break;
            else 
                System.out.println("Operation not found!");
            }
        return input;
    }
    
    static void reader() throws IOException, ParseException{
        FileReader reader = new FileReader("C:\\Users\\enriq\\Desktop\\BETTERVS\\ENRIQUEAT2'\\src\\enriqueat2\\LATEST_CODE.json");
        if (reader.ready()) {
            Scanner collect = new Scanner(reader);
            String line = "";
            while(collect.hasNext()){
                line = line + collect.nextLine();
            }
            if (!line.equals("")) {
                reader.close();
                FileReader reader2 = new FileReader("C:\\Users\\enriq\\Desktop\\BETTERVS\\ENRIQUEAT2'\\src\\enriqueat2\\LATEST_CODE.json");
                folder = (JSONObject) jsonParser.parse(reader2);
                list = (JSONArray) folder.get("folder");
            }
        }
        reader.close();
    }

    static void writer() throws IOException {
        FileWriter writer = new FileWriter("C:\\Users\\enriq\\Desktop\\BETTERVS\\ENRIQUEAT2'\\src\\enriqueat2\\LATEST_CODE.json");
        writer.write(folder.toJSONString());
        writer.close();

    }
    public static void main(String[] args) throws IOException, ParseException {
            collect = new Scanner(System.in);
            folder = new JSONObject();
            info = new JSONObject();
            list = new JSONArray();
            jsonParser = new JSONParser();

            String input = addStud();

            if(input.matches("(?i)A")){
                reader();
                
                System.out.print("Enter ID: ");
                String getID = collect.next();
                System.out.print("Enter Full Name: ");
                String getFN = collect.next();
                System.out.print("Enter Course:");
                String course = collect.next();
                System.out.print("Enter Birthday: ");
                String bday = collect.next();

                boolean exists = false;
                for (int i = 0; i < list.size(); i++) {
                    JSONObject infoObj = (JSONObject) list.get(i);
                    String getid = (String) infoObj.get("id");
                    if (getid.equals(getID)) {
                        exists = true;
                        break;
                    }   
                   }  
                    if (exists) {
                        System.out.println("Student already Exists!");
                    }else{
                        info.put("id", getID);
                        info.put("fullname", getFN);
                        info.put("course", course);
                        info.put("birthdate", bday);
                        
                        list.add(info);
                        folder.put("folder", list);
                        writer();
                        System.out.println("Successfully added Student!");
                    }
                }

            if(input.matches("(?i)D")){
                reader();

                System.out.print("Enter ID: ");
                String todelete = collect.next();

                    JSONArray latestlist = list;
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject infoObj = (JSONObject) latestlist.get(i);
                        String getid = (String) infoObj.get("id");
                        if (getid.equals(todelete)) {
                            latestlist.remove(i);
                            folder.put("folder", latestlist);
                            System.out.println("Successfully delete");
                            break;
                        
                        }else{
                            System.out.printf("Student ID %s cannot be found!", todelete);
                            break;
                        }
                        
                    }

                    writer();
            }
            
            if (input.matches("(?i)V")) {
                
                System.out.print("[a]View Specific student\n[b]View All\n\nEnter Operation: ");
                String vput = collect.next().toUpperCase();

                switch (vput) {
                    case "A":
                 reader();
                System.out.print("Enter ID: ");
                String toview = collect.next();

                    JSONArray latestlist = list;
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject infoObj = (JSONObject) latestlist.get(i);
                        String getid = (String) infoObj.get("id");
                        if (getid.equals(toview)) {
                            latestlist.get(i);
                            Iterator iterator = infoObj.keySet().iterator();
                            while (iterator.hasNext()) {
                                String key = (String) iterator.next();
                                String value = (String) infoObj.get(key);
                                System.out.println(key+ ": "+ value);
                            }
                         break;
                        }else{
                            System.out.printf("Student ID %s cannot be found!", toview);
                            break;
                        }               
                    }
                    writer();    
                    break;
                    
                    case "B":
                    System.out.println(list.size());
                    default:
                        break;
                }

            }
        
    }
}