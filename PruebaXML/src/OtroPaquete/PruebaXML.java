/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OtroPaquete;

import pruebaxml.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        //AlgoritmoDiferencial obj = new AlgoritmoDiferencial(2,80,-50);
        Memetico obj_memetico = new Memetico();
        obj_memetico.POBLACION = 7;
        obj_memetico.SUPERIOR = 10;
        obj_memetico.INFERIOR = 0;
        obj_memetico.NUM_HIJOS = 3;
        obj_memetico.ITE_BUSQUEDA_LOCAL = 3;
        obj_memetico.CRITERIO_MEMETICO = 6;
        ArrayList<Individuo> result = obj_memetico.run();
        for(int i=0;i<result.size();i++)
        {
            for(int j=0;j<result.get(i).getEntradas_individuo().size();j++)
            {
                System.out.println("Valor: "+ result.get(i).getEntradas_individuo().get(j));
            }
            System.out.println("Evaluacion: "+ result.get(i).getEvaluacion());
            System.out.println();
            System.out.println();
        }
        //obj.GenerarIndividuos(obj.getPOBLACION());
        /*for(int i=0;i<obj.getIndividuos().size();i++)
        {
            for(int j = 0;j<obj.getIndividuos().get(i).getEntradas_individuo().size();j++)
            {
                for(int k = 0;k<obj.getIndividuos().get(i).getEntradas_individuo().get(j).getEntradas().size();k++)
                for(int k = 0;k<result.get(i).getEntradas_individuo().get(j).getEntradas().size();k++)
                {
                    System.out.print(result.get(i).getEntradas_individuo().get(j).getEntradas().get(k)+
                            " "+result.get(i).getEntradas_individuo().get(j).getValores().get(k));
                }
                System.out.println();                
            }
            System.out.println("Evaluacion: "+result.get(i).getEvaluacion());
            System.out.println();
        }*/
 /*AlgoritmoDiferencial obj = new AlgoritmoDiferencial(2,80,-50);
        obj.AlgoritmoDiferencial();
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
        } */
    }

}
