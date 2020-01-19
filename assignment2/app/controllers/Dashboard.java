package controllers;

import models.Assessment;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;
import java.util.List;
import java.util.Collections;
import java.util.SortedSet;

public class Dashboard extends Controller
{
  public static void index() {
    Logger.info("Rendering Dashboard");
    if(Accounts.userType()== "member") {

      Member member = Accounts.getLoggedInMember();

      double memberBMI;
      boolean isIdealBMI;

      if (member.latestAssessment() != null) {
        memberBMI = GymUtility.calculateBMI(member, member.latestAssessment());
        isIdealBMI = GymUtility.isIdealBodyWeight(member, member.latestAssessment());
      }
      else {
        memberBMI = GymUtility.calculateStartBMI(member);
        isIdealBMI = GymUtility.isIdealStartBodyWeight(member);
      }
      String BMICategory = GymUtility.determineBMICategory(memberBMI);
      List<Assessment> assessmentlist = member.assessmentlist;//Assessment.findAll();
      member.sortAssessmentList();
      GymUtility.updateAssessmentTrend(member);
      render("dashboard.html", member, assessmentlist, memberBMI, BMICategory, isIdealBMI);
    }
    else if(Accounts.userType()== "trainer"){
      Trainer trainer = Accounts.getLoggedInTrainer();
      List<Member> memberList = Member.findAll();
      for(Member member: memberList){
        member.assessmentCount();
      }
      render("trainerdashboard.html",trainer,memberList);
    }
  }

  public static void addAssessment(double weight, double chest, double thigh, double upperArm, double waist, double hips)
  {
    Member member = Accounts.getLoggedInMember();
    Assessment assessment = new Assessment(weight, chest, thigh, upperArm, waist, hips);
    assessment.save();
    member.assessmentlist.add(assessment);
    GymUtility.updateAssessmentTrend(member);
    member.assessmentCount();
    member.save();
    Logger.info("Adding Assessment");
    redirect("/dashboard");
  }
  public static void deleteAssessment(Long id, Long assessmentid)
  {
    Member member = Accounts.getLoggedInMember();
    Assessment assessment = Assessment.findById(assessmentid);
    member.assessmentlist.remove(assessment);
    GymUtility.updateAssessmentTrend(member);
    member.assessmentCount();
    member.save();
    assessment.delete();
    Logger.info("Deleting Assessment");
    redirect("/dashboard");

  }




}

