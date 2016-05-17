/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joao.PreProcess.engine;

import br.com.joao.PreProcess.DataSetTools;
import br.com.joao.PreProcess.InstanceSortable;
import br.com.joao.PreProcess.Mensure;
import br.com.joao.PreProcess.PreProcessing;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;

/**
 *
 * @author joao
 */
public class JMeans extends SwingWorker<Long, Integer> {

    private JProgressBar bar;
    private JJFuzzy engine;
    private File source;
    private double fator;
    private int vars;
    private boolean invert = false;
    private String label = "";

    public JMeans() {
    }

    public JMeans(File source, double fator, int vars) {
        this.source = source;
        this.fator = fator;
        this.vars = vars;
    }

    public JMeans(String source, double fator, int vars) {
        this.source = new File(source);
        this.fator = fator;
        this.vars = vars;
    }

    public JMeans(JProgressBar bar, File soucer, double fator, int vars) {
        this.bar = bar;
        this.source = soucer;
        this.fator = fator;
        this.vars = vars;
    }

    public JMeans(JProgressBar bar, String soucer, double fator, int vars) {
        this.bar = bar;
        this.source = new File(soucer);
        this.fator = fator;
        this.vars = vars;
    }

    public JMeans(File soucer) {
        this.source = soucer;
    }

    public void setBar(JProgressBar bar) {
        this.bar = bar;
        if (bar != null) {
            bar.setStringPainted(true);
        }
    }

    @Override
    protected Long doInBackground() throws Exception {
        long start = System.currentTimeMillis();
        publish(3);
        DataSetTools fb = new DataSetTools();
        Mensure men = fb.newMensure(source, fator);
        publish(15);
        PreProcessing pre = fb.newPreProcessing();
        List<InstanceSortable> list = pre.process(men, fb.dataToSortlist(source));
        publish(30);
        EngineTools par = new EngineTools();
        publish(50);
        setEngine(par.makeEngine(list, vars));
        getEngine().setLabel(label);
        publish(60);
        RuleTools tools = new RuleTools();
        publish(70);
        RuleBlock bloco;
        if (invert) {
            bloco = tools.MakeRule(fb.invertDataSet(fb.newDataSet(source)), getEngine());
        } else {
            bloco = tools.MakeRule((fb.newDataSet(source)), getEngine());
        }
        publish(80);
        HashMap<String, RuleBlock> ruleBlocks = new HashMap<>();
        ruleBlocks.put(bloco.getName(), bloco);
        publish(90);
        getEngine().getFunctionBlock().setRuleBlocks(ruleBlocks);
        publish(100);
        return System.currentTimeMillis() - start;
    }

    @Override
    protected void process(List<Integer> list) {
        for (Integer i : list) {
            if (bar != null) {
                bar.setValue(i);
                bar.repaint();
            }
        }
    }

    public JJFuzzy getEngine() {
        return engine;
    }

    public void setEngine(JJFuzzy engine) {
        this.engine = engine;
    }

    public double getFator() {
        return fator;
    }

    public void setFator(double fator) {
        this.fator = fator;
    }

    public int getVars() {
        return vars;
    }

    public void setVars(int vars) {
        this.vars = vars;
    }

    public void setInvert(boolean invert) {
        this.invert = invert;
    }

    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }

    public void setSource(String path) {
        this.source = new File(path);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public JProgressBar getBar() {
        return bar;
    }

    public boolean isInvert() {
        return invert;
    }

}
