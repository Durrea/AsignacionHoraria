/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OtroPaquete;

import pruebaxml.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Eduardo
 */
public class Memetico {

    public ArrayList<Individuo> individuos;
    public int CAMINOS;
    public int ENTRADAS;
    public int SUPERIOR;
    public int INFERIOR;
    public ArrayList<String> caminos;
    public ArrayList<String> caminos_cubiertos;
    public Document doc;
    public int ITE_BUSQUEDA_LOCAL;
    public int NUM_HIJOS;
    public double PRESERVE = 0.5;
    public int POBLACION;
    public int CRITERIO_MEMETICO = 10;
    public double VALOR_T = 0.0001;

    public Memetico() {
        this.individuos = new ArrayList();
        this.individuos = new ArrayList();
        this.caminos_cubiertos = new ArrayList();
        this.caminos = cargarCaminos();
        this.CAMINOS = this.caminos.size();
        this.ENTRADAS = 1;
    }

    public ArrayList<Individuo> run() {
        int i = 0;
        ArrayList<Double> iteraciones = new ArrayList();
        ArrayList<Individuo> pop = GenerarPoblacionInicial(POBLACION);
        while (i < CRITERIO_MEMETICO) {
            ArrayList<Individuo> newpop = GenerarNuevaPoblacion(pop);
            pop = ActualizarPoblacion(pop, newpop);
            if (Converge(pop, iteraciones, i, VALOR_T)) {
                System.out.println("Converge");
                pop = reiniciarPoblacion(pop);
            }
            i = i + 1;
        }
        OrdenarIndividuos(pop, 0, (pop.size() - 1));
        return pop;
    }

    public ArrayList<Individuo> GenerarPoblacionInicial(int num) {
        for (int i = 0; i < num; i++) {
            Individuo ind = new Individuo(this.CAMINOS);
            ind.CalcularEntradas(this.ENTRADAS, SUPERIOR, INFERIOR);
            ind.EvaluarIndividuo();
            ind = BusquedaLocal(ind);
            this.individuos.add(ind);
        }
        return this.individuos;
    }

    public Individuo BusquedaLocal(Individuo individuo) {
        for (int i = 0; i < this.ITE_BUSQUEDA_LOCAL; i++) {
            Individuo nuevo = individuo.GenerarVecindario(individuo, CAMINOS, SUPERIOR);
            if (nuevo.getEvaluacion() > individuo.getEvaluacion()) {
                individuo = nuevo;
            }
        }
        return individuo;
    }

    public ArrayList<Individuo> ActualizarPoblacion(ArrayList<Individuo> pop, ArrayList<Individuo> nueva) {
        ArrayList<Individuo> nuevapoblacion = new ArrayList<Individuo>();
        for (int i = 0; i < pop.size(); i++) {
            nuevapoblacion.add(pop.get(i));
        }
        for (int i = 0; i < nueva.size(); i++) {
            nuevapoblacion.add(nueva.get(i));
        }
        nuevapoblacion = OrdenarIndividuos(nuevapoblacion, 0, nuevapoblacion.size() - 1);
        for (int i = 0; i < nueva.size(); i++) {
            nuevapoblacion.remove(0);
        }
        return nuevapoblacion;
    }

    public ArrayList<Individuo> GenerarNuevaPoblacion(ArrayList<Individuo> pop) {
        ArrayList<Individuo> buffer = (ArrayList<Individuo>) pop.clone();
        ArrayList<Individuo> hijos = new ArrayList();
        for (int i = 0; i < this.NUM_HIJOS; i++) {
            int limite = buffer.size() - 1;
            int k = (int) (Math.random() * limite);
            int j = (int) (Math.random() * limite);
            Individuo ind_recombinado = Recombinar(buffer.get(k).clone(), buffer.get(j).clone());
            Mutar(ind_recombinado);
            ind_recombinado.EvaluarIndividuo();
            hijos.add(ind_recombinado);
        }

        for (int i = 0; i < hijos.size(); i++) {
            buffer.add(hijos.get(i));
        }
        buffer = OrdenarIndividuos(buffer, 0, buffer.size() - 1);
        int i = 0;
        while (i < hijos.size()) {
            buffer.remove(0);
            i++;
        }
        return buffer;
    }

    public ArrayList<Individuo> reiniciarPoblacion(ArrayList<Individuo> pop) {

        ArrayList<Individuo> newpop = new ArrayList();
        ArrayList<Individuo> population = (ArrayList<Individuo>) pop.clone();

        population = OrdenarIndividuos(population, 0, population.size() - 1);
        int preserved = (int) (population.size() * this.PRESERVE);
        for (int i = 0; i < preserved; i++) {
            Individuo individuo = population.get(i);
            newpop.add(individuo);
        }
        for (int i = preserved; i < population.size(); i++) {
            Individuo ind = new Individuo(this.CAMINOS);
            ind.caminos_cubiertos = this.caminos_cubiertos;
            ind.CalcularEntradas(this.ENTRADAS, SUPERIOR, INFERIOR);
            ind.EvaluarIndividuo();
            ind = BusquedaLocal(ind);
            newpop.add(ind);
        }

        return newpop;
    }

    public Individuo Recombinar(Individuo padre, Individuo madre) {
        //Recombinacion basada en un punto
        Individuo hijo = new Individuo(padre.getCAMINOS());
        Individuo hijo2 = new Individuo(madre.getCAMINOS());
        for (int i = 0; i < padre.getEntradas_individuo().size(); i++) {
            if (i < padre.getCAMINOS() / 2) {
                int entrada1;
                int entrada2;
                entrada1 = padre.getEntradas_individuo().get(i);
                entrada2 = madre.getEntradas_individuo().get(i);
                hijo.getEntradas_individuo().add(entrada1);
                hijo2.getEntradas_individuo().add(entrada2);
            } else {
                int entrada1;
                int entrada2;
                entrada1 = padre.getEntradas_individuo().get(i);
                entrada2 = madre.getEntradas_individuo().get(i);
                hijo.getEntradas_individuo().add(entrada2);
                hijo2.getEntradas_individuo().add(entrada1);
            }
        }

        hijo.caminos_cubiertos = (ArrayList<String>) padre.caminos_cubiertos.clone();
        hijo2.caminos_cubiertos = (ArrayList<String>) madre.caminos_cubiertos.clone();
        //Mutar(hijo);
        //Mutar(hijo2);
        hijo.EvaluarIndividuo();
        hijo2.EvaluarIndividuo();
        //individuos.add(hijo);
        //individuos.add(hijo2);
        if (hijo.getEvaluacion() > hijo2.getEvaluacion()) {
            return hijo;
        } else {
            return hijo2;
        }
    }

    public void Mutar(Individuo ind) {
        int entradaMutada = (int) (Math.random() * (ind.getEntradas_individuo().size() - 1));
        //System.out.println(ind.getEntradas_individuo().size()+ "--" + entradaMutada);
        int valor = 0;
        if (SUPERIOR < 0) {
            valor = INFERIOR + (int) (Math.random() * (INFERIOR - SUPERIOR));
            ind.getEntradas_individuo().set(entradaMutada,valor);
        } else {
            valor = (int) (Math.random() * SUPERIOR + INFERIOR);
            ind.getEntradas_individuo().set(entradaMutada,valor);
        }

    }

    public boolean Converge(ArrayList<Individuo> miPoblacion, ArrayList<Double> iteraciones, int numero, double VALOR_T) {
        //System.out.println("Conver..");        
        double sumaAdaptacion = 0;
        double entropiaSuma = 0;
        ArrayList<Double> probabilidades = new ArrayList<>();
        ArrayList<Double> entropia = new ArrayList<>();

        for (int i = 0; i < miPoblacion.size(); i++) {
            Individuo ind = miPoblacion.get(i);
            sumaAdaptacion += ind.getEvaluacion();
        }
        for (int i = 0; i < miPoblacion.size(); i++) {
            Individuo ind = miPoblacion.get(i);
            probabilidades.add(ind.getEvaluacion() / sumaAdaptacion);
        }

        for (int i = 0; i < miPoblacion.size(); i++) {
            entropia.add(probabilidades.get(i) * Math.log10(probabilidades.get(i)));
            entropiaSuma += entropia.get(i);
        }
        iteraciones.add(numero, entropiaSuma);

        if (numero == 0) {
            return false;
        } else {
            //double v = iteraciones.get(numero - 1) - iteraciones.get(numero);
            //System.out.println("Compara con VALOR_T: "+v);                    
            if (iteraciones.get(numero - 1) - iteraciones.get(numero) < VALOR_T) {
                //System.out.println("Si conver.");
                return true;
            }
        }
        return false;
    }

    public ArrayList OrdenarIndividuos(ArrayList<Individuo> individuos, int izq, int der) {
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

    public ArrayList<String> cargarCaminos() {
        ArrayList<String> caminos = new ArrayList();
        Individuo obj = new Individuo(CAMINOS);
        obj.CargarCaminos();
        caminos = (ArrayList<String>) obj.getCaminos().clone();
        return caminos;
    }


    public String funcionAuxiliar(int number) {
        int value = 0;
        int sum = 0;
        int pos = 1;
        int result = 0;
        String camino = "";
        do {
            //System.out.println("RAMA 4");
            camino = camino + "RAMA4-";
            int digit = number % 10;
            if (pos % 2 == 0) {
                //System.out.println("RAMA 1");
                camino = camino + "RAMA1-";
                value = 3 * digit;
            } else {
                //System.out.println("RAMA 2");
                camino = camino + "RAMA2-";
                value = digit;
            }
            sum = sum + value;
            number = number / 10;
            pos = pos + 1;
        } while (number > 0);
        //System.out.println("RAMA 3");
        camino = camino + "RAMA3-";
        result = sum & 11;
        if (result == 10) {
            //System.out.println("RAMA 5");
            camino = camino + "RAMA5-";
            result = 1;
        } else {
            //System.out.println("RAMA 6");
            camino = camino + "RAMA6-";
        }

        System.out.println("Camino :" + camino);

        return camino;
    }
}
