package com.app.foodmaniaapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.foodmaniaapp.Helper.FirebaseConfig;
import com.app.foodmaniaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastrarActivity extends AppCompatActivity {

    private EditText edt_sobrenome_cadastrar, edt_nome_cadastrar;
    private EditText edt_email_cadastrar;
    private EditText edt_senha_cadastrar;
    private Button btn_cadastro_cadastrar;

    private FirebaseAuth autentication;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        getSupportActionBar().hide();

        InitComponebts();

        autentication = FirebaseConfig.getFirefebaseAutentication();

        btn_cadastro_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email_cadastrar.getText().toString();
                String senha = edt_senha_cadastrar.getText().toString();

                if (!email.isEmpty()) {
                    if (!senha.isEmpty()) {
                        autentication.createUserWithEmailAndPassword(
                                email, senha
                        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CadastrarActivity.this,
                                            "Cadastro realizado com sucesso!",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                } else {
                                    String erroExcesao = " ";
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        erroExcesao = "Digite uma senha mais forte";
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        erroExcesao = "Por favor, digite um email valido";
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        erroExcesao = "Esta conta já foi cadastrada";
                                    } catch (Exception e) {
                                    erroExcesao = "ao cadastrar usuário: " + e.getMessage();
                                    e.printStackTrace();
                                    }

                                    Toast.makeText(CadastrarActivity.this, "Erro" + erroExcesao, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(CadastrarActivity.this,"Preencha o campo senha",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CadastrarActivity.this,"Preencha o campo email",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void InitComponebts() {
        edt_nome_cadastrar = findViewById(R.id.edt_nome_cadastrar);
        edt_sobrenome_cadastrar = findViewById(R.id.edt_sobrenome_cadastrar);
        edt_email_cadastrar = findViewById(R.id.edt_email_cadastrar);
        edt_senha_cadastrar = findViewById(R.id.edt_senha_cadastrar);
        btn_cadastro_cadastrar = findViewById(R.id.btn_cadastro_cadastrar);
    }
}