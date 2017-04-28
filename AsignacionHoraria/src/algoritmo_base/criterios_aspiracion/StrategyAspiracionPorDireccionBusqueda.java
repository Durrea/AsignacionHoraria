/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_base.criterios_aspiracion;

import algoritmo_base.Individual;

/**
 * Clase que representa un criterio de aspiración en el cual la solución promesa
 * mejora a la solución actual, y a su vez la solución actual mejora a la
 * solución anterior
 * @author Camilo
 */
public class StrategyAspiracionPorDireccionBusqueda implements StrategyAspiracion{

    /**
     * Indica si el problema es de maximización. Si su valor es false significa
     * que el problema es de minimización
     */
    private final boolean maximizacion;
    
    /**
     * Crea un StrategyAspiracionPorODireccionBusqueda
     * @param maximizacion indica si el problema es de maximización (true), o
     * de minimizacion (false)
     */
    public StrategyAspiracionPorDireccionBusqueda(boolean maximizacion){
        this.maximizacion = maximizacion;
    }
    
    @Override
    public boolean esAceptado(Individual promisingSolution, Individual bestSolution, Individual currentSolution, Individual previousSolution) {
        if (maximizacion)
            return (promisingSolution.getEvaluacion() > currentSolution.getEvaluacion()
                    && currentSolution.getEvaluacion() > previousSolution.getEvaluacion());
        else
            return (promisingSolution.getEvaluacion() < bestSolution.getEvaluacion()
                    && currentSolution.getEvaluacion() < previousSolution.getEvaluacion());
    }
    
}
