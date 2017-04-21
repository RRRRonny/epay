package com.chao117.dao.impl;

import com.chao117.dao.BaseDAO;
import com.chao117.model.ServerResult;
import com.chao117.model.ShipAddress;
import com.chao117.service.InsertShipAddressHelper;

/**
 * Created by chan on 21/04/2017.
 */
public class AddShipAddImpl implements BaseDAO<ShipAddress> {
    private int errorCode;
    private String result;
    private ShipAddress shipAddress;

    private ServerResult<ShipAddress> serverResult;

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean init(ShipAddress object) {
        errorCode = ERROR_NON;
        result = RESULT_FAILURE;
        this.shipAddress = object;
        return false;
    }

    @Override
    public boolean doOperate() {
        InsertShipAddressHelper helper = new InsertShipAddressHelper();
        int lines = helper.insertShipAddress(shipAddress);
        if (lines > 0) {
            result = RESULT_SUCCESS;
        } else {
            updateErrorCode(ERROR_REFUSE);
        }
        return false;
    }

    @Override
    public void updateErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public ServerResult getServerResult() {
        serverResult = new ServerResult<>();
        serverResult.setResult(result);
        serverResult.setErrorCode(errorCode);
        return null;
    }
}
