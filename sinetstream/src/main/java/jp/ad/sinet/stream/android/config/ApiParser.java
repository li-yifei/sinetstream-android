/*
 * Copyright (C) 2020 National Institute of Informatics
 *
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package jp.ad.sinet.stream.android.config;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

import jp.ad.sinet.stream.android.api.InvalidConfigurationException;
import jp.ad.sinet.stream.android.api.ValueType;

public class ApiParser extends BaseParser {

    /* Entry point */
    public void parse(@NonNull Map<String,Object> myParams)
            throws InvalidConfigurationException {
        //parseTopic(myParams);
        parseTopics(myParams);

        /*
         * Workaround for weird crash after unsubscribe() & disconnect().
         * https://github.com/eclipse/paho.mqtt.android/issues/238
         *
         * Here we ignore user-specified clientId, and let the
         * library MqttAsyncClient generate a random one instead.
         */
        //parseClientId(myParams);

        parseConsistency(myParams);
        parseValueType(myParams);
        parseDataEncryption(myParams);
    }

    private String mTopic = null;
    private void parseTopic(@NonNull Map<String,Object> myParams)
            throws InvalidConfigurationException {
        String key = "topic"; /* Mandatory */
        mTopic = super.parseString(myParams, key, true);
    }

    @Nullable
    public final String getTopic() {
        return mTopic;
    }

    private String[] mTopics = null;
    private void parseTopics(@NonNull Map<String,Object> myParams)
            throws InvalidConfigurationException {
        String key = "topics"; /* Mandatory */
        mTopics = super.parseStringList(myParams, key, true);
    }

    @Nullable
    public final String[] getTopics() {
        return mTopics;
    }

    private String mClientId = null;
    private void parseClientId(@NonNull Map<String,Object> myParams)
            throws InvalidConfigurationException {
        String key = "client_id"; /* Optional */
        mClientId = super.parseAlphaNumeric(myParams, key, false);
    }

    @Nullable
    public final String getClientId() {
        return mClientId;
    }

    //private int mConsistency = (Consistency.AT_LEAST_ONCE).getQos();
    private Integer mConsistency = null;
    private void parseConsistency(@NonNull Map<String,Object> myParams)
            throws InvalidConfigurationException {
        String key = "consistency"; /* Optional */
        mConsistency = super.parseConsistency(myParams, key, false);
    }

    @Nullable
    public Integer getConsistency() {
        return mConsistency;
    }

    //private ValueType mValueType = ValueType.BYTE_ARRAY;
    private ValueType mValueType = null;
    private void parseValueType(@NonNull Map<String,Object> myParams)
            throws InvalidConfigurationException {
        String key = "value_type"; /* Optional */
        mValueType = super.parseValueType(myParams, key, false);
    }

    @Nullable
    public final ValueType getValueType() {
        return mValueType;
    }

    private Boolean mDataEncryption = null;
    private void parseDataEncryption(@NonNull Map<String,Object> myParams)
            throws InvalidConfigurationException {
        String key = "data_encryption"; /* Optional */
        mDataEncryption = super.parseBoolean(myParams, key, false);
    }

    @Nullable
    public final Boolean getDataEncryption() {
        return mDataEncryption;
    }
}
