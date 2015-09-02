package com.fanxing.server.io.proto.processor;

import com.fanxing.server.io.proto.request.EquipmentRecastRequest;
import com.fanxing.server.io.proto.response.EquipmentRecastResponse;
import com.fanxing.server.io.proto.request.EquipmentRefinRequest;
import com.fanxing.server.io.proto.response.EquipmentRefinResponse;
import com.fanxing.server.io.proto.request.EquipmentRollbackRequest;
import com.fanxing.server.io.proto.response.EquipmentRollbackResponse;
import com.fanxing.server.io.proto.request.EquipmentInheritRequest;
import com.fanxing.server.io.proto.response.EquipmentInheritResponse;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public interface EquipmentProcessor {

	void processEquipmentRecast(EquipmentRecastRequest request, EquipmentRecastResponse response);

	void processEquipmentRefin(EquipmentRefinRequest request, EquipmentRefinResponse response);

	void processEquipmentRollback(EquipmentRollbackRequest request, EquipmentRollbackResponse response);

	void processEquipmentInherit(EquipmentInheritRequest request, EquipmentInheritResponse response);

}
