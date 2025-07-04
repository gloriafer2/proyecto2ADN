/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;
import proyectoadn.PatronADN;

/**
 *
 * @author Gloria
 */

    public class MiArbolBinarioBusqueda {

    private Nodo raiz; 
    private int cantidadNodos;

    public MiArbolBinarioBusqueda() {
        this.raiz = null;
        this.cantidadNodos = 0;
    }

    public void insertar(PatronADN nuevoPatron) {
        if (buscarNodo(raiz, nuevoPatron.getSecuencia()) == null) {
             raiz = insertarRecursivo(raiz, nuevoPatron);
             cantidadNodos++;
        }
    }

    private Nodo insertarRecursivo(Nodo actual, PatronADN patron) { 
        if (actual == null) {
            return new Nodo(patron); 
        }

        int comparacion = patron.compareTo(actual.getPatron());

        if (comparacion < 0) {
            actual.izquierda = insertarRecursivo(actual.izquierda, patron);
        } else if (comparacion > 0) {
            actual.derecha = insertarRecursivo(actual.derecha, patron);
        } else {
            return actual;
        }
        return actual;
    }
    
    private Nodo buscarNodo(Nodo nodoActual, String secuencia) {
        if (nodoActual == null) {
            return null;
        }
        if (nodoActual.getPatron().getSecuencia().equals(secuencia)) {
            return nodoActual;
        }
        
        Nodo encontrado = buscarNodo(nodoActual.izquierda, secuencia); 
        if (encontrado != null) return encontrado;
        encontrado = buscarNodo(nodoActual.derecha, secuencia);
        return encontrado;
    }

    public PatronADN[] obtenerPatronesOrdenados() {
        PatronADN[] patronesOrdenados = new PatronADN[cantidadNodos];
        
        int[] indiceActual = {0};

        recorridoInordenInversoToArray(raiz, patronesOrdenados, indiceActual);
        
        return patronesOrdenados;
    }

    private void recorridoInordenInversoToArray(Nodo nodoActual, PatronADN[] array, int[] indice) {
        if (nodoActual != null) {
            recorridoInordenInversoToArray(nodoActual.derecha, array, indice);
            
            if (indice[0] < array.length) {
                array[indice[0]] = nodoActual.getPatron();
                indice[0]++;
            }
            
            recorridoInordenInversoToArray(nodoActual.izquierda, array, indice);
        }
    }
    
    public int getCantidadNodos() {
        return cantidadNodos;
    }

    public PatronADN obtenerPatronMasFrecuente() {
        if (raiz == null) {
            return null;
        }
        Nodo actual = raiz; 
        while (actual.getIzquierda() != null) {
            actual = (Nodo) actual.getIzquierda();
        }
        return actual.getPatron();
    }

    public PatronADN obtenerPatronMenosFrecuente() {
        if (raiz == null) {
            return null;
        }
        Nodo actual = raiz; 
        while (actual.getDerecha() != null) {
            actual = actual.getDerecha();
        }
        return actual.getPatron();
    }
    }
