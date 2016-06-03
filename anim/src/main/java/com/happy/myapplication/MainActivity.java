package com.happy.myapplication;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIcon;
    private Button mRoate, mScale, mTranslate, mAlpha, mGroup, mTranslateXml, mGroupXml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIcon = (ImageView) findViewById(R.id.icon);
        mRoate = (Button) findViewById(R.id.roate);
        mScale = (Button) findViewById(R.id.scale);
        mTranslate = (Button) findViewById(R.id.translate);
        mAlpha = (Button) findViewById(R.id.alpha);
        mGroup = (Button) findViewById(R.id.group);
        mTranslateXml = (Button) findViewById(R.id.translate_xml);
        mGroupXml = (Button) findViewById(R.id.group_xml);

        mRoate.setOnClickListener(this);
        mScale.setOnClickListener(this);
        mTranslate.setOnClickListener(this);
        mAlpha.setOnClickListener(this);
        mGroup.setOnClickListener(this);
        mTranslateXml.setOnClickListener(this);
        mGroupXml.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.roate:
                playRoate();
                break;
            case R.id.alpha:
                playAlpha();
                break;
            case R.id.translate:
                playTranslation();
                break;
            case R.id.scale:
                playScale();
                break;
            case R.id.group:
                playGroup();
                break;
            case R.id.translate_xml:
                playTranslationXml();
                break;
            case R.id.group_xml:
                playGroupXml();
                break;
        }
    }

    private void playGroupXml() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.group);
        animator.setTarget(mIcon);
        animator.start();
    }

    private void playTranslationXml() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.translation);
        animator.setTarget(mIcon);
        animator.start();
    }

    private void playGroup() {
        float translationY = mIcon.getTranslationY();
        ObjectAnimator translation = ObjectAnimator.ofFloat(mIcon, "translationY", translationY, 300f, translationY);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mIcon, "alpha", 1f, 0.3f, 1f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mIcon, "rotation", 0, 360);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mIcon, "scaleY", 1f, 2f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translation).after(alpha).before(rotation).with(scaleY);
        animatorSet.setDuration(5000);
        animatorSet.start();
    }

    private void playScale() {
        ObjectAnimator scale = ObjectAnimator.ofFloat(mIcon, "scaleY", 1f, 3f, 1f);
        scale.setDuration(3000);
        scale.start();
    }

    private void playTranslation() {
        float translationX = mIcon.getTranslationX();
        ObjectAnimator translation = ObjectAnimator.ofFloat(mIcon, "translationX", translationX, 500f, translationX);
        translation.setDuration(5000);
        translation.start();
    }

    private void playAlpha() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mIcon, "alpha", 1f, 0f, 1f);
        alpha.setDuration(3000);
        alpha.start();
    }

    private void playRoate() {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mIcon, "rotation", 0, 270);
        rotation.setDuration(5000);
        rotation.start();
    }
}
