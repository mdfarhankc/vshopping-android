package com.example.jamia_virtual_shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class chats extends AppCompatActivity {
    MessagesAdapter adapterMessages;
    ListView listMessages;
    Button bt1;
    EditText edtxttosent;
    Handler hnd;
    Runnable ad;
    String url,lid,toid,cname;
    SharedPreferences sh;
    String lastid,hu="",lastidd="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        hnd=new Handler();

        listMessages=findViewById(R.id.lcc);
        bt1= (Button) findViewById(R.id.bc);
        adapterMessages = new MessagesAdapter(chats.this);
        edtxttosent=(EditText)findViewById(R.id.input_chat_message);
        listMessages.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listMessages.setStackFromBottom(true);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        sh= PreferenceManager.getDefaultSharedPreferences( getApplicationContext() );
        lastid= sh.getString("lastid", "0");
        toid=chatwithstore.sid[chatwithstore.pos];


//        Toast.makeText( this, ""+sh.getString("lastid", "0"), Toast.LENGTH_SHORT ).show();
        cname=sh.getString("sname", "");
        hu = sh.getString( "ip", "" );


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final String msggg=edtxttosent.getText().toString();
                if(msggg.equalsIgnoreCase(""))
                {
                    edtxttosent.setError("enter message");
                }
                else {


                    String url = "http://" + hu + ":5000/add_chat";
                            Toast.makeText( getApplicationContext(), url, Toast.LENGTH_LONG ).show();


                    RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
                    StringRequest postRequest = new StringRequest( Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//                            Toast.makeText( getApplicationContext(), response, Toast.LENGTH_LONG ).show();

                                    // response
                                    try {
                                        JSONObject jsonObj = new JSONObject( response );
                                        if (jsonObj.getString( "status" ).equalsIgnoreCase( "Inserted" )) {

//                                            startActivity( new Intent( getApplicationContext(), Usersuggestion.class ) );
                                            edtxttosent.setText( "" );
                                        }


                                        // }
                                        else {
                                            Toast.makeText( getApplicationContext(), "Not found", Toast.LENGTH_LONG ).show();
                                        }

                                    } catch (Exception e) {
                                        Toast.makeText( getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT ).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText( getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT ).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences( getApplicationContext() );
                            Map<String, String> params = new HashMap<String, String>();

                            String id = sh.getString( "lid", "" );
                            params.put( "lid", id );
                            params.put( "toid", toid );
                            params.put( "message", msggg );

//                params.put("mac",maclis);

                            return params;
                        }
                    };

                    int MY_SOCKET_TIMEOUT_MS = 100000;

                    postRequest.setRetryPolicy( new DefaultRetryPolicy(
                            MY_SOCKET_TIMEOUT_MS,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
                    requestQueue.add( postRequest );

                }
        }
    });
        ad=new Runnable() {
            @Override
            public void run() {

                String chatURL = "http://" + hu + ":5000/view_chat";

                SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd hh:MM:ss" );
                RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
                StringRequest postRequest = new StringRequest( Request.Method.POST, chatURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                                Toast.makeText( getApplicationContext(), response, Toast.LENGTH_LONG ).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject( response );
                                    if (jsonObj.getString( "status" ).equalsIgnoreCase( "ok" )) {
                                        JSONArray jss = jsonObj.getJSONArray( "data" );//from python

                                        for (int i = 0; i < jss.length(); i++) {

                                            JSONObject js = jss.getJSONObject( i );
                                            ChatMessage message = new ChatMessage();
                                            message.setMessage( js.getString( "messsage" ) );//dbcolumnname
                                            String dt = js.getString( "date" );
                                            String sid = js.getString( "from_id" );
//                         Toast.makeText(Chat.this, ""+js.getString("from_id"), Toast.LENGTH_SHORT).show();
                                            message.setDate( dt );
                                            if (js.getString( "from_id" ).equalsIgnoreCase( sh.getString( "lid", "" ) )) {
                                                message.setUsername( "Me" );//text
//                                                Toast.makeText( Chat.this, "kkkkk", Toast.LENGTH_SHORT ).show();
                                                message.setIncomingMessage( false );
                                            } else {
                                                message.setUsername( chatwithstore.sname[chatwithstore.pos] );
                                                message.setIncomingMessage( true );
                                            }
                                            adapterMessages.add( message );
                                            lastid = js.getString( "chat_id" );
                                        }
                                        listMessages.setAdapter( adapterMessages );
                                        SharedPreferences.Editor edt = sh.edit();
                                        edt.putString( "lastid", lastid );
                                        edt.commit();


                                        hnd.postDelayed( ad, 5000 );
                                    }


                                    // }
                                    else {
                                        Toast.makeText( getApplicationContext(), "Not found", Toast.LENGTH_LONG ).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText( getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT ).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText( getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT ).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences( getApplicationContext() );
                        Map<String, String> params = new HashMap<String, String>();

                        String id = sh.getString( "lid", "" );
                        params.put( "lid", id );
                        params.put( "toid", toid);
                        params.put( "lastid", lastid );
                        return params;
                    }
                };
                int MY_SOCKET_TIMEOUT_MS=100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);

            }

        };
        hnd.post( ad );



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences.Editor ed=sh.edit();
        ed.putString( "lastid","0" );
        ed.commit();
        hnd.removeCallbacks( ad );
        startActivity( new Intent( chats.this,chatwithstore.class ) );
    }
}
