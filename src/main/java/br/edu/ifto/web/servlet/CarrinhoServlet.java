package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.CarrinhoRepository;
import br.edu.ifto.web.model.ItemCarrinho;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {
    private final CarrinhoRepository repository = new CarrinhoRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UUID carrinhoId = SessaoCarrinho.obter(session);
        List<ItemCarrinho> carrinho;
        try {
            carrinho = carrinhoId == null ? Collections.emptyList() : repository.listar(carrinhoId);
        } catch (SQLException erro) {
            throw new ServletException("Não foi possível consultar o carrinho", erro);
        }

        int quantidadeTotal = carrinho.stream()
                .mapToInt(ItemCarrinho::getQuantidade)
                .sum();
        BigDecimal valorTotal = carrinho.stream()
                .map(ItemCarrinho::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        request.setAttribute("carrinho", carrinho);
        request.setAttribute("quantidadeTotal", quantidadeTotal);
        request.setAttribute("valorTotal", valorTotal);
        request.getRequestDispatcher("/WEB-INF/views/carrinho.jsp").forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UUID carrinhoId = SessaoCarrinho.obter(session);
        if (carrinhoId != null) {
            try {
                repository.limpar(carrinhoId);
            } catch (SQLException erro) {
                throw new ServletException("Não foi possível limpar o carrinho", erro);
            }
            session.removeAttribute("carrinho");
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
