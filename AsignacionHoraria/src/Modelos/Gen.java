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
public class Gen {
    
    private String codigoMateria;
    private int costo;
    private ArrayList<FranjaHoraria> horarios;

    public Gen(String codigoMateria, int costo, ArrayList<FranjaHoraria> horarios) {
        this.codigoMateria = codigoMateria;
        this.costo = costo;
        this.horarios = horarios;
    }

    public String getCodigoMateria() {
        return codigoMateria;
    }

    public void setCodigoMateria(String codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public ArrayList<FranjaHoraria> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<FranjaHoraria> horarios) {
        this.horarios = horarios;
    }
    
    
    
}
