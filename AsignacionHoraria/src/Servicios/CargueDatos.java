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
    
    public CargueDatos()
    {
        
    }
    public ArrayList CargarDatosProfesor()
    {
        ArrayList<InfoDocente> docentes = new ArrayList();
        try {
            String linea;
            file = new FileReader("profesores.txt");
            buffer = new BufferedReader(file);
            while((linea = buffer.readLine())!=null)
            {
                String[] datosprof = linea.split(";");
                InfoDocente docente = new InfoDocente(datosprof[0],datosprof[1]);                
                docentes.add(docente);
            }
            buffer.close();
        } catch (Exception ex) {
            System.out.println("Error");
        }
        return docentes;
    }
    public ArrayList CargarDatosMaterias()
    {
        ArrayList<InfoMaterias> materias = new ArrayList();
        try {
            String linea;
            file = new FileReader("curso_semestre.txt");
            buffer = new BufferedReader(file);
            while((linea = buffer.readLine())!=null)
            {
                String[] datosmat = linea.split(";");
                InfoMaterias materia = new InfoMaterias(datosmat[0],datosmat[1],datosmat[2],datosmat[3],Integer.parseInt(datosmat[4]),Integer.parseInt(datosmat[5]));
                materias.add(materia);
            }
            buffer.close();
        } catch (Exception ex) {
            System.out.println("Error");
        }
        return materias;
    }
    public void CargarDatos()
    {
        ArrayList<InfoDocente> docentes = new ArrayList();
        ArrayList<InfoMaterias> materias = new ArrayList();
        docentes = CargarDatosProfesor();
        materias = CargarDatosMaterias();
        //En este estoy trabajando
    }
}
