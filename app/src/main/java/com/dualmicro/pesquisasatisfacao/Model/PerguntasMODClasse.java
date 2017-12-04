package com.dualmicro.pesquisasatisfacao.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by doalc on 03/12/2017.
 */

public class PerguntasMODClasse implements Serializable  {
    private int codigoPergunta = 0;
    private String pergunta;
    private Integer status;
    private List<Integer> respostas;

    public List<Integer> getRespostas() {
        return respostas;
    }

    public int getTotalRespostas(int respostas) {
        int count = 0;

        for (Integer n : this.respostas)
            if (n == respostas)
                count++;

        return count;
    }

    public void setRespostas(List<Integer> respostas) {
        this.respostas = respostas;
    }

    public void setRespostas(int respostas) {
        this.respostas.add(respostas);
    }


    public PerguntasMODClasse() {
    }

    public PerguntasMODClasse(int codigoPergunta, String pergunta, Integer status) {
        this.codigoPergunta = codigoPergunta;
        this.pergunta = pergunta;
        this.status = status;
    }

    public int getCodigoPergunta() {
        return codigoPergunta;
    }

    public void setCodigoPergunta(int codigoPergunta) {
        this.codigoPergunta = codigoPergunta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%03d - %s", this.codigoPergunta, this.pergunta);
    }
}
