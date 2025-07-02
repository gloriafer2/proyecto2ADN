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
    PatronADN patron;       // El patrón de ADN almacenado en esta entrada
    HashEntry siguiente;    // Referencia a la siguiente entrada en caso de colisión

    /**
     * Constructor de HashEntry.
     * @param patron El objeto PatronADN a almacenar en esta entrada.
     */
    public HashEntry(PatronADN patron) {
        this.patron = patron;
        this.siguiente = null; // Al principio, no hay siguiente
    }
}
