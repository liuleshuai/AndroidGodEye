package cn.hikyson.godeye.sample;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import cn.hikyson.android.godeye.sample.R;

/**
 * 用于展示打印日志的View
 * Created by kysonchao on 2017/12/9.
 */
public class LogView extends ConstraintLayout implements Loggable {
    private TextView mLogTv;
    private Handler mMainHandler;
    private ScrollView mSv;
    private boolean mIsFollow = true;

    public LogView(Context context) {
        this(context, null);
    }

    public LogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_log_layout, this);
        mLogTv = this.findViewById(R.id.view_log_layout_log_tv);
        mSv = this.findViewById(R.id.view_log_layout_sc);
        mMainHandler = new Handler(Looper.getMainLooper());
        this.findViewById(R.id.view_log_layout_follow).setSelected(this.mIsFollow);
        this.findViewById(R.id.view_log_layout_follow).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                LogView.this.follow(v.isSelected());
            }
        });
        this.findViewById(R.id.view_log_layout_clear).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogView.this.clear();
            }
        });
    }

    @Override
    public void log(final String msg) {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                mLogTv.append(msg + "\n\n");
                if (mIsFollow) {
                    LogView.this.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mSv.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    }, 250);
                }
            }
        });
    }

    private void clear() {
        mLogTv.setText("");
    }

    private void follow(boolean follow) {
        mIsFollow = follow;
        if (mIsFollow) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSv.fullScroll(ScrollView.FOCUS_DOWN);
                }
            }, 250);
        }
    }
}
