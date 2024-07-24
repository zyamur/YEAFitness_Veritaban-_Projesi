package org.example;

import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;

import static spark.Spark.get;
import static spark.Spark.post;


public class Main {
    static DBConnection con = new DBConnection();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Kullanici kul = new Kullanici(90, "ayşe",  154, 66, "Kadın", 34,null);
        //Egitmen eg = new Egitmen(4);

        //con.createDersRandevusu(kul,12,"pilates");
        con.createKullanici(kul);

//        EgzersizProgrami eg = new EgzersizProgrami(kul);
//        con.createEgzersizProgrami(eg);

//        try {
//            Egitmen eg = new Egitmen(4);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


//        try {
//            con.assignSporSahasi(kul ,11,"yüzme");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        //con.createEgzersizProgrami();


//        post("/kullanicilar", (request, response) -> {
//            response.type("application/json");
//            Kullanici kullanici = new Gson().fromJson(request.body(), Kullanici.class);
//            con.createKullanici(kullanici);
//
//            return new Gson()
//                    .toJson(new StandardResponse(StatusResponse.SUCCESS));
//        });
//
//        post("/kullanicilar", (request, response) -> {
//            response.type("application/json");
//            Kullanici kullanici = new Gson().fromJson(request.body(), Kullanici.class);
//            con.createKullanici(kullanici);
//
//            return new Gson().toJson(kullanici);
//        });

       /*
       get("/hello", (req, res)->
        {
            DBConnection dbConnection = new DBConnection();
            dbConnection.createConnection();
            Connection connection = dbConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from kullanici where kullanici_id > 3 ");
            ArrayList<Integer> ids = new ArrayList<>();
            while(resultSet.next())
            {
                ids.add(resultSet.getInt("kullanici_id"));
            }
            return ids;
        });


        get("/join/:id", (req, res)-> {

            String program = createProgram();

            return json(program);
        });

        get("/moves/:id", (req, res)-> {

            String program = createProgram();

            return json(program);
        });
*/
    }

}