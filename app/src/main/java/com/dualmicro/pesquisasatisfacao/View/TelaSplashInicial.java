package com.dualmicro.pesquisasatisfacao.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dualmicro.pesquisasatisfacao.R;
import com.dualmicro.pesquisasatisfacao.dao.PesquisaSatisfacaoDB;


public class TelaSplashInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_splash_inicial);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                escondeTela();
                ChamarTelaInicial();
            }
        },3000);
    }

    protected void ChamarTelaInicial(){

        Intent intTelaInicial;
        PesquisaSatisfacaoDB perguntas = new PesquisaSatisfacaoDB(this);

        if(perguntas.buscaTodasRespostas().size() == 0){
            intTelaInicial = new Intent(this,TelaAvisoCadastroPerguntas.class);
        }else {
            intTelaInicial = new Intent(this,TelaInicial.class);
        }
        startActivity(intTelaInicial);
        finish();
    }

    protected void escondeTela() {
        View decorView = getWindow().getDecorView();
// Esconde tanto a barra de navegação e a barra de status .
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
