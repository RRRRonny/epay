package com.chao117.dao.impl;

import com.chao117.dao.BaseDAO;
import com.chao117.model.Location;
import com.chao117.model.ServerResult;
import com.chao117.model.ShipAddress;
import com.chao117.model.User;
import com.chao117.service.QueryLocationHelper;
import com.chao117.service.QueryShipAddressHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chan on 21/04/2017.
 */
public class RequestShipAddressImpl implements BaseDAO<User> {
    private int errorCode;
    private String result;
    private User user;

    private ServerResult<ShipAddress> serverResult;
    private List<ShipAddress> shipAddressList;

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean init(User object) {
        updateErrorCode(ERROR_NON);
        result = RESULT_FAILURE;
        this.user = object;
        return false;
    }

    @Override
    public boolean doOperate() {
        QueryShipAddressHelper helper = new QueryShipAddressHelper();
        ResultSet set = helper.queryShipAddress(user);
        if (set == null) {
            updateErrorCode(ERROR_REFUSE);
        }
        try {
            shipAddressList = new ArrayList<>();
            while (set.next()) {
                ShipAddress shipAddress = new ShipAddress();
                shipAddress.setId(set.getInt(set.findColumn(ID)));
                //todo 联合查询
                //查询 location
                Location location = new QueryLocationHelper().queryLocation(set.getInt(set.findColumn(L_ID)));
                if (location == null) {
                    updateErrorCode(ERROR_REFUSE);
                    continue;
                }
                shipAddress.setLocation(location);
                shipAddress.setDetail(set.getString(set.findColumn(SA_DETAIL)));
                shipAddress.setRemark(set.getString(set.findColumn(SA_REMARK)));
                shipAddress.setTimestamp(set.getLong(set.findColumn(SA_TIMESTAMP)));
                shipAddressList.add(shipAddress);
            }
        } catch (SQLException e) {
            updateErrorCode(ERROR_REFUSE);
            e.printStackTrace();
        } finally {
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
        serverResult.setErrorCode(errorCode);
        serverResult.setObjects(shipAddressList);
        return serverResult;
    }
}
