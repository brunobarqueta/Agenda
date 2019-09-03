
package Agenda;
import java.io.Serializable;
import java.util.Calendar;

public class Evento  implements Serializable{
    
    private int id;
    private Calendar dataHora;
    private String descricao;
    private int duracao;
    private int agenda;
    
    public Evento(Calendar datahora, String descricao, int duracao, int agenda){
        this.dataHora = datahora;
        this.descricao = descricao;
        this.duracao = duracao;
        this.agenda = agenda;

    }
    public String getDescricao(){
        return this.descricao;
    }
    public Calendar getDataHora(){
        return this.dataHora;
    }
    
    public int getDuracao(){
        return this.duracao;
    }
    
    public int getAgenda(){
        return this.agenda;
    }
}