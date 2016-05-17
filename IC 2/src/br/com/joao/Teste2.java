/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joao;

import br.com.joao.PreProcess.engine.JMeans;
import java.util.concurrent.ExecutionException;
import org.antlr.runtime.RecognitionException;

/**
 *
 * @author joao
 */
public class Teste2 {
    public static void main(String[] args) throws RecognitionException, InterruptedException, ExecutionException {
        JMeans means = new JMeans("2004.txt", 2, 3);
        means.setInvert(true);
        means.run();
        System.out.println(means.get() + " milisegundos");     
        
    }
}
