
package game;

import env3d.Env;
import java.util.ArrayList;


/**
 *
 * @author 33643
 */

public class JeuDevineLeMotOrdre extends Jeu{
    
    private final int TEMPSLIMITE = 70;

    
    private Chronometre chrono;
    
    public JeuDevineLeMotOrdre(){
        super();
        this.chrono = new Chronometre(TEMPSLIMITE);
    }
    
    @Override
    protected void démarrePartie(Partie partie){
        this.chrono.start();
        this.nbLettresRestantes = this.getMot().size();
    }
    
    
    protected void appliqueRegles(Partie partie) {
        if ( this.chrono.remainsTime() && this.nbLettresRestantes > 0 ) {
            if (this.tuxTrouveLettre()) {
                this.nbLettresRestantes--;
                Env env = getEnv();
                env.removeObject(this.getMot().get(0));
                getMot().remove(0);
            }
        } 
    }
    
    protected void terminePartie(Partie partie){
        this.chrono.stop();
        int temps = (int )this.chrono.getTime();
        if ( temps < TEMPSLIMITE )
            partie.setTemps(temps);
        partie.setTrouve(nbLettresRestantes);
        
    }
    
    private boolean tuxTrouveLettre(){
        boolean res = false;
        ArrayList<Letter> lettres = this.getMot();
        Tux tux = this.getTux();
        if ( !lettres.isEmpty() ){
            Letter l = lettres.get(0);
            double X =  l.getX()- tux.getX();
            double Z = l.getZ()- tux.getZ();
            if (Z < 2.3 && X < 2.3 && Z > -1.3 && X > -1.3){
                res = true;        
            }
        }
        return res;
    }
    
    private int getNbLettresRestantes(){
        return this.nbLettresRestantes;
    }
    
    private int getTemps(){
        return this.chrono.getSeconds();
    }
    
}