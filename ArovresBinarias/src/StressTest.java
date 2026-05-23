import ArvoreAVL.Arvore_AVL;
import java.util.Random;
public class StressTest {
    // Configurações globais dos testes
    private static final long SEED = 42L;
    private static final int TOTAL = 100_000;

    // Volumes progressivos para o gráfico Volume x Tempo
    private static final int[] VOLUMES = {
            1_000, 5_000, 10_000, 25_000, 50_000, 75_000, 100_000
    };

    // Gera array aleatório com seed fixa (mesmo dado para AVL e RBT)
    private static int[] gerarDadosAleatorios(int tamanho) {
        Random rng = new Random(SEED);
        int[] dados = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            dados[i] = rng.nextInt(Integer.MAX_VALUE);
        }
        return dados;
    }

    // Gera array ordenado crescente (pior caso da BST)
    private static int[] gerarDadosOrdenados(int tamanho){
        int[] dados = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            dados[i] = i + 1;
        }
        return dados;
    }

    // Mede o tempo total de inserção de um array inteiro na arvore AVL
    private static long medirInsercao(Arvore_AVL arvore, int[] dados){
        long inicio = System.nanoTime();
        for (int v : dados) {
            arvore.inserir(v);
        }
        long fim = System.nanoTime();
        return fim - inicio;
    }

    // Ponto de entrada
    public static void main(String[] args ) {
            // testes serão chamados aqui nos próximos commits
    }
}

