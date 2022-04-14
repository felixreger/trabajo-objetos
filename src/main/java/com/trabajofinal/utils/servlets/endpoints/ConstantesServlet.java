package com.trabajofinal.utils.servlets.endpoints;

public class ConstantesServlet {

    //url endpoints
    public static final String URL_USUARIO = "/usuarios";
    public static final String URL_DIRECTORIO = "/directorio";
    public static final String URL_COMENTARIO = "/comentario";
    public static final String URL_CATEDRA = "/catedra";
    public static final String URL_CARPETA = "/carpeta";
    public static final String URL_ARCHIVO = "/archivo";
    public static final String URL_FUENTE = URL_ARCHIVO + "/fuente";
    public static final String URL_FILTRO_ARCHIVOS = "/filtro";
    public static final String URL_TOP = URL_USUARIO + "/top";

    //status
    public static final int UNPROCESSABLE_ENTITY = 422;

    //criterios
    public static final String AUTOR = "autor";
    public static final String TIPO = "tipo";
    public static final String CONTIENE_NOMBRE = "contienenombre";
    public static final String CONTIENE_PALABRA = "contienepalabra";
    public static final String FECHA_CREACION = "fechacreacion";
    public static final String FECHA_MODIFICACION = "fechamodificacion";

}
