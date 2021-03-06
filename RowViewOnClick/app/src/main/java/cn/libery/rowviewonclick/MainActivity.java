package cn.libery.rowviewonclick;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnRowViewClickListener {
    private RowView rowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rowView = (RowView) findViewById(R.id.rowView);
        rowView.initializeData(R.drawable.ic_launcher, "TEST", RowActionEnum.TEST, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(RowActionEnum action) {
        Toast.makeText(getApplicationContext(), "RowViewOnClick", Toast.LENGTH_SHORT).show();
    }
}
