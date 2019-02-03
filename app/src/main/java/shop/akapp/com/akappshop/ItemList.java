package shop.akapp.com.akappshop;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemList extends AppCompatActivity implements ItemAdapter.ItemClicked{

    private FragmentManager fragmentManager;
    private Fragment listItem,itemDetails;
    private TextView tvCompany,tvItemName,tvPrice,tvEmi,tvDelievery,tvCompleteDesc;
    private ImageView ivStamp,ivItem;

    Toolbar toolbar;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if(Build.VERSION.SDK_INT>=21)
        {
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        toolbar=findViewById(R.id.toolbar);
        tvCompany=findViewById(R.id.tvCompany);
        tvItemName=findViewById(R.id.tvItemName);
        tvPrice=findViewById(R.id.tvPrice);
        tvDelievery=findViewById(R.id.tvDelievery);
        tvEmi=findViewById(R.id.tvEmi);
        tvCompleteDesc=findViewById(R.id.tvCompleteDecs);
        ivStamp=findViewById(R.id.ivStamp);
        ivItem=findViewById(R.id.ivItem);

        setSupportActionBar(toolbar);
         actionBar=this.getSupportActionBar();
        actionBar.setTitle("Related Items");
        actionBar.setDisplayHomeAsUpEnabled(true);

        fragmentManager=this.getSupportFragmentManager();
        listItem=fragmentManager.findFragmentById(R.id.itemlistfragment);
        itemDetails=fragmentManager.findFragmentById(R.id.itemDetailFragment);

        fragmentManager.beginTransaction()
                .show(listItem)
                .hide(itemDetails)
                .commit();

    }

    @Override
    public void onItemclick(int index) {

        fragmentManager.beginTransaction()
                .show(itemDetails)
                .hide(listItem)
                .addToBackStack(null)
                .commit();

        tvCompany.setText(ApplicationClass.Items.get(index).getCompanyName());
        tvItemName.setText(ApplicationClass.Items.get(index).getItemName());
        ivStamp.setImageResource(ApplicationClass.Items.get(index).getStamp());
        ivItem.setImageResource(ApplicationClass.Items.get(index).getItemImage());
        tvPrice.setText(ApplicationClass.Items.get(index).getPrice());
        tvEmi.setText(ApplicationClass.Items.get(index).getEmi());
        tvDelievery.setText(ApplicationClass.Items.get(index).getDelievery());
        tvCompleteDesc.setText(ApplicationClass.Items.get(index).getCompleteDescription());



    }
}
