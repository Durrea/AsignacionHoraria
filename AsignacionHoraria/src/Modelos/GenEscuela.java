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
public class GenEscuela{
    private InfoMaterias materia;
    private ArrayList<FranjaHoraria> horarios; 
    private int value;
    
    public GenEscuela()
    {
        horarios = new ArrayList<>();
    }

    public GenEscuela(InfoMaterias materia, ArrayList<FranjaHoraria> horarios) {
        this.materia = materia;
        this.horarios = horarios;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    
    public InfoMaterias getMateria() {
        return materia;
    }

    public void setMateria(InfoMaterias materia) {
        this.materia = materia;
    }

    public ArrayList<FranjaHoraria> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<FranjaHoraria> horarios) {
        this.horarios = horarios;
    }    
}
