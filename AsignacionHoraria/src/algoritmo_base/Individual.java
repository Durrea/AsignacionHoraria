/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_base;

import java.util.ArrayList;

/**
 * Clase que representa un Individuo del Problema en el cual se realiza la búsqueda
 * @author Camilo
 */
public interface Individual {
    /**
     * Permite obtener el vecindario de éste Individuo. La forma de generar el
     * vecindario varía según el problema
     * @return el conjunto de individuos generados a partir del individuo actual
     * (el vecindario)
     */
    public Individual getNeighbourhood(Individual individuo);
    /**
     * Permite obtener la evaluación del individuo. La forma de calcular la evaluación
     * depende del problema estudiado
     * @return la evaluación del individuo
     */
    public double getEvaluacion();
    /**
     * Permite obtener el tamaño de un individuo, es decir, el tamaño del arreglo
     * que representa a una solución
     * @return el tamaño del arreglo que representa a un Individuo
     */
    public int getIndividualSize();
    /**
     * Permite obtener un valor del arreglo que representa a un Individuo
     * @param position es la posición en el arreglo que se desea obtener
     * @return el valor correspondiente al arreglo del Individuo que se encuentra
     * en la posición indicada
     */
    public double getValue(int position);
    /**
     * Permite crear una copia idéntica pero independiente del Individuo
     * @return una copia del individuo
     */
    public Individual clonar();
}
