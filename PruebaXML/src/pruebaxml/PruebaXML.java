/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

import javax.xml.xpath.XPathExpressionException;

/**
 *
 * @author Eduardo
 */
public class PruebaXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws XPathExpressionException {
        // TODO code application logic here
        
        XML obj = new XML();
        obj.cargarArchivo();
    }
    
}
