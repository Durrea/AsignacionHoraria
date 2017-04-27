/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgoritmoMemetico;

import Modelos.Gen;
import Servicios.CargueDatos;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class Memetico implements IMemetico{

    @Override
    public void ejecutar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList generarPoblacionInicial() {
       
        ArrayList<Gen> genes = new ArrayList<>();
        
        genes = generateRandomConfiguration();
            
        for (int i = 0; i < genes.size(); i++) {
            
            System.out.println("Materia: " + genes.get(i).getMateria().getNombreMateria());
            System.out.println("Docente: " + genes.get(i).getMateria().getPosDocente());
            System.out.println("Tipo: " + genes.get(i).getMateria().getTipoMateria());
            System.out.println("Franja 1: " +  genes.get(i).getHorarios().get(0).getDia()+":"+
                    genes.get(i).getHorarios().get(0).getFranja()+
                    "\nFranja2: "+genes.get(i).getHorarios().get(1).getDia()+":"+genes.get(i).getHorarios().get(1).getFranja());
            System.out.println(genes.get(i).getAulas().get(0).getNombreAula()+"-"+genes.get(i).getAulas().get(1).getNombreAula());
            
            System.out.println("******************");
        }
        
       
        return genes;
    }

    @Override
    public double funcionAdaptacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList generarNuevaPoblacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList reiniciarPoblacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private ArrayList generateRandomConfiguration() {
        
        ArrayList<Gen> genes = new ArrayList<>();
        
        CargueDatos datos = new CargueDatos();
        datos.CargarDatos();
       
        int primerHorario;
        int segundoHorario;
        int aula;
        
        for (int i = 0; i < datos.getMaterias().size(); i++) {
            Gen gen = new Gen();
            //System.out.println(datos.getMaterias().get(i).getNombreMateria());
            gen.setMateria(datos.getMaterias().get(i));
     
            primerHorario = (int) (Math.random() * datos.getFranjas().size()) ;
            segundoHorario = (int) (Math.random() * datos.getFranjas().size()) ;
            while (datos.getFranjas().get(primerHorario).getDia() == datos.getFranjas().get(segundoHorario).getDia())
            {
                segundoHorario = (int) (Math.random() * datos.getFranjas().size()) ;
            }
            
            if (datos.getFranjas().get(primerHorario).getDia() < datos.getFranjas().get(segundoHorario).getDia())
            {
                gen.getHorarios().add(datos.getFranjas().get(primerHorario));
                gen.getHorarios().add(datos.getFranjas().get(segundoHorario));
            }
            else 
            {                
                gen.getHorarios().add(datos.getFranjas().get(segundoHorario));
                gen.getHorarios().add(datos.getFranjas().get(primerHorario));
            }
            
            aula = (int) (Math.random() * datos.getAulas().size());
            gen.getAulas().add(datos.getAulas().get(aula));
            aula = (int) (Math.random() * datos.getAulas().size());
            gen.getAulas().add(datos.getAulas().get(aula));
            
            genes.add(gen);
        
        }
        return genes;
    }   
}
