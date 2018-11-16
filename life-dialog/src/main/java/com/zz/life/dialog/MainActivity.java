package com.zz.life.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PopupWindow mPopupWindow;
    private Toolbar mToolbar;
    private int mScreenWidth;
    private View mEditView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.btn_bottom_popup).setOnClickListener(this);
        findViewById(R.id.btn_dialog).setOnClickListener(this);
        mEditView = findViewById(R.id.btn_edit);
        mEditView.setOnClickListener(this);

        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                showMenuPop();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMenuPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_menu_popup, null);
        final PopupWindow popupWindow = new PopupWindow(view, mScreenWidth / 2, LinearLayout.LayoutParams.WRAP_CONTENT);

        ListView listView = (ListView) view.findViewById(R.id.lv_menu_container);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                popupWindow.dismiss();
                Map<String, Object> map = (Map<String, Object>) adapterView.getAdapter().getItem(i);
                Toast.makeText(MainActivity.this, map.get("data").toString(), Toast.LENGTH_SHORT).show();
            }
        });
        String[] from = {"icon", "data"};
        int[] to = {R.id.img_menu_item_icon, R.id.tv_menu_title};
        listView.setAdapter(new SimpleAdapter(this, getData(), R.layout.layout_menu_item, from, to));

        popupWindow.setAnimationStyle(R.style.anim_menu_dialog);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x92000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(mToolbar, (int) (mScreenWidth * 0.48), 0);
    }

    private List<Map<String, Object>> getData() {
        int[] icon = {R.drawable.ic_chat_black_24dp, R.drawable.ic_person_add_black_24dp, R.drawable.ic_monetization_on_black_24dp, R.drawable.ic_live_help_black_24dp};
        String[] data = {"New Chat", "Add Contacts", "Money", "Help & Feedback"};
        List<Map<String, Object>> datas = new ArrayList<>();
        Map<String, Object> map;
        for (int i = 0; i < icon.length; i++) {
            map = new HashMap<>();
            map.put("icon", icon[i]);
            map.put("data", data[i]);
            datas.add(map);
        }
        return datas;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bottom_popup:
                showBottomPopupWindow();
                break;
            case R.id.btn_edit:
                showEditView();
                break;
            case R.id.btn_dialog:
                View v = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null);
                final AlertDialog dialog = new AlertDialog.Builder(this).setView(v).create();
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, ((TextView) (view)).getText(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                };

                v.findViewById(R.id.tv_cancel).setOnClickListener(onClickListener);
                v.findViewById(R.id.tv_sure).setOnClickListener(onClickListener);
                dialog.show();
                break;
        }
    }

    private void showEditView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_edit_view, null);
        int width = (int) (mScreenWidth * 0.9);
        final PopupWindow popupWindow = new PopupWindow(view, width, LinearLayout.LayoutParams.WRAP_CONTENT);
        final EditText editText = (EditText) view.findViewById(R.id.edit_content);
        view.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mToolbar.setTitle(editText.getText());
                popupWindow.dismiss();
            }
        });
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        //设置背景颜色
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        //设置可以获取焦点，否则弹出菜单中的EditText是无法获取输入的
        popupWindow.setFocusable(true);
        //防止虚拟软键盘被弹出菜单遮住
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // 设置其他区域颜色
        final Window window = getWindow();
        final WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = 0.6F;
        window.setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0F;
                window.setAttributes(params);
            }
        });
        //设置动画
        popupWindow.setAnimationStyle(R.style.anim_edit_dialog);
        popupWindow.showAsDropDown(mEditView, 0, 0);
    }

    private void showBottomPopupWindow() {
        View popView = LayoutInflater.from(this).inflate(R.layout.layout_popup_bottom, null);
        mPopupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置允许在外点击消失
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        //设置背景颜色
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        final Window window = getWindow();
        // 设置其他区域透明度
        final WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = 0.8F;
        window.setAttributes(params);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0F;
                window.setAttributes(params);
            }
        });

        //设置动画
        mPopupWindow.setAnimationStyle(R.style.anim_bottom_dialog);
        popView.findViewById(R.id.tv_camera_album).setOnClickListener(onClickListener);
        popView.findViewById(R.id.tv_picasso).setOnClickListener(onClickListener);
        popView.findViewById(R.id.tv_cancel).setOnClickListener(onClickListener);
        //参数1:根视图，整个Window界面的最顶层View  参数2:显示位置
        mPopupWindow.showAtLocation(window.getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            mPopupWindow.dismiss();
        }
    };
}
