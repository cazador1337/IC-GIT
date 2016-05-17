/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joao.PreProcess.engine;

import br.com.joao.PreProcess.engine.setup.Setting;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sourceforge.jFuzzyLogic.rule.LinguisticTerm;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.rule.RuleExpression;
import net.sourceforge.jFuzzyLogic.rule.RuleTerm;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import net.sourceforge.jFuzzyLogic.ruleConnectionMethod.RuleConnectionMethodAndMin;

/**
 *
 * @author joao
 */
public class RuleTools {

    private static final int INSTANCE_KEY = 0;

    public RuleBlock MakeRule(Dataset data, JJFuzzy eng) {
        RuleBlock root = new RuleBlock(eng.getFunctionBlock());
        int size = eng.getVars().length;

        for (int i = 0; size < data.size(); i++, size++) {
            RuleTerm[] terms = genRuleTerms(eng.setValues(extractValues(data.subList(i, size), INSTANCE_KEY)));
            RuleExpression expression = joinRules(terms);
            Rule rule = newRule("rule" + (i + 1), root, expression);
            addConsequent(rule, eng.setValue(data.get(size).get(INSTANCE_KEY)));
            root.add(rule);
        }
        root.setRuleAccumulationMethod(Setting.newRuleAccumulation());
        root.setRuleActivationMethod(Setting.newRuleActivation());
        return root;
    }

    private RuleTerm[] genRuleTerms(Variable... vars) {
        RuleTerm[] array = new RuleTerm[vars.length];
        int i = 0;
        for (Variable var : vars) {
            array[i++] = new RuleTerm(var, findInferencia(var), false);
        }
        return array;
    }

    private RuleExpression joinRules(RuleTerm... rules) {//pelo menos duas para começar
        int pos = 0;
        RuleExpression base = null;

        if (rules.length != 1) {
            base = new RuleExpression(rules[pos++], rules[pos++],
                    RuleConnectionMethodAndMin.get());
        } else {
            base = base = new RuleExpression(rules[pos], rules[pos++],
                    RuleConnectionMethodAndMin.get());
        }

        for (; pos < rules.length; pos++) {
            base = new RuleExpression(base, rules[pos],
                    RuleConnectionMethodAndMin.get());
        }

        return base;
    }

    private Rule newRule(String label, RuleBlock block, RuleExpression base) {
        Rule rule = new Rule(label, block);
        rule.setAntecedents(base);
        return rule;
    }

    private Rule addConsequent(Rule rule, Variable var) {
        rule.addConsequent(var, findInferencia(var), false);
        return rule;
    }

    public String findInferencia(Variable var) {
        HashMap<String, LinguisticTerm> map = var.getLinguisticTerms();
        LinguisticTerm max = null;
        Set<String> keys = map.keySet();
        for (String key : keys) {
            if (max == null) {
                max = map.get(key);
                //System.out.println(var.getMembership(key));
            }
            if (var.getMembership(key) > var.getMembership(max.getTermName())) {
                max = map.get(key);
                //System.out.println(var.getMembership(key));
            }
        }
        //System.out.println("-------------");
        return max.getTermName();
    }

    public Double[] extractValues(List<Instance> list, Object key) {
        Double[] array = new Double[list.size()];
        int i = 0;
        for (Instance in : list) {
            array[i++] = in.get(key);
        }
        return array;
    }
}
