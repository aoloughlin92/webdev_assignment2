package controllers;

        import models.Assessment;
        import models.Member;
        import models.Trainer;
        import play.Logger;
        import play.mvc.Controller;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Collections;
        import java.util.SortedSet;

public class TrainerDashboard extends Controller
{

    public static void index() {
            Logger.info("Rendering Dashboard");
            Trainer trainer = Accounts.getLoggedInTrainer();
            List<Member> memberList = Member.findAll();
            for(Member member: memberList){
                member.assessmentCount();
            }
            render("trainerdashboard.html",trainer,memberList);

    }

    public static void deleteMember( Long id)
    {
        Member member = Member.findById(id);
        Logger.info("Removing member" + id);
        member.delete();
        redirect("/trainerdashboard");

    }

    public static void selectMember(Long id){
        Member member = Member.findById(id);
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
        Logger.info("Member id = "+id);
        render("member.html",member,memberBMI,assessmentlist,isIdealBMI,BMICategory);
    }

    public static void addComment(Long id, Long assessmentid,String comment)
    {
        Member member = Member.findById(id);
        Assessment assessment = Assessment.findById(assessmentid);
        assessment.addComment(comment);
        assessment.save();
        member.save();
        Logger.info("Adding Assessment");
        redirect("/members/"+id);
    }

}