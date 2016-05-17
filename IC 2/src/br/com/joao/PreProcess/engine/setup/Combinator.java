/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joao.PreProcess.engine.setup;

import java.util.LinkedList;

/**
 *
 * @author joao
 */
public class Combinator {

    public Combinator() {
    }        

    public LinkedList<LinkedList<Integer>> combinar(int[]... arrays) {
        LinkedList<LinkedList<Integer>> map = new LinkedList<>();
        for (int[] array : arrays) {
            map.add(newArray(array));
        }
        map = join(map, map.remove());
        return map;
    }

    public static LinkedList<Integer> newArray(int... array) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (Integer integer : array) {
            list.add(integer);
        }
        return list;
    }

    public static LinkedList<LinkedList<Integer>> join(LinkedList<LinkedList<Integer>> map, LinkedList<Integer> enter) {
        if (!map.isEmpty()) {
            map = join(map, map.remove());
            int i = 0;
            LinkedList<LinkedList<Integer>> aux = new LinkedList<>();
            for (LinkedList<Integer> list : map) {
                for (Integer inte : enter) {
                    LinkedList<Integer> x = new LinkedList<>(list);
                    x.add(inte);
                    aux.add(x);
                }
            }
            return aux;
        } else {
            for (Integer in : enter) {
                map.add(newArray(in));
            }
        }
        return map;
    }
}
