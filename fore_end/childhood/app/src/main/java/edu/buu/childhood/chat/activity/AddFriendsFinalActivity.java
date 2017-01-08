package edu.buu.childhood.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;

import edu.buu.childhood.R;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.SmackManager;

/**
 * Created by Administrator on 2016/9/29.
 */
public class AddFriendsFinalActivity extends BaseAvtivity {
    private String toUser;
    private TextView sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.activity_addfriends_final);
        sendBtn = (TextView) findViewById(R.id.tv_send);
        Intent intent = getIntent();
        if (intent != null) {
            toUser = intent.getStringExtra("fromUser");
        }
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Roster roster = SmackManager.getInstance().getRoster();
                String serviceName = SmackManager.getInstance().getServiceName();
                try {
                    roster.createEntry(toUser + "@" + serviceName, toUser, new String[]{"Friends"});
                } catch (SmackException.NotLoggedInException e) {
                    e.printStackTrace();
                } catch (SmackException.NoResponseException e) {
                    e.printStackTrace();
                } catch (XMPPException.XMPPErrorException e) {
                    e.printStackTrace();
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
}
