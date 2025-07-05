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

    public HashEntry(String clave, Object valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }
}

public class MiHashTable {
    private HashEntry[] tabla;
    private int tamano; 
    private int cantidadElementos; 
    private int colisionesRegistradas; 

    public MiHashTable(int capacidadInicial) {
        this.tabla = new HashEntry[capacidadInicial];
        this.tamano = capacidadInicial;
        this.cantidadElementos = 0;
        this.colisionesRegistradas = 0;
    }

    private int hash(String clave) {
        int hash = 0;
        for (char c : clave.toCharArray()) {
            hash = (hash * 31 + c) % tamano;
        }
        return Math.abs(hash);
    }

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
        reporte.append("Total de colisiones registradas: ").append(colisionesTotal).append("\n");
        reporte.append("Número total de elementos almacenados: ").append(cantidadElementos).append("\n");
        return reporte.toString();
    }
}