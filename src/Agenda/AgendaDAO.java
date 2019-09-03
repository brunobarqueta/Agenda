
package Agenda;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgendaDAO  implements Serializable{
    
    private Connection conn = null;
    
    public AgendaDAO(){
        conn = ConnectionFactory.getConnection();
    }
    
    public boolean create(Agenda agenda){
        String sql = "INSERT INTO agenda (nome) VALUES (?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, agenda.getNome());
            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println("Erro Create");
            return false;
        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }
    public List<Agenda> getAll(){
        String sql = "SELECT id, nome FROM agenda";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Agenda> listaAgendas = new ArrayList<>();    
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Agenda ag = new Agenda(rs.getString("nome"));
                ag.setId(Integer.parseInt(rs.getString("id")));
                listaAgendas.add(ag);
            }

        } catch (SQLException ex) {
            System.err.println("Erro Create");
        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        
        return listaAgendas;
    }
    public boolean delete(int id){
        String sql = "DELETE FROM agenda WHERE id = ?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Integer.toString(id));
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro Create");
            return false;
        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }
    
    public Agenda getAgenda(int id){
        String sql = "SELECT * FROM agenda WHERE id = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;   
        List<Agenda> listaAgendas = new ArrayList<>();
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Integer.toString(id));
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Agenda ag = new Agenda(rs.getString("nome"));
                EventoDAO eventoDAO = new EventoDAO();
                List<Evento> eventoLista = new ArrayList<>();
                eventoLista = eventoDAO.getAll(ag.getId());
                ag.listaDeEventos = eventoLista;
                
                listaAgendas.add(ag);
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        System.out.println(listaAgendas.get(0));
                System.out.println(listaAgendas.get(0).getNome());

        return listaAgendas.get(0);
    }
}
