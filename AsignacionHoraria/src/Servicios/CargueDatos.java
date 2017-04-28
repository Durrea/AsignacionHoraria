/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.FranjaHoraria;
import Modelos.InfoAulas;
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
    
    private ArrayList<InfoDocente> docentes;
    private ArrayList<InfoMaterias> materias;
    private ArrayList<InfoAulas> aulas;
    private ArrayList<FranjaHoraria> franjas;
    
    public CargueDatos()
    {
        this.docentes = new ArrayList();
        this.materias = new ArrayList();
        this.aulas = new ArrayList();
        this.franjas = new ArrayList();
    }
    public ArrayList CargarDatosProfesor(int PROBLEMA)
    {
        FileReader file;
        BufferedReader buffer;
        ArrayList<InfoDocente> docs = new ArrayList();
        try {
            String linea;
            if(PROBLEMA == 1)
            {
                file = new FileReader("profesores.txt");
            }
            else
            {
                file = new FileReader("profesores2.txt");
            }
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
    public ArrayList CargarDatosMaterias(int PROBLEMA)
    {
        FileReader file;
        BufferedReader buffer;
        ArrayList<InfoMaterias> mats = new ArrayList();
        try {
            String linea;
            if(PROBLEMA == 1)
            {
                file = new FileReader("curso_semestre.txt");
            }
            else
            {
                file = new FileReader("curso_semestre2.txt");
            }
            buffer = new BufferedReader(file);
            while((linea = buffer.readLine())!=null)
            {
                String[] datosmat = linea.split(";");
                InfoMaterias materia = new InfoMaterias(datosmat[0],datosmat[1],datosmat[2],datosmat[3],Integer.parseInt(datosmat[4]),Integer.parseInt(datosmat[5]),datosmat[6]);
                mats.add(materia);
            }
            buffer.close();
        } catch (Exception ex) {
            System.out.println("Error CargueDatosMaterias");
        }
        return mats;
    }
    public void CargarDatos(int PROBLEMA)
    {
        FileReader file;
        BufferedReader buffer; 
        
        if(PROBLEMA == 1)
        {
            this.docentes = CargarDatosProfesor(PROBLEMA);
            CargarDatosAulas();
            CargarDatosFranjas(PROBLEMA);
            this.materias = CargarDatosMaterias(PROBLEMA);
        }
        else
        {
            this.docentes = CargarDatosProfesor(PROBLEMA);
            CargarDatosFranjas(PROBLEMA);
            this.materias = CargarDatosMaterias(PROBLEMA);
        }
        
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
                                //j = this.materias.size();
                            }
                        }
                        //i = this.docentes.size();
                    }
                }
            }
            
        }catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        
    }
    public void CargarDatosAulas()
    {
        FileReader file;
        BufferedReader buffer;
        try
        {
            String linea;
            file = new FileReader("salones.txt");
            buffer = new BufferedReader(file);
            while((linea = buffer.readLine())!=null)
            {
                String[] datos = linea.split(";");
                InfoAulas aula = new InfoAulas(Integer.parseInt(datos[0]),datos[1],Integer.parseInt(datos[2]),datos[3]);
                this.aulas.add(aula);
            }
            
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void CargarDatosFranjas(int PROBLEMA)
    {
        FileReader file;
        BufferedReader buffer;
        try
        {
            String linea;
            if(PROBLEMA == 1)
            {
                file = new FileReader("franjas.txt");
            }
            else
            {
                file = new FileReader("franjas2.txt");
            }
            buffer = new BufferedReader(file);
            while((linea = buffer.readLine())!=null)
            {
                String[] datos = linea.split(";");
                FranjaHoraria franja = new FranjaHoraria(Integer.parseInt(datos[0]),Integer.parseInt(datos[1]));
                this.franjas.add(franja);
            }
            
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
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

    public ArrayList<InfoAulas> getAulas() {
        return aulas;
    }

    public void setAulas(ArrayList<InfoAulas> aulas) {
        this.aulas = aulas;
    }

    public ArrayList<FranjaHoraria> getFranjas() {
        return franjas;
    }

    public void setFranjas(ArrayList<FranjaHoraria> franjas) {
        this.franjas = franjas;
    }
    
    
}
