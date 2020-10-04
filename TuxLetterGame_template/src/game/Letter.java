/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.EnvObject;

/**
 *
 * @author 33643
 */
public class Letter extends EnvObject {

    private char letter;

    public Letter(char l, double x, double z) {
        this.letter = l;
        this.setX(x);
        this.setZ(z);
        setScale(1.5);
        this.setY(getScale() * 1.1);
        if (l == ' ') {
            setTexture("models/cube/cube.png");
            setModel("models/cube/cube.obj");
        } else {
            setTexture("models/letter/" + l + ".png");
            setModel("models/cube/cube.obj");
        }
    }
}
