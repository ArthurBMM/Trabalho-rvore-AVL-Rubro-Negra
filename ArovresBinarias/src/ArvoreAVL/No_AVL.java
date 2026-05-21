package ArvoreAVL;

public class No_AVL {
    int valor;
    No_AVL esquerda;
    No_AVL direito;
    int altura;

    public int Obter_altura(No_AVL noAvl){
        if (noAvl == null){
            return -1;
        }
        return noAvl.altura;
    }
    public int Calcular_Fator_de_balanceamento(No_AVL noAvl){
        if (noAvl == null){
            return 0;
        }
        return Obter_altura(noAvl.esquerda) - Obter_altura(noAvl.direito);
    }

}
