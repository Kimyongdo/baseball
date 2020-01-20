package com.tistory.firewallgogo.baseballgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    ImageView answeriv01;
    ImageView answeriv02;
    ImageView answeriv03;
    ImageView[] iv = new ImageView[10];
    ImageView[] im = new ImageView[10];
    int[] nn=new int[3];
    ImageView ivcancel;
    ImageView ivstart;
    ImageView ivtop;
    ScrollView sv;
    static int number =12;
    int[] com = new int[3]; //컴퓨터 랜덤 번호
    int[] me = new int[3]; //내가 선택한 번호
    int strike =0; //스트라이크
    int ball =0; //볼
    TextView tvscroll;
    int account=0; //기회
    ImageView ivlosedialog; //패배 다이얼로그 이미지
    TextView tvlosedialog; //패배 다이얼로그 텍스트
    ImageView ivwindialog; //승리 다이얼로그 이미지
    TextView tvwindialog; //승리 다이얼로그 텍스트
    private InterstitialAd mInterstitialAd; //광고



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ////////////////광고//////////////////////////////
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        //ca-app-pub-3940256099942544/1033173712 테스트 코드
        mInterstitialAd.setAdUnitId("ca-app-pub-2398130378795170/8340259911");
        //mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int i) { //로드 실패했을 경우
                super.onAdFailedToLoad(i);
                //Toast.makeText(MainActivity.this,"로드실패",Toast.LENGTH_SHORT).show();
            }
        });


        //컴퓨터 랜덤번호
        Random rnd = new Random();
        for(int i=0; i<com.length; i++){
            com[i]=rnd.nextInt(10);
            for(int k=0; k<i; k++){
                if(com[i]==com[k]){
                    i--;
                    break;
                }
            }
        }


/////////////////////////////////////////////////////////////////////////////
        //find 연결 및 태그 넣기
        //위치에 태그를 넣는 중.
        for(int i=0; i<iv.length; i++){
            iv[i] = findViewById(R.id.iv0+i);
            iv[i].setTag(i+"");
            iv[i].setOnClickListener(listener); //각 번호를 누르는 순간.
        }


        for(int i=0; i<iv.length; i++){
            im[i] = findViewById(R.id.iv0+i);
        }
        //취소이미지 연결
        ivcancel = findViewById(R.id.ivcancel);
        ivcancel.setOnClickListener(listenercancel);

        //맞출 세자리 숫자들
        tv=findViewById(R.id.tv);
        answeriv01 = findViewById(R.id.answeriv01);//12
        answeriv02 = findViewById(R.id.answeriv02);//13
        answeriv03 = findViewById(R.id.answeriv03);//14
        answeriv01.setTag("12");
        answeriv02.setTag("13");
        answeriv03.setTag("14");

        //start연결
        ivstart = findViewById(R.id.ivstart);
         //상단 그림 연결
        ivtop = findViewById(R.id.ivtop);
        //스크롤뷰
        sv=findViewById(R.id.sv);
        //전광판
        //tvscroll = findViewById(R.id.tvscroll);
        //tvscroll.setSelected(true);
        ivtop.setImageResource(R.drawable.mainstartgame11);




    }//onCreate


    //0 1 2 3 4 ..누르면 대표수 0 1 2 3.. 올라감.
  View.OnClickListener listener  = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
                ImageView iv = (ImageView)view;
                String s = iv.getTag().toString();
                int n = Integer.parseInt(s); //클릭한 이미지의 태그번호가 들어온다.


          //모두 동일한 n이지만 누른 버튼이 서로 달라 n의 값은 모두 다르다.
          if(number==12) {
              answeriv01.setImageResource(R.drawable.num00+n); //tag==0이면 이미지 0번 보여줌.
              im[n].setImageResource(R.drawable.ball0+n); //누른 숫자 이미지를 노란색으로 set.
              nn[0]=n;
              me[0] =n;
              ivstart.setImageResource(R.drawable.ballstartbefore);
              }
          else if(number==13){

              answeriv02.setImageResource(R.drawable.num00+n);
              im[n].setImageResource(R.drawable.ball0+n);
              nn[1]=n;
              me[1]=n;


                  if (me[0] == me[1]){
                      Toast.makeText(MainActivity.this,"숫자를 중복해서 사용할 수 없습니다",Toast.LENGTH_SHORT).show();
                      answeriv02.setImageResource(R.drawable.questionmark);
                      number=12;

                  }


              ivstart.setImageResource(R.drawable.ballstartbefore);


          }
          else if(number==14){
              answeriv03.setImageResource(R.drawable.num00+n);
              im[n].setImageResource(R.drawable.ball0+n);
              nn[2]=n;
              me[2]=n; //내가 누른 번호와 숫자는 동일함.

              if (me[0] == me[2] || me[1] == me[2]){
                  Toast.makeText(MainActivity.this,"숫자를 중복해서 사용할 수 없습니다",Toast.LENGTH_SHORT).show();
                  answeriv03.setImageResource(R.drawable.questionmark);
                  number=13;
              }
              //빈칸이 모두 다 채워지면 이떄 리스너가 발동하도록

              if(number==14){
                  ivstart.setImageResource(R.drawable.ballstart);
                  ivstart.setOnClickListener(listenerstart);
              }

                   /////////////////////////////////////////////////////////////////////////
              //중복된 수가 2개 나오면 다시 리턴하는 쪽으로 바꿔야하는데 오늘 너무 많이 해서 패스하기로
          }



          number++;
            if(number>15){
                number=15;
            }
      }
  };//숫자 누르면 숫자가 간다.


    //취소버튼 - 하나씩 다운되고 범위가 지정됨.
    View.OnClickListener listenercancel  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


          if(number==13){
              answeriv01.setImageResource(R.drawable.questionmark);
              im[nn[0]].setImageResource(R.drawable.num00+nn[0]);
              ivstart.setImageResource(R.drawable.ballstartbefore);
          }

           else if(number==14){
                answeriv02.setImageResource(R.drawable.questionmark);
              im[nn[1]].setImageResource(R.drawable.num00+nn[1]);
              ivstart.setImageResource(R.drawable.ballstartbefore);

            }
            else if(number==15){
                answeriv03.setImageResource(R.drawable.questionmark);
              im[nn[2]].setImageResource(R.drawable.num00+nn[2]);
                 ivstart.setImageResource(R.drawable.ballstartbefore);
            }
            number--;

            if(number<=11) number=12;


        }
    };//최소버튼

    //게임시작버튼
    View.OnClickListener listenerstart = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sv.fullScroll(view.FOCUS_DOWN);

            //횟수
            ivtop.setImageResource(R.drawable.chance0115+account);
            account++;
            //기회 모두 소진 시

            if(account==4) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View v = layoutInflater.inflate(R.layout.support, null);
                builder.setView(v);

                builder.setPositiveButton("광고보기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //광고코드///////////////////////
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mInterstitialAd.loadAd(adRequest);
                        /////////////////////////

                    }
                });

                builder.setNegativeButton("취소하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });



                AlertDialog dialog = builder.create(); //create 생성
                dialog.setCanceledOnTouchOutside(true); //바깥 눌러도 안꺼짐
                dialog.setCancelable(true); //뒤로가기 눌러도 안꺼짐.
                dialog.show();


            }


            if(account==15){
                ivtop.setImageResource(R.drawable.youlose);
                tv.setText("");

                //패배 다이얼로그 띄우기
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //다이얼로그 뜨게 하는 창.
                //builder.setTitle("실패");//창 이름
                LayoutInflater layoutInflater = getLayoutInflater();              //XML-객체로변경
                View v = layoutInflater.inflate(R.layout.losedialog, null);          //다이얼로그에 뜨게 할 XML 모양
                //패배시 다이얼로그 연결
                ivlosedialog = v.findViewById(R.id.ivlosedialog);
                tvlosedialog = v.findViewById(R.id.tvlosedialog);
                builder.setView(v);
                builder.setPositiveButton("다시 도전", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       //광고코드///////////////////////
                        AdRequest adRequest = new AdRequest.Builder().build();
                       mInterstitialAd.loadAd(adRequest);
                       /////////////////////////
                        Intent intent = new Intent(MainActivity.this, activity_second.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("처음으로", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //광고코드///////////////////////
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mInterstitialAd.loadAd(adRequest);
                        /////////////////////////
                        Intent intent = new Intent(MainActivity.this, activity_second.class);
                        startActivity(intent);
                        finish();
                    }
                });

                AlertDialog dialog = builder.create(); //create 생성
                dialog.setCanceledOnTouchOutside(false); //바깥 눌러도 안꺼짐
                dialog.setCancelable(false); //뒤로가기 눌러도 안꺼짐.
                dialog.show();

            }



            //중복 수는 불가능
            while (true) {
//
                //모든 경우의 수와 위치 비교
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (me[i] == com[j] && i == j) strike++;
                        if (me[i] == com[j] && i != j) ball++;
                    }
                }

                //정답이라면
                if (strike == 3) {
                    ivtop.setImageResource(R.drawable.youwin);
                    tv.setText("");
                    answeriv01.setImageResource(R.drawable.questionmark);
                    answeriv02.setImageResource(R.drawable.questionmark);
                    answeriv03.setImageResource(R.drawable.questionmark);
                    ivstart.setImageResource(R.drawable.ballstartbefore);
                    ivstart.setOnClickListener(null);
                    im[nn[0]].setImageResource(R.drawable.num00+nn[0]);
                    im[nn[1]].setImageResource(R.drawable.num00+nn[1]);
                    im[nn[2]].setImageResource(R.drawable.num00+nn[2]);

                    for(int i=0; i<iv.length; i++) {
                        iv[i] = null;
                    }//번호 연결 다 끊어버리기.

                        //승리 다이얼로그 띄우기
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //다이얼로그 뜨게 하는 창.
                        //builder.setTitle("축하합니다!");//창 이름
                        LayoutInflater layoutInflater = getLayoutInflater();              //XML-객체로변경
                        View v = layoutInflater.inflate(R.layout.windialog, null);          //다이얼로그에 뜨게 할 XML 모양
                        //승리시 다이얼로그 연결
                    ivwindialog = v.findViewById(R.id.ivwindialog);
                    tvwindialog = v.findViewById(R.id.tvwindialog);
                        builder.setView(v);

                        builder.setPositiveButton("다시 도전", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //광고코드///////////////////////
                                AdRequest adRequest = new AdRequest.Builder().build();
                                mInterstitialAd.loadAd(adRequest);
                                /////////////////////////
                                Intent intent = new Intent(MainActivity.this, activity_second.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        builder.setNegativeButton("처음으로", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //광고코드///////////////////////
                                AdRequest adRequest = new AdRequest.Builder().build();
                                mInterstitialAd.loadAd(adRequest);
                                /////////////////////////
                                Intent intent = new Intent(MainActivity.this, activity_second.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        AlertDialog dialog = builder.create(); //create 생성
                        dialog.setCanceledOnTouchOutside(false); //바깥 눌러도 안꺼짐
                        dialog.setCancelable(false); //뒤로가기 눌러도 안꺼짐.
                        dialog.show();



                    break;
                }

                tv.append(me[0]+" "+me[1]+" "+me[2]+"  "+"Strike : "+strike+"  "+"Ball : "+ball+"\n");

                //숫자초기화
                number=12;
                //퀘스쳔마크
                answeriv01.setImageResource(R.drawable.questionmark);
                answeriv02.setImageResource(R.drawable.questionmark);
                answeriv03.setImageResource(R.drawable.questionmark);
                //시작버튼 검은색으로
                ivstart.setImageResource(R.drawable.ballstartbefore);
                ivstart.setOnClickListener(null);
                //노란색버튼->흰색버튼
                im[nn[0]].setImageResource(R.drawable.num00+nn[0]);
                im[nn[1]].setImageResource(R.drawable.num00+nn[1]);
                im[nn[2]].setImageResource(R.drawable.num00+nn[2]);
                break;
            }//while문
            strike=0;
            ball=0;
        }
    };



}//class
