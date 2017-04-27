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
public class InfoMaterias {
    
    private String nombrePrograma;
    private String codigoMateria;
    private String nombreMateria;
    private String grupoMateria;
    private int cupoMateria;
    private int semestre;
    private int posDocente;
    private String tipoMateria;
 
    public InfoMaterias() {}

    public InfoMaterias(String nombrePrograma, String codigoMateria, String nombreMateria, String grupoMateria, int cupoMateria, int semestre,String tipoMateria) {
        this.nombrePrograma = nombrePrograma;
        this.codigoMateria = codigoMateria;
        this.nombreMateria = nombreMateria;
        this.grupoMateria = grupoMateria;
        this.cupoMateria = cupoMateria;
        this.semestre = semestre;
        this.posDocente = -1;
        this.tipoMateria = tipoMateria;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public String getCodigoMateria() {
        return codigoMateria;
    }

    public void setCodigoMateria(String codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getGrupoMateria() {
        return grupoMateria;
    }

    public void setGrupoMateria(String grupoMateria) {
        this.grupoMateria = grupoMateria;
    }

    public int getCupoMateria() {
        return cupoMateria;
    }

    public void setCupoMateria(int cupoMateria) {
        this.cupoMateria = cupoMateria;
    }
    
     public int getPosDocente() {
        return posDocente;
    }

    public void setPosDocente(int posDocente) {
        this.posDocente = posDocente;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }
    
    public String getTipoMateria() {
        return tipoMateria;
    }

    public void setTipoMateria(String tipoMateria) {
        this.tipoMateria = tipoMateria;
    }
}
