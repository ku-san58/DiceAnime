package com.example.diceanime;

import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //JAVAの定数
    private static final int DICE_COUNT = 6;
    //javaの変数
    private int diceNo;
    //javaのクラス
    //ランダムな数値を生成するクラス
    private Random random;
    //androのUI部品
    //テキストビュー部品：画面に文字を表示する
    private TextView textDicePip;
    //イメージビュー部品：画面に画像を表示する
    private ImageView imageView;

    //メディアプレイヤー部品：音楽再生する
    private MediaPlayer mediaPlayer;

    private static final int DICE_STOP=0;

    //AnimationDrowable:アニメーションを表示する部品
    private AnimationDrawable diceAnimetion;

    //レイアウト部品：画面にUI部品をレイアウト（配置）する
    private ConstraintLayout layout;

    //効果音の選択値
    private int setMusicNo;

    //出目範囲最大：１～６
    private int setDiceCount;

    /*
    サイコロの静止画像表示設定
    引数で受け取ったサイコロの目に対する画像をイメージビュー部品にセットする
     */
    private void setImage(int diceNo){
        switch(diceNo){
            case 1:
                imageView.setImageResource(R.drawable.d1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.d2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.d3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.d4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.d5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.d6);
                break;
        }
    }
    /*
    サイコロを振った時の効果音設定
     */
    private void setMusic(){
        if (setMusicNo == R.id.menu_sound1) {

            mediaPlayer = mediaPlayer.create(this, R.raw.dice3);
        }else {
            mediaPlayer = mediaPlayer.create(this, R.raw.dice2);
        }
    }

    /*
        サイコロを振った時の処理
     */
    private void diceRoll() {
        //アプリ開始時、またはサイコロ停止状態で画面たっぷ時
            if (diceNo == DICE_STOP) {
                //アニメーションのセット
                setAnimetionImage();
                //アニメーションの開始
                diceAnimetion.start();




        //さいころの出目を作成
        //nextInt(6)の場合[0-5]までの6つの数値がランダムに生成される
        //生成された数値に＋１することで[1-6]までの数値に変更している
        diceNo = random.nextInt(setDiceCount) + 1;

        textDicePip.setText(R.string.message_stop_touch);

        //音楽の再生
        //音楽ファイルのっ再生位置を０秒目に設定
        mediaPlayer.seekTo(0);
        //音楽ファイルの再生開始
        mediaPlayer.start();

    }else {
            //サイコロ回転状態から画面たっぷ時
            //アニメーションの停止
            diceAnimetion.stop();
            //サイコロ画面のセット
            setImage(diceNo);
            //サイコロの目を停止（０）に変更
            diceNo = DICE_STOP;
            //音楽の停止
            mediaPlayer.pause();
            //画面に、再度画面をタッチすればしあ頃が転がるというメッセージを表示
            textDicePip.setText(R.string.message_start_touch);
        }

    }

    /*
    サイコロのアニメーション表示設定
     */
    private void setAnimetionImage(){
        imageView.setImageResource(R.drawable.dice_anime);
        diceAnimetion = (AnimationDrawable) imageView.getDrawable();
    }

    /*
    画面をタッチした時に動作する処理
     */
    public boolean onTouchEvent(MotionEvent event){
        //指を離すとき（ACTION_UP）だけ判定させる
        if(event.getAction() == MotionEvent.ACTION_UP){
            //ダイスロール実行
            diceRoll();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_black:
                layout.setBackgroundColor(Color.BLACK);
                textDicePip.setTextColor(Color.WHITE);
                break;
            case R.id.menu_white:
                layout.setBackgroundColor(Color.WHITE);
                textDicePip.setTextColor(Color.BLACK);
                break;
            case R.id.menu_sound1:
                setMusicNo = R.id.menu_sound1;
                setMusic();
                break;
            case R.id.menu_sound2:
                setMusicNo = R.id.menu_sound2;
                setMusic();
                break;

            case R.id.menu_diceno_1:
                setDiceCount=1;
                break;
            case R.id.menu_diceno_2:
                setDiceCount=2;
                break;
            case R.id.menu_diceno_3:
                setDiceCount=3;
                break;
            case R.id.menu_diceno_4:
                setDiceCount=4;
                break;
            case R.id.menu_diceno_5:
                setDiceCount=5;
                break;
            case R.id.menu_diceno_6:
                setDiceCount=6;
                break;


        }
        return  super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ランダム部品生成
        random = new Random();


        //テキストビュー部品の生成
        //レイアウトXML上で作成したテキストビュー部品のtIDを指定する
        textDicePip = (TextView)findViewById(R.id.textView);



        //テキストビューに表示する文字をセット
        textDicePip.setText(R.string.message_start_touch);

        //レイアウトXML上で作成したイメージビュー部品のIDを指定する
        imageView = (ImageView)findViewById(R.id.imageView);

        //効果音の初期設定
        setMusicNo = R.id.menu_sound1;

        //音楽再生部品を生成するメソッドを呼びだす
        setMusic();

        diceNo = DICE_STOP;

        //レイアウト部品（メニュー）生成（背景色変更用）
        layout =  (ConstraintLayout) findViewById(R.id.mainLayout);

        //サイコロの面の数の初期値（６面）
        setDiceCount= DICE_COUNT;


    }
}
