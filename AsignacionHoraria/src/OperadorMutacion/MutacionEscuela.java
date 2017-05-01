/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperadorMutacion;

import Modelos.Individuo;
import Modelos.IndividuoEscuela;
import Modelos.InfoMaterias;
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
        InfoMaterias materia1 = datos.getMaterias().get((int) (Math.random() * datos.getMaterias().size()));
        InfoMaterias materia2 = datos.getMaterias().get((int) (Math.random() * datos.getMaterias().size()));
        while (materia1.getNombreMateria().equals(materia2.getNombreMateria()))
        {            
        materia2 = datos.getMaterias().get((int) (Math.random() * datos.getMaterias().size()));    
        }
        //System.out.println(datos.getMaterias().get(i).getNombreMateria())
        //System.out.println(materia1.getNombreMateria());
        //System.out.println(materia2.getNombreMateria());
        ind.getGenes().get(genran1).setMateria(materia1);
        ind.getGenes().get(genran2).setMateria(materia2);

        return ind;
    }

}
