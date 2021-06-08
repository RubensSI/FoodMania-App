package com.app.foodmaniaapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.app.foodmaniaapp.Adapter.AdapterEmpresa;
import com.app.foodmaniaapp.Adapter.AdapterProduto;
import com.app.foodmaniaapp.Helper.FirebaseConfig;
import com.app.foodmaniaapp.Listener.RecyclerItemClickListener;
import com.app.foodmaniaapp.Model.Empresa;
import com.app.foodmaniaapp.Model.Produto;
import com.app.foodmaniaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    //private MaterialSearshView searshView;
    private RecyclerView recyclerEmpresa;
    private List<Empresa> empresas = new ArrayList<>();
    private DatabaseReference mFirebaseRef;
    private AdapterEmpresa adapterEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        inicializarComponentes();
        mFirebaseRef = FirebaseConfig.getReferenceFirebase();
        autenticacao = FirebaseConfig.getFirefebaseAutentication();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Food Mania");
        setSupportActionBar(toolbar);

        recyclerEmpresa.setLayoutManager( new LinearLayoutManager(this));
        recyclerEmpresa.setHasFixedSize(false);
        adapterEmpresa = new AdapterEmpresa( empresas);
        recyclerEmpresa.setAdapter(adapterEmpresa);

        /* Recupera empresa */
        recuperarEmpresas();

        recyclerEmpresa.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerEmpresa,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Empresa empresaSelecionada = empresas.get(position);
                                Intent i = new Intent(HomeActivity.this, CardapioActivity.class);
                                i.putExtra("empresa", empresaSelecionada);
                                startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }

    private void recuperarEmpresas() {
        DatabaseReference mEmpresaRef = mFirebaseRef.child("empresa");
        mEmpresaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                empresas.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    empresas.add(ds.getValue(Empresa.class));
                }

                adapterEmpresa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_usuario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menuSair :
                deslogarUsuario();
                break;
            case R.id.menuConfiguracoes :
                abrirConfigaracoes();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void inicializarComponentes() {
        recyclerEmpresa = findViewById(R.id.recyclerEmpresa);
    }

    private void deslogarUsuario() {
        try {

            autenticacao.signOut();
            Intent intent = new Intent(HomeActivity.this, AutenticationActivity
                    .class);
            startActivity(intent);
            finish();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private void abrirConfigaracoes() {

        startActivity(new Intent( HomeActivity.this, ConfiguracoesUsuarioActivity.class));
    }
}