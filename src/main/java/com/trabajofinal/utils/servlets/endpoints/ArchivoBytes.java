package com.trabajofinal.utils.servlets.endpoints;

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
