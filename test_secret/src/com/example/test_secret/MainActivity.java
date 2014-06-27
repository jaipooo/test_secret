package com.example.test_secret;

import com.example.test_secret.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

/**
 * 非同期通信でPOSTリクエストをする
 *
 */
public class MainActivity extends Activity implements OnClickListener {

  private Button btn = null;
  
  String [] inputnum1;
 
	int [] severID = null; //サーバーID
	String [] severIDstr = null; //サーバーID
	int [] dnum = null;	//分散情報(int)
	String [] dstr = null;	//分散情報(str)
	
	int scrtnum;
	int k=1;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btn = (Button)findViewById(R.id.button1);
    
    btn.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    // ボタン押下時
    if( v == btn )
    {
    	EditText editText1=(EditText)findViewById(R.id.editText1);
    	scrtnum = Integer.parseInt(editText1.getText().toString());
    	
    	Random rnd = new Random();
    	
        severID = new int[10];
        severIDstr = new String[10];
        dnum = new int[10];
        dstr = new String[10];
                
    	severIDstr[1]=String.valueOf(severID[1]);
    	dstr[1]=String.valueOf(dnum[1]);
    	
        severID[1]=1+rnd.nextInt(100);
        severID[2]=1+rnd.nextInt(100);
        severID[3]=1+rnd.nextInt(100);
        severID[4]=5;
        severID[5]=5;
        severID[6]=5;
        
        for(int i=1; i<4; i++){
        	dnum[i] = knfunc(severID[i], scrtnum);
        	dstr[i] = String.valueOf(dnum[i]);
        	severIDstr[i] = String.valueOf(severID[i]);
        }
    	
    	
        //n=3なので3回
      exec_post();
      exec_post();
      exec_post();
      Toast.makeText(getApplication(), "分散完了", Toast.LENGTH_LONG).show();
    }
  }
  
  private static int knfunc(int x,int scrt){
  	int dstr;
  	int a1 = 7;
  	
  	dstr = scrt + a1*x ;
  	return dstr;
  }

  // POST通信を実行（AsyncTaskによる非同期処理を使うバージョン）
  private void exec_post() {

    // 非同期タスクを定義
    HttpPostTask task = new HttpPostTask(
      this,
      "http://192.168.11.2/test1.php",

      // タスク完了時に呼ばれるUIのハンドラ
      new HttpPostHandler(){

        @Override
        public void onPostCompleted(String response) {
          // 受信結果をUIに表示
          //tv.setText( response );
        }

        @Override
        public void onPostFailed(String response) {
          //tv.setText( response );
          Toast.makeText(
            getApplicationContext(), 
            "エラーが発生しました。", 
            Toast.LENGTH_LONG
          ).show();
        }
      }
    );

    task.addPostParam( "post_1", severIDstr[k] );
    task.addPostParam( "post_2", dstr[k] );
    
    //n=2k=2なのでこの設定わあわ
    k += 1;
    if(k==4)k=1;
	   
    // タスクを開始
    task.execute();

  }
}
