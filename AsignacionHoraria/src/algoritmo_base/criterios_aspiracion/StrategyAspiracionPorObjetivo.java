/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_base.criterios_aspiracion;

import algoritmo_base.Individual;

/**
 * Clase que representa un criterio de aspiración en el cual la solución promesa
 * mejora a la mejor solución disponible
 * @author Camilo
 */
public class StrategyAspiracionPorObjetivo implements StrategyAspiracion{

    /**
     * Indica si el problema es de maximización. Si su valor es false significa
     * que el problema es de minimización
     */
    private final boolean maximizacion;
    
    /**
     * Crea un StrategyAspiracionPorObjetivo
     * @param maximizacion indica si el problema es de maximización (true), o
     * de minimizacion (false)
     */
    public StrategyAspiracionPorObjetivo(boolean maximizacion){
        this.maximizacion = maximizacion;
    }
    
    @Override
    public boolean esAceptado(Individual promisingSolution, Individual bestSolution, Individual currentSolution, Individual previousSolution) {
        if (maximizacion)
            return (promisingSolution.getEvaluacion() > bestSolution.getEvaluacion());
        else
            return (promisingSolution.getEvaluacion() < bestSolution.getEvaluacion());
    }
    
}
