package BD1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import br.com.joao.PreProcess.*;
import java.util.List;
import net.sf.javaml.core.Instance;

/**
 *
 * @author joao
 */
public class PreProcessing {

    public List<InstanceSortable> process(Mensure medidor,
            List<InstanceSortable> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (medidor.isClose(list.get(i), list.get(i + 1), InstanceSortable.MIM, InstanceSortable.MAX)) {
                Instance t = list.remove(i + 1);
                list.get(i).put(InstanceSortable.MAX, t.get(InstanceSortable.MAX));
                i--;
            }
        }
        setCentroide(medidor, list);
        return list;
    }

    private void setCentroide(Mensure medidor,
            List<InstanceSortable> list) {
        for (InstanceSortable i : list) {
            i.put(InstanceSortable.CORE, medidor.findCentroide(
                    medidor.getTermos(i.get(InstanceSortable.MIM), i.get(InstanceSortable.MAX))));
        }
    }

}
