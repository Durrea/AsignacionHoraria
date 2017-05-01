/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusquedaLocal;

import AlgoritmoMemetico.Memetico;
import Modelos.Individuo;
import Modelos.IndividuoEscuela;
import algoritmo_base.Individual;

/**
 *
 * @author Eduardo
 */
public class BusquedaLocalEscuela implements IBUsquedaLocal{

    public int NUM_ITERACIONES;
    @Override
    public Individual LocalSearchEngine(Individual current, int numiteraciones) 
    {
        this.NUM_ITERACIONES = numiteraciones;
        int i = 0;
        Individual nuevo;
        Individual actual;

        actual = (IndividuoEscuela) current;

        while (i < this.NUM_ITERACIONES) {

            nuevo = (IndividuoEscuela) actual.getNeighbourhood(actual);
            
            if (nuevo.ObtenerEvaluacion() < actual.ObtenerEvaluacion()) {
                actual = nuevo;
            }
            i++;
        }
        return actual;
    }
        
}
    
