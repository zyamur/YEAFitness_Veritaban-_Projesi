package org.example;

public class EgzersizProgrami {

    private int kullanici_ID;
    private String egzersiz_ismi;
    private String zorluk_seviyesi;
    private double ortalama_kalori_yakimi;
    Kullanici kullanici;


    //
    public EgzersizProgrami(Kullanici kullanici){
        this.kullanici_ID = kullanici.getKullanici_id();
        this.zorluk_seviyesi= setZorlukSeviyesi(kullanici);
        this.egzersiz_ismi = setEgzersizIsmi(zorluk_seviyesi, kullanici.getCinsiyet() );

    }
    public String setZorlukSeviyesi (Kullanici kullanici){
        String zorlukSeviyesi="";

        if (kullanici.getCinsiyet().equals("Erkek")){
            if (kullanici.getBazal_metabolizma() <1200){
                zorlukSeviyesi="zor"; this.ortalama_kalori_yakimi=1200;}
            else if (kullanici.getBazal_metabolizma()>1200 && kullanici.getBazal_metabolizma()<1600) {
                zorlukSeviyesi="orta"; this.ortalama_kalori_yakimi=900;
            }else{ zorlukSeviyesi="kolay"; this.ortalama_kalori_yakimi=600;}
        }

        if (kullanici.getCinsiyet().equals("Kadın")){
            if (kullanici.getBazal_metabolizma()<1000){
                zorlukSeviyesi="zor"; this.ortalama_kalori_yakimi=100;  }
            else if (kullanici.getBazal_metabolizma()>1000 && kullanici.getBazal_metabolizma()<1400) {
                zorlukSeviyesi="orta"; this.ortalama_kalori_yakimi=700;
            }else{ zorlukSeviyesi="kolay"; this.ortalama_kalori_yakimi=450; }
        }

        if (kullanici.getYas()>=60) {
            zorlukSeviyesi="kolay";
        }

        return zorlukSeviyesi;
    }



    public String setEgzersizIsmi(String zorlukSeviyesi,String cinsiyet){
        String egzersiz_ismi="";

        if (cinsiyet.equals("Erkek")){
            if (zorlukSeviyesi.equals("zor")) {
                egzersiz_ismi=" 4x12 barbell bench press " +
                        " 4x12 wide grip latt pulldown " +
                        " 4x12 chest supported dumbell row" +
                        " 3x15 standing dumbell curl " +
                        " 3x15 bench dips " +
                        " 20 dakika 10km/h hızda koşu " +
                        " 10 dakika eliptik bisiklet ";
            } else if (zorlukSeviyesi.equals("orta")) {
                egzersiz_ismi=" 4x12 barbell bench press " +
                        " 4x12 wide grip latt pulldown " +
                        " 4x12 squat" +
                        " 15 dakika 7km/h hızda koşu " +
                        " 10 dakika eliptik bisiklet ";
            } else if (zorlukSeviyesi.equals("kolay")) {
                egzersiz_ismi=" 30 dakika 5km/h hızda yürüyüş " +
                        " 15 dakika eliptik bisiklet ";}
        } else if (cinsiyet.equals("Kadın")) {
            if (zorlukSeviyesi.equals("zor")) {
                egzersiz_ismi=" 4x12 dumbell raise " +
                        " 4x12 thigh adductor " +
                        " 3x15 barbell hip thrust" +
                        " 3x15 glute kickback " +
                        " 4x12 squat" +
                        " 3x15 seated cable rows " +
                        " 15 dakika 10km/h hızda koşu " +
                        " 10 dakika recumbent bisiklet ";
            } else if (zorlukSeviyesi.equals("orta")) {
                egzersiz_ismi=" 4x12 thigh adductor " +
                        " 3x15 barbell hip thrust" +
                        " 4x12 squat" +
                        " 15 dakika 7km/h hızda koşu " +
                        " 10 dakika recumbent bisiklet ";
            } else if (zorlukSeviyesi.equals("kolay")) {
                egzersiz_ismi=" 30 dakika 5km/h hızda yürüyüş " +
                        " 15 dakika eliptik bisiklet ";}

        }

        return egzersiz_ismi;
    }

    public int getKullanici_ID() {
        return kullanici_ID;
    }

    public void setKullanici_ID(int kullanici_ID) {
        this.kullanici_ID = kullanici_ID;
    }

    public String getEgzersiz_ismi() {
        return egzersiz_ismi;
    }

    public void setEgzersiz_ismi(String egzersiz_ismi) {
        this.egzersiz_ismi = egzersiz_ismi;
    }

    public String getZorluk_seviyesi() {
        return zorluk_seviyesi;
    }

    public void setZorluk_seviyesi(String zorluk_seviyesi) {
        this.zorluk_seviyesi = zorluk_seviyesi;
    }

    public double getOrtalama_kalori_yakimi() {
        return ortalama_kalori_yakimi;
    }

    public void setOrtalama_kalori_yakimi(double ortalama_kalori_yakimi) {
        this.ortalama_kalori_yakimi = ortalama_kalori_yakimi;
    }
}
