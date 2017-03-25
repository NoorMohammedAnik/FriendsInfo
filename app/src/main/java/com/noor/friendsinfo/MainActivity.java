package com.noor.friendsinfo;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.noor.friendsinfo.R.id.txtId;

public class MainActivity extends AppCompatActivity {

    EditText txtID,txtName,txtEmail;
    Button btnSave,btnView;
    TextView text;

    MySqlite mySqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID=(EditText)findViewById(txtId);
        txtName=(EditText)findViewById(R.id.txtName);
        txtEmail=(EditText)findViewById(R.id.txtEmail);
        btnSave=(Button)findViewById(R.id.btnSave);

        mySqlite=new MySqlite(this);

        text=(TextView)findViewById(R.id.textView);
        btnView=(Button)findViewById(R.id.btnView);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean chk=mySqlite.addToTable(txtID.getText().toString(),txtName.getText().toString(),txtEmail.getText().toString());

                if (chk==true)
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });

    }


//added onclick method in xml
    public void viewData(View v)
    {
        Cursor result=mySqlite.display();

        if (result.getCount()==0)
        {
          //  Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();

            showMsg("Error","No Data Found ! ");
            return;
        }

        StringBuffer buffer=new StringBuffer();


        result.moveToFirst();

        do {
            buffer.append("ID : "+result.getString(0)+"\n");
            buffer.append("NAME : "+result.getString(1)+"\n");
            buffer.append("EMAIL : "+result.getString(2)+"\n\n");

        }while (result.moveToNext());

       // Display(buffer.toString());
        showMsg("Data",buffer.toString());


       // Display(buffer.toString());

    }

//    public void Display(String data)
//    {
//        text.setText(data);
//    }


    public void showMsg(String title,String msg)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.show();
    }


    public void update(View v)
    {

       boolean chk= mySqlite.UpdateData(txtID.getText().toString(),txtName.getText().toString(),txtEmail.getText().toString());
        if (chk==true)
            Toast.makeText(this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Data Not Updated", Toast.LENGTH_SHORT).show();
    }

    public void delete(View v)
    {

       int delChk= mySqlite.DeleteData(txtID.getText().toString());
        if (delChk>0)
            Toast.makeText(this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Error ! Data Not Deleted", Toast.LENGTH_SHORT).show();
    }
}
