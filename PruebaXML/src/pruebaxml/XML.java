/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

import java.io.File;
import java.util.ArrayList;
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

    public NodeList cargarArchivo() throws XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document documento = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            documento = (Document) builder.parse(new File("prueba.xml"));
        } catch (Exception spe) {
            System.out.println(spe.getMessage());
        }
        String path = "//Proceso[@valor='Print P Large']";
        XPath xpath = XPathFactory.newInstance().newXPath();
        //System.out.println("Llego aqui");
        NodeList nodos = (NodeList) xpath.evaluate(path, documento, XPathConstants.NODESET);
        for (int i = 0; i < nodos.getLength(); i++) {
            System.out.println(nodos.item(i).getNodeName() + " : "
                    + nodos.item(i).getAttributes().getNamedItem("valor"));
        }

        Element nodoRaiz = documento.getDocumentElement();
        NodeList lectura = documento.getElementsByTagName("Leer");
        for (int i = 0; i < lectura.getLength(); i++) {
            //System.out.println(lectura.item(i).getAttributes().getNamedItem("valor"));
            entradas.add(String.valueOf(lectura.item(i).getAttributes().getNamedItem("valor").getTextContent()));
            valores.add(String.valueOf(Math.floor(Math.random() * 100 + 1)));
        }
        System.out.println("Nodo raiz: " + nodoRaiz.getNodeName());
        System.out.println("Ultimo hijo: " + documento.getLastChild().getTextContent());
        System.out.println("Hijos de la raiz");
        //imprimirHijos(nodoRaiz);
        reemplazarEntradas(nodoRaiz);

        return nodos;
    }

    public void imprimirHijos(Node nodoRaiz) {
        NodeList listahijos = nodoRaiz.getChildNodes();
        for (int i = 0; i < listahijos.getLength(); i++) {
            Node nodo = listahijos.item(i);
            if (nodo instanceof Element) {
                System.out.println(nodo.getAttributes().getNamedItem("valor"));
                imprimirHijos(nodo);
            }
        }
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
        imprimirHijos(nodoRaiz);
    }

}
