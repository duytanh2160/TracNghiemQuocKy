package com.son.tracnghiemquocky;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class MainActivity extends AppCompatActivity {

    Button BtEasy,BtHard,BtManual,BtExit,BtMedium,BtHighScore;
    //ImageView ImgTuaDe;
    MediaPlayer btSound;
    Animation upFromBottom,downFromTop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btSound = MediaPlayer.create(this,R.raw.button3);

        //ImgTuaDe = (ImageView)findViewById(R.id.ImgTuade);


        BtEasy = (Button)findViewById(R.id.BtnEasyMode);
        BtMedium = (Button)findViewById(R.id.BtnMediumMode);
        BtHard = (Button)findViewById(R.id.BtnHardMode);
        BtManual = (Button)findViewById(R.id.BtnManual);
        BtExit = (Button)findViewById(R.id.BtnExit);
        BtHighScore = (Button)findViewById(R.id.BtnHighScore);

        upFromBottom = AnimationUtils.loadAnimation(this,R.anim.upfrombottom);
        downFromTop = AnimationUtils.loadAnimation(this,R.anim.downfromtop);
        AnimationButton();

        BtEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btSound.start();
                Intent intent = new Intent(MainActivity.this,activity_easymode.class);
                startActivity(intent);
            }
        });

        //BtEasy.startAnimation(leftToRight);
        BtHard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btSound.start();
                Bundle bundle = new Bundle();
                Intent intent = new Intent(MainActivity.this,activity_gameplay.class);
                bundle.putString("Continent","");
                bundle.putString("modeChose","hard");                                   //////////////////////////////////////
                intent.putExtra("MyPackage",bundle);
                startActivity(intent);
            }
        });

        BtMedium.setOnClickListener(new View.OnClickListener(){                                   ////////////////////////////////////// nguyên hàm này
            @Override
            public void onClick(View v){
                btSound.start();
                Bundle bundle = new Bundle();
                Intent intent = new Intent(MainActivity.this,activity_gameplay.class);
                bundle.putString("Continent","");
                bundle.putString("modeChose","medium");
                intent.putExtra("MyPackage",bundle);
                startActivity(intent);
            }
        });

        BtHighScore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btSound.start();
                Intent intent = new Intent(MainActivity.this,activity_highscore.class);
                startActivity(intent);
            }
        });

        BtManual.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btSound.start();
                Intent intent = new Intent(MainActivity.this,activity_manualcredit.class);
                startActivity(intent);
            }
        });

        BtExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btSound.start();
                finish();
            }
        });

    }

    void AnimationButton(){
        BtEasy.setAnimation(upFromBottom);
        BtHard.setAnimation(upFromBottom);
        BtMedium.setAnimation(upFromBottom);
        BtManual.setAnimation(upFromBottom);
        BtExit.setAnimation(upFromBottom);
        BtHighScore.setAnimation(upFromBottom);
        //ImgTuaDe.setAnimation(downFromTop);
    }

}
