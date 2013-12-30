package info.ishared.android.chakaifang;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private Button mAgreeBtn;
    private Button mDisAgreeBtn;
    private Dialog mDialog;
    private View mDialogView;

    private MainController mController;
    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = new MainController(this);


        setContentView(R.layout.main);
        mTextView = (TextView) this.findViewById(R.id.test);

        mDialogView = View.inflate(this, R.layout.law_dialog, null);

        mAgreeBtn = (Button) mDialogView.findViewById(R.id.btn_agree);
        mDisAgreeBtn = (Button) mDialogView.findViewById(R.id.btn_disagree);

        showLawDialog();

        mAgreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                try {
                    String text = mController.queryUserInfo("杨阳");
                    mTextView.setText(text);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mDisAgreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
    }

    private void showLawDialog() {


        mDialog = new Dialog(MainActivity.this, R.style.dialog);
        mDialog.setContentView(mDialogView);
        mDialog.setCancelable(false);
        mDialog.show();
    }
}
