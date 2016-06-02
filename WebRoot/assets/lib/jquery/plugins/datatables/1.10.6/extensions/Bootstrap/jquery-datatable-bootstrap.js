$.extend(true, $.fn.dataTable.defaults, {
//	"sDom" : "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'p i>>",
	//"sDom": "<'dt-toolbar'<''T>r><'table-responsive't><'row'<lp i>>",
	"sDom":'<"datatable-header"lT><"datatable-scroll"t><"datatable-footer"ip>',
	"destroy": true,
	"scrollCollapse": true,
	"sPaginationType" : "bootstrap",
	"oLanguage" : {
		"sLengthMenu" : "_MENU_",
		"sInfo": "显示 <b>_START_</b> 至 <b>_END_</b> 共  _PAGES_ 页 _TOTAL_ 条记录",
		"sEmptyTable":"暂无数据",
		"sZeroRecords":"暂无数据",
		"sInfoEmpty":"显示 <b>0</b> 至 <b>0</b> 共  0 页0 条记录"
	},
        drawCallback: function () {
            $(this).find('tbody tr').slice(-3).find('.dropdown, .btn-group').addClass('dropup');
        },
        preDrawCallback: function() {
            $(this).find('tbody tr').slice(-3).find('.dropdown, .btn-group').removeClass('dropup');
        }
});
$.extend($.fn.dataTableExt.oStdClasses, {
	"sWrapper" : "dataTables_wrapper form-inline"
});
$.fn.dataTableExt.oApi.fnPagingInfo = function(oSettings) {
	return {
		"iStart" : oSettings._iDisplayStart,
		"iEnd" : oSettings.fnDisplayEnd(),
		"iLength" : oSettings._iDisplayLength,
		"iTotal" : oSettings.fnRecordsTotal(),
		"iFilteredTotal" : oSettings.fnRecordsDisplay(),
		"iPage" : oSettings._iDisplayLength === -1 ? 0 : Math
				.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength),
		"iTotalPages" : oSettings._iDisplayLength === -1 ? 0 : Math
				.ceil(oSettings.fnRecordsDisplay() / oSettings._iDisplayLength)
	};
};
$
		.extend(
				$.fn.dataTableExt.oPagination,
				{
					"bootstrap" : {
						"fnInit" : function(oSettings, nPaging, fnDraw) {
							var oLang = oSettings.oLanguage.oPaginate;
							var fnClickHandler = function(e) {
								e.preventDefault();
								if (oSettings.oApi._fnPageChange(oSettings,
										e.data.action)) {
									fnDraw(oSettings);
								}
							};
							$(nPaging)
									.addClass('pagination')
									.append(
											'<ul>'
													+ '<li class="first disabled"><a href="#"><i class="fa fa-step-backward"></i></a></li>'/* 添加首页 */
													+ '<li class="prev disabled"><a href="#"><i class="fa fa-chevron-left"></i></a></li>'
													+ '<li class="next disabled"><a href="#"><i class="fa fa-chevron-right"></i></a></li>'
													+ '<li class="last disabled"><a href="#"><i class="fa fa-step-forward"></i></a></li>'/* 添加尾页 */
													+ '</ul>');
							var els = $('a', nPaging);
							/* 添加首页 */
							$(els[0]).bind('click.DT', { action: "first" }, fnClickHandler );
							$(els[1]).bind('click.DT', {action : "previous"}, fnClickHandler);
							$(els[2]).bind('click.DT', {action : "next"}, fnClickHandler);
							/* 添加尾页 */
							$(els[3]).bind('click.DT', {action : "last"}, fnClickHandler);
						},
						"fnUpdate" : function(oSettings, fnDraw) {
							var iListLength = 5;
							var oPaging = oSettings.oInstance.fnPagingInfo();
							var an = oSettings.aanFeatures.p;
							var i, ien, j, sClass, iStart, iEnd, iHalf = Math
									.floor(iListLength / 2);
							if (oPaging.iTotalPages < iListLength) {
								iStart = 1;
								iEnd = oPaging.iTotalPages;
							} else if (oPaging.iPage <= iHalf) {
								iStart = 1;
								iEnd = iListLength;
							} else if (oPaging.iPage >= (oPaging.iTotalPages - iHalf)) {
								iStart = oPaging.iTotalPages - iListLength + 1;
								iEnd = oPaging.iTotalPages;
							} else {
								iStart = oPaging.iPage - iHalf + 1;
								iEnd = iStart + iListLength - 1;
							}
							for (i = 0, ien = an.length; i < ien; i++) {
								$('li:gt(1)', an[i]).filter(':lt(-2)').remove();/* 此处修改 $('li:gt(0)', an[i]).filter(':not(:last)').remove();*/
								for (j = iStart; j <= iEnd; j++) {
									sClass = (j == oPaging.iPage + 1) ? 'class="active"': '';
									$('<li ' + sClass + '><a href="#">'+ j + '</a></li>').insertBefore(
											$('li:eq(-2)', an[i])[0]/*$('li:last', an[i])[0]*/
										)
											.bind(
													'click',
													function(e) {
														e.preventDefault();
														oSettings._iDisplayStart = (parseInt(
																$('a', this)
																		.text(),
																10) - 1)
																* oPaging.iLength;
														fnDraw(oSettings);
													});
								}
								if (oPaging.iPage === 0) {
									$('li:lt(2)', an[i]).addClass('disabled'); /*$('li:first', an[i]).addClass('disabled');*/
								} else {
									$('li:lt(2)', an[i]).removeClass('disabled'); /*此处修改$('li:first', an[i]).removeClass('disabled');*/
								}
								if (oPaging.iPage === oPaging.iTotalPages - 1
										|| oPaging.iTotalPages === 0) {
									$('li:gt(-3)', an[i]).addClass('disabled'); /*此处修改$('li:last', an[i]).addClass('disabled');*/
								} else {
									$('li:gt(-3)', an[i]).removeClass('disabled'); /*此处修改$('li:last', an[i]).removeClass('disabled');*/
								}
							}
						}
					}
				});
$.extend(true, $.fn.DataTable.TableTools.classes, {
	"container" : "DTTT btn-group",
	"buttons" : {
		"normal" : "btn btn-white",
		"disabled" : "disabled"
	},
	"collection" : {
		"container" : "DTTT_dropdown dropdown-menu",
		"buttons" : {
			"normal" : "",
			"disabled" : "disabled"
		}
	},
	"print" : {
		"info" : "DTTT_print_info modal"
	},
	"select" : {
		"row" : "active"
	}
});
$.extend(true, $.fn.DataTable.TableTools.DEFAULTS.oTags, {
	"collection" : {
		"container" : "ul",
		"button" : "li",
		"liner" : "a"
	}
});
$('.dataTables_length select').select2({
        minimumResultsForSearch: "-1"
    });