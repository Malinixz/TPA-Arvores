/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;
import java.util.Comparator;

//Ajuste esse importe para que importe sua classe de Árvore binária
import lib.ArvoreAVL;

/**
 *
 * @author victoriocarvalho
 * 
 * Classe principal do aplicativo a ser utilizado para fazer o relatório do trabalho 
 * de árvore binária
 */
public class AppRelatorioArvoreBinaria {
    public static void main(String[] args) {
        //Instancio o meu gerador de árvores (que também foi fornecido)
        GeradorDeArvores gerador = new GeradorDeArvores();
        //Instancio um comparador de alunos por matricula (também fornecido)
        ComparadorAlunoPorMatricula comparador = new ComparadorAlunoPorMatricula();
        ArvoreAVL<Integer> arv;
        
        
//------Início do trecho citado nas questões 1, 2 e 3 do relatório-------------------------------
        //Instancio uma árvore binária. Lembre de ajustar o import para sua classe de árvore binária
        arv = new ArvoreAVL(Comparator.naturalOrder());
        arv.adicionar(8);
        arv.adicionar(4);
        arv.adicionar(10);
        arv.adicionar(9);
        arv.adicionar(15);
        arv.adicionar(12);
        arv.adicionar(14);
        arv.adicionar(17);
        arv.adicionar(5);
        arv.adicionar(3);
        arv.adicionar(1);
        System.out.println("----------------------------");
        arv.percorrerInOrder(arv.getRaiz());
        //Chamo o gerador para inserir 100 elementos nessa árvore de forma que fique degenerada

    }
}
