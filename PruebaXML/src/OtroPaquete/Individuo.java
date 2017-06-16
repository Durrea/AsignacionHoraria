/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OtroPaquete;

import pruebaxml.*;
import java.util.ArrayList;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Eduardo
 */
public class Individuo {

    private int CAMINOS;
    private ArrayList<Integer> entradas_individuo;
    private double evaluacion;
    public ArrayList<String> caminos;
    public ArrayList<String> caminos_cubiertos;
    
    public Individuo(int caminos) {
        this.CAMINOS = caminos;
        entradas_individuo = new ArrayList();
        this.evaluacion = -1;
        this.caminos = new ArrayList();
        CargarCaminos();
        this.caminos_cubiertos = new ArrayList();

    }

    public ArrayList<Integer> getEntradas_individuo() {
        return entradas_individuo;
    }

    public void setEntradas_individuo(ArrayList<Integer> entradas_individuo) {
        this.entradas_individuo = entradas_individuo;
    }

    public ArrayList<String> getCaminos() {
        return caminos;
    }

    public void setCaminos(ArrayList<String> caminos) {
        this.caminos = caminos;
    }

    public ArrayList<String> getCaminos_cubiertos() {
        return caminos_cubiertos;
    }

    public void setCaminos_cubiertos(ArrayList<String> caminos_cubiertos) {
        this.caminos_cubiertos = caminos_cubiertos;
    }

    

    public int getCAMINOS() {
        return CAMINOS;
    }

    public void setCAMINOS(int CAMINOS) {
        this.CAMINOS = CAMINOS;
    }

    public double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public void CargarCaminos() {
        this.caminos.add("RAMA4-RAMA2-RAMA3-RAMA5-");
        this.caminos.add("RAMA4-RAMA2-RAMA3-RAMA6-");
        this.caminos.add("RAMA4-RAMA2-RAMA4-RAMA1-RAMA3-RAMA5-");
        this.caminos.add("RAMA4-RAMA2-RAMA4-RAMA1-RAMA3-RAMA6-");
    }

    public void cargarCaminosCubiertos() {
        for (int i = 0; i < this.entradas_individuo.size(); i++) {
            this.caminos_cubiertos.add(funcionAuxiliar(this.entradas_individuo.get(i)));
        }
    }

    public void CalcularEntradas(int num_entradas, int superior, int inferior) {
        for (int i = 0; i < 4; i++) {
            int number = (int) (Math.random() * 99) + 1;
            this.entradas_individuo.add(number);
        }
    }

    public Individuo GenerarVecindario(Individuo individuo, int numvecinos, int superior) {
        ArrayList<Individuo> vecindario = new ArrayList();
        int posicion_mejor = -1;
        superior = superior / 10;
        for (int i = 0; i < numvecinos; i++) {
            Individuo ind = individuo.clone();
            int entrada = (int) (Math.random() * ind.entradas_individuo.size() - 1);
            int valor = (int) (Math.random() * 4);
            int operador = (int) (Math.random() * 1);//0 para suma y 1 para resta
            int numero = (int) (Math.random() * superior);

            if (operador == 0) {
                //int v = ind.entradas_individuo.get(entrada).getValores().get(valor)+numero;
                ind.entradas_individuo.set(valor, ind.entradas_individuo.get(valor) + numero);
                //System.out.println("Valor generado: "+v);
            } else {
                //int v = ind.entradas_individuo.get(entrada).getValores().get(valor)-numero;
                ind.entradas_individuo.set(valor, ind.entradas_individuo.get(valor) - numero);
                //System.out.println("Valor generado: "+v);
            }
            ind.EvaluarIndividuo();
            if (posicion_mejor == -1) {
                vecindario.add(ind);
                posicion_mejor = i;
            } else {
                vecindario.add(ind);
                if (vecindario.get(i).evaluacion > vecindario.get(posicion_mejor).evaluacion) {
                    posicion_mejor = i;
                }
            }
        }
        Individuo ind_retorno = vecindario.get(posicion_mejor);
        return ind_retorno;

    }

    public Individuo clone() {
        
        Individuo clon = new Individuo(this.CAMINOS);
        clon.entradas_individuo = (ArrayList<Integer>) this.entradas_individuo.clone();
        clon.caminos = (ArrayList<String>) this.caminos.clone();
        clon.caminos_cubiertos = (ArrayList<String>) this.caminos_cubiertos.clone();

        return clon;
    }

    public void EvaluarIndividuo() {
        
        cargarCaminosCubiertos();
        
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;

        for (int j = 0; j < caminos_cubiertos.size(); j++) {
            if (caminos.get(0).equalsIgnoreCase(caminos_cubiertos.get(j).toString())) {
                //System.out.println("Camino 1");
                count1 = 1;
            }
            if (caminos.get(1).equalsIgnoreCase(caminos_cubiertos.get(j).toString())) {
                //System.out.println("Camino 2");
                count2 = 1;
            }
            if (caminos.get(2).equalsIgnoreCase(caminos_cubiertos.get(j).toString())) {
                //System.out.println("Camino 3");
                count3 = 1;
            }
            if (caminos.get(3).equalsIgnoreCase(caminos_cubiertos.get(j).toString())) {
                //System.out.println("Camino 4");
                count4 = 1;
            }
        }

        this.setEvaluacion(count1 + count2 + count3 + count4);
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

        //System.out.println("Camino :" + camino);

        return camino;
    }
}
