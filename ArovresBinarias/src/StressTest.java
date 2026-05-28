import ArvoreAVL.Arvore_AVL;
import Arvore_Rubro_Negro.Arvore_RubroNegra;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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

    private static long medirInsercaoRBT(Arvore_RubroNegra arvore, int[] dados) {
        long inicio = System.nanoTime();
        for (int v : dados) {
            arvore.inserir(v);
        }
        long fim = System.nanoTime();
        return fim - inicio;
    }

    private static long medirBuscaRBT(Arvore_RubroNegra arvore, int[] dados) {
        long inicio = System.nanoTime();
        for (int v : dados) {
            arvore.buscar(v);
        }
        long fim = System.nanoTime();
        return fim - inicio;
    }

    private static long medirRemocaoRBT(Arvore_RubroNegra arvore, int[] dados) {
        int qtdRemover = dados.length / 5;
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

    // Escreve um array de inteiros em um arquivo txt, um número por linha
    private static void escreverArquivo(String caminho, int[] dados) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(caminho));
        for (int v : dados) {
            bw.write(v + "\n");
        }
        bw.close();
    }

    // Gera todos os arquivos de dados para cada volume
    private static void gerarArquivos() throws IOException {
        for (int volume : VOLUMES) {
            int[] aleatorios = gerarDadosAleatorios(volume);
            escreverArquivo("ArovresBinarias/Dados/dados_" + volume + ".txt", aleatorios);
        }
        System.out.println("Arquivos gerados com sucesso!");
    }

    // Lê um arquivo txt e retorna um array de inteiros
    private static int[] lerArquivo(String caminho) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        List<Integer> lista = new ArrayList<>();
        String linha;
        while ((linha = br.readLine()) != null) {
            lista.add(Integer.parseInt(linha.trim()));
        }
        br.close();
        return lista.stream().mapToInt(Integer::intValue).toArray();
    }

    // Ponto de entrada
    public static void main(String[] args) throws IOException {
        aquecer();
        // gerarArquivos() - como os arquivos ja foram criados retirei da main
        System.out.printf("%-15s | %-10s | %-20s | %-20s | %-20s%n",
                "Estrutura", "Volume", "Insercao (ns)", "Busca (ns)", "Remocao (ns)");
        System.out.println("-".repeat(95));

        for (int volume : VOLUMES) {
            int[] dados = lerArquivo("ArovresBinarias/Dados/dados_" + volume + ".txt");
            //Teste AVL
            Arvore_AVL avl = new Arvore_AVL();
            long insercao = medirInsercao(avl, dados);
            long busca    = medirBusca(avl, dados);
            long remocao  = medirRemocao(avl, dados);
            imprimirResultados("AVL", volume, insercao, busca, remocao);

            // Teste RBT com os mesmos dados
            Arvore_RubroNegra rbt = new Arvore_RubroNegra();
            long insercaoRbt = medirInsercaoRBT(rbt, dados);
            long buscaRbt    = medirBuscaRBT(rbt, dados);
            long remocaoRbt  = medirRemocaoRBT(rbt, dados);
            imprimirResultados("RBT", volume, insercaoRbt, buscaRbt, remocaoRbt);

            System.out.println();
        }
    }
}

