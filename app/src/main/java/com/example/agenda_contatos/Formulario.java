package com.example.agenda_contatos;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class Formulario extends AppCompatActivity {

    FormularioHelper helper;
    Contato contato;

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
}
