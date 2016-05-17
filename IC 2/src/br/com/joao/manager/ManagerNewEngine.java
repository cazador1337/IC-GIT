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
import br.com.joao.PreProcess.engine.setup.Setting;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joao
 */
public class ManagerNewEngine implements PropertyChangeListener {

    private JMeans maker;
    private ListSyncronized<JJFuzzy> list;
    private JTable table;
    private LinkedList<LinkedList<Integer>> tasks;

    public ManagerNewEngine() {
        list = new ListSyncronized<>();
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void makeEngine(String label, String source, Double fator, int vars, boolean invert, JProgressBar status) {
        maker = new JMeans();
        maker.addPropertyChangeListener(this);
        maker.setSource(source);
        maker.setBar(status);
        maker.setFator(fator);
        maker.setInvert(invert);
        maker.setVars(vars);
        maker.setLabel(label);
        new Thread(maker).start();
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if (maker.isDone()) {
            JJFuzzy aux = maker.getEngine();
            if (aux != null) {
                list.add(aux);
                aux.setSettings(Setting.getDefuMethod(), Setting.getRuleAggreagation(), Setting.getRuleActivation());
                maker.setEngine(null);
                updateTable();
            }
            if (tasks != null && !tasks.isEmpty()) {
                setSettings(tasks.remove().toArray());
                cleanStatus(maker.getBar());
                makeEngine(Setting.makeLabel(), maker.getSource().getAbsolutePath(),
                        maker.getFator(), maker.getVars(), maker.isInvert(), maker.getBar());
            }
        }
    }

    public void updateTable() {
        List<TableObjectInterface> list = new LinkedList<>(this.list);
        if (!list.isEmpty()) {
            table.setModel(new MyTableDefault(list, "Nome", "Termos Antecessores", "Termos", "Defuzzier", "Accumulation", "Activation"));
        } else {
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

    public void clear() {
        list.clear();
        updateTable();
    }

    public JJFuzzy[] getSelectEngines() {
        int[] array = table.getSelectedRows();
        JJFuzzy[] args = new JJFuzzy[array.length];
        for (int i = 0; i < array.length; i++) {
            args[i] = list.get(array[i]);
        }
        return args;
    }

    private void setSettings(int defu, int action, int aggre) {
        Setting.setDefuMethod(defu);
        Setting.setRuleActivation(action);
        Setting.setRuleAggreagation(aggre);
    }

    private void setSettings(Object... opts) {
        setSettings(Integer.parseInt(opts[0].toString()),
                Integer.parseInt(opts[1].toString()),
                Integer.parseInt(opts[2].toString()));
    }

    public void makeEngines(LinkedList<LinkedList<Integer>> map,
            String file, Double aDouble, int i, boolean selected, JProgressBar bar) {
        setSettings(map.remove().toArray());
        makeEngine(Setting.makeLabel(), file, aDouble, i, selected, bar);
        tasks = map;
    }

    private void cleanStatus(JProgressBar jProgressBarStatus) {
        jProgressBarStatus.setValue(0);
        jProgressBarStatus.repaint();
    }

}
