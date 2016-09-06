package com.zxingtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.Intents;
import com.lychee.formviewtest.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by j on 2016/9/6.
 */
public class ZXingActivity extends AppCompatActivity {

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.imageView)
    ImageView imageView;

    private static final int REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != data && requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    textView.setText(data.getStringExtra(Intents.Scan.RESULT));
                    textView2.setText(data.getStringExtra(Intents.Scan.RESULT_FORMAT));
                    textView3.setText(data.toUri(data.getFlags()));
                    break;
            }
        }
    }

    private void callCapture(String characterSet) {

        Intent intent = new Intent();
        intent.setAction(Intents.Scan.ACTION);
        intent.putExtra(Intents.Scan.MODE, Intents.Scan.QR_CODE_MODE);
        intent.putExtra(Intents.Scan.CHARACTER_SET, characterSet);
        intent.putExtra(Intents.Scan.WIDTH, 500);
        intent.putExtra(Intents.Scan.HEIGHT, 500);
        // intent.putExtra(Intents.Scan.PROMPT_MESSAGE, "type your prompt message");
        intent.setClass(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }


    @OnClick({R.id.button, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                callCapture("UTF-8");
                break;
            case R.id.button2:
                Bitmap bitmap = QRCreateUtils.createQRCodeWithLogo(editText.getText().toString(), 500,
                        BitmapFactory.decodeResource(getResources(), R.drawable.launcher_icon));
                imageView.setImageBitmap(bitmap);
                break;
        }
    }
}
