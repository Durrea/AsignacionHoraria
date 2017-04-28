/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_base.criterios_aspiracion;

import algoritmo_base.Individual;
import algoritmo_base.lista_tabu.TabuList;
import java.util.ArrayList;

/**
 * Clase que representa al criterio de aspiración por defecto
 * @author Camilo
 */
public class AspiracionPorDefault {
    
    /**
     * Permite obtener el Individuo aceptado en caso de que se use el criterio de
     * aspiración por defecto
     * @param currentSolution es la solución a partir de la cual se generó el vecindario
     * @param neighbourhood es el conjunto de individuos (vecindario) generado en
     * la iteración actual del algoritmo de Búsqueda Tabú
     * @param listaTabu es la lista tabú actual
     * @return el Individuo del vecindario al cual le queda menos tiempo para salir
     * de la lista tabú
     */
    public Individual getIndividuoAceptado(Individual currentSolution, ArrayList<Individual> neighbourhood, TabuList listaTabu){
        int i=0, posMenor, auxTiempo, menorTiempo = Integer.MAX_VALUE;
        for (Individual neighbour: neighbourhood){
            auxTiempo = listaTabu.tiempoTabu(currentSolution, neighbour);
            if (auxTiempo < menorTiempo){
                menorTiempo=auxTiempo;
                posMenor = i;
            }
            i++;
        }
        return neighbourhood.get(i);
    }
}
