/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package martinellibertoli.miab_server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Martinelli
 */
public class ServerMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{ //gotta catch 'em all
        // TODO code application logic here    
        ServerSocket welcomeSocket = new ServerSocket(3600);
        while(true){
            //Validator validator=new Validator();
            //Creator creator=new Creator();
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("connected with client.");
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            JSONObject clientJsonObj;
            JSONObject capitalizedJsonObj = new JSONObject();
            JSONParser parser = new JSONParser();
            clientJsonObj = (JSONObject) parser.parse(inFromClient.readLine());
            /*
            switch((String)clientJsonObj.get("command")){
                case u: //upload
                    //controllo validità pacchetto
                    validator.checkMessage(clientJsonObj);
                    //check md5
                    //check filename
                    //manda ack e opcode1
                    creator.createack();
                case s: //send
                    //controlla segmento
                    //salva segmento
                    //manda ack con nsegmento richiesto
                case e: //end
            }
            */
            /*String imageDataString = (String) clientJsonObj.get("image");
            String imagePath = (String) clientJsonObj.get("imagePath");
            System.out.println(imageDataString);           
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder().decode(imageDataString);
 
            try ( 
                  // Write a image byte array into file system
                  FileOutputStream imageOutFile = new FileOutputStream(imagePath)) {
                imageOutFile.write(imageByteArray);
            }           
            capitalizedJsonObj.put("info", "Image Successfully Received and Stored");           
            outToClient.writeBytes(capitalizedJsonObj.toString() + '\n');*/
        }
    }
    
}
