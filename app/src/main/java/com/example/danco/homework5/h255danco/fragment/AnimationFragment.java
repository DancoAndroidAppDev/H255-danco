package com.example.danco.homework5.h255danco.fragment;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;

import com.example.danco.homework5.h255danco.R;
import com.example.danco.homework5.h255danco.view.CustomComponent;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnimationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnimationFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = AnimationFragment.class.getSimpleName();

    private CustomComponent theRing;
    private Interpolator interpolator;


    public static AnimationFragment newInstance() {
        AnimationFragment fragment = new AnimationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public AnimationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animation, container, false);

        Button viewPropertyButton =
                (Button) view.findViewById(R.id.viewPropertyAnimationButton);
        Button objectPropertyButton =
                (Button) view.findViewById(R.id.objectPropertyAnimationButton);

        viewPropertyButton.setOnClickListener(this);
        objectPropertyButton.setOnClickListener(this);

        theRing = (CustomComponent) view.findViewById(R.id.ring);

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        interpolator = AnimationUtils.loadInterpolator(
                activity, android.R.interpolator.accelerate_decelerate);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewPropertyAnimationButton:
                animation1();
                break;
            case R.id.objectPropertyAnimationButton:
                animation2();
                break;
        }
    }


    private void animation1() {
        Log.i(TAG, "Animation 1");
        RightLateral endAction = new RightLateral();
        ViewCompat.animate(theRing)
                .translationXBy(-25)
                .translationYBy(-100f)
                .setDuration(1000).setInterpolator(interpolator)
                .withEndAction(endAction)
                .withLayer()
                .start();
    }


    private void animation2() {
        Log.i(TAG, "Animation 2");
        Animator animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.ring_progress);
        animator.setTarget(theRing);
        animator.start();
    }

    class RightLateral implements Runnable {

        @Override
        public void run() {
            ViewCompat.animate(theRing)
                    .translationYBy(25f)
                    .translationYBy(-100f)
                    .setDuration(1000).setInterpolator(interpolator)
                    .withEndAction(new DropAndRotate())
                    .withLayer()
                    .start();
        }
    }


    class DropAndRotate implements Runnable {
        @Override
        public void run() {
            ViewCompat.animate(theRing)
                    .translationYBy(200f)
                    .rotation(360)
                    .setDuration(1000).setInterpolator(interpolator)
                    .withEndAction(new Squish())
                    .withLayer()
                    .start();
        }
    }


    class Squish implements Runnable {
        @Override
        public void run() {
            ViewCompat.animate(theRing)
                    .scaleYBy(-.75f)
                    .scaleXBy(1.25f)
                    .setDuration(100)
                    .setInterpolator(interpolator)
                    .withLayer()
                    .withEndAction(new Restore())
                    .start();
        }
    }

    class Restore implements Runnable {

        public void run() {
            ViewCompat.animate(theRing)
                    .scaleY(1.0f)
                    .scaleX(1.0f)
                    .translationX(0f)
                    .translationY(0f)
                    .setDuration(500)
                    .setInterpolator(interpolator)
                    .withLayer()
                    .start();
            ViewCompat.setRotation(theRing, 0f);
        }
    }
}
