/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoadn;

/**
 *
 * @author Gloria
 */

class HashEntry {
    
    String clave;
    Object valor;
    HashEntry siguiente;

    /**
     * Constructor para crear una nueva `HashEntry`.
     *
     * @param clave La clave ({@code String}) de la entrada.
     * @param valor El valor ({@code Object}) asociado a la clave.
     */
    public HashEntry(String clave, Object valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null; // Se inicializa sin siguiente, se enlaza después si hay colisiones.
    }
}

/**
 * La clase `MiHashTable` implementa una tabla hash personalizada utilizando
 * la técnica de encadenamiento separado para resolver colisiones.
 * Almacena pares clave-valor, donde las claves son {@code String} y los valores son {@code Object}.
 * Es útil para un acceso eficiente (búsqueda, inserción) a los elementos.
 * También lleva un registro de las colisiones ocurridas para fines de reporte.
 *
 */
public class MiHashTable {
    private HashEntry[] tabla;
    private int tamano;
    private int cantidadElementos;
    private int colisionesRegistradas;

    /**
     * Constructor para crear una nueva instancia de `MiHashTable`.
     * Inicializa el arreglo de la tabla hash con la capacidad especificada.
     *
     * @param capacidadInicial La capacidad (tamaño del arreglo) de la tabla hash.
     * Idealmente, este debería ser un número primo para una mejor distribución.
     */
    public MiHashTable(int capacidadInicial) {
        this.tabla = new HashEntry[capacidadInicial];
        this.tamano = capacidadInicial;
        this.cantidadElementos = 0;
        this.colisionesRegistradas = 0;
    }

    /**
     * Calcula el valor hash para una clave dada, que luego se utiliza como
     * índice en el arreglo de la tabla hash.
     * Este método utiliza una función hash multiplicativa simple:
     * `hash = (hash * 31 + c) % tamano`, donde 31 es un número primo comúnmente usado.
     * Asegura que el índice retornado sea no negativo.
     *
     * @param clave La clave ({@code String}) para la cual se calculará el hash.
     * @return Un valor entero que representa el índice en el arreglo de la tabla hash.
     */
    private int hash(String clave) {
        int hash = 0;
        for (char c : clave.toCharArray()) {
            hash = (hash * 31 + c) % tamano;
        }
        return Math.abs(hash);
    }

    /**
     * Inserta un par clave-valor en la tabla hash.
     * Si la clave ya existe en la tabla, su valor asociado se actualiza con el nuevo valor.
     * Si la clave es nueva, se inserta como una nueva entrada.
     * En caso de colisión (dos claves mapean al mismo índice), el nuevo elemento
     * se agrega al principio de la lista enlazada en esa cubeta, y se incrementa
     * el contador de colisiones registradas.
     *
     * @param clave La clave ({@code String}) a insertar o actualizar.
     * @param valor El valor ({@code Object}) asociado a la clave.
     */
    public void insertar(String clave, Object valor) {
        int indice = hash(clave);
        HashEntry nuevoEntry = new HashEntry(clave, valor);

       
        if (tabla[indice] == null) {
            tabla[indice] = nuevoEntry;
            cantidadElementos++;
        } else {
            HashEntry actual = tabla[indice];
            while (actual != null) {
                if (actual.clave.equals(clave)) {
                    actual.valor = valor;
                    return;
                }
                actual = actual.siguiente;
            }

            
            nuevoEntry.siguiente = tabla[indice]; 
            tabla[indice] = nuevoEntry;          
            cantidadElementos++;
            colisionesRegistradas++; 
        }
    }

    /**
     * Busca un valor en la tabla hash dado su clave.
     * Navega por la lista enlazada en la cubeta correspondiente al hash de la clave
     * hasta encontrar la clave deseada.
     *
     * @return El {@code Object} asociado a la clave si se encuentra, o {@code null} si la clave no existe en la tabla.
     */
    public Object buscar(String clave) {
        int indice = hash(clave);
        HashEntry actual = tabla[indice];
       
        while (actual != null) {
            if (actual.clave.equals(clave)) {
                return actual.valor; 
            }
            actual = actual.siguiente; 
        }
        return null;
    }
    
    /**
     * Obtiene un arreglo que contiene todos los valores ({@code Object})
     * almacenados en la tabla hash.
     * Este método recorre todas las cubetas y las listas enlazadas dentro de ellas
     * para recolectar todos los valores.
     *
     * @return Un arreglo de {@code Object} con todos los valores presentes en la tabla.
     * Retorna un arreglo vacío si la tabla no contiene elementos.
     */
    public Object[] getTodosLosValores() {
        int count = 0;
        for (int i = 0; i < tabla.length; i++) {
            HashEntry current = tabla[i];
            while (current != null) {
                count++;
                current = current.siguiente;
            }
        }

        Object[] valores = new Object[count];
        int index = 0;
        for (int i = 0; i < tabla.length; i++) {
            HashEntry current = tabla[i];
            while (current != null) {
                if (index < valores.length) {
                    valores[index++] = current.valor;
                }
                current = current.siguiente;
            }
        }
        return valores;
    }

    /**
     * Genera un reporte detallado sobre el estado de las colisiones en la tabla hash.
     * El reporte incluye:
     * <ul>
     * <li>El número de colisiones por cada celda (cubeta) donde ocurrieron.</li>
     * <li>El tamaño total del arreglo de la tabla.</li>
     * <li>La cantidad de celdas que están ocupadas (contienen al menos un elemento).</li>
     * <li>El número total de colisiones registradas.</li>
     * <li>El número total de elementos almacenados en la tabla.</li>
     * </ul>
     *
     * @return Una cadena de texto formateada con el reporte de colisiones.
     */
    public String getReporteColisiones() {
        StringBuilder reporte = new StringBuilder("Reporte de Colisiones de MiHashTable:\n");
        reporte.append("--------\n");
        int celdasOcupadas = 0;
        int colisionesTotal = 0; 

        for (int i = 0; i < tabla.length; i++) {
            if (tabla[i] != null) {
                celdasOcupadas++;
                int colisionesEnCelda = 0;
                HashEntry actual = tabla[i].siguiente;
                while (actual != null) {
                    colisionesEnCelda++;
                    actual = actual.siguiente;
                }
                if (colisionesEnCelda > 0) {
                    reporte.append("Celda ").append(i).append(": ").append(colisionesEnCelda).append(" colision(es)\n");
                    colisionesTotal += colisionesEnCelda;
                }
            }
        }
        reporte.append("-----\n");
        reporte.append("Tamaño total de la tabla: ").append(tabla.length).append("\n");
        reporte.append("Celdas ocupadas: ").append(celdasOcupadas).append("\n");
        reporte.append("Total de colisiones registradas (elementos en cadenas > 1): ").append(colisionesTotal).append("\n");
        reporte.append("Número total de elementos almacenados: ").append(cantidadElementos).append("\n");
        return reporte.toString();
    }
}