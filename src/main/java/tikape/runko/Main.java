package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.database.SmoothieDao;
import tikape.runko.database.SmoothieRaakaaineDao;
import tikape.runko.domain.SmoothieRaakaaine;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:smoothie.db");

        SmoothieDao smoothieDao = new SmoothieDao(database);
        RaakaAineDao raakaaineDao = new RaakaAineDao(database);
        SmoothieRaakaaineDao smrDao = new SmoothieRaakaaineDao(database);

        Spark.get("/smoothiepankki", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothieDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/lisaasmoothie", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothieDao.findAll());
            map.put("raakaaineet", raakaaineDao.findAll());

            return new ModelAndView(map, "lisaasmoothie");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/lisaasmoothie", (req, res)-> {
            String nimi = req.queryParams("nimi");
            smoothieDao.save(nimi);
            res.redirect("/lisaasmoothie");
            return"";
        });
        
        Spark.post("/lisaaraakaainesmoothieen", (req, res)-> {
            String smoothie = req.queryParams("smoothie");
            String raakaaine = req.queryParams("raakaaine");
            int raakaaineId = raakaaineDao.findByName(raakaaine).getId();
            int smoothieId = smoothieDao.findByName(smoothie).getId();
            String maara = req.queryParams("maara");
            String ohje = req.queryParams("ohje");
            SmoothieRaakaaine smr = new SmoothieRaakaaine(smoothieId, raakaaineId, raakaaine, maara, ohje);
            smrDao.saveOrUpdate(smr);
            res.redirect("/lisaasmoothie");
            return ""; 
        });

        Spark.get("/lisaaraakaaine", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaaineet", raakaaineDao.findAll());

            return new ModelAndView(map, "lisaaraakaaine");
        }, new ThymeleafTemplateEngine());

        Spark.post("/lisaaraakaaine", (req, res) -> {
            String nimi = req.queryParams("nimi");
            raakaaineDao.save(nimi);
            res.redirect("/lisaaraakaaine");
            return "";
        });
        
        Spark.get("/poistaraakaaine/:id", (req, res) -> {
            int raakaaineid = Integer.parseInt(req.params(":id"));
            raakaaineDao.delete(raakaaineid);
            res.redirect("/lisaaraakaaine");
            return "";
        });
        
        Spark.get("/smoothie/:id", (req, res) -> {
            Integer smoothieId = Integer.parseInt(req.params(":id"));
            HashMap map = new HashMap<>();
            map.put("smoothie", smoothieDao.findOne(smoothieId));
            map.put("raakaaineet", smrDao.findAll(smoothieId));

            return new ModelAndView(map, "smoothie");
        }, new ThymeleafTemplateEngine());
    }
}  
       /* Spark.post("/tasks/:id", (req, res) -> {
            Integer taskId = Integer.parseInt(req.params(":id"));
            Integer userId = Integer.parseInt(req.queryParams("userId"));
            
            TaskAssignment ta = new TaskAssignment(-1, taskId, userId, Boolean.FALSE);
            taskAssignments.saveOrUpdate(ta);

            res.redirect("/tasks");
            return "";
        });*/