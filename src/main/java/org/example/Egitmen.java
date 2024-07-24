package org.example;

import java.sql.*;

public class Egitmen {
    private String username="root";
    private String password="zeynep";
    private String url="jdbc:mysql://localhost:3306/YeaFitness";
    private int egitmen_id;
    private String Isim;
    private String Calisma_saatleri;
    private String Saha_ismi_eg;

    private Connection connection=null;
    ResultSet rs;
    PreparedStatement preparedStatement;

    //eğitmene kullanıcı nasıl eklencek??

    public Egitmen(int ID) throws ClassNotFoundException, SQLException {
        try {
            Class.forName(".com.mysql.cj.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("driver çalışmadı."+e);
        }
        try {
            connection= DriverManager.getConnection(url,username,password);
            String sql="SELECT * FROM egitmen";
            preparedStatement=connection.prepareStatement(sql);
            rs=preparedStatement.executeQuery();

            while(rs.next()){
                if(rs.getInt("egitmen_id")==(ID)) {
                    this.Isim = rs.getString("isim") ;
                    this.egitmen_id = ID;
                    this.Calisma_saatleri = rs.getString("calisma_saatleri");
                    this.Saha_ismi_eg = rs.getString("saha_ismi_eg");

                    System.out.println(this.Isim + " " + Calisma_saatleri + " " + egitmen_id + " " + Saha_ismi_eg);

                }
            }

            System.out.println("bağlantı başarılı");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
