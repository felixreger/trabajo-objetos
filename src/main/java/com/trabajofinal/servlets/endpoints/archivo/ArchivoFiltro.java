package com.trabajofinal.servlets.endpoints.archivo;

import com.trabajofinal.excepciones.ExcepcionRequest;
import com.trabajofinal.servlets.autentificacion.cors.CorsFilter;
import com.trabajofinal.servlets.endpoints.criterio.FabricaCriterio;
import com.trabajofinal.servlets.endpoints.request.requestcontrol.RequestControl;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.excepciones.ExcepcionServicio;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="ArchivosConFiltro", value= ConstantesServlet.URL_FILTRO_ARCHIVOS)
public class ArchivoFiltro extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!CorsFilter.habilitarCors(request, response)) return;

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        RequestControl requestControl = new RequestControl();

        String criteriosParam = request.getParameter("criterios");
        String path = request.getParameter("pathCarpetaBase");
        requestControl.agregarParametros(Arrays.asList(path,criteriosParam));

        try {
            requestControl.validarRequest();
        } catch (ExcepcionRequest e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Map<String, String> filtros = this.getCriterios(criteriosParam);
        FabricaCriterio fabrica = new FabricaCriterio();
        Criterio c = fabrica.getCriterioArchivo(filtros);

        try {
            if(!servicio.existeElemento(path)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Elemento directorio = servicio.getDirectorio(path);
            List<Archivo> archivos = directorio.filtrar(c);
            String archivosJson = this.gson.toJson(archivos);
            out.print(archivosJson);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        out.flush();
    }

    private Map<String, String> getCriterios(String criteriosParam){

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
