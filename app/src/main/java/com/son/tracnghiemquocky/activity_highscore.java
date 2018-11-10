package com.son.tracnghiemquocky;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;

public class activity_highscore extends Activity {
    TextView TxtSoDiem;
    Animation upFromBottom;
    int[] HighScore = new int[3];

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        upFromBottom = AnimationUtils.loadAnimation(this,R.anim.upfrombottom);

        float groupSpace = 0.06f;
        float barSpace = 0.02f;
        float barWidth = 0.45f;

        LoadHighScore();

        BarChart chart = (BarChart) findViewById(R.id.chart);

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        entries1.add(new BarEntry(0f,(float)HighScore[0]));
        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();
        entries2.add(new BarEntry(1f,(float)HighScore[1]));
        ArrayList<BarEntry> entries3 = new ArrayList<BarEntry>();
        entries3.add(new BarEntry(2f,(float)HighScore[2]));

        BarDataSet set1 = new BarDataSet(entries1,"Easy Mode");
        BarDataSet set2 = new BarDataSet(entries2,"Normal Mode");
        BarDataSet set3 = new BarDataSet(entries3,"Hard Mode");

        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set1.setValueTextSize(10);
        set2.setColors(Color.LTGRAY);
        set2.setValueTextSize(10);
        set3.setColors(Color.CYAN);
        set3.setValueTextSize(10);

        BarData data = new BarData(set1,set2,set3);
        data.setBarWidth(barWidth);
        chart.setData(data);
        chart.setFitBars(true);
        chart.invalidate();
    }


    void LoadHighScore(){
        SharedPreferences shaPre = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        if(shaPre != null){
            if((Integer)shaPre.getInt("highscore_easy",0) != null) {
                HighScore[0] = shaPre.getInt("highscore_easy", 0);
            } else HighScore[0] = 0;
            if((Integer)shaPre.getInt("highscore_medium",0) != null) {
                HighScore[1] = shaPre.getInt("highscore_medium", 0);
            } else HighScore[1] = 0;
            if((Integer)shaPre.getInt("highscore_hard",0) != null) {
                HighScore[2] = shaPre.getInt("highscore_hard", 0);
            } else HighScore[2] = 0;
        }
        else{
            HighScore[0] = HighScore[1] = HighScore[2] = 0;
        }
    }
}
