package com.example.agenda_contatos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class Formulario extends AppCompatActivity {

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

        meuContato.setNome("Leonardo");
        meuContato.setEmail("leonardocescaf@gmail.com");
        meuContato.setTelefone("983310534");
        meuContato.setEndereco("Av. Farrapos,3852");

        meuContato.setId(new Long(10));

        FormularioHelper helper = new FormularioHelper(this);

        helper.colocaNoFormulario(meuContato);

        Contato meuContato2 = new Contato();

        meuContato2 = helper.pegaContatoDoFormulario();

        Log.i("Meu Log", meuContato2.getNome());
        Log.i("Meu Log", meuContato2.getEmail());
        Log.i("Meu Log", meuContato2.getTelefone());
        Log.i("Meu Log", meuContato2.getEndereco());

        Log.i("Meu Log", String.valueOf(meuContato2.getId()));

        Log.i("Meu Log", meuContato2.toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home) {
            this.finish();
        } else if (id == R.id.menu_formulario_ok) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return true;
    }
}
