package com.zz.databinding;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zz.databinding.databinding.ActivityMainBinding;
import com.zz.databinding.databinding.LayoutItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<AppModel> appModels = new ArrayList<>();
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initData();
    }

    private void initData() {
        // 获取包资源管理器
        PackageManager packageManager = getPackageManager();

        // 获取手机安装的所有app
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        AppModel model;
        try {
            // 将获取到的app信息转换成自定义集合类型
            for (PackageInfo info : packageInfos) {
                // app 图标
                Drawable icon = packageManager.getApplicationIcon(info.packageName);
                // app 名称
                String name = packageManager.getApplicationLabel(info.applicationInfo).toString();

                model = new AppModel(icon, name, info.packageName,
                        info.versionName, info.versionCode);

                appModels.add(model);

                Log.e(TAG, "onCreate: " + model);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // 获取布局文件中的 RecyclerView 对象
        RecyclerView container = mBinding.rvContainer;
        container.addItemDecoration(new DividerItemDecoration(this, OrientationHelper.VERTICAL));
        // 具有固定大小
        container.setHasFixedSize(true);
        // 设置布局管理器
        container.setLayoutManager(new LinearLayoutManager(this));
        // 设置适配器
        container.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends RecyclerView.Adapter<ViewHolder> {

        /**
         * 构建ViewHolder对象
         *
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.
                    from(parent.getContext()).inflate(R.layout.layout_item, null));
        }

        /**
         * 绑定显示的数据
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(appModels.get(position));
        }

        /**
         * 返回数据源数量
         *
         * @return
         */
        @Override
        public int getItemCount() {
            return appModels.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private final LayoutItemBinding mBinding;


        ViewHolder(View itemView) {
            super(itemView);
            // 将 布局文件对象与DataBinding 进行关联
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.setActionHandler(new ActionHandler());
        }

        /**
         * 设置每一行显示的数据源
         *
         * @param model
         */
        void bind(AppModel model) {
            mBinding.setApp(model);
        }

    }

    public class ActionHandler {

        public void onClick(AppModel model) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        }

        public boolean onLongClick(AppModel model) {
            Toast.makeText(MainActivity.this, "long click " + model.getPkg(),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    public class AppModel {

        private Drawable icon;

        private String name;

        private String pkg;

        private String verName;

        private int verCode;

        public AppModel() {
        }

        AppModel(Drawable icon, String name, String pkg, String verName, int verCode) {
            this.icon = icon;
            this.name = name;
            this.pkg = pkg;
            this.verName = verName;
            this.verCode = verCode;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPkg() {
            return pkg;
        }

        public void setPkg(String pkg) {
            this.pkg = pkg;
        }

        public String getVerName() {
            return verName;
        }

        public void setVerName(String verName) {
            this.verName = verName;
        }

        public int getVerCode() {
            return verCode;
        }

        public void setVerCode(int verCode) {
            this.verCode = verCode;
        }
    }
}
