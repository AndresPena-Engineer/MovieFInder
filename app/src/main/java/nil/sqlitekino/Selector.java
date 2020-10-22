package nil.sqlitekino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Selector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        Intent i = getIntent();
        ((ImageView) findViewById(R.id.imgOut)).setImageResource(i.getIntExtra(Const.IMG_KEY,R.drawable.wonderwoman));
        String[] ss = i.getStringArrayExtra(Const.EXTRA_KEY);
        ((TextView)findViewById(R.id.nField)).setText(ss[0]);
        ((TextView)findViewById(R.id.gField)).setText(ss[1]);
        ((TextView)findViewById(R.id.pField)).setText(ss[2]);
    }

    public void onHomeClick(View v){
        startActivity(new Intent(this,LandingPage.class));
    }
    public void onBackClick(View v){
        startActivity(new Intent(this,Midpoint.class));
    }
}
