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
            if(genes_1.get(i).equals(genes_2.get(i)))
            {
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
    
}
