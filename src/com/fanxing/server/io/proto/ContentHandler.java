package com.fanxing.server.io.proto;

import com.fanxing.server.io.proto.protocol.MessageIdProto.MessageId;
import com.fanxing.server.io.proto.RedirectRequest;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.IContentHandler;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.response.GainSupplyResponse;
import com.fanxing.server.io.proto.processor.LegionProcessor;
import com.fanxing.server.io.proto.protocol.LegionProtos.CS_RemoveMember;
import com.fanxing.server.io.proto.response.RemoveMemberResponse;
import com.fanxing.server.io.proto.request.RemoveMemberRequest;
import com.fanxing.server.io.proto.response.RecommendLegionListResponse;
import com.fanxing.server.io.proto.protocol.LegionProtos.CS_ReplyApplyJoinLegion;
import com.fanxing.server.io.proto.response.ReplyApplyJoinLegionResponse;
import com.fanxing.server.io.proto.request.ReplyApplyJoinLegionRequest;
import com.fanxing.server.io.proto.protocol.RoleProtos.CS_AccountGetRoleList;
import com.fanxing.server.io.proto.response.AccountGetRoleListResponse;
import com.fanxing.server.io.proto.request.AccountGetRoleListRequest;
import com.fanxing.server.io.proto.processor.RoleProcessor;
import com.fanxing.server.io.proto.protocol.TankProtos.CS_TankStrengthen;
import com.fanxing.server.io.proto.response.TankStrengthenResponse;
import com.fanxing.server.io.proto.request.TankStrengthenRequest;
import com.fanxing.server.io.proto.processor.TankProcessor;
import com.fanxing.server.io.proto.protocol.EquipmentProtos.CS_EquipmentRecast;
import com.fanxing.server.io.proto.response.EquipmentRecastResponse;
import com.fanxing.server.io.proto.request.EquipmentRecastRequest;
import com.fanxing.server.io.proto.processor.EquipmentProcessor;
import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.processor.CommonProcessor;
import com.fanxing.server.io.proto.protocol.LegionProtos.CS_GetLegionMemberInfoList;
import com.fanxing.server.io.proto.response.GetLegionMemberInfoListResponse;
import com.fanxing.server.io.proto.request.GetLegionMemberInfoListRequest;
import com.fanxing.server.io.proto.protocol.EquipmentProtos.CS_EquipmentRollback;
import com.fanxing.server.io.proto.response.EquipmentRollbackResponse;
import com.fanxing.server.io.proto.request.EquipmentRollbackRequest;
import com.fanxing.server.io.proto.protocol.CommonProtos.CS_CheckVersion;
import com.fanxing.server.io.proto.response.CheckVersionResponse;
import com.fanxing.server.io.proto.request.CheckVersionRequest;
import com.fanxing.server.io.proto.protocol.TankProtos.CS_TankUpgrade;
import com.fanxing.server.io.proto.response.TankUpgradeResponse;
import com.fanxing.server.io.proto.request.TankUpgradeRequest;
import com.fanxing.server.io.proto.response.LeaveLegionResponse;
import com.fanxing.server.io.proto.protocol.CommonProtos.CS_ServerStatus;
import com.fanxing.server.io.proto.request.ServerStatusRequest;
import com.fanxing.server.io.proto.protocol.LegionProtos.CS_DonateGoods;
import com.fanxing.server.io.proto.response.DonateGoodsResponse;
import com.fanxing.server.io.proto.request.DonateGoodsRequest;
import com.fanxing.server.io.proto.protocol.LegionProtos.CS_TransferLegion;
import com.fanxing.server.io.proto.response.TransferLegionResponse;
import com.fanxing.server.io.proto.request.TransferLegionRequest;
import com.fanxing.server.io.proto.protocol.LegionProtos.CS_ApplyJoinLegion;
import com.fanxing.server.io.proto.response.ApplyJoinLegionResponse;
import com.fanxing.server.io.proto.request.ApplyJoinLegionRequest;
import com.fanxing.server.io.proto.protocol.LegionProtos.CS_BuyLegionGoods;
import com.fanxing.server.io.proto.response.BuyLegionGoodsResponse;
import com.fanxing.server.io.proto.request.BuyLegionGoodsRequest;
import com.fanxing.server.io.proto.protocol.RoleProtos.CS_NewRoleInfo;
import com.fanxing.server.io.proto.request.NewRoleInfoRequest;
import com.fanxing.server.io.proto.protocol.CommonProtos.CS_HttpCheck;
import com.fanxing.server.io.proto.response.HttpCheckResponse;
import com.fanxing.server.io.proto.request.HttpCheckRequest;
import com.fanxing.server.io.proto.protocol.TestProtos.CS_Test1;
import com.fanxing.server.io.proto.response.Test1Response;
import com.fanxing.server.io.proto.request.Test1Request;
import com.fanxing.server.io.proto.processor.TestProcessor;
import com.fanxing.server.io.proto.protocol.RoleProtos.CS_RoleInfo;
import com.fanxing.server.io.proto.response.RoleInfoResponse;
import com.fanxing.server.io.proto.request.RoleInfoRequest;
import com.fanxing.server.io.proto.protocol.LegionProtos.CS_GetLegionInfo;
import com.fanxing.server.io.proto.response.GetLegionInfoResponse;
import com.fanxing.server.io.proto.request.GetLegionInfoRequest;
import com.fanxing.server.io.proto.protocol.CommonProtos.VO_ServerInfo;
import com.fanxing.server.io.proto.request.ServerInfoRequest;
import com.fanxing.server.io.proto.protocol.AccountProtos.CS_AccountRegister;
import com.fanxing.server.io.proto.response.AccountRegisterResponse;
import com.fanxing.server.io.proto.request.AccountRegisterRequest;
import com.fanxing.server.io.proto.processor.AccountProcessor;
import com.fanxing.server.io.proto.protocol.EquipmentProtos.CS_EquipmentInherit;
import com.fanxing.server.io.proto.response.EquipmentInheritResponse;
import com.fanxing.server.io.proto.request.EquipmentInheritRequest;
import com.fanxing.server.io.proto.protocol.CommonProtos.CS_GetServerList;
import com.fanxing.server.io.proto.response.GetServerListResponse;
import com.fanxing.server.io.proto.request.GetServerListRequest;
import com.fanxing.server.io.proto.protocol.CommonProtos.CS_Reconnect;
import com.fanxing.server.io.proto.request.ReconnectRequest;
import com.fanxing.server.io.proto.response.RankingLegionListResponse;
import com.fanxing.server.io.proto.protocol.AccountProtos.CS_AccountLogin;
import com.fanxing.server.io.proto.response.AccountLoginResponse;
import com.fanxing.server.io.proto.request.AccountLoginRequest;
import com.fanxing.server.io.proto.protocol.LegionProtos.CS_SendChatMessage;
import com.fanxing.server.io.proto.response.SendChatMessageResponse;
import com.fanxing.server.io.proto.request.SendChatMessageRequest;
import com.fanxing.server.io.proto.response.UpgradeLegionResponse;
import com.fanxing.server.io.proto.protocol.CommonProtos.CS_SelectServer;
import com.fanxing.server.io.proto.response.SelectServerResponse;
import com.fanxing.server.io.proto.request.SelectServerRequest;
import com.fanxing.server.io.proto.protocol.LegionProtos.CS_GetLegionLog;
import com.fanxing.server.io.proto.response.GetLegionLogResponse;
import com.fanxing.server.io.proto.request.GetLegionLogRequest;
import com.fanxing.server.io.proto.response.DelateLegionLeaderResponse;
import com.fanxing.server.io.proto.response.CreateLegionResponse;
import com.fanxing.server.io.proto.protocol.RoleProtos.CS_AccountAuthenticate;
import com.fanxing.server.io.proto.request.AccountAuthenticateRequest;
import com.fanxing.server.io.proto.protocol.EquipmentProtos.CS_EquipmentRefin;
import com.fanxing.server.io.proto.response.EquipmentRefinResponse;
import com.fanxing.server.io.proto.request.EquipmentRefinRequest;
import com.fanxing.server.io.proto.protocol.LegionProtos.CS_ReplyInvitation;
import com.fanxing.server.io.proto.response.ReplyInvitationResponse;
import com.fanxing.server.io.proto.request.ReplyInvitationRequest;
import com.fanxing.server.io.proto.protocol.TankProtos.CS_TankUpgradesStar;
import com.fanxing.server.io.proto.response.TankUpgradesStarResponse;
import com.fanxing.server.io.proto.request.TankUpgradesStarRequest;
import com.fanxing.server.io.proto.protocol.RoleProtos.CS_CreateRole;
import com.fanxing.server.io.proto.request.CreateRoleRequest;
import com.fanxing.server.io.proto.protocol.TcpProtos.CS_TcpConnect;
import com.fanxing.server.io.proto.request.TcpConnectRequest;
import com.fanxing.server.io.proto.processor.TcpProcessor;
import com.fanxing.server.io.proto.protocol.AccountProtos.CS_Disconnect;
import com.fanxing.server.io.proto.request.DisconnectRequest;
import com.fanxing.server.io.proto.response.GainWelfareResponse;
import com.fanxing.server.io.proto.protocol.LegionProtos.CS_InviteJoinLegion;
import com.fanxing.server.io.proto.response.InviteJoinLegionResponse;
import com.fanxing.server.io.proto.request.InviteJoinLegionRequest;
import com.fanxing.server.io.proto.protocol.TankProtos.CS_TankChangeEquipment;
import com.fanxing.server.io.proto.response.TankChangeEquipmentResponse;
import com.fanxing.server.io.proto.request.TankChangeEquipmentRequest;
import com.fanxing.server.io.proto.protocol.LegionProtos.CS_PermissionManage;
import com.fanxing.server.io.proto.response.PermissionManageResponse;
import com.fanxing.server.io.proto.request.PermissionManageRequest;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class ContentHandler implements IContentHandler {

	private LegionProcessor legionProcessor;

	private RoleProcessor roleProcessor;

	private TankProcessor tankProcessor;

	private EquipmentProcessor equipmentProcessor;

	private CommonProcessor commonProcessor;

	private TestProcessor testProcessor;

	private AccountProcessor accountProcessor;

	private TcpProcessor tcpProcessor;

	@Override
	public void handle(IContent content) throws Exception {
		Response response;
		switch (MessageId.valueOf(content.getMessageId())) {
		case MICS_GainSupply : 
			if (getLegionProcessor() != null) {
				Request request = new Request(content);
				GainSupplyResponse resp = new GainSupplyResponse(request);
				getLegionProcessor().processGainSupply(request, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_RemoveMember : 
			if (getLegionProcessor() != null) {
				CS_RemoveMember cS_RemoveMember = CS_RemoveMember.parseFrom(content.getDatas());
				RemoveMemberRequest removeMemberRequest = new RemoveMemberRequest(content, cS_RemoveMember);
				RemoveMemberResponse resp = new RemoveMemberResponse(removeMemberRequest);
				getLegionProcessor().processRemoveMember(removeMemberRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_RecommendLegionList : 
			if (getLegionProcessor() != null) {
				Request request = new Request(content);
				RecommendLegionListResponse resp = new RecommendLegionListResponse(request);
				getLegionProcessor().processRecommendLegionList(request, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_ReplyApplyJoinLegion : 
			if (getLegionProcessor() != null) {
				CS_ReplyApplyJoinLegion cS_ReplyApplyJoinLegion = CS_ReplyApplyJoinLegion.parseFrom(content.getDatas());
				ReplyApplyJoinLegionRequest replyApplyJoinLegionRequest = new ReplyApplyJoinLegionRequest(content, cS_ReplyApplyJoinLegion);
				ReplyApplyJoinLegionResponse resp = new ReplyApplyJoinLegionResponse(replyApplyJoinLegionRequest);
				getLegionProcessor().processReplyApplyJoinLegion(replyApplyJoinLegionRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_AccountGetRoleList : 
			if (getRoleProcessor() != null) {
				CS_AccountGetRoleList cS_AccountGetRoleList = CS_AccountGetRoleList.parseFrom(content.getDatas());
				AccountGetRoleListRequest accountGetRoleListRequest = new AccountGetRoleListRequest(content, cS_AccountGetRoleList);
				AccountGetRoleListResponse resp = new AccountGetRoleListResponse(accountGetRoleListRequest);
				getRoleProcessor().processAccountGetRoleList(accountGetRoleListRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_TankStrengthen : 
			if (getTankProcessor() != null) {
				CS_TankStrengthen cS_TankStrengthen = CS_TankStrengthen.parseFrom(content.getDatas());
				TankStrengthenRequest tankStrengthenRequest = new TankStrengthenRequest(content, cS_TankStrengthen);
				TankStrengthenResponse resp = new TankStrengthenResponse(tankStrengthenRequest);
				getTankProcessor().processTankStrengthen(tankStrengthenRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_EquipmentRecast : 
			if (getEquipmentProcessor() != null) {
				CS_EquipmentRecast cS_EquipmentRecast = CS_EquipmentRecast.parseFrom(content.getDatas());
				EquipmentRecastRequest equipmentRecastRequest = new EquipmentRecastRequest(content, cS_EquipmentRecast);
				EquipmentRecastResponse resp = new EquipmentRecastResponse(equipmentRecastRequest);
				getEquipmentProcessor().processEquipmentRecast(equipmentRecastRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_ServerHeart : 
			if (getCommonProcessor() != null) {
				Request request = new Request(content);
				Response resp = new Response(request);
				getCommonProcessor().processServerHeart(request, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_GetLegionMemberInfoList : 
			if (getLegionProcessor() != null) {
				CS_GetLegionMemberInfoList cS_GetLegionMemberInfoList = CS_GetLegionMemberInfoList.parseFrom(content.getDatas());
				GetLegionMemberInfoListRequest getLegionMemberInfoListRequest = new GetLegionMemberInfoListRequest(content, cS_GetLegionMemberInfoList);
				GetLegionMemberInfoListResponse resp = new GetLegionMemberInfoListResponse(getLegionMemberInfoListRequest);
				getLegionProcessor().processGetLegionMemberInfoList(getLegionMemberInfoListRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_EquipmentRollback : 
			if (getEquipmentProcessor() != null) {
				CS_EquipmentRollback cS_EquipmentRollback = CS_EquipmentRollback.parseFrom(content.getDatas());
				EquipmentRollbackRequest equipmentRollbackRequest = new EquipmentRollbackRequest(content, cS_EquipmentRollback);
				EquipmentRollbackResponse resp = new EquipmentRollbackResponse(equipmentRollbackRequest);
				getEquipmentProcessor().processEquipmentRollback(equipmentRollbackRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_CheckVersion : 
			if (getCommonProcessor() != null) {
				CS_CheckVersion cS_CheckVersion = CS_CheckVersion.parseFrom(content.getDatas());
				CheckVersionRequest checkVersionRequest = new CheckVersionRequest(content, cS_CheckVersion);
				CheckVersionResponse resp = new CheckVersionResponse(checkVersionRequest);
				getCommonProcessor().processCheckVersion(checkVersionRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_TankUpgrade : 
			if (getTankProcessor() != null) {
				CS_TankUpgrade cS_TankUpgrade = CS_TankUpgrade.parseFrom(content.getDatas());
				TankUpgradeRequest tankUpgradeRequest = new TankUpgradeRequest(content, cS_TankUpgrade);
				TankUpgradeResponse resp = new TankUpgradeResponse(tankUpgradeRequest);
				getTankProcessor().processTankUpgrade(tankUpgradeRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_LeaveLegion : 
			if (getLegionProcessor() != null) {
				Request request = new Request(content);
				LeaveLegionResponse resp = new LeaveLegionResponse(request);
				getLegionProcessor().processLeaveLegion(request, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_ServerStatus : 
			if (getCommonProcessor() != null) {
				CS_ServerStatus cS_ServerStatus = CS_ServerStatus.parseFrom(content.getDatas());
				ServerStatusRequest serverStatusRequest = new ServerStatusRequest(content, cS_ServerStatus);
				Response resp = new Response(serverStatusRequest);
				getCommonProcessor().processServerStatus(serverStatusRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_DonateGoods : 
			if (getLegionProcessor() != null) {
				CS_DonateGoods cS_DonateGoods = CS_DonateGoods.parseFrom(content.getDatas());
				DonateGoodsRequest donateGoodsRequest = new DonateGoodsRequest(content, cS_DonateGoods);
				DonateGoodsResponse resp = new DonateGoodsResponse(donateGoodsRequest);
				getLegionProcessor().processDonateGoods(donateGoodsRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_TransferLegion : 
			if (getLegionProcessor() != null) {
				CS_TransferLegion cS_TransferLegion = CS_TransferLegion.parseFrom(content.getDatas());
				TransferLegionRequest transferLegionRequest = new TransferLegionRequest(content, cS_TransferLegion);
				TransferLegionResponse resp = new TransferLegionResponse(transferLegionRequest);
				getLegionProcessor().processTransferLegion(transferLegionRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_ApplyJoinLegion : 
			if (getLegionProcessor() != null) {
				CS_ApplyJoinLegion cS_ApplyJoinLegion = CS_ApplyJoinLegion.parseFrom(content.getDatas());
				ApplyJoinLegionRequest applyJoinLegionRequest = new ApplyJoinLegionRequest(content, cS_ApplyJoinLegion);
				ApplyJoinLegionResponse resp = new ApplyJoinLegionResponse(applyJoinLegionRequest);
				getLegionProcessor().processApplyJoinLegion(applyJoinLegionRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_BuyLegionGoods : 
			if (getLegionProcessor() != null) {
				CS_BuyLegionGoods cS_BuyLegionGoods = CS_BuyLegionGoods.parseFrom(content.getDatas());
				BuyLegionGoodsRequest buyLegionGoodsRequest = new BuyLegionGoodsRequest(content, cS_BuyLegionGoods);
				BuyLegionGoodsResponse resp = new BuyLegionGoodsResponse(buyLegionGoodsRequest);
				getLegionProcessor().processBuyLegionGoods(buyLegionGoodsRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_NewRoleInfo : 
			if (getRoleProcessor() != null) {
				CS_NewRoleInfo cS_NewRoleInfo = CS_NewRoleInfo.parseFrom(content.getDatas());
				NewRoleInfoRequest newRoleInfoRequest = new NewRoleInfoRequest(content, cS_NewRoleInfo);
				Response resp = new Response(newRoleInfoRequest);
				getRoleProcessor().processNewRoleInfo(newRoleInfoRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_HttpCheck : 
			if (getCommonProcessor() != null) {
				CS_HttpCheck cS_HttpCheck = CS_HttpCheck.parseFrom(content.getDatas());
				HttpCheckRequest httpCheckRequest = new HttpCheckRequest(content, cS_HttpCheck);
				HttpCheckResponse resp = new HttpCheckResponse(httpCheckRequest);
				getCommonProcessor().processHttpCheck(httpCheckRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_Test1 : 
			if (getTestProcessor() != null) {
				CS_Test1 cS_Test1 = CS_Test1.parseFrom(content.getDatas());
				Test1Request test1Request = new Test1Request(content, cS_Test1);
				Test1Response resp = new Test1Response(test1Request);
				getTestProcessor().processTest1(test1Request, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_RoleInfo : 
			if (getRoleProcessor() != null) {
				CS_RoleInfo cS_RoleInfo = CS_RoleInfo.parseFrom(content.getDatas());
				RoleInfoRequest roleInfoRequest = new RoleInfoRequest(content, cS_RoleInfo);
				RoleInfoResponse resp = new RoleInfoResponse(roleInfoRequest);
				getRoleProcessor().processRoleInfo(roleInfoRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_GetLegionInfo : 
			if (getLegionProcessor() != null) {
				CS_GetLegionInfo cS_GetLegionInfo = CS_GetLegionInfo.parseFrom(content.getDatas());
				GetLegionInfoRequest getLegionInfoRequest = new GetLegionInfoRequest(content, cS_GetLegionInfo);
				GetLegionInfoResponse resp = new GetLegionInfoResponse(getLegionInfoRequest);
				getLegionProcessor().processGetLegionInfo(getLegionInfoRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MIVO_ServerInfo : 
			if (getCommonProcessor() != null) {
				VO_ServerInfo vO_ServerInfo = VO_ServerInfo.parseFrom(content.getDatas());
				ServerInfoRequest serverInfoRequest = new ServerInfoRequest(content, vO_ServerInfo);
				Response resp = new Response(serverInfoRequest);
				getCommonProcessor().processServerInfo(serverInfoRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_AccountRegister : 
			if (getAccountProcessor() != null) {
				CS_AccountRegister cS_AccountRegister = CS_AccountRegister.parseFrom(content.getDatas());
				AccountRegisterRequest accountRegisterRequest = new AccountRegisterRequest(content, cS_AccountRegister);
				AccountRegisterResponse resp = new AccountRegisterResponse(accountRegisterRequest);
				getAccountProcessor().processAccountRegister(accountRegisterRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_EquipmentInherit : 
			if (getEquipmentProcessor() != null) {
				CS_EquipmentInherit cS_EquipmentInherit = CS_EquipmentInherit.parseFrom(content.getDatas());
				EquipmentInheritRequest equipmentInheritRequest = new EquipmentInheritRequest(content, cS_EquipmentInherit);
				EquipmentInheritResponse resp = new EquipmentInheritResponse(equipmentInheritRequest);
				getEquipmentProcessor().processEquipmentInherit(equipmentInheritRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_GetServerList : 
			if (getCommonProcessor() != null) {
				CS_GetServerList cS_GetServerList = CS_GetServerList.parseFrom(content.getDatas());
				GetServerListRequest getServerListRequest = new GetServerListRequest(content, cS_GetServerList);
				GetServerListResponse resp = new GetServerListResponse(getServerListRequest);
				getCommonProcessor().processGetServerList(getServerListRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_Reconnect : 
			if (getCommonProcessor() != null) {
				CS_Reconnect cS_Reconnect = CS_Reconnect.parseFrom(content.getDatas());
				ReconnectRequest reconnectRequest = new ReconnectRequest(content, cS_Reconnect);
				Response resp = new Response(reconnectRequest);
				getCommonProcessor().processReconnect(reconnectRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_RankingLegionList : 
			if (getLegionProcessor() != null) {
				Request request = new Request(content);
				RankingLegionListResponse resp = new RankingLegionListResponse(request);
				getLegionProcessor().processRankingLegionList(request, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_AccountLogin : 
			if (getAccountProcessor() != null) {
				CS_AccountLogin cS_AccountLogin = CS_AccountLogin.parseFrom(content.getDatas());
				AccountLoginRequest accountLoginRequest = new AccountLoginRequest(content, cS_AccountLogin);
				AccountLoginResponse resp = new AccountLoginResponse(accountLoginRequest);
				getAccountProcessor().processAccountLogin(accountLoginRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_SendChatMessage : 
			if (getLegionProcessor() != null) {
				CS_SendChatMessage cS_SendChatMessage = CS_SendChatMessage.parseFrom(content.getDatas());
				SendChatMessageRequest sendChatMessageRequest = new SendChatMessageRequest(content, cS_SendChatMessage);
				SendChatMessageResponse resp = new SendChatMessageResponse(sendChatMessageRequest);
				getLegionProcessor().processSendChatMessage(sendChatMessageRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_UpgradeLegion : 
			if (getLegionProcessor() != null) {
				Request request = new Request(content);
				UpgradeLegionResponse resp = new UpgradeLegionResponse(request);
				getLegionProcessor().processUpgradeLegion(request, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_SelectServer : 
			if (getCommonProcessor() != null) {
				CS_SelectServer cS_SelectServer = CS_SelectServer.parseFrom(content.getDatas());
				SelectServerRequest selectServerRequest = new SelectServerRequest(content, cS_SelectServer);
				SelectServerResponse resp = new SelectServerResponse(selectServerRequest);
				getCommonProcessor().processSelectServer(selectServerRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_GetLegionLog : 
			if (getLegionProcessor() != null) {
				CS_GetLegionLog cS_GetLegionLog = CS_GetLegionLog.parseFrom(content.getDatas());
				GetLegionLogRequest getLegionLogRequest = new GetLegionLogRequest(content, cS_GetLegionLog);
				GetLegionLogResponse resp = new GetLegionLogResponse(getLegionLogRequest);
				getLegionProcessor().processGetLegionLog(getLegionLogRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_DelateLegionLeader : 
			if (getLegionProcessor() != null) {
				Request request = new Request(content);
				DelateLegionLeaderResponse resp = new DelateLegionLeaderResponse(request);
				getLegionProcessor().processDelateLegionLeader(request, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_CreateLegion : 
			if (getLegionProcessor() != null) {
				Request request = new Request(content);
				CreateLegionResponse resp = new CreateLegionResponse(request);
				getLegionProcessor().processCreateLegion(request, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_AccountAuthenticate : 
			if (getRoleProcessor() != null) {
				CS_AccountAuthenticate cS_AccountAuthenticate = CS_AccountAuthenticate.parseFrom(content.getDatas());
				AccountAuthenticateRequest accountAuthenticateRequest = new AccountAuthenticateRequest(content, cS_AccountAuthenticate);
				Response resp = new Response(accountAuthenticateRequest);
				getRoleProcessor().processAccountAuthenticate(accountAuthenticateRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_EquipmentRefin : 
			if (getEquipmentProcessor() != null) {
				CS_EquipmentRefin cS_EquipmentRefin = CS_EquipmentRefin.parseFrom(content.getDatas());
				EquipmentRefinRequest equipmentRefinRequest = new EquipmentRefinRequest(content, cS_EquipmentRefin);
				EquipmentRefinResponse resp = new EquipmentRefinResponse(equipmentRefinRequest);
				getEquipmentProcessor().processEquipmentRefin(equipmentRefinRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_ReplyInvitation : 
			if (getLegionProcessor() != null) {
				CS_ReplyInvitation cS_ReplyInvitation = CS_ReplyInvitation.parseFrom(content.getDatas());
				ReplyInvitationRequest replyInvitationRequest = new ReplyInvitationRequest(content, cS_ReplyInvitation);
				ReplyInvitationResponse resp = new ReplyInvitationResponse(replyInvitationRequest);
				getLegionProcessor().processReplyInvitation(replyInvitationRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_TankUpgradesStar : 
			if (getTankProcessor() != null) {
				CS_TankUpgradesStar cS_TankUpgradesStar = CS_TankUpgradesStar.parseFrom(content.getDatas());
				TankUpgradesStarRequest tankUpgradesStarRequest = new TankUpgradesStarRequest(content, cS_TankUpgradesStar);
				TankUpgradesStarResponse resp = new TankUpgradesStarResponse(tankUpgradesStarRequest);
				getTankProcessor().processTankUpgradesStar(tankUpgradesStarRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_CreateRole : 
			if (getRoleProcessor() != null) {
				CS_CreateRole cS_CreateRole = CS_CreateRole.parseFrom(content.getDatas());
				CreateRoleRequest createRoleRequest = new CreateRoleRequest(content, cS_CreateRole);
				Response resp = new Response(createRoleRequest);
				getRoleProcessor().processCreateRole(createRoleRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_TcpConnect : 
			if (getTcpProcessor() != null) {
				CS_TcpConnect cS_TcpConnect = CS_TcpConnect.parseFrom(content.getDatas());
				TcpConnectRequest tcpConnectRequest = new TcpConnectRequest(content, cS_TcpConnect);
				Response resp = new Response(tcpConnectRequest);
				getTcpProcessor().processTcpConnect(tcpConnectRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_Disconnect : 
			if (getAccountProcessor() != null) {
				CS_Disconnect cS_Disconnect = CS_Disconnect.parseFrom(content.getDatas());
				DisconnectRequest disconnectRequest = new DisconnectRequest(content, cS_Disconnect);
				Response resp = new Response(disconnectRequest);
				getAccountProcessor().processDisconnect(disconnectRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_GainWelfare : 
			if (getLegionProcessor() != null) {
				Request request = new Request(content);
				GainWelfareResponse resp = new GainWelfareResponse(request);
				getLegionProcessor().processGainWelfare(request, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_InviteJoinLegion : 
			if (getLegionProcessor() != null) {
				CS_InviteJoinLegion cS_InviteJoinLegion = CS_InviteJoinLegion.parseFrom(content.getDatas());
				InviteJoinLegionRequest inviteJoinLegionRequest = new InviteJoinLegionRequest(content, cS_InviteJoinLegion);
				InviteJoinLegionResponse resp = new InviteJoinLegionResponse(inviteJoinLegionRequest);
				getLegionProcessor().processInviteJoinLegion(inviteJoinLegionRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_TankChangeEquipment : 
			if (getTankProcessor() != null) {
				CS_TankChangeEquipment cS_TankChangeEquipment = CS_TankChangeEquipment.parseFrom(content.getDatas());
				TankChangeEquipmentRequest tankChangeEquipmentRequest = new TankChangeEquipmentRequest(content, cS_TankChangeEquipment);
				TankChangeEquipmentResponse resp = new TankChangeEquipmentResponse(tankChangeEquipmentRequest);
				getTankProcessor().processTankChangeEquipment(tankChangeEquipmentRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		case MICS_PermissionManage : 
			if (getLegionProcessor() != null) {
				CS_PermissionManage cS_PermissionManage = CS_PermissionManage.parseFrom(content.getDatas());
				PermissionManageRequest permissionManageRequest = new PermissionManageRequest(content, cS_PermissionManage);
				PermissionManageResponse resp = new PermissionManageResponse(permissionManageRequest);
				getLegionProcessor().processPermissionManage(permissionManageRequest, resp);
				response = resp;
			} else {
				response = createNoProcessorResponse(new RedirectRequest(content));
			}
			break;
		default : 
			response = createNoProcessorResponse(new RedirectRequest(content));
		}
		response.build();
	}

	protected Response createNoProcessorResponse(Request request) {
		return new Response(request);
	}

	private LegionProcessor getLegionProcessor() {
		return legionProcessor;
	}

	public void setLegionProcessor(LegionProcessor legionProcessor) {
		this.legionProcessor = legionProcessor;
	}

	private RoleProcessor getRoleProcessor() {
		return roleProcessor;
	}

	public void setRoleProcessor(RoleProcessor roleProcessor) {
		this.roleProcessor = roleProcessor;
	}

	private TankProcessor getTankProcessor() {
		return tankProcessor;
	}

	public void setTankProcessor(TankProcessor tankProcessor) {
		this.tankProcessor = tankProcessor;
	}

	private EquipmentProcessor getEquipmentProcessor() {
		return equipmentProcessor;
	}

	public void setEquipmentProcessor(EquipmentProcessor equipmentProcessor) {
		this.equipmentProcessor = equipmentProcessor;
	}

	private CommonProcessor getCommonProcessor() {
		return commonProcessor;
	}

	public void setCommonProcessor(CommonProcessor commonProcessor) {
		this.commonProcessor = commonProcessor;
	}

	private TestProcessor getTestProcessor() {
		return testProcessor;
	}

	public void setTestProcessor(TestProcessor testProcessor) {
		this.testProcessor = testProcessor;
	}

	private AccountProcessor getAccountProcessor() {
		return accountProcessor;
	}

	public void setAccountProcessor(AccountProcessor accountProcessor) {
		this.accountProcessor = accountProcessor;
	}

	private TcpProcessor getTcpProcessor() {
		return tcpProcessor;
	}

	public void setTcpProcessor(TcpProcessor tcpProcessor) {
		this.tcpProcessor = tcpProcessor;
	}

}
