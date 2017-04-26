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
public class InfoAulas {
    
    private int idsalon;
    private String nombreAula;
    private int capacidad;
    private String tipo;
    private ArrayList<FranjaHoraria> horarios;

    public InfoAulas(int idsalon, String nombreAula, int capacidad, String tipo) {
        this.idsalon = idsalon;
        this.nombreAula = nombreAula;
        this.capacidad = capacidad;
        this.tipo = tipo;
        this.horarios = new ArrayList();
    }

    public int getIdsalon() {
        return idsalon;
    }

    public void setIdsalon(int idsalon) {
        this.idsalon = idsalon;
    }

    public String getNombreAula() {
        return nombreAula;
    }

    public void setNombreAula(String nombreAula) {
        this.nombreAula = nombreAula;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<FranjaHoraria> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<FranjaHoraria> horarios) {
        this.horarios = horarios;
    }

    
        
    
}
