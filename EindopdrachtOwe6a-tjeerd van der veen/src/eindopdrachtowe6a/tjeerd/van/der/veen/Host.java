package eindopdrachtowe6a.tjeerd.van.der.veen;

import java.util.ArrayList;

/**
 *Host object met de naam van de host en alle virussen die erbij horen
 * @author theox
 */
public class Host {
    
    private ArrayList virusses;
    /**
     * initializer voor de Host
     * @param virus String met het ID virus dat in deze host voorkomt
     */
    public Host(String virus){
        this.virusses = new ArrayList();
        this.virusses.add(virus);
    }
    /**
     * toevoegen van een virus aan de host
     * @param virus String met het ID van de virus dat toegevoegd moet worden
     */
    void addVirus(String virus){
        this.virusses.add(virus);
    }
    /**
     * getter voor alle virussen die bij deze host horen
     * @return ArrayList met alle virussen die in deze host gevonden worden
     */
    ArrayList getVirusses(){
        return this.virusses;
    }
}
