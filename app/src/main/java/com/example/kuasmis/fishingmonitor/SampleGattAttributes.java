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

import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

    static {
        // Sample Services.
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
        
        attributes.put("00002a00-0000-1000-8000-00805f9b34fb", "Device Name");
        attributes.put("00002a01-0000-1000-8000-00805f9b34fb", "Appearance");
        attributes.put("00002a02-0000-1000-8000-00805f9b34fb", "Peripheral Privacy Flag");
        attributes.put("00002a03-0000-1000-8000-00805f9b34fb", "Reconnection Address");
        attributes.put("00002a04-0000-1000-8000-00805f9b34fb", "Peripheral Preferred Connection Parameters");
        attributes.put("00002a05-0000-1000-8000-00805f9b34fb", "Service Changed");
        attributes.put("00002a23-0000-1000-8000-00805f9b34fb", "System ID");
        attributes.put("00002a24-0000-1000-8000-00805f9b34fb", "Model Number String");
        attributes.put("00002a25-0000-1000-8000-00805f9b34fb", "Serial Number String");
        attributes.put("00002a26-0000-1000-8000-00805f9b34fb", "Firmware Revision String");
        attributes.put("00002a27-0000-1000-8000-00805f9b34fb", "Hardware Revision String");
        attributes.put("00002a28-0000-1000-8000-00805f9b34fb", "Software Revision String");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
        attributes.put("00002a2a-0000-1000-8000-00805f9b34fb", "IEEE 11073-20601 Regulatory Cerfification Data List");
        attributes.put("00002a50-0000-1000-8000-00805f9b34fb", "PnP ID");
        attributes.put("00002a06-0000-1000-8000-00805f9b34fb", "Alert Level");
        attributes.put("00002a07-0000-1000-8000-00805f9b34fb", "Tx Power Level");
        attributes.put("00002a19-0000-1000-8000-00805f9b34fb", "Bettery Level");
        attributes.put("00002a38-0000-1000-8000-00805f9b34fb", "Body Sensor Location");
        attributes.put("00002a37-0000-1000-8000-00805f9b34fb", "Heart Rate Measurement");
        attributes.put("00002a39-0000-1000-8000-00805f9b34fb", "Heart Rate Control Point");
        attributes.put("00002a40-0000-1000-8000-00805f9b34fb", "Ringer Control Point");
        attributes.put("00002a3f-0000-1000-8000-00805f9b34fb", "Alert Status");
        attributes.put("00002a41-0000-1000-8000-00805f9b34fb", "Ringer Setting");
        attributes.put("00002a35-0000-1000-8000-00805f9b34fb", "Blood Pressure Measurement");
        attributes.put("00002a49-0000-1000-8000-00805f9b34fb", "Blood Pressure Feature");
        attributes.put("00002a36-0000-1000-8000-00805f9b34fb", "Intermediate Cuff Pressure");
        attributes.put("00002a43-0000-1000-8000-00805f9b34fb", "Alert Category ID");
        attributes.put("00002a42-0000-1000-8000-00805f9b34fb", "Alert Category ID Bit Mask");
        attributes.put("00002a44-0000-1000-8000-00805f9b34fb", "Alert Notification Control Point");
        attributes.put("00002a3f-0000-1000-8000-00805f9b34fb", "Alert Status");
        attributes.put("00002a22-0000-1000-8000-00805f9b34fb", "Boot Keyboard Input Report");
        attributes.put("00002a32-0000-1000-8000-00805f9b34fb", "Boot Keyboard Output Report");
        attributes.put("00002a33-0000-1000-8000-00805f9b34fb", "Boot Mouse Input Report");
        attributes.put("00002a5c-0000-1000-8000-00805f9b34fb", "CSC Feature");
        attributes.put("00002a5b-0000-1000-8000-00805f9b34fb", "CSC Measurement");
        attributes.put("00002a2b-0000-1000-8000-00805f9b34fb", "Current Time");
        attributes.put("00002a66-0000-1000-8000-00805f9b34fb", "Cycling Power Control Point");
        attributes.put("00002a65-0000-1000-8000-00805f9b34fb", "Cycling Power Feature");
        attributes.put("00002a63-0000-1000-8000-00805f9b34fb", "Cycling Power Measurement");
        attributes.put("00002a64-0000-1000-8000-00805f9b34fb", "Cycling Power Vector");
        attributes.put("00002a08-0000-1000-8000-00805f9b34fb", "Date Time");
        attributes.put("00002a0a-0000-1000-8000-00805f9b34fb", "Day Date Time");
        attributes.put("00002a09-0000-1000-8000-00805f9b34fb", "Day of Week");
        attributes.put("00002a0d-0000-1000-8000-00805f9b34fb", "DST Offset");
        attributes.put("00002a0c-0000-1000-8000-00805f9b34fb", "Exact Time 256");
        attributes.put("00002a51-0000-1000-8000-00805f9b34fb", "Glucose Feature");
        attributes.put("00002a18-0000-1000-8000-00805f9b34fb", "Glucose Measurement");
        attributes.put("00002a34-0000-1000-8000-00805f9b34fb", "Glucose Measurement Context");
        attributes.put("00002a4c-0000-1000-8000-00805f9b34fb", "HID Control Point");
        attributes.put("00002a4a-0000-1000-8000-00805f9b34fb", "HID Information");
        attributes.put("00002a36-0000-1000-8000-00805f9b34fb", "Intermediate Cuff Pressure");
        attributes.put("00002a1e-0000-1000-8000-00805f9b34fb", "Intermediate Temperature");
        attributes.put("00002a6b-0000-1000-8000-00805f9b34fb", "LN Control Point");
        attributes.put("00002a6a-0000-1000-8000-00805f9b34fb", "LN Feature");
        attributes.put("00002a0f-0000-1000-8000-00805f9b34fb", "Local Time Information");
        attributes.put("00002a67-0000-1000-8000-00805f9b34fb", "Location and Speed");
        attributes.put("00002a21-0000-1000-8000-00805f9b34fb", "Measurement Interval");
        attributes.put("00002a68-0000-1000-8000-00805f9b34fb", "Navigation");
        attributes.put("00002a46-0000-1000-8000-00805f9b34fb", "New Alert");
        attributes.put("00002a69-0000-1000-8000-00805f9b34fb", "Position Quality");
        attributes.put("00002a4e-0000-1000-8000-00805f9b34fb", "Protocol Mode");
        attributes.put("00002a52-0000-1000-8000-00805f9b34fb", "Record Access Control Point");
        attributes.put("00002a14-0000-1000-8000-00805f9b34fb", "Reference Time Information");
        attributes.put("00002a4d-0000-1000-8000-00805f9b34fb", "Report");
        attributes.put("00002a4b-0000-1000-8000-00805f9b34fb", "Report Map");
        attributes.put("00002a54-0000-1000-8000-00805f9b34fb", "RSC Feature");
        attributes.put("00002a53-0000-1000-8000-00805f9b34fb", "RSC Measurement");
        attributes.put("00002a55-0000-1000-8000-00805f9b34fb", "SC Control Point");
        attributes.put("00002a4f-0000-1000-8000-00805f9b34fb", "Scan Interval Window");
        attributes.put("00002a31-0000-1000-8000-00805f9b34fb", "Scan Refresh");
        attributes.put("00002a5d-0000-1000-8000-00805f9b34fb", "Sensor Location");
        attributes.put("00002a47-0000-1000-8000-00805f9b34fb", "Supported New Alert Category");
        attributes.put("00002a48-0000-1000-8000-00805f9b34fb", "Supported Unread Alert Category");
        attributes.put("00002a1c-0000-1000-8000-00805f9b34fb", "Temperature Measurement");
        attributes.put("00002a1d-0000-1000-8000-00805f9b34fb", "Temperature Type");
        attributes.put("00002a12-0000-1000-8000-00805f9b34fb", "Time Accuracy");
        attributes.put("00002a13-0000-1000-8000-00805f9b34fb", "Time Source");
        attributes.put("00002a16-0000-1000-8000-00805f9b34fb", "Time Update Control Point");
        attributes.put("00002a17-0000-1000-8000-00805f9b34fb", "Time Update State");
        attributes.put("00002a11-0000-1000-8000-00805f9b34fb", "Time with DST");
        attributes.put("00002a0e-0000-1000-8000-00805f9b34fb", "Time Zone");
        attributes.put("00002a45-0000-1000-8000-00805f9b34fb", "Unread Alert Status");
        attributes.put("0000fff1-0000-1000-8000-00805f9b34fb", "0xFFF1");
        attributes.put("0000fff2-0000-1000-8000-00805f9b34fb", "0xFFF2");
        attributes.put("0000fff3-0000-1000-8000-00805f9b34fb", "0xFFF3");
        attributes.put("0000fff4-0000-1000-8000-00805f9b34fb", "0xFFF4");
        attributes.put("0000fff5-0000-1000-8000-00805f9b34fb", "0xFFF5");
        
        // KT_Motion Service
        attributes.put("00001600-1212-efde-1600-785feabcd123", "KT_Motion Service");
        
        // KT_Motion Characteristics
        attributes.put("00001601-1212-efde-1600-785feabcd123", "Notification");
        attributes.put("00001602-1212-efde-1600-785feabcd123", "Savor");
        attributes.put("00001603-1212-efde-1600-785feabcd123", "Tx Rate");
        attributes.put("00001604-1212-efde-1600-785feabcd123", "Gyro fsr");
        attributes.put("00001605-1212-efde-1600-785feabcd123", "Accel fsr");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}
