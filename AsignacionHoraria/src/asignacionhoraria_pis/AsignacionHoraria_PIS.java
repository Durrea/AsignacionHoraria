/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asignacionhoraria_pis;

import AlgoritmoMemetico.Memetico;
import Modelos.Gen;
import Modelos.Individuo;
import Modelos.InfoDocente;
import Modelos.InfoMaterias;
import Servicios.CargueDatos;
import java.util.ArrayList;

/**
 *
 * @author ingesis
 */
public class AsignacionHoraria_PIS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //CargueDatos c = new CargueDatos();
        //c.CargarDatos();
        //ArrayList<InfoMaterias> materias = c.getMaterias();
        //ArrayList<InfoDocente> docentes = c.getDocentes();
        //materias = c.CargarDatosMaterias();
        //for (int i = 0; i < materias.size(); i++) 
        //{
        //    if(materias.get(i).getPosDocente() == -1)
        //    {
        //    System.out.println(materias.get(i).getNombreMateria()+" "+materias.get(i).getGrupoMateria()+" "+materias.get(i).getPosDocente());
        //    }
        //}
        
        Memetico memetico = new Memetico();
        memetico.NUM_REPETICIONES = 20;
        memetico.poblacionSize = 30;
        memetico.ejecutar();
        //ArrayList<Individuo> poblacion = new ArrayList<Individuo>();
        //poblacion = memetico.generarPoblacionInicial();
        //Individuo ind = new Individuo();
        //poblacion = ind.OrdenarIndividuos(poblacion, 0, poblacion.size()-1);
        
        for (int i = 0; i < memetico.poblacion.size(); i++) 
        {
            System.out.println(memetico.poblacion.get(i).ObtenerEvaluacion());
        }
        
        /*while (true) {            
        System.out.println((int)(Math.random()*2));    
        }*/
        
        /*for (int i = 0; i < docentes.size(); i++) 
        {
            System.out.println(docentes.get(i).getNombreDocente());
        }*/
    }
    
}
