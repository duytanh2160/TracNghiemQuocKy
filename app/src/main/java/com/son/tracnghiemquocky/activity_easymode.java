package com.son.tracnghiemquocky;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresPermission;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

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



public class activity_easymode extends Activity {

    RadioGroup RG;
    RadioButton Asia,Europe,Africa,NorthAmerica,SouthAmerica,AustraliaOceania;
    Button BtConfirm;
    MediaPlayer btSound;
    ImageView ImgMapReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easymode);
        RG = (RadioGroup)findViewById(R.id.RadioGroupContinent);
        Asia = (RadioButton)findViewById(R.id.RbAsia);
        Europe = (RadioButton)findViewById(R.id.RbEurope);
        Africa = (RadioButton)findViewById(R.id.RbAfrica);
        NorthAmerica = (RadioButton)findViewById(R.id.RbNorthAmerica);
        SouthAmerica = (RadioButton)findViewById(R.id.RbSouthAmerica);
        AustraliaOceania = (RadioButton)findViewById(R.id.RbAustraliaOceania);
        BtConfirm = (Button)findViewById(R.id.BtnConfirm);
        btSound = MediaPlayer.create(this,R.raw.button4);
        ImgMapReview = (ImageView)findViewById(R.id.ImgMapReview);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ICIELPANTON-LIGHT_0.OTF");
        Asia.setTypeface(typeface);
        Europe.setTypeface(typeface);
        Africa.setTypeface(typeface);
        NorthAmerica.setTypeface(typeface);
        SouthAmerica.setTypeface(typeface);
        AustraliaOceania.setTypeface(typeface);

        //Mờ button từ đoạn này
        BtConfirm.setEnabled(false);


        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                BtConfirm.setEnabled(true);
                ImgMapReview.setImageResource(R.drawable.mapasia);
                if (Europe.isChecked()) {
                    ImgMapReview.setImageResource(R.drawable.mapeurope);
                }
                if (Africa.isChecked()) {
                    ImgMapReview.setImageResource(R.drawable.mapafrica);
                }
                if (NorthAmerica.isChecked()) {
                    ImgMapReview.setImageResource(R.drawable.mapnortham);
                }
                if (SouthAmerica.isChecked()) {
                    ImgMapReview.setImageResource(R.drawable.mapsoutham);
                }
                if (AustraliaOceania.isChecked()) {
                    ImgMapReview.setImageResource(R.drawable.mapaustra);
                }
            }
        });

        BtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btSound.start();

                int radioId = RG.getCheckedRadioButtonId();
                Asia = (RadioButton)findViewById(radioId);

                Bundle bundle = new Bundle();
                Intent intent = new Intent(activity_easymode.this,activity_gameplay.class);

                int idCheck = RG.getCheckedRadioButtonId();
                switch(idCheck){
                    case R.id.RbAsia:
                        bundle.putString("Continent","asia");
                        break;
                    case R.id.RbEurope:
                        bundle.putString("Continent","europe");
                        break;
                    case R.id.RbAfrica:
                        bundle.putString("Continent","africa");
                        break;
                    case R.id.RbNorthAmerica:
                        bundle.putString("Continent","northamerica");
                        break;
                    case R.id.RbSouthAmerica:
                        bundle.putString("Continent","southamerica");
                        break;
                    case R.id.RbAustraliaOceania:
                        bundle.putString("Continent","australiaoceania");
                        break;
                }
                bundle.putString("modeChose","easy");                                      ///////////////////
                intent.putExtra("MyPackage",bundle);
                startActivity(intent);
            }
        });
    }
   public void checkButton(View view){
        int radioId = RG.getCheckedRadioButtonId();
        Asia = (RadioButton) findViewById(radioId);
        //Toast.makeText(this, "Selected " + Asia.getText(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.d(this.getClass().getName(), "back button pressed");
        }
        return super.onKeyDown(keyCode, event);
    }

}
