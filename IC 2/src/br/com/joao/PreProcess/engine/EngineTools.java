/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joao.PreProcess.engine;

import br.com.joao.PreProcess.InstanceSortable;
import br.com.joao.PreProcess.engine.setup.Setting;
import java.util.HashMap;
import java.util.List;
import net.sf.javaml.core.Instance;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunctionTrapetzoidal;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunctionTriangular;
import net.sourceforge.jFuzzyLogic.membership.Value;
import net.sourceforge.jFuzzyLogic.rule.LinguisticTerm;
import net.sourceforge.jFuzzyLogic.rule.Variable;

/**
 *
 * @author joao
 */
public class EngineTools {
    public static final String SAIDA = "saida";

    public EngineTools() {
    }

    public JJFuzzy makeEngine(List<InstanceSortable> soucer, int size) {
        JJFuzzy eng = new JJFuzzy();
        FIS fis = new FIS();
        FunctionBlock root = new FunctionBlock(fis);
        root.setName(JJFuzzy.BLOCK);

        addAlltoBlock(root, genVariable(size, soucer));

        Variable out = newOutPutVar(soucer);
        root.setVariable(out.getName(), out);

        fis.addFunctionBlock(root.getName(), root);//esta string é a key para usar no getFunctionBlock.
        
        eng.setFis(fis);
        eng.setTerms(newTerms(soucer));
        eng.setVars(root.getVariables());
        return eng;
    }

    private Variable newOutPutVar(List<InstanceSortable> soucer) {
        Variable out = new Variable(SAIDA);
        out.setLinguisticTerms(newTerms(soucer));
        out.setDefuzzifier(Setting.newDefuzzifier(out));
        return out;
    }

    private Variable[] genVariable(int i, List<InstanceSortable> soucer) {
        String[] labels = newLabels("var", i);
        Variable[] vars = new Variable[i];
        HashMap<String, LinguisticTerm> map = newTerms(soucer);
        for (int j = 0; j < i; j++) {
            vars[j] = new Variable(labels[j]);
            vars[j].setLinguisticTerms(map);
        }
        return vars;
    }

    private void addAlltoBlock(FunctionBlock root, Variable... args) {
        for (Variable arg : args) {
            root.setVariable(arg.getName(), arg);
        }
    }

    private MembershipFunctionTriangular newMembership(Double min, Double core, Double max) {
        return new MembershipFunctionTriangular(new Value(min), new Value(core), new Value(max));
    }
    
    private MembershipFunctionTrapetzoidal newMembership(Double a, Double b, Double c, Double d) {
        return new MembershipFunctionTrapetzoidal(new Value(a), new Value(b), new Value(c), new Value(d));
    }

    private String[] newLabels(String stem, int i) {
        String[] terms = new String[i];
        for (int j = 0; j < i; j++) {
            terms[j] = stem + (j + 1);
        }
        return terms;
    }

    private HashMap<String, LinguisticTerm> newTerms(List<InstanceSortable> soucer) {
        HashMap<String, LinguisticTerm> map = new HashMap<>();
        int index = 0, pos = 0;
        String[] labels = newLabels("term", soucer.size());        
                
        Instance anterior = soucer.get(pos), atual = soucer.get(++pos);
        map.put(labels[index], newFirstTerm(
                labels[index], anterior, anterior, atual));        
        for (; pos < soucer.size()-1; pos++) {
            map.put(labels[++index], newTerm(
                    labels[index], anterior, atual, soucer.get(pos+1)));
            anterior = atual;
            atual = soucer.get(pos+1);
        }
        map.put(labels[++index], newLastTerm(
                labels[index], atual, anterior, atual));
        return map;
    }

    private LinguisticTerm newTerm(String label, Instance a, Instance b, Instance c) {
        MembershipFunctionTriangular function = newMembership(
                getCore(a),
                getCore(b),
                getCore(c));
        return new LinguisticTerm(label, function);
    }
    
    private LinguisticTerm newFirstTerm(String label,Instance in, Instance a, Instance b) {
        MembershipFunctionTrapetzoidal function = newMembership(in.get(InstanceSortable.MIM),
                in.get(InstanceSortable.MIM),
                getCore(a),
                getCore(b));
        return new LinguisticTerm(label, function);
    }
    
    private LinguisticTerm newLastTerm(String label,Instance in, Instance a, Instance b) {
        MembershipFunctionTrapetzoidal function = newMembership(
                getCore(a),
                getCore(b), 
                in.get(InstanceSortable.MAX),
                in.get(InstanceSortable.MAX));
        return new LinguisticTerm(label, function);
    }
    
    private Double getCore(Instance in){
        return in.get(InstanceSortable.CORE);
    }    

}
