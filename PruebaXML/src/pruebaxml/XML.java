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
                
        Node nodoRaiz = documento.getFirstChild();
        System.out.println("Nodo raiz: "+nodoRaiz.getNodeName());
        System.out.println("Hijos de la raiz");
        NodeList listaNodosHijos = nodoRaiz.getChildNodes();
        int cont = 0;
        for(int i = 0; i< listaNodosHijos.getLength(); i++ )
        {
            //System.out.println(listaNodosHijos.item(i).getNodeName());
            //System.out.println(listaNodosHijos.item(i).getTextContent());
            //System.out.println(listaNodosHijos.item(i).getNodeValue());
            //System.out.println(listaNodosHijos.item(i).getNodeType());
            //System.out.println(listaNodosHijos.item(i).getNodeName());

            Node padre = listaNodosHijos.item(i).getParentNode();
            if(padre.getNodeName().equalsIgnoreCase("Condicional"))
            {
                if(padre.getChildNodes().item(0).isEqualNode(listaNodosHijos.item(i)))
                {
                    
                }
                else
                {
                    
                }                   
            }            
            System.out.println(listaNodosHijos.item(i).getTextContent()); 

            /*NamedNodeMap atributos = listaNodosHijos.item(i).getAttributes(  );            
            Node unAtributo = atributos.getNamedItem( "resultado_condicion" );
            String valorAtributo = unAtributo.getNodeValue();
            System.out.println(valorAtributo);*/
            cont ++;
        }
        System.out.println(cont);
    }    
}
