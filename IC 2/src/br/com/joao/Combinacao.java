package br.com.joao;


import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author joao
 */
public class Combinacao {

    public static void main(String[] args) {
        int[] arrayA = new int[]{1, 2, 3, 4};
        int[] arrayB = new int[]{5, 6, 7};
        int[] arrayC = new int[]{10, 9, 11};

        LinkedList<LinkedList<Integer>> map = new LinkedList<>();
        map.add(newArray(arrayA));
        map.add(newArray(arrayB));
        map.add(newArray(arrayC));

        map = join(map, map.remove());
        for (LinkedList<Integer> list : map) {
            for (Integer i : list) {
                System.out.print(i + ",");
            }
            System.out.println("");
        }
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
            LinkedList<LinkedList<Integer>> aux = new LinkedList<>();
            for (LinkedList<Integer> list : map) {
                for (Integer inte : enter) {
                    LinkedList<Integer> x = new LinkedList<>(list);
                    x.add(inte);
                    aux.add(x);
                }                
            }
            return aux;
        }else{
            for (Integer in : enter) {
                map.add(newArray(in));
            }
        }
        return map;
    }
}
