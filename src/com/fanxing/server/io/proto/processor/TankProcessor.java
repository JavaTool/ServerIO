package com.fanxing.server.io.proto.processor;

import com.fanxing.server.io.proto.request.TankUpgradeRequest;
import com.fanxing.server.io.proto.response.TankUpgradeResponse;
import com.fanxing.server.io.proto.request.TankStrengthenRequest;
import com.fanxing.server.io.proto.response.TankStrengthenResponse;
import com.fanxing.server.io.proto.request.TankUpgradesStarRequest;
import com.fanxing.server.io.proto.response.TankUpgradesStarResponse;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public interface TankProcessor {

	void processTankUpgrade(TankUpgradeRequest request, TankUpgradeResponse response);

	void processTankStrengthen(TankStrengthenRequest request, TankStrengthenResponse response);

	void processTankUpgradesStar(TankUpgradesStarRequest request, TankUpgradesStarResponse response);

}
