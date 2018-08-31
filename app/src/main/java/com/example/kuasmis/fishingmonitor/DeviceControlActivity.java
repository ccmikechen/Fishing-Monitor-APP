/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.kuasmis.fishingmonitor;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import data.DataGetterManager;
import data.MiDataGetter;
import data.MiDataRecorder;

/**
 * For a given BLE device, this Activity provides the user interface to connect, display data,
 * and display GATT services and characteristics supported by the device.  The Activity
 * communicates with {@code BluetoothLeService}, which in turn interacts with the
 * Bluetooth LE API.
 */
public class DeviceControlActivity extends Activity {
    private final static String TAG = DeviceControlActivity.class.getSimpleName();
    
    public static final String DEVICE_CHARACTERISTIC = "DEVICE_CHARACTERISTIC";
    public static final String DEVICE_NAME = "DEVICE_NAME";
    public static final String DEVICE_PROPERTIES = "DEVICE_PROPERTIES";
    public static final String DEVICE_SERVICE = "DEVICE_SERVICE";
    public static final String DEVICE_UUID = "DEVICE_UUID";
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    private TextView mConnectionState;
    private TextView mDataField;
    private TextView mCharacteristicUUID;
    private EditText mWriteData;
    private Button mButtonWrite;
    private Button mButtonNotify;
    private Button mInitBtn;
    private String mDeviceName;
    private String mDeviceAddress;
    private ExpandableListView mGattServicesList;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private BluetoothGattCharacteristic characteristic;
    private BluetoothGattCharacteristic characteristic3;
    private BluetoothGattCharacteristic characteristic4;
    private BluetoothGattCharacteristic characteristic5;
    private BluetoothGattCharacteristic characteristic6;
    private BluetoothGattCharacteristic characteristic7;
    private BluetoothGattCharacteristic characteristica;
    private BluetoothGattCharacteristic characteristicc;
    private BluetoothGattCharacteristic characteristice;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";
    private boolean mNotify = false;
    private boolean canInit = false;

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);

            MiDataGetter dataGetter = new MiDataGetter();
            mBluetoothLeService.addDataGetter(dataGetter);
            DataGetterManager.setMiDataGetter(dataGetter);

            MiDataRecorder dataRecorder = new MiDataRecorder();
            mBluetoothLeService.addDataGetter(dataRecorder);
            DataGetterManager.setMiDataRecorder(dataRecorder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA), intent.getStringExtra(BluetoothLeService.CHARACTERISTIC_UUID));
            }
        }
    };

    // If a given GATT characteristic is selected, check for supported features.  This sample
    // demonstrates 'Read' and 'Notify' features.  See
    // http://d.android.com/reference/android/bluetooth/BluetoothGatt.html for the complete
    // list of supported characteristic features.
    private final ExpandableListView.OnChildClickListener servicesListClickListner =
            new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                            int childPosition, long id) {
                    if (mGattCharacteristics != null) {
                        characteristic = mGattCharacteristics.get(groupPosition).get(childPosition);
                        final int charaProp = characteristic.getProperties();
                        if ((charaProp & BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                            // If there is an active notification on a characteristic, clear
                            // it first so it doesn't update the data field on the user interface.
                            if (mNotifyCharacteristic != null) {
                                mBluetoothLeService.setCharacteristicNotification(
                                        mNotifyCharacteristic, false);
                                mNotifyCharacteristic = null;
                            }
                            mBluetoothLeService.readCharacteristic(characteristic);
                        }
                        /*
                        if ((charaProp & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                            mNotifyCharacteristic = characteristic;
                            mBluetoothLeService.setCharacteristicNotification(
                                    characteristic, true);
                        }
                        */
                        return true;
                    }
                    return false;
                }
    };

    private void clearUI() {
        mGattServicesList.setAdapter((SimpleExpandableListAdapter) null);
        mDataField.setText(R.string.no_data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gatt_services_characteristics);

        final Intent intent = getIntent();
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);

        // Sets up UI references.
        ((TextView) findViewById(R.id.device_address)).setText(mDeviceAddress);
        mGattServicesList = (ExpandableListView) findViewById(R.id.gatt_services_list);
        mGattServicesList.setOnChildClickListener(servicesListClickListner);
        mConnectionState = (TextView) findViewById(R.id.connection_state);
        mDataField = (TextView) findViewById(R.id.data_value);
        mCharacteristicUUID = (TextView) findViewById(R.id.characteristic_uuid);

        final Button chartButton = (Button) findViewById(R.id.chart_button);
        mButtonNotify = (Button) findViewById(R.id.button_notify);
        mButtonNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canInit)
                    DeviceControlActivity.this.start(0x49);
                if (!canInit)
                    return;
                if(!mNotify) {
                    try {
                        byte value[] = {(byte)0x12, (byte)0x01};
                        mBluetoothLeService.setCharacteristicValue(characteristic5, value);
                        mNotify = true;
                        mButtonNotify.setText("Stop Notify");
                        chartButton.setEnabled(true);
                        return;
                    } catch(Exception e) {
                        Log.e("DEBUG BLE", "setCharacteristicValue fail");
                    }
                }
                byte value[] = {(byte)0x12, (byte)0x00};
                mBluetoothLeService.setCharacteristicValue(characteristic5, value);
                mNotify = false;
                mButtonNotify.setText("Notify");
                chartButton.setEnabled(false);
            };
        });


        chartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceControlActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        getActionBar().setTitle(mDeviceName);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
            mButtonNotify.setEnabled(true);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
            mButtonNotify.setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_connect:
                mBluetoothLeService.connect(mDeviceAddress);
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void start(int id) {
        try {
            mBluetoothLeService.setCharacteristicNotification(characteristic3, true);
            Thread.sleep(100L);
            mBluetoothLeService.setCharacteristicNotification(characteristic6, true);
            Thread.sleep(100L);
            mBluetoothLeService.setCharacteristicNotification(characteristic7, true);
            Thread.sleep(100L);
            mBluetoothLeService.setCharacteristicNotification(characteristicc, true);
            Thread.sleep(100L);
            mBluetoothLeService.setCharacteristicNotification(characteristice, true);
            Thread.sleep(300L);
            byte[] e = new byte[]{(byte) -116, (byte) -24, (byte) 95, (byte) 19, (byte) 1, (byte) 23, (byte) -82, (byte) 64, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) id};
            mBluetoothLeService.setCharacteristicValue(characteristic4, e);
            Thread.sleep(200L);
            byte[] value2 = new byte[]{(byte) 14, (byte) 10, (byte) 11, (byte) 21, (byte) 54, (byte) 16, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1};
            mBluetoothLeService.setCharacteristicValue(characteristica, value2);
            Thread.sleep(100L);
            byte[] value3 = new byte[]{(byte) 6};
            mBluetoothLeService.setCharacteristicValue(characteristic5, value3);
            Thread.sleep(100L);
            byte[] value4 = new byte[]{(byte) 3, (byte) 1};
            mBluetoothLeService.setCharacteristicValue(characteristic5, value4);
            Thread.sleep(100L);
            byte[] value5 = new byte[]{(byte) 10, (byte) 14, (byte) 10, (byte) 11, (byte) 21, (byte) 49, (byte) 30, (byte) 5, (byte) 0};
            mBluetoothLeService.setCharacteristicValue(characteristic5, value5);
            Thread.sleep(100L);
            byte[] value6 = new byte[]{(byte) 10, (byte) 14, (byte) 10, (byte) 11, (byte) 21, (byte) 54, (byte) 30, (byte) 0, (byte) 0};
            mBluetoothLeService.setCharacteristicValue(characteristic5, value6);
            Thread.sleep(100L);
            byte[] value7 = new byte[]{(byte) 3, (byte) 0};
            mBluetoothLeService.setCharacteristicValue(characteristic5, value7);
            Thread.sleep(100L);
            byte[] value8 = new byte[]{(byte) 18, (byte) 1};
            mBluetoothLeService.setCharacteristicValue(characteristic5, value8);
            Thread.sleep(100L);
        } catch(Exception e) {
            Log.e("", "");
        }
    }

    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionState.setText(resourceId);
            }
        });
    }

    private void displayData(String data, String uuid) {
        if (data != null) {
            mDataField.setText(data);
            mCharacteristicUUID.setText(uuid);
        }
    }

    // Demonstrates how to iterate through the supported GATT Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the ExpandableListView
    // on the UI.
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().getString(R.string.unknown_service);
        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                if(uuid.contains("0000ff03-0000-1000-8000-00805f9b34fb")) {
                	characteristic3 = gattCharacteristic;
                } else if(uuid.contains("0000ff04-0000-1000-8000-00805f9b34fb")) {
                	characteristic4 = gattCharacteristic;
                } else if(uuid.contains("0000ff05-0000-1000-8000-00805f9b34fb")) {
                	characteristic5 = gattCharacteristic;
                } else if(uuid.contains("0000ff06-0000-1000-8000-00805f9b34fb")) {
                	characteristic6 = gattCharacteristic;
                } else if(uuid.contains("0000ff07-0000-1000-8000-00805f9b34fb")) {
                	characteristic7 = gattCharacteristic;
                } else if(uuid.contains("0000ff0a-0000-1000-8000-00805f9b34fb")) {
                	characteristica = gattCharacteristic;
                } else if(uuid.contains("0000ff0c-0000-1000-8000-00805f9b34fb")) {
                	characteristicc = gattCharacteristic;
                } else if(uuid.contains("0000ff0e-0000-1000-8000-00805f9b34fb")) {
                	characteristice = gattCharacteristic;
                	canInit = true;
                }
                //Log.e("---Ch---", uuid);
                currentCharaData.put(LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }

        SimpleExpandableListAdapter gattServiceAdapter = new SimpleExpandableListAdapter(
                this,
                gattServiceData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {LIST_NAME, LIST_UUID},
                new int[] { android.R.id.text1, android.R.id.text2 },
                gattCharacteristicData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {LIST_NAME, LIST_UUID},
                new int[] { android.R.id.text1, android.R.id.text2 }
        );
        mGattServicesList.setAdapter(gattServiceAdapter);
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }
}
