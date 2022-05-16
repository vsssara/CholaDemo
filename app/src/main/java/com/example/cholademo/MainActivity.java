package com.example.cholademo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    LinearLayout myLinearLayout;

    ArrayList<String> Questions ;

    ArrayList<String> Questions_Type ;

    HashMap<String , String> Question_Options ;


    private ProgressBar progressBar;
    private TextView progressText;
    int i = 0;

    LinearLayout Next_Btn_lay , Pervious_Btn_lay ;
    Button submit_Btn;

    TextView Questions_TV;

    HashMap<String , String> Ques_Ans_map ;

    int Question_percentage;

    private GridListAdapter adapter;
    private ArrayList<String> arrayList;
    GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLinearLayout= findViewById(R.id.dynamic_layout);

        // set the id for the progressbar and progress text
        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);


        Pervious_Btn_lay = findViewById(R.id.Pervious_Btn_lay);
        Pervious_Btn_lay.setVisibility(View.GONE);
        Next_Btn_lay = findViewById(R.id.Next_Btn_lay);
        Next_Btn_lay.setVisibility(View.VISIBLE);
        submit_Btn = findViewById(R.id.submit_Btn);
        submit_Btn.setVisibility(View.GONE);

        Questions_TV = findViewById(R.id.Questions_TV);

        Questions = new ArrayList<>();
        Questions_Type = new ArrayList<>();
        Question_Options = new HashMap<>();

        Ques_Ans_map = new HashMap<>();

         gridView = (GridView) findViewById(R.id.grid_view);
        arrayList = new ArrayList<>();

        Dynamic_Data_APi();



        if (Questions_Type.get(0).equals("M"))
        {
            Set_First_Question();
        }
        else {
//            Dynamic_image_data(1);
            data_view();
        }




        // make as timer like that
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // set the limitations for the numeric
//                // text under the progress bar
//                if (i <= 100) {
//                    progressText.setText("" + i);
//                    progressBar.setProgress(i);
//                    i++;
//                    handler.postDelayed(this, 200);
//                } else {
//                    handler.removeCallbacks(this);
//                }
//            }
//        }, 200);



    }

    private void Set_First_Question() {

        progressText.setText("1");
        progressBar.setProgress(1 * Question_percentage );
        Questions_TV.setText(Questions.get(0));
        dynamic_radioButton(1);

    }

    public void data_view()
    {
        for (int i = 0; i < 4; i++)
            arrayList.add("GridView Items " + i);

        adapter = new GridListAdapter(this, arrayList, false);
        gridView.setAdapter(adapter);
    }


    private void Dynamic_Data_APi()
    {
        Questions.add("Q1");
        Questions.add("Q2");
        Questions.add("Q3");
        Questions.add("Q4");
        Questions.add("Q5");

        Questions_Type.add("P");
        Questions_Type.add("M");
        Questions_Type.add("M");
        Questions_Type.add("M");
        Questions_Type.add("P");

        Question_percentage = 100 / Questions.size() ;

        Question_Options.put("Q1_O1" , "1_A1");
        Question_Options.put("Q1_O2" , "1_A2");
        Question_Options.put("Q1_O3" , "1_A3");
        Question_Options.put("Q1_O4" , "1_A2");
        Question_Options.put("Q1_O5" , "1_A3");

        Question_Options.put("Q2_O1" , "2_A1");
        Question_Options.put("Q2_O2" , "2_A2");
        Question_Options.put("Q2_O3" , "2_A3");
        Question_Options.put("Q2_O4" , "2_A4");
        Question_Options.put("Q2_O5" , "2_A5");
        Question_Options.put("Q2_O6" , "2_A6");

        Question_Options.put("Q3_O1" , "3_A1");
        Question_Options.put("Q3_O2" , "3_A2");
        Question_Options.put("Q3_O3" , "3_A3");

        Question_Options.put("Q4_O1" , "4_A1");
        Question_Options.put("Q4_O2" , "4_A2");
        Question_Options.put("Q4_O3" , "4_A3");
        Question_Options.put("Q4_O4" , "4_A4");

        Question_Options.put("Q5_O1" , "5_A1");
        Question_Options.put("Q5_O2" , "5_A2");
        Question_Options.put("Q5_O3" , "5_A3");




    }






    private void dynamic_radioButton(int Q_no)
    {
        myLinearLayout.removeAllViews();

        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        myLinearLayout.addView(radioGroup);

        ArrayList<String> Single_Ques_Options = new ArrayList<>();

        for (int i=0 ; i< Question_Options.size(); i++) {
          if ( Question_Options.containsKey("Q"+Q_no+"_O"+i) ){
              Single_Ques_Options.add(Question_Options.get("Q"+Q_no+"_O"+i));
          }
        }

        for (int i = 0; i < Single_Ques_Options.size(); i++) {
            final RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i+1);
            radioButton.setText(Single_Ques_Options.get(i).toString());
            radioGroup.addView(radioButton);

//            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.profile);
//            radioButton.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(getResources(), bmp ), null, null, null);


            radioButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    int checkBoxId = v.getId();

                    for (int id= 1 ; id< Question_Options.size(); id++)
                    {
                        if (checkBoxId == id)
                        {
                            Ques_Ans_map.put( "Q"+progressText.getText().toString() , radioButton.getText().toString());
                        }
                    }
                }
            });



        }

        addLineSeperator();
    }

    private void addLineSeperator()
    {
        LinearLayout lineLayout = new LinearLayout(this);
        lineLayout.setBackgroundColor(Color.GRAY);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ViewGroup.LayoutParams.WRAP_CONTENT,(int) ViewGroup.LayoutParams.WRAP_CONTENT);

        // params.setMargins(0, convertDpToPixel(10), 0, convertDpToPixel(10));
        lineLayout.setLayoutParams(params);
        myLinearLayout.addView(lineLayout);

    }

    public void pervious_btn(View view)
    {
        submit_Btn.setVisibility(View.GONE);
        Next_Btn_lay.setVisibility(View.VISIBLE);

        int NextQuestion=Integer.parseInt(progressText.getText().toString()) ;

        int Current_Question_no = NextQuestion-1;
        progressBar.setProgress( Current_Question_no * Question_percentage );

        progressText.setText(String.valueOf(NextQuestion-1));
        Questions_TV.setText(Questions.get(NextQuestion-2));
        dynamic_radioButton(NextQuestion-1);

        if (NextQuestion == 2){
            Pervious_Btn_lay.setVisibility(View.GONE);
            Next_Btn_lay.setVisibility(View.VISIBLE);
        }


    }

    public void Submit_btn(View view)
    {

        Iterator myVeryOwnIterator = Ques_Ans_map.keySet().iterator();
        while(myVeryOwnIterator.hasNext()) {
            String key=(String)myVeryOwnIterator.next();
            String value=(String)Ques_Ans_map.get(key);
            Toast.makeText(this, "Key: "+key+" Value: "+value, Toast.LENGTH_LONG).show();
        }
    }

    public void Next_btn(View view)
    {
        arrayList.clear();

        for (int i = 20; i < 24; i++)
            arrayList.add("GridView Items " + i);

        adapter = new GridListAdapter(this, arrayList, false);
        gridView.setAdapter(adapter);



//       int NextQuestion=Integer.parseInt(progressText.getText().toString()) ;
//
//       if (NextQuestion<Questions.size())
//       {
//           Pervious_Btn_lay.setVisibility(View.VISIBLE);
//
//           int Current_Question_no = NextQuestion+1;
//
//           progressText.setText(String.valueOf(NextQuestion+1));
//           progressBar.setProgress( Current_Question_no * Question_percentage );
//           Questions_TV.setText(Questions.get(NextQuestion));
//           dynamic_radioButton(NextQuestion+1);
//
//           if (NextQuestion+1 == Questions.size())
//           {
//               Next_Btn_lay.setVisibility(View.GONE);
//               Pervious_Btn_lay.setVisibility(View.VISIBLE);
//               submit_Btn.setVisibility(View.VISIBLE);
//           }
//
//       }


    }
    private void Dynamic_image_data(int Q_no)
    {
        ArrayList<String> Single_Ques_Options = new ArrayList<>();

        for (int i=0 ; i< Question_Options.size(); i++)
        {
            if ( Question_Options.containsKey("Q"+Q_no+"_O"+i) )
            {
                Single_Ques_Options.add(Question_Options.get("Q"+Q_no+"_O"+i));
            }
        }

        int looping_img = 0;

        if (Single_Ques_Options.size()==2){
            looping_img =1;
        }
        else if (Single_Ques_Options.size()==4 || Single_Ques_Options.size()==3 ){
            looping_img =2;
        }
        else if (Single_Ques_Options.size()==6 || Single_Ques_Options.size()==5 ){
            looping_img =3;
        }

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);

        // if options comes 4 change it as 2 , options comes 4 change 2
        for (int i=0 ; i< looping_img ; i++)
        {
            if (i==0){int index_no =1; }  if (i==1){int index_no =2; } if (i==2){int index_no=3; }

            TableRow tbrow0 = new TableRow(this);
            tbrow0.setBackgroundColor(Color.parseColor("#DCDDE1"));
            tbrow0.setPadding(2, 2, 2, 2);

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            ToggleButton tb = new ToggleButton(this);
            tb.setBackground(getDrawable(R.drawable.toggle_selector));
            tb.setLayoutParams(new LinearLayout.LayoutParams(50, 30));
            tb.setChecked(false);

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.ic_launcher);

            TextView tv = new TextView(this);
            tv.setText(Questions.get(i+i));

            linearLayout.addView(tb);
            linearLayout.addView(imageView);

            //------***--------------


            ImageView imageView2 = new ImageView(this);
            imageView2.setImageResource(R.mipmap.ic_launcher);


            if (i+1 == looping_img )
            {
                if (Single_Ques_Options.size() == 5 || Single_Ques_Options.size() == 3){
                    imageView2.setVisibility(View.GONE);
                }
            }
            tbrow0.addView(linearLayout);
            tbrow0.addView(imageView2);

            stk.addView(tbrow0);

            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                }
            });
            imageView2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                }
            });
        }
//        myLinearLayout.addView(stk);

        addLineSeperator();




    }


//    private void Dynamic_image_data(int Q_no)
//    {
//        RadioGroup radioGroup = new RadioGroup(this);
//        radioGroup.setOrientation(LinearLayout.VERTICAL);
//        myLinearLayout.addView(radioGroup);
//
//        ArrayList<String> Single_Ques_Options = new ArrayList<>();
//
//        for (int i=0 ; i< Question_Options.size(); i++)
//        {
//            if ( Question_Options.containsKey("Q"+Q_no+"_O"+i) )
//            {
//                Single_Ques_Options.add(Question_Options.get("Q"+Q_no+"_O"+i));
//            }
//        }
//
//        int looping_img = 0;
//
//        if (Single_Ques_Options.size()==2){
//            looping_img =1;
//        }
//        else if (Single_Ques_Options.size()==4 || Single_Ques_Options.size()==3 ){
//            looping_img =2;
//        }
//        else if (Single_Ques_Options.size()==6 || Single_Ques_Options.size()==5 ){
//            looping_img =3;
//        }
//
//        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
//
//        // if options comes 4 change it as 2 , options comes 4 change 2
//        for (int i=0 ; i< looping_img ; i++)
//        {
//            if (i==0){int index_no =1; }  if (i==1){int index_no =2; } if (i==2){int index_no=3; }
//
//            TableRow tbrow0 = new TableRow(this);
//            tbrow0.setBackgroundColor(Color.parseColor("#DCDDE1"));
//            tbrow0.setPadding(2, 2, 2, 2);
//
//            LinearLayout linearLayout = new LinearLayout(this);
//            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
//
//            ToggleButton tb = new ToggleButton(this);
//            tb.setBackground(getDrawable(R.drawable.option_selector));
//            tb.setLayoutParams(new LinearLayout.LayoutParams(50, 30));
//            tb.setChecked(false);
//
//            ImageView imageView = new ImageView(this);
//            imageView.setImageResource(R.mipmap.ic_launcher);
//
//            TextView tv = new TextView(this);
//            tv.setText(quiz_ques_option_response.get(Q_no-1).getCode());
//            tv.setVisibility(View.GONE);
//
//            linearLayout.addView(tb);
//            linearLayout.addView(imageView);
//            linearLayout.addView(tv);
//
//            //------***--------------
//
//
//            ImageView imageView2 = new ImageView(this);
//            imageView2.setImageResource(R.mipmap.ic_launcher);
//
//
//            if (i+1 == looping_img )
//            {
//                if (Single_Ques_Options.size() == 5 || Single_Ques_Options.size() == 3){
//                    imageView2.setVisibility(View.GONE);
//                }
//            }
//
//            tbrow0.addView(linearLayout);
//            tbrow0.addView(imageView2);
//
//            stk.addView(tbrow0);
//
//
//            imageView.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//
//                }
//            });
//
//
//
//
//        }
////        myLinearLayout.addView(stk);
//
//        addLineSeperator();
//
//
//
//
//    }

}