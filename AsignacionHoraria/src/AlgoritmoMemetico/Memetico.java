/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgoritmoMemetico;

import BusquedaLocal.BusquedaLocalEscuela;
import BusquedaLocal.BusquedaLocalImpl;
import BusquedaLocal.IBUsquedaLocal;
import Convergencia.ConvergenciaEscuela;
import Convergencia.ConvergenciaUniversidad;
import Convergencia.IConvergencia;
import Modelos.Gen;
import Modelos.GenEscuela;
import Modelos.Individuo;
import Modelos.IndividuoEscuela;
import OperadorMutacion.IMutacion;
import OperadorMutacion.MutacionEscuela;
import OperadorMutacion.MutacionUniversidad;
import OperadorRecombinacion.RTR;
import OperadorRecombinacion.RTREscuela;
import OperadorRecombinacion.Recombinacion;
import Servicios.CargueDatos;
import algoritmo_base.ConfiguracionTabuSearch;
//import algoritmo_base.ConfiguracionTabuSearch;
import algoritmo_base.Individual;
import algoritmo_base.TabuSearch;
import algoritmo_base.criterios_aspiracion.CriteriosAspiracionEnum;
import algoritmo_base.criterios_parada.CriteriosParadaEnum;
import algoritmo_base.lista_tabu.TabuListMovimientos;
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

    public ArrayList<Individual> poblacion = new ArrayList<>();
    public ArrayList<Double> iteraciones = new ArrayList<>();
    public int poblacionSize = 25;
    public int NUM_HIJOS;
    public int NUM_REPETICIONES;
    public double ENTROPIA_ANTERIOR;
    public double VALOR_T = 0.01;
    public double PRESERVE = 0.25;
    public int PROBLEMA; //0 == Universidad // 1 == Escuela
    public int NUM_REPETICIONES_LOCAL;

    @Override
    public void ejecutar() {

        int i = 0;

        CargueDatos datos = new CargueDatos();
        datos.CargarDatos(PROBLEMA);
        IConvergencia convergencia;
        if (this.PROBLEMA == 0) {
            convergencia = new ConvergenciaUniversidad();
        } else {
            convergencia = new ConvergenciaEscuela();
        }
        Individual ind;

        ArrayList<Individual> miPoblacion = generarPoblacionInicial(datos);

        if (this.PROBLEMA == 0) {
            ind = new Individuo();
        } else {
            ind = new IndividuoEscuela();
        }

        while (i < this.NUM_REPETICIONES) {

            //miPoblacion = ind.OrdenarIndividuos(miPoblacion, 0, miPoblacion.size() - 1);
            ArrayList<Individual> newPoblacion = generarNuevaPoblacion(miPoblacion, datos);
            miPoblacion = actualizarPoblacion(miPoblacion, newPoblacion);
            if (convergencia.Converge(miPoblacion, this.iteraciones, i, this.VALOR_T)) {
                miPoblacion = reiniciarPoblacion(miPoblacion, datos);
            }
            i = i + 1;
        }
        this.poblacion = miPoblacion;
        this.poblacion = ind.OrdenarIndividuos(this.poblacion, 0, this.poblacion.size() - 1);
    }

    @Override
    public ArrayList generarPoblacionInicial(CargueDatos datos) {

        for (int i = 0; i < poblacionSize; i++) {

            Individual individuo;
            Individual individuoMejorado;

            if (this.PROBLEMA == 0) {
                ArrayList<Gen> genes;
                individuo = new Individuo();
                genes = individuo.generateRandomConfiguration(datos);
                individuo = new Individuo(genes);
            } else {
                ArrayList<GenEscuela> genes;
                individuo = new IndividuoEscuela();
                genes = individuo.generateRandomConfiguration(datos);
                individuo = new IndividuoEscuela(genes);
            }

            individuo.getEvaluacion();
            individuoMejorado = localSearchEngine(individuo);
            poblacion.add(individuoMejorado);
        }
        return poblacion;


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
    }

    @Override
    public double funcionAdaptacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList generarNuevaPoblacion(ArrayList pop, CargueDatos datos) {
        ArrayList<Individual> buffer = pop;
        ArrayList<Individual> hijos = new ArrayList();
        int k = 0;
        int j = 0;
        Recombinacion recombinacion = null;
        IMutacion mutacion = null;
        Individual ind = null;

        if (this.PROBLEMA == 0) {
            recombinacion = new RTR();
            mutacion = new MutacionUniversidad();
            ind = new Individuo();
        } else {
            recombinacion = new RTREscuela();
            mutacion = new MutacionEscuela();
            ind = new IndividuoEscuela();
        }
        for (int i = 0; i < this.NUM_HIJOS; i++) {

            while (k == j) {
                k = (int) (Math.random() * buffer.size());
                j = (int) (Math.random() * buffer.size());
            }
            Individual hijo = (Individual) recombinacion.OperadorRecombinacion(buffer.get(k), buffer.get(j));;
            Individual hijomutado = (Individual) mutacion.Mutar(hijo, datos);;

            //System.out.println("k: "+k);
            //System.out.println("j: "+j);

            hijomutado.getEvaluacion();
            hijos.add(hijomutado);
            k = 0;
            j = 0;
        }
        for (int i = 0; i < hijos.size(); i++) {
            buffer.add(hijos.get(i));
        }

        buffer = ind.OrdenarIndividuos(buffer, 0, buffer.size() - 1);
        for (int i = buffer.size() - 1; i > this.poblacionSize; i--) {
            buffer.remove(i);
        }
        return buffer;
    }

    @Override
    public ArrayList reiniciarPoblacion(ArrayList pop, CargueDatos datos) {

        Individual ind = null;
        ArrayList<Individual> newpop = new ArrayList();
        ArrayList<Individual> population = pop;
        ArrayList genes = null;

        if (this.PROBLEMA == 0) {
            ind = new Individuo();
            genes = new ArrayList<Gen>();
        } else {
            ind = new IndividuoEscuela();
            genes = new ArrayList<GenEscuela>();
        }

        population = ind.OrdenarIndividuos(population, 0, population.size() - 1);
        int preserved = (int) (population.size() * this.PRESERVE);
        for (int i = 0; i < preserved; i++) {
            Individual individuo = null;
            individuo = (Individual) population.get(i);
            newpop.add(individuo);
        }
        for (int i = (preserved + 1); i < population.size(); i++) {
            genes = ind.generateRandomConfiguration(datos);
            Individual individuo = null;
            if (this.PROBLEMA == 0) {
                individuo = new Individuo(genes);
            } else {
                individuo = new IndividuoEscuela(genes);
            }
            individuo.getEvaluacion();
            individuo = localSearchEngine(individuo);
            newpop.add(individuo);
        }

        return newpop;
    }

    private Individual localSearchEngine(Individual individuo) {

        IBUsquedaLocal busqueda;
        if (this.PROBLEMA == 0) {
            busqueda = new BusquedaLocalImpl();

        } else {
            busqueda = new BusquedaLocalEscuela();
        }

        Individual best = (Individual) busqueda.LocalSearchEngine(individuo, this.NUM_REPETICIONES_LOCAL);
        return best;
        /*TabuSearch busqueda = new TabuSearch();
        
        ConfiguracionTabuSearch configuracion = new ConfiguracionTabuSearch();
        configuracion.setTipoProblema(false);
        configuracion.setCriterioParada(CriteriosParadaEnum.NUM_ITERACIONES, this.NUM_REPETICIONES_LOCAL);
        configuracion.setCriterioAspiracion(CriteriosAspiracionEnum.POR_OBJETIVO);
        configuracion.setListaTabu(new TabuListMovimientos(), 5);
        
        Individual best;
        if(this.PROBLEMA == 0)
        {
            best = (Individuo) busqueda.tabuSearch(configuracion, individuo);
        }
        else
        {
            best = (IndividuoEscuela) busqueda.tabuSearch(configuracion, individuo);
        }        
        return best;*/
    }

    @Override
    public ArrayList actualizarPoblacion(ArrayList pop, ArrayList newPop) {

        ArrayList<Individual> poblacionActualizada = new ArrayList<>();
        Individual individuo;

        if (this.PROBLEMA == 0) {

            for (int i = 0; i < pop.size(); i++) {
                poblacionActualizada.add((Individuo) pop.get(i));
                poblacionActualizada.add((Individuo) newPop.get(i));
            }
            individuo = new Individuo();
        } else {
            for (int i = 0; i < pop.size(); i++) {
                poblacionActualizada.add((IndividuoEscuela) pop.get(i));
                poblacionActualizada.add((IndividuoEscuela) newPop.get(i));
            }
            individuo = new IndividuoEscuela();
        }

        poblacionActualizada = individuo.OrdenarIndividuos(poblacionActualizada, 0, poblacionActualizada.size() - 1);

        for (int i = poblacionActualizada.size() - 1; i > this.poblacionSize; i--) {
            poblacionActualizada.remove(i);
        }
        return poblacionActualizada;
    }
}
