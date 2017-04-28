/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgoritmoMemetico;

import BusquedaLocal.BusquedaLocalImpl;
import Modelos.Gen;
import Modelos.Individuo;
import OperadorRecombinacion.RTR;
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

    public ArrayList<Individuo> poblacion = new ArrayList<>();
    public ArrayList<Double> iteraciones = new ArrayList<>();
    public int poblacionSize = 25;
    public int NUM_HIJOS;
    public int NUM_REPETICIONES;
    public double ENTROPIA_ANTERIOR;
    public double VALOR_T = 0.01;
    public double PRESERVE = 0.25;

    @Override
    public void ejecutar() {
        
        CargueDatos datos = new CargueDatos();
        datos.CargarDatos();
        
        ArrayList<Individuo> miPoblacion = generarPoblacionInicial(datos);
        this.NUM_HIJOS = 10;
        this.ENTROPIA_ANTERIOR = 0;
        int i = 0;
        while (i < this.NUM_REPETICIONES) {
            Individuo ind = new Individuo();
            miPoblacion = ind.OrdenarIndividuos(miPoblacion, 0, miPoblacion.size()-1);
            ArrayList<Individuo> newPoblacion = generarNuevaPoblacion(miPoblacion);
            miPoblacion = actualizarPoblacion(miPoblacion, newPoblacion);
            if (convergue(miPoblacion, i)) {
                miPoblacion = reiniciarPoblacion(miPoblacion, datos);
            }
            i = i + 1;
        }
        this.poblacion = miPoblacion;
        Individuo ind = new Individuo();
        this.poblacion = ind.OrdenarIndividuos(this.poblacion, 0, this.poblacion.size()-1);
    }

    @Override
    public ArrayList generarPoblacionInicial(CargueDatos datos) {

        for (int i = 0; i < poblacionSize; i++) {

            ArrayList<Gen> genes = new ArrayList<>();

            genes = generateRandomConfiguration(datos);
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
    public ArrayList generarNuevaPoblacion(ArrayList pop) {
        ArrayList<Individuo> buffer = pop;
        ArrayList<Individuo> hijos = new ArrayList();
        int k = 0;
        int j = 0;
        RTR recombinacion = new RTR();
        for (int i = 0; i < this.NUM_HIJOS; i++) {
            while (k == j) {
                k = (int) (Math.random() * buffer.size());
                j = (int) (Math.random() * buffer.size());
            }
            //System.out.println("k: "+k);
            //System.out.println("j: "+j);
            Individuo hijo = (Individuo) recombinacion.OperadorRecombinacion(buffer.get(k), buffer.get(j));
            Individuo hijomutado = Mutar(hijo);
            hijomutado.getEvaluacion();
            hijos.add(hijomutado);
            k = 0;
            j = 0;
        }
        for (int i = 0; i < hijos.size(); i++) {
            buffer.add(hijos.get(i));
        }
        Individuo ind = new Individuo();
        buffer = ind.OrdenarIndividuos(buffer, 0, buffer.size() - 1);
        for (int i = buffer.size() - 1; i > this.poblacionSize; i--) {
            buffer.remove(i);
        }
        return buffer;
    }

    @Override
    public ArrayList reiniciarPoblacion(ArrayList pop,CargueDatos datos) {

        ArrayList<Individuo> newpop = new ArrayList();
        ArrayList<Individuo> population = pop;
        ArrayList<Gen> genes;
        Individuo ind = new Individuo();
        population = ind.OrdenarIndividuos(population, 0, population.size()-1);
        int preserved = (int) (population.size()*this.PRESERVE);
        for (int i = 0; i < preserved; i++) 
        {
            Individuo individuo = population.get(i);
            newpop.add(individuo);
        }
        for (int i = (preserved+1); i < population.size(); i++) 
        {
            genes = generateRandomConfiguration(datos);
            Individuo individuo = new Individuo(genes);
            individuo.getEvaluacion();
            individuo = localSearchEngine(individuo);
            newpop.add(individuo);
        }
        
        return newpop;
    }

    private ArrayList generateRandomConfiguration(CargueDatos datos) {

        ArrayList<Gen> genes = new ArrayList<>();

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
            gen.setValue(i);
            genes.add(gen);

        }
        return genes;
    }

    private Individuo localSearchEngine(Individuo individuo) {

        BusquedaLocalImpl busqueda = new BusquedaLocalImpl();
        busqueda.NUM_ITERACIONES = 3;
        Individuo best = (Individuo) busqueda.LocalSearchEngine(individuo);
        return best;
        /*TabuSearch busqueda = new TabuSearch();
        
        ConfiguracionTabuSearch configuracion = new ConfiguracionTabuSearch();
        configuracion.setTipoProblema(false);
        configuracion.setCriterioParada(CriteriosParadaEnum.NUM_ITERACIONES, 1000);
        configuracion.setCriterioAspiracion(CriteriosAspiracionEnum.POR_OBJETIVO);
        configuracion.setListaTabu(new TabuListMovimientos(), 5);
        
        //Problema del Flow Shop
        
        
        //CIUDADES     
        
        Individuo best = (Individuo) busqueda.tabuSearch(configuracion, individuo);
        return best;*/
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

        for (int i = poblacionActualizada.size() - 1; i > this.poblacionSize; i--) {
            poblacionActualizada.remove(i);
        }
        return poblacionActualizada;
    }

    public Individuo Mutar(Individuo individuo) {
        int genran = (int) (Math.random() * individuo.getGenes().size());
        //System.out.println(datos.getMaterias().get(i).getNombreMateria())
        CargueDatos datos = new CargueDatos();
        datos.CargarDatos();
        int primerHorario = (int) (Math.random() * datos.getFranjas().size());
        int segundoHorario = (int) (Math.random() * datos.getFranjas().size());
        while (datos.getFranjas().get(primerHorario).getDia() == datos.getFranjas().get(segundoHorario).getDia()) {
            segundoHorario = (int) (Math.random() * datos.getFranjas().size());
        }

        if (datos.getFranjas().get(primerHorario).getDia() < datos.getFranjas().get(segundoHorario).getDia()) {
            individuo.getGenes().get(genran).getHorarios().set(0, datos.getFranjas().get(primerHorario));
            individuo.getGenes().get(genran).getHorarios().set(1, datos.getFranjas().get(segundoHorario));
        } else {
            individuo.getGenes().get(genran).getHorarios().set(0, datos.getFranjas().get(segundoHorario));
            individuo.getGenes().get(genran).getHorarios().set(1, datos.getFranjas().get(primerHorario));
        }

        return individuo;
    }

    private boolean convergue(ArrayList<Individuo> miPoblacion, int iteracion) {
        int sumaAdaptacion = 0;
        double entropiaSuma = 0;
        ArrayList<Double> probabilidades = new ArrayList<>();
        ArrayList<Double> entropia = new ArrayList<>();

        for (int i = 0; i < miPoblacion.size(); i++) {
            sumaAdaptacion += miPoblacion.get(i).ObtenerEvaluacion();
        }

        for (int i = 0; i < miPoblacion.size(); i++) {
            probabilidades.add(miPoblacion.get(i).ObtenerEvaluacion() / sumaAdaptacion);
        }

        for (int i = 0; i < miPoblacion.size(); i++) {
            entropia.add(probabilidades.get(i) * Math.log10(probabilidades.get(i)));
            entropiaSuma += entropia.get(i);
        }

        iteraciones.add(iteracion, entropiaSuma);

        if (iteracion == 0) {
            return false;
        } else {
            if (iteraciones.get(iteracion - 1) - iteraciones.get(iteracion) < this.VALOR_T) {
                return true;
            }
        }
        return false;
    }
}
