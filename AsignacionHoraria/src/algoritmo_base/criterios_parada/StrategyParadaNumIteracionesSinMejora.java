/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_base.criterios_parada;

import algoritmo_base.Individual;

/**
 * Clase que representa a una estrategia de parada según el número de iteraciones
 * transcurridas en las cuales no ha habido una mejora de la solucion
 * @author Camilo
 */
public class StrategyParadaNumIteracionesSinMejora implements StrategyParada {

   /**
     * Representa el número de iteraciones transcurridas sin que haya mejora
     */
    private int numIteraciones;
    /**
     * Representa el número máximo de iteraciones que se pueden dar sin que haya
     * una mejora
     */
    private final int maxIteraciones;
    /**
     * Indica si el problema es de maximización. Si el valor es false significa
     * que el problema es de minimización
     */
    private boolean maximizacion;
    /**
     * Representa la mejorEvaluacion encontrada hasta el momento
     */
    private double mejorEvaluacion;
    
    /**
     * Crea una StrategyParadaNumIteracionesSinMejora con un número máximo de 
     * iteraciones
     * @param maxIteraciones es el número máximo de iteraciones que se va a fijar
     * @param maximizacion indica si el problema es de maximización (true), o
     * de minimizacion (false)
     */
    public StrategyParadaNumIteracionesSinMejora(int maxIteraciones, boolean maximizacion){
        numIteraciones = 0;
        this.maxIteraciones = maxIteraciones;
        this.maximizacion = maximizacion;
    }
    @Override
    public boolean debeParar(Individual bestSolution) {
        if (numIteraciones==0) 
            this.mejorEvaluacion = bestSolution.getEvaluacion();
        else{
            if ((maximizacion && bestSolution.getEvaluacion() > this.mejorEvaluacion)
                    || (!maximizacion && bestSolution.getEvaluacion() < this.mejorEvaluacion)){
                this.mejorEvaluacion = bestSolution.getEvaluacion();
                numIteraciones = 0;
            }
        }
        numIteraciones++;
        return (numIteraciones > maxIteraciones);
    }
    
}
