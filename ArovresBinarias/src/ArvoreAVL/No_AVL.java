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

    public No_AVL(int valor) {
        this.valor = valor;
        this.esquerda = null;
        this.direito = null;
        this.altura = 1;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public No_AVL getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(No_AVL esquerda) {
        this.esquerda = esquerda;
    }

    public No_AVL getDireito() {
        return direito;
    }

    public void setDireito(No_AVL direito) {
        this.direito = direito;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
