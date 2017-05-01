/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperadorMutacion;

import Modelos.Individuo;
import Servicios.CargueDatos;
import algoritmo_base.Individual;

/**
 *
 * @author ingesis
 */
public class MutacionEscuela implements IMutacion{

    @Override
    public Individual Mutar(Individual individuo, CargueDatos datos) {
        
        Individuo ind = (Individuo) individuo;
        int genran = (int) (Math.random() * ind.getGenes().size());
        //System.out.println(datos.getMaterias().get(i).getNombreMateria())

        int primerHorario = (int) (Math.random() * datos.getFranjas().size());
        int segundoHorario = (int) (Math.random() * datos.getFranjas().size());
        while (datos.getFranjas().get(primerHorario).getDia() == datos.getFranjas().get(segundoHorario).getDia()) {
            segundoHorario = (int) (Math.random() * datos.getFranjas().size());
        }

        if (datos.getFranjas().get(primerHorario).getDia() < datos.getFranjas().get(segundoHorario).getDia()) {
            ind.getGenes().get(genran).getHorarios().set(0, datos.getFranjas().get(primerHorario));
            ind.getGenes().get(genran).getHorarios().set(1, datos.getFranjas().get(segundoHorario));
        } else {
            ind.getGenes().get(genran).getHorarios().set(0, datos.getFranjas().get(segundoHorario));
            ind.getGenes().get(genran).getHorarios().set(1, datos.getFranjas().get(primerHorario));
        }

        return individuo;
    }
    
}
