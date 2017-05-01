/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Convergencia;

import Modelos.Individuo;
import Modelos.IndividuoEscuela;
import algoritmo_base.Individual;
import java.util.ArrayList;

/**
 *
 * @author Eduardo
 */
public class ConvergenciaEscuela implements IConvergencia{

    @Override
    public boolean Converge(ArrayList<Individual> miPoblacion, ArrayList<Double> iteraciones, int numero, double VALOR_T) 
    {
        int sumaAdaptacion = 0;
        double entropiaSuma = 0;
        ArrayList<Double> probabilidades = new ArrayList<>();
        ArrayList<Double> entropia = new ArrayList<>();

        for (int i = 0; i < miPoblacion.size(); i++) {
            IndividuoEscuela ind = (IndividuoEscuela)miPoblacion.get(i);
            sumaAdaptacion += ind.ObtenerEvaluacion();
        }

        for (int i = 0; i < miPoblacion.size(); i++) {
            IndividuoEscuela ind = (IndividuoEscuela)miPoblacion.get(i);
            probabilidades.add(ind.ObtenerEvaluacion() / sumaAdaptacion);
        }

        for (int i = 0; i < miPoblacion.size(); i++) {
            entropia.add(probabilidades.get(i) * Math.log10(probabilidades.get(i)));
            entropiaSuma += entropia.get(i);
        }
        iteraciones.add(numero, entropiaSuma);

        if (numero == 0) {
            return false;
        } else {
            if (iteraciones.get(numero - 1) - iteraciones.get(numero) < VALOR_T) {
                return true;
            }
        }
        return false;
    } 
}
   
