package libery.cn.disklrucache;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.GridView;


public class MainActivity extends ActionBarActivity {
    private GridView gridView;
    private GridViewAdapter adapter;
    private int mSize;
    private int mSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView);
        mSize = getResources().getDimensionPixelSize(R.dimen.size);
        mSpace = getResources().getDimensionPixelSize(R.dimen.space);
        adapter = new GridViewAdapter(this, 0, ImageUrls.imageUrls, gridView);
        gridView.setAdapter(adapter);
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int numColumn = (int) Math.floor(gridView.getWidth() / (mSize + mSpace));
                if (numColumn > 0) {
                    int columnWith = (gridView.getWidth() / numColumn) - mSpace;
                    adapter.setmHeight(columnWith);
                    gridView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.flushCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cancelTask();
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
}
