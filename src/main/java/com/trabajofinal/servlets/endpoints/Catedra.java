package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.servlets.endpoints.body.RequestBody;
import com.trabajofinal.utils.servlets.endpoints.Constantes;
import com.trabajofinal.exceptions.ExcepcionServicio;
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

@WebServlet(name="Catedra", value= Constantes.URL_CATEDRA)
public class Catedra extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idCatedra = request.getParameter("idCatedra");
        List<com.trabajofinal.modelo.Catedra> catedras = new ArrayList<>();

        try {
            if (idCatedra == null)
                catedras.addAll(servicio.getCatedras());
            else
                catedras.add(servicio.getCatedra(idCatedra));

            String listaUsuariosJson = this.gson.toJson(catedras);
            out.print(listaUsuariosJson);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al cargar el/las catedra/s");
        }finally {
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject body = RequestBody.getRequestBody(request);
        String idCatedra = body.getString("idCatedra");
        String url = body.getString("url");

        try {
            if(servicio.existeCatedra(idCatedra)){
                response.setStatus(Constantes.UNPROCESSABLE_ENTITY);
                out.print("La catedra " + idCatedra +", ya existe!");
            }else{
                servicio.addCatedra(new com.trabajofinal.modelo.Catedra(idCatedra, url));
                out.print("Catedra guardada exitosamente!");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al guardar la catedra");
        }finally {
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject body = RequestBody.getRequestBody(request);
        String idCatedra = body.getString("idCatedra");
        String url = body.getString("url");

        try {
            if(!servicio.existeCatedra(idCatedra)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("No se puede actualizar la catedra " + idCatedra +", porque no existe!");
            }else{
                servicio.updateCatedra(new com.trabajofinal.modelo.Catedra(idCatedra, url));
                out.print("Catedra actualizada exitosamente!");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al actualizar la catedra " + idCatedra);
        }finally{
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String idCatedra = String.valueOf(request.getParameter("idCatedra"));

        try {
            if(!servicio.existeCatedra(idCatedra)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("No se puede eliminar la catedra " + idCatedra +", porque no existe!");
            }else{
                servicio.deleteCatedra(idCatedra);
                out.print("Catedra " +  idCatedra + " eliminada");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al eliminar la catedra " + idCatedra);
        }finally{
            out.flush();
        }
    }
}
