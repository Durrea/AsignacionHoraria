/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperadorRecombinacion;

import Modelos.Gen;
import Modelos.Individuo;
import algoritmo_base.Individual;
import java.util.ArrayList;

/**
 *
 * @author Eduardo
 */
public class RTR implements Recombinacion
{
    
    @Override
    public Individual OperadorRecombinacion(Individual agente_a, Individual agente_b) 
    {
        Individuo agente_1 = (Individuo)agente_a;
        Individuo agente_2 = (Individuo)agente_b;
        
        ArrayList<Gen> genes_1 = agente_1.getGenes();
        ArrayList<Gen> genes_2 = agente_2.getGenes();
        
        ArrayList<Gen> resultado = new ArrayList();
        
        for (int i = 0; i < genes_1.size(); i++) 
        {
            if(CompareIndividual(genes_1.get(i), genes_2.get(i)))
            {
                System.out.println("Entro aqui padres iguales");
                resultado.add(genes_1.get(i));
            }
            else
            {
                int gen = (int)(Math.random()*2);
                if(gen == 0)
                {
                    resultado.add(genes_1.get(i));
                }
                else
                {
                    resultado.add(genes_2.get(i));
                }
            }
        }
        Individuo individuo_resultante = new Individuo(resultado);
        return individuo_resultante;
    }
    public boolean CompareIndividual(Gen a, Gen b)
    {
        //Individuo individuo_a = (Individuo) a;
        //Individuo individuo_b = (Individuo) b;
        //boolean resultado = false;
        int valor = 0;
        
        if(a.getMateria().getNombreMateria().equalsIgnoreCase(b.getMateria().getNombreMateria())&&
            a.getMateria().getGrupoMateria().equalsIgnoreCase(b.getMateria().getGrupoMateria()))
        {
            for (int j = 0; j < a.getHorarios().size(); j++) {
                if (a.getHorarios().get(j).getDia() == b.getHorarios().get(j).getDia()
                        && a.getHorarios().get(j).getFranja() == b.getHorarios().get(j).getFranja()
                        && a.getAulas().get(j).getIdsalon() == b.getAulas().get(j).getIdsalon()) 
                {
                    valor = valor + 1;
                }
            }
        }
        
            
        if(valor == a.getHorarios().size())
        {
            return true;
        }
        else
        {
            return false;
        }        
    }
}
