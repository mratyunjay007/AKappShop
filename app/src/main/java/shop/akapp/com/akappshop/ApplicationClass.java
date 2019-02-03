package shop.akapp.com.akappshop;

import android.app.Application;

import java.util.ArrayList;

import shop.akapp.com.akappshop.R;

public class ApplicationClass extends Application {

    public static ArrayList<ItemDetails> Items;
    public static User user;


    @Override
    public void onCreate() {
        super.onCreate();


        Items=new ArrayList<ItemDetails>();

        Items.add(new ItemDetails("Latitute 3500 series","Rs:32999","Dell",
                "Its new dell series with Advanced Features and...",
                "starts at rs 1,370. No Cost EMI available",
                "Free Delievery","Are you in search of a wide-angle lens for your favourite " +
                "Nikon DSLR Camera? You have many choices to choose from. Tokina has come up with an exclusive collection " +
                "of lens for the photography enthusiasts. Tokina AT-X M 100mm F/2.8 Prime Lens is a new macro lens that " +
                "ensures stunning photography. The redesigning of the multi-coating of the lens has been done in such a " +
                "manner that it perfectly suits both the CMOS as well as CCD sensors of the DSLR camera. Interestingly, " +
                "the lens comes with a user-friendly focus limiter switch. The focus out of the closes focus can be locked " +
                "to ensure faster focus, while using it as a moderate telephoto lens. This helps to take stunning and perfect. " +
                "portraits.  ", R.drawable.laptop, R.drawable.best_price));

        Items.add(new ItemDetails("SanDisk Ultra Dual 32gb 3.0 OTG pan drive","Rs: 599","Sandisk",
                "Low cost smart and faster way of tranferring files...",
                "starts at rs 1,370. No Cost EMI available",
                "Free Delievery","Are you in search of a wide-angle lens for your favourite " +
                "Nikon DSLR Camera? You have many choices to choose from. Tokina has come up with an exclusive collection " +
                "of lens for the photography enthusiasts. Tokina AT-X M 100mm F/2.8 Prime Lens is a new macro lens that " +
                "ensures stunning photography. The redesigning of the multi-coating of the lens has been done in such a " +
                "manner that it perfectly suits both the CMOS as well as CCD sensors of the DSLR camera. Interestingly, " +
                "the lens comes with a user-friendly focus limiter switch. The focus out of the closes focus can be locked " +
                "to ensure faster focus, while using it as a moderate telephoto lens. This helps to take stunning and perfect. " +
                "portraits.  ",R.drawable.memory,R.drawable.best_price));

        Items.add(new ItemDetails("Redmi Note6","Rs: 11999","Xiomi",
                "12Mp + 5Mp AI Dual Camera get perfect picture... ",
                "starts at rs 1,370. No Cost EMI available",
                "Free Delievery","Are you in search of a wide-angle lens for your favourite " +
                "Nikon DSLR Camera? You have many choices to choose from. Tokina has come up with an exclusive collection " +
                "of lens for the photography enthusiasts. Tokina AT-X M 100mm F/2.8 Prime Lens is a new macro lens that " +
                "ensures stunning photography. The redesigning of the multi-coating of the lens has been done in such a " +
                "manner that it perfectly suits both the CMOS as well as CCD sensors of the DSLR camera. Interestingly, " +
                "the lens comes with a user-friendly focus limiter switch. The focus out of the closes focus can be locked " +
                "to ensure faster focus, while using it as a moderate telephoto lens. This helps to take stunning and perfect. " +
                "portraits.  ",R.drawable.mobile_phone,R.drawable.best_price));

        Items.add(new ItemDetails("LCD Screen full HD","Rs: 23999","LG",
                "Incredibly slim profile and stylish, contemporary...",
                "starts at rs 1,370. No Cost EMI available",
                "Free Delievery","Are you in search of a wide-angle lens for your favourite " +
                "Nikon DSLR Camera? You have many choices to choose from. Tokina has come up with an exclusive collection " +
                "of lens for the photography enthusiasts. Tokina AT-X M 100mm F/2.8 Prime Lens is a new macro lens that " +
                "ensures stunning photography. The redesigning of the multi-coating of the lens has been done in such a " +
                "manner that it perfectly suits both the CMOS as well as CCD sensors of the DSLR camera. Interestingly, " +
                "the lens comes with a user-friendly focus limiter switch. The focus out of the closes focus can be locked " +
                "to ensure faster focus, while using it as a moderate telephoto lens. This helps to take stunning and perfect. " +
                "portraits.  ",R.drawable.screen,R.drawable.best_price));

        Items.add(new ItemDetails("WD Elements 2TB Portable external Hard Drive(Black)","Rs: 5199",
                "Western Digital","Ultar -fast data transfers with high capacity WD quality...",
                "starts at rs 1,370. No Cost EMI available",
                "Free Delievery","Are you in search of a wide-angle lens for your favourite " +
                "Nikon DSLR Camera? You have many choices to choose from. Tokina has come up with an exclusive collection " +
                "of lens for the photography enthusiasts. Tokina AT-X M 100mm F/2.8 Prime Lens is a new macro lens that " +
                "ensures stunning photography. The redesigning of the multi-coating of the lens has been done in such a " +
                "manner that it perfectly suits both the CMOS as well as CCD sensors of the DSLR camera. Interestingly, " +
                "the lens comes with a user-friendly focus limiter switch. The focus out of the closes focus can be locked " +
                "to ensure faster focus, while using it as a moderate telephoto lens. This helps to take stunning and perfect. " +
                "portraits.  ", R.drawable.hdd,R.drawable.best_price));

        Items.add(new ItemDetails("Nikon D5300 24.2 Mp Digital SLR camera","Rs: 32995","Nikon",
                "Superb Image quality through cutting edge...",
                "No cost emi Available",
                "Free Delievery","The Instax Mini 9 is the new range of instant camera " +
                "launched by Fujifilm. This product has an analog type printing capability which enables users to obtain" +
                " on-the-spot high quality credit-card size photos instantly. The refreshed Mini 9 range is also equipped " +
                "with a selfie mirror and comes with a close-up lens in addition to features such as automatic exposure " +
                "measurement for aperture settings and high-key mode that enables users to take brighter photos - perfect " +
                "for portraits. The Instax camera is very easy and fun to use and enables users to capture memories in print, " +
                "gift them or preserve them forever. This stylish, compact camera is the perfect tool for parties, festivals, " +
                "events and days out, allowing users to capture more fun in retro prints with a simple motion. The Instax mini " +
                "9 comes in five trendy new colors, 'Flamingo pink', 'Lime green', 'Cobalt blue', 'Smoky white' and 'Ice blue'.\n",
                R.drawable.camera,R.drawable.best_price));

        Items.add(new ItemDetails("Canon EF50MM F /1.8 STM lens for canon DSLR cameras","Rs: 8237",
                "Canon","A wide aperture lens for high quality poratrait and low...",
                "starts at rs 1,370. No Cost EMI available",
                "Free Delievery","Are you in search of a wide-angle lens for your favourite " +
                "Nikon DSLR Camera? You have many choices to choose from. Tokina has come up with an exclusive collection " +
                "of lens for the photography enthusiasts. Tokina AT-X M 100mm F/2.8 Prime Lens is a new macro lens that " +
                "ensures stunning photography. The redesigning of the multi-coating of the lens has been done in such a " +
                "manner that it perfectly suits both the CMOS as well as CCD sensors of the DSLR camera. Interestingly, " +
                "the lens comes with a user-friendly focus limiter switch. The focus out of the closes focus can be locked " +
                "to ensure faster focus, while using it as a moderate telephoto lens. This helps to take stunning and perfect. " +
                "portraits.  ",
                R.drawable.camera_lens,R.drawable.best_price));



    }
}
