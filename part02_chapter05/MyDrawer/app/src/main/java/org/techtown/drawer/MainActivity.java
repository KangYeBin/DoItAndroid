package org.techtown.drawer;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback{

    private AppBarConfiguration mAppBarConfiguration;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Toast.makeText(this, "첫번째 메뉴 선택됨", Toast.LENGTH_SHORT).show();
            onFragmentSelected(0, null);
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "두번째 메뉴 선택됨", Toast.LENGTH_SHORT).show();
            onFragmentSelected(1, null);
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(this, "세번째 메뉴 선택됨", Toast.LENGTH_SHORT).show();
            onFragmentSelected(2, null);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentSelected(int position, Bundle bundle) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = fragment1;
            toolbar.setTitle("첫번째 화면");
        } else if (position == 1) {
            fragment = fragment2;
            toolbar.setTitle("두번째 화면");
        } else if (position == 2) {
            fragment = fragment3;
            toolbar.setTitle("세번째 화면");
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}