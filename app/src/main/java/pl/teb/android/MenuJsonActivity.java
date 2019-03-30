package pl.teb.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class MenuJsonActivity extends AppCompatActivity {

    private static final String TAG = "MenuJsonActivity";
    private static ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_json);
        getMenu(this);
    }
    protected void getMenu(final Context context) {
        showSimpleProgressDialog(context, "Loading...", "Fetching Json", false);

        final TextView textView = (TextView) findViewById(R.id.text1);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.192:5544";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        removeSimpleProgressDialog();
                        Log.v(TAG, "OnResponse");
                        textView.setText("Response is: " + response.toString().substring(0, 500));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        removeSimpleProgressDialog();
                        Log.v(TAG, "ErrorResponse" + error.getMessage());
                    }
                }

        );

        queue.add(jsonArrayRequest);
    }
        public static void showSimpleProgressDialog(Context context, String title,
                                                    String msg, boolean isCancelable) {
            try {
                if (mProgressDialog == null) {
                    mProgressDialog = ProgressDialog.show(context, title, msg);
                    mProgressDialog.setCancelable(isCancelable);
                }

                if (!mProgressDialog.isShowing()) {
                    mProgressDialog.show();
                }

            } catch (IllegalArgumentException ie) {
                ie.printStackTrace();
            } catch (RuntimeException re) {
                re.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void removeSimpleProgressDialog() {
            try {
                if (mProgressDialog != null) {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                        mProgressDialog = null;
                    }
                }
            } catch (IllegalArgumentException ie) {
                ie.printStackTrace();

            } catch (RuntimeException re) {
                re.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

