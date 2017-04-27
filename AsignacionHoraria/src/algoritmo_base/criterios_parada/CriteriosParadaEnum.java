/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_base.criterios_parada;

/**
 * Clase de enumeración que contiene los tipos soportados de Criterios de Parada
 * para el algoritmo de Búsqueda Tabú
 * @author Camilo
 */
public enum CriteriosParadaEnum {
    /**
     * Indica que el algoritmo se ejecutará durante un número determinado de iteracioens
     */
    NUM_ITERACIONES,
    /**
     * Indica que el algoritmo dejará de ejecutarse cuando la mejor solución no
     * mejore en un número determinado de iteraciones
     */
    NUM_ITERACIONES_SIN_MEJORA,
    /**
     * Indica que el algoritmo dejará de ejecutarse cuando alguna solución tenga
     * una evaluación mejor que un valor de evaluación establecido. Para el caso
     * de minimización se considera que un valor es mejor cuando es menor, y para
     * maximización se considera que es mejor cuando es mayor
     */
    EVALUACION_OBJETIVO
}
