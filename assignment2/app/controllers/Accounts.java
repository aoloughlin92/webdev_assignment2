package controllers;

import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

public class Accounts extends Controller{

    public static void signup()
    {
        render("signup.html");
    }
    public static void login()
    {
        render("login.html");
    }
    public static void register(String name,String gender, String email, String password, String address, double height, double startingWeight)
    {
        Logger.info("Registering new user " + email);
        Member member = new Member(name,gender, email, password, address, height, startingWeight);
        member.save();
        redirect("/");
    }
    public static void authenticate(String email, String password)
    {
        Logger.info("Attempting to authenticate with " + email + ":" + password);

        Member member = Member.findByEmail(email);
        if ((member != null) && (member.checkPassword(password) == true)) {
            Logger.info("Authentication Member successful");
            session.put("logged_in_Memberid", member.id);
            redirect ("/dashboard");
        } else {
            Trainer trainer = Trainer.findByEmail(email);
            if((trainer!=null) &&trainer.checkPassword(password)==true){
                Logger.info("Authentication Trainer successful");
                session.put("logged_in_Trainerid", trainer.id);
                redirect ("/trainerdashboard");
            }

            Logger.info("Authentication failed");
            redirect("/login");
        }
    }

    public static void logout()
    {
        session.clear();
        redirect ("/");
    }

    public static Member getLoggedInMember()
    {
        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
        } else {
            login();
        }
        return member;
    }
    public static Trainer getLoggedInTrainer()
    {
        Trainer trainer = null;
        if (session.contains("logged_in_Trainerid")) {
            String memberId = session.get("logged_in_Trainerid");
            trainer = Trainer.findById(Long.parseLong(memberId));
        } else {
            login();
        }
        return trainer;
    }

    public static String userType(){
        String str = "";
        if (session.contains("logged_in_Memberid")) {
            str="member";
        }
        else if(session.contains("logged_in_Trainerid")){
            str="trainer";
        }

        return str;
    }

    public static void getMemberInfo() {
        Logger.info("Rendering Account Settings");

        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
        } else {
            login();
        }
        render ("accountsettings.html",member);
    }
    public static void getTrainerInfo() {
        Logger.info("Rendering Account Settings");

        Trainer trainer = null;
        if (session.contains("logged_in_Trainerid")) {
            String trainerId = session.get("logged_in_Trainerid");
            trainer = Trainer.findById(Long.parseLong(trainerId));
        } else {
            login();
        }
        render ("traineraccountsettings.html",trainer);
    }

    public static void editName(String name){
        Member member =getLoggedInMember();
        member.setName(name);
        member.save();
        Logger.info("Updating Name");
        redirect("/accountsettings");
    }

    public static void editGender(String gender){
        Member member =getLoggedInMember();
        member.setGender(gender);
        member.save();
        Logger.info("Updating Gender");
        redirect("/accountsettings");
    }

    public static void editPassword(String password){
        Member member =getLoggedInMember();
        member.setPassword(password);
        member.save();
        Logger.info("Updating Password");
        redirect("/accountsettings");
    }

    public static void editEmail(String email){
        Member member =getLoggedInMember();
        member.setEmail(email);
        member.save();
        Logger.info("Updating Email");
        redirect("/accountsettings");
    }

    public static void editHeight(double height){
        Member member =getLoggedInMember();
        member.setHeight(height);
        member.save();
        Logger.info("Updating Height");
        redirect("/accountsettings");
    }

    public static void editAddress(String address){
        Member member =getLoggedInMember();
        member.setAddress(address);
        member.save();
        Logger.info("Updating Address");
        redirect("/accountsettings");
    }

    public static void editStartingWeight(double weight){
        Member member =getLoggedInMember();
        member.setStartingWeight(weight);
        member.save();
        Logger.info("Updating Weight");
        redirect("/accountsettings");
    }

    public static void editTrainerName(String name){
        Trainer trainer =getLoggedInTrainer();
        trainer.setName(name);
        trainer.save();
        Logger.info("Updating Name");
        redirect("/traineraccountsettings");
    }

    public static void editTrainerGender(String gender){
        Trainer trainer =getLoggedInTrainer();
        trainer.setGender(gender);
        trainer.save();
        Logger.info("Updating Gender");
        redirect("/traineraccountsettings");
    }

    public static void editTrainerPassword(String password){
        Trainer trainer =getLoggedInTrainer();
        trainer.setPassword(password);
        trainer.save();
        Logger.info("Updating Password");
        redirect("/traineraccountsettings");
    }

    public static void editTrainerEmail(String email){
        Trainer trainer =getLoggedInTrainer();
        trainer.setEmail(email);
        trainer.save();
        Logger.info("Updating Email");
        redirect("/traineraccountsettings");
    }
    public static void editTrainerAddress(String address){
        Trainer trainer =getLoggedInTrainer();
        trainer.setAddress(address);
        trainer.save();
        Logger.info("Updating Address");
        redirect("/traineraccountsettings");
    }

}
