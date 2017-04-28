/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_base;

import algoritmo_base.lista_tabu.TabuList;
import algoritmo_base.criterios_aspiracion.AspiracionPorDefault;
import algoritmo_base.criterios_parada.StrategyParada;
import algoritmo_base.criterios_aspiracion.StrategyAspiracion;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Clase que representa a la Búsqueda Tabú
 * @author Camilo
 */
public class TabuSearch {
    
    /**
     * Estrategia que será aplicada para el criterio de aspiracion
     */
    private StrategyAspiracion estrategiaAspiracion;
    /**
     * Estrategia que será aplicada para el criterio de parada
     */
    private StrategyParada estrategiaParada;
    /**
     * Criterio de aspiración por defecto
     */
    private AspiracionPorDefault aspiracionPorDefecto;
    /**
     * Es la lista tabú que usa el algoritmo
     */
    private TabuList listaTabu;
    /**
     * Comparador de individuos. Cambia para problemas de maximización y minimización
     */
    private Comparator comparador;
    
    /**
     * Permite aplicar el algoritmo de búsqueda tabú
     * @param configuration corresponde a la configuración con la cual va a ser
     * ejecutado el algoritmo
     * @param seed es una solución semilla que se le provee al algoritmo
     * @return el Individuo con la mejor evaluación al momento de cumplir el
     * criterio de parada establecido en la configuración
     */
    public Individual tabuSearch(ConfiguracionTabuSearch configuration, Individual seed){
        
        //Configuración y declaración de variables
        configuration.aplicarConfiguración(this);
        //seed.getEvaluacion();
        Individual currentSolution = seed;
        Individual bestSolution = seed;
        Individual previousSolution = seed;
        Individual promisingSolution;
        listaTabu.createTabuList(seed.getIndividualSize());
        
        while (!estrategiaParada.debeParar(bestSolution)){
            promisingSolution = null;
            //Se genera el vecindario y se lo ordena según el valor de la evaluación
            ArrayList<Individual> neighbourhood = currentSolution.getNeighbourhood();
            neighbourhood.sort(comparador);
            //Se obtiene la mejor solución que no sea tabú
            for (Individual neighbour : neighbourhood){
                if (!listaTabu.isTabu(neighbour, currentSolution) 
                        || (estrategiaAspiracion!=null && estrategiaAspiracion.esAceptado(neighbour, bestSolution, currentSolution, previousSolution))){
                    promisingSolution = neighbour.clonar();
                    break;
                }
            }
            //En caso de que todo el vecindario sea tabú se aplica el criterio
            //de aspiración por defecto
            if (promisingSolution==null)
                promisingSolution = aspiracionPorDefecto.getIndividuoAceptado(currentSolution ,neighbourhood, listaTabu);
            //Se actualiza la lista tabú
            listaTabu.actualizar(promisingSolution, currentSolution);
            //Se actualizan las referencias a las soluciones
            previousSolution = currentSolution.clonar();
            currentSolution = promisingSolution.clonar();
            //Se actualiza el mejor, si la nueva solución es mejor
            if (comparador.compare(promisingSolution, bestSolution) < 0){
                bestSolution = promisingSolution.clonar();
            }
        }
        return bestSolution;
    }
    
    /**
     * Permite fijar la estrategia para el criterio de aspiración
     * @param estrategiaAspiracion es la estrategia ha ser usada para el criterio
     * de aspiración
     */
    public void setEstrategiaAspiracion(StrategyAspiracion estrategiaAspiracion){
        this.estrategiaAspiracion = estrategiaAspiracion;
    }
    
    /**
     * Permite fijar la estrategia para el criterio de parada
     * @param estrategiaParada es la estrategia ha ser usada para el criterio
     * de parada
     */
    public void setEstrategiaParada(StrategyParada estrategiaParada){
        this.estrategiaParada = estrategiaParada;
    }
    
    /**
     * Permite fijar la lista tabú que va a ser usada
     * @param listaTabu es la lista tabú que será usada por el algoritmo
     */
    public void setListaTabu(TabuList listaTabu){
        this.listaTabu = listaTabu;
    }
    
    /**
     * Permite fijar un comparador de individuos
     * @param comparador es el que comparador que será usado
     */
    public void setComparador(Comparator comparador){
        this.comparador = comparador;
    }
    
    /**
     * Permite fijar la estrategia de aspiración por defecto
     * @param aspiracionPorDefecto es el criterio de aspiración por defecto a ser
     * usado
     */
    public void setAspiracionPorDefecto(AspiracionPorDefault aspiracionPorDefecto){
        this.aspiracionPorDefecto = aspiracionPorDefecto;
    }
}
