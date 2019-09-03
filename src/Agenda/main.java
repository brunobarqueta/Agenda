package Agenda;
import java.util.Calendar;
import java.util.Scanner;
import Agenda.Evento;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class main {

	public static void main(String[] args) {
            agendaGUI form = new agendaGUI();
            form.setVisible(true);
        }
        public static boolean serialize(Agenda object){
          try{
          	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("C:\\Users\\Mateu\\Desktop\\"+object.getNome()+".txt"));
               oos.writeObject(object);
              oos.close();
              return true;
            } catch (Exception e){                
                System.out.println("aaaaaaaa");

                System.out.println(e.toString());
                return false;
            }
        }
//        public static Agenda desserialize (String nameAgenda){
//            try{
//                ObjectInputStream obj = new ObjectInputStream(new FileInputStream(nameAgenda)); 
//                Agenda newAgenda = (Agenda)obj.readObject();
//                obj.close();
//                return newAgenda;
//            }catch (Exception e){
//                return null;
//            }
//        }
        public static Agenda desserialize (String nameAgenda){
            try{
                ObjectInputStream obj = new ObjectInputStream(new FileInputStream(nameAgenda)); 
                Agenda newAgenda = (Agenda)obj.readObject();
                obj.close();
                return newAgenda;
            }catch (Exception e){
                return null;
            }
	
        }
}
