/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_base.criterios_parada;

import algoritmo_base.Individual;

/**
 * Clase que representa a una estrategia de parada según un valor de evaluación
 * objetivo
 * @author Camilo
 */
public class StrategyParadaEvaluacionObjetivo implements StrategyParada{
    /**
     * Representa el valor de evaluación objetivo
     */
    private final double objetivo;
    /**
     * Indica si el problema es de maximización. Si el valor es false significa
     * que el problema es de minimización
     */
    private final boolean maximizacion;
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
     * Representa la mejorEvaluacion encontrada hasta el momento
     */
    private double mejorEvaluacion;
    
    /**
     * Crea un StrategyParadaEvaluacionObjetivo
     * @param objetivo es el valor objetivo para el criterio de parada
     * @param maximizacion indica si el problema es de maximización (true), o
     * de minimizacion (false)
     * @param maxIteraciones es el número máximo de iteraciones que se va a fijar
     */
    public StrategyParadaEvaluacionObjetivo(double objetivo, boolean maximizacion, int maxIteraciones){
        this.objetivo = objetivo;
        this.maximizacion = maximizacion;
        this.maxIteraciones = maxIteraciones;
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
        if (maximizacion){
            return (bestSolution.getEvaluacion() > objetivo || numIteraciones > maxIteraciones);
        } else {
            return (bestSolution.getEvaluacion() < objetivo || numIteraciones > maxIteraciones);
        }
    }
}
