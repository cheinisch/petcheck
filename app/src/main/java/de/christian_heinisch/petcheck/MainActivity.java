package de.christian_heinisch.petcheck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import de.christian_heinisch.petcheck.data.JSONParser;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static InputStream is;

    // Erstelle einen Zähler für GoBack mit dem Wert 0
    public int gobackcound = 0;

    // Zeit für den Zeitraum, nachdem das Autoupdate gemacht werden muss
    public int time_between = 90;

    // String für die Dateiprüfung. Da der Fisch das neuste Tier ist, wird die geprüft
    String FILE_NAME = "fish.json";

    NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(filecheck(FILE_NAME) == false)
        {
            Update();
        }

        // Automatisches Update alle 90 Tage
        CheckAutoUpdate();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        getPetName();


        start();



    }


    @Override
    public void onBackPressed() {

        /* Zähler wird geprüft, wenn größer 0, dann wird zurückgegangen und
        * den Zähler um 1 reduziert. Sobald Zähler bei 0 angekommen ist, wird
        * die App beendet
        * */

        if (gobackcound > 0){
            gobackcound = gobackcound - 1;
            super.onBackPressed();
        }else{
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.



        int id = item.getItemId();

        if (id == R.id.nav_auswahl) {

            Intent intent = new Intent(this, SelectActivity.class);
            startActivity(intent);

        }

        if (id == R.id.nav_allgemeines) {

            System.out.println("allgemeines");
            // GoBackcound wird für jeden Klick auf ein Menüeintrag hochgesetzt
            gobackcound = gobackcound + 1;

            titelleiste(getResources().getString(R.string.allgemeines_head));

            AllgemeinesFragment allgemeinesFragment = new AllgemeinesFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.content_main,
                    allgemeinesFragment,
                    allgemeinesFragment.getTag()
            )
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_uebersicht) {

            gobackcound = gobackcound + 1;

            titelleiste(getResources().getString(R.string.uebersicht_head));

            OverviewFragment overviewFragment = new OverviewFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.content_main,
                    overviewFragment,
                    overviewFragment.getTag()
            )
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_checkliste) {

            // Zähler zum zurückgehen wird um 1 erhöht. Wird für die Funktion onBackPressed() benötigt
            gobackcound = gobackcound + 1;

            // Setzt den Text der Titelleiste auf Checkliste
            titelleiste(getResources().getString(R.string.checklist_header));

            // Läd für jedes Tier das passende Checklistenfragment
            if(getPetNameString().equals("Kaninchen")){
                rabbitCheck();
            }else if(getPetNameString().equals("Aquarienfische")){
                fishCheck();
            }else if(getPetNameString().equals("Hund")){
                //Do nothing
            }


        }else if(id == R.id.action_settings){
            // Läd das Einstellungenfragment
            settings();

        }else if(id == R.id.action_about){

            // Läd das Über die App Fragment
            about();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void allgemeines(){

        titelleiste(getResources().getString(R.string.allgemeines_head));

        AllgemeinesFragment allgemeinesFragment = new AllgemeinesFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.content_main,
                allgemeinesFragment,
                allgemeinesFragment.getTag()
        )
                .addToBackStack(null)
                .commit();
    }

    public void overview() {

        titelleiste("Übersicht");

        OverviewFragment overviewFragment = new OverviewFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.content_main,
                overviewFragment,
                overviewFragment.getTag()
        )
                .addToBackStack(null)
                .commit();
    }

    public void rabbitCheck(){
        RabbitCheckFragment rabbitCheckFragment = new RabbitCheckFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.content_main,
                rabbitCheckFragment,
                rabbitCheckFragment.getTag()
        )
                .addToBackStack(null)
                .commit();
    }

    public void fishCheck(){
        FishCheckFragment fishCheckFragment = new FishCheckFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.content_main,
                fishCheckFragment,
                fishCheckFragment.getTag()
        )
                .addToBackStack(null)
                .commit();
    }

    public void settings() {

        titelleiste("Einstellungen");

        SettingFragment settingFragment = new SettingFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.content_main,
                settingFragment,
                settingFragment.getTag()
        )
                .addToBackStack(null)
                .commit();
    }

    public void getVersion(){
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionString = pInfo.versionName;
        TextView version = (TextView) this.findViewById(R.id.textVersion);
        version.setText("Version " + versionString);
    }

    public void titelleiste(String title){

        setTitle(title);

    }




    public void start(){

        try {
            Intent i = getIntent();
            String check_intent = i.getStringExtra("goback");
            String check_intent_string = "from_detail";

            // Werte aus Detail_Activity auslesen. Wenn Die Mainactivity von der DetailActivity ausgelesen wird, ist der Returnstring "from_detail" und die OverviewFragment wird geladen

            if(check_intent.equals(check_intent_string)){
                overview();
            }else{
                allgemeines();
            }
        } catch( Exception e){
            allgemeines();
        }

    }

    public void about(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void getPetName(){

        SharedPreferences settings = this.getSharedPreferences("Petload", 0);

        String Tiername = settings.getString("titel", "Kaninchen").toString();

        JSONParser jParser = new JSONParser();

        Menu header=navigationView.getMenu();
        MenuItem test = header.findItem(R.id.nav_auswahl);
        test.setTitle(Tiername);
    }

    public String getPetNameString(){
        SharedPreferences settings = this.getSharedPreferences("Petload", 0);

        String Tiername = settings.getString("titel", "Kaninchen").toString();

        return Tiername;
    }

    public String getpetdata(){

        String json;

        SharedPreferences settings = this.getSharedPreferences("Petload", 0);

        json = settings.getString("json", "rabbit").toString();

        return json;

    }


    public boolean filecheck(String FILE_NAME){
        // Gibt der Variable file den Pfad un den Namen der zu prüfenden Datei
        File file = this.getFileStreamPath(FILE_NAME);

        // Gibt ein True oder False zurück
        return file.exists();
    }

    public void CheckAutoUpdate(){

        long timestamp = System.currentTimeMillis();
        long old_timestamp;

        SharedPreferences settings = this.getSharedPreferences("UpdateInfo", 0);
        old_timestamp = settings.getLong("LastUpdate", timestamp);

        if(TimeUnit.MILLISECONDS.toDays(timestamp) > TimeUnit.MILLISECONDS.toDays(old_timestamp)+time_between){
            Update();
        }

    }


    private void Update() {
        System.out.println("file Not Exist");
        startActivity(new Intent(this, UpdateActivity.class));
    }

}
