package ArvoreAVL;

public class Arvore_AVL {
    private No_AVL raiz;
    public void atualizarAltura(No_AVL no_avl) {
        int alturaEsquerda = no_avl.Obter_altura(no_avl.getEsquerda());
        int alturaDireita = no_avl.Obter_altura(no_avl.getDireita());
        no_avl.setAltura(1+ Math.max(alturaEsquerda, alturaDireita));
    }
    public No_AVL Rotacao_Simples_Direita(No_AVL no_avl) {
        No_AVL auxiliar = no_avl.getEsquerda();
        no_avl.setEsquerda(no_avl.getEsquerda().getDireita());
        auxiliar.setDireita(no_avl);
        atualizarAltura(no_avl);
        atualizarAltura(auxiliar);
        return auxiliar;
    }
    public No_AVL Rotacao_Simples_Esquerda(No_AVL no_avl) {
        No_AVL auxiliar = no_avl.getDireita();
        no_avl.setDireita(no_avl.getDireita().getEsquerda());
        auxiliar.setEsquerda(no_avl);
        atualizarAltura(no_avl);
        atualizarAltura(auxiliar);
        return auxiliar;
    }
    public No_AVL Rotacao_Esquerda_Direita(No_AVL no_avl) {
        no_avl.setEsquerda(Rotacao_Simples_Esquerda(no_avl.getEsquerda()));
        return Rotacao_Simples_Direita(no_avl);
    }
    public No_AVL Rotacao_Dreita_Esquerda(No_AVL no_avl) {
        no_avl.setDireita(Rotacao_Simples_Direita(no_avl.getDireita()));
        return Rotacao_Simples_Esquerda(no_avl);
    }
    public No_AVL Inserir(No_AVL no_avl,int valor) {
        if (no_avl == null) {
            return new No_AVL(valor);
        }
        if (valor < no_avl.getValor()) {
            no_avl.setEsquerda(Inserir(no_avl.getEsquerda(), valor));
        }
        if (valor > no_avl.getValor()) {
            no_avl.setDireita(Inserir(no_avl.getDireita(), valor));
        }
        atualizarAltura(no_avl);
        int fator_balanceamento = no_avl.Calcular_Fator_de_balanceamento(no_avl);
        int fator_balnceamento_filho;
        if (fator_balanceamento > 1) {
            fator_balnceamento_filho = no_avl.Calcular_Fator_de_balanceamento(no_avl.getEsquerda());
            if (fator_balnceamento_filho > 0) {
                return Rotacao_Simples_Direita(no_avl);
            }
            if (fator_balnceamento_filho < 0) {
                return Rotacao_Esquerda_Direita(no_avl);
            }
        }
        if (fator_balanceamento < -1) {
            fator_balnceamento_filho = no_avl.Calcular_Fator_de_balanceamento(no_avl.getDireita());
            if (fator_balnceamento_filho < 0) {
                return Rotacao_Simples_Esquerda(no_avl);
            }
            if (fator_balnceamento_filho > 0) {
                return Rotacao_Dreita_Esquerda(no_avl);
            }
        }
        return no_avl;
    }
    public No_AVL Buscar(No_AVL no_avl,int valor) {
        if (no_avl == null) {
            return null;
        }
        if (valor < no_avl.getValor()) {
            return Buscar(no_avl.getEsquerda(), valor);
        }
        if (valor > no_avl.getValor()) {
            return Buscar(no_avl.getDireita(), valor);
        }
        if (valor == no_avl.getValor()) {
            return no_avl;
        }
        return null;
    }

    public No_AVL buscar(int valor) {
        return Buscar(raiz, valor);
    }

    public No_AVL getRaiz() {
        return raiz;
    }

    public void setRaiz(No_AVL raiz) {
        this.raiz = raiz;
    }

    public void inserir(int valor) {
        raiz = Inserir(raiz,valor);
    }
}
