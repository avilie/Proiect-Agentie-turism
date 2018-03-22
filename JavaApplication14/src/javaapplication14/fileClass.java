/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */

//Clasa prin intermediul careia extragem datele din fisier
//Fisierul are urmatoarea structura: 
//numeLocatie | numeOras | pretMediu | [ Activitate1 | Activitate2 | .. | Activitaten ] | perioadaInceput - perioadaSfarsit
//Datele sunt salvate in vectorul vectorLocatii care are urmatoarea structura
//[ String, String, String, [String, .., String  ], String] 

public class fileClass {
    private String numeFis;
    private ArrayList listaInter; //Colectie in care vom salva activitatile
    
    class locatii{
    
        protected String numeLocatie;
        protected String numeOras;
        protected String pretMediu;
        private ArrayList activitati;
        protected String  perioada;
        
        public locatii(String numeLocatie, String numeOras, String pretMediu, ArrayList activitati, String perioada){
            this.numeLocatie = numeLocatie;
            this.numeOras = numeOras;
            this.pretMediu = pretMediu;
            this.activitati = (ArrayList<Object>)activitati.clone();
            this.perioada = perioada;
        }
     
        public String toString() {
        return "numeLoc.:" + this.numeLocatie + ", "
                + "numeOras.:" + this.numeOras + ", " + "pretMediu:"
                + this.pretMediu + " " + "Activitati.:" + this.activitati +
                ", " + "Perioada:" + this.perioada;
    }
        
    }
    
    protected ArrayList<locatii> vectorLocatii;
    
    public fileClass(String numeFis){
           if(numeFis.length() == 0){
            System.out.println("Numele fisierului este invalid!");
            return;
        }

        vectorLocatii = new ArrayList<locatii>();
        listaInter = new ArrayList();
        this.numeFis = numeFis;
        this.readFile();
    }
    
    private void readFile(){
        try {
            BufferedReader in = new BufferedReader( new FileReader(numeFis) );
            
            String nume;  //Variabila in care salvam cate o linie din fisier
            int flag = 0;
            while( (nume = in.readLine()) != null ){     
            String[] vec = nume.split("\\|"); //Extragem datele ignorand separatorul
                   
            for(int i = 0; i < vec.length; i++){
                vec[i] = vec[i].trim(); //Stergem spatiile albe de la inceputul si de la sfarsitul string-ului
                
               //in cazul in care am inceput sa citim din activitati si ultimul caracter nu este ')'
               //astfel, nu mai adaug ultimul String inca o data in colectie
               if( flag == 1 && !vec[i].endsWith(")") ){
                    listaInter.add(vec[i]);
                }
                
               //Cazurile tratate ale activitatilor
               //Sunt trei cazuri posibile:
               //String-ul incepe cu (, se termina cu ) sau are in ambele extremitati ( si )
                if ( vec[i].startsWith("(") && vec[i].endsWith(")") ){
                    String tempString[];
                    tempString = vec[i].split("[\\(\\)]");
                    listaInter.add(tempString[1]);
                    flag = 0;
                } else if( vec[i].startsWith("(") ){
                    String tempString[];
                    //String-ul nostru va fi de forma (String
                    //Il impartim in 2 string-uri "(" si "String"
                    //Il selectam pe al doilea
                    tempString = vec[i].split("\\(");
                    listaInter.add(tempString[1]);
                    flag = 1; //in momentul in care incepem sa citim din activitati
                } else if( vec[i].endsWith(")") ){
                    String tempString[];
                    //String-ul nostru va fi de forma String)
                    //Il impartim in 2 string-uri "String" + ")"
                    //Il selectam pe al doilea
                    tempString = vec[i].split("\\)");
                    listaInter.add(tempString[0]);
                    flag = 0;
                }
                
                
            }                         
            
            vectorLocatii.add(new locatii(vec[0], vec[1], vec[2], listaInter, vec[vec.length - 1]));
            
            listaInter.clear();
                       
            }
            in.close();
        } catch (IOException e) {
            System.err.println("Eroare la citirea din fisier!");
            e.printStackTrace();
        }
    }
    
  
}
