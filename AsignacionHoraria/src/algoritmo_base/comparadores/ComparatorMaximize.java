/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_base.comparadores;

import algoritmo_base.Individual;
import java.util.Comparator;

/**
 * Clase que implementa un comparador de Individuos, para problemas de minimización
 * @author Camilo
 */
public class ComparatorMaximize implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
       Individual i1 = (Individual) o1;
       Individual i2 = (Individual) o2;
       return (Double.compare(i2.getEvaluacion(), i1.getEvaluacion())); //Es mejor el que tenga mayor evaluación
    }
    
}
