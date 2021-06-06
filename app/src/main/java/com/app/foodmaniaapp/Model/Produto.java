package com.app.foodmaniaapp.Model;

import com.app.foodmaniaapp.Helper.FirebaseConfig;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by rubenspinto
 */

public class Produto {

    private String idUsuario;
    private String nome;
    private String descricao;
    private Double preco;

    public Produto() {

    }

    public void salvar() {
        DatabaseReference mFirebaseRef = FirebaseConfig.getReferenceFirebase();
        DatabaseReference mProdutoRef = mFirebaseRef
                .child("produtos")
                .child( getIdUsuario() )
                .push();
        mProdutoRef.setValue(this);
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
