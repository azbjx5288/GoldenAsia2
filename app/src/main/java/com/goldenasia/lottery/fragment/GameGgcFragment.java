package com.goldenasia.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.GgcMethodListCommand;
import com.goldenasia.lottery.data.GgcPackageInfo;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.ScrapeType;
import com.goldenasia.lottery.data.ScrapeTypeEntity;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.pattern.GgcMethodPopupWindow;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 刮刮彩卡包列表
 * Created by Sakura.
 */
public class GameGgcFragment extends BaseFragment {
    private static final String TAG = GameGgcFragment.class.getSimpleName();

    private static final int ID_METHOD_LIST = 1;

    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @BindView(R.id.package_list_view)
    GridView packageListView;
    @BindView(R.id.my_package)
    ImageView myPackage;
    @BindView(R.id.title_text_layout)
    LinearLayout titleTextLayout;
    @BindView(R.id.title)
    TextView title;

    private Lottery lottery;
    private int scType;
    private PackageAdapter packageAdapter;
    private ArrayList<String> methodList;
    private ArrayList<GgcPackageInfo> ggcPackageEntities;
    private ArrayList<ScrapeType> scrapeTypes;

    private GgcMethodPopupWindow popupWindow;

    public static void launch(BaseFragment fragment, Lottery lottery) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("lottery", lottery);
        FragmentLauncher.launch(fragment.getActivity(), GameGgcFragment.class, bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.game_ggc_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();
        loadList();
    }

    private void applyArguments() {
        lottery = (Lottery) getArguments().getSerializable("lottery");
        scType = 1;
        ggcPackageEntities = new ArrayList<>();
        packageAdapter = new PackageAdapter();
        packageListView.setAdapter(packageAdapter);
    }

    private void initMethodList() {
        methodList = new ArrayList<>();

        for (ScrapeType scrapeType : scrapeTypes) {
            methodList.add(scrapeType.getType_name());
        }
        popupWindow = new GgcMethodPopupWindow(getActivity(), methodList);
        popupWindow.setOnChooseListner(new GgcMethodPopupWindow.OnChooseListner() {
            @Override
            public void onChoose(int i) {
                if (scType != i + 1) {
                    scType = i + 1;
                    title.setText(scrapeTypes.get(i).getType_name());
                    loadList();
                }
            }
        });
    }

    private void loadList() {
        GgcMethodListCommand ggcMethodListCommand = new GgcMethodListCommand();
        ggcMethodListCommand.setScrape_type(scType);
        TypeToken typeToken = new TypeToken<RestResponse<ScrapeTypeEntity>>() {
        };
        RestRequestManager.executeCommand(getActivity(), ggcMethodListCommand, typeToken, restCallback,
                ID_METHOD_LIST, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(android.R.id.home)
    public void finishFragment() {
        getActivity().finish();
    }

    @OnClick(R.id.help)
    public void showHelp() {
        //TODO:点击“帮助”按钮，显示帮助信息
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case ConstantInformation.REFRESH_RESULT:
                loadList();
                break;
            case ConstantInformation.LAUNCH_RESULT:
                openPackage();
                break;
        }
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            ScrapeTypeEntity scrapeTypeEntity = (ScrapeTypeEntity) response.getData();
            if (methodList == null) {
                scrapeTypes = scrapeTypeEntity.getScrapeTypeList();
                title.setText(scrapeTypes.get(0).getType_name());
                initMethodList();
                ggcPackageEntities = scrapeTypeEntity.getGgcPackageEntities();
                packageAdapter.notifyDataSetChanged();
                packageListView.setVisibility(View.VISIBLE);
                loadingLayout.setVisibility(View.GONE);
            }

            ggcPackageEntities = scrapeTypeEntity.getGgcPackageEntities();
            packageAdapter.notifyDataSetChanged();

            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 7003) {
                Toast.makeText(getActivity(), "正在更新服务器请稍等", Toast.LENGTH_LONG).show();
                return true;
            } else if (errCode == 7006) {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };

    @OnClick(R.id.my_package)
    public void openPackage() {
        launchFragment(GgcMyPackageFragment.class);
    }

    @OnClick(R.id.title_text_layout)
    public void onClick(View view) {
        if (popupWindow != null) {
            popupWindow.showAsDropDown(view);
        }
    }

    private class PackageAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return ggcPackageEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ggc_package, parent,
                        false);
                viewHolder = new ViewHolder(convertView);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (scType == 1)
                viewHolder.cardImage.setImageResource(R.drawable.guaguakalb);
                //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.guaguakalb, viewHolder.cardImage);
            else
                viewHolder.cardImage.setImageResource(R.drawable.zuqiuguaguakalb);
                /*ImageLoader.getInstance().displayImage("drawable://" + R.drawable.zhuqiuguaguakalb, viewHolder
                        .cardImage);*/
            viewHolder.cardAmount.setText("剩余" + ggcPackageEntities.get(position).getTotal_unsale_num() + "张");

            return convertView;
        }
    }

    static class ViewHolder {
        @BindView(R.id.card_image)
        ImageView cardImage;
        @BindView(R.id.card_amount)
        TextView cardAmount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    @OnItemClick(R.id.package_list_view)
    public void selectPackage(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("lottery", lottery);
        bundle.putSerializable("packageId", ggcPackageEntities.get(position).getScb_id());
        bundle.putSerializable("scrapeType", ggcPackageEntities.get(position).getScrape_type());
        launchFragmentForResult(ShoppingGgcFragment.class, bundle, 1);
    }
}
