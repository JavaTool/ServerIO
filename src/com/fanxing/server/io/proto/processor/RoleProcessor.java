package com.fanxing.server.io.proto.processor;

import com.fanxing.server.io.proto.request.RoleInfoRequest;
import com.fanxing.server.io.proto.response.RoleInfoResponse;
import com.fanxing.server.io.proto.request.AccountGetRoleListRequest;
import com.fanxing.server.io.proto.response.AccountGetRoleListResponse;
import com.fanxing.server.io.proto.request.CreateRoleRequest;
import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.request.NewRoleInfoRequest;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public interface RoleProcessor {

	void processRoleInfo(RoleInfoRequest request, RoleInfoResponse response);

	void processAccountGetRoleList(AccountGetRoleListRequest request, AccountGetRoleListResponse response);

	void processCreateRole(CreateRoleRequest request, Response response);

	void processNewRoleInfo(NewRoleInfoRequest request, Response response);

}
