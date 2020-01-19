package models;

import jdk.nashorn.internal.ir.Assignment;
import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.*;
import java.util.Collections;

import static java.lang.Math.abs;

@Entity
public class Member extends Model
{
    public String name;
    public String gender;
    public String email;
    public String password;
    public String address;
    public double height;
    public double startingWeight;
    public int noOfAssessments;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Assessment> assessmentlist = new ArrayList<Assessment>();

    public Member(String name, String gender, String email, String password, String address, double height, double startingWeight)
    {

        this.name = name;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.address = address;
        this.height = height;
        this.startingWeight = startingWeight;
        this.noOfAssessments = 0;
    }

    public Assessment latestAssessment(){
        if(assessmentlist.size()!=0)
            return assessmentlist.get(assessmentlist.size()-1);
        else
            return null;
    }


    public static Member findByEmail(String email)
    {
        return find("email", email).first();
    }

    public boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }



    public double getHeight() {
        return height;
    }
    public double getHeightInInches(){
        return height*39.37;
    }

    public String getGender(){
        return gender;
    }
    // public abstract void chosenPackage(String chosenPackage);


    public double getStartingWeight() {
        return startingWeight;
    }

    public double idealBodyWeight(){
        double height = getHeightInInches();
        double idealWeight;
        double lowerLimit;
        String gender =getGender();

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

        return idealWeight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setPassword(String password){
     this.password = password;
    }

    public void setStartingWeight(double startingWeight) {
        this.startingWeight = startingWeight;
    }

    public void assessmentCount(){
        noOfAssessments = assessmentlist.size();
    }

    public void sortAssessmentList(){
        Collections.sort(assessmentlist,new SortbyMillis());
        for(int i=0;i<assessmentlist.size();i++) {
            System.out.println(assessmentlist.get(i).getTimeStamp());
        }
    }


}

