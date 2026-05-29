package Arvore_Rubro_Negro;

public class Arvore_RubroNegra {
    private No_RubroNegro raiz;
    private final No_RubroNegro nil; // sentinela: representa toda folha NIL (preta)

    private int rotacoesInsercao = 0;
    private int rotacoesRemocao  = 0;
    private boolean emInsercao   = false;

    public Arvore_RubroNegra() {
        nil = new No_RubroNegro(0);
        nil.setCor(No_RubroNegro.PRETO);
        raiz = nil;
    }

    // Rotação simples à esquerda — filho direito (auxiliar) sobe, nó atual desce para a esquerda
    private void Rotacao_Simples_Esquerda(No_RubroNegro no) {
        if (emInsercao) rotacoesInsercao++; else rotacoesRemocao++;
        No_RubroNegro auxiliar = no.getDireita();
        no.setDireita(auxiliar.getEsquerda());
        if (auxiliar.getEsquerda() != nil) {
            auxiliar.getEsquerda().setPai(no);
        }
        auxiliar.setPai(no.getPai());
        if (no.getPai() == nil) {
            raiz = auxiliar;
        } else if (no == no.getPai().getEsquerda()) {
            no.getPai().setEsquerda(auxiliar);
        } else {
            no.getPai().setDireita(auxiliar);
        }
        auxiliar.setEsquerda(no);
        no.setPai(auxiliar);
    }

    // Rotação simples à direita — filho esquerdo (auxiliar) sobe, nó atual desce para a direita
    private void Rotacao_Simples_Direita(No_RubroNegro no) {
        if (emInsercao) rotacoesInsercao++; else rotacoesRemocao++;
        No_RubroNegro auxiliar = no.getEsquerda();
        no.setEsquerda(auxiliar.getDireita());
        if (auxiliar.getDireita() != nil) {
            auxiliar.getDireita().setPai(no);
        }
        auxiliar.setPai(no.getPai());
        if (no.getPai() == nil) {
            raiz = auxiliar;
        } else if (no == no.getPai().getDireita()) {
            no.getPai().setDireita(auxiliar);
        } else {
            no.getPai().setEsquerda(auxiliar);
        }
        auxiliar.setDireita(no);
        no.setPai(auxiliar);
    }

    // Inserção iterativa como BST e correção das violações rubro-negras
    private void inserir_interno(int valor) {
        No_RubroNegro no_novo = new No_RubroNegro(valor);
        no_novo.setEsquerda(nil);
        no_novo.setDireita(nil);

        No_RubroNegro pai = nil;
        No_RubroNegro atual = raiz;

        // Desce como numa BST normal
        while (atual != nil) {
            pai = atual;
            if (no_novo.getValor() < atual.getValor()) {
                atual = atual.getEsquerda();
            } else {
                atual = atual.getDireita();
            }
        }
        no_novo.setPai(pai);
        if (pai == nil) {
            raiz = no_novo;                         // árvore estava vazia
        } else if (no_novo.getValor() < pai.getValor()) {
            pai.setEsquerda(no_novo);
        } else {
            pai.setDireita(no_novo);
        }
        Inserir_Fixup(no_novo);                     // corrige as violações
    }

    // Corrige violações das propriedades rubro-negras após inserção
    private void Inserir_Fixup(No_RubroNegro no) {
        while (no.getPai().getCor() == No_RubroNegro.VERMELHO) {
            if (no.getPai() == no.getPai().getPai().getEsquerda()) {
                No_RubroNegro tio = no.getPai().getPai().getDireita();
                if (tio.getCor() == No_RubroNegro.VERMELHO) {
                    // Caso 1: tio vermelho → recoloração
                    no.getPai().setCor(No_RubroNegro.PRETO);
                    tio.setCor(No_RubroNegro.PRETO);
                    no.getPai().getPai().setCor(No_RubroNegro.VERMELHO);
                    no = no.getPai().getPai();      // problema sobe para o avô
                } else {
                    if (no == no.getPai().getDireita()) {
                        // Caso 2: triângulo → rotaciona o pai
                        no = no.getPai();
                        Rotacao_Simples_Esquerda(no);
                    }
                    // Caso 3: reta → rotaciona o avô + recolore
                    no.getPai().setCor(No_RubroNegro.PRETO);
                    no.getPai().getPai().setCor(No_RubroNegro.VERMELHO);
                    Rotacao_Simples_Direita(no.getPai().getPai());
                }
            } else {
                // Lógica simétrica (pai é filho direito do avô)
                No_RubroNegro tio = no.getPai().getPai().getEsquerda();
                if (tio.getCor() == No_RubroNegro.VERMELHO) {
                    no.getPai().setCor(No_RubroNegro.PRETO);
                    tio.setCor(No_RubroNegro.PRETO);
                    no.getPai().getPai().setCor(No_RubroNegro.VERMELHO);
                    no = no.getPai().getPai();
                } else {
                    if (no == no.getPai().getEsquerda()) {
                        no = no.getPai();
                        Rotacao_Simples_Direita(no);
                    }
                    no.getPai().setCor(No_RubroNegro.PRETO);
                    no.getPai().getPai().setCor(No_RubroNegro.VERMELHO);
                    Rotacao_Simples_Esquerda(no.getPai().getPai());
                }
            }
        }
        raiz.setCor(No_RubroNegro.PRETO);          // garante raiz preta
    }

    // Busca recursiva — retorna o nó com o valor buscado ou nil se não encontrar
    private No_RubroNegro Buscar(No_RubroNegro no, int valor) {
        if (no == nil || no.getValor() == valor) return no;
        if (valor < no.getValor()) return Buscar(no.getEsquerda(), valor);
        return Buscar(no.getDireita(), valor);
    }

    // Remoção do nó com o valor informado e correção das violações rubro-negras
    private void Remover(No_RubroNegro no) {
        No_RubroNegro sucessor = no;
        No_RubroNegro substituto;
        boolean cor_original = sucessor.getCor();

        if (no.getEsquerda() == nil) {
            // Caso 1: sem filho esquerdo
            substituto = no.getDireita();
            Transplantar(no, no.getDireita());
        } else if (no.getDireita() == nil) {
            // Caso 2: sem filho direito
            substituto = no.getEsquerda();
            Transplantar(no, no.getEsquerda());
        } else {
            // Caso 3: dois filhos — substitui pelo sucessor (mínimo da subárvore direita)
            sucessor = Minimo(no.getDireita());
            cor_original = sucessor.getCor();
            substituto = sucessor.getDireita();
            if (sucessor.getPai() == no) {
                substituto.setPai(sucessor);
            } else {
                Transplantar(sucessor, sucessor.getDireita());
                sucessor.setDireita(no.getDireita());
                sucessor.getDireita().setPai(sucessor);
            }
            Transplantar(no, sucessor);
            sucessor.setEsquerda(no.getEsquerda());
            sucessor.getEsquerda().setPai(sucessor);
            sucessor.setCor(no.getCor());
        }
        // Se a cor removida era preta, pode ter violado as propriedades
        if (cor_original == No_RubroNegro.PRETO) {
            Remover_Fixup(substituto);
        }
    }

    // Substitui a subárvore de no_removido pela subárvore de substituto
    private void Transplantar(No_RubroNegro no_removido, No_RubroNegro substituto) {
        if (no_removido.getPai() == nil) {
            raiz = substituto;
        } else if (no_removido == no_removido.getPai().getEsquerda()) {
            no_removido.getPai().setEsquerda(substituto);
        } else {
            no_removido.getPai().setDireita(substituto);
        }
        substituto.setPai(no_removido.getPai());
    }

    // Retorna o nó de menor valor a partir de um nó
    private No_RubroNegro Minimo(No_RubroNegro no) {
        while (no.getEsquerda() != nil) {
            no = no.getEsquerda();
        }
        return no;
    }

    // Corrige violações das propriedades rubro-negras após remoção
    private void Remover_Fixup(No_RubroNegro no) {
        while (no != raiz && no.getCor() == No_RubroNegro.PRETO) {
            if (no == no.getPai().getEsquerda()) {
                No_RubroNegro irmao = no.getPai().getDireita();

                // Caso 1: irmão vermelho → recolore e rotaciona
                if (irmao.getCor() == No_RubroNegro.VERMELHO) {
                    irmao.setCor(No_RubroNegro.PRETO);
                    no.getPai().setCor(No_RubroNegro.VERMELHO);
                    Rotacao_Simples_Esquerda(no.getPai());
                    irmao = no.getPai().getDireita();
                }

                // Caso 2: irmão preto com filhos pretos → recolore irmão
                if (irmao.getEsquerda().getCor() == No_RubroNegro.PRETO &&
                        irmao.getDireita().getCor() == No_RubroNegro.PRETO) {
                    irmao.setCor(No_RubroNegro.VERMELHO);
                    no = no.getPai();               // problema sobe
                } else {
                    // Caso 3: irmão preto, filho direito preto → rotaciona irmão
                    if (irmao.getDireita().getCor() == No_RubroNegro.PRETO) {
                        irmao.getEsquerda().setCor(No_RubroNegro.PRETO);
                        irmao.setCor(No_RubroNegro.VERMELHO);
                        Rotacao_Simples_Direita(irmao);
                        irmao = no.getPai().getDireita();
                    }
                    // Caso 4: irmão preto, filho direito vermelho → rotaciona pai
                    irmao.setCor(no.getPai().getCor());
                    no.getPai().setCor(No_RubroNegro.PRETO);
                    irmao.getDireita().setCor(No_RubroNegro.PRETO);
                    Rotacao_Simples_Esquerda(no.getPai());
                    no = raiz;                      // encerra o loop
                }
            } else {
                // Lógica simétrica (no é filho direito)
                No_RubroNegro irmao = no.getPai().getEsquerda();

                if (irmao.getCor() == No_RubroNegro.VERMELHO) {
                    irmao.setCor(No_RubroNegro.PRETO);
                    no.getPai().setCor(No_RubroNegro.VERMELHO);
                    Rotacao_Simples_Direita(no.getPai());
                    irmao = no.getPai().getEsquerda();
                }

                if (irmao.getDireita().getCor() == No_RubroNegro.PRETO &&
                        irmao.getEsquerda().getCor() == No_RubroNegro.PRETO) {
                    irmao.setCor(No_RubroNegro.VERMELHO);
                    no = no.getPai();
                } else {
                    if (irmao.getEsquerda().getCor() == No_RubroNegro.PRETO) {
                        irmao.getDireita().setCor(No_RubroNegro.PRETO);
                        irmao.setCor(No_RubroNegro.VERMELHO);
                        Rotacao_Simples_Esquerda(irmao);
                        irmao = no.getPai().getEsquerda();
                    }
                    irmao.setCor(no.getPai().getCor());
                    no.getPai().setCor(No_RubroNegro.PRETO);
                    irmao.getEsquerda().setCor(No_RubroNegro.PRETO);
                    Rotacao_Simples_Direita(no.getPai());
                    no = raiz;
                }
            }
        }
        no.setCor(No_RubroNegro.PRETO);            // garante que a raiz (ou no) fique preta
    }

    // Métodos públicos — interface de uso da árvore
    public void inserir(int valor)                  { emInsercao = true;  inserir_interno(valor); }
    public void remover(int valor)                  { emInsercao = false; No_RubroNegro no = Buscar(raiz, valor); if (no != nil) Remover(no); }
    public No_RubroNegro buscar(int valor)          { return Buscar(raiz, valor); }
    public No_RubroNegro getRaiz()                  { return raiz; }
    public void setRaiz(No_RubroNegro raiz)         { this.raiz = raiz; }
    public No_RubroNegro getNil()                   { return nil; }

    public int getRotacoesInsercao()                { return rotacoesInsercao; }
    public int getRotacoesRemocao()                 { return rotacoesRemocao; }
    public void resetContadores()                   { rotacoesInsercao = 0; rotacoesRemocao = 0; }
}