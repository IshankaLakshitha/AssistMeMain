package sliitassisme.assistmemain;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import sliitassisme.assistmemain.FirstTime.remidItem;

public class TestActivity extends AppCompatActivity {

    Button show;
    Dialog MyDialog;
    Button hello,close;
    TextView tex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        MyDialog = new Dialog(TestActivity.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.popupnotification);
        MyDialog.setTitle("My Custom Dialog");

        remidItem RM=new remidItem(MainActivity.DATABASEHANDLER);

        hello = (Button)MyDialog.findViewById(R.id.hello);
        close = (Button)MyDialog.findViewById(R.id.close);

        hello.setEnabled(true);
        close.setEnabled(true);

        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hello, I'm Custom Alert Dialog", Toast.LENGTH_LONG).show();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MyDialog.cancel();
                finish();
            }
        });

        tex=(TextView)MyDialog.findViewById(R.id.textView6);
        tex.setText(RM.itemsForDay());

        MyDialog.show();
    }
}
