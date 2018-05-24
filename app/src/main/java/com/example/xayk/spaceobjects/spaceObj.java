package com.example.xayk.spaceobjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

/**
 * Created by XayK on 24.01.2018.
 */

public class spaceObj extends View{


        int values=65;

        double []angle = new double[1000];
        int []radius = new int[1000];
        double []speed = new double[1000];
        int []widt= new int[1000];
        double Max1=0,Max2=0;

        boolean runn=true;
        int ShipsLeft=10;
        class SpaceShip {
            double angle;
            boolean status;
            int radius;
            int healh;
            public SpaceShip()
            {
                healh=100;
                angle=0;
                radius=0;
                status=false;
            }

        }

        int PCS=0;

        int cX,cY;

        double tarX,tarY;
        boolean target;

        SpaceShip[] spaceShip ;
        int curShips=0;

    public spaceObj(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        for(int i=0;i<values;i++)
        {
            angle[i]=0+(Math.random()*10)%(Math.PI*2-1);
            radius[i]=150+(int)(Math.random()*1000)%350;
            speed[i]=0.01+(Math.random())%0.05;
            widt[i]=3+(int)(Math.random()*10)%4;
            if(Math.random()<0.5)speed[i]=-speed[i];
        }
        spaceShip = new SpaceShip[5];
        for(int i=0;i<5;i++)
        {
            spaceShip[i]=new SpaceShip();
        }
    }

    void generate(int from,int to)
    {
        for(int i=from;i<to;i++)
        {
            angle[i]=0+(Math.random()*10)%(Math.PI*2-1);
            radius[i]=150+(int)(Math.random()*1000)%350;
            speed[i]=0.01+(Math.random())%0.05;
            if(Math.random()<0.5)speed[i]=-speed[i];
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setTextSize(35);



        p.setColor(Color.WHITE);

        cY=canvas.getHeight()/2;
        cX=canvas.getWidth()/2;



        canvas.drawColor(Color.BLACK);

        p.setColor(Color.WHITE);
        canvas.drawRect(0,0,canvas.getWidth(),100,p);
        p.setColor(Color.BLACK);
        canvas.drawText("PCS - "+PCS,40,50,p);
        canvas.drawText("SHIP LEFT - "+ShipsLeft,240,50,p);


        p.setColor(Color.MAGENTA);
        p.setStrokeWidth(5);
        if(target)canvas.drawLine(cX,cY,(float)tarX,(float)tarY,p);
        p.setColor(Color.WHITE);
        findSector();
        for(int i=0;i<values;i++) {


            canvas.drawCircle((float) (cX-radius[i]*Math.sin(angle[i])),(float) (cY-radius[i]*Math.cos((angle[i]))),widt[i],p);
            if(angle[i]==Max1 || angle[i]==Max2)
                canvas.drawLine(cX,cY,(float) (cX-radius[i]*Math.sin(angle[i])),(float) (cY-radius[i]*Math.cos((angle[i]))),p);
            //*0.0174533
        }




        for(int i=0;i<5;i++) {
            if (spaceShip[i].status) {
                p.setColor(Color.GREEN);
                if (checkCollision(i))
                    p.setColor(Color.RED);
               // canvas.drawBitmap();
                canvas.drawCircle((float) (cX - spaceShip[i].radius * Math.sin(spaceShip[i].angle)), (float) (cY - spaceShip[i].radius * Math.cos((spaceShip[i].angle))), 20, p);

                p.setColor(Color.RED);
                canvas.drawRect(canvas.getWidth()/2-(float)(spaceShip[i].healh*0.01)*canvas.getWidth()/2,80,canvas.getWidth()/2+(float)(spaceShip[i].healh*0.01)*canvas.getWidth()/2,160,p);
                p.setColor(Color.WHITE);
                canvas.drawText("HP"+spaceShip[i].healh,canvas.getWidth()/2-100,150,p);
            }
        }

        p.setColor(Color.BLUE);
        canvas.drawCircle(cX,cY,100,p);

        p.setColor(Color.GREEN);
        canvas.drawCircle(cX+35,cY+25,25,p);
        canvas.drawCircle(cX+55,cY+15,25,p);
        canvas.drawCircle(cX+50,cY+30,25,p);
        canvas.drawCircle(cX-20,cY-30,45,p);
        canvas.drawCircle(cX-50,cY+30,25,p);
        canvas.drawCircle(cX-20,cY-30,10,p);
    }


    boolean checkCollision(int j)
    {
     for(int i =0;i<values;i++)

             if(spaceShip[j].status)
             {
                 if(Math.hypot(((float)cX-radius[i]*Math.sin(angle[i]))-(float) (cX- spaceShip[j].radius*Math.sin(spaceShip[j].angle)),
                         (float) (cY-radius[i]*Math.cos((angle[i])))- (float) (cY-spaceShip[j].radius*Math.cos((spaceShip[j].angle))))<=20+widt[i]) {
                     spaceShip[j].healh -= 10;
                     if (spaceShip[j].healh <= 0)
                     {
                         values+=20;
                         generate(values-10,values);
                        // Toast.makeText(getContext(),"You FAILED the mission",Toast.LENGTH_SHORT).show();
                         spaceShip[j].status=false;
                         ShipsLeft--;
                         if(ShipsLeft==0)
                         {

                             runn=false;


                         }

                     }
                     return true;
                 }
             }

         return false;
    }


    public  void findSector()
    {
        double[] ang= Arrays.copyOf(angle,values);;
        Arrays.sort(ang);
        double max1=0,max2=0;
        for(int i=0;i<ang.length;i++)
        {
            if(i==0)
            {
                max1=ang[0];
                max2=ang[ang.length-1]-Math.PI*2;
            }
            else if(max1-max2<ang[i]-ang[i-1]){
                max1=ang[i];
                max2=ang[i-1];
            }

        }
        Max1=max1;
        if(max2<0)Max2=max2+Math.PI*2;
        else  Max2=max2;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //move();
        if(!runn)return true;
        tarX=event.getX();
        tarY= event.getY();
        double a,b,c;
        c=Math.hypot(cX-tarX,cY-tarY);
        b=Math.abs(cY-tarY);
        a=Math.abs(cX-tarX);
        double ang=0;
                if(cX>tarX && cY > tarY)ang=(double)(Math.acos((b*b+c*c-a*a)/(2*b*c)));
                else   if(cX>tarX && cY < tarY)ang=(double)(Math.acos((a*a+c*c-b*b)/(2*a*c)))+Math.PI/2;
                else   if(cX<tarX && cY < tarY)ang=(double)(Math.acos((b*b+c*c-a*a)/(2*b*c)))+Math.PI;
                else   if(cX<tarX && cY > tarY)ang=(double)(Math.acos((a*a+c*c-b*b)/(2*a*c)))+Math.PI*1.5;

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                target=true;

                break;
            case MotionEvent.ACTION_UP:
                if(spaceShip[curShips].status==false)launchSpaceObj( ang);
                target=false;
                break;
        }
        return true;
        //return super.onTouchEvent(event);
    }




    public void launchSpaceObj(double a)
    {

        spaceShip[curShips].angle=a;
        spaceShip[curShips].radius=500;
        spaceShip[curShips].healh=100;

        spaceShip[curShips].status=true;

        //curShips++;
        //if(curShips>4)curShips=0;
    }


    public  void move(){
        for(int i=0;i<values;i++) {

            angle[i]+=speed[i];
            if(angle[i]>Math.PI*2)angle[i]-=Math.PI*2;
            if(angle[i]<0)angle[i]+=Math.PI*2;
        }
        for(int i=0;i<5;i++) {
            if(spaceShip[i].status)
            {
                spaceShip[i].radius-=7;
                if(spaceShip[i].radius<=0)
                {
                    spaceShip[i].status=false;
                    Toast.makeText(getContext(),"Ship landed the planet with "+spaceShip[i].healh+"%",Toast.LENGTH_LONG).show();
                    PCS+=spaceShip[i].healh;
                }
            }
        }
        invalidate();
    }
}
