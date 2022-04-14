package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.excepciones.ExcepcionRequest;
import com.trabajofinal.modelo.Catedra;
import com.trabajofinal.servlets.endpoints.request.body.JsonBody;
import com.trabajofinal.servlets.endpoints.request.body.JsonBodyBuffer;
import com.trabajofinal.servlets.endpoints.request.requestcontrol.RequestControl;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.servicios.Servicios;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebServlet(name="Catedra", value= ConstantesServlet.URL_CATEDRA)
public class CatedraServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

    /**
     * Se retorna en formato json una catedra especifica o todas las existentes segun se lo indique
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idCatedra = request.getParameter("idCatedra");
        List<Catedra> catedras = new ArrayList<>();

        try {
            if (idCatedra == null)
                catedras.addAll(servicio.getCatedras());
            else
                catedras.add(servicio.getCatedra(idCatedra));

            String listaUsuariosJson = this.gson.toJson(catedras);
            out.print(listaUsuariosJson);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        out.flush();

    }

    /**
     * Se realiza un control de la especificacion de la request.
     * Se obtienen todos los parametros necesarios para crear una instancia de Catedra
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        RequestControl requestControl = new RequestControl();

        JsonBody body = new JsonBodyBuffer(request);
        String idCatedra = body.getString("idCatedra");
        String url = body.getString("url");
        requestControl.addAll(Arrays.asList(idCatedra, url));

        try {
            requestControl.validarRequest();
            if(servicio.existeCatedra(idCatedra)) {
                response.setStatus(ConstantesServlet.UNPROCESSABLE_ENTITY);
                return;
            }
            servicio.addCatedra(new Catedra(idCatedra, url));
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ExcepcionRequest e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * Se realiza un control de la especificacion de la request.
     * Se obtienen todos los parametros necesarios para modificar una instancia de Catedra
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        RequestControl requestControl = new RequestControl();

        JsonBody body = new JsonBodyBuffer(request);
        String idCatedra = body.getString("idCatedra");
        String url = body.getString("url");
        requestControl.addAll(Arrays.asList(idCatedra, url));

        try {
            requestControl.validarRequest();

            if(!servicio.existeCatedra(idCatedra)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            servicio.updateCatedra(new Catedra(idCatedra, url));
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ExcepcionRequest e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * Se realiza un control de la especificacion de la request.
     * Luego, se elimina la catedra indicada por el id
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestControl requestControl = new RequestControl();
        String idCatedra = request.getParameter("idCatedra");
        requestControl.add(idCatedra);

        try {
            requestControl.validarRequest();

            if(!servicio.existeCatedra(idCatedra)){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            servicio.deleteCatedra(idCatedra);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ExcepcionRequest e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
