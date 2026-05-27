package Arvore_Rubro_Negro;

public class Arvore_RubroNegra {
    private No_RubroNegro raiz;
    private final No_RubroNegro nil; // sentinela: representa toda folha NIL (preta)

    public Arvore_RubroNegra() {
        nil = new No_RubroNegro(0);
        nil.cor = No_RubroNegro.PRETO;
        raiz = nil;
    }

    // ---------- ROTAÇÕES ----------
    private void rotacaoEsquerda(No_RubroNegro x) {
        No_RubroNegro y = x.direita;
        x.direita = y.esquerda;
        if (y.esquerda != nil) {
            y.esquerda.pai = x;
        }
        y.pai = x.pai;
        if (x.pai == nil) {
            raiz = y;
        } else if (x == x.pai.esquerda) {
            x.pai.esquerda = y;
        } else {
            x.pai.direita = y;
        }
        y.esquerda = x;
        x.pai = y;
    }
    private void rotacaoDireita(No_RubroNegro x) { // espelho da esquerda
        No_RubroNegro y = x.esquerda;
        x.esquerda = y.direita;
        if (y.direita != nil) {
            y.direita.pai = x;
        }
        y.pai = x.pai;
        if (x.pai == nil) {
            raiz = y;
        } else if (x == x.pai.direita) {
            x.pai.direita = y;
        } else {
            x.pai.esquerda = y;
        }
        y.direita = x;
        x.pai = y;
    }



}
