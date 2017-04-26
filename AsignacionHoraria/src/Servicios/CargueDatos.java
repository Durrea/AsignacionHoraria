/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.InfoDocente;
import Modelos.InfoMaterias;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo
 */
public class CargueDatos {
    
    private FileReader file;
    private BufferedReader buffer;
    private ArrayList<InfoDocente> docentes;
    private ArrayList<InfoMaterias> materias;
    
    public CargueDatos()
    {
        this.docentes = new ArrayList();
        this.materias = new ArrayList();
    }
    public ArrayList CargarDatosProfesor()
    {
        ArrayList<InfoDocente> docs = new ArrayList();
        try {
            String linea;
            file = new FileReader("profesores.txt");
            buffer = new BufferedReader(file);
            while((linea = buffer.readLine())!=null)
            {
                String[] datosprof = linea.split(";");
                InfoDocente docente = new InfoDocente(datosprof[0],datosprof[1]);                
                docs.add(docente);
            }
            buffer.close();
        } catch (Exception ex) {
            System.out.println("Error");
        }
        return docs;
    }
    public ArrayList CargarDatosMaterias()
    {
        ArrayList<InfoMaterias> mats = new ArrayList();
        try {
            String linea;
            file = new FileReader("curso_semestre.txt");
            buffer = new BufferedReader(file);
            while((linea = buffer.readLine())!=null)
            {
                String[] datosmat = linea.split(";");
                InfoMaterias materia = new InfoMaterias(datosmat[0],datosmat[1],datosmat[2],datosmat[3],Integer.parseInt(datosmat[4]),Integer.parseInt(datosmat[5]));
                mats.add(materia);
            }
            buffer.close();
        } catch (Exception ex) {
            System.out.println("Error");
        }
        return materias;
    }
    public void CargarDatos()
    {
        this.docentes = CargarDatosProfesor();
        this.materias = CargarDatosMaterias();
        try{
            String linea; 
            file = new FileReader("profesor_curso.txt");
            buffer = new BufferedReader(file);
            while((linea = buffer.readLine())!=null)
            {
                String[] datos = linea.split(";");
                for (int i = 0; i < this.docentes.size(); i++) 
                {
                    if(datos[3].equalsIgnoreCase(this.docentes.get(i).getNombreDocente()))
                    {
                        for (int j = 0; j < this.materias.size(); j++) 
                        {
                            if(this.materias.get(j).getNombreMateria().equalsIgnoreCase(datos[1])&&
                               this.materias.get(j).getGrupoMateria().equalsIgnoreCase(datos[2]))
                            {
                                this.docentes.get(i).getAsignaturas().add(this.materias.get(j));
                                this.materias.get(j).setPosDocente(i);
                                j = this.materias.size();
                            }
                        }
                        i = this.docentes.size();
                    }
                }
            }
            
        }catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        
    }

    public ArrayList<InfoDocente> getDocentes() {
        return docentes;
    }

    public void setDocentes(ArrayList<InfoDocente> docentes) {
        this.docentes = docentes;
    }

    public ArrayList<InfoMaterias> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<InfoMaterias> materias) {
        this.materias = materias;
    }
    
}
