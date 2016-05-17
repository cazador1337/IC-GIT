/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joao.manager;

import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author joao
 */
public class ListSyncronized<MyObject> extends LinkedList<MyObject> {

    private int sizeLimit = -1;

    @Override
    public synchronized void add(int i, MyObject e) {
        super.add(i, e); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized boolean addAll(int i, Collection<? extends MyObject> clctn) {
        return super.addAll(i, clctn); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized boolean addAll(Collection<? extends MyObject> clctn) {
        return super.addAll(clctn); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized boolean add(MyObject e) {
        if (super.size() < sizeLimit || sizeLimit == -1) {
            super.add(e);
            return true;
        } else {
            System.out.println("false");
            return false;
        }
    }

    public void setSizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public int getSizeLimit() {
        return sizeLimit;
    }

}
