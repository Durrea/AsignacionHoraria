/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_base.criterios_aspiracion;

import algoritmo_base.Individual;

/**
 * Inerfaz que representa una estrategia para verificar el criterio de aspiracion 
 * del algoritmo de Búsqueda Tabú
 * @author Camilo
 */
public interface StrategyAspiracion {
    
    /**
     * Permite evaluar si un individuo promesa pertenece a la lista de aspiración
     * de acuerdo a la estrategia definida
     * @param promisingSolution es la solución promesa, es decir, la que se desea
     * evaluar si pertenece o no a la lista de aspiración
     * @param bestSolution es el mejor individuo encontrado hasta el momento
     * @param currentSolution es el individuo que fue utilizado como base para
     * la generación del vecindario en la iteración actual
     * @param previousSolution es el individuo que fue utilizado como base para
     * la generación del vecindario en la iteración anterior
     * @return true si la solución promesa cumple con el criterio de aspiración
     * establecido en la estrategia, false en caso de que no lo cumpla
     */
    public boolean esAceptado(Individual promisingSolution, Individual bestSolution, 
            Individual currentSolution, Individual previousSolution);
}
