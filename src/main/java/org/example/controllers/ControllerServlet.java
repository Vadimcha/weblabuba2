//package org.example.controllers;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//import org.example.models.RequestType;
//
//import java.io.IOException;
//
//@WebServlet("/api/*")
//public class ControllerServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        processRequest(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//        processRequest(req, resp);
//    }
//
//    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//        RequestType requestType = getRequestType(req);
//
//        if (requestType == RequestType.CALCULATE) {
//            resp.sendRedirect("./private/area-check?" + req.getQueryString());
//            return;
//        }
//        if (requestType == RequestType.CLEAR) {
//            req.getRequestDispatcher("./private/clear").forward(req, resp);
//            return;
//        }
//        if (requestType == RequestType.GET_ALL) {
//            req.getRequestDispatcher("./private/get-all");
//            return;
//        }
//    }
//
//    private RequestType getRequestType(HttpServletRequest req) {
//        String raw = req.getParameter("requestType");
//        if (raw == null) return RequestType.GET_ALL;
//
//        for (RequestType type : RequestType.values()) {
//            if (type.jsonValue.equalsIgnoreCase(raw)) {
//                return type;
//            }
//        }
//
//        return RequestType.GET_ALL;
//    }
//}
//
