package Arvore_Rubro_Negro;

public class No_RubroNegro {
    static final boolean VERMELHO = true;
    static final boolean PRETO = false;

    int valor;
    boolean cor;
    No_RubroNegro esquerda;
    No_RubroNegro direita;
    No_RubroNegro pai;

    public No_RubroNegro(int valor) {
        this.valor = valor;
        this.cor = VERMELHO; // novos nós nascem vermelhos
    }
}
