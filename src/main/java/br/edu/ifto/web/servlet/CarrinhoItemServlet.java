package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.CarrinhoRepository;
import br.edu.ifto.web.dao.ProdutoRepository;
import br.edu.ifto.web.model.Produto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/carrinho/itens")
public class CarrinhoItemServlet extends HttpServlet {
    private final ProdutoRepository produtos = new ProdutoRepository();
    private final CarrinhoRepository carrinhos = new CarrinhoRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = parseId(request.getParameter("id"));
        if (id != null) {
            try {
                Optional<Produto> produto = produtos.buscarPorId(id);
                if (produto.isPresent()) {
                    UUID carrinhoId = SessaoCarrinho.obterOuCriar(request.getSession());
                    carrinhos.adicionar(carrinhoId, id);
                }
            } catch (SQLException erro) {
                throw new ServletException("Não foi possível adicionar o produto ao carrinho", erro);
            }
        }
        response.sendRedirect(request.getContextPath() + "/carrinho");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = parseId(request.getParameter("id"));
        HttpSession session = request.getSession(false);
        UUID carrinhoId = SessaoCarrinho.obter(session);
        if (id != null && carrinhoId != null) {
            try {
                carrinhos.remover(carrinhoId, id);
            } catch (SQLException erro) {
                throw new ServletException("Não foi possível remover o produto do carrinho", erro);
            }
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private Long parseId(String value) {
        try {
            return value == null ? null : Long.valueOf(value);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}
