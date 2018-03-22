/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication14;

import java.text.ParseException;
/**
 *
 * @author Alex
 */
public class JavaApplication14 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {      
        informatiiLocatii newInst = new informatiiLocatii("locuri.txt");       
        
        //Cerinta 1
        newInst.cer1("Poiana Brasov");
        
        //Cerinta 2
        newInst.cer2("Bran" ,"01/04/2018", "31/12/2018");
        
        //Cerinta 3
        newInst.cer3();
        
              
    }
}
