package br.edu.ifto.web.model;

import java.math.BigDecimal;

public class Produto {
    private final Long id;
    private final String nome;
    private final String descricao;
    private final BigDecimal preco;

    public Produto(Long id, String nome, String descricao, BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}
