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
 * �񓯊��ʐM��POST���N�G�X�g������
 *
 */
public class MainActivity extends Activity implements OnClickListener {

  private Button btn = null;
  
  String [] inputnum1;
 
	int [] severID = null; //�T�[�o�[ID
	String [] severIDstr = null; //�T�[�o�[ID
	int [] dnum = null;	//���U���(int)
	String [] dstr = null;	//���U���(str)
	
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
    // �{�^��������
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
    	
    	
        //n=3�Ȃ̂�3��
      exec_post();
      exec_post();
      exec_post();
      Toast.makeText(getApplication(), "���U����", Toast.LENGTH_LONG).show();
    }
  }
  
  private static int knfunc(int x,int scrt){
  	int dstr;
  	int a1 = 7;
  	
  	dstr = scrt + a1*x ;
  	return dstr;
  }

  // POST�ʐM�����s�iAsyncTask�ɂ��񓯊��������g���o�[�W�����j
  private void exec_post() {

    // �񓯊��^�X�N���`
    HttpPostTask task = new HttpPostTask(
      this,
      "http://192.168.11.2/test1.php",

      // �^�X�N�������ɌĂ΂��UI�̃n���h��
      new HttpPostHandler(){

        @Override
        public void onPostCompleted(String response) {
          // ��M���ʂ�UI�ɕ\��
          //tv.setText( response );
        }

        @Override
        public void onPostFailed(String response) {
          //tv.setText( response );
          Toast.makeText(
            getApplicationContext(), 
            "�G���[���������܂����B", 
            Toast.LENGTH_LONG
          ).show();
        }
      }
    );

    task.addPostParam( "post_1", severIDstr[k] );
    task.addPostParam( "post_2", dstr[k] );
    
    //n=2k=2�Ȃ̂ł��̐ݒ�킠��
    k += 1;
    if(k==4)k=1;
	   
    // �^�X�N���J�n
    task.execute();

  }
}
