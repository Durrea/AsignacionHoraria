/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import algoritmo_base.Individual;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ingesis
 */
public class Individuo implements Individual {

    public ArrayList<Gen> genes;
    private double evaluacion;

    public Individuo(ArrayList<Gen> genes) {
        this.genes = genes;
        this.evaluacion = 0;
    }

    public Individuo() {

    }

    public ArrayList<Gen> getGenes() {
        return genes;
    }

    public void setGenes(ArrayList<Gen> genes) {
        this.genes = genes;
    }

    @Override
    public Individual getNeighbourhood(Individual individuo) {
        int size = this.genes.size();
        Individuo newSolution;
        ArrayList<Individual> neighbourhood = new ArrayList();
        for (int i = 0; i < size/4; i++) {
            /*for (int j = i + 1; j < size; j++) {

             newSolution = new Individuo((ArrayList<Gen>) this.genes.clone());
             ArrayList<FranjaHoraria> auxIntercambio = newSolution.getGenes().get(i).getHorarios();
             newSolution.getGenes().get(i).setHorarios(newSolution.getGenes().get(j).getHorarios());
             //newSolution.getGenes().set(i, newSolution.getGenes().get(j));
             newSolution.getGenes().get(j).setHorarios(auxIntercambio);
             //newSolution.getGenes().set(j, auxIntercambio);

             newSolution.getEvaluacion();
             neighbourhood.add(newSolution);
             }*/

            newSolution = new Individuo((ArrayList<Gen>) this.genes.clone());
            ArrayList<FranjaHoraria> auxIntercambio = newSolution.getGenes().get(i).getHorarios();
            int j = (int) (Math.random() * newSolution.genes.size());
            newSolution.getGenes().get(i).setHorarios(newSolution.getGenes().get(j).getHorarios());
            newSolution.getGenes().get(j).setHorarios(auxIntercambio);
            newSolution.getEvaluacion();
            neighbourhood.add(newSolution);

        }
        neighbourhood = OrdenarIndividuos(neighbourhood, 0, neighbourhood.size() - 1);
        return neighbourhood.get(0);
        //Collections.sort(neighbourhood,Individuo.this.evaluacion);
        //return neighbourhood;
    }

    public double ObtenerEvaluacion() {
        return this.evaluacion;
    }

    @Override
    public double getEvaluacion() {

        /*int rest1 = evaluarRestriccionSalonHora();
         int rest2 = evaluarRestriccionProfesorHora();
         int rest3 = evaluarRestriccionSemestreHora();
         int rest4 = evaluarRestriccionTipoMateria();*/
        int rest5 = evaluarRestricciones();
        this.evaluacion = rest5;

        /*if (rest5 == this.evaluacion) {
         System.out.println("Restricciones iguales");
         } else {
         System.out.println("Restricciones diferentess");
         }*/
        return this.evaluacion;
    }

    @Override
    public int getIndividualSize() {
        return this.genes.size();
    }

    @Override
    public double getValue(int position) {
        return this.genes.get(position).getValue();
    }

    @Override
    public Individual clonar() {

        return new Individuo(this.genes);
    }

    private int evaluarRestricciones() {
        int penalizacion = 0;

        for (int i = 0; i < this.genes.size(); i++) {

            for (int k = 0; k < genes.get(i).getAulas().size(); k++) {
                //Evaluar restriccion de Tipo
                if (!genes.get(i).getMateria().getTipoMateria().equalsIgnoreCase(genes.get(i).getAulas().get(k).getTipo())) {

                    penalizacion = penalizacion + 2;
                    //System.out.println(genes.get(i).getAulas().get(k).getTipo());
                    //System.out.println("Penalizacion TipoMateria");
                }
            }

            for (int j = i + 1; j < this.genes.size(); j++) {
                //Evaluar restriccion de Semestre
                if (genes.get(i).getMateria().getSemestre() == genes.get(j).getMateria().getSemestre()) {
                    for (int k = 0; k < 2; k++) {
                        if (genes.get(i).getHorarios().get(k).getDia() == genes.get(j).getHorarios().get(k).getDia()) {
                            if (genes.get(i).getHorarios().get(k).getFranja() == genes.get(j).getHorarios().get(k).getFranja()) {
                                penalizacion = penalizacion + 2;
                                //System.out.println("Penalizacion SemestreHora");
                            }
                        }
                    }
                }
                //Evaluar restriccion de Docente
                if (genes.get(i).getMateria().getPosDocente() == genes.get(j).getMateria().getPosDocente()) {
                    for (int k = 0; k < 2; k++) {
                        if (genes.get(i).getHorarios().get(k).getDia() == genes.get(j).getHorarios().get(k).getDia()) {
                            if (genes.get(i).getHorarios().get(k).getFranja() == genes.get(j).getHorarios().get(k).getFranja()) {
                                penalizacion = penalizacion + 5;
                                //System.out.println("Penalizacion ProfesorHora");
                            }
                        }
                    }
                }
                //Evaluar restriccion hora
                for (int k = 0; k < 2; k++) {
                    if (genes.get(i).getHorarios().get(k).getDia() == genes.get(j).getHorarios().get(k).getDia()) {
                        if (genes.get(i).getHorarios().get(k).getFranja() == genes.get(j).getHorarios().get(k).getFranja()) {
                            if (genes.get(i).getAulas().get(k).getIdsalon() == genes.get(j).getAulas().get(k).getIdsalon()) {
                                penalizacion = penalizacion + 5;
                                //System.out.println("Penalizacion SalonHora");
                            }
                        }
                    }
                }
            }
        }

        return penalizacion;
    }

    private int evaluarRestriccionSalonHora() {

        int penalizacion = 0; //Aumento de penalización -> 5

        for (int i = 0; i < this.genes.size() - 1; i++) {
            for (int j = i + 1; j < this.genes.size(); j++) {

                for (int k = 0; k < 2; k++) {
                    if (genes.get(i).getHorarios().get(k).getDia() == genes.get(j).getHorarios().get(k).getDia()) {
                        if (genes.get(i).getHorarios().get(k).getFranja() == genes.get(j).getHorarios().get(k).getFranja()) {
                            if (genes.get(i).getAulas().get(k).getIdsalon() == genes.get(j).getAulas().get(k).getIdsalon()) {
                                penalizacion = penalizacion + 5;
                                //System.out.println("Penalizacion SalonHora");
                            }
                        }
                    }
                }
            }
        }

        return penalizacion;
    }

    private int evaluarRestriccionProfesorHora() {
        int penalizacion = 0; //Aumento de penalización -> 5

        for (int i = 0; i < this.genes.size() - 1; i++) {
            for (int j = i + 1; j < this.genes.size(); j++) {

                if (genes.get(i).getMateria().getPosDocente() == genes.get(j).getMateria().getPosDocente()) {
                    for (int k = 0; k < 2; k++) {
                        if (genes.get(i).getHorarios().get(k).getDia() == genes.get(j).getHorarios().get(k).getDia()) {
                            if (genes.get(i).getHorarios().get(k).getFranja() == genes.get(j).getHorarios().get(k).getFranja()) {
                                penalizacion = penalizacion + 5;
                                //System.out.println("Penalizacion ProfesorHora");
                            }
                        }
                    }
                }
            }
        }

        return penalizacion;
    }

    private int evaluarRestriccionSemestreHora() {
        int penalizacion = 0; //Aumento de penalización -> 5

        for (int i = 0; i < this.genes.size() - 1; i++) {
            for (int j = i + 1; j < this.genes.size(); j++) {

                if (genes.get(i).getMateria().getSemestre() == genes.get(j).getMateria().getSemestre()) {
                    for (int k = 0; k < 2; k++) {
                        if (genes.get(i).getHorarios().get(k).getDia() == genes.get(j).getHorarios().get(k).getDia()) {
                            if (genes.get(i).getHorarios().get(k).getFranja() == genes.get(j).getHorarios().get(k).getFranja()) {
                                penalizacion = penalizacion + 2;
                                //System.out.println("Penalizacion SemestreHora");
                            }
                        }
                    }

                }
            }
        }

        return penalizacion;
    }

    private int evaluarRestriccionTipoMateria() {
        int penalizacion = 0; //Aumento de penalización -> 5

        for (int i = 0; i < this.genes.size(); i++) {

            for (int k = 0; k < genes.get(i).getAulas().size(); k++) {
                if (!genes.get(i).getMateria().getTipoMateria().equalsIgnoreCase(genes.get(i).getAulas().get(k).getTipo())) {

                    penalizacion = penalizacion + 2;
                    //System.out.println(genes.get(i).getAulas().get(k).getTipo());
                    //System.out.println("Penalizacion TipoMateria");
                }
            }
        }

        return penalizacion;
    }

    public ArrayList OrdenarIndividuos(ArrayList<Individual> individuos, int izq, int der) {
        //individuos = (ArrayList<Individuo>) individuos;
        Individuo pivote = (Individuo) individuos.get(izq); // tomamos primer elemento como pivote
        int i = izq; // i realiza la búsqueda de izquierda a derecha
        int j = der; // j realiza la búsqueda de derecha a izquierda
        Individuo ind;
        int aux;

        while (i < j) {            // mientras no se crucen las búsquedas
            while (individuos.get(i).getEvaluacion() <= pivote.getEvaluacion() && i < j) {
                i++; // busca elemento mayor que pivote
            }
            while (individuos.get(j).getEvaluacion() > pivote.getEvaluacion()) {
                j--;         // busca elemento menor que pivote
            }
            if (i < j) {
                ind = (Individuo) individuos.get(i); // si no se han cruzado                      
                //aux= A[i];                  // los intercambia
                individuos.set(i, individuos.get(j));
                //A[i]=A[j];
                individuos.set(j, ind);
                //A[j]=aux;
            }
        }
        individuos.set(izq, individuos.get(j));
        //A[izq]=A[j]; // se coloca el pivote en su lugar de forma que tendremos
        individuos.set(j, pivote);
        //A[j]=pivote; // los menores a su izquierda y los mayores a su derecha
        if (izq < j - 1) {
            OrdenarIndividuos(individuos, izq, j - 1); // ordenamos subarray izquierdo
        }
        if (j + 1 < der) {
            OrdenarIndividuos(individuos, j + 1, der); // ordenamos subarray derecho
        }
        return individuos;
    }

    @Override
    public ArrayList getNeighbourhood() {
        
        int size = this.genes.size();
        Individuo newSolution;
        ArrayList<Individuo> neighbourhood = new ArrayList();
        for (int i = 0; i < size; i++) {
            /*for (int j = i + 1; j < size; j++) {

             newSolution = new Individuo((ArrayList<Gen>) this.genes.clone());
             ArrayList<FranjaHoraria> auxIntercambio = newSolution.getGenes().get(i).getHorarios();
             newSolution.getGenes().get(i).setHorarios(newSolution.getGenes().get(j).getHorarios());
             //newSolution.getGenes().set(i, newSolution.getGenes().get(j));
             newSolution.getGenes().get(j).setHorarios(auxIntercambio);
             //newSolution.getGenes().set(j, auxIntercambio);

             newSolution.getEvaluacion();
             neighbourhood.add(newSolution);
             }*/

            newSolution = new Individuo((ArrayList<Gen>) this.genes.clone());
            ArrayList<FranjaHoraria> auxIntercambio = newSolution.getGenes().get(i).getHorarios();
            int j = (int) (Math.random() * newSolution.genes.size());
            newSolution.getGenes().get(i).setHorarios(newSolution.getGenes().get(j).getHorarios());
            newSolution.getGenes().get(j).setHorarios(auxIntercambio);
            newSolution.getEvaluacion();
            neighbourhood.add(newSolution);

        }
        return neighbourhood;
    }
    
}
