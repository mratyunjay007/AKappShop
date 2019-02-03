package shop.akapp.com.akappshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener,ProducAdapter.ItemClicked {


    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    View header;
    Toolbar toolbar;
    EditText etVoiceSearch;
    ActionMenuItemView speak;

    private static final int REQUEST_CODE = 1234;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TextView tvSearch;

//    ListView listView;
    ArrayList<String> items;
    ArrayList<Product> products;

    CardView cvSpinner;
    private TextView userName,userEmail;
    ProfilePictureView fbprof_Pic;
    private ImageView prof_Pic,ivSelector,ivCancel,ivSettings,ivShare,ivInviteFriend,ivReveiew,ivBug,ivSelView;
    ImageView ivProfile,ivMenu;
    private float transX;
    private float transY;
    float dX,dY,newX,newY;
    private boolean flag=false;
    private int side=1;
    private GoogleSignInClient mGoogleSignInClient;

   SearchView searchView;

    private SharedPreferences sharedPreferences;
    private final String Preferences_Name="User_Info";
    private final String Id="User_id";
    private final String Name="User_name";
    private final String Email="User_Email";
    private final String Birthday="User_Birthday";
    private final String Friends="User_Friends";
    private final String Image="Image_Url";

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null)
        {
            if(account.getDisplayName()!=null)
            {
                userName.setText(account.getDisplayName());
            }
            if(account.getEmail()!=null)
            {
                userEmail.setText(account.getEmail());
            }
            if(account.getPhotoUrl()!=null)
            {

               prof_Pic.setVisibility(View.VISIBLE);
               fbprof_Pic.setVisibility(View.GONE);

                Glide.with(this).load(account.getPhotoUrl().toString()).into(prof_Pic);
            }
        }
        if(AccessToken.getCurrentAccessToken() !=null)
        {
            userName.setText(sharedPreferences.getString(Name,null));
            if(sharedPreferences.getString(Email,null)!=null){
                userEmail.setText(sharedPreferences.getString(Email,null));
            }
            else{
                userEmail.setText("");
            }
            if(sharedPreferences.getString(Image,null)!=null)
            {
                prof_Pic.setVisibility(View.GONE);
                fbprof_Pic.setVisibility(View.VISIBLE);
                fbprof_Pic.setProfileId(sharedPreferences.getString(Id,null));
                fbprof_Pic.setPresetSize(ProfilePictureView.SMALL);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

////        //linking variable to layout elements
//          listView=findViewById(R.id.listView);
        recyclerView=findViewById(R.id.recyclerView);
        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigationview);
        header=navigationView.getHeaderView(0);
        toolbar=findViewById(R.id.toolbar);
        cvSpinner=findViewById(R.id.cvSpinner);
        ivMenu=findViewById(R.id.ivMenu);
        ivProfile=findViewById(R.id.ivProfile);
        etVoiceSearch=findViewById(R.id.voiceSearch);
        speak=findViewById(R.id.speak);
        searchView=findViewById(R.id.searchView);
        tvSearch=findViewById(R.id.tvSearch);

        fbprof_Pic=(ProfilePictureView)header.findViewById(R.id.fbprof_Pic);
        prof_Pic=header.findViewById(R.id.prof_Pic);
        userName=header.findViewById(R.id.userName);
        userEmail=header.findViewById(R.id.userEmail);
        ivSelector = findViewById(R.id.ivSelector);

        ivSettings=findViewById(R.id.ivSettings);
        ivInviteFriend=findViewById(R.id.ivInviteFriend);
        ivShare=findViewById(R.id.ivShare);
        ivReveiew=findViewById(R.id.ivReview);
        ivBug=findViewById(R.id.ivBug);


        Glide.with(this).load(R.drawable.logo).into(ivSelector);
        sharedPreferences=getSharedPreferences(Preferences_Name, Context.MODE_PRIVATE);
//        listView.setVisibility(View.GONE);



        tvSearch.setVisibility(View.VISIBLE);
        //to move image button according to touch gestures
        ivSelector.setOnTouchListener(onTouchListener());

        setSupportActionBar(toolbar);               //seting up toolbar
        //setting up content in toolbar
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("      ");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        //initializing items arraylist
        readData();
//        items=new ArrayList<String>();
////        for(int i=0;i<products.size();i++)
//        {
//            items.add(products.get(i).getName());
//        }
//        //ading elements to array
        products.get(0).setImage(R.drawable.screen_usb_touch);
        products.get(1).setImage(R.drawable.smart_robot);
        products.get(2).setImage(R.drawable.firmware);
        products.get(3).setImage(R.drawable.receiver);
        products.get(4).setImage(R.drawable.radio_system);
        products.get(5).setImage(R.drawable.transmitter);
        products.get(6).setImage(R.drawable.ibus);
        products.get(7).setImage(R.drawable.transmitter_receiver);
        products.get(8).setImage(R.drawable.spring_antena);
        products.get(9).setImage(R.drawable.led);
        products.get(10).setImage(R.drawable.nano_controller);
       // products.get(11).setImage(R.drawable.arduino);
        products.get(11).setImage(R.drawable.deans_t_plug);
        products.get(12).setImage(R.drawable.digispark_development);
        products.get(13).setImage(R.drawable.fordable_quad_copter);
        products.get(14).setImage(R.drawable.joystick);
      //  products.get(16).setImage(R.drawable.mini_broadband_based);
      //  products.get(17).setImage(R.drawable.rc_helicopter);
        products.get(15).setImage(R.drawable.micro_usb);
        products.get(16).setImage(R.drawable.spring_antena);

        recyclerView.setHasFixedSize(true);
        layoutManager=new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ProducAdapter(MainActivity.this,products);
        recyclerView.setAdapter(adapter);



        //creating array adapter to set content in list view
//        ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,items);
//        listView.setAdapter(adapter);

        //setting list item on click listener
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String name=listView.getItemAtPosition(position).toString();
//                Toast.makeText(MainActivity.this, "name", Toast.LENGTH_SHORT).show();
//            }
//        });


        //setting navigation menu listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                switch(menuItem.getItemId()) {
                    case R.id.logout:
                        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                        if(account!=null){
                        signOut();
                        }
                        else if(AccessToken.getCurrentAccessToken()!=null){
                            LoginManager.getInstance().logOut();
                            clearSharedInfo();
                            startActivity(new Intent(MainActivity.this,Login.class));
                            MainActivity.this.finish();
                        }
                        break;
                }
                return false;
            }

        });

        if(Build.VERSION.SDK_INT>=21)       //checking built in verwsion to set status bar color
        {
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        cvSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSelector.setScaleX(1.7f);
                ivSelector.setScaleY(1.7f);

                if(side==0)
                {
                    cvSpinner.animate().translationXBy(-90).scaleX(0).scaleY(0).alpha(0).setDuration(200);
                    cvSpinner.animate().translationYBy(0).setDuration(200);




                }
                else if(side==1)
                {
                    cvSpinner.animate().translationXBy(90).scaleX(0).scaleY(0).alpha(0).setDuration(200);
                    cvSpinner.animate().translationYBy(0).setDuration(200);


                }
                cvSpinner.setVisibility(View.GONE);
                ivSelector.setVisibility(View.VISIBLE);
                ivSelector.animate().scaleX(1).scaleY(1).alpha(1).setDuration(200);

            }
        });


        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0)
        {
            speak.setEnabled(false);
            speak.setText("Recognizer not present");
        }
        etVoiceSearch.addTextChangedListener(new TextWatcher()
        {@
                Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

            // TODO Auto-generated method stub
        }@
                Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            // TODO Auto-generated method stub
        }@
                Override
        public void afterTextChanged(Editable s)
        {
            // TODO Auto-generated method stub
            etVoiceSearch.setVisibility(View.GONE);
            tvSearch.setVisibility(View.VISIBLE);
            startActivity(new Intent(MainActivity.this,ItemList.class));

        }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {    //searching based on text query

            @Override
            public boolean onQueryTextSubmit(String s) {
                tvSearch.setVisibility(View.VISIBLE);
//                ArrayList<String> finalresult=new ArrayList<>();
//                ArrayList<Product> productquery=new ArrayList<Product>();
//
//                for(int i=0;i<items.size();i++)
//                {
//                    if(items.get(i).toLowerCase().contains(s.toLowerCase()))
//                    {
//                        productquery.add(products.get(i));
//                    }
//                }
//
//                adapter=new ProductAdapter(MainActivity.this,productquery);
//                recyclerView.setAdapter(adapter);
                // listView.setVisibility(View.GONE);

                startActivity(new Intent(MainActivity.this,ItemList.class));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                tvSearch.setVisibility(View.GONE);
//                ArrayList<String> templist=new ArrayList<>();
//
//                for(String temp: items)
//                {
//                        if (temp.toLowerCase().contains(s.toLowerCase())) {
//                            templist.add(temp);
//
//                    }
//                }
//                listView.setVisibility(View.VISIBLE);
//                ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,templist);
//                listView.setAdapter(adapter);

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);        //linking menu file

       // MenuItem menuItem=menu.findItem(R.id.item_search);      //find item in menu file
       // SearchView searchView=(SearchView) menuItem.getActionView();        //declaring search view variable

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {    //searching based on text query
//
//            @Override
//            public boolean onQueryTextSubmit(String s) {
////                ArrayList<String> finalresult=new ArrayList<>();
////                ArrayList<Product> productquery=new ArrayList<Product>();
////
////                for(int i=0;i<items.size();i++)
////                {
////                    if(items.get(i).toLowerCase().contains(s.toLowerCase()))
////                    {
////                        productquery.add(products.get(i));
////                    }
////                }
////
////                adapter=new ProductAdapter(MainActivity.this,productquery);
////                recyclerView.setAdapter(adapter);
//               // listView.setVisibility(View.GONE);
//
//                startActivity(new Intent(MainActivity.this,ItemList.class));
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
////                ArrayList<String> templist=new ArrayList<>();
////
////                for(String temp: items)
////                {
////                        if (temp.toLowerCase().contains(s.toLowerCase())) {
////                            templist.add(temp);
////
////                    }
////                }
////                listView.setVisibility(View.VISIBLE);
////                ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,templist);
////                listView.setAdapter(adapter);
//
//                return true;
//            }
//        });

        return super.onCreateOptionsMenu(menu);
    }

    //if any element in tool bar is clicked we can get which item was clicked through this function
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.speak: etVoiceSearch.setVisibility(View.VISIBLE);
                        speak=findViewById(R.id.speak);
               startVoiceRecognitionActivity();
                break;

        }
        return true;
    }

    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice searching...");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            final ArrayList < String > matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (!matches.isEmpty())
            {
                String Query = matches.get(0);
                etVoiceSearch.setText(Query);
                speak.setEnabled(false);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void readData() {
        products = new ArrayList<Product>();
        try {
            String[] line;
            CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open("productnew.csv")));
            reader.readNext();
            int i=0;
            while((line=reader.readNext())!=null && i<17)
            {       i++;
                Product sample = new Product();
                if(line[0]!="")
                {
                    sample.setId(Integer.parseInt(line[0]));
                    sample.setName(line[3]);
                    sample.setDescription(line[8]);
                    sample.setTag(line[28]);
                    products.add(sample);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                        prof_Pic.setImageResource(R.mipmap.userimage);
                        userName.setText("username");
                        userEmail.setText("email");
                        startActivity(new Intent(MainActivity.this,Login.class));
                        MainActivity.this.finish();

                    }
                });
    }

    private void clearSharedInfo(){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

        //setting up motion event AND ONTOUCH LISTENER IN view
public void spinner(View v){




            cvSpinner.setX(transX);
            cvSpinner.setY(transY-250);
            cvSpinner.setScaleX(0f);
            cvSpinner.setScaleY(0f);
            ivSelector.animate().scaleX(1.7f).scaleY(1.7f).alpha(0).setDuration(100);

            if(side==0)
            {

                cvSpinner.animate().translationXBy(130).scaleX(1).scaleY(1).alpha(1).setDuration(200);
                cvSpinner.animate().translationYBy(0).setDuration(200);
                cvSpinner.setX(transX+130);

            }
            else if(side==1)
            {

                cvSpinner.animate().translationXBy(-790).scaleX(1).scaleY(1).alpha(1).setDuration(200);
                cvSpinner.animate().translationYBy(0).setDuration(200);
                cvSpinner.setX(transX-790);

            }
    ivSelector.setVisibility(View.GONE);

    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {
            int lastAction;

            DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
            int screenHight = displaymetrics.heightPixels;
            int screenWidth = displaymetrics.widthPixels;

            @SuppressLint("NewApi") @Override
            public boolean onTouch(View view, MotionEvent event) {


                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:

                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        transX=view.getX();
                        transY=view.getY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;

                    case MotionEvent.ACTION_MOVE:

                            newX = event.getRawX() + dX;
                            newY = event.getRawY() + dY;

                            // check if the view out of screen
                            if ((newX <= 0 || newX >= screenWidth - view.getWidth()) || (newY <= 0 || newY >= screenHight - view.getHeight())) {
                                lastAction = MotionEvent.ACTION_MOVE;
                                break;
                            }

                            view.setX(newX);
                            view.setY(newY);

                            if (newX >= 0 && newX <= (screenWidth / 2)) {
                                view.animate().translationX(-screenWidth + 200).setDuration(100);
                                view.setY(newY);
                                transX = 0;
                                transY = newY;
                                side = 0;
                            } else if (newX >= (screenWidth / 2) && newX <= screenWidth - view.getWidth()) {
                                //  view.setX(screenWidth - view.getWidth());
                                view.animate().translationX(0).setDuration(100);
                                view.setY(newY);
                                transX = screenWidth - view.getWidth();
                                transY = newY;
                                side = 1;
                            }
                            if(newY>=0 && newY<=200)
                            {
                                view.animate().translationY(-screenHight+700).setDuration(100);
                                view.setX(newX);
                            }
                            else if(newY>=screenHight-300 && newY<=screenHight-view.getHeight())
                            {
                                view.animate().translationY(0).setDuration(100);
                                view.setX(newX);
                            }


                        lastAction = MotionEvent.ACTION_MOVE;

                        break;

                    case MotionEvent.ACTION_UP:
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            cvSpinner.setVisibility(View.VISIBLE);
                            spinner(view);

                        }
                        break;

                    default:
                        return false;
                }
                return true;
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.ivProfile:    startActivity(new Intent(MainActivity.this,Personal_Details.class));
                                        break;
            case R.id.ivMenu:         drawerLayout.openDrawer(GravityCompat.START);
                                             break;
        }
    }

    @Override
    public void OnItemClicked(int index) {
        startActivity(new Intent(MainActivity.this,ItemList.class));
    }
}

