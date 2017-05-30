/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

import java.io.FileNotFoundException;
import javax.xml.xpath.XPathExpressionException;

/**
 *
 * @author Eduardo
 */
public class PruebaXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws XPathExpressionException, FileNotFoundException {
        // TODO code application logic here
        
        //XML obj = new XML();
        //obj.cargarArchivo();
        Individuo individuo_prueba = new Individuo(6);
        individuo_prueba.EvaluarIndividuo(null);
        for(int i=0;i<individuo_prueba.getEntradas_individuo().size();i++)
        {
            for(int j = 0;j<individuo_prueba.getEntradas_individuo().get(i).getEntradas().size();j++)
            {
                System.out.println("Valor: "+individuo_prueba.getEntradas_individuo().get(i).getEntradas().get(j));
            }
            System.out.println();
            System.out.println();
        }
    }
    
}
