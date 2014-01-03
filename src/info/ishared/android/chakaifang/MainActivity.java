package info.ishared.android.chakaifang;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import info.ishared.android.chakaifang.model.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private Button mAgreeBtn;
    private Button mDisAgreeBtn;
    private Button mQuery;

    private EditText mEditText;

    private Dialog mDialog;
    private View mDialogView;
    private SimpleAdapter listAdapter;
    private ListView mListView;
    private List<HashMap<String, String>> listDate = new ArrayList<HashMap<String, String>>();

    private MainController mController;
    private Handler mHandler;

    private ProgressDialog mProgressDialog;
    private static final int DIALOG_PROGRESS = 11;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = new MainController(this);


        setContentView(R.layout.main);

        mDialogView = View.inflate(this, R.layout.law_dialog, null);

        mEditText = (EditText) this.findViewById(R.id.input_query_key);
        mListView = (ListView) this.findViewById(R.id.query_data_list_view);

        mAgreeBtn = (Button) mDialogView.findViewById(R.id.btn_agree);
        mDisAgreeBtn = (Button) mDialogView.findViewById(R.id.btn_disagree);
        mQuery = (Button) this.findViewById(R.id.query__btn);

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

        mQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showDialog(DIALOG_PROGRESS);
                String key = mEditText.getText().toString().trim();
                try {
                    mController.queryUserInfo(key);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    hideProgressDialog();
                }
            }
        });

        listAdapter = new SimpleAdapter(this, listDate, R.layout.user_list_view_item,
                new String[]{"item_data_info"},
                new int[]{R.id.item_data_info});
        mListView.setAdapter(listAdapter);
        mHandler = new Handler();
    }


    public void updateQueryListData(final List<UserInfo> userInfoList) {
        listDate.clear();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                for (UserInfo userInfo : userInfoList) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("item_data_info", formatUserInfoToListView(userInfo));
                    listDate.add(map);
                    Log.d(AppConfig.TAG, "finfifiififififififififi========");
                }

                listAdapter.notifyDataSetChanged();
            }
        });
    }


    private String formatUserInfoToListView(UserInfo userInfo) {
        String dataStr = "姓名:" + userInfo.getUserName() + "\r\n";
        dataStr += "性别:" + userInfo.getSex() + "\r\n";
        dataStr += "身份证号:" + userInfo.getIdCardNo() + "\r\n";
        dataStr += "出生日期:" + userInfo.getBirthDay() + "\r\n";
        dataStr += "年龄:" + userInfo.getPhoneNumber() + "\r\n";
        dataStr += "开房日期:" + userInfo.getCheckInDate() + "\r\n";
        dataStr += "手机:" + userInfo.getCellPhone() + "\r\n";
        dataStr += "电子邮件:" + userInfo.getEmail() + "\r\n";
        dataStr += "地址:" + userInfo.getAddress();
        return dataStr;
    }

    private void showLawDialog() {


        mDialog = new Dialog(MainActivity.this, R.style.dialog);
        mDialog.setContentView(mDialogView);
        mDialog.setCancelable(false);
        mDialog.show();
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setMessage("正在处理，请等待......");
                mProgressDialog.setCancelable(false);
                mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                            return true;
                        }
                        return false;
                    }
                });
                return mDialog;
            default:
                return null;
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            removeDialog(DIALOG_PROGRESS);
        }
    }

}
