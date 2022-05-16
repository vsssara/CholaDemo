package com.example.cholademo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchSpinner extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    String[] options;

    ListView listview_item ;

    ArrayList<String> listarra = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_spinner);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);


        // Creating ArrayAdapter using the string array and default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Animals, android.R.layout.simple_spinner_item);

        // Specify layout to be used when list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Applying the adapter to our spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        options = SearchSpinner.this.getResources().getStringArray(R.array.Animals);


        listarra.add("aaaa");
        listarra.add("bbbb");
        listarra.add("cccc");
        listarra.add("dddd");

        final List<KeyPairBoolData> listArray0 = new ArrayList<>();
        for (int i = 0; i < listarra.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(listarra.get(i));
            h.setSelected(false);
            listArray0.add(h);
        }


        /**
         * Search MultiSelection Spinner (With Search/Filter Functionality)
         *
         *  Using MultiSpinnerSearch class
         */
        MultiSpinnerSearch multiSelectSpinnerWithSearch = findViewById(R.id.multipleItemSelectionSpinner);

        // Pass true If you want searchView above the list. Otherwise false. default = true.
        multiSelectSpinnerWithSearch.setSearchEnabled(true);

        // A text that will display in search hint.
        multiSelectSpinnerWithSearch.setSearchHint("Select your mood");

        // Set text that will display when search result not found...
        multiSelectSpinnerWithSearch.setEmptyTitle("Not Data Found!");

        // If you will set the limit, this button will not display automatically.
        multiSelectSpinnerWithSearch.setShowSelectAllButton(true);

        //A text that will display in clear text button
        multiSelectSpinnerWithSearch.setClearText("Close & Clear");

        // Removed second parameter, position. Its not required now..
        // If you want to pass preselected items, you can do it while making listArray,
        // Pass true in setSelected of any item that you want to preselect
        multiSelectSpinnerWithSearch.setItems( listArray0, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.e("selected values", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });





    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(this, " You select >> "+options[position], Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void Dialog_view(View view) {

        final Dialog dialog_data = new Dialog(this);

        dialog_data.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog_data.getWindow().setGravity(Gravity.CENTER);

        dialog_data.setContentView(R.layout.custom_alertdialog);

        WindowManager.LayoutParams lp_number_picker = new WindowManager.LayoutParams();
        Window window = dialog_data.getWindow();
        lp_number_picker.copyFrom(window.getAttributes());

        lp_number_picker.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp_number_picker.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp_number_picker);

        EditText filterText = (EditText) dialog_data.findViewById(R.id.alertdialog_edittext);
        ListView alertdialog_Listview = (ListView) dialog_data.findViewById(R.id.alertdialog_Listview);
        alertdialog_Listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        Button ok_but = dialog_data.findViewById(R.id.ok_but);

        String tutorials[]
                = { "Algorithms", "Data Structures","Languages", "Interview Corner","GATE", "ISRO CS"};

        ArrayAdapter<String> arr = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_multiple_choice ,
                tutorials);
        alertdialog_Listview.setAdapter(arr);

        alertdialog_Listview.setItemChecked(2,true);


        alertdialog_Listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id)
            {

                Log.e("selected values", String.valueOf(a.getItemAtPosition(position)) );

            }
        });


        filterText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
//                filter(s.toString());
                arr.getFilter().filter(s);
            }
        });




        ok_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cntChoice = alertdialog_Listview.getCount();

                SparseBooleanArray sparseBooleanArray = alertdialog_Listview.getCheckedItemPositions();

                for(int i = 0; i < cntChoice; i++){
                    if(sparseBooleanArray.get(i)) {
                       Log.e("selected item" , alertdialog_Listview.getItemAtPosition(i).toString() );
                    }
                }

            }
        });

        dialog_data.show();

    }









    public void pick(View view)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, 11);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            Uri uri = data.getData();

          String s =  Pdf_file_compress(  uri);

            Log.e("file_str" , Pdf_file_compress(  uri));

            String upToNCharacters = s.substring(0, Math.min(s.length(), 8));

            Uri returnUri = data.getData();
            String mimeType = getContentResolver().getType(returnUri);

            Toast.makeText(SearchSpinner.this, upToNCharacters +"-----------"+ mimeType +"-----"  + uri.toString() , Toast.LENGTH_LONG).show();

        } catch (NullPointerException ee)
        {
            Log.e("error select file" ,"unsupport file");
        }


    }

    private String Pdf_file_compress( Uri uri)
    {
        ContentResolver resolver = getApplicationContext().getContentResolver();

        try (InputStream is = resolver.openInputStream(uri))
        {
            // Perform operations on "stream".


            byte[] bytesArray = new byte[is.available()];
            is.read(bytesArray);

            String encoded = Base64.encodeToString( bytesArray, Base64.NO_WRAP);
            return encoded;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}