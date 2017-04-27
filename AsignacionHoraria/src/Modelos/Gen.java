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
    
    private InfoMaterias materia;
    private ArrayList<FranjaHoraria> horarios;
    private ArrayList<InfoAulas> aulas;  
    
    public Gen()
    {
        horarios = new ArrayList<>();
        aulas = new ArrayList<>();
    }

    public Gen(InfoMaterias materia, ArrayList<FranjaHoraria> horarios, ArrayList<InfoAulas> aulas) {
        this.materia = materia;
        this.horarios = horarios;
        this.aulas = aulas;
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

    public ArrayList<InfoAulas> getAulas() {
        return aulas;
    }

    public void setAulas(ArrayList<InfoAulas> aulas) {
        this.aulas = aulas;
    }
    
    
    
}
