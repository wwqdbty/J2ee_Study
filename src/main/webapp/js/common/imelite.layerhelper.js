//TODO 需要依赖layer和common.js才可以工作, 是否应该放在plugin呢?
var openIframes = {};

imelite.openIframeLayer = function(flag, title, content, width, height) {

	layer.open({
		type : 2,
		title : title,
		fix : false,
		maxmin : true,
		content : content,
		area : [width + "px", height + "px"],
		success : function(layero, index) {
			// 保存弹出层信息
			openIframes[flag] = {
				containerId : layero.attr("id"),
				index : index
			};
//			console.log(openIframes);
		},
		end : function() {
			// 移除缓存数据
			openIframes[flag] = {};
		}
	});
};

imelite.closeIframeLayer = function(windowName) {
	var index = layer.getFrameIndex(windowName);
	layer.close(index);
};

imelite.onCallback = function(toFlag, param, method) {
	var layerContainer = $("#" + openIframes[toFlag].containerId);
	//得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	var layerIframe = window[layerContainer.find('iframe')[0]['name']];
	layerIframe[method](param);
};
