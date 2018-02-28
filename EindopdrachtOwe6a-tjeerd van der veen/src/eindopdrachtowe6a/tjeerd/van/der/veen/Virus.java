/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eindopdrachtowe6a.tjeerd.van.der.veen;

import java.awt.List;
import java.util.ArrayList;

/**
 * een virus class voor de eindopdracht van blok 6
 * deze class bevat een naam, classificatie, host, ID, en hostCount met bijbehorende getters een een setter om een host toe te voegen
 * @author theox
 */
public class Virus {
    private String name, classificatie;
    private ArrayList host;
    private int ID, hostCount = 0;
    /**
     * de initializering voor een leeg virus
     */
    public Virus() {
        System.out.println("Warning empty virus created!");
    }
    /**
     * de initializering voor een standaard virus
     * @param name een String voor de naam van het virus
     * @param host een String voor de naam van de host van het virus
     * @param classificatie een String voor de classificatie van het virus
     * @param ID een int voor het ID nummer van het virus
     */
    public Virus(String name, String host, String classificatie, int ID){
        this.host = new ArrayList();
        this.name=name;
        this.host.add(host);
        this.classificatie=classificatie;
        this.ID=ID;
        this.hostCount+=1;
    }
    /**
     * de initializering voor een virus waarvan geen host is aangegeven
     * @param name een String voor de naam van het virus
     * @param classificatie een String voor de classificatie van het virus
     * @param ID een int voor het ID nummer van het virus
     */
    public Virus(String name, String classificatie, int ID){
        this.host = new ArrayList();
        this.name=name;
        this.classificatie=classificatie;
        this.ID=ID;
    }
    /**
     * setter om een host toe te voegen aan een bestaand virus
     * @param host een string voor de naam van de host die toegevoegd moet worden
     */
    void addHost(String host){  
        this.host.add(host);
        this.hostCount+=1;
    }
    /**
     * getter voor de host
     * @return geeft een arraylist met daarin alle bekende hosts van dit virus
     */
    ArrayList getHosts(){
        return(this.host);
    }
    /**
     * getter voor het ID
     * @return int met daarin het ID
     */
    int getID(){
        return(this.ID);
    }
    /**
     * getter voor de naam
     * @return String met de naam
     */
    String getName(){
        return(this.name);
    }
    /**
     * getter voor de classificatie
     * @return String met de classificatie
     */
    String getClassificatie(){
        return(this.classificatie);
    }
    /**
     * getter voor het aantal hosts
     * @return int met het aantal hosts
     */
    int getHostCount(){
        return(this.hostCount);
    }
}
