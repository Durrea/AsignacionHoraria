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
 * @author ingesis
 */
public class MemeticoEscuela implements IMemetico{

    @Override
    public void ejecutar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList generarPoblacionInicial(CargueDatos datos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList generarNuevaPoblacion(ArrayList pop, CargueDatos datos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList actualizarPoblacion(ArrayList pop, ArrayList newPop) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList reiniciarPoblacion(ArrayList pop, CargueDatos datos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double funcionAdaptacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     private ArrayList generateRandomConfiguration(CargueDatos datos) {

        ArrayList<Gen> genes = new ArrayList<>();

        int primerHorario;
        int segundoHorario;
        int tercerHorario;
        int cuartoHorario;

        for (int i = 0; i < datos.getMaterias().size(); i++) {
            Gen gen = new Gen();
            //System.out.println(datos.getMaterias().get(i).getNombreMateria());
            gen.setMateria(datos.getMaterias().get(i));

            primerHorario = (int) (Math.random() * datos.getFranjas().size());
            segundoHorario = (int) (Math.random() * datos.getFranjas().size());
            tercerHorario = (int) (Math.random() * datos.getFranjas().size());
            cuartoHorario = (int) (Math.random() * datos.getFranjas().size());
            gen.getHorarios().add(datos.getFranjas().get(primerHorario));
            gen.getHorarios().add(datos.getFranjas().get(segundoHorario));
            gen.getHorarios().add(datos.getFranjas().get(tercerHorario));
            gen.getHorarios().add(datos.getFranjas().get(cuartoHorario));         
            gen.setValue(i);
            genes.add(gen);

        }
        return genes;
    }
}
