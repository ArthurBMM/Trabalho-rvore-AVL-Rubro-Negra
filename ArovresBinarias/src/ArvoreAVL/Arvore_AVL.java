package ArvoreAVL;

public class Arvore_AVL {
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
}
