package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.LoginService;
import services.LoginServiceImplement;

import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet({"/login", "/login.html"})
public class LoginServlet extends HttpServlet {
    //Inicializado dos variables estaticas
    final static String USERNAME = "admin";
    final static String PASSWORD = "12345";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Hago una condición preguntando si la cookie es distinto de null
        // si es verdadero obtengo la cookie caso contrario creo un nuevo objeto
        //de la cookie
        //Cookie[] cookies = req.getCookies() != null ? req.getCookies() : new Cookie[0];
        //Busco dentro la variable cookie si existe la cookie
       /* Optional<String> cookieOptional = Arrays.stream(cookies)
                .filter(c -> "username".equals(c.getName()))
                //Convertimos la cookie a tipo string
                .map(Cookie::getValue)
                .findAny();*/
        //Creamos un nuevo objeto de la cookie
        //LoginService auth= new LoginServiceImplement();
        //Optional<String> cookieOptional=auth.getUserName(req);
        LoginService loginService = new LoginServiceImplement();
        Optional<String> usernameOptional=auth.getUserName(req);


        if (usernameOptional.isPresent()) {
           resp.setContentType("text/html;charset=UTF-8");
            try(PrintWriter out = resp.getWriter()) {
                //Creo la plantilla html
                out.print("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\">");
                out.println("<title>Hola" + usernameOptional.get()+"</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Hola " + usernameOptional.get() +"ya has inicido sesión anteriormente!</h1>");
                out.println("<p><a href='" + req.getContextPath() + "/index.html'>Volver al inicio</a></p>");
                out.println("</body>");
                out.println("</html>");
            }
        }else{
            getServletContext().getRequestDispatcher("/login.jsp").forward(req,resp);
        }

    }

    //Sobreescribimos el método doPOst


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            //resp.setContentType("text/html;charset=UTF-8");
            //Cookie usernameCookie = new Cookie("username", username);
           // resp.addCookie(usernameCookie);
            /*try (PrintWriter out = resp.getWriter()) {
                //Creo la plantilla html
                out.print("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\">");
                out.println("<title>Login correcto</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Login correcto</h1>");
                out.println("<h3>Bienvenido a mi aplicación" + username + " sesion con exito!</h3>");
                out.println("<p><a href='" + req.getContextPath() + "/index.html'>Volver al inicio</a></p>");
                out.println("</body>");
                out.println("</html>");

            }*/

            //creo la secion
            HttpSession sesion=req.getSession();
            sesion.setAttribute("user", username);



            resp.sendRedirect(req.getContextPath()+"/login.html");
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos usted no tiene acceso");
        }
    }
}