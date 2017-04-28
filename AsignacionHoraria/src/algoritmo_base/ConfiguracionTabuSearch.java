/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_base;

import algoritmo_base.comparadores.ComparatorMaximize;
import algoritmo_base.comparadores.ComparatorMinimize;
import algoritmo_base.criterios_aspiracion.AspiracionPorDefault;
import algoritmo_base.criterios_parada.CriteriosParadaEnum;
import algoritmo_base.criterios_aspiracion.CriteriosAspiracionEnum;
import static algoritmo_base.criterios_aspiracion.CriteriosAspiracionEnum.*;
import algoritmo_base.criterios_aspiracion.StrategyAspiracionPorDireccionBusqueda;
import algoritmo_base.criterios_aspiracion.StrategyAspiracionPorObjetivo;
import static algoritmo_base.criterios_parada.CriteriosParadaEnum.*;
import algoritmo_base.criterios_parada.StrategyParadaEvaluacionObjetivo;
import algoritmo_base.criterios_parada.StrategyParadaNumIteraciones;
import algoritmo_base.criterios_parada.StrategyParadaNumIteracionesSinMejora;
import algoritmo_base.lista_tabu.TabuList;
import algoritmo_base.lista_tabu.TabuListMovimientos;

/**
 * Clase que contiene la configuración para el algoritmo de Búsqueda Tabú
 * @author Camilo
 */
public class ConfiguracionTabuSearch {
    /**
     * Indica el tipo de criterio de aspiracion que será usado al momento de
     * ejecutar el algoritmo de Búsqueda Tabú
     */
    private CriteriosAspiracionEnum tipoAspiracion;
    /**
     * Indica el tipo de criterio de parada que será usado al momento de
     * ejecutar el algoritmo de Búsqueda Tabú
     */
    private CriteriosParadaEnum tipoParada;
    /**
     * Representa el tipo de lista tabú que va a ser usada por el algoritmo
     */
    private TabuList listaTabu;
    /**
     * Indica si el problema es de maximización. Si su valor es false significa
     * que el problema es de minimización
     */
    private boolean maximizacion;
    /**
     * Indica el objetivo a ser usado en el criterio de parada. Su significado
     * varía dependiendo del tipo de criterio de parada. e.g. para algoritmos
     * que tienen en cuenta el número de iteraciones el objetivo es el número
     * máximo de iteraciones, y para los que tienen en cuenta la evaluación el
     * objetivo es el valor de evaluación
     */
    private double objetivo;
            
    /**
     * Crea un objeto ConfiguracionTabuSearch con valores por defecto
     */
    public ConfiguracionTabuSearch(){
        tipoAspiracion = POR_OBJETIVO;
        tipoParada = NUM_ITERACIONES;
        listaTabu = new TabuListMovimientos();
        maximizacion = true;
        objetivo = 0;
    }
    
    /**
     * Permite fijar el criterio de aspiración a ser usado por el algoritmo.
     * Si se fija el criterio POR_DEFAULT entonces únicamente se usará el criterio
     * de aspiración por defecto, de lo contrario se usará el criterio escogido
     * junto con el criterio por defecto
     * @param tipoAspiracion es el tipo de criterios de aspiración que se desea
     * usar
     */
    public void setCriterioAspiracion(CriteriosAspiracionEnum tipoAspiracion){
        this.tipoAspiracion = tipoAspiracion;
    }
    
    /**
     * Permite fijar el criterio de parada del algoritmo. Para los criterios de
     * parada que tienen en cuenta el valor de evaluación, adicionalmente se
     * fija un número máximo de iteraciones de forma interna para evitar que el
     * algooritmo se ejecute por siempre en caso de que no se pueda alcanzar el
     * objetivo
     * @param tipoParada es el criterio de parada a ser usado
     * @param objetivo es el objetivo a ser usado en el criterio de parada. 
     * Su significado varía dependiendo del tipo de criterio de parada. e.g. 
     * para algoritmos que tienen en cuenta el número de iteraciones el objetivo 
     * es el número máximo de iteraciones, y para los que tienen en cuenta la 
     * evaluación el objetivo es el valor de evaluación deseado
     */
    public void setCriterioParada(CriteriosParadaEnum tipoParada, double objetivo){
        this.tipoParada = tipoParada;
        this.objetivo = objetivo;
    }
    
    /**
     * Permite fijar el tipo de problema de la función a evaluar con el algoritmo
     * de Búsqueda Tabú
     * @param maximizacion indica el tipo de problema, true para problemas de
     * maximización y false para problemas de minimización
     */
    public void setTipoProblema(boolean maximizacion){
        this.maximizacion = maximizacion;
    }
    
    /**
     * Permite fijar el tipo de lista tabú que va a ser usada
     * @param listaTabu es la lista tabú que se va a usar en el algoritmo de
     * Búsqueda Tabú
     * @param tabuTenure es el Tabu Tenure que va a ser usado, es decir, el número
     * de iteraciones que un elemento va a permanecer en la lista tabú
     */
    public void setListaTabu(TabuList listaTabu, int tabuTenure){
        this.listaTabu = listaTabu;
        this.listaTabu.setTabuTenure(tabuTenure);
    }
    
    /**
     * Permite realizar la configuración del algoritmo de Búqueda Tabú. Los valores
     * de la configuración deben haber sido establecidos previamente, de lo
     * contrario se tomarán los valores por defecto
     * @param algoritmoBusqueda es el objeto al que se le va a aplicar la
     * configuración
     */
    public void aplicarConfiguración(TabuSearch algoritmoBusqueda){
        this.aplicarCriterioAspiracion(algoritmoBusqueda);
        this.aplicarCriterioParada(algoritmoBusqueda);
        this.aplicarComparador(algoritmoBusqueda);
        algoritmoBusqueda.setAspiracionPorDefecto(new AspiracionPorDefault());
        algoritmoBusqueda.setListaTabu(listaTabu);
    }
    
    /**
     * Permite aplicar la configuración del criterio de aspiración elegido
     * @param algoritmoBusqueda es el objeto al que se le va a aplicar la
     * configuración
     */
    private void aplicarCriterioAspiracion(TabuSearch algoritmoBusqueda){
        switch (this.tipoAspiracion){
            case POR_OBJETIVO:
                algoritmoBusqueda.setEstrategiaAspiracion(new StrategyAspiracionPorObjetivo(maximizacion));
                break;
            case POR_DIRECCION_BUSQUEDA:
                algoritmoBusqueda.setEstrategiaAspiracion(new StrategyAspiracionPorDireccionBusqueda(maximizacion));
                break;
            case POR_DEFAULT:
                algoritmoBusqueda.setEstrategiaAspiracion(null);
                break;
            default:
                algoritmoBusqueda.setEstrategiaAspiracion(new StrategyAspiracionPorObjetivo(maximizacion));
                break;
        }
    }
    /**
     * Permite aplicar la configuracion del criterio de parada elegido
     * @param algoritmoBusqueda es el objeto al que se le va a aplicar la
     * configuración
     */
    private void aplicarCriterioParada(TabuSearch algoritmoBusqueda){
        switch (this.tipoParada){
            case NUM_ITERACIONES:
                algoritmoBusqueda.setEstrategiaParada(new StrategyParadaNumIteraciones((int)objetivo));
                break;
            case NUM_ITERACIONES_SIN_MEJORA:
                algoritmoBusqueda.setEstrategiaParada(new StrategyParadaNumIteracionesSinMejora((int)objetivo, maximizacion));
                break;
            case EVALUACION_OBJETIVO:
                algoritmoBusqueda.setEstrategiaParada(new StrategyParadaEvaluacionObjetivo(objetivo, maximizacion, 100000));
                break;
            default:
                algoritmoBusqueda.setEstrategiaParada(new StrategyParadaNumIteraciones((int)objetivo));
                break;
        }
    }
    
    /**
     * Permite fijar el comparador correcto, dependiendo de si el problema es de
     * maximización o minimización
     * @param algoritmoBusqueda 
     */
    private void aplicarComparador(TabuSearch algoritmoBusqueda){
        if (maximizacion)
            algoritmoBusqueda.setComparador(new ComparatorMaximize());
        else
            algoritmoBusqueda.setComparador(new ComparatorMinimize());
    }
}
