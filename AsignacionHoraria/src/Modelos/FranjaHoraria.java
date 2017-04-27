/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author ingesis
 */
public class FranjaHoraria {
    
    private int franja;
    private int dia;

    public FranjaHoraria(int franja, int dia) {
        this.franja = franja;
        this.dia = dia;
    }

    public int getFranja() {
        return franja;
    }

    public void setFranja(int franja) {
        this.franja = franja;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
    
    

}
