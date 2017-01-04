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
  var output = '';
  var optionsSelected__soy5 = opt_data.value;
  output += '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"><div class="input-select-wrapper">' + ((opt_data.showLabel) ? ddm.select_label(opt_data) : '') + '<div class="form-builder-select-field input-group-container">' + ((! opt_data.readOnly) ? ddm.hidden_select(opt_data) : '') + ((! opt_data.multiple) ? '<a class="form-control select-field-trigger" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" href="javascript:;" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((! opt_data.readOnly) ? (optionsSelected__soy5 && optionsSelected__soy5.value) ? '<span class="option-selected">' + soy.$$escapeHtml(optionsSelected__soy5.label) + '</span>' : '<span class="option-selected option-selected-placeholder">' + soy.$$escapeHtml(opt_data.strings.chooseAnOption) + '</span>' : '<span class="option-selected option-selected-placeholder">' + soy.$$escapeHtml(opt_data.strings.chooseAnOption) + '</span>') + '</a>' : '');
  if (! opt_data.readOnly) {
    output += '<div class="drop-chosen hide"><ul class="results-chosen">';
    var optionList60 = opt_data.options;
    var optionListLen60 = optionList60.length;
    for (var optionIndex60 = 0; optionIndex60 < optionListLen60; optionIndex60++) {
      var optionData60 = optionList60[optionIndex60];
      var selectedValue__soy47 = '' + ((optionsSelected__soy5 && optionsSelected__soy5.value) ? soy.$$escapeHtml(optionsSelected__soy5.value) : '');
      output += '<li class="' + ((selectedValue__soy47 == optionData60.value) ? 'option-selected' : '') + '" data-option-index="' + soy.$$escapeHtmlAttribute(optionIndex60) + '">' + soy.$$escapeHtml(optionData60.label) + '</li>';
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
  var optionList129 = opt_data.options;
  var optionListLen129 = optionList129.length;
  for (var optionIndex129 = 0; optionIndex129 < optionListLen129; optionIndex129++) {
    var optionData129 = optionList129[optionIndex129];
    var selectedValue__soy114 = '' + ((opt_data.value && opt_data.value.value) ? soy.$$escapeHtml(opt_data.value.value) : '');
    output += '<option dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" ' + ((selectedValue__soy114 == optionData129.value) ? 'selected' : '') + ' value="' + soy.$$escapeHtmlAttribute(optionData129.value) + '">' + soy.$$escapeHtml(optionData129.label) + '</option>';
  }
  output += '</select>';
  return output;
};
if (goog.DEBUG) {
  ddm.hidden_select.soyTemplateName = 'ddm.hidden_select';
}
