package com.agora.example.longjiao.agoraexample.Model;

/**
 * Created by apple on 15/9/24.
 */
public class Record {

    private String callId;
    private String recordValue;

    public Record(String callId, String recordValue){

        this.callId=callId;
        this.recordValue=recordValue;
    }

    public String getCallId(){
        return callId;
    }

    public String getRecordValue(){
        return recordValue;
    }
}
