package com.example.xayk.spaceobjects;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    class myThread extends Thread{

        boolean running=true;

        @Override
        public void run(){
        super.run();
        while(running)
        {
            try{
                Thread.sleep(50);

            }catch (InterruptedException E){}
            SpaceObjj.post(new Runnable() {
                @Override
                public void run() {
                    SpaceObjj.move();
                    if(SpaceObjj.runn==false) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Game OVER");
                        builder.setMessage("Your final score is "+SpaceObjj.PCS);
                        builder.setCancelable(false);
                        builder.setNegativeButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog= builder.create();
                        alertDialog.show();
                        SpaceObjj.ShipsLeft=10;
                        SpaceObjj.PCS=0;
                        SpaceObjj.values=60;
                        SpaceObjj.runn=true;

                    }
                }
            });
        }

        }
    }

    spaceObj SpaceObjj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpaceObjj = (spaceObj) findViewById(R.id.view);

        myThread mt = new myThread();
        mt.start();
    }
}
