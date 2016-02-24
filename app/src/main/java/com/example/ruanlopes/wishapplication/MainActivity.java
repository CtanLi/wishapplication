package com.example.ruanlopes.wishapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ruanlopes.wishapplication.ToolBar.EnhancedMenuInflater;
import com.example.ruanlopes.wishapplication.ToolBar.SplitToolbar;
import com.facebook.login.LoginManager;

public class MainActivity extends AppCompatActivity {

    private SplitToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        // Custom Code For Toolbar
        //
        toolbar = (SplitToolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            EnhancedMenuInflater.inflate(getMenuInflater(), toolbar.getMenu(), true);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return onOptionsItemSelected(item);
                }
            });
        }

        // get an instance of FragmentTransaction from your Activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //add a fragment
        TabFragment fragment = new TabFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (toolbar == null) {
            EnhancedMenuInflater.inflate(getMenuInflater(), menu, false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout_menu_item) {
            LoginManager.getInstance().logOut();
            Snackbar.make(this.findViewById(R.id.logout_menu_item), "YES", Snackbar.LENGTH_SHORT)
                    .show();
            startActivity(new Intent(this, LoginActivity.class));
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("com.package.ACTION_LOGOUT");
            sendBroadcast(broadcastIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
