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
        JFrame startFrame = new JFrame("YeaFitness - Hoşgeldiniz");
        startFrame.setSize(600, 400);
        startFrame.setLayout(new GridLayout(2, 1));
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton createUserButton = new JButton("Kullanıcı Ekle");
        createUserButton.setFont(new Font("Arial", Font.BOLD, 20));
        createUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startFrame.setVisible(false);
                showCreateUserScreen();
            }
        });

        JButton loginButton = new JButton("Var Olan Kullanıcıdan Giriş Yap");
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startFrame.setVisible(false);
                showLoginScreen();
            }
        });

        startFrame.add(createUserButton);
        startFrame.add(loginButton);
        startFrame.setVisible(true);
    }

    public static void showCreateUserScreen() {
        JFrame frame = new JFrame("Kullanıcı Oluşturma");
        frame.setSize(600, 600);
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
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int kullanici_id = Integer.parseInt(idField.getText());
                    if (isUserExist(kullanici_id)) {
                        JOptionPane.showMessageDialog(frame, "Bu ID'de kullanıcı bulunmaktadır, lütfen başka bir ID deneyin.");
                        return;
                    }

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

    public static void showLoginScreen() {
        JFrame loginFrame = new JFrame("Kullanıcı Girişi");
        loginFrame.setSize(400, 200);
        loginFrame.setLayout(new GridLayout(3, 2));
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel idLabel = new JLabel("Kullanıcı ID:");
        JTextField idField = new JTextField();

        JButton loginButton = new JButton("Giriş Yap");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int kullanici_id = Integer.parseInt(idField.getText());
                    if (isUserExist(kullanici_id)) {
                        Kullanici kullanici = getUserDetails(kullanici_id);
                        JOptionPane.showMessageDialog(loginFrame, "Giriş başarılı. Hoşgeldiniz, " + kullanici.getIsim());
                        loginFrame.setVisible(false);
                        showOptions(kullanici);
                    } else {
                        JOptionPane.showMessageDialog(loginFrame, "Bu ID'de bir kullanıcı bulunamadı.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(loginFrame, "Hata: " + ex.getMessage());
                }
            }
        });

        loginFrame.add(idLabel);
        loginFrame.add(idField);
        loginFrame.add(loginButton);
        loginFrame.setVisible(true);
    }

    public static boolean isUserExist(int kullanici_id) {
        try {
            // Veritabanı bağlantısını açıyoruz
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            String query = "SELECT * FROM kullanici WHERE kullanici_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, kullanici_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Eğer sonuç dönerse, kullanıcı mevcuttur
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Hata durumunda false döndürüyoruz
        } finally {
            // Bağlantıyı kapatmayı unutmuyoruz
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static Kullanici getUserDetails(int kullanici_id) {
        Kullanici kullanici = null;
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            String query = "SELECT * FROM kullanici WHERE kullanici_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, kullanici_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                kullanici = new Kullanici(
                        resultSet.getInt("kullanici_id"),
                        resultSet.getString("isim"),
                        resultSet.getInt("boy"),
                        resultSet.getInt("kilo"),
                        resultSet.getString("cinsiyet"),
                        resultSet.getInt("yas"),
                        resultSet.getString("telefon_numarasi")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kullanici;
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
        optionsFrame.setSize(600, 400);
        optionsFrame.setLayout(new GridLayout(3, 1));

        JButton createExerciseButton = new JButton("Egzersiz programı oluştur !");
        createExerciseButton.setFont(new Font("Arial", Font.BOLD, 20));
        createExerciseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EgzersizProgrami program = new EgzersizProgrami(kul);
                createEgzersizProgrami(program);
                JOptionPane.showMessageDialog(optionsFrame, "Egzersiz programı oluşturuldu.");
                showExerciseProgram(program);
            }
        });

        JButton getTrainingButton = new JButton("Eğitim al !");
        getTrainingButton.setFont(new Font("Arial", Font.BOLD, 20));
        getTrainingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showTrainingSelectionFrame(kul);
            }
        });

        JButton chooseGymButton = new JButton("Spor salonu seç !");
        chooseGymButton.setFont(new Font("Arial", Font.BOLD, 20));
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
        exerciseFrame.setSize(600, 400);
        exerciseFrame.setLayout(new GridLayout(2, 1));

        JLabel egzersizIsmiLabel = new JLabel("Egzersiz İsmi: " + egzersizProgrami.getEgzersiz_ismi());
        egzersizIsmiLabel.setFont(new Font("Arial", Font.BOLD, 18));
        exerciseFrame.add(egzersizIsmiLabel);

        exerciseFrame.setVisible(true);
    }

    public static void showGymSelectionFrame(int kullaniciId) {
        JFrame gymFrame = new JFrame("Spor Salonu Seçimi");
        gymFrame.setSize(600, 400);
        gymFrame.setLayout(new GridLayout(3, 2));

        JLabel branşLabel = new JLabel("Branş:");
        branşLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        String[] branşlar = {"pilates", "basketbol", "fitness", "tenis", "voleybol", "yüzme"};
        JComboBox<String> branşComboBox = new JComboBox<>(branşlar);

        JLabel saatLabel = new JLabel("Saat:");
        saatLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        String[] saatler = new String[17];
        for (int i = 0; i < 17; i++) {
            saatler[i] = (8 + i) + ":00";
        }
        JComboBox<String> saatComboBox = new JComboBox<>(saatler);

        JButton submitButton = new JButton("Randevu Al");
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String seçilenBranş = (String) branşComboBox.getSelectedItem();
                String seçilenSaat = (String) saatComboBox.getSelectedItem();
                int saat = Integer.parseInt(seçilenSaat.split(":")[0]);
                try {
                    boolean randevuSuccess = updateRandevu_alir(seçilenBranş, kullaniciId);
                    if (!randevuSuccess) {
                        JOptionPane.showMessageDialog(gymFrame, "Randevu alınamadı, kontenjan dolmuş olabilir.");
                        return;
                    }

                    boolean sporSahasiSuccess = assignSporSahasi(new Kullanici(kullaniciId, "", 0, 0, "", 0, ""), saat, seçilenBranş);
                    if (!sporSahasiSuccess) {
                        JOptionPane.showMessageDialog(gymFrame, "Randevu alınamadı, seçilen saat için spor sahası müsait değil.");
                        return;
                    }

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
        trainingFrame.setSize(600, 400);
        trainingFrame.setLayout(new GridLayout(3, 2));

        JLabel branşLabel = new JLabel("Branş:");
        branşLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        String[] branşlar = {"pilates", "basketbol", "fitness", "tenis", "voleybol", "yüzme"};
        JComboBox<String> branşComboBox = new JComboBox<>(branşlar);

        JLabel saatLabel = new JLabel("Saat:");
        saatLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        String[] saatler = new String[17];
        for (int i = 0; i < 17; i++) {
            saatler[i] = (8 + i) + ":00";
        }
        JComboBox<String> saatComboBox = new JComboBox<>(saatler);

        JButton submitButton = new JButton("Eğitim Al");
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String seçilenBranş = (String) branşComboBox.getSelectedItem();
                String seçilenSaat = (String) saatComboBox.getSelectedItem();
                int saat = Integer.parseInt(seçilenSaat.split(":")[0]);
                try {
                    boolean success = createDersRandevusu(kul, saat, seçilenBranş);
                    if (success) {
                        JOptionPane.showMessageDialog(trainingFrame, "Eğitim başarıyla alındı.");
                    } else {
                        JOptionPane.showMessageDialog(trainingFrame, "Eğitim alınamadı, ya kullanıcıya zaten bir eğitmen atanmış ya da geçersiz saat girilmiştir.");
                    }
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

    public static boolean updateRandevu_alir(String Saha_ismi, int kullanici_id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Veritabanına bağlan
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Kontenjanı kontrol et
            String checkKontenjanSql = "SELECT kontenjan, anlik_doluluk FROM spor_sahasi WHERE saha_ismi = ?";
            preparedStatement = connection.prepareStatement(checkKontenjanSql);
            preparedStatement.setString(1, Saha_ismi);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int kontenjan = resultSet.getInt("kontenjan");
                int anlik_doluluk = resultSet.getInt("anlik_doluluk");

                if (anlik_doluluk < kontenjan) {
                    // Kontenjan müsait, randevuyu ekle
                    String sql = "INSERT INTO randevu_alir (kullanici_id_rand, saha_ismi_rand) VALUES (?, ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, kullanici_id);
                    preparedStatement.setString(2, Saha_ismi);

                    int result = preparedStatement.executeUpdate();
                    System.out.println("Inserted rows: " + result);

                    // Anlık doluluğu artır
                    String updateDolulukSql = "UPDATE spor_sahasi SET anlik_doluluk = anlik_doluluk + 1 WHERE saha_ismi = ?";
                    preparedStatement = connection.prepareStatement(updateDolulukSql);
                    preparedStatement.setString(1, Saha_ismi);
                    preparedStatement.executeUpdate();

                    return true; // Randevu başarıyla alındı
                } else {
                    // Kontenjan dolu, randevu alınamadı
                    System.out.println("Kontenjan dolu, randevu alınamadı.");
                    return false; // Randevu alınamadı
                }
            } else {
                System.out.println("Saha bulunamadı.");
                return false; // Saha bulunamadı
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Bir hata meydana geldi
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }


    public static boolean createDersRandevusu(Kullanici kullanici, int ders_saati, String brans) throws ClassNotFoundException, SQLException {
        String[] saatler;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver çalışmadı." + e);
        }
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Kullanıcıya zaten bir eğitmen atanıp atanmadığını kontrol et
            String checkSql = "SELECT egitmen_ID FROM kullanici WHERE kullanici_id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setInt(1, kullanici.getKullanici_id());
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                int existingEgitmenId = resultSet.getInt("egitmen_ID");
                if (existingEgitmenId != 0) {  // Zaten bir eğitmen atanmışsa
                    //JOptionPane.showMessageDialog(null, "Bu kullanıcıya zaten bir eğitmen atanmış. Başka bir ders alınamaz.");
                    return false;
                }
            }

            String sql = "SELECT * FROM egitmen";
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("egitmen_id");
                String isim = rs.getString("isim");

                String saha_ismi = rs.getString("saha_ismi_eg");
                String calisma_saatleri = rs.getString("calisma_saatleri");

                saatler = calisma_saatleri.split("-");
                int[] saatler_int = new int[2];
                for (int i = 0; i < 2; i++)
                    saatler_int[i] = Integer.parseInt(saatler[i]);

                if (saatler_int[0] < ders_saati && ders_saati < saatler_int[1] && brans.equals(saha_ismi)) {
                    System.out.println(saha_ismi + " " + isim);

                    AssignEgitmenID(kullanici.getKullanici_id(), ID);

                    String sql2 = "UPDATE egitmen SET anlik_doluluk_eg = anlik_doluluk_eg + 1 where isim=?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setString(1, isim);
                    preparedStatement2.executeUpdate();

                    updateDers_alir(ID, kullanici.getKullanici_id());

                    return true; // Başarıyla tamamlandı
                } else if (saha_ismi.equals(brans)) {
                    System.out.println("Aradığınız kriterlerde randevu bulunamamaktadır.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Bağlantı başarısız.");
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return false; // Randevu alınamadı
    }



    public static boolean assignSporSahasi(Kullanici kullanici, int randevu_saati, String brans) throws ClassNotFoundException, SQLException {
        String[] saatler;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver çalışmadı: " + e);
            return false;
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM spor_sahasi")) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String Saha_ismi = rs.getString("saha_ismi");
                String Hizmet_saatleri = rs.getString("hizmet_saatleri");
                int Kontenjan = rs.getInt("kontenjan");
                int Anlik_doluluk = rs.getInt("anlik_doluluk");

                saatler = Hizmet_saatleri.split("-");
                int[] saatler_int = new int[2];
                for (int i = 0; i < 2; i++) {
                    saatler_int[i] = Integer.parseInt(saatler[i]);
                }

                if (saatler_int[0] <= randevu_saati && randevu_saati <= saatler_int[1] && brans.equals(Saha_ismi) && Anlik_doluluk < Kontenjan) {
                    System.out.println(Saha_ismi + " " + randevu_saati);

                    // Spor sahasının doluluk oranını güncelle
                    String sql2 = "UPDATE spor_sahasi SET anlik_doluluk = anlik_doluluk + 1 WHERE saha_ismi = ?";
                    try (PreparedStatement preparedStatement2 = connection.prepareStatement(sql2)) {
                        preparedStatement2.setString(1, Saha_ismi);
                        preparedStatement2.executeUpdate();
                    }

                    // Randevuyu güncelle
                    boolean randevuSuccess = updateRandevu_alir(Saha_ismi, kullanici.getKullanici_id());
                    return randevuSuccess; // Eğer randevu başarıyla alındıysa true döndür
                }
            }

            System.out.println("Aradığınız kriterlerde randevu bulunamamaktadır.");
            return false; // Uygun spor sahası bulunamadı veya kontenjan dolu
        } catch (SQLException e) {
            System.out.println("Bağlantı başarısız: " + e);
            return false; // Bir hata meydana geldi
        }
    }


    public static void AssignEgitmenID(int kullanici_id, int egitmen_id) {
        try {
            // Kullanıcıya zaten atanmış bir eğitmen olup olmadığını kontrol et
            String checkSql = "SELECT egitmen_ID FROM kullanici WHERE kullanici_id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setInt(1, kullanici_id);
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                int existingEgitmenId = resultSet.getInt("egitmen_ID");
                if (existingEgitmenId != 0) {  // Zaten bir eğitmen atanmışsa
                    JOptionPane.showMessageDialog(null, "Bu kullanıcıya zaten bir eğitmen atanmış.");
                    return;
                }
            }

            // Eğitmen atanmadıysa güncellemeyi gerçekleştir
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