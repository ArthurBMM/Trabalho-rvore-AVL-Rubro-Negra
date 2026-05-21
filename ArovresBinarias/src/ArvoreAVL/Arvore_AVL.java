package ArvoreAVL;

public class Arvore_AVL {
    public void atualizarAltura(No_AVL no_avl) {
        int alturaEsquerda = no_avl.Obter_altura(no_avl.esquerda);
        int alturaDireita = no_avl.Obter_altura(no_avl.direita);
        no_avl.setAltura(1+ Math.max(alturaEsquerda, alturaDireita));
    }
    public No_AVL Rotacao_Simples_Direita(No_AVL no_avl) {
        No_AVL auxiliar = no_avl.esquerda;
        no_avl.esquerda = no_avl.esquerda.direita;
        auxiliar.direita = no_avl;
        atualizarAltura(no_avl);
        atualizarAltura(auxiliar);
        return auxiliar;
    }
}
