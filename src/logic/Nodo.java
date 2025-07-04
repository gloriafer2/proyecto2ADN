/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;
import proyectoadn.PatronADN;
        
public class Nodo { 

    PatronADN patron;   
    Nodo izquierda;     
    Nodo derecha;       

    /**
     * Constructor de Nodo.
     * @param patron El PatronADN a almacenar en este nodo.
     */
    public Nodo(PatronADN patron) { 
        this.patron = patron;
        this.izquierda = null;
        this.derecha = null;
    }

    /**
     * Obtiene el patrón de ADN almacenado en este nodo.
     * @return El PatronADN del nodo.
     */
    public PatronADN getPatron() {
        return patron;
    }

    /**
     * Obtiene el hijo izquierdo de este nodo.
     * @return El Nodo hijo izquierdo.
     */
    public Nodo getIzquierda() {
        return izquierda;
    }

    /**
     * Obtiene el hijo derecho de este nodo.
     * @return El Nodo hijo derecho.
     */
    public Nodo getDerecha() {
        return derecha;
    }
    
}