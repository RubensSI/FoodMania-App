 package com.app.foodmaniaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.app.foodmaniaapp.Helper.FirebaseConfig;
import com.app.foodmaniaapp.R;
import com.google.firebase.auth.FirebaseAuth;

 public class HomeActivity extends AppCompatActivity {

     private FirebaseAuth mAuth;
     private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        inicializarComponentes();

        mAuth = FirebaseConfig.getFirefebaseAutentication();

        Toolbar toolbars = findViewById(R.id.toolbars);
        toolbars.setTitle("Food Mania");
        setSupportActionBar(toolbars);
    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {

         MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.menu_usuario, menu);

         // Configurar botão pesquisa
         MenuItem searchItem = menu.findItem(R.id.menuPesquisa);

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
        searchView = findViewById(R.id.materialSearshView);
     }

     private void deslogarUsuario() {
         try {

             mAuth.signOut();
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