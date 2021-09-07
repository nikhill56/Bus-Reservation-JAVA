import javafx.application.*;
import java.io.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import java.sql.*;
import javafx.scene.control.TableView;
import javafx.collections.*;
import javafx.beans.*;

public class App extends Application {
    int verify = 0;
    int value = 0;
    int user_id = 1;
    int booking_no = 101;
    Font font = Font.font("Arial", 14);
    Font fon = Font.font("Arial", 18);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Setting the Primary Stage
        primaryStage.setTitle("Transport Management System");
        GridPane root = new GridPane();

        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);

        Label l = new Label("Login Details");
        Label username = new Label("User");
        Label Password = new Label("Password");
        TextField t1 = new TextField();
        PasswordField t2 = new PasswordField();
        Button submit = new Button("Login");
        Button create = new Button("Create User");
        Button close = new Button("Close");

        root.add(l, 0, 1);
        l.setFont(fon);
        l.setWrapText(true);

        root.add(username, 0, 2);
        username.setFont(font);

        root.add(Password, 0, 3);
        Password.setFont(font);

        Label l1 = new Label();
        l1.setFont(font);

        root.add(t1, 1, 2);
        t1.setFont(font);

        root.add(t2, 1, 3);
        t2.setFont(font);

        root.add(submit, 1, 4);
        submit.setFont(font);

        root.add(create, 1, 5);
        create.setFont(font);

        root.add(close, 1, 6);
        close.setFont(font);

        root.add(l1, 1, 7);// prints incorrect username or password
        l1.setFont(font);

        create.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent ae) {
                // TODO Auto-generated method stub
                try {
                    Class c1 = Class.forName("com.mysql.cj.jdbc.Driver");

                    final String JdbcDriver = "com.mysql.cj.jdbc.Driver";
                    final String user = "root";
                    final String pass = "password";
                    final String db_url = "jdbc:mysql://127.0.0.1:3306/transport";
                    Connection con = DriverManager.getConnection(db_url, user, pass);
                    String query = "Insert into users values (?,?,?);";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setInt(1, user_id);
                    ps.setString(2, t1.getText());
                    ps.setString(3, t2.getText());

                    ps.execute();
                    System.out.println("User Created");
                    user_id++;
                    con.close();
                } catch (Exception e) {
                    user_id++;
                    handle(ae);

                }
            }

        });

        close.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent ar) {
                // TODO Auto-generated method stub
                primaryStage.close();// close the app
            }

        });

        submit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent ae) {
                // TODO Auto-generated method stub
                try {
                    Class c1 = Class.forName("com.mysql.cj.jdbc.Driver");

                    final String JdbcDriver = "com.mysql.cj.jdbc.Driver";
                    final String user = "root";
                    final String pass = "password";
                    final String db_url = "jdbc:mysql://127.0.0.1:3306/transport";
                    Connection con = DriverManager.getConnection(db_url, user, pass);
                    String query = "Select * from users where username=? and password=?;";// check if present in table
                    PreparedStatement ps = con.prepareStatement(query);
                    String query1 = "select * from routes;";
                    PreparedStatement st1 = con.prepareStatement(query1);
                    ps.setString(1, t1.getText());
                    ps.setString(2, t2.getText());

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        verify = 1;// set verify =1
                    }
                    ResultSet rs1 = st1.executeQuery();// to print routes on console
                    if (verify == 1) {

                        System.out.println("Start\t   Destination\tBus No\tRoute No      Price");
                        while (rs1.next()) {
                            System.out.println("" + rs1.getString(1) + "  " + rs1.getString(2) + "\t" + rs1.getString(3)
                                    + "\t" + rs1.getString(4) + "\t\t" + rs1.getString(5));
                        }
                    }
                    con.close();
                } catch (Exception e) {

                }
                if (verify == 0) {
                    // Closes application
                    l1.setText("Incorrect Username or Password");// this is for l1.setText()

                }
                if (verify == 1) {
                    primaryStage.close();

                    Stage Menu = new Stage();
                    GridPane menubar = new GridPane();
                    menubar.setAlignment(Pos.CENTER);
                    menubar.setHgap(10);
                    menubar.setVgap(10);
                    menubar.setPadding(new Insets(25, 25, 25, 25));
                    Scene scene1 = new Scene(menubar, 400, 400);
                    Menu.setScene(scene1);
                    Menu.setTitle("Menu");

                    Button Reserve = new Button("Reservation");
                    Button Cancel = new Button("Cancellation");
                    Button Show = new Button("Show all Passengers");
                    Button avail = new Button("check availability");
                    Button Exit = new Button("Exit");

                    Reserve.setFont(font);
                    Cancel.setFont(font);
                    Show.setFont(font);
                    Exit.setFont(font);
                    avail.setFont(font);

                    menubar.add(Reserve, 0, 1);
                    menubar.add(Cancel, 0, 2);
                    menubar.add(Show, 0, 3);
                    menubar.add(avail, 0, 4);
                    menubar.add(Exit, 0, 5);

                    Reserve.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent ae) {
                            // TODO Auto-generated method stub

                            Stage res = new Stage();// creating a new window
                            GridPane R = new GridPane();
                            Scene RScene = new Scene(R, 400, 400);
                            res.setScene(RScene);// passing scene's obj to stage
                            res.setTitle("Reservations");// setting the stage title

                            // Setting all Scene Elements
                            R.setAlignment(Pos.CENTER);
                            R.setHgap(10);
                            R.setVgap(10);
                            R.setPadding(new Insets(25, 25, 25, 25));

                            // Setting all Tables
                            Label fname = new Label("First Name");
                            Label gender = new Label("Gender");
                            Label lname = new Label("Last Name");
                            Label start = new Label("Start Point");
                            Label end = new Label("Destination");
                            // Label ph=new Label("Phone Number");
                            Label age = new Label("Age");
                            Label inform = new Label();

                            // Inserting Information
                            TextField tfname = new TextField();
                            TextField tlname = new TextField();
                            TextField tage = new TextField();
                            TextField tgender = new TextField();
                            TextField tstart = new TextField();
                            TextField tend = new TextField();

                            // Setting Fonts
                            fname.setFont(font);
                            lname.setFont(font);
                            start.setFont(font);
                            gender.setFont(font);
                            // ph.setFont(font);
                            end.setFont(font);
                            age.setFont(font);
                            tfname.setFont(font);
                            tlname.setFont(font);
                            tage.setFont(font);
                            tgender.setFont(font);
                            tstart.setFont(font);
                            tend.setFont(font);

                            Button submit1 = new Button("Submit");
                            Button Close = new Button("Cancel Booking");

                            // Adding all Fields
                            R.add(fname, 0, 1);
                            R.add(lname, 0, 2);
                            R.add(gender, 0, 3);
                            R.add(age, 0, 4);
                            R.add(start, 0, 5);
                            R.add(end, 0, 6);
                            R.add(tfname, 1, 1);
                            R.add(tlname, 1, 2);
                            R.add(tgender, 1, 3);
                            R.add(tage, 1, 4);
                            R.add(tstart, 1, 5);
                            R.add(tend, 1, 6);
                            R.add(submit1, 1, 7);
                            R.add(Close, 1, 8);
                            R.add(inform, 1, 9);// displays the error msg

                            submit1.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent ae) {
                                    // TODO Auto-generated method stub

                                    String tbfname = tfname.getText();
                                    String tblname = tlname.getText();
                                    String tbgender = tgender.getText();
                                    String tbage = tage.getText();
                                    String tbstart = tstart.getText();
                                    String tbend = tend.getText();

                                    try {
                                        Class c1 = Class.forName("com.mysql.cj.jdbc.Driver");

                                        final String JdbcDriver = "com.mysql.cj.jdbc.Driver";
                                        final String user = "root";
                                        final String pass = "password";
                                        final String db_url = "jdbc:mysql://127.0.0.1:3306/transport";
                                        Connection con = DriverManager.getConnection(db_url, user, pass);

                                        String query = "Select route_id from routes where start=? and dest=?;";
                                        PreparedStatement st = con.prepareStatement(query);
                                        st.setString(1, tbstart);
                                        st.setString(2, tbend);
                                        ResultSet r = st.executeQuery();
                                        if (r.next()) {
                                            value = 1;
                                        }
                                        if (value == 0) {
                                            inform.setText("There are no transports heading there");

                                        }
                                        if (value == 1) {

                                            String query1 = "INSERT INTO passengers VALUES(?,?,?,?,?,?);";
                                            String query3 = "INSERT INTO allotment VALUES(?,?,?,?,?);";

                                            PreparedStatement ps = con.prepareStatement(query1);
                                            ps.setString(1, tbfname);
                                            ps.setString(2, tblname);
                                            ps.setString(3, tbage);
                                            ps.setString(4, tbgender);
                                            ps.setString(5, tbstart);
                                            ps.setString(6, tbend);
                                            // ps.executeUpdate();
                                            ps.execute();
                                            System.out.println("Inserted Successfully");
                                            String query2 = "Select bus_no,route_id,price from routes where start=? and dest=?;";// display
                                                                                                                                 // busno
                                                                                                                                 // nd
                                                                                                                                 // rid
                                                                                                                                 // on
                                                                                                                                 // console

                                            PreparedStatement stmt = con.prepareStatement(query2);
                                            stmt.setString(1, tbstart);
                                            stmt.setString(2, tbend);
                                            ResultSet rs = stmt.executeQuery();
                                            int bus_no = 0, route_id = 0;

                                            while (rs.next()) {
                                                bus_no = rs.getInt(1);
                                                route_id = rs.getInt(2);

                                                System.out.println("BUS NO : " + bus_no + "\n Route No : " + route_id
                                                        + "\n Price : " + rs.getString(3));

                                            }

                                            PreparedStatement ps3 = con.prepareStatement(query3);
                                            ps3.setString(1, tbfname);
                                            ps3.setString(2, tbstart);
                                            ps3.setString(3, tbend);
                                            ps3.setInt(4, bus_no);
                                            ps3.setInt(5, route_id);

                                            ps3.execute();

                                            String query4 = "update availibilty set seats = seats-1 where bus_no =? and route_id = ?; ";
                                            PreparedStatement smt = con.prepareStatement(query4);

                                            smt.setInt(1, bus_no);
                                            smt.setInt(2, route_id);

                                            // execute the java preparedstatement
                                            smt.executeUpdate();

                                            con.close();

                                            res.close();// close the stage/window
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }

                            });

                            Close.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent ae) {
                                    // TODO Auto-generated method stub
                                    res.close();
                                }

                            });

                            res.show();

                        }

                    }); // TODO CANCELATION
                    Cancel.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent ae) {
                            // TODO Auto-generated method stub

                            Stage Cancel = new Stage();
                            Cancel.setTitle("Cancellations");
                            GridPane root2 = new GridPane();
                            Scene scene = new Scene(root2, 400, 400);
                            Cancel.setScene(scene);

                            root2.setAlignment(Pos.CENTER);
                            root2.setHgap(10);
                            root2.setVgap(10);
                            root2.setPadding(new Insets(25, 25, 25, 25));

                            Label passen = new Label("Passenger Name");
                            Label rid = new Label("Route ID");

                            TextField t1 = new TextField();
                            TextField t2 = new TextField();

                            Button Can = new Button("Cancel Seat");

                            root2.add(passen, 0, 1);
                            root2.add(rid, 0, 2);
                            root2.add(t1, 1, 1);
                            root2.add(t2, 1, 2);
                            root2.add(Can, 1, 3);

                            Can.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent ae) {
                                    // TODO Auto-generated method stub
                                    try {
                                        int routeid = 0;
                                        routeid = Integer.parseInt(t2.getText());

                                        Class c1 = Class.forName("com.mysql.cj.jdbc.Driver");

                                        final String JdbcDriver = "com.mysql.cj.jdbc.Driver";
                                        final String user = "root";
                                        final String pass = "password";
                                        final String db_url = "jdbc:mysql://127.0.0.1:3306/transport";
                                        Connection con = DriverManager.getConnection(db_url, user, pass);
                                        String query1 = "select * from passengers where Fname = ?;";
                                        PreparedStatement ps1 = con.prepareStatement(query1);
                                        ps1.setString(1, t1.getText());
                                        ResultSet rs1 = ps1.executeQuery();
                                        int i = 0;
                                        while (rs1.next()) {

                                            i = 1;

                                            String query = "delete from passengers where Start=Any(select start from routes) and Dest=Any(select dest from routes) and Fname=?; ";

                                            PreparedStatement ps = con.prepareStatement(query);

                                            ps.setString(1, t1.getText());
                                            ps.execute();
                                            System.out.println("Passenger Booking Cancelled");

                                            String query4 = "update availibilty set seats = seats+1 where route_id = ?; ";
                                            PreparedStatement smt = con.prepareStatement(query4);

                                            smt.setInt(1, routeid);

                                            // execute the java preparedstatement
                                            smt.executeUpdate();

                                            String query2 = "delete from allotment where Fname=? and route_id =?; ";
                                            PreparedStatement ps2 = con.prepareStatement(query2);
                                            ps2.setString(1, t1.getText());
                                            ps2.setString(2, t2.getText());
                                            ps2.execute();

                                            Cancel.close();

                                        }
                                        if (i == 0)
                                            System.out.println("Passenger not found");
                                    }

                                    catch (Exception e) {

                                    }
                                }

                            });

                            Cancel.show();
                        }

                    });
                    Show.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent ae) {
                            try {
                                Class c1 = Class.forName("com.mysql.cj.jdbc.Driver");

                                final String JdbcDriver = "com.mysql.cj.jdbc.Driver";
                                final String user = "root";
                                final String pass = "password";
                                final String db_url = "jdbc:mysql://127.0.0.1:3306/transport";
                                Connection con = DriverManager.getConnection(db_url, user, pass);
                                File fw = new File("Passengers.txt");// this is a new file
                                FileWriter f = new FileWriter(fw);// filewriter opens the file
                                PrintWriter pw = new PrintWriter(f);// inserts into the file
                                Statement stmt = con.createStatement();
                                String query = "select * from passengers;";
                                ResultSet rs = stmt.executeQuery(query);
                                System.out.println("Firstname LastName Age Gender Boarding Point Destination");
                                pw.println("Firstname \t LastName \t Age \t Gender \t Boarding Point \t Destination");// prints
                                                                                                                      // to
                                                                                                                      // the
                                                                                                                      // file
                                while (rs.next()) {
                                    System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "  \t  "
                                            + rs.getString(3) + "   \t  " + rs.getString(4) + "  \t  " + rs.getString(5)
                                            + "  \t   " + rs.getString(6));
                                    pw.println(rs.getString(1) + "\t " + rs.getString(2) + "\t" + rs.getString(3) + "\t"
                                            + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6));
                                }
                                pw.close();
                            } catch (Exception e) {

                            }

                        }

                    });
                    Exit.setOnAction(new EventHandler<ActionEvent>() {

                        public void handle(ActionEvent ae) {
                            Menu.close();// first menu will close
                            primaryStage.close();// app will close
                        }
                    });

                    avail.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent ae) {
                            Stage ava = new Stage();
                            GridPane root3 = new GridPane();
                            Scene s = new Scene(root3, 400, 400);
                            ava.setTitle("availability");
                            ava.setScene(s);
                            Label l1 = new Label("bus no");
                            Label l2 = new Label("route id");
                            Label l3 = new Label();// to print the no of seats

                            l1.setFont(font);
                            l2.setFont(font);
                            l3.setFont(font);

                            TextField t4 = new TextField();
                            TextField t5 = new TextField();
                            t4.setFont(font);
                            t5.setFont(font);
                            Button check = new Button("check");

                            check.setFont(font);
                            root3.add(l1, 0, 1);
                            root3.add(l2, 0, 2);
                            root3.add(l3, 0, 4);
                            root3.add(t4, 1, 1);
                            root3.add(t5, 1, 2);
                            root3.add(check, 0, 3);
                            root3.setAlignment(Pos.CENTER);
                            root3.setHgap(10);
                            root3.setVgap(10);
                            root3.setPadding(new Insets(25, 25, 25, 25));

                            check.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent ae) {
                                    // TODO Auto-generated method stub
                                    try {
                                        int busno = 0;
                                        busno = Integer.parseInt(t4.getText());
                                        int routeid = 0;
                                        routeid = Integer.parseInt(t5.getText());

                                        Class c1 = Class.forName("com.mysql.cj.jdbc.Driver");

                                        final String JdbcDriver = "com.mysql.cj.jdbc.Driver";
                                        final String user = "root";
                                        final String pass = "password";
                                        final String db_url = "jdbc:mysql://127.0.0.1:3306/transport";
                                        Connection con = DriverManager.getConnection(db_url, user, pass);
                                        String query = "SELECT seats from availibilty where bus_no = ? and route_id = ?;";
                                        PreparedStatement st = con.prepareStatement(query);

                                        st.setInt(1, busno);
                                        st.setInt(2, routeid);
                                        ResultSet rs = st.executeQuery();
                                        int i = 0;
                                        while (rs.next()) {
                                            l3.setText("Number of seats are " + rs.getString(1));
                                            i = 1;

                                        }
                                        if (i == 0)
                                            System.out.println("Invalid data");

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }

                                }

                            });

                            ava.show();

                        }

                    });

                    Menu.show();
                }

            }

        });

        primaryStage.show();
    }

}
