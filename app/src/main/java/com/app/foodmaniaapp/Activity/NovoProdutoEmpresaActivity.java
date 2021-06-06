package com.app.foodmaniaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.foodmaniaapp.Helper.mFirebaseUsers;
import com.app.foodmaniaapp.Model.Produto;
import com.app.foodmaniaapp.R;

import java.util.Objects;

public class NovoProdutoEmpresaActivity extends AppCompatActivity {

    private EditText edtProdutoNome, edtProdutoDescricao, edtProdutoPreco;
    private String idUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_produto_empresa);

        /* Configurações iniciais */
        inicializarCompenentes();
        idUsuarioLogado = mFirebaseUsers.getUserId();

        // Configuracao Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Produto");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    public void validarDadosProduto(View view) {

        String nome = edtProdutoNome.getText().toString();
        String descricao = edtProdutoDescricao.getText().toString();
        String preco = edtProdutoPreco.getText().toString();

        if ( !nome.isEmpty() ) {
            if ( !descricao.isEmpty() ) {
                if ( !preco.isEmpty() ) {

                    Produto produto = new Produto();
                    produto.setIdUsuario( idUsuarioLogado );
                    produto.setNome( nome );
                    produto.setDescricao( descricao );
                    produto.setPreco( Double.parseDouble( preco ) );
                    produto.salvar();

                    finish();
                    exibirMensagem("Produto salvo com sucesso!");


                } else {
                    exibirMensagem("Digite um preço para o produto");
                }
            } else {
                exibirMensagem("Digite uma descrição para o produto");
            }
        } else {
            exibirMensagem("Digite um nome para o produto");
        }

    }

    private void exibirMensagem(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void inicializarCompenentes() {
        edtProdutoNome = findViewById(R.id.edt_nome_prod_novoprod);
        edtProdutoDescricao  = findViewById(R.id.edt_desc_prod_novoprod);
        edtProdutoPreco = findViewById(R.id.edt_preco_prod_novoprod);
    }


}