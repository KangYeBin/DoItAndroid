package org.techtown.fragment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ImageSelectionCallback{
    ListFragment listFragment;
    ViewFragment viewFragment;

    int [] images = {R.drawable.dream01, R.drawable.dream02, R.drawable.dream03};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        listFragment = (ListFragment) manager.findFragmentById(R.id.listFragment);
        viewFragment = (ViewFragment) manager.findFragmentById(R.id.viewFragment);

    }

    @Override
    public void onImageSelected(int position) {
        viewFragment.setImage(images[position]);
    }
}