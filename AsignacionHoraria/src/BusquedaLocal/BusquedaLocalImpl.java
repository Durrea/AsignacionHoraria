/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusquedaLocal;

import Modelos.Individuo;
import algoritmo_base.Individual;

/**
 *
 * @author Eduardo
 */
public class BusquedaLocalImpl implements IBUsquedaLocal {

    public int NUM_ITERACIONES;

    public BusquedaLocalImpl() {
        this.NUM_ITERACIONES = 10;
    }

    @Override
    public Individual LocalSearchEngine(Individual current) {
        int i = 0;
        Individuo nuevo;
        Individuo actual = (Individuo) current;
        while (i < this.NUM_ITERACIONES) {
            nuevo = (Individuo) actual.getNeighbourhood(actual);
            if (nuevo.ObtenerEvaluacion() < actual.ObtenerEvaluacion()) {
                actual = nuevo;
            }
            i++;
        }
        return actual;
    }

}
