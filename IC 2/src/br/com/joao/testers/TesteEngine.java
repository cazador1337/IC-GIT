/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joao.testers;

import Default.Tabela.TableObjectInterface;
import br.com.joao.PreProcess.DataSetTools;
import br.com.joao.PreProcess.engine.EngineTools;
import br.com.joao.PreProcess.engine.JJFuzzy;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sourceforge.jFuzzyLogic.rule.Variable;

/**
 *
 * @author joao
 */
public class TesteEngine {

    private static final int INSTANCE_KEY = 0;

    private JJFuzzy fuzzy;
    private File file;
    private List<TableObjectInterface> previsões;
    private boolean invert = false;
    private DataSetTools tools;

    public TesteEngine(JJFuzzy fuzzy, File file) {
        this.fuzzy = fuzzy;
        this.file = file;
        previsões = new LinkedList<>();
        tools = new DataSetTools();
    }

    public TesteEngine(JJFuzzy fuzzy, File file, boolean invert) {
        this(fuzzy, file);
        this.invert = invert;
    }

    public TesteEngine(JJFuzzy fuzzy, String file, boolean invert) {
        this(fuzzy, new File(file), invert);
    }

    public void execute() {
        try {
            int size = fuzzy.getVars().length - 1;//nos var conta a var de saída, e eu só quero as de entrada
            Dataset data = tools.newDataSet(file);
            if (invert) {
                data = tools.invertDataSet(data);
            }
            Forecast fore;
            int pos = 0;//posição do instance.
            for (int i = 0; size < data.size(); i++, size++) {
                fore = new Forecast();
                fuzzy.setValues(extractValues(data.subList(i, size), INSTANCE_KEY));
                Variable saida = fuzzy.getFis().getVariable(EngineTools.SAIDA);

                fore.setValorPrevisto(saida.getDefuzzifier().defuzzify());
                fore.setValorReal(data.get(size).get(pos));
                previsões.add(fore);
            }
        } catch (IOException ex) {
            Logger.getLogger(TesteEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Double[] dataToArray(Dataset data, int i, int j, int pos) {
        Double[] array = new Double[Math.abs(j - i)];
        for (int k = i, y = 0; k < j; k++, y++) {
            array[y] = data.get(k).get(pos);
        }
        return array;
    }

    public List<TableObjectInterface> getPrevisões() {
        return previsões;
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
