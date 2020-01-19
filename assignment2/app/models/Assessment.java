package models;


import play.db.jpa.Model;

import javax.persistence.Entity;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.lang.Math.abs;


@Entity
public class Assessment extends Model
{
   public double weight;
    public double chest;
    public double thigh;
    public double upperArm;
    public double waist;
    public double hips;
    public String timeStamp;
    public String trend;
    public String comment;
    public long millis;



    public Assessment(double weight, double chest, double thigh, double upperArm, double waist, double hips)
    {
        this.weight = weight;
        this.chest = chest;
        this.thigh = thigh;
        this.upperArm = upperArm;
        this.waist = waist;
        this.hips = hips;

        this.timeStamp = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        this.millis = System.currentTimeMillis();
        this.trend = "unchanged";
    }

    public double getWeight() {
        return weight;
    }

    public void setTrend(double previousWeight,double idealWeight){
        //as assessments are per person and height is constant, BMI trend can be calculated by comparing weights
        if(abs(idealWeight - weight)<abs(idealWeight-previousWeight))
        {
            trend ="ui green tag label";
        }
        else if(abs(idealWeight - weight)>abs(idealWeight-previousWeight)){
            trend = "ui red tag label";
        }
        else if(abs(idealWeight - weight)==abs(idealWeight-previousWeight)){
            trend = "ui blue tag label";
        }
    }
    public void addComment(String comment){
        this.comment = comment;
    }

    public long getMillis() {
        return millis;
    }
    public String getTimeStamp()
    {
        return timeStamp;
    }
}
