package Arvore_Rubro_Negro;

public class No_RubroNegro {
    static final boolean VERMELHO = true;
    static final boolean PRETO = false;

    private int valor;
    private boolean cor;
    private No_RubroNegro esquerda;
    private No_RubroNegro direita;
    private No_RubroNegro pai;

    public No_RubroNegro(int valor) {
        this.valor = valor;
        this.cor = VERMELHO; // novos nós nascem vermelhos
        this.esquerda = null;
        this.direita = null;
        this.pai = null;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean getCor() {
        return cor;
    }

    public void setCor(boolean cor) {
        this.cor = cor;
    }

    public No_RubroNegro getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(No_RubroNegro esquerda) {
        this.esquerda = esquerda;
    }

    public No_RubroNegro getDireita() {
        return direita;
    }

    public void setDireita(No_RubroNegro direita) {
        this.direita = direita;
    }

    public No_RubroNegro getPai() {
        return pai;
    }

    public void setPai(No_RubroNegro pai) {
        this.pai = pai;
    }
}