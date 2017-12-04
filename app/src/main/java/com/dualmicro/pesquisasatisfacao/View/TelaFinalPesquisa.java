package com.dualmicro.pesquisasatisfacao.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dualmicro.pesquisasatisfacao.R;

public class TelaFinalPesquisa extends AppCompatActivity {

    Button btnTelaListaPerguntas;
    Handler handle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_final_pesquisa);

        geraComponenteEvento();

        handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentInicioPesquisa = new Intent(TelaFinalPesquisa.this, TelaInicial.class);
                startActivity(intentInicioPesquisa);
                finish();
            }
        },5000);

    }

    protected void geraComponenteEvento(){
        btnTelaListaPerguntas = (Button) findViewById(R.id.btnAbreListaPerguntas_ACTTelaFinalPesquisa);

        btnTelaListaPerguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handle.removeCallbacksAndMessages(null);
                Intent intTelaListaPerguntas = new Intent(TelaFinalPesquisa.this,TelaListaPerguntas.class);
                startActivity(intTelaListaPerguntas);
                finish();
            }
        });
    }
}
