package com.dualmicro.pesquisasatisfacao.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dualmicro.pesquisasatisfacao.R;

public class TelaAvisoCadastroPerguntas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_aviso_cadastro_perguntas);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intTelaListaPerguntas = new Intent(TelaAvisoCadastroPerguntas.this,TelaListaPerguntas.class);
                startActivity(intTelaListaPerguntas);
            }
        },4000);

    }
}
