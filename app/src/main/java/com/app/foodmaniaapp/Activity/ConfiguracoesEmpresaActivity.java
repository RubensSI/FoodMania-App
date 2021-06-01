package com.app.foodmaniaapp.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.foodmaniaapp.Helper.mFirebaseUsers;
import com.app.foodmaniaapp.Model.Empresa;
import com.app.foodmaniaapp.Model.Upload;
import com.app.foodmaniaapp.R;

import java.util.Objects;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
public class ConfiguracoesEmpresaActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;

    // componentes empresa
    private EditText edt_nome_emp_config;
    private EditText edt_categoria_emp_config;
    private EditText edt_tempo_emp_config;
    private EditText edt_taxa_emp_config;
    private Button btn_salvar_emp_config;

    // componentes upload imagem
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;

    private String DownloadUrl = "";

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    private String idUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes_empresa);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Configurações");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.profile_image);
        mProgressBar = findViewById(R.id.progress_bar);

        edt_nome_emp_config = findViewById(R.id.edt_nome_emp_config);
        edt_categoria_emp_config = findViewById(R.id.edt_categoria_emp_config);
        edt_tempo_emp_config = findViewById(R.id.edt_tempo_emp_config);
        edt_taxa_emp_config = findViewById(R.id.edt_taxa_emp_config);
        btn_salvar_emp_config = findViewById(R.id.btn_salvar_emp_config);

        idUsuarioLogado = mFirebaseUsers.getUserId();

        mStorageRef = FirebaseStorage.getInstance().getReference()
                .child("imagens")
                .child("empresas")
                .child(idUsuarioLogado + "jpeg");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference()
                .child("imagens")
                .child("empresas")
                .child(idUsuarioLogado + "jpeg");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    uploadFile();
            }
        });

        btn_salvar_emp_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDadosEmpresa();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void validarDadosEmpresa() {

        String nome = edt_nome_emp_config.getText().toString();
        String taxa = edt_taxa_emp_config.getText().toString();
        String categoria = edt_categoria_emp_config.getText().toString();
        String tempo = edt_tempo_emp_config.getText().toString();

        if ( !nome.isEmpty() ) {
            if ( !taxa.isEmpty() ) {
                if ( !categoria.isEmpty() ) {
                    if ( !tempo.isEmpty() ) {

                        Empresa empresa = new Empresa();
                        empresa.setIdUsuario( idUsuarioLogado );
                        empresa.setNome( nome );
                        empresa.setPrecoEntrega( Double.parseDouble(taxa));
                        empresa.setCategoria( categoria );
                        empresa.setUrlImagem( DownloadUrl );
                        empresa.salvar();
                        exibirMensagem("Empresa adicionada com sucesso!");
                        finish();

                    } else {
                        exibirMensagem("Digite um tempo de entrega");
                    }
                } else {
                    exibirMensagem("Digite uma categoria");
                }
            } else {
                exibirMensagem("Digite uma taxa de entrega");
            }
        } else {
            exibirMensagem("Digite um nome para a empresa");
        }

    }

    private void exibirMensagem(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(ConfiguracoesEmpresaActivity.this,
                                    "Upload realizado com sucesso", Toast.LENGTH_LONG).show();
                                     DownloadUrl = taskSnapshot.getMetadata().getReference()
                                    .getDownloadUrl().toString();
                            Upload upload = new Upload(mEditTextFileName.getText().toString()
                                    .trim(), DownloadUrl);

                            String uploadAd = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadAd).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(ConfiguracoesEmpresaActivity.this,
                                    e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() /
                                    taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });

        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}