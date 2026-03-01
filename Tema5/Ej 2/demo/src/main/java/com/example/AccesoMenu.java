package com.example;

import java.util.Scanner;
import org.exist.xmldb.DatabaseImpl;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XQueryService;

public class AccesoMenu {
    private Collection col = null;
    private Scanner scanner = new Scanner(System.in);
    
    public void conectar() {
        try {
            DatabaseManager.registerDatabase(new DatabaseImpl());
            col = DatabaseManager.getCollection(
                "xmldb:exist://localhost:8080/exist/xmlrpc/db/prueba_collection", 
                "admin", 
                ""
            );
            System.out.println("Conectado a eXist-db");
        } catch (Exception e) {
            System.out.println("Error conexión: " + e.getMessage());
        }
    }
    
    public void desconectar() {
        try { col.close(); } catch (Exception e) { }
    }
    
    public void mostrarMenus() {
        try {
            XPathQueryService service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet rs = service.query("doc('/db/prueba_collection/menus.xml')//menu");
            ResourceIterator it = rs.getIterator();
            while (it.hasMoreResources()) 
                System.out.println(it.nextResource().getContent());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void insertMenu() {
        try {
            System.out.println("Nombre:"); String nom = scanner.nextLine();
            System.out.println("Primer plato:"); String p1 = scanner.nextLine();
            System.out.println("Segundo plato:"); String p2 = scanner.nextLine();
            System.out.println("Postre:"); String post = scanner.nextLine();
            System.out.println("Precio:"); String pre = scanner.nextLine();
            
            XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
            String xq = "update insert <menu><nombre>" + nom + "</nombre>" +
                       "<primerPlato>" + p1 + "</primerPlato>" +
                       "<segundoPlato>" + p2 + "</segundoPlato>" +
                       "<postre>" + post + "</postre>" +
                       "<precio>" + pre + "</precio></menu> " +
                       "into doc('/db/prueba_collection/menus.xml')/restaurante";
            service.query(xq);
            System.out.println("Insertado");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void buscarMenu(String nombre) {
        try {
            XPathQueryService service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet rs = service.query("doc('/db/prueba_collection/menus.xml')//menu[nombre='" + nombre + "']");
            ResourceIterator it = rs.getIterator();
            while (it.hasMoreResources()) 
                System.out.println(it.nextResource().getContent());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void borrarMenu(String nombre) {
        try {
            XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
            service.query("update delete doc('/db/prueba_collection/menus.xml')//menu[nombre='" + nombre + "']");
            System.out.println("Borrado: " + nombre);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void actualizaPrecio(int inc) {
        try {
            XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
            service.query("for $p in doc('/db/prueba_collection/menus.xml')//precio " +
                         "return update value $p with $p * (1 + " + inc + " div 100)");
            System.out.println("Precios actualizados " + inc + "%");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void buscarMasBaratoQue(int precio) {
        try {
            XPathQueryService service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            String xq = "for $m in doc('/db/prueba_collection/menus.xml')//menu[number(precio) < " + precio + "] " +
                       "order by number($m/precio) " +
                       "return concat($m/nombre, ' - ', $m/precio, '€')";
            ResourceSet rs = service.query(xq);
            ResourceIterator it = rs.getIterator();
            while (it.hasMoreResources()) 
                System.out.println(it.nextResource().getContent());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}