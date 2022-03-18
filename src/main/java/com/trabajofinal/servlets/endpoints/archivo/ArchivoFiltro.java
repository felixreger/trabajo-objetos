package com.trabajofinal.servlets.endpoints.archivo;

import com.trabajofinal.servlets.endpoints.criterio.FabricaCriterio;
import com.trabajofinal.utils.servlets.endpoints.Constantes;
import com.trabajofinal.exceptions.ExcepcionServicio;
import com.trabajofinal.modelo.Archivo;
import com.trabajofinal.modelo.Elemento;
import com.trabajofinal.modelo.criterios.Criterio;
import com.trabajofinal.servicios.Servicios;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="ArchivosConFiltro", value= Constantes.URL_FILTRO_ARCHIVOS)
public class ArchivoFiltro extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> filtros = this.getCriterios(request);

        String path = request.getParameter("pathCarpetaBase");
        FabricaCriterio fabrica = new FabricaCriterio();
        Criterio c = fabrica.getCriterioArchivo(filtros);

        try {
            if(servicio.existeElemento(path)) {
                Elemento directorio = servicio.getDirectorio(path);
                List<Archivo> archivos = directorio.filtrar(c);
                String archivosJson = this.gson.toJson(archivos);
                out.print(archivosJson);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("El directorio no existe!");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al filtrar los archivos");
        }finally {
            out.flush();
        }
    }

    private Map<String, String> getCriterios(HttpServletRequest request){

        String criteriosParam = request.getParameter("criterios");

        String[] listaCriterios = criteriosParam.split("&");
        Map<String, String> criterios = new HashMap<>();

        int i = 0;
        while (i < listaCriterios.length -1){
            criterios.put(listaCriterios[i], listaCriterios[i+1]);
            i = i + 2;
        }
        return criterios;
    }
}
