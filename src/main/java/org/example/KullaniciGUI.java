package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class KullaniciGUI {
    private static String jdbcUrl = "jdbc:mysql://localhost:3306/yeafitness";
    private static String username = "root";
    private static String password = "zeynep";
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet rs = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Kullanıcı Oluşturma");
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(9, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel idLabel = new JLabel("Kullanıcı ID:");
        JTextField idField = new JTextField();
        JLabel isimLabel = new JLabel("İsim:");
        JTextField isimField = new JTextField();
        JLabel boyLabel = new JLabel("Boy:");
        JTextField boyField = new JTextField();
        JLabel kiloLabel = new JLabel("Kilo:");
        JTextField kiloField = new JTextField();
        JLabel cinsiyetLabel = new JLabel("Cinsiyet:");
        JTextField cinsiyetField = new JTextField();
        JLabel yasLabel = new JLabel("Yaş:");
        JTextField yasField = new JTextField();
        JLabel telefonLabel = new JLabel("Telefon Numarası:");
        JTextField telefonField = new JTextField();
        JLabel egitmenLabel = new JLabel("Eğitmen ID:");
        JTextField egitmenField = new JTextField();

        JButton submitButton = new JButton("Kullanıcı Oluştur");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int kullanici_id = Integer.parseInt(idField.getText());
                    String isim = isimField.getText();
                    int boy = Integer.parseInt(boyField.getText());
                    int kilo = Integer.parseInt(kiloField.getText());
                    String cinsiyet = cinsiyetField.getText();
                    int yas = Integer.parseInt(yasField.getText());
                    String telefon_numarasi = telefonField.getText();
                    int egitmen_id = Integer.parseInt(egitmenField.getText());

                    Kullanici kul = new Kullanici(kullanici_id, isim, boy, kilo, cinsiyet, yas, telefon_numarasi);
                    kul.setEgitmen_id(egitmen_id);

                    createKullanici(kul);
                    JOptionPane.showMessageDialog(frame, "Kullanıcı başarıyla oluşturuldu.");

                    // Yeni sayfa açma
                    frame.setVisible(false);
                    showOptions(kul);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Hata: " + ex.getMessage());
                }
            }
        });

        frame.add(idLabel);
        frame.add(idField);
        frame.add(isimLabel);
        frame.add(isimField);
        frame.add(boyLabel);
        frame.add(boyField);
        frame.add(kiloLabel);
        frame.add(kiloField);
        frame.add(cinsiyetLabel);
        frame.add(cinsiyetField);
        frame.add(yasLabel);
        frame.add(yasField);
        frame.add(telefonLabel);
        frame.add(telefonField);
        frame.add(egitmenLabel);
        frame.add(egitmenField);
        frame.add(submitButton);

        frame.setVisible(true);
    }

    public static void createKullanici(Kullanici kul) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();

            String sql = "INSERT INTO kullanici(kullanici_id, isim, hedef, boy, kilo, " +
                    "bazal_metabolizma, cinsiyet, yas, telefon_numarasi, egitmen_ID) " +
                    "VALUES (" + kul.getKullanici_id() + ", '" + kul.getIsim() + "', " + kul.getHedef() + ", " + kul.getBoy() + ", " + kul.getKilo() + ", " +
                    kul.getBazal_metabolizma() + ", '" + kul.getCinsiyet() + "', " + kul.getYas() + ", '" + kul.getTelefon_numarasi() + "', " + kul.getEgitmen_id() + ")";

            int result = statement.executeUpdate(sql);
            System.out.println("Inserted rows: " + result);

            connection.close();
        } catch (Exception e) {
            System.out.println("Bu ID'de kullanıcı bulunmaktadır.");
            e.printStackTrace();
        }
    }

    public static void showOptions(Kullanici kul) {
        JFrame optionsFrame = new JFrame("Seçenekler");
        optionsFrame.setSize(400, 200);
        optionsFrame.setLayout(new GridLayout(3, 1));

        JButton createExerciseButton = new JButton("Egzersiz programı oluştur !");
        createExerciseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EgzersizProgrami program = new EgzersizProgrami(kul);
                createEgzersizProgrami(program);
                JOptionPane.showMessageDialog(optionsFrame, "Egzersiz programı oluşturuldu.");
                showExerciseProgram(program);  // Yeni sayfayı göster
            }
        });

        JButton getTrainingButton = new JButton("Eğitim al !");
        getTrainingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showTrainingSelectionFrame(kul);
            }
        });

        JButton chooseGymButton = new JButton("Spor salonu seç !");
        chooseGymButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showGymSelectionFrame(kul.getKullanici_id());
            }
        });

        optionsFrame.add(createExerciseButton);
        optionsFrame.add(getTrainingButton);
        optionsFrame.add(chooseGymButton);

        optionsFrame.setVisible(true);
    }

    public static void createEgzersizProgrami(EgzersizProgrami egzersizProgrami) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();

            String sql = "INSERT INTO egzersiz_programi(kullanici_ID, egzersiz_ismi, zorluk_seviyesi, ortalama_kalori_yakimi) " +
                    "VALUES (" + egzersizProgrami.getKullanici_ID() + ", '" + egzersizProgrami.getEgzersiz_ismi() + "', '" + egzersizProgrami.getZorluk_seviyesi()
                    + "', " + egzersizProgrami.getOrtalama_kalori_yakimi() + ")";

            int result = statement.executeUpdate(sql);
            System.out.println("Inserted rows: " + result);

            connection.close();
        } catch (Exception e) {
            System.out.println("Egzersiz programı oluşturulamadı.");
            e.printStackTrace();
        }
    }

    public static void showExerciseProgram(EgzersizProgrami egzersizProgrami) {
        JFrame exerciseFrame = new JFrame("Egzersiz Programı");
        exerciseFrame.setSize(400, 200);
        exerciseFrame.setLayout(new GridLayout(2, 1));

        JLabel egzersizIsmiLabel = new JLabel("Egzersiz İsmi: " + egzersizProgrami.getEgzersiz_ismi());
        exerciseFrame.add(egzersizIsmiLabel);

        exerciseFrame.setVisible(true);
    }

    public static void showGymSelectionFrame(int kullaniciId) {
        JFrame gymFrame = new JFrame("Spor Salonu Seçimi");
        gymFrame.setSize(400, 200);
        gymFrame.setLayout(new GridLayout(3, 2));

        JLabel branşLabel = new JLabel("Branş:");
        String[] branşlar = {"pilates", "basketbol", "fitness", "tenis", "voleybol", "yüzme"};
        JComboBox<String> branşComboBox = new JComboBox<>(branşlar);

        JLabel saatLabel = new JLabel("Saat:");
        String[] saatler = new String[17];
        for (int i = 0; i < 17; i++) {
            saatler[i] = (8 + i) + ":00";
        }
        JComboBox<String> saatComboBox = new JComboBox<>(saatler);

        JButton submitButton = new JButton("Randevu Al");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String seçilenBranş = (String) branşComboBox.getSelectedItem();
                String seçilenSaat = (String) saatComboBox.getSelectedItem();
                int saat = Integer.parseInt(seçilenSaat.split(":")[0]);
                try {
                    // Her iki metodu da çağır
                    updateRandevu_alir(seçilenBranş, kullaniciId);
                    assignSporSahasi(new Kullanici(kullaniciId, "", 0, 0, "", 0, ""), saat, seçilenBranş);
                    JOptionPane.showMessageDialog(gymFrame, "Randevu başarıyla alındı.");
                } catch (ClassNotFoundException | SQLException ex) {
                    JOptionPane.showMessageDialog(gymFrame, "Hata: " + ex.getMessage());
                }
            }
        });

        gymFrame.add(branşLabel);
        gymFrame.add(branşComboBox);
        gymFrame.add(saatLabel);
        gymFrame.add(saatComboBox);
        gymFrame.add(submitButton);

        gymFrame.setVisible(true);
    }

    public static void showTrainingSelectionFrame(Kullanici kul) {
        JFrame trainingFrame = new JFrame("Eğitim Seçimi");
        trainingFrame.setSize(400, 200);
        trainingFrame.setLayout(new GridLayout(3, 2));

        JLabel branşLabel = new JLabel("Branş:");
        String[] branşlar = {"pilates", "basketbol", "fitness", "tenis", "voleybol", "yüzme"};
        JComboBox<String> branşComboBox = new JComboBox<>(branşlar);

        JLabel saatLabel = new JLabel("Saat:");
        String[] saatler = new String[17];
        for (int i = 0; i < 17; i++) {
            saatler[i] = (8 + i) + ":00";
        }
        JComboBox<String> saatComboBox = new JComboBox<>(saatler);

        JButton submitButton = new JButton("Eğitim Al");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String seçilenBranş = (String) branşComboBox.getSelectedItem();
                String seçilenSaat = (String) saatComboBox.getSelectedItem();
                int saat = Integer.parseInt(seçilenSaat.split(":")[0]);
                try {
                    createDersRandevusu(kul, saat, seçilenBranş);
                    JOptionPane.showMessageDialog(trainingFrame, "Eğitim başarıyla alındı.");
                } catch (ClassNotFoundException | SQLException ex) {
                    JOptionPane.showMessageDialog(trainingFrame, "Hata: " + ex.getMessage());
                }
            }
        });

        trainingFrame.add(branşLabel);
        trainingFrame.add(branşComboBox);
        trainingFrame.add(saatLabel);
        trainingFrame.add(saatComboBox);
        trainingFrame.add(submitButton);

        trainingFrame.setVisible(true);
    }

    public static void updateRandevu_alir(String Saha_ismi, int kullanici_id) throws SQLException {
        try {
            String sql = "INSERT INTO randevu_alir (kullanici_id_rand, saha_ismi_rand) VALUES (?, ?)";
            connection = DriverManager.getConnection(jdbcUrl, username, password);
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

    public static void createDersRandevusu(Kullanici kullanici, int ders_saati,String brans) throws ClassNotFoundException, SQLException {
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
                int kontenjan = rs.getInt("kontenjan_eg");
                int anlik_doluluk_eg = rs.getInt("anlik_doluluk_eg");

//                System.out.println(saha_ismi);
//
                saatler = calisma_saatleri.split("-");
                int[] saatler_int = new int[2];
                for(int i=0;i<2;i++)
                    saatler_int[i]=Integer.parseInt(saatler[i]);
//                for(int i=0;i<2;i++)
//                    System.out.println(saatler_int[i]);



                if (saatler_int[0] < ders_saati && ders_saati < saatler_int[1] && brans.equals(saha_ismi)&&anlik_doluluk_eg<kontenjan) {
                    System.out.println(saha_ismi + " " + isim);

                    AssignEgitmenID(kullanici.getKullanici_id(), ID);
                    /*sql="UPDATE kullanici SET egitmen_id = ID";
                    preparedStatement = connection.prepareStatement(sql);
                    break;*/
                    String sql2="UPDATE egitmen SET anlik_doluluk_eg = anlik_doluluk_eg + 1 where isim=?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setString(1, isim);
                    preparedStatement2.executeUpdate();

                    updateDers_alir(ID,kullanici.getKullanici_id());


                }
                else if (saha_ismi.equals(brans))
                    System.out.println("Aradığınız kriterlerde randevu bulunamamaktadır.");

            }

        } catch (
                SQLException e) {
            System.out.println("bağlantı başarısız");
        }
    }

    public static void assignSporSahasi(Kullanici kullanici, int randevu_saati, String brans) throws ClassNotFoundException, SQLException {
        String[] saatler;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("driver çalışmadı." + e);
        }
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            String sql = "SELECT * FROM spor_sahasi";
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String Saha_ismi = rs.getString("saha_ismi");
                String Hizmet_saatleri = rs.getString("hizmet_saatleri");
                int Kontenjan = rs.getInt("kontenjan");
                int Anlik_doluluk = rs.getInt("anlik_doluluk");

                saatler = Hizmet_saatleri.split("-");
                int[] saatler_int = new int[2];
                for (int i = 0; i < 2; i++)
                    saatler_int[i] = Integer.parseInt(saatler[i]);

                if (saatler_int[0] <= randevu_saati && randevu_saati <= saatler_int[1] && brans.equals(Saha_ismi) && Anlik_doluluk < Kontenjan) {
                    System.out.println(Saha_ismi + " " + randevu_saati);
                    String sql2 = "UPDATE spor_sahasi SET anlik_doluluk = anlik_doluluk + 1 where saha_ismi=?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setString(1, Saha_ismi);
                    preparedStatement2.executeUpdate();

                    updateRandevu_alir(Saha_ismi, kullanici.getKullanici_id());

                    break;
                } else if (Saha_ismi.equals(brans))
                    System.out.println("Aradığınız kriterlerde randevu bulunamamaktadır.");
            }

        } catch (SQLException e) {
            System.out.println("bağlantı başarısız");
        }
    }

    public static void AssignEgitmenID(int kullanici_id, int egitmen_id) {
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

    public static void updateDers_alir(int egitmen_id, int kullanici_id) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();

            String sql = "INSERT INTO egitmen_dersleri (egitmen_id_ders, kullanici_id_ders) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, egitmen_id);
            preparedStatement.setInt(2, kullanici_id);

            int result = preparedStatement.executeUpdate();
            System.out.println("Inserted rows: " + result);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
