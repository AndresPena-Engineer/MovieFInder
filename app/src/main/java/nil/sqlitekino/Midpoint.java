package nil.sqlitekino;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static nil.sqlitekino.Const.*;

public class Midpoint extends AppCompatActivity {
    public static final String[] titles = new String[]{"([hH]omeward ?[bB]ound)", "([sS]cooby ?[dD]oo(:? ?[cC]yber ?[cC]hase)?)", "[sS]harknado", "[wW]onder ?[wW]oman"};
    public static final int[] resources = new int[]{R.drawable.homewardbound, R.drawable.scoobydoocyber, R.drawable.sharknado, R.drawable.wonderwoman};
    public static final Pattern[] ts;
    public static final Pattern ms;// = Pattern.compile(titles[0] + "|" + titles[1] + "|" + titles[2] + "|" + titles[3]);

    static {
        StringBuilder sb = new StringBuilder();
        ArrayList<Pattern> ps = new ArrayList<>();
        for (String p : titles) {
            ps.add(Pattern.compile(p));
            sb.append(p).append("|");
        }
        ts = ps.toArray(new Pattern[0]);
        ms = Pattern.compile(sb.substring(0, sb.length() - 1));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midpoint);
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                row._ID,
                row.COL_NAME_TITLE,
                row.COL_NAME_GENRE,
                row.COL_NAME_PRICE
        };

// Filter results WHERE "title" = 'My Title'
        String[] selectionArgs = {"My Title"};
        boolean[] used = new boolean[titles.length];
        Cursor cursor = Const.db.query(true, Const.TABLE_NAME, null, null, null, null, null, null, null);
        LinearLayout llm = (LinearLayout) findViewById(R.id.lordParent);
        final Context c = this;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(row.COL_NAME_TITLE));
            if (ms.matcher(name).matches()) {
                for (int i = 0; i < titles.length; i++) {
                    if (ts[i].matcher(name).matches()) {
                        if (!used[i]) {
                            ImageViewBundle iv = new ImageViewBundle(this,new String[]{
                                    cursor.getString(cursor.getColumnIndex(row.COL_NAME_TITLE)),
                                    cursor.getString(cursor.getColumnIndex(row.COL_NAME_GENRE)),
                                    cursor.getString(cursor.getColumnIndex(row.COL_NAME_PRICE))
                            },resources[i]);
                            llm.addView(iv);
                            iv.setOnClickListener(new View.OnClickListener(){
                                public void onClick(View v){
                                    ImageViewBundle ivb =(ImageViewBundle) v;
                                    Intent i = new Intent(c, Selector.class);
                                    i.putExtra(Const.EXTRA_KEY,ivb.s);
                                    i.putExtra(Const.IMG_KEY,ivb.i);
                                    startActivity(i);
                                }
                            });
                            used[i] = true;
                            break;
                        }
                    }
                }
            }
        }
        cursor.close();
    }
    class ImageViewBundle extends android.support.v7.widget.AppCompatImageView{
        public final String[] s;
        public final int i;
        public ImageViewBundle(Context c,String[] s, int i) {
            super(c);
            this.s = s;
            this.i = i;
            this.setImageResource(i);
        }
    }

    public void onHomeClick(View v){
        startActivity(new Intent(this,LandingPage.class));
    }
    public void onBackClick(View v){
        startActivity(new Intent(this,LandingPage.class));
    }
}
