//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package shop.akapp.com.akappshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class Dbhelper extends SQLiteOpenHelper {
    public static String dbname = "credentials12";

    public Dbhelper(Context context) {
        super(context, dbname, (CursorFactory)null, 1);
    }

    public Dbhelper(Context context, String name) {
        super(context, name, (CursorFactory)null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  credentials(batchname TEXT,date TEXT)");
        db.execSQL("CREATE TABLE  register(FcmToken TEXT)");
        db.execSQL("CREATE TABLE  formfill(chk TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.onCreate(db);
    }

    public Boolean credentialEntry(String batchname, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("batchname", String.valueOf(batchname));
        contentValues.put("date", String.valueOf(date));
        long ret = db.insert("credentials", (String)null, contentValues);
        return ret == -1L ? false : true;
    }

    public Cursor readcredentials() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  credentials", (String[])null);
        return res;
    }

    public Boolean URLEntry(String page) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("url", String.valueOf(page));
        long ret = db.insert("imageurl", (String)null, contentValues);
        return ret == -1L ? false : true;
    }

    public Cursor readURL() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  imageurl", (String[])null);
        return res;
    }

    public Boolean register(String fcm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FcmToken", String.valueOf(fcm));
        long ret = db.insert("register", (String)null, contentValues);
        return ret == -1L ? false : true;
    }

    public Boolean fillform(String check) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("chk", String.valueOf(check));
        long ret = db.insert("formfill", (String)null, contentValues);
        return ret == -1L ? false : true;
    }

    public Cursor readform() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  formfill", (String[])null);
        return res;
    }

    public Cursor readregister() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  register", (String[])null);
        return res;
    }

    public Cursor readPI() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  personalinfo", (String[])null);
        return res;
    }

    public Cursor readSkills() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  specialization", (String[])null);
        return res;
    }

    public Cursor readDetailsFromSkills(String Skill) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  specialization where skill ='" + Skill + "'", (String[])null);
        return res;
    }

    public Boolean PIentry(String name, String email, String phone, String admNo, String univRoll, String section, String imageurl, String overallscore, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", String.valueOf(name));
        contentValues.put("email", String.valueOf(email));
        contentValues.put("phone", String.valueOf(phone));
        contentValues.put("univRoll", String.valueOf(admNo));
        contentValues.put("admNo", String.valueOf(univRoll));
        contentValues.put("section", String.valueOf(section));
        contentValues.put("overallscore", String.valueOf(overallscore));
        contentValues.put("description", String.valueOf(description));
        contentValues.put("imageurl", String.valueOf(imageurl));
        long ret = db.insert("personalinfo", (String)null, contentValues);
        return ret == -1L ? false : true;
    }

    public Boolean AudioLecture(int classnumber, String url, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("classnumber", classnumber);
        contentValues.put("url", String.valueOf(url));
        contentValues.put("date", String.valueOf(date));
        long ret = db.insert("audiolecture", (String)null, contentValues);
        return ret == -1L ? false : true;
    }

    public Cursor readLectures() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  audiolecture", (String[])null);
        return res;
    }

    public Cursor getUrlFromClass(int classnumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  audiolecture where classnumber=" + classnumber, (String[])null);
        return res;
    }

    public void deleteLectures() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from  audiolecture");
    }

    public Boolean topstoriesinsert(String title, String content, String url, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", String.valueOf(title));
        contentValues.put("content", String.valueOf(content));
        contentValues.put("url", String.valueOf(url));
        contentValues.put("date", String.valueOf(date));
        contentValues.put("time", String.valueOf(time));
        long ret = db.insert("topstories", (String)null, contentValues);
        return ret == -1L ? false : true;
    }

    public Boolean HighLightsinsert(String title, String content, String url, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", String.valueOf(title));
        contentValues.put("content", String.valueOf(content));
        contentValues.put("url", String.valueOf(url));
        contentValues.put("date", String.valueOf(date));
        contentValues.put("time", String.valueOf(time));
        long ret = db.insert("highlights", (String)null, contentValues);
        return ret == -1L ? false : true;
    }

    public Boolean topstoriesinsertBook(String title, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", String.valueOf(title));
        contentValues.put("url", String.valueOf(url));
        long ret = db.insert("topstoriesbook", (String)null, contentValues);
        return ret == -1L ? false : true;
    }

    public Boolean HighLightsinsertBook(String title, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", String.valueOf(title));
        contentValues.put("url", String.valueOf(url));
        long ret = db.insert("highlightsbook", (String)null, contentValues);
        return ret == -1L ? false : true;
    }

    public void delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from  " + dbname);
        db.execSQL("update sqlite_sequence set seq =0 where name ='recommended_table'");
    }

    public void topstoriesdelete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from  topstories");
        db.execSQL("update sqlite_sequence set seq =0 where name ='topstories'");
    }

    public void HighLightsdelete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from  highlights");
        db.execSQL("update sqlite_sequence set seq =0 where name ='highlights'");
    }

    public Cursor readtempo() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  " + dbname, (String[])null);
        return res;
    }

    public Cursor readtopstories() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  topstories", (String[])null);
        return res;
    }

    public Cursor readtopstoriesbook() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  topstoriesbook", (String[])null);
        return res;
    }

    public Cursor readHighlightsbook() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  highlightsbook", (String[])null);
        return res;
    }

    public Cursor readHigh() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  highlights", (String[])null);
        return res;
    }

    public Cursor readtopstoriesFromTitle(String Title) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  topstories where title ='" + Title + "'", (String[])null);
        return res;
    }

    public Cursor readtopstoriesBookFromTitle(String Title) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  topstoriesbook where title ='" + Title + "'", (String[])null);
        return res;
    }

    public Cursor readHighlightsFromTitle(String Title) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  highlights where title ='" + Title + "'", (String[])null);
        return res;
    }

    public Cursor readHighlightsBookFromTitle(String Title) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  highlightsbook where title ='" + Title + "'", (String[])null);
        return res;
    }
}
