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
public class InfoDocente {
    
    private String nombrePrograma;
    private String nombreDocente;
    private ArrayList<InfoMaterias> asignaturas;
    
    
    public InfoDocente(){}
    
    public InfoDocente(String nombrePrograma, String nombreDocente) {
        this.nombrePrograma = nombrePrograma;
        this.nombreDocente = nombreDocente;
        this.asignaturas = new ArrayList();
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public ArrayList<InfoMaterias> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(ArrayList<InfoMaterias> asignaturas) {
        this.asignaturas = asignaturas;
    }
    
}
