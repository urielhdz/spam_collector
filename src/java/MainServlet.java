/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.PeopleFeed;
import com.google.gson.Gson;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.SessionHandler;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;
import javax.servlet.ServletContext;
/**
 *
 * @author urielhernandez
 */
public class MainServlet extends HttpServlet {

    private static final String CLIENT_ID = "996123163592-tbm1tkl9s2lnpo1c472clvrbhk678u8q.apps.googleusercontent.com";

    private static final String CLIENT_SECRET = "f_zSPgB7cKkv2NgTBXYoX8S8";

    private static final String APPLICATION_NAME = "Google+ Java Quickstart";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!"/".equals(request.getServletPath())) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return;
      }

      response.setContentType("text/html");
      try {
        // Create a state token to prevent request forgery.
        // Store it in the session for later validation.
        String state = new BigInteger(130, new SecureRandom()).toString(32);
        request.getSession().setAttribute("state", state);
        // Fancy way to read index.html into memory, and set the client ID
        // and state values in the HTML before serving it.
        ServletContext s_context = this.getServletContext();
        response.getWriter().print(new Scanner(new File(s_context.getRealPath("index.html")), "UTF-8")
            .useDelimiter("\\A").next()
            .replaceAll("[{]{2}\\s*CLIENT_ID\\s*[}]{2}", CLIENT_ID)
            .replaceAll("[{]{2}\\s*STATE\\s*[}]{2}", state)
            .replaceAll("[{]{2}\\s*APPLICATION_NAME\\s*[}]{2}",
                APPLICATION_NAME)
            .toString());
        response.setStatus(HttpServletResponse.SC_OK);
      } catch (FileNotFoundException e) {
        // When running the quickstart, there was some path issue in finding
        // index.html.  Double check the quickstart guide.
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().print(e.toString());
      }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
