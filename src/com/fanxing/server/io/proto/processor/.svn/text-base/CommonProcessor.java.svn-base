package com.fanxing.server.io.proto.processor;

import com.fanxing.server.io.proto.request.GetServerListRequest;
import com.fanxing.server.io.proto.response.GetServerListResponse;
import com.fanxing.server.io.proto.request.ServerInfoRequest;
import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.request.ServerStatusRequest;
import com.fanxing.server.io.proto.request.CheckVersionRequest;
import com.fanxing.server.io.proto.response.CheckVersionResponse;
import com.fanxing.server.io.proto.request.SelectServerRequest;
import com.fanxing.server.io.proto.response.SelectServerResponse;
import com.fanxing.server.io.proto.request.ReconnectRequest;
import com.fanxing.server.io.proto.request.HttpCheckRequest;
import com.fanxing.server.io.proto.response.HttpCheckResponse;
import com.fanxing.server.io.proto.Request;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public interface CommonProcessor {

	void processGetServerList(GetServerListRequest request, GetServerListResponse response);

	void processServerInfo(ServerInfoRequest request, Response response);

	void processServerStatus(ServerStatusRequest request, Response response);

	void processCheckVersion(CheckVersionRequest request, CheckVersionResponse response);

	void processSelectServer(SelectServerRequest request, SelectServerResponse response);

	void processReconnect(ReconnectRequest request, Response response);

	void processHttpCheck(HttpCheckRequest request, HttpCheckResponse response);

	void processServerHeart(Request request, Response response);

}
