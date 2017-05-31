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
    
    public void EvaluarIndividuo(Document doc)
    {
        //CalcularValores(num_entradas,superior,inferior);
        Element root = doc.getDocumentElement();
        Node root_copy = root.cloneNode(true);
        for(int i = 0; i< this.entradas_individuo.size(); i++ )
        {
            reemplazarEntradas(root,this.entradas_individuo.get(i).getValores(),this.entradas_individuo.get(i).getEntradas());           
            //imprimirHijos(root);
            try
            {
                evaluarGrafo(root);
            }catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            //String camino = "/Inicio";
            //ConstruirCamino(root, camino);
            //System.out.println("Camino: "+camino);
            //camino = "/Inicio";
            //imprimirHijos(root);
            imprimirRamas(root);
            System.out.println();
            System.out.println();
            root = (Element) root_copy.cloneNode(true);
        }
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
        for(int i = 0;i<listahijos.getLength();i++)
        {
            Node nodo = listahijos.item(i);            
            if(!nodo.getNodeName().equalsIgnoreCase("#text"))
            {
                if(nodo.getNodeName().equalsIgnoreCase("Condicional"))
                {
                    if(nodo.getAttributes().getNamedItem("valor").getTextContent().equalsIgnoreCase("true"))
                    {
                        //nodo = nodo.getChildNodes().item(1);
                        nodo = nodo.getNextSibling();
                        camino = camino+"/"+nodo.getNodeName()+"/YES";
                        System.out.println(camino);
                    }
                    else
                    {
                        camino = camino+"/"+nodo.getNodeName()+"/NO";
                        nodo = nodo.getNextSibling();
                        //nodo = nodo.getChildNodes().item(3);
                        System.out.println(camino);
                    }
                }
                else
                {
                    camino = camino+"/"+nodo.getNodeName();
                }                
                ConstruirCamino(nodo,camino);
            }
        }
    }
    public void imprimirRamas(Node nodoRaiz)
    {        
        Node nodo = nodoRaiz.getNextSibling();
        if(nodo == null)
        {
            System.out.println("Es null");
        }
        else
        {
            System.out.println(nodo.getNodeName());
        }
    }
}
