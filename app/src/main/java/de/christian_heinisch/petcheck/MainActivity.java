package de.christian_heinisch.petcheck;

import android.content.Intent;
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

import java.io.InputStream;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static InputStream is;

    // Erstelle einen Zähler für GoBack mit dem Wert 0
    public int gobackcound = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





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
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_allgemeines) {


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

            gobackcound = gobackcound + 1;

            titelleiste(getResources().getString(R.string.checklist_header));

            RabbitCheckFragment rabbitCheckFragment = new RabbitCheckFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.content_main,
                    rabbitCheckFragment,
                    rabbitCheckFragment.getTag()
            )
                    .addToBackStack(null)
                    .commit();

        }else if(id == R.id.action_about){
/*
            gobackcound = gobackcound + 1;
            titelleiste("Über diese APP");

            AboutFragment aboutFragment = new AboutFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.content_main,
                    aboutFragment,
                    aboutFragment.getTag()
            )
                    .addToBackStack(null)
                    .commit();

            */about();
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

}
