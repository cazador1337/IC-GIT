/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joao.PreProcess.engine;

import Default.Tabela.TableObjectInterface;
import br.com.joao.PreProcess.engine.setup.Setting;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.LinguisticTerm;
import net.sourceforge.jFuzzyLogic.rule.Variable;

/**
 *
 * @author joao
 */
public class JJFuzzy implements Serializable, TableObjectInterface {

    public static final String BLOCK = "root", RULE = "rule";

    private FIS fis;
    private Variable[] vars;
    private LinguisticTerm[] terms;
    private String label;
    private int[] settings;

    public JJFuzzy() {
    }

    public JJFuzzy(FIS fis) {//testar para ver se não tem erro
        this.fis = fis;
        setVars(fis.getFunctionBlock(BLOCK).getVariables());
        setTerms(vars[0].getLinguisticTerms());
    }

    public JJFuzzy(String path) {//testar para ver se não tem erro
        this.fis = FIS.load(path);
        setVars(fis.getFunctionBlock(BLOCK).getVariables());
        setTerms(vars[0].getLinguisticTerms());
    }

    public FIS getFis() {
        return fis;
    }

    public void setFis(FIS fis) {
        this.fis = fis;
    }

    public Variable[] getVars() {
        return vars;
    }

    void setVars(HashMap<String, Variable> map) {
        Set<String> keys = map.keySet();
        ArrayList<Variable> array = new ArrayList<>();
        vars = new Variable[keys.size()];
        int i = 0;
        for (String key : keys) {
            array.add(map.get(key));
        }
        Collections.sort(array,new Comparator<Variable>() {
            @Override
            public int compare(Variable t, Variable t1) {
                return t.getName().length() < t1.getName().length() ? 1 :
                        t.getName().compareTo(t1.getName());
            }
        });
        for (Variable v : array) {
            vars[i++] = v;
        }
    }

    public LinguisticTerm[] getTerms() {
        return terms;
    }

    void setTerms(HashMap<String, LinguisticTerm> map) {
        Set<String> keys = map.keySet();
        ArrayList<LinguisticTerm> array = new ArrayList<>();
        for (String key : keys) {
            array.add(map.get(key));
        }
        Collections.sort(array);
        terms = new LinguisticTerm[array.size()];
        for (int i = 0; i < terms.length; i++) {
            terms[i] = array.get(i);
        }
    }

    public FunctionBlock getFunctionBlock() {
        return fis.getFunctionBlock(BLOCK);
    }

    public Variable[] setValues(Double... array) {
        for (int i = 1;i < array.length ; i++) {
            fis.setVariable(vars[i].getName(), array[i-1]);
        }
        try {
            fis.evaluate();//super importante não mexer
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return mapToArray(fis.getFunctionBlock(BLOCK).getVariables());
    }

    public Variable setValue(Double val) {
        fis.setVariable(vars[0].getName(), val);
        fis.evaluate();//super importante não mexer
        return fis.getFunctionBlock(BLOCK).getVariables().get(vars[0].getName());
    }

    private Variable[] mapToArray(HashMap<String, Variable> map) {//não salva a var saida
        Set<String> keys = map.keySet();
        Variable[] array = new Variable[keys.size() - 1];
        int i = 0;
        for (String key : keys) {
            if (!key.equals(EngineTools.SAIDA)) {
                array[i++] = map.get(key);
            }
        }
        return array;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int[] getSettings() {
        return settings;
    }

    public void setSettings(int... settings) {
        this.settings = settings;
    }

    @Override
    public Object[] atributeToTable() {
        return new Object[]{label, vars.length - 1, terms.length,
            Setting.getDefuzzifierName(settings[0]),
            Setting.getRuleAccumulationName(settings[1]),
            Setting.getRuleActivationName(settings[2])};
    }   

}
