/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.util.ArrayList;

/**
 *
 * @author ingesis
 */
public class Individuo {
    
    private ArrayList<Gen> genes;
    private double adaptacion;

    public Individuo(ArrayList<Gen> genes, double adaptacion) {
        this.genes = genes;
        this.adaptacion = adaptacion;
    }

    public Individuo() {
        
    }

    public ArrayList<Gen> getGenes() {
        return genes;
    }

    public void setGenes(ArrayList<Gen> genes) {
        this.genes = genes;
    }

    public double getAdaptacion() {
        return adaptacion;
    }

    public void setAdaptacion(double adaptacion) {
        this.adaptacion = adaptacion;
    }

    
    
}
