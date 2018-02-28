package eindopdrachtowe6a.tjeerd.van.der.veen;

import java.util.*;
import javax.swing.JTextArea;

/**
 * class voor bij de eindopdracht die het sorteren en vergelijken van de virussen overziet
 * @author theox
 */
public class VirusLogic {

    private HashMap<String, Virus> virusMap = new HashMap();
    private HashMap<String, Host> hostMap = new HashMap();
    


/**
 * de initializer voor de class VirusLogic
 * @param virusMap een hasmap van alle virus objecten
 * @param hostMap een hasmap van alle host objecten
 */
public VirusLogic(HashMap virusMap, HashMap hostMap){
    this.virusMap = virusMap;
    this.hostMap = hostMap;
}
/**
 * zet alle virussen van een gegeven host in de gegeven text area 
 * @param field een text area om de virussen in te zetten
 * @param selected een host object
 */
public void normalSelect(JTextArea field, Host selected){
    field.setText(selected.getVirusses().toString().replace(",", "\n").replace("[", "").replace("]","").replace(" ", ""));
}
/**
 * Vergelijkt de 2 gegeven lijsten en returnt een met de overeenkomsten in die lijsten
 * @param list1 de eerste ArrayList om te vergelijken
 * @param list2 de twijde ArrayList om te vergelijken
 * @return een ArrayList met daarin de overeenkomsten tussen list1 en list2
 */
public ArrayList compareSelected(ArrayList<String> list1,ArrayList<String> list2){
    ArrayList<String> similarity = new ArrayList();
    similarity.add("");
    if(list1.size()>list2.size()){
        list1.stream().filter((x) -> (list2.contains(x))).forEach((x) -> {
            similarity.add(x);
        });
    }else{
        list2.stream().filter((x) -> (list1.contains(x))).forEach((x) -> {
            similarity.add(x);
        });
    }
    return similarity;
}
/**
 * Sorteert de virussen van de gegeven host op hun ID
 * @param host de host waarvan de virussen gesorteerd moeten worden
 * @return een int[] met daarin de virussen van de host gesorteerd op hun ID nummer
 */
public int[] sortID(String host){
    int[] sorted = new int[hostMap.get(host).getVirusses().size()];
    sorted = selectSort(this.convertToInt(hostMap.get(host).getVirusses()));
    return sorted;
}
/**
 * Sorteert de virussen van de gegeven host op het aantal hosts dat ze hebben.
 * @param host de host met de virussen die gesorteerd moeten worden
 * @return een int[] met daarin de ids van de host in volgorde op hoeveel hosts ze hebben.
 */
public int[] sortHostCount(String host){
    int[] sorted = new int[hostMap.get(host).getVirusses().size()];
    ArrayList<String> unsorted = new ArrayList();
    HashMap<Integer, ArrayList<Integer>> sizeMap = new HashMap();
    hostMap.get(host).getVirusses().stream().forEach((x) -> {//maakt een lijst van alle hoeveelheden hosts die er zijn
        if(unsorted.contains(String.valueOf(virusMap.get(x).getHostCount()))){
            sizeMap.get(virusMap.get(x).getHostCount()).add(virusMap.get(x).getID());
        }else{
            unsorted.add(String.valueOf(virusMap.get(x).getHostCount()));
            sizeMap.put(virusMap.get(x).getHostCount(), new ArrayList());
            sizeMap.get(virusMap.get(x).getHostCount()).add(virusMap.get(x).getID());
        }
        
        });
    int[] sorted1 = this.selectSort(this.convertToInt(unsorted));
    int location =0;
    for(int i: sorted1){
        for(int x: sizeMap.get(i)){
            sorted[location]=x;
            location++;
        }
    }
    return sorted;
}
/**
 * voert een selectSort uit op de gegeven int[]
 * @param arr de te sorteren int[]
 * @return een gesorteerde int[] van klein naar groot
 */
public int[] selectSort(int[] arr){
    for (int i = 0; i < arr.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j] < arr[index]) 
                    index = j;
      
            int smallerNumber = arr[index];  
            arr[index] = arr[i];
            arr[i] = smallerNumber;
        }
    return arr;
}
/**
 * maakt van een ArrayList met strings een int[]
 * @param arr de te verranderen ArrayList
 * @return een in[] met daarin de ints uit de ArrayList
 */
public int[] convertToInt(ArrayList<String> arr){   
    int[] ret = new int[arr.size()];
    for (int i=0; i < ret.length; i++)
    {
        ret[i] = Integer.parseInt(arr.get(i));
    }
    return ret;
}

}