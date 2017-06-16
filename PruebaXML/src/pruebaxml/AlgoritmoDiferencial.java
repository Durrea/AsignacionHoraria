/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Eduardo
 */
public class AlgoritmoDiferencial {

    private int POBLACION;
    private ArrayList<Individuo> individuos;
    private ArrayList<Individuo> individuosNp;
    private ArrayList<Individuo> generacion;
    private int CAMINOS;
    private int ENTRADAS;
    private int SUPERIOR;
    private int INFERIOR;
    public Document doc;
    private ArrayList<String> caminos;
    public ArrayList<Integer> caminos_cubiertos;
    public double CR;

    public AlgoritmoDiferencial(int POBLACION, int superior, int inferior) {
        this.POBLACION = POBLACION;
        this.generacion = new ArrayList();
        this.SUPERIOR = superior;
        this.INFERIOR = inferior;
        this.individuos = new ArrayList();
        this.caminos_cubiertos = new ArrayList();
        this.caminos = cargarCaminos();
        this.CAMINOS = this.caminos.size();
        this.doc = cargarXML();
        this.ENTRADAS = NumeroEntradas();
        this.individuosNp = new ArrayList();
        this.CR = 0.8;
    }

    public ArrayList<Individuo> getIndividuosNp() {
        return individuosNp;
    }

    public void setIndividuosNp(ArrayList<Individuo> individuosNp) {
        this.individuosNp = individuosNp;
    }
    
    public int getSUPERIOR() {
        return SUPERIOR;
    }

    public void setSUPERIOR(int SUPERIOR) {
        this.SUPERIOR = SUPERIOR;
    }

    public int getINFERIOR() {
        return INFERIOR;
    }

    public void setINFERIOR(int INFERIOR) {
        this.INFERIOR = INFERIOR;
    }

    public int getCAMINOS() {
        return CAMINOS;
    }

    public void setCAMINOS(int CAMINOS) {
        this.CAMINOS = CAMINOS;
    }

    public int getENTRADAS() {
        return ENTRADAS;
    }

    public void setENTRADAS(int ENTRADAS) {
        this.ENTRADAS = ENTRADAS;
    }

    public int getPOBLACION() {
        return POBLACION;
    }

    public void setPOBLACION(int POBLACION) {
        this.POBLACION = POBLACION;
    }

    public ArrayList<Individuo> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ArrayList<Individuo> individuos) {
        this.individuos = individuos;
    }

    public void AlgoritmoDiferencial() {

        int g = 0;
        GenerarIndividuos(this.POBLACION);
        EvaluarIndividuos(this.POBLACION);
        int MAX_GEN = 20;        
        int NUM_HIJOS = 5;
        int NP = this.POBLACION;
        Random rnd = new Random();

        Individuo r1;
        Individuo r2;        

        for (g = 0; g < MAX_GEN; g++) {
            for (int i = 0; i < NP; i++) {
                r1 = SeleccionarIndividuo();
                r2 = SeleccionarIndividuo();                
                int jrand = (int) (rnd.nextDouble() * 11 + 0);
                this.individuosNp.clear();
                
                for (int j = 0; j < NUM_HIJOS; j++) {
                    
                    Individuo np = new Individuo(this.CAMINOS);
                    if((rnd.nextDouble()*1+0) < this.CR || j == jrand)
                    {
                        np = Recombinar(r1, r2);
                        this.individuosNp.add(np);
                    }
                    else
                    {
                        this.individuosNp.add(this.individuos.get(i));
                    }
                }
                int pos = -1;
                for (int j = 0; j < this.individuosNp.size(); j++) 
                {
                    Individuo ind = new Individuo(this.CAMINOS);
                    if(this.individuosNp.get(j).getEvaluacion()>this.individuosNp.get(i).getEvaluacion())
                    {
                        this.generacion.add(this.individuosNp.get(j));                        
                    }
                    else
                    {
                        pos = i;
                        //this.generacion.add(this.individuos.get(i));
                    }
                }
                if(pos != -1)
                {
                    this.generacion.add(this.individuosNp.get(pos));                    
                }
                
            }
            
        }
        OrdenarIndividuos(0,this.individuos.size()-1);
        //obj.eliminarRepetidos();                    
    }

    public void GenerarIndividuos(int num) {
        Document doc_copy = (Document) this.doc.cloneNode(true);
        for (int i = 0; i < num; i++) {
            Individuo ind = new Individuo(this.CAMINOS);
            ind.caminos_posibles = this.caminos;
            ind.caminos_cubiertos = this.caminos_cubiertos;
            ind.CalcularEntradas(doc_copy, this.ENTRADAS, SUPERIOR, INFERIOR);

            this.individuos.add(ind);
            doc_copy = (Document) this.doc.cloneNode(true);
        }
    }

    public void EvaluarIndividuos(int num) {
        Document doc_copy = (Document) this.doc.cloneNode(true);
        for (int i = 0; i < num; i++) {
            this.individuos.get(i).EvaluarIndividuo(doc_copy);
            doc_copy = (Document) this.doc.cloneNode(true);
        }
    }

    public Document cargarXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document documento = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            documento = (Document) builder.parse(new File("prueba.xml"));
        } catch (Exception spe) {
            System.out.println(spe.getMessage());
        }
        return documento;
    }

    public ArrayList<String> cargarCaminos() {
        ArrayList<String> caminos = new ArrayList();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("caminos.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                caminos.add(linea);
                this.caminos_cubiertos.add(0);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return caminos;
    }

    public Individuo SeleccionarIndividuo() {
        int pos = (int) (Math.random() * (this.individuos.size() - 1) + 0);
        return this.individuos.get(pos);
    }

    public Individuo Recombinar(Individuo padre, Individuo madre) {
        //Recombinacion basada en un punto
        Individuo hijo = new Individuo(padre.getCAMINOS());
        Individuo hijo2 = new Individuo(madre.getCAMINOS());
        for (int i = 0; i < padre.getEntradas_individuo().size(); i++) {
            if (i < padre.getCAMINOS() / 2) {
                Entradas entrada_1 = new Entradas(this.ENTRADAS,this.SUPERIOR,this.INFERIOR);
                entrada_1.setValores((ArrayList<Integer>) padre.getEntradas_individuo().get(i).getValores().clone());
                entrada_1.setEntradas((ArrayList<String>) padre.getEntradas_individuo().get(i).getEntradas().clone());
                entrada_1.setCamino_cubierto(padre.getEntradas_individuo().get(i).getCamino_cubierto());
                Entradas entrada_2 = new Entradas(this.ENTRADAS,this.SUPERIOR,this.INFERIOR);
                entrada_2.setValores((ArrayList<Integer>) madre.getEntradas_individuo().get(i).getValores().clone());
                entrada_2.setEntradas((ArrayList<String>) madre.getEntradas_individuo().get(i).getEntradas().clone());
                entrada_2.setCamino_cubierto(madre.getEntradas_individuo().get(i).getCamino_cubierto());
                hijo.getEntradas_individuo().add(entrada_1);                
                hijo2.getEntradas_individuo().add(entrada_2);
            } else {
                Entradas entrada_1 = new Entradas(this.ENTRADAS,this.SUPERIOR,this.INFERIOR);
                entrada_1.setValores((ArrayList<Integer>) madre.getEntradas_individuo().get(i).getValores().clone());
                entrada_1.setEntradas((ArrayList<String>) madre.getEntradas_individuo().get(i).getEntradas().clone());
                entrada_1.setCamino_cubierto(madre.getEntradas_individuo().get(i).getCamino_cubierto());
                Entradas entrada_2 = new Entradas(this.ENTRADAS,this.SUPERIOR,this.INFERIOR);
                entrada_2.setValores((ArrayList<Integer>) padre.getEntradas_individuo().get(i).getValores().clone());
                entrada_2.setEntradas((ArrayList<String>) padre.getEntradas_individuo().get(i).getEntradas().clone());
                entrada_2.setCamino_cubierto(padre.getEntradas_individuo().get(i).getCamino_cubierto());
                hijo.getEntradas_individuo().add(entrada_1);                
                hijo2.getEntradas_individuo().add(entrada_2);
            }
        }
        hijo.caminos_posibles = padre.caminos_posibles;
        hijo.caminos_cubiertos = padre.caminos_cubiertos;
        hijo2.caminos_posibles = madre.caminos_posibles;
        hijo2.caminos_cubiertos = madre.caminos_cubiertos;
        Document doc_copy_1 = (Document) this.doc.cloneNode(true);
        Document doc_copy_2 = (Document) this.doc.cloneNode(true);
        //Mutar(hijo);
        //Mutar(hijo2);
        hijo.EvaluarIndividuo(doc_copy_1);
        hijo2.EvaluarIndividuo(doc_copy_2);
        individuos.add(hijo);
        individuos.add(hijo2);
        if(hijo.getEvaluacion() > hijo2.getEvaluacion())
        {
            return hijo;
        }
        else
        {
            return hijo2;
        }
    }

    public void Mutar(Individuo ind) {
        int entradaMutada = (int) (Math.random() * (ind.getEntradas_individuo().size()-1));
        //System.out.println(ind.getEntradas_individuo().size()+ "--" + entradaMutada);
        int valor = 0;
        for (int i = 0; i < ind.getEntradas_individuo().get(entradaMutada).getValores().size(); i++) {
                    //System.out.println("Por aquí pase");
            if (SUPERIOR < 0) {
                        //System.out.println("Por aquí tambien");
                valor = INFERIOR + (int) (Math.random() * (INFERIOR - SUPERIOR));
                ind.getEntradas_individuo().get(entradaMutada).getValores().set(i, valor);
            } else {
                        //System.out.println("y por aquí");
                valor = (int) (Math.random() * SUPERIOR + INFERIOR);
                ind.getEntradas_individuo().get(entradaMutada).getValores().set(i, valor);
            }
        }
        
    }

    public int NumeroEntradas() {
        NodeList lectura = this.doc.getElementsByTagName("Leer");
        return lectura.getLength();
    }
    public void OrdenarIndividuos(int izq, int der) {
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
            OrdenarIndividuos(izq, j - 1); // ordenamos subarray izquierdo
        }
        if (j + 1 < der) {
            OrdenarIndividuos(j + 1, der); // ordenamos subarray derecho
        }        
    }
    /*public void eliminarRepetidos()
    {
        ArrayList<Individuo> listaind = new ArrayList();
        listaind.add(this.generacion.get(0));
        int tam = 0;
        for (int i = 1; i < this.generacion.size(); i++) 
        {
            if(this.generacion.get(i).getX0()!= listaind.get(tam).getX0() &&
                       this.generacion.get(i).getX1()!= listaind.get(tam).getX1() &&
                       this.generacion.get(i).getX2()!= listaind.get(tam).getX2() &&
                       this.generacion.get(i).getX3()!= listaind.get(tam).getX3() &&
                       this.generacion.get(i).getX4()!= listaind.get(tam).getX4() &&
                       this.generacion.get(i).getX5()!= listaind.get(tam).getX5() &&
                       this.generacion.get(i).getX6()!= listaind.get(tam).getX6() &&
                       this.generacion.get(i).getX7()!= listaind.get(tam).getX7() &&
                       this.generacion.get(i).getX8()!= listaind.get(tam).getX8() &&
                       this.generacion.get(i).getX9()!= listaind.get(tam).getX9() &&
                       this.generacion.get(i).getX10()!= listaind.get(tam).getX10() &&
                       this.generacion.get(i).getX11()!= listaind.get(tam).getX11() &&
                       this.generacion.get(i).getBeneficio()!= listaind.get(tam).getBeneficio() &&
                       this.generacion.get(i).getCosto()!= listaind.get(tam).getCosto() &&
                       this.generacion.get(i).getGanancia()!= listaind.get(tam).getGanancia())
                    {
                        Individuo obj = new Individuo();
                        obj = reemplazo(this.generacion.get(i));
                        listaind.add(obj);
                        tam++;
                    }
        }
        setGeneracion(listaind);
    }*/
}
