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
    private int CAMINOS;
    private int ENTRADAS;
    private int SUPERIOR;
    private int INFERIOR;
    public Document doc;
    private ArrayList<String> caminos;
    public ArrayList<Integer> caminos_cubiertos;
    public AlgoritmoDiferencial(int POBLACION, int superior, int inferior)
    {
        this.POBLACION = POBLACION;        
        this.SUPERIOR = superior;
        this.INFERIOR = inferior;
        this.individuos = new ArrayList();
        this.caminos_cubiertos = new ArrayList();
        this.caminos = cargarCaminos();
        this.CAMINOS = this.caminos.size();
        this.doc = cargarXML();
        this.ENTRADAS = NumeroEntradas();        
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
        //Funcion obj = new Funcion();
        //obj.GenerarIndividuos(num);
        //obj.evaluarIndividuos();
        int MAX_GEN = 30;
        int NP = this.POBLACION;
        Random rnd = new Random();
        
        Individuo r1;
        Individuo r2;
        Individuo r3;

        for (g = 0; g < MAX_GEN; g++) {
            for (int i = 0; i < NP; i++) {
                r1 = SeleccionarIndividuo();
                r2 = SeleccionarIndividuo();
                r3 = SeleccionarIndividuo();                                
                int jrand = (int) (rnd.nextDouble() * 11 + 0);
                //obj.getIndividuosNp().clear();
                for(int j = 0;j<12;j++)
                {
                    //Individuo np = new Individuo();
                    //if((rnd.nextDouble()*1+0) < obj.getCR() || j == jrand)
                    //{
                        //np = obj.mutacion(r1,r2,r3);
                        //obj.getIndividuosNp().add(np);
                    //}
                    //else
                    {
                        //np = obj.reemplazo(obj.getIndividuos().get(i));
                        //obj.getIndividuosNp().add(np);
                    }
                }
                int pos = -1;
                /*for (int j = 0; j < obj.getIndividuosNp().size(); j++) 
                {
                    Individuo ind = new Individuo();
                    if(obj.getIndividuosNp().get(j).getBeneficio()>obj.getIndividuosNp().get(i).getBeneficio())
                    {
                        
                        obj.getGeneracion().add(obj.getIndividuosNp().get(j));
                    }
                    else
                    {
                        pos = i;
                        //this.generacion.add(this.individuos.get(i));
                    }
                }*/
                /*if(pos != -1)
                {
                    obj.getGeneracion().add(obj.getIndividuosNp().get(pos));
                }
                
            }
            
        }
        obj.burbuja();
        obj.eliminarRepetidos();*/
    }
        }
    }
    public void GenerarIndividuos(int num)
    {
        Document doc_copy = (Document) this.doc.cloneNode(true);
        for(int i=0;i<num;i++)
        {            
            Individuo ind = new Individuo(this.CAMINOS);
            ind.caminos_posibles = this.caminos;
            ind.caminos_cubiertos = this.caminos_cubiertos;
            ind.CalcularEntradas(doc_copy, this.ENTRADAS, SUPERIOR, INFERIOR);
            
            this.individuos.add(ind);
            doc_copy = (Document) this.doc.cloneNode(true);
        }
    }
    public void EvaluarIndividuos(int num)
    {
        Document doc_copy = (Document) this.doc.cloneNode(true);
        for(int i=0;i<num;i++)
        {
            this.individuos.get(i).EvaluarIndividuo(doc_copy);
            doc_copy = (Document) this.doc.cloneNode(true);
        }
    }
    public Document cargarXML()
    {
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
    public ArrayList<String> cargarCaminos()
    {
        ArrayList<String> caminos = new ArrayList();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
           // Apertura del fichero y creacion de BufferedReader para poder
           // hacer una lectura comoda (disponer del metodo readLine()).
           archivo = new File ("caminos.txt");
           fr = new FileReader (archivo);
           br = new BufferedReader(fr);

           // Lectura del fichero
           String linea;
           while((linea=br.readLine())!=null)
           {
               caminos.add(linea);
               this.caminos_cubiertos.add(0);
           }
              
        }
        catch(Exception e){
           System.out.println(e.getMessage());
        }
        return caminos;
    }
    public Individuo SeleccionarIndividuo()
    {
        int pos = (int)(Math.random()*(this.individuos.size()-1) + 0);
        return this.individuos.get(pos);
    }
    public void Recombinar()
    {
        
    }
    public int NumeroEntradas()
    {       
        NodeList lectura = this.doc.getElementsByTagName("Leer");
        return lectura.getLength();
    }
}
  
