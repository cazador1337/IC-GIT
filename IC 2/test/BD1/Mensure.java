package BD1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import br.com.joao.PreProcess.*;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.DatasetTools;

/**
 *
 * @author joao
 */
public class Mensure {

    private TreeMap<Double, Integer> floor, ceiling;
    private LinkedList<Double> fonte;
    private double deviation; //standard deviation

    Mensure(Dataset set) {
        setFonte(parseToList(set, 0));
        setDeviation(findDeviantio(set));
        setFloor(new TreeMap<>());
        setCeiling(new TreeMap<>());
        fillTrees(fonte, floor, ceiling);
    }

    private void setFonte(LinkedList<Double> fonte) {
        this.fonte = fonte;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }

    private double findDeviantio(Dataset set) {
        Instance result = DatasetTools.standardDeviation(set,
                DatasetTools.average(set));
        return result.get(0);
    }

    private LinkedList<Double> parseToList(Dataset set, int key) {
        if (set.size() <= 1) {
            throw new Error("Datase too small");
        }
        LinkedList<Double> aux = new LinkedList<>();
        for (Instance i : set) {
            aux.add(i.get(key));
        }
        return aux;
    }

    public double getDeviation() {
        return deviation;
    }

    private void setFloor(TreeMap<Double, Integer> floor) {
        this.floor = floor;
    }

    private void setCeiling(TreeMap<Double, Integer> ceiling) {
        this.ceiling = ceiling;
    }

    private void fillTrees(LinkedList<Double> fonte,
            TreeMap<Double, Integer> floor,
            TreeMap<Double, Integer> ceiling) {
        int i = 0;
        for (Double d : fonte) {
            floor.putIfAbsent(d, i);
            ceiling.put(d, i);
            i++;
        }
    }

    public boolean isClose(Instance a, Instance b, int... key) {
        double aC = findCentroide(getTermos(a.get(key[0]),
                a.get(key[key.length - 1])));
        double bC = findCentroide(getTermos(b.get(key[0]),
                b.get(key[key.length - 1])));
        double dif = Math.abs(aC - bC);
        return dif <= deviation;
    }

    public List<Double> getTermos(double a, double b) {
        int i = floor.get(floor.ceilingKey(a));
        int j = ceiling.get(ceiling.floorKey(b));
        return fonte.subList(i, j + 1);
    }

    public double findCentroide(List<Double> list) {
        double media = 0;
        for (Double d : list) {
            media += d;
        }
        return media / list.size();
    }

}
