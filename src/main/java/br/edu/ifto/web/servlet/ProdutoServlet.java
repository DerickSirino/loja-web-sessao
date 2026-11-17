package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.ProdutoRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {
    private final ProdutoRepository repository = new ProdutoRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("produtos", repository.listarTodos());
        } catch (SQLException erro) {
            throw new ServletException("Não foi possível consultar os produtos", erro);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/produtos.jsp");
        dispatcher.forward(request, response);
    }
}
