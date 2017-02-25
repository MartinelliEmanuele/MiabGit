/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package martinellibertoli.miab;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

/**
 *
 * @author Martinelli
 */
public class Creator {

    public Creator() {
    }
    /**
     * Funzione che converte un array di byte in una stringa Base64
     * @param file file da codificare
     * @return dataString stringa base64 contenente il file codificato
     */
    public String[] encodeFile(File file){
        String[] dataString;       
        //byte[]=Arrays.copyOfRange(data, counter, counter+2047)
        int counter=0;
        try {            
            try ( // Reading Image from file system
                    FileInputStream convertMe = new FileInputStream(file)) {
                    byte data[] = new byte[(int) file.length()];
                    byte[] slice;
                    int nPacchetti = (int)(file.length()/2048)+1;
                    dataString=new String[nPacchetti];
                    System.out.println(nPacchetti);
                    convertMe.read(data);
                    String oldbase=null, newbase=null;
                    for(int i=0; i<nPacchetti; i++){
                        if(i==nPacchetti-1){
                            byte[] lastslice = new byte[(int)(file.length()%2048)];
                            lastslice=Arrays.copyOfRange(data, 0, (int)file.length());
                            dataString[i]=Base64.getEncoder().encodeToString(lastslice);
                            if(oldbase!=null){
                            newbase = dataString[i].substring(oldbase.length(), dataString[i].length());
                            dataString[i]=newbase;
                            }
                        }
                        else{
                        slice = Arrays.copyOfRange(data, 0, counter+2047);
                        counter+=2048;                      
                        dataString[i]=Base64.getEncoder().encodeToString(slice);
                        
                        if(oldbase!=null){
                            newbase = dataString[i].substring(oldbase.length(), dataString[i].length());
                            dataString[i]=newbase;
                        }
                        oldbase=Base64.getEncoder().encodeToString(slice);
                        }
                        System.out.println(dataString[i]);
                    }
                    
            }
            System.out.println("Immagine convertita correttamente");
            return dataString;
        } catch (IOException ioe) {
            System.out.println("Errore durante la lettura dell'immagine" + ioe);
        }
        return null;
    }
    
    
}
