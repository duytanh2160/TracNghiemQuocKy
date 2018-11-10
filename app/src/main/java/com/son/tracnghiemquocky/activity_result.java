package com.son.tracnghiemquocky;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class activity_result extends Activity {

    Button ReturnBT;
    PieChart ResultChart;
    MediaPlayer winningSound;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        winningSound = MediaPlayer.create(this,R.raw.win);
        winningSound.start();

        DisplayMetrics Dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(Dm);

        ResultChart = (PieChart) findViewById(R.id.ResultPieChart);
        ResultChart.setUsePercentValues(true);
        ResultChart.setEntryLabelTextSize(8f);
        ResultChart.getDescription().setEnabled(false);
        ResultChart.setExtraOffsets(5,10,5,5);
        ResultChart.setRotationEnabled(true);
        ResultChart.setDrawHoleEnabled(true);
        ResultChart.setHoleColor(Color.WHITE);
        ResultChart.setTransparentCircleRadius(60f);
        ResultChart.setCenterText("Biểu đồ tỉ lệ trả lời đúng và sai");

        int width = Dm.widthPixels;
        int height = Dm.heightPixels;
        int soCauDung,soCauSai;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        ReturnBT = (Button)findViewById(R.id.BtnReturn);


        ReturnBT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });


        Intent callerIntent = getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("MyPackage");
        soCauDung = packageFromCaller.getInt("KQ");
        soCauSai = (packageFromCaller.getInt("Socau") - packageFromCaller.getInt("KQ"));

        List<PieEntry> EntriesVal = new ArrayList<>();
        EntriesVal.add(new PieEntry(soCauDung,"Chọn đúng"));
        EntriesVal.add(new PieEntry(soCauSai,"Chọn sai"));

        PieDataSet dataSet = new PieDataSet(EntriesVal,"");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setValueFormatter(new MyValueFormatter());
        PieData data = new PieData(dataSet);

        data.setValueTextSize(9.1f);
        data.setValueTextColor(Color.YELLOW);

        ResultChart.setData(data);
        ResultChart.invalidate();
    }
}