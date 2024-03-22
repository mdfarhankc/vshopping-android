package com.example.jamia_virtual_shopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_orders extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView li;
    SharedPreferences sh;
    String[]  mid,pn, price ,q ,tp,photo,d;
    String ip, url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);


        li=findViewById(R.id.lo);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = sh.getString("url", "");
        url = ip + "and_view_orders";
        Toast.makeText(this, ""+url, Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "aaaa"+sh.getString("lid",""), Toast.LENGTH_SHORT).show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {


//

                                JSONArray js = jsonObj.getJSONArray("data");//from python
                                mid = new String[js.length()];
                                pn = new String[js.length()];
                                price = new String[js.length()];
                                q = new String[js.length()];
                                tp = new String[js.length()];
                                photo = new String[js.length()];
                                d = new String[js.length()];



                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    mid[i] = u.getString("booking_id");//dbcolumn name in double quotes
                                    pn[i] = u.getString("name");
                                    price[i] = u.getString("price");
                                    q[i] = u.getString("bquantity");
                                    tp[i] = u.getString("total_price");
                                    photo[i] = u.getString("photo");
                                    d[i] = u.getString("date");


//                                    Toast.makeText(getApplicationContext(), "rr"+com[i], Toast.LENGTH_SHORT).show();

                                }
                                li.setAdapter(new custom_view_orders(getApplicationContext(), mid, pn, price, q, tp, photo,d) {
                                });//custom_view_service.xml and li is the listview object


                            } else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {

            //                value Passing android to python
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", sh.getString("lid",""));//passing to python
                return params;
            }
        };


        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
        li.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
       final String j=mid[i].toString();
        Toast.makeText(this, ""+j, Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(view_orders.this);
        builder.setTitle("options");
        builder.setItems(new CharSequence[]
                        {"Track","Cancel"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:

//                                BigDecimal requestQueue = null;
//                                BigDecimal postRequest = null;
                            {

                                Intent ij= new Intent(getApplicationContext(),product_track.class);
                                ij.putExtra("bid",j);
//                                ij.putExtra("mid", String.valueOf(bid));
                                startActivity(ij);
//





//
                            }


                            break;

////                            case 1:
////                            {
////                                SharedPreferences sh2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
////                                String ip = sh2.getString("ip", "");
////                                final String sid1 = sh2.getString("lid", "");
//////                                params.put("lid", sp.getString("lid", ""));
////
////
////                                String url = "http://" + ip + ":5000/tockens";
////
////
//////                                requestQueue.add(postRequest);
////                            }
////
////
////
////
////
////                            break;
//
//                            case 1:
//                                Intent ij= new Intent(getApplicationContext(),Upipay.class);
//                                ij.putExtra("amount",am);
//                                ij.putExtra("mid", String.valueOf(bid));
//                                startActivity(ij);
//
//                                String url = "http://" + ip + ":5000/online_bank";
//                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
////                String url = null;
//                                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                                        new Response.Listener<String>() {
//                                            @Override
//                                            public void onResponse(String response) {
//                                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//
//                                                try {
//                                                    JSONObject jsonObj = new JSONObject(response);
//                                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//                                                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
//
//                                                        Intent i =new Intent(getApplicationContext(),view_cart.class);
//                                                        startActivity(i);
//                                                    } else {
//                                                        Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
//                                                    }
//
//                                                } catch (Exception e) {
//                                                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        },
//                                        new Response.ErrorListener() {
//                                            @Override
//                                            public void onErrorResponse(VolleyError error) {
//                                                // error
//                                                Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                ) {
//
//                                    //                value Passing android to python
//                                    @Override
//                                    protected Map<String, String> getParams() {
//                                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                        Map<String, String> params = new HashMap<String, String>();
//
//                                        params.put("amount",am);//passing to python
//                                        params.put("mid", String.valueOf(bid));//passing to python
//                                        params.put("id", sh.getString("lid",""));//passing to python
//
//
//
//                                        return params;
//                                    }
//                                };
//
//
//                                int MY_SOCKET_TIMEOUT_MS = 100000;
//
//                                postRequest.setRetryPolicy(new DefaultRetryPolicy(
//                                        MY_SOCKET_TIMEOUT_MS,
//                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                                requestQueue.add(postRequest);
//



                            case 1:

                                break;


                        }
                    }
                });
        builder.create().show();


    }
}
