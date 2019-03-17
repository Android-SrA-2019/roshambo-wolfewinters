/**
 * Author: Keith Faulkner
 * Date: 2019-03-15
 */
package com.wolfewinters.roshambo;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
/**
 * Main class
 */
public class MainActivity extends AppCompatActivity {
    //private variable declarations
    private AnimatorSet animations;
    private Rochambo rochambo;
    /**
     * Creates or loads state
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ObjectAnimator animatorPlayer = ObjectAnimator.ofFloat(findViewById(R.id.user_move_image), "rotationX", 0f, 360f).setDuration(500);
        ObjectAnimator animatorGame = ObjectAnimator.ofFloat(findViewById(R.id.cpu_move_image),"rotationY", 0f, 360f).setDuration(500);
        animations = new AnimatorSet();
        animations.playTogether(animatorGame, animatorPlayer);
        animations.setInterpolator(new AnticipateOvershootInterpolator());
        if(savedInstanceState != null){
            rochambo = (Rochambo)savedInstanceState.getSerializable("rochambo");
            updateView();
        }else {
            rochambo = new Rochambo();
        }
    }
    /**
     * Used to have the state of the instance
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("rochambo", rochambo);
    }
    /**
     * Updates text and image  views based on move chosen
     */
    public void updateView(){
        ImageView playerMove = findViewById(R.id.user_move_image);
        ImageView gameMove = findViewById(R.id.cpu_move_image);
        playerMove.setImageResource(getRoshamboImage(rochambo.getPlayerMove()));
        gameMove.setImageResource(getRoshamboImage(rochambo.getGameMove()));
        TextView result = findViewById(R.id.result_text);
        result.setText(rochambo.winLoseOrDraw());
    }
    /**
     * Click event for player to select their move
     * @param view
     */
    public void playRoshambo(View view) {
        int id = view.getId();
        int move;
        if (id == R.id.rock_button){move = Rochambo.ROCK;}
        else if (id == R.id.paper_button){move = Rochambo.PAPER;}
        else if (id == R.id.scissor_button){move = Rochambo.SCISSOR;}
        else{move = Rochambo.NONE;}
        rochambo.playerMakesMove(move);
        updateView();
        animations.end();
        animations.start();
    }
    /**
     * Switches on move to load the correct image
     * @param move
     * @return
     */
    public int getRoshamboImage(int move){
        int image;
        if (move == Rochambo.PAPER){image = R.mipmap.paper;}
        else if (move == Rochambo.SCISSOR){image = R.mipmap.scissors;}
        else if (move == Rochambo.ROCK){image = R.mipmap.rock;}
        else {image = R.mipmap.none;}
        return image;
    }
}