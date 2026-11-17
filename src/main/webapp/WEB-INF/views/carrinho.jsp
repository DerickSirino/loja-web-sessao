<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrinho - Loja Web</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
<header class="topo">
    <div class="container topo-conteudo">
        <h1>Loja Web</h1>
        <nav class="menu">
            <a href="index.jsp">Início</a>
            <a href="produtos">Produtos</a>
            <a class="ativo" href="carrinho">Carrinho <span class="contador">${quantidadeTotal}</span></a>
        </nav>
    </div>
</header>

<main class="container secao">
    <div class="titulo-pagina">
        <div>
            <h2>Meu carrinho</h2>
            <p>Confira os produtos selecionados para esta sessão.</p>
        </div>
    </div>

    <div class="carrinho-layout">
        <section class="carrinho-produtos">
            <c:choose>
                <c:when test="${empty carrinho}">
                    <p>Seu carrinho está vazio.</p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="item" items="${carrinho}">
                        <article class="item-carrinho">
                            <div class="item-icone">${item.produto.nome == 'Notebook' ? '💻' : item.produto.nome == 'Mouse' ? '🖱️' : item.produto.nome == 'Teclado Mecânico' ? '⌨️' : item.produto.nome == 'Monitor' ? '🖥️' : '🎧'}</div>
                            <div class="item-info">
                                <h3>${item.produto.nome}</h3>
                                <span><fmt:formatNumber value="${item.produto.preco}" type="currency" /></span>
                            </div>
                            <div class="quantidade"><span>Quantidade</span><strong>${item.quantidade}</strong></div>
                            <div class="subtotal"><span>Subtotal</span><strong><fmt:formatNumber value="${item.subtotal}" type="currency" /></strong></div>
                            <button class="btn-remover" type="button" data-id="${item.produto.id}">Remover</button>
                        </article>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </section>

        <aside class="resumo">
            <h3>Resumo do pedido</h3>
            <div class="resumo-linha"><span>Itens</span><strong>${quantidadeTotal}</strong></div>
            <div class="resumo-linha total"><span>Total</span><strong><fmt:formatNumber value="${valorTotal}" type="currency" /></strong></div>
            <a class="btn btn-secundario btn-bloco" href="${pageContext.request.contextPath}/produtos">Continuar comprando</a>
            <button id="limpar-carrinho" class="btn btn-perigo btn-bloco" type="button">Limpar carrinho</button>
        </aside>
    </div>

</main>

<footer><div class="container">Programação para Web I</div></footer>
<script>
    const contexto = '${pageContext.request.contextPath}';

    document.querySelectorAll('.btn-remover').forEach(function (botao) {
        botao.addEventListener('click', function () {
            fetch(contexto + '/carrinho/itens?id=' + encodeURIComponent(botao.dataset.id), {method: 'DELETE'})
                .then(function (response) {
                    if (!response.ok) {
                        throw new Error('Erro ao remover o item');
                    }
                    window.location.reload();
                })
                .catch(function (erro) {
                    console.error(erro);
                });
        });
    });

    document.getElementById('limpar-carrinho').addEventListener('click', function () {
        fetch(contexto + '/carrinho', {method: 'DELETE'})
            .then(function (response) {
                if (!response.ok) {
                    throw new Error('Erro ao limpar o carrinho');
                }
                window.location.reload();
            })
            .catch(function (erro) {
                console.error(erro);
            });
    });
</script>
</body>
</html>
