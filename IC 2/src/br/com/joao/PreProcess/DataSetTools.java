/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joao.PreProcess;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;

/**
 *
 * @author joao
 */
public class DataSetTools {

    public Mensure newMensure(File source, double fator) throws IOException {
        PreProcessing tools = new PreProcessing();
        Mensure m = new Mensure(sortDataSet(newDataSet(source)));
        m.setDeviation(m.getDeviation() / fator);
        return m;
    }

    public Dataset sortDataSet(Dataset data) {//retorna um novo dataSet ele não muda o original
        return listToDataset(dataToSortlist(data));
    }

    public List<InstanceSortable> dataToSortlist(Dataset from) {
        List<InstanceSortable> result = new LinkedList<>();
        for (Instance instance : from) {
            result.add(new InstanceSortable(instance));
        }
        Collections.sort(result);
        return result;
    }
    
    public List<InstanceSortable> dataToSortlist(File from) throws IOException {        
        return dataToSortlist(newDataSet(from));
    }

    public Dataset listToDataset(List<InstanceSortable> from) {
        Dataset data = new DefaultDataset();
        data.addAll(from);
        return data;
    }

    public Dataset newDataSet(File soucer) throws IOException {
        Dataset data = FileHandler.loadDataset(soucer);
        return data;
    }

    public PreProcessing newPreProcessing() {
        return new PreProcessing();
    }
    
    public Dataset invertDataSet(Dataset from){
        Dataset data = new DefaultDataset();
        
        for (int i = from.size()-1; i >= 0; i--) {
            data.add(from.get(i));
        }
        
        return data;
    }

}
