package br.edu.ifto.web.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

final class Database {
    private Database() {
    }

    static Connection conectar() throws SQLException {
        return DriverManager.getConnection(
                config("DB_URL", "jdbc:postgresql://localhost:5432/loja"),
                config("DB_USER", "loja"),
                config("DB_PASSWORD", "loja"));
    }

    private static String config(String nome, String padrao) {
        String valor = System.getenv(nome);
        return valor == null || valor.isBlank() ? padrao : valor;
    }
}
