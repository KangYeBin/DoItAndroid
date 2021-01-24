package org.techtown.doitmission10;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("첫번째 화면");

        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fragment1 = new Fragment1();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        //바로가기 메뉴
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_fragment1:
                        onFragmentSelected(0, null);
                        break;
                    case R.id.nav_fragment2:
                        onFragmentSelected(1, null);
                        break;
                    case R.id.nav_fragment3:
                        onFragmentSelected(2, null);
                        break;
                }
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        //하단 탭
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        showToast("첫번째 탭 선택됨");
                        fragment1 = new Fragment1();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
                        toolbar.setTitle("첫번째 화면");
                        return true;
                    case R.id.tab2:
                        showToast("두번째 탭 선택됨");
                        fragment2 = new Fragment2();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
                        toolbar.setTitle("두번째 화면");
                        return true;
                    case R.id.tab3:
                        showToast("세번째 탭 선택됨");
                        fragment3 = new Fragment3();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment3).commit();
                        toolbar.setTitle("세번째 화면");
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }

    public void onFragmentSelected(int position, Bundle bundle) {
        Fragment curFragment = null;
        if (position == 0) {
            curFragment = new Fragment1();
            toolbar.setTitle("첫번째 화면");
        } else if (position == 1) {
            curFragment = new Fragment2();
            toolbar.setTitle("두번째 화면");
        } else if (position == 2) {
            curFragment = new Fragment3();
            toolbar.setTitle("세번째 화면");
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).commit();
    }
}