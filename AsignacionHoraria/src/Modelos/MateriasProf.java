/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.util.ArrayList;

/**
 *
 * @author ingesis
 */
public class MateriasProf {
    
    private String codigoMateria;
    private ArrayList<FranjaHoraria> horariosCoste;

    public MateriasProf(String codigoMateria, ArrayList<FranjaHoraria> horariosCoste) {
        this.codigoMateria = codigoMateria;
        this.horariosCoste = horariosCoste;
    }

    public String getCodigoMateria() {
        return codigoMateria;
    }

    public void setCodigoMateria(String codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public ArrayList<FranjaHoraria> getHorariosCoste() {
        return horariosCoste;
    }

    public void setHorariosCoste(ArrayList<FranjaHoraria> horariosCoste) {
        this.horariosCoste = horariosCoste;
    }
    
    
}
