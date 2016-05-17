/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD2;

import br.com.joao.PreProcess.InstanceSortable;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.DatasetTools;

/**
 *
 * @author joao
 */
public class Mensure {

    private static final int SIZE = 3, SUM = 4;

    private double deviation; //standard deviation

    Mensure(Dataset set) {
        setDeviation(findDeviantio(set));
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }

    private double findDeviantio(Dataset set) {
        Instance result = DatasetTools.standardDeviation(set,
                DatasetTools.average(set));
        return result.get(0);
    }

    public double getDeviation() {
        return deviation;
    }

    public boolean isClose(Instance a, Instance b) {
        double dif = Math.abs(findCentroide(a) - findCentroide(b));
        return dif <= deviation;
    }
    
    public void update(Instance a, Instance b) {
        a.put(SUM, a.get(SUM) + b.get(SUM));
        a.put(SIZE, a.get(SIZE)+1);
    }

    public double findCentroide(Instance a) {
        if(a.get(SUM) == 0){
            a.put(SUM, a.get(InstanceSortable.CORE));
            a.put(SIZE, 1.0);
        }
        return a.get(SUM) / a.get(SIZE);
    }

}
