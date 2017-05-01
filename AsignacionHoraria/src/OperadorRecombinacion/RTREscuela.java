/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperadorRecombinacion;

import Modelos.Gen;
import Modelos.GenEscuela;
import Modelos.Individuo;
import Modelos.IndividuoEscuela;
import algoritmo_base.Individual;
import java.util.ArrayList;

/**
 *
 * @author Eduardo
 */
public class RTREscuela implements Recombinacion {

    @Override
    public Individual OperadorRecombinacion(Individual agente_a, Individual agente_b) {
        IndividuoEscuela agente_1 = (IndividuoEscuela) agente_a;
        IndividuoEscuela agente_2 = (IndividuoEscuela) agente_b;

        ArrayList<GenEscuela> genes_1 = agente_1.genes;
        ArrayList<GenEscuela> genes_2 = agente_2.genes;

        ArrayList<GenEscuela> resultado = new ArrayList();

        for (int i = 0; i < genes_1.size(); i++) {
            if (CompareIndividual(genes_1.get(i), genes_2.get(i))) {
                //System.out.println("Entro aqui padres iguales");
                resultado.add(genes_1.get(i));
            } else {
                //System.out.println("Entro aqui padres diferents");
                int gen = (int) (Math.random() * 2);
                if (gen == 0) {
                    resultado.add(genes_1.get(i));
                } else {
                    resultado.add(genes_2.get(i));
                }
            }
        }
        IndividuoEscuela individuo_resultante = new IndividuoEscuela(resultado);
        return individuo_resultante;
    }

    public boolean CompareIndividual(GenEscuela a, GenEscuela b) {
        //Individuo individuo_a = (Individuo) a;
        //Individuo individuo_b = (Individuo) b;
        //boolean resultado = false;
        int valor = 0;

        if (a.getMateria().getNombreMateria().equalsIgnoreCase(b.getMateria().getNombreMateria())) {
            return true;
        }
        return false;

    }
}
