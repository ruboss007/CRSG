package com.example.mission;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity implements OnClickListener{
	
	private Context context;
	private static final int START_ANIM = 1;
	private static final int STOP_ANIM = 0;
	private static final int SPLASHTIME = 3000;
	private Animation anim;
	private TextView tv;
	private boolean go = true;
	private boolean goA1 = true;
	private Button sh;
	private Button md;
	private Button ln;
	private Button rd;
	private Button st;
	private Handler h;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		h = new Handler();
		tv = (TextView) this.findViewById(R.id.textView2);
		
		
		sh = (Button) this.findViewById(R.id.button1);
		md = (Button) this.findViewById(R.id.button2);
		ln = (Button) this.findViewById(R.id.button3);
		st = (Button) this.findViewById(R.id.button4);
		rd = (Button) this.findViewById(R.id.buttonRedy);
		
		sh.setOnClickListener(this);
		md.setOnClickListener(this);
		ln.setOnClickListener(this);
		st.setOnClickListener(this);
		rd.setOnClickListener(this);
		
		Message msg = new Message();
		msg.what = START_ANIM;
		splashHandler.sendMessageDelayed(msg, START_ANIM);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(context,ShortActivity.class);
		
		switch(v.getId()){
			case R.id.button1:
				sh.setBackgroundColor(Color.parseColor("#FFFFFF"));
				sh.setTextColor(Color.parseColor("#1D1D1D"));
				intent.putExtra("style", ShortActivity.SHORT);
				startActivity(intent);
				break;
			case R.id.button2:
				md.setBackgroundColor(Color.parseColor("#FFFFFF"));
				md.setTextColor(Color.parseColor("#1D1D1D"));
				intent.putExtra("style", ShortActivity.MEDIUM);
				startActivity(intent);
				break;
			case R.id.button3:
				ln.setBackgroundColor(Color.parseColor("#FFFFFF"));
				ln.setTextColor(Color.parseColor("#1D1D1D"));
				intent.putExtra("style", ShortActivity.LONG);
				startActivity(intent);
				break;
			case R.id.buttonRedy:
				ln.setBackgroundColor(Color.parseColor("#FFFFFF"));
				ln.setTextColor(Color.parseColor("#1D1D1D"));
				intent.putExtra("style", ShortActivity.READY);
				startActivity(intent);
				break;
			case R.id.button4:
				st.setBackgroundColor(Color.parseColor("#FFFFFF"));
				st.setTextColor(Color.parseColor("#1D1D1D"));
				Intent intent1 = new Intent(context,SettingsActivity.class);
				startActivity(intent1);
				break;
		}
		
	}

	
	private Handler splashHandler = new Handler() { //создаем новый хэндлер
		public void handleMessage(Message msg) {
		       switch (msg.what) {
		       case START_ANIM:
		    	   Thread t = new Thread(new Runnable() {
		    		      public void run() {
		    		       while(go){
		    		    	   synchronized(updateProgress){
			    		    	   try {
									TimeUnit.SECONDS.sleep(3);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			    		    	   
		    		    		   h.post(updateProgress);
		    		    	   }
		    		       }
		    		      }
		    		    });
		    		    t.start();

		    	   //anim = AnimationUtils.loadAnimation(context, R.anim.myalpha);
		   		  // tv.startAnimation(anim);
		   		   break;
		       }
		 
		       super.handleMessage(msg);
		   }
		};
		
		Runnable updateProgress = new Runnable() {
		    public void run() {
		    	if(goA1){
			    	anim = AnimationUtils.loadAnimation(context, R.anim.myalpha2);
			    	tv.startAnimation(anim);
			    	goA1 = false;
		    	}
		    	else{
		    		anim = AnimationUtils.loadAnimation(context, R.anim.myalpha);
			    	tv.startAnimation(anim);
			    	goA1 = true;
		    	}
		    }
		  };
		
		
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			go = true;
			sh.setBackgroundColor(Color.parseColor("#292929"));
			sh.setTextColor(Color.parseColor("#939393"));
			md.setBackgroundColor(Color.parseColor("#292929"));
			md.setTextColor(Color.parseColor("#939393"));
			ln.setBackgroundColor(Color.parseColor("#292929"));
			ln.setTextColor(Color.parseColor("#939393"));
			st.setBackgroundColor(Color.parseColor("#292929"));
			st.setTextColor(Color.parseColor("#939393"));
			Message msg = new Message();
			msg.what = START_ANIM;
			splashHandler.sendMessageDelayed(msg, START_ANIM);
		}
		@Override
		protected void onStop() {
			go = false;
			super.onStop();
			
		}
		
}
