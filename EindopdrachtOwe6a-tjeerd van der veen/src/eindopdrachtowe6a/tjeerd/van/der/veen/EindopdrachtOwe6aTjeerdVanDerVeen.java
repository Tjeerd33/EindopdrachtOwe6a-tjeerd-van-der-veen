package eindopdrachtowe6a.tjeerd.van.der.veen;

//kleine verrandering
import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.*;

/**
 * eindversie:28-2-2018
 * known bugs:wanneer een file gelezen word, word de eerste text area niet direct ingevult
 * gebruik deze link als source: 
 * ftp://ftp.genome.jp/pub/db/virushostdb/virushostdb.tsv
 * @author theox
 */
public class EindopdrachtOwe6aTjeerdVanDerVeen {
    
    static HashMap<String, Virus> virusMap = new HashMap();
    static HashMap<String, Host> hostMap = new HashMap();
    static ArrayList<String> classifications = new ArrayList();
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VirusGUI Gui = new VirusGUI();
        Gui.setVisible(true);
    }
    /**
     * sorteert de virussen van de gegeven hosts op het aantal hosts en zet deze selectie in de text erea onder de bijbehorende comboboxen
     * @param host1 een string met de indentifier van de geselcteerde host uit de 2de combobox
     * @param host2 een string met de indentifier van de geselcteerde host uit de eerste combobox
     * @param area1 de text area onder de 2de combobox
     * @param area2 de text area onder de eerste combox
     */
    public static void sortHost(String host1, String host2, JTextArea area1, JTextArea area2){
        VirusLogic logic = new VirusLogic(virusMap, hostMap);
        area1.setText(Arrays.toString(logic.sortHostCount(host1)).replace(",", "\n").replace("[", "").replace("]","").replace(" ", ""));
        area2.setText(Arrays.toString(logic.sortHostCount(host2)).replace(",", "\n").replace("[", "").replace("]","").replace(" ", ""));
    }
    /**
     * sorteert de virussen van de gegeven hosts op ID en zet deze selectie in de text erea onder de bijbehorende comboboxen
     * @param host1 een string met de indentifier van de geselcteerde host uit de 2de combobox
     * @param host2 een string met de indentifier van de geselcteerde host uit de eerste combobox
     * @param area1 de text area onder de 2de combobox
     * @param area2 de text area onder de eerste combox
     */
    public static void sortID(String host1, String host2, JTextArea area1, JTextArea area2){
        VirusLogic logic = new VirusLogic(virusMap, hostMap);
        area1.setText(Arrays.toString(logic.sortID(host1)).replace(",", "\n").replace("[", "").replace("]","").replace(" ", ""));
        area2.setText(Arrays.toString(logic.sortID(host2)).replace(",", "\n").replace("[", "").replace("]","").replace(" ", ""));
    }
    
    
    /**
     * Haalt het bestand op via de URL die gegeven word en update de GUI met de gevonden gegevens van dat bestand
     * @param location een URL naar het te selecteren bestand
     * @param Status JLabel in de GUI
     * @param Host1 de comboBox om de hostst in te zetten
     * @param host2 de 2de comboBox om de hosts in te zetten
     * @param classification 3de comboBox om de classificaties van de virussen in te zetten
     * @throws MalformedURLException een error word opgevangen wanneer de URL niet bestaat
     * @throws IOException een error word opgevangen als het bestand dat van de URL komt niet bestaat of gelezen kan worden
     */
    public static void getURL(String location, JLabel Status, JComboBox Host1, JComboBox host2, JComboBox classification) throws MalformedURLException, IOException{
        Status.setText("Getting file");
        URL website = new URL(location);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        Status.setText("Parsing to local file");
        FileOutputStream fos = new FileOutputStream("information.txt");//maakt een tijdelijk locaal bestand
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        Status.setText("Opening local file");
        getFile("information.txt");//opent het locale bestand
        updateGui(Host1, host2, classification);//update de GUI met de gevonden hosts en bijbehorende virussen
        Status.setText("Done");
    }
    /**
     * Opent het tijdelijke locale bestand dat gemaakt word in getURL
     * @param source het bestand dat geopend moet worden
     */
    public static void getFile(String source){
        JFileChooser fileChooser = new JFileChooser();
        try{
            BufferedReader inFile;
            inFile = new BufferedReader(new FileReader(source));
            String line;
            int lineOne=1;
            while ((line = inFile.readLine())!=null){
                String[] lineList = line.split("\t");
                try{
                    if(lineOne==1){
                        lineOne+=1; 
                    }
                    else if (!virusMap.containsKey(lineList[0])){
                        updateHosts(lineList);
                        virusMap.put(lineList[0], new Virus(lineList[1],lineList[7],lineList[2],Integer.parseInt(lineList[0])));
                        updateClassifications(virusMap.get(lineList[0]).getClassificatie());
                    }else if(virusMap.containsKey(lineList[0])){
                        updateHosts(lineList);
                        virusMap.get(lineList[0]).addHost(lineList[7]);
                    }
                }catch(ArrayIndexOutOfBoundsException aioobe){
                    System.out.println("Warning virus without host encounterd");
                    virusMap.put(lineList[0], new Virus(lineList[1],lineList[2],Integer.parseInt(lineList[0])));
                    }
            }
        }catch(FileNotFoundException fnfe){
            System.out.println("File was not found");
        }catch(IOException ioe){
            System.out.println("File was not readable");
        }
    }
    /**
     * update de hostMap met de gegeven string array
     * @param entry een string array met daarin tenminste 9 items
     */
    public static void updateHosts(String[] entry){
        if(hostMap.containsKey(entry[8])){
            if(!(hostMap.get(entry[8]).getVirusses()).contains(entry[0])){
                hostMap.get(entry[8]).addVirus(entry[0]);
            } 
        }else{
            hostMap.put(entry[8],new Host(entry[0]));
        }
    }
    /**
     * update de classificatie lijst
     * @param classification een string
     */
    public static void updateClassifications(String classification){
        String[] classes = classification.split(";");
        if(!classifications.contains(classes[1])){
            classifications.add(classes[1]);
        }
    }
    /**
     * Update de comboboxen in de GUI
     * @param host1 combobox 1 (een lijst van hosts)
     * @param host2 combobox 2 (een lijst van hosts)
     * @param classificationBox combobox 3 alle clasificaties van de gevonden virussen
     */
    public static void updateGui(JComboBox host1, JComboBox host2, JComboBox classificationBox){
        host1.removeAllItems();
        host2.removeAllItems();
        classificationBox.removeAllItems();
        hostMap.keySet().stream().map((x) -> {
            host1.addItem(x);
            return x;
        }).forEach((x) -> {
            host2.addItem(x);
        });
        classifications.stream().forEach((x) -> {
            classificationBox.addItem(x);
        });
    }
    /**
     * update de textAreas onder de comboboxes
     * @param area de area onder de aangeroepen combobox
     * @param selected de host die in de aangeroepen combobox staat
     * @param area2 de area onder de andere combobox
     * @param selected2 de andere combobox
     */
    public static void makeSelection(JTextArea area, String selected, JTextArea area2, String selected2){
        VirusLogic logic = new VirusLogic(virusMap, hostMap);
        logic.normalSelect(area, hostMap.get(selected));
        if(selected2!=null){
            area2.setText(logic.compareSelected(hostMap.get(selected).getVirusses(), hostMap.get(selected2).getVirusses()).toString().replace(",", "\n").replace("[", "").replace("]","").replace(" ", ""));
        }
    }
}
