/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication14;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 *
 * @author Alex
 */
public class informatiiLocatii extends fileClass {

    final private int nrLocatiiAfisate = 5;
    final private int nrZileActivitate = 10;
    
    public informatiiLocatii(String numeFis) {
        super(numeFis);
    }
    
    //Afiseaza intreaga oferta de turism
    public void display(){
    
        for(int i = 0; i < vectorLocatii.size(); i++){
            System.out.println(vectorLocatii.get(i));
        }
        
    }
    
    //Cerinta 1
    public void cer1( String nume ){
        for(int i = 0; i < vectorLocatii.size(); i++){
            if( vectorLocatii.get(i).numeLocatie.equals(nume) ){
                System.out.println(vectorLocatii.get(i) + " ");
                break;
            }
        }
    }
    
    public void cer2(String Oras, String data1, String data2){
        
        ArrayList<Integer> indexArray = new ArrayList<>();
        ArrayList<Double> costuriTotale = new ArrayList<>(); //Colectie in care vom salva indecsii locatiilor in care perioada de vacanta depaseste 10 zile
        
        int nrZile = 0;
        
        try {
        Date datax1 = new SimpleDateFormat("dd/MM/yyyy").parse(data1);
        Date datax2 = new SimpleDateFormat("dd/MM/yyyy").parse(data2);     
        
            if(datax1.after(datax2)) { //In cazul in care datele sunt introduse incorect iesim din metoda
                System.out.println("Reintroduceti datele!");
                return;
            }
        
        for(int i = 0; i < vectorLocatii.size(); i++){
        String varTemp = vectorLocatii.get(i).numeOras; //Selectam un oras
        if( varTemp.equals(Oras) ){ //Daca numele coincide cu parametrul Oras
        String temp = vectorLocatii.get(i).perioada; //Selectam perioada
        String[] vectorTemps = temp.split("-"); //Date are forma data1 - data2. O impartim in String-uri
                 //Transformam String-urile in format de tipul date
                Date temporar1 = new SimpleDateFormat("dd/MM/yyyy").parse(vectorTemps[0].replaceAll("\\s+", ""));
                Date temporar2 = new SimpleDateFormat("dd/MM/yyyy").parse(vectorTemps[1].replaceAll("\\s+", ""));               
                
                //verificam daca Datele introduce sunt situate in perioada din fisier
                if( ( !temporar1.after(datax1) && !temporar2.before(datax1) ) &&
                      !temporar1.after(datax2) && !temporar2.before(datax2) ) {
                    indexArray.add(i); //daca datele sunt valide retinem index-ul locatiei
                }
                
                }    
            }
                //Daca nicio perioada nu este disponibila iesim
                if(indexArray.size() == 0){ 
                    System.out.println("Nu exista locatii disponibile intre datele:" + datax1 + " " + datax2);
                    return;
                }
        
        } catch (ParseException ex) {
            System.err.println("Eroare la convertire!");
            ex.printStackTrace();
            }
        
            
        try {
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(data1);
            Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(data2);
            //Calculam numarul de zile dintre datele introduse de utilizator
            nrZile = daysBetween(date1, date2);
        } catch (ParseException ex) {
            System.err.println("Eroare la convertire!");
            ex.printStackTrace();
            }
       
        //Calculam costul total fiecarei locatii
        for(int i = 0; i < indexArray.size(); i++ ){
            double temp = nrZile * Double.parseDouble(vectorLocatii.get( indexArray.get(i) ).pretMediu);
            costuriTotale.add( temp );
        }    
        
        Integer list1[] = new Integer[indexArray.size()];
        list1 = indexArray.toArray(list1);
        Double list2[] = new Double[costuriTotale.size()];
        list2 = costuriTotale.toArray(list2);
        //sortam preturile descrescator
        //in acelasi timp trebuie sa modificam pozitia indecsilor din colectia indexArray in functie de pozitia preturilor
        sortArray(list2, list1);
        
        int iterator = 1;
        for(int i = 0; i < indexArray.size(); i++){
            if(iterator == nrLocatiiAfisate){
                break;
            }
            System.out.println( vectorLocatii.get(list1[i]).numeLocatie + " " + list2[i] ); //le afisam pe maxim 5 cele mai scumpte
            iterator++;
        }
        
        
    }
            
    private void sortArray( Double[] temp, Integer[] temp1 ){
        double x;
        int y;
        for(int i = 0; i < temp.length; i++){
            for(int j = i + 1; j < temp.length; j++){
               if( temp[i] < temp[j] ){
                   x = temp[i];
                   temp[i] = temp[j];
                   temp[j] = x;
                   
                   y = temp1[i];
                   temp1[i] = temp1[j];
                   temp1[j] = y;
                   
               }
               
            }
        }    
    }

    private int daysBetween(Date d1, Date d2){ return Days.daysBetween(
                new LocalDate(d1.getTime()), new LocalDate(d2.getTime())).getDays(); }
     
    public void cer3(){
        ArrayList<Double> preturi = new ArrayList<>();         //Colectie in care vom salva pretul total al fiecarei locatii
        ArrayList<Integer> indexArray = new ArrayList<>();  //Colectie in care vom salva indecsii locatiilor in care perioada de vacanta depaseste 10 zile
        String data1, data2;
        int nrZile;
        
        for(int i = 0; i < vectorLocatii.size(); i++){ //iteram locatiile
            
        String temp = vectorLocatii.get(i).perioada;
        String[] vectorTemps = temp.split("-"); //Date are forma data1 - data2. O impartim in String-uri
            data1 = vectorTemps[0].replaceAll("\\s+", "");
            data2 = vectorTemps[1].replaceAll("\\s+", "");
            try {
            //Transformam String-urile in format de tipul date
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(data1); 
            Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(data2); 
            nrZile = daysBetween(date1, date2); //calculam numarul de zile dintre cele doua date
            
            if(nrZile > 10){
                indexArray.add(i); //daca se respecta conditia retinem index-ul
            }
            
            } catch (ParseException ex) {
            System.err.println("Eroare la convertire!");
            ex.printStackTrace();
            }
        }
        
        for(int i = 0; i < indexArray.size(); i++){
            preturi.add( nrZileActivitate * Double.parseDouble(vectorLocatii.get( indexArray.get(i)).pretMediu ) ); //Populam colectia cu preturile calculate
        }
        
        Integer list1[] = new Integer[indexArray.size()];
        list1 = indexArray.toArray(list1);
        Double list2[] = new Double[preturi.size()];
        list2 = preturi.toArray(list2);
        //sortam preturile descrescator
        //in acelasi timp trebuie sa modificam pozitia indecsilor din colectia indexArray in functie de pozitia preturilor
        sortArray(list2, list1); 
             
        System.out.println("Cea mai ieftina locatie unde se poate practica 10 zile o activitate: " + vectorLocatii.get( list1[list1.length - 1] ));
        System.out.println("Pretul: " + list2[list2.length - 1]);
        
    }
    
}
   
