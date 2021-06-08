package com.app.foodmaniaapp.Model;

import com.app.foodmaniaapp.Helper.FirebaseConfig;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by rubenspinto
 */

public class Produto {

    private String idUsuario;
    private String idProduto;
    private String nome;
    private String descricao;
    private Double preco;

    public Produto() {
        DatabaseReference mFirebaseRef = FirebaseConfig.getReferenceFirebase();
        DatabaseReference mProdutoRef = mFirebaseRef
                .child("produtos");
        setIdProduto( mProdutoRef.push().getKey() );
    }

    public void salvar() {
        DatabaseReference mFirebaseRef = FirebaseConfig.getReferenceFirebase();
        DatabaseReference mProdutoRef = mFirebaseRef
                .child("produtos")
                .child( getIdUsuario() )
                .child( getIdProduto() );
        mProdutoRef.setValue(this);
    }

    public void remover() {
        DatabaseReference mFirebaseRef = FirebaseConfig.getReferenceFirebase();
        DatabaseReference mProdutoRef = mFirebaseRef
                .child("produtos")
                .child( getIdUsuario() )
                .child( getIdProduto() );
        mProdutoRef.removeValue();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdProduto() { return idProduto; }

    public void setIdProduto(String idProduto) { this.idProduto = idProduto; }

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
