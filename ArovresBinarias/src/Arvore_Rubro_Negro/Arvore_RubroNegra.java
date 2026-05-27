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

    // ---------- INSERÇÃO ----------
    public void inserir(int valor) {
        No_RubroNegro z = new No_RubroNegro(valor);
        z.esquerda = nil;
        z.direita = nil;

        No_RubroNegro y = nil;
        No_RubroNegro x = raiz;

        // Desce como numa BST normal
        while (x != nil) {
            y = x;
            if (z.valor < x.valor) {
                x = x.esquerda;
            } else {
                x = x.direita;
            }
        }
        z.pai = y;
        if (y == nil) {
            raiz = z;            // árvore estava vazia
        } else if (z.valor < y.valor) {
            y.esquerda = z;
        } else {
            y.direita = z;
        }
        inserirFixup(z); // corrige as violações
    }

    // ---------- CORREÇÃO DE VIOLAÇÕES NA INSERÇÃO ----------
    private void inserirFixup(No_RubroNegro z) {
        while (z.pai.cor == No_RubroNegro.VERMELHO) {
            if (z.pai == z.pai.pai.esquerda) {
                No_RubroNegro tio = z.pai.pai.direita;
                if (tio.cor == No_RubroNegro.VERMELHO) {
                    // Caso 1: tio vermelho → recoloração
                    z.pai.cor = No_RubroNegro.PRETO;
                    tio.cor = No_RubroNegro.PRETO;
                    z.pai.pai.cor = No_RubroNegro.VERMELHO;
                    z = z.pai.pai; // problema sobe para o avô
                } else {
                    if (z == z.pai.direita) {
                        // Caso 2: triângulo → rotaciona o pai
                        z = z.pai;
                        rotacaoEsquerda(z);
                    }
                    // Caso 3: reta → rotaciona o avô + recolore
                    z.pai.cor = No_RubroNegro.PRETO;
                    z.pai.pai.cor = No_RubroNegro.VERMELHO;
                    rotacaoDireita(z.pai.pai);
                }
            } else {
                // Lógica simétrica (pai é filho direito do avô)
                No_RubroNegro tio = z.pai.pai.esquerda;
                if (tio.cor == No_RubroNegro.VERMELHO) {
                    z.pai.cor = No_RubroNegro.PRETO;
                    tio.cor = No_RubroNegro.PRETO;
                    z.pai.pai.cor = No_RubroNegro.VERMELHO;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.esquerda) {
                        z = z.pai;
                        rotacaoDireita(z);
                    }
                    z.pai.cor = No_RubroNegro.PRETO;
                    z.pai.pai.cor = No_RubroNegro.VERMELHO;
                    rotacaoEsquerda(z.pai.pai);
                }
            }
        }
        raiz.cor = No_RubroNegro.PRETO; // garante raiz preta
    }



}
