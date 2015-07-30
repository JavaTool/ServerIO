package com.fanxing.server.io.proto;

import com.fanxing.server.io.proto.processor.Processors;
import com.fanxing.server.io.proto.protocol.MessageIdProto.MessageId;
import javax.servlet.http.HttpSession;
import com.fanxing.server.io.proto.protocol.AccountProtos.CS_AccountRegister;
import com.fanxing.server.io.proto.response.AccountRegisterResponse;
import com.fanxing.server.io.proto.request.AccountRegisterRequest;
import com.fanxing.server.io.proto.processor.AccountProcessor;
import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.processor.CommonProcessor;
import com.fanxing.server.io.proto.protocol.RoleProtos.CS_CreateRole;
import com.fanxing.server.io.proto.request.CreateRoleRequest;
import com.fanxing.server.io.proto.processor.RoleProcessor;
import com.fanxing.server.io.proto.protocol.CommonProtos.CS_CheckVersion;
import com.fanxing.server.io.proto.response.CheckVersionResponse;
import com.fanxing.server.io.proto.request.CheckVersionRequest;
import com.fanxing.server.io.proto.protocol.CommonProtos.CS_SelectServer;
import com.fanxing.server.io.proto.request.SelectServerRequest;
import com.fanxing.server.io.proto.protocol.CommonProtos.CS_GetServerList;
import com.fanxing.server.io.proto.response.GetServerListResponse;
import com.fanxing.server.io.proto.request.GetServerListRequest;
import com.fanxing.server.io.proto.protocol.TestProtos.CS_Test1;
import com.fanxing.server.io.proto.response.Test1Response;
import com.fanxing.server.io.proto.request.Test1Request;
import com.fanxing.server.io.proto.processor.TestProcessor;
import com.fanxing.server.io.proto.protocol.AccountProtos.CS_Disconnect;
import com.fanxing.server.io.proto.request.DisconnectRequest;
import com.fanxing.server.io.proto.protocol.CommonProtos.CS_Reconnect;
import com.fanxing.server.io.proto.request.ReconnectRequest;
import com.fanxing.server.io.proto.protocol.CommonProtos.VO_ServerInfo;
import com.fanxing.server.io.proto.request.ServerInfoRequest;
import com.fanxing.server.io.proto.protocol.RoleProtos.CS_AccountGetRoleList;
import com.fanxing.server.io.proto.response.AccountGetRoleListResponse;
import com.fanxing.server.io.proto.request.AccountGetRoleListRequest;
import com.fanxing.server.io.proto.protocol.RoleProtos.CS_NewRoleInfo;
import com.fanxing.server.io.proto.request.NewRoleInfoRequest;
import com.fanxing.server.io.proto.protocol.RoleProtos.CS_RoleInfo;
import com.fanxing.server.io.proto.response.RoleInfoResponse;
import com.fanxing.server.io.proto.request.RoleInfoRequest;
import com.fanxing.server.io.proto.protocol.CommonProtos.CS_ServerStatus;
import com.fanxing.server.io.proto.request.ServerStatusRequest;
import com.fanxing.server.io.proto.protocol.AccountProtos.CS_AccountLogin;
import com.fanxing.server.io.proto.response.AccountLoginResponse;
import com.fanxing.server.io.proto.request.AccountLoginRequest;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class DefaultMessageHandle implements MessageHandle, Processors {

	private AccountProcessor accountProcessor;

	private CommonProcessor commonProcessor;

	private RoleProcessor roleProcessor;

	private TestProcessor testProcessor;

	@Override
	public Response handle(byte[] receiveDatas, String ip, String receiveMessageId, HttpSession session) throws Exception {
		Response response;
		switch (MessageId.valueOf(receiveMessageId)) {
		case MICS_AccountRegister : 
			if (getAccountProcessor() != null) {
				CS_AccountRegister cS_AccountRegister = CS_AccountRegister.parseFrom(receiveDatas);
				AccountRegisterRequest accountRegisterRequest = new AccountRegisterRequest(ip, receiveMessageId, session, cS_AccountRegister);
				AccountRegisterResponse resp = new AccountRegisterResponse(accountRegisterRequest);
				getAccountProcessor().processAccountRegister(accountRegisterRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		case MICS_ServerHeart : 
			if (getCommonProcessor() != null) {
				Request request = new Request(ip, receiveMessageId, session);
				Response resp = new Response(request);
				getCommonProcessor().processServerHeart(request, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		case MICS_CreateRole : 
			if (getRoleProcessor() != null) {
				CS_CreateRole cS_CreateRole = CS_CreateRole.parseFrom(receiveDatas);
				CreateRoleRequest createRoleRequest = new CreateRoleRequest(ip, receiveMessageId, session, cS_CreateRole);
				Response resp = new Response(createRoleRequest);
				getRoleProcessor().processCreateRole(createRoleRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		case MICS_CheckVersion : 
			if (getCommonProcessor() != null) {
				CS_CheckVersion cS_CheckVersion = CS_CheckVersion.parseFrom(receiveDatas);
				CheckVersionRequest checkVersionRequest = new CheckVersionRequest(ip, receiveMessageId, session, cS_CheckVersion);
				CheckVersionResponse resp = new CheckVersionResponse(checkVersionRequest);
				getCommonProcessor().processCheckVersion(checkVersionRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		case MICS_SelectServer : 
			if (getCommonProcessor() != null) {
				CS_SelectServer cS_SelectServer = CS_SelectServer.parseFrom(receiveDatas);
				SelectServerRequest selectServerRequest = new SelectServerRequest(ip, receiveMessageId, session, cS_SelectServer);
				Response resp = new Response(selectServerRequest);
				getCommonProcessor().processSelectServer(selectServerRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		case MICS_GetServerList : 
			if (getCommonProcessor() != null) {
				CS_GetServerList cS_GetServerList = CS_GetServerList.parseFrom(receiveDatas);
				GetServerListRequest getServerListRequest = new GetServerListRequest(ip, receiveMessageId, session, cS_GetServerList);
				GetServerListResponse resp = new GetServerListResponse(getServerListRequest);
				getCommonProcessor().processGetServerList(getServerListRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		case MICS_Test1 : 
			if (getTestProcessor() != null) {
				CS_Test1 cS_Test1 = CS_Test1.parseFrom(receiveDatas);
				Test1Request test1Request = new Test1Request(ip, receiveMessageId, session, cS_Test1);
				Test1Response resp = new Test1Response(test1Request);
				getTestProcessor().processTest1(test1Request, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		case MICS_Disconnect : 
			if (getAccountProcessor() != null) {
				CS_Disconnect cS_Disconnect = CS_Disconnect.parseFrom(receiveDatas);
				DisconnectRequest disconnectRequest = new DisconnectRequest(ip, receiveMessageId, session, cS_Disconnect);
				Response resp = new Response(disconnectRequest);
				getAccountProcessor().processDisconnect(disconnectRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		case MICS_Reconnect : 
			if (getCommonProcessor() != null) {
				CS_Reconnect cS_Reconnect = CS_Reconnect.parseFrom(receiveDatas);
				ReconnectRequest reconnectRequest = new ReconnectRequest(ip, receiveMessageId, session, cS_Reconnect);
				Response resp = new Response(reconnectRequest);
				getCommonProcessor().processReconnect(reconnectRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		case MIVO_ServerInfo : 
			if (getCommonProcessor() != null) {
				VO_ServerInfo vO_ServerInfo = VO_ServerInfo.parseFrom(receiveDatas);
				ServerInfoRequest serverInfoRequest = new ServerInfoRequest(ip, receiveMessageId, session, vO_ServerInfo);
				Response resp = new Response(serverInfoRequest);
				getCommonProcessor().processServerInfo(serverInfoRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		case MICS_AccountGetRoleList : 
			if (getRoleProcessor() != null) {
				CS_AccountGetRoleList cS_AccountGetRoleList = CS_AccountGetRoleList.parseFrom(receiveDatas);
				AccountGetRoleListRequest accountGetRoleListRequest = new AccountGetRoleListRequest(ip, receiveMessageId, session, cS_AccountGetRoleList);
				AccountGetRoleListResponse resp = new AccountGetRoleListResponse(accountGetRoleListRequest);
				getRoleProcessor().processAccountGetRoleList(accountGetRoleListRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		case MICS_NewRoleInfo : 
			if (getRoleProcessor() != null) {
				CS_NewRoleInfo cS_NewRoleInfo = CS_NewRoleInfo.parseFrom(receiveDatas);
				NewRoleInfoRequest newRoleInfoRequest = new NewRoleInfoRequest(ip, receiveMessageId, session, cS_NewRoleInfo);
				Response resp = new Response(newRoleInfoRequest);
				getRoleProcessor().processNewRoleInfo(newRoleInfoRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		case MICS_RoleInfo : 
			if (getRoleProcessor() != null) {
				CS_RoleInfo cS_RoleInfo = CS_RoleInfo.parseFrom(receiveDatas);
				RoleInfoRequest roleInfoRequest = new RoleInfoRequest(ip, receiveMessageId, session, cS_RoleInfo);
				RoleInfoResponse resp = new RoleInfoResponse(roleInfoRequest);
				getRoleProcessor().processRoleInfo(roleInfoRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		case MICS_ServerStatus : 
			if (getCommonProcessor() != null) {
				CS_ServerStatus cS_ServerStatus = CS_ServerStatus.parseFrom(receiveDatas);
				ServerStatusRequest serverStatusRequest = new ServerStatusRequest(ip, receiveMessageId, session, cS_ServerStatus);
				Response resp = new Response(serverStatusRequest);
				getCommonProcessor().processServerStatus(serverStatusRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		case MICS_AccountLogin : 
			if (getAccountProcessor() != null) {
				CS_AccountLogin cS_AccountLogin = CS_AccountLogin.parseFrom(receiveDatas);
				AccountLoginRequest accountLoginRequest = new AccountLoginRequest(ip, receiveMessageId, session, cS_AccountLogin);
				AccountLoginResponse resp = new AccountLoginResponse(accountLoginRequest);
				getAccountProcessor().processAccountLogin(accountLoginRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
			}
			break;
		default : 
			response = createNoProcessorResponse(new RedirectRequest(ip, receiveMessageId, session, receiveDatas));
		}
		response.build();
		return response;
	}

	protected Response createNoProcessorResponse(Request request) {
		return new Response(request);
	}

	@Override
	public AccountProcessor getAccountProcessor() {
		return accountProcessor;
	}

	public void setAccountProcessor(AccountProcessor accountProcessor) {
		this.accountProcessor = accountProcessor;
	}

	@Override
	public CommonProcessor getCommonProcessor() {
		return commonProcessor;
	}

	public void setCommonProcessor(CommonProcessor commonProcessor) {
		this.commonProcessor = commonProcessor;
	}

	@Override
	public RoleProcessor getRoleProcessor() {
		return roleProcessor;
	}

	public void setRoleProcessor(RoleProcessor roleProcessor) {
		this.roleProcessor = roleProcessor;
	}

	@Override
	public TestProcessor getTestProcessor() {
		return testProcessor;
	}

	public void setTestProcessor(TestProcessor testProcessor) {
		this.testProcessor = testProcessor;
	}

}
