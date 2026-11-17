package br.edu.ifto.web.dao;

import br.edu.ifto.web.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoRepository {
    public List<Produto> listarTodos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        try (Connection conexao = Database.conectar();
             PreparedStatement comando = conexao.prepareStatement(
                     "SELECT id, nome, descricao, preco FROM produtos ORDER BY id");
             ResultSet resultado = comando.executeQuery()) {
            while (resultado.next()) {
                produtos.add(mapear(resultado));
            }
        }
        return produtos;
    }

    public Optional<Produto> buscarPorId(Long id) throws SQLException {
        try (Connection conexao = Database.conectar();
             PreparedStatement comando = conexao.prepareStatement(
                     "SELECT id, nome, descricao, preco FROM produtos WHERE id = ?")) {
            comando.setLong(1, id);
            try (ResultSet resultado = comando.executeQuery()) {
                return resultado.next() ? Optional.of(mapear(resultado)) : Optional.empty();
            }
        }
    }

    private Produto mapear(ResultSet resultado) throws SQLException {
        return new Produto(resultado.getLong("id"), resultado.getString("nome"),
                resultado.getString("descricao"), resultado.getBigDecimal("preco"));
    }
}
