/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_base.criterios_parada;

import algoritmo_base.Individual;

/**
 * Clase que representa a una estrategia de parada según el número de iteraciones
 * transcurridas
 * @author Camilo
 */
public class StrategyParadaNumIteraciones implements StrategyParada{

    /**
     * Representa el número de iteraciones transcurridas
     */
    private int numIteraciones;
    /**
     * Representa el número máximo de iteraciones que se pueden dar
     */
    private final int maxIteraciones;
    
    /**
     * Crea una StrategyParadaNumIteraciones con un número máximo de iteraciones
     * @param maxIteraciones es el número máximo de iteraciones que se va a fijar
     */
    public StrategyParadaNumIteraciones(int maxIteraciones){
        numIteraciones = 0;
        this.maxIteraciones = maxIteraciones;
    }
    @Override
    public boolean debeParar(Individual bestSolution) {
        numIteraciones++;
        return (numIteraciones > maxIteraciones);
    }
    
}
