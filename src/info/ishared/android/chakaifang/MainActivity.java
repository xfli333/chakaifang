package info.ishared.android.chakaifang;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private Button mAgreeBtn;
    private Button mDisAgreeBtn;
    private Dialog mDialog;
    private View mDialogView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDialogView = View.inflate(this, R.layout.law_dialog, null);

        mAgreeBtn = (Button) mDialogView.findViewById(R.id.btn_agree);
        mDisAgreeBtn = (Button) mDialogView.findViewById(R.id.btn_disagree);

        showLawDialog();

        mAgreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
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
