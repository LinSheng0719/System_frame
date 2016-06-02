/* ------------------------------------------------------------------------------
*
*  # TableTools extension for Datatables
*
*  Specific JS code additions for datatable_extension_tools.html page
*
*  Version: 1.0
*  Latest update: Aug 1, 2015
*
* ---------------------------------------------------------------------------- */

$(function() {
    $.extend( $.fn.dataTable.defaults, {
        autoWidth: false,
        columnDefs: [{ 
            orderable: false,
            width: '100px'
        }],
        dom: '<"datatable-header"lT><"datatable-scroll"t><"datatable-footer"ip>',
        language: {
            lengthMenu: '_MENU_',
            paginate: { 'first': '首页', 'last': '末页', 'next': '&rarr;', 'previous': '&larr;' },
            infoEmpty:'显示 <b>0</b> 至 <b>0</b> 共  0 页0 条记录',
            info:'显示 <b>_START_</b> 至 <b>_END_</b> 共  _PAGES_ 页 _TOTAL_ 条记录',
            emptyTable:'暂无数据'
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
});
