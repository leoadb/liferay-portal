// This file was automatically generated from grid.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_b69d8aa9 = function(opt_data, opt_ignored) {
  return soydata.VERY_UNSAFE.ordainSanitizedHtml(ddm.grid(opt_data));
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_b69d8aa9.soyTemplateName = 'ddm.__deltemplate_s2_b69d8aa9';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'grid', 0, ddm.__deltemplate_s2_b69d8aa9);


ddm.grid = function(opt_data, opt_ignored) {
  var output = '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '<div class="liferay-ddm-form-field-grid table-responsive">' + ((! opt_data.readOnly) ? ddm.hidden_grid(opt_data) : '') + '<table class="table table-autofit table-list table-striped"><thead><tr><th></th>';
  var columnList35 = opt_data.columns;
  var columnListLen35 = columnList35.length;
  for (var columnIndex35 = 0; columnIndex35 < columnListLen35; columnIndex35++) {
    var columnData35 = columnList35[columnIndex35];
    output += '<th>' + soy.$$escapeHtml(columnData35.label) + '</th>';
  }
  output += '</tr></thead><tbody>';
  var rowList73 = opt_data.rows;
  var rowListLen73 = rowList73.length;
  for (var rowIndex73 = 0; rowIndex73 < rowListLen73; rowIndex73++) {
    var rowData73 = rowList73[rowIndex73];
    output += '<tr name="' + soy.$$escapeHtmlAttribute(rowData73.value) + '"><td>' + soy.$$escapeHtml(rowData73.label) + '</td>';
    var columnList70 = opt_data.columns;
    var columnListLen70 = columnList70.length;
    for (var columnIndex70 = 0; columnIndex70 < columnListLen70; columnIndex70++) {
      var columnData70 = columnList70[columnIndex70];
      var checked__soy43 = '' + ((columnData70.value == opt_data.value[rowData73.value]) ? 'checked' : '');
      checked__soy43 = soydata.VERY_UNSAFE.$$ordainSanitizedAttributesForInternalBlocks(checked__soy43);
      var autoFocus__soy47 = '' + ((opt_data.focusTarget && (opt_data.focusTarget.row == rowData73.value && opt_data.focusTarget.index == columnIndex70)) ? 'autoFocus' : '');
      autoFocus__soy47 = soydata.VERY_UNSAFE.$$ordainSanitizedAttributesForInternalBlocks(autoFocus__soy47);
      output += '<td><input ' + ((autoFocus__soy47) ? 'autoFocus' : '') + ' ' + ((checked__soy43) ? 'checked' : '') + ' class="form-builder-grid-field" data-row-index="' + soy.$$escapeHtmlAttribute(columnIndex70) + '" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' name="' + soy.$$escapeHtmlAttribute(rowData73.value) + '" type="radio" value="' + soy.$$escapeHtmlAttribute(columnData70.value) + '" /></td>';
    }
    output += '</tr>';
  }
  output += '</tbody></table></div></div>';
  return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
};
if (goog.DEBUG) {
  ddm.grid.soyTemplateName = 'ddm.grid';
}


ddm.hidden_grid = function(opt_data, opt_ignored) {
  var output = '';
  var rowList89 = opt_data.rows;
  var rowListLen89 = rowList89.length;
  for (var rowIndex89 = 0; rowIndex89 < rowListLen89; rowIndex89++) {
    var rowData89 = rowList89[rowIndex89];
    var inputValue__soy77 = opt_data.value[rowData89.value] ? rowData89.value + ';' + opt_data.value[rowData89.value] : '';
    output += '<input class="form-control" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" type="hidden" ' + ((inputValue__soy77) ? 'value="' + soy.$$escapeHtmlAttribute(inputValue__soy77) + '"' : '') + '/>';
  }
  return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
};
if (goog.DEBUG) {
  ddm.hidden_grid.soyTemplateName = 'ddm.hidden_grid';
}
