/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgoritmoMemetico;

import Servicios.CargueDatos;
import java.util.ArrayList;

/**
 *
 * @author Eduardo
 */
public interface IMemetico {
    
    public void ejecutar();
    public ArrayList generarPoblacionInicial(CargueDatos datos);
    public ArrayList generarNuevaPoblacion(ArrayList pop, CargueDatos datos);
    public ArrayList actualizarPoblacion(ArrayList pop, ArrayList newPop);
    public ArrayList reiniciarPoblacion(ArrayList pop, CargueDatos datos);
    public double funcionAdaptacion();
}
