// This file was automatically generated from select.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_2dbfb377 = function(opt_data, opt_ignored) {
  return '' + ddm.select(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_2dbfb377.soyTemplateName = 'ddm.__deltemplate_s2_2dbfb377';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'select', 0, ddm.__deltemplate_s2_2dbfb377);


ddm.select = function(opt_data, opt_ignored) {
  var output = '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"><div class="input-select-wrapper">' + ((opt_data.showLabel) ? ddm.select_label(opt_data) : '') + '<div class="form-builder-select-field input-group-container">' + ((! opt_data.readOnly) ? ddm.hidden_select(soy.$$augmentMap(opt_data, {options: opt_data.options, name: opt_data.name, multiple: opt_data.multiple, dir: opt_data.dir, value: opt_data.value, string: opt_data.strings})) : '');
  if (! opt_data.multiple) {
    output += '<a class="form-control select-field-trigger" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" href="javascript:;" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">';
    var selectedLabel__soy36 = '' + ((! opt_data.readOnly) ? (opt_data.value && opt_data.value.value) ? soy.$$escapeHtml(opt_data.value.label) : soy.$$escapeHtml(opt_data.strings.chooseAnOption) : soy.$$escapeHtml(opt_data.strings.chooseAnOption));
    output += '<span class="option-selected">' + soy.$$escapeHtml(selectedLabel__soy36) + '</span></a>';
  } else {
  }
  if (! opt_data.readOnly) {
    output += '<div class="drop-chosen hide"><ul class="results-chosen">';
    var optionList66 = opt_data.options;
    var optionListLen66 = optionList66.length;
    for (var optionIndex66 = 0; optionIndex66 < optionListLen66; optionIndex66++) {
      var optionData66 = optionList66[optionIndex66];
      var selectedValue__soy53 = '' + ((opt_data.value && opt_data.value.value) ? soy.$$escapeHtml(opt_data.value.value) : '');
      output += '<li class="' + ((selectedValue__soy53 == optionData66.value) ? 'option-selected' : '') + '" data-option-index="' + soy.$$escapeHtmlAttribute(optionIndex66) + '">' + soy.$$escapeHtml(optionData66.label) + '</li>';
    }
    output += '</ul></div>';
  }
  output += ((opt_data.selecteCaretDoubleIcon) ? '<a class="select-arrow-down-container" href="javascript:;">' + soy.$$filterNoAutoescape(opt_data.selecteCaretDoubleIcon) + '</a>' : '') + '</div>' + ((opt_data.childElementsHTML) ? soy.$$filterNoAutoescape(opt_data.childElementsHTML) : '') + '</div></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.select.soyTemplateName = 'ddm.select';
}


ddm.select_label = function(opt_data, opt_ignored) {
  return '<label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '');
};
if (goog.DEBUG) {
  ddm.select_label.soyTemplateName = 'ddm.select_label';
}


ddm.hidden_select = function(opt_data, opt_ignored) {
  var output = '<select class="form-control hide" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" ' + ((opt_data.multiple) ? 'multiple size="' + soy.$$escapeHtmlAttribute(opt_data.options.length) + '"' : '') + '>' + ((! opt_data.readOnly) ? '<option dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" disabled ' + ((! opt_data.value) ? 'selected' : '') + ' value="">' + soy.$$escapeHtml(opt_data.strings.chooseAnOption) + '</option>' : '');
  var optionList135 = opt_data.options;
  var optionListLen135 = optionList135.length;
  for (var optionIndex135 = 0; optionIndex135 < optionListLen135; optionIndex135++) {
    var optionData135 = optionList135[optionIndex135];
    var selectedValue__soy120 = '' + ((opt_data.value && opt_data.value.value) ? soy.$$escapeHtml(opt_data.value.value) : '');
    output += '<option dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" ' + ((selectedValue__soy120 == optionData135.value) ? 'selected' : '') + ' value="' + soy.$$escapeHtmlAttribute(optionData135.value) + '">' + soy.$$escapeHtml(optionData135.label) + '</option>';
  }
  output += '</select>';
  return output;
};
if (goog.DEBUG) {
  ddm.hidden_select.soyTemplateName = 'ddm.hidden_select';
}
