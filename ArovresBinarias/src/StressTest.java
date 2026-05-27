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

    // Mede o tempo total de busca de um array inteiro na arvore AVL
    private static long medirBusca(Arvore_AVL arvore, int[] dados) {
        long inicio = System.nanoTime();
        for (int v : dados) {
            arvore.buscar(v);
        }
        long fim = System.nanoTime();
        return fim - inicio;
    }

    // Mede o tempo de remoção de 20% dos nós da árvore AVL
    private static long medirRemocao(Arvore_AVL arvore, int[] dados) {
        int qtdRemover = dados.length / 5; // 20%
        long inicio = System.nanoTime();
        for (int i = 0; i < qtdRemover; i++) {
            arvore.remover(dados[i]);
        }
        long fim = System.nanoTime();
        return fim - inicio;
    }

    // Imprime os resultados em formato de tabela comparativa
    private static void imprimirResultados(String nomeEstrutura, int volume,
                                           long tempoInsercao, long tempoBusca,
                                           long tempoRemocao) {
        System.out.printf("%-15s | %-10d | %-20d | %-20d | %-20d%n",
                nomeEstrutura, volume, tempoInsercao, tempoBusca, tempoRemocao);
    }

    // Aquecimento da JVM (não entra na medição)
    private static void aquecer() {
        Arvore_AVL avl = new Arvore_AVL();
        int[] warmup = gerarDadosAleatorios(1_000);
        medirInsercao(avl, warmup);
        medirBusca(avl, warmup);
        medirRemocao(avl, warmup);
    }

    // Ponto de entrada
    public static void main(String[] args) {
        aquecer();
        System.out.printf("%-15s | %-10s | %-20s | %-20s | %-20s%n",
                "Estrutura", "Volume", "Insercao (ns)", "Busca (ns)", "Remocao (ns)");
        System.out.println("-".repeat(95));

        for (int volume : VOLUMES) {
            int[] dadosAleatorios = gerarDadosAleatorios(volume);
            int[] dadosOrdenados = gerarDadosOrdenados(volume);

            // Teste com dados aleatorios
            Arvore_AVL avlAleatorio = new Arvore_AVL();
            long insercao = medirInsercao(avlAleatorio, dadosAleatorios);
            long busca    = medirBusca(avlAleatorio, dadosAleatorios);
            long remocao  = medirRemocao(avlAleatorio, dadosAleatorios);
            imprimirResultados("AVL Aleatorio", volume, insercao, busca, remocao);

            // Testes com dados Ordenados (pior caso)
            Arvore_AVL avlOrdenado = new Arvore_AVL();
            long insercaoOrd       = medirInsercao(avlOrdenado, dadosOrdenados);
            long buscaOrd    = medirBusca(avlOrdenado, dadosOrdenados);
            long remocaoOrd  = medirRemocao(avlOrdenado, dadosOrdenados);
            imprimirResultados("AVL Ordenado", volume, insercaoOrd, buscaOrd, remocaoOrd);

            System.out.println();
        }
    }
}

