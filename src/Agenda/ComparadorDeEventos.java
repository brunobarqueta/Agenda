
package Agenda;

import java.util.Comparator;
import Agenda.Evento;

public class ComparadorDeEventos implements Comparator<Evento> {
    @Override
    public int compare(Evento e1, Evento e2) {
        if (e1.getDataHora().getTimeInMillis() < e2.getDataHora().getTimeInMillis()) return -1;
        else if (e1.getDataHora().getTimeInMillis() > e2.getDataHora().getTimeInMillis()) return 1;
        else return 0;
    }
}
