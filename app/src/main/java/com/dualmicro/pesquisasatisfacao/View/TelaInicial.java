package com.dualmicro.pesquisasatisfacao.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dualmicro.pesquisasatisfacao.R;
import com.dualmicro.pesquisasatisfacao.dao.PesquisaSatisfacaoDB;

public class TelaInicial extends AppCompatActivity {

    private Button btnIniciaPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        if (new PesquisaSatisfacaoDB(this).buscaTodasRespostas().size() == 0) {
            Intent inteTelaAviso = new Intent(this,TelaAvisoCadastroPerguntas.class);
            startActivity(inteTelaAviso);
            finish();
        }

        geraComponentes();
        geraEventos();
        if (getIntent().getIntExtra("MenssagemRetornoTempoExcedido", 0) == 2)
            Toast.makeText(this, "Demora na respota questionario reiniciado", Toast.LENGTH_LONG).show();

    }

    protected void geraComponentes() {
        btnIniciaPesquisa = (Button) findViewById(R.id.btnInicioPesquisa_ActTelaInicial);

    }

    protected void geraEventos() {
        btnIniciaPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentInicioPesquisa = new Intent(TelaInicial.this, TelaPerguntas.class);
                startActivity(intentInicioPesquisa);
            }
        });
    }

    protected void escondeTela() {
        View decorView = getWindow().getDecorView();
// Esconde tanto a barra de navegação e a barra de status .
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
