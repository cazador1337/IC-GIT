/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joao.PreProcess.engine.setup;

import net.sourceforge.jFuzzyLogic.defuzzifier.Defuzzifier;
import net.sourceforge.jFuzzyLogic.defuzzifier.DefuzzifierCenterOfArea;
import net.sourceforge.jFuzzyLogic.defuzzifier.DefuzzifierCenterOfGravity;
import net.sourceforge.jFuzzyLogic.defuzzifier.DefuzzifierCenterOfGravityFunctions;
import net.sourceforge.jFuzzyLogic.defuzzifier.DefuzzifierCenterOfGravitySingletons;
import net.sourceforge.jFuzzyLogic.defuzzifier.DefuzzifierLeftMostMax;
import net.sourceforge.jFuzzyLogic.defuzzifier.DefuzzifierMeanMax;
import net.sourceforge.jFuzzyLogic.defuzzifier.DefuzzifierRightMostMax;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import net.sourceforge.jFuzzyLogic.ruleAccumulationMethod.RuleAccumulationMethod;
import net.sourceforge.jFuzzyLogic.ruleAccumulationMethod.RuleAccumulationMethodBoundedSum;
import net.sourceforge.jFuzzyLogic.ruleAccumulationMethod.RuleAccumulationMethodMax;
import net.sourceforge.jFuzzyLogic.ruleAccumulationMethod.RuleAccumulationMethodNormedSum;
import net.sourceforge.jFuzzyLogic.ruleAccumulationMethod.RuleAccumulationMethodProbOr;
import net.sourceforge.jFuzzyLogic.ruleAccumulationMethod.RuleAccumulationMethodSum;
import net.sourceforge.jFuzzyLogic.ruleActivationMethod.RuleActivationMethod;
import net.sourceforge.jFuzzyLogic.ruleActivationMethod.RuleActivationMethodMin;
import net.sourceforge.jFuzzyLogic.ruleActivationMethod.RuleActivationMethodProduct;

/**
 *
 * @author joao
 */
public class Setting {

    public static final int DEFUSSIFIER_CENTER_OF_AREA = 0,
            DEFUSSIFIER_CENTER_OF_GRAVITY = 1,
            DEFUSSIFIER_CENTER_OF_GRAVITY_SINGLETONS = 2,
            DEFUSSIFIER_LEFT_MOST_MAX = 3,
            DEFUSSIFIER_MEAN_MAX = 4,
            DEFUSSIFIER_RIGHT_MOST_MAX = 5,
            DEFUSSIFIER_CENTER_OF_GRAVITY_FUNCTIONS = 6;

    public static final int RULE_AGGREGATION_MAX = 0,
            RULE_AGGREGATION_BOUNDED_SUM = 1,
            RULE_AGGREGATION_PROB_OR = 2,
            RULE_AGGREGATION_SUM = 3,
            RULE_AGGREGATION_NORMED_SUM = 4;

    public static final int RULE_ACTIVATION_MIN = 0,
            RULE_ACTIVATION_PRODUCT = 1;

    private static int defuMethod = 0, ruleAggreagation = 3, ruleActivation = 0;

    public static void setDefuMethod(int defuMethod) {
        Setting.defuMethod = defuMethod;
    }

    public static void setRuleAggreagation(int ruleAggreagation) {
        Setting.ruleAggreagation = ruleAggreagation;
    }

    public static void setRuleActivation(int ruleActivation) {
        Setting.ruleActivation = ruleActivation;
    }

    public static int getDefuMethod() {
        return defuMethod;
    }

    public static int getRuleAggreagation() {
        return ruleAggreagation;
    }

    public static int getRuleActivation() {
        return ruleActivation;
    }

    public static Defuzzifier newDefuzzifier(Variable var) {
        switch (defuMethod) {
            case DEFUSSIFIER_CENTER_OF_AREA:
                return new DefuzzifierCenterOfArea(var);

            case DEFUSSIFIER_CENTER_OF_GRAVITY:
                return new DefuzzifierCenterOfGravity(var);

            case DEFUSSIFIER_CENTER_OF_GRAVITY_SINGLETONS:
                return new DefuzzifierCenterOfGravitySingletons(var);

            case DEFUSSIFIER_LEFT_MOST_MAX:
                return new DefuzzifierLeftMostMax(var);

            case DEFUSSIFIER_MEAN_MAX:
                return new DefuzzifierMeanMax(var);

            case DEFUSSIFIER_RIGHT_MOST_MAX:
                return new DefuzzifierRightMostMax(var);

            case DEFUSSIFIER_CENTER_OF_GRAVITY_FUNCTIONS:
                return new DefuzzifierCenterOfGravityFunctions(var);

            default:
                return new DefuzzifierCenterOfArea(var);
        }
    }

    public static RuleAccumulationMethod newRuleAccumulation() {
        switch (ruleAggreagation) {
            case RULE_AGGREGATION_BOUNDED_SUM:
                return new RuleAccumulationMethodBoundedSum();
            case RULE_AGGREGATION_MAX:
                return new RuleAccumulationMethodMax();
            case RULE_AGGREGATION_NORMED_SUM:
                return new RuleAccumulationMethodNormedSum();
            case RULE_AGGREGATION_PROB_OR:
                return new RuleAccumulationMethodProbOr();
            case RULE_AGGREGATION_SUM:
                return new RuleAccumulationMethodSum();
            default:
                return new RuleAccumulationMethodSum();
        }
    }

    public static RuleActivationMethod newRuleActivation() {
        switch (ruleActivation) {
            case RULE_ACTIVATION_MIN:
                return new RuleActivationMethodMin();
            case RULE_ACTIVATION_PRODUCT:
                return new RuleActivationMethodProduct();
            default:
                return new RuleActivationMethodMin();
        }
    }

    public static String getDefuzzifierName(int i) {
        switch (i) {
            case DEFUSSIFIER_CENTER_OF_AREA:
                return "CENTER OF AREA";

            case DEFUSSIFIER_CENTER_OF_GRAVITY:
                return "CENTER OF GRAVITY";

            case DEFUSSIFIER_CENTER_OF_GRAVITY_SINGLETONS:
                return "CENTER OF GRAVITY SINGLETONS";

            case DEFUSSIFIER_LEFT_MOST_MAX:
                return "LEFT MOST MAX";

            case DEFUSSIFIER_MEAN_MAX:
                return "MEAN MAX";

            case DEFUSSIFIER_RIGHT_MOST_MAX:
                return "RIGHT MOST MAX";

            case DEFUSSIFIER_CENTER_OF_GRAVITY_FUNCTIONS:
                return "CENTER OF GRAVITY FUNCTIONS";

            default:
                return "CENTER OF AREA";
        }
    }

    public static String getRuleAccumulationName(int i) {
        switch (i) {
            case RULE_AGGREGATION_BOUNDED_SUM:
                return "AGGREGATION BOUNDED SUM";
            case RULE_AGGREGATION_MAX:
                return "AGGREGATION MAX";
            case RULE_AGGREGATION_NORMED_SUM:
                return "AGGREGATION NORMED SUM";
            case RULE_AGGREGATION_PROB_OR:
                return "AGGREGATION PROB OR";
            case RULE_AGGREGATION_SUM:
                return "AGGREGATION SUM";
            default:
                return "AGGREGATION SUM";
        }
    }

    public static String getRuleActivationName(int i) {
        switch (i) {
            case RULE_ACTIVATION_MIN:
                return "ACTIVATION MIN";
            case RULE_ACTIVATION_PRODUCT:
                return "ACTIVATION PRODUCT";
            default:
                return "ACTIVATION MIN";
        }
    }

    public static String makeLabel() {
        StringBuilder aux = new StringBuilder();
        aux.append(getDefuzzifierName(defuMethod) + ", ");
        aux.append(getRuleActivationName(ruleActivation) + ", ");
        aux.append(getRuleAccumulationName(ruleAggreagation));
        return aux.toString();
    }

}
