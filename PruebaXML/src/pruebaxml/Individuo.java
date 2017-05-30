/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

import java.util.ArrayList;
import org.w3c.dom.Element;

/**
 *
 * @author Eduardo
 */
public class Individuo {
    
    private int CAMINOS;
    private ArrayList<Entradas> entradas_individuo;
    private double evaluacion;
    
    public Individuo(int caminos)
    {
        this.CAMINOS = caminos;
        entradas_individuo = new ArrayList();
        this.evaluacion = -1;
    }

    public int getCAMINOS() {
        return CAMINOS;
    }

    public void setCAMINOS(int CAMINOS) {
        this.CAMINOS = CAMINOS;
    }

    public ArrayList<Entradas> getEntradas_individuo() {
        return entradas_individuo;
    }

    public void setEntradas_individuo(ArrayList<Entradas> entradas_individuo) {
        this.entradas_individuo = entradas_individuo;
    }

    public double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(double evaluacion) {
        this.evaluacion = evaluacion;
    }
    
    public void EvaluarIndividuo(Element root)
    {
        //CalcularValores(num_entradas,superior,inferior);
        
    }
    public void CalcularEntradas(int num_entradas, int superior, int inferior)
    {
        for(int i=0;i<this.CAMINOS;i++)
        {
            Entradas obj = new Entradas(num_entradas,superior,inferior);
            obj.GenerarEntradasAleatorias();
            this.entradas_individuo.add(obj);
        }
    }
}
