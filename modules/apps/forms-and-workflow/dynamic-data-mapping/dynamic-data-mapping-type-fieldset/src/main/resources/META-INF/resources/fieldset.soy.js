// This file was automatically generated from fieldset.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @hassoydelcall {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_95ba1b96 = function(opt_data, opt_ignored) {
  return '' + ddm.fieldset(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_95ba1b96.soyTemplateName = 'ddm.__deltemplate_s2_95ba1b96';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'fieldset', 0, ddm.__deltemplate_s2_95ba1b96);


ddm.fieldset_column = function(opt_data, opt_ignored) {
  var output = '';
  var fieldList11 = opt_data.column.fields;
  var fieldListLen11 = fieldList11.length;
  for (var fieldIndex11 = 0; fieldIndex11 < fieldListLen11; fieldIndex11++) {
    var fieldData11 = fieldList11[fieldIndex11];
    output += '<div class="col-md-' + soy.$$escapeHtmlAttribute(opt_data.column.size) + '">';
    var variant__soy8 = fieldData11.type;
    output += soy.$$getDelegateFn(soy.$$getDelTemplateId('ddm.field'), variant__soy8, true)(fieldData11) + '</div>';
  }
  return output;
};
if (goog.DEBUG) {
  ddm.fieldset_column.soyTemplateName = 'ddm.fieldset_column';
}


ddm.fieldset_columns = function(opt_data, opt_ignored) {
  var output = '';
  var columnList16 = opt_data.columns;
  var columnListLen16 = columnList16.length;
  for (var columnIndex16 = 0; columnIndex16 < columnListLen16; columnIndex16++) {
    var columnData16 = columnList16[columnIndex16];
    output += ddm.fieldset_column(soy.$$augmentMap(opt_data, {column: columnData16}));
  }
  return output;
};
if (goog.DEBUG) {
  ddm.fieldset_columns.soyTemplateName = 'ddm.fieldset_columns';
}


ddm.fieldset_rows = function(opt_data, opt_ignored) {
  var output = '';
  var rowList23 = opt_data.rows;
  var rowListLen23 = rowList23.length;
  for (var rowIndex23 = 0; rowIndex23 < rowListLen23; rowIndex23++) {
    var rowData23 = rowList23[rowIndex23];
    output += '<div class="row">' + ddm.fieldset_columns(soy.$$augmentMap(opt_data, {columns: rowData23.columns})) + '</div>';
  }
  return output;
};
if (goog.DEBUG) {
  ddm.fieldset_rows.soyTemplateName = 'ddm.fieldset_rows';
}


ddm.fieldset = function(opt_data, opt_ignored) {
  return '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-fieldset" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') + '<fieldset>' + ((opt_data.showLabel) ? '<legend>' + soy.$$escapeHtml(opt_data.label) + '</legend>' : '') + ddm.fieldset_rows(opt_data) + '</fieldset></div>';
};
if (goog.DEBUG) {
  ddm.fieldset.soyTemplateName = 'ddm.fieldset';
}
