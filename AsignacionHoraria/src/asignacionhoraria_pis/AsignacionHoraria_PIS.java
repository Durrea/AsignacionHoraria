/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asignacionhoraria_pis;

import Modelos.InfoDocente;
import Modelos.InfoMaterias;
import Servicios.CargueDatos;
import java.util.ArrayList;

/**
 *
 * @author ingesis
 */
public class AsignacionHoraria_PIS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        CargueDatos c = new CargueDatos();
        c.CargarDatos();
        ArrayList<InfoMaterias> materias = c.getMaterias();
        ArrayList<InfoDocente> docentes = c.getDocentes();
        //materias = c.CargarDatosMaterias();
        for (int i = 0; i < c.getMaterias().size(); i++) 
        {
            System.out.println(c.getMaterias().get(i).getNombreMateria()+""+materias.get(i).getPosDocente());
        }
        /*for (int i = 0; i < docentes.size(); i++) 
        {
            System.out.println(docentes.get(i).getNombreDocente());
        }*/
    }
    
}
