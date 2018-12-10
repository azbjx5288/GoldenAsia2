package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

@RequestConfig(api = "?c=game&a=deleteCollection",method = Request.Method.GET)
public class DeleteCollectCommand {
    private String deleteItems;

    public void setDeleteItems(String deleteItems) {
        this.deleteItems = deleteItems;
    }
}
