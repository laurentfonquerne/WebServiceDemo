package com.example.administrateur.webservicedemo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText txtCode;
    private TextView txtResult;
    private TextView txtName;
    private ListView lstResult;

    private final String URL_WS1 = "http://demo@services.groupkt.com/country/get/iso2code/";
    private final String URL_WS2 = "http://demo@services.groupkt.com/country/get/all";
    ArrayAdapter<String> adapter;
    ArrayList<String> pays = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCode   = (EditText) findViewById(R.id.txtCode);
        txtName   = (TextView) findViewById(R.id.txtName);
        lstResult = (ListView) findViewById(R.id.lstResult);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item,pays);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, pays);

    }
    public void parCode(View v) throws InterruptedException, JSONException {
        String code = txtCode.getText().toString();
        String adr = URL_WS1 + code;
      //  Toast.makeText(this, adr,Toast.LENGTH_LONG).show();

        HttpClient hc = new HttpClient();
        hc.setAdr(adr);
        hc.start();
        hc.join();



        JSONObject jo  = new   JSONObject(hc.getResponse());
        JSONObject jo2 = jo.getJSONObject("RestResponse");
        JSONObject jo3 = jo2.getJSONObject("result");

        txtName.setText(jo3.getString("name"));



    }
    public void parTous(View v) throws InterruptedException, JSONException {
        String code = txtCode.getText().toString();
        String adr = URL_WS2 + code;

        HttpClient hc = new HttpClient();
        hc.setAdr(adr);
        hc.start();
        hc.join();

        JSONObject jo  = new   JSONObject(hc.getResponse());
        JSONObject jo2 = jo.getJSONObject("RestResponse");
     
        JSONArray  ja = new JSONArray(jo2.getString("result"));

        for (int i = 0; i < ja.length(); i++) {

        // On récupère un objet JSON du tableau
            JSONObject obj = new JSONObject(ja.getString(i));
            pays.add(obj.getString("name"));
        }
        // Set The Adapter
        lstResult.setAdapter(adapter);

    }


}
