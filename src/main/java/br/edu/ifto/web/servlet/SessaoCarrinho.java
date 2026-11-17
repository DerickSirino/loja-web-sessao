package br.edu.ifto.web.servlet;

import javax.servlet.http.HttpSession;
import java.util.UUID;

final class SessaoCarrinho {
    private SessaoCarrinho() {
    }

    static UUID obter(HttpSession sessao) {
        return sessao == null ? null : (UUID) sessao.getAttribute("carrinho");
    }

    static UUID obterOuCriar(HttpSession sessao) {
        UUID id = obter(sessao);
        if (id == null) {
            id = UUID.randomUUID();
            sessao.setAttribute("carrinho", id);
        }
        return id;
    }
}
