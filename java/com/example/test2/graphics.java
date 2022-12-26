package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import java.io.Serializable;
import java.text.DecimalFormat;


public class graphics extends AppCompatActivity implements Serializable {
    int amount;
    double[] massArray=new double[20];
    double[] qArray=new double[20];

    float currentX=0;
    float currentY=0;
    float nextX=0;
    float nextY=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
        Intent intent = getIntent();
        massArray=getIntent().getDoubleArrayExtra("massArray");
        qArray=getIntent().getDoubleArrayExtra("qArray");
        amount=getIntent().getIntExtra("amount",0);
    }

    public float findKforY(float height){
        double max=0;
        for(int i=0;i<amount;i++){if(qArray[i]>max) max=qArray[i];}
        return (float) (height/max);
    };

    public float findKforX(float width){
        double max=0;
        for(int i=0;i<amount;i++){if(massArray[i]>max) max=massArray[i];}
        return (float) (width/max);
    };

    class DrawView extends View {
        Paint p;
        Rect rect;
        public DrawView(Context context) {
            super(context);
            p = new Paint();
            rect = new Rect();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            float width=getWidth();
            float height=getHeight();
            float xstart= (float) (width*0.1);
            float xstop = (float) (width*0.9);
            float ystart= (float) (height*0.85);
            float ystop = (float) (height*0.1);

            canvas.drawARGB(80, 102, 204, 255);
            p.setColor(Color.RED);
            p.setStrokeWidth(3);
            p.setTextSize(30);
            float kY = findKforY((float) (height*0.7));
            float kX = findKforX((float) (width*0.75));
            DecimalFormat df=new DecimalFormat("###.###");
            for(int i=0;i<amount;i++){
                currentX=(float) (xstart+kX*massArray[i]);
                currentY=(float) (ystart-kY*qArray[i]);
                canvas.drawCircle(currentX, currentY, (float) (height*0.005),p);
                canvas.drawText(String.valueOf(massArray[i]), (float) (currentX-width*0.006), (float) (ystart+height*0.055),p);
                canvas.drawLine(currentX, (float) (ystart-height*0.01),currentX,(float) (ystart+height*0.01),p);
                canvas.drawLine((float) (xstart-width*0.006),currentY,(float) (xstart+width*0.006),currentY,p);
                canvas.drawText(df.format(qArray[i]), (float) (xstart+width*0.01), (float) (currentY+height*0.0075),p);
            }
            for(int i=0;i<amount-1;i++){
                currentX=(float) (xstart+kX*massArray[i]);
                currentY=(float) (ystart-kY*qArray[i]);
                nextX=(float) (xstart+kX*massArray[i+1]);
                nextY=(float) (ystart-kY*qArray[i+1]);
                canvas.drawLine(currentX,currentY,nextX,nextY,p);
            }
            // ордината
            canvas.drawLine(xstart,ystart,xstart,ystop,p);
            canvas.drawLine(xstart,ystop, (float) (xstart-width*0.009), (float) (ystop+height*0.03),p);
            canvas.drawLine(xstart,ystop, (float) (xstart+width*0.009), (float) (ystop+height*0.03),p);
            canvas.drawText("Q, Дж", (float) (xstart-width*0.01), (float) (ystop-height*0.025),p);
            //
            //абсцисса
            canvas.drawLine(xstart,ystart,xstop,ystart,p);
            canvas.drawLine(xstop,ystart, (float) (xstop-width*0.015), (float) (ystart+height*0.02),p);
            canvas.drawLine(xstop,ystart, (float) (xstop-width*0.015), (float) (ystart-height*0.02),p);
            canvas.drawText("m, грамм", (float) (xstop+width*0.01), (float) (ystart+height*0.02),p);
            //

        }

    }
}