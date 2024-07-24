package org.example;
import java.sql.*;

public class SporSahasi {
    private String username="root";
    private String password="zeynep";
    private String url="jdbc:mysql://localhost:3306/YeaFitness";
    private int kontenjan;
    private String saha_ismi;
    private String hizmet_saatleri;
    private int doluluk_orani;

    private Connection connection=null;
    ResultSet rs;
    PreparedStatement preparedStatement;

    public SporSahasi(String saha_ismi, String hizmet_saatleri) throws ClassNotFoundException, SQLException {
        try {
            Class.forName(".com.mysql.cj.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("driver çalışmadı."+e);
        }
        try {
            connection= DriverManager.getConnection(url,username,password);
            String sql="SELECT * FROM spor_sahasi";
            preparedStatement=connection.prepareStatement(sql);
            rs=preparedStatement.executeQuery();

            while(rs.next()){
                if(rs.getString("saha_ismi").equals(saha_ismi)&&rs.getString("hizmet_saatleri").equals(hizmet_saatleri)) {
                    this.saha_ismi = saha_ismi;
                    this.kontenjan =rs.getInt("kontenjan") ;
                    this.hizmet_saatleri = hizmet_saatleri;
                    this.doluluk_orani = rs.getInt("anlik_doluluk");

                    //System.out.println(this.Saha_ismi + " " + Kontenjan + " " + Hizmet_saatleri + " " + Doluluk_orani);
                }
            }

            System.out.println("bağlantı başarılı");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getKontenjan() {
        return kontenjan;
    }

    public void setKontenjan(int kontenjan) {
        this.kontenjan = kontenjan;
    }

    public String getSaha_ismi() {
        return saha_ismi;
    }

    public void setSaha_ismi(String saha_ismi) {
        this.saha_ismi = saha_ismi;
    }

    public String getHizmet_saatleri() {
        return hizmet_saatleri;
    }

    public void setHizmet_saatleri(String hizmet_saatleri) {
        this.hizmet_saatleri = hizmet_saatleri;
    }

    public int getDoluluk_orani() {
        return doluluk_orani;
    }

    public void setDoluluk_orani(int doluluk_orani) {
        this.doluluk_orani = doluluk_orani;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }
}