package ArvoreAVL;

public class Arvore_AVL {
    private No_AVL raiz; // Raiz da árvore, ponto de entrada para todas as operações

    private int rotacoesInsercao = 0;
    private int rotacoesRemocao  = 0;
    private boolean emInsercao   = false;

    // Recalcula e atualiza a altura de um nó com base na altura dos seus filhos
    public void atualizarAltura(No_AVL no_avl) {
        int alturaEsquerda = no_avl.Obter_altura(no_avl.getEsquerda());
        int alturaDireita = no_avl.Obter_altura(no_avl.getDireita());
        no_avl.setAltura(1 + Math.max(alturaEsquerda, alturaDireita));
    }

    // Rotação simples à direita — corrige desbalanceamento LL
    // O filho esquerdo (auxiliar) sobe e o nó atual desce para a direita
    public No_AVL Rotacao_Simples_Direita(No_AVL no_avl) {
        if (emInsercao) rotacoesInsercao++; else rotacoesRemocao++;
        No_AVL auxiliar = no_avl.getEsquerda();
        no_avl.setEsquerda(no_avl.getEsquerda().getDireita()); // filho direito de auxiliar vai pra esquerda de no_avl
        auxiliar.setDireita(no_avl);                           // no_avl desce pra direita de auxiliar
        atualizarAltura(no_avl);                               // atualiza y primeiro (agora filho)
        atualizarAltura(auxiliar);                             // depois x (agora raiz)
        return auxiliar;                                       // retorna nova raiz
    }

    // Rotação simples à esquerda — corrige desbalanceamento RR
    // O filho direito (auxiliar) sobe e o nó atual desce para a esquerda
    public No_AVL Rotacao_Simples_Esquerda(No_AVL no_avl) {
        if (emInsercao) rotacoesInsercao++; else rotacoesRemocao++;
        No_AVL auxiliar = no_avl.getDireita();
        no_avl.setDireita(no_avl.getDireita().getEsquerda()); // filho esquerdo de auxiliar vai pra direita de no_avl
        auxiliar.setEsquerda(no_avl);                         // no_avl desce pra esquerda de auxiliar
        atualizarAltura(no_avl);                              // atualiza y primeiro (agora filho)
        atualizarAltura(auxiliar);                            // depois x (agora raiz)
        return auxiliar;                                      // retorna nova raiz
    }

    // Rotação dupla esquerda-direita — corrige desbalanceamento LR
    // Primeiro rotaciona o filho esquerdo à esquerda, depois rotaciona o nó à direita
    public No_AVL Rotacao_Esquerda_Direita(No_AVL no_avl) {
        no_avl.setEsquerda(Rotacao_Simples_Esquerda(no_avl.getEsquerda()));
        return Rotacao_Simples_Direita(no_avl);
    }

    // Rotação dupla direita-esquerda — corrige desbalanceamento RL
    // Primeiro rotaciona o filho direito à direita, depois rotaciona o nó à esquerda
    public No_AVL Rotacao_Dreita_Esquerda(No_AVL no_avl) {
        no_avl.setDireita(Rotacao_Simples_Direita(no_avl.getDireita()));
        return Rotacao_Simples_Esquerda(no_avl);
    }

    // Inserção recursiva — insere como BST e rebalanceia na volta da recursão
    public No_AVL Inserir(No_AVL no_avl, int valor) {
        // Caso base: chegou em um nó nulo, cria e retorna o novo nó
        if (no_avl == null) {
            return new No_AVL(valor);
        }

        // Desce para a subárvore correta
        if (valor < no_avl.getValor()) {
            no_avl.setEsquerda(Inserir(no_avl.getEsquerda(), valor));
        }
        if (valor > no_avl.getValor()) {
            no_avl.setDireita(Inserir(no_avl.getDireita(), valor));
        }
        // Valores iguais são ignorados (sem duplicatas)

        // Atualiza a altura do nó atual após a inserção
        atualizarAltura(no_avl);

        // Verifica se o nó ficou desbalanceado
        int fator_balanceamento = no_avl.Calcular_Fator_de_balanceamento(no_avl);
        int fator_balnceamento_filho;

        // Desbalanceado à esquerda (LL ou LR)
        if (fator_balanceamento > 1) {
            fator_balnceamento_filho = no_avl.Calcular_Fator_de_balanceamento(no_avl.getEsquerda());
            if (fator_balnceamento_filho > 0) return Rotacao_Simples_Direita(no_avl);  // caso LL
            if (fator_balnceamento_filho < 0) return Rotacao_Esquerda_Direita(no_avl); // caso LR
        }

        // Desbalanceado à direita (RR ou RL)
        if (fator_balanceamento < -1) {
            fator_balnceamento_filho = no_avl.Calcular_Fator_de_balanceamento(no_avl.getDireita());
            if (fator_balnceamento_filho < 0) return Rotacao_Simples_Esquerda(no_avl);  // caso RR
            if (fator_balnceamento_filho > 0) return Rotacao_Dreita_Esquerda(no_avl);   // caso RL
        }

        return no_avl; // nó balanceado, retorna sem alteração
    }

    // Busca recursiva — retorna o nó com o valor buscado ou null se não encontrar
    public No_AVL Buscar(No_AVL no_avl, int valor) {
        if (no_avl == null) return null;                              // não encontrado
        if (valor < no_avl.getValor()) return Buscar(no_avl.getEsquerda(), valor); // busca à esquerda
        if (valor > no_avl.getValor()) return Buscar(no_avl.getDireita(), valor);  // busca à direita
        if (valor == no_avl.getValor()) return no_avl;               // encontrado!
        return null;
    }

    // Remoção recursiva — remove o nó, ajusta a árvore e rebalanceia
    public No_AVL Remover(No_AVL no_avl, int valor) {
        if (no_avl == null) return null; // valor não encontrado

        // Desce para a subárvore correta
        if (valor < no_avl.getValor()) {
            no_avl.setEsquerda(Remover(no_avl.getEsquerda(), valor));
        } else if (valor > no_avl.getValor()) {
            no_avl.setDireita(Remover(no_avl.getDireita(), valor));
        } else {
            // Nó encontrado — trata os 3 casos de remoção

            // Caso 1 e 2: nenhum ou apenas um filho
            if (no_avl.getEsquerda() == null || no_avl.getDireita() == null) {
                No_AVL auxiliar = (no_avl.getEsquerda() != null)
                        ? no_avl.getEsquerda()
                        : no_avl.getDireita(); // null se folha, filho se tiver um
                no_avl = auxiliar;
            } else {
                // Caso 3: dois filhos
                // Substitui pelo sucessor (menor nó da subárvore direita)
                No_AVL sucessor = no_avl.getDireita();
                while (sucessor.getEsquerda() != null) {
                    sucessor = sucessor.getEsquerda();
                }
                no_avl.setValor(sucessor.getValor());                          // copia valor do sucessor
                no_avl.setDireita(Remover(no_avl.getDireita(), sucessor.getValor())); // remove o sucessor
            }
        }

        if (no_avl == null) return null; // era folha, retorna nulo

        // Atualiza altura e rebalanceia após remoção
        atualizarAltura(no_avl);
        int fb = no_avl.Calcular_Fator_de_balanceamento(no_avl);

        if (fb > 1) { // desbalanceado à esquerda
            if (no_avl.Calcular_Fator_de_balanceamento(no_avl.getEsquerda()) >= 0)
                return Rotacao_Simples_Direita(no_avl);  // caso LL
            return Rotacao_Esquerda_Direita(no_avl);     // caso LR
        }
        if (fb < -1) { // desbalanceado à direita
            if (no_avl.Calcular_Fator_de_balanceamento(no_avl.getDireita()) <= 0)
                return Rotacao_Simples_Esquerda(no_avl); // caso RR
            return Rotacao_Dreita_Esquerda(no_avl);      // caso RL
        }

        return no_avl;
    }

    // Métodos públicos — interface de uso da árvore
    public void inserir(int valor)        { emInsercao = true;  raiz = Inserir(raiz, valor); }
    public void remover(int valor)        { emInsercao = false; raiz = Remover(raiz, valor); }
    public No_AVL buscar(int valor)       { return Buscar(raiz, valor); }
    public No_AVL getRaiz()               { return raiz; }
    public void setRaiz(No_AVL raiz)      { this.raiz = raiz; }

    public int getRotacoesInsercao()      { return rotacoesInsercao; }
    public int getRotacoesRemocao()       { return rotacoesRemocao; }
    public void resetContadores()         { rotacoesInsercao = 0; rotacoesRemocao = 0; }
}