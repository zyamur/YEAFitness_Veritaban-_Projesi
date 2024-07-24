package org.example;

import java.sql.*;

public class Kullanici {

    private int kullanici_id;
    private String isim;
    private double hedef; //sqlde de düzelt double yap
    private int boy;
    private int kilo;
    private double bazal_metabolizma;
    private String cinsiyet;
    private int yas;
    private String telefon_numarasi;
    private int egitmen_id;


    public Kullanici(int kullanici_id, String isim, int boy, int kilo,  String cinsiyet, int yas, String telefon_numarasi) {
        this.kullanici_id = kullanici_id;
        this.isim = isim;
        this.boy = boy;
        this.kilo = kilo;
        this.cinsiyet = cinsiyet;
        this.yas = yas;
        this.telefon_numarasi = telefon_numarasi;

        //eğitmen alıyosa set eğitmen methodu
        this.egitmen_id = 0;
        this.hedef = setHedefKilo(this);
        this.bazal_metabolizma = setBazalMetabolizma(this);
    }
    public double setHedefKilo(Kullanici kullanici){
        double hedefKilo=0;
        if (kullanici.getCinsiyet().equals("Erkek")) {
            hedefKilo=50+2.3*((kullanici.getBoy()/2.54)-60);
        } else if (kullanici.getCinsiyet().equals("Kadın")) {
            hedefKilo=45.5+2.3*((kullanici.getBoy()/2.54)-60);
        }

        return hedefKilo;
    }

    public double setBazalMetabolizma(Kullanici kullanici){
        double bazal=0;

        if (kullanici.getCinsiyet().equals("Erkek")) {
            bazal=660.47+(13.75*kullanici.getKilo())+((double)5*kullanici.getBoy()/100)-(6.76*kullanici.getYas());
        } else if (kullanici.getCinsiyet().equals("Kadın")) {
            bazal=655.10+(9.56*kullanici.getKilo())+(1.85*kullanici.getBoy()/100)-(4.68*kullanici.getYas());
        }

        return bazal;
    }

//    public void saveToDatabase() {
//        String sql = "INSERT INTO egzersiz_programi (boy, kilo, yas, cinsiyet, " +
//                "hedef_kilo, bazal_metabolizma, zorluk_seviyesi, ortalama_kalori_yakimi, program) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//        try (Connection conn = DatabaseUtil.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setInt(1, this.boy);
//            pstmt.setDouble(2, this.kilo);
//            pstmt.setInt(3, this.yas);
//            pstmt.setString(4, this.cinsiyet);
//            pstmt.setDouble(5, this.hedef);
//            pstmt.setDouble(6, this.bazal_metabolizma);
//            pstmt.setString(7, this.zorlukSeviyesi);
//            pstmt.setDouble(8, this.ortalama_kalori_yakimi);
//            pstmt.setString(9, this.program);
//
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


    public int getKullanici_id() {
        return kullanici_id;
    }

    public void setKullanici_id(int kullanici_id) {
        this.kullanici_id = kullanici_id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public double getHedef() {
        return hedef;
    }

    public void setHedef(double hedef) {
        this.hedef = hedef;
    }

    public int getBoy() {
        return boy;
    }

    public void setBoy(int boy) {
        this.boy = boy;
    }

    public int getKilo() {
        return kilo;
    }

    public void setKilo(int kilo) {
        this.kilo = kilo;
    }

    public double getBazal_metabolizma() {
        return bazal_metabolizma;
    }

    public void setBazal_metabolizma(double bazal_metabolizma) {
        this.bazal_metabolizma = bazal_metabolizma;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public int getYas() {
        return yas;
    }

    public void setYas(int yas) {
        this.yas = yas;
    }

    public String getTelefon_numarasi() {
        return telefon_numarasi;
    }

    public void setTelefon_numarasi(String telefon_numarasi) {
        this.telefon_numarasi = telefon_numarasi;
    }

    public int getEgitmen_id() {
        return egitmen_id;
    }

    public void setEgitmen_id(int egitmen_id) {
        this.egitmen_id = egitmen_id;
    }
}
