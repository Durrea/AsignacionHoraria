/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
    
    public void cargarArchivo()            
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ( );
        Document documento = null;
        try
        {
           DocumentBuilder builder = factory.newDocumentBuilder();           
           documento = (Document) builder.parse(new File("prueba.xml"));
        }
        catch (Exception spe)
        {
           System.out.println(spe.getMessage());
        }
                
        Element nodoRaiz = documento.getDocumentElement();
        
        System.out.println("Nodo raiz: "+nodoRaiz.getNodeName());
        //System.out.println("Ultimo hijo: "+documento.getLastChild().getTextContent());
        System.out.println("Hijos de la raiz");
        imprimirHijos(nodoRaiz);
    }
    public void imprimirHijos(Node nodoRaiz)
    {
        NodeList listahijos = nodoRaiz.getChildNodes();
        for(int i = 0; i< listahijos.getLength(); i++ )
        {
            Node nodo = listahijos.item(i);
            if(nodo instanceof Element)
            {
                System.out.println(nodo.getNodeName());
                imprimirHijos(nodo);
            }         
        }    
    }
}
