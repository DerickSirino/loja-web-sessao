package br.edu.ifto.web.dao;

import br.edu.ifto.web.model.ItemCarrinho;
import br.edu.ifto.web.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarrinhoRepository {
    public List<ItemCarrinho> listar(UUID carrinhoId) throws SQLException {
        List<ItemCarrinho> itens = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.descricao, p.preco, i.quantidade "
                + "FROM carrinho_itens i JOIN produtos p ON p.id = i.produto_id "
                + "WHERE i.carrinho_id = ? ORDER BY p.id";
        try (Connection conexao = Database.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setObject(1, carrinhoId);
            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    Produto produto = new Produto(resultado.getLong("id"), resultado.getString("nome"),
                            resultado.getString("descricao"), resultado.getBigDecimal("preco"));
                    itens.add(new ItemCarrinho(produto, resultado.getInt("quantidade")));
                }
            }
        }
        return itens;
    }

    public void adicionar(UUID carrinhoId, Long produtoId) throws SQLException {
        try (Connection conexao = Database.conectar()) {
            conexao.setAutoCommit(false);
            try (PreparedStatement criar = conexao.prepareStatement(
                    "INSERT INTO carrinhos (id) VALUES (?) ON CONFLICT (id) DO NOTHING");
                 PreparedStatement adicionar = conexao.prepareStatement(
                    "INSERT INTO carrinho_itens (carrinho_id, produto_id, quantidade) VALUES (?, ?, 1) "
                    + "ON CONFLICT (carrinho_id, produto_id) DO UPDATE "
                    + "SET quantidade = carrinho_itens.quantidade + 1")) {
                criar.setObject(1, carrinhoId);
                criar.executeUpdate();
                adicionar.setObject(1, carrinhoId);
                adicionar.setLong(2, produtoId);
                adicionar.executeUpdate();
                conexao.commit();
            } catch (SQLException erro) {
                conexao.rollback();
                throw erro;
            }
        }
    }

    public void remover(UUID carrinhoId, Long produtoId) throws SQLException {
        try (Connection conexao = Database.conectar();
             PreparedStatement comando = conexao.prepareStatement(
                     "DELETE FROM carrinho_itens WHERE carrinho_id = ? AND produto_id = ?")) {
            comando.setObject(1, carrinhoId);
            comando.setLong(2, produtoId);
            comando.executeUpdate();
        }
    }

    public void limpar(UUID carrinhoId) throws SQLException {
        try (Connection conexao = Database.conectar();
             PreparedStatement comando = conexao.prepareStatement("DELETE FROM carrinhos WHERE id = ?")) {
            comando.setObject(1, carrinhoId);
            comando.executeUpdate();
        }
    }
}
