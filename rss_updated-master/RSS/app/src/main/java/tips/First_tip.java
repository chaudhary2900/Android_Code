package tips;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import home.example.com.rss.MainActivity;
import home.example.com.rss.R;

import tips.SimpleGestureFilter.SimpleGestureListener;

public class First_tip extends Activity implements SimpleGestureListener {
  static   String s1;
    static int i = 1;
    String ty;

    private tips.SimpleGestureFilter detector;
    View view3;
    int nobutton;
    TextView tipfirst;
    RatingBar ratingbar;
    Button button;
    String tip_id;
    JSONArray project1, content1, content_tips;
    String tip_content, cont;
    Button btn0, btn1, btn2;
    Context context;
   private String rating,remarks_detail;
    EditText remarksDetail;
  static ArrayList<String> list,l1;



    public String JSON_URL = "http://www.trinityapplab.in/RSS/tips.php";
    //Fragment fragmentTwo;
    //FragmentManager fragmentManager = getFragmentManager();
//FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_list);
         addListenerOnButtonClick();

        list= new ArrayList<String>();
        tipfirst = (TextView) findViewById(R.id.tips_list_view);
        remarksDetail=(EditText)findViewById(R.id.remarks_edit);

//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(remarksDetail, InputMethodManager.SHOW_IMPLICIT);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        new LongOperation().execute(JSON_URL);


        detector = new tips.SimpleGestureFilter(this, this);



    }



    // for Rating
    public void addListenerOnButtonClick(){



        ratingbar=(RatingBar)findViewById(R.id.ratingBar1);

     button=(Button)findViewById(R.id.button123);
        //Performing action on Button Click



    button.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            //Getting the rating and displaying it on the toast
            tip_id = "1";
            rating = String.valueOf(ratingbar.getRating());
            remarks_detail = remarksDetail.getText().toString();
            //  Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
            new SendtoServer().execute();

            remarksDetail.setText(null);
            ratingbar.setRating(0);
        }

    });




    }


    private class LongOperation extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String tips_content;

        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(First_tip.this);


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
            // tvisit1.setText("Output : ");
            Dialog.setMessage("Current Trending...");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {

                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.

                // Server url call by GET method
                HttpGet httpget = new HttpGet(urls[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                tips_content = Client.execute(httpget, responseHandler);
                try {



                    JSONArray jsonArray = new JSONArray(tips_content);







                            for (int j= 0; j < jsonArray.length(); j++) {
                            JSONObject jsnobject=jsonArray.getJSONObject(j);
                           list.add(jsnobject.getString("tips_content"));
                                if(j==0)
                                {
                                   s1=jsnobject.getString("tips_content");
                                }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (ClientProtocolException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (IOException e) {
                Error = e.getMessage();
                cancel(true);
            }

            return null;
        }


        protected void onPostExecute(Void unused) {


            Dialog.dismiss();

            if (Error != null) {


            } else {

              tipfirst.setText(s1);

//                try{
//                    JSONObject obj=   project1.getJSONObject(0);
//                    name =obj.getString("label");
//
//                }
//                catch (JSONException e) {
//                    e.printStackTrace();
//                }



            }


        }

    }


                @Override
                public boolean dispatchTouchEvent(MotionEvent me) {
                    // Call onTouchEvent of SimpleGestureFilter class
                    this.detector.onTouchEvent(me);
                    return super.dispatchTouchEvent(me);
                }

                @Override
                public void onSwipe(int direction) {

                    switch (direction) {

                      case tips.SimpleGestureFilter.SWIPE_RIGHT :

                          Intent intent =new Intent(First_tip.this, MainActivity.class);
                          startActivity(intent);

                 break;
               case tips.SimpleGestureFilter.SWIPE_LEFT :

                   Intent intent1 =new Intent(First_tip.this, MainActivity.class);
                   startActivity(intent1);

                  break;
            case tips.SimpleGestureFilter.SWIPE_DOWN:



                if (i == list.size()) {

                    i--;
                } else {
                    s1 = list.get(i);
                    tipfirst.setText(s1);
                    i--;
                }


                if (i == -1) {
                    i=0;
                }
                break;


            case tips.SimpleGestureFilter.SWIPE_UP:





                if(list.size()==i)

            {
                i--;
            }else
                {
                    s1=list.get(i);

                    tipfirst.setText(s1);


               }
                 i++;
                button.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {


               ty=String.valueOf(i);


                        tip_id=ty;
                        rating=String.valueOf(ratingbar.getRating());
                        remarks_detail = remarksDetail.getText().toString();

                        new SendtoServer().execute();

                        remarksDetail.setText(null);
                        ratingbar.setRating(0);


                    }

                });

                break;
        }
    }



    @Override
    public void onDoubleTap() {
        Toast.makeText(this, "You tapped double", Toast.LENGTH_SHORT).show();

    }



    class SendtoServer extends AsyncTask<String, Void, String> {


        ProgressDialog dlg;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg = new ProgressDialog(First_tip.this);
            dlg.setMessage("Saving...");
            dlg.show();

        }


        @Override
        protected String doInBackground(String... params) {


            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            nameValuePairList.add(new BasicNameValuePair("tip_id", tip_id));
            nameValuePairList.add(new BasicNameValuePair("remark", remarks_detail));
            nameValuePairList.add(new BasicNameValuePair("rating", rating));


            // edt1, edt4, edt2,edt3


            String result = new ServiceHandler().makeServiceCall("http://www.trinityapplab.in/RSS/rating.php", 1, nameValuePairList);
        //  Log.d("result =", result.toString());

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();

        }


    }
}



