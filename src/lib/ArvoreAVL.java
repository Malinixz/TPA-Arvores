package lib;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class ArvoreAVL <T> extends ArvoreBinaria<T> {

    public ArvoreAVL(Comparator<T> comp) {
        super(comp);
    }

    //Implementar métodos para efetuar o balanceamento e sobrescrever método de adicionar elemento...
    public void adicionar(T valor) {
        No n = new No(valor);
        adicionarAVL(this.raiz, n);
    }

    public void adicionarAVL(No<T> aComparar, No<T> aInserir) {

        if (aComparar == null) {
            this.raiz = aInserir;

        } else {

            if (getComparador().compare(aInserir.getValor(),aComparar.getValor()) == -1) {

                if (aComparar.getFilhoEsquerda() == null) {
                    aComparar.setFilhoEsquerda(aInserir);
                    aInserir.setPai(aComparar);
                    verificarBalanceamento(aComparar);

                } else {
                    adicionarAVL(aComparar.getFilhoEsquerda(), aInserir);
                }

            } else if (getComparador().compare(aInserir.getValor(),aComparar.getValor()) == 1) {

                if (aComparar.getFilhoDireita() == null) {
                    aComparar.setFilhoDireita(aInserir);
                    aInserir.setPai(aComparar);
                    verificarBalanceamento(aComparar);

                } else {
                    adicionarAVL(aComparar.getFilhoDireita(), aInserir);
                }

            } else {
                // O nó já existe
            }
        }
    }

    public No<T> RotaEsquerda(No<T> inicial) { //Quando FB 2 e FB filhodireita > 0
        No<T> novaRaiz = inicial.getFilhoDireita();
        novaRaiz.setPai(inicial.getPai());
        inicial.setFilhoDireita(novaRaiz.getFilhoEsquerda());

        if (inicial.getFilhoDireita() != null) {
            inicial.getFilhoDireita().setPai(inicial);
        }

        novaRaiz.setFilhoEsquerda(inicial);
        inicial.setPai(novaRaiz);

        if (novaRaiz.getPai() != null) {
            if (novaRaiz.getPai().getFilhoDireita() == inicial){          // SE O NÓ INICIAL FOR UM FILHO A DIREITA DE ALGUM
                novaRaiz.getPai().setFilhoDireita(novaRaiz);              // A NOVA RAIZ SERÁ O NOVO FILHO A DIREITA DESTE
            }else if (novaRaiz.getPai().getFilhoEsquerda() == inicial) {  // SE O NÓ INICIAL FOR UM FILHO A ESQUERDA DE ALGUM
                novaRaiz.getPai().setFilhoEsquerda(novaRaiz);
            }
        }

        CalcFB(inicial);
        CalcFB(novaRaiz);

        return novaRaiz;
    }

    public No<T> RotaDireita(No<T> inicial) { //Quando FB -2 e FB filhodireita < 0
        No<T> novaRaiz = inicial.getFilhoEsquerda();
        novaRaiz.setPai(inicial.getPai());
        inicial.setFilhoEsquerda(novaRaiz.getFilhoDireita());

        if (inicial.getFilhoEsquerda() != null) {
            inicial.getFilhoEsquerda().setPai(inicial);
        }

        novaRaiz.setFilhoDireita(inicial);
        inicial.setPai(novaRaiz);

        if (novaRaiz.getPai() != null) {
            if (novaRaiz.getPai().getFilhoDireita() == inicial){
                novaRaiz.getPai().setFilhoDireita(novaRaiz);
            }else if (novaRaiz.getPai().getFilhoEsquerda() == inicial){
                novaRaiz.getPai().setFilhoEsquerda((novaRaiz));
            }
        }

        CalcFB(inicial);
        CalcFB(novaRaiz);

        return novaRaiz;
    }

    public No<T> RotaEsqDir(No<T> raiz) { //Quando FB -2 e FB filhodireita > 0
        raiz.setFilhoEsquerda(RotaEsquerda(raiz.getFilhoEsquerda()));
        return RotaDireita(raiz);
    }

    public No<T> RotaDirEsq(No<T> raiz) { //Quando FB 2 e FB filhodireita < 0
        raiz.setFilhoDireita(RotaDireita(raiz.getFilhoDireita()));
        return RotaEsquerda(raiz);
    }

    public int altura(No<T> no) {
        if (no == null) {
            return -1; // Subárvore vazia
        }

        Queue<No<T>> fila = new LinkedList<>();
        fila.offer(no);
        int altura = -1;

        while (true) {
            int nivelSize = fila.size(); // Tamanho da fila no nível atual

            if (nivelSize == 0) {
                return altura; // Todas as folhas foram processadas, retorna a altura
            }

            altura++; // Incrementa a altura antes de processar o próximo nível

            // Processar todos os nós no nível atual e adicionar seus filhos à fila
            while (nivelSize > 0) {
                No<T> currentNode = fila.poll();

                // Adiciona os filhos à fila, se existirem
                if (currentNode.getFilhoEsquerda() != null) {
                    fila.offer(currentNode.getFilhoEsquerda());
                }
                if (currentNode.getFilhoDireita() != null) {
                    fila.offer(currentNode.getFilhoDireita());
                }

                nivelSize--; // Decrementa o tamanho do nível atual
            }
        }
    }

    public void CalcFB(No<T> no) {

        Integer alturaEsquerda = altura(no.getFilhoEsquerda());
        Integer alturaDireita = altura(no.getFilhoDireita());

        no.setFB(alturaDireita - alturaEsquerda);
    }

    public void verificarBalanceamento(No atual) {
        CalcFB(atual);
        int balanceamento = atual.getFB();

        if (balanceamento == -2) {

            if (altura(atual.getFilhoEsquerda().getFilhoEsquerda()) >= altura(atual.getFilhoEsquerda().getFilhoDireita())) {
                atual = RotaDireita(atual);

            } else {
                atual = RotaEsqDir(atual);
            }

        } else if (balanceamento == 2) {

            if (altura(atual.getFilhoDireita().getFilhoDireita()) >= altura(atual.getFilhoDireita().getFilhoEsquerda())) {
                atual = RotaEsquerda(atual);

            } else {
                atual = RotaDirEsq(atual);
            }
        }

        if (atual.getPai() != null) {
            verificarBalanceamento(atual.getPai());
        } else {
            this.raiz = atual;
        }
    }
}
