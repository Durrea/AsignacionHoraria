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
        /*Individuo individuo_prueba = new Individuo(6);
        individuo_prueba.EvaluarIndividuo(null);
        for(int i=0;i<individuo_prueba.getEntradas_individuo().size();i++)
        {
            for(int j = 0;j<individuo_prueba.getEntradas_individuo().get(i).getEntradas().size();j++)
            {
                System.out.println("Valor: "+individuo_prueba.getEntradas_individuo().get(i).getEntradas().get(j));
            }
            System.out.println();
            System.out.println();
        }*/
        AlgoritmoDiferencial obj = new AlgoritmoDiferencial(4,70,-70);
        obj.GenerarIndividuos(obj.getPOBLACION());
        /*for(int i=0;i<obj.getIndividuos().size();i++)
        {
            for(int j = 0;j<obj.getIndividuos().get(i).getEntradas_individuo().size();j++)
            {
                for(int k = 0;k<obj.getIndividuos().get(i).getEntradas_individuo().get(j).getEntradas().size();k++)
                {
                    System.out.println(obj.getIndividuos().get(i).getEntradas_individuo().get(j).getEntradas().get(k)
                    + obj.getIndividuos().get(i).getEntradas_individuo().get(j).getValores().get(k));
                }
            }
            System.out.println();
            System.out.println();
        }*/
        obj.EvaluarIndividuos(obj.getPOBLACION());
        for(int i=0;i<obj.getIndividuos().size();i++)
        {
            for(int j=0;j<obj.getIndividuos().get(i).getEntradas_individuo().size();j++)
            {
                for(int k = 0;k<obj.getIndividuos().get(i).getEntradas_individuo().get(j).getEntradas().size();k++)
                {
                    System.out.print(obj.getIndividuos().get(i).getEntradas_individuo().get(j).getEntradas().get(k)+
                            " "+obj.getIndividuos().get(i).getEntradas_individuo().get(j).getValores().get(k));
                }
                System.out.println();                
            }
            System.out.println("Evaluacion: "+obj.getIndividuos().get(i).getEvaluacion());
            System.out.println();
        }
        
        Individuo padre = obj.SeleccionarIndividuo();
        Individuo madre = obj.SeleccionarIndividuo();
        obj.Recombinar(padre, madre);
        System.out.println(".....");
        for(int i=0;i<obj.getIndividuos().size();i++)
        {
            for(int j=0;j<obj.getIndividuos().get(i).getEntradas_individuo().size();j++)
            {
                for(int k = 0;k<obj.getIndividuos().get(i).getEntradas_individuo().get(j).getEntradas().size();k++)
                {
                    System.out.print(obj.getIndividuos().get(i).getEntradas_individuo().get(j).getEntradas().get(k)+
                            " "+obj.getIndividuos().get(i).getEntradas_individuo().get(j).getValores().get(k));
                }
                System.out.println();                
            }
            System.out.println("Evaluacion: "+obj.getIndividuos().get(i).getEvaluacion());
            System.out.println();
        }
    }
    
}
