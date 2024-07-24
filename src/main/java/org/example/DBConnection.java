package org.example;

import java.sql.*;

public class DBConnection {
    final String jdbcUrl = "jdbc:mysql://localhost:3306/yeafitness";
    final String username = "root";
    final String password = "zeynep";
    ResultSet rs;
    PreparedStatement preparedStatement;
    Connection connection = null;

    public void createKullanici(Kullanici kul){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();

            String sql = "INSERT INTO kullanici(kullanici_id, isim, hedef, boy, kilo, " +
                    "bazal_metabolizma, cinsiyet, yas, telefon_numarasi, egitmen_ID) " +
                    "VALUES (" + kul.getKullanici_id() + ", '" + kul.getIsim() + "', '" + kul.getHedef() + "', " + kul.getBoy() + ", " + kul.getKilo() + ", " +
                    kul.getBazal_metabolizma() + ", '" + kul.getCinsiyet() + "', '" + kul.getYas() + "', '" + kul.getTelefon_numarasi() + "', " + kul.getEgitmen_id() + ")";

            int result = statement.executeUpdate(sql);
            System.out.println("Inserted rows: " + result);

            connection.close();
        } catch (Exception e) {
            System.out.println("Bu ID'de kullanıcı bulunmaktadır.");
            //e.printStackTrace();
        }

    }



    //try catch yazılacak id aynıysa
    public void createEgzersizProgrami(EgzersizProgrami egzersizProgrami){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();

            String sql = "INSERT INTO egzersiz_programi(kullanici_ID, egzersiz_ismi, zorluk_seviyesi, ortalama_kalori_yakimi) " +
                    "VALUES (" + egzersizProgrami.getKullanici_ID() + ", '" + egzersizProgrami.getEgzersiz_ismi() + "', '" + egzersizProgrami.getZorluk_seviyesi()
                    + "', " + egzersizProgrami.getOrtalama_kalori_yakimi() + ")";

            int result = statement.executeUpdate(sql);
            System.out.println("Inserted rows: " + result);

            connection.close();
        } catch (Exception e) {
            System.out.println("Egzersiz programı oluşturulamadı.");
            //e.printStackTrace();
        }

    }

    public void assignSporSahasi(Kullanici kullanici, int randevu_saati,String brans) throws ClassNotFoundException, SQLException {

        String[] saatler;
        try {
            Class.forName(".com.mysql.cj.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("driver çalışmadı." + e);
        }
        try {
            this.connection = DriverManager.getConnection(jdbcUrl, username, password);
            String sql = "SELECT * FROM spor_sahasi  ";
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                String Saha_ismi = rs.getString("saha_ismi");
                String Hizmet_saatleri = rs.getString("hizmet_saatleri");
                int Kontenjan = rs.getInt("kontenjan");
                int Anlik_doluluk = rs.getInt("anlik_doluluk");
                /*System.out.println(Saha_ismi);
                System.out.println(Hizmet_saatleri);
                System.out.println(Kontenjan);
                System.out.println(Anlik_doluluk);*/

                //String olan saatler integera çevrilip arraye atıldı
                saatler = Hizmet_saatleri.split("-");
                int[] saatler_int = new int[2];
                for(int i=0;i<2;i++)
                    saatler_int[i]=Integer.parseInt(saatler[i]);
                /*for(int i=0;i<2;i++)
                    System.out.println(saatler_int[i]);*/

                if (saatler_int[0] <= randevu_saati && randevu_saati <= saatler_int[1] && brans.equals(Saha_ismi) && Anlik_doluluk<Kontenjan) {
                    System.out.println(Saha_ismi + " " + randevu_saati);
                    String sql2="UPDATE spor_sahasi SET anlik_doluluk = anlik_doluluk + 1 where saha_ismi=?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setString(1, Saha_ismi);
                    preparedStatement2.executeUpdate();

                    updateRandevu_alir(Saha_ismi,kullanici.getKullanici_id());

                    break;
                } else if (Saha_ismi.equals(brans))
                    System.out.println("Aradığınız kriterlerde randevu bulunamamaktadır.");

            }

        }
        catch (SQLException e) {
            System.out.println("bağlantı başarısız");
        }
    }

    public void updateRandevu_alir(String Saha_ismi, int kullanici_id) throws SQLException {
        try{
        String sql = "INSERT INTO randevu_alir (kullanici_id_rand, saha_ismi_rand) VALUES (?, ?)";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, kullanici_id);
        preparedStatement.setString(2, Saha_ismi);

        int result = preparedStatement.executeUpdate();
        System.out.println("Inserted rows: " + result);

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
    }


    public void createDersRandevusu(Kullanici kullanici, int ders_saati,String brans) throws ClassNotFoundException, SQLException {
        String[] saatler;
        try {
            Class.forName(".com.mysql.cj.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("driver çalışmadı." + e);
        }
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            String sql = "SELECT * FROM egitmen";
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("egitmen_id");
                String isim = rs.getString("isim");

//                System.out.println(isim+" "+ ID);
                String saha_ismi = rs.getString("saha_ismi_eg");
                String calisma_saatleri = rs.getString("calisma_saatleri");

//                System.out.println(saha_ismi);
//
                saatler = calisma_saatleri.split("-");
                int[] saatler_int = new int[2];
                for(int i=0;i<2;i++)
                    saatler_int[i]=Integer.parseInt(saatler[i]);
//                for(int i=0;i<2;i++)
//                    System.out.println(saatler_int[i]);



                if (saatler_int[0] < ders_saati && ders_saati < saatler_int[1] && brans.equals(saha_ismi)) {
                    System.out.println(saha_ismi + " " + isim);

                    AssignEgitmenID(kullanici.getKullanici_id(), ID);
                    /*sql="UPDATE kullanici SET egitmen_id = ID";
                    preparedStatement = connection.prepareStatement(sql);
                    break;*/
                }
                else if (saha_ismi.equals(brans))
                    System.out.println("Aradığınız kriterlerde randevu bulunamamaktadır.");

            }

        } catch (
                SQLException e) {
            System.out.println("bağlantı başarısız");
        }
    }

    public void AssignEgitmenID(int kullanici_id, int egitmen_id) {
        try {
            String sql = "UPDATE kullanici SET egitmen_ID = ? WHERE kullanici_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, egitmen_id);
            preparedStatement.setInt(2, kullanici_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}


