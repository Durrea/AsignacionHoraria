/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Eduardo
 */
public class Memetico {
    public ArrayList<Individuo> individuos;
    public int CAMINOS;
    public int ENTRADAS;
    public int SUPERIOR;
    public int INFERIOR;
    public ArrayList<String> caminos;
    public ArrayList<Integer> caminos_cubiertos;
    public Document doc;
    public int ITE_BUSQUEDA_LOCAL;
    public int NUM_HIJOS;
    public double PRESERVE = 0.5;
    public int POBLACION;
    public int CRITERIO_MEMETICO = 10;
    public double VALOR_T = 0.0001;
    
    
    public Memetico()
    {
        this.individuos = new ArrayList();                                
        this.individuos = new ArrayList();
        this.caminos_cubiertos = new ArrayList();
        this.caminos = cargarCaminos();
        this.CAMINOS = this.caminos.size();
        this.doc = cargarXML();
        this.ENTRADAS = NumeroEntradas();                
    }
    
    public ArrayList<Individuo> run()
    {
        int i = 0;
        ArrayList<Double> iteraciones = new ArrayList();
        ArrayList<Individuo> pop = GenerarPoblacionInicial(POBLACION);
        while(i<CRITERIO_MEMETICO)
        {
            ArrayList<Individuo> newpop = GenerarNuevaPoblacion(pop);
            pop = ActualizarPoblacion(pop, newpop);
            if(Converge(pop, iteraciones, i, VALOR_T))
            {
                System.out.println("Converge");
                pop = reiniciarPoblacion(pop);
            }
            i = i + 1;
        }
        OrdenarIndividuos(pop, 0, (pop.size()-1));
        return pop;
    }
    
    public ArrayList<Individuo> GenerarPoblacionInicial(int num) {
        Document doc_copy = (Document) this.doc.cloneNode(true);
        for (int i = 0; i < num; i++) {
            Individuo ind = new Individuo(this.CAMINOS);
            ind.caminos_posibles = this.caminos;
            ind.caminos_cubiertos = this.caminos_cubiertos;
            ind.CalcularEntradas(doc_copy, this.ENTRADAS, SUPERIOR, INFERIOR);
            ind.EvaluarIndividuo(doc_copy);
            ind = BusquedaLocal(ind);            
            this.individuos.add(ind);
            doc_copy = (Document) this.doc.cloneNode(true);
        }
        return this.individuos;
    }
    
    public Individuo BusquedaLocal(Individuo individuo)
    {        
        for (int i = 0; i < this.ITE_BUSQUEDA_LOCAL; i++) 
        {
            Document doc_copy = (Document) doc.cloneNode(true);
            Individuo nuevo = individuo.GenerarVecindario(individuo, CAMINOS, SUPERIOR, doc_copy);
            if(nuevo.getEvaluacion()>individuo.getEvaluacion())
            {
                individuo = nuevo;
            }
        }
        return individuo;                
    }
    public ArrayList<Individuo> ActualizarPoblacion(ArrayList<Individuo> pop, ArrayList<Individuo> nueva)
    {
        ArrayList<Individuo> nuevapoblacion = new ArrayList<Individuo>();
        for(int i=0;i<pop.size();i++)
        {
            nuevapoblacion.add(pop.get(i));
        }
        for(int i=0;i<nueva.size();i++)
        {
            nuevapoblacion.add(nueva.get(i));
        }
        nuevapoblacion = OrdenarIndividuos(nuevapoblacion,0,nuevapoblacion.size()-1);
        for(int i=0;i<nueva.size();i++)
        {
            nuevapoblacion.remove(0);
        }
        return nuevapoblacion;
    }
    
    public ArrayList<Individuo> GenerarNuevaPoblacion(ArrayList<Individuo> pop)
    {
        ArrayList<Individuo> buffer = (ArrayList<Individuo>) pop.clone();
        ArrayList<Individuo> hijos = new ArrayList();
        Document doc_copy = (Document) this.doc.cloneNode(true);              
        for(int i=0;i<this.NUM_HIJOS;i++)
        {
            int limite = buffer.size()-1;
            int k = (int) (Math.random()*limite);
            int j = (int) (Math.random()*limite);            
            Individuo ind_recombinado = Recombinar(buffer.get(k).clone(),buffer.get(j).clone());
            Mutar(ind_recombinado);
            ind_recombinado.EvaluarIndividuo(doc_copy);
            hijos.add(ind_recombinado);           
        }
        
        for(int i=0;i<hijos.size();i++)
        {
            buffer.add(hijos.get(i));            
        }
        buffer = OrdenarIndividuos(buffer, 0, buffer.size()-1);        
        int i = 0;
        while(i<hijos.size())
        {
            buffer.remove(0);
            i++;
        }        
        return buffer;       
    }
    
    public ArrayList<Individuo> reiniciarPoblacion(ArrayList<Individuo> pop) {
        
        ArrayList<Individuo> newpop = new ArrayList();
        ArrayList<Individuo> population = (ArrayList<Individuo>) pop.clone();        
        
        population = OrdenarIndividuos(population, 0, population.size() - 1);
        int preserved = (int) (population.size() * this.PRESERVE);
        for (int i = 0; i < preserved; i++) {
            Individuo individuo = population.get(i);
            newpop.add(individuo);
        }
        for (int i = preserved ; i < population.size(); i++) {
            Document doc_copy = (Document) this.doc.cloneNode(true);
            Individuo ind = new Individuo(this.CAMINOS);
            ind.caminos_posibles = this.caminos;
            ind.caminos_cubiertos = this.caminos_cubiertos;
            ind.CalcularEntradas(doc_copy, this.ENTRADAS, SUPERIOR, INFERIOR);
            ind.EvaluarIndividuo(doc_copy);                                                
            ind = BusquedaLocal(ind);
            newpop.add(ind);
        }

        return newpop;
    }
    
    public Individuo Recombinar(Individuo padre, Individuo madre) {
        //Recombinacion basada en un punto
        Individuo hijo = new Individuo(padre.getCAMINOS());
        Individuo hijo2 = new Individuo(madre.getCAMINOS());
        for (int i = 0; i < padre.getEntradas_individuo().size(); i++) {
            if (i < padre.getCAMINOS() / 2) {
                Entradas entrada_1 = new Entradas(this.ENTRADAS,this.SUPERIOR,this.INFERIOR);
                entrada_1.setValores((ArrayList<Integer>) padre.getEntradas_individuo().get(i).getValores().clone());
                entrada_1.setEntradas((ArrayList<String>) padre.getEntradas_individuo().get(i).getEntradas().clone());
                entrada_1.setCamino_cubierto(padre.getEntradas_individuo().get(i).getCamino_cubierto());
                Entradas entrada_2 = new Entradas(this.ENTRADAS,this.SUPERIOR,this.INFERIOR);
                entrada_2.setValores((ArrayList<Integer>) madre.getEntradas_individuo().get(i).getValores().clone());
                entrada_2.setEntradas((ArrayList<String>) madre.getEntradas_individuo().get(i).getEntradas().clone());
                entrada_2.setCamino_cubierto(madre.getEntradas_individuo().get(i).getCamino_cubierto());
                hijo.getEntradas_individuo().add(entrada_1);                
                hijo2.getEntradas_individuo().add(entrada_2);
            } else {
                Entradas entrada_1 = new Entradas(this.ENTRADAS,this.SUPERIOR,this.INFERIOR);
                entrada_1.setValores((ArrayList<Integer>) madre.getEntradas_individuo().get(i).getValores().clone());
                entrada_1.setEntradas((ArrayList<String>) madre.getEntradas_individuo().get(i).getEntradas().clone());
                entrada_1.setCamino_cubierto(madre.getEntradas_individuo().get(i).getCamino_cubierto());
                Entradas entrada_2 = new Entradas(this.ENTRADAS,this.SUPERIOR,this.INFERIOR);
                entrada_2.setValores((ArrayList<Integer>) padre.getEntradas_individuo().get(i).getValores().clone());
                entrada_2.setEntradas((ArrayList<String>) padre.getEntradas_individuo().get(i).getEntradas().clone());
                entrada_2.setCamino_cubierto(padre.getEntradas_individuo().get(i).getCamino_cubierto());
                hijo.getEntradas_individuo().add(entrada_1);                
                hijo2.getEntradas_individuo().add(entrada_2);
            }
        }
        hijo.caminos_posibles = padre.caminos_posibles;
        hijo.caminos_cubiertos = padre.caminos_cubiertos;
        hijo2.caminos_posibles = madre.caminos_posibles;
        hijo2.caminos_cubiertos = madre.caminos_cubiertos;
        Document doc_copy_1 = (Document) this.doc.cloneNode(true);
        Document doc_copy_2 = (Document) this.doc.cloneNode(true);
        //Mutar(hijo);
        //Mutar(hijo2);
        hijo.EvaluarIndividuo(doc_copy_1);
        hijo2.EvaluarIndividuo(doc_copy_2);
        //individuos.add(hijo);
        //individuos.add(hijo2);
        if(hijo.getEvaluacion() > hijo2.getEvaluacion())
        {
            return hijo;
        }
        else
        {
            return hijo2;
        }
    }
    public void Mutar(Individuo ind) {
        int entradaMutada = (int) (Math.random() * (ind.getEntradas_individuo().size()-1));
        //System.out.println(ind.getEntradas_individuo().size()+ "--" + entradaMutada);
        int valor = 0;
        for (int i = 0; i < ind.getEntradas_individuo().get(entradaMutada).getValores().size(); i++) {
                    //System.out.println("Por aquí pase");
            if (SUPERIOR < 0) {
                        //System.out.println("Por aquí tambien");
                valor = INFERIOR + (int) (Math.random() * (INFERIOR - SUPERIOR));
                ind.getEntradas_individuo().get(entradaMutada).getValores().set(i, valor);
            } else {
                        //System.out.println("y por aquí");
                valor = (int) (Math.random() * SUPERIOR + INFERIOR);
                ind.getEntradas_individuo().get(entradaMutada).getValores().set(i, valor);
            }
        }
        
    }
    public boolean Converge(ArrayList<Individuo> miPoblacion, ArrayList<Double> iteraciones, int numero, double VALOR_T) 
    {
        //System.out.println("Conver..");        
        double sumaAdaptacion = 0;
        double entropiaSuma = 0;
        ArrayList<Double> probabilidades = new ArrayList<>();
        ArrayList<Double> entropia = new ArrayList<>();

        for (int i = 0; i < miPoblacion.size(); i++) {
            Individuo ind = miPoblacion.get(i);            
            sumaAdaptacion += ind.getEvaluacion();
        }        
        for (int i = 0; i < miPoblacion.size(); i++) {
            Individuo ind = miPoblacion.get(i);
            probabilidades.add(ind.getEvaluacion() / sumaAdaptacion);           
        }

        for (int i = 0; i < miPoblacion.size(); i++) {
            entropia.add(probabilidades.get(i) * Math.log10(probabilidades.get(i)));
            entropiaSuma += entropia.get(i);            
        }
        iteraciones.add(numero, entropiaSuma);

        if (numero == 0) {
            return false;
        } else {
            //double v = iteraciones.get(numero - 1) - iteraciones.get(numero);
            //System.out.println("Compara con VALOR_T: "+v);                    
            if (iteraciones.get(numero - 1) - iteraciones.get(numero) < VALOR_T) {
                //System.out.println("Si conver.");
                return true;
            }
        }
        return false;
    } 
    public ArrayList OrdenarIndividuos(ArrayList<Individuo> individuos, int izq, int der) {
        //individuos = (ArrayList<Individuo>) individuos;
        Individuo pivote = (Individuo) individuos.get(izq); // tomamos primer elemento como pivote
        int i = izq; // i realiza la búsqueda de izquierda a derecha
        int j = der; // j realiza la búsqueda de derecha a izquierda
        Individuo ind;
        int aux;

        while (i < j) {            // mientras no se crucen las búsquedas
            while (individuos.get(i).getEvaluacion() <= pivote.getEvaluacion() && i < j) {
                i++; // busca elemento mayor que pivote
            }
            while (individuos.get(j).getEvaluacion() > pivote.getEvaluacion()) {
                j--;         // busca elemento menor que pivote
            }
            if (i < j) {
                ind = (Individuo) individuos.get(i); // si no se han cruzado                      
                //aux= A[i];                  // los intercambia
                individuos.set(i, individuos.get(j));
                //A[i]=A[j];
                individuos.set(j, ind);
                //A[j]=aux;
            }
        }
        individuos.set(izq, individuos.get(j));
        //A[izq]=A[j]; // se coloca el pivote en su lugar de forma que tendremos
        individuos.set(j, pivote);
        //A[j]=pivote; // los menores a su izquierda y los mayores a su derecha
        if (izq < j - 1) {
            OrdenarIndividuos(individuos, izq, j - 1); // ordenamos subarray izquierdo
        }
        if (j + 1 < der) {
            OrdenarIndividuos(individuos, j + 1, der); // ordenamos subarray derecho
        }
        return individuos;
    }
    public Document cargarXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document documento = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            documento = (Document) builder.parse(new File("prueba.xml"));
        } catch (Exception spe) {
            System.out.println(spe.getMessage());
        }
        return documento;
    }

    public ArrayList<String> cargarCaminos() {
        ArrayList<String> caminos = new ArrayList();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("caminos.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                caminos.add(linea);
                this.caminos_cubiertos.add(0);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return caminos;
    }
    public int NumeroEntradas() {
        NodeList lectura = this.doc.getElementsByTagName("Leer");
        return lectura.getLength();
    }
}
