package outdated;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class OUTDATED {
    //GLOBAL DECLARATIONS
    static JSONArray studList;
    static JSONParser jsonParser;
    static Scanner collect;
    static JSONObject folderStud;

    // getUserInput METHOD
    static char getUserInput(){
       collect = new Scanner(System.in);
        
        char input;
                while(true){
                    System.out.print("\n[A]dd Student\n[D]elete Student\n[V]iew Student\n\nEnter an Operation: ");
                    input = collect.next().charAt(0);
                    
                    if(input == 'A' || input == 'D' || input == 'V')
                        break;
                    else
                        System.out.println("Please enter a valid option that is found above!!!");
                }
                return input;
    }
    // END OF getUserInput METHOD


    // addStud METHOD
    static JSONObject addStud(char x){
        JSONObject Student = new JSONObject();
        // to Collect
        System.out.print("\nEnter Student ID: ");
        String StudID = collect.next();
        System.out.print("\nEnter First Name: ");
        String firstName = collect.next();
        System.out.print("\nEnter Last Name: ");
        String lastName = collect.next();
        System.out.print("\nEnter Birthday: ");
        String studBday = collect.next();
        System.out.print("\nEnter Course: ");
        String studCourse = collect.next();
        
        
        // To JSON
        Student.put("ID", StudID);
        Student.put("First Name", firstName);
        Student.put("Last Name", lastName);
        Student.put("Birthday", studBday);
        Student.put("Course", studCourse);
        
        return Student;
    }
    // END OF addStud METHOD
    
    //FILEREADER METHOD
    static void reader() throws FileNotFoundException, IOException, org.json.simple.parser.ParseException{
        FileReader reader = new FileReader("src\\enriqueat2\\LISTS.json");
        if(reader.ready()){
            Scanner collect = new Scanner(reader);
            String line = "";
            while(collect.hasNext()){
                line = line + collect.nextLine();
            }
            if(!line.equals("")){
                reader.close();
                FileReader reader2 = new FileReader("src\\enriqueat2\\LISTS.json");
                folderStud = (JSONObject)jsonParser.parse(reader2);
                studList = (JSONArray)folderStud.get("folderStud");
            }
        }
        reader.close();     
    }
    // END OF FILEREADER METHOD
    
    

    //WRITER METHOD
    static void writer() throws IOException{
         FileWriter file = new FileWriter ("src\\enriqueat2\\LISTS.json");
         file.write(folderStud.toJSONString());
         file.close();
    }
    // END OF WRITER METHOD
   
    
    //--------------------------------------------------------------MAIN METHOD-------------------------------------------------------------------
    public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {
       
        jsonParser = new JSONParser();
        folderStud = new JSONObject();
        studList = new JSONArray();

        //SELECTION
        char input = getUserInput();

        //IF TO ADD
        if(input == 'A'){
          reader();
            studList.add(addStud(input));
            folderStud.put("folderStud", studList);
          writer();
          System.out.print("The new student details has been added!");
        
    }
    
    
        //IF TO DELETE    
        if(input == 'D'){ 
        reader();
        boolean isitremoved = false;
        System.out.print("Please enter the ID to delete: ");
        String toDelete = collect.next();       
            JSONArray latestStudlist = studList;
            
            for(int i = 0; i < studList.size(); i++){ 
            JSONObject studentObj = (JSONObject)  latestStudlist.get(i);
            String getID = (String) studentObj.get("ID");
                if ((getID.equals(toDelete))) {
                        latestStudlist.remove(i);
                        isitremoved = true;
                        
                        System.out.println("ID : " + toDelete + " Successfully Deleted");
                } 
            }
            if(!isitremoved){
                System.out.println("ID : " + toDelete + " is not found!");
            }
                
                writer();
                
            }
        //IF TO VIEW 
        if(input == 'V'){
        reader();
        boolean isitviewed = false;
        System.out.print("Please enter the ID to View : ");
        String toDelete = collect.next();       
            JSONArray latestStudlist = studList;
            for(int i = 0; i < studList.size(); i++){ 
            JSONObject studentObj = (JSONObject)  latestStudlist.get(i);
            String getID = (String) studentObj.get("ID");
                if ((getID.equals(toDelete))) {
                    latestStudlist.get(i);
                    Iterator iterator = studentObj.keySet().iterator();
                while(iterator.hasNext()){
                    String key = (String) iterator.next();
                    String value = (String) studentObj.get(key);
                    System.out.println(key+": "+value);
                    }
                isitviewed = true;
                } 
            }
            if(!isitviewed){
                System.out.println("ID : " + toDelete + " is not found!");
            }
                folderStud.put("folderStud", latestStudlist );
                writer();
        }
      }
   }    
 // END OF MAIN