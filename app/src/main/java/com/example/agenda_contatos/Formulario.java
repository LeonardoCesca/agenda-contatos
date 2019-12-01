package com.example.agenda_contatos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.InputStream;

public class Formulario extends AppCompatActivity {

    FormularioHelper helper;
    Contato contato;
    private String localArquivoFoto;
    private static final int TIRA_FOTO = 123;
    private boolean fotoResource = false;

    private Bitmap bitmap;
    ImageView imagemContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Back Button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Contato meuContato = new Contato();

        this.helper = new FormularioHelper(this);

        Intent intent = this.getIntent();
        this.contato = (Contato) intent.getSerializableExtra("contatoSelecionado");

        if(this.contato != null) {
            this.helper.colocaNoFormulario(contato);
        }
        final Button botaoFoto = helper.getBotaoFoto();
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertaSourceImagem();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:

                Contato contato = helper.pegaContatoDoFormulario();
                ContatoDAO dao = new ContatoDAO(Formulario.this);

                if(contato.getId() == null){
                    dao.inserirContato(contato);
                }else{
                    dao.alteraContato(contato);
                }

                dao.close();

                finish();
                return false;

            case android.R.id.home:
                this.finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return true;
    }

    public void carregaFotoCamera(){

        fotoResource = true;

        localArquivoFoto = getExternalFilesDir(null)+"/"+System.currentTimeMillis()+".jpeg";
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName()+".provider", new File(localArquivoFoto)));
        startActivityForResult(intentCamera, 123);
    }
    public void carregaFotoBiblioteca(){
        fotoResource = false;

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 1);
    }

    private void alertaSourceImagem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione a fonte da imagem:");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                carregaFotoCamera();
            }
        });
        builder.setNegativeButton("Biblioteca", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                carregaFotoBiblioteca();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(!fotoResource){
            if(resultCode == -1) {
                InputStream stream = null;

                try {

                    if(bitmap != null) {
                        bitmap.recycle();
                    }

                    stream = getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(stream);
                    imagemContato.setImageBitmap(bitmap);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }else{
            if (requestCode == TIRA_FOTO){
                if(resultCode == Activity.RESULT_OK){
                    helper.carregaImagem(this.localArquivoFoto);
                }else{
                    this.localArquivoFoto = null;
                }
            }
        }

    }
}
