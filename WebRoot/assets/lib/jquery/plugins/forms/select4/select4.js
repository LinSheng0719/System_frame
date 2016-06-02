/*
Copyright 2015 LinCH

Version: 1.0.1 Timestamp: 2015-04-06 15:11:26

This software is licensed under the Apache License, Version 2.0 (the "Apache License") or the GNU
General Public License version 2 (the "GPL License"). You may choose either license to govern your
use of this software only upon the condition that you accept all of the terms of either the Apache
License or the GPL License.

You may obtain a copy of the Apache License and the GPL License at:

    http://www.apache.org/licenses/LICENSE-2.0
    http://www.gnu.org/licenses/gpl-2.0.html

Unless required by applicable law or agreed to in writing, software distributed under the
Apache License or the GPL License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the Apache License and the GPL License for
the specific language governing permissions and limitations under the Apache License and the GPL License.
*/
;(function($, window, document,undefined){
	"use strict";
	/**
	 * 下拉框对象
	 */
	var Select = function(element,options){
		this.version = '201512101107';
		this.$element = element,
		this.defaults = {},
		this.options = $.extend({},this.defaults,options);
	}
	/**
	 * 告警打印
	 */
	var warn = function(method,content){
		var html = new Array();
		html.push('Select4 method '+method+' : ');
		html.push(content);
		console.warn(html.join(''));
	}
	
	/**
	 * 根据html5的要求，自己扩展的属性都以data-开头，进行统一的规范
	 */
	Select.prototype = {
		/**
		 * 方法名：下拉框回填
		 * 参数　：data-value[默认值]
		 * 示例　：
		 *  html code:
		 * 	<select class="select4" name="" data-value="2">
		 * 		<option value="1">选项1</option>
		 * 		<option value="2">选项2</option>
		 *  <select>
		 *  javascript code:(must be include 'JQuery lib')
		 *  $('.select4').select4backfill();
		 */
		backfill:function(){
			this.$element.each(function(i){
				var defaultValue,me = $(this);
				if(me.data('value')+'' && me.data('value') != ''){
					defaultValue = me.data('value');
					me.find('option').each(function(i){
						if($(this).val() == defaultValue){
							me.get(0).selectedIndex = i;
							me.get(0).options[i].selected = true;  
						}
					});
				}
			});
		},
		ajax:function(){
			
			var _self = this;
			
			/* 定义必要的参数 */
			var paramNames = ['display-text','value-field','url'],id;
			
			this.$element.each(function(i){
				var me = $(this);
				/* 为select设置id */
				if(!$(this).attr('id')){
					$(this).attr('id','select4ajax-'+i+(new Date().getTime()));
				}
				var id = $(this).attr('id');
				/**
				 * 循环判断需要的参数是否存在
				 */
				var _paramNames = [];
				$.each(paramNames,function(index,o){
					if( !me.data(o) || me.data(o) ==''){
						_paramNames.push(o);
					}
				});
				if(_paramNames.length>0){
					$.each(_paramNames,function(index,o){
						_paramNames[index] = ('data-'+o);
					});
					warn('ajax','['+i+'] missing parameters [ '+_paramNames.join(',')+' ] !');
					return true;
				}
				/**
				 * 对特殊的参数进行判断
				 */
				/* 交互参数 */
				var params = {};
				if(me.data('params') && me.data('params') !=''){
					var _params = eval("("+me.data('params')+")");
					for(var name in _params){
						if(_params[name].id){
							params[name] = $('#'+_params[name].id).length==1?($('#'+_params[name].id).val()==null&&_params[name].empty?_params[name].empty:$('#'+_params[name].id).val()):'';
						}
						if(_params[name].value){
							params[name] = _params[name].value;
						}
					}
				}
				
				/**
				 * 执行交互动作
				 */
				$.post(
					me.data('url'),
					params,
					function(data, textStatus, jqXHR){
						if(!data.data)data.data = [];
						/* 获得select对象 */
						var _select = $('#'+id),_options=[];
						/* 清空下拉框 */
						_select.empty();
						/* 定义一个默认的空分组 */
						var optgroup = [{
							label:null,
							options:[]
						}];
						/* 判断是否存在额外的附加选项的定义 */
						if(_select.data('options') && _select.data('options')!=null){
							var _opts = eval("("+me.data('options')+")");
							if(_opts){
								$.each(_opts,function(_oi,_op){
									_op._text = _op.text;
									if(_op.label){
										$.each(optgroup,function(_ogi,_ogo){
											if(_ogo.label == _op.label){
												_ogo.options.push(_op);
											}
										});
										
									}else{
										optgroup[0].options.push(_op);
									}
								});
							}
						}
						
						/* 设置返回的结果 */
						var displayText = _select.data("display-text")+"",replaceTexts = displayText.match(/\{(.*?)\}/gi);
						var valueField = _select.data("value-field")+"",repaceValues = valueField.match(/\{(.*?)\}/gi)!=null?(valueField.match(/\{(.*?)\}/gi)):(function(){
							valueField = '{'+valueField+'}';
							return [valueField];
						})();
						/* 设置显示样式 */
						if(data.data){
							$.each(data.data,function(_oi,_op){
								/* 设置显示效果 */
								_op._text = displayText;
								/* 设置默认值 */
								if(_select.data("value") && _select.data("value")!=""){
									if(_op[_select.data("value-field")] == _select.data("value")){
										_op._selected = "selected";
									}
								}
								if(replaceTexts!=null)
									$.each(replaceTexts,function(_ri,_ro){
										_op._text = _op._text.replace(new RegExp(_ro, 'g'),_op[_ro.substring(1,_ro.length-1)]||'');
									});
							});
						}
						
						/* 判断是否设置了分组的关键字，根据关键字进行拓展分组信息 */
						if(_select.data('optgroup-field') && _select.data('optgroup-field')!=null){
							//$.inArray("js", arr);
							var optgroups = [];
							$.each(data.data,function(_i,_o){
								/* 判断分组是否被定义过了 */
								var optgroupField = _o[_select.data('optgroup-field')];
								var index = $.inArray(optgroupField, optgroups);
								if(index ==-1){
									if(optgroupField){
										optgroups.push(_o[_select.data('optgroup-field')]);
										/* 新建分组对象 */
										optgroup.push({
											label:_o[_select.data('optgroup-field')],
											options:[_o]
										});
									}else{
										optgroup[0].options.push(_o);
									}
								}else{
									optgroup[index+1].options.push(_o);
								}
							});
						}else{
							$.each(data.data,function(_i,_o){
								optgroup[0].options.push(_o);
							});
						}
						/* 生成内容 */
						var html = new Array();
						$.each(optgroup,function(_ogi,_ogo){
							/* */
							if(_ogo.label!=null){
								html.push('<optgroup label="'+_ogo.label+'">');
							}
							
							$.each(_ogo.options,function(_ooi,_ooo){
								
								var arr = [],_value = valueField;
								for(var vi in repaceValues){
									var key = repaceValues[vi];
									_value = _value.replace(new RegExp(key, 'g'),_ooo[key.substring(1,key.length-1)]);
								}
								html.push('<option value="'+_value+'" '+(_ooo._selected ? "selected":"")+' >');
								html.push(_ooo._text);
								html.push('</option>');
							});
							/* */
							if(_ogo.label!=null){
								html.push('</optgroup>');
							}
							_select.html(html.join(''));
						});
						/* 回调函数 */
						if(_select.data('callback') && _select.data('callback')!=''){
							if(typeof _self.options[_select.data('callback')] == 'function'){
								_self.options[_select.data('callback')](_select,params,data);
							}
						}
					},
					'json'
				).error(function(e) {
			    	if(e.status == 404){
			    		warn('ajax','data-url [ '+me.data('url')+' ] no found !');
			    	}else if(e.status == 500){
			    		warn('ajax','data-url [ '+me.data('url')+' ] internal server error !');
			    	}else{
			    		warn('ajax','data-url [ '+me.data('url')+' ] '+e.status);
			    	}
			    });
			});
		}
	}
	/**
	 * 回填
	 */
	$.fn.select4backfill = function(options){
		var select = new Select(this,options);
		return select.backfill();
	}
	$.fn.select4ajax = function(options){
		var select = new Select(this,options);
		return select.ajax();
	}
})(jQuery, window, document);