package controllers;

import models.Assessment;
import models.Member;

import static java.lang.Math.abs;

public class GymUtility {

    public static double calculateBMI(Member member, Assessment assessment){
        double bmi = assessment.getWeight()/(member.getHeight()*member.getHeight());
        return (int) (bmi*100)/100.0;
    }


    public static double calculateStartBMI(Member member){
        double bmi = member.getStartingWeight()/(member.getHeight()*member.getHeight());
        return (int) (bmi*100)/100.0;
    }

    public static String determineBMICategory(double bmiValue){
       if(bmiValue < 16){
            return "SEVERELY UNDERWEIGHT";
       }
       else if(bmiValue>= 16 && bmiValue<18.5){
            return "UNDERWEIGHT";
       }
       else if(bmiValue >=18.5 && bmiValue < 25){
           return "NORMAL";
       }
       else if(bmiValue >=25 && bmiValue < 30){
           return "OVERWEIGHT";
       }
       else if(bmiValue >=30 && bmiValue < 35){
           return "MODERATELY OBESE";
       }
       else if(bmiValue >=35){
           return "SEVERELY OBESE";
       }
       else
           return "";
    }

    public static boolean isIdealBodyWeight(Member member, Assessment assessment){
        double height = member.getHeightInInches();
        double weight = assessment.getWeight();
        double idealWeight;
        double lowerLimit;
        String gender = member.getGender();

        if(gender.charAt(0) == 'm'|| gender.charAt(0) == 'M')
        {
           lowerLimit = 50;
        }
        else
        {
            lowerLimit = 45.5;
        }

        if(height<60)
        {
            idealWeight = lowerLimit;
        }
        else{
            idealWeight = lowerLimit +(2.3*(height - 60));
        }

        return (abs(weight - idealWeight)<2);
    }


    public static boolean isIdealStartBodyWeight(Member member){
        double height = member.getHeightInInches();
        double weight = member.getStartingWeight();
        double idealWeight;
        double lowerLimit;
        String gender = member.getGender();

        if(gender.charAt(0) == 'm'|| gender.charAt(0) == 'M')
        {
            lowerLimit = 50;
        }
        else
        {
            lowerLimit = 45.5;
        }

        if(height<60)
        {
            idealWeight = lowerLimit;
        }
        else{
            idealWeight = lowerLimit +(2.3*(height - 60));
        }

        return (abs(weight - idealWeight)<2);
    }

    private double toTwoDecimalPlaces(double num){
        return (int) (num *100 ) /100.0;
    }


    public static void updateAssessmentTrend(Member member) {
        double idealWeight = member.idealBodyWeight();
        //update trend for all previous assessments

        for (int i = member.assessmentlist.size()-1; i >= 0; i--) {
            double previousWeight;
            if(i==member.assessmentlist.size()-1) {
                previousWeight = member.getStartingWeight();
            }
            else
            {
                previousWeight = member.assessmentlist.get(i+1).getWeight();
            }
            member.assessmentlist.get(i).setTrend(previousWeight, idealWeight);
        }
    }
}
