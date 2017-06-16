/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

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
    private ArrayList<Entradas> entradas_individuo;
    private double evaluacion;
    public ArrayList<String> caminos;
    public ArrayList<String> caminos_posibles;
    public ArrayList<Integer> caminos_cubiertos;
    
    public Individuo(int caminos)
    {
        this.CAMINOS = caminos;
        entradas_individuo = new ArrayList();
        this.evaluacion = -1;
        this.caminos = new ArrayList();
        this.caminos_posibles = new ArrayList();
        this.caminos_cubiertos = new ArrayList();
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
    
    public void EvaluarIndividuo(Document doc)
    {        
        Element root = doc.getDocumentElement();
        Node root_copy = root.cloneNode(true);
        String camino = "/Inicio";
        for(int i = 0; i< this.entradas_individuo.size(); i++ )
        {
            reemplazarEntradas(root_copy,this.entradas_individuo.get(i).getValores(),this.entradas_individuo.get(i).getEntradas());            
            //imprimirHijos(root_copy);
            try
            {
                evaluarGrafo(root_copy);
                //imprimirHijos(root_copy);
            }catch(Exception e)
            {
                System.out.println(e.getMessage());
            }            
            ConstruirCamino(root_copy, camino);
            this.entradas_individuo.get(i).setCamino_cubierto(this.caminos.get(0));
            //System.out.println(this.caminos.get(0));
            this.caminos.clear();
            camino = "/Inicio";
            //imprimirHijos(root);
            //System.out.println("Camino entrada: "+this.entradas_individuo.get(i).getCamino_cubierto());
            //System.out.println();
            //System.out.println();
            root_copy = root.cloneNode(true);
            //root = (Element) root_copy.cloneNode(true);
        }
        int cubiertos = CaminosCubiertos(this.entradas_individuo);
        //System.out.println("Evaluacion individuo: " + cubiertos);
        /*for(int i =0;i<this.caminos_cubiertos.size();i++)
        {
            System.out.println("Camino "+i+" valor "+this.caminos_cubiertos.get(i));
        }*/               
        this.evaluacion = cubiertos;
        this.evaluacion = this.evaluacion / this.CAMINOS;
        //System.out.println("Evaluacion: "+this.evaluacion);
    }
    public void CalcularEntradas(Document doc, int num_entradas, int superior, int inferior)
    {
        for(int i=0;i<this.CAMINOS;i++)
        {
            Entradas obj = new Entradas(num_entradas,superior,inferior);
            obj.GenerarEntradasAleatorias(doc);
            this.entradas_individuo.add(obj);
        }
    }
    public void reemplazarEntradas(Node nodoRaiz, ArrayList<Integer> valores, ArrayList<String> entradas) {
        NodeList listahijos = nodoRaiz.getChildNodes();
        for (int i = 0; i < listahijos.getLength(); i++) {
            Node nodo = listahijos.item(i);
            if (nodo instanceof Element) {
                for (int j = 0; j < entradas.size(); j++) {
                    if (((Element) nodo).getTagName().equals("Condicional") || ((Element) nodo).getTagName().equals("Leer")) {
                        nodo.getAttributes().getNamedItem("valor").
                                setTextContent(nodo.getAttributes().getNamedItem("valor").
                                        getTextContent().replace(entradas.get(j), Integer.toString(valores.get(j))));
                    }
                }
                reemplazarEntradas(nodo,valores,entradas);
            }
        }
        //imprimirHijos(nodoRaiz);
    }

    public void evaluarGrafo(Node nodoRaiz) throws ScriptException {

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        NodeList listahijos = nodoRaiz.getChildNodes();
        for (int i = 0; i < listahijos.getLength(); i++) {
            Node nodo = listahijos.item(i);
            if (nodo instanceof Element) {
                if (((Element) nodo).getTagName().equals("Condicional")) {
                    nodo.getAttributes().getNamedItem("valor").
                            setTextContent(nodo.getAttributes().getNamedItem("valor").
                                    getTextContent().replace("mayor", ">"));
                    nodo.getAttributes().getNamedItem("valor").
                            setTextContent(nodo.getAttributes().getNamedItem("valor").
                                    getTextContent().replace("menor", "<"));
                    nodo.getAttributes().getNamedItem("valor").
                            setTextContent(nodo.getAttributes().getNamedItem("valor").
                                    getTextContent().replace("mayorigual", ">="));
                    nodo.getAttributes().getNamedItem("valor").
                            setTextContent(nodo.getAttributes().getNamedItem("valor").
                                    getTextContent().replace("menorigual", "<="));
                    //nodo.getAttributes().getNamedItem("valor").
                            //setTextContent(nodo.getAttributes().getNamedItem("valor").
                                    //getTextContent().replace("diferente", "<>"));
                    nodo.getAttributes().getNamedItem("valor").
                            setTextContent(nodo.getAttributes().getNamedItem("valor").
                                    getTextContent().replace("igual", "=="));

                    Object eval = engine.eval(nodo.getAttributes().getNamedItem("valor").getTextContent());
                    nodo.getAttributes().getNamedItem("valor").setTextContent(String.valueOf(eval));
                }
            }
            evaluarGrafo(nodo);
        }
        //imprimirHijos(nodoRaiz);
    }
    public void imprimirHijos(Node nodoRaiz) {
        NodeList listahijos = nodoRaiz.getChildNodes();
        for (int i = 0; i < listahijos.getLength(); i++) {
            Node nodo = listahijos.item(i);
            if (!nodo.getNodeName().equalsIgnoreCase("#text")) {
                System.out.println(nodo.getNodeName()+" : "+nodo.getAttributes().getNamedItem("valor").getTextContent());
                imprimirHijos(nodo);
            }
        }
    }
    public void ConstruirCamino(Node nodoRaiz, String camino)
    {
        NodeList listahijos = nodoRaiz.getChildNodes();
        if(nodoRaiz.getNodeName().equalsIgnoreCase("Condicional"))
        {            
            if(nodoRaiz.getAttributes().getNamedItem("valor").getTextContent().equalsIgnoreCase("true"))
            {
                Node nodo = nodoRaiz.getChildNodes().item(1);
                camino = camino+"/"+nodo.getNodeName();
                //this.caminos.add(camino);
                //System.out.println(camino);
                ConstruirCamino(nodo,camino);
            }
            else
            {
                Node nodo = nodoRaiz.getChildNodes().item(3);
                camino = camino+"/"+nodo.getNodeName();
                //this.caminos.add(camino);
                //System.out.println(camino);
                ConstruirCamino(nodo,camino);
            }
        }
        else
        {            
            for(int i = 0;i<listahijos.getLength();i++)
            {
                Node nodo = listahijos.item(i);            
                if(!nodo.getNodeName().equalsIgnoreCase("#text"))
                {
                    if(nodo.getNodeName().equalsIgnoreCase("Condicional"))
                    {
                        if(nodo.getAttributes().getNamedItem("valor").getTextContent().equalsIgnoreCase("true"))
                        {
                            camino = camino+"/"+nodo.getNodeName()+"/YES";
                            //this.caminos.add(camino);
                            ConstruirCamino(nodo,camino);                                                         
                        }
                        else
                        {
                            camino = camino+"/"+nodo.getNodeName()+"/NO";                        
                            //this.caminos.add(camino);
                            ConstruirCamino(nodo,camino);
                        }
                    }
                    else
                    {
                        if(nodoRaiz.getNodeName().equalsIgnoreCase("Condicional"))
                        {
                            camino = camino+"/"+nodo.getNodeName();
                            //this.caminos.add(camino);
                            //System.out.println(camino);
                            ConstruirCamino(nodo,camino);
                        }
                        else
                        {
                            camino = camino+"/"+nodo.getNodeName();
                            //this.caminos.add(camino);
                            //System.out.println(camino);
                            ConstruirCamino(nodo,camino);
                        }                                        
                    }                                
                }
            }
        }        
        this.caminos.add(camino);        
    }
    public int CaminosCubiertos(ArrayList<Entradas> entradas_individuo)
    {
        for(int i=0;i<this.caminos_cubiertos.size();i++)
        {
            this.caminos_cubiertos.set(i, 0);
        }
        for(int i=0;i<this.CAMINOS;i++)
        {
            for(int j =0;j<this.CAMINOS;j++)
            {
                if(entradas_individuo.get(i).getCamino_cubierto().equalsIgnoreCase(this.caminos_posibles.get(j)))
                {
                    this.caminos_cubiertos.set(j, this.caminos_cubiertos.get(j)+1);
                    j = this.CAMINOS;
                }
            }
        }
        int num_caminos_cubiertos = 0;
        for(int i=0;i<this.caminos_cubiertos.size();i++)
        {
            if(this.caminos_cubiertos.get(i) != 0)
            {
                num_caminos_cubiertos = num_caminos_cubiertos + 1;  
            }
        }
        return num_caminos_cubiertos;
    }
    public Individuo GenerarVecindario(Individuo individuo, int numvecinos, int superior, Document doc)
    {
        ArrayList<Individuo> vecindario = new ArrayList();        
        int posicion_mejor = -1;
        superior = superior /10;
        for(int i=0;i<numvecinos;i++)
        {            
            Individuo ind = new Individuo(individuo.CAMINOS);
            ind = individuo.clone();            
            int entrada = (int) (Math.random()*ind.entradas_individuo.size()-1);
            int valor = (int)(Math.random() * ind.entradas_individuo.get(entrada).getValores().size()-1);
            int operador = (int)(Math.random()*1);//0 para suma y 1 para resta
            int numero = (int) (Math.random()*superior);
            
            if(operador == 0)
            {
                //int v = ind.entradas_individuo.get(entrada).getValores().get(valor)+numero;
                ind.entradas_individuo.get(entrada).getValores().set(valor, ind.entradas_individuo.get(entrada).getValores().get(valor)+numero);
                //System.out.println("Valor generado: "+v);
            }
            else
            {
                //int v = ind.entradas_individuo.get(entrada).getValores().get(valor)-numero;
                ind.entradas_individuo.get(entrada).getValores().set(valor, ind.entradas_individuo.get(entrada).getValores().get(valor)-numero);
                //System.out.println("Valor generado: "+v);
            }
            ind.EvaluarIndividuo(doc);
            if(posicion_mejor == -1)
            {
                vecindario.add(ind);
                posicion_mejor = i;
            }
            else
            {
                vecindario.add(ind);
                if(vecindario.get(i).evaluacion > vecindario.get(posicion_mejor).evaluacion)
                {
                    posicion_mejor = i;
                }
            }            
        }
        Individuo ind_retorno = vecindario.get(posicion_mejor).clone();
        return ind_retorno;
        
    }
    public Individuo clone()
    {
        Individuo nuevo = new Individuo(this.CAMINOS);
        nuevo.caminos = (ArrayList<String>) this.caminos.clone();
        nuevo.caminos_cubiertos = (ArrayList<Integer>) this.caminos_cubiertos.clone();
        nuevo.caminos_posibles = (ArrayList<String>) this.caminos_posibles.clone();
        nuevo.entradas_individuo = (ArrayList<Entradas>) this.entradas_individuo.clone();
        return nuevo;        
    }
}
