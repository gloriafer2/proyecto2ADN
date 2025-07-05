/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoadn;

/**
 *
 * @author Gloria
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import logic.MiArbolBinarioBusqueda;

public class Modelo {

    private MiHashTable tablaPatronesADN;
    private MiArbolBinarioBusqueda arbolFrecuenciaPatrones;
    private MapeadorCodones mapeadorCodones;

    private String secuenciaPrincipalADN;

    public Modelo() {
        this.tablaPatronesADN = new MiHashTable(101);
        this.arbolFrecuenciaPatrones = new MiArbolBinarioBusqueda();
        this.mapeadorCodones = new MapeadorCodones();
        this.secuenciaPrincipalADN = "";
    }

    public void cargarSecuenciaADN(File archivo) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea.trim().toUpperCase());
            }
            this.secuenciaPrincipalADN = sb.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }
        procesarSecuenciaADN();
    }

    private void procesarSecuenciaADN() {
        if (secuenciaPrincipalADN.length() < 3) {
            System.out.println("La secuencia de ADN es demasiado corta para extraer patrones.");
            return;
        }

        this.tablaPatronesADN = new MiHashTable(101);
        this.arbolFrecuenciaPatrones = new MiArbolBinarioBusqueda();

        for (int i = 0; i <= secuenciaPrincipalADN.length() - 3; i += 3) {
            String secuenciaFragmento = secuenciaPrincipalADN.substring(i, i + 3);

            if (secuenciaFragmento.matches("[ATCG]+")) {
                PatronADN patronExistente = (PatronADN) tablaPatronesADN.buscar(secuenciaFragmento);

                if (patronExistente != null) {
                    patronExistente.incrementarFrecuencia();
                    patronExistente.agregarUbicacion(i);
                } else {
                    PatronADN nuevoPatron = new PatronADN(secuenciaFragmento);
                    nuevoPatron.incrementarFrecuencia();
                    nuevoPatron.agregarUbicacion(i);
                    tablaPatronesADN.insertar(secuenciaFragmento, nuevoPatron);
                }
            }
        }
        
        transferirPatronesATree();
    }
    
    private void transferirPatronesATree() {
        Object[] todosLosPatrones = tablaPatronesADN.getTodosLosValores();
        
        if (todosLosPatrones != null) {
            for (Object obj : todosLosPatrones) {
                if (obj instanceof PatronADN) {
                    arbolFrecuenciaPatrones.insertar((PatronADN) obj);
                }
            }
        }
    }

    public PatronADN[] getPatronesOrdenadosPorFrecuencia() {
        return arbolFrecuenciaPatrones.obtenerPatronesOrdenados();
    }

    public PatronADN buscarPatron(String secuencia) {
        return (PatronADN) tablaPatronesADN.buscar(secuencia.toUpperCase());
    }

    public PatronADN getPatronMasFrecuente() {
        return arbolFrecuenciaPatrones.obtenerPatronMasFrecuente();
    }

    public PatronADN getPatronMenosFrecuente() {
        return arbolFrecuenciaPatrones.obtenerPatronMenosFrecuente();
    }

    public String generarReporteColisiones() {
        return tablaPatronesADN.getReporteColisiones();
    }

    public String generarReporteAminoacidos() {
        Object[] todosLosPatrones = tablaPatronesADN.getTodosLosValores();
        
        if (todosLosPatrones == null || todosLosPatrones.length == 0) {
            return "No se han procesado patrones de ADN.";
        }
        
        MiHashTable aminoacidosFrecuencia = new MiHashTable(50);

        for (Object obj : todosLosPatrones) {
            if (obj instanceof PatronADN) {
                PatronADN patron = (PatronADN) obj;
                String codonARN = mapeadorCodones.convertirADN_a_ARN(patron.getSecuencia());
                Aminoacido amino = mapeadorCodones.getAminoacido(codonARN);

                if (amino != null) {
                    String claveAmino = amino.getNombreCompleto();
                    
                    Aminoacido acumuladorAmino = (Aminoacido) aminoacidosFrecuencia.buscar(claveAmino);
                    
                    if (acumuladorAmino != null) {
                        acumuladorAmino.incrementarFrecuenciaGlobal(patron.getFrecuencia());
                        acumuladorAmino.agregarCodonAsociado(codonARN);
                    } else {
                        Aminoacido nuevoAcumuladorAmino = new Aminoacido(amino.getNombreCompleto(), amino.getAbreviatura3Letras(), amino.getAbreviatura1Letra());
                        nuevoAcumuladorAmino.incrementarFrecuenciaGlobal(patron.getFrecuencia());
                        nuevoAcumuladorAmino.agregarCodonAsociado(codonARN);
                        aminoacidosFrecuencia.insertar(claveAmino, nuevoAcumuladorAmino);
                    }
                }
            }
        }
        
        StringBuilder reporte = new StringBuilder("Reporte de Aminoácidos:\n");
        reporte.append("----------------------------------------------------------------\n");

        Object[] listaAminoacidosAcumulados = aminoacidosFrecuencia.getTodosLosValores();

        if (listaAminoacidosAcumulados != null) {
            for (Object obj : listaAminoacidosAcumulados) {
                if (obj instanceof Aminoacido) {
                    Aminoacido amino = (Aminoacido) obj;
                    reporte.append("Aminoácido: ").append(amino.getNombreCompleto())
                           .append(" (").append(amino.getAbreviatura3Letras()).append(" / ")
                           .append(amino.getAbreviatura1Letra()).append(")\n");
                    reporte.append("  Frecuencia Total: ").append(amino.getFrecuenciaGlobal()).append("\n");
                    reporte.append("  Codones asociados: ");
                    String[] codones = amino.getTodosLosCodonesAsociados();
                    if (codones != null) {
                        for (int i = 0; i < codones.length; i++) {
                            reporte.append(codones[i]);
                            if (i < codones.length - 1) {
                                reporte.append(", ");
                            }
                        }
                    }
                    reporte.append("\n----------------------------------------------------------------\n");
                }
            }
        } else {
            reporte.append("No se encontraron aminoácidos para reportar.\n");
        }
        
        return reporte.toString();
    }
}