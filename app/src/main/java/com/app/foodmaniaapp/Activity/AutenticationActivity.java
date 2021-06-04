package com.app.foodmaniaapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.app.foodmaniaapp.Helper.FirebaseConfig;
import com.app.foodmaniaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AutenticationActivity extends AppCompatActivity {

    private Button btn_acessar_login, btn_cadastrar_login;
    private EditText etd_email_login, edt_senha_login;

    private FirebaseAuth autentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autentication);

        InitialComponents();

        autentication = FirebaseConfig.getFirefebaseAutentication();

        // verificar usuario logado
        checkUser();

        btn_acessar_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etd_email_login.getText().toString();
                String passwd = edt_senha_login.getText().toString();

                if (!email.isEmpty() ) {
                    if (!passwd.isEmpty() ) {
                        autentication.signInWithEmailAndPassword(
                                email, passwd
                        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AutenticationActivity.this,
                                            "Usuário logado com sucesso ",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser currentUser = autentication.getCurrentUser();
                                    OpenHome(currentUser.getDisplayName());
                                } else {
                                    Toast.makeText(AutenticationActivity.this,
                                            "Erro ao fazer login " + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(AutenticationActivity.this, "Preencha a Senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AutenticationActivity.this, "Preencha o E-mail!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_cadastrar_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCadastrar();
            }
        });

    }

    private void OpenCadastrar() {
        Intent i = new Intent(AutenticationActivity.this, CadastrarActivity.class);
        startActivity(i);
        finish();
    }

    private void checkUser() {
        FirebaseUser currentUser = autentication.getCurrentUser();
        if ( currentUser != null ) {
            OpenHome(currentUser.getDisplayName());
        }
    }

    private void OpenHome(String tipoUsuario) {
       if (tipoUsuario.equals("E")) {
           startActivity(new Intent(getApplicationContext(), EmpresaActivity.class));
        } else {
           startActivity(new Intent(getApplicationContext(), HomeActivity.class));
       }
    }

    private void InitialComponents() {
        btn_cadastrar_login = findViewById(R.id.btn_cadastrar_login);
        etd_email_login = findViewById(R.id.edt_email_login);
        edt_senha_login = findViewById(R.id.edt_senha_login);
        btn_acessar_login = findViewById(R.id.btn_acessar_login);

    }
}