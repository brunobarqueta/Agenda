
package Agenda;
import Agenda.Evento;
import Agenda.ComparadorDeEventos;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

public class Agenda implements Serializable{
    static boolean feriadosLigados = true;
    static Evento[] feriados = {
        new Evento(new GregorianCalendar(0000,1,1), "Ano novo", 24, 0),
        new Evento(new GregorianCalendar(0000,3,30), "Sexta-feira santa", 24, 0),
        new Evento(new GregorianCalendar(0000,4,1), "Páscoa", 24, 0),
        new Evento(new GregorianCalendar(0000,4,21), "Tiradentes", 24, 0),
        new Evento(new GregorianCalendar(0000,5,1), "Dia do trabalhador", 24,0),
        new Evento(new GregorianCalendar(0000,5,31), "Corpus Christi", 24, 0),
        new Evento(new GregorianCalendar(0000,9,20), "Dia da independência", 24, 0),
        new Evento(new GregorianCalendar(0000,10,12), "Nossa Senhora Aparecida", 24, 0),
        new Evento(new GregorianCalendar(0000,11,2), "Finados", 24, 0),
        new Evento(new GregorianCalendar(0000,11,15), "Proclamação da República", 24, 0),
        new Evento(new GregorianCalendar(0000,12,25), "Natal", 24, 0)
    };
    List<Evento> listaDeEventos = new ArrayList<Evento>();
    
    private int id;
    private String nome;
    
    public Agenda(String nome){
        this.nome = nome;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public Evento[] getFeriados(){
        return this.feriados;
    }
    
    public List<Evento> getEventosDia(Calendar dataPesquisa){
        List<Evento> eventosParaRetornar = new ArrayList<Evento>();
        
        for(int i = 0; i < listaDeEventos.size(); i++){
            if(listaDeEventos.get(i).getDataHora().get(Calendar.DAY_OF_MONTH) == dataPesquisa.get(Calendar.DAY_OF_MONTH) &&
                listaDeEventos.get(i).getDataHora().get(Calendar.MONTH) == dataPesquisa.get(Calendar.MONTH) &&
                listaDeEventos.get(i).getDataHora().get(Calendar.YEAR) == dataPesquisa.get(Calendar.YEAR)){
                
                eventosParaRetornar.add(listaDeEventos.get(i));
            }
        }
        
        if(feriadosLigados){
            for(int i = 0; i < feriados.length; i++){
                if(feriados[i].getDataHora().get(Calendar.DAY_OF_MONTH) == dataPesquisa.get(Calendar.DAY_OF_MONTH) &&
                   feriados[i].getDataHora().get(Calendar.MONTH) == dataPesquisa.get(Calendar.MONTH)){

                    feriados[i].getDataHora().set(Calendar.YEAR, dataPesquisa.get(Calendar.YEAR));

                    eventosParaRetornar.add(feriados[i]);
                }
            }
        }
        
        Collections.sort(eventosParaRetornar, new ComparadorDeEventos());
        
        return eventosParaRetornar;
    }
    
    public List<Evento> getEventosMes(Calendar dataPesquisa){
        List<Evento> eventosParaRetornar = new ArrayList<Evento>();
        
        for(int i = 0; i < listaDeEventos.size(); i++){
            if(listaDeEventos.get(i).getDataHora().get(Calendar.MONTH) == dataPesquisa.get(Calendar.MONTH) &&
                listaDeEventos.get(i).getDataHora().get(Calendar.YEAR) == dataPesquisa.get(Calendar.YEAR)){
                eventosParaRetornar.add(listaDeEventos.get(i));
            }
        }
        if(feriadosLigados){
            for(int i = 0; i < feriados.length; i++){
                if(feriados[i].getDataHora().get(Calendar.MONTH) == dataPesquisa.get(Calendar.MONTH)){

                    feriados[i].getDataHora().set(Calendar.YEAR, dataPesquisa.get(Calendar.YEAR));

                    eventosParaRetornar.add(feriados[i]);
                }
            }
        }
        
        Collections.sort(eventosParaRetornar, new ComparadorDeEventos());

        return eventosParaRetornar;
    }
    
    public boolean CriaEvento(Calendar dataHora, String descricao, int duracao){
        Evento evento = new Evento(dataHora, descricao, duracao, getId());
        if (evento instanceof Evento){
            if(this.listaDeEventos.add(evento)){
                return true;
            }
        }
        return false;
    }
    
    public Evento RemoveEvento(Evento evento){
        this.listaDeEventos.remove(evento);
        return evento;
    }
    
    public List<Evento> getTodosEventos(){
        
        List<Evento> eventosParaRetornar = listaDeEventos;
        
        Collections.sort(eventosParaRetornar, new ComparadorDeEventos());

        return eventosParaRetornar;
    }
}
