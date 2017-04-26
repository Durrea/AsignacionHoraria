/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgoritmoMemetico;

import java.util.ArrayList;

/**
 *
 * @author Eduardo
 */
public interface Memetico {
    
    public void ejecutar();
    public ArrayList generarPoblacionInicial();
    public double funcionAdaptacion();
}
