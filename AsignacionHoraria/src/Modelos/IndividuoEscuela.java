/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Servicios.CargueDatos;
import algoritmo_base.Individual;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ingesis
 */
public class IndividuoEscuela implements Individual {

    public double evaluacion;
    private int TOTAL_FRANJAS = 60;
    public ArrayList<GenEscuela> genes = new ArrayList<GenEscuela>();

    public IndividuoEscuela() {
    }

    public IndividuoEscuela(ArrayList<GenEscuela> genes) {
        this.genes = genes;
        this.evaluacion = 0;
    }

    @Override
    public double ObtenerEvaluacion() {
        return this.evaluacion;
    }

    @Override
    public Individual getNeighbourhood(Individual individuo) {
        int size = (int) (this.genes.size());
        IndividuoEscuela newSolution;
        ArrayList<Individual> neighbourhood = new ArrayList();
        newSolution = new IndividuoEscuela((ArrayList<GenEscuela>) this.genes.clone());

        for (int i = 0; i < size / 4; i++) {

            int a = (int) (Math.random() * this.genes.size());
            int b = (int) (Math.random() * this.genes.size());
            newSolution.genes.get(a).setMateria(newSolution.genes.get(b).getMateria());
            newSolution.getEvaluacion();
            neighbourhood.add(newSolution);
        }
        //neighbourhood = OrdenarIndividuos(neighbourhood, 0, neighbourhood.size() - 1);
        neighbourhood = OrdenarIndividuos(neighbourhood, 0, neighbourhood.size() - 1);
        return neighbourhood.get(0);
        /*int size = (int) (this.genes.size());
         IndividuoEscuela newSolution;
         ArrayList<Individual> neighbourhood = new ArrayList();
         newSolution = new IndividuoEscuela((ArrayList<GenEscuela>) this.genes.clone());

         for (int i = 0; i < size - 1; i++) {
         for (int j = i + 1; j < size; j++) {

         Collections.shuffle(newSolution.genes);
         //InfoMaterias auxMateria = new InfoMaterias();
         //int a = (int) (Math.random() * newSolution.genes.size());
         //int b = (int) (Math.random() * newSolution.genes.size());
         GenEscuela auxMateria;
         auxMateria = newSolution.genes.get(i);
         newSolution.getGenes().set(i, newSolution.getGenes().get(j));
         newSolution.getGenes().set(j, auxMateria);
         //newSolution.getGenes().get(i).setMateria(newSolution.getGenes().get(j).getMateria());
         //newSolution.getGenes().get(b).setMateria(auxMateria);
         //newSolution.getGenes().get(j).setHorarios(auxMateria.getHorarios());
         newSolution.getEvaluacion();
         neighbourhood.add(newSolution);
         }

         }
         neighbourhood = OrdenarIndividuos(neighbourhood, 0, neighbourhood.size() - 1);
         return neighbourhood.get(0);*/
        //Collections.sort(neighbourhood,Individuo.this.evaluacion);
        //return neighbourhood;
    }

    @Override
    public double getEvaluacion() {
        int evaluacion = evaluarRestricciones();
        this.evaluacion = evaluacion;
        return evaluacion;
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
        return new IndividuoEscuela(this.genes);
    }

    @Override
    public ArrayList<Individual> OrdenarIndividuos(ArrayList<Individual> individuos, int izq, int der) {
        //individuos = (ArrayList<Individuo>) individuos;
        IndividuoEscuela pivote = (IndividuoEscuela) individuos.get(izq); // tomamos primer elemento como pivote
        int i = izq; // i realiza la búsqueda de izquierda a derecha
        int j = der; // j realiza la búsqueda de derecha a izquierda
        IndividuoEscuela ind;
        int aux;

        while (i < j) {            // mientras no se crucen las búsquedas
            while (individuos.get(i).getEvaluacion() <= pivote.getEvaluacion() && i < j) {
                i++; // busca elemento mayor que pivote
            }
            while (individuos.get(j).getEvaluacion() > pivote.getEvaluacion()) {
                j--;         // busca elemento menor que pivote
            }
            if (i < j) {
                ind = (IndividuoEscuela) individuos.get(i); // si no se han cruzado                      
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
    public ArrayList generateRandomConfiguration(CargueDatos datos) {
        ArrayList<GenEscuela> genes = new ArrayList<GenEscuela>();
        ArrayList<InfoMaterias> materiasAux = datos.getMaterias();
        int materia;
        //int suma = 0;
        int value = 0;
        for (int i = 0; i < 15; i++) {
            //materiasAux.clear();
            //System.out.println("iteracion");
            materiasAux = (ArrayList<InfoMaterias>) datos.getMaterias().clone();
            for (int j = 0; j < 4; j++) {
                //suma++;
                GenEscuela gen = new GenEscuela();
                materia = (int) (Math.random() * materiasAux.size());
                //System.out.println(materiasAux.get(materia).getNombreMateria());
                gen.setMateria(materiasAux.get(materia));
                value = value + 1;
                gen.setValue(value);
                genes.add(gen);
                materiasAux.remove(materia);
            }
            //System.out.println("********");
        }
        //System.out.println(suma);
        /*for (int i = 0; i < this.TOTAL_FRANJAS; i++) {
         GenEscuela gen = new GenEscuela();
         //System.out.println(datos.getMaterias().get(i).getNombreMateria());
         materia = (int) (Math.random() * datos.getMaterias().size());
         gen.setMateria(datos.getMaterias().get(materia));
         gen.setValue(i);
         genes.add(gen);

         }*/
        return genes;

    }

    public ArrayList<GenEscuela> getGenes() {
        return genes;
    }

    @Override
    public ArrayList<Individual> getNeighbourhood() {
        int size = (int) (this.genes.size());
        IndividuoEscuela newSolution;
        ArrayList<Individual> neighbourhood = new ArrayList();
        newSolution = new IndividuoEscuela((ArrayList<GenEscuela>) this.genes.clone());

        for (int i = 0; i < size / 4; i++) {

            int a = (int) (Math.random() * this.genes.size());
            int b = (int) (Math.random() * this.genes.size());
            newSolution.genes.get(a).setMateria(newSolution.genes.get(b).getMateria());
            newSolution.getEvaluacion();
            neighbourhood.add(newSolution);
        }
        //neighbourhood = OrdenarIndividuos(neighbourhood, 0, neighbourhood.size() - 1);
        return neighbourhood;
    }

    private int evaluarRestricciones() {
        int pena1 = 1;
        int penalizacionJardinSemana = 0;
        int penalizacionPreJardinSemana = 0;
        int penalizacionTransicionSemana = 0;
        int penalizacionJardinDia = 0;
        int penalizacionPreJardinDia = 0;
        int penalizacionTransicionDia = 0;
        int sumaIngles = 0;
        int sumaMatematicas = 0;
        int sumaMusica = 0;
        int sumalectura = 0;
        int sumaEstetica = 0;

        int genes = this.genes.size();

        for (int i = 0; i < 20; i++) {
            if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("INGLES")) {
                sumaIngles++;
            } else if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("MATEMATICAS")) {
                sumaMatematicas++;
            } else if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("ESTETICA")) {
                sumaEstetica++;
            } else if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("MUSICA")) {
                sumaMusica++;
            } else if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("LECTURA-ESCRITURA")) {
                sumalectura++;
            }
        }

        if (sumaEstetica != 4) {
            penalizacionTransicionSemana += pena1;
        }
        if (sumaIngles != 4) {
            penalizacionTransicionSemana += pena1;
        }
        if (sumaMatematicas != 4) {
            penalizacionTransicionSemana += pena1;
        }
        if (sumaMusica != 4) {
            penalizacionTransicionSemana += pena1;
        }
        if (sumalectura != 4) {
            penalizacionTransicionSemana += pena1;
        }
        sumaIngles = 0;
        sumaMatematicas = 0;
        sumaMusica = 0;
        sumalectura = 0;
        sumaEstetica = 0;

        for (int i = 20; i < 40; i++) {
            if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("INGLES")) {
                sumaIngles++;
            } else if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("MATEMATICAS")) {
                sumaMatematicas++;
            } else if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("ESTETICA")) {
                sumaEstetica++;
            } else if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("MUSICA")) {
                sumaMusica++;
            } else if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("LECTURA-ESCRITURA")) {
                sumalectura++;
            }
        }

        if (sumaEstetica != 4) {
            penalizacionPreJardinSemana += pena1;
        }
        if (sumaIngles != 4) {
            penalizacionPreJardinSemana += pena1;
        }
        if (sumaMatematicas != 4) {
            penalizacionPreJardinSemana += pena1;
        }
        if (sumaMusica != 4) {
            penalizacionPreJardinSemana += pena1;
        }
        if (sumalectura != 4) {
            penalizacionPreJardinSemana += pena1;
        }

        sumaIngles = 0;
        sumaMatematicas = 0;
        sumaMusica = 0;
        sumalectura = 0;
        sumaEstetica = 0;

        for (int i = 40; i < 60; i++) {
            if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("INGLES")) {
                sumaIngles++;
            } else if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("MATEMATICAS")) {
                sumaMatematicas++;
            } else if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("ESTETICA")) {
                sumaEstetica++;
            } else if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("MUSICA")) {
                sumaMusica++;
            } else if (this.genes.get(i).getMateria().getNombreMateria().equalsIgnoreCase("LECTURA-ESCRITURA")) {
                sumalectura++;
            }
        }

        if (sumaEstetica != 4) {
            penalizacionJardinSemana += pena1;
        }
        if (sumaIngles != 4) {
            penalizacionJardinSemana += pena1;
        }
        if (sumaMatematicas != 4) {
            penalizacionJardinSemana += pena1;
        }
        if (sumaMusica != 4) {
            penalizacionJardinSemana += pena1;
        }
        if (sumalectura != 4) {
            penalizacionJardinSemana += pena1;
        }

        return penalizacionJardinSemana + penalizacionPreJardinSemana + penalizacionTransicionSemana
                + penalizacionJardinDia + penalizacionPreJardinDia + penalizacionTransicionDia;
    }

}
