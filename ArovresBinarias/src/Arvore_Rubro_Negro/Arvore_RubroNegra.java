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

    // ---------- BUSCA ----------
    public No_RubroNegro buscar(int valor) {
        return buscar(raiz, valor);
    }

    private No_RubroNegro buscar(No_RubroNegro no, int valor) {
        if (no == nil || no.valor == valor) return no;
        if (valor < no.valor) return buscar(no.esquerda, valor);
        return buscar(no.direita, valor);
    }

    // ---------- REMOÇÃO ----------
    public void remover(int valor) {
        No_RubroNegro z = buscar(raiz, valor);
        if (z == nil) return; // valor não encontrado
        remover(z);
    }

    private void remover(No_RubroNegro z) {
        No_RubroNegro y = z;
        No_RubroNegro x;
        boolean corOriginalY = y.cor;

        if (z.esquerda == nil) {
            // Caso 1: sem filho esquerdo
            x = z.direita;
            transplantar(z, z.direita);
        } else if (z.direita == nil) {
            // Caso 2: sem filho direito
            x = z.esquerda;
            transplantar(z, z.esquerda);
        } else {
            // Caso 3: dois filhos — substitui pelo sucessor (mínimo da subárvore direita)
            y = minimo(z.direita);
            corOriginalY = y.cor;
            x = y.direita;
            if (y.pai == z) {
                x.pai = y;
            } else {
                transplantar(y, y.direita);
                y.direita = z.direita;
                y.direita.pai = y;
            }
            transplantar(z, y);
            y.esquerda = z.esquerda;
            y.esquerda.pai = y;
            y.cor = z.cor;
        }
        // Se a cor removida era preta, pode ter violado as propriedades
        if (corOriginalY == No_RubroNegro.PRETO) {
            removerFixup(x);
        }
    }

    // Substitui a subárvore de u pela subárvore de v
    private void transplantar(No_RubroNegro u, No_RubroNegro v) {
        if (u.pai == nil) {
            raiz = v;
        } else if (u == u.pai.esquerda) {
            u.pai.esquerda = v;
        } else {
            u.pai.direita = v;
        }
        v.pai = u.pai;
    }

    // Retorna o nó de menor valor a partir de um nó
    private No_RubroNegro minimo(No_RubroNegro no) {
        while (no.esquerda != nil) {
            no = no.esquerda;
        }
        return no;
    }

    // ---------- CORREÇÃO DE VIOLAÇÕES DA REMOÇÃO ----------
    private void removerFixup(No_RubroNegro x) {
        while (x != raiz && x.cor == No_RubroNegro.PRETO) {
            if (x == x.pai.esquerda) {
                No_RubroNegro irmao = x.pai.direita;

                // Caso 1: irmão vermelho → recolore e rotaciona
                if (irmao.cor == No_RubroNegro.VERMELHO) {
                    irmao.cor = No_RubroNegro.PRETO;
                    x.pai.cor = No_RubroNegro.VERMELHO;
                    rotacaoEsquerda(x.pai);
                    irmao = x.pai.direita;
                }

                // Caso 2: irmão preto com filhos pretos → recolore irmão
                if (irmao.esquerda.cor == No_RubroNegro.PRETO &&
                        irmao.direita.cor == No_RubroNegro.PRETO) {
                    irmao.cor = No_RubroNegro.VERMELHO;
                    x = x.pai; // problema sobe
                } else {
                    // Caso 3: irmão preto, filho direito preto → rotaciona irmão
                    if (irmao.direita.cor == No_RubroNegro.PRETO) {
                        irmao.esquerda.cor = No_RubroNegro.PRETO;
                        irmao.cor = No_RubroNegro.VERMELHO;
                        rotacaoDireita(irmao);
                        irmao = x.pai.direita;
                    }
                    // Caso 4: irmão preto, filho direito vermelho → rotaciona pai
                    irmao.cor = x.pai.cor;
                    x.pai.cor = No_RubroNegro.PRETO;
                    irmao.direita.cor = No_RubroNegro.PRETO;
                    rotacaoEsquerda(x.pai);
                    x = raiz; // encerra o loop
                }
            } else {
                // Lógica simétrica (x é filho direito)
                No_RubroNegro irmao = x.pai.esquerda;

                if (irmao.cor == No_RubroNegro.VERMELHO) {
                    irmao.cor = No_RubroNegro.PRETO;
                    x.pai.cor = No_RubroNegro.VERMELHO;
                    rotacaoDireita(x.pai);
                    irmao = x.pai.esquerda;
                }

                if (irmao.direita.cor == No_RubroNegro.PRETO &&
                        irmao.esquerda.cor == No_RubroNegro.PRETO) {
                    irmao.cor = No_RubroNegro.VERMELHO;
                    x = x.pai;
                } else {
                    if (irmao.esquerda.cor == No_RubroNegro.PRETO) {
                        irmao.direita.cor = No_RubroNegro.PRETO;
                        irmao.cor = No_RubroNegro.VERMELHO;
                        rotacaoEsquerda(irmao);
                        irmao = x.pai.esquerda;
                    }
                    irmao.cor = x.pai.cor;
                    x.pai.cor = No_RubroNegro.PRETO;
                    irmao.esquerda.cor = No_RubroNegro.PRETO;
                    rotacaoDireita(x.pai);
                    x = raiz;
                }
            }
        }
        x.cor = No_RubroNegro.PRETO; // garante que a raiz (ou x) fique preta
    }
}
