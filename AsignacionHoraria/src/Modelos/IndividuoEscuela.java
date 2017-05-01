/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Servicios.CargueDatos;
import algoritmo_base.Individual;
import java.util.ArrayList;

/**
 *
 * @author ingesis
 */
public class IndividuoEscuela implements Individual {

    public double evaluacion;
    private int TOTAL_FRANJAS = 60;
    ArrayList<GenEscuela> genes = new ArrayList<GenEscuela>();

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
        System.out.println("Olis");
        return null;
    }

    @Override
    public ArrayList getNeighbourhood() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getEvaluacion() {
        return 0;
    }

    @Override
    public int getIndividualSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getValue(int position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Individual clonar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        System.out.println("primero");
        ArrayList<GenEscuela> genes = new ArrayList<GenEscuela>();
        System.out.println("segundo");
        int materia;

        for (int i = 0; i < this.TOTAL_FRANJAS; i++) {
            GenEscuela gen = new GenEscuela();
            //System.out.println(datos.getMaterias().get(i).getNombreMateria());
            materia = (int) (Math.random() * datos.getMaterias().size());
            gen.setMateria(datos.getMaterias().get(materia));
            gen.setValue(i);
            genes.add(gen);

        }
        return genes;

    }

}
