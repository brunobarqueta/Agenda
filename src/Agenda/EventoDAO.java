
package Agenda;

import Agenda.ConnectionFactory;
import Agenda.Evento;
import java.io.Serializable;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Mateu
 */
public class EventoDAO  implements Serializable{
    private Connection conn = null;
    
    public EventoDAO(){
        conn = ConnectionFactory.getConnection();
    }
    
    public boolean create(Evento evento){
        
        String dia = Integer.toString(evento.getDataHora().get(Calendar.DAY_OF_MONTH));
        String mes = Integer.toString(evento.getDataHora().get(Calendar.MONTH));
        String ano = Integer.toString(evento.getDataHora().get(Calendar.YEAR));
        String hora = Integer.toString(evento.getDataHora().get(Calendar.HOUR_OF_DAY));
        String minuto = Integer.toString(evento.getDataHora().get(Calendar.MINUTE));
          
        String sql = "INSERT INTO evento (descricao, datahora, duracao, agenda) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, evento.getDescricao());
            stmt.setString(2, dia+"/"+mes+"/"+ano+" "+hora+":"+minuto+":00");
            stmt.setString(3, Integer.toString(evento.getDuracao()));
            stmt.setString(4, Integer.toString(evento.getAgenda()));

            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return false;
        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }
    public List<Evento> getAll(int agenda){
        String sql = "SELECT * FROM evento WHERE agenda = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Evento> listaDeEventos = new ArrayList<>();    
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Integer.toString(agenda));
            rs = stmt.executeQuery();
            
            while(rs.next()){
                String[] dataHoraSplit = rs.getString("datahora").split(" ");
                String[] dataSplit = dataHoraSplit[0].split("-");
                Calendar gc = new GregorianCalendar(Integer.parseInt(dataSplit[2]),Integer.parseInt(dataSplit[1]),Integer.parseInt(dataSplit[0]));
                Evento ev = new Evento(gc, rs.getString("descricao"), Integer.parseInt(rs.getString("duracao")), Integer.parseInt(rs.getString("agenda")));
                listaDeEventos.add(ev);
            }

        } catch (SQLException ex) {
            System.err.println("Erro Create");
        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        
        return listaDeEventos;
    }
    public boolean delete(int id){
        String sql = "DELETE FROM evento WHERE id = ?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Integer.toString(id));
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return false;
        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }
    
    public List<Evento> getMes(String mes, String ano, int agenda){
        String sql = "SELECT * FROM evento WHERE month(datahora) = ? AND (year(datahora) = ? OR year(datahora) = 0001) AND (agenda = ? OR agenda = 0)";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Evento> listaDeEventos = new ArrayList<>();    
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, mes);
            stmt.setString(2, ano);
            stmt.setInt(3, agenda);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                if(!Agenda.feriadosLigados && rs.getInt("agenda") == 0) {
                    continue;
                };
                
                String[] dataHoraSplit = rs.getString("datahora").split(" ");
                String[] dataSplit = dataHoraSplit[0].split("-");
                String[] horaSplit = dataHoraSplit[1].split(":");
                Calendar gc = new GregorianCalendar(Integer.parseInt(dataSplit[0]),Integer.parseInt(dataSplit[1]),Integer.parseInt(dataSplit[2]), Integer.parseInt(horaSplit[0]), Integer.parseInt(horaSplit[1]));
                Evento ev = new Evento(gc, rs.getString("descricao"), Integer.parseInt(rs.getString("duracao")), Integer.parseInt(rs.getString("agenda")));
                listaDeEventos.add(ev);
            }

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        
        return listaDeEventos;
    }
    public List<Evento> getDia(String dia, String mes, String ano, int agenda){
        String sql = "SELECT * FROM evento WHERE day(datahora) = ? AND month(datahora) = ? AND (year(datahora) = ? OR year(datahora) = 0001) AND (agenda = ? OR agenda = 0)";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Evento> listaDeEventos = new ArrayList<>();    
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, dia);
            stmt.setString(2, mes);
            stmt.setString(3, ano);
            stmt.setInt(4, agenda);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                if(!Agenda.feriadosLigados && rs.getInt("agenda") == 0) {
                    continue;
                };
                
                String[] dataHoraSplit = rs.getString("datahora").split(" ");
                String[] dataSplit = dataHoraSplit[0].split("-");
                String[] horaSplit = dataHoraSplit[1].split(":");
                Calendar gc = new GregorianCalendar(Integer.parseInt(dataSplit[0]),Integer.parseInt(dataSplit[1]),Integer.parseInt(dataSplit[2]), Integer.parseInt(horaSplit[0]), Integer.parseInt(horaSplit[1]));
                Evento ev = new Evento(gc, rs.getString("descricao"), Integer.parseInt(rs.getString("duracao")), Integer.parseInt(rs.getString("agenda")));
                listaDeEventos.add(ev);
            }

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        
        return listaDeEventos;
    }
}
