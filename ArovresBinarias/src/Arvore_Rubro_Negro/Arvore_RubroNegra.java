package Arvore_Rubro_Negro;

public class Arvore_RubroNegra {
    private No_RubroNegro raiz;
    private final No_RubroNegro nil; // sentinela: representa toda folha NIL (preta)

    public Arvore_RubroNegra() {
        nil = new No_RubroNegro(0);
        nil.cor = No_RubroNegro.PRETO;
        raiz = nil;
    }
}
