(function() {
	$.extend($.fn, {
		
		mask : function(setting) {
			/* 用于修改默认的显示文本 */
			if(typeof setting == 'string'){
				var $mask = this.find("> div.codeapes-mask-layer");
				/* 判断遮罩层是否已经存在 */
				if($mask.length == 1){
					$mask.find('div.codeapes-mask-layer-text').html(setting);
					return $mask;
				}else{
					/* 不存在就设置默认设置显示的值 */
					var text = setting;
					setting = {body:text};
				}
			}
			
			/* 判断参数类型 */
			this.unmask();
			var target = this;
			/**/
			var options = $.extend(true,{
				css:{
					'z-index':10000,
					'background-color':'#fff'
				},
				animation:function($mask_layer_div){
					$mask_layer_div.fadeIn('fast', function(){$(this).fadeTo('slow', 0.8);})
				},
				plugins:{
					actual:false
				}
			},setting);
			
			/* 定义遮罩 div */
			var $mask_layer_div = $('<div class="codeapes-mask-layer"></div>');
			/* 获得坐标 */
			var position = {
				top : 0,
				left : 0
			};
			/* 覆盖位置 */
			position = $mask_layer_div.position();
			/* 把遮罩层追加至目标对象之后 */
			$mask_layer_div.appendTo(target);
			/* 获得外宽及外高 */
			var mask_layer_div_width = (function() {
				/* 判断是否启用了第三方插件获取外框 */
				if (options.plugins.actual) {
					return target.actual('outerWidth');
				} else {
					if (!target.outerWidth()) {
						return target.width();
					} else
						return target.outerWidth();
				}
			})();
			var mask_layer_div_height = (function() {
				/* 判断是否启用了第三方插件获取外框 */
				if (options.plugins.actual) {
					return target.actual('outerHeight');
				} else {
					if (!target.outerHeight()) {
						return target.height();
					} else
						return target.outerHeight();
				}
			})();
			var css = $.extend(true, {
						position : 'absolute',
						top : position.top,
						left : position.left,
						width : mask_layer_div_width,
						height : mask_layer_div_height,
						opacity : 0
					}, options.css);
			$mask_layer_div.css(css);
			if (options.cls) {
				$mask_layer_div.addClass(options.cls);
			}
			
			/**
			 * 构造文本内容
			 */
			function buildTextBody(text){
				var $msg_box = $('<div style="position:absolute;border:#6593cf 1px solid; padding:2px;background:#ccca"><div style="line-height:24px;border:#a3bad9 1px solid;background:white;padding:2px 10px 2px 10px;width:150px;" class="codeapes-mask-layer-text">'+text+'</div></div>');
				$msg_box.appendTo($mask_layer_div);
				var widthspace = ($mask_layer_div.width() - $msg_box.width());
				var heightspace = ($mask_layer_div.height() - $msg_box.height());
				$msg_box.css({
					cursor : 'wait',
					top : (heightspace / 2 - 2),
					left : (widthspace / 2 - 2)
				});
			}
			
			/* 判断有没有第三方 */
			if(typeof options.body == 'string'){
				buildTextBody(options.body);
			}
			if(typeof options.body == 'object'){
				if(!options.body.type){
					options.body.type = 'text';
				}
				if('text' == options.body.type){
					buildTextBody(options.body.text||'missing "text" property.');
				}
				if('html' == options.body.type){
					if(!options.body.html){
						options.body.html = '<div>missing "html" property.</div>';
					}
					$(options.body.html).appendTo($mask_layer_div);
				}
			}
			if(typeof options.body == 'function'){
				options.body(target,$mask_layer_div);
			}
			options.animation($mask_layer_div);
			return $mask_layer_div;
		},
		unmask : function() {
			this.find("> div.codeapes-mask-layer").fadeOut('slow', 0, function() {$(this).remove();});
		}
	});
})();