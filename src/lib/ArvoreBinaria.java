package lib;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;


public class ArvoreBinaria<T extends Comparable> implements IArvoreBinaria<T> {
    
    protected No<T> raiz = null;
    protected Comparator<T> comparador; 
    
    protected No<T> atual = null;
    private ArrayList<No<T>> pilhaNavegacao = null;

    public ArvoreBinaria(Comparator<T> comp) {
        comparador = comp;
    }
    

    @Override
    public void adicionar(T novoValor) {
        No<T> novoNo = new No<T>(novoValor);
        if (this.raiz == null){
            this.raiz = novoNo;
        }else{
            this.atual = this.raiz;
            while(true){
                if (novoNo.getValor().compareTo(atual.getValor()) == -1){
                    if (atual.getFilhoEsquerda() != null){
                        this.atual = atual.getFilhoEsquerda(); 
                    }else{
                        atual.setFilhoEsquerda(novoNo);
                        break;
                    }
                }else{
                    if (atual.getFilhoDireita() != null){
                        this.atual = atual.getFilhoDireita();
                    }else{
                        atual.setFilhoDireita(novoNo);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public T pesquisar(T valor) {
        return pesquisarRecursivamente(raiz, valor);
    }

    private T pesquisarRecursivamente(No<T> noAtual, T valor) {
        if (noAtual == null) {
            return null;
        }

        int comparacao = comparador.compare(valor, noAtual.getValor());

        if (comparacao == 0) {
            return noAtual.getValor();
        } else if (comparacao < 0) {
            return pesquisarRecursivamente(noAtual.getFilhoEsquerda(), valor);
        } else {
            return pesquisarRecursivamente(noAtual.getFilhoDireita(), valor);
        }
    }

    public T remover(T valor) {
        // Inicializa os nós de busca
        No<T> atual = raiz;
        No<T> paiAtual = null;
    
        // Encontra o nó com o valor desejado
        while (atual != null) {
            int comparacao = comparador.compare(valor, atual.getValor());
    
            if (comparacao == 0) {
                // O valor foi encontrado, vamos removê-lo
    
                if (atual.getFilhoEsquerda() == null && atual.getFilhoDireita() == null) {
                    // Caso 1: O nó não possui filhos, é uma folha
                    if (paiAtual != null) {
                        if (atual.getValor().compareTo(paiAtual.getValor()) == -1) {
                            paiAtual.setFilhoEsquerda(null);
                        } else {
                            paiAtual.setFilhoDireita(null);
                        }
                    } else {
                        raiz = null;
                    }
                } else if (atual.getFilhoEsquerda() != null && atual.getFilhoDireita() != null) {
                    // Caso 2: O nó possui dois filhos
                    No<T> substituto = atual.getFilhoDireita();
                    No<T> paiSubstituto = atual;
    
                    while (substituto.getFilhoEsquerda() != null) {
                        paiSubstituto = substituto;
                        substituto = substituto.getFilhoEsquerda();
                    }
    
                    substituto.setFilhoEsquerda(atual.getFilhoEsquerda);
    
                    if (paiAtual != null) {
                        if (atual.getValor().compareTo(paiAtual.getValor()) == -1) {
                            paiAtual.setFilhoEsquerda(substituto);
                        } else {
                            paiAtual.setFilhoDireita(substituto);
                        }
                    } else {
                        raiz = substituto;
                    }
    
                    if (substituto.getValor().compareTo(paiSubstituto.getValor()) == -1) {
                        paiSubstituto.setFilhoEsquerda(null);
                    } else {
                        paiSubstituto.setFilhoDireita(null);
                    }
                } else {
                    // Caso 3: O nó possui um filho à esquerda ou à direita
                    No<T> filho = (atual.getFilhoEsquerda() != null) ? atual.getFilhoEsquerda() : atual.getFilhoDireita();
    
                    if (paiAtual != null) {
                        if (atual.getValor().compareTo(paiAtual.getValor()) == -1) {
                            paiAtual.setFilhoEsquerda(filho);
                        } else {
                            paiAtual.setFilhoDireita(filho);
                        }
                    } else {
                        raiz = filho;
                    }
                }
    
                return atual.getValor(); // Retorna o valor removido
            } else if (comparacao < 0) {
                paiAtual = atual;
                atual = atual.getFilhoEsquerda();
            } else {
                paiAtual = atual;
                atual = atual.getFilhoDireita();
            }
        }
        return null; // Valor não encontrado, retorna null
    }

    
    @Override
    public int altura() {
        if (raiz == null) {
            return -1; // Árvore vazia
        }

        Queue<No<T>> fila = new LinkedList<>();
        fila.offer(raiz);
        int altura = -1;

        while (true) {
            int nivelSize = fila.size(); // Tamanho da fila no nível atual

            if (nivelSize == 0) {
                return altura; // Todas as folhas foram processadas, retorna a altura
            }

            altura++; // Incrementa a altura antes de processar o próximo nível

            // Processar todos os nós no nível atual e adicionar seus filhos à fila
            while (nivelSize > 0) {
                No<T> no = fila.poll();

                // Adiciona os filhos à fila, se existirem
                if (no.getFilhoEsquerda() != null) {
                    fila.offer(no.getFilhoEsquerda());
                }
                if (no.getFilhoDireita() != null) {
                    fila.offer(no.getFilhoDireita());
                }

                nivelSize--; // Decrementa o tamanho do nível atual
            }
        }
    }

    @Override
    public int quantidadeNos() {
        return contarNos(raiz);
    }

    private int contarNos(No<T> noAtual) {
        if (noAtual == null) {
            return 0;
        }

        return 1 + contarNos(noAtual.getFilhoEsquerda()) + contarNos(noAtual.getFilhoDireita());
    }

    @Override
    public String caminharEmNivel() {
        if (raiz == null) {
            return "[]";
        }

        StringBuilder resultado = new StringBuilder("[");
        Queue<No<T>> fila = new LinkedList<>();
        fila.offer(raiz);

        while (!fila.isEmpty()) {
            No<T> no = fila.poll();
            resultado.append(no.getValor().toString()).append(" ");

            if (no.getFilhoEsquerda() != null) {
                fila.offer(no.getFilhoEsquerda());
            }

            if (no.getFilhoDireita() != null) {
                fila.offer(no.getFilhoDireita());
            }
        }

        resultado.deleteCharAt(resultado.length() - 1); // Remove o espaço extra no final
        resultado.append("]");
        return resultado.toString();
    }

    @Override
    public String caminharEmOrdem() {
        StringBuilder resultado = new StringBuilder("[");
        caminharEmOrdemRecursivamente(raiz, resultado);
        resultado.deleteCharAt(resultado.length() - 1); // Remove a vírgula extra no final
        resultado.append("]");
        return resultado.toString();
    }

    private void caminharEmOrdemRecursivamente(No<T> noAtual, StringBuilder resultado) {
        if (noAtual == null) {
            return;
        }

        caminharEmOrdemRecursivamente(noAtual.getFilhoEsquerda(), resultado);
        resultado.append(noAtual.getValor().toString()).append(" ");
        caminharEmOrdemRecursivamente(noAtual.getFilhoDireita(), resultado);
    }


    @Override
    public T obterProximo() {
        if (raiz == null) {
            return null; // Retorna null se a árvore estiver vazia
        }

        if (pilhaNavegacao == null) {
            pilhaNavegacao = new ArrayList<>();
            No<T> noAtual = raiz;
            while (noAtual != null) {
                pilhaNavegacao.add(noAtual);
                noAtual = noAtual.getFilhoEsquerda();
            }
        }

        if (pilhaNavegacao.isEmpty()) {
            return null; // Retorna null se todos os elementos já foram percorridos
        }

        No<T> proximoNo = pilhaNavegacao.remove(pilhaNavegacao.size() - 1);
        No<T> noAtual = proximoNo.getFilhoDireita();
        while (noAtual != null) {
            pilhaNavegacao.add(noAtual);
            noAtual = noAtual.getFilhoEsquerda();
        }

        return proximoNo.getValor();
    }
    
    // @Override
    // public void reiniciarNavegacao(){
    //     atual = encontrarMenorElemento(raiz);
    //     pilhaNavegacao = new ArrayList<>();
    //     while (atual != null) {
    //         pilhaNavegacao.add(atual);
    //         atual = atual.getEsquerda();
    //     }
    // }
    
}
