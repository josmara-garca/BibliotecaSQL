import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class LibroLogica {

    private static final String URL = "jdbc:mysql://db.juanavila.es:3306/josemaria_biblioteca";
    private static final String USER = "root";
    private static final String PASS = "";

    Scanner sc = new Scanner(System.in);
    int opcion;

    public void iniciar (){
        mostrarMenu();

    }

    public void mostrarMenu (){
        System.out.println("1- Crear libro");
        System.out.println("2- Mostrar libro");
        System.out.println("3- Crear autor");
        System.out.println("4- Mostrar autor");
        System.out.println("5- Crear socio");
        System.out.println("6- Mostrar socio");
        System.out.println("7- Realizar prestamo");
        System.out.println("8- Mostrar prestamos realizados por un socio");
        System.out.println("9- Mostrar libros de un autor");
        System.out.println("10- Asociar autor");
        System.out.println("0- Salir");
        ejecutarOpcion();
    }

    public void ejecutarOpcion (){
        do {
            System.out.print("Elige opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion){
                case 0:
                    salir ();
                    break;
                case 1:
                    crearLibro ();
                    break;
                case 2:
                    mostrarLibro ();
                    break;
                case 3:
                    crearAutor ();
                    break;
                case 4:
                    mostrarAutor ();
                    break;
                case 5:
                    crearSocio ();
                    break;
                case 6:
                    mostrarSocio ();
                    break;
                case 7:
                    realizarPrestamo ();
                    break;
                case 8:
                    mostrarPrestamoSocio ();
                    break;
                case 9:
                    mostrarLibrosAutor ();
                    break;
                case 10:
                    asociarAutor ();
                    break;
                    default:
                        System.out.println("Opcion no valida; entre 0 y 10");
                        System.out.println("Por favor, elija otra");

            }


        }while (opcion != 0);
    }

    private void crearLibro (){
        System.out.print("Titulo: ");
        String titulo = sc.nextLine();
        System.out.print("isbn: ");
        String isbn = sc.nextLine();
        System.out.print("Numero de paginas:");
        int num_pag = sc.nextInt();
        sc.nextLine();
        try{
            Connection conn = DriverManager.getConnection(URL,USER,PASS);

            PreparedStatement ps = conn.prepareStatement("insert into libro (titulo, isbn, num_pag) values (?,?,?");
            ps.setString(1,titulo);
            ps.setString(2,isbn);
            ps.setInt(3,num_pag);
            ps.executeUpdate();
            System.out.println("operacion con exito");

        }catch (Exception E){
            System.out.println(E.getMessage());
        }

    }

    private void mostrarLibro (){
        System.out.println("ISBN: ");
        String isbn = sc.nextLine();
       try {
           Connection conn = DriverManager.getConnection(URL,USER,PASS);
           PreparedStatement ps = conn.prepareStatement("SELECT * FROM libro WHERE isbn = ?");
           ps.setString(1,isbn);
           ResultSet rs = ps.executeQuery();
           if(rs.next()) {
               String titulo = rs.getString("titulo");
               int numPag = rs.getInt("num_pag");

               System.out.println(String.format("ISBN: %s | Titulo: %s | Num pag: %d",isbn,titulo,numPag));
           }
           else {
               System.err.println("El libro no existe");
           }

       }catch (Exception E){
           System.out.println(E.getMessage());
       }

    }

    private void crearAutor (){
        System.out.print("DNI: ");
        String dni = sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ciudad nacimiento: ");
        String ciudad_nac = sc.nextLine();
        try{
            Connection cnn = DriverManager.getConnection(URL, USER, PASS);

            PreparedStatement ps = cnn.prepareStatement("insert into autor (dni, nombre_completo, ciudad_nac) values (?,?,?)");
            ps.setString(1, dni);
            ps.setString(2,nombre);
            ps.setString(3, ciudad_nac);
            ps.executeUpdate();
            System.out.println("operacion con exito");
        }catch (Exception E){
            System.out.println(E.getMessage());
        }

    }

    private void mostrarAutor (){
        System.out.print("dni: ");
        String dni = sc.nextLine();
        try{
            Connection conn = DriverManager.getConnection(URL,USER,PASS);
            PreparedStatement ps = conn.prepareStatement("select from autor where dni = ?");
            ps.setString(1,dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String cuidad_nac = rs.getString("ciudad_nac");
                String nombre_completo = rs.getString("nombre_completo");

                System.out.println(String.format("dni: %s, ciudad_nacimiento: %s, nombre_completo: %s" , dni, cuidad_nac, nombre_completo));
            }else{
                System.err.println("El libro no existe");
            }

        }catch (Exception E){
            System.out.println(E.getMessage());
        }


    }

    private void crearSocio (){
        System.out.print("nombre: ");
        String nombre = sc.nextLine();
        System.out.print("direccion: ");
        String direccion = sc.nextLine();
        System.out.print("num: ");
        int num = sc.nextInt();
        sc.nextLine();
        try{
            Connection conn = DriverManager.getConnection(URL,USER,PASS);

            PreparedStatement ps = conn.prepareStatement("insert into socio (nombre,direccion,num) values (?,?,?)");
            ps.setString(1,nombre);
            ps.setString(2,direccion);
            ps.setInt(3,num);
            ps.executeUpdate();
            System.out.println("operacion con exito");
        }catch (Exception E){
            System.out.println(E.getMessage());
        }

    }

    private void mostrarSocio (){
        System.out.print("Socio num: ");
        int num = sc.nextInt();
        sc.nextLine();
        try{
            Connection conn = DriverManager.getConnection(URL,USER,PASS);
            PreparedStatement ps = conn.prepareStatement("select from socio where num = ?");
            ps.setInt(1, num);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String direccion = rs.getString("direccion");
                String nombre = rs.getString("nombre");

                System.out.println(String.format("El numero es: %d, La direccion es: %s, El nombre es: %s", num , direccion, nombre));
            }else{
                System.err.println("El autor no existe");
            }

        }catch (Exception E){
            System.out.println(E.getMessage());
        }

    }

    private  Date solicitarFecha (){
        java.util.Date fecha = null;
        do{
            System.out.print("introduce el dia:");
            int dia = sc.nextInt();
            sc.nextLine();
            System.out.print("introduce mes: ");
            int mes = sc.nextInt();
            sc.nextLine();
            System.out.print("introduce año: ");
            int anyo = sc.nextInt();
            sc.nextLine();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            try {
                fecha = df.parse(String.format("%d,%d,%d", dia, mes, anyo));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        while(fecha == null);
        return new Date(fecha.getTime());

    }

    private void realizarPrestamo (){
        System.out.print("isbn_libro; ");
        String isbn = sc.nextLine();
        System.out.print("num_socio: ");
        int num_socio = sc.nextInt();
        sc.nextLine();
        Date fecha_prestamo = solicitarFecha();






    }

    private void mostrarPrestamoSocio (){

    }

    private void mostrarLibrosAutor (){


    }

    private void asociarAutor (){
        System.out.print("isbn: ");
        String isbn = sc.nextLine();
        System.out.print("dni: ");
        String dni = sc.nextLine();

        try{
            Connection conn = DriverManager.getConnection(URL,USER,PASS);
            PreparedStatement ps = conn.prepareStatement("insert into autor_libro (isbn, dni_autor) values (?,?))");
            ps.setString(1, isbn);
            ps.setString(2, dni);
            ps.executeUpdate();
            System.out.println("operacion con exito");
        }catch (Exception E){
            System.out.println(E.getMessage());
        }

    }

    private void salir (){

    }



}
