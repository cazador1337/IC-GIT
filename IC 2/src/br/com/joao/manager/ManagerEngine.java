/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joao.manager;

import Default.Tabela.MyTableDefault;
import Default.Tabela.TableObjectInterface;
import br.com.joao.PreProcess.engine.JJFuzzy;
import br.com.joao.PreProcess.engine.JMeans;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joao
 */
public class ManagerEngine {
    
    private JMeans maker;
    private LinkedList<JJFuzzy> list;
    private JTable table;
    
    public ManagerEngine() {
        list = new LinkedList<>();
    }

    public void setTable(JTable table) {
        this.table = table;
    }
    
    public void updateTable() {
        List<TableObjectInterface> list = new LinkedList<>(this.list);
        if (!list.isEmpty()) {
            table.setModel(new MyTableDefault(list, "Nome"));
        }else{
            table.setModel(new DefaultTableModel());
        }
    }
    
    public void deleteSelected() {
        int[] array = table.getSelectedRows();
        LinkedList<JJFuzzy> aux = new LinkedList<>();
        for (int i : array) {
            aux.add(list.get(i));
        }

        for (JJFuzzy j : aux) {
            list.remove(j);
        }

        updateTable();
    }
    
    public void addAll(JJFuzzy... args){
        for (JJFuzzy arg : args) {
            add(arg);
        }
    }
    
    public void add(JJFuzzy arg){
        list.add(arg);
        updateTable();
    }
    
    public JJFuzzy getEngine(int i){
        return list.get(i);
    }
    
}
