package com.trabajofinal.utils.servlets.endpoints;

/**
 * Clase utilizada para obtener el archivo fuente desde la base de datos, dado que
 * cuando se solicita el campo de el archivo, se necesita almacenar un arreglo de
 * bytes. La extension se utiliza para que sea especificada en la respuesta de la
 * consulta.
 */
public class ArchivoBytes {
    private byte[] archivoFuente;
    private String extension;

    public ArchivoBytes() {
    }

    public byte[] getArchivoFuente() {
        return archivoFuente;
    }

    public void setArchivoFuente(byte[] archivoFuente) {
        this.archivoFuente = archivoFuente;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public boolean esValido() {
        return archivoFuente != null;
    }
}
