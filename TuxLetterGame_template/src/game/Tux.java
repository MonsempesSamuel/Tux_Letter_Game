/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
import env3d.advanced.EnvNode;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author monsemps
 */
public class Tux extends EnvNode {

    private final Env env;
    private final Room room;

    public Tux(Env env, Room room) {
        this.env = env;
        this.room = room;
        setScale(4.0);
        setX(50);// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(50); // positionnement au milieu de la profondeur de la room
        setTexture("models/tux/tux_angry.png");
        setModel("models/tux/tux.obj");
    }

    public void déplace() {
        if (env.getKeyDown(Keyboard.KEY_Z) || env.getKeyDown(Keyboard.KEY_UP)) { // Fleche 'haut' ou Z
            // Haut    
            this.setRotateY(180);
            if (this.getZ() >= 0) {
                this.setZ(this.getZ() - 1.0);
            } else {
                this.setZ(100);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_Q) || env.getKeyDown(Keyboard.KEY_LEFT)) { // Fleche 'gauche' ou Q
            // Gauche         
            this.setRotateY(270);
            if (this.getX() >= 0) {
                this.setX(this.getX() - 1.0);
            } else {
                this.setX(100);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_D) || env.getKeyDown(Keyboard.KEY_RIGHT)) { // Fleche 'droite' ou D
            // Droite       
            this.setRotateY(90);
            if (this.getX() <= 100) {
                this.setX(this.getX() + 1.0);
            } else {
                this.setX(0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_S) || env.getKeyDown(Keyboard.KEY_DOWN)) { // Fleche 'Bas' ou S
            // Bas  
            this.setRotateY(0);
            if (this.getZ() <= 100) {
                this.setZ(this.getZ() + 1.0);
            } else {
                this.setZ(0);
            }
        }
    }
}
