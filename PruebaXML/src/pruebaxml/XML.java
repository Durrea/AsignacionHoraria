/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Eduardo
 */
public class XML {

    ArrayList<String> entradas = new ArrayList();
    ArrayList<String> valores = new ArrayList();

    public void cargarArchivo() throws XPathExpressionException, FileNotFoundException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document documento = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            documento = (Document) builder.parse(new File("prueba.xml"));
        } catch (Exception spe) {
            System.out.println(spe.getMessage());
        }
        //String path1 = "//Proceso[@valor='Print P Large']";
        //String path2 = "//Proceso[@valor='NADA']";
        String path1 = "/Inicio/Leer/Leer/Condicional/Proceso/Condicional/Proceso";
        ArrayList<String> paths = new ArrayList();
        paths.add(path1);
        //paths.add(path2);
        //documento.normalizeDocument();
        int caminos = numeroCaminos(documento, paths);
        System.out.println("El numero de caminos es: "+ caminos);
        Element nodoRaiz = documento.getDocumentElement();
        
        //imprimirHijos(nodoRaiz);
        NodeList hijosRaiz = nodoRaiz.getChildNodes();
        NodeList hijos2 = hijosRaiz.item(1).getChildNodes();
        NodeList hijos3 = hijos2.item(1).getChildNodes();
        NodeList hijos4 = hijos3.item(1).getChildNodes();
        imprimirNodos(hijos4);
        //Node hijo = hijosRaiz.item(0);
        
        //System.out.println("Tamaño: "+ hijo.getNodeName());
        //System.out.println(hijoRaiz.getNodeName()+" : "+hijoRaiz.getAttributes().getNamedItem("valor").getTextContent());
        
        NodeList lectura = documento.getElementsByTagName("Leer");
        for (int i = 0; i < lectura.getLength(); i++) {
            //System.out.println(lectura.item(i).getAttributes().getNamedItem("valor"));
            entradas.add(String.valueOf(lectura.item(i).getAttributes().getNamedItem("valor").getTextContent()));
            valores.add(String.valueOf(Math.floor(Math.random() * 100 + 1)));
        }
        //System.out.println("Nodo raiz: " + nodoRaiz.getNodeName());
        //System.out.println("Ultimo hijo: " + documento.getLastChild().getTextContent());
        //System.out.println("Hijos de la raiz");
        //imprimirHijos(nodoRaiz);
        /*reemplazarEntradas(nodoRaiz);
        try {
            evaluarGrafo(nodoRaiz);
        } catch (ScriptException ex) {
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        //return nodos;
    }

    public void imprimirHijos(Node nodoRaiz) {
        NodeList listahijos = nodoRaiz.getChildNodes();
        for (int i = 0; i < listahijos.getLength(); i++) {
            Node nodo = listahijos.item(i);
            //if (!nodo.getNodeName().equalsIgnoreCase("#text")) {
                System.out.println(nodo.getNodeName()+" : ");
                imprimirHijos(nodo);
            //}
        }
    }
    public void LimpiarGrafo(Node nodoRaiz)
    {
        NodeList listahijos = nodoRaiz.getChildNodes();
        for (int i = 0; i < listahijos.getLength(); i++) {
            if(listahijos.item(i).getNodeName().equalsIgnoreCase("#text"))
            {
                nodoRaiz.removeChild(listahijos.item(i));
            }            
            LimpiarGrafo(listahijos.item(i));
        }
        imprimirHijos(nodoRaiz);
    }
    
    
    public void Camino() {

    }

    public void reemplazarEntradas(Node nodoRaiz) {
        NodeList listahijos = nodoRaiz.getChildNodes();
        for (int i = 0; i < listahijos.getLength(); i++) {
            Node nodo = listahijos.item(i);
            if (nodo instanceof Element) {
                for (int j = 0; j < entradas.size(); j++) {
                    if (((Element) nodo).getTagName().equals("Condicional") || ((Element) nodo).getTagName().equals("Leer")) {
                        nodo.getAttributes().getNamedItem("valor").
                                setTextContent(nodo.getAttributes().getNamedItem("valor").
                                        getTextContent().replace(entradas.get(j), valores.get(j)));
                    }
                }
                reemplazarEntradas(nodo);

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
        imprimirHijos(nodoRaiz);
    }
    
    public int numeroCaminos(Document doc, ArrayList<String> paths) throws XPathExpressionException
    {
        XPath xpath = XPathFactory.newInstance().newXPath();
        int numcaminos = 0;
        for(int i=0;i<paths.size();i++)            
        {
            NodeList nodos = (NodeList) xpath.evaluate(paths.get(i), doc, XPathConstants.NODESET);
            numcaminos = numcaminos + nodos.getLength();
        }
        return numcaminos;
    }
    public void imprimirNodos(NodeList hijos)
    {
        for(int i=0;i<hijos.getLength();i++)
        {
            //if(!hijos.item(i).getNodeName().equalsIgnoreCase("#text"))
            //{
                System.out.println("Nodo: "+hijos.item(i).getNodeName());
            //}
        }
    }
}
