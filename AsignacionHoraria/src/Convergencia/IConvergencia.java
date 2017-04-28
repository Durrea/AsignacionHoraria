/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Convergencia;

import algoritmo_base.Individual;
import java.util.ArrayList;

/**
 *
 * @author ingesis
 */
public interface IConvergencia {
    
    public boolean Converge(ArrayList<Individual> individuos, ArrayList<Double> iteraciones, int numero, double VALOR_T);
    
}
