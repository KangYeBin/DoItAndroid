package org.techtown.pager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 pager;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3); // 3개까지 추가

        FragmentStateAdapter adapter = new MyPagerAdapter(this, 3);
//        fragment1 = new Fragment1();
//        adapter.addItem(fragment1);
//        fragment2 = new Fragment2();
//        adapter.addItem(fragment2);
//        fragment3 = new Fragment3();
//        adapter.addItem(fragment3);
        pager.setAdapter(adapter);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);        //세번째 프래그먼트
            }
        });
    }

    class MyPagerAdapter extends FragmentStateAdapter {     //어댑터를 통해 프래그먼트 관리
        ArrayList<Fragment> items = new ArrayList<Fragment>();

        //ViewPager를 사용하는 경우
//        public MyPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        public void addItem (Fragment item) {   //새로운 프래그먼트 추가
//            items.add(item);
//        }
//
//        @NonNull
//        @Override
//        public Fragment getItem(int position) {
//            return items.get(position);
//        }
//
//        @Override
//        public int getCount() { //adapter에 들어있는 프래그먼트의 개수
//            return items.size();
//        }

        //ViewPager2를 사용하는 경우
        public int count;
        public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity, int count) {
            super(fragmentActivity);
            this.count = count;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            int index = position % count;       // 0, 1, 2
            if (index == 0) return new Fragment1();
            else if (index == 1) return new Fragment2();
            else if (index == 2) return new Fragment3();

            return null;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}