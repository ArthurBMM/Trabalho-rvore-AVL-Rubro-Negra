package ArvoreAVL;

public class Arvore_AVL {
    public void atualizarAltura(No_AVL no_avl) {
        int alturaEsquerda = no_avl.Obter_altura(no_avl.esquerda);
        int alturaDireita = no_avl.Obter_altura(no_avl.direita);
        no_avl.setAltura(1+ Math.max(alturaEsquerda, alturaDireita));
    }
}
