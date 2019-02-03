package shop.akapp.com.akappshop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.GONE;

public class Login extends AppCompatActivity implements  View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    Button btnLogin;
    ImageView ivGoogleSignIn,ivFb,ivInfi,ivIntro;
    EditText Number,Password;
    Dbhelper dbhelper;
    View view;
    ProgressBar progressBar;
    TextView tvLoad,tvRegister;
    View LinearLayout,splashScreen;
    private LoginButton ivFbLogin;
    private GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    private  final int Req_Code=9001;
    String picUrlString;

    private SharedPreferences sharedPreferences;
    private final String Preferences_Name="User_Info";
    private final String Id="User_id";
    private final String Name="User_name";
    private final String Email="User_Email";
    private final String Birthday="User_Birthday";
    private final String Friends="User_Friends";
    private final String Image="Image_Url";
    
    private String firstname,lastname,email,birthday,friends,id;
    private URL profilePicture;
    CountDownTimer countDownTimer;

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null)
        {
            startActivity(new Intent(Login.this,MainActivity.class));
            Login.this.finish();

        }
        else if(AccessToken.getCurrentAccessToken()!=null)
        {
            startActivity(new Intent(Login.this,MainActivity.class));
            Login.this.finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        btnLogin=findViewById(R.id.btnlogin);
        tvRegister=findViewById(R.id.tvRegister);
        ivInfi=findViewById(R.id.ivInfi);
        Number=findViewById(R.id.etUserNumber);
        Password=findViewById(R.id.etPassword);
        progressBar=findViewById(R.id.progressBar);
        tvLoad=findViewById(R.id.tvLoad);
        LinearLayout=findViewById(R.id.LinearLayout);
        splashScreen=findViewById(R.id.splashScreen);
        ivGoogleSignIn=findViewById(R.id.ivGoogleSignIn);
        ivFbLogin=findViewById(R.id.ivFbLogin);
        ivFb=findViewById(R.id.ivFb);
        ivIntro=findViewById(R.id.ivIntro);

        Glide.with(this).load(R.drawable.logo).into(ivInfi);
        Glide.with(this).load(R.drawable.logo).into(ivIntro);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        callbackManager=CallbackManager.Factory.create();
        sharedPreferences=getSharedPreferences(Preferences_Name,Context.MODE_PRIVATE);




        if(Build.VERSION.SDK_INT>=21)
        {
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                         splashScreen.setVisibility(GONE);
                                         LinearLayout.setVisibility(View.VISIBLE);
                                      }
                                  },2000);

        hidesoftkeyboard(Login.this);
        dbhelper = new Dbhelper(this.getApplicationContext());

        ivGoogleSignIn.setOnClickListener(this);
        ivFb.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,RegisterActivity.class));
                Login.this.finish();
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
        //ivInfi.animate().rotationYBy(30000).setDuration(20000);



      //  ivInfi.animate().setDuration(shortAnimTime).alpha(show? 1:0).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                ivInfi.setVisibility(show ? View.VISIBLE:GONE);
//            }
//        });

        tvLoad.setVisibility(show?View.VISIBLE:GONE);
        tvLoad.animate().setDuration(shortAnimTime).alpha(show? 1:0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                tvLoad.setVisibility(show ? View.VISIBLE:GONE);
            }
        });


    }
    else{
        LinearLayout.setVisibility(show? GONE :View.VISIBLE);
//        progressBar.setVisibility(show? View.VISIBLE: GONE);
        ivInfi.setVisibility(show? View.VISIBLE:GONE);
        tvLoad.setVisibility(show? View.VISIBLE: GONE);
    }
}

    public void usingcountdownTimer()
    {
        countDownTimer=new CountDownTimer(Long.MAX_VALUE,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                final Animation myAnim = AnimationUtils.loadAnimation(Login.this,R.anim.bounce);
                // Use bounce interpolator with amplitude 0.1 and frequency 15

                ivInfi.startAnimation(myAnim);
            }

            @Override
            public void onFinish() {
                start();

            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try{
            countDownTimer.cancel();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
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

    public void GoogleSignIn()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Req_Code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Req_Code)
        { Log.i("message","Printed");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);


            
        }
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            startActivity(new Intent(Login.this,MainActivity.class));
            Login.this.finish();
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Failed", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivGoogleSignIn:

                        GoogleSignIn();
                        break;

            //FB LOgin
            case R.id.ivFb:
                ivFbLogin.performClick();
                ivFbLogin.setReadPermissions(Arrays.asList("public_profile","email"));
                ivFbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        Bundle params = new Bundle();
                        params.putBoolean("redirect", false);

                        new GraphRequest(
                                AccessToken.getCurrentAccessToken(),
                                "me/picture",
                                params,
                                HttpMethod.GET,
                                new GraphRequest.Callback() {
                                    public void onCompleted(GraphResponse response) {
                                        try {
                                             picUrlString = (String) response.getJSONObject().getJSONObject("data").get("url");
                                            //Load picture url in imageView
                                                                                   } catch (JSONException  e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                        ).executeAsync();


                        String accessTokens=loginResult.getAccessToken().getToken();
                        GraphRequest request=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Log.d("response",response.toString());
                                try {
                                    firstname = object.getString("first_name");
                                    lastname=object.getString("last_name");

                                }catch (JSONException e)
                                {
                                    e.printStackTrace();
                                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                try {
                                    email = object.getString("email");


                                }catch (JSONException e){
                                    e.printStackTrace();
                                    //Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                try{
                                    birthday=object.getString("birthday");

                                }catch(JSONException e){
                                    e.printStackTrace();
                                   // Toast.makeText(Login.this, "Birthday Error", Toast.LENGTH_SHORT).show();
                                }
                               try {
                                   friends = object.getJSONObject("friends").getJSONObject("summary").getString("total_count");
                               }catch(JSONException e)
                               {
                                   e.printStackTrace();
                               }

                                callsharedpref();
                                startActivity(new Intent(Login.this,MainActivity.class));
                                Login.this.finish();
                            }
                        });
                        Bundle parameters=new Bundle();
                        parameters.putString("fields","id, first_name, last_name, email, birthday, gender, friends");
                        request.setParameters(parameters);
                        request.executeAsync();
                        Toast.makeText(Login.this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(Login.this, "Login Cancelled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(Login.this, "Error Occured", Toast.LENGTH_SHORT).show();
                    }

                });


                break;


           //Login through volley apijson
            case R.id.btnlogin:
                hidesoftkeyboard(Login.this);
                //startActivity(new Intent(Login.this,MainActivity.class));
                if(Number.getText().toString().equals("") || Password.getText().toString().equals(""))
                {
                    Toast.makeText(Login.this, "Please provide All Fields", Toast.LENGTH_SHORT).show();
                }
                else{

                    ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                    if(networkInfo!=null && networkInfo.isConnected()) {
                        Toast.makeText(Login.this, "Connected", Toast.LENGTH_SHORT).show();
                        ShowProgressBar(true);
                        tvLoad.setText("Processing Login Credentials..Please wait...");
                    }else{
                        Toast.makeText(Login.this, "Not connected", Toast.LENGTH_SHORT).show();
                    }

                    if (!Login.this.Number.getText().toString().equals("") && !Login.this.Password.getText().toString().equals("")) {
                        String url = "http://www.swasthikassociates.in/VerifyUser.php";
                        StringRequest stringRequest = new StringRequest(1, url, new Response.Listener<String>() {
                            public void onResponse(String response) {

                                ShowProgressBar(false);
                                AlertDialog var2;
                                if (response.equals("user is not verified")) {
                                    var2 = new AlertDialog.Builder(Login.this).setTitle("Error").setMessage("Your account has not been verified yet. You may contact admin authorities.").setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    }).show();
                                } else if (response.equals("not a valid user")) {
                                    var2 = new AlertDialog.Builder(Login.this).setTitle("Error").setMessage("You are not a vaild user.Either your phonenumber or password are incorrect.").setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    }).show();
                                } else if (response.equals("user is verified you may continue!")) {
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                    Login.this.dbhelper.fillform("check");
                                    Login.this.startActivity(i);
                                    Login.this.finish();
                                } else if (response.equals("user has already logged in from another device")) {
                                    var2 = (new AlertDialog.Builder(Login.this)).setTitle("Error").setMessage("Access Denied! This account have been registered from another device.").setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    }).show();
                                }

                                Log.e("response", response);
                            }
                        }, new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                Log.e("token", error.toString());
                                Toast.makeText(Login.this.getApplicationContext(), "No network connection", Toast.LENGTH_LONG).show();
//                            Toast.makeText(Login.this.getApplicationContext(), "fetching from local db", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap();
                                params.put("phonenumber", Login.this.Number.getText().toString());
                                params.put("password", Login.this.Password.getText().toString());
                                return params;
                            }
                        };
                        Volley.newRequestQueue(Login.this.getApplicationContext()).add(stringRequest);
                    } else {
                        Snackbar.make(view,"Please fill all fields ",1000).show();
                    }
                }
                break;

        }

    }



    public void callsharedpref()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Id,id);
        editor.putString(Name, firstname + " " + lastname);
        editor.putString(Image, picUrlString);
        editor.putString(Email, email);
        editor.putString(Birthday, birthday);
        editor.putString(Friends,friends);
        editor.commit();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();

    }
}
