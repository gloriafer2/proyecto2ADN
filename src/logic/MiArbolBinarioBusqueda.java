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

    /**
     * Representa el nodo raíz del árbol binario de búsqueda.
     * Es el punto de entrada para todas las operaciones del árbol.
     */
    private Nodo raiz;

    /**
     * Mantiene un conteo del número total de nodos (patrones únicos)
     * actualmente almacenados en el árbol.
     */
    private int cantidadNodos;

    /**
     * Clase interna privada que representa un nodo individual dentro del árbol binario de búsqueda.
     * Cada nodo almacena un objeto {@link PatronADN} y referencias a sus hijos izquierdo y derecho.
     */
    private class Nodo {
        PatronADN patron;
        Nodo izquierda;
        Nodo derecha;

        /**
         * Constructor para un nuevo nodo.
         *
         * @param patron El objeto {@link PatronADN} que se almacenará en este nodo.
         */
        public Nodo(PatronADN patron) {
            this.patron = patron;
            this.izquierda = null;
            this.derecha = null;
        }

        /**
         * Retorna el objeto {@link PatronADN} almacenado en este nodo.
         * @return El {@link PatronADN} del nodo.
         */
        public PatronADN getPatron() {
            return patron;
        }

        /**
         * Retorna el nodo hijo izquierdo.
         * @return El nodo hijo izquierdo, o {@code null} si no tiene.
         */
        public Nodo getIzquierda() {
            return izquierda;
        }

        /**
         * Retorna el nodo hijo derecho.
         * @return El nodo hijo derecho, o {@code null} si no tiene.
         */
        public Nodo getDerecha() {
            return derecha;
        }
    }

    /**
     * Constructor de la clase MiArbolBinarioBusqueda.
     * Inicializa un árbol binario de búsqueda vacío, estableciendo la raíz a {@code null}
     * y la cantidad de nodos a cero.
     */
    public MiArbolBinarioBusqueda() {
        this.raiz = null;
        this.cantidadNodos = 0;
    }

    /**
     * Inserta un nuevo objeto {@link PatronADN} en el árbol.
     * Si un patrón con la misma secuencia ya existe en el árbol, no se inserta un duplicado.
     * La inserción se basa en la comparación de los objetos {@link PatronADN}
     * utilizando su método {@code compareTo}, que debería ordenar por frecuencia.
     *
     * @param nuevoPatron El {@link PatronADN} a insertar en el árbol.
     */
    public void insertar(PatronADN nuevoPatron) {
        // Solo inserta si el patrón no existe previamente en el árbol por su secuencia.
        // Esto es crucial para la lógica de Modelo.transferirPatronesATree()
        // que vuelve a insertar todos los patrones cada vez.
        if (buscarNodoPorSecuencia(raiz, nuevoPatron.getSecuencia()) == null) {
            raiz = insertarRecursivo(raiz, nuevoPatron);
            cantidadNodos++;
        }
    }

    /**
     * Método auxiliar recursivo para insertar un {@link PatronADN} en el árbol.
     * Navega por el árbol hasta encontrar la posición correcta para el nuevo patrón
     * basándose en su frecuencia relativa a los patrones existentes.
     *
     * @param actual El nodo actual en la recursión.
     * @param patron El {@link PatronADN} a insertar.
     * @return El nodo actual después de la inserción.
     */
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
            
        }
        return actual;
    }

    /**
     * Busca un nodo en el árbol que contenga un {@link PatronADN} con una secuencia específica.
     * Este método realiza una búsqueda recursiva que no se basa en el orden de frecuencia
     * del árbol, sino en una comparación directa de la secuencia de ADN.
     *
     * @param nodoActual El nodo desde el cual iniciar la búsqueda (típicamente la raíz).
     * @param secuencia La secuencia de ADN (String) del patrón a buscar.
     * @return El {@link Nodo} que contiene el patrón con la secuencia deseada, o {@code null} si no se encuentra.
     */
    private Nodo buscarNodoPorSecuencia(Nodo nodoActual, String secuencia) {
        if (nodoActual == null) {
            return null;
        }
        if (nodoActual.getPatron().getSecuencia().equals(secuencia)) {
            return nodoActual;
        }

        Nodo encontrado = buscarNodoPorSecuencia(nodoActual.izquierda, secuencia);
        if (encontrado != null) return encontrado;
        encontrado = buscarNodoPorSecuencia(nodoActual.derecha, secuencia);
        return encontrado;
    }


    /**
     * Obtiene todos los objetos {@link PatronADN} almacenados en el árbol,
     * ordenados de mayor a menor frecuencia.
     * Esto se logra mediante un recorrido "inorden inverso" (derecha-raíz-izquierda)
     * del árbol, que aprovecha la ordenación del árbol por frecuencia.
     *
     * @return Un arreglo de {@link PatronADN} ordenados de mayor a menor frecuencia.
     */
    public PatronADN[] obtenerPatronesOrdenados() {
     
        PatronADN[] patronesOrdenados = new PatronADN[cantidadNodos];
        int[] indiceActual = {0};

        recorridoInordenInversoToArray(raiz, patronesOrdenados, indiceActual);

        return patronesOrdenados;
    }

    /**
     * Método auxiliar recursivo para realizar un recorrido inorden inverso (derecha-raíz-izquierda)
     * del árbol y almacenar los patrones en el arreglo proporcionado.
     * Este recorrido garantiza que los patrones se recuperen en orden descendente de frecuencia.
     *
     * @param nodoActual El nodo actual en el recorrido.
     * @param array El arreglo donde se almacenarán los patrones.
     * @param indice Un arreglo de un solo elemento que actúa como contador para la posición actual en el array.
     */
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

    /**
     * Retorna la cantidad total de patrones únicos (nodos) almacenados en el árbol.
     *
     * @return El número de nodos en el árbol.
     */
    public int getCantidadNodos() {
        return cantidadNodos;
    }

    /**
     * Obtiene el patrón de ADN con la mayor frecuencia.
     * En un árbol binario de búsqueda donde los elementos "mayores" (más frecuentes)
     * están a la derecha, el patrón más frecuente será el elemento más a la derecha del árbol.
     *
     * @return El objeto {@link PatronADN} con la mayor frecuencia, o {@code null} si el árbol está vacío.
     */
    public PatronADN obtenerPatronMasFrecuente() {
        if (raiz == null) {
            return null;
        }
        Nodo actual = raiz;
        // Navega al nodo más a la derecha (el de mayor valor/frecuencia)
        while (actual.getDerecha() != null) {
            actual = actual.getDerecha();
        }
        return actual.getPatron();
    }

    /**
     * Obtiene el patrón de ADN con la menor frecuencia.
     * En un árbol binario de búsqueda donde los elementos "menores" (menos frecuentes)
     * están a la izquierda, el patrón menos frecuente será el elemento más a la izquierda del árbol.
     *
     * @return El objeto {@link PatronADN} con la menor frecuencia, o {@code null} si el árbol está vacío.
     */
    public PatronADN obtenerPatronMenosFrecuente() {
        if (raiz == null) {
            return null;
        }
        Nodo actual = raiz;
        while (actual.getIzquierda() != null) {
            actual = actual.getIzquierda();
        }
        return actual.getPatron();
    }
}