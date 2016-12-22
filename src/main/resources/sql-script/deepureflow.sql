/*
Navicat MySQL Data Transfer

Source Server         : mysql5.6
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : deepureflow

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-09-09 15:23:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for deepureflow_agent_flow
-- ----------------------------
DROP TABLE IF EXISTS `deepureflow_agent_flow`;
CREATE TABLE `deepureflow_agent_flow` (
  `AGENT_FLOW_ID` varchar(50) NOT NULL COMMENT '销售计划编号',
  `AGENT_FLOW_DATE` datetime DEFAULT NULL COMMENT '销售计划时间',
  `AGENT_ID` varchar(50) DEFAULT NULL COMMENT '所属终端编号',
  `CREATE_BY` varchar(50) DEFAULT NULL COMMENT '创建人',
  `CREATE_AT` datetime DEFAULT NULL COMMENT '创建时间',
  `AGENT_FLOW_STATUS` tinyint(4) DEFAULT '0' COMMENT '销售计划状态 0-已启用',
  PRIMARY KEY (`AGENT_FLOW_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售主表';

-- ----------------------------
-- Records of deepureflow_agent_flow
-- ----------------------------

-- ----------------------------
-- Table structure for deepureflow_agent_flow_shipment_item
-- ----------------------------
DROP TABLE IF EXISTS `deepureflow_agent_flow_shipment_item`;
CREATE TABLE `deepureflow_agent_flow_shipment_item` (
  `AGENT_FLOW_SHIPMENT_ITEM_ID` varchar(50) NOT NULL COMMENT '销售计划子项编号',
  `AGENT_FLOW_ID` varchar(50) DEFAULT NULL COMMENT '销售计划编号',
  `SKU_ID` varchar(10) DEFAULT NULL COMMENT 'sku产品编号',
  `SKU_NAME` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `SHIPMENT_QUANTITY` int(20) DEFAULT NULL COMMENT '剩余数量',
  `BASE_UNIT` varchar(20) DEFAULT NULL COMMENT '标准单位',
  `MIN_UNIT` varchar(20) DEFAULT NULL COMMENT '最小单位',
  PRIMARY KEY (`AGENT_FLOW_SHIPMENT_ITEM_ID`),
  KEY `SALE_PLAN_ID` (`AGENT_FLOW_ID`) USING BTREE,
  CONSTRAINT `deepureflow_agent_flow_item_ibfk_1` FOREIGN KEY (`AGENT_FLOW_ID`) REFERENCES `deepureflow_agent_flow` (`AGENT_FLOW_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售子项表';

-- ----------------------------
-- Records of deepureflow_agent_flow_shipment_item
-- ----------------------------

-- ----------------------------
-- Table structure for deepureflow_agent_flow_stock_item
-- ----------------------------
DROP TABLE IF EXISTS `deepureflow_agent_flow_stock_item`;
CREATE TABLE `deepureflow_agent_flow_stock_item` (
  `AGENT_FLOW_SHIPMENT_ITEM_ID` varchar(50) NOT NULL COMMENT '销售计划子项编号',
  `AGENT_FLOW_ID` varchar(50) DEFAULT NULL COMMENT '销售计划编号',
  `SKU_ID` varchar(10) DEFAULT NULL COMMENT 'sku产品编号',
  `SKU_NAME` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `STOCK_QUANTITY` int(20) DEFAULT NULL COMMENT '剩余数量',
  `BASE_UNIT` varchar(20) DEFAULT NULL COMMENT '标准单位',
  `MIN_UNIT` varchar(20) DEFAULT NULL COMMENT '最小单位',
  PRIMARY KEY (`AGENT_FLOW_SHIPMENT_ITEM_ID`),
  KEY `SALE_PLAN_ID` (`AGENT_FLOW_ID`),
  CONSTRAINT `deepureflow_agent_flow_stock_item_ibfk_1` FOREIGN KEY (`AGENT_FLOW_ID`) REFERENCES `deepureflow_agent_flow` (`AGENT_FLOW_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售子项表';

-- ----------------------------
-- Records of deepureflow_agent_flow_stock_item
-- ----------------------------

-- ----------------------------
-- Table structure for deepureflow_sale_plan
-- ----------------------------
DROP TABLE IF EXISTS `deepureflow_sale_plan`;
CREATE TABLE `deepureflow_sale_plan` (
  `SALE_PLAN_ID` varchar(50) NOT NULL COMMENT '销售计划编号',
  `SALE_PLAN_DATE` datetime DEFAULT NULL COMMENT '销售计划时间',
  `TERMINAL_ID` varchar(50) DEFAULT NULL COMMENT '所属终端编号',
  `CREATE_BY` varchar(50) DEFAULT NULL COMMENT '创建人',
  `CREATE_AT` datetime DEFAULT NULL COMMENT '创建时间',
  `SALE_PLAN_STATUS` tinyint(4) DEFAULT '0' COMMENT '销售计划状态 0-已启用',
  PRIMARY KEY (`SALE_PLAN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售主表';

-- ----------------------------
-- Records of deepureflow_sale_plan
-- ----------------------------

-- ----------------------------
-- Table structure for deepureflow_sale_plan_item
-- ----------------------------
DROP TABLE IF EXISTS `deepureflow_sale_plan_item`;
CREATE TABLE `deepureflow_sale_plan_item` (
  `SALE_PLAN_ITEM_ID` varchar(50) NOT NULL COMMENT '销售计划子项编号',
  `SALE_PLAN_ID` varchar(50) DEFAULT NULL COMMENT '销售计划编号',
  `SKU_ID` varchar(10) DEFAULT NULL COMMENT 'sku产品编号',
  `SKU_NAME` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `QUANTITY` int(20) DEFAULT NULL COMMENT '销售数量',
  `BASE_UNIT` varchar(20) DEFAULT NULL COMMENT '标准单位',
  `MIN_UNIT` varchar(20) DEFAULT NULL COMMENT '最小单位',
  PRIMARY KEY (`SALE_PLAN_ITEM_ID`),
  KEY `SALE_PLAN_ID` (`SALE_PLAN_ID`),
  CONSTRAINT `deepureflow_sale_plan_item_ibfk_1` FOREIGN KEY (`SALE_PLAN_ID`) REFERENCES `deepureflow_sale_plan` (`SALE_PLAN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售子项表';

-- ----------------------------
-- Records of deepureflow_sale_plan_item
-- ----------------------------

-- ----------------------------
-- Table structure for deepureflow_terminal_flow
-- ----------------------------
DROP TABLE IF EXISTS `deepureflow_terminal_flow`;
CREATE TABLE `deepureflow_terminal_flow` (
  `TERMINAL_FLOW_ID` varchar(50) NOT NULL COMMENT '销售计划编号',
  `TERMINAL_FLOW_DATE` datetime DEFAULT NULL COMMENT '销售计划时间',
  `TERMINAL_ID` varchar(50) DEFAULT NULL COMMENT '所属终端编号',
  `CREATE_BY` varchar(50) DEFAULT NULL COMMENT '创建人',
  `CREATE_AT` datetime DEFAULT NULL COMMENT '创建时间',
  `TERMINAL_FLOW_STATUS` tinyint(4) DEFAULT '0' COMMENT '销售计划状态 0-已启用',
  PRIMARY KEY (`TERMINAL_FLOW_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售主表';

-- ----------------------------
-- Records of deepureflow_terminal_flow
-- ----------------------------

-- ----------------------------
-- Table structure for deepureflow_terminal_flow_item
-- ----------------------------
DROP TABLE IF EXISTS `deepureflow_terminal_flow_item`;
CREATE TABLE `deepureflow_terminal_flow_item` (
  `TERMINAL_FLOW_ITEM_ID` varchar(50) NOT NULL COMMENT '销售计划子项编号',
  `TERMINAL_FLOW_ID` varchar(50) DEFAULT NULL COMMENT '销售计划编号',
  `SKU_ID` varchar(10) DEFAULT NULL COMMENT 'sku产品编号',
  `SKU_NAME` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `STOCK_QUANTITY` int(20) DEFAULT NULL COMMENT '剩余数量',
  `BASE_UNIT` varchar(20) DEFAULT NULL COMMENT '标准单位',
  `MIN_UNIT` varchar(20) DEFAULT NULL COMMENT '最小单位',
  PRIMARY KEY (`TERMINAL_FLOW_ITEM_ID`),
  KEY `SALE_PLAN_ID` (`TERMINAL_FLOW_ID`),
  CONSTRAINT `deepureflow_terminal_flow_item_ibfk_1` FOREIGN KEY (`TERMINAL_FLOW_ID`) REFERENCES `deepureflow_terminal_flow` (`TERMINAL_FLOW_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售子项表';

-- ----------------------------
-- Records of deepureflow_terminal_flow_item
-- ----------------------------

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `ID` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NAME` varchar(30) DEFAULT NULL COMMENT '分类名称',
  `CODE` varchar(30) DEFAULT NULL COMMENT '分类的编号，ERP传过来的',
  `HSCODE` varchar(30) DEFAULT NULL COMMENT '分类的国际编号，是人用于固定该分类的具体值',
  `LAYER` tinyint(4) DEFAULT NULL COMMENT '所属分类的层，根分类是第0层',
  `ACTIVATE_FLAG` tinyint(4) DEFAULT NULL COMMENT '分类标志激活',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父分类的ID',
  `DESCRIPTION` varchar(4000) DEFAULT NULL COMMENT '分类描述',
  `CREATE_AT` datetime DEFAULT NULL COMMENT '创建时间戳',
  `CREATE_BY` varchar(100) DEFAULT NULL COMMENT '创建人的ID，一般为某内部员工ID',
  `UPDATE_AT` datetime DEFAULT NULL COMMENT '更新该条目的时间戳',
  `SYNC_FLAG` tinyint(4) DEFAULT NULL COMMENT '同步标志',
  PRIMARY KEY (`ID`),
  KEY `INDEX_ACTIVATE_FLAG` (`ACTIVATE_FLAG`) USING BTREE,
  KEY `INDEX_PARENT_ID` (`PARENT_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=180128 DEFAULT CHARSET=utf8 COMMENT='产品分类表';

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES ('11', '营养保健食品', '11', null, '1', '1', '0', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('12', '日化品', '12', null, '1', '1', '0', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('13', '帝泊洱', '13', null, '1', '1', '0', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('14', '酒', '14', null, '1', '1', '0', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('15', '其他', '15', null, '1', '1', '0', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('16', '保健用品', '16', null, '1', '1', '0', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('17', '辅销品', '17', null, '1', '1', '0', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('18', '消费品', '18', null, '1', '1', '0', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1101', '功能保健食品', '1101', null, '2', '1', '11', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1102', '三通保健食品', '1102', null, '2', '1', '11', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1201', '护肤品', '1201', null, '2', '1', '12', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1202', '口腔护理品', '1202', null, '2', '1', '12', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1203', '日常护理品', '1203', null, '2', '1', '12', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1204', '家居护理品', '1204', null, '2', '1', '12', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1301', '茶板块', '1301', null, '2', '1', '13', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1302', '水板块', '1302', null, '2', '1', '13', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1401', '封坛', '1401', null, '2', '1', '14', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1402', '国台', '1402', null, '2', '1', '14', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1403', '金士', '1403', null, '2', '1', '14', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1404', '昇和坊', '1404', null, '2', '1', '14', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1405', '相邀', '1405', null, '2', '1', '14', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1406', '吉时酒', '1406', null, '2', '1', '14', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1407', '名仕', '1407', null, '2', '1', '14', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1501', '组合', '1501', null, '2', '1', '15', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1601', '纺织用品', '1601', null, '2', '1', '16', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1701', '推广工具', '1701', null, '2', '1', '17', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1702', '印刷品', '1702', null, '2', '1', '17', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('1801', '消费类食品', '1801', null, '2', '1', '18', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110101', '常心胶囊', '110101', null, '3', '1', '1101', '', null, 'ADMIN', '2015-11-22 23:09:19', null);
INSERT INTO `product_category` VALUES ('110102', '坤春胶囊', '110102', null, '3', '0', '1101', '', null, 'ADMIN', '2015-12-01 13:00:05', null);
INSERT INTO `product_category` VALUES ('110103', '九尊肝脂胶囊', '110103', null, '3', '1', '1101', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110104', '麦硒康冲剂', '110104', null, '3', '1', '1101', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110105', '人参源胶囊', '110105', null, '3', '1', '1101', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110106', '润生软胶囊', '110106', null, '3', '1', '1101', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110107', '减肥胶囊', '110107', null, '3', '1', '1101', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110108', '麦硒康口嚼片', '110108', null, '3', '1', '1101', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110109', '人参粉', '110109', null, '3', '1', '1101', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110110', '蛋白粉', '110110', null, '3', '1', '1101', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110111', '果蔬谷物蛋白粉', '110111', null, '3', '1', '1101', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110112', '胶原蛋白复合粉', '110112', null, '3', '1', '1101', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110113', '补钙食品', '110113', null, '3', '1', '1101', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110201', '芪参茶', '110201', null, '3', '1', '1102', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110202', '益生菌粉', '110202', null, '3', '1', '1102', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110203', '银杏叶滴丸', '110203', null, '3', '1', '1102', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('110204', '三通标准装', '110204', null, '3', '1', '1102', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120101', '帝蘭净白亮颜系列', '120101', null, '3', '1', '1201', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120102', '帝蘭凝时焕颜系列', '120102', null, '3', '1', '1201', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120103', '帝蘭水润亲颜系列', '120103', null, '3', '1', '1201', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120104', '男士经典尊容系列', '120104', null, '3', '1', '1201', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120105', '诺杰傲系列', '120105', null, '3', '1', '1201', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120106', '夏梵娜系列', '120106', null, '3', '1', '1201', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120107', '易得康系列', '120107', null, '3', '1', '1201', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120108', '帝蘭漫妮系列', '120108', null, '3', '1', '1201', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120109', '帝蘭滢润系列', '120109', null, '3', '1', '1201', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120110', '帝蘭青春密码系列', '120110', null, '3', '1', '1201', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120201', '牙膏', '120201', null, '3', '1', '1202', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120301', '便携装', '120301', null, '3', '1', '1203', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120302', '金丽娇妍系列', '120302', null, '3', '1', '1203', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120303', '丹参洗护系列', '120303', null, '3', '1', '1203', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('120401', '舒宜家洁系列', '120401', null, '3', '1', '1204', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('130101', '普洱茶珍', '130101', null, '3', '1', '1301', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('130201', '小分子水', '130201', null, '3', '1', '1302', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('140101', '封坛酒', '140101', null, '3', '1', '1401', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('140201', '国台十五年', '140201', null, '3', '1', '1402', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('140202', '国台五年', '140202', null, '3', '1', '1402', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('140203', '适宜国台酒', '140203', null, '3', '1', '1402', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('140204', '坛装酒', '140204', null, '3', '1', '1402', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('140205', '龙酒', '140205', null, '3', '1', '1402', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('140301', '金士酒', '140301', null, '3', '1', '1403', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('140401', '昇和坊酒', '140401', null, '3', '1', '1404', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('140501', '相邀酒', '140501', null, '3', '1', '1405', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('140601', '吉时开坛', '140601', null, '3', '1', '1406', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('140701', '名仕酒', '140701', null, '3', '1', '1407', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('150101', '帝泊洱组合', '150101', null, '3', '1', '1501', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('150102', '辅销品组合', '150102', null, '3', '1', '1501', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('150103', '混搭组合', '150103', null, '3', '1', '1501', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('150104', '酒组合', '150104', null, '3', '1', '1501', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('150105', '日化品组合', '150105', null, '3', '1', '1501', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('150106', '保健品组合', '150106', null, '3', '1', '1501', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('150107', '消费类食品组合', '150107', null, '3', '1', '1501', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('160101', '袜子', '160101', null, '3', '1', '1601', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('170101', '产品工具', '170101', null, '3', '1', '1701', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('170102', '品牌工具', '170102', null, '3', '1', '1701', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('170201', '光盘', '170201', null, '3', '1', '1702', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('170202', '海报', '170202', null, '3', '1', '1702', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('170203', '刊物', '170203', null, '3', '1', '1702', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('170204', '业务单据', '170204', null, '3', '1', '1702', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('170205', '折页', '170205', null, '3', '1', '1702', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('180101', '有机食品', '180101', null, '3', '1', '1801', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('180102', '茶珍营养米', '180102', null, '3', '1', '1801', null, null, 'initialData', null, null);
INSERT INTO `product_category` VALUES ('180117', 'JU-sprint 4 ', '7228', null, '2', '1', '11', '', '2015-12-21 00:00:00', null, null, null);
INSERT INTO `product_category` VALUES ('180119', 'JU-sprint 4 ', '7228', null, '2', '1', '11', '', '2015-12-21 00:00:00', null, null, null);
INSERT INTO `product_category` VALUES ('180123', '1111', '1111', null, '3', '0', '1401', '', '2016-01-07 00:00:00', null, null, null);
INSERT INTO `product_category` VALUES ('180125', '777', '777', null, '3', '0', '1301', '', '2016-01-07 00:00:00', null, null, null);
INSERT INTO `product_category` VALUES ('180127', '0000', '0000', null, '3', '0', '1302', '', '2016-01-07 00:00:00', null, null, null);

-- ----------------------------
-- Table structure for product_sku
-- ----------------------------
DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE `product_sku` (
  `ID` int(30) NOT NULL AUTO_INCREMENT COMMENT '商品SKU ID',
  `CODE` varchar(100) DEFAULT NULL COMMENT 'ERP推过来的商品SKU ID',
  `NAME` varchar(100) DEFAULT NULL COMMENT '商品SKU名称',
  `PRODUCT_TYPE` tinyint(4) DEFAULT '0' COMMENT '0-单品,1-套餐',
  `ACTIVATE_FLAG` tinyint(4) DEFAULT '1' COMMENT '激活该商品SKU的标志位 1-激活 0-未激活',
  `ERP_UNIT` varchar(40) DEFAULT NULL COMMENT 'ERP 传过来的规格单位',
  `ERP_MIN_UNIT` varchar(40) DEFAULT NULL COMMENT 'ERP最小单位',
  `CREATE_AT` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_BY` varchar(100) DEFAULT NULL COMMENT '创建人',
  `CATEGORY_CODE` varchar(255) DEFAULT NULL COMMENT '产品品类Code',
  PRIMARY KEY (`ID`),
  KEY `INDEX_ACTIVATE_FLAG` (`ACTIVATE_FLAG`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=950 DEFAULT CHARSET=utf8 COMMENT='商品SKU表';

-- ----------------------------
-- Records of product_sku
-- ----------------------------
INSERT INTO `product_sku` VALUES ('1', '10000015', '40ml瓶体，G40Y-008-02', '0', '1', '只', null, '2015-11-13 20:27:36', null, null);
INSERT INTO `product_sku` VALUES ('3', '10000308', '天士力丹参牙膏（晚安装）,140g/支，1支/盒，100盒/箱', '0', '0', '支', null, '2015-11-13 20:27:36', 'FUWW', null);
INSERT INTO `product_sku` VALUES ('5', '10002176', '芪参茶普洱型小袋,袋', '0', '1', '袋', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('7', '10002177', '芪参茶普洱型30袋装,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('9', '10002178', '芪参茶普洱型铁盒80袋装,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'FUWW', null);
INSERT INTO `product_sku` VALUES ('11', '10002179', '麦硒康口嚼片,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('13', '10002180', '人参源胶囊 60粒/盒,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('15', '10002181', '九尊肝脂胶囊 80粒/盒,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('17', '10002182', '常心胶囊,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('19', '10002183', '坤春胶囊,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('21', '10002184', '减肥胶囊,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('23', '10002185', '减肥胶囊（样品）,瓶', '0', '0', '瓶', null, '2015-11-13 20:27:36', 'ADMIN', null);
INSERT INTO `product_sku` VALUES ('25', '10002186', '益生菌粉,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('27', '10002187', '蛋白粉,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('29', '10002188', '麦硒康冲剂,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('31', '10002189', '益生菌粉（30袋装）,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('33', '10002190', '银杏叶滴丸 360粒/盒,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('35', '10002191', '保健便携盒,box', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('37', '10002192', '金士力佳友司徽,piece', '0', '1', '片', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('39', '10002193', '金士力佳友纸杯,set', '0', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('41', '10002194', '走进金士力光碟套盘,set', '0', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('43', '10002195', '帝泊洱折页,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('45', '10002196', '金士力佳友简装笔记本(送笔),册', '0', '1', '册', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('47', '10002197', '金士力佳友精品杯,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('49', '10002198', '从“制造”到“质造”,册', '0', '1', '册', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('51', '10002199', '体态减龄套装折页,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('53', '10002200', '大健康颂全球营销大会光盘套装,套', '0', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('55', '10002201', '硒与健康50问,册', '0', '1', '册', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('57', '10002202', '麦硒康折页,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('59', '10002203', '天士力现代中药城三维虚拟全景漫游光盘,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('61', '10002204', '金士力佳友塑料袋,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('63', '10002205', '金士力佳友纸袋,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('65', '10002206', '《卓越》第001期,本', '0', '1', '本', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('67', '10002207', '《卓越》第003期,本', '0', '1', '本', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('69', '10002208', '《卓越》第004期,本', '0', '1', '本', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('71', '10002209', '丹参伴侣牙膏折页,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('73', '10002210', '平衡生活手册2013版,册', '0', '1', '册', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('75', '10002211', '产品折页2013版,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('77', '10002212', '事业手册2013版,本', '0', '1', '本', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('79', '10002213', '《卓越》第005期,本', '0', '1', '本', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('81', '10002214', '天士力19周年活动集锦光盘,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('83', '10002215', '天士力19周年活动集锦,本', '0', '1', '本', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('85', '10002216', '《卓越》第006期赠天士力19周年活动集锦,套', '0', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('87', '10002217', '三通标准装提袋,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('89', '10002218', '果蔬谷物蛋白粉折页,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('91', '10002219', '麦硒康光盘,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('93', '10002220', '企业光盘2013版,套', '0', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('95', '10002221', '2013年金鹰盛典光盘,套', '0', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('97', '10002222', '天士力报,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('99', '10002223', '水润亲颜系列提袋,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('101', '10002224', '水润亲颜系列折页,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('103', '10002225', '国际商报2014版,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('105', '10002226', '金鹰盛典特刊,本', '0', '1', '本', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('107', '10002227', '分销时代,本', '0', '1', '本', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('109', '10002228', '金士力皮肤感应测试仪,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('111', '10002229', 'C胞活力水产品手册,本', '0', '1', '本', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('113', '10002230', '筑梦大健康,本', '0', '1', '本', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('115', '10002231', '大健康颂全球经销商大会光盘套装,套', '0', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('117', '10002232', '《卓越》第007期,册', '0', '1', '册', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('119', '10002233', '产品折页2014版,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('121', '10002234', '帝泊洱C胞活力小分子水6支装外箱,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('123', '10002235', 'SC-386C立式透明门饮料柜,台', '0', '1', '台', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('125', '10002236', '帝泊洱茶珍体验装（清香型）,袋', '0', '1', '袋', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('127', '10002237', '产品手册2014版,册', '0', '1', '册', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('129', '10002238', '平衡生活手册2014版,册', '0', '1', '册', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('131', '10002239', '麦硒康冲剂体验装,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('133', '10002240', '舒宜植物倍护洗衣液单页,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('135', '10002241', '《卓越》第008期,册', '0', '1', '册', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('137', '10002242', '凝时焕颜新折页,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('139', '10002243', '2014金鹰盛典光盘,套', '0', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('141', '10002244', '2014金鹰盛典特刊,册', '0', '1', '册', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('143', '10002245', '荧光手电筒套盒,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('145', '10002246', '产品折页2015版,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('147', '10002247', '帝蘭丹参精粹沐浴系列折页,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('149', '10002248', '分销时代2015年第1期,册', '0', '1', '册', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('151', '10002249', '金士力佳友产品介绍彩页合集,套', '0', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('153', '10002250', '水质快速分析盒（C胞活力试验工具）,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('155', '10002251', '《大健康导报》第119期专刊,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('157', '10002252', '《卓越》第009期,本', '0', '1', '本', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('159', '10002253', '金士力佳友视频集锦,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('161', '10002254', '法国鹰演健康扫描,次', '0', '1', '次', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('163', '10002255', '享“瘦”随身记,本', '0', '1', '本', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('165', '10002256', '享“瘦”随身袋,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('167', '10002257', '计步器,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('169', '10002258', '卷尺,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('171', '10002259', '体重秤,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('173', '10002260', '丹参精粹手提袋,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('175', '10002261', '一星——健康手环,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('177', '10002262', '二星——血氧仪,台', '0', '1', '台', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('179', '10002263', '二星——绒布袋,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('181', '10002264', '三星——定制旅行箱,个', '0', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('183', '10002265', '明星——平板电脑,台', '0', '1', '台', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('185', '10002266', '2015金士力佳友金鼎之约颁奖盛典盛况光盘,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('187', '10002267', '礼品山参,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('189', '10002268', '中秋定制月饼,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('191', '10002269', '2015金鹰盛典内场票,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('193', '10002270', '2015金鹰盛典看台票,张', '0', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('195', '10002271', '丹参伴侣牙膏,套', '0', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('197', '10002272', '舒宜植物倍护洗衣液,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('199', '10002273', '帝蘭凝时焕颜精华冻干片,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('201', '10002274', '帝蘭凝时焕颜茶萃醒肤面膜,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('203', '10002275', '帝蘭凝时焕颜眼霜,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'ADMIN', null);
INSERT INTO `product_sku` VALUES ('205', '10002276', '金丽娇妍泡沫洁面乳170G,支', '0', '1', '支', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('207', '10002277', '金丽娇妍沐浴啫喱170G,支', '0', '1', '支', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('209', '10002278', '金丽娇妍营养精华啫喱170G,支', '0', '1', '支', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('211', '10002279', '帝蘭丹参精粹洗发露,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('213', '10002280', '帝蘭丹参精粹润发素,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('215', '10002281', '纳雅天水,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('217', '10002282', '帝蘭水润亲颜洁面乳,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('219', '10002283', '帝蘭水润亲颜柔肤水,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('221', '10002284', '帝蘭水润亲颜眼部精华霜,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('223', '10002285', '帝蘭水润亲颜锁水保湿霜,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('225', '10002286', '帝蘭水润亲颜祛角质霜,支', '0', '1', '支', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('227', '10002287', '帝蘭水润亲颜嫩肤精华液,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('229', '10002288', '帝蘭水润亲颜盈润面膜,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('231', '10002289', '帝蘭漫妮青春驻颜原液,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('233', '10002290', '帝蘭凝时焕颜液,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('235', '10002291', '帝蘭青春密码原液体验装,支', '0', '1', '支', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('237', '10002292', '帝蘭丹参精粹沐浴露,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('239', '10002293', '帝蘭丹参精粹润肤乳,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('241', '10002294', '帝蘭滢润舒颜调理露,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('243', '10002295', '帝蘭凝时焕颜泡沫洁面乳,支', '0', '1', '支', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('245', '10002296', '茶珍营养米,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('247', '10002297', '天养稻有机大米,箱', '0', '1', '箱', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('249', '10002298', '帝泊洱即溶普洱茶珍—礼盒,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('251', '10002299', '帝泊洱即溶普洱茶珍—宝石蓝商务装（清香型）,罐', '0', '1', '罐', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('253', '10002300', '帝泊洱即溶普洱茶珍—中国红小礼盒（古茶型）,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('255', '10002301', '复合胶原蛋白粉,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'FUWW', null);
INSERT INTO `product_sku` VALUES ('257', '10002302', '果蔬谷物蛋白粉,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'ADMIN', null);
INSERT INTO `product_sku` VALUES ('259', '10002303', '帝泊洱即溶普洱茶珍（甘醇型）100袋,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('261', '10002304', '帝泊洱即溶普洱茶珍（甘醇型）100袋,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('263', '10002305', '帝泊洱即溶普洱茶珍—礼盒（古茶型+甘醇型）,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('265', '10002306', '帝泊洱即溶普洱茶珍—礼盒（古茶型+甘醇型）,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('267', '10002307', '蜜制速溶人参粉,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('269', '10002308', '国台十五年陈酿单瓶,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('271', '10002309', '国台十五年陈酿（箱）,箱', '0', '1', '箱', null, '2015-11-13 20:27:36', 'ADMIN', null);
INSERT INTO `product_sku` VALUES ('273', '10002310', 'A5,箱', '0', '1', '箱', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('275', '10002311', 'A5(现场提货),箱', '0', '1', '箱', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('277', '10002312', 'A5单瓶,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('279', '10002313', 'A15,箱', '0', '1', '箱', null, '2015-11-13 20:27:36', 'FUWW', null);
INSERT INTO `product_sku` VALUES ('281', '10002314', 'A15(现场提货),箱', '0', '1', '箱', null, '2015-11-13 20:27:36', 'FUWW', null);
INSERT INTO `product_sku` VALUES ('283', '10002315', 'A15单瓶,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('285', '10002316', '帝泊洱C胞活力小分子水单箱,箱', '0', '0', '箱', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('287', '10002317', '适宜国台酒酱香18°（箱）,箱', '0', '1', '箱', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('289', '10002318', '适宜国台酒酱香18°(瓶),瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('291', '10002319', '国台龙瓶礼品酒,瓶', '0', '1', '瓶', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('293', '10002320', '国台黄坛酒5L,箱', '0', '1', '箱', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('295', '10002321', '吉时开坛酒-现场提货,箱', '0', '1', '箱', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('297', '10002322', '吉时开坛酒,箱', '0', '1', '箱', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('299', '10002323', '凝时焕颜加量装,套', '0', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('301', '10002324', '银杏叶滴丸大包装,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('303', '10002325', '金丽娇妍个人护理基础系列套装,套', '0', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('305', '10002326', '三通标准装,套', '0', '1', '套', null, '2015-11-13 20:27:36', 'ADMIN', null);
INSERT INTO `product_sku` VALUES ('307', '10002327', '丹参伴侣牙膏赠折页,套', '0', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('309', '10002330', '柠檬酸苹果酸钙粉,盒', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('311', '10002331', '名仕精品珍藏酒（斤）,斤', '0', '1', 'kg', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('313', '10002332', '名仕陈年珍藏酒（斤）,斤', '0', '1', 'kg', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('315', '10002401', '帝泊洱即溶普洱茶珍（清香型）100袋,盒,帝泊洱', '0', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('317', '40000001', '大健康示范家庭卡副卡,张', '1', '1', '张', null, '2015-11-13 20:27:36', 'FUWW', null);
INSERT INTO `product_sku` VALUES ('319', '40000002', '大健康示范家庭卡副卡（逆生抗衰老）,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('321', '40000003', '丹参伴侣牙膏折页套,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('323', '40000004', '体态减龄套装折页十赠五,张', '1', '1', '张', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('325', '40000005', '体态减龄套装,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('327', '40000006', '果蔬谷物蛋白粉折页十赠五,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('329', '40000007', '分销时代1000本,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('331', '40000008', '金士力皮肤感应测试仪（50个）,个', '1', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('333', '40000009', '筑梦大健康（100本）,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('335', '40000010', '适宜国台酒酱香18°买10箱送1箱,箱', '1', '1', '箱', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('337', '40000011', '漫妮金卡B,套', '1', '0', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('339', '40000012', '漫妮金卡B,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('341', '40000013', '漫妮钻卡B,套,金士力佳友日化一厂(宏泽)', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('343', '40000014', '漫妮钻卡B,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('345', '40000015', '秋冬进补保健金卡,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('347', '40000016', '茶酒钻卡,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('349', '40000017', '名仕陈年珍藏酒（坛）,坛', '1', '1', '坛', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('351', '40000018', '名仕精品珍藏酒（坛）,坛', '1', '1', '坛', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('353', '40000019', '帝蘭青春密码原液体验装套装,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('355', '40000020', 'C胞活力40箱,箱', '1', '1', '箱', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('357', '40000021', '金士力佳友产品介绍彩页合集（100套）,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('359', '40000022', '大健康示范家庭卡（逆生抗衰老）,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('361', '40000023', '帝蘭漫妮青春驻颜原液（五支装）,盒', '1', '1', '盒', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('363', '40000024', '金士力佳友视频集锦100套,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('365', '40000025', '帝蘭丹参沐浴10套赠1套,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('367', '40000026', '帝蘭丹参沐浴50套赠10套,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('369', '40000027', '“好米难求”组合套装,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('371', '40000028', '凝时焕颜加乘修护套组,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('373', '40000029', '凝时焕颜加乘修护套组（三套装）,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('375', '40000030', '碧蓝之梦A主卡,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('377', '40000031', '碧蓝之梦副卡,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('379', '40000032', '碧蓝之梦C主卡,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('381', '40000033', '碧蓝之梦B主卡,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('383', '40000034', '碧蓝之梦D主卡,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('385', '40000035', '国台五年超值套,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('387', '40000036', '国台五年超值套-现场提货,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('389', '40000037', '国台十五年超值套,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('391', '40000038', '国台十五年超值套-现场提货,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('393', '40000039', '“硒硒”相关A套,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('395', '40000040', '“硒硒”相关B套,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('397', '40000041', '碧蓝之梦“享瘦”至尊主卡,个', '1', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('399', '40000042', '碧蓝之梦“享瘦”至尊男士副卡,个', '1', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('401', '40000043', '碧蓝之梦“享瘦”至尊女士副卡,个', '1', '1', '个', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('403', '40000044', '碧蓝之梦年度消费健康礼包（限老会员重消购买）,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('405', '40000045', '碧蓝之梦年度消费靓丽礼包（限老会员重消购买）,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('407', '40000046', '碧蓝之梦年度消费内调外养礼包（限老会员重消购买）,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('409', '40000047', '循环通组合双节特惠大礼包,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('411', '40000048', '血糖稳组合双节特惠大礼包,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('413', '40000049', '免疫强组合双节特惠大礼包,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('415', '40000050', '青春美组合双节特惠大礼包,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('417', '40000051', '体重控组合双节特惠大礼包,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('419', '40000052', '全家福组合双节特惠大礼包,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('421', '40000053', '美酒香茶组合双节特惠大礼包,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('423', '40000054', '大健康家园社区店产品体验包组合,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('425', '40000055', '抗衰老组合双节特惠大礼包A,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('427', '40000056', '抗衰老组合双节特惠大礼包B,套', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('429', '40000057', '运费收入', '0', '1', '项', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('431', '40000058', '帝蘭水润亲颜恒润套组,套,金士力佳友（天津）有限公司', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('433', '40000059', '帝蘭水润亲颜极润套组,套,金士力佳友（天津）有限公司', '1', '1', '套', null, '2015-11-13 20:27:36', 'initialData', null);
INSERT INTO `product_sku` VALUES ('435', '10002504', '蒂蘭丹参精粹洗发露家庭装-测试数据', '0', '0', '瓶', null, '2015-11-18 10:04:12', 'FUWW', null);
INSERT INTO `product_sku` VALUES ('437', '40000060', '帝蘭丹参精粹洗护发家庭装套组,套,金士力佳友日化用品有限公司', '1', '1', '套', null, '2015-11-26 11:57:53', 'ADMIN', null);
INSERT INTO `product_sku` VALUES ('439', '10002337', '芪参茶 20袋/盒,盒,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('441', '10002338', '芪参茶体验装,box,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('443', '10002339', '普洱型芪参茶20袋装,盒,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('445', '10002340', '芪参茶绿茶型30袋装,盒,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('447', '10002341', '芪参茶普洱型30袋装,盒,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('449', '10002342', '芪参茶绿茶型铁盒80袋装,盒,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('451', '10002343', '芪参茶普洱型铁盒80袋装,盒,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('453', '10002344', '人参源胶囊 60粒/盒,瓶,金士力健康用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('455', '10002345', '人参源胶囊 60粒/盒,瓶,金士力健康用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('457', '10002346', '九尊肝脂胶囊 80粒/盒,瓶,金士力健康用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('459', '10002347', '常心胶囊,瓶,金士力健康用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('461', '10002348', '坤春胶囊,瓶,金士力健康用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('463', '10002349', '润生软胶囊 90粒/盒,bottle,金士力健康用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('465', '10002350', '润生软胶囊大包装,瓶,金士力健康用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('467', '10002351', '润生软胶囊90粒,瓶,金士力健康用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('469', '10002352', '润生软胶囊90粒,瓶,金士力健康用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('471', '10002353', '益生菌粉,盒,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('473', '10002354', '金士力蛋白粉,盒,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('475', '10002355', '金士力蛋白粉,盒,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('477', '10002356', '银杏叶滴丸 360粒/盒,瓶,金士力健康用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('479', '10002357', '吉时酒单瓶,瓶,金士力健康用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('481', '10002358', '三通标准装,套,金士力健康用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('483', '10002359', '健康美容手册,book,金士力佳友（天津）有限公司', '0', '1', '本', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('485', '10002360', '金鹰盛典看台票,张,金士力佳友（天津）有限公司', '0', '1', '张', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('487', '10002361', '金鹰盛典内场票,张,金士力佳友（天津）有限公司', '0', '1', '张', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('489', '10002362', '金鼎之约内场票,张,金士力佳友（天津）有限公司', '0', '1', '张', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('491', '10002363', '金鼎之约看台票,张,金士力佳友（天津）有限公司', '0', '1', '张', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('493', '10002364', '帝蘭纳米银牙膏140G,支,金士力佳友日化用品有限公司', '0', '1', '支', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('495', '10002365', '帝蘭三七植物精华牙膏,支,金士力佳友日化用品有限公司', '0', '1', '支', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('497', '10002366', '金士力美体塑身乳剂,piece,金士力佳友日化用品有限公司', '0', '1', '片', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('499', '10002367', '金士力美体塑身乳剂(盒装),盒,金士力佳友日化用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('501', '10002368', '金士力睡康保健乳(盒装),盒,金士力佳友日化用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('503', '10002369', '活性微生物袜子男,pair,金士力佳友（天津）有限公司', '0', '1', '双', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('505', '10002370', '夏梵娜保湿爽肤水,瓶,金士力佳友日化用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('507', '10002371', '夏梵娜保湿乳液,瓶,金士力佳友日化用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('509', '10002372', '夏梵娜亮白营养霜,瓶,金士力佳友日化用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('511', '10002373', '夏梵娜修护精华啫喱,支,金士力佳友日化用品有限公司', '0', '1', '支', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('513', '10002374', '夏梵娜活肤眼霜,bottle,金士力佳友日化用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('515', '10002375', '夏梵娜均衡保湿洁面乳,支,金士力佳友日化用品有限公司', '0', '1', '支', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('517', '10002376', '夏梵娜保湿滋养面膜,piece,金士力佳友日化用品有限公司', '0', '1', '片', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('519', '10002377', '帝蘭凝时焕颜精华冻干片,盒,金士力佳友日化用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('521', '10002378', '帝蘭水润亲颜柔肤水,box,金士力佳友日化用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('523', '10002379', '帝蘭水润亲颜眼部精华,box,金士力佳友日化用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('525', '10002380', '帝蘭净白亮颜眼部精华素,瓶,金士力佳友日化用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('527', '10002381', '帝蘭净白亮颜隔离乳液,瓶,金士力佳友日化用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('529', '10002382', '舒宜纳米银牙膏,piece,金士力佳友日化用品有限公司', '0', '1', '片', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('531', '10002383', '金丽娇妍泡沫洁面乳170G,支,金士力佳友日化用品有限公司', '0', '1', '支', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('533', '10002384', '金丽娇妍沐浴啫喱170G,支,金士力佳友日化用品有限公司', '0', '1', '支', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('535', '10002385', '金丽娇妍营养精华啫喱170G,支,金士力佳友日化用品有限公司', '0', '1', '支', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('537', '10002386', '金丽娇妍均衡调理洗发露,瓶,金士力佳友日化用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('539', '10002387', '金丽娇妍均衡调理润发素,瓶,金士力佳友日化用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('541', '10002388', '帝蘭丹参精粹洗发露,瓶,金士力佳友（天津）有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('543', '10002389', '帝蘭丹参精粹润发素,瓶,金士力佳友（天津）有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('545', '10002390', '舒爽清凉洗发露,瓶,金士力佳友日化用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('547', '10002391', '帝蘭丹参精粹洗发露家庭装,瓶,金士力佳友日化用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('549', '10002392', '帝蘭丹参精粹润发素家庭装,瓶,金士力佳友日化用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('551', '10002393', '蛋白质粉,box,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('553', '10002394', '益生菌儿童,box,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('555', '10002395', '益生菌女士,box,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('557', '10002396', '益生菌男士,box,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('559', '10002397', '益生菌中老年,box,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('561', '10002398', '帝泊洱即溶普洱茶珍--（宝石蓝）旅行装,罐,帝泊洱', '0', '1', '罐', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('563', '10002399', '帝泊洱即溶普洱茶珍--（宝石蓝）商务装,罐,帝泊洱', '0', '1', '罐', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('565', '10002400', '帝泊洱（清香型）90袋送10袋,盒,帝泊洱', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('567', '10002402', '金酱相邀酒,箱,金士酒业', '0', '1', '箱', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('569', '10002403', '相邀酒单瓶,瓶,金士酒业', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('571', '10002404', '国台五年精品单瓶,瓶,金士酒业', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('573', '10002405', '金士酒（箱）,箱,金士酒业', '0', '1', '箱', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('575', '10002406', '国台五年精品（箱）,箱,金士酒业', '0', '1', '箱', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('577', '10002407', '国台酒——陈年酱香 佳友珍藏 现提,箱,金士酒业', '0', '1', '箱', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('579', '10002408', '帝泊洱C胞活力小分子水单箱,箱,帝泊洱', '0', '1', '箱', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('581', '10002409', '帝泊洱C胞活力小分子水单箱,箱,帝泊洱', '0', '1', '箱', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('583', '10002410', '夏梵娜基础护肤五件套礼盒,set,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('585', '10002411', '夏梵娜基础护肤试用装,set,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('587', '10002412', '金丽娇妍个人护理基础系列礼盒,set,金士力佳友（天津）有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('589', '10002413', '帝蘭凝时焕颜系列,box,金士力佳友日化用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('591', '10002414', '凝时焕颜加量装,套,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('593', '10002415', '帝蘭水润亲颜系列,套,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('595', '10002416', '帝蘭水润亲颜系列,盒,金士力佳友日化用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('597', '10002417', '帝蘭净白亮颜系列礼盒,套,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('599', '10002418', '男士经典尊容系列,set,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('601', '10002419', '帝蘭凝时焕颜系列半价购冻干片,套,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('603', '10002420', '帝蘭水润亲颜系列送柔肤水,套,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('605', '10002421', '帝蘭凝时焕颜精华冻干片买5送1,套,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('607', '10002422', '银杏叶滴丸大包装,盒,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('609', '10002423', '芪参茶大包装,盒,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('611', '10002424', '吉时酒,箱,金士力佳友（天津）有限公司', '0', '1', '箱', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('613', '10002425', '普洱型芪参茶大包装,盒,金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('615', '10002426', '吉时酒5+1促销礼包,套,金士力佳友（天津）有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('617', '10002427', '净白亮颜促销装,set,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('619', '10002428', '水润亲颜促销装,set,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('621', '10002429', '帝蘭商务旅行便携装-粉色,套,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('623', '10002430', '帝蘭商务旅行便携装-透明,套,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('625', '10002431', '益生菌买5送1,套,金士力健康用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('627', '10002432', '吉时酒3送相邀,套,金士酒业', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('629', '10002433', '相邀酒3送1吉时酒,set,金士酒业', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('631', '10002434', '吉时酒3箱送1箱,set,金士酒业', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('633', '10002435', '帝蘭水润亲颜系列送帝蘭水润亲颜系列试用装,套,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('635', '10002436', '金士力美体塑身乳剂上市促销1,set,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('637', '10002437', '金士力美体塑身乳剂上市促销2,set,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('639', '10002438', '芪参茶大包装促销1,set,金士力健康用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('641', '10002439', '芪参茶大包装促销2,set,金士力健康用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('643', '10002440', '男士经典尊容系列赠九尊肝脂胶囊,set,金士力佳友（天津）有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('645', '10002441', '净白亮颜系列赠柔肤水,套,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('647', '10002442', '女性保健套餐赠益生菌,套,金士力佳友（天津）有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('649', '10002443', '帝泊洱即溶普洱茶珍--（宝石蓝）旅行装三赠一,罐,帝泊洱', '0', '1', '罐', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('651', '10002444', '帝泊洱即溶普洱茶珍--（宝石蓝）商务装三赠一,套,帝泊洱', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('653', '10002445', '金丽娇妍个人护理基础系列套装,套,金士力佳友（天津）有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('655', '10002446', '三通标准装,套,金士力健康用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('657', '10002447', '三通标准装,套,金士力健康用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('659', '10002448', '补硒助三通套装,套,金士力佳友（天津）有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('661', '10002449', '漫妮普卡B,套,金士力佳友日化一厂(宏泽)', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('663', '10002450', '漫妮普卡C,套,金士力佳友日化一厂(宏泽)', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('665', '10002451', '凝时焕颜新品推广套装,套,金士力佳友日化用品有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('667', '10002452', '新春礼包一,套,金士力佳友（天津）有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('669', '10002453', '新春礼包二,套,金士力佳友（天津）有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('671', '10002454', '大健康示范家庭卡,套,金士力佳友（天津）有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('673', '10002455', '减脂套组一,套,金士力佳友（天津）有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('675', '10002456', '减脂套组二,套,金士力佳友（天津）有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('677', '10002457', '减脂套组三,套,金士力佳友（天津）有限公司', '0', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('679', '10002458', '改善睡眠粉，盒，金士力健康用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('681', '10002459', '新生恒牙宝牙膏（蓝莓香型），盒，金士力佳友日化用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('683', '10002460', '新生恒牙宝牙膏（草莓香型），盒，金士力佳友日化用品有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('685', '10002461', '润生软胶囊,瓶,广东仙乐制药有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('687', '10002462', '吉爽饮料,瓶,天津和治药业有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('689', '10002463', '好仕饮料,瓶,天津和治药业有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('691', '10002464', '2015金鹰盛典光盘（含盒）,盒,金士力佳友（天津）有限公司', '0', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('693', '10002465', '2015金鹰盛典特刊,本,金士力佳友（天津）有限公司', '0', '1', '本', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('695', '10002466', '帝蘭青春驻颜原液,瓶,金士力佳友日化用品有限公司', '0', '1', '瓶', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('697', '10002467', '天养稻有机大米（专供）,箱,黑龙江双城市岗子峪米业有限公司', '0', '1', '箱', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('699', '10002503', '银杏叶滴丸-测试1', null, '1', '瓶', null, '2016-01-12 09:12:34', null, null);
INSERT INTO `product_sku` VALUES ('701', '10002507', '丹参精粹护发素家庭装', null, '1', '瓶', null, '2016-01-12 09:12:34', null, null);
INSERT INTO `product_sku` VALUES ('703', '10002510', '维生素', '0', '0', '盒', null, '2016-01-12 09:12:34', 'ADMIN', null);
INSERT INTO `product_sku` VALUES ('705', '40000061', '大健康家园社区店产品体验包组合1,套,金士力佳友（天津）有限公司', '1', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('707', '40000062', '大健康家园社区店产品体验包组合2,套,金士力佳友（天津）有限公司', '1', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('709', '40000063', '帝蘭青春驻颜原液（五支装）,盒,金士力佳友日化用品有限公司', '1', '1', '盒', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('711', '40000064', '帝蘭青春驻颜原液金卡B,套,金士力佳友日化用品有限公司', '1', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('713', '40000065', '帝蘭青春驻颜原液钻卡B,套,金士力佳友日化用品有限公司', '1', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('715', '40000066', '大健康家园社区店产品体验包组合1,套,金士力佳友（天津）有限公司', '1', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('717', '40000067', '大健康家园社区店产品体验包组合2,套,金士力佳友（天津）有限公司', '1', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('719', '40000068', '高端社区店产品礼包,套,金士力佳友（天津）有限公司', '1', '1', '套', null, '2016-01-12 09:12:34', 'initialData', null);
INSERT INTO `product_sku` VALUES ('949', '10002524', '测试接口用物料，请勿下销售订单', null, '0', '盒', null, '2016-02-03 16:43:37', null, null);

-- ----------------------------
-- Table structure for security_data_resource
-- ----------------------------
DROP TABLE IF EXISTS `security_data_resource`;
CREATE TABLE `security_data_resource` (
  `ID` int(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `NAME` varchar(60) NOT NULL COMMENT '名称',
  `TYPE` tinyint(1) DEFAULT NULL COMMENT '类型,1订单 2退货，3换货，4网币申购，5 网币申领，6.折扣申领 7.转账记录 8 电子钱包查询，9 电子钱包对账单, 10 会员',
  `STATEMENT` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of security_data_resource
-- ----------------------------
INSERT INTO `security_data_resource` VALUES ('1', '查看全部经销商', '1', 'com.tasly.deepureflow.client.IAgentMapper.queryAllAgent');
INSERT INTO `security_data_resource` VALUES ('2', '查看大区经销商', '1', 'com.tasly.deepureflow.client.IAgentMapper.queryAgentForRegional');
INSERT INTO `security_data_resource` VALUES ('3', '查看办事处经销商', '1', 'com.tasly.deepureflow.client.IAgentMapper.queryAgentForOffice');
INSERT INTO `security_data_resource` VALUES ('4', '查看本人经销商', '1', 'com.tasly.deepureflow.client.IAgentMapper.queryAgentForSaleMan');
INSERT INTO `security_data_resource` VALUES ('5', '查看全部终端', '2', 'com.tasly.deepureflow.client.ITerminalMapper.queryAllTerminal');
INSERT INTO `security_data_resource` VALUES ('6', '查看大区终端', '2', 'com.tasly.deepureflow.client.ITerminalMapper.queryTerminalForRegional');
INSERT INTO `security_data_resource` VALUES ('7', '查看办事处终端', '2', 'com.tasly.deepureflow.client.ITerminalMapper.queryTerminalForOffice');
INSERT INTO `security_data_resource` VALUES ('8', '查看终端', '2', 'com.tasly.deepureflow.client.ITerminalMapper.queryTerminal');
INSERT INTO `security_data_resource` VALUES ('9', '查看所有终端销售计划', '3', 'planflow:terimalplan');
INSERT INTO `security_data_resource` VALUES ('10', '查看所有经销商产品流向', '4', 'planflow:agentproduct');
INSERT INTO `security_data_resource` VALUES ('11', '查看所有终端产品流向', '5', 'planflow:termianlproduct');
INSERT INTO `security_data_resource` VALUES ('12', '查看所有内部员工', '6', 'system:allemployee');
INSERT INTO `security_data_resource` VALUES ('13', '查看所有大区', '7', 'system:allplanopen');
INSERT INTO `security_data_resource` VALUES ('14', '查看所有体系', '8', 'system:allhierarchy');
INSERT INTO `security_data_resource` VALUES ('15', '查看所有渠道', '9', 'system:allchannel');

-- ----------------------------
-- Table structure for security_menu
-- ----------------------------
DROP TABLE IF EXISTS `security_menu`;
CREATE TABLE `security_menu` (
  `ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `NAME` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `URL` varchar(100) DEFAULT NULL COMMENT 'URL',
  `PARENT_ID` int(10) DEFAULT '0' COMMENT '父菜单ID',
  `PRIORITY` int(10) DEFAULT NULL COMMENT '优先级',
  `ACTIVE` tinyint(1) DEFAULT '1' COMMENT '是否激活，1激活',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of security_menu
-- ----------------------------
INSERT INTO `security_menu` VALUES ('1', '主数据管理', '', '0', '1', '1');
INSERT INTO `security_menu` VALUES ('2', '计划流向管理', '', '0', '2', '1');
INSERT INTO `security_menu` VALUES ('3', '系统管理', null, '0', '3', '1');
INSERT INTO `security_menu` VALUES ('4', '经销商管理', null, '1', '1', '1');
INSERT INTO `security_menu` VALUES ('5', '终端管理', '', '1', '2', '1');
INSERT INTO `security_menu` VALUES ('6', '终端销售计划管理', null, '2', '1', '1');
INSERT INTO `security_menu` VALUES ('7', '经销商产品流向管理', null, '2', '2', '1');
INSERT INTO `security_menu` VALUES ('8', '终端产品流向管理', null, '2', '3', '1');
INSERT INTO `security_menu` VALUES ('9', '内部员工管理', '/user/getUserInfo', '3', '1', '1');
INSERT INTO `security_menu` VALUES ('10', '角色权限管理', '/system/roleList', '3', '2', '1');
INSERT INTO `security_menu` VALUES ('11', '计划流向开放管理', null, '3', '3', '1');
INSERT INTO `security_menu` VALUES ('12', '体系管理', '/hierarchy/hierarchyList', '3', '4', '1');
INSERT INTO `security_menu` VALUES ('13', '渠道管理', '/channel/channelList', '3', '5', '1');
INSERT INTO `security_menu` VALUES ('14', '大区管理', '/zone/zoneList', '3', '2', '1');

-- ----------------------------
-- Table structure for security_resource
-- ----------------------------
DROP TABLE IF EXISTS `security_resource`;
CREATE TABLE `security_resource` (
  `ID` int(30) NOT NULL AUTO_INCREMENT COMMENT '资源编号',
  `NAME` varchar(60) DEFAULT NULL COMMENT '资源名称',
  `PERMISSION` varchar(60) DEFAULT NULL COMMENT '权限',
  `VALID` tinyint(1) DEFAULT NULL COMMENT '是否可用，0不可用，1可用',
  `CATEGORY` tinyint(1) DEFAULT NULL COMMENT '1主数据管理 2计划流向管理 3系统管理',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of security_resource
-- ----------------------------
INSERT INTO `security_resource` VALUES ('1', '经销商查询', 'master.agent.query', '1', '1');
INSERT INTO `security_resource` VALUES ('2', '经销商新增', 'master.agent.add', '1', '1');
INSERT INTO `security_resource` VALUES ('3', '经销商修改', 'master.agent.edit', '1', '1');
INSERT INTO `security_resource` VALUES ('4', '经销商删除', 'master.agent.delete', '1', '1');
INSERT INTO `security_resource` VALUES ('5', '终端查询', 'master.terminal.query', '1', '1');
INSERT INTO `security_resource` VALUES ('6', '终端新增', 'master.terminal.add', '1', '1');
INSERT INTO `security_resource` VALUES ('7', '终端修改', 'master.terminal.edit', '1', '1');
INSERT INTO `security_resource` VALUES ('8', '终端删除', 'master.terminal.delete', '1', '1');
INSERT INTO `security_resource` VALUES ('9', '终端销售计划查询', 'planflow.terminalsale.query', '1', '2');
INSERT INTO `security_resource` VALUES ('10', '终端销售计划新增', 'planflow.terminalsale.add', '1', '2');
INSERT INTO `security_resource` VALUES ('11', '终端销售计划修改', 'planflow.terminalsale.edit', '1', '2');
INSERT INTO `security_resource` VALUES ('12', '终端销售计划删除', 'planflow.terminalsale.delete', '1', '2');
INSERT INTO `security_resource` VALUES ('13', '经销商产品流向查询', 'planflow.agentproduct.query', '1', '2');
INSERT INTO `security_resource` VALUES ('14', '经销商产品流向新增', 'planflow.agentproduct.add', '1', '2');
INSERT INTO `security_resource` VALUES ('15', '经销商产品流向修改', 'planflow.agentproduct.edit', '1', '2');
INSERT INTO `security_resource` VALUES ('16', '经销商产品流向删除', 'planflow.agentproduct.delete', '1', '2');
INSERT INTO `security_resource` VALUES ('17', '终端产品流向查询', 'planflow.terminalproduct.query', '1', '2');
INSERT INTO `security_resource` VALUES ('18', '终端产品流向新增', 'planflow.terminalproduct.add', '1', '2');
INSERT INTO `security_resource` VALUES ('19', '终端产品流向修改', 'planflow.terminalproduct.edit', '1', '2');
INSERT INTO `security_resource` VALUES ('20', '终端产品流向删除', 'planflow.terminalproduct.delete', '1', '2');
INSERT INTO `security_resource` VALUES ('21', '内部员工查询', 'system.employee.query', '1', '3');
INSERT INTO `security_resource` VALUES ('22', '内部员工新增', 'system.employee.add', '1', '3');
INSERT INTO `security_resource` VALUES ('23', '内部员工修改', 'system.employee.edit', '1', '3');
INSERT INTO `security_resource` VALUES ('24', '内部员工删除', 'system.employee.delete', '1', '3');
INSERT INTO `security_resource` VALUES ('25', '角色权限查询', 'system.roleauthority.query', '1', '3');
INSERT INTO `security_resource` VALUES ('26', '角色权限新增', 'system.roleauthority.add', '1', '3');
INSERT INTO `security_resource` VALUES ('27', '角色权限修改', 'system.roleauthority.edit', '1', '3');
INSERT INTO `security_resource` VALUES ('28', '角色权限删除', 'system.roleauthority.delete', '1', '3');
INSERT INTO `security_resource` VALUES ('29', '计划流向开放查询', 'system.planopen.query', '1', '3');
INSERT INTO `security_resource` VALUES ('30', '计划流向开放新增', 'system.planopen.add', '1', '3');
INSERT INTO `security_resource` VALUES ('31', '计划流向开放修改', 'system.planopen.edit', '1', '3');
INSERT INTO `security_resource` VALUES ('32', '计划流向开放删除', 'system.planopen.delete', '1', '3');
INSERT INTO `security_resource` VALUES ('33', '体系查询', 'system.hierarchy.query', '1', '3');
INSERT INTO `security_resource` VALUES ('34', '体系新增', 'system.hierarchy.add', '1', '3');
INSERT INTO `security_resource` VALUES ('35', '体系修改', 'system.hierarchy.edit', '1', '3');
INSERT INTO `security_resource` VALUES ('36', '体系删除', 'system.hierarchy.delete', '1', '3');
INSERT INTO `security_resource` VALUES ('37', '渠道查询', 'system.channel.query', '1', '3');
INSERT INTO `security_resource` VALUES ('38', '渠道新增', 'system.channel.add', '1', '3');
INSERT INTO `security_resource` VALUES ('39', '渠道修改', 'system.channel.edit', '1', '3');
INSERT INTO `security_resource` VALUES ('40', '渠道删除', 'system.channel.delete', '1', '3');
INSERT INTO `security_resource` VALUES ('41', '大区查询', 'system.zone.query', '1', '3');
INSERT INTO `security_resource` VALUES ('42', '大区新增', 'system.zone.add', '1', '3');
INSERT INTO `security_resource` VALUES ('43', '大区修改', 'system.zone.edit', '1', '3');
INSERT INTO `security_resource` VALUES ('44', '大区删除', 'system.zone.delete', '1', '3');
INSERT INTO `security_resource` VALUES ('45', '大区任务新增', 'system.zoneplan.add', '1', '3');
INSERT INTO `security_resource` VALUES ('46', '大区任务修改', 'system.zoneplan.edit', '1', '3');
INSERT INTO `security_resource` VALUES ('47', '大区任务删除', 'system.zoneplan.delete', '1', '3');

-- ----------------------------
-- Table structure for security_role
-- ----------------------------
DROP TABLE IF EXISTS `security_role`;
CREATE TABLE `security_role` (
  `ID` int(30) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `CODE` varchar(60) NOT NULL COMMENT '角色名称',
  `NAME` varchar(300) DEFAULT NULL COMMENT '角色描述',
  `VALID` tinyint(1) NOT NULL COMMENT '是否可用，0表示不可用，1表示可用',
  `TYPE` tinyint(1) NOT NULL COMMENT '0 经销商 1终端 2 内部员工',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of security_role
-- ----------------------------
INSERT INTO `security_role` VALUES ('1', 'headquartersOperator', '总部营运', '1', '2');
INSERT INTO `security_role` VALUES ('2', 'regionalManager', '大区经理', '1', '2');
INSERT INTO `security_role` VALUES ('3', 'officeManager', '办事处经理', '1', '2');
INSERT INTO `security_role` VALUES ('4', 'salesMan', '销售代表', '1', '2');

-- ----------------------------
-- Table structure for security_role_data_resource
-- ----------------------------
DROP TABLE IF EXISTS `security_role_data_resource`;
CREATE TABLE `security_role_data_resource` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(20) DEFAULT NULL,
  `DATA_RESOURCE_ID` int(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=552 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of security_role_data_resource
-- ----------------------------
INSERT INTO `security_role_data_resource` VALUES ('550', '1', '1');
INSERT INTO `security_role_data_resource` VALUES ('551', '1', '10');

-- ----------------------------
-- Table structure for security_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `security_role_menu`;
CREATE TABLE `security_role_menu` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(20) NOT NULL,
  `MENU_ID` int(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3248 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of security_role_menu
-- ----------------------------
INSERT INTO `security_role_menu` VALUES ('1', '1', '1');
INSERT INTO `security_role_menu` VALUES ('2', '1', '2');
INSERT INTO `security_role_menu` VALUES ('3', '1', '3');
INSERT INTO `security_role_menu` VALUES ('4', '1', '4');
INSERT INTO `security_role_menu` VALUES ('5', '1', '5');
INSERT INTO `security_role_menu` VALUES ('6', '1', '6');
INSERT INTO `security_role_menu` VALUES ('7', '1', '7');
INSERT INTO `security_role_menu` VALUES ('8', '1', '8');
INSERT INTO `security_role_menu` VALUES ('9', '1', '9');
INSERT INTO `security_role_menu` VALUES ('10', '1', '10');
INSERT INTO `security_role_menu` VALUES ('12', '1', '12');
INSERT INTO `security_role_menu` VALUES ('13', '1', '13');
INSERT INTO `security_role_menu` VALUES ('14', '1', '14');
INSERT INTO `security_role_menu` VALUES ('3246', '2', '4');
INSERT INTO `security_role_menu` VALUES ('3247', '2', '5');

-- ----------------------------
-- Table structure for security_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `security_role_resource`;
CREATE TABLE `security_role_resource` (
  `ID` int(30) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_ID` int(30) DEFAULT NULL COMMENT '角色编号',
  `RESOURCE_ID` int(30) DEFAULT NULL COMMENT '资源编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of security_role_resource
-- ----------------------------
INSERT INTO `security_role_resource` VALUES ('1', '1', '1');
INSERT INTO `security_role_resource` VALUES ('2', '1', '2');
INSERT INTO `security_role_resource` VALUES ('3', '1', '3');
INSERT INTO `security_role_resource` VALUES ('4', '1', '4');
INSERT INTO `security_role_resource` VALUES ('5', '1', '5');
INSERT INTO `security_role_resource` VALUES ('6', '1', '6');
INSERT INTO `security_role_resource` VALUES ('7', '1', '7');
INSERT INTO `security_role_resource` VALUES ('8', '1', '8');
INSERT INTO `security_role_resource` VALUES ('9', '1', '9');
INSERT INTO `security_role_resource` VALUES ('10', '1', '10');
INSERT INTO `security_role_resource` VALUES ('11', '1', '11');
INSERT INTO `security_role_resource` VALUES ('12', '1', '12');
INSERT INTO `security_role_resource` VALUES ('13', '1', '13');
INSERT INTO `security_role_resource` VALUES ('14', '1', '14');
INSERT INTO `security_role_resource` VALUES ('15', '1', '15');
INSERT INTO `security_role_resource` VALUES ('16', '1', '16');
INSERT INTO `security_role_resource` VALUES ('17', '1', '17');
INSERT INTO `security_role_resource` VALUES ('18', '1', '18');
INSERT INTO `security_role_resource` VALUES ('19', '1', '19');
INSERT INTO `security_role_resource` VALUES ('20', '1', '20');
INSERT INTO `security_role_resource` VALUES ('21', '1', '21');
INSERT INTO `security_role_resource` VALUES ('22', '1', '22');
INSERT INTO `security_role_resource` VALUES ('23', '1', '23');
INSERT INTO `security_role_resource` VALUES ('24', '1', '24');
INSERT INTO `security_role_resource` VALUES ('25', '1', '25');
INSERT INTO `security_role_resource` VALUES ('26', '1', '26');
INSERT INTO `security_role_resource` VALUES ('27', '1', '27');
INSERT INTO `security_role_resource` VALUES ('28', '1', '28');
INSERT INTO `security_role_resource` VALUES ('29', '1', '29');
INSERT INTO `security_role_resource` VALUES ('30', '1', '30');
INSERT INTO `security_role_resource` VALUES ('31', '1', '31');
INSERT INTO `security_role_resource` VALUES ('32', '1', '32');
INSERT INTO `security_role_resource` VALUES ('33', '1', '33');
INSERT INTO `security_role_resource` VALUES ('34', '1', '34');
INSERT INTO `security_role_resource` VALUES ('35', '1', '35');
INSERT INTO `security_role_resource` VALUES ('36', '1', '36');
INSERT INTO `security_role_resource` VALUES ('37', '1', '37');
INSERT INTO `security_role_resource` VALUES ('38', '1', '38');
INSERT INTO `security_role_resource` VALUES ('39', '1', '39');
INSERT INTO `security_role_resource` VALUES ('40', '1', '40');
INSERT INTO `security_role_resource` VALUES ('44', '2', '1');
INSERT INTO `security_role_resource` VALUES ('45', '1', '41');
INSERT INTO `security_role_resource` VALUES ('46', '1', '42');
INSERT INTO `security_role_resource` VALUES ('47', '1', '43');
INSERT INTO `security_role_resource` VALUES ('48', '1', '44');
INSERT INTO `security_role_resource` VALUES ('49', '1', '45');
INSERT INTO `security_role_resource` VALUES ('50', '1', '46');
INSERT INTO `security_role_resource` VALUES ('51', '1', '47');

-- ----------------------------
-- Table structure for security_user
-- ----------------------------
DROP TABLE IF EXISTS `security_user`;
CREATE TABLE `security_user` (
  `USER_ID` varchar(20) NOT NULL COMMENT '用户ID',
  `USER_NAME` varchar(255) DEFAULT NULL COMMENT '用户姓名',
  `USER_PASSWORD` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `IS_ACTIVE` tinyint(1) DEFAULT '1' COMMENT '是否激活 0:未激活 1:已激活',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of security_user
-- ----------------------------
INSERT INTO `security_user` VALUES ('100001', 'tom', '670b14728ad9902aecba32e22fa4f6bd', '1');

-- ----------------------------
-- Table structure for security_user_role
-- ----------------------------
DROP TABLE IF EXISTS `security_user_role`;
CREATE TABLE `security_user_role` (
  `USER_ID` char(30) NOT NULL,
  `ROLE_ID` int(30) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`),
  KEY `IDX_USER_ROLE_ID` (`USER_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of security_user_role
-- ----------------------------
INSERT INTO `security_user_role` VALUES ('100001', '1');

-- ----------------------------
-- Table structure for security_user_station
-- ----------------------------
DROP TABLE IF EXISTS `security_user_station`;
CREATE TABLE `security_user_station` (
  `USER_ID` char(30) NOT NULL COMMENT '用户编号',
  `STATION_ID` int(30) NOT NULL COMMENT '岗位编号',
  PRIMARY KEY (`USER_ID`,`STATION_ID`),
  KEY `IDX_USER_ROLE_ID` (`USER_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of security_user_station
-- ----------------------------
INSERT INTO `security_user_station` VALUES ('100001', '1');

-- ----------------------------
-- Table structure for system_area
-- ----------------------------
DROP TABLE IF EXISTS `system_area`;
CREATE TABLE `system_area` (
  `AreaCode` varchar(20) NOT NULL,
  `AreaName` varchar(20) NOT NULL,
  `ParentAreaCode` varchar(20) NOT NULL,
  `ProvinceCode` varchar(20) DEFAULT NULL COMMENT '省份缩写',
  `Attr1` varchar(20) DEFAULT NULL COMMENT '属性1-中国八区',
  PRIMARY KEY (`AreaCode`),
  UNIQUE KEY `pri_idx` (`AreaCode`) USING BTREE,
  KEY `parent_area_code_idx` (`ParentAreaCode`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_area
-- ----------------------------
INSERT INTO `system_area` VALUES ('0001', '中国', '0', null, null);
INSERT INTO `system_area` VALUES ('110000', '北京市', '0001', 'BJ', 'HB');
INSERT INTO `system_area` VALUES ('110100', '市辖区', '110000', 'BJ', null);
INSERT INTO `system_area` VALUES ('110101', '东城区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110102', '西城区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110103', '崇文区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110104', '宣武区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110105', '朝阳区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110106', '丰台区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110107', '石景山区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110108', '海淀区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110109', '门头沟区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110111', '房山区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110112', '通州区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110113', '顺义区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110114', '昌平区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110115', '大兴区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110116', '怀柔区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110117', '平谷区', '110100', 'BJ', null);
INSERT INTO `system_area` VALUES ('110200', '县', '110000', 'BJ', null);
INSERT INTO `system_area` VALUES ('110228', '密云县', '110200', 'BJ', null);
INSERT INTO `system_area` VALUES ('110229', '延庆县', '110200', 'BJ', null);
INSERT INTO `system_area` VALUES ('120000', '天津市', '0001', 'TJ', 'HB');
INSERT INTO `system_area` VALUES ('120100', '市辖区', '120000', 'TJ', null);
INSERT INTO `system_area` VALUES ('120101', '和平区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120102', '河东区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120103', '河西区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120104', '南开区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120105', '河北区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120106', '红桥区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120107', '塘沽区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120108', '汉沽区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120109', '大港区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120110', '东丽区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120111', '西青区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120112', '津南区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120113', '北辰区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120114', '武清区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120115', '宝坻区', '120100', 'TJ', null);
INSERT INTO `system_area` VALUES ('120200', '县', '120000', 'TJ', null);
INSERT INTO `system_area` VALUES ('120221', '宁河县', '120200', 'TJ', null);
INSERT INTO `system_area` VALUES ('120223', '静海县', '120200', 'TJ', null);
INSERT INTO `system_area` VALUES ('120225', '蓟县', '120200', 'TJ', null);
INSERT INTO `system_area` VALUES ('130000', '河北省', '0001', 'HE', 'HB');
INSERT INTO `system_area` VALUES ('130100', '石家庄市', '130000', 'HE', null);
INSERT INTO `system_area` VALUES ('130101', '市辖区', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130102', '长安区', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130103', '桥东区', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130104', '桥西区', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130105', '新华区', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130107', '井陉矿区', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130108', '裕华区', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130121', '井陉县', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130123', '正定县', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130124', '栾城县', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130125', '行唐县', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130126', '灵寿县', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130127', '高邑县', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130128', '深泽县', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130129', '赞皇县', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130130', '无极县', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130131', '平山县', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130132', '元氏县', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130133', '赵县', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130181', '辛集市', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130182', '藁城市', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130183', '晋州市', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130184', '新乐市', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130185', '鹿泉市', '130100', 'HE', null);
INSERT INTO `system_area` VALUES ('130200', '唐山市', '130000', 'HE', null);
INSERT INTO `system_area` VALUES ('130201', '市辖区', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130202', '路南区', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130203', '路北区', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130204', '古冶区', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130205', '开平区', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130207', '丰南区', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130208', '丰润区', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130223', '滦县', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130224', '滦南县', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130225', '乐亭县', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130227', '迁西县', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130229', '玉田县', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130230', '唐海县', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130281', '遵化市', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130283', '迁安市', '130200', 'HE', null);
INSERT INTO `system_area` VALUES ('130300', '秦皇岛市', '130000', 'HE', null);
INSERT INTO `system_area` VALUES ('130301', '市辖区', '130300', 'HE', null);
INSERT INTO `system_area` VALUES ('130302', '海港区', '130300', 'HE', null);
INSERT INTO `system_area` VALUES ('130303', '山海关区', '130300', 'HE', null);
INSERT INTO `system_area` VALUES ('130304', '北戴河区', '130300', 'HE', null);
INSERT INTO `system_area` VALUES ('130321', '青龙满族自治县', '130300', 'HE', null);
INSERT INTO `system_area` VALUES ('130322', '昌黎县', '130300', 'HE', null);
INSERT INTO `system_area` VALUES ('130323', '抚宁县', '130300', 'HE', null);
INSERT INTO `system_area` VALUES ('130324', '卢龙县', '130300', 'HE', null);
INSERT INTO `system_area` VALUES ('130400', '邯郸市', '130000', 'HE', null);
INSERT INTO `system_area` VALUES ('130401', '市辖区', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130402', '邯山区', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130403', '丛台区', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130404', '复兴区', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130406', '峰峰矿区', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130421', '邯郸县', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130423', '临漳县', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130424', '成安县', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130425', '大名县', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130426', '涉县', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130427', '磁县', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130428', '肥乡县', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130429', '永年县', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130430', '邱县', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130431', '鸡泽县', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130432', '广平县', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130433', '馆陶县', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130434', '魏县', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130435', '曲周县', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130481', '武安市', '130400', 'HE', null);
INSERT INTO `system_area` VALUES ('130500', '邢台市', '130000', 'HE', null);
INSERT INTO `system_area` VALUES ('130501', '市辖区', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130502', '桥东区', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130503', '桥西区', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130521', '邢台县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130522', '临城县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130523', '内丘县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130524', '柏乡县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130525', '隆尧县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130526', '任县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130527', '南和县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130528', '宁晋县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130529', '巨鹿县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130530', '新河县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130531', '广宗县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130532', '平乡县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130533', '威县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130534', '清河县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130535', '临西县', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130581', '南宫市', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130582', '沙河市', '130500', 'HE', null);
INSERT INTO `system_area` VALUES ('130600', '保定市', '130000', 'HE', null);
INSERT INTO `system_area` VALUES ('130601', '市辖区', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130602', '新市区', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130603', '北市区', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130604', '南市区', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130621', '满城县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130622', '清苑县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130623', '涞水县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130624', '阜平县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130625', '徐水县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130626', '定兴县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130627', '唐县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130628', '高阳县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130629', '容城县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130630', '涞源县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130631', '望都县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130632', '安新县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130633', '易县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130634', '曲阳县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130635', '蠡县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130636', '顺平县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130637', '博野县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130638', '雄县', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130681', '涿州市', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130682', '定州市', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130683', '安国市', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130684', '高碑店市', '130600', 'HE', null);
INSERT INTO `system_area` VALUES ('130700', '张家口市', '130000', 'HE', null);
INSERT INTO `system_area` VALUES ('130701', '市辖区', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130702', '桥东区', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130703', '桥西区', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130705', '宣化区', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130706', '下花园区', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130721', '宣化县', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130722', '张北县', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130723', '康保县', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130724', '沽源县', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130725', '尚义县', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130726', '蔚县', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130727', '阳原县', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130728', '怀安县', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130729', '万全县', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130730', '怀来县', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130731', '涿鹿县', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130732', '赤城县', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130733', '崇礼县', '130700', 'HE', null);
INSERT INTO `system_area` VALUES ('130800', '承德市', '130000', 'HE', null);
INSERT INTO `system_area` VALUES ('130801', '市辖区', '130800', 'HE', null);
INSERT INTO `system_area` VALUES ('130802', '双桥区', '130800', 'HE', null);
INSERT INTO `system_area` VALUES ('130803', '双滦区', '130800', 'HE', null);
INSERT INTO `system_area` VALUES ('130804', '鹰手营子矿区', '130800', 'HE', null);
INSERT INTO `system_area` VALUES ('130821', '承德县', '130800', 'HE', null);
INSERT INTO `system_area` VALUES ('130822', '兴隆县', '130800', 'HE', null);
INSERT INTO `system_area` VALUES ('130823', '平泉县', '130800', 'HE', null);
INSERT INTO `system_area` VALUES ('130824', '滦平县', '130800', 'HE', null);
INSERT INTO `system_area` VALUES ('130825', '隆化县', '130800', 'HE', null);
INSERT INTO `system_area` VALUES ('130826', '丰宁满族自治县', '130800', 'HE', null);
INSERT INTO `system_area` VALUES ('130827', '宽城满族自治县', '130800', 'HE', null);
INSERT INTO `system_area` VALUES ('130828', '围场满族蒙古族自治县', '130800', 'HE', null);
INSERT INTO `system_area` VALUES ('130900', '沧州市', '130000', 'HE', null);
INSERT INTO `system_area` VALUES ('130901', '市辖区', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130902', '新华区', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130903', '运河区', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130921', '沧县', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130922', '青县', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130923', '东光县', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130924', '海兴县', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130925', '盐山县', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130926', '肃宁县', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130927', '南皮县', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130928', '吴桥县', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130929', '献县', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130930', '孟村回族自治县', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130981', '泊头市', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130982', '任丘市', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130983', '黄骅市', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('130984', '河间市', '130900', 'HE', null);
INSERT INTO `system_area` VALUES ('131000', '廊坊市', '130000', 'HE', null);
INSERT INTO `system_area` VALUES ('131001', '市辖区', '131000', 'HE', null);
INSERT INTO `system_area` VALUES ('131002', '安次区', '131000', 'HE', null);
INSERT INTO `system_area` VALUES ('131003', '广阳区', '131000', 'HE', null);
INSERT INTO `system_area` VALUES ('131022', '固安县', '131000', 'HE', null);
INSERT INTO `system_area` VALUES ('131023', '永清县', '131000', 'HE', null);
INSERT INTO `system_area` VALUES ('131024', '香河县', '131000', 'HE', null);
INSERT INTO `system_area` VALUES ('131025', '大城县', '131000', 'HE', null);
INSERT INTO `system_area` VALUES ('131026', '文安县', '131000', 'HE', null);
INSERT INTO `system_area` VALUES ('131028', '大厂回族自治县', '131000', 'HE', null);
INSERT INTO `system_area` VALUES ('131081', '霸州市', '131000', 'HE', null);
INSERT INTO `system_area` VALUES ('131082', '三河市', '131000', 'HE', null);
INSERT INTO `system_area` VALUES ('131100', '衡水市', '130000', 'HE', null);
INSERT INTO `system_area` VALUES ('131101', '市辖区', '131100', 'HE', null);
INSERT INTO `system_area` VALUES ('131102', '桃城区', '131100', 'HE', null);
INSERT INTO `system_area` VALUES ('131121', '枣强县', '131100', 'HE', null);
INSERT INTO `system_area` VALUES ('131122', '武邑县', '131100', 'HE', null);
INSERT INTO `system_area` VALUES ('131123', '武强县', '131100', 'HE', null);
INSERT INTO `system_area` VALUES ('131124', '饶阳县', '131100', 'HE', null);
INSERT INTO `system_area` VALUES ('131125', '安平县', '131100', 'HE', null);
INSERT INTO `system_area` VALUES ('131126', '故城县', '131100', 'HE', null);
INSERT INTO `system_area` VALUES ('131127', '景县', '131100', 'HE', null);
INSERT INTO `system_area` VALUES ('131128', '阜城县', '131100', 'HE', null);
INSERT INTO `system_area` VALUES ('131181', '冀州市', '131100', 'HE', null);
INSERT INTO `system_area` VALUES ('131182', '深州市', '131100', 'HE', null);
INSERT INTO `system_area` VALUES ('140000', '山西省', '0001', 'SX', 'HB');
INSERT INTO `system_area` VALUES ('140100', '太原市', '140000', 'SX', null);
INSERT INTO `system_area` VALUES ('140101', '市辖区', '140100', 'SX', null);
INSERT INTO `system_area` VALUES ('140105', '小店区', '140100', 'SX', null);
INSERT INTO `system_area` VALUES ('140106', '迎泽区', '140100', 'SX', null);
INSERT INTO `system_area` VALUES ('140107', '杏花岭区', '140100', 'SX', null);
INSERT INTO `system_area` VALUES ('140108', '尖草坪区', '140100', 'SX', null);
INSERT INTO `system_area` VALUES ('140109', '万柏林区', '140100', 'SX', null);
INSERT INTO `system_area` VALUES ('140110', '晋源区', '140100', 'SX', null);
INSERT INTO `system_area` VALUES ('140121', '清徐县', '140100', 'SX', null);
INSERT INTO `system_area` VALUES ('140122', '阳曲县', '140100', 'SX', null);
INSERT INTO `system_area` VALUES ('140123', '娄烦县', '140100', 'SX', null);
INSERT INTO `system_area` VALUES ('140181', '古交市', '140100', 'SX', null);
INSERT INTO `system_area` VALUES ('140200', '大同市', '140000', 'SX', null);
INSERT INTO `system_area` VALUES ('140201', '市辖区', '140200', 'SX', null);
INSERT INTO `system_area` VALUES ('140202', '城区', '140200', 'SX', null);
INSERT INTO `system_area` VALUES ('140203', '矿区', '140200', 'SX', null);
INSERT INTO `system_area` VALUES ('140211', '南郊区', '140200', 'SX', null);
INSERT INTO `system_area` VALUES ('140212', '新荣区', '140200', 'SX', null);
INSERT INTO `system_area` VALUES ('140221', '阳高县', '140200', 'SX', null);
INSERT INTO `system_area` VALUES ('140222', '天镇县', '140200', 'SX', null);
INSERT INTO `system_area` VALUES ('140223', '广灵县', '140200', 'SX', null);
INSERT INTO `system_area` VALUES ('140224', '灵丘县', '140200', 'SX', null);
INSERT INTO `system_area` VALUES ('140225', '浑源县', '140200', 'SX', null);
INSERT INTO `system_area` VALUES ('140226', '左云县', '140200', 'SX', null);
INSERT INTO `system_area` VALUES ('140227', '大同县', '140200', 'SX', null);
INSERT INTO `system_area` VALUES ('140300', '阳泉市', '140000', 'SX', null);
INSERT INTO `system_area` VALUES ('140301', '市辖区', '140300', 'SX', null);
INSERT INTO `system_area` VALUES ('140302', '城区', '140300', 'SX', null);
INSERT INTO `system_area` VALUES ('140303', '矿区', '140300', 'SX', null);
INSERT INTO `system_area` VALUES ('140311', '郊区', '140300', 'SX', null);
INSERT INTO `system_area` VALUES ('140321', '平定县', '140300', 'SX', null);
INSERT INTO `system_area` VALUES ('140322', '盂县', '140300', 'SX', null);
INSERT INTO `system_area` VALUES ('140400', '长治市', '140000', 'SX', null);
INSERT INTO `system_area` VALUES ('140401', '市辖区', '140400', 'SX', null);
INSERT INTO `system_area` VALUES ('140402', '城区', '140400', 'SX', null);
INSERT INTO `system_area` VALUES ('140411', '郊区', '140400', 'SX', null);
INSERT INTO `system_area` VALUES ('140421', '长治县', '140400', 'SX', null);
INSERT INTO `system_area` VALUES ('140423', '襄垣县', '140400', 'SX', null);
INSERT INTO `system_area` VALUES ('140424', '屯留县', '140400', 'SX', null);
INSERT INTO `system_area` VALUES ('140425', '平顺县', '140400', 'SX', null);
INSERT INTO `system_area` VALUES ('140426', '黎城县', '140400', 'SX', null);
INSERT INTO `system_area` VALUES ('140427', '壶关县', '140400', 'SX', null);
INSERT INTO `system_area` VALUES ('140428', '长子县', '140400', 'SX', null);
INSERT INTO `system_area` VALUES ('140429', '武乡县', '140400', 'SX', null);
INSERT INTO `system_area` VALUES ('140430', '沁县', '140400', 'SX', null);
INSERT INTO `system_area` VALUES ('140431', '沁源县', '140400', 'SX', null);
INSERT INTO `system_area` VALUES ('140481', '潞城市', '140400', 'SX', null);
INSERT INTO `system_area` VALUES ('140500', '晋城市', '140000', 'SX', null);
INSERT INTO `system_area` VALUES ('140501', '市辖区', '140500', 'SX', null);
INSERT INTO `system_area` VALUES ('140502', '城区', '140500', 'SX', null);
INSERT INTO `system_area` VALUES ('140521', '沁水县', '140500', 'SX', null);
INSERT INTO `system_area` VALUES ('140522', '阳城县', '140500', 'SX', null);
INSERT INTO `system_area` VALUES ('140524', '陵川县', '140500', 'SX', null);
INSERT INTO `system_area` VALUES ('140525', '泽州县', '140500', 'SX', null);
INSERT INTO `system_area` VALUES ('140581', '高平市', '140500', 'SX', null);
INSERT INTO `system_area` VALUES ('140600', '朔州市', '140000', 'SX', null);
INSERT INTO `system_area` VALUES ('140601', '市辖区', '140600', 'SX', null);
INSERT INTO `system_area` VALUES ('140602', '朔城区', '140600', 'SX', null);
INSERT INTO `system_area` VALUES ('140603', '平鲁区', '140600', 'SX', null);
INSERT INTO `system_area` VALUES ('140621', '山阴县', '140600', 'SX', null);
INSERT INTO `system_area` VALUES ('140622', '应县', '140600', 'SX', null);
INSERT INTO `system_area` VALUES ('140623', '右玉县', '140600', 'SX', null);
INSERT INTO `system_area` VALUES ('140624', '怀仁县', '140600', 'SX', null);
INSERT INTO `system_area` VALUES ('140700', '晋中市', '140000', 'SX', null);
INSERT INTO `system_area` VALUES ('140701', '市辖区', '140700', 'SX', null);
INSERT INTO `system_area` VALUES ('140702', '榆次区', '140700', 'SX', null);
INSERT INTO `system_area` VALUES ('140721', '榆社县', '140700', 'SX', null);
INSERT INTO `system_area` VALUES ('140722', '左权县', '140700', 'SX', null);
INSERT INTO `system_area` VALUES ('140723', '和顺县', '140700', 'SX', null);
INSERT INTO `system_area` VALUES ('140724', '昔阳县', '140700', 'SX', null);
INSERT INTO `system_area` VALUES ('140725', '寿阳县', '140700', 'SX', null);
INSERT INTO `system_area` VALUES ('140726', '太谷县', '140700', 'SX', null);
INSERT INTO `system_area` VALUES ('140727', '祁县', '140700', 'SX', null);
INSERT INTO `system_area` VALUES ('140728', '平遥县', '140700', 'SX', null);
INSERT INTO `system_area` VALUES ('140729', '灵石县', '140700', 'SX', null);
INSERT INTO `system_area` VALUES ('140781', '介休市', '140700', 'SX', null);
INSERT INTO `system_area` VALUES ('140800', '运城市', '140000', 'SX', null);
INSERT INTO `system_area` VALUES ('140801', '市辖区', '140800', 'SX', null);
INSERT INTO `system_area` VALUES ('140802', '盐湖区', '140800', 'SX', null);
INSERT INTO `system_area` VALUES ('140821', '临猗县', '140800', 'SX', null);
INSERT INTO `system_area` VALUES ('140822', '万荣县', '140800', 'SX', null);
INSERT INTO `system_area` VALUES ('140823', '闻喜县', '140800', 'SX', null);
INSERT INTO `system_area` VALUES ('140824', '稷山县', '140800', 'SX', null);
INSERT INTO `system_area` VALUES ('140825', '新绛县', '140800', 'SX', null);
INSERT INTO `system_area` VALUES ('140826', '绛县', '140800', 'SX', null);
INSERT INTO `system_area` VALUES ('140827', '垣曲县', '140800', 'SX', null);
INSERT INTO `system_area` VALUES ('140828', '夏县', '140800', 'SX', null);
INSERT INTO `system_area` VALUES ('140829', '平陆县', '140800', 'SX', null);
INSERT INTO `system_area` VALUES ('140830', '芮城县', '140800', 'SX', null);
INSERT INTO `system_area` VALUES ('140881', '永济市', '140800', 'SX', null);
INSERT INTO `system_area` VALUES ('140882', '河津市', '140800', 'SX', null);
INSERT INTO `system_area` VALUES ('140900', '忻州市', '140000', 'SX', null);
INSERT INTO `system_area` VALUES ('140901', '市辖区', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('140902', '忻府区', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('140921', '定襄县', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('140922', '五台县', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('140923', '代县', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('140924', '繁峙县', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('140925', '宁武县', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('140926', '静乐县', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('140927', '神池县', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('140928', '五寨县', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('140929', '岢岚县', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('140930', '河曲县', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('140931', '保德县', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('140932', '偏关县', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('140981', '原平市', '140900', 'SX', null);
INSERT INTO `system_area` VALUES ('141000', '临汾市', '140000', 'SX', null);
INSERT INTO `system_area` VALUES ('141001', '市辖区', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141002', '尧都区', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141021', '曲沃县', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141022', '翼城县', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141023', '襄汾县', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141024', '洪洞县', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141025', '古县', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141026', '安泽县', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141027', '浮山县', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141028', '吉县', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141029', '乡宁县', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141030', '大宁县', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141031', '隰县', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141032', '永和县', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141033', '蒲县', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141034', '汾西县', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141081', '侯马市', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141082', '霍州市', '141000', 'SX', null);
INSERT INTO `system_area` VALUES ('141100', '吕梁市', '140000', 'SX', null);
INSERT INTO `system_area` VALUES ('141101', '市辖区', '141100', 'SX', null);
INSERT INTO `system_area` VALUES ('141102', '离石区', '141100', 'SX', null);
INSERT INTO `system_area` VALUES ('141121', '文水县', '141100', 'SX', null);
INSERT INTO `system_area` VALUES ('141122', '交城县', '141100', 'SX', null);
INSERT INTO `system_area` VALUES ('141123', '兴县', '141100', 'SX', null);
INSERT INTO `system_area` VALUES ('141124', '临县', '141100', 'SX', null);
INSERT INTO `system_area` VALUES ('141125', '柳林县', '141100', 'SX', null);
INSERT INTO `system_area` VALUES ('141126', '石楼县', '141100', 'SX', null);
INSERT INTO `system_area` VALUES ('141127', '岚县', '141100', 'SX', null);
INSERT INTO `system_area` VALUES ('141128', '方山县', '141100', 'SX', null);
INSERT INTO `system_area` VALUES ('141129', '中阳县', '141100', 'SX', null);
INSERT INTO `system_area` VALUES ('141130', '交口县', '141100', 'SX', null);
INSERT INTO `system_area` VALUES ('141181', '孝义市', '141100', 'SX', null);
INSERT INTO `system_area` VALUES ('141182', '汾阳市', '141100', 'SX', null);
INSERT INTO `system_area` VALUES ('150000', '内蒙古自治区', '0001', 'NM', 'HB');
INSERT INTO `system_area` VALUES ('150100', '呼和浩特市', '150000', 'NM', null);
INSERT INTO `system_area` VALUES ('150101', '市辖区', '150100', 'NM', null);
INSERT INTO `system_area` VALUES ('150102', '新城区', '150100', 'NM', null);
INSERT INTO `system_area` VALUES ('150103', '回民区', '150100', 'NM', null);
INSERT INTO `system_area` VALUES ('150104', '玉泉区', '150100', 'NM', null);
INSERT INTO `system_area` VALUES ('150105', '赛罕区', '150100', 'NM', null);
INSERT INTO `system_area` VALUES ('150121', '土默特左旗', '150100', 'NM', null);
INSERT INTO `system_area` VALUES ('150122', '托克托县', '150100', 'NM', null);
INSERT INTO `system_area` VALUES ('150123', '和林格尔县', '150100', 'NM', null);
INSERT INTO `system_area` VALUES ('150124', '清水河县', '150100', 'NM', null);
INSERT INTO `system_area` VALUES ('150125', '武川县', '150100', 'NM', null);
INSERT INTO `system_area` VALUES ('150200', '包头市', '150000', 'NM', null);
INSERT INTO `system_area` VALUES ('150201', '市辖区', '150200', 'NM', null);
INSERT INTO `system_area` VALUES ('150202', '东河区', '150200', 'NM', null);
INSERT INTO `system_area` VALUES ('150203', '昆都仑区', '150200', 'NM', null);
INSERT INTO `system_area` VALUES ('150204', '青山区', '150200', 'NM', null);
INSERT INTO `system_area` VALUES ('150205', '石拐区', '150200', 'NM', null);
INSERT INTO `system_area` VALUES ('150206', '白云矿区', '150200', 'NM', null);
INSERT INTO `system_area` VALUES ('150207', '九原区', '150200', 'NM', null);
INSERT INTO `system_area` VALUES ('150221', '土默特右旗', '150200', 'NM', null);
INSERT INTO `system_area` VALUES ('150222', '固阳县', '150200', 'NM', null);
INSERT INTO `system_area` VALUES ('150223', '达尔罕茂明安联合旗', '150200', 'NM', null);
INSERT INTO `system_area` VALUES ('150300', '乌海市', '150000', 'NM', null);
INSERT INTO `system_area` VALUES ('150301', '市辖区', '150300', 'NM', null);
INSERT INTO `system_area` VALUES ('150302', '海勃湾区', '150300', 'NM', null);
INSERT INTO `system_area` VALUES ('150303', '海南区', '150300', 'NM', null);
INSERT INTO `system_area` VALUES ('150304', '乌达区', '150300', 'NM', null);
INSERT INTO `system_area` VALUES ('150400', '赤峰市', '150000', 'NM', null);
INSERT INTO `system_area` VALUES ('150401', '市辖区', '150400', 'NM', null);
INSERT INTO `system_area` VALUES ('150402', '红山区', '150400', 'NM', null);
INSERT INTO `system_area` VALUES ('150403', '元宝山区', '150400', 'NM', null);
INSERT INTO `system_area` VALUES ('150404', '松山区', '150400', 'NM', null);
INSERT INTO `system_area` VALUES ('150421', '阿鲁科尔沁旗', '150400', 'NM', null);
INSERT INTO `system_area` VALUES ('150422', '巴林左旗', '150400', 'NM', null);
INSERT INTO `system_area` VALUES ('150423', '巴林右旗', '150400', 'NM', null);
INSERT INTO `system_area` VALUES ('150424', '林西县', '150400', 'NM', null);
INSERT INTO `system_area` VALUES ('150425', '克什克腾旗', '150400', 'NM', null);
INSERT INTO `system_area` VALUES ('150426', '翁牛特旗', '150400', 'NM', null);
INSERT INTO `system_area` VALUES ('150428', '喀喇沁旗', '150400', 'NM', null);
INSERT INTO `system_area` VALUES ('150429', '宁城县', '150400', 'NM', null);
INSERT INTO `system_area` VALUES ('150430', '敖汉旗', '150400', 'NM', null);
INSERT INTO `system_area` VALUES ('150500', '通辽市', '150000', 'NM', null);
INSERT INTO `system_area` VALUES ('150501', '市辖区', '150500', 'NM', null);
INSERT INTO `system_area` VALUES ('150502', '科尔沁区', '150500', 'NM', null);
INSERT INTO `system_area` VALUES ('150521', '科尔沁左翼中旗', '150500', 'NM', null);
INSERT INTO `system_area` VALUES ('150522', '科尔沁左翼后旗', '150500', 'NM', null);
INSERT INTO `system_area` VALUES ('150523', '开鲁县', '150500', 'NM', null);
INSERT INTO `system_area` VALUES ('150524', '库伦旗', '150500', 'NM', null);
INSERT INTO `system_area` VALUES ('150525', '奈曼旗', '150500', 'NM', null);
INSERT INTO `system_area` VALUES ('150526', '扎鲁特旗', '150500', 'NM', null);
INSERT INTO `system_area` VALUES ('150581', '霍林郭勒市', '150500', 'NM', null);
INSERT INTO `system_area` VALUES ('150600', '鄂尔多斯市', '150000', 'NM', null);
INSERT INTO `system_area` VALUES ('150602', '东胜区', '150600', 'NM', null);
INSERT INTO `system_area` VALUES ('150621', '达拉特旗', '150600', 'NM', null);
INSERT INTO `system_area` VALUES ('150622', '准格尔旗', '150600', 'NM', null);
INSERT INTO `system_area` VALUES ('150623', '鄂托克前旗', '150600', 'NM', null);
INSERT INTO `system_area` VALUES ('150624', '鄂托克旗', '150600', 'NM', null);
INSERT INTO `system_area` VALUES ('150625', '杭锦旗', '150600', 'NM', null);
INSERT INTO `system_area` VALUES ('150626', '乌审旗', '150600', 'NM', null);
INSERT INTO `system_area` VALUES ('150627', '伊金霍洛旗', '150600', 'NM', null);
INSERT INTO `system_area` VALUES ('150700', '呼伦贝尔市', '150000', 'NM', null);
INSERT INTO `system_area` VALUES ('150701', '市辖区', '150700', 'NM', null);
INSERT INTO `system_area` VALUES ('150702', '海拉尔区', '150700', 'NM', null);
INSERT INTO `system_area` VALUES ('150721', '阿荣旗', '150700', 'NM', null);
INSERT INTO `system_area` VALUES ('150722', '莫力达瓦达斡尔族自治', '150700', 'NM', null);
INSERT INTO `system_area` VALUES ('150723', '鄂伦春自治旗', '150700', 'NM', null);
INSERT INTO `system_area` VALUES ('150724', '鄂温克族自治旗', '150700', 'NM', null);
INSERT INTO `system_area` VALUES ('150725', '陈巴尔虎旗', '150700', 'NM', null);
INSERT INTO `system_area` VALUES ('150726', '新巴尔虎左旗', '150700', 'NM', null);
INSERT INTO `system_area` VALUES ('150727', '新巴尔虎右旗', '150700', 'NM', null);
INSERT INTO `system_area` VALUES ('150781', '满洲里市', '150700', 'NM', null);
INSERT INTO `system_area` VALUES ('150782', '牙克石市', '150700', 'NM', null);
INSERT INTO `system_area` VALUES ('150783', '扎兰屯市', '150700', 'NM', null);
INSERT INTO `system_area` VALUES ('150784', '额尔古纳市', '150700', 'NM', null);
INSERT INTO `system_area` VALUES ('150785', '根河市', '150700', 'NM', null);
INSERT INTO `system_area` VALUES ('150800', '巴彦淖尔市', '150000', 'NM', null);
INSERT INTO `system_area` VALUES ('150801', '市辖区', '150800', 'NM', null);
INSERT INTO `system_area` VALUES ('150802', '临河区', '150800', 'NM', null);
INSERT INTO `system_area` VALUES ('150821', '五原县', '150800', 'NM', null);
INSERT INTO `system_area` VALUES ('150822', '磴口县', '150800', 'NM', null);
INSERT INTO `system_area` VALUES ('150823', '乌拉特前旗', '150800', 'NM', null);
INSERT INTO `system_area` VALUES ('150824', '乌拉特中旗', '150800', 'NM', null);
INSERT INTO `system_area` VALUES ('150825', '乌拉特后旗', '150800', 'NM', null);
INSERT INTO `system_area` VALUES ('150826', '杭锦后旗', '150800', 'NM', null);
INSERT INTO `system_area` VALUES ('150900', '乌兰察布市', '150000', 'NM', null);
INSERT INTO `system_area` VALUES ('150901', '市辖区', '150900', 'NM', null);
INSERT INTO `system_area` VALUES ('150902', '集宁区', '150900', 'NM', null);
INSERT INTO `system_area` VALUES ('150921', '卓资县', '150900', 'NM', null);
INSERT INTO `system_area` VALUES ('150922', '化德县', '150900', 'NM', null);
INSERT INTO `system_area` VALUES ('150923', '商都县', '150900', 'NM', null);
INSERT INTO `system_area` VALUES ('150924', '兴和县', '150900', 'NM', null);
INSERT INTO `system_area` VALUES ('150925', '凉城县', '150900', 'NM', null);
INSERT INTO `system_area` VALUES ('150926', '察哈尔右翼前旗', '150900', 'NM', null);
INSERT INTO `system_area` VALUES ('150927', '察哈尔右翼中旗', '150900', 'NM', null);
INSERT INTO `system_area` VALUES ('150928', '察哈尔右翼后旗', '150900', 'NM', null);
INSERT INTO `system_area` VALUES ('150929', '四子王旗', '150900', 'NM', null);
INSERT INTO `system_area` VALUES ('150981', '丰镇市', '150900', 'NM', null);
INSERT INTO `system_area` VALUES ('152200', '兴安盟', '150000', 'NM', null);
INSERT INTO `system_area` VALUES ('152201', '乌兰浩特市', '152200', 'NM', null);
INSERT INTO `system_area` VALUES ('152202', '阿尔山市', '152200', 'NM', null);
INSERT INTO `system_area` VALUES ('152221', '科尔沁右翼前旗', '152200', 'NM', null);
INSERT INTO `system_area` VALUES ('152222', '科尔沁右翼中旗', '152200', 'NM', null);
INSERT INTO `system_area` VALUES ('152223', '扎赉特旗', '152200', 'NM', null);
INSERT INTO `system_area` VALUES ('152224', '突泉县', '152200', 'NM', null);
INSERT INTO `system_area` VALUES ('152500', '锡林郭勒盟', '150000', 'NM', null);
INSERT INTO `system_area` VALUES ('152501', '二连浩特市', '152500', 'NM', null);
INSERT INTO `system_area` VALUES ('152502', '锡林浩特市', '152500', 'NM', null);
INSERT INTO `system_area` VALUES ('152522', '阿巴嘎旗', '152500', 'NM', null);
INSERT INTO `system_area` VALUES ('152523', '苏尼特左旗', '152500', 'NM', null);
INSERT INTO `system_area` VALUES ('152524', '苏尼特右旗', '152500', 'NM', null);
INSERT INTO `system_area` VALUES ('152525', '东乌珠穆沁旗', '152500', 'NM', null);
INSERT INTO `system_area` VALUES ('152526', '西乌珠穆沁旗', '152500', 'NM', null);
INSERT INTO `system_area` VALUES ('152527', '太仆寺旗', '152500', 'NM', null);
INSERT INTO `system_area` VALUES ('152528', '镶黄旗', '152500', 'NM', null);
INSERT INTO `system_area` VALUES ('152529', '正镶白旗', '152500', 'NM', null);
INSERT INTO `system_area` VALUES ('152530', '正蓝旗', '152500', 'NM', null);
INSERT INTO `system_area` VALUES ('152531', '多伦县', '152500', 'NM', null);
INSERT INTO `system_area` VALUES ('152900', '阿拉善盟', '150000', 'NM', null);
INSERT INTO `system_area` VALUES ('152921', '阿拉善左旗', '152900', 'NM', null);
INSERT INTO `system_area` VALUES ('152922', '阿拉善右旗', '152900', 'NM', null);
INSERT INTO `system_area` VALUES ('152923', '额济纳旗', '152900', 'NM', null);
INSERT INTO `system_area` VALUES ('210000', '辽宁省', '0001', 'LN', 'DB');
INSERT INTO `system_area` VALUES ('210100', '沈阳市', '210000', 'LN', null);
INSERT INTO `system_area` VALUES ('210101', '市辖区', '210100', 'LN', null);
INSERT INTO `system_area` VALUES ('210102', '和平区', '210100', 'LN', null);
INSERT INTO `system_area` VALUES ('210103', '沈河区', '210100', 'LN', null);
INSERT INTO `system_area` VALUES ('210104', '大东区', '210100', 'LN', null);
INSERT INTO `system_area` VALUES ('210105', '皇姑区', '210100', 'LN', null);
INSERT INTO `system_area` VALUES ('210106', '铁西区', '210100', 'LN', null);
INSERT INTO `system_area` VALUES ('210111', '苏家屯区', '210100', 'LN', null);
INSERT INTO `system_area` VALUES ('210112', '东陵区', '210100', 'LN', null);
INSERT INTO `system_area` VALUES ('210113', '新城子区', '210100', 'LN', null);
INSERT INTO `system_area` VALUES ('210114', '于洪区', '210100', 'LN', null);
INSERT INTO `system_area` VALUES ('210122', '辽中县', '210100', 'LN', null);
INSERT INTO `system_area` VALUES ('210123', '康平县', '210100', 'LN', null);
INSERT INTO `system_area` VALUES ('210124', '法库县', '210100', 'LN', null);
INSERT INTO `system_area` VALUES ('210181', '新民市', '210100', 'LN', null);
INSERT INTO `system_area` VALUES ('210200', '大连市', '210000', 'LN', null);
INSERT INTO `system_area` VALUES ('210201', '市辖区', '210200', 'LN', null);
INSERT INTO `system_area` VALUES ('210202', '中山区', '210200', 'LN', null);
INSERT INTO `system_area` VALUES ('210203', '西岗区', '210200', 'LN', null);
INSERT INTO `system_area` VALUES ('210204', '沙河口区', '210200', 'LN', null);
INSERT INTO `system_area` VALUES ('210211', '甘井子区', '210200', 'LN', null);
INSERT INTO `system_area` VALUES ('210212', '旅顺口区', '210200', 'LN', null);
INSERT INTO `system_area` VALUES ('210213', '金州区', '210200', 'LN', null);
INSERT INTO `system_area` VALUES ('210224', '长海县', '210200', 'LN', null);
INSERT INTO `system_area` VALUES ('210281', '瓦房店市', '210200', 'LN', null);
INSERT INTO `system_area` VALUES ('210282', '普兰店市', '210200', 'LN', null);
INSERT INTO `system_area` VALUES ('210283', '庄河市', '210200', 'LN', null);
INSERT INTO `system_area` VALUES ('210300', '鞍山市', '210000', 'LN', null);
INSERT INTO `system_area` VALUES ('210301', '市辖区', '210300', 'LN', null);
INSERT INTO `system_area` VALUES ('210302', '铁东区', '210300', 'LN', null);
INSERT INTO `system_area` VALUES ('210303', '铁西区', '210300', 'LN', null);
INSERT INTO `system_area` VALUES ('210304', '立山区', '210300', 'LN', null);
INSERT INTO `system_area` VALUES ('210311', '千山区', '210300', 'LN', null);
INSERT INTO `system_area` VALUES ('210321', '台安县', '210300', 'LN', null);
INSERT INTO `system_area` VALUES ('210323', '岫岩满族自治县', '210300', 'LN', null);
INSERT INTO `system_area` VALUES ('210381', '海城市', '210300', 'LN', null);
INSERT INTO `system_area` VALUES ('210400', '抚顺市', '210000', 'LN', null);
INSERT INTO `system_area` VALUES ('210401', '市辖区', '210400', 'LN', null);
INSERT INTO `system_area` VALUES ('210402', '新抚区', '210400', 'LN', null);
INSERT INTO `system_area` VALUES ('210403', '东洲区', '210400', 'LN', null);
INSERT INTO `system_area` VALUES ('210404', '望花区', '210400', 'LN', null);
INSERT INTO `system_area` VALUES ('210411', '顺城区', '210400', 'LN', null);
INSERT INTO `system_area` VALUES ('210421', '抚顺县', '210400', 'LN', null);
INSERT INTO `system_area` VALUES ('210422', '新宾满族自治县', '210400', 'LN', null);
INSERT INTO `system_area` VALUES ('210423', '清原满族自治县', '210400', 'LN', null);
INSERT INTO `system_area` VALUES ('210500', '本溪市', '210000', 'LN', null);
INSERT INTO `system_area` VALUES ('210501', '市辖区', '210500', 'LN', null);
INSERT INTO `system_area` VALUES ('210502', '平山区', '210500', 'LN', null);
INSERT INTO `system_area` VALUES ('210503', '溪湖区', '210500', 'LN', null);
INSERT INTO `system_area` VALUES ('210504', '明山区', '210500', 'LN', null);
INSERT INTO `system_area` VALUES ('210505', '南芬区', '210500', 'LN', null);
INSERT INTO `system_area` VALUES ('210521', '本溪满族自治县', '210500', 'LN', null);
INSERT INTO `system_area` VALUES ('210522', '桓仁满族自治县', '210500', 'LN', null);
INSERT INTO `system_area` VALUES ('210600', '丹东市', '210000', 'LN', null);
INSERT INTO `system_area` VALUES ('210601', '市辖区', '210600', 'LN', null);
INSERT INTO `system_area` VALUES ('210602', '元宝区', '210600', 'LN', null);
INSERT INTO `system_area` VALUES ('210603', '振兴区', '210600', 'LN', null);
INSERT INTO `system_area` VALUES ('210604', '振安区', '210600', 'LN', null);
INSERT INTO `system_area` VALUES ('210624', '宽甸满族自治县', '210600', 'LN', null);
INSERT INTO `system_area` VALUES ('210681', '东港市', '210600', 'LN', null);
INSERT INTO `system_area` VALUES ('210682', '凤城市', '210600', 'LN', null);
INSERT INTO `system_area` VALUES ('210700', '锦州市', '210000', 'LN', null);
INSERT INTO `system_area` VALUES ('210701', '市辖区', '210700', 'LN', null);
INSERT INTO `system_area` VALUES ('210702', '古塔区', '210700', 'LN', null);
INSERT INTO `system_area` VALUES ('210703', '凌河区', '210700', 'LN', null);
INSERT INTO `system_area` VALUES ('210711', '太和区', '210700', 'LN', null);
INSERT INTO `system_area` VALUES ('210726', '黑山县', '210700', 'LN', null);
INSERT INTO `system_area` VALUES ('210727', '义县', '210700', 'LN', null);
INSERT INTO `system_area` VALUES ('210781', '凌海市', '210700', 'LN', null);
INSERT INTO `system_area` VALUES ('210782', '北宁市', '210700', 'LN', null);
INSERT INTO `system_area` VALUES ('210800', '营口市', '210000', 'LN', null);
INSERT INTO `system_area` VALUES ('210801', '市辖区', '210800', 'LN', null);
INSERT INTO `system_area` VALUES ('210802', '站前区', '210800', 'LN', null);
INSERT INTO `system_area` VALUES ('210803', '西市区', '210800', 'LN', null);
INSERT INTO `system_area` VALUES ('210804', '鲅鱼圈区', '210800', 'LN', null);
INSERT INTO `system_area` VALUES ('210811', '老边区', '210800', 'LN', null);
INSERT INTO `system_area` VALUES ('210881', '盖州市', '210800', 'LN', null);
INSERT INTO `system_area` VALUES ('210882', '大石桥市', '210800', 'LN', null);
INSERT INTO `system_area` VALUES ('210900', '阜新市', '210000', 'LN', null);
INSERT INTO `system_area` VALUES ('210901', '市辖区', '210900', 'LN', null);
INSERT INTO `system_area` VALUES ('210902', '海州区', '210900', 'LN', null);
INSERT INTO `system_area` VALUES ('210903', '新邱区', '210900', 'LN', null);
INSERT INTO `system_area` VALUES ('210904', '太平区', '210900', 'LN', null);
INSERT INTO `system_area` VALUES ('210905', '清河门区', '210900', 'LN', null);
INSERT INTO `system_area` VALUES ('210911', '细河区', '210900', 'LN', null);
INSERT INTO `system_area` VALUES ('210921', '阜新蒙古族自治县', '210900', 'LN', null);
INSERT INTO `system_area` VALUES ('210922', '彰武县', '210900', 'LN', null);
INSERT INTO `system_area` VALUES ('211000', '辽阳市', '210000', 'LN', null);
INSERT INTO `system_area` VALUES ('211001', '市辖区', '211000', 'LN', null);
INSERT INTO `system_area` VALUES ('211002', '白塔区', '211000', 'LN', null);
INSERT INTO `system_area` VALUES ('211003', '文圣区', '211000', 'LN', null);
INSERT INTO `system_area` VALUES ('211004', '宏伟区', '211000', 'LN', null);
INSERT INTO `system_area` VALUES ('211005', '弓长岭区', '211000', 'LN', null);
INSERT INTO `system_area` VALUES ('211011', '太子河区', '211000', 'LN', null);
INSERT INTO `system_area` VALUES ('211021', '辽阳县', '211000', 'LN', null);
INSERT INTO `system_area` VALUES ('211081', '灯塔市', '211000', 'LN', null);
INSERT INTO `system_area` VALUES ('211100', '盘锦市', '210000', 'LN', null);
INSERT INTO `system_area` VALUES ('211101', '市辖区', '211100', 'LN', null);
INSERT INTO `system_area` VALUES ('211102', '双台子区', '211100', 'LN', null);
INSERT INTO `system_area` VALUES ('211103', '兴隆台区', '211100', 'LN', null);
INSERT INTO `system_area` VALUES ('211121', '大洼县', '211100', 'LN', null);
INSERT INTO `system_area` VALUES ('211122', '盘山县', '211100', 'LN', null);
INSERT INTO `system_area` VALUES ('211200', '铁岭市', '210000', 'LN', null);
INSERT INTO `system_area` VALUES ('211201', '市辖区', '211200', 'LN', null);
INSERT INTO `system_area` VALUES ('211202', '银州区', '211200', 'LN', null);
INSERT INTO `system_area` VALUES ('211204', '清河区', '211200', 'LN', null);
INSERT INTO `system_area` VALUES ('211221', '铁岭县', '211200', 'LN', null);
INSERT INTO `system_area` VALUES ('211223', '西丰县', '211200', 'LN', null);
INSERT INTO `system_area` VALUES ('211224', '昌图县', '211200', 'LN', null);
INSERT INTO `system_area` VALUES ('211281', '调兵山市', '211200', 'LN', null);
INSERT INTO `system_area` VALUES ('211282', '开原市', '211200', 'LN', null);
INSERT INTO `system_area` VALUES ('211300', '朝阳市', '210000', 'LN', null);
INSERT INTO `system_area` VALUES ('211301', '市辖区', '211300', 'LN', null);
INSERT INTO `system_area` VALUES ('211302', '双塔区', '211300', 'LN', null);
INSERT INTO `system_area` VALUES ('211303', '龙城区', '211300', 'LN', null);
INSERT INTO `system_area` VALUES ('211321', '朝阳县', '211300', 'LN', null);
INSERT INTO `system_area` VALUES ('211322', '建平县', '211300', 'LN', null);
INSERT INTO `system_area` VALUES ('211324', '喀喇沁左翼蒙古族自治', '211300', 'LN', null);
INSERT INTO `system_area` VALUES ('211381', '北票市', '211300', 'LN', null);
INSERT INTO `system_area` VALUES ('211382', '凌源市', '211300', 'LN', null);
INSERT INTO `system_area` VALUES ('211400', '葫芦岛市', '210000', 'LN', null);
INSERT INTO `system_area` VALUES ('211401', '市辖区', '211400', 'LN', null);
INSERT INTO `system_area` VALUES ('211402', '连山区', '211400', 'LN', null);
INSERT INTO `system_area` VALUES ('211403', '龙港区', '211400', 'LN', null);
INSERT INTO `system_area` VALUES ('211404', '南票区', '211400', 'LN', null);
INSERT INTO `system_area` VALUES ('211421', '绥中县', '211400', 'LN', null);
INSERT INTO `system_area` VALUES ('211422', '建昌县', '211400', 'LN', null);
INSERT INTO `system_area` VALUES ('211481', '兴城市', '211400', 'LN', null);
INSERT INTO `system_area` VALUES ('220000', '吉林省', '0001', 'JL', 'DB');
INSERT INTO `system_area` VALUES ('220100', '长春市', '220000', 'JL', null);
INSERT INTO `system_area` VALUES ('220101', '市辖区', '220100', 'JL', null);
INSERT INTO `system_area` VALUES ('220102', '南关区', '220100', 'JL', null);
INSERT INTO `system_area` VALUES ('220103', '宽城区', '220100', 'JL', null);
INSERT INTO `system_area` VALUES ('220104', '朝阳区', '220100', 'JL', null);
INSERT INTO `system_area` VALUES ('220105', '二道区', '220100', 'JL', null);
INSERT INTO `system_area` VALUES ('220106', '绿园区', '220100', 'JL', null);
INSERT INTO `system_area` VALUES ('220112', '双阳区', '220100', 'JL', null);
INSERT INTO `system_area` VALUES ('220122', '农安县', '220100', 'JL', null);
INSERT INTO `system_area` VALUES ('220181', '九台市', '220100', 'JL', null);
INSERT INTO `system_area` VALUES ('220182', '榆树市', '220100', 'JL', null);
INSERT INTO `system_area` VALUES ('220183', '德惠市', '220100', 'JL', null);
INSERT INTO `system_area` VALUES ('220200', '吉林市', '220000', 'JL', null);
INSERT INTO `system_area` VALUES ('220201', '市辖区', '220200', 'JL', null);
INSERT INTO `system_area` VALUES ('220202', '昌邑区', '220200', 'JL', null);
INSERT INTO `system_area` VALUES ('220203', '龙潭区', '220200', 'JL', null);
INSERT INTO `system_area` VALUES ('220204', '船营区', '220200', 'JL', null);
INSERT INTO `system_area` VALUES ('220211', '丰满区', '220200', 'JL', null);
INSERT INTO `system_area` VALUES ('220221', '永吉县', '220200', 'JL', null);
INSERT INTO `system_area` VALUES ('220281', '蛟河市', '220200', 'JL', null);
INSERT INTO `system_area` VALUES ('220282', '桦甸市', '220200', 'JL', null);
INSERT INTO `system_area` VALUES ('220283', '舒兰市', '220200', 'JL', null);
INSERT INTO `system_area` VALUES ('220284', '磐石市', '220200', 'JL', null);
INSERT INTO `system_area` VALUES ('220300', '四平市', '220000', 'JL', null);
INSERT INTO `system_area` VALUES ('220301', '市辖区', '220300', 'JL', null);
INSERT INTO `system_area` VALUES ('220302', '铁西区', '220300', 'JL', null);
INSERT INTO `system_area` VALUES ('220303', '铁东区', '220300', 'JL', null);
INSERT INTO `system_area` VALUES ('220322', '梨树县', '220300', 'JL', null);
INSERT INTO `system_area` VALUES ('220323', '伊通满族自治县', '220300', 'JL', null);
INSERT INTO `system_area` VALUES ('220381', '公主岭市', '220300', 'JL', null);
INSERT INTO `system_area` VALUES ('220382', '双辽市', '220300', 'JL', null);
INSERT INTO `system_area` VALUES ('220400', '辽源市', '220000', 'JL', null);
INSERT INTO `system_area` VALUES ('220401', '市辖区', '220400', 'JL', null);
INSERT INTO `system_area` VALUES ('220402', '龙山区', '220400', 'JL', null);
INSERT INTO `system_area` VALUES ('220403', '西安区', '220400', 'JL', null);
INSERT INTO `system_area` VALUES ('220421', '东丰县', '220400', 'JL', null);
INSERT INTO `system_area` VALUES ('220422', '东辽县', '220400', 'JL', null);
INSERT INTO `system_area` VALUES ('220500', '通化市', '220000', 'JL', null);
INSERT INTO `system_area` VALUES ('220501', '市辖区', '220500', 'JL', null);
INSERT INTO `system_area` VALUES ('220502', '东昌区', '220500', 'JL', null);
INSERT INTO `system_area` VALUES ('220503', '二道江区', '220500', 'JL', null);
INSERT INTO `system_area` VALUES ('220521', '通化县', '220500', 'JL', null);
INSERT INTO `system_area` VALUES ('220523', '辉南县', '220500', 'JL', null);
INSERT INTO `system_area` VALUES ('220524', '柳河县', '220500', 'JL', null);
INSERT INTO `system_area` VALUES ('220581', '梅河口市', '220500', 'JL', null);
INSERT INTO `system_area` VALUES ('220582', '集安市', '220500', 'JL', null);
INSERT INTO `system_area` VALUES ('220600', '白山市', '220000', 'JL', null);
INSERT INTO `system_area` VALUES ('220601', '市辖区', '220600', 'JL', null);
INSERT INTO `system_area` VALUES ('220602', '八道江区', '220600', 'JL', null);
INSERT INTO `system_area` VALUES ('220621', '抚松县', '220600', 'JL', null);
INSERT INTO `system_area` VALUES ('220622', '靖宇县', '220600', 'JL', null);
INSERT INTO `system_area` VALUES ('220623', '长白朝鲜族自治县', '220600', 'JL', null);
INSERT INTO `system_area` VALUES ('220625', '江源县', '220600', 'JL', null);
INSERT INTO `system_area` VALUES ('220681', '临江市', '220600', 'JL', null);
INSERT INTO `system_area` VALUES ('220700', '松原市', '220000', 'JL', null);
INSERT INTO `system_area` VALUES ('220701', '市辖区', '220700', 'JL', null);
INSERT INTO `system_area` VALUES ('220702', '宁江区', '220700', 'JL', null);
INSERT INTO `system_area` VALUES ('220721', '前郭尔罗斯蒙古族自治', '220700', 'JL', null);
INSERT INTO `system_area` VALUES ('220722', '长岭县', '220700', 'JL', null);
INSERT INTO `system_area` VALUES ('220723', '乾安县', '220700', 'JL', null);
INSERT INTO `system_area` VALUES ('220724', '扶余县', '220700', 'JL', null);
INSERT INTO `system_area` VALUES ('220800', '白城市', '220000', 'JL', null);
INSERT INTO `system_area` VALUES ('220801', '市辖区', '220800', 'JL', null);
INSERT INTO `system_area` VALUES ('220802', '洮北区', '220800', 'JL', null);
INSERT INTO `system_area` VALUES ('220821', '镇赉县', '220800', 'JL', null);
INSERT INTO `system_area` VALUES ('220822', '通榆县', '220800', 'JL', null);
INSERT INTO `system_area` VALUES ('220881', '洮南市', '220800', 'JL', null);
INSERT INTO `system_area` VALUES ('220882', '大安市', '220800', 'JL', null);
INSERT INTO `system_area` VALUES ('222400', '延边朝鲜族自治州', '220000', 'JL', null);
INSERT INTO `system_area` VALUES ('222401', '延吉市', '222400', 'JL', null);
INSERT INTO `system_area` VALUES ('222402', '图们市', '222400', 'JL', null);
INSERT INTO `system_area` VALUES ('222403', '敦化市', '222400', 'JL', null);
INSERT INTO `system_area` VALUES ('222404', '珲春市', '222400', 'JL', null);
INSERT INTO `system_area` VALUES ('222405', '龙井市', '222400', 'JL', null);
INSERT INTO `system_area` VALUES ('222406', '和龙市', '222400', 'JL', null);
INSERT INTO `system_area` VALUES ('222424', '汪清县', '222400', 'JL', null);
INSERT INTO `system_area` VALUES ('222426', '安图县', '222400', 'JL', null);
INSERT INTO `system_area` VALUES ('230000', '黑龙江省', '0001', 'HL', 'DB');
INSERT INTO `system_area` VALUES ('230100', '哈尔滨市', '230000', 'HL', null);
INSERT INTO `system_area` VALUES ('230101', '市辖区', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230102', '道里区', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230103', '南岗区', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230104', '道外区', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230106', '香坊区', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230107', '动力区', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230108', '平房区', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230109', '松北区', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230111', '呼兰区', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230123', '依兰县', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230124', '方正县', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230125', '宾县', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230126', '巴彦县', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230127', '木兰县', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230128', '通河县', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230129', '延寿县', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230181', '阿城市', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230182', '双城市', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230183', '尚志市', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230184', '五常市', '230100', 'HL', null);
INSERT INTO `system_area` VALUES ('230200', '齐齐哈尔市', '230000', 'HL', null);
INSERT INTO `system_area` VALUES ('230201', '市辖区', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230202', '龙沙区', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230203', '建华区', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230204', '铁锋区', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230205', '昂昂溪区', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230206', '富拉尔基区', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230207', '碾子山区', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230208', '梅里斯达斡尔族区', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230221', '龙江县', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230223', '依安县', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230224', '泰来县', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230225', '甘南县', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230227', '富裕县', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230229', '克山县', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230230', '克东县', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230231', '拜泉县', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230281', '讷河市', '230200', 'HL', null);
INSERT INTO `system_area` VALUES ('230300', '鸡西市', '230000', 'HL', null);
INSERT INTO `system_area` VALUES ('230301', '市辖区', '230300', 'HL', null);
INSERT INTO `system_area` VALUES ('230302', '鸡冠区', '230300', 'HL', null);
INSERT INTO `system_area` VALUES ('230303', '恒山区', '230300', 'HL', null);
INSERT INTO `system_area` VALUES ('230304', '滴道区', '230300', 'HL', null);
INSERT INTO `system_area` VALUES ('230305', '梨树区', '230300', 'HL', null);
INSERT INTO `system_area` VALUES ('230306', '城子河区', '230300', 'HL', null);
INSERT INTO `system_area` VALUES ('230307', '麻山区', '230300', 'HL', null);
INSERT INTO `system_area` VALUES ('230321', '鸡东县', '230300', 'HL', null);
INSERT INTO `system_area` VALUES ('230381', '虎林市', '230300', 'HL', null);
INSERT INTO `system_area` VALUES ('230382', '密山市', '230300', 'HL', null);
INSERT INTO `system_area` VALUES ('230400', '鹤岗市', '230000', 'HL', null);
INSERT INTO `system_area` VALUES ('230401', '市辖区', '230400', 'HL', null);
INSERT INTO `system_area` VALUES ('230402', '向阳区', '230400', 'HL', null);
INSERT INTO `system_area` VALUES ('230403', '工农区', '230400', 'HL', null);
INSERT INTO `system_area` VALUES ('230404', '南山区', '230400', 'HL', null);
INSERT INTO `system_area` VALUES ('230405', '兴安区', '230400', 'HL', null);
INSERT INTO `system_area` VALUES ('230406', '东山区', '230400', 'HL', null);
INSERT INTO `system_area` VALUES ('230407', '兴山区', '230400', 'HL', null);
INSERT INTO `system_area` VALUES ('230421', '萝北县', '230400', 'HL', null);
INSERT INTO `system_area` VALUES ('230422', '绥滨县', '230400', 'HL', null);
INSERT INTO `system_area` VALUES ('230500', '双鸭山市', '230000', 'HL', null);
INSERT INTO `system_area` VALUES ('230501', '市辖区', '230500', 'HL', null);
INSERT INTO `system_area` VALUES ('230502', '尖山区', '230500', 'HL', null);
INSERT INTO `system_area` VALUES ('230503', '岭东区', '230500', 'HL', null);
INSERT INTO `system_area` VALUES ('230505', '四方台区', '230500', 'HL', null);
INSERT INTO `system_area` VALUES ('230506', '宝山区', '230500', 'HL', null);
INSERT INTO `system_area` VALUES ('230521', '集贤县', '230500', 'HL', null);
INSERT INTO `system_area` VALUES ('230522', '友谊县', '230500', 'HL', null);
INSERT INTO `system_area` VALUES ('230523', '宝清县', '230500', 'HL', null);
INSERT INTO `system_area` VALUES ('230524', '饶河县', '230500', 'HL', null);
INSERT INTO `system_area` VALUES ('230600', '大庆市', '230000', 'HL', null);
INSERT INTO `system_area` VALUES ('230601', '市辖区', '230600', 'HL', null);
INSERT INTO `system_area` VALUES ('230602', '萨尔图区', '230600', 'HL', null);
INSERT INTO `system_area` VALUES ('230603', '龙凤区', '230600', 'HL', null);
INSERT INTO `system_area` VALUES ('230604', '让胡路区', '230600', 'HL', null);
INSERT INTO `system_area` VALUES ('230605', '红岗区', '230600', 'HL', null);
INSERT INTO `system_area` VALUES ('230606', '大同区', '230600', 'HL', null);
INSERT INTO `system_area` VALUES ('230621', '肇州县', '230600', 'HL', null);
INSERT INTO `system_area` VALUES ('230622', '肇源县', '230600', 'HL', null);
INSERT INTO `system_area` VALUES ('230623', '林甸县', '230600', 'HL', null);
INSERT INTO `system_area` VALUES ('230624', '杜尔伯特蒙古族自治县', '230600', 'HL', null);
INSERT INTO `system_area` VALUES ('230700', '伊春市', '230000', 'HL', null);
INSERT INTO `system_area` VALUES ('230701', '市辖区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230702', '伊春区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230703', '南岔区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230704', '友好区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230705', '西林区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230706', '翠峦区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230707', '新青区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230708', '美溪区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230709', '金山屯区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230710', '五营区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230711', '乌马河区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230712', '汤旺河区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230713', '带岭区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230714', '乌伊岭区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230715', '红星区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230716', '上甘岭区', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230722', '嘉荫县', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230781', '铁力市', '230700', 'HL', null);
INSERT INTO `system_area` VALUES ('230800', '佳木斯市', '230000', 'HL', null);
INSERT INTO `system_area` VALUES ('230801', '市辖区', '230800', 'HL', null);
INSERT INTO `system_area` VALUES ('230802', '永红区', '230800', 'HL', null);
INSERT INTO `system_area` VALUES ('230803', '向阳区', '230800', 'HL', null);
INSERT INTO `system_area` VALUES ('230804', '前进区', '230800', 'HL', null);
INSERT INTO `system_area` VALUES ('230805', '东风区', '230800', 'HL', null);
INSERT INTO `system_area` VALUES ('230811', '郊区', '230800', 'HL', null);
INSERT INTO `system_area` VALUES ('230822', '桦南县', '230800', 'HL', null);
INSERT INTO `system_area` VALUES ('230826', '桦川县', '230800', 'HL', null);
INSERT INTO `system_area` VALUES ('230828', '汤原县', '230800', 'HL', null);
INSERT INTO `system_area` VALUES ('230833', '抚远县', '230800', 'HL', null);
INSERT INTO `system_area` VALUES ('230881', '同江市', '230800', 'HL', null);
INSERT INTO `system_area` VALUES ('230882', '富锦市', '230800', 'HL', null);
INSERT INTO `system_area` VALUES ('230900', '七台河市', '230000', 'HL', null);
INSERT INTO `system_area` VALUES ('230901', '市辖区', '230900', 'HL', null);
INSERT INTO `system_area` VALUES ('230902', '新兴区', '230900', 'HL', null);
INSERT INTO `system_area` VALUES ('230903', '桃山区', '230900', 'HL', null);
INSERT INTO `system_area` VALUES ('230904', '茄子河区', '230900', 'HL', null);
INSERT INTO `system_area` VALUES ('230921', '勃利县', '230900', 'HL', null);
INSERT INTO `system_area` VALUES ('231000', '牡丹江市', '230000', 'HL', null);
INSERT INTO `system_area` VALUES ('231001', '市辖区', '231000', 'HL', null);
INSERT INTO `system_area` VALUES ('231002', '东安区', '231000', 'HL', null);
INSERT INTO `system_area` VALUES ('231003', '阳明区', '231000', 'HL', null);
INSERT INTO `system_area` VALUES ('231004', '爱民区', '231000', 'HL', null);
INSERT INTO `system_area` VALUES ('231005', '西安区', '231000', 'HL', null);
INSERT INTO `system_area` VALUES ('231024', '东宁县', '231000', 'HL', null);
INSERT INTO `system_area` VALUES ('231025', '林口县', '231000', 'HL', null);
INSERT INTO `system_area` VALUES ('231081', '绥芬河市', '231000', 'HL', null);
INSERT INTO `system_area` VALUES ('231083', '海林市', '231000', 'HL', null);
INSERT INTO `system_area` VALUES ('231084', '宁安市', '231000', 'HL', null);
INSERT INTO `system_area` VALUES ('231085', '穆棱市', '231000', 'HL', null);
INSERT INTO `system_area` VALUES ('231100', '黑河市', '230000', 'HL', null);
INSERT INTO `system_area` VALUES ('231101', '市辖区', '231100', 'HL', null);
INSERT INTO `system_area` VALUES ('231102', '爱辉区', '231100', 'HL', null);
INSERT INTO `system_area` VALUES ('231121', '嫩江县', '231100', 'HL', null);
INSERT INTO `system_area` VALUES ('231123', '逊克县', '231100', 'HL', null);
INSERT INTO `system_area` VALUES ('231124', '孙吴县', '231100', 'HL', null);
INSERT INTO `system_area` VALUES ('231181', '北安市', '231100', 'HL', null);
INSERT INTO `system_area` VALUES ('231182', '五大连池市', '231100', 'HL', null);
INSERT INTO `system_area` VALUES ('231200', '绥化市', '230000', 'HL', null);
INSERT INTO `system_area` VALUES ('231201', '市辖区', '231200', 'HL', null);
INSERT INTO `system_area` VALUES ('231202', '北林区', '231200', 'HL', null);
INSERT INTO `system_area` VALUES ('231221', '望奎县', '231200', 'HL', null);
INSERT INTO `system_area` VALUES ('231222', '兰西县', '231200', 'HL', null);
INSERT INTO `system_area` VALUES ('231223', '青冈县', '231200', 'HL', null);
INSERT INTO `system_area` VALUES ('231224', '庆安县', '231200', 'HL', null);
INSERT INTO `system_area` VALUES ('231225', '明水县', '231200', 'HL', null);
INSERT INTO `system_area` VALUES ('231226', '绥棱县', '231200', 'HL', null);
INSERT INTO `system_area` VALUES ('231281', '安达市', '231200', 'HL', null);
INSERT INTO `system_area` VALUES ('231282', '肇东市', '231200', 'HL', null);
INSERT INTO `system_area` VALUES ('231283', '海伦市', '231200', 'HL', null);
INSERT INTO `system_area` VALUES ('232700', '大兴安岭地区', '230000', 'HL', null);
INSERT INTO `system_area` VALUES ('232721', '呼玛县', '232700', 'HL', null);
INSERT INTO `system_area` VALUES ('232722', '塔河县', '232700', 'HL', null);
INSERT INTO `system_area` VALUES ('232723', '漠河县', '232700', 'HL', null);
INSERT INTO `system_area` VALUES ('232724', '呼中区', '232700', 'HL', null);
INSERT INTO `system_area` VALUES ('232725', '松岭区', '232700', 'HL', null);
INSERT INTO `system_area` VALUES ('232726', '新林区', '232700', 'HL', null);
INSERT INTO `system_area` VALUES ('232727', '加格达奇', '232700', 'HL', null);
INSERT INTO `system_area` VALUES ('310000', '上海市', '0001', 'SH', 'HD');
INSERT INTO `system_area` VALUES ('310100', '市辖区', '310000', 'SH', null);
INSERT INTO `system_area` VALUES ('310101', '黄浦区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310103', '卢湾区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310104', '徐汇区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310105', '长宁区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310106', '静安区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310107', '普陀区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310108', '闸北区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310109', '虹口区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310110', '杨浦区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310112', '闵行区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310113', '宝山区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310114', '嘉定区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310115', '浦东新区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310116', '金山区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310117', '松江区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310118', '青浦区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310119', '南汇区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310120', '奉贤区', '310100', 'SH', null);
INSERT INTO `system_area` VALUES ('310200', '县', '310000', 'SH', null);
INSERT INTO `system_area` VALUES ('310230', '崇明县', '310200', 'SH', null);
INSERT INTO `system_area` VALUES ('320000', '江苏省', '0001', 'JS', 'HD');
INSERT INTO `system_area` VALUES ('320100', '南京市', '320000', 'JS', null);
INSERT INTO `system_area` VALUES ('320101', '市辖区', '320100', 'JS', null);
INSERT INTO `system_area` VALUES ('320102', '玄武区', '320100', 'JS', null);
INSERT INTO `system_area` VALUES ('320103', '白下区', '320100', 'JS', null);
INSERT INTO `system_area` VALUES ('320104', '秦淮区', '320100', 'JS', null);
INSERT INTO `system_area` VALUES ('320105', '建邺区', '320100', 'JS', null);
INSERT INTO `system_area` VALUES ('320106', '鼓楼区', '320100', 'JS', null);
INSERT INTO `system_area` VALUES ('320107', '下关区', '320100', 'JS', null);
INSERT INTO `system_area` VALUES ('320111', '浦口区', '320100', 'JS', null);
INSERT INTO `system_area` VALUES ('320113', '栖霞区', '320100', 'JS', null);
INSERT INTO `system_area` VALUES ('320114', '雨花台区', '320100', 'JS', null);
INSERT INTO `system_area` VALUES ('320115', '江宁区', '320100', 'JS', null);
INSERT INTO `system_area` VALUES ('320116', '六合区', '320100', 'JS', null);
INSERT INTO `system_area` VALUES ('320124', '溧水县', '320100', 'JS', null);
INSERT INTO `system_area` VALUES ('320125', '高淳县', '320100', 'JS', null);
INSERT INTO `system_area` VALUES ('320200', '无锡市', '320000', 'JS', null);
INSERT INTO `system_area` VALUES ('320201', '市辖区', '320200', 'JS', null);
INSERT INTO `system_area` VALUES ('320202', '崇安区', '320200', 'JS', null);
INSERT INTO `system_area` VALUES ('320203', '南长区', '320200', 'JS', null);
INSERT INTO `system_area` VALUES ('320204', '北塘区', '320200', 'JS', null);
INSERT INTO `system_area` VALUES ('320205', '锡山区', '320200', 'JS', null);
INSERT INTO `system_area` VALUES ('320206', '惠山区', '320200', 'JS', null);
INSERT INTO `system_area` VALUES ('320211', '滨湖区', '320200', 'JS', null);
INSERT INTO `system_area` VALUES ('320281', '江阴市', '320200', 'JS', null);
INSERT INTO `system_area` VALUES ('320282', '宜兴市', '320200', 'JS', null);
INSERT INTO `system_area` VALUES ('320300', '徐州市', '320000', 'JS', null);
INSERT INTO `system_area` VALUES ('320301', '市辖区', '320300', 'JS', null);
INSERT INTO `system_area` VALUES ('320302', '鼓楼区', '320300', 'JS', null);
INSERT INTO `system_area` VALUES ('320303', '云龙区', '320300', 'JS', null);
INSERT INTO `system_area` VALUES ('320304', '九里区', '320300', 'JS', null);
INSERT INTO `system_area` VALUES ('320305', '贾汪区', '320300', 'JS', null);
INSERT INTO `system_area` VALUES ('320311', '泉山区', '320300', 'JS', null);
INSERT INTO `system_area` VALUES ('320321', '丰县', '320300', 'JS', null);
INSERT INTO `system_area` VALUES ('320322', '沛县', '320300', 'JS', null);
INSERT INTO `system_area` VALUES ('320323', '铜山县', '320300', 'JS', null);
INSERT INTO `system_area` VALUES ('320324', '睢宁县', '320300', 'JS', null);
INSERT INTO `system_area` VALUES ('320381', '新沂市', '320300', 'JS', null);
INSERT INTO `system_area` VALUES ('320382', '邳州市', '320300', 'JS', null);
INSERT INTO `system_area` VALUES ('320400', '常州市', '320000', 'JS', null);
INSERT INTO `system_area` VALUES ('320401', '市辖区', '320400', 'JS', null);
INSERT INTO `system_area` VALUES ('320402', '天宁区', '320400', 'JS', null);
INSERT INTO `system_area` VALUES ('320404', '钟楼区', '320400', 'JS', null);
INSERT INTO `system_area` VALUES ('320405', '戚墅堰区', '320400', 'JS', null);
INSERT INTO `system_area` VALUES ('320411', '新北区', '320400', 'JS', null);
INSERT INTO `system_area` VALUES ('320412', '武进区', '320400', 'JS', null);
INSERT INTO `system_area` VALUES ('320481', '溧阳市', '320400', 'JS', null);
INSERT INTO `system_area` VALUES ('320482', '金坛市', '320400', 'JS', null);
INSERT INTO `system_area` VALUES ('320500', '苏州市', '320000', 'JS', null);
INSERT INTO `system_area` VALUES ('320501', '市辖区', '320500', 'JS', null);
INSERT INTO `system_area` VALUES ('320502', '沧浪区', '320500', 'JS', null);
INSERT INTO `system_area` VALUES ('320503', '平江区', '320500', 'JS', null);
INSERT INTO `system_area` VALUES ('320504', '金阊区', '320500', 'JS', null);
INSERT INTO `system_area` VALUES ('320505', '虎丘区', '320500', 'JS', null);
INSERT INTO `system_area` VALUES ('320506', '吴中区', '320500', 'JS', null);
INSERT INTO `system_area` VALUES ('320507', '相城区', '320500', 'JS', null);
INSERT INTO `system_area` VALUES ('320581', '常熟市', '320500', 'JS', null);
INSERT INTO `system_area` VALUES ('320582', '张家港市', '320500', 'JS', null);
INSERT INTO `system_area` VALUES ('320583', '昆山市', '320500', 'JS', null);
INSERT INTO `system_area` VALUES ('320584', '吴江市', '320500', 'JS', null);
INSERT INTO `system_area` VALUES ('320585', '太仓市', '320500', 'JS', null);
INSERT INTO `system_area` VALUES ('320600', '南通市', '320000', 'JS', null);
INSERT INTO `system_area` VALUES ('320601', '市辖区', '320600', 'JS', null);
INSERT INTO `system_area` VALUES ('320602', '崇川区', '320600', 'JS', null);
INSERT INTO `system_area` VALUES ('320611', '港闸区', '320600', 'JS', null);
INSERT INTO `system_area` VALUES ('320621', '海安县', '320600', 'JS', null);
INSERT INTO `system_area` VALUES ('320623', '如东县', '320600', 'JS', null);
INSERT INTO `system_area` VALUES ('320681', '启东市', '320600', 'JS', null);
INSERT INTO `system_area` VALUES ('320682', '如皋市', '320600', 'JS', null);
INSERT INTO `system_area` VALUES ('320683', '通州市', '320600', 'JS', null);
INSERT INTO `system_area` VALUES ('320684', '海门市', '320600', 'JS', null);
INSERT INTO `system_area` VALUES ('320700', '连云港市', '320000', 'JS', null);
INSERT INTO `system_area` VALUES ('320701', '市辖区', '320700', 'JS', null);
INSERT INTO `system_area` VALUES ('320703', '连云区', '320700', 'JS', null);
INSERT INTO `system_area` VALUES ('320705', '新浦区', '320700', 'JS', null);
INSERT INTO `system_area` VALUES ('320706', '海州区', '320700', 'JS', null);
INSERT INTO `system_area` VALUES ('320721', '赣榆县', '320700', 'JS', null);
INSERT INTO `system_area` VALUES ('320722', '东海县', '320700', 'JS', null);
INSERT INTO `system_area` VALUES ('320723', '灌云县', '320700', 'JS', null);
INSERT INTO `system_area` VALUES ('320724', '灌南县', '320700', 'JS', null);
INSERT INTO `system_area` VALUES ('320800', '淮安市', '320000', 'JS', null);
INSERT INTO `system_area` VALUES ('320801', '市辖区', '320800', 'JS', null);
INSERT INTO `system_area` VALUES ('320802', '清河区', '320800', 'JS', null);
INSERT INTO `system_area` VALUES ('320803', '楚州区', '320800', 'JS', null);
INSERT INTO `system_area` VALUES ('320804', '淮阴区', '320800', 'JS', null);
INSERT INTO `system_area` VALUES ('320811', '清浦区', '320800', 'JS', null);
INSERT INTO `system_area` VALUES ('320826', '涟水县', '320800', 'JS', null);
INSERT INTO `system_area` VALUES ('320829', '洪泽县', '320800', 'JS', null);
INSERT INTO `system_area` VALUES ('320830', '盱眙县', '320800', 'JS', null);
INSERT INTO `system_area` VALUES ('320831', '金湖县', '320800', 'JS', null);
INSERT INTO `system_area` VALUES ('320900', '盐城市', '320000', 'JS', null);
INSERT INTO `system_area` VALUES ('320901', '市辖区', '320900', 'JS', null);
INSERT INTO `system_area` VALUES ('320902', '亭湖区', '320900', 'JS', null);
INSERT INTO `system_area` VALUES ('320903', '盐都区', '320900', 'JS', null);
INSERT INTO `system_area` VALUES ('320921', '响水县', '320900', 'JS', null);
INSERT INTO `system_area` VALUES ('320922', '滨海县', '320900', 'JS', null);
INSERT INTO `system_area` VALUES ('320923', '阜宁县', '320900', 'JS', null);
INSERT INTO `system_area` VALUES ('320924', '射阳县', '320900', 'JS', null);
INSERT INTO `system_area` VALUES ('320925', '建湖县', '320900', 'JS', null);
INSERT INTO `system_area` VALUES ('320981', '东台市', '320900', 'JS', null);
INSERT INTO `system_area` VALUES ('320982', '大丰市', '320900', 'JS', null);
INSERT INTO `system_area` VALUES ('321000', '扬州市', '320000', 'JS', null);
INSERT INTO `system_area` VALUES ('321001', '市辖区', '321000', 'JS', null);
INSERT INTO `system_area` VALUES ('321002', '广陵区', '321000', 'JS', null);
INSERT INTO `system_area` VALUES ('321003', '邗江区', '321000', 'JS', null);
INSERT INTO `system_area` VALUES ('321011', '维扬区', '321000', 'JS', null);
INSERT INTO `system_area` VALUES ('321023', '宝应县', '321000', 'JS', null);
INSERT INTO `system_area` VALUES ('321081', '仪征市', '321000', 'JS', null);
INSERT INTO `system_area` VALUES ('321084', '高邮市', '321000', 'JS', null);
INSERT INTO `system_area` VALUES ('321088', '江都市', '321000', 'JS', null);
INSERT INTO `system_area` VALUES ('321100', '镇江市', '320000', 'JS', null);
INSERT INTO `system_area` VALUES ('321101', '市辖区', '321100', 'JS', null);
INSERT INTO `system_area` VALUES ('321102', '京口区', '321100', 'JS', null);
INSERT INTO `system_area` VALUES ('321111', '润州区', '321100', 'JS', null);
INSERT INTO `system_area` VALUES ('321112', '丹徒区', '321100', 'JS', null);
INSERT INTO `system_area` VALUES ('321181', '丹阳市', '321100', 'JS', null);
INSERT INTO `system_area` VALUES ('321182', '扬中市', '321100', 'JS', null);
INSERT INTO `system_area` VALUES ('321183', '句容市', '321100', 'JS', null);
INSERT INTO `system_area` VALUES ('321200', '泰州市', '320000', 'JS', null);
INSERT INTO `system_area` VALUES ('321201', '市辖区', '321200', 'JS', null);
INSERT INTO `system_area` VALUES ('321202', '海陵区', '321200', 'JS', null);
INSERT INTO `system_area` VALUES ('321203', '高港区', '321200', 'JS', null);
INSERT INTO `system_area` VALUES ('321281', '兴化市', '321200', 'JS', null);
INSERT INTO `system_area` VALUES ('321282', '靖江市', '321200', 'JS', null);
INSERT INTO `system_area` VALUES ('321283', '泰兴市', '321200', 'JS', null);
INSERT INTO `system_area` VALUES ('321284', '姜堰市', '321200', 'JS', null);
INSERT INTO `system_area` VALUES ('321300', '宿迁市', '320000', 'JS', null);
INSERT INTO `system_area` VALUES ('321301', '市辖区', '321300', 'JS', null);
INSERT INTO `system_area` VALUES ('321302', '宿城区', '321300', 'JS', null);
INSERT INTO `system_area` VALUES ('321311', '宿豫区', '321300', 'JS', null);
INSERT INTO `system_area` VALUES ('321322', '沭阳县', '321300', 'JS', null);
INSERT INTO `system_area` VALUES ('321323', '泗阳县', '321300', 'JS', null);
INSERT INTO `system_area` VALUES ('321324', '泗洪县', '321300', 'JS', null);
INSERT INTO `system_area` VALUES ('330000', '浙江省', '0001', 'ZJ', 'HD');
INSERT INTO `system_area` VALUES ('330100', '杭州市', '330000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330101', '市辖区', '330100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330102', '上城区', '330100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330103', '下城区', '330100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330104', '江干区', '330100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330105', '拱墅区', '330100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330106', '西湖区', '330100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330108', '滨江区', '330100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330109', '萧山区', '330100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330110', '余杭区', '330100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330122', '桐庐县', '330100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330127', '淳安县', '330100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330182', '建德市', '330100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330183', '富阳市', '330100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330185', '临安市', '330100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330200', '宁波市', '330000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330201', '市辖区', '330200', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330203', '海曙区', '330200', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330204', '江东区', '330200', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330205', '江北区', '330200', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330206', '北仑区', '330200', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330211', '镇海区', '330200', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330212', '鄞州区', '330200', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330225', '象山县', '330200', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330226', '宁海县', '330200', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330281', '余姚市', '330200', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330282', '慈溪市', '330200', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330283', '奉化市', '330200', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330300', '温州市', '330000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330301', '市辖区', '330300', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330302', '鹿城区', '330300', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330303', '龙湾区', '330300', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330304', '瓯海区', '330300', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330322', '洞头县', '330300', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330324', '永嘉县', '330300', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330326', '平阳县', '330300', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330327', '苍南县', '330300', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330328', '文成县', '330300', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330329', '泰顺县', '330300', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330381', '瑞安市', '330300', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330382', '乐清市', '330300', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330400', '嘉兴市', '330000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330401', '市辖区', '330400', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330402', '秀城区', '330400', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330411', '秀洲区', '330400', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330421', '嘉善县', '330400', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330424', '海盐县', '330400', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330481', '海宁市', '330400', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330482', '平湖市', '330400', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330483', '桐乡市', '330400', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330500', '湖州市', '330000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330501', '市辖区', '330500', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330502', '吴兴区', '330500', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330503', '南浔区', '330500', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330521', '德清县', '330500', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330522', '长兴县', '330500', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330523', '安吉县', '330500', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330600', '绍兴市', '330000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330601', '市辖区', '330600', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330602', '越城区', '330600', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330621', '绍兴县', '330600', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330624', '新昌县', '330600', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330681', '诸暨市', '330600', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330682', '上虞市', '330600', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330683', '嵊州市', '330600', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330700', '金华市', '330000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330701', '市辖区', '330700', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330702', '婺城区', '330700', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330703', '金东区', '330700', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330723', '武义县', '330700', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330726', '浦江县', '330700', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330727', '磐安县', '330700', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330781', '兰溪市', '330700', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330782', '义乌市', '330700', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330783', '东阳市', '330700', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330784', '永康市', '330700', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330800', '衢州市', '330000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330801', '市辖区', '330800', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330802', '柯城区', '330800', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330803', '衢江区', '330800', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330822', '常山县', '330800', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330824', '开化县', '330800', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330825', '龙游县', '330800', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330881', '江山市', '330800', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330900', '舟山市', '330000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330901', '市辖区', '330900', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330902', '定海区', '330900', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330903', '普陀区', '330900', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330921', '岱山县', '330900', 'ZJ', null);
INSERT INTO `system_area` VALUES ('330922', '嵊泗县', '330900', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331000', '台州市', '330000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331001', '市辖区', '331000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331002', '椒江区', '331000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331003', '黄岩区', '331000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331004', '路桥区', '331000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331021', '玉环县', '331000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331022', '三门县', '331000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331023', '天台县', '331000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331024', '仙居县', '331000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331081', '温岭市', '331000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331082', '临海市', '331000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331100', '丽水市', '330000', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331101', '市辖区', '331100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331102', '莲都区', '331100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331121', '青田县', '331100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331122', '缙云县', '331100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331123', '遂昌县', '331100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331124', '松阳县', '331100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331125', '云和县', '331100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331126', '庆元县', '331100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331127', '景宁畲族自治县', '331100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('331181', '龙泉市', '331100', 'ZJ', null);
INSERT INTO `system_area` VALUES ('340000', '安徽省', '0001', 'AH', 'HD');
INSERT INTO `system_area` VALUES ('340100', '合肥市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('340101', '市辖区', '340100', 'AH', null);
INSERT INTO `system_area` VALUES ('340102', '瑶海区', '340100', 'AH', null);
INSERT INTO `system_area` VALUES ('340103', '庐阳区', '340100', 'AH', null);
INSERT INTO `system_area` VALUES ('340104', '蜀山区', '340100', 'AH', null);
INSERT INTO `system_area` VALUES ('340111', '包河区', '340100', 'AH', null);
INSERT INTO `system_area` VALUES ('340121', '长丰县', '340100', 'AH', null);
INSERT INTO `system_area` VALUES ('340122', '肥东县', '340100', 'AH', null);
INSERT INTO `system_area` VALUES ('340123', '肥西县', '340100', 'AH', null);
INSERT INTO `system_area` VALUES ('340200', '芜湖市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('340201', '市辖区', '340200', 'AH', null);
INSERT INTO `system_area` VALUES ('340202', '镜湖区', '340200', 'AH', null);
INSERT INTO `system_area` VALUES ('340203', '马塘区', '340200', 'AH', null);
INSERT INTO `system_area` VALUES ('340204', '新芜区', '340200', 'AH', null);
INSERT INTO `system_area` VALUES ('340207', '鸠江区', '340200', 'AH', null);
INSERT INTO `system_area` VALUES ('340221', '芜湖县', '340200', 'AH', null);
INSERT INTO `system_area` VALUES ('340222', '繁昌县', '340200', 'AH', null);
INSERT INTO `system_area` VALUES ('340223', '南陵县', '340200', 'AH', null);
INSERT INTO `system_area` VALUES ('340300', '蚌埠市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('340301', '市辖区', '340300', 'AH', null);
INSERT INTO `system_area` VALUES ('340302', '龙子湖区', '340300', 'AH', null);
INSERT INTO `system_area` VALUES ('340303', '蚌山区', '340300', 'AH', null);
INSERT INTO `system_area` VALUES ('340304', '禹会区', '340300', 'AH', null);
INSERT INTO `system_area` VALUES ('340311', '淮上区', '340300', 'AH', null);
INSERT INTO `system_area` VALUES ('340321', '怀远县', '340300', 'AH', null);
INSERT INTO `system_area` VALUES ('340322', '五河县', '340300', 'AH', null);
INSERT INTO `system_area` VALUES ('340323', '固镇县', '340300', 'AH', null);
INSERT INTO `system_area` VALUES ('340400', '淮南市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('340401', '市辖区', '340400', 'AH', null);
INSERT INTO `system_area` VALUES ('340402', '大通区', '340400', 'AH', null);
INSERT INTO `system_area` VALUES ('340403', '田家庵区', '340400', 'AH', null);
INSERT INTO `system_area` VALUES ('340404', '谢家集区', '340400', 'AH', null);
INSERT INTO `system_area` VALUES ('340405', '八公山区', '340400', 'AH', null);
INSERT INTO `system_area` VALUES ('340406', '潘集区', '340400', 'AH', null);
INSERT INTO `system_area` VALUES ('340421', '凤台县', '340400', 'AH', null);
INSERT INTO `system_area` VALUES ('340500', '马鞍山市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('340501', '市辖区', '340500', 'AH', null);
INSERT INTO `system_area` VALUES ('340502', '金家庄区', '340500', 'AH', null);
INSERT INTO `system_area` VALUES ('340503', '花山区', '340500', 'AH', null);
INSERT INTO `system_area` VALUES ('340504', '雨山区', '340500', 'AH', null);
INSERT INTO `system_area` VALUES ('340521', '当涂县', '340500', 'AH', null);
INSERT INTO `system_area` VALUES ('340600', '淮北市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('340601', '市辖区', '340600', 'AH', null);
INSERT INTO `system_area` VALUES ('340602', '杜集区', '340600', 'AH', null);
INSERT INTO `system_area` VALUES ('340603', '相山区', '340600', 'AH', null);
INSERT INTO `system_area` VALUES ('340604', '烈山区', '340600', 'AH', null);
INSERT INTO `system_area` VALUES ('340621', '濉溪县', '340600', 'AH', null);
INSERT INTO `system_area` VALUES ('340700', '铜陵市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('340701', '市辖区', '340700', 'AH', null);
INSERT INTO `system_area` VALUES ('340702', '铜官山区', '340700', 'AH', null);
INSERT INTO `system_area` VALUES ('340703', '狮子山区', '340700', 'AH', null);
INSERT INTO `system_area` VALUES ('340711', '郊区', '340700', 'AH', null);
INSERT INTO `system_area` VALUES ('340721', '铜陵县', '340700', 'AH', null);
INSERT INTO `system_area` VALUES ('340800', '安庆市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('340801', '市辖区', '340800', 'AH', null);
INSERT INTO `system_area` VALUES ('340802', '迎江区', '340800', 'AH', null);
INSERT INTO `system_area` VALUES ('340803', '大观区', '340800', 'AH', null);
INSERT INTO `system_area` VALUES ('340811', '郊区', '340800', 'AH', null);
INSERT INTO `system_area` VALUES ('340822', '怀宁县', '340800', 'AH', null);
INSERT INTO `system_area` VALUES ('340823', '枞阳县', '340800', 'AH', null);
INSERT INTO `system_area` VALUES ('340824', '潜山县', '340800', 'AH', null);
INSERT INTO `system_area` VALUES ('340825', '太湖县', '340800', 'AH', null);
INSERT INTO `system_area` VALUES ('340826', '宿松县', '340800', 'AH', null);
INSERT INTO `system_area` VALUES ('340827', '望江县', '340800', 'AH', null);
INSERT INTO `system_area` VALUES ('340828', '岳西县', '340800', 'AH', null);
INSERT INTO `system_area` VALUES ('340881', '桐城市', '340800', 'AH', null);
INSERT INTO `system_area` VALUES ('341000', '黄山市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('341001', '市辖区', '341000', 'AH', null);
INSERT INTO `system_area` VALUES ('341002', '屯溪区', '341000', 'AH', null);
INSERT INTO `system_area` VALUES ('341003', '黄山区', '341000', 'AH', null);
INSERT INTO `system_area` VALUES ('341004', '徽州区', '341000', 'AH', null);
INSERT INTO `system_area` VALUES ('341021', '歙县', '341000', 'AH', null);
INSERT INTO `system_area` VALUES ('341022', '休宁县', '341000', 'AH', null);
INSERT INTO `system_area` VALUES ('341023', '黟县', '341000', 'AH', null);
INSERT INTO `system_area` VALUES ('341024', '祁门县', '341000', 'AH', null);
INSERT INTO `system_area` VALUES ('341100', '滁州市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('341101', '市辖区', '341100', 'AH', null);
INSERT INTO `system_area` VALUES ('341102', '琅琊区', '341100', 'AH', null);
INSERT INTO `system_area` VALUES ('341103', '南谯区', '341100', 'AH', null);
INSERT INTO `system_area` VALUES ('341122', '来安县', '341100', 'AH', null);
INSERT INTO `system_area` VALUES ('341124', '全椒县', '341100', 'AH', null);
INSERT INTO `system_area` VALUES ('341125', '定远县', '341100', 'AH', null);
INSERT INTO `system_area` VALUES ('341126', '凤阳县', '341100', 'AH', null);
INSERT INTO `system_area` VALUES ('341181', '天长市', '341100', 'AH', null);
INSERT INTO `system_area` VALUES ('341182', '明光市', '341100', 'AH', null);
INSERT INTO `system_area` VALUES ('341200', '阜阳市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('341201', '市辖区', '341200', 'AH', null);
INSERT INTO `system_area` VALUES ('341202', '颍州区', '341200', 'AH', null);
INSERT INTO `system_area` VALUES ('341203', '颍东区', '341200', 'AH', null);
INSERT INTO `system_area` VALUES ('341204', '颍泉区', '341200', 'AH', null);
INSERT INTO `system_area` VALUES ('341221', '临泉县', '341200', 'AH', null);
INSERT INTO `system_area` VALUES ('341222', '太和县', '341200', 'AH', null);
INSERT INTO `system_area` VALUES ('341225', '阜南县', '341200', 'AH', null);
INSERT INTO `system_area` VALUES ('341226', '颍上县', '341200', 'AH', null);
INSERT INTO `system_area` VALUES ('341282', '界首市', '341200', 'AH', null);
INSERT INTO `system_area` VALUES ('341300', '宿州市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('341301', '市辖区', '341300', 'AH', null);
INSERT INTO `system_area` VALUES ('341302', '?桥区', '341300', 'AH', null);
INSERT INTO `system_area` VALUES ('341321', '砀山县', '341300', 'AH', null);
INSERT INTO `system_area` VALUES ('341322', '萧县', '341300', 'AH', null);
INSERT INTO `system_area` VALUES ('341323', '灵璧县', '341300', 'AH', null);
INSERT INTO `system_area` VALUES ('341324', '泗县', '341300', 'AH', null);
INSERT INTO `system_area` VALUES ('341400', '巢湖市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('341401', '市辖区', '341400', 'AH', null);
INSERT INTO `system_area` VALUES ('341402', '居巢区', '341400', 'AH', null);
INSERT INTO `system_area` VALUES ('341421', '庐江县', '341400', 'AH', null);
INSERT INTO `system_area` VALUES ('341422', '无为县', '341400', 'AH', null);
INSERT INTO `system_area` VALUES ('341423', '含山县', '341400', 'AH', null);
INSERT INTO `system_area` VALUES ('341424', '和县', '341400', 'AH', null);
INSERT INTO `system_area` VALUES ('341500', '六安市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('341501', '市辖区', '341500', 'AH', null);
INSERT INTO `system_area` VALUES ('341502', '金安区', '341500', 'AH', null);
INSERT INTO `system_area` VALUES ('341503', '裕安区', '341500', 'AH', null);
INSERT INTO `system_area` VALUES ('341521', '寿县', '341500', 'AH', null);
INSERT INTO `system_area` VALUES ('341522', '霍邱县', '341500', 'AH', null);
INSERT INTO `system_area` VALUES ('341523', '舒城县', '341500', 'AH', null);
INSERT INTO `system_area` VALUES ('341524', '金寨县', '341500', 'AH', null);
INSERT INTO `system_area` VALUES ('341525', '霍山县', '341500', 'AH', null);
INSERT INTO `system_area` VALUES ('341600', '亳州市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('341601', '市辖区', '341600', 'AH', null);
INSERT INTO `system_area` VALUES ('341602', '谯城区', '341600', 'AH', null);
INSERT INTO `system_area` VALUES ('341621', '涡阳县', '341600', 'AH', null);
INSERT INTO `system_area` VALUES ('341622', '蒙城县', '341600', 'AH', null);
INSERT INTO `system_area` VALUES ('341623', '利辛县', '341600', 'AH', null);
INSERT INTO `system_area` VALUES ('341700', '池州市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('341701', '市辖区', '341700', 'AH', null);
INSERT INTO `system_area` VALUES ('341702', '贵池区', '341700', 'AH', null);
INSERT INTO `system_area` VALUES ('341721', '东至县', '341700', 'AH', null);
INSERT INTO `system_area` VALUES ('341722', '石台县', '341700', 'AH', null);
INSERT INTO `system_area` VALUES ('341723', '青阳县', '341700', 'AH', null);
INSERT INTO `system_area` VALUES ('341800', '宣城市', '340000', 'AH', null);
INSERT INTO `system_area` VALUES ('341801', '市辖区', '341800', 'AH', null);
INSERT INTO `system_area` VALUES ('341802', '宣州区', '341800', 'AH', null);
INSERT INTO `system_area` VALUES ('341821', '郎溪县', '341800', 'AH', null);
INSERT INTO `system_area` VALUES ('341822', '广德县', '341800', 'AH', null);
INSERT INTO `system_area` VALUES ('341823', '泾县', '341800', 'AH', null);
INSERT INTO `system_area` VALUES ('341824', '绩溪县', '341800', 'AH', null);
INSERT INTO `system_area` VALUES ('341825', '旌德县', '341800', 'AH', null);
INSERT INTO `system_area` VALUES ('341881', '宁国市', '341800', 'AH', null);
INSERT INTO `system_area` VALUES ('350000', '福建省', '0001', 'FJ', 'HN');
INSERT INTO `system_area` VALUES ('350100', '福州市', '350000', 'FJ', null);
INSERT INTO `system_area` VALUES ('350101', '市辖区', '350100', 'FJ', null);
INSERT INTO `system_area` VALUES ('350102', '鼓楼区', '350100', 'FJ', null);
INSERT INTO `system_area` VALUES ('350103', '台江区', '350100', 'FJ', null);
INSERT INTO `system_area` VALUES ('350104', '仓山区', '350100', 'FJ', null);
INSERT INTO `system_area` VALUES ('350105', '马尾区', '350100', 'FJ', null);
INSERT INTO `system_area` VALUES ('350111', '晋安区', '350100', 'FJ', null);
INSERT INTO `system_area` VALUES ('350121', '闽侯县', '350100', 'FJ', null);
INSERT INTO `system_area` VALUES ('350122', '连江县', '350100', 'FJ', null);
INSERT INTO `system_area` VALUES ('350123', '罗源县', '350100', 'FJ', null);
INSERT INTO `system_area` VALUES ('350124', '闽清县', '350100', 'FJ', null);
INSERT INTO `system_area` VALUES ('350125', '永泰县', '350100', 'FJ', null);
INSERT INTO `system_area` VALUES ('350128', '平潭县', '350100', 'FJ', null);
INSERT INTO `system_area` VALUES ('350181', '福清市', '350100', 'FJ', null);
INSERT INTO `system_area` VALUES ('350182', '长乐市', '350100', 'FJ', null);
INSERT INTO `system_area` VALUES ('350200', '厦门市', '350000', 'FJ', null);
INSERT INTO `system_area` VALUES ('350201', '市辖区', '350200', 'FJ', null);
INSERT INTO `system_area` VALUES ('350203', '思明区', '350200', 'FJ', null);
INSERT INTO `system_area` VALUES ('350205', '海沧区', '350200', 'FJ', null);
INSERT INTO `system_area` VALUES ('350206', '湖里区', '350200', 'FJ', null);
INSERT INTO `system_area` VALUES ('350211', '集美区', '350200', 'FJ', null);
INSERT INTO `system_area` VALUES ('350212', '同安区', '350200', 'FJ', null);
INSERT INTO `system_area` VALUES ('350213', '翔安区', '350200', 'FJ', null);
INSERT INTO `system_area` VALUES ('350300', '莆田市', '350000', 'FJ', null);
INSERT INTO `system_area` VALUES ('350301', '市辖区', '350300', 'FJ', null);
INSERT INTO `system_area` VALUES ('350302', '城厢区', '350300', 'FJ', null);
INSERT INTO `system_area` VALUES ('350303', '涵江区', '350300', 'FJ', null);
INSERT INTO `system_area` VALUES ('350304', '荔城区', '350300', 'FJ', null);
INSERT INTO `system_area` VALUES ('350305', '秀屿区', '350300', 'FJ', null);
INSERT INTO `system_area` VALUES ('350322', '仙游县', '350300', 'FJ', null);
INSERT INTO `system_area` VALUES ('350400', '三明市', '350000', 'FJ', null);
INSERT INTO `system_area` VALUES ('350401', '市辖区', '350400', 'FJ', null);
INSERT INTO `system_area` VALUES ('350402', '梅列区', '350400', 'FJ', null);
INSERT INTO `system_area` VALUES ('350403', '三元区', '350400', 'FJ', null);
INSERT INTO `system_area` VALUES ('350421', '明溪县', '350400', 'FJ', null);
INSERT INTO `system_area` VALUES ('350423', '清流县', '350400', 'FJ', null);
INSERT INTO `system_area` VALUES ('350424', '宁化县', '350400', 'FJ', null);
INSERT INTO `system_area` VALUES ('350425', '大田县', '350400', 'FJ', null);
INSERT INTO `system_area` VALUES ('350426', '尤溪县', '350400', 'FJ', null);
INSERT INTO `system_area` VALUES ('350427', '沙县', '350400', 'FJ', null);
INSERT INTO `system_area` VALUES ('350428', '将乐县', '350400', 'FJ', null);
INSERT INTO `system_area` VALUES ('350429', '泰宁县', '350400', 'FJ', null);
INSERT INTO `system_area` VALUES ('350430', '建宁县', '350400', 'FJ', null);
INSERT INTO `system_area` VALUES ('350481', '永安市', '350400', 'FJ', null);
INSERT INTO `system_area` VALUES ('350500', '泉州市', '350000', 'FJ', null);
INSERT INTO `system_area` VALUES ('350501', '市辖区', '350500', 'FJ', null);
INSERT INTO `system_area` VALUES ('350502', '鲤城区', '350500', 'FJ', null);
INSERT INTO `system_area` VALUES ('350503', '丰泽区', '350500', 'FJ', null);
INSERT INTO `system_area` VALUES ('350504', '洛江区', '350500', 'FJ', null);
INSERT INTO `system_area` VALUES ('350505', '泉港区', '350500', 'FJ', null);
INSERT INTO `system_area` VALUES ('350521', '惠安县', '350500', 'FJ', null);
INSERT INTO `system_area` VALUES ('350524', '安溪县', '350500', 'FJ', null);
INSERT INTO `system_area` VALUES ('350525', '永春县', '350500', 'FJ', null);
INSERT INTO `system_area` VALUES ('350526', '德化县', '350500', 'FJ', null);
INSERT INTO `system_area` VALUES ('350527', '金门县', '350500', 'FJ', null);
INSERT INTO `system_area` VALUES ('350581', '石狮市', '350500', 'FJ', null);
INSERT INTO `system_area` VALUES ('350582', '晋江市', '350500', 'FJ', null);
INSERT INTO `system_area` VALUES ('350583', '南安市', '350500', 'FJ', null);
INSERT INTO `system_area` VALUES ('350600', '漳州市', '350000', 'FJ', null);
INSERT INTO `system_area` VALUES ('350601', '市辖区', '350600', 'FJ', null);
INSERT INTO `system_area` VALUES ('350602', '芗城区', '350600', 'FJ', null);
INSERT INTO `system_area` VALUES ('350603', '龙文区', '350600', 'FJ', null);
INSERT INTO `system_area` VALUES ('350622', '云霄县', '350600', 'FJ', null);
INSERT INTO `system_area` VALUES ('350623', '漳浦县', '350600', 'FJ', null);
INSERT INTO `system_area` VALUES ('350624', '诏安县', '350600', 'FJ', null);
INSERT INTO `system_area` VALUES ('350625', '长泰县', '350600', 'FJ', null);
INSERT INTO `system_area` VALUES ('350626', '东山县', '350600', 'FJ', null);
INSERT INTO `system_area` VALUES ('350627', '南靖县', '350600', 'FJ', null);
INSERT INTO `system_area` VALUES ('350628', '平和县', '350600', 'FJ', null);
INSERT INTO `system_area` VALUES ('350629', '华安县', '350600', 'FJ', null);
INSERT INTO `system_area` VALUES ('350681', '龙海市', '350600', 'FJ', null);
INSERT INTO `system_area` VALUES ('350700', '南平市', '350000', 'FJ', null);
INSERT INTO `system_area` VALUES ('350701', '市辖区', '350700', 'FJ', null);
INSERT INTO `system_area` VALUES ('350702', '延平区', '350700', 'FJ', null);
INSERT INTO `system_area` VALUES ('350721', '顺昌县', '350700', 'FJ', null);
INSERT INTO `system_area` VALUES ('350722', '浦城县', '350700', 'FJ', null);
INSERT INTO `system_area` VALUES ('350723', '光泽县', '350700', 'FJ', null);
INSERT INTO `system_area` VALUES ('350724', '松溪县', '350700', 'FJ', null);
INSERT INTO `system_area` VALUES ('350725', '政和县', '350700', 'FJ', null);
INSERT INTO `system_area` VALUES ('350781', '邵武市', '350700', 'FJ', null);
INSERT INTO `system_area` VALUES ('350782', '武夷山市', '350700', 'FJ', null);
INSERT INTO `system_area` VALUES ('350783', '建瓯市', '350700', 'FJ', null);
INSERT INTO `system_area` VALUES ('350784', '建阳市', '350700', 'FJ', null);
INSERT INTO `system_area` VALUES ('350800', '龙岩市', '350000', 'FJ', null);
INSERT INTO `system_area` VALUES ('350801', '市辖区', '350800', 'FJ', null);
INSERT INTO `system_area` VALUES ('350802', '新罗区', '350800', 'FJ', null);
INSERT INTO `system_area` VALUES ('350821', '长汀县', '350800', 'FJ', null);
INSERT INTO `system_area` VALUES ('350822', '永定县', '350800', 'FJ', null);
INSERT INTO `system_area` VALUES ('350823', '上杭县', '350800', 'FJ', null);
INSERT INTO `system_area` VALUES ('350824', '武平县', '350800', 'FJ', null);
INSERT INTO `system_area` VALUES ('350825', '连城县', '350800', 'FJ', null);
INSERT INTO `system_area` VALUES ('350881', '漳平市', '350800', 'FJ', null);
INSERT INTO `system_area` VALUES ('350900', '宁德市', '350000', 'FJ', null);
INSERT INTO `system_area` VALUES ('350901', '市辖区', '350900', 'FJ', null);
INSERT INTO `system_area` VALUES ('350902', '蕉城区', '350900', 'FJ', null);
INSERT INTO `system_area` VALUES ('350921', '霞浦县', '350900', 'FJ', null);
INSERT INTO `system_area` VALUES ('350922', '古田县', '350900', 'FJ', null);
INSERT INTO `system_area` VALUES ('350923', '屏南县', '350900', 'FJ', null);
INSERT INTO `system_area` VALUES ('350924', '寿宁县', '350900', 'FJ', null);
INSERT INTO `system_area` VALUES ('350925', '周宁县', '350900', 'FJ', null);
INSERT INTO `system_area` VALUES ('350926', '柘荣县', '350900', 'FJ', null);
INSERT INTO `system_area` VALUES ('350981', '福安市', '350900', 'FJ', null);
INSERT INTO `system_area` VALUES ('350982', '福鼎市', '350900', 'FJ', null);
INSERT INTO `system_area` VALUES ('360000', '江西省', '0001', 'JX', 'HD');
INSERT INTO `system_area` VALUES ('360100', '南昌市', '360000', 'JX', null);
INSERT INTO `system_area` VALUES ('360101', '市辖区', '360100', 'JX', null);
INSERT INTO `system_area` VALUES ('360102', '东湖区', '360100', 'JX', null);
INSERT INTO `system_area` VALUES ('360103', '西湖区', '360100', 'JX', null);
INSERT INTO `system_area` VALUES ('360104', '青云谱区', '360100', 'JX', null);
INSERT INTO `system_area` VALUES ('360105', '湾里区', '360100', 'JX', null);
INSERT INTO `system_area` VALUES ('360111', '青山湖区', '360100', 'JX', null);
INSERT INTO `system_area` VALUES ('360121', '南昌县', '360100', 'JX', null);
INSERT INTO `system_area` VALUES ('360122', '新建县', '360100', 'JX', null);
INSERT INTO `system_area` VALUES ('360123', '安义县', '360100', 'JX', null);
INSERT INTO `system_area` VALUES ('360124', '进贤县', '360100', 'JX', null);
INSERT INTO `system_area` VALUES ('360200', '景德镇市', '360000', 'JX', null);
INSERT INTO `system_area` VALUES ('360201', '市辖区', '360200', 'JX', null);
INSERT INTO `system_area` VALUES ('360202', '昌江区', '360200', 'JX', null);
INSERT INTO `system_area` VALUES ('360203', '珠山区', '360200', 'JX', null);
INSERT INTO `system_area` VALUES ('360222', '浮梁县', '360200', 'JX', null);
INSERT INTO `system_area` VALUES ('360281', '乐平市', '360200', 'JX', null);
INSERT INTO `system_area` VALUES ('360300', '萍乡市', '360000', 'JX', null);
INSERT INTO `system_area` VALUES ('360301', '市辖区', '360300', 'JX', null);
INSERT INTO `system_area` VALUES ('360302', '安源区', '360300', 'JX', null);
INSERT INTO `system_area` VALUES ('360313', '湘东区', '360300', 'JX', null);
INSERT INTO `system_area` VALUES ('360321', '莲花县', '360300', 'JX', null);
INSERT INTO `system_area` VALUES ('360322', '上栗县', '360300', 'JX', null);
INSERT INTO `system_area` VALUES ('360323', '芦溪县', '360300', 'JX', null);
INSERT INTO `system_area` VALUES ('360400', '九江市', '360000', 'JX', null);
INSERT INTO `system_area` VALUES ('360401', '市辖区', '360400', 'JX', null);
INSERT INTO `system_area` VALUES ('360402', '庐山区', '360400', 'JX', null);
INSERT INTO `system_area` VALUES ('360403', '浔阳区', '360400', 'JX', null);
INSERT INTO `system_area` VALUES ('360421', '九江县', '360400', 'JX', null);
INSERT INTO `system_area` VALUES ('360423', '武宁县', '360400', 'JX', null);
INSERT INTO `system_area` VALUES ('360424', '修水县', '360400', 'JX', null);
INSERT INTO `system_area` VALUES ('360425', '永修县', '360400', 'JX', null);
INSERT INTO `system_area` VALUES ('360426', '德安县', '360400', 'JX', null);
INSERT INTO `system_area` VALUES ('360427', '星子县', '360400', 'JX', null);
INSERT INTO `system_area` VALUES ('360428', '都昌县', '360400', 'JX', null);
INSERT INTO `system_area` VALUES ('360429', '湖口县', '360400', 'JX', null);
INSERT INTO `system_area` VALUES ('360430', '彭泽县', '360400', 'JX', null);
INSERT INTO `system_area` VALUES ('360481', '瑞昌市', '360400', 'JX', null);
INSERT INTO `system_area` VALUES ('360500', '新余市', '360000', 'JX', null);
INSERT INTO `system_area` VALUES ('360501', '市辖区', '360500', 'JX', null);
INSERT INTO `system_area` VALUES ('360502', '渝水区', '360500', 'JX', null);
INSERT INTO `system_area` VALUES ('360521', '分宜县', '360500', 'JX', null);
INSERT INTO `system_area` VALUES ('360600', '鹰潭市', '360000', 'JX', null);
INSERT INTO `system_area` VALUES ('360601', '市辖区', '360600', 'JX', null);
INSERT INTO `system_area` VALUES ('360602', '月湖区', '360600', 'JX', null);
INSERT INTO `system_area` VALUES ('360622', '余江县', '360600', 'JX', null);
INSERT INTO `system_area` VALUES ('360681', '贵溪市', '360600', 'JX', null);
INSERT INTO `system_area` VALUES ('360700', '赣州市', '360000', 'JX', null);
INSERT INTO `system_area` VALUES ('360701', '市辖区', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360702', '章贡区', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360721', '赣县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360722', '信丰县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360723', '大余县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360724', '上犹县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360725', '崇义县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360726', '安远县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360727', '龙南县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360728', '定南县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360729', '全南县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360730', '宁都县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360731', '于都县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360732', '兴国县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360733', '会昌县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360734', '寻乌县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360735', '石城县', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360781', '瑞金市', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360782', '南康市', '360700', 'JX', null);
INSERT INTO `system_area` VALUES ('360800', '吉安市', '360000', 'JX', null);
INSERT INTO `system_area` VALUES ('360801', '市辖区', '360800', 'JX', null);
INSERT INTO `system_area` VALUES ('360802', '吉州区', '360800', 'JX', null);
INSERT INTO `system_area` VALUES ('360803', '青原区', '360800', 'JX', null);
INSERT INTO `system_area` VALUES ('360821', '吉安县', '360800', 'JX', null);
INSERT INTO `system_area` VALUES ('360822', '吉水县', '360800', 'JX', null);
INSERT INTO `system_area` VALUES ('360823', '峡江县', '360800', 'JX', null);
INSERT INTO `system_area` VALUES ('360824', '新干县', '360800', 'JX', null);
INSERT INTO `system_area` VALUES ('360825', '永丰县', '360800', 'JX', null);
INSERT INTO `system_area` VALUES ('360826', '泰和县', '360800', 'JX', null);
INSERT INTO `system_area` VALUES ('360827', '遂川县', '360800', 'JX', null);
INSERT INTO `system_area` VALUES ('360828', '万安县', '360800', 'JX', null);
INSERT INTO `system_area` VALUES ('360829', '安福县', '360800', 'JX', null);
INSERT INTO `system_area` VALUES ('360830', '永新县', '360800', 'JX', null);
INSERT INTO `system_area` VALUES ('360881', '井冈山市', '360800', 'JX', null);
INSERT INTO `system_area` VALUES ('360900', '宜春市', '360000', 'JX', null);
INSERT INTO `system_area` VALUES ('360901', '市辖区', '360900', 'JX', null);
INSERT INTO `system_area` VALUES ('360902', '袁州区', '360900', 'JX', null);
INSERT INTO `system_area` VALUES ('360921', '奉新县', '360900', 'JX', null);
INSERT INTO `system_area` VALUES ('360922', '万载县', '360900', 'JX', null);
INSERT INTO `system_area` VALUES ('360923', '上高县', '360900', 'JX', null);
INSERT INTO `system_area` VALUES ('360924', '宜丰县', '360900', 'JX', null);
INSERT INTO `system_area` VALUES ('360925', '靖安县', '360900', 'JX', null);
INSERT INTO `system_area` VALUES ('360926', '铜鼓县', '360900', 'JX', null);
INSERT INTO `system_area` VALUES ('360981', '丰城市', '360900', 'JX', null);
INSERT INTO `system_area` VALUES ('360982', '樟树市', '360900', 'JX', null);
INSERT INTO `system_area` VALUES ('360983', '高安市', '360900', 'JX', null);
INSERT INTO `system_area` VALUES ('361000', '抚州市', '360000', 'JX', null);
INSERT INTO `system_area` VALUES ('361001', '市辖区', '361000', 'JX', null);
INSERT INTO `system_area` VALUES ('361002', '临川区', '361000', 'JX', null);
INSERT INTO `system_area` VALUES ('361021', '南城县', '361000', 'JX', null);
INSERT INTO `system_area` VALUES ('361022', '黎川县', '361000', 'JX', null);
INSERT INTO `system_area` VALUES ('361023', '南丰县', '361000', 'JX', null);
INSERT INTO `system_area` VALUES ('361024', '崇仁县', '361000', 'JX', null);
INSERT INTO `system_area` VALUES ('361025', '乐安县', '361000', 'JX', null);
INSERT INTO `system_area` VALUES ('361026', '宜黄县', '361000', 'JX', null);
INSERT INTO `system_area` VALUES ('361027', '金溪县', '361000', 'JX', null);
INSERT INTO `system_area` VALUES ('361028', '资溪县', '361000', 'JX', null);
INSERT INTO `system_area` VALUES ('361029', '东乡县', '361000', 'JX', null);
INSERT INTO `system_area` VALUES ('361030', '广昌县', '361000', 'JX', null);
INSERT INTO `system_area` VALUES ('361100', '上饶市', '360000', 'JX', null);
INSERT INTO `system_area` VALUES ('361101', '市辖区', '361100', 'JX', null);
INSERT INTO `system_area` VALUES ('361102', '信州区', '361100', 'JX', null);
INSERT INTO `system_area` VALUES ('361121', '上饶县', '361100', 'JX', null);
INSERT INTO `system_area` VALUES ('361122', '广丰县', '361100', 'JX', null);
INSERT INTO `system_area` VALUES ('361123', '玉山县', '361100', 'JX', null);
INSERT INTO `system_area` VALUES ('361124', '铅山县', '361100', 'JX', null);
INSERT INTO `system_area` VALUES ('361125', '横峰县', '361100', 'JX', null);
INSERT INTO `system_area` VALUES ('361126', '弋阳县', '361100', 'JX', null);
INSERT INTO `system_area` VALUES ('361127', '余干县', '361100', 'JX', null);
INSERT INTO `system_area` VALUES ('361128', '鄱阳县', '361100', 'JX', null);
INSERT INTO `system_area` VALUES ('361129', '万年县', '361100', 'JX', null);
INSERT INTO `system_area` VALUES ('361130', '婺源县', '361100', 'JX', null);
INSERT INTO `system_area` VALUES ('361181', '德兴市', '361100', 'JX', null);
INSERT INTO `system_area` VALUES ('370000', '山东省', '0001', 'SD', 'HB');
INSERT INTO `system_area` VALUES ('370100', '济南市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('370101', '市辖区', '370100', 'SD', null);
INSERT INTO `system_area` VALUES ('370102', '历下区', '370100', 'SD', null);
INSERT INTO `system_area` VALUES ('370103', '市中区', '370100', 'SD', null);
INSERT INTO `system_area` VALUES ('370104', '槐荫区', '370100', 'SD', null);
INSERT INTO `system_area` VALUES ('370105', '天桥区', '370100', 'SD', null);
INSERT INTO `system_area` VALUES ('370112', '历城区', '370100', 'SD', null);
INSERT INTO `system_area` VALUES ('370113', '长清区', '370100', 'SD', null);
INSERT INTO `system_area` VALUES ('370124', '平阴县', '370100', 'SD', null);
INSERT INTO `system_area` VALUES ('370125', '济阳县', '370100', 'SD', null);
INSERT INTO `system_area` VALUES ('370126', '商河县', '370100', 'SD', null);
INSERT INTO `system_area` VALUES ('370181', '章丘市', '370100', 'SD', null);
INSERT INTO `system_area` VALUES ('370200', '青岛市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('370201', '市辖区', '370200', 'SD', null);
INSERT INTO `system_area` VALUES ('370202', '市南区', '370200', 'SD', null);
INSERT INTO `system_area` VALUES ('370203', '市北区', '370200', 'SD', null);
INSERT INTO `system_area` VALUES ('370205', '四方区', '370200', 'SD', null);
INSERT INTO `system_area` VALUES ('370211', '黄岛区', '370200', 'SD', null);
INSERT INTO `system_area` VALUES ('370212', '崂山区', '370200', 'SD', null);
INSERT INTO `system_area` VALUES ('370213', '李沧区', '370200', 'SD', null);
INSERT INTO `system_area` VALUES ('370214', '城阳区', '370200', 'SD', null);
INSERT INTO `system_area` VALUES ('370281', '胶州市', '370200', 'SD', null);
INSERT INTO `system_area` VALUES ('370282', '即墨市', '370200', 'SD', null);
INSERT INTO `system_area` VALUES ('370283', '平度市', '370200', 'SD', null);
INSERT INTO `system_area` VALUES ('370284', '胶南市', '370200', 'SD', null);
INSERT INTO `system_area` VALUES ('370285', '莱西市', '370200', 'SD', null);
INSERT INTO `system_area` VALUES ('370300', '淄博市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('370301', '市辖区', '370300', 'SD', null);
INSERT INTO `system_area` VALUES ('370302', '淄川区', '370300', 'SD', null);
INSERT INTO `system_area` VALUES ('370303', '张店区', '370300', 'SD', null);
INSERT INTO `system_area` VALUES ('370304', '博山区', '370300', 'SD', null);
INSERT INTO `system_area` VALUES ('370305', '临淄区', '370300', 'SD', null);
INSERT INTO `system_area` VALUES ('370306', '周村区', '370300', 'SD', null);
INSERT INTO `system_area` VALUES ('370321', '桓台县', '370300', 'SD', null);
INSERT INTO `system_area` VALUES ('370322', '高青县', '370300', 'SD', null);
INSERT INTO `system_area` VALUES ('370323', '沂源县', '370300', 'SD', null);
INSERT INTO `system_area` VALUES ('370400', '枣庄市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('370401', '市辖区', '370400', 'SD', null);
INSERT INTO `system_area` VALUES ('370402', '市中区', '370400', 'SD', null);
INSERT INTO `system_area` VALUES ('370403', '薛城区', '370400', 'SD', null);
INSERT INTO `system_area` VALUES ('370404', '峄城区', '370400', 'SD', null);
INSERT INTO `system_area` VALUES ('370405', '台儿庄区', '370400', 'SD', null);
INSERT INTO `system_area` VALUES ('370406', '山亭区', '370400', 'SD', null);
INSERT INTO `system_area` VALUES ('370481', '滕州市', '370400', 'SD', null);
INSERT INTO `system_area` VALUES ('370500', '东营市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('370501', '市辖区', '370500', 'SD', null);
INSERT INTO `system_area` VALUES ('370502', '东营区', '370500', 'SD', null);
INSERT INTO `system_area` VALUES ('370503', '河口区', '370500', 'SD', null);
INSERT INTO `system_area` VALUES ('370521', '垦利县', '370500', 'SD', null);
INSERT INTO `system_area` VALUES ('370522', '利津县', '370500', 'SD', null);
INSERT INTO `system_area` VALUES ('370523', '广饶县', '370500', 'SD', null);
INSERT INTO `system_area` VALUES ('370600', '烟台市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('370601', '市辖区', '370600', 'SD', null);
INSERT INTO `system_area` VALUES ('370602', '芝罘区', '370600', 'SD', null);
INSERT INTO `system_area` VALUES ('370611', '福山区', '370600', 'SD', null);
INSERT INTO `system_area` VALUES ('370612', '牟平区', '370600', 'SD', null);
INSERT INTO `system_area` VALUES ('370613', '莱山区', '370600', 'SD', null);
INSERT INTO `system_area` VALUES ('370634', '长岛县', '370600', 'SD', null);
INSERT INTO `system_area` VALUES ('370681', '龙口市', '370600', 'SD', null);
INSERT INTO `system_area` VALUES ('370682', '莱阳市', '370600', 'SD', null);
INSERT INTO `system_area` VALUES ('370683', '莱州市', '370600', 'SD', null);
INSERT INTO `system_area` VALUES ('370684', '蓬莱市', '370600', 'SD', null);
INSERT INTO `system_area` VALUES ('370685', '招远市', '370600', 'SD', null);
INSERT INTO `system_area` VALUES ('370686', '栖霞市', '370600', 'SD', null);
INSERT INTO `system_area` VALUES ('370687', '海阳市', '370600', 'SD', null);
INSERT INTO `system_area` VALUES ('370700', '潍坊市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('370701', '市辖区', '370700', 'SD', null);
INSERT INTO `system_area` VALUES ('370702', '潍城区', '370700', 'SD', null);
INSERT INTO `system_area` VALUES ('370703', '寒亭区', '370700', 'SD', null);
INSERT INTO `system_area` VALUES ('370704', '坊子区', '370700', 'SD', null);
INSERT INTO `system_area` VALUES ('370705', '奎文区', '370700', 'SD', null);
INSERT INTO `system_area` VALUES ('370724', '临朐县', '370700', 'SD', null);
INSERT INTO `system_area` VALUES ('370725', '昌乐县', '370700', 'SD', null);
INSERT INTO `system_area` VALUES ('370781', '青州市', '370700', 'SD', null);
INSERT INTO `system_area` VALUES ('370782', '诸城市', '370700', 'SD', null);
INSERT INTO `system_area` VALUES ('370783', '寿光市', '370700', 'SD', null);
INSERT INTO `system_area` VALUES ('370784', '安丘市', '370700', 'SD', null);
INSERT INTO `system_area` VALUES ('370785', '高密市', '370700', 'SD', null);
INSERT INTO `system_area` VALUES ('370786', '昌邑市', '370700', 'SD', null);
INSERT INTO `system_area` VALUES ('370800', '济宁市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('370801', '市辖区', '370800', 'SD', null);
INSERT INTO `system_area` VALUES ('370802', '市中区', '370800', 'SD', null);
INSERT INTO `system_area` VALUES ('370811', '任城区', '370800', 'SD', null);
INSERT INTO `system_area` VALUES ('370826', '微山县', '370800', 'SD', null);
INSERT INTO `system_area` VALUES ('370827', '鱼台县', '370800', 'SD', null);
INSERT INTO `system_area` VALUES ('370828', '金乡县', '370800', 'SD', null);
INSERT INTO `system_area` VALUES ('370829', '嘉祥县', '370800', 'SD', null);
INSERT INTO `system_area` VALUES ('370830', '汶上县', '370800', 'SD', null);
INSERT INTO `system_area` VALUES ('370831', '泗水县', '370800', 'SD', null);
INSERT INTO `system_area` VALUES ('370832', '梁山县', '370800', 'SD', null);
INSERT INTO `system_area` VALUES ('370881', '曲阜市', '370800', 'SD', null);
INSERT INTO `system_area` VALUES ('370882', '兖州市', '370800', 'SD', null);
INSERT INTO `system_area` VALUES ('370883', '邹城市', '370800', 'SD', null);
INSERT INTO `system_area` VALUES ('370900', '泰安市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('370901', '市辖区', '370900', 'SD', null);
INSERT INTO `system_area` VALUES ('370902', '泰山区', '370900', 'SD', null);
INSERT INTO `system_area` VALUES ('370903', '岱岳区', '370900', 'SD', null);
INSERT INTO `system_area` VALUES ('370921', '宁阳县', '370900', 'SD', null);
INSERT INTO `system_area` VALUES ('370923', '东平县', '370900', 'SD', null);
INSERT INTO `system_area` VALUES ('370982', '新泰市', '370900', 'SD', null);
INSERT INTO `system_area` VALUES ('370983', '肥城市', '370900', 'SD', null);
INSERT INTO `system_area` VALUES ('371000', '威海市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('371001', '市辖区', '371000', 'SD', null);
INSERT INTO `system_area` VALUES ('371002', '环翠区', '371000', 'SD', null);
INSERT INTO `system_area` VALUES ('371081', '文登市', '371000', 'SD', null);
INSERT INTO `system_area` VALUES ('371082', '荣成市', '371000', 'SD', null);
INSERT INTO `system_area` VALUES ('371083', '乳山市', '371000', 'SD', null);
INSERT INTO `system_area` VALUES ('371100', '日照市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('371101', '市辖区', '371100', 'SD', null);
INSERT INTO `system_area` VALUES ('371102', '东港区', '371100', 'SD', null);
INSERT INTO `system_area` VALUES ('371103', '岚山区', '371100', 'SD', null);
INSERT INTO `system_area` VALUES ('371121', '五莲县', '371100', 'SD', null);
INSERT INTO `system_area` VALUES ('371122', '莒县', '371100', 'SD', null);
INSERT INTO `system_area` VALUES ('371200', '莱芜市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('371201', '市辖区', '371200', 'SD', null);
INSERT INTO `system_area` VALUES ('371202', '莱城区', '371200', 'SD', null);
INSERT INTO `system_area` VALUES ('371203', '钢城区', '371200', 'SD', null);
INSERT INTO `system_area` VALUES ('371300', '临沂市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('371301', '市辖区', '371300', 'SD', null);
INSERT INTO `system_area` VALUES ('371302', '兰山区', '371300', 'SD', null);
INSERT INTO `system_area` VALUES ('371311', '罗庄区', '371300', 'SD', null);
INSERT INTO `system_area` VALUES ('371312', '河东区', '371300', 'SD', null);
INSERT INTO `system_area` VALUES ('371321', '沂南县', '371300', 'SD', null);
INSERT INTO `system_area` VALUES ('371322', '郯城县', '371300', 'SD', null);
INSERT INTO `system_area` VALUES ('371323', '沂水县', '371300', 'SD', null);
INSERT INTO `system_area` VALUES ('371324', '苍山县', '371300', 'SD', null);
INSERT INTO `system_area` VALUES ('371325', '费县', '371300', 'SD', null);
INSERT INTO `system_area` VALUES ('371326', '平邑县', '371300', 'SD', null);
INSERT INTO `system_area` VALUES ('371327', '莒南县', '371300', 'SD', null);
INSERT INTO `system_area` VALUES ('371328', '蒙阴县', '371300', 'SD', null);
INSERT INTO `system_area` VALUES ('371329', '临沭县', '371300', 'SD', null);
INSERT INTO `system_area` VALUES ('371400', '德州市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('371401', '市辖区', '371400', 'SD', null);
INSERT INTO `system_area` VALUES ('371402', '德城区', '371400', 'SD', null);
INSERT INTO `system_area` VALUES ('371421', '陵县', '371400', 'SD', null);
INSERT INTO `system_area` VALUES ('371422', '宁津县', '371400', 'SD', null);
INSERT INTO `system_area` VALUES ('371423', '庆云县', '371400', 'SD', null);
INSERT INTO `system_area` VALUES ('371424', '临邑县', '371400', 'SD', null);
INSERT INTO `system_area` VALUES ('371425', '齐河县', '371400', 'SD', null);
INSERT INTO `system_area` VALUES ('371426', '平原县', '371400', 'SD', null);
INSERT INTO `system_area` VALUES ('371427', '夏津县', '371400', 'SD', null);
INSERT INTO `system_area` VALUES ('371428', '武城县', '371400', 'SD', null);
INSERT INTO `system_area` VALUES ('371481', '乐陵市', '371400', 'SD', null);
INSERT INTO `system_area` VALUES ('371482', '禹城市', '371400', 'SD', null);
INSERT INTO `system_area` VALUES ('371500', '聊城市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('371501', '市辖区', '371500', 'SD', null);
INSERT INTO `system_area` VALUES ('371502', '东昌府区', '371500', 'SD', null);
INSERT INTO `system_area` VALUES ('371521', '阳谷县', '371500', 'SD', null);
INSERT INTO `system_area` VALUES ('371522', '莘县', '371500', 'SD', null);
INSERT INTO `system_area` VALUES ('371523', '茌平县', '371500', 'SD', null);
INSERT INTO `system_area` VALUES ('371524', '东阿县', '371500', 'SD', null);
INSERT INTO `system_area` VALUES ('371525', '冠县', '371500', 'SD', null);
INSERT INTO `system_area` VALUES ('371526', '高唐县', '371500', 'SD', null);
INSERT INTO `system_area` VALUES ('371581', '临清市', '371500', 'SD', null);
INSERT INTO `system_area` VALUES ('371600', '滨州市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('371601', '市辖区', '371600', 'SD', null);
INSERT INTO `system_area` VALUES ('371602', '滨城区', '371600', 'SD', null);
INSERT INTO `system_area` VALUES ('371621', '惠民县', '371600', 'SD', null);
INSERT INTO `system_area` VALUES ('371622', '阳信县', '371600', 'SD', null);
INSERT INTO `system_area` VALUES ('371623', '无棣县', '371600', 'SD', null);
INSERT INTO `system_area` VALUES ('371624', '沾化县', '371600', 'SD', null);
INSERT INTO `system_area` VALUES ('371625', '博兴县', '371600', 'SD', null);
INSERT INTO `system_area` VALUES ('371626', '邹平县', '371600', 'SD', null);
INSERT INTO `system_area` VALUES ('371700', '荷泽市', '370000', 'SD', null);
INSERT INTO `system_area` VALUES ('371701', '市辖区', '371700', 'SD', null);
INSERT INTO `system_area` VALUES ('371702', '牡丹区', '371700', 'SD', null);
INSERT INTO `system_area` VALUES ('371721', '曹县', '371700', 'SD', null);
INSERT INTO `system_area` VALUES ('371722', '单县', '371700', 'SD', null);
INSERT INTO `system_area` VALUES ('371723', '成武县', '371700', 'SD', null);
INSERT INTO `system_area` VALUES ('371724', '巨野县', '371700', 'SD', null);
INSERT INTO `system_area` VALUES ('371725', '郓城县', '371700', 'SD', null);
INSERT INTO `system_area` VALUES ('371726', '鄄城县', '371700', 'SD', null);
INSERT INTO `system_area` VALUES ('371727', '定陶县', '371700', 'SD', null);
INSERT INTO `system_area` VALUES ('371728', '东明县', '371700', 'SD', null);
INSERT INTO `system_area` VALUES ('410000', '河南省', '0001', 'HA', 'HZ');
INSERT INTO `system_area` VALUES ('410100', '郑州市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('410101', '市辖区', '410100', 'HA', null);
INSERT INTO `system_area` VALUES ('410102', '中原区', '410100', 'HA', null);
INSERT INTO `system_area` VALUES ('410103', '二七区', '410100', 'HA', null);
INSERT INTO `system_area` VALUES ('410104', '管城回族区', '410100', 'HA', null);
INSERT INTO `system_area` VALUES ('410105', '金水区', '410100', 'HA', null);
INSERT INTO `system_area` VALUES ('410106', '上街区', '410100', 'HA', null);
INSERT INTO `system_area` VALUES ('410108', '惠济区', '410100', 'HA', null);
INSERT INTO `system_area` VALUES ('410122', '中牟县', '410100', 'HA', null);
INSERT INTO `system_area` VALUES ('410181', '巩义市', '410100', 'HA', null);
INSERT INTO `system_area` VALUES ('410182', '荥阳市', '410100', 'HA', null);
INSERT INTO `system_area` VALUES ('410183', '新密市', '410100', 'HA', null);
INSERT INTO `system_area` VALUES ('410184', '新郑市', '410100', 'HA', null);
INSERT INTO `system_area` VALUES ('410185', '登封市', '410100', 'HA', null);
INSERT INTO `system_area` VALUES ('410200', '开封市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('410201', '市辖区', '410200', 'HA', null);
INSERT INTO `system_area` VALUES ('410202', '龙亭区', '410200', 'HA', null);
INSERT INTO `system_area` VALUES ('410203', '顺河回族区', '410200', 'HA', null);
INSERT INTO `system_area` VALUES ('410204', '鼓楼区', '410200', 'HA', null);
INSERT INTO `system_area` VALUES ('410205', '南关区', '410200', 'HA', null);
INSERT INTO `system_area` VALUES ('410211', '郊区', '410200', 'HA', null);
INSERT INTO `system_area` VALUES ('410221', '杞县', '410200', 'HA', null);
INSERT INTO `system_area` VALUES ('410222', '通许县', '410200', 'HA', null);
INSERT INTO `system_area` VALUES ('410223', '尉氏县', '410200', 'HA', null);
INSERT INTO `system_area` VALUES ('410224', '开封县', '410200', 'HA', null);
INSERT INTO `system_area` VALUES ('410225', '兰考县', '410200', 'HA', null);
INSERT INTO `system_area` VALUES ('410300', '洛阳市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('410301', '市辖区', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410302', '老城区', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410303', '西工区', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410304', '廛河回族区', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410305', '涧西区', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410306', '吉利区', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410307', '洛龙区', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410322', '孟津县', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410323', '新安县', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410324', '栾川县', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410325', '嵩县', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410326', '汝阳县', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410327', '宜阳县', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410328', '洛宁县', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410329', '伊川县', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410381', '偃师市', '410300', 'HA', null);
INSERT INTO `system_area` VALUES ('410400', '平顶山市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('410401', '市辖区', '410400', 'HA', null);
INSERT INTO `system_area` VALUES ('410402', '新华区', '410400', 'HA', null);
INSERT INTO `system_area` VALUES ('410403', '卫东区', '410400', 'HA', null);
INSERT INTO `system_area` VALUES ('410404', '石龙区', '410400', 'HA', null);
INSERT INTO `system_area` VALUES ('410411', '湛河区', '410400', 'HA', null);
INSERT INTO `system_area` VALUES ('410421', '宝丰县', '410400', 'HA', null);
INSERT INTO `system_area` VALUES ('410422', '叶县', '410400', 'HA', null);
INSERT INTO `system_area` VALUES ('410423', '鲁山县', '410400', 'HA', null);
INSERT INTO `system_area` VALUES ('410425', '郏县', '410400', 'HA', null);
INSERT INTO `system_area` VALUES ('410481', '舞钢市', '410400', 'HA', null);
INSERT INTO `system_area` VALUES ('410482', '汝州市', '410400', 'HA', null);
INSERT INTO `system_area` VALUES ('410500', '安阳市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('410501', '市辖区', '410500', 'HA', null);
INSERT INTO `system_area` VALUES ('410502', '文峰区', '410500', 'HA', null);
INSERT INTO `system_area` VALUES ('410503', '北关区', '410500', 'HA', null);
INSERT INTO `system_area` VALUES ('410505', '殷都区', '410500', 'HA', null);
INSERT INTO `system_area` VALUES ('410506', '龙安区', '410500', 'HA', null);
INSERT INTO `system_area` VALUES ('410522', '安阳县', '410500', 'HA', null);
INSERT INTO `system_area` VALUES ('410523', '汤阴县', '410500', 'HA', null);
INSERT INTO `system_area` VALUES ('410526', '滑县', '410500', 'HA', null);
INSERT INTO `system_area` VALUES ('410527', '内黄县', '410500', 'HA', null);
INSERT INTO `system_area` VALUES ('410581', '林州市', '410500', 'HA', null);
INSERT INTO `system_area` VALUES ('410600', '鹤壁市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('410601', '市辖区', '410600', 'HA', null);
INSERT INTO `system_area` VALUES ('410602', '鹤山区', '410600', 'HA', null);
INSERT INTO `system_area` VALUES ('410603', '山城区', '410600', 'HA', null);
INSERT INTO `system_area` VALUES ('410611', '淇滨区', '410600', 'HA', null);
INSERT INTO `system_area` VALUES ('410621', '浚县', '410600', 'HA', null);
INSERT INTO `system_area` VALUES ('410622', '淇县', '410600', 'HA', null);
INSERT INTO `system_area` VALUES ('410700', '新乡市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('410701', '市辖区', '410700', 'HA', null);
INSERT INTO `system_area` VALUES ('410702', '红旗区', '410700', 'HA', null);
INSERT INTO `system_area` VALUES ('410703', '卫滨区', '410700', 'HA', null);
INSERT INTO `system_area` VALUES ('410704', '凤泉区', '410700', 'HA', null);
INSERT INTO `system_area` VALUES ('410711', '牧野区', '410700', 'HA', null);
INSERT INTO `system_area` VALUES ('410721', '新乡县', '410700', 'HA', null);
INSERT INTO `system_area` VALUES ('410724', '获嘉县', '410700', 'HA', null);
INSERT INTO `system_area` VALUES ('410725', '原阳县', '410700', 'HA', null);
INSERT INTO `system_area` VALUES ('410726', '延津县', '410700', 'HA', null);
INSERT INTO `system_area` VALUES ('410727', '封丘县', '410700', 'HA', null);
INSERT INTO `system_area` VALUES ('410728', '长垣县', '410700', 'HA', null);
INSERT INTO `system_area` VALUES ('410781', '卫辉市', '410700', 'HA', null);
INSERT INTO `system_area` VALUES ('410782', '辉县市', '410700', 'HA', null);
INSERT INTO `system_area` VALUES ('410800', '焦作市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('410801', '市辖区', '410800', 'HA', null);
INSERT INTO `system_area` VALUES ('410802', '解放区', '410800', 'HA', null);
INSERT INTO `system_area` VALUES ('410803', '中站区', '410800', 'HA', null);
INSERT INTO `system_area` VALUES ('410804', '马村区', '410800', 'HA', null);
INSERT INTO `system_area` VALUES ('410811', '山阳区', '410800', 'HA', null);
INSERT INTO `system_area` VALUES ('410821', '修武县', '410800', 'HA', null);
INSERT INTO `system_area` VALUES ('410822', '博爱县', '410800', 'HA', null);
INSERT INTO `system_area` VALUES ('410823', '武陟县', '410800', 'HA', null);
INSERT INTO `system_area` VALUES ('410825', '温县', '410800', 'HA', null);
INSERT INTO `system_area` VALUES ('410881', '济源市', '410800', 'HA', null);
INSERT INTO `system_area` VALUES ('410882', '沁阳市', '410800', 'HA', null);
INSERT INTO `system_area` VALUES ('410883', '孟州市', '410800', 'HA', null);
INSERT INTO `system_area` VALUES ('410900', '濮阳市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('410901', '市辖区', '410900', 'HA', null);
INSERT INTO `system_area` VALUES ('410902', '华龙区', '410900', 'HA', null);
INSERT INTO `system_area` VALUES ('410922', '清丰县', '410900', 'HA', null);
INSERT INTO `system_area` VALUES ('410923', '南乐县', '410900', 'HA', null);
INSERT INTO `system_area` VALUES ('410926', '范县', '410900', 'HA', null);
INSERT INTO `system_area` VALUES ('410927', '台前县', '410900', 'HA', null);
INSERT INTO `system_area` VALUES ('410928', '濮阳县', '410900', 'HA', null);
INSERT INTO `system_area` VALUES ('411000', '许昌市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('411001', '市辖区', '411000', 'HA', null);
INSERT INTO `system_area` VALUES ('411002', '魏都区', '411000', 'HA', null);
INSERT INTO `system_area` VALUES ('411023', '许昌县', '411000', 'HA', null);
INSERT INTO `system_area` VALUES ('411024', '鄢陵县', '411000', 'HA', null);
INSERT INTO `system_area` VALUES ('411025', '襄城县', '411000', 'HA', null);
INSERT INTO `system_area` VALUES ('411081', '禹州市', '411000', 'HA', null);
INSERT INTO `system_area` VALUES ('411082', '长葛市', '411000', 'HA', null);
INSERT INTO `system_area` VALUES ('411100', '漯河市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('411101', '市辖区', '411100', 'HA', null);
INSERT INTO `system_area` VALUES ('411102', '源汇区', '411100', 'HA', null);
INSERT INTO `system_area` VALUES ('411103', '郾城区', '411100', 'HA', null);
INSERT INTO `system_area` VALUES ('411104', '召陵区', '411100', 'HA', null);
INSERT INTO `system_area` VALUES ('411121', '舞阳县', '411100', 'HA', null);
INSERT INTO `system_area` VALUES ('411122', '临颍县', '411100', 'HA', null);
INSERT INTO `system_area` VALUES ('411200', '三门峡市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('411201', '市辖区', '411200', 'HA', null);
INSERT INTO `system_area` VALUES ('411202', '湖滨区', '411200', 'HA', null);
INSERT INTO `system_area` VALUES ('411221', '渑池县', '411200', 'HA', null);
INSERT INTO `system_area` VALUES ('411222', '陕县', '411200', 'HA', null);
INSERT INTO `system_area` VALUES ('411224', '卢氏县', '411200', 'HA', null);
INSERT INTO `system_area` VALUES ('411281', '义马市', '411200', 'HA', null);
INSERT INTO `system_area` VALUES ('411282', '灵宝市', '411200', 'HA', null);
INSERT INTO `system_area` VALUES ('411300', '南阳市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('411301', '市辖区', '411300', 'HA', null);
INSERT INTO `system_area` VALUES ('411302', '宛城区', '411300', 'HA', null);
INSERT INTO `system_area` VALUES ('411303', '卧龙区', '411300', 'HA', null);
INSERT INTO `system_area` VALUES ('411321', '南召县', '411300', 'HA', null);
INSERT INTO `system_area` VALUES ('411322', '方城县', '411300', 'HA', null);
INSERT INTO `system_area` VALUES ('411323', '西峡县', '411300', 'HA', null);
INSERT INTO `system_area` VALUES ('411324', '镇平县', '411300', 'HA', null);
INSERT INTO `system_area` VALUES ('411325', '内乡县', '411300', 'HA', null);
INSERT INTO `system_area` VALUES ('411326', '淅川县', '411300', 'HA', null);
INSERT INTO `system_area` VALUES ('411327', '社旗县', '411300', 'HA', null);
INSERT INTO `system_area` VALUES ('411328', '唐河县', '411300', 'HA', null);
INSERT INTO `system_area` VALUES ('411329', '新野县', '411300', 'HA', null);
INSERT INTO `system_area` VALUES ('411330', '桐柏县', '411300', 'HA', null);
INSERT INTO `system_area` VALUES ('411381', '邓州市', '411300', 'HA', null);
INSERT INTO `system_area` VALUES ('411400', '商丘市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('411401', '市辖区', '411400', 'HA', null);
INSERT INTO `system_area` VALUES ('411402', '梁园区', '411400', 'HA', null);
INSERT INTO `system_area` VALUES ('411403', '睢阳区', '411400', 'HA', null);
INSERT INTO `system_area` VALUES ('411421', '民权县', '411400', 'HA', null);
INSERT INTO `system_area` VALUES ('411422', '睢县', '411400', 'HA', null);
INSERT INTO `system_area` VALUES ('411423', '宁陵县', '411400', 'HA', null);
INSERT INTO `system_area` VALUES ('411424', '柘城县', '411400', 'HA', null);
INSERT INTO `system_area` VALUES ('411425', '虞城县', '411400', 'HA', null);
INSERT INTO `system_area` VALUES ('411426', '夏邑县', '411400', 'HA', null);
INSERT INTO `system_area` VALUES ('411481', '永城市', '411400', 'HA', null);
INSERT INTO `system_area` VALUES ('411500', '信阳市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('411501', '市辖区', '411500', 'HA', null);
INSERT INTO `system_area` VALUES ('411502', '?河区', '411500', 'HA', null);
INSERT INTO `system_area` VALUES ('411503', '平桥区', '411500', 'HA', null);
INSERT INTO `system_area` VALUES ('411521', '罗山县', '411500', 'HA', null);
INSERT INTO `system_area` VALUES ('411522', '光山县', '411500', 'HA', null);
INSERT INTO `system_area` VALUES ('411523', '新县', '411500', 'HA', null);
INSERT INTO `system_area` VALUES ('411524', '商城县', '411500', 'HA', null);
INSERT INTO `system_area` VALUES ('411525', '固始县', '411500', 'HA', null);
INSERT INTO `system_area` VALUES ('411526', '潢川县', '411500', 'HA', null);
INSERT INTO `system_area` VALUES ('411527', '淮滨县', '411500', 'HA', null);
INSERT INTO `system_area` VALUES ('411528', '息县', '411500', 'HA', null);
INSERT INTO `system_area` VALUES ('411600', '周口市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('411601', '市辖区', '411600', 'HA', null);
INSERT INTO `system_area` VALUES ('411602', '川汇区', '411600', 'HA', null);
INSERT INTO `system_area` VALUES ('411621', '扶沟县', '411600', 'HA', null);
INSERT INTO `system_area` VALUES ('411622', '西华县', '411600', 'HA', null);
INSERT INTO `system_area` VALUES ('411623', '商水县', '411600', 'HA', null);
INSERT INTO `system_area` VALUES ('411624', '沈丘县', '411600', 'HA', null);
INSERT INTO `system_area` VALUES ('411625', '郸城县', '411600', 'HA', null);
INSERT INTO `system_area` VALUES ('411626', '淮阳县', '411600', 'HA', null);
INSERT INTO `system_area` VALUES ('411627', '太康县', '411600', 'HA', null);
INSERT INTO `system_area` VALUES ('411628', '鹿邑县', '411600', 'HA', null);
INSERT INTO `system_area` VALUES ('411681', '项城市', '411600', 'HA', null);
INSERT INTO `system_area` VALUES ('411700', '驻马店市', '410000', 'HA', null);
INSERT INTO `system_area` VALUES ('411701', '市辖区', '411700', 'HA', null);
INSERT INTO `system_area` VALUES ('411702', '驿城区', '411700', 'HA', null);
INSERT INTO `system_area` VALUES ('411721', '西平县', '411700', 'HA', null);
INSERT INTO `system_area` VALUES ('411722', '上蔡县', '411700', 'HA', null);
INSERT INTO `system_area` VALUES ('411723', '平舆县', '411700', 'HA', null);
INSERT INTO `system_area` VALUES ('411724', '正阳县', '411700', 'HA', null);
INSERT INTO `system_area` VALUES ('411725', '确山县', '411700', 'HA', null);
INSERT INTO `system_area` VALUES ('411726', '泌阳县', '411700', 'HA', null);
INSERT INTO `system_area` VALUES ('411727', '汝南县', '411700', 'HA', null);
INSERT INTO `system_area` VALUES ('411728', '遂平县', '411700', 'HA', null);
INSERT INTO `system_area` VALUES ('411729', '新蔡县', '411700', 'HA', null);
INSERT INTO `system_area` VALUES ('420000', '湖北省', '0001', 'HB', 'HZ');
INSERT INTO `system_area` VALUES ('420100', '武汉市', '420000', 'HB', null);
INSERT INTO `system_area` VALUES ('420101', '市辖区', '420100', 'HB', null);
INSERT INTO `system_area` VALUES ('420102', '江岸区', '420100', 'HB', null);
INSERT INTO `system_area` VALUES ('420103', '江汉区', '420100', 'HB', null);
INSERT INTO `system_area` VALUES ('420104', '?口区', '420100', 'HB', null);
INSERT INTO `system_area` VALUES ('420105', '汉阳区', '420100', 'HB', null);
INSERT INTO `system_area` VALUES ('420106', '武昌区', '420100', 'HB', null);
INSERT INTO `system_area` VALUES ('420107', '青山区', '420100', 'HB', null);
INSERT INTO `system_area` VALUES ('420111', '洪山区', '420100', 'HB', null);
INSERT INTO `system_area` VALUES ('420112', '东西湖区', '420100', 'HB', null);
INSERT INTO `system_area` VALUES ('420113', '汉南区', '420100', 'HB', null);
INSERT INTO `system_area` VALUES ('420114', '蔡甸区', '420100', 'HB', null);
INSERT INTO `system_area` VALUES ('420115', '江夏区', '420100', 'HB', null);
INSERT INTO `system_area` VALUES ('420116', '黄陂区', '420100', 'HB', null);
INSERT INTO `system_area` VALUES ('420117', '新洲区', '420100', 'HB', null);
INSERT INTO `system_area` VALUES ('420200', '黄石市', '420000', 'HB', null);
INSERT INTO `system_area` VALUES ('420201', '市辖区', '420200', 'HB', null);
INSERT INTO `system_area` VALUES ('420202', '黄石港区', '420200', 'HB', null);
INSERT INTO `system_area` VALUES ('420203', '西塞山区', '420200', 'HB', null);
INSERT INTO `system_area` VALUES ('420204', '下陆区', '420200', 'HB', null);
INSERT INTO `system_area` VALUES ('420205', '铁山区', '420200', 'HB', null);
INSERT INTO `system_area` VALUES ('420222', '阳新县', '420200', 'HB', null);
INSERT INTO `system_area` VALUES ('420281', '大冶市', '420200', 'HB', null);
INSERT INTO `system_area` VALUES ('420300', '十堰市', '420000', 'HB', null);
INSERT INTO `system_area` VALUES ('420301', '市辖区', '420300', 'HB', null);
INSERT INTO `system_area` VALUES ('420302', '茅箭区', '420300', 'HB', null);
INSERT INTO `system_area` VALUES ('420303', '张湾区', '420300', 'HB', null);
INSERT INTO `system_area` VALUES ('420321', '郧县', '420300', 'HB', null);
INSERT INTO `system_area` VALUES ('420322', '郧西县', '420300', 'HB', null);
INSERT INTO `system_area` VALUES ('420323', '竹山县', '420300', 'HB', null);
INSERT INTO `system_area` VALUES ('420324', '竹溪县', '420300', 'HB', null);
INSERT INTO `system_area` VALUES ('420325', '房县', '420300', 'HB', null);
INSERT INTO `system_area` VALUES ('420381', '丹江口市', '420300', 'HB', null);
INSERT INTO `system_area` VALUES ('420500', '宜昌市', '420000', 'HB', null);
INSERT INTO `system_area` VALUES ('420501', '市辖区', '420500', 'HB', null);
INSERT INTO `system_area` VALUES ('420502', '西陵区', '420500', 'HB', null);
INSERT INTO `system_area` VALUES ('420503', '伍家岗区', '420500', 'HB', null);
INSERT INTO `system_area` VALUES ('420504', '点军区', '420500', 'HB', null);
INSERT INTO `system_area` VALUES ('420505', '猇亭区', '420500', 'HB', null);
INSERT INTO `system_area` VALUES ('420506', '夷陵区', '420500', 'HB', null);
INSERT INTO `system_area` VALUES ('420525', '远安县', '420500', 'HB', null);
INSERT INTO `system_area` VALUES ('420526', '兴山县', '420500', 'HB', null);
INSERT INTO `system_area` VALUES ('420527', '秭归县', '420500', 'HB', null);
INSERT INTO `system_area` VALUES ('420528', '长阳土家族自治县', '420500', 'HB', null);
INSERT INTO `system_area` VALUES ('420529', '五峰土家族自治县', '420500', 'HB', null);
INSERT INTO `system_area` VALUES ('420581', '宜都市', '420500', 'HB', null);
INSERT INTO `system_area` VALUES ('420582', '当阳市', '420500', 'HB', null);
INSERT INTO `system_area` VALUES ('420583', '枝江市', '420500', 'HB', null);
INSERT INTO `system_area` VALUES ('420600', '襄樊市', '420000', 'HB', null);
INSERT INTO `system_area` VALUES ('420601', '市辖区', '420600', 'HB', null);
INSERT INTO `system_area` VALUES ('420602', '襄城区', '420600', 'HB', null);
INSERT INTO `system_area` VALUES ('420606', '樊城区', '420600', 'HB', null);
INSERT INTO `system_area` VALUES ('420607', '襄阳区', '420600', 'HB', null);
INSERT INTO `system_area` VALUES ('420624', '南漳县', '420600', 'HB', null);
INSERT INTO `system_area` VALUES ('420625', '谷城县', '420600', 'HB', null);
INSERT INTO `system_area` VALUES ('420626', '保康县', '420600', 'HB', null);
INSERT INTO `system_area` VALUES ('420682', '老河口市', '420600', 'HB', null);
INSERT INTO `system_area` VALUES ('420683', '枣阳市', '420600', 'HB', null);
INSERT INTO `system_area` VALUES ('420684', '宜城市', '420600', 'HB', null);
INSERT INTO `system_area` VALUES ('420700', '鄂州市', '420000', 'HB', null);
INSERT INTO `system_area` VALUES ('420701', '市辖区', '420700', 'HB', null);
INSERT INTO `system_area` VALUES ('420702', '梁子湖区', '420700', 'HB', null);
INSERT INTO `system_area` VALUES ('420703', '华容区', '420700', 'HB', null);
INSERT INTO `system_area` VALUES ('420704', '鄂城区', '420700', 'HB', null);
INSERT INTO `system_area` VALUES ('420800', '荆门市', '420000', 'HB', null);
INSERT INTO `system_area` VALUES ('420801', '市辖区', '420800', 'HB', null);
INSERT INTO `system_area` VALUES ('420802', '东宝区', '420800', 'HB', null);
INSERT INTO `system_area` VALUES ('420804', '掇刀区', '420800', 'HB', null);
INSERT INTO `system_area` VALUES ('420821', '京山县', '420800', 'HB', null);
INSERT INTO `system_area` VALUES ('420822', '沙洋县', '420800', 'HB', null);
INSERT INTO `system_area` VALUES ('420881', '钟祥市', '420800', 'HB', null);
INSERT INTO `system_area` VALUES ('420900', '孝感市', '420000', 'HB', null);
INSERT INTO `system_area` VALUES ('420901', '市辖区', '420900', 'HB', null);
INSERT INTO `system_area` VALUES ('420902', '孝南区', '420900', 'HB', null);
INSERT INTO `system_area` VALUES ('420921', '孝昌县', '420900', 'HB', null);
INSERT INTO `system_area` VALUES ('420922', '大悟县', '420900', 'HB', null);
INSERT INTO `system_area` VALUES ('420923', '云梦县', '420900', 'HB', null);
INSERT INTO `system_area` VALUES ('420981', '应城市', '420900', 'HB', null);
INSERT INTO `system_area` VALUES ('420982', '安陆市', '420900', 'HB', null);
INSERT INTO `system_area` VALUES ('420984', '汉川市', '420900', 'HB', null);
INSERT INTO `system_area` VALUES ('421000', '荆州市', '420000', 'HB', null);
INSERT INTO `system_area` VALUES ('421001', '市辖区', '421000', 'HB', null);
INSERT INTO `system_area` VALUES ('421002', '沙市区', '421000', 'HB', null);
INSERT INTO `system_area` VALUES ('421003', '荆州区', '421000', 'HB', null);
INSERT INTO `system_area` VALUES ('421022', '公安县', '421000', 'HB', null);
INSERT INTO `system_area` VALUES ('421023', '监利县', '421000', 'HB', null);
INSERT INTO `system_area` VALUES ('421024', '江陵县', '421000', 'HB', null);
INSERT INTO `system_area` VALUES ('421081', '石首市', '421000', 'HB', null);
INSERT INTO `system_area` VALUES ('421083', '洪湖市', '421000', 'HB', null);
INSERT INTO `system_area` VALUES ('421087', '松滋市', '421000', 'HB', null);
INSERT INTO `system_area` VALUES ('421100', '黄冈市', '420000', 'HB', null);
INSERT INTO `system_area` VALUES ('421101', '市辖区', '421100', 'HB', null);
INSERT INTO `system_area` VALUES ('421102', '黄州区', '421100', 'HB', null);
INSERT INTO `system_area` VALUES ('421121', '团风县', '421100', 'HB', null);
INSERT INTO `system_area` VALUES ('421122', '红安县', '421100', 'HB', null);
INSERT INTO `system_area` VALUES ('421123', '罗田县', '421100', 'HB', null);
INSERT INTO `system_area` VALUES ('421124', '英山县', '421100', 'HB', null);
INSERT INTO `system_area` VALUES ('421125', '浠水县', '421100', 'HB', null);
INSERT INTO `system_area` VALUES ('421126', '蕲春县', '421100', 'HB', null);
INSERT INTO `system_area` VALUES ('421127', '黄梅县', '421100', 'HB', null);
INSERT INTO `system_area` VALUES ('421181', '麻城市', '421100', 'HB', null);
INSERT INTO `system_area` VALUES ('421182', '武穴市', '421100', 'HB', null);
INSERT INTO `system_area` VALUES ('421200', '咸宁市', '420000', 'HB', null);
INSERT INTO `system_area` VALUES ('421201', '市辖区', '421200', 'HB', null);
INSERT INTO `system_area` VALUES ('421202', '咸安区', '421200', 'HB', null);
INSERT INTO `system_area` VALUES ('421221', '嘉鱼县', '421200', 'HB', null);
INSERT INTO `system_area` VALUES ('421222', '通城县', '421200', 'HB', null);
INSERT INTO `system_area` VALUES ('421223', '崇阳县', '421200', 'HB', null);
INSERT INTO `system_area` VALUES ('421224', '通山县', '421200', 'HB', null);
INSERT INTO `system_area` VALUES ('421281', '赤壁市', '421200', 'HB', null);
INSERT INTO `system_area` VALUES ('421300', '随州市', '420000', 'HB', null);
INSERT INTO `system_area` VALUES ('421301', '市辖区', '421300', 'HB', null);
INSERT INTO `system_area` VALUES ('421302', '曾都区', '421300', 'HB', null);
INSERT INTO `system_area` VALUES ('421381', '广水市', '421300', 'HB', null);
INSERT INTO `system_area` VALUES ('422800', '恩施土家族苗族自治州', '420000', 'HB', null);
INSERT INTO `system_area` VALUES ('422801', '恩施市', '422800', 'HB', null);
INSERT INTO `system_area` VALUES ('422802', '利川市', '422800', 'HB', null);
INSERT INTO `system_area` VALUES ('422822', '建始县', '422800', 'HB', null);
INSERT INTO `system_area` VALUES ('422823', '巴东县', '422800', 'HB', null);
INSERT INTO `system_area` VALUES ('422825', '宣恩县', '422800', 'HB', null);
INSERT INTO `system_area` VALUES ('422826', '咸丰县', '422800', 'HB', null);
INSERT INTO `system_area` VALUES ('422827', '来凤县', '422800', 'HB', null);
INSERT INTO `system_area` VALUES ('422828', '鹤峰县', '422800', 'HB', null);
INSERT INTO `system_area` VALUES ('429000', '省直辖行政单位', '420000', 'HB', null);
INSERT INTO `system_area` VALUES ('429004', '仙桃市', '429000', 'HB', null);
INSERT INTO `system_area` VALUES ('429005', '潜江市', '429000', 'HB', null);
INSERT INTO `system_area` VALUES ('429006', '天门市', '429000', 'HB', null);
INSERT INTO `system_area` VALUES ('429021', '神农架林区', '429000', 'HB', null);
INSERT INTO `system_area` VALUES ('430000', '湖南省', '0001', 'HN', 'HZ');
INSERT INTO `system_area` VALUES ('430100', '长沙市', '430000', 'HN', null);
INSERT INTO `system_area` VALUES ('430101', '市辖区', '430100', 'HN', null);
INSERT INTO `system_area` VALUES ('430102', '芙蓉区', '430100', 'HN', null);
INSERT INTO `system_area` VALUES ('430103', '天心区', '430100', 'HN', null);
INSERT INTO `system_area` VALUES ('430104', '岳麓区', '430100', 'HN', null);
INSERT INTO `system_area` VALUES ('430105', '开福区', '430100', 'HN', null);
INSERT INTO `system_area` VALUES ('430111', '雨花区', '430100', 'HN', null);
INSERT INTO `system_area` VALUES ('430121', '长沙县', '430100', 'HN', null);
INSERT INTO `system_area` VALUES ('430122', '望城县', '430100', 'HN', null);
INSERT INTO `system_area` VALUES ('430124', '宁乡县', '430100', 'HN', null);
INSERT INTO `system_area` VALUES ('430181', '浏阳市', '430100', 'HN', null);
INSERT INTO `system_area` VALUES ('430200', '株洲市', '430000', 'HN', null);
INSERT INTO `system_area` VALUES ('430201', '市辖区', '430200', 'HN', null);
INSERT INTO `system_area` VALUES ('430202', '荷塘区', '430200', 'HN', null);
INSERT INTO `system_area` VALUES ('430203', '芦淞区', '430200', 'HN', null);
INSERT INTO `system_area` VALUES ('430204', '石峰区', '430200', 'HN', null);
INSERT INTO `system_area` VALUES ('430211', '天元区', '430200', 'HN', null);
INSERT INTO `system_area` VALUES ('430221', '株洲县', '430200', 'HN', null);
INSERT INTO `system_area` VALUES ('430223', '攸县', '430200', 'HN', null);
INSERT INTO `system_area` VALUES ('430224', '茶陵县', '430200', 'HN', null);
INSERT INTO `system_area` VALUES ('430225', '炎陵县', '430200', 'HN', null);
INSERT INTO `system_area` VALUES ('430281', '醴陵市', '430200', 'HN', null);
INSERT INTO `system_area` VALUES ('430300', '湘潭市', '430000', 'HN', null);
INSERT INTO `system_area` VALUES ('430301', '市辖区', '430300', 'HN', null);
INSERT INTO `system_area` VALUES ('430302', '雨湖区', '430300', 'HN', null);
INSERT INTO `system_area` VALUES ('430304', '岳塘区', '430300', 'HN', null);
INSERT INTO `system_area` VALUES ('430321', '湘潭县', '430300', 'HN', null);
INSERT INTO `system_area` VALUES ('430381', '湘乡市', '430300', 'HN', null);
INSERT INTO `system_area` VALUES ('430382', '韶山市', '430300', 'HN', null);
INSERT INTO `system_area` VALUES ('430400', '衡阳市', '430000', 'HN', null);
INSERT INTO `system_area` VALUES ('430401', '市辖区', '430400', 'HN', null);
INSERT INTO `system_area` VALUES ('430405', '珠晖区', '430400', 'HN', null);
INSERT INTO `system_area` VALUES ('430406', '雁峰区', '430400', 'HN', null);
INSERT INTO `system_area` VALUES ('430407', '石鼓区', '430400', 'HN', null);
INSERT INTO `system_area` VALUES ('430408', '蒸湘区', '430400', 'HN', null);
INSERT INTO `system_area` VALUES ('430412', '南岳区', '430400', 'HN', null);
INSERT INTO `system_area` VALUES ('430421', '衡阳县', '430400', 'HN', null);
INSERT INTO `system_area` VALUES ('430422', '衡南县', '430400', 'HN', null);
INSERT INTO `system_area` VALUES ('430423', '衡山县', '430400', 'HN', null);
INSERT INTO `system_area` VALUES ('430424', '衡东县', '430400', 'HN', null);
INSERT INTO `system_area` VALUES ('430426', '祁东县', '430400', 'HN', null);
INSERT INTO `system_area` VALUES ('430481', '耒阳市', '430400', 'HN', null);
INSERT INTO `system_area` VALUES ('430482', '常宁市', '430400', 'HN', null);
INSERT INTO `system_area` VALUES ('430500', '邵阳市', '430000', 'HN', null);
INSERT INTO `system_area` VALUES ('430501', '市辖区', '430500', 'HN', null);
INSERT INTO `system_area` VALUES ('430502', '双清区', '430500', 'HN', null);
INSERT INTO `system_area` VALUES ('430503', '大祥区', '430500', 'HN', null);
INSERT INTO `system_area` VALUES ('430511', '北塔区', '430500', 'HN', null);
INSERT INTO `system_area` VALUES ('430521', '邵东县', '430500', 'HN', null);
INSERT INTO `system_area` VALUES ('430522', '新邵县', '430500', 'HN', null);
INSERT INTO `system_area` VALUES ('430523', '邵阳县', '430500', 'HN', null);
INSERT INTO `system_area` VALUES ('430524', '隆回县', '430500', 'HN', null);
INSERT INTO `system_area` VALUES ('430525', '洞口县', '430500', 'HN', null);
INSERT INTO `system_area` VALUES ('430527', '绥宁县', '430500', 'HN', null);
INSERT INTO `system_area` VALUES ('430528', '新宁县', '430500', 'HN', null);
INSERT INTO `system_area` VALUES ('430529', '城步苗族自治县', '430500', 'HN', null);
INSERT INTO `system_area` VALUES ('430581', '武冈市', '430500', 'HN', null);
INSERT INTO `system_area` VALUES ('430600', '岳阳市', '430000', 'HN', null);
INSERT INTO `system_area` VALUES ('430601', '市辖区', '430600', 'HN', null);
INSERT INTO `system_area` VALUES ('430602', '岳阳楼区', '430600', 'HN', null);
INSERT INTO `system_area` VALUES ('430603', '云溪区', '430600', 'HN', null);
INSERT INTO `system_area` VALUES ('430611', '君山区', '430600', 'HN', null);
INSERT INTO `system_area` VALUES ('430621', '岳阳县', '430600', 'HN', null);
INSERT INTO `system_area` VALUES ('430623', '华容县', '430600', 'HN', null);
INSERT INTO `system_area` VALUES ('430624', '湘阴县', '430600', 'HN', null);
INSERT INTO `system_area` VALUES ('430626', '平江县', '430600', 'HN', null);
INSERT INTO `system_area` VALUES ('430681', '汨罗市', '430600', 'HN', null);
INSERT INTO `system_area` VALUES ('430682', '临湘市', '430600', 'HN', null);
INSERT INTO `system_area` VALUES ('430700', '常德市', '430000', 'HN', null);
INSERT INTO `system_area` VALUES ('430701', '市辖区', '430700', 'HN', null);
INSERT INTO `system_area` VALUES ('430702', '武陵区', '430700', 'HN', null);
INSERT INTO `system_area` VALUES ('430703', '鼎城区', '430700', 'HN', null);
INSERT INTO `system_area` VALUES ('430721', '安乡县', '430700', 'HN', null);
INSERT INTO `system_area` VALUES ('430722', '汉寿县', '430700', 'HN', null);
INSERT INTO `system_area` VALUES ('430723', '澧县', '430700', 'HN', null);
INSERT INTO `system_area` VALUES ('430724', '临澧县', '430700', 'HN', null);
INSERT INTO `system_area` VALUES ('430725', '桃源县', '430700', 'HN', null);
INSERT INTO `system_area` VALUES ('430726', '石门县', '430700', 'HN', null);
INSERT INTO `system_area` VALUES ('430781', '津市市', '430700', 'HN', null);
INSERT INTO `system_area` VALUES ('430800', '张家界市', '430000', 'HN', null);
INSERT INTO `system_area` VALUES ('430801', '市辖区', '430800', 'HN', null);
INSERT INTO `system_area` VALUES ('430802', '永定区', '430800', 'HN', null);
INSERT INTO `system_area` VALUES ('430811', '武陵源区', '430800', 'HN', null);
INSERT INTO `system_area` VALUES ('430821', '慈利县', '430800', 'HN', null);
INSERT INTO `system_area` VALUES ('430822', '桑植县', '430800', 'HN', null);
INSERT INTO `system_area` VALUES ('430900', '益阳市', '430000', 'HN', null);
INSERT INTO `system_area` VALUES ('430901', '市辖区', '430900', 'HN', null);
INSERT INTO `system_area` VALUES ('430902', '资阳区', '430900', 'HN', null);
INSERT INTO `system_area` VALUES ('430903', '赫山区', '430900', 'HN', null);
INSERT INTO `system_area` VALUES ('430921', '南县', '430900', 'HN', null);
INSERT INTO `system_area` VALUES ('430922', '桃江县', '430900', 'HN', null);
INSERT INTO `system_area` VALUES ('430923', '安化县', '430900', 'HN', null);
INSERT INTO `system_area` VALUES ('430981', '沅江市', '430900', 'HN', null);
INSERT INTO `system_area` VALUES ('431000', '郴州市', '430000', 'HN', null);
INSERT INTO `system_area` VALUES ('431001', '市辖区', '431000', 'HN', null);
INSERT INTO `system_area` VALUES ('431002', '北湖区', '431000', 'HN', null);
INSERT INTO `system_area` VALUES ('431003', '苏仙区', '431000', 'HN', null);
INSERT INTO `system_area` VALUES ('431021', '桂阳县', '431000', 'HN', null);
INSERT INTO `system_area` VALUES ('431022', '宜章县', '431000', 'HN', null);
INSERT INTO `system_area` VALUES ('431023', '永兴县', '431000', 'HN', null);
INSERT INTO `system_area` VALUES ('431024', '嘉禾县', '431000', 'HN', null);
INSERT INTO `system_area` VALUES ('431025', '临武县', '431000', 'HN', null);
INSERT INTO `system_area` VALUES ('431026', '汝城县', '431000', 'HN', null);
INSERT INTO `system_area` VALUES ('431027', '桂东县', '431000', 'HN', null);
INSERT INTO `system_area` VALUES ('431028', '安仁县', '431000', 'HN', null);
INSERT INTO `system_area` VALUES ('431081', '资兴市', '431000', 'HN', null);
INSERT INTO `system_area` VALUES ('431100', '永州市', '430000', 'HN', null);
INSERT INTO `system_area` VALUES ('431101', '市辖区', '431100', 'HN', null);
INSERT INTO `system_area` VALUES ('431102', '芝山区', '431100', 'HN', null);
INSERT INTO `system_area` VALUES ('431103', '冷水滩区', '431100', 'HN', null);
INSERT INTO `system_area` VALUES ('431121', '祁阳县', '431100', 'HN', null);
INSERT INTO `system_area` VALUES ('431122', '东安县', '431100', 'HN', null);
INSERT INTO `system_area` VALUES ('431123', '双牌县', '431100', 'HN', null);
INSERT INTO `system_area` VALUES ('431124', '道县', '431100', 'HN', null);
INSERT INTO `system_area` VALUES ('431125', '江永县', '431100', 'HN', null);
INSERT INTO `system_area` VALUES ('431126', '宁远县', '431100', 'HN', null);
INSERT INTO `system_area` VALUES ('431127', '蓝山县', '431100', 'HN', null);
INSERT INTO `system_area` VALUES ('431128', '新田县', '431100', 'HN', null);
INSERT INTO `system_area` VALUES ('431129', '江华瑶族自治县', '431100', 'HN', null);
INSERT INTO `system_area` VALUES ('431200', '怀化市', '430000', 'HN', null);
INSERT INTO `system_area` VALUES ('431201', '市辖区', '431200', 'HN', null);
INSERT INTO `system_area` VALUES ('431202', '鹤城区', '431200', 'HN', null);
INSERT INTO `system_area` VALUES ('431221', '中方县', '431200', 'HN', null);
INSERT INTO `system_area` VALUES ('431222', '沅陵县', '431200', 'HN', null);
INSERT INTO `system_area` VALUES ('431223', '辰溪县', '431200', 'HN', null);
INSERT INTO `system_area` VALUES ('431224', '溆浦县', '431200', 'HN', null);
INSERT INTO `system_area` VALUES ('431225', '会同县', '431200', 'HN', null);
INSERT INTO `system_area` VALUES ('431226', '麻阳苗族自治县', '431200', 'HN', null);
INSERT INTO `system_area` VALUES ('431227', '新晃侗族自治县', '431200', 'HN', null);
INSERT INTO `system_area` VALUES ('431228', '芷江侗族自治县', '431200', 'HN', null);
INSERT INTO `system_area` VALUES ('431229', '靖州苗族侗族自治县', '431200', 'HN', null);
INSERT INTO `system_area` VALUES ('431230', '通道侗族自治县', '431200', 'HN', null);
INSERT INTO `system_area` VALUES ('431281', '洪江市', '431200', 'HN', null);
INSERT INTO `system_area` VALUES ('431300', '娄底市', '430000', 'HN', null);
INSERT INTO `system_area` VALUES ('431301', '市辖区', '431300', 'HN', null);
INSERT INTO `system_area` VALUES ('431302', '娄星区', '431300', 'HN', null);
INSERT INTO `system_area` VALUES ('431321', '双峰县', '431300', 'HN', null);
INSERT INTO `system_area` VALUES ('431322', '新化县', '431300', 'HN', null);
INSERT INTO `system_area` VALUES ('431381', '冷水江市', '431300', 'HN', null);
INSERT INTO `system_area` VALUES ('431382', '涟源市', '431300', 'HN', null);
INSERT INTO `system_area` VALUES ('433100', '湘西土家族苗族自治州', '430000', 'HN', null);
INSERT INTO `system_area` VALUES ('433101', '吉首市', '433100', 'HN', null);
INSERT INTO `system_area` VALUES ('433122', '泸溪县', '433100', 'HN', null);
INSERT INTO `system_area` VALUES ('433123', '凤凰县', '433100', 'HN', null);
INSERT INTO `system_area` VALUES ('433124', '花垣县', '433100', 'HN', null);
INSERT INTO `system_area` VALUES ('433125', '保靖县', '433100', 'HN', null);
INSERT INTO `system_area` VALUES ('433126', '古丈县', '433100', 'HN', null);
INSERT INTO `system_area` VALUES ('433127', '永顺县', '433100', 'HN', null);
INSERT INTO `system_area` VALUES ('433130', '龙山县', '433100', 'HN', null);
INSERT INTO `system_area` VALUES ('440000', '广东省', '0001', 'GD', 'HN');
INSERT INTO `system_area` VALUES ('440100', '广州市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('440101', '市辖区', '440100', 'GD', null);
INSERT INTO `system_area` VALUES ('440102', '东山区', '440100', 'GD', null);
INSERT INTO `system_area` VALUES ('440103', '荔湾区', '440100', 'GD', null);
INSERT INTO `system_area` VALUES ('440104', '越秀区', '440100', 'GD', null);
INSERT INTO `system_area` VALUES ('440105', '海珠区', '440100', 'GD', null);
INSERT INTO `system_area` VALUES ('440106', '天河区', '440100', 'GD', null);
INSERT INTO `system_area` VALUES ('440107', '芳村区', '440100', 'GD', null);
INSERT INTO `system_area` VALUES ('440111', '白云区', '440100', 'GD', null);
INSERT INTO `system_area` VALUES ('440112', '黄埔区', '440100', 'GD', null);
INSERT INTO `system_area` VALUES ('440113', '番禺区', '440100', 'GD', null);
INSERT INTO `system_area` VALUES ('440114', '花都区', '440100', 'GD', null);
INSERT INTO `system_area` VALUES ('440183', '增城市', '440100', 'GD', null);
INSERT INTO `system_area` VALUES ('440184', '从化市', '440100', 'GD', null);
INSERT INTO `system_area` VALUES ('440200', '韶关市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('440201', '市辖区', '440200', 'GD', null);
INSERT INTO `system_area` VALUES ('440203', '武江区', '440200', 'GD', null);
INSERT INTO `system_area` VALUES ('440204', '浈江区', '440200', 'GD', null);
INSERT INTO `system_area` VALUES ('440205', '曲江区', '440200', 'GD', null);
INSERT INTO `system_area` VALUES ('440222', '始兴县', '440200', 'GD', null);
INSERT INTO `system_area` VALUES ('440224', '仁化县', '440200', 'GD', null);
INSERT INTO `system_area` VALUES ('440229', '翁源县', '440200', 'GD', null);
INSERT INTO `system_area` VALUES ('440232', '乳源瑶族自治县', '440200', 'GD', null);
INSERT INTO `system_area` VALUES ('440233', '新丰县', '440200', 'GD', null);
INSERT INTO `system_area` VALUES ('440281', '乐昌市', '440200', 'GD', null);
INSERT INTO `system_area` VALUES ('440282', '南雄市', '440200', 'GD', null);
INSERT INTO `system_area` VALUES ('440300', '深圳市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('440301', '市辖区', '440300', 'GD', null);
INSERT INTO `system_area` VALUES ('440303', '罗湖区', '440300', 'GD', null);
INSERT INTO `system_area` VALUES ('440304', '福田区', '440300', 'GD', null);
INSERT INTO `system_area` VALUES ('440305', '南山区', '440300', 'GD', null);
INSERT INTO `system_area` VALUES ('440306', '宝安区', '440300', 'GD', null);
INSERT INTO `system_area` VALUES ('440307', '龙岗区', '440300', 'GD', null);
INSERT INTO `system_area` VALUES ('440308', '盐田区', '440300', 'GD', null);
INSERT INTO `system_area` VALUES ('440400', '珠海市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('440401', '市辖区', '440400', 'GD', null);
INSERT INTO `system_area` VALUES ('440402', '香洲区', '440400', 'GD', null);
INSERT INTO `system_area` VALUES ('440403', '斗门区', '440400', 'GD', null);
INSERT INTO `system_area` VALUES ('440404', '金湾区', '440400', 'GD', null);
INSERT INTO `system_area` VALUES ('440500', '汕头市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('440501', '市辖区', '440500', 'GD', null);
INSERT INTO `system_area` VALUES ('440507', '龙湖区', '440500', 'GD', null);
INSERT INTO `system_area` VALUES ('440511', '金平区', '440500', 'GD', null);
INSERT INTO `system_area` VALUES ('440512', '濠江区', '440500', 'GD', null);
INSERT INTO `system_area` VALUES ('440513', '潮阳区', '440500', 'GD', null);
INSERT INTO `system_area` VALUES ('440514', '潮南区', '440500', 'GD', null);
INSERT INTO `system_area` VALUES ('440515', '澄海区', '440500', 'GD', null);
INSERT INTO `system_area` VALUES ('440523', '南澳县', '440500', 'GD', null);
INSERT INTO `system_area` VALUES ('440600', '佛山市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('440601', '市辖区', '440600', 'GD', null);
INSERT INTO `system_area` VALUES ('440604', '禅城区', '440600', 'GD', null);
INSERT INTO `system_area` VALUES ('440605', '南海区', '440600', 'GD', null);
INSERT INTO `system_area` VALUES ('440606', '顺德区', '440600', 'GD', null);
INSERT INTO `system_area` VALUES ('440607', '三水区', '440600', 'GD', null);
INSERT INTO `system_area` VALUES ('440608', '高明区', '440600', 'GD', null);
INSERT INTO `system_area` VALUES ('440700', '江门市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('440701', '市辖区', '440700', 'GD', null);
INSERT INTO `system_area` VALUES ('440703', '蓬江区', '440700', 'GD', null);
INSERT INTO `system_area` VALUES ('440704', '江海区', '440700', 'GD', null);
INSERT INTO `system_area` VALUES ('440705', '新会区', '440700', 'GD', null);
INSERT INTO `system_area` VALUES ('440781', '台山市', '440700', 'GD', null);
INSERT INTO `system_area` VALUES ('440783', '开平市', '440700', 'GD', null);
INSERT INTO `system_area` VALUES ('440784', '鹤山市', '440700', 'GD', null);
INSERT INTO `system_area` VALUES ('440785', '恩平市', '440700', 'GD', null);
INSERT INTO `system_area` VALUES ('440800', '湛江市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('440801', '市辖区', '440800', 'GD', null);
INSERT INTO `system_area` VALUES ('440802', '赤坎区', '440800', 'GD', null);
INSERT INTO `system_area` VALUES ('440803', '霞山区', '440800', 'GD', null);
INSERT INTO `system_area` VALUES ('440804', '坡头区', '440800', 'GD', null);
INSERT INTO `system_area` VALUES ('440811', '麻章区', '440800', 'GD', null);
INSERT INTO `system_area` VALUES ('440823', '遂溪县', '440800', 'GD', null);
INSERT INTO `system_area` VALUES ('440825', '徐闻县', '440800', 'GD', null);
INSERT INTO `system_area` VALUES ('440881', '廉江市', '440800', 'GD', null);
INSERT INTO `system_area` VALUES ('440882', '雷州市', '440800', 'GD', null);
INSERT INTO `system_area` VALUES ('440883', '吴川市', '440800', 'GD', null);
INSERT INTO `system_area` VALUES ('440900', '茂名市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('440901', '市辖区', '440900', 'GD', null);
INSERT INTO `system_area` VALUES ('440902', '茂南区', '440900', 'GD', null);
INSERT INTO `system_area` VALUES ('440903', '茂港区', '440900', 'GD', null);
INSERT INTO `system_area` VALUES ('440923', '电白县', '440900', 'GD', null);
INSERT INTO `system_area` VALUES ('440981', '高州市', '440900', 'GD', null);
INSERT INTO `system_area` VALUES ('440982', '化州市', '440900', 'GD', null);
INSERT INTO `system_area` VALUES ('440983', '信宜市', '440900', 'GD', null);
INSERT INTO `system_area` VALUES ('441200', '肇庆市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('441201', '市辖区', '441200', 'GD', null);
INSERT INTO `system_area` VALUES ('441202', '端州区', '441200', 'GD', null);
INSERT INTO `system_area` VALUES ('441203', '鼎湖区', '441200', 'GD', null);
INSERT INTO `system_area` VALUES ('441223', '广宁县', '441200', 'GD', null);
INSERT INTO `system_area` VALUES ('441224', '怀集县', '441200', 'GD', null);
INSERT INTO `system_area` VALUES ('441225', '封开县', '441200', 'GD', null);
INSERT INTO `system_area` VALUES ('441226', '德庆县', '441200', 'GD', null);
INSERT INTO `system_area` VALUES ('441283', '高要市', '441200', 'GD', null);
INSERT INTO `system_area` VALUES ('441284', '四会市', '441200', 'GD', null);
INSERT INTO `system_area` VALUES ('441300', '惠州市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('441301', '市辖区', '441300', 'GD', null);
INSERT INTO `system_area` VALUES ('441302', '惠城区', '441300', 'GD', null);
INSERT INTO `system_area` VALUES ('441303', '惠阳区', '441300', 'GD', null);
INSERT INTO `system_area` VALUES ('441322', '博罗县', '441300', 'GD', null);
INSERT INTO `system_area` VALUES ('441323', '惠东县', '441300', 'GD', null);
INSERT INTO `system_area` VALUES ('441324', '龙门县', '441300', 'GD', null);
INSERT INTO `system_area` VALUES ('441400', '梅州市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('441401', '市辖区', '441400', 'GD', null);
INSERT INTO `system_area` VALUES ('441402', '梅江区', '441400', 'GD', null);
INSERT INTO `system_area` VALUES ('441421', '梅县', '441400', 'GD', null);
INSERT INTO `system_area` VALUES ('441422', '大埔县', '441400', 'GD', null);
INSERT INTO `system_area` VALUES ('441423', '丰顺县', '441400', 'GD', null);
INSERT INTO `system_area` VALUES ('441424', '五华县', '441400', 'GD', null);
INSERT INTO `system_area` VALUES ('441426', '平远县', '441400', 'GD', null);
INSERT INTO `system_area` VALUES ('441427', '蕉岭县', '441400', 'GD', null);
INSERT INTO `system_area` VALUES ('441481', '兴宁市', '441400', 'GD', null);
INSERT INTO `system_area` VALUES ('441500', '汕尾市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('441501', '市辖区', '441500', 'GD', null);
INSERT INTO `system_area` VALUES ('441502', '城区', '441500', 'GD', null);
INSERT INTO `system_area` VALUES ('441521', '海丰县', '441500', 'GD', null);
INSERT INTO `system_area` VALUES ('441523', '陆河县', '441500', 'GD', null);
INSERT INTO `system_area` VALUES ('441581', '陆丰市', '441500', 'GD', null);
INSERT INTO `system_area` VALUES ('441600', '河源市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('441601', '市辖区', '441600', 'GD', null);
INSERT INTO `system_area` VALUES ('441602', '源城区', '441600', 'GD', null);
INSERT INTO `system_area` VALUES ('441621', '紫金县', '441600', 'GD', null);
INSERT INTO `system_area` VALUES ('441622', '龙川县', '441600', 'GD', null);
INSERT INTO `system_area` VALUES ('441623', '连平县', '441600', 'GD', null);
INSERT INTO `system_area` VALUES ('441624', '和平县', '441600', 'GD', null);
INSERT INTO `system_area` VALUES ('441625', '东源县', '441600', 'GD', null);
INSERT INTO `system_area` VALUES ('441700', '阳江市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('441701', '市辖区', '441700', 'GD', null);
INSERT INTO `system_area` VALUES ('441702', '江城区', '441700', 'GD', null);
INSERT INTO `system_area` VALUES ('441721', '阳西县', '441700', 'GD', null);
INSERT INTO `system_area` VALUES ('441723', '阳东县', '441700', 'GD', null);
INSERT INTO `system_area` VALUES ('441781', '阳春市', '441700', 'GD', null);
INSERT INTO `system_area` VALUES ('441800', '清远市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('441801', '市辖区', '441800', 'GD', null);
INSERT INTO `system_area` VALUES ('441802', '清城区', '441800', 'GD', null);
INSERT INTO `system_area` VALUES ('441821', '佛冈县', '441800', 'GD', null);
INSERT INTO `system_area` VALUES ('441823', '阳山县', '441800', 'GD', null);
INSERT INTO `system_area` VALUES ('441825', '连山壮族瑶族自治县', '441800', 'GD', null);
INSERT INTO `system_area` VALUES ('441826', '连南瑶族自治县', '441800', 'GD', null);
INSERT INTO `system_area` VALUES ('441827', '清新县', '441800', 'GD', null);
INSERT INTO `system_area` VALUES ('441881', '英德市', '441800', 'GD', null);
INSERT INTO `system_area` VALUES ('441882', '连州市', '441800', 'GD', null);
INSERT INTO `system_area` VALUES ('441900', '东莞市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('442000', '中山市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('445100', '潮州市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('445101', '市辖区', '445100', 'GD', null);
INSERT INTO `system_area` VALUES ('445102', '湘桥区', '445100', 'GD', null);
INSERT INTO `system_area` VALUES ('445121', '潮安县', '445100', 'GD', null);
INSERT INTO `system_area` VALUES ('445122', '饶平县', '445100', 'GD', null);
INSERT INTO `system_area` VALUES ('445200', '揭阳市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('445201', '市辖区', '445200', 'GD', null);
INSERT INTO `system_area` VALUES ('445202', '榕城区', '445200', 'GD', null);
INSERT INTO `system_area` VALUES ('445221', '揭东县', '445200', 'GD', null);
INSERT INTO `system_area` VALUES ('445222', '揭西县', '445200', 'GD', null);
INSERT INTO `system_area` VALUES ('445224', '惠来县', '445200', 'GD', null);
INSERT INTO `system_area` VALUES ('445281', '普宁市', '445200', 'GD', null);
INSERT INTO `system_area` VALUES ('445300', '云浮市', '440000', 'GD', null);
INSERT INTO `system_area` VALUES ('445301', '市辖区', '445300', 'GD', null);
INSERT INTO `system_area` VALUES ('445302', '云城区', '445300', 'GD', null);
INSERT INTO `system_area` VALUES ('445321', '新兴县', '445300', 'GD', null);
INSERT INTO `system_area` VALUES ('445322', '郁南县', '445300', 'GD', null);
INSERT INTO `system_area` VALUES ('445323', '云安县', '445300', 'GD', null);
INSERT INTO `system_area` VALUES ('445381', '罗定市', '445300', 'GD', null);
INSERT INTO `system_area` VALUES ('450000', '广西壮族自治区', '0001', 'GX', 'HN');
INSERT INTO `system_area` VALUES ('450100', '南宁市', '450000', 'GX', null);
INSERT INTO `system_area` VALUES ('450101', '市辖区', '450100', 'GX', null);
INSERT INTO `system_area` VALUES ('450102', '兴宁区', '450100', 'GX', null);
INSERT INTO `system_area` VALUES ('450103', '青秀区', '450100', 'GX', null);
INSERT INTO `system_area` VALUES ('450105', '江南区', '450100', 'GX', null);
INSERT INTO `system_area` VALUES ('450107', '西乡塘区', '450100', 'GX', null);
INSERT INTO `system_area` VALUES ('450108', '良庆区', '450100', 'GX', null);
INSERT INTO `system_area` VALUES ('450109', '邕宁区', '450100', 'GX', null);
INSERT INTO `system_area` VALUES ('450122', '武鸣县', '450100', 'GX', null);
INSERT INTO `system_area` VALUES ('450123', '隆安县', '450100', 'GX', null);
INSERT INTO `system_area` VALUES ('450124', '马山县', '450100', 'GX', null);
INSERT INTO `system_area` VALUES ('450125', '上林县', '450100', 'GX', null);
INSERT INTO `system_area` VALUES ('450126', '宾阳县', '450100', 'GX', null);
INSERT INTO `system_area` VALUES ('450127', '横县', '450100', 'GX', null);
INSERT INTO `system_area` VALUES ('450200', '柳州市', '450000', 'GX', null);
INSERT INTO `system_area` VALUES ('450201', '市辖区', '450200', 'GX', null);
INSERT INTO `system_area` VALUES ('450202', '城中区', '450200', 'GX', null);
INSERT INTO `system_area` VALUES ('450203', '鱼峰区', '450200', 'GX', null);
INSERT INTO `system_area` VALUES ('450204', '柳南区', '450200', 'GX', null);
INSERT INTO `system_area` VALUES ('450205', '柳北区', '450200', 'GX', null);
INSERT INTO `system_area` VALUES ('450221', '柳江县', '450200', 'GX', null);
INSERT INTO `system_area` VALUES ('450222', '柳城县', '450200', 'GX', null);
INSERT INTO `system_area` VALUES ('450223', '鹿寨县', '450200', 'GX', null);
INSERT INTO `system_area` VALUES ('450224', '融安县', '450200', 'GX', null);
INSERT INTO `system_area` VALUES ('450225', '融水苗族自治县', '450200', 'GX', null);
INSERT INTO `system_area` VALUES ('450226', '三江侗族自治县', '450200', 'GX', null);
INSERT INTO `system_area` VALUES ('450300', '桂林市', '450000', 'GX', null);
INSERT INTO `system_area` VALUES ('450301', '市辖区', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450302', '秀峰区', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450303', '叠彩区', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450304', '象山区', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450305', '七星区', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450311', '雁山区', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450321', '阳朔县', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450322', '临桂县', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450323', '灵川县', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450324', '全州县', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450325', '兴安县', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450326', '永福县', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450327', '灌阳县', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450328', '龙胜各族自治县', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450329', '资源县', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450330', '平乐县', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450331', '荔蒲县', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450332', '恭城瑶族自治县', '450300', 'GX', null);
INSERT INTO `system_area` VALUES ('450400', '梧州市', '450000', 'GX', null);
INSERT INTO `system_area` VALUES ('450401', '市辖区', '450400', 'GX', null);
INSERT INTO `system_area` VALUES ('450403', '万秀区', '450400', 'GX', null);
INSERT INTO `system_area` VALUES ('450404', '蝶山区', '450400', 'GX', null);
INSERT INTO `system_area` VALUES ('450405', '长洲区', '450400', 'GX', null);
INSERT INTO `system_area` VALUES ('450421', '苍梧县', '450400', 'GX', null);
INSERT INTO `system_area` VALUES ('450422', '藤县', '450400', 'GX', null);
INSERT INTO `system_area` VALUES ('450423', '蒙山县', '450400', 'GX', null);
INSERT INTO `system_area` VALUES ('450481', '岑溪市', '450400', 'GX', null);
INSERT INTO `system_area` VALUES ('450500', '北海市', '450000', 'GX', null);
INSERT INTO `system_area` VALUES ('450501', '市辖区', '450500', 'GX', null);
INSERT INTO `system_area` VALUES ('450502', '海城区', '450500', 'GX', null);
INSERT INTO `system_area` VALUES ('450503', '银海区', '450500', 'GX', null);
INSERT INTO `system_area` VALUES ('450512', '铁山港区', '450500', 'GX', null);
INSERT INTO `system_area` VALUES ('450521', '合浦县', '450500', 'GX', null);
INSERT INTO `system_area` VALUES ('450600', '防城港市', '450000', 'GX', null);
INSERT INTO `system_area` VALUES ('450601', '市辖区', '450600', 'GX', null);
INSERT INTO `system_area` VALUES ('450602', '港口区', '450600', 'GX', null);
INSERT INTO `system_area` VALUES ('450603', '防城区', '450600', 'GX', null);
INSERT INTO `system_area` VALUES ('450621', '上思县', '450600', 'GX', null);
INSERT INTO `system_area` VALUES ('450681', '东兴市', '450600', 'GX', null);
INSERT INTO `system_area` VALUES ('450700', '钦州市', '450000', 'GX', null);
INSERT INTO `system_area` VALUES ('450701', '市辖区', '450700', 'GX', null);
INSERT INTO `system_area` VALUES ('450702', '钦南区', '450700', 'GX', null);
INSERT INTO `system_area` VALUES ('450703', '钦北区', '450700', 'GX', null);
INSERT INTO `system_area` VALUES ('450721', '灵山县', '450700', 'GX', null);
INSERT INTO `system_area` VALUES ('450722', '浦北县', '450700', 'GX', null);
INSERT INTO `system_area` VALUES ('450800', '贵港市', '450000', 'GX', null);
INSERT INTO `system_area` VALUES ('450801', '市辖区', '450800', 'GX', null);
INSERT INTO `system_area` VALUES ('450802', '港北区', '450800', 'GX', null);
INSERT INTO `system_area` VALUES ('450803', '港南区', '450800', 'GX', null);
INSERT INTO `system_area` VALUES ('450804', '覃塘区', '450800', 'GX', null);
INSERT INTO `system_area` VALUES ('450821', '平南县', '450800', 'GX', null);
INSERT INTO `system_area` VALUES ('450881', '桂平市', '450800', 'GX', null);
INSERT INTO `system_area` VALUES ('450900', '玉林市', '450000', 'GX', null);
INSERT INTO `system_area` VALUES ('450901', '市辖区', '450900', 'GX', null);
INSERT INTO `system_area` VALUES ('450902', '玉州区', '450900', 'GX', null);
INSERT INTO `system_area` VALUES ('450921', '容县', '450900', 'GX', null);
INSERT INTO `system_area` VALUES ('450922', '陆川县', '450900', 'GX', null);
INSERT INTO `system_area` VALUES ('450923', '博白县', '450900', 'GX', null);
INSERT INTO `system_area` VALUES ('450924', '兴业县', '450900', 'GX', null);
INSERT INTO `system_area` VALUES ('450981', '北流市', '450900', 'GX', null);
INSERT INTO `system_area` VALUES ('451000', '百色市', '450000', 'GX', null);
INSERT INTO `system_area` VALUES ('451001', '市辖区', '451000', 'GX', null);
INSERT INTO `system_area` VALUES ('451002', '右江区', '451000', 'GX', null);
INSERT INTO `system_area` VALUES ('451021', '田阳县', '451000', 'GX', null);
INSERT INTO `system_area` VALUES ('451022', '田东县', '451000', 'GX', null);
INSERT INTO `system_area` VALUES ('451023', '平果县', '451000', 'GX', null);
INSERT INTO `system_area` VALUES ('451024', '德保县', '451000', 'GX', null);
INSERT INTO `system_area` VALUES ('451025', '靖西县', '451000', 'GX', null);
INSERT INTO `system_area` VALUES ('451026', '那坡县', '451000', 'GX', null);
INSERT INTO `system_area` VALUES ('451027', '凌云县', '451000', 'GX', null);
INSERT INTO `system_area` VALUES ('451028', '乐业县', '451000', 'GX', null);
INSERT INTO `system_area` VALUES ('451029', '田林县', '451000', 'GX', null);
INSERT INTO `system_area` VALUES ('451030', '西林县', '451000', 'GX', null);
INSERT INTO `system_area` VALUES ('451031', '隆林各族自治县', '451000', 'GX', null);
INSERT INTO `system_area` VALUES ('451100', '贺州市', '450000', 'GX', null);
INSERT INTO `system_area` VALUES ('451101', '市辖区', '451100', 'GX', null);
INSERT INTO `system_area` VALUES ('451102', '八步区', '451100', 'GX', null);
INSERT INTO `system_area` VALUES ('451121', '昭平县', '451100', 'GX', null);
INSERT INTO `system_area` VALUES ('451122', '钟山县', '451100', 'GX', null);
INSERT INTO `system_area` VALUES ('451123', '富川瑶族自治县', '451100', 'GX', null);
INSERT INTO `system_area` VALUES ('451200', '河池市', '450000', 'GX', null);
INSERT INTO `system_area` VALUES ('451201', '市辖区', '451200', 'GX', null);
INSERT INTO `system_area` VALUES ('451202', '金城江区', '451200', 'GX', null);
INSERT INTO `system_area` VALUES ('451221', '南丹县', '451200', 'GX', null);
INSERT INTO `system_area` VALUES ('451222', '天峨县', '451200', 'GX', null);
INSERT INTO `system_area` VALUES ('451223', '凤山县', '451200', 'GX', null);
INSERT INTO `system_area` VALUES ('451224', '东兰县', '451200', 'GX', null);
INSERT INTO `system_area` VALUES ('451225', '罗城仫佬族自治县', '451200', 'GX', null);
INSERT INTO `system_area` VALUES ('451226', '环江毛南族自治县', '451200', 'GX', null);
INSERT INTO `system_area` VALUES ('451227', '巴马瑶族自治县', '451200', 'GX', null);
INSERT INTO `system_area` VALUES ('451228', '都安瑶族自治县', '451200', 'GX', null);
INSERT INTO `system_area` VALUES ('451229', '大化瑶族自治县', '451200', 'GX', null);
INSERT INTO `system_area` VALUES ('451281', '宜州市', '451200', 'GX', null);
INSERT INTO `system_area` VALUES ('451300', '来宾市', '450000', 'GX', null);
INSERT INTO `system_area` VALUES ('451301', '市辖区', '451300', 'GX', null);
INSERT INTO `system_area` VALUES ('451302', '兴宾区', '451300', 'GX', null);
INSERT INTO `system_area` VALUES ('451321', '忻城县', '451300', 'GX', null);
INSERT INTO `system_area` VALUES ('451322', '象州县', '451300', 'GX', null);
INSERT INTO `system_area` VALUES ('451323', '武宣县', '451300', 'GX', null);
INSERT INTO `system_area` VALUES ('451324', '金秀瑶族自治县', '451300', 'GX', null);
INSERT INTO `system_area` VALUES ('451381', '合山市', '451300', 'GX', null);
INSERT INTO `system_area` VALUES ('451400', '崇左市', '450000', 'GX', null);
INSERT INTO `system_area` VALUES ('451401', '市辖区', '451400', 'GX', null);
INSERT INTO `system_area` VALUES ('451402', '江洲区', '451400', 'GX', null);
INSERT INTO `system_area` VALUES ('451421', '扶绥县', '451400', 'GX', null);
INSERT INTO `system_area` VALUES ('451422', '宁明县', '451400', 'GX', null);
INSERT INTO `system_area` VALUES ('451423', '龙州县', '451400', 'GX', null);
INSERT INTO `system_area` VALUES ('451424', '大新县', '451400', 'GX', null);
INSERT INTO `system_area` VALUES ('451425', '天等县', '451400', 'GX', null);
INSERT INTO `system_area` VALUES ('451481', '凭祥市', '451400', 'GX', null);
INSERT INTO `system_area` VALUES ('460000', '海南省', '0001', 'HI', 'HN');
INSERT INTO `system_area` VALUES ('460100', '海口市', '460000', 'HI', null);
INSERT INTO `system_area` VALUES ('460101', '市辖区', '460100', 'HI', null);
INSERT INTO `system_area` VALUES ('460105', '秀英区', '460100', 'HI', null);
INSERT INTO `system_area` VALUES ('460106', '龙华区', '460100', 'HI', null);
INSERT INTO `system_area` VALUES ('460107', '琼山区', '460100', 'HI', null);
INSERT INTO `system_area` VALUES ('460108', '美兰区', '460100', 'HI', null);
INSERT INTO `system_area` VALUES ('460200', '三亚市', '460000', 'HI', null);
INSERT INTO `system_area` VALUES ('460201', '市辖区', '460200', 'HI', null);
INSERT INTO `system_area` VALUES ('469000', '省直辖县级行政单位', '460000', 'HI', null);
INSERT INTO `system_area` VALUES ('469001', '五指山市', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469002', '琼海市', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469003', '儋州市', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469005', '文昌市', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469006', '万宁市', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469007', '东方市', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469025', '定安县', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469026', '屯昌县', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469027', '澄迈县', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469028', '临高县', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469030', '白沙黎族自治县', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469031', '昌江黎族自治县', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469033', '乐东黎族自治县', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469034', '陵水黎族自治县', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469035', '保亭黎族苗族自治县', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469036', '琼中黎族苗族自治县', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469037', '西沙群岛', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469038', '南沙群岛', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('469039', '中沙群岛的岛礁及其海', '469000', 'HI', null);
INSERT INTO `system_area` VALUES ('500000', '重庆市', '0001', 'CQ', 'XN');
INSERT INTO `system_area` VALUES ('500100', '市辖区', '500000', 'CQ', null);
INSERT INTO `system_area` VALUES ('500101', '万州区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500102', '涪陵区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500103', '渝中区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500104', '大渡口区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500105', '江北区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500106', '沙坪坝区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500107', '九龙坡区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500108', '南岸区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500109', '北碚区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500110', '万盛区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500111', '双桥区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500112', '渝北区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500113', '巴南区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500114', '黔江区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500115', '长寿区', '500100', 'CQ', null);
INSERT INTO `system_area` VALUES ('500200', '县', '500000', 'CQ', null);
INSERT INTO `system_area` VALUES ('500222', '綦江县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500223', '潼南县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500224', '铜梁县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500225', '大足县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500226', '荣昌县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500227', '璧山县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500228', '梁平县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500229', '城口县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500230', '丰都县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500231', '垫江县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500232', '武隆县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500233', '忠县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500234', '开县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500235', '云阳县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500236', '奉节县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500237', '巫山县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500238', '巫溪县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500240', '石柱土家族自治县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500241', '秀山土家族苗族自治县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500242', '酉阳土家族苗族自治县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500243', '彭水苗族土家族自治县', '500200', 'CQ', null);
INSERT INTO `system_area` VALUES ('500300', '市', '500000', 'CQ', null);
INSERT INTO `system_area` VALUES ('500381', '江津市', '500300', 'CQ', null);
INSERT INTO `system_area` VALUES ('500382', '合川市', '500300', 'CQ', null);
INSERT INTO `system_area` VALUES ('500383', '永川市', '500300', 'CQ', null);
INSERT INTO `system_area` VALUES ('500384', '南川市', '500300', 'CQ', null);
INSERT INTO `system_area` VALUES ('510000', '四川省', '0001', 'SC', 'XN');
INSERT INTO `system_area` VALUES ('510100', '成都市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('510101', '市辖区', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510104', '锦江区', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510105', '青羊区', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510106', '金牛区', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510107', '武侯区', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510108', '成华区', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510112', '龙泉驿区', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510113', '青白江区', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510114', '新都区', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510115', '温江区', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510121', '金堂县', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510122', '双流县', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510124', '郫县', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510129', '大邑县', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510131', '蒲江县', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510132', '新津县', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510181', '都江堰市', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510182', '彭州市', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510183', '邛崃市', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510184', '崇州市', '510100', 'SC', null);
INSERT INTO `system_area` VALUES ('510300', '自贡市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('510301', '市辖区', '510300', 'SC', null);
INSERT INTO `system_area` VALUES ('510302', '自流井区', '510300', 'SC', null);
INSERT INTO `system_area` VALUES ('510303', '贡井区', '510300', 'SC', null);
INSERT INTO `system_area` VALUES ('510304', '大安区', '510300', 'SC', null);
INSERT INTO `system_area` VALUES ('510311', '沿滩区', '510300', 'SC', null);
INSERT INTO `system_area` VALUES ('510321', '荣县', '510300', 'SC', null);
INSERT INTO `system_area` VALUES ('510322', '富顺县', '510300', 'SC', null);
INSERT INTO `system_area` VALUES ('510400', '攀枝花市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('510401', '市辖区', '510400', 'SC', null);
INSERT INTO `system_area` VALUES ('510402', '东区', '510400', 'SC', null);
INSERT INTO `system_area` VALUES ('510403', '西区', '510400', 'SC', null);
INSERT INTO `system_area` VALUES ('510411', '仁和区', '510400', 'SC', null);
INSERT INTO `system_area` VALUES ('510421', '米易县', '510400', 'SC', null);
INSERT INTO `system_area` VALUES ('510422', '盐边县', '510400', 'SC', null);
INSERT INTO `system_area` VALUES ('510500', '泸州市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('510501', '市辖区', '510500', 'SC', null);
INSERT INTO `system_area` VALUES ('510502', '江阳区', '510500', 'SC', null);
INSERT INTO `system_area` VALUES ('510503', '纳溪区', '510500', 'SC', null);
INSERT INTO `system_area` VALUES ('510504', '龙马潭区', '510500', 'SC', null);
INSERT INTO `system_area` VALUES ('510521', '泸县', '510500', 'SC', null);
INSERT INTO `system_area` VALUES ('510522', '合江县', '510500', 'SC', null);
INSERT INTO `system_area` VALUES ('510524', '叙永县', '510500', 'SC', null);
INSERT INTO `system_area` VALUES ('510525', '古蔺县', '510500', 'SC', null);
INSERT INTO `system_area` VALUES ('510600', '德阳市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('510601', '市辖区', '510600', 'SC', null);
INSERT INTO `system_area` VALUES ('510603', '旌阳区', '510600', 'SC', null);
INSERT INTO `system_area` VALUES ('510623', '中江县', '510600', 'SC', null);
INSERT INTO `system_area` VALUES ('510626', '罗江县', '510600', 'SC', null);
INSERT INTO `system_area` VALUES ('510681', '广汉市', '510600', 'SC', null);
INSERT INTO `system_area` VALUES ('510682', '什邡市', '510600', 'SC', null);
INSERT INTO `system_area` VALUES ('510683', '绵竹市', '510600', 'SC', null);
INSERT INTO `system_area` VALUES ('510700', '绵阳市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('510701', '市辖区', '510700', 'SC', null);
INSERT INTO `system_area` VALUES ('510703', '涪城区', '510700', 'SC', null);
INSERT INTO `system_area` VALUES ('510704', '游仙区', '510700', 'SC', null);
INSERT INTO `system_area` VALUES ('510722', '三台县', '510700', 'SC', null);
INSERT INTO `system_area` VALUES ('510723', '盐亭县', '510700', 'SC', null);
INSERT INTO `system_area` VALUES ('510724', '安县', '510700', 'SC', null);
INSERT INTO `system_area` VALUES ('510725', '梓潼县', '510700', 'SC', null);
INSERT INTO `system_area` VALUES ('510726', '北川羌族自治县', '510700', 'SC', null);
INSERT INTO `system_area` VALUES ('510727', '平武县', '510700', 'SC', null);
INSERT INTO `system_area` VALUES ('510781', '江油市', '510700', 'SC', null);
INSERT INTO `system_area` VALUES ('510800', '广元市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('510801', '市辖区', '510800', 'SC', null);
INSERT INTO `system_area` VALUES ('510802', '市中区', '510800', 'SC', null);
INSERT INTO `system_area` VALUES ('510811', '元坝区', '510800', 'SC', null);
INSERT INTO `system_area` VALUES ('510812', '朝天区', '510800', 'SC', null);
INSERT INTO `system_area` VALUES ('510821', '旺苍县', '510800', 'SC', null);
INSERT INTO `system_area` VALUES ('510822', '青川县', '510800', 'SC', null);
INSERT INTO `system_area` VALUES ('510823', '剑阁县', '510800', 'SC', null);
INSERT INTO `system_area` VALUES ('510824', '苍溪县', '510800', 'SC', null);
INSERT INTO `system_area` VALUES ('510900', '遂宁市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('510901', '市辖区', '510900', 'SC', null);
INSERT INTO `system_area` VALUES ('510903', '船山区', '510900', 'SC', null);
INSERT INTO `system_area` VALUES ('510904', '安居区', '510900', 'SC', null);
INSERT INTO `system_area` VALUES ('510921', '蓬溪县', '510900', 'SC', null);
INSERT INTO `system_area` VALUES ('510922', '射洪县', '510900', 'SC', null);
INSERT INTO `system_area` VALUES ('510923', '大英县', '510900', 'SC', null);
INSERT INTO `system_area` VALUES ('511000', '内江市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('511001', '市辖区', '511000', 'SC', null);
INSERT INTO `system_area` VALUES ('511002', '市中区', '511000', 'SC', null);
INSERT INTO `system_area` VALUES ('511011', '东兴区', '511000', 'SC', null);
INSERT INTO `system_area` VALUES ('511024', '威远县', '511000', 'SC', null);
INSERT INTO `system_area` VALUES ('511025', '资中县', '511000', 'SC', null);
INSERT INTO `system_area` VALUES ('511028', '隆昌县', '511000', 'SC', null);
INSERT INTO `system_area` VALUES ('511100', '乐山市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('511101', '市辖区', '511100', 'SC', null);
INSERT INTO `system_area` VALUES ('511102', '市中区', '511100', 'SC', null);
INSERT INTO `system_area` VALUES ('511111', '沙湾区', '511100', 'SC', null);
INSERT INTO `system_area` VALUES ('511112', '五通桥区', '511100', 'SC', null);
INSERT INTO `system_area` VALUES ('511113', '金口河区', '511100', 'SC', null);
INSERT INTO `system_area` VALUES ('511123', '犍为县', '511100', 'SC', null);
INSERT INTO `system_area` VALUES ('511124', '井研县', '511100', 'SC', null);
INSERT INTO `system_area` VALUES ('511126', '夹江县', '511100', 'SC', null);
INSERT INTO `system_area` VALUES ('511129', '沐川县', '511100', 'SC', null);
INSERT INTO `system_area` VALUES ('511132', '峨边彝族自治县', '511100', 'SC', null);
INSERT INTO `system_area` VALUES ('511133', '马边彝族自治县', '511100', 'SC', null);
INSERT INTO `system_area` VALUES ('511181', '峨眉山市', '511100', 'SC', null);
INSERT INTO `system_area` VALUES ('511300', '南充市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('511301', '市辖区', '511300', 'SC', null);
INSERT INTO `system_area` VALUES ('511302', '顺庆区', '511300', 'SC', null);
INSERT INTO `system_area` VALUES ('511303', '高坪区', '511300', 'SC', null);
INSERT INTO `system_area` VALUES ('511304', '嘉陵区', '511300', 'SC', null);
INSERT INTO `system_area` VALUES ('511321', '南部县', '511300', 'SC', null);
INSERT INTO `system_area` VALUES ('511322', '营山县', '511300', 'SC', null);
INSERT INTO `system_area` VALUES ('511323', '蓬安县', '511300', 'SC', null);
INSERT INTO `system_area` VALUES ('511324', '仪陇县', '511300', 'SC', null);
INSERT INTO `system_area` VALUES ('511325', '西充县', '511300', 'SC', null);
INSERT INTO `system_area` VALUES ('511381', '阆中市', '511300', 'SC', null);
INSERT INTO `system_area` VALUES ('511400', '眉山市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('511401', '市辖区', '511400', 'SC', null);
INSERT INTO `system_area` VALUES ('511402', '东坡区', '511400', 'SC', null);
INSERT INTO `system_area` VALUES ('511421', '仁寿县', '511400', 'SC', null);
INSERT INTO `system_area` VALUES ('511422', '彭山县', '511400', 'SC', null);
INSERT INTO `system_area` VALUES ('511423', '洪雅县', '511400', 'SC', null);
INSERT INTO `system_area` VALUES ('511424', '丹棱县', '511400', 'SC', null);
INSERT INTO `system_area` VALUES ('511425', '青神县', '511400', 'SC', null);
INSERT INTO `system_area` VALUES ('511500', '宜宾市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('511501', '市辖区', '511500', 'SC', null);
INSERT INTO `system_area` VALUES ('511502', '翠屏区', '511500', 'SC', null);
INSERT INTO `system_area` VALUES ('511521', '宜宾县', '511500', 'SC', null);
INSERT INTO `system_area` VALUES ('511522', '南溪县', '511500', 'SC', null);
INSERT INTO `system_area` VALUES ('511523', '江安县', '511500', 'SC', null);
INSERT INTO `system_area` VALUES ('511524', '长宁县', '511500', 'SC', null);
INSERT INTO `system_area` VALUES ('511525', '高县', '511500', 'SC', null);
INSERT INTO `system_area` VALUES ('511526', '珙县', '511500', 'SC', null);
INSERT INTO `system_area` VALUES ('511527', '筠连县', '511500', 'SC', null);
INSERT INTO `system_area` VALUES ('511528', '兴文县', '511500', 'SC', null);
INSERT INTO `system_area` VALUES ('511529', '屏山县', '511500', 'SC', null);
INSERT INTO `system_area` VALUES ('511600', '广安市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('511601', '市辖区', '511600', 'SC', null);
INSERT INTO `system_area` VALUES ('511602', '广安区', '511600', 'SC', null);
INSERT INTO `system_area` VALUES ('511621', '岳池县', '511600', 'SC', null);
INSERT INTO `system_area` VALUES ('511622', '武胜县', '511600', 'SC', null);
INSERT INTO `system_area` VALUES ('511623', '邻水县', '511600', 'SC', null);
INSERT INTO `system_area` VALUES ('511681', '华蓥市', '511600', 'SC', null);
INSERT INTO `system_area` VALUES ('511700', '达州市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('511701', '市辖区', '511700', 'SC', null);
INSERT INTO `system_area` VALUES ('511702', '通川区', '511700', 'SC', null);
INSERT INTO `system_area` VALUES ('511721', '达县', '511700', 'SC', null);
INSERT INTO `system_area` VALUES ('511722', '宣汉县', '511700', 'SC', null);
INSERT INTO `system_area` VALUES ('511723', '开江县', '511700', 'SC', null);
INSERT INTO `system_area` VALUES ('511724', '大竹县', '511700', 'SC', null);
INSERT INTO `system_area` VALUES ('511725', '渠县', '511700', 'SC', null);
INSERT INTO `system_area` VALUES ('511781', '万源市', '511700', 'SC', null);
INSERT INTO `system_area` VALUES ('511800', '雅安市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('511801', '市辖区', '511800', 'SC', null);
INSERT INTO `system_area` VALUES ('511802', '雨城区', '511800', 'SC', null);
INSERT INTO `system_area` VALUES ('511821', '名山县', '511800', 'SC', null);
INSERT INTO `system_area` VALUES ('511822', '荥经县', '511800', 'SC', null);
INSERT INTO `system_area` VALUES ('511823', '汉源县', '511800', 'SC', null);
INSERT INTO `system_area` VALUES ('511824', '石棉县', '511800', 'SC', null);
INSERT INTO `system_area` VALUES ('511825', '天全县', '511800', 'SC', null);
INSERT INTO `system_area` VALUES ('511826', '芦山县', '511800', 'SC', null);
INSERT INTO `system_area` VALUES ('511827', '宝兴县', '511800', 'SC', null);
INSERT INTO `system_area` VALUES ('511900', '巴中市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('511901', '市辖区', '511900', 'SC', null);
INSERT INTO `system_area` VALUES ('511902', '巴州区', '511900', 'SC', null);
INSERT INTO `system_area` VALUES ('511921', '通江县', '511900', 'SC', null);
INSERT INTO `system_area` VALUES ('511922', '南江县', '511900', 'SC', null);
INSERT INTO `system_area` VALUES ('511923', '平昌县', '511900', 'SC', null);
INSERT INTO `system_area` VALUES ('512000', '资阳市', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('512001', '市辖区', '512000', 'SC', null);
INSERT INTO `system_area` VALUES ('512002', '雁江区', '512000', 'SC', null);
INSERT INTO `system_area` VALUES ('512021', '安岳县', '512000', 'SC', null);
INSERT INTO `system_area` VALUES ('512022', '乐至县', '512000', 'SC', null);
INSERT INTO `system_area` VALUES ('512081', '简阳市', '512000', 'SC', null);
INSERT INTO `system_area` VALUES ('513200', '阿坝藏族羌族自治州', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('513221', '汶川县', '513200', 'SC', null);
INSERT INTO `system_area` VALUES ('513222', '理县', '513200', 'SC', null);
INSERT INTO `system_area` VALUES ('513223', '茂县', '513200', 'SC', null);
INSERT INTO `system_area` VALUES ('513224', '松潘县', '513200', 'SC', null);
INSERT INTO `system_area` VALUES ('513225', '九寨沟县', '513200', 'SC', null);
INSERT INTO `system_area` VALUES ('513226', '金川县', '513200', 'SC', null);
INSERT INTO `system_area` VALUES ('513227', '小金县', '513200', 'SC', null);
INSERT INTO `system_area` VALUES ('513228', '黑水县', '513200', 'SC', null);
INSERT INTO `system_area` VALUES ('513229', '马尔康县', '513200', 'SC', null);
INSERT INTO `system_area` VALUES ('513230', '壤塘县', '513200', 'SC', null);
INSERT INTO `system_area` VALUES ('513231', '阿坝县', '513200', 'SC', null);
INSERT INTO `system_area` VALUES ('513232', '若尔盖县', '513200', 'SC', null);
INSERT INTO `system_area` VALUES ('513233', '红原县', '513200', 'SC', null);
INSERT INTO `system_area` VALUES ('513300', '甘孜藏族自治州', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('513321', '康定县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513322', '泸定县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513323', '丹巴县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513324', '九龙县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513325', '雅江县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513326', '道孚县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513327', '炉霍县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513328', '甘孜县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513329', '新龙县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513330', '德格县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513331', '白玉县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513332', '石渠县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513333', '色达县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513334', '理塘县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513335', '巴塘县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513336', '乡城县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513337', '稻城县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513338', '得荣县', '513300', 'SC', null);
INSERT INTO `system_area` VALUES ('513400', '凉山彝族自治州', '510000', 'SC', null);
INSERT INTO `system_area` VALUES ('513401', '西昌市', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513422', '木里藏族自治县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513423', '盐源县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513424', '德昌县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513425', '会理县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513426', '会东县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513427', '宁南县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513428', '普格县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513429', '布拖县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513430', '金阳县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513431', '昭觉县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513432', '喜德县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513433', '冕宁县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513434', '越西县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513435', '甘洛县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513436', '美姑县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('513437', '雷波县', '513400', 'SC', null);
INSERT INTO `system_area` VALUES ('520000', '贵州省', '0001', 'GZ', 'XN');
INSERT INTO `system_area` VALUES ('520100', '贵阳市', '520000', 'GZ', null);
INSERT INTO `system_area` VALUES ('520101', '市辖区', '520100', 'GZ', null);
INSERT INTO `system_area` VALUES ('520102', '南明区', '520100', 'GZ', null);
INSERT INTO `system_area` VALUES ('520103', '云岩区', '520100', 'GZ', null);
INSERT INTO `system_area` VALUES ('520111', '花溪区', '520100', 'GZ', null);
INSERT INTO `system_area` VALUES ('520112', '乌当区', '520100', 'GZ', null);
INSERT INTO `system_area` VALUES ('520113', '白云区', '520100', 'GZ', null);
INSERT INTO `system_area` VALUES ('520114', '小河区', '520100', 'GZ', null);
INSERT INTO `system_area` VALUES ('520121', '开阳县', '520100', 'GZ', null);
INSERT INTO `system_area` VALUES ('520122', '息烽县', '520100', 'GZ', null);
INSERT INTO `system_area` VALUES ('520123', '修文县', '520100', 'GZ', null);
INSERT INTO `system_area` VALUES ('520181', '清镇市', '520100', 'GZ', null);
INSERT INTO `system_area` VALUES ('520200', '六盘水市', '520000', 'GZ', null);
INSERT INTO `system_area` VALUES ('520201', '钟山区', '520200', 'GZ', null);
INSERT INTO `system_area` VALUES ('520203', '六枝特区', '520200', 'GZ', null);
INSERT INTO `system_area` VALUES ('520221', '水城县', '520200', 'GZ', null);
INSERT INTO `system_area` VALUES ('520222', '盘县', '520200', 'GZ', null);
INSERT INTO `system_area` VALUES ('520300', '遵义市', '520000', 'GZ', null);
INSERT INTO `system_area` VALUES ('520301', '市辖区', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520302', '红花岗区', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520303', '汇川区', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520321', '遵义县', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520322', '桐梓县', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520323', '绥阳县', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520324', '正安县', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520325', '道真仡佬族苗族自治县', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520326', '务川仡佬族苗族自治县', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520327', '凤冈县', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520328', '湄潭县', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520329', '余庆县', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520330', '习水县', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520381', '赤水市', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520382', '仁怀市', '520300', 'GZ', null);
INSERT INTO `system_area` VALUES ('520400', '安顺市', '520000', 'GZ', null);
INSERT INTO `system_area` VALUES ('520401', '市辖区', '520400', 'GZ', null);
INSERT INTO `system_area` VALUES ('520402', '西秀区', '520400', 'GZ', null);
INSERT INTO `system_area` VALUES ('520421', '平坝县', '520400', 'GZ', null);
INSERT INTO `system_area` VALUES ('520422', '普定县', '520400', 'GZ', null);
INSERT INTO `system_area` VALUES ('520423', '镇宁布依族苗族自治县', '520400', 'GZ', null);
INSERT INTO `system_area` VALUES ('520424', '关岭布依族苗族自治县', '520400', 'GZ', null);
INSERT INTO `system_area` VALUES ('520425', '紫云苗族布依族自治县', '520400', 'GZ', null);
INSERT INTO `system_area` VALUES ('522200', '铜仁地区', '520000', 'GZ', null);
INSERT INTO `system_area` VALUES ('522201', '铜仁市', '522200', 'GZ', null);
INSERT INTO `system_area` VALUES ('522222', '江口县', '522200', 'GZ', null);
INSERT INTO `system_area` VALUES ('522223', '玉屏侗族自治县', '522200', 'GZ', null);
INSERT INTO `system_area` VALUES ('522224', '石阡县', '522200', 'GZ', null);
INSERT INTO `system_area` VALUES ('522225', '思南县', '522200', 'GZ', null);
INSERT INTO `system_area` VALUES ('522226', '印江土家族苗族自治县', '522200', 'GZ', null);
INSERT INTO `system_area` VALUES ('522227', '德江县', '522200', 'GZ', null);
INSERT INTO `system_area` VALUES ('522228', '沿河土家族自治县', '522200', 'GZ', null);
INSERT INTO `system_area` VALUES ('522229', '松桃苗族自治县', '522200', 'GZ', null);
INSERT INTO `system_area` VALUES ('522230', '万山特区', '522200', 'GZ', null);
INSERT INTO `system_area` VALUES ('522300', '黔西南布依族苗族自治', '520000', 'GZ', null);
INSERT INTO `system_area` VALUES ('522301', '兴义市', '522300', 'GZ', null);
INSERT INTO `system_area` VALUES ('522322', '兴仁县', '522300', 'GZ', null);
INSERT INTO `system_area` VALUES ('522323', '普安县', '522300', 'GZ', null);
INSERT INTO `system_area` VALUES ('522324', '晴隆县', '522300', 'GZ', null);
INSERT INTO `system_area` VALUES ('522325', '贞丰县', '522300', 'GZ', null);
INSERT INTO `system_area` VALUES ('522326', '望谟县', '522300', 'GZ', null);
INSERT INTO `system_area` VALUES ('522327', '册亨县', '522300', 'GZ', null);
INSERT INTO `system_area` VALUES ('522328', '安龙县', '522300', 'GZ', null);
INSERT INTO `system_area` VALUES ('522400', '毕节地区', '520000', 'GZ', null);
INSERT INTO `system_area` VALUES ('522401', '毕节市', '522400', 'GZ', null);
INSERT INTO `system_area` VALUES ('522422', '大方县', '522400', 'GZ', null);
INSERT INTO `system_area` VALUES ('522423', '黔西县', '522400', 'GZ', null);
INSERT INTO `system_area` VALUES ('522424', '金沙县', '522400', 'GZ', null);
INSERT INTO `system_area` VALUES ('522425', '织金县', '522400', 'GZ', null);
INSERT INTO `system_area` VALUES ('522426', '纳雍县', '522400', 'GZ', null);
INSERT INTO `system_area` VALUES ('522427', '威宁彝族回族苗族自治', '522400', 'GZ', null);
INSERT INTO `system_area` VALUES ('522428', '赫章县', '522400', 'GZ', null);
INSERT INTO `system_area` VALUES ('522600', '黔东南苗族侗族自治州', '520000', 'GZ', null);
INSERT INTO `system_area` VALUES ('522601', '凯里市', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522622', '黄平县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522623', '施秉县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522624', '三穗县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522625', '镇远县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522626', '岑巩县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522627', '天柱县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522628', '锦屏县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522629', '剑河县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522630', '台江县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522631', '黎平县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522632', '榕江县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522633', '从江县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522634', '雷山县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522635', '麻江县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522636', '丹寨县', '522600', 'GZ', null);
INSERT INTO `system_area` VALUES ('522700', '黔南布依族苗族自治州', '520000', 'GZ', null);
INSERT INTO `system_area` VALUES ('522701', '都匀市', '522700', 'GZ', null);
INSERT INTO `system_area` VALUES ('522702', '福泉市', '522700', 'GZ', null);
INSERT INTO `system_area` VALUES ('522722', '荔波县', '522700', 'GZ', null);
INSERT INTO `system_area` VALUES ('522723', '贵定县', '522700', 'GZ', null);
INSERT INTO `system_area` VALUES ('522725', '瓮安县', '522700', 'GZ', null);
INSERT INTO `system_area` VALUES ('522726', '独山县', '522700', 'GZ', null);
INSERT INTO `system_area` VALUES ('522727', '平塘县', '522700', 'GZ', null);
INSERT INTO `system_area` VALUES ('522728', '罗甸县', '522700', 'GZ', null);
INSERT INTO `system_area` VALUES ('522729', '长顺县', '522700', 'GZ', null);
INSERT INTO `system_area` VALUES ('522730', '龙里县', '522700', 'GZ', null);
INSERT INTO `system_area` VALUES ('522731', '惠水县', '522700', 'GZ', null);
INSERT INTO `system_area` VALUES ('522732', '三都水族自治县', '522700', 'GZ', null);
INSERT INTO `system_area` VALUES ('530000', '云南省', '0001', 'YN', 'XN');
INSERT INTO `system_area` VALUES ('530100', '昆明市', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('530101', '市辖区', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530102', '五华区', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530103', '盘龙区', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530111', '官渡区', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530112', '西山区', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530113', '东川区', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530121', '呈贡县', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530122', '晋宁县', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530124', '富民县', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530125', '宜良县', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530126', '石林彝族自治县', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530127', '嵩明县', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530128', '禄劝彝族苗族自治县', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530129', '寻甸回族彝族自治县', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530181', '安宁市', '530100', 'YN', null);
INSERT INTO `system_area` VALUES ('530300', '曲靖市', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('530301', '市辖区', '530300', 'YN', null);
INSERT INTO `system_area` VALUES ('530302', '麒麟区', '530300', 'YN', null);
INSERT INTO `system_area` VALUES ('530321', '马龙县', '530300', 'YN', null);
INSERT INTO `system_area` VALUES ('530322', '陆良县', '530300', 'YN', null);
INSERT INTO `system_area` VALUES ('530323', '师宗县', '530300', 'YN', null);
INSERT INTO `system_area` VALUES ('530324', '罗平县', '530300', 'YN', null);
INSERT INTO `system_area` VALUES ('530325', '富源县', '530300', 'YN', null);
INSERT INTO `system_area` VALUES ('530326', '会泽县', '530300', 'YN', null);
INSERT INTO `system_area` VALUES ('530328', '沾益县', '530300', 'YN', null);
INSERT INTO `system_area` VALUES ('530381', '宣威市', '530300', 'YN', null);
INSERT INTO `system_area` VALUES ('530400', '玉溪市', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('530401', '市辖区', '530400', 'YN', null);
INSERT INTO `system_area` VALUES ('530402', '红塔区', '530400', 'YN', null);
INSERT INTO `system_area` VALUES ('530421', '江川县', '530400', 'YN', null);
INSERT INTO `system_area` VALUES ('530422', '澄江县', '530400', 'YN', null);
INSERT INTO `system_area` VALUES ('530423', '通海县', '530400', 'YN', null);
INSERT INTO `system_area` VALUES ('530424', '华宁县', '530400', 'YN', null);
INSERT INTO `system_area` VALUES ('530425', '易门县', '530400', 'YN', null);
INSERT INTO `system_area` VALUES ('530426', '峨山彝族自治县', '530400', 'YN', null);
INSERT INTO `system_area` VALUES ('530427', '新平彝族傣族自治县', '530400', 'YN', null);
INSERT INTO `system_area` VALUES ('530428', '元江哈尼族彝族傣族自', '530400', 'YN', null);
INSERT INTO `system_area` VALUES ('530500', '保山市', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('530501', '市辖区', '530500', 'YN', null);
INSERT INTO `system_area` VALUES ('530502', '隆阳区', '530500', 'YN', null);
INSERT INTO `system_area` VALUES ('530521', '施甸县', '530500', 'YN', null);
INSERT INTO `system_area` VALUES ('530522', '腾冲县', '530500', 'YN', null);
INSERT INTO `system_area` VALUES ('530523', '龙陵县', '530500', 'YN', null);
INSERT INTO `system_area` VALUES ('530524', '昌宁县', '530500', 'YN', null);
INSERT INTO `system_area` VALUES ('530600', '昭通市', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('530601', '市辖区', '530600', 'YN', null);
INSERT INTO `system_area` VALUES ('530602', '昭阳区', '530600', 'YN', null);
INSERT INTO `system_area` VALUES ('530621', '鲁甸县', '530600', 'YN', null);
INSERT INTO `system_area` VALUES ('530622', '巧家县', '530600', 'YN', null);
INSERT INTO `system_area` VALUES ('530623', '盐津县', '530600', 'YN', null);
INSERT INTO `system_area` VALUES ('530624', '大关县', '530600', 'YN', null);
INSERT INTO `system_area` VALUES ('530625', '永善县', '530600', 'YN', null);
INSERT INTO `system_area` VALUES ('530626', '绥江县', '530600', 'YN', null);
INSERT INTO `system_area` VALUES ('530627', '镇雄县', '530600', 'YN', null);
INSERT INTO `system_area` VALUES ('530628', '彝良县', '530600', 'YN', null);
INSERT INTO `system_area` VALUES ('530629', '威信县', '530600', 'YN', null);
INSERT INTO `system_area` VALUES ('530630', '水富县', '530600', 'YN', null);
INSERT INTO `system_area` VALUES ('530700', '丽江市', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('530701', '市辖区', '530700', 'YN', null);
INSERT INTO `system_area` VALUES ('530702', '古城区', '530700', 'YN', null);
INSERT INTO `system_area` VALUES ('530721', '玉龙纳西族自治县', '530700', 'YN', null);
INSERT INTO `system_area` VALUES ('530722', '永胜县', '530700', 'YN', null);
INSERT INTO `system_area` VALUES ('530723', '华坪县', '530700', 'YN', null);
INSERT INTO `system_area` VALUES ('530724', '宁蒗彝族自治县', '530700', 'YN', null);
INSERT INTO `system_area` VALUES ('530800', '思茅市', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('530801', '市辖区', '530800', 'YN', null);
INSERT INTO `system_area` VALUES ('530802', '翠云区', '530800', 'YN', null);
INSERT INTO `system_area` VALUES ('530821', '普洱哈尼族彝族自治县', '530800', 'YN', null);
INSERT INTO `system_area` VALUES ('530822', '墨江哈尼族自治县', '530800', 'YN', null);
INSERT INTO `system_area` VALUES ('530823', '景东彝族自治县', '530800', 'YN', null);
INSERT INTO `system_area` VALUES ('530824', '景谷傣族彝族自治县', '530800', 'YN', null);
INSERT INTO `system_area` VALUES ('530825', '镇沅彝族哈尼族拉祜族', '530800', 'YN', null);
INSERT INTO `system_area` VALUES ('530826', '江城哈尼族彝族自治县', '530800', 'YN', null);
INSERT INTO `system_area` VALUES ('530827', '孟连傣族拉祜族佤族自', '530800', 'YN', null);
INSERT INTO `system_area` VALUES ('530828', '澜沧拉祜族自治县', '530800', 'YN', null);
INSERT INTO `system_area` VALUES ('530829', '西盟佤族自治县', '530800', 'YN', null);
INSERT INTO `system_area` VALUES ('530900', '临沧市', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('530901', '市辖区', '530900', 'YN', null);
INSERT INTO `system_area` VALUES ('530902', '临翔区', '530900', 'YN', null);
INSERT INTO `system_area` VALUES ('530921', '凤庆县', '530900', 'YN', null);
INSERT INTO `system_area` VALUES ('530922', '云县', '530900', 'YN', null);
INSERT INTO `system_area` VALUES ('530923', '永德县', '530900', 'YN', null);
INSERT INTO `system_area` VALUES ('530924', '镇康县', '530900', 'YN', null);
INSERT INTO `system_area` VALUES ('530925', '双江拉祜族佤族布朗族', '530900', 'YN', null);
INSERT INTO `system_area` VALUES ('530926', '耿马傣族佤族自治县', '530900', 'YN', null);
INSERT INTO `system_area` VALUES ('530927', '沧源佤族自治县', '530900', 'YN', null);
INSERT INTO `system_area` VALUES ('532300', '楚雄彝族自治州', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('532301', '楚雄市', '532300', 'YN', null);
INSERT INTO `system_area` VALUES ('532322', '双柏县', '532300', 'YN', null);
INSERT INTO `system_area` VALUES ('532323', '牟定县', '532300', 'YN', null);
INSERT INTO `system_area` VALUES ('532324', '南华县', '532300', 'YN', null);
INSERT INTO `system_area` VALUES ('532325', '姚安县', '532300', 'YN', null);
INSERT INTO `system_area` VALUES ('532326', '大姚县', '532300', 'YN', null);
INSERT INTO `system_area` VALUES ('532327', '永仁县', '532300', 'YN', null);
INSERT INTO `system_area` VALUES ('532328', '元谋县', '532300', 'YN', null);
INSERT INTO `system_area` VALUES ('532329', '武定县', '532300', 'YN', null);
INSERT INTO `system_area` VALUES ('532331', '禄丰县', '532300', 'YN', null);
INSERT INTO `system_area` VALUES ('532500', '红河哈尼族彝族自治州', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('532501', '个旧市', '532500', 'YN', null);
INSERT INTO `system_area` VALUES ('532502', '开远市', '532500', 'YN', null);
INSERT INTO `system_area` VALUES ('532522', '蒙自县', '532500', 'YN', null);
INSERT INTO `system_area` VALUES ('532523', '屏边苗族自治县', '532500', 'YN', null);
INSERT INTO `system_area` VALUES ('532524', '建水县', '532500', 'YN', null);
INSERT INTO `system_area` VALUES ('532525', '石屏县', '532500', 'YN', null);
INSERT INTO `system_area` VALUES ('532526', '弥勒县', '532500', 'YN', null);
INSERT INTO `system_area` VALUES ('532527', '泸西县', '532500', 'YN', null);
INSERT INTO `system_area` VALUES ('532528', '元阳县', '532500', 'YN', null);
INSERT INTO `system_area` VALUES ('532529', '红河县', '532500', 'YN', null);
INSERT INTO `system_area` VALUES ('532530', '金平苗族瑶族傣族自治', '532500', 'YN', null);
INSERT INTO `system_area` VALUES ('532531', '绿春县', '532500', 'YN', null);
INSERT INTO `system_area` VALUES ('532532', '河口瑶族自治县', '532500', 'YN', null);
INSERT INTO `system_area` VALUES ('532600', '文山壮族苗族自治州', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('532621', '文山县', '532600', 'YN', null);
INSERT INTO `system_area` VALUES ('532622', '砚山县', '532600', 'YN', null);
INSERT INTO `system_area` VALUES ('532623', '西畴县', '532600', 'YN', null);
INSERT INTO `system_area` VALUES ('532624', '麻栗坡县', '532600', 'YN', null);
INSERT INTO `system_area` VALUES ('532625', '马关县', '532600', 'YN', null);
INSERT INTO `system_area` VALUES ('532626', '丘北县', '532600', 'YN', null);
INSERT INTO `system_area` VALUES ('532627', '广南县', '532600', 'YN', null);
INSERT INTO `system_area` VALUES ('532628', '富宁县', '532600', 'YN', null);
INSERT INTO `system_area` VALUES ('532800', '西双版纳傣族自治州', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('532801', '景洪市', '532800', 'YN', null);
INSERT INTO `system_area` VALUES ('532822', '勐海县', '532800', 'YN', null);
INSERT INTO `system_area` VALUES ('532823', '勐腊县', '532800', 'YN', null);
INSERT INTO `system_area` VALUES ('532900', '大理白族自治州', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('532901', '大理市', '532900', 'YN', null);
INSERT INTO `system_area` VALUES ('532922', '漾濞彝族自治县', '532900', 'YN', null);
INSERT INTO `system_area` VALUES ('532923', '祥云县', '532900', 'YN', null);
INSERT INTO `system_area` VALUES ('532924', '宾川县', '532900', 'YN', null);
INSERT INTO `system_area` VALUES ('532925', '弥渡县', '532900', 'YN', null);
INSERT INTO `system_area` VALUES ('532926', '南涧彝族自治县', '532900', 'YN', null);
INSERT INTO `system_area` VALUES ('532927', '巍山彝族回族自治县', '532900', 'YN', null);
INSERT INTO `system_area` VALUES ('532928', '永平县', '532900', 'YN', null);
INSERT INTO `system_area` VALUES ('532929', '云龙县', '532900', 'YN', null);
INSERT INTO `system_area` VALUES ('532930', '洱源县', '532900', 'YN', null);
INSERT INTO `system_area` VALUES ('532931', '剑川县', '532900', 'YN', null);
INSERT INTO `system_area` VALUES ('532932', '鹤庆县', '532900', 'YN', null);
INSERT INTO `system_area` VALUES ('533100', '德宏傣族景颇族自治州', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('533102', '瑞丽市', '533100', 'YN', null);
INSERT INTO `system_area` VALUES ('533103', '潞西市', '533100', 'YN', null);
INSERT INTO `system_area` VALUES ('533122', '梁河县', '533100', 'YN', null);
INSERT INTO `system_area` VALUES ('533123', '盈江县', '533100', 'YN', null);
INSERT INTO `system_area` VALUES ('533124', '陇川县', '533100', 'YN', null);
INSERT INTO `system_area` VALUES ('533300', '怒江傈僳族自治州', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('533321', '泸水县', '533300', 'YN', null);
INSERT INTO `system_area` VALUES ('533323', '福贡县', '533300', 'YN', null);
INSERT INTO `system_area` VALUES ('533324', '贡山独龙族怒族自治县', '533300', 'YN', null);
INSERT INTO `system_area` VALUES ('533325', '兰坪白族普米族自治县', '533300', 'YN', null);
INSERT INTO `system_area` VALUES ('533400', '迪庆藏族自治州', '530000', 'YN', null);
INSERT INTO `system_area` VALUES ('533421', '香格里拉县', '533400', 'YN', null);
INSERT INTO `system_area` VALUES ('533422', '德钦县', '533400', 'YN', null);
INSERT INTO `system_area` VALUES ('533423', '维西傈僳族自治县', '533400', 'YN', null);
INSERT INTO `system_area` VALUES ('540000', '西藏自治区', '0001', 'XZ', 'XN');
INSERT INTO `system_area` VALUES ('540100', '拉萨市', '540000', 'XZ', null);
INSERT INTO `system_area` VALUES ('540101', '市辖区', '540100', 'XZ', null);
INSERT INTO `system_area` VALUES ('540102', '城关区', '540100', 'XZ', null);
INSERT INTO `system_area` VALUES ('540121', '林周县', '540100', 'XZ', null);
INSERT INTO `system_area` VALUES ('540122', '当雄县', '540100', 'XZ', null);
INSERT INTO `system_area` VALUES ('540123', '尼木县', '540100', 'XZ', null);
INSERT INTO `system_area` VALUES ('540124', '曲水县', '540100', 'XZ', null);
INSERT INTO `system_area` VALUES ('540125', '堆龙德庆县', '540100', 'XZ', null);
INSERT INTO `system_area` VALUES ('540126', '达孜县', '540100', 'XZ', null);
INSERT INTO `system_area` VALUES ('540127', '墨竹工卡县', '540100', 'XZ', null);
INSERT INTO `system_area` VALUES ('542100', '昌都地区', '540000', 'XZ', null);
INSERT INTO `system_area` VALUES ('542121', '昌都县', '542100', 'XZ', null);
INSERT INTO `system_area` VALUES ('542122', '江达县', '542100', 'XZ', null);
INSERT INTO `system_area` VALUES ('542123', '贡觉县', '542100', 'XZ', null);
INSERT INTO `system_area` VALUES ('542124', '类乌齐县', '542100', 'XZ', null);
INSERT INTO `system_area` VALUES ('542125', '丁青县', '542100', 'XZ', null);
INSERT INTO `system_area` VALUES ('542126', '察雅县', '542100', 'XZ', null);
INSERT INTO `system_area` VALUES ('542127', '八宿县', '542100', 'XZ', null);
INSERT INTO `system_area` VALUES ('542128', '左贡县', '542100', 'XZ', null);
INSERT INTO `system_area` VALUES ('542129', '芒康县', '542100', 'XZ', null);
INSERT INTO `system_area` VALUES ('542132', '洛隆县', '542100', 'XZ', null);
INSERT INTO `system_area` VALUES ('542133', '边坝县', '542100', 'XZ', null);
INSERT INTO `system_area` VALUES ('542200', '山南地区', '540000', 'XZ', null);
INSERT INTO `system_area` VALUES ('542221', '乃东县', '542200', 'XZ', null);
INSERT INTO `system_area` VALUES ('542222', '扎囊县', '542200', 'XZ', null);
INSERT INTO `system_area` VALUES ('542223', '贡嘎县', '542200', 'XZ', null);
INSERT INTO `system_area` VALUES ('542224', '桑日县', '542200', 'XZ', null);
INSERT INTO `system_area` VALUES ('542225', '琼结县', '542200', 'XZ', null);
INSERT INTO `system_area` VALUES ('542226', '曲松县', '542200', 'XZ', null);
INSERT INTO `system_area` VALUES ('542227', '措美县', '542200', 'XZ', null);
INSERT INTO `system_area` VALUES ('542228', '洛扎县', '542200', 'XZ', null);
INSERT INTO `system_area` VALUES ('542229', '加查县', '542200', 'XZ', null);
INSERT INTO `system_area` VALUES ('542231', '隆子县', '542200', 'XZ', null);
INSERT INTO `system_area` VALUES ('542232', '错那县', '542200', 'XZ', null);
INSERT INTO `system_area` VALUES ('542233', '浪卡子县', '542200', 'XZ', null);
INSERT INTO `system_area` VALUES ('542300', '日喀则地区', '540000', 'XZ', null);
INSERT INTO `system_area` VALUES ('542301', '日喀则市', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542322', '南木林县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542323', '江孜县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542324', '定日县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542325', '萨迦县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542326', '拉孜县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542327', '昂仁县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542328', '谢通门县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542329', '白朗县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542330', '仁布县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542331', '康马县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542332', '定结县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542333', '仲巴县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542334', '亚东县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542335', '吉隆县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542336', '聂拉木县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542337', '萨嘎县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542338', '岗巴县', '542300', 'XZ', null);
INSERT INTO `system_area` VALUES ('542400', '那曲地区', '540000', 'XZ', null);
INSERT INTO `system_area` VALUES ('542421', '那曲县', '542400', 'XZ', null);
INSERT INTO `system_area` VALUES ('542422', '嘉黎县', '542400', 'XZ', null);
INSERT INTO `system_area` VALUES ('542423', '比如县', '542400', 'XZ', null);
INSERT INTO `system_area` VALUES ('542424', '聂荣县', '542400', 'XZ', null);
INSERT INTO `system_area` VALUES ('542425', '安多县', '542400', 'XZ', null);
INSERT INTO `system_area` VALUES ('542426', '申扎县', '542400', 'XZ', null);
INSERT INTO `system_area` VALUES ('542427', '索县', '542400', 'XZ', null);
INSERT INTO `system_area` VALUES ('542428', '班戈县', '542400', 'XZ', null);
INSERT INTO `system_area` VALUES ('542429', '巴青县', '542400', 'XZ', null);
INSERT INTO `system_area` VALUES ('542430', '尼玛县', '542400', 'XZ', null);
INSERT INTO `system_area` VALUES ('542500', '阿里地区', '540000', 'XZ', null);
INSERT INTO `system_area` VALUES ('542521', '普兰县', '542500', 'XZ', null);
INSERT INTO `system_area` VALUES ('542522', '札达县', '542500', 'XZ', null);
INSERT INTO `system_area` VALUES ('542523', '噶尔县', '542500', 'XZ', null);
INSERT INTO `system_area` VALUES ('542524', '日土县', '542500', 'XZ', null);
INSERT INTO `system_area` VALUES ('542525', '革吉县', '542500', 'XZ', null);
INSERT INTO `system_area` VALUES ('542526', '改则县', '542500', 'XZ', null);
INSERT INTO `system_area` VALUES ('542527', '措勤县', '542500', 'XZ', null);
INSERT INTO `system_area` VALUES ('542600', '林芝地区', '540000', 'XZ', null);
INSERT INTO `system_area` VALUES ('542621', '林芝县', '542600', 'XZ', null);
INSERT INTO `system_area` VALUES ('542622', '工布江达县', '542600', 'XZ', null);
INSERT INTO `system_area` VALUES ('542623', '米林县', '542600', 'XZ', null);
INSERT INTO `system_area` VALUES ('542624', '墨脱县', '542600', 'XZ', null);
INSERT INTO `system_area` VALUES ('542625', '波密县', '542600', 'XZ', null);
INSERT INTO `system_area` VALUES ('542626', '察隅县', '542600', 'XZ', null);
INSERT INTO `system_area` VALUES ('542627', '朗县', '542600', 'XZ', null);
INSERT INTO `system_area` VALUES ('610000', '陕西省', '0001', 'SN', 'XB');
INSERT INTO `system_area` VALUES ('610100', '西安市', '610000', 'SN', null);
INSERT INTO `system_area` VALUES ('610101', '市辖区', '610100', 'SN', null);
INSERT INTO `system_area` VALUES ('610102', '新城区', '610100', 'SN', null);
INSERT INTO `system_area` VALUES ('610103', '碑林区', '610100', 'SN', null);
INSERT INTO `system_area` VALUES ('610104', '莲湖区', '610100', 'SN', null);
INSERT INTO `system_area` VALUES ('610111', '灞桥区', '610100', 'SN', null);
INSERT INTO `system_area` VALUES ('610112', '未央区', '610100', 'SN', null);
INSERT INTO `system_area` VALUES ('610113', '雁塔区', '610100', 'SN', null);
INSERT INTO `system_area` VALUES ('610114', '阎良区', '610100', 'SN', null);
INSERT INTO `system_area` VALUES ('610115', '临潼区', '610100', 'SN', null);
INSERT INTO `system_area` VALUES ('610116', '长安区', '610100', 'SN', null);
INSERT INTO `system_area` VALUES ('610122', '蓝田县', '610100', 'SN', null);
INSERT INTO `system_area` VALUES ('610124', '周至县', '610100', 'SN', null);
INSERT INTO `system_area` VALUES ('610125', '户县', '610100', 'SN', null);
INSERT INTO `system_area` VALUES ('610126', '高陵县', '610100', 'SN', null);
INSERT INTO `system_area` VALUES ('610200', '铜川市', '610000', 'SN', null);
INSERT INTO `system_area` VALUES ('610201', '市辖区', '610200', 'SN', null);
INSERT INTO `system_area` VALUES ('610202', '王益区', '610200', 'SN', null);
INSERT INTO `system_area` VALUES ('610203', '印台区', '610200', 'SN', null);
INSERT INTO `system_area` VALUES ('610204', '耀州区', '610200', 'SN', null);
INSERT INTO `system_area` VALUES ('610222', '宜君县', '610200', 'SN', null);
INSERT INTO `system_area` VALUES ('610300', '宝鸡市', '610000', 'SN', null);
INSERT INTO `system_area` VALUES ('610301', '市辖区', '610300', 'SN', null);
INSERT INTO `system_area` VALUES ('610302', '渭滨区', '610300', 'SN', null);
INSERT INTO `system_area` VALUES ('610303', '金台区', '610300', 'SN', null);
INSERT INTO `system_area` VALUES ('610304', '陈仓区', '610300', 'SN', null);
INSERT INTO `system_area` VALUES ('610322', '凤翔县', '610300', 'SN', null);
INSERT INTO `system_area` VALUES ('610323', '岐山县', '610300', 'SN', null);
INSERT INTO `system_area` VALUES ('610324', '扶风县', '610300', 'SN', null);
INSERT INTO `system_area` VALUES ('610326', '眉县', '610300', 'SN', null);
INSERT INTO `system_area` VALUES ('610327', '陇县', '610300', 'SN', null);
INSERT INTO `system_area` VALUES ('610328', '千阳县', '610300', 'SN', null);
INSERT INTO `system_area` VALUES ('610329', '麟游县', '610300', 'SN', null);
INSERT INTO `system_area` VALUES ('610330', '凤县', '610300', 'SN', null);
INSERT INTO `system_area` VALUES ('610331', '太白县', '610300', 'SN', null);
INSERT INTO `system_area` VALUES ('610400', '咸阳市', '610000', 'SN', null);
INSERT INTO `system_area` VALUES ('610401', '市辖区', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610402', '秦都区', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610403', '杨凌区', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610404', '渭城区', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610422', '三原县', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610423', '泾阳县', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610424', '乾县', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610425', '礼泉县', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610426', '永寿县', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610427', '彬县', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610428', '长武县', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610429', '旬邑县', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610430', '淳化县', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610431', '武功县', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610481', '兴平市', '610400', 'SN', null);
INSERT INTO `system_area` VALUES ('610500', '渭南市', '610000', 'SN', null);
INSERT INTO `system_area` VALUES ('610501', '市辖区', '610500', 'SN', null);
INSERT INTO `system_area` VALUES ('610502', '临渭区', '610500', 'SN', null);
INSERT INTO `system_area` VALUES ('610521', '华县', '610500', 'SN', null);
INSERT INTO `system_area` VALUES ('610522', '潼关县', '610500', 'SN', null);
INSERT INTO `system_area` VALUES ('610523', '大荔县', '610500', 'SN', null);
INSERT INTO `system_area` VALUES ('610524', '合阳县', '610500', 'SN', null);
INSERT INTO `system_area` VALUES ('610525', '澄城县', '610500', 'SN', null);
INSERT INTO `system_area` VALUES ('610526', '蒲城县', '610500', 'SN', null);
INSERT INTO `system_area` VALUES ('610527', '白水县', '610500', 'SN', null);
INSERT INTO `system_area` VALUES ('610528', '富平县', '610500', 'SN', null);
INSERT INTO `system_area` VALUES ('610581', '韩城市', '610500', 'SN', null);
INSERT INTO `system_area` VALUES ('610582', '华阴市', '610500', 'SN', null);
INSERT INTO `system_area` VALUES ('610600', '延安市', '610000', 'SN', null);
INSERT INTO `system_area` VALUES ('610601', '市辖区', '610600', 'SN', null);
INSERT INTO `system_area` VALUES ('610602', '宝塔区', '610600', 'SN', null);
INSERT INTO `system_area` VALUES ('610621', '延长县', '610600', 'SN', null);
INSERT INTO `system_area` VALUES ('610622', '延川县', '610600', 'SN', null);
INSERT INTO `system_area` VALUES ('610623', '子长县', '610600', 'SN', null);
INSERT INTO `system_area` VALUES ('610624', '安塞县', '610600', 'SN', null);
INSERT INTO `system_area` VALUES ('610625', '志丹县', '610600', 'SN', null);
INSERT INTO `system_area` VALUES ('610626', '吴旗县', '610600', 'SN', null);
INSERT INTO `system_area` VALUES ('610627', '甘泉县', '610600', 'SN', null);
INSERT INTO `system_area` VALUES ('610628', '富县', '610600', 'SN', null);
INSERT INTO `system_area` VALUES ('610629', '洛川县', '610600', 'SN', null);
INSERT INTO `system_area` VALUES ('610630', '宜川县', '610600', 'SN', null);
INSERT INTO `system_area` VALUES ('610631', '黄龙县', '610600', 'SN', null);
INSERT INTO `system_area` VALUES ('610632', '黄陵县', '610600', 'SN', null);
INSERT INTO `system_area` VALUES ('610700', '汉中市', '610000', 'SN', null);
INSERT INTO `system_area` VALUES ('610701', '市辖区', '610700', 'SN', null);
INSERT INTO `system_area` VALUES ('610702', '汉台区', '610700', 'SN', null);
INSERT INTO `system_area` VALUES ('610721', '南郑县', '610700', 'SN', null);
INSERT INTO `system_area` VALUES ('610722', '城固县', '610700', 'SN', null);
INSERT INTO `system_area` VALUES ('610723', '洋县', '610700', 'SN', null);
INSERT INTO `system_area` VALUES ('610724', '西乡县', '610700', 'SN', null);
INSERT INTO `system_area` VALUES ('610725', '勉县', '610700', 'SN', null);
INSERT INTO `system_area` VALUES ('610726', '宁强县', '610700', 'SN', null);
INSERT INTO `system_area` VALUES ('610727', '略阳县', '610700', 'SN', null);
INSERT INTO `system_area` VALUES ('610728', '镇巴县', '610700', 'SN', null);
INSERT INTO `system_area` VALUES ('610729', '留坝县', '610700', 'SN', null);
INSERT INTO `system_area` VALUES ('610730', '佛坪县', '610700', 'SN', null);
INSERT INTO `system_area` VALUES ('610800', '榆林市', '610000', 'SN', null);
INSERT INTO `system_area` VALUES ('610801', '市辖区', '610800', 'SN', null);
INSERT INTO `system_area` VALUES ('610802', '榆阳区', '610800', 'SN', null);
INSERT INTO `system_area` VALUES ('610821', '神木县', '610800', 'SN', null);
INSERT INTO `system_area` VALUES ('610822', '府谷县', '610800', 'SN', null);
INSERT INTO `system_area` VALUES ('610823', '横山县', '610800', 'SN', null);
INSERT INTO `system_area` VALUES ('610824', '靖边县', '610800', 'SN', null);
INSERT INTO `system_area` VALUES ('610825', '定边县', '610800', 'SN', null);
INSERT INTO `system_area` VALUES ('610826', '绥德县', '610800', 'SN', null);
INSERT INTO `system_area` VALUES ('610827', '米脂县', '610800', 'SN', null);
INSERT INTO `system_area` VALUES ('610828', '佳县', '610800', 'SN', null);
INSERT INTO `system_area` VALUES ('610829', '吴堡县', '610800', 'SN', null);
INSERT INTO `system_area` VALUES ('610830', '清涧县', '610800', 'SN', null);
INSERT INTO `system_area` VALUES ('610831', '子洲县', '610800', 'SN', null);
INSERT INTO `system_area` VALUES ('610900', '安康市', '610000', 'SN', null);
INSERT INTO `system_area` VALUES ('610901', '市辖区', '610900', 'SN', null);
INSERT INTO `system_area` VALUES ('610902', '汉滨区', '610900', 'SN', null);
INSERT INTO `system_area` VALUES ('610921', '汉阴县', '610900', 'SN', null);
INSERT INTO `system_area` VALUES ('610922', '石泉县', '610900', 'SN', null);
INSERT INTO `system_area` VALUES ('610923', '宁陕县', '610900', 'SN', null);
INSERT INTO `system_area` VALUES ('610924', '紫阳县', '610900', 'SN', null);
INSERT INTO `system_area` VALUES ('610925', '岚皋县', '610900', 'SN', null);
INSERT INTO `system_area` VALUES ('610926', '平利县', '610900', 'SN', null);
INSERT INTO `system_area` VALUES ('610927', '镇坪县', '610900', 'SN', null);
INSERT INTO `system_area` VALUES ('610928', '旬阳县', '610900', 'SN', null);
INSERT INTO `system_area` VALUES ('610929', '白河县', '610900', 'SN', null);
INSERT INTO `system_area` VALUES ('611000', '商洛市', '610000', 'SN', null);
INSERT INTO `system_area` VALUES ('611001', '市辖区', '611000', 'SN', null);
INSERT INTO `system_area` VALUES ('611002', '商州区', '611000', 'SN', null);
INSERT INTO `system_area` VALUES ('611021', '洛南县', '611000', 'SN', null);
INSERT INTO `system_area` VALUES ('611022', '丹凤县', '611000', 'SN', null);
INSERT INTO `system_area` VALUES ('611023', '商南县', '611000', 'SN', null);
INSERT INTO `system_area` VALUES ('611024', '山阳县', '611000', 'SN', null);
INSERT INTO `system_area` VALUES ('611025', '镇安县', '611000', 'SN', null);
INSERT INTO `system_area` VALUES ('611026', '柞水县', '611000', 'SN', null);
INSERT INTO `system_area` VALUES ('620000', '甘肃省', '0001', 'GS', 'XB');
INSERT INTO `system_area` VALUES ('620100', '兰州市', '620000', 'GS', null);
INSERT INTO `system_area` VALUES ('620101', '市辖区', '620100', 'GS', null);
INSERT INTO `system_area` VALUES ('620102', '城关区', '620100', 'GS', null);
INSERT INTO `system_area` VALUES ('620103', '七里河区', '620100', 'GS', null);
INSERT INTO `system_area` VALUES ('620104', '西固区', '620100', 'GS', null);
INSERT INTO `system_area` VALUES ('620105', '安宁区', '620100', 'GS', null);
INSERT INTO `system_area` VALUES ('620111', '红古区', '620100', 'GS', null);
INSERT INTO `system_area` VALUES ('620121', '永登县', '620100', 'GS', null);
INSERT INTO `system_area` VALUES ('620122', '皋兰县', '620100', 'GS', null);
INSERT INTO `system_area` VALUES ('620123', '榆中县', '620100', 'GS', null);
INSERT INTO `system_area` VALUES ('620200', '嘉峪关市', '620000', 'GS', null);
INSERT INTO `system_area` VALUES ('620201', '市辖区', '620200', 'GS', null);
INSERT INTO `system_area` VALUES ('620300', '金昌市', '620000', 'GS', null);
INSERT INTO `system_area` VALUES ('620301', '市辖区', '620300', 'GS', null);
INSERT INTO `system_area` VALUES ('620302', '金川区', '620300', 'GS', null);
INSERT INTO `system_area` VALUES ('620321', '永昌县', '620300', 'GS', null);
INSERT INTO `system_area` VALUES ('620400', '白银市', '620000', 'GS', null);
INSERT INTO `system_area` VALUES ('620401', '市辖区', '620400', 'GS', null);
INSERT INTO `system_area` VALUES ('620402', '白银区', '620400', 'GS', null);
INSERT INTO `system_area` VALUES ('620403', '平川区', '620400', 'GS', null);
INSERT INTO `system_area` VALUES ('620421', '靖远县', '620400', 'GS', null);
INSERT INTO `system_area` VALUES ('620422', '会宁县', '620400', 'GS', null);
INSERT INTO `system_area` VALUES ('620423', '景泰县', '620400', 'GS', null);
INSERT INTO `system_area` VALUES ('620500', '天水市', '620000', 'GS', null);
INSERT INTO `system_area` VALUES ('620501', '市辖区', '620500', 'GS', null);
INSERT INTO `system_area` VALUES ('620502', '秦城区', '620500', 'GS', null);
INSERT INTO `system_area` VALUES ('620503', '北道区', '620500', 'GS', null);
INSERT INTO `system_area` VALUES ('620521', '清水县', '620500', 'GS', null);
INSERT INTO `system_area` VALUES ('620522', '秦安县', '620500', 'GS', null);
INSERT INTO `system_area` VALUES ('620523', '甘谷县', '620500', 'GS', null);
INSERT INTO `system_area` VALUES ('620524', '武山县', '620500', 'GS', null);
INSERT INTO `system_area` VALUES ('620525', '张家川回族自治县', '620500', 'GS', null);
INSERT INTO `system_area` VALUES ('620600', '武威市', '620000', 'GS', null);
INSERT INTO `system_area` VALUES ('620601', '市辖区', '620600', 'GS', null);
INSERT INTO `system_area` VALUES ('620602', '凉州区', '620600', 'GS', null);
INSERT INTO `system_area` VALUES ('620621', '民勤县', '620600', 'GS', null);
INSERT INTO `system_area` VALUES ('620622', '古浪县', '620600', 'GS', null);
INSERT INTO `system_area` VALUES ('620623', '天祝藏族自治县', '620600', 'GS', null);
INSERT INTO `system_area` VALUES ('620700', '张掖市', '620000', 'GS', null);
INSERT INTO `system_area` VALUES ('620701', '市辖区', '620700', 'GS', null);
INSERT INTO `system_area` VALUES ('620702', '甘州区', '620700', 'GS', null);
INSERT INTO `system_area` VALUES ('620721', '肃南裕固族自治县', '620700', 'GS', null);
INSERT INTO `system_area` VALUES ('620722', '民乐县', '620700', 'GS', null);
INSERT INTO `system_area` VALUES ('620723', '临泽县', '620700', 'GS', null);
INSERT INTO `system_area` VALUES ('620724', '高台县', '620700', 'GS', null);
INSERT INTO `system_area` VALUES ('620725', '山丹县', '620700', 'GS', null);
INSERT INTO `system_area` VALUES ('620800', '平凉市', '620000', 'GS', null);
INSERT INTO `system_area` VALUES ('620801', '市辖区', '620800', 'GS', null);
INSERT INTO `system_area` VALUES ('620802', '崆峒区', '620800', 'GS', null);
INSERT INTO `system_area` VALUES ('620821', '泾川县', '620800', 'GS', null);
INSERT INTO `system_area` VALUES ('620822', '灵台县', '620800', 'GS', null);
INSERT INTO `system_area` VALUES ('620823', '崇信县', '620800', 'GS', null);
INSERT INTO `system_area` VALUES ('620824', '华亭县', '620800', 'GS', null);
INSERT INTO `system_area` VALUES ('620825', '庄浪县', '620800', 'GS', null);
INSERT INTO `system_area` VALUES ('620826', '静宁县', '620800', 'GS', null);
INSERT INTO `system_area` VALUES ('620900', '酒泉市', '620000', 'GS', null);
INSERT INTO `system_area` VALUES ('620901', '市辖区', '620900', 'GS', null);
INSERT INTO `system_area` VALUES ('620902', '肃州区', '620900', 'GS', null);
INSERT INTO `system_area` VALUES ('620921', '金塔县', '620900', 'GS', null);
INSERT INTO `system_area` VALUES ('620922', '安西县', '620900', 'GS', null);
INSERT INTO `system_area` VALUES ('620923', '肃北蒙古族自治县', '620900', 'GS', null);
INSERT INTO `system_area` VALUES ('620924', '阿克塞哈萨克族自治县', '620900', 'GS', null);
INSERT INTO `system_area` VALUES ('620981', '玉门市', '620900', 'GS', null);
INSERT INTO `system_area` VALUES ('620982', '敦煌市', '620900', 'GS', null);
INSERT INTO `system_area` VALUES ('621000', '庆阳市', '620000', 'GS', null);
INSERT INTO `system_area` VALUES ('621001', '市辖区', '621000', 'GS', null);
INSERT INTO `system_area` VALUES ('621002', '西峰区', '621000', 'GS', null);
INSERT INTO `system_area` VALUES ('621021', '庆城县', '621000', 'GS', null);
INSERT INTO `system_area` VALUES ('621022', '环县', '621000', 'GS', null);
INSERT INTO `system_area` VALUES ('621023', '华池县', '621000', 'GS', null);
INSERT INTO `system_area` VALUES ('621024', '合水县', '621000', 'GS', null);
INSERT INTO `system_area` VALUES ('621025', '正宁县', '621000', 'GS', null);
INSERT INTO `system_area` VALUES ('621026', '宁县', '621000', 'GS', null);
INSERT INTO `system_area` VALUES ('621027', '镇原县', '621000', 'GS', null);
INSERT INTO `system_area` VALUES ('621100', '定西市', '620000', 'GS', null);
INSERT INTO `system_area` VALUES ('621101', '市辖区', '621100', 'GS', null);
INSERT INTO `system_area` VALUES ('621102', '安定区', '621100', 'GS', null);
INSERT INTO `system_area` VALUES ('621121', '通渭县', '621100', 'GS', null);
INSERT INTO `system_area` VALUES ('621122', '陇西县', '621100', 'GS', null);
INSERT INTO `system_area` VALUES ('621123', '渭源县', '621100', 'GS', null);
INSERT INTO `system_area` VALUES ('621124', '临洮县', '621100', 'GS', null);
INSERT INTO `system_area` VALUES ('621125', '漳县', '621100', 'GS', null);
INSERT INTO `system_area` VALUES ('621126', '岷县', '621100', 'GS', null);
INSERT INTO `system_area` VALUES ('621200', '陇南市', '620000', 'GS', null);
INSERT INTO `system_area` VALUES ('621201', '市辖区', '621200', 'GS', null);
INSERT INTO `system_area` VALUES ('621202', '武都区', '621200', 'GS', null);
INSERT INTO `system_area` VALUES ('621221', '成县', '621200', 'GS', null);
INSERT INTO `system_area` VALUES ('621222', '文县', '621200', 'GS', null);
INSERT INTO `system_area` VALUES ('621223', '宕昌县', '621200', 'GS', null);
INSERT INTO `system_area` VALUES ('621224', '康县', '621200', 'GS', null);
INSERT INTO `system_area` VALUES ('621225', '西和县', '621200', 'GS', null);
INSERT INTO `system_area` VALUES ('621226', '礼县', '621200', 'GS', null);
INSERT INTO `system_area` VALUES ('621227', '徽县', '621200', 'GS', null);
INSERT INTO `system_area` VALUES ('621228', '两当县', '621200', 'GS', null);
INSERT INTO `system_area` VALUES ('622900', '临夏回族自治州', '620000', 'GS', null);
INSERT INTO `system_area` VALUES ('622901', '临夏市', '622900', 'GS', null);
INSERT INTO `system_area` VALUES ('622921', '临夏县', '622900', 'GS', null);
INSERT INTO `system_area` VALUES ('622922', '康乐县', '622900', 'GS', null);
INSERT INTO `system_area` VALUES ('622923', '永靖县', '622900', 'GS', null);
INSERT INTO `system_area` VALUES ('622924', '广河县', '622900', 'GS', null);
INSERT INTO `system_area` VALUES ('622925', '和政县', '622900', 'GS', null);
INSERT INTO `system_area` VALUES ('622926', '东乡族自治县', '622900', 'GS', null);
INSERT INTO `system_area` VALUES ('622927', '积石山保安族东乡族撒', '622900', 'GS', null);
INSERT INTO `system_area` VALUES ('623000', '甘南藏族自治州', '620000', 'GS', null);
INSERT INTO `system_area` VALUES ('623001', '合作市', '623000', 'GS', null);
INSERT INTO `system_area` VALUES ('623021', '临潭县', '623000', 'GS', null);
INSERT INTO `system_area` VALUES ('623022', '卓尼县', '623000', 'GS', null);
INSERT INTO `system_area` VALUES ('623023', '舟曲县', '623000', 'GS', null);
INSERT INTO `system_area` VALUES ('623024', '迭部县', '623000', 'GS', null);
INSERT INTO `system_area` VALUES ('623025', '玛曲县', '623000', 'GS', null);
INSERT INTO `system_area` VALUES ('623026', '碌曲县', '623000', 'GS', null);
INSERT INTO `system_area` VALUES ('623027', '夏河县', '623000', 'GS', null);
INSERT INTO `system_area` VALUES ('630000', '青海省', '0001', 'QH', 'XB');
INSERT INTO `system_area` VALUES ('630100', '西宁市', '630000', 'QH', null);
INSERT INTO `system_area` VALUES ('630101', '市辖区', '630100', 'QH', null);
INSERT INTO `system_area` VALUES ('630102', '城东区', '630100', 'QH', null);
INSERT INTO `system_area` VALUES ('630103', '城中区', '630100', 'QH', null);
INSERT INTO `system_area` VALUES ('630104', '城西区', '630100', 'QH', null);
INSERT INTO `system_area` VALUES ('630105', '城北区', '630100', 'QH', null);
INSERT INTO `system_area` VALUES ('630121', '大通回族土族自治县', '630100', 'QH', null);
INSERT INTO `system_area` VALUES ('630122', '湟中县', '630100', 'QH', null);
INSERT INTO `system_area` VALUES ('630123', '湟源县', '630100', 'QH', null);
INSERT INTO `system_area` VALUES ('632100', '海东地区', '630000', 'QH', null);
INSERT INTO `system_area` VALUES ('632121', '平安县', '632100', 'QH', null);
INSERT INTO `system_area` VALUES ('632122', '民和回族土族自治县', '632100', 'QH', null);
INSERT INTO `system_area` VALUES ('632123', '乐都县', '632100', 'QH', null);
INSERT INTO `system_area` VALUES ('632126', '互助土族自治县', '632100', 'QH', null);
INSERT INTO `system_area` VALUES ('632127', '化隆回族自治县', '632100', 'QH', null);
INSERT INTO `system_area` VALUES ('632128', '循化撒拉族自治县', '632100', 'QH', null);
INSERT INTO `system_area` VALUES ('632200', '海北藏族自治州', '630000', 'QH', null);
INSERT INTO `system_area` VALUES ('632221', '门源回族自治县', '632200', 'QH', null);
INSERT INTO `system_area` VALUES ('632222', '祁连县', '632200', 'QH', null);
INSERT INTO `system_area` VALUES ('632223', '海晏县', '632200', 'QH', null);
INSERT INTO `system_area` VALUES ('632224', '刚察县', '632200', 'QH', null);
INSERT INTO `system_area` VALUES ('632300', '黄南藏族自治州', '630000', 'QH', null);
INSERT INTO `system_area` VALUES ('632321', '同仁县', '632300', 'QH', null);
INSERT INTO `system_area` VALUES ('632322', '尖扎县', '632300', 'QH', null);
INSERT INTO `system_area` VALUES ('632323', '泽库县', '632300', 'QH', null);
INSERT INTO `system_area` VALUES ('632324', '河南蒙古族自治县', '632300', 'QH', null);
INSERT INTO `system_area` VALUES ('632500', '海南藏族自治州', '630000', 'QH', null);
INSERT INTO `system_area` VALUES ('632521', '共和县', '632500', 'QH', null);
INSERT INTO `system_area` VALUES ('632522', '同德县', '632500', 'QH', null);
INSERT INTO `system_area` VALUES ('632523', '贵德县', '632500', 'QH', null);
INSERT INTO `system_area` VALUES ('632524', '兴海县', '632500', 'QH', null);
INSERT INTO `system_area` VALUES ('632525', '贵南县', '632500', 'QH', null);
INSERT INTO `system_area` VALUES ('632600', '果洛藏族自治州', '630000', 'QH', null);
INSERT INTO `system_area` VALUES ('632621', '玛沁县', '632600', 'QH', null);
INSERT INTO `system_area` VALUES ('632622', '班玛县', '632600', 'QH', null);
INSERT INTO `system_area` VALUES ('632623', '甘德县', '632600', 'QH', null);
INSERT INTO `system_area` VALUES ('632624', '达日县', '632600', 'QH', null);
INSERT INTO `system_area` VALUES ('632625', '久治县', '632600', 'QH', null);
INSERT INTO `system_area` VALUES ('632626', '玛多县', '632600', 'QH', null);
INSERT INTO `system_area` VALUES ('632700', '玉树藏族自治州', '630000', 'QH', null);
INSERT INTO `system_area` VALUES ('632721', '玉树县', '632700', 'QH', null);
INSERT INTO `system_area` VALUES ('632722', '杂多县', '632700', 'QH', null);
INSERT INTO `system_area` VALUES ('632723', '称多县', '632700', 'QH', null);
INSERT INTO `system_area` VALUES ('632724', '治多县', '632700', 'QH', null);
INSERT INTO `system_area` VALUES ('632725', '囊谦县', '632700', 'QH', null);
INSERT INTO `system_area` VALUES ('632726', '曲麻莱县', '632700', 'QH', null);
INSERT INTO `system_area` VALUES ('632800', '海西蒙古族藏族自治州', '630000', 'QH', null);
INSERT INTO `system_area` VALUES ('632801', '格尔木市', '632800', 'QH', null);
INSERT INTO `system_area` VALUES ('632802', '德令哈市', '632800', 'QH', null);
INSERT INTO `system_area` VALUES ('632821', '乌兰县', '632800', 'QH', null);
INSERT INTO `system_area` VALUES ('632822', '都兰县', '632800', 'QH', null);
INSERT INTO `system_area` VALUES ('632823', '天峻县', '632800', 'QH', null);
INSERT INTO `system_area` VALUES ('640000', '宁夏回族自治区', '0001', 'NX', 'XB');
INSERT INTO `system_area` VALUES ('640100', '银川市', '640000', 'NX', null);
INSERT INTO `system_area` VALUES ('640101', '市辖区', '640100', 'NX', null);
INSERT INTO `system_area` VALUES ('640104', '兴庆区', '640100', 'NX', null);
INSERT INTO `system_area` VALUES ('640105', '西夏区', '640100', 'NX', null);
INSERT INTO `system_area` VALUES ('640106', '金凤区', '640100', 'NX', null);
INSERT INTO `system_area` VALUES ('640121', '永宁县', '640100', 'NX', null);
INSERT INTO `system_area` VALUES ('640122', '贺兰县', '640100', 'NX', null);
INSERT INTO `system_area` VALUES ('640181', '灵武市', '640100', 'NX', null);
INSERT INTO `system_area` VALUES ('640200', '石嘴山市', '640000', 'NX', null);
INSERT INTO `system_area` VALUES ('640201', '市辖区', '640200', 'NX', null);
INSERT INTO `system_area` VALUES ('640202', '大武口区', '640200', 'NX', null);
INSERT INTO `system_area` VALUES ('640205', '惠农区', '640200', 'NX', null);
INSERT INTO `system_area` VALUES ('640221', '平罗县', '640200', 'NX', null);
INSERT INTO `system_area` VALUES ('640300', '吴忠市', '640000', 'NX', null);
INSERT INTO `system_area` VALUES ('640301', '市辖区', '640300', 'NX', null);
INSERT INTO `system_area` VALUES ('640302', '利通区', '640300', 'NX', null);
INSERT INTO `system_area` VALUES ('640323', '盐池县', '640300', 'NX', null);
INSERT INTO `system_area` VALUES ('640324', '同心县', '640300', 'NX', null);
INSERT INTO `system_area` VALUES ('640381', '青铜峡市', '640300', 'NX', null);
INSERT INTO `system_area` VALUES ('640400', '固原市', '640000', 'NX', null);
INSERT INTO `system_area` VALUES ('640401', '市辖区', '640400', 'NX', null);
INSERT INTO `system_area` VALUES ('640402', '原州区', '640400', 'NX', null);
INSERT INTO `system_area` VALUES ('640422', '西吉县', '640400', 'NX', null);
INSERT INTO `system_area` VALUES ('640423', '隆德县', '640400', 'NX', null);
INSERT INTO `system_area` VALUES ('640424', '泾源县', '640400', 'NX', null);
INSERT INTO `system_area` VALUES ('640425', '彭阳县', '640400', 'NX', null);
INSERT INTO `system_area` VALUES ('640500', '中卫市', '640000', 'NX', null);
INSERT INTO `system_area` VALUES ('640501', '市辖区', '640500', 'NX', null);
INSERT INTO `system_area` VALUES ('640502', '沙坡头区', '640500', 'NX', null);
INSERT INTO `system_area` VALUES ('640521', '中宁县', '640500', 'NX', null);
INSERT INTO `system_area` VALUES ('640522', '海原县', '640500', 'NX', null);
INSERT INTO `system_area` VALUES ('650000', '新疆维吾尔自治区', '0001', 'XJ', 'XB');
INSERT INTO `system_area` VALUES ('650100', '乌鲁木齐市', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('650101', '市辖区', '650100', 'XJ', null);
INSERT INTO `system_area` VALUES ('650102', '天山区', '650100', 'XJ', null);
INSERT INTO `system_area` VALUES ('650103', '沙依巴克区', '650100', 'XJ', null);
INSERT INTO `system_area` VALUES ('650104', '新市区', '650100', 'XJ', null);
INSERT INTO `system_area` VALUES ('650105', '水磨沟区', '650100', 'XJ', null);
INSERT INTO `system_area` VALUES ('650106', '头屯河区', '650100', 'XJ', null);
INSERT INTO `system_area` VALUES ('650107', '达坂城区', '650100', 'XJ', null);
INSERT INTO `system_area` VALUES ('650108', '东山区', '650100', 'XJ', null);
INSERT INTO `system_area` VALUES ('650121', '乌鲁木齐县', '650100', 'XJ', null);
INSERT INTO `system_area` VALUES ('650200', '克拉玛依市', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('650201', '市辖区', '650200', 'XJ', null);
INSERT INTO `system_area` VALUES ('650202', '独山子区', '650200', 'XJ', null);
INSERT INTO `system_area` VALUES ('650203', '克拉玛依区', '650200', 'XJ', null);
INSERT INTO `system_area` VALUES ('650204', '白碱滩区', '650200', 'XJ', null);
INSERT INTO `system_area` VALUES ('650205', '乌尔禾区', '650200', 'XJ', null);
INSERT INTO `system_area` VALUES ('652100', '吐鲁番地区', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('652101', '吐鲁番市', '652100', 'XJ', null);
INSERT INTO `system_area` VALUES ('652122', '鄯善县', '652100', 'XJ', null);
INSERT INTO `system_area` VALUES ('652123', '托克逊县', '652100', 'XJ', null);
INSERT INTO `system_area` VALUES ('652200', '哈密地区', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('652201', '哈密市', '652200', 'XJ', null);
INSERT INTO `system_area` VALUES ('652222', '巴里坤哈萨克自治县', '652200', 'XJ', null);
INSERT INTO `system_area` VALUES ('652223', '伊吾县', '652200', 'XJ', null);
INSERT INTO `system_area` VALUES ('652300', '昌吉回族自治州', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('652301', '昌吉市', '652300', 'XJ', null);
INSERT INTO `system_area` VALUES ('652302', '阜康市', '652300', 'XJ', null);
INSERT INTO `system_area` VALUES ('652303', '米泉市', '652300', 'XJ', null);
INSERT INTO `system_area` VALUES ('652323', '呼图壁县', '652300', 'XJ', null);
INSERT INTO `system_area` VALUES ('652324', '玛纳斯县', '652300', 'XJ', null);
INSERT INTO `system_area` VALUES ('652325', '奇台县', '652300', 'XJ', null);
INSERT INTO `system_area` VALUES ('652327', '吉木萨尔县', '652300', 'XJ', null);
INSERT INTO `system_area` VALUES ('652328', '木垒哈萨克自治县', '652300', 'XJ', null);
INSERT INTO `system_area` VALUES ('652700', '博尔塔拉蒙古自治州', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('652701', '博乐市', '652700', 'XJ', null);
INSERT INTO `system_area` VALUES ('652722', '精河县', '652700', 'XJ', null);
INSERT INTO `system_area` VALUES ('652723', '温泉县', '652700', 'XJ', null);
INSERT INTO `system_area` VALUES ('652800', '巴音郭楞蒙古自治州', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('652801', '库尔勒市', '652800', 'XJ', null);
INSERT INTO `system_area` VALUES ('652822', '轮台县', '652800', 'XJ', null);
INSERT INTO `system_area` VALUES ('652823', '尉犁县', '652800', 'XJ', null);
INSERT INTO `system_area` VALUES ('652824', '若羌县', '652800', 'XJ', null);
INSERT INTO `system_area` VALUES ('652825', '且末县', '652800', 'XJ', null);
INSERT INTO `system_area` VALUES ('652826', '焉耆回族自治县', '652800', 'XJ', null);
INSERT INTO `system_area` VALUES ('652827', '和静县', '652800', 'XJ', null);
INSERT INTO `system_area` VALUES ('652828', '和硕县', '652800', 'XJ', null);
INSERT INTO `system_area` VALUES ('652829', '博湖县', '652800', 'XJ', null);
INSERT INTO `system_area` VALUES ('652900', '阿克苏地区', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('652901', '阿克苏市', '652900', 'XJ', null);
INSERT INTO `system_area` VALUES ('652922', '温宿县', '652900', 'XJ', null);
INSERT INTO `system_area` VALUES ('652923', '库车县', '652900', 'XJ', null);
INSERT INTO `system_area` VALUES ('652924', '沙雅县', '652900', 'XJ', null);
INSERT INTO `system_area` VALUES ('652925', '新和县', '652900', 'XJ', null);
INSERT INTO `system_area` VALUES ('652926', '拜城县', '652900', 'XJ', null);
INSERT INTO `system_area` VALUES ('652927', '乌什县', '652900', 'XJ', null);
INSERT INTO `system_area` VALUES ('652928', '阿瓦提县', '652900', 'XJ', null);
INSERT INTO `system_area` VALUES ('652929', '柯坪县', '652900', 'XJ', null);
INSERT INTO `system_area` VALUES ('653000', '克孜勒苏柯尔克孜自治', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('653001', '阿图什市', '653000', 'XJ', null);
INSERT INTO `system_area` VALUES ('653022', '阿克陶县', '653000', 'XJ', null);
INSERT INTO `system_area` VALUES ('653023', '阿合奇县', '653000', 'XJ', null);
INSERT INTO `system_area` VALUES ('653024', '乌恰县', '653000', 'XJ', null);
INSERT INTO `system_area` VALUES ('653100', '喀什地区', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('653101', '喀什市', '653100', 'XJ', null);
INSERT INTO `system_area` VALUES ('653121', '疏附县', '653100', 'XJ', null);
INSERT INTO `system_area` VALUES ('653122', '疏勒县', '653100', 'XJ', null);
INSERT INTO `system_area` VALUES ('653123', '英吉沙县', '653100', 'XJ', null);
INSERT INTO `system_area` VALUES ('653124', '泽普县', '653100', 'XJ', null);
INSERT INTO `system_area` VALUES ('653125', '莎车县', '653100', 'XJ', null);
INSERT INTO `system_area` VALUES ('653126', '叶城县', '653100', 'XJ', null);
INSERT INTO `system_area` VALUES ('653127', '麦盖提县', '653100', 'XJ', null);
INSERT INTO `system_area` VALUES ('653128', '岳普湖县', '653100', 'XJ', null);
INSERT INTO `system_area` VALUES ('653129', '伽师县', '653100', 'XJ', null);
INSERT INTO `system_area` VALUES ('653130', '巴楚县', '653100', 'XJ', null);
INSERT INTO `system_area` VALUES ('653131', '塔什库尔干塔吉克自治', '653100', 'XJ', null);
INSERT INTO `system_area` VALUES ('653200', '和田地区', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('653201', '和田市', '653200', 'XJ', null);
INSERT INTO `system_area` VALUES ('653221', '和田县', '653200', 'XJ', null);
INSERT INTO `system_area` VALUES ('653222', '墨玉县', '653200', 'XJ', null);
INSERT INTO `system_area` VALUES ('653223', '皮山县', '653200', 'XJ', null);
INSERT INTO `system_area` VALUES ('653224', '洛浦县', '653200', 'XJ', null);
INSERT INTO `system_area` VALUES ('653225', '策勒县', '653200', 'XJ', null);
INSERT INTO `system_area` VALUES ('653226', '于田县', '653200', 'XJ', null);
INSERT INTO `system_area` VALUES ('653227', '民丰县', '653200', 'XJ', null);
INSERT INTO `system_area` VALUES ('654000', '伊犁哈萨克自治州', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('654002', '伊宁市', '654000', 'XJ', null);
INSERT INTO `system_area` VALUES ('654003', '奎屯市', '654000', 'XJ', null);
INSERT INTO `system_area` VALUES ('654021', '伊宁县', '654000', 'XJ', null);
INSERT INTO `system_area` VALUES ('654022', '察布查尔锡伯自治县', '654000', 'XJ', null);
INSERT INTO `system_area` VALUES ('654023', '霍城县', '654000', 'XJ', null);
INSERT INTO `system_area` VALUES ('654024', '巩留县', '654000', 'XJ', null);
INSERT INTO `system_area` VALUES ('654025', '新源县', '654000', 'XJ', null);
INSERT INTO `system_area` VALUES ('654026', '昭苏县', '654000', 'XJ', null);
INSERT INTO `system_area` VALUES ('654027', '特克斯县', '654000', 'XJ', null);
INSERT INTO `system_area` VALUES ('654028', '尼勒克县', '654000', 'XJ', null);
INSERT INTO `system_area` VALUES ('654200', '塔城地区', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('654201', '塔城市', '654200', 'XJ', null);
INSERT INTO `system_area` VALUES ('654202', '乌苏市', '654200', 'XJ', null);
INSERT INTO `system_area` VALUES ('654221', '额敏县', '654200', 'XJ', null);
INSERT INTO `system_area` VALUES ('654223', '沙湾县', '654200', 'XJ', null);
INSERT INTO `system_area` VALUES ('654224', '托里县', '654200', 'XJ', null);
INSERT INTO `system_area` VALUES ('654225', '裕民县', '654200', 'XJ', null);
INSERT INTO `system_area` VALUES ('654226', '和布克赛尔蒙古自治县', '654200', 'XJ', null);
INSERT INTO `system_area` VALUES ('654300', '阿勒泰地区', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('654301', '阿勒泰市', '654300', 'XJ', null);
INSERT INTO `system_area` VALUES ('654321', '布尔津县', '654300', 'XJ', null);
INSERT INTO `system_area` VALUES ('654322', '富蕴县', '654300', 'XJ', null);
INSERT INTO `system_area` VALUES ('654323', '福海县', '654300', 'XJ', null);
INSERT INTO `system_area` VALUES ('654324', '哈巴河县', '654300', 'XJ', null);
INSERT INTO `system_area` VALUES ('654325', '青河县', '654300', 'XJ', null);
INSERT INTO `system_area` VALUES ('654326', '吉木乃县', '654300', 'XJ', null);
INSERT INTO `system_area` VALUES ('659000', '省直辖行政单位', '650000', 'XJ', null);
INSERT INTO `system_area` VALUES ('659001', '石河子市', '659000', 'XJ', null);
INSERT INTO `system_area` VALUES ('659002', '阿拉尔市', '659000', 'XJ', null);
INSERT INTO `system_area` VALUES ('659003', '图木舒克市', '659000', 'XJ', null);
INSERT INTO `system_area` VALUES ('659004', '五家渠市', '659000', 'XJ', null);
INSERT INTO `system_area` VALUES ('710000', '台湾', '0001', 'TW', 'GAT');
INSERT INTO `system_area` VALUES ('710100', '市辖区', '710000', 'TW', null);
INSERT INTO `system_area` VALUES ('710200', '台北市', '710000', 'TW', null);
INSERT INTO `system_area` VALUES ('710201', '信义区', '710200', 'TW', null);
INSERT INTO `system_area` VALUES ('710300', '新北市', '710000', 'TW', null);
INSERT INTO `system_area` VALUES ('710301', '板桥区', '710300', 'TW', null);
INSERT INTO `system_area` VALUES ('710400', '桃园市', '710000', 'TW', null);
INSERT INTO `system_area` VALUES ('710401', '桃园区', '710400', 'TW', null);
INSERT INTO `system_area` VALUES ('710500', '台中市', '710000', 'TW', null);
INSERT INTO `system_area` VALUES ('710501', '西屯区', '710500', 'TW', null);
INSERT INTO `system_area` VALUES ('710600', '台南市', '710000', 'TW', null);
INSERT INTO `system_area` VALUES ('710601', '安平区', '710600', 'TW', null);
INSERT INTO `system_area` VALUES ('710602', '新营区', '710600', 'TW', null);
INSERT INTO `system_area` VALUES ('710700', '高雄市', '710000', 'TW', null);
INSERT INTO `system_area` VALUES ('710701', '苓雅区', '710700', 'TW', null);
INSERT INTO `system_area` VALUES ('710702', '凤山区', '710700', 'TW', null);
INSERT INTO `system_area` VALUES ('810000', '香港', '0001', 'HK', 'GAT');
INSERT INTO `system_area` VALUES ('810100', '市辖区', '810000', 'HK', null);
INSERT INTO `system_area` VALUES ('810200', '香港岛', '810000', 'HK', null);
INSERT INTO `system_area` VALUES ('810201', '中西区', '810200', 'HK', null);
INSERT INTO `system_area` VALUES ('810300', '九龙半岛', '810000', 'HK', null);
INSERT INTO `system_area` VALUES ('810301', '油尖旺区', '810300', 'HK', null);
INSERT INTO `system_area` VALUES ('810400', '新界', '810000', 'HK', null);
INSERT INTO `system_area` VALUES ('810401', '北区', '810400', 'HK', null);
INSERT INTO `system_area` VALUES ('910000', '澳门', '0001', 'MO', 'GAT');
INSERT INTO `system_area` VALUES ('910100', '市辖区', '910000', 'MO', null);
INSERT INTO `system_area` VALUES ('910101', '花地玛堂区', '910100', 'MO', null);
INSERT INTO `system_area` VALUES ('910102', '圣安多尼堂区', '910100', 'MO', null);
INSERT INTO `system_area` VALUES ('910103', '大堂区', '910100', 'MO', null);
INSERT INTO `system_area` VALUES ('910104', '望德堂区', '910100', 'MO', null);
INSERT INTO `system_area` VALUES ('910105', '风顺堂区', '910100', 'MO', null);
INSERT INTO `system_area` VALUES ('DB', '东北', '0002', null, null);
INSERT INTO `system_area` VALUES ('GAT', '港澳台', '0002', null, null);
INSERT INTO `system_area` VALUES ('HB', '华北', '0002', null, null);
INSERT INTO `system_area` VALUES ('HD', '华东', '0002', null, null);
INSERT INTO `system_area` VALUES ('HN', '华南', '0002', null, null);
INSERT INTO `system_area` VALUES ('HZ', '华中', '0002', null, null);
INSERT INTO `system_area` VALUES ('XB', '西北', '0002', null, null);
INSERT INTO `system_area` VALUES ('XN', '西南', '0002', null, null);

-- ----------------------------
-- Table structure for system_channel
-- ----------------------------
DROP TABLE IF EXISTS `system_channel`;
CREATE TABLE `system_channel` (
  `CHANNEL_ID` int(20) NOT NULL AUTO_INCREMENT COMMENT '渠道主键',
  `CHANNEL_NAME` varchar(255) DEFAULT NULL COMMENT '渠道名称',
  `HIERARCHY_ID` int(20) DEFAULT NULL COMMENT '体系编号',
  `IS_DELETE` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`CHANNEL_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_channel
-- ----------------------------
INSERT INTO `system_channel` VALUES ('2', '渠道2222gxx', '1', '0');
INSERT INTO `system_channel` VALUES ('3', '渠道3', '2', '0');
INSERT INTO `system_channel` VALUES ('4', '渠道4', '1', '0');
INSERT INTO `system_channel` VALUES ('5', '渠道5', '1', '0');
INSERT INTO `system_channel` VALUES ('6', '渠道6', '2', '0');
INSERT INTO `system_channel` VALUES ('7', '渠道7', '2', '0');
INSERT INTO `system_channel` VALUES ('8', '渠道8', '1', '0');
INSERT INTO `system_channel` VALUES ('9', '渠道9', '1', '0');
INSERT INTO `system_channel` VALUES ('10', '渠道10', '2', '0');
INSERT INTO `system_channel` VALUES ('11', '渠道11', '2', '0');

-- ----------------------------
-- Table structure for system_hierarchy
-- ----------------------------
DROP TABLE IF EXISTS `system_hierarchy`;
CREATE TABLE `system_hierarchy` (
  `HIERARCHY_ID` int(20) NOT NULL AUTO_INCREMENT COMMENT '体系主键',
  `HIERARCHY_NAME` varchar(255) DEFAULT NULL COMMENT '体系名称',
  `HIERARCHY_NICK` varchar(255) DEFAULT NULL COMMENT '体系简写',
  `IS_DELETE` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`HIERARCHY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_hierarchy
-- ----------------------------
INSERT INTO `system_hierarchy` VALUES ('1', '体系1', '体1', '0');
INSERT INTO `system_hierarchy` VALUES ('2', '体系2', '体2', '0');

-- ----------------------------
-- Table structure for system_office
-- ----------------------------
DROP TABLE IF EXISTS `system_office`;
CREATE TABLE `system_office` (
  `OFFICE_ID` int(20) NOT NULL AUTO_INCREMENT COMMENT '办事处编号',
  `OFFICE_NAME` varchar(100) DEFAULT NULL COMMENT '办事处名称',
  `ZONE_ID` int(20) DEFAULT NULL COMMENT '大区编号',
  `IS_DELETE` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`OFFICE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_office
-- ----------------------------

-- ----------------------------
-- Table structure for system_station
-- ----------------------------
DROP TABLE IF EXISTS `system_station`;
CREATE TABLE `system_station` (
  `STATION_ID` int(20) NOT NULL AUTO_INCREMENT COMMENT '岗位编号',
  `STATION_NAME` varchar(40) DEFAULT NULL COMMENT '岗位名称',
  `IS_DELETE` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`STATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_station
-- ----------------------------

-- ----------------------------
-- Table structure for system_station_office
-- ----------------------------
DROP TABLE IF EXISTS `system_station_office`;
CREATE TABLE `system_station_office` (
  `STATION_ID` int(20) NOT NULL COMMENT '岗位编号',
  `OFFICE_ID` int(20) NOT NULL COMMENT '办事处编号',
  PRIMARY KEY (`STATION_ID`,`OFFICE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_station_office
-- ----------------------------

-- ----------------------------
-- Table structure for system_station_terminal
-- ----------------------------
DROP TABLE IF EXISTS `system_station_terminal`;
CREATE TABLE `system_station_terminal` (
  `STATION_ID` int(20) NOT NULL COMMENT '岗位编号',
  `TERMINAL_ID` int(20) NOT NULL COMMENT '终端编号',
  PRIMARY KEY (`STATION_ID`,`TERMINAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_station_terminal
-- ----------------------------

-- ----------------------------
-- Table structure for system_zone
-- ----------------------------
DROP TABLE IF EXISTS `system_zone`;
CREATE TABLE `system_zone` (
  `ZONE_ID` int(20) NOT NULL AUTO_INCREMENT COMMENT '大区编号',
  `ZONE_NAME` varchar(40) DEFAULT NULL COMMENT '大区名称',
  `ZONE_PLAN_STATUS` tinyint(4) DEFAULT '1' COMMENT '大区操作计划状态：0-不可操作 1-可操作',
  `ZONE_FLOW_STATUS` tinyint(4) DEFAULT '1' COMMENT '大区操作流向状态：0-不可操作 1-可操作',
  `IS_DELETE` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`ZONE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_zone
-- ----------------------------
INSERT INTO `system_zone` VALUES ('1', '华东区', '1', '1', '0');
INSERT INTO `system_zone` VALUES ('2', '华北区', '1', '1', '0');
INSERT INTO `system_zone` VALUES ('3', '华南区', '1', '0', '0');

-- ----------------------------
-- Table structure for system_zone_plan
-- ----------------------------
DROP TABLE IF EXISTS `system_zone_plan`;
CREATE TABLE `system_zone_plan` (
  `ZONE_ID` varchar(20) NOT NULL COMMENT '大区编号',
  `PLAN_TYPE` tinyint(4) NOT NULL COMMENT '大区计划类型：0-计划 1-流向',
  `PLAN_START_DAY` int(2) DEFAULT NULL COMMENT '计划开始日期',
  `PLAN_END_DAY` int(2) DEFAULT NULL COMMENT '计划结束日期',
  `PLAN_STATUS` tinyint(4) DEFAULT NULL COMMENT '计划是否启用 0-未启用 1-已启用',
  PRIMARY KEY (`ZONE_ID`,`PLAN_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_zone_plan
-- ----------------------------
INSERT INTO `system_zone_plan` VALUES ('1', '0', '1', '10', '1');
INSERT INTO `system_zone_plan` VALUES ('1', '1', '2', '12', '1');
INSERT INTO `system_zone_plan` VALUES ('2', '0', '10', '20', '1');
INSERT INTO `system_zone_plan` VALUES ('2', '1', '20', '22', '1');

-- ----------------------------
-- Table structure for user_agent
-- ----------------------------
DROP TABLE IF EXISTS `user_agent`;
CREATE TABLE `user_agent` (
  `AGENT_ID` varchar(50) NOT NULL COMMENT '经销商ID',
  `ERP_CODE` varchar(255) DEFAULT NULL COMMENT 'ERP编号',
  `AGENT_NAME` varchar(100) NOT NULL COMMENT '经销商名称',
  `HIERARCHY_ID` varchar(50) NOT NULL COMMENT '体系ID',
  `AGENT_STATUS` varchar(10) NOT NULL COMMENT '状态 0-待开发 1-已开发',
  `IS_VIRTUAL` tinyint(4) DEFAULT '0' COMMENT '是否虚拟 0-否，1-是',
  `PLAN_DATE` date NOT NULL COMMENT '计划加入时间',
  `JOIN_DATE` date NOT NULL COMMENT '经销商加入时间',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '经销商创建时间',
  `IS_DELETE` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`AGENT_ID`),
  KEY `IDX_AGENT_JOIN_DATE` (`JOIN_DATE`) USING BTREE,
  KEY `user_agent_id` (`AGENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='经销商表';

-- ----------------------------
-- Records of user_agent
-- ----------------------------

-- ----------------------------
-- Table structure for user_agent_terminal
-- ----------------------------
DROP TABLE IF EXISTS `user_agent_terminal`;
CREATE TABLE `user_agent_terminal` (
  `AGENT_ID` varchar(50) NOT NULL COMMENT '经销商ID',
  `TERMINAL_ID` varchar(50) NOT NULL COMMENT '终端ID',
  PRIMARY KEY (`AGENT_ID`,`TERMINAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='经销商和终端对应表';

-- ----------------------------
-- Records of user_agent_terminal
-- ----------------------------

-- ----------------------------
-- Table structure for user_terminal
-- ----------------------------
DROP TABLE IF EXISTS `user_terminal`;
CREATE TABLE `user_terminal` (
  `TERMINAL_ID` varchar(50) NOT NULL COMMENT '终端ID',
  `TERMINAL_NAME` varchar(255) DEFAULT NULL COMMENT '终端名称',
  `TERMINAL_TYPE` tinyint(4) NOT NULL COMMENT '终端类型 0-个人 1-非个人',
  `ERP_CODE` varchar(255) DEFAULT NULL COMMENT 'ERP编号',
  `STATION_ID` varchar(50) NOT NULL COMMENT '岗位编号',
  `HIERARCHY_ID` varchar(50) DEFAULT NULL,
  `CHANNEL_ID` varchar(50) NOT NULL COMMENT '终端体系编号',
  `TERMINAL_STATUS` tinyint(4) DEFAULT NULL COMMENT '状态 0-待开发 1-已开发',
  `PLAN_DATE` date NOT NULL COMMENT '计划加入时间',
  `JOIN_DATE` date NOT NULL COMMENT '终端加入时间',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '终端创建时间',
  `TERMINAL_ADDRESS` varchar(300) DEFAULT NULL COMMENT '终端详细地址',
  `TERMINAL_DISTRICT` int(10) DEFAULT NULL COMMENT '终端区',
  `TERMINAL_CITY` int(10) DEFAULT NULL COMMENT '终端市',
  `TERMINAL_PROVINCE` int(10) DEFAULT NULL COMMENT '终端省',
  `PRODUCT_CATEGORY_IDS` varchar(255) DEFAULT NULL COMMENT '产品编号组',
  `IS_DELETE` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`TERMINAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='终端主表';

-- ----------------------------
-- Records of user_terminal
-- ----------------------------
