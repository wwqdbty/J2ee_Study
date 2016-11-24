//TODO 需要依赖ztree才可以工作, 是否应该放在plugin呢?
(
	function() {
		var zTreeHelper = {
			// tree容器配置
			containerSettings : {
				"belongsContainerId" : "div_container",
				"immediateContainerId" : "ul_treeContainer"
			}, 
		
			/**
			 * 异步返回数据预处理
			 *
			 * @param strTreeId 节点所在Tree的ID
			 * @param jsCurrentNode 当前点击的节点
			 * @param jsChildNodes 异步获取的当前节点子节点
			 *
			 * @return 处理后的当前结点的子节点数据
			 */
			filter : function (strTreeId, jsCurrentNode, jsChildNodes) {
//				console.log(JSON.stringify(jsChildNodes));
				 //console.log("filter");
				if (!jsChildNodes) {
					return null;
				}
				for (var i = 0; i < jsChildNodes.length; i++) {
					jsChildNodes[i].name = jsChildNodes[i].name.replace(/\.n/g, '.');
				}
				return jsChildNodes;
			},

			/**
			 * 节点展开前的事件回掉函数
			 *
			 * @param asynFlag 是否异步加载 - 默认true
			 * @param strTreeId 节点所在Tree的ID
			 * @param jsCurrentNode 当前点击的节点
			 *
			 * @return 是否展开节点
			 */
			beforeExpand : function (asynFlag, strTreeId, jsCurrentNode) {
				//console.log("beforeExpand");
				if (!jsCurrentNode || !asynFlag) {
					return true;
				}
				if (!jsCurrentNode.isAjaxing) {
					zTreeHelper.ajaxGetNodes(jsCurrentNode, "refresh");
					return true;
				} else {
					alert("正在下载数据中，请稍后展开节点。。。");
					return false;
				}
			}, 

			/**
			 * 节点异步加载正常结束的事件回调函数
			 *
			 * @param obEvent 事件对象
			 * @param strTreeId 节点所在Tree的ID
			 * @param jsCurrentNode 当前点击的节点
			 * @param strNodeContent 异步获取的节点数据字符串表示形式
			 * @param strSelNodesIds 需选中的节点IDs字符串表示形式, 多个使用","分割
			 *
			 * @return 是否展开节点
			 */
			onAsyncSuccess : function (obEvent, strTreeId, jsCurrentNode, strNodeContent, strSelNodesIds) {
				// console.log("onAsyncSuccess");
				// 设置默认选中
				zTreeHelper.setDefaultSelNodes(strSelNodesIds);
				
				if (!jsCurrentNode) {
					return;
				}
				if (!strNodeContent || strNodeContent.length == 0) {
					return;
				}
				var zTree = $.fn.zTree.getZTreeObj(strTreeId);

				jsCurrentNode.icon = "";
				zTree.updateNode(jsCurrentNode);
				zTree.selectNode(jsCurrentNode.children[0]);
			}, 

			/**
			 * 设置默认选中节点, 异步加载时的默认选中未实现, 后期可根据子父ID规则进行递归打开节点 
			 */
			setDefaultSelNodes : function (strSelNodesIds) {
				if (strSelNodesIds == undefined) {
					return;
				}
				var zTree = $.fn.zTree.getZTreeObj(zTreeHelper.containerSettings.immediateContainerId);
				var strArrSelNode = strSelNodesIds.split(",");
				var jsArrSelNode = new Array();
				for (var i=0; i<strArrSelNode.length; i++) {
					var strSelNodeId = strArrSelNode[i];
					jsArrSelNode = jsArrSelNode.concat(zTree.getNodesByParam("id", strSelNodeId));
				}
				for (var i=0; i<jsArrSelNode.length; i++) {
					zTree.selectNode(jsArrSelNode[i]);
				}
			}, 
			
			onAsyncError : function (event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
				//console.log("onAsyncError");
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				alert("异步获取数据出现异常。");
				treeNode.icon = "";
				zTree.updateNode(treeNode);
			}, 

			/**
			 * 获取节点数据并强制刷新
			 *
			 * @param jsCurrentNode 当前点击的节点
			 * @param strReloadType 刷新方式
			 *
			 */
			ajaxGetNodes : function (jsCurrentNode, strReloadType) {
				//console.log("ajaxGetNodes");
				var zTree = $.fn.zTree.getZTreeObj("ul_treeContainer");
				if (strReloadType == "refresh") {
					jsCurrentNode.icon = "css/zTreeStyle/img/loading.gif";
					zTree.updateNode(jsCurrentNode);
				}
				zTree.reAsyncChildNodes(jsCurrentNode, strReloadType, true);
			}, 

			/**
			 * 单击事件之前预处理, 根据返回值确定是否处理单击事件
			 *
			 * @param strTreeId treeId
			 * @param jsCurrentNode 当前被操作的节点
			 *
			 * @return 是否处理单击事件
			 */
			beforeClick : function (strTreeId, jsCurrentNode) {
				var check = (jsCurrentNode && !jsCurrentNode.isParent);
				// if (!check)
					// alert("只能选择城市..."); check;
			}, 

			/**
			 * 单击事件处理
			 *
			 * @param obEvent 事件对象
			 * @param strTreeId treeId
			 * @param jsCurrentNode 当前被操作的节点
			 * @param domBerth 停靠至元素
			 * @param fnSelNodeCallBack 单击节点后的自定义回调函数
			 *
			 * @return 是否处理单击事件
			 */
			onClick : function (obEvent, strTreeId, jsCurrentNode, domBerth, fnSelNodeCallBack) {
				var zTree = $.fn.zTree.getZTreeObj(strTreeId);
				var jsSelectedNodes = zTree.getSelectedNodes();
				var strNodeNameValues = "";
				var strNodeIdValues = "";
				jsSelectedNodes.sort(function compare(a, b) {
					return a.id - b.id;
				});
				for (var i = 0; i < jsSelectedNodes.length; i++) {
					strNodeNameValues += jsSelectedNodes[i].name + ",";
					strNodeIdValues += jsSelectedNodes[i].id + ",";
				}
				if (strNodeNameValues.length > 0) {
					strNodeNameValues = strNodeNameValues.substring(0, strNodeNameValues.length - 1);
					strNodeIdValues = strNodeIdValues.substring(0, strNodeIdValues.length - 1);
				}
				//IE下不被支持
				// domBerth.attr("value", strNodeNameValues);
				domBerth.val(strNodeNameValues);
				domBerth.attr("selIds", strNodeIdValues);
				
				if (fnSelNodeCallBack != undefined) {
					fnSelNodeCallBack(jsCurrentNode, jsSelectedNodes);
				}
			}, 

			/**
			 * 显示下拉Tree
			 *
			 * @param strBusTypeId 业务类型ID
			 * @param domBerth 停靠至元素
			 * @param strSelNodesIds 默认选中节点NodeIds
			 * @param asynFlag 是否异步加载 - 默认false
			 * @param fnSelNodeCallBack 单击节点后的自定义回调函数
			 */
			showXlTree : function (strBusType, domBerth, jsonSettings) {
				if (jsonSettings == undefined) {
					jsonSettings = {};
				}
				var strSelNodesIds = jsonSettings.selNodeId;
				// 单击节点后的自定义回调函数
				var fnSelNodeCallBack = jsonSettings.callback;
				// 是否异步加载 - 默认false
				var asynFlag = jsonSettings.asynFlag;
				// 其他参数
				var jsonRequestParam = jsonSettings.requestParam;
				
				var	jsTreeSettings = {
					async : {
						// 设置 开启异步加载模式
						enable : true,
						// 异步加载AJAX获取数据URL
						url : function(strTreeId, jsCurrentNode) {
							return zTreeHelper.getUrl(strBusType, jsonRequestParam, strTreeId, jsCurrentNode);
						},
						// 异步加载时提交的父节点属性的动态参数
						autoParam : ["id"],
						// 异步加载时提交的其他静态参数
						otherParam : {
							"xxx" : "xxx"
						},
						// 异步返回数据进行预处理的函数
						dataFilter : zTreeHelper.filter
					},
					data: {
						simpleData: {
							// 使用简单数据类型
							enable : true, 
							pIdKey : "pid"
						}
					},
					callback : {
						// 节点展开前的 事件回掉函数
						beforeExpand : function(strTreeId, jsCurrentNode) {
							return zTreeHelper.beforeExpand(asynFlag, strTreeId, jsCurrentNode);
						},
						// 异步加载正常结束的事件回调函数
						onAsyncSuccess : function (obEvent, strTreeId, jsCurrentNode, strNodeContent) {
							zTreeHelper.onAsyncSuccess(obEvent, strTreeId, jsCurrentNode, strNodeContent, strSelNodesIds);
						},
						// 异步加载出现异常错误的事件回调函数
						onAsyncError : zTreeHelper.onAsyncError,
						// 节点单击事件之前预处理, 根据返回值确定是否处理单击事件
						beforeClick : zTreeHelper.beforeClick,
						// 节点单击事件处理
						onClick : function (obEvent, strTreeId, jsCurrentNode) {
							zTreeHelper.onClick(obEvent, strTreeId, jsCurrentNode, domBerth, fnSelNodeCallBack);
						}
					}
				};
				zTreeHelper.containerSettings.width = domBerth.css("width").substring(0, domBerth.css("width").length - 2);
				// 初始化Tree所属容器及直属容器
				var strContainerHTML = zTreeHelper.initContainer();
				// 显示Tree
				zTreeHelper.excuteShow(strContainerHTML, jsTreeSettings, domBerth);
				// 绑定相关事件
				zTreeHelper.bindEvent(domBerth);
			}, 

			/**
			 * 根据业务类型获取数据URL
			 *
			 * @param strBusType 业务类型
			 * @param strTreeId treeId
			 * @param jsCurrentNode 当前被操作的节点
			 *
			 * @return 是否处理单击事件
			 */
			getUrl : function (strBusType, jsonRequestParam, strTreeId, jsCurrentNode) {
				//console.log("getUrl");
				var url = "";

				if (strBusType == imelite.enumBusType.all) {
					// url = "category.json";
					url = "/manage/course/ajax_category_tree.do?1=1";
					
				} else {
					url = "/manage/course/ajax_category_tree.do?type=" + strBusType;
				}

				if (jsonRequestParam != undefined) {
					for (var key in jsonRequestParam) {
						url += "&" + key + "=" + jsonRequestParam[key];
					}
				}
				return url;
			}, 

			/**
			 * 初始化显示容器HTML
			 *
			 * @return Tree容器HTML
			 */
			initContainer : function () {
				var strContainerHTML = "<div id='" + zTreeHelper.containerSettings.belongsContainerId + "' class='menuContent' style='display:none; position: absolute;'>" + "<ul id='" + zTreeHelper.containerSettings.immediateContainerId + "' class='ztree' style='margin-top:0; width:" + zTreeHelper.containerSettings.width + "px;'></ul>" + "</div>";
				return strContainerHTML;
			}, 

			/**
			 * 执行树显示
			 *
			 * @param strContainerHTML 容器html
			 * @param jsTreeSettings 树配置
			 * @param domBerthOrWrap 停靠至元素
			 */
			excuteShow : function (strContainerHTML, jsTreeSettings, domBerth) {
				$("body").append(strContainerHTML);
				var domImmediateContainer = $("#" + zTreeHelper.containerSettings.immediateContainerId);
				// 初始化树
				$.fn.zTree.init(domImmediateContainer, jsTreeSettings);
				
				// 计算 tree所属容器显示位置
				var obOffset = domBerth.offset();
				var iOffsetLeft = obOffset.left;
				var iOffsetTop = obOffset.top;
				var iOuterHeight = domBerth.outerHeight();
				iOffsetTop = iOffsetTop + iOuterHeight;

				$("#" + zTreeHelper.containerSettings.belongsContainerId).css({
					left : iOffsetLeft + "px",
					top : iOffsetTop + "px",
					zIndex : 99999
				}).slideDown("fast");
			}, 

			hideContainer : function () {
				$("#" + zTreeHelper.containerSettings.belongsContainerId).fadeOut("fast");
				$("body").unbind("mousedown", zTreeHelper.onBodyDown);
			}, 

			onBodyDown : function (event, domBerth) {
				// 触发事件对象时tree所属容器相关对象, 则不处理事件
				if (event.target.id != zTreeHelper.containerSettings.belongsContainerId && event.target.id != domBerth.attr("id") && $(event.target).parents("#" + zTreeHelper.containerSettings.belongsContainerId).length == 0) {
					zTreeHelper.hideContainer();
				}
			}, 

			bindEvent : function (domBerth) {
				$("body").bind("mousedown", function(event) {
					zTreeHelper.onBodyDown(event, domBerth);
				});
			}, 
			
			/**
			 * 显示常规Tree
			 *
			 * @param strBusTypeId 业务类型ID
			 * @param domWrap 包含至容器
			 * @param jsonSettings 其他配置项 
			 */
			showNormalTree : function (strBusType, domWrap, jsonSettings) {
				if (jsonSettings == undefined) {
					jsonSettings = {};
				}
				// 默认选中节点NodeId
				var strSelNodesIds = jsonSettings.selNodeId;
				// 单击节点后的自定义回调函数
				var fnSelNodeCallBack = jsonSettings.callback;
				// 是否异步加载 - 默认false
				var asynFlag = jsonSettings.asynFlag;
				// 其他参数
				var jsonRequestParam = jsonSettings.requestParam;
				
				var	jsTreeSettings = {
					async : {
						// 设置 开启异步加载模式
						enable : true,
						// 异步加载AJAX获取数据URL
						url : function(strTreeId, jsCurrentNode) {
							return zTreeHelper.getUrl(strBusType, jsonRequestParam, strTreeId, jsCurrentNode);
						},
						// 异步加载时提交的父节点属性的动态参数
						autoParam : ["id"],
						// 异步加载时提交的其他静态参数
						otherParam : {
							"xxx" : "xxx"
						},
						// 异步返回数据进行预处理的函数
						dataFilter : zTreeHelper.filter
					},
					data: {
						simpleData: {
							// 使用简单数据类型
							enable : true, 
							pIdKey : "pid"
						}
					},
					callback : {
						// 节点展开前的 事件回掉函数
						beforeExpand : function(strTreeId, jsCurrentNode) {
							return zTreeHelper.beforeExpand(asynFlag, strTreeId, jsCurrentNode);
						},
						// 异步加载正常结束的事件回调函数
						onAsyncSuccess : function (obEvent, strTreeId, jsCurrentNode, strNodeContent) {
							zTreeHelper.onAsyncSuccess(obEvent, strTreeId, jsCurrentNode, strNodeContent, strSelNodesIds);
						},
						// 异步加载出现异常错误的事件回调函数
						onAsyncError : zTreeHelper.onAsyncError,
						// 节点单击事件之前预处理, 根据返回值确定是否处理单击事件
						beforeClick : zTreeHelper.beforeClick,
						// 节点单击事件处理
						onClick : function (obEvent, strTreeId, jsCurrentNode) {
							zTreeHelper.onClick(obEvent, strTreeId, jsCurrentNode, domWrap, fnSelNodeCallBack);
						}
					}
				};
				// 显示Tree
				$.fn.zTree.init(domWrap, jsTreeSettings);
			}
		};
		imelite.showXlTree = zTreeHelper.showXlTree;
		imelite.showNormalTree = zTreeHelper.showNormalTree;
	}()
);
