package cn.libery.rowviewonclick;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by SZQ on 2015/5/30.
 */
public class RowView extends LinearLayout implements View.OnClickListener {
    private ImageView img;
    private TextView tv;
    private Button btn;
    private Context context;
    private RowActionEnum action;
    private OnRowViewClickListener listener;

    public RowView(Context context) {
        super(context);
        this.context = context;
        initializeView();
    }

    public RowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeView();
    }

    public RowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initializeView();
    }

    private void initializeView() {
        LayoutInflater.from(context).inflate(R.layout.layout_rowview_item, this);
        img = (ImageView) findViewById(R.id.image);
        tv = (TextView) findViewById(R.id.tv);
        btn = (Button) findViewById(R.id.btn);
    }

    public void initializeData(int resId, String label, RowActionEnum action, OnRowViewClickListener onClickListener) {
        this.action = action;
        this.listener = onClickListener;
        img.setBackgroundResource(resId);
        tv.setText(label);
        btn.setBackgroundResource(resId);
        btn.setText("This is button");
        if (action != null) {
            setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(action);
        }
    }
}
