/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
    private Element root;
    public AlgoritmoDiferencial(int POBLACION, int entradas, int superior, int inferior)
    {
        this.POBLACION = POBLACION;
        this.ENTRADAS = entradas;
        this.SUPERIOR = superior;
        this.INFERIOR = inferior;
        this.individuos = new ArrayList();
        this.root = cargarXML();
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

    public Element getRoot() {
        return root;
    }

    public void setRoot(Element root) {
        this.root = root;
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
        
        //Individuo r1 = new Individuo();
        //Individuo r2 = new Individuo();
        //Individuo r3 = new Individuo();

        for (g = 0; g < MAX_GEN; g++) {
            for (int i = 0; i < NP; i++) {
                //r1 = obj.seleccionarIndividuos();
                //r2 = obj.seleccionarIndividuos();
                //r3 = obj.seleccionarIndividuos();
                
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
        for(int i=0;i<num;i++)
        {
            Individuo ind = new Individuo(this.CAMINOS);
            ind.CalcularEntradas(this.ENTRADAS, SUPERIOR, INFERIOR);
            this.individuos.add(ind);
        }
    }
    public void EvaluarIndividuos(int num)
    {
        for(int i=0;i<num;i++)
        {
            this.individuos.get(i).EvaluarIndividuo(this.root);            
        }
    }
    public Element cargarXML()
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document documento = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            documento = (Document) builder.parse(new File("prueba.xml"));
        } catch (Exception spe) {
            System.out.println(spe.getMessage());
        }
        Element root = documento.getDocumentElement();
        return root;
    }
}
  
