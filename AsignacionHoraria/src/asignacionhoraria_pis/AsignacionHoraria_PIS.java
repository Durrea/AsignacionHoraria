/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asignacionhoraria_pis;

import AlgoritmoMemetico.Memetico;
import Modelos.Gen;
import Modelos.Individuo;
import Modelos.IndividuoEscuela;
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
        memetico.PROBLEMA = 1;
        memetico.NUM_REPETICIONES = 10;
        memetico.NUM_HIJOS = 10;
        memetico.ENTROPIA_ANTERIOR = 0;
        memetico.poblacionSize = 10;
        memetico.NUM_REPETICIONES_LOCAL = 10;
        memetico.PRESERVE = 0.30;
        memetico.ejecutar();
        //ArrayList<Individuo> poblacion = new ArrayList<Individuo>();
        //poblacion = memetico.generarPoblacionInicial();
        //Individuo ind = new Individuo();
        //poblacion = ind.OrdenarIndividuos(poblacion, 0, poblacion.size()-1);
        if (memetico.PROBLEMA == 0) {
            /*for (int i = 0; i < memetico.poblacion.size(); i++) {
             Individuo ind = (Individuo) memetico.poblacion.get(i);
             System.out.println(ind.ObtenerEvaluacion());
             }*/
            Individuo ind = (Individuo) memetico.poblacion.get(0);
            System.out.println("Evaluacion: " + ind.ObtenerEvaluacion());
            System.out.println("***********************************************");
            for (int i = 0; i < ind.genes.size(); i++) {
                System.out.println("Materia:" + ind.genes.get(i).getMateria().getNombreMateria());
                System.out.println("#Docente:" + ind.genes.get(i).getMateria().getPosDocente());
                System.out.println("Semestre:" + ind.genes.get(i).getMateria().getSemestre());
                for (int j = 0; j < ind.genes.get(i).getHorarios().size(); j++) {
                    System.out.println("Horario:" + ind.genes.get(i).getHorarios().get(j).getDia() + "-" + ind.genes.get(i).getHorarios().get(j).getFranja());
                    System.out.println("Salon: " + ind.genes.get(i).getAulas().get(j).getNombreAula());
                }
                System.out.println("***********************************************");
            }
        } else {
            /*for (int i = 0; i < memetico.poblacion.size(); i++) {
             IndividuoEscuela ind = (IndividuoEscuela) memetico.poblacion.get(i);
             System.out.println(ind.ObtenerEvaluacion());
             }*/
            IndividuoEscuela ind = (IndividuoEscuela) memetico.poblacion.get(0);
            System.out.println("Evaluacion: " + ind.ObtenerEvaluacion());
            System.out.println("***********************************************");
            for (int i = 0; i < 60; i++) {
             System.out.println(ind.genes.get(i).getMateria().getNombreMateria());

             }
        }

        /*for (int i = 0; i < memetico.poblacion.size(); i++) 
         {
         Individuo ind = (Individuo) memetico.poblacion.get(i);
         System.out.println(ind.ObtenerEvaluacion());
         }*/
        /*while (true) {            
         System.out.println((int)(Math.random()*2));    
         }*/
        /*for (int i = 0; i < docentes.size(); i++) 
         {
         System.out.println(docentes.get(i).getNombreDocente());
         }*/
    }

}
