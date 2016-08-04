package com.rich.father.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.easemob.redpacketui.RedPacketConstant;
import com.easemob.redpacketui.utils.RedPacketUtil;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.rich.father.R;
import com.rich.father.adapter.ProductAdapter;
import com.rich.father.app.App;
import com.rich.father.models.Product;
import com.rich.father.models.Products;
import com.rich.father.utils.HttpAsyncTask;
import com.rich.father.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.Set;

public class FirstFragment extends EaseChatFragment implements HttpAsyncTask.IHttpAsyncTask{

    public static final String TAG = FirstFragment.class.getName();
    private static final int REQUIRE_TYPE_NORMAL = 1;
    private static final int REQUIRE_TYPE_BUY = 2;//购买
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //red packet code : 红包功能使用的常量
    private static final int MESSAGE_TYPE_RECV_RED_PACKET = 5;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET = 6;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET_ACK = 7;
    private static final int MESSAGE_TYPE_RECV_RED_PACKET_ACK = 8;
    private static final int REQUEST_CODE_SEND_RED_PACKET = 16;
    private static final int ITEM_RED_PACKET = 16;
    //end of red packet code

    private ListView listViewProduct;
    private ProductAdapter productAdapter;

    private FragmentActivity activity;
    private int getRequireType;
    private String mParam1;
    private String mParam2;

    private Products products;
    private ArrayList<Product> productArrayList;

    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        HttpAsyncTask.getInstance(this, REQUIRE_TYPE_NORMAL);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first, container, false);
        listViewProduct = (ListView)v.findViewById(R.id.list_product);
        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HttpAsyncTask.getInstance(FirstFragment.this, REQUIRE_TYPE_BUY);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case REQUEST_CODE_SEND_RED_PACKET:
                    if (data != null){
                        Bundle bundle = data.getExtras();
                        Set<String> keySet = bundle.keySet();//获取所有的Key
                        for(String key : keySet){//bundle.get(key);来获取对应的value
                            App.log("xu", "key---->"+key+"-----value------>"+bundle.getString(key));
                        }

                        String greetings = data.getStringExtra(RedPacketConstant.EXTRA_RED_PACKET_GREETING);
                        String red_packet_id = data.getStringExtra(RedPacketConstant.EXTRA_RED_PACKET_ID);
                        String rp_receive_id = data.getStringExtra(RedPacketConstant.EXTRA_RED_PACKET_RECEIVER_ID);
                        String rp_type = data.getStringExtra(RedPacketConstant.EXTRA_RED_PACKET_TYPE);
                        String refresh_group_rp = data.getStringExtra(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION);
                        String sender_id = data.getStringExtra(RedPacketConstant.EXTRA_RED_PACKET_SENDER_ID);
                        String is_open_rp_msg = data.getStringExtra(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE);
                        String is_rp_msg = data.getStringExtra(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE);
                        String sender_name = data.getStringExtra(RedPacketConstant.EXTRA_RED_PACKET_SENDER_NAME);
                        String receiver_name = data.getStringExtra(RedPacketConstant.EXTRA_RED_PACKET_RECEIVER_NAME);
                        String sponsor_name = data.getStringExtra(RedPacketConstant.EXTRA_SPONSOR_NAME);
                        String rp_group_id = data.getStringExtra(RedPacketConstant.EXTRA_RED_PACKET_GROUP_ID);
                        String msg_rp_type = data.getStringExtra(RedPacketConstant.MESSAGE_ATTR_RED_PACKET_TYPE);
                        String group_exclusive_rp = data.getStringExtra(RedPacketConstant.GROUP_RED_PACKET_TYPE_EXCLUSIVE);
                        String special_rp_receiver_id = data.getStringExtra(RedPacketConstant.MESSAGE_ATTR_SPECIAL_RECEIVER_ID);

                        App.saveData2SP(activity, App.SP_PACKAGE_REDPACKET, App.SP_KEY_RED_PACKET_ID, red_packet_id);
                        App.saveData2SP(activity, App.SP_PACKAGE_REDPACKET, App.SP_KEY_RED_PACKET_RECEIVED_ID, rp_receive_id);
                        //App.log("xu", "greetings---->"+greetings+",  red_packet_id---->"+red_packet_id+",  rp_receive_id---->"+rp_receive_id+",  rp_type---->"+rp_type);
                        //App.log("xu", "refresh_group_rp---->"+refresh_group_rp+",  sender_id---->"+sender_id+",  is_open_rp_msg---->"+is_open_rp_msg+",  is_rp_msg---->"+is_rp_msg);
                        //App.log("xu", "sender_name---->"+sender_name+",  receiver_name---->"+receiver_name+",  sponsor_name---->"+sponsor_name+",  rp_group_id---->"+rp_group_id);
                        //App.log("xu", "msg_rp_type---->"+msg_rp_type+",  group_exclusive_rp---->"+group_exclusive_rp+",  special_rp_receiver_id---->"+special_rp_receiver_id);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onPreExecute() {
        LoadingDialog.showDialog(activity);
    }

    @Override
    public Object doInBackground(Object... params) {
        getRequireType = (Integer)params[0];
        switch (getRequireType){
            case REQUIRE_TYPE_NORMAL:
                products = App.getProducts(activity, App.GET_PRODUCTS);
                break;
            case REQUIRE_TYPE_BUY:
                String phone = App.getData4SP(activity, App.SP_PACKAGE_USER, App.SP_KEY_PHONE);
                RedPacketUtil.startRedPacketActivityForResult(FirstFragment.this, EaseConstant.CHATTYPE_SINGLE, "18775769566", REQUEST_CODE_SEND_RED_PACKET);
                break;
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public void onPostExecute(Object result) {
        LoadingDialog.dismissDialog();
        switch (getRequireType){
            case REQUIRE_TYPE_NORMAL:
                if(products != null){
                    productArrayList = products.getData();
                    productAdapter = new ProductAdapter(activity, productArrayList);
                    listViewProduct.setAdapter(productAdapter);
                }
                break;
            case REQUIRE_TYPE_BUY:
                break;
        }
    }
}
