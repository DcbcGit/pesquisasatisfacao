package com.dualmicro.pesquisasatisfacao.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dualmicro.pesquisasatisfacao.Model.PerguntasMODClasse;
import com.dualmicro.pesquisasatisfacao.R;
import com.dualmicro.pesquisasatisfacao.dao.PesquisaSatisfacaoDB;

public class TelaCadastroPerguntas extends AppCompatActivity {
    TextView txtPergunta;
    Button btnNovoPergunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_perguntas);

    }

    protected void geraComponentes() {
        txtPergunta = (TextView) findViewById(R.id.txtPergunta_ACTCadastroPergunta);
        btnNovoPergunta = (Button) findViewById(R.id.btnNovaPergunta_ACTListaPerguntas);
    }

    protected void geraEventos(){
        btnNovoPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerguntasMODClasse pergMod = new PerguntasMODClasse();
                PesquisaSatisfacaoDB pergDao = new PesquisaSatisfacaoDB(TelaCadastroPerguntas.this);

                pergMod.setPergunta(txtPergunta.getText().toString());
                pergMod.setStatus(1);

                pergDao.inserirPergunta(pergMod);
            }
        });
    }

}
