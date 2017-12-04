package com.dualmicro.pesquisasatisfacao.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dualmicro.pesquisasatisfacao.Model.PerguntasMODClasse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doalc on 03/12/2017.
 */

public class PesquisaSatisfacaoDB extends SQLiteOpenHelper {

    SQLiteDatabase dbGeral;
    static final int versao = 1;

    public PesquisaSatisfacaoDB(Context context) {
        super(context, "SurveyDB", null, versao);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlPergunta = "";
        sqlPergunta = "CREATE TABLE IF NOT EXISTS TB_PERGUNTAS( ";
        sqlPergunta += "id INTEGER PRIMARY KEY,";
        sqlPergunta += "pergunta TEXT,";
        sqlPergunta += "status INTEGER );";
        db.execSQL(sqlPergunta);

        String sqlResposta = "";
        sqlResposta = "CREATE TABLE IF NOT EXISTS TB_RESPOSTAS( ";
        sqlResposta += "id INTEGER PRIMARY KEY,";
        sqlResposta += "pergunta INTEGER,";
        sqlResposta += "resposta INTEGER );";
        db.execSQL(sqlResposta);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public List<PerguntasMODClasse> buscaTodasRespostas() {
        dbGeral = getReadableDatabase();

        String cmdSQL = "SELECT * FROM TB_PERGUNTAS";
        Cursor cursor = dbGeral.rawQuery(cmdSQL, null);

        List<PerguntasMODClasse> lstPerguntas = new ArrayList<PerguntasMODClasse>();

        while (cursor.moveToNext()) {
            lstPerguntas.add(new PerguntasMODClasse(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("pergunta")),
                    cursor.getInt(cursor.getColumnIndex("status"))
            ));
        }

        return lstPerguntas;
    }

    public List<Integer> buscaRespostas(int pergunta) {
        dbGeral = getReadableDatabase();

        Cursor cursor = dbGeral.query("TB_RESPOSTAS",new String[]{"pergunta","resposta"}
                   ," resposta = ?",new String[]{ String.valueOf(pergunta)},null,null,null,null);

        List<Integer> lstRespotas = new ArrayList<Integer>();

        while (cursor.moveToNext()) {
            lstRespotas.add(
                    cursor.getInt(cursor.getColumnIndex("resposta"))
            );
        }

        return lstRespotas;
    }

    private ContentValues AtribuiValoresPerguntas(PerguntasMODClasse pergunta) {
        ContentValues values = new ContentValues();

        values.put("pergunta", pergunta.getPergunta());
        values.put("status", pergunta.getStatus());

        return values;
    }

    private ContentValues AtribuiValoresRespostas(int pergunta, int resposta) {
        ContentValues values = new ContentValues();

        values.put("pergunta", pergunta);
        values.put("resposta", resposta);

        return values;
    }

    public void inserirPergunta(PerguntasMODClasse pergunta) {
        //MEtodo que indica ao banco escrever no banco de dados.
        dbGeral = getWritableDatabase();

        dbGeral.insert("TB_PERGUNTAS", null, AtribuiValoresPerguntas(pergunta));
    }

    public void inserirResposta(int pergunta, int resposta) {
        //MEtodo que indica ao banco escrever no banco de dados.
        dbGeral = getWritableDatabase();
        dbGeral.insert("TB_RESPOSTAS", null, AtribuiValoresRespostas(pergunta, resposta));
    }

    //MEtodo que indica ao banco escrever no banco de dados.
    public void atualizaPergunta(PerguntasMODClasse pergunta) {

        dbGeral = getWritableDatabase();
        String where = " id = ?";
        String[] parametros = {String.valueOf(pergunta.getCodigoPergunta())};

        dbGeral.update("TB_PERGUNTAS", AtribuiValoresPerguntas(pergunta), where, parametros);
    }

    public void excluirPergunta(PerguntasMODClasse pergunta) {

        dbGeral = getWritableDatabase();
        String where = " id = ?";
        String[] parametros = {String.valueOf(pergunta.getCodigoPergunta())};

        dbGeral.delete("TB_PERGUNTAS", where, parametros);
    }

    public void excluirResposta(int pergunta) {

        dbGeral = getWritableDatabase();
        String where = " pergunta = ?";
        String[] parametros = {String.valueOf(pergunta)};

        dbGeral.delete("TB_RESPOSTAS", where, parametros);
    }

}