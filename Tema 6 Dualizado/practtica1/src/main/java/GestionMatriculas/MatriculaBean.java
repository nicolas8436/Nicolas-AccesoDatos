package GestionMatriculas;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // REPARADO: Usamos el Statement de sql, no el de beans
import java.util.EventListener;
import java.util.EventObject;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatriculaBean implements Serializable {

    // Propiedades del Bean
    protected String DNI;
    protected String NombreModulo;
    protected String Curso;
    protected double Nota;
    
    // Propiedades de estado y eventos
    protected boolean modo; // true = todas, false = DNI particular
    protected Vector<Matricula> Matriculas = new Vector<Matricula>();
    protected ModoModificadoListener receptor;

    // --- CLASES E INTERFACES INTERNAS ---

    // REPARADO: Añadido 'static' para que el Main pueda leer el Vector libremente sin errores de visibilidad
    public static class Matricula {
        public String dni;
        public String nombreModulo;
        public String curso;
        public double nota;

        public Matricula(String dni, String nombreModulo, String curso, double nota) {
            this.dni = dni;
            this.nombreModulo = nombreModulo;
            this.curso = curso;
            this.nota = nota;
        }
    }

    public class ModoModificadoEvent extends EventObject {
        protected boolean modo;

        public ModoModificadoEvent(Object source, boolean modo) {
            super(source);
            this.modo = modo;
        }

        public boolean getModo() {
            return modo;
        }
    }

    public interface ModoModificadoListener extends EventListener {
        public void capturarModoModificado(ModoModificadoEvent ev);
    }

    // --- CONSTRUCTOR ---
    public MatriculaBean() {
        try {
            recargarFilas(); 
        } catch (ClassNotFoundException ex) {
            this.DNI = "";
            this.NombreModulo = "";
            this.Curso = "";
            this.Nota = 0.0;
            Logger.getLogger(MatriculaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // --- MÉTODOS OBLIGATORIOS DE LA PRÁCTICA ---

    // Carga TODAS las matrículas en el Vector
    public void recargarFilas() throws ClassNotFoundException {
        try {
            Matriculas.clear();
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/control_matriculas", "root", "Root");
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from matriculas");
            
            while (rs.next()) {
                Matricula m = new Matricula(
                    rs.getString("DNI"),
                    rs.getString("NombreModulo"),
                    rs.getString("Curso"),
                    rs.getDouble("Nota")
                );
                Matriculas.add(m);
            }
            
            this.modo = true; 
            
            if (receptor != null) {
                receptor.capturarModoModificado(new ModoModificadoEvent(this, this.modo));
            }
            
            rs.close();
            s.close();
            con.close();
            
        } catch (SQLException e) {
            Logger.getLogger(MatriculaBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Carga las matrículas de un DNI concreto
    public void recargarDNI(String nDNI) throws ClassNotFoundException {
        try {
            Matriculas.clear();
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/control_matriculas", "root", "Root");
            
            PreparedStatement ps = con.prepareStatement("select * from matriculas where DNI = ?");
            ps.setString(1, nDNI);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Matricula m = new Matricula(
                    rs.getString("DNI"),
                    rs.getString("NombreModulo"),
                    rs.getString("Curso"),
                    rs.getDouble("Nota")
                );
                Matriculas.add(m);
            }
            
            this.modo = false; 
            
            if (receptor != null) {
                receptor.capturarModoModificado(new ModoModificadoEvent(this, this.modo));
            }
            
            rs.close();
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            Logger.getLogger(MatriculaBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Recupera en las propiedades el registro 'i' del vector
    public void seleccionarFila(int i) {
        if (i >= 0 && i < Matriculas.size()) {
            Matricula m = Matriculas.get(i);
            this.DNI = m.dni;
            this.NombreModulo = m.nombreModulo;
            this.Curso = m.curso;
            this.Nota = m.nota;
        }
    }

    // Añade una matrícula a partir de las propiedades actuales del componente
    public void addMatricula() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/control_matriculas", "root", "Root");
            PreparedStatement s = con.prepareStatement("insert into matriculas values (?,?,?,?)");
            
            s.setString(1, DNI);
            s.setString(2, NombreModulo);
            s.setString(3, Curso);
            s.setDouble(4, Nota);
            
            s.executeUpdate();
            
            s.close();
            con.close();
            
            if (this.modo) {
                recargarFilas();
            } else {
                recargarDNI(this.DNI);
            }
            
        } catch (SQLException e) {
            Logger.getLogger(MatriculaBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void addModoModificadoListener(ModoModificadoListener receptor) {
        this.receptor = receptor;
    }

    public void removeModoModificadoListener() {
        this.receptor = null;
    }

    // --- GETTERS Y SETTERS PROPIEDADES ---
    public String getDNI() { return DNI; }
    public void setDNI(String DNI) { this.DNI = DNI; }

    public String getNombreModulo() { return NombreModulo; }
    public void setNombreModulo(String nombreModulo) { this.NombreModulo = nombreModulo; }

    public String getCurso() { return Curso; }
    public void setCurso(String curso) { this.Curso = curso; }

    public double getNota() { return Nota; }
    public void setNota(double nota) { this.Nota = nota; }

    public boolean isModo() { return modo; }

    public Vector<Matricula> getMatriculas() { return Matriculas; }
}