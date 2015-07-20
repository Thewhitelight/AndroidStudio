package cn.libery.activeandroiddemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    private EditText edit_name, edit_age, edit_gender;
    private Button btn_insert, btn_update, btn_delete, btn_select;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_age = (EditText) findViewById(R.id.edit_age);
        edit_gender = (EditText) findViewById(R.id.edit_gender);
        btn_insert = (Button) findViewById(R.id.insert);
        btn_update = (Button) findViewById(R.id.update);
        btn_delete = (Button) findViewById(R.id.delete);
        btn_select = (Button) findViewById(R.id.select);
        text = (TextView) findViewById(R.id.text);
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_name.getText().toString().trim();
                String age = edit_age.getText().toString();
                String gender = edit_gender.getText().toString().trim();
                Students students = new Students(name, age, gender);
                students.save();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_name.getText().toString().trim();
                String age = edit_age.getText().toString().trim();
                new Update(Students.class).set("Name = ?", name).where("Age = ?", age).execute();
            }
        });
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_name.getText().toString().trim();
                selectStudents(name);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_name.getText().toString().trim();
                new Delete().from(Students.class).where("Name = ?", name).execute();
            }
        });
    }

    private void selectStudents(String name) {
        int num = getAll(name).size();
        for (int i = 0; i < num; i++) {
            text.append(getAll(name).get(i).getName().toString() + getAll(name).get(i).getAge().toString() + getAll(name).get(i).getGender().toString());
        }

    }

    public static List<Students> getAll(String name) {
        return new Select()
                .from(Students.class)
                .where("Name = ?", name).execute();
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
