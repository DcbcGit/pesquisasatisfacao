package com.dualmicro.pesquisasatisfacao.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dualmicro.pesquisasatisfacao.Model.PerguntasMODClasse;
import com.dualmicro.pesquisasatisfacao.R;
import com.dualmicro.pesquisasatisfacao.dao.PesquisaSatisfacaoDB;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class TelaPerguntas extends AppCompatActivity {

    Button btn01, btn02, btn03, btn04, btn05;
    TextView txtPergunta;
    List<PerguntasMODClasse> lstPerguntas;
    PerguntasMODClasse perguntaAtual;
    Timer tempo;
    int perAtual = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perguntas);

        lstPerguntas = new PesquisaSatisfacaoDB(this).buscaTodasRespostas();

        geraComponentes();
        geraEventos();
        geraTempoTimer();
        mostraPergunta(lstPerguntas.get(perAtual - 1));
    }

    protected void mostraPergunta(PerguntasMODClasse pergunta) {
        this.perguntaAtual = pergunta;
        txtPergunta.setText(this.perguntaAtual.getPergunta());
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            PesquisaSatisfacaoDB pesqDAO = new PesquisaSatisfacaoDB(TelaPerguntas.this);
            pesqDAO.inserirResposta(perguntaAtual.getCodigoPergunta(), (Integer.decode(String.valueOf(v.getTag()))));

            if (perAtual < lstPerguntas.size())
                mostraPergunta(lstPerguntas.get(perAtual));

            tempo.cancel();
            geraTempoTimer();

            perAtual++;
            if (perAtual > lstPerguntas.size()) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intfinal = new Intent(TelaPerguntas.this, TelaFinalPesquisa.class);
                        startActivity(intfinal);
                        tempo.cancel();
                        finish();
                    }
                }, 100);
            }
        }
    };

    protected void geraComponentes() {
        btn01 = (Button) findViewById(R.id.btnResposta01_ACTPerguntas);
        btn02 = (Button) findViewById(R.id.btnResposta02_ACTPerguntas);
        btn03 = (Button) findViewById(R.id.btnResposta03_ACTPerguntas);
        btn04 = (Button) findViewById(R.id.btnResposta04_ACTPerguntas);
        btn05 = (Button) findViewById(R.id.btnResposta05_ACTPerguntas);

        txtPergunta = (TextView) findViewById(R.id.txtPergunta_ACTPergunta);
    }

    protected void geraEventos() {
        btn01.setOnClickListener(onClickListener);
        btn02.setOnClickListener(onClickListener);
        btn03.setOnClickListener(onClickListener);
        btn04.setOnClickListener(onClickListener);
        btn05.setOnClickListener(onClickListener);

    }

    protected void geraTempoTimer() {
        tempo = new Timer();
        tempo.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
                Intent inteTelaInicial = new Intent(TelaPerguntas.this, TelaInicial.class);
                inteTelaInicial.putExtra("MenssagemRetornoTempoExcedido", 2);
                startActivity(inteTelaInicial);
            }
        }, 5000);
    }
}