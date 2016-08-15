package com.example.luos.cst_project.View;

import android.content.Intent;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import com.example.luos.cst_project.Model.Config;
import com.example.luos.cst_project.Model.DataFrame;
import com.example.luos.cst_project.Model.User;
import com.example.luos.cst_project.Presenter.IFriendListPresenterCompl;
import com.example.luos.cst_project.R;

import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends BaseActivity implements IFriendListView {
    private FriendListFragment fragment;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        Intent i = getIntent();
        user = i.getParcelableExtra(LoginActivity.USERDATA);

        FragmentManager fm = getSupportFragmentManager();
        fragment = new FriendListFragment();
        fm.beginTransaction()
                .add(R.id.activity_friend_list,fragment)
                .commit();
        IFriendListPresenterCompl compl = new IFriendListPresenterCompl(this);
    }

    @Override
    public void processMessage(Message msg) {
        Log.d("Test_processMessage","FriendsList procemessage()..."+msg.what);
        switch (msg.what){
            case Config.SEND_NOTIFICATION:
                Bundle bundle = msg.getData();
                ArrayList data = bundle.getParcelableArrayList("msgList");
                List<DataFrame.PersonalMsg> msgList = (List<DataFrame.PersonalMsg>) data.get(0);
                Log.d("Test_msgList",msgList.toString());
                saveMessageToDb(msgList);
                sendNotifycation(msgList);
                break;
            default:
                break;

        }
    }

    @Override
    public ArrayList<DataFrame.User> getFriendList() {
        return friends;
    }

    @Override
    public void setFriendList(ArrayList<DataFrame.User> friends) {
        this.friends = friends;
    }
}
