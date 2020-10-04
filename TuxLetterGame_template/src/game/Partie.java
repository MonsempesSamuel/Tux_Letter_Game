package game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Partie{
    
    private final String date;
    private final String mot;
    private final int niveau;
    private int trouve;
    private int temps;
    
    public Partie(String date, String mot, int niveau ){
        this.date = date;
        this.mot = mot;
        this.niveau = niveau;
        this.trouve = 0;
        this.temps = 0;
    }
    
    public Partie(Element partieElt ){
        this.date = Profil.xmlDateToProfileDate(partieElt.getAttribute("date"));
        this.mot = partieElt.getElementsByTagName("ns1:mot").item(0).getTextContent();
        this.niveau = Integer.parseInt(  ((Element )partieElt.getElementsByTagName("ns1:mot").item(0)).getAttribute("niveau")  );
        this.trouve = Integer.parseInt(partieElt.getAttribute("trouve").replace("%", ""));
        this.temps = Integer.parseInt(partieElt.getElementsByTagName("ns1:temps").item(0).getTextContent());
    }
    
    public Element getPartie(Document doc){
        return (Element )doc.getDocumentElement().getElementsByTagName("parties");
    }
    
    public void setTrouve(int nbLettresRestantes){
            this.trouve = (int )( ( ( this.mot.length() - nbLettresRestantes ) / this.mot.length() ) * 100 )  ;
    }
    
    public void setTemps(int temps){
        this.temps = temps;
    }
    
    public int getNiveau(){
        return this.niveau;
    }
    
    public String getMot(){
        return this.mot;
    }
    
    public String toString() {
        return "-La date de partie :\n" + this.date
                + "-Le mot de cette partie :\n" + this.mot
                + "-Le niveau du mot :\n" + this.niveau
                + "-Le temps de cette partie : \n" + this.temps
                + "-Le pourcentage de la partie : \n" + this.trouve + "%";
    }
}