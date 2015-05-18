package cn.libery.objectanimator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private TextView tv, tv2, tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
                ObjectAnimator oa = ObjectAnimator.ofFloat(tv, "rotation", 0f, 360f);
                oa.setDuration(1000);
                oa.start();
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tv, "alpha", 0.3f, 1.0f, 0.5f, 1.0f);
                objectAnimator.setDuration(5000);
                objectAnimator.start();
                float currentTranslateX = tv.getTranslationX();
                float currentTranslateY = tv.getTranslationY();
                ObjectAnimator animator = objectAnimator.ofFloat(tv, "translationX", currentTranslateX, -500f, currentTranslateX);
                animator.setDuration(3000);
                animator.start();
                ObjectAnimator animatorTranslateY = objectAnimator.ofFloat(tv, "translationY", currentTranslateY, -500f, currentTranslateY);
                animatorTranslateY.setDuration(3000);
                animatorTranslateY.start();
                break;
            case R.id.tv2:
                ObjectAnimator rotation = ObjectAnimator.ofFloat(tv2, "rotation", 0f, 360f);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(tv2, "alpha", 0.3f, 1.0f, 0.5f, 1.0f);
                float currentTranslationX = tv2.getTranslationX();
                float currentTranslationY = tv2.getTranslationY();
                float scaleSizeX = tv2.getScaleX();
                float scaleSizeY = tv2.getScaleY();
                ObjectAnimator translationX = ObjectAnimator.ofFloat(tv2, "translationX", currentTranslationY, 300f, currentTranslationX);
                ObjectAnimator translationY = ObjectAnimator.ofFloat(tv2, "translationY", currentTranslationY, 300f, currentTranslationY);
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(tv2, "scaleX", scaleSizeX, 2f, scaleSizeX);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(tv2, "scaleY", scaleSizeY, 2f, scaleSizeY);
                AnimatorSet animationSet = new AnimatorSet();
//                animationSet.play(rotation).with(alpha).after(translationY);
                animationSet.playTogether(rotation, alpha, scaleX, scaleY, translationX, translationY);
                animationSet.setDuration(5000);
                animationSet.start();
                break;
            case R.id.tv3:
                Animator animators = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.animation);
                animators.setTarget(tv3);
                animators.start();
                animators.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tv3.setTextColor(Color.BLUE);
                        tv3.setClickable(false);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                break;
        }
    }
}
