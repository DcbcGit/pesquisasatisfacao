package com.dualmicro.pesquisasatisfacao.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dualmicro.pesquisasatisfacao.Model.PerguntasMODClasse;
import com.dualmicro.pesquisasatisfacao.R;
import com.dualmicro.pesquisasatisfacao.dao.PesquisaSatisfacaoDB;

import java.util.List;

public class TelaListaPerguntas extends AppCompatActivity {

    PerguntasMODClasse perguntaEdicao;
    EditText txtPergunta;
    Button btnNovoPergunta;
    ListView lstViewPerguntas;
    List<PerguntasMODClasse> lstPerguntas;
    ArrayAdapter pergAdap;
    private AlertDialog alerta = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_lista_perguntas);

        perguntaEdicao = new PerguntasMODClasse();
        geraComponentes();
        geraEventos();
        atualizaLista();

        registerForContextMenu(lstViewPerguntas);
    }

    //Menu da List View de Perguntas
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo escolhaUsu = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final PerguntasMODClasse perguntaSelecionada = (PerguntasMODClasse) lstViewPerguntas.getItemAtPosition(escolhaUsu.position);

        switch (item.getItemId()) {
            case R.id.menu_Exclui_Pergunta_ACTListaPerguntas:
                new AlertDialog.Builder(this)
                        .setTitle("Excluindo Pergunta")
                        .setMessage("Tem certeza que deseja excluir a pergunta?")
                        .setPositiveButton("Excluir",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        new PesquisaSatisfacaoDB(TelaListaPerguntas.this).excluirPergunta(perguntaSelecionada);
                                        new PesquisaSatisfacaoDB(TelaListaPerguntas.this).excluirResposta(perguntaSelecionada.getCodigoPergunta());
                                        atualizaLista();
                                    }
                                })
                        .show();

                break;
        }

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();

        switch (v.getId()) {
            case R.id.lstPerguntas_ACTListaPerguntas:
                inflater.inflate(R.menu.menu_lista_perguntas_item, menu);
                break;
        }
    }

    //Menu da Barra de Estatus
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_perguntas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intMenu = null;

        switch (item.getItemId()) {
            //case R.id.menu_AnaliseDados_ACTListaPerguntas:
             //   intMenu = new Intent(this, TelaAnalisePesquisa.class);
              //  break;
            case R.id.menu_VoltaPesquisa__ACTListaPerguntas:
                intMenu = new Intent(this, TelaInicial.class);
                break;
        }

        startActivity(intMenu);
        finish();

        return true;
    }

    protected void geraComponentes() {
        btnNovoPergunta = (Button) findViewById(R.id.btnNovaPergunta_ACTListaPerguntas);
        lstViewPerguntas = (ListView) findViewById(R.id.lstPerguntas_ACTListaPerguntas);
        txtPergunta = (EditText) findViewById(R.id.txtPergunta_ACTCadastroPergunta);
    }

    protected void geraEventos() {
        btnNovoPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamaTelaCadastroPergunta();
            }
        });
        lstViewPerguntas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int posicao, long id) {
                perguntaEdicao = (PerguntasMODClasse) lstViewPerguntas.getItemAtPosition(posicao);
                chamaTelaCadastroPergunta();
            }
        });
    }

    protected void atualizaLista() {

        //new AcessoAssyncClass().execute();
        lstPerguntas = new PesquisaSatisfacaoDB(this).buscaTodasRespostas();
        //AlunoAdapter adap = new AlunoAdapter(this, lstAlunos);
        pergAdap = new ArrayAdapter<PerguntasMODClasse>(this, android.R.layout.simple_list_item_1, lstPerguntas);
        lstViewPerguntas.setAdapter(pergAdap);
    }

    private void chamaTelaCadastroPergunta() {

        LayoutInflater li = getLayoutInflater();
        View view = li.inflate(R.layout.activity_tela_cadastro_perguntas, null);
        final EditText txtPer = (EditText) view.findViewById(R.id.txtPergunta_ACTCadastroPergunta);

        txtPer.setText(perguntaEdicao.getPergunta());

        view.findViewById(R.id.btnGravaPergunta_ACTCadastroPergunta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PesquisaSatisfacaoDB pergDao = new PesquisaSatisfacaoDB(TelaListaPerguntas.this);

                perguntaEdicao.setPergunta(txtPer.getText().toString());
                perguntaEdicao.setStatus(1);

                if (perguntaEdicao.getCodigoPergunta() == 0) {
                    pergDao.inserirPergunta(perguntaEdicao);
                    Toast.makeText(TelaListaPerguntas.this, "Pergunta Cadastrada...", Toast.LENGTH_LONG).show();
                } else {
                    pergDao.atualizaPergunta(perguntaEdicao);
                    Toast.makeText(TelaListaPerguntas.this, "Pergunta Atualizada...", Toast.LENGTH_LONG).show();
                }

                perguntaEdicao = new PerguntasMODClasse();
                alerta.dismiss();
                atualizaLista();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cadastro de Pergunta");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();
    }
}