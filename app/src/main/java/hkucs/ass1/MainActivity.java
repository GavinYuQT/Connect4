package hkucs.ass1;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    Button startgame;
    ImageView image;
    ImageButton chessArray[][] = new ImageButton[6][7];
    boolean startchess = true;
    boolean win = false;
    int color = 1;
    int step = 1;
    int row = -1;
    int column = -1;
    int actualrow = -1;
    int actualcolumn = -1;
    MediaPlayer mediaPlayer;
    MediaPlayer shitPlayer;
    TextView showWin;
    Button retract;
    TextView showColor;
    int chess[][] = {
            {0,0,0,0,0,0,0}, {0,0,0,0,0,0,0}, {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0}, {0,0,0,0,0,0,0}, {0,0,0,0,0,0,0}
    };
    int record[][] = {
            {0,0,0,0,0,0,0}, {0,0,0,0,0,0,0}, {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0}, {0,0,0,0,0,0,0}, {0,0,0,0,0,0,0}
    };
    int imageid[][] = {
            {R.id.IB1,R.id.IB2,R.id.IB3,R.id.IB4,R.id.IB5,R.id.IB6,R.id.IB7},
            {R.id.IB8,R.id.IB9,R.id.IB10,R.id.IB11,R.id.IB12,R.id.IB13,R.id.IB14},
            {R.id.IB15,R.id.IB16,R.id.IB17,R.id.IB18,R.id.IB19,R.id.IB20,R.id.IB21},
            {R.id.IB22,R.id.IB23,R.id.IB24,R.id.IB25,R.id.IB26,R.id.IB27,R.id.IB28},
            {R.id.IB29,R.id.IB30,R.id.IB31,R.id.IB32,R.id.IB33,R.id.IB34,R.id.IB35},
            {R.id.IB36,R.id.IB37,R.id.IB38,R.id.IB39,R.id.IB40,R.id.IB41,R.id.IB42}
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.liu);
        startgame = (Button) findViewById(R.id.startgame);
        startgame.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                initial();
            }
        });

        for (int i=0;i<6;i++)
            for(int j=0;j<7;j++){
                chessArray[i][j] = (ImageButton)findViewById(imageid[i][j]);
                chessArray[i][j].setOnClickListener(handler);
            }

        mediaPlayer = MediaPlayer.create(this,R.raw.rap);
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();

        retract = (Button) findViewById(R.id.retract);
        retract.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (step==1)
                    return;
                back();
            }
        });

        showWin = (TextView) findViewById(R.id.showWin);
        showColor = (TextView) findViewById(R.id.showColor);
        showColor.setText("RED");
        showColor.setTextColor(Color.RED);
        shitPlayer = MediaPlayer.create(this,R.raw.shit);
    }

    @Override
    public void finish() {
        super.finish();
        mediaPlayer.stop();
    }

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!startchess)
                return;

            for (int i=0;i<6;i++)
                for(int j=0;j<7;j++)
                    if (view.getId() == imageid[i][j]){
                        row = i;
                        column = j;
                    }
            for (int i=5;i>=0;i--) {
                if (chess[i][column] == 0) {
                    actualrow =  i;
                    actualcolumn = column;
                    putChess(actualrow,actualcolumn);
                    break;
                }
            }
            battlecheck();
            nextTurn();
            updateData();
        }
    };

    public void nextTurn(){
        if(!win) {
            if (color == 1 && actualrow != 0) {
                color = 2;
                image.setImageResource(R.drawable.liang);
            } else {
                color = 1;
                image.setImageResource(R.drawable.liu);
            }
        }
    }

    public void putChess(int r,int c){
        chess[r][c] = color;
        record[r][c] = step;
        step++;
        if (color == 1) {
            chessArray[r][c].setImageResource(R.drawable.red_t);
        }
        else {
            chessArray[r][c].setImageResource(R.drawable.green_t);
        }
    }

    public void battlecheck(){
        for(int j=0;j<=3;j++){
            if (chess[actualrow][j]==color && chess[actualrow][j+1]==color && chess[actualrow][j+2]==color && chess[actualrow][j+3]==color){
                win = true;
                if (color ==1) {
                    chessArray[actualrow][j].setImageResource(R.drawable.red_wint);
                    chessArray[actualrow][j+1].setImageResource(R.drawable.red_wint);
                    chessArray[actualrow][j+2].setImageResource(R.drawable.red_wint);
                    chessArray[actualrow][j+3].setImageResource(R.drawable.red_wint);
                }
                else {
                    chessArray[actualrow][j].setImageResource(R.drawable.green_wint);
                    chessArray[actualrow][j+1].setImageResource(R.drawable.green_wint);
                    chessArray[actualrow][j+2].setImageResource(R.drawable.green_wint);
                    chessArray[actualrow][j+3].setImageResource(R.drawable.green_wint);
                }
            }
        }
        for(int i=0;i<=2;i++){
            if (chess[i][actualcolumn]==color && chess[i+1][actualcolumn]==color && chess[i+2][actualcolumn]==color && chess[i+3][actualcolumn]==color){
                win = true;
                if (color ==1){
                    chessArray[i][actualcolumn].setImageResource(R.drawable.red_wint);
                    chessArray[i+1][actualcolumn].setImageResource(R.drawable.red_wint);
                    chessArray[i+2][actualcolumn].setImageResource(R.drawable.red_wint);
                    chessArray[i+3][actualcolumn].setImageResource(R.drawable.red_wint);
                }
                else{
                    chessArray[i][actualcolumn].setImageResource(R.drawable.green_wint);
                    chessArray[i+1][actualcolumn].setImageResource(R.drawable.green_wint);
                    chessArray[i+2][actualcolumn].setImageResource(R.drawable.green_wint);
                    chessArray[i+3][actualcolumn].setImageResource(R.drawable.green_wint);
                }
            }
        }
        if (actualrow + actualcolumn >= 3 && actualrow + actualcolumn <= 8){
            int start_j ;
            int end_j ;
            if (actualrow + actualcolumn >5){
                start_j = actualrow+actualcolumn-5;
                end_j = 3;
            }
            else {
                start_j = 0;
                end_j = actualrow+actualcolumn-3;
            }
            for(int j= start_j;j<=end_j;j++){
                int i = actualrow+actualcolumn - j;
                if (chess[i][j]==color && chess[i-1][j+1]==color && chess[i-2][j+2]==color && chess[i-3][j+3]==color){
                    win = true;
                    if (color == 1){
                        chessArray[i][j].setImageResource(R.drawable.red_wint);
                        chessArray[i-1][j+1].setImageResource(R.drawable.red_wint);
                        chessArray[i-2][j+2].setImageResource(R.drawable.red_wint);
                        chessArray[i-3][j+3].setImageResource(R.drawable.red_wint);
                    }
                    else {
                        chessArray[i][j].setImageResource(R.drawable.green_wint);
                        chessArray[i-1][j+1].setImageResource(R.drawable.green_wint);
                        chessArray[i-2][j+2].setImageResource(R.drawable.green_wint);
                        chessArray[i-3][j+3].setImageResource(R.drawable.green_wint);
                    }
                }
            }
        }
        if (actualrow-actualcolumn<=2 && actualrow-actualcolumn>=-3){
            int start_i ;
            int end_i ;
            if (actualrow - actualcolumn <= -1){
                start_i = 0;
                end_i = actualrow - actualcolumn + 2;
            }
            else {
                start_i = actualrow - actualcolumn;
                end_i = 2;
            }
            for (int i=start_i;i<=end_i;i++){
                int j = i-(actualrow-actualcolumn);
                if (chess[i][j]==color && chess[i+1][j+1]==color && chess[i+2][j+2]==color && chess[i+3][j+3]==color){
                    win = true;
                    if (color == 1){
                        chessArray[i][j].setImageResource(R.drawable.red_wint);
                        chessArray[i+1][j+1].setImageResource(R.drawable.red_wint);
                        chessArray[i+2][j+2].setImageResource(R.drawable.red_wint);
                        chessArray[i+3][j+3].setImageResource(R.drawable.red_wint);
                    }
                    else {
                        chessArray[i][j].setImageResource(R.drawable.green_wint);
                        chessArray[i+1][j+1].setImageResource(R.drawable.green_wint);
                        chessArray[i+2][j+2].setImageResource(R.drawable.green_wint);
                        chessArray[i+3][j+3].setImageResource(R.drawable.green_wint);
                    }
                }
            }
        }

        if (step == 43) {
            startchess = false;
            shitPlayer.start();
            mediaPlayer.stop();
            showWin.setText("Draw");
        }


        if (win){
            startchess = false;
            shitPlayer.start();
            mediaPlayer.stop();
            if (color == 1){
                showWin.setText("Red Win!");
                showWin.setTextColor(Color.RED);
            }else{
                showWin.setText("Green Win!");
                showWin.setTextColor(Color.GREEN);
            }
        }
    }

    public void initial(){
        step = 1;
        color = 1;
        win = false;
        startchess = true;
        image.setImageResource(R.drawable.liu);
        for (int i=0;i<6;i++)
            for(int j=0;j<7;j++){
                chess[i][j] = 0;
                record[i][j] = 0;
                chessArray[i][j].setImageResource(R.drawable.empty);
            }
        updateData();

    }

    public void updateChess(){
        image.setImageResource(R.drawable.liu);
        for(int i=0;i<6;i++) {
            for (int j = 0; j < 7; j++) {
                if (chess[i][j]==1){
                    chessArray[i][j].setImageResource(R.drawable.red_t);
                }else if (chess[i][j]==2) {
                    chessArray[i][j].setImageResource(R.drawable.green_t);
                }else{
                    chessArray[i][j].setImageResource(R.drawable.empty);
                }
            }
        }
    }

    public void back(){
        for (int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                if (record[i][j]==step-1){
                    record[i][j] = 0;
                    chess[i][j] = 0;
                    chessArray[i][j].setImageResource(R.drawable.empty);
                    step--;
                    nextTurn();
                    updateData();
                    if (win){
                        win = false;
                        startchess = true;
                        showWin.setText("");
                        updateChess();
                    }
                    return;
                }
            }
        }
    }

    public void updateData(){
        if (color ==1) {
            showColor.setText("RED");
            showColor.setTextColor(Color.RED);
        }else{
            showColor.setText("GREEN");
            showColor.setTextColor(Color.GREEN);
        }
    }
}

