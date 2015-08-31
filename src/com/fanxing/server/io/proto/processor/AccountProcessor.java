package com.fanxing.server.io.proto.processor;

import com.fanxing.server.io.proto.request.AccountLoginRequest;
import com.fanxing.server.io.proto.response.AccountLoginResponse;
import com.fanxing.server.io.proto.request.AccountRegisterRequest;
import com.fanxing.server.io.proto.response.AccountRegisterResponse;
import com.fanxing.server.io.proto.request.DisconnectRequest;
import com.fanxing.server.io.proto.Response;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public interface AccountProcessor {

	void processAccountLogin(AccountLoginRequest request, AccountLoginResponse response);

	void processAccountRegister(AccountRegisterRequest request, AccountRegisterResponse response);

	void processDisconnect(DisconnectRequest request, Response response);

}
