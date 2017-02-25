/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package martinellibertoli.miab;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.net.Socket;
import java.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Martinelli
 */
public class Main {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String imagePath=null, savePath=null;
        File selectedFile=null;
        boolean selected=false;
        Creator c=new Creator();
        JFileChooser fileChooser = new JFileChooser(), pathChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        pathChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setDialogTitle("Choose the file");
        pathChooser.setDialogTitle("Choose the saving path");
        
        while(!selected){
            int result = fileChooser.showOpenDialog(fileChooser);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                imagePath = selectedFile.getAbsolutePath();
                int result2 = pathChooser.showSaveDialog(pathChooser);
                if (result2 == JFileChooser.APPROVE_OPTION) {
                    File selectedPath=pathChooser.getSelectedFile();
                    savePath = selectedPath.getAbsolutePath();
                    if(imagePath.equals(savePath))
                        savePath+=" (1)";
                    selected=true;
                }
                else
                    JOptionPane.showMessageDialog(null, "Please choose a destination!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            else
                System.exit(0);
        }
        String[] dataString = c.encodeFile(selectedFile);
        
        FileInputStream convertMe = new FileInputStream(selectedFile);
        byte data[] = new byte[(int) selectedFile.length()];
        int nPacchetti = (int)(selectedFile.length()/2048);
        dataString=new String[nPacchetti];
        System.out.println(nPacchetti);
        convertMe.read(data);
        String basetring=Base64.getEncoder().encodeToString(data);
        System.out.println(basetring);
        
        try (Socket clientSocket = new Socket("127.0.0.1", 3600)) {
            if(dataString.equals("ERR")){
                System.out.println("Riprovare.");
                clientSocket.close();
            }
            else {
                System.out.println("Connesso con il server.");
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                JSONObject jsonObj = new JSONObject();
                JSONParser parser = new JSONParser();
                jsonObj.put("image", dataString);
                jsonObj.put("imagePath", savePath);
                outToServer.writeBytes(jsonObj.toString() + '\n');
                JSONObject modifiedJsonObj = (JSONObject) parser.parse(inFromServer.readLine());
                String response = (String) modifiedJsonObj.get("info");
                System.out.print("Risposta del server: " + response);
                }
        }catch(IOException e){}
            
    }
    
}
