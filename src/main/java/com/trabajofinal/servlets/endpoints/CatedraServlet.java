package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.modelo.Catedra;
import com.trabajofinal.servlets.endpoints.body.RequestBody;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.servicios.Servicios;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="Catedra", value= ConstantesServlet.URL_CATEDRA)
public class CatedraServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject body = RequestBody.getRequestBody(request);
        String idCatedra = body.getString("idCatedra");
        String url = body.getString("url");

        try {
            if(servicio.existeCatedra(idCatedra)) {
                response.setStatus(ConstantesServlet.UNPROCESSABLE_ENTITY);
                return;
            }
            servicio.addCatedra(new Catedra(idCatedra, url));
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject body = RequestBody.getRequestBody(request);
        String idCatedra = body.getString("idCatedra");
        String url = body.getString("url");

        try {
            if(!servicio.existeCatedra(idCatedra)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            servicio.updateCatedra(new Catedra(idCatedra, url));
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idCatedra = request.getParameter("idCatedra");

        try {
            if(!servicio.existeCatedra(idCatedra)){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            servicio.deleteCatedra(idCatedra);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
