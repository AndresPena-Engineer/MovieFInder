package nil.sqlitekino;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LandingPage extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        final Context c = this;
        if (Const.onLaunch) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    Const.onLaunch = false;
                    Const cst = new Const(c);
                    Const.db = cst.getWritableDatabase();
                    Const.db.execSQL("DELETE FROM " + Const.TABLE_NAME);
                }
            });
        }
    }

    public void onAddClick(View v) {
        CharSequence title = ((TextView) findViewById(R.id.name)).getText();
        if (!Midpoint.ms.matcher(title).matches()) {
            Toast.makeText(this, "Title not found", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (Const.db.query(true, Const.TABLE_NAME, null, Const.row.COL_NAME_TITLE + " LIKE ?", new String[]{title.toString()}, null, null, null, null).getCount() != 0) {
                Toast.makeText(this, "Title already submitted", Toast.LENGTH_LONG).show();
            }
        }
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Const.row.COL_NAME_GENRE, ((TextView) findViewById(R.id.genre)).getText().toString());
        values.put(Const.row.COL_NAME_PRICE, ((TextView) findViewById(R.id.price)).getText().toString());
        values.put(Const.row.COL_NAME_TITLE, ((TextView) findViewById(R.id.name)).getText().toString());

// Insert the new row, returning the primary key value of the new row
        Const.db.insert(Const.TABLE_NAME, null, values);
        Toast.makeText(this, "Added: " + ((TextView) findViewById(R.id.name)).getText(), Toast.LENGTH_LONG).show();
        ((TextView) findViewById(R.id.genre)).setText("");
        ((TextView) findViewById(R.id.price)).setText("");
        ((TextView) findViewById(R.id.name)).setText("");
    }

    public void onShowClick(View v) {
        if (Const.db == null || isEmptyTable() == 0) {
            Toast.makeText(this, "No table data available. db?=" + (Const.db == null), Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(this, Midpoint.class);
            startActivity(i);
        }
    }

    private int isEmptyTable() {
        String count = "SELECT count(*) FROM " + Const.TABLE_NAME;
        Cursor c = Const.db.rawQuery(count, null);
        c.moveToFirst();
        int i = c.getInt(0);
        c.close();
        return i;
    }

}
