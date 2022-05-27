package edu.cmu.muyut2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MainActivity activity = this;
    TextView messageView = (TextView) findViewById(R.id.msg);
    TextView countryView = (TextView) findViewById(R.id.country);
    TextView regionView = (TextView) findViewById(R.id.region);
    TextView cityView = (TextView) findViewById(R.id.city);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button locate = (Button) findViewById(R.id.locate);
//        Button locateMyself = (Button) findViewById(R.id.locateMyself);
//
//        locate.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View viewParam) {
//                String ip = ((EditText) findViewById(R.id.inputIP)).getText().toString();
//                LocateIP locateIP = new LocateIP(activity, ip);
//                locateIP.locate();
//                countryView.setText("");
//                regionView.setText("");
//                cityView.setText("");
//            }
//        });
    }
//    public void setView(int code, String message, Location location) {
//        messageView.setText(message);
//        if (code != 400) {
//            countryView.setText(location.getCountry());
//            regionView.setText(location.getRegionName());
//            cityView.setText(location.getCity());
//        }
//    }
}