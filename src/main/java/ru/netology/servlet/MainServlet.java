package ru.netology.servlet;

import java.io.IOException;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/", loadOnStartup=1)
public class MainServlet extends HttpServlet {
    private PostController controller;

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final var path = req.getRequestURI();

        if (path.equals("/api/get")||path.equals("/")) {
            controller.all(resp);
            return;
        }
        if (path.matches("/api/get/\\d+")) {
            // easy way
            final var id = Long.parseLong(path.substring(path.lastIndexOf("/")+1));
            controller.getById(id, resp);
            return;
        }
        super.doGet(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final var path = req.getRequestURI();

        if (path.equals("/api/posts")) {
            controller.save(req.getReader(), resp);
            return;
        }
        super.doPost(req,resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final var path = req.getRequestURI();
        if (path.matches("/api/delete/\\d+")) {
            final var id = Long.parseLong(path.substring(path.lastIndexOf("/")+1));
            controller.removeById(id, resp);
            return;
        }
        super.doDelete(req,resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final var method = req.getMethod();

        if (method.equals("GET")){
            doGet(req,resp);
            return;
        }
        if (method.equals("POST")){
            doPost(req,resp);
            return;
        }
        if (method.equals("DELETE")){
            doDelete(req,resp);
            return;
        }

       resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}

