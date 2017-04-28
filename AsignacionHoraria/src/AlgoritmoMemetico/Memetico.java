/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgoritmoMemetico;

import BusquedaLocal.BusquedaLocalImpl;
import Modelos.Gen;
import Modelos.Individuo;
import Servicios.CargueDatos;
//import algoritmo_base.ConfiguracionTabuSearch;
import algoritmo_base.Individual;
//import algoritmo_base.TabuSearch;
//import algoritmo_base.criterios_aspiracion.CriteriosAspiracionEnum;
//import algoritmo_base.criterios_parada.CriteriosParadaEnum;
//import algoritmo_base.lista_tabu.TabuListMovimientos;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class Memetico implements IMemetico {

    public ArrayList<Individuo> poblacion = new ArrayList<>();
    int poblacionSize = 25;

    @Override
    public void ejecutar() {
        ArrayList<Individuo> miPoblacion = generarPoblacionInicial();
        
    }

    @Override
    public ArrayList generarPoblacionInicial() {

        for (int i = 0; i < poblacionSize; i++) {

            ArrayList<Gen> genes = new ArrayList<>();

            genes = generateRandomConfiguration();
            Individuo individuo = new Individuo(genes);
            individuo.getEvaluacion();
            Individuo individuoMejorado = localSearchEngine(individuo);


            /*for (int i = 0; i < genes.size(); i++) {

             System.out.println("Materia: " + genes.get(i).getMateria().getNombreMateria());
             System.out.println("Docente: " + genes.get(i).getMateria().getPosDocente());
             System.out.println("Tipo: " + genes.get(i).getMateria().getTipoMateria());
             System.out.println("Franja 1: " + genes.get(i).getHorarios().get(0).getDia() + ":"
             + genes.get(i).getHorarios().get(0).getFranja()
             + "\nFranja2: " + genes.get(i).getHorarios().get(1).getDia() + ":" + genes.get(i).getHorarios().get(1).getFranja());
             System.out.println(genes.get(i).getAulas().get(0).getNombreAula() + "-" + genes.get(i).getAulas().get(1).getNombreAula());

             System.out.println("******************");
             }*/
            poblacion.add(individuoMejorado);
        }
        return poblacion;
    }

    @Override
    public double funcionAdaptacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList generarNuevaPoblacion( ArrayList pop) {
        int Nop = 5;
        ArrayList<Individuo> buffer = pop;
        for (int i = 0; i < Nop; i++) {
            
        }
        return null;
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

            primerHorario = (int) (Math.random() * datos.getFranjas().size());
            segundoHorario = (int) (Math.random() * datos.getFranjas().size());
            while (datos.getFranjas().get(primerHorario).getDia() == datos.getFranjas().get(segundoHorario).getDia()) {
                segundoHorario = (int) (Math.random() * datos.getFranjas().size());
            }

            if (datos.getFranjas().get(primerHorario).getDia() < datos.getFranjas().get(segundoHorario).getDia()) {
                gen.getHorarios().add(datos.getFranjas().get(primerHorario));
                gen.getHorarios().add(datos.getFranjas().get(segundoHorario));
            } else {
                gen.getHorarios().add(datos.getFranjas().get(segundoHorario));
                gen.getHorarios().add(datos.getFranjas().get(primerHorario));
            }

            aula = (int) (Math.random() * datos.getAulas().size());
            gen.getAulas().add(datos.getAulas().get(aula));
            aula = (int) (Math.random() * datos.getAulas().size());
            gen.getAulas().add(datos.getAulas().get(aula));
            gen.setValue(i + 1);
            genes.add(gen);

        }
        return genes;
    }

    private Individuo localSearchEngine(Individuo individuo) {

        BusquedaLocalImpl busqueda = new BusquedaLocalImpl();
        busqueda.NUM_ITERACIONES = 20;
        Individuo best = (Individuo) busqueda.LocalSearchEngine(individuo);
        return best;
    }

    @Override
    public ArrayList actualizarPoblacion(ArrayList pop, ArrayList newPop) {

        ArrayList<Individuo> poblacionActualizada = new ArrayList<>();
        for (int i = 0; i < pop.size(); i++) {
            poblacionActualizada.add((Individuo) pop.get(i));
            poblacionActualizada.add((Individuo) newPop.get(i));
        }

        Individuo individuo = new Individuo();
        poblacionActualizada = individuo.OrdenarIndividuos(poblacionActualizada, 0, poblacionActualizada.size() - 1);

        for (int i = poblacionActualizada.size() - 1; i == pop.size(); i--) {
            poblacionActualizada.remove(i);
        }
        return poblacionActualizada;
    }
}
