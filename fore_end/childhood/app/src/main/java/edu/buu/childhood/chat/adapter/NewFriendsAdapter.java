package edu.buu.childhood.chat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.buu.childhood.R;
import edu.buu.childhood.Interface.BtnListener;
import edu.buu.childhood.chat.bean.InviteMessage;
import edu.buu.childhood.util.AsyncImageLoader;


/**
 * Created by Administrator on 2016/9/25.
 */
public class NewFriendsAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<InviteMessage> inviteMessageList;
    private AsyncImageLoader imageLoader;
    private BtnListener btnListener;

    public NewFriendsAdapter(Context context, List<InviteMessage> userDataList,BtnListener btnListener) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.inviteMessageList = userDataList;
        imageLoader = new AsyncImageLoader(context);
        this.btnListener=btnListener;
    }

    @Override
    public int getCount() {
        return inviteMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return inviteMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_newfriendsmsag, null);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_reason = (TextView) convertView.findViewById(R.id.tv_reason);
            holder.tv_added = (TextView) convertView.findViewById(R.id.tv_added);
            holder.btn_add = (Button) convertView.findViewById(R.id.btn_add);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final InviteMessage inviteMessage = inviteMessageList.get(position);
        holder.tv_name.setText(inviteMessage.getFromUserNick());
        holder.tv_reason.setText("请求加你为好友");
        String imageUrl = inviteMessage.getImageUrl();
        if (!TextUtils.isEmpty(imageUrl)) {
            holder.iv_avatar.setTag(imageUrl);
            Bitmap bitmap = imageLoader.loadImage(holder.iv_avatar, imageUrl);
            if (bitmap != null) {
                holder.iv_avatar.setImageBitmap(bitmap);
            }
        }
        if (inviteMessage.getStatus() == InviteMessage.InviteMesageStatus.AGREED
                || inviteMessage.getStatus() == InviteMessage.InviteMesageStatus.BEAGREED) {

            holder.tv_added.setVisibility(View.VISIBLE);
            holder.btn_add.setVisibility(View.GONE);
        } else {
            holder.tv_added.setVisibility(View.GONE);
            holder.btn_add.setVisibility(View.VISIBLE);
            holder.btn_add.setTag(inviteMessage);
            holder.btn_add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    btnListener.onClick(v,position);
                    //holder.tv_added.setVisibility(View.VISIBLE);
                    //holder.btn_add.setVisibility(View.GONE);
                   /* Log.i("userName",inviteMessage.getFrom());
                    Presence presenceRes = new Presence(Presence.Type.subscribed);
                    String serviceName=SmackManager.getInstance().getServiceName();
                    presenceRes.setTo(inviteMessage.getFrom()+"@"+serviceName);
                    try {
                        SmackManager.getInstance().getConnection().sendStanza(presenceRes);
                    } catch (SmackException.NotConnectedException e) {
                        e.printStackTrace();
                    }*/
                }

            });

        }
        //holder.tv_added.setText(inviteMessage.getStatus());
        return convertView;
    }

    public class ViewHolder {
        ImageView iv_avatar;
        TextView tv_name;
        TextView tv_reason;
        TextView tv_added;
        Button btn_add;
    }
}
