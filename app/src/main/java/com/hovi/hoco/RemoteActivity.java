package com.hovi.hoco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hovi.hoco.adapter.ListRoomButtonAdapter;
import com.hovi.hoco.hocoview.HocoButton;
import com.hovi.hoco.hocoview.HocoLightController;
import com.hovi.hoco.model.Device;
import com.hovi.hoco.model.Light;
import com.hovi.hoco.model.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RemoteActivity extends AppCompatActivity {


    private int floor = 1;

    private TextView txtFloor1;
    private TextView txtFloor2;

    private HocoButton btnLight;
    private HocoButton btnFan;
    private HocoButton btnAirCondition;
    private HocoButton btnPowerSocket;

    private HocoLightController lightController;

    private ListView listView;

    private ArrayList<Room> listRoom;
    private ListRoomButtonAdapter listRoomButtonAdapter;

    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        txtFloor1 = findViewById(R.id.remote_f1_name);
        txtFloor2 = findViewById(R.id.remote_f2_name);

        btnLight = findViewById(R.id.remote_button_light);
        btnFan = findViewById(R.id.remote_button_fan);
        btnAirCondition = findViewById(R.id.remote_button_airconditioner);
        btnPowerSocket = findViewById(R.id.remote_button_powersocket);

        avatar = findViewById(R.id.remote_avatar);

        lightController = findViewById(R.id.remote_light_controller);
        listView = findViewById(R.id.remote_list_air);
        listView.setDivider(null);
        listView.setDividerHeight(0);

        listRoom = new ArrayList<>();
        setupUI();
        setupRoom();

        listView.setAdapter(listRoomButtonAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listRoomButtonAdapter.setChooseRoom(position);
                listRoomButtonAdapter.notifyDataSetChanged();
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RemoteActivity.this, SettingActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });
    }

    private void setupUI() {
        btnLight.enableView(true);

    }

    private void setupRoom(){
        listRoom.add(new Room("Phòng khách"));
        listRoom.add(new Room("Nhà vệ sinh"));
        listRoom.add(new Room("Phòng ngủ phụ"));
        listRoom.add(new Room("Bếp"));
        listRoomButtonAdapter = new ListRoomButtonAdapter(this, listRoom);
    }


}