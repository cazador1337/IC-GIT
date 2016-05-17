/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joao.PreProcess;

import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;

/**
 *
 * @author joao
 */
public class InstanceSortable extends SparseInstance implements Comparable<InstanceSortable> {

    public static int KEY = 0, MAX = 2, MIM = 1, CORE = 0;

    public InstanceSortable(Instance i) {
        super.put(CORE, i.get(0));
        super.put(MIM, i.get(0));
        super.put(MAX, i.get(0));
    }    

    public static void setKey(int key) {
        InstanceSortable.KEY = key;
    }

    @Override
    public int compareTo(InstanceSortable t) {
        return this.get(KEY) < t.get(KEY) ? -1 : this.get(KEY) > t.get(KEY) ? 1 : 0;
    }

}
