package com.son.tracnghiemquocky;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import android.os.Handler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/*class Flags{
    public String ID;
    public String ImgName;
    public String Continent;
    public String AnswerA,AnswerB,AnswerC,AnswerD,Answer;

}*/
class Flags{
    public String ID;
    public String ImgName;
    public String Continent;
    public String Answer;
    public String Difficulty;

}

class Country{
    public String ID;
    public String Name;
}

public class activity_gameplay extends Activity {

    Random rand = new Random();
    ImageView ImgQuocKy,ImgLayer;
    TextView TxtSoLuongCauHoi,TxtSoCauDung,TxtQuestion;
    RadioGroup RG;
    RadioButton A,B,C,D;
    Button BT;
    ProgressBar mProgressBar;
    int[] HighScore = new int[3];
    Handler mHandler = new Handler();

    ArrayList<Flags> flagList = new ArrayList<>();
    ArrayList<Country> countryList = new ArrayList<Country>();
    ArrayList<Integer> previousID = new ArrayList<Integer>();

    int flagID = 0;
    int soCauDung = 0;
    int dem = 0;
    int mProgressStatus = 0;
    int getmProgressMax = 100;
    int soCauPhaiTraLoi = 0;

    String modeChose;

    boolean isOnStop = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        ImgQuocKy = (ImageView)findViewById(R.id.ImgQuocKy);
        ImgLayer = (ImageView)findViewById(R.id.ImgLayerCheCo);

        TxtSoLuongCauHoi = (TextView)findViewById(R.id.TxtSoLuongCauHoi);
        TxtQuestion = (TextView)findViewById(R.id.TxtQuestion);
        TxtSoCauDung = (TextView)findViewById(R.id.TxtSoCauDung);

        RG = (RadioGroup)findViewById(R.id.RadioGroupAnswer);
        BT = (Button)findViewById(R.id.BtnXacnhan);

        mProgressBar = (ProgressBar) findViewById(R.id.ProgressBar);

        A = (RadioButton)findViewById(R.id.RdbA);
        B = (RadioButton)findViewById(R.id.RdbB);
        C = (RadioButton)findViewById(R.id.RdbC);
        D = (RadioButton)findViewById(R.id.RdbD);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ICIELPANTON-BLACK_0.OTF");

        TxtSoLuongCauHoi.setTypeface(typeface);
        TxtQuestion.setTypeface(typeface);
        A.setTypeface(typeface);
        B.setTypeface(typeface);
        C.setTypeface(typeface);
        D.setTypeface(typeface);

        LoadHighScore();

        Intent callerIntent = getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("MyPackage");
        modeChose = packageFromCaller.getString("modeChose");
        setNumberOfQuestion(modeChose);
        ReadData(packageFromCaller.getString("Continent"),modeChose);

        Random ran = new Random();

        //Thực hiện random câu hỏi
        for (int i=0; i< flagList.size(); i++) {                       //Đầu tiên tạo 1 dãy số từ 1->10 (10 vì data hiện có 10 cờ)
            previousID.add(new Integer(i));
        }
        Collections.shuffle(previousID);

        flagID = previousID.get(dem);
        Display(flagID);                                                //Bắt đầu

        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                BT.setEnabled(true);
            }
        });
        ProgressBar();



    }

    @Override
    protected  void onStart(){
        super.onStart();


    }
    @Override
    protected void onResume(){
        super.onResume();
        isOnStop = false;
        BT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int idCheck = RG.getCheckedRadioButtonId();
                switch (idCheck) {
                    case R.id.RdbA:
                        if (flagList.get(flagID).Answer.compareTo((String)A.getText()) == 0) {
                            soCauDung = soCauDung + 1;
                        }
                        break;
                    case R.id.RdbB:
                        if (flagList.get(flagID).Answer.compareTo((String)B.getText()) == 0  ) {
                            soCauDung = soCauDung + 1;
                        }
                        break;
                    case R.id.RdbC:
                        if (flagList.get(flagID).Answer.compareTo((String)C.getText()) == 0) {
                            soCauDung = soCauDung + 1;
                        }
                        break;
                    case R.id.RdbD:
                        if (flagList.get(flagID).Answer.compareTo((String)D.getText()) == 0) {
                            soCauDung = soCauDung + 1;
                        }
                        break;
                }
                dem++;
                if(dem >= soCauPhaiTraLoi){
                    if(modeChose.compareTo("hard") == 0 || modeChose.compareTo("medium") == 0) {                                       ///////////
                        //mProgressStatus = getmProgressMax;
                        mProgressBar.setEnabled(false);
                    }
                    Intent intent = new Intent(activity_gameplay.this,activity_result.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("KQ",soCauDung);
                    bundle.putInt("Socau",soCauPhaiTraLoi);
                    intent.putExtra("MyPackage",bundle);
                    startActivity(intent);
                    SetHighScore(soCauDung,modeChose);
                    SaveHighScore();
                    finish();
                }
                else {
                    mProgressStatus = 0;
                    flagID = previousID.get(dem);
                    Display(flagID);
                }
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        isOnStop = true;
    }

    void setNumberOfQuestion(String modeChose){
        if(modeChose.compareTo("easy") == 0)
            soCauPhaiTraLoi = 10;
        else if (modeChose.compareTo("hard") == 0)
            soCauPhaiTraLoi = 30;
        else if (modeChose.compareTo("medium")==0)
            soCauPhaiTraLoi = 30;
    }

    void ProgressBar(){
        if(modeChose.compareTo("easy") == 0)
            mProgressBar.setEnabled(false);
        if(modeChose.compareTo("hard") == 0 || modeChose.compareTo("medium") == 0 ) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                        while (mProgressStatus < getmProgressMax) {
                            if(isOnStop == false) {
                                mProgressStatus++;
                                android.os.SystemClock.sleep(50);
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgressBar.setProgress(mProgressStatus);
                                    }
                                });
                            }
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mProgressStatus = 0;
                                if (dem < soCauPhaiTraLoi - 1) {
                                    ProgressBar();
                                    dem++;
                                    flagID = previousID.get(dem);
                                    Display(flagID);
                                } else {
                                    Intent intent = new Intent(activity_gameplay.this, activity_result.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("KQ", soCauDung);
                                    bundle.putInt("Socau", flagList.size());
                                    intent.putExtra("MyPackage", bundle);
                                    startActivity(intent);
                                    SetHighScore(soCauDung,modeChose);
                                    SaveHighScore();
                                    finish();
                                }
                            }
                        });
                    }
            }).start();
        }
    }


    void Display(int i){

        String selectedFlagID = flagList.get(i).ID;
        int temp = 0;
        int randomAnswer[] = new int[3];

        ArrayList<Integer> random2 = new ArrayList<Integer>();

        //Thực hiện ngẫu nhiên câu trả lời
        for (int j = 0; j < countryList.size() ; j++) {
            random2.add(new Integer(j));
        }
        Collections.shuffle(random2);

        for (int j = 0; j < countryList.size() && temp <= 2 ; j++) {             //lọc những câu trả lời khác ko trùng với "Câu trả lời đúng"
            if(countryList.get(random2.get(j)).ID != selectedFlagID) {
                randomAnswer[temp] = random2.get(j);
                temp++;
            }
        }


        int position = dem + 1;

        Resources res = getResources();
        String mDrawableName = flagList.get(i).ImgName;
        int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
        Drawable drawable = res.getDrawable(resID );
        ImgQuocKy.setImageDrawable(drawable);


        if(modeChose.compareTo("hard") == 0) {
            //Ngẫu nhiên layer che cờ
            int randomLayer = rand.nextInt((4 - 1) + 1) + 1;
            if (randomLayer == 1)
                mDrawableName = "layer1";
            else if (randomLayer == 2)
                mDrawableName = "layer2";
            else if (randomLayer == 3)
                mDrawableName = "layer3";
            else if (randomLayer == 4)
                mDrawableName = "layer4";
            resID = res.getIdentifier(mDrawableName, "drawable", getPackageName());
            Drawable drawable2 = res.getDrawable(resID);
            ImgLayer.setImageDrawable(drawable2);
        }


        TxtSoLuongCauHoi.setText("" + position + " / " + "" +soCauPhaiTraLoi);
        TxtSoCauDung.setText("Corrected: " + soCauDung);

        int random = rand.nextInt((4 - 1) + 1) + 1;
        if(random == 1) {
            A.setText(flagList.get(i).Answer);
            B.setText(countryList.get(randomAnswer[0]).Name);
            C.setText(countryList.get(randomAnswer[1]).Name);
            D.setText(countryList.get(randomAnswer[2]).Name);
        }
        else if(random == 2) {
            B.setText(flagList.get(i).Answer);
            A.setText(countryList.get(randomAnswer[0]).Name);
            C.setText(countryList.get(randomAnswer[1]).Name);
            D.setText(countryList.get(randomAnswer[2]).Name);
        }
        else if(random == 3) {
            C.setText(flagList.get(i).Answer);

            A.setText(countryList.get(randomAnswer[0]).Name);
            B.setText(countryList.get(randomAnswer[1]).Name);
            D.setText(countryList.get(randomAnswer[2]).Name);
        }
        else if(random == 4) {
            D.setText(flagList.get(i).Answer);
            A.setText(countryList.get(randomAnswer[0]).Name);
            B.setText(countryList.get(randomAnswer[1]).Name);
            C.setText(countryList.get(randomAnswer[2]).Name);
        }
        RG.clearCheck();
        BT.setEnabled(false);
    }

    void ReadData(String selectedContinent, String modeChose)
    {
        try {
            DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = DBF.newDocumentBuilder();
            InputStream in = getAssets().open("data.xml");

            Document doc = builder.parse(in);
            Element root = doc.getDocumentElement();
            NodeList list = root.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if(node instanceof Element){
                    Element Item = (Element) node;

                    NodeList listChild = Item.getElementsByTagName("ID");
                    String ID = listChild.item(0).getTextContent();

                    listChild = Item.getElementsByTagName("ImgName");
                    String ImgName = listChild.item(0).getTextContent();

                    listChild = Item.getElementsByTagName("Continent");
                    String Continent = listChild.item(0).getTextContent();

                    listChild = Item.getElementsByTagName("Answer");
                    String Answer = listChild.item(0).getTextContent();

                    listChild = Item.getElementsByTagName("Difficulty");
                    String Difficulty = listChild.item(0).getTextContent();

                    if(selectedContinent.compareTo(Continent)== 0 && modeChose.compareTo("easy") == 0) {    //Chỉ ghi cờ vào khi Continent của data = Continent được chọn
                        Flags F1 = new Flags();
                        F1.ID = ID;
                        F1.ImgName = ImgName;
                        F1.Continent = Continent;
                        F1.Answer = Answer;
                        flagList.add(F1);
                        Country C1 = new Country();
                        C1.ID = ID;
                        C1.Name = Answer;
                        countryList.add(C1);
                    }
                    else if(modeChose.compareTo("medium") == 0 && Difficulty.compareTo("2") == 0) {
                        Flags F1 = new Flags();
                        F1.ID = ID;
                        F1.ImgName = ImgName;
                        F1.Continent = Continent;
                        F1.Answer = Answer;
                        flagList.add(F1);
                        Country C1 = new Country();
                        C1.ID = ID;
                        C1.Name = Answer;
                        countryList.add(C1);
                    }
                    else if(modeChose.compareTo("hard") == 0 && Difficulty.compareTo("3") == 0){
                        Flags F1 = new Flags();
                        F1.ID = ID;
                        F1.ImgName = ImgName;
                        F1.Continent = Continent;
                        F1.Answer = Answer;
                        flagList.add(F1);
                        Country C1 = new Country();
                        C1.ID = ID;
                        C1.Name = Answer;
                        countryList.add(C1);
                    }
                }
            }
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (SAXException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
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

    void SetHighScore(int soCauDung, String modeChose){
        if(soCauDung > HighScore[0] || soCauDung > HighScore[1] || soCauDung > HighScore[2]){
            if(modeChose.compareTo("easy") == 0){
                HighScore[0] = soCauDung;
            }
            if(modeChose.compareTo("medium") == 0){
                HighScore[1] = soCauDung;
            }
            if(modeChose.compareTo("hard") == 0){
                HighScore[2] = soCauDung;
            }
        }
    }

    void SaveHighScore(){
        SharedPreferences shaPre = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shaPre.edit();
        editor.putInt("highscore_easy",HighScore[0]);
        editor.putInt("highscore_medium",HighScore[1]);
        editor.putInt("highscore_hard",HighScore[2]);
        editor.apply();
    }



}
