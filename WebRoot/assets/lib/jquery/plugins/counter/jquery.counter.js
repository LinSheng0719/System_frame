/**
 * animateBackground-plugin
 */
(function($) {
	if (!document.defaultView || !document.defaultView.getComputedStyle) {
		var oldcss = jQuery.css;
		jQuery.css = function(elem, name, force) {
			if (name === 'background-position') {
				name = 'backgroundPosition';
			}
			if (name !== 'backgroundPosition' || !elem.currentStyle
					|| elem.currentStyle[name]) {
				return oldcss.apply(this, arguments);
			}
			var style = elem.style;
			if (!force && style && style[name]) {
				return style[name];
			}
			return oldcss(elem, 'backgroundPositionX', force) + ' '
					+ oldcss(elem, 'backgroundPositionY', force);
		};
	}

	var oldAnim = $.fn.animate;
	$.fn.animate = function(prop) {
		if ('background-position' in prop) {
			prop.backgroundPosition = prop['background-position'];
			delete prop['background-position'];
		}
		if ('backgroundPosition' in prop) {
			prop.backgroundPosition = '(' + prop.backgroundPosition + ')';
		}
		return oldAnim.apply(this, arguments);
	};

	function toArray(strg) {
		strg = strg.replace(/left|top/g, '0px');
		strg = strg.replace(/right|bottom/g, '100%');
		strg = strg.replace(/([0-9\.]+)(\s|\)|$)/g, "$1px$2");
		var res = strg
				.match(/(-?[0-9\.]+)(px|\%|em|pt)\s(-?[0-9\.]+)(px|\%|em|pt)/);
		return [parseFloat(res[1], 10), res[2], parseFloat(res[3], 10), res[4]];
	}

	$.fx.step.backgroundPosition = function(fx) {
		if (!fx.bgPosReady) {
			var start = $.css(fx.elem, 'backgroundPosition');

			if (!start) {//FF2 no inline-style fallback
				start = '0px 0px';
			}

			start = toArray(start);

			fx.start = [start[0], start[2]];

			var end = toArray(fx.end);
			fx.end = [end[0], end[2]];

			fx.unit = [end[1], end[3]];
			fx.bgPosReady = true;
		}

		var nowPosX = [];
		nowPosX[0] = ((fx.end[0] - fx.start[0]) * fx.pos) + fx.start[0]
				+ fx.unit[0];
		nowPosX[1] = ((fx.end[1] - fx.start[1]) * fx.pos) + fx.start[1]
				+ fx.unit[1];
		fx.elem.style.backgroundPosition = nowPosX[0] + ' ' + nowPosX[1];
	};
})(jQuery);
/*!
* jquery.counter.js 1.0
*
* Copyright 2016, 
* Released under the GPL v2 License
*
* Date: Mar 16, 2016
* 
* $('.counter').counter(1000);
* $('.counter').counter(xxx,xxx);
* $('.counter').counter(1000,{
* 	
* });
* 
* 
* 
*/
(function($) {
	"use strict";
	/**
	 * 参数名		参数类型			参数介绍
	 * number		number/String	需要显示的值
	 * options		String/Object	显示的效果（主题）/具体的主题或其他配置
	 */
	$.fn.counter = function(number,options) {
		/* 设置项 */
		var settings = {
			selector:'.counter',
			theme:'default-counter',
			/* 垂直偏移量，该值根据具体样式图片进行设置 */
			y_offset:30,
			/* 数值 */
			number:number
		};
		/* 判断第二个参数 */
		if(typeof options == 'object'){
			settings = $.extend({},settings, options||{});
		}
		/*  */
		return this.each(function(){
			var $this = $(this);
			/* 设置样式 */
			if(!$this.hasClass(settings.theme)){
				$this.addClass(settings.theme);
			}
			/* 获取i元素及新值 */
			var i_tags = $this.find('i'),i_tags_ln = i_tags.length,n_num = settings.number+'',n_num_ln=n_num.length;
			
			/* 补全 i */
			if(n_num_ln > i_tags_ln){
				for(var i=0,ln=n_num_ln-i_tags_ln;i<ln;i++){
					$this.prepend('<i></i>');
				}
			}else if(n_num_ln < i_tags_ln){
				/* 设置新的值，在新的值前面补0 */
				for(var i=0,ln=i_tags_ln-n_num_ln;i<ln;i++){
					n_num = '0'+n_num;
				}
			}
			/* 动画 */
			for(var i=0,ln=n_num.length;i<ln;i++){
		        var num = n_num.charAt(ln-i-1);
		        var y = -parseInt(num)*settings.y_offset;
		        var obj = $this.find('i').eq(ln-i-1);
		        obj.attr('data-number',num);
		        obj.animate({ //滚动动画 
		            backgroundPosition :'(0 '+String(y)+'px)'  
		            }, 'slow','swing',function(){
		        });
			}
			/* 移除0 */
			if(!settings.removeZero){
				$this.find('i').each(function(){
					if($(this).attr('data-number') == '0'){
						$(this).remove();
					}else{
						return false;
					}
				});
			}
		});
	};
})(jQuery);