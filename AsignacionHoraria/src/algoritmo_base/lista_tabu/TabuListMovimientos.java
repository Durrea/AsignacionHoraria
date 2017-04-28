/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_base.lista_tabu;

import algoritmo_base.Individual;

/**
 * Lista tabú que funciona guardando los movimientos prohibidos
 * @author Camilo
 */
public class TabuListMovimientos implements TabuList {

    /**
     * Representa a los elementos en la lista tabú. Para este caso se usa una
     * matriz en la cual un 0 indica que el movimiento respectivo no pertenece
     * a la lista tabú, y un número mayor a 0 indica que si pertenece; éste número
     * indica el número de iteraciones faltantes para que el movimiento salga de
     * la lista tabú. e.g. si hay un 5 en [2][3], el intercambio de [2] 
     * a [3] estará prohibido durante 5 iteraciones.
     */
    private int listaTabu[][];
    /**
     * Representa el Tabu Tenure de la lista tabú
     */
    private int tabuTenure;
    
    @Override
    public void actualizar(Individual newSolution, Individual currentSolution) {
        int posX = -1, posY = -1;
        for (int i=0;i<listaTabu.length;i++){
            if (newSolution.getValue(i)!=currentSolution.getValue(i)){
                if (posX==-1) posX = i;
                else posY = i;
            }
            for (int j=0;j<listaTabu.length;j++){
               listaTabu[i][j] = (listaTabu[i][j] == 0) ? 0 : listaTabu[i][j]-1; 
            }
        }
        listaTabu[posX][posY] = tabuTenure;
    }

    @Override
    public boolean isTabu(Individual promisingSolution, Individual currentSolution) {
        int posX = -1, posY = -1;
        for (int i=0;i<listaTabu.length;i++){
            System.out.println("Promising: "+promisingSolution.getValue(i) + "Current: "+currentSolution.getValue(i));
            if (promisingSolution.getValue(i)!=currentSolution.getValue(i)){
                System.out.println("Promising: "+promisingSolution.getValue(i) + "Current: "+currentSolution.getValue(i));
                if (posX==-1) posX = i;
                else posY = i;
            }
        }
        if(posX == -1 || posY == -1)
        {
            return false;
        }
        System.out.println("X: "+posX + "Y: "+posY);
        return listaTabu[posX][posY]!=0;
    }

    @Override
    public void setTabuTenure(int tabuTenure) {
        this.tabuTenure = tabuTenure;
    }

    @Override
    public int tiempoTabu(Individual promisingSolution, Individual currentSolution) {
        int posX = -1, posY = -1;
        for (int i=0;i<listaTabu.length;i++){
            if (promisingSolution.getValue(i)!=currentSolution.getValue(i)){
                if (posX==-1) posX = i;
                else posY = i;
            }
        }
        return listaTabu[posX][posY];
    }

    @Override
    public void createTabuList(int individualSize) {
        listaTabu = new int[individualSize][individualSize];
    }
    
}
