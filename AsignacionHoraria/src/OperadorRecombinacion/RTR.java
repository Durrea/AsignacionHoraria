/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperadorRecombinacion;

import Modelos.Gen;
import Modelos.Individuo;
import java.util.ArrayList;

/**
 *
 * @author Eduardo
 */
public class RTR implements Recombinacion
{
    
    @Override
    public Object OperadorRecombinacion(Object agente_a, Object agente_b) 
    {
        Individuo agente_1 = (Individuo)agente_a;
        Individuo agente_2 = (Individuo)agente_b;
        
        ArrayList<Gen> genes_1 = agente_1.getGenes();
        ArrayList<Gen> genes_2 = agente_2.getGenes();
        
        ArrayList<Gen> resultado = new ArrayList();
        
        for (int i = 0; i < genes_1.size(); i++) 
        {
            if(genes_1.get(i).getCosto() == genes_2.get(i).getCosto())
            {
                resultado.add(genes_1.get(i));
            }
            else
            {
                
            }
        }
        Individuo individuo_resultante = new Individuo();
        individuo_resultante.setGenes(resultado);
        return individuo_resultante;
    }
    
}
