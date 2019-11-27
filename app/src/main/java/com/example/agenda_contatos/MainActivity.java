package com.example.agenda_contatos;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView minhaLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Formulario.class);
                startActivity(intent);
            }
        });
        minhaLista = (ListView) findViewById(R.id.minhaLista);
        registerForContextMenu(minhaLista);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        final MenuItem itemLigar = menu.add("Ligar para Contato");
        final MenuItem itemSMS = menu.add("Enviar SMS");
        final MenuItem itemApagar = menu.add("Apagar Contato");

        itemApagar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                
                return false;
            }
        });



        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onResume() {
        carregaLista();
        super.onResume();
    }
    private void carregaLista(){
        ContatoDAO dao = new ContatoDAO(this);
        List<Contato> contatos = dao.getLista();
        dao.close();
        ContatoAdaptador adaptador = new ContatoAdaptador(contatos, this);

        this.minhaLista.setAdapter(adaptador);
    }
}
