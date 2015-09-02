package com.fanxing.server.io.proto.processor;

import com.fanxing.server.io.proto.request.GetLegionInfoRequest;
import com.fanxing.server.io.proto.response.GetLegionInfoResponse;
import com.fanxing.server.io.proto.request.GetLegionMemberInfoListRequest;
import com.fanxing.server.io.proto.response.GetLegionMemberInfoListResponse;
import com.fanxing.server.io.proto.request.GetLegionLogRequest;
import com.fanxing.server.io.proto.response.GetLegionLogResponse;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.response.RecommendLegionListResponse;
import com.fanxing.server.io.proto.response.RankingLegionListResponse;
import com.fanxing.server.io.proto.response.CreateLegionResponse;
import com.fanxing.server.io.proto.response.UpgradeLegionResponse;
import com.fanxing.server.io.proto.response.LeaveLegionResponse;
import com.fanxing.server.io.proto.request.TransferLegionRequest;
import com.fanxing.server.io.proto.response.TransferLegionResponse;
import com.fanxing.server.io.proto.response.DelateLegionLeaderResponse;
import com.fanxing.server.io.proto.request.ApplyJoinLegionRequest;
import com.fanxing.server.io.proto.response.ApplyJoinLegionResponse;
import com.fanxing.server.io.proto.request.ReplyApplyJoinLegionRequest;
import com.fanxing.server.io.proto.response.ReplyApplyJoinLegionResponse;
import com.fanxing.server.io.proto.request.InviteJoinLegionRequest;
import com.fanxing.server.io.proto.response.InviteJoinLegionResponse;
import com.fanxing.server.io.proto.request.ReplyInvitationRequest;
import com.fanxing.server.io.proto.response.ReplyInvitationResponse;
import com.fanxing.server.io.proto.request.DonateGoodsRequest;
import com.fanxing.server.io.proto.response.DonateGoodsResponse;
import com.fanxing.server.io.proto.request.SendChatMessageRequest;
import com.fanxing.server.io.proto.response.SendChatMessageResponse;
import com.fanxing.server.io.proto.response.GainWelfareResponse;
import com.fanxing.server.io.proto.response.GainSupplyResponse;
import com.fanxing.server.io.proto.request.BuyLegionGoodsRequest;
import com.fanxing.server.io.proto.response.BuyLegionGoodsResponse;
import com.fanxing.server.io.proto.request.RemoveMemberRequest;
import com.fanxing.server.io.proto.response.RemoveMemberResponse;
import com.fanxing.server.io.proto.request.PermissionManageRequest;
import com.fanxing.server.io.proto.response.PermissionManageResponse;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public interface LegionProcessor {

	void processGetLegionInfo(GetLegionInfoRequest request, GetLegionInfoResponse response);

	void processGetLegionMemberInfoList(GetLegionMemberInfoListRequest request, GetLegionMemberInfoListResponse response);

	void processGetLegionLog(GetLegionLogRequest request, GetLegionLogResponse response);

	void processRecommendLegionList(Request request, RecommendLegionListResponse response);

	void processRankingLegionList(Request request, RankingLegionListResponse response);

	void processCreateLegion(Request request, CreateLegionResponse response);

	void processUpgradeLegion(Request request, UpgradeLegionResponse response);

	void processLeaveLegion(Request request, LeaveLegionResponse response);

	void processTransferLegion(TransferLegionRequest request, TransferLegionResponse response);

	void processDelateLegionLeader(Request request, DelateLegionLeaderResponse response);

	void processApplyJoinLegion(ApplyJoinLegionRequest request, ApplyJoinLegionResponse response);

	void processReplyApplyJoinLegion(ReplyApplyJoinLegionRequest request, ReplyApplyJoinLegionResponse response);

	void processInviteJoinLegion(InviteJoinLegionRequest request, InviteJoinLegionResponse response);

	void processReplyInvitation(ReplyInvitationRequest request, ReplyInvitationResponse response);

	void processDonateGoods(DonateGoodsRequest request, DonateGoodsResponse response);

	void processSendChatMessage(SendChatMessageRequest request, SendChatMessageResponse response);

	void processGainWelfare(Request request, GainWelfareResponse response);

	void processGainSupply(Request request, GainSupplyResponse response);

	void processBuyLegionGoods(BuyLegionGoodsRequest request, BuyLegionGoodsResponse response);

	void processRemoveMember(RemoveMemberRequest request, RemoveMemberResponse response);

	void processPermissionManage(PermissionManageRequest request, PermissionManageResponse response);

}
