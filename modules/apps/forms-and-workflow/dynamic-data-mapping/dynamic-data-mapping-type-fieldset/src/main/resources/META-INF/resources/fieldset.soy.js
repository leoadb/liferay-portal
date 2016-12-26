// This file was automatically generated from fieldset.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @hassoydelcall {ddm.del_form_rows}
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


ddm.fieldset = function(opt_data, opt_ignored) {
  return '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-fieldset" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') + '<fieldset>' + ((opt_data.showLabel) ? '<legend>' + soy.$$escapeHtml(opt_data.label) + '</legend>' : '') + soy.$$getDelegateFn(soy.$$getDelTemplateId('ddm.del_form_rows'), '', true)(opt_data) + '</fieldset></div>';
};
if (goog.DEBUG) {
  ddm.fieldset.soyTemplateName = 'ddm.fieldset';
}
