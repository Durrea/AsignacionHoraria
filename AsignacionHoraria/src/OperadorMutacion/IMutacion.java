/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperadorMutacion;

import Servicios.CargueDatos;
import algoritmo_base.Individual;

/**
 *
 * @author ingesis
 */
public interface IMutacion {
    
    public Individual Mutar(Individual individuo, CargueDatos datos);
    
}
