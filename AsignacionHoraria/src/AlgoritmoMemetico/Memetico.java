/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgoritmoMemetico;

import Modelos.Gen;
import Servicios.CargueDatos;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class Memetico implements IMemetico{

    @Override
    public void ejecutar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList generarPoblacionInicial() {
       
        ArrayList<Gen> genes = null;
        CargueDatos datos = new CargueDatos();
        
        for (int i = 0; i < datos.getMaterias().size(); i++) {
            
            System.out.println(datos.getMaterias().get(i).getNombreMateria());
        }
       
        
        return genes;
    }

    @Override
    public double funcionAdaptacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
