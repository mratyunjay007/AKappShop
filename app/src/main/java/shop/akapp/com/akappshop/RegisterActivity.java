package shop.akapp.com.akappshop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnTouchListener;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import static android.view.View.GONE;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etName,etPhone,etEmail,etPassword,etCnfPswd;
    private Button btnSubmit;
    private  Dbhelper dbhelper;
    private CardView cv1,cv2,cv3,cv4,cv5;
    ProgressBar progressBar;
    TextView tvLoad,tvWelcome;
    View LinearLayout,InfoLayout;
    CountDownTimer countDownTimer;
    ImageView ivInfi;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName=findViewById(R.id.etName);
        etPhone=findViewById(R.id.etPhone);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etCnfPswd=findViewById(R.id.etCnfPswd);
        btnSubmit=findViewById(R.id.btnSubmit);
        ivInfi=findViewById(R.id.ivInfi);
        progressBar=findViewById(R.id.progressBar);
        tvWelcome=findViewById(R.id.tvWelcome);
        tvLoad=findViewById(R.id.tvLoad);
        LinearLayout=findViewById(R.id.LinearLayout);
        InfoLayout=findViewById(R.id.Infolayout);
        cv1=findViewById(R.id.cv1);
        cv2=findViewById(R.id.cv2);
        cv3=findViewById(R.id.cv3);
        cv4=findViewById(R.id.cv4);
        cv5=findViewById(R.id.cv5);

        Glide.with(this).load(R.drawable.logo).into(ivInfi);
//        RelativeLayout login = (RelativeLayout)this.findViewById(2131230838);
//        login.setOnTouchListener(new OnTouchListener() {
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Intent i = new Intent(RegisterActivity.this.getApplicationContext(), LoginActivity.class);
//                RegisterActivity.this.startActivity(i);
//                RegisterActivity.this.finish();
//                return false;
//            }
//        });

        if(Build.VERSION.SDK_INT>=21)
        {
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
//        etName.setOnClickListener(this);
//        etPhone.setOnClickListener(this);
//        etEmail.setOnClickListener(this);
//        etPassword.setOnClickListener(this);
//        etCnfPswd.setOnClickListener(this);

        dbhelper = new Dbhelper(this.getApplicationContext());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hidesoftkeyboard(RegisterActivity.this);

                if (!etName.getText().toString().equals("") && !etPhone.getText().toString().equals("")
                        &&!etEmail.getText().toString().equals("") && !etPassword.getText().toString().equals("") &&
                        !etCnfPswd.getText().toString().equals("")) {

                    if(etPassword.getText().toString().equals(etCnfPswd.getText().toString())) {
                        Date date = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
                        SimpleDateFormat format1 = new SimpleDateFormat("hh:mm:ss");

                        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                        if(networkInfo!=null && networkInfo.isConnected()) {
                           // Toast.makeText(Login.this, "Connected", Toast.LENGTH_SHORT).show();
                            ShowProgressBar(true);
                            tvLoad.setText("Processing Your Details..Please wait...");
                        }else{
                            Toast.makeText(RegisterActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
                        }


                        String urlT = "http://www.swasthikassociates.in/RegisterStudent.php?name=" + etName.getText().toString().trim() + "&phonenumber=" +
                                etPhone.getText().toString().trim() + "&email=" + etEmail.getText().toString().trim() +
                                "&password=" + etPassword.getText().toString().trim() +
                                "&date=" + format.format(date) + "&time=" + format1.format(date);

                        String url = urlT.replace(" ", "%20");

                        StringRequest stringRequest = new StringRequest(0, url, new Response.Listener<String>() {
                            public void onResponse(String response) {

                                ShowProgressBar(false);
                                Log.e("success", response);
                                AlertDialog var2;
                                if (response.contains("Records inserted successfully.")) {

                                    dbhelper.fillform("registered");
                                    Log.e("success", response);
                                    if(Build.VERSION.SDK_INT>=21){
                                        InfoLayout.setAlpha(0.4f);
                                        tvWelcome.setAlpha(1);
                                        new Timer().schedule(new TimerTask() {
                                            @Override
                                            public void run() {

                                                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                                RegisterActivity.this.finish();
                                            }
                                        },3000);

                                    }else{
                                    var2 = new AlertDialog.Builder(RegisterActivity.this)
                                            .setTitle("Information").setMessage("Thank you for registering yourself with Infi.")
                                            .setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent it = new Intent(RegisterActivity.this, MainActivity.class);
                                            RegisterActivity.this.startActivity(it);
                                            dialogInterface.cancel();
                                            RegisterActivity.this.finish();
                                        }
                                    }).show();
                                }
                                } else if (response.contains("Duplicate entry")) {
                                    var2 = new AlertDialog.Builder(RegisterActivity.this).setTitle("Error")
                                            .setMessage("Sorry, this mobile number has already been registered." +
                                                    " Please provide your actual details")
                                            .setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    }).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                Log.e("token", error.toString());
                                Toast.makeText(RegisterActivity.this.getApplicationContext(), "No network connection", 0).show();
                            }
                        }) {
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap();
                                return params;
                            }
                        };
                        Volley.newRequestQueue(RegisterActivity.this.getApplicationContext()).add(stringRequest);
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Make sure Two passwords are same ", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(view, "Please fill all fields..", 1000).show();
                }

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)

    void ShowProgressBar(final boolean show)
    {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            int shortAnimTime=getResources().getInteger(android.R.integer.config_shortAnimTime);

            LinearLayout.setVisibility(show? GONE:View.VISIBLE);
            LinearLayout.animate().setDuration(shortAnimTime).alpha(show? 0:1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    LinearLayout.setVisibility(show ? GONE:View.VISIBLE);
                }
            });
            ivInfi.setVisibility(show? View.VISIBLE:GONE);
           // ivInfi.animate().rotationYBy(30000).setDuration(20000);
            if(show)
            {
                usingcountdownTimer();
            }else{
                try{
                    countDownTimer.cancel();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
//            progressBar.setVisibility(show? View.VISIBLE:GONE);
//            progressBar.animate().setDuration(shortAnimTime).alpha(show? 1:0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    progressBar.setVisibility(show ? View.VISIBLE:GONE);
//                }
//            });

            tvLoad.setVisibility(show?View.VISIBLE:GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(show? 1:0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE:GONE);
                }
            });


        }
        else{

            ivInfi.setVisibility(show? View.VISIBLE:GONE);
            LinearLayout.setVisibility(show? GONE :View.VISIBLE);
           // progressBar.setVisibility(show? View.VISIBLE: GONE);
            tvLoad.setVisibility(show? View.VISIBLE: GONE);
        }
    }

    public void usingcountdownTimer()
    {
        countDownTimer=new CountDownTimer(Long.MAX_VALUE,1100) {
            @Override
            public void onTick(long millisUntilFinished) {
                final Animation myAnim = AnimationUtils.loadAnimation(RegisterActivity.this,R.anim.bounce);
                // Use bounce interpolator with amplitude 0.1 and frequency 15

                ivInfi.startAnimation(myAnim);
            }

            @Override
            public void onFinish() {
                start();

            }
        }.start();
    }

    public void hidesoftkeyboard(Activity activity)
    {
        InputMethodManager imm=(InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view=activity.getCurrentFocus();
        if(view==null)
        {
            view =new View(activity);

        }
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);

    }


    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.etName: etName.animate().scaleX(1).scaleY(1).setDuration(300);
                break;
            case R.id.etEmail: etEmail.animate().scaleX(1).scaleY(1).setDuration(300);
                break;
            case R.id.etPassword: etPassword.animate().scaleX(1).scaleY(1).setDuration(300);
                break;
            case R.id.etCnfPswd: etCnfPswd.animate().scaleX(1).scaleY(1).setDuration(300);
                break;
            case R.id.etPhone: etPhone.animate().scaleX(1).scaleY(1).setDuration(300);
                break;
        }
    }

}
