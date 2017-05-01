/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperadorMutacion;

import Modelos.Individuo;
import Modelos.IndividuoEscuela;
import Servicios.CargueDatos;
import algoritmo_base.Individual;

/**
 *
 * @author ingesis
 */
public class MutacionEscuela implements IMutacion {

    @Override
    public Individual Mutar(Individual individuo, CargueDatos datos) {

        IndividuoEscuela ind = (IndividuoEscuela) individuo;
        int genran1 = (int) (Math.random() * ind.genes.size());
        int genran2 = (int) (Math.random() * ind.genes.size());
        //System.out.println(datos.getMaterias().get(i).getNombreMateria())
        ind.getGenes().get(genran1).setMateria(datos.getMaterias().get((int) (Math.random() * datos.getMaterias().size())));
        ind.getGenes().get(genran2).setMateria(datos.getMaterias().get((int) (Math.random() * datos.getMaterias().size())));

        return ind;
    }

}
