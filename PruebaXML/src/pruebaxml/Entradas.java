/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Eduardo
 */
public class Entradas {
    
    private int NUMERO_ENTRADAS;
    private int LIMITE_SUPERIOR;
    private int LIMITE_INFERIOR;
    private ArrayList<Integer> valores;
    private ArrayList<String> entradas;
    private String camino_cubierto;
    
    public Entradas(int NUM, int limite_superior, int limite_inferior)
    {
        this.NUMERO_ENTRADAS = NUM;
        this.LIMITE_SUPERIOR = limite_superior;
        this.LIMITE_INFERIOR = limite_inferior;
        this.camino_cubierto = "";
        valores = new ArrayList();
        entradas = new ArrayList();
    }

    public ArrayList<String> getEntradas() {
        return entradas;
    }

    public void setEntradas(ArrayList<String> entradas) {
        this.entradas = entradas;
    }
    
    public ArrayList<Integer> getValores() {
        return valores;
    }

    public void setValores(ArrayList<Integer> valores) {
        this.valores = valores;
    }
    
    public String getCamino_cubierto() {
        return camino_cubierto;
    }

    public void setCamino_cubierto(String camino_cubierto) {
        this.camino_cubierto = camino_cubierto;
    }
    
    public int getNUMERO_ENTRADAS() {
        return NUMERO_ENTRADAS;
    }

    public void setNUMERO_ENTRADAS(int NUMERO_ENTRADAS) {
        this.NUMERO_ENTRADAS = NUMERO_ENTRADAS;
    }

    public int getLIMITE_SUPERIOR() {
        return LIMITE_SUPERIOR;
    }

    public void setLIMITE_SUPERIOR(int LIMITE_SUPERIOR) {
        this.LIMITE_SUPERIOR = LIMITE_SUPERIOR;
    }

    public int getLIMITE_INFERIOR() {
        return LIMITE_INFERIOR;
    }

    public void setLIMITE_INFERIOR(int LIMITE_INFERIOR) {
        this.LIMITE_INFERIOR = LIMITE_INFERIOR;
    }
    
    public void GenerarEntradasAleatorias(Document doc)
    {
        NodeList lectura = doc.getElementsByTagName("Leer");
        int valor;
        for(int i=0;i<this.NUMERO_ENTRADAS;i++)
        {
            if(this.LIMITE_SUPERIOR == 0 && this.LIMITE_INFERIOR == 0)
            {
                valor = (int) (Math.random());
                this.valores.add(valor);
                
            }
            else
            {
                if(this.LIMITE_INFERIOR < 0)
                {
                    valor = this.LIMITE_INFERIOR + (int) (Math.random() * (this.LIMITE_SUPERIOR  - this.LIMITE_INFERIOR));
                    this.valores.add(valor);
                }
                else
                {
                    valor = (int) (Math.random() * this.LIMITE_SUPERIOR + this.LIMITE_INFERIOR);
                    this.valores.add(valor);
                }                
            }
            this.entradas.add(lectura.item(i).getAttributes().getNamedItem("valor").getTextContent());
        }
    }
}
