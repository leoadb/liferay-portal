// This file was automatically generated from select.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_2dbfb377 = function(opt_data, opt_ignored) {
  return soydata.VERY_UNSAFE.ordainSanitizedHtml(ddm.select(opt_data));
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_2dbfb377.soyTemplateName = 'ddm.__deltemplate_s2_2dbfb377';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'select', 0, ddm.__deltemplate_s2_2dbfb377);


ddm.select = function(opt_data, opt_ignored) {
  var output = '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"><div class="input-select-wrapper">' + ((opt_data.showLabel) ? ddm.select_label(opt_data) : '') + '<div class="form-builder-select-field input-group-container">' + ((! opt_data.readOnly) ? ddm.hidden_select(opt_data) : '') + '<div class="form-control select-field-trigger" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"><a class="select-arrow-down-container" href="javascript:;">' + ((opt_data.selectCaretDoubleIcon) ? soy.$$escapeHtml(opt_data.selectCaretDoubleIcon) : '') + '</a>';
  if (opt_data.multiple) {
    if (opt_data.value.length == 0) {
      output += '<span class="option-selected option-selected-placeholder">' + soy.$$escapeHtml(opt_data.strings.chooseOptions) + '</span>';
    } else {
      output += '<ul class="multiple-badge-list">';
      var currentValueList41 = opt_data.value;
      var currentValueListLen41 = currentValueList41.length;
      for (var currentValueIndex41 = 0; currentValueIndex41 < currentValueListLen41; currentValueIndex41++) {
        var currentValueData41 = currentValueList41[currentValueIndex41];
        output += ddm.badge_item({badgeCloseIcon: opt_data.badgeCloseIcon, value: currentValueData41.value, label: currentValueData41.label});
      }
      output += '</ul>';
    }
  } else {
    if (opt_data.value && opt_data.value.length > 0) {
      var currentValueList52 = opt_data.value;
      var currentValueListLen52 = currentValueList52.length;
      for (var currentValueIndex52 = 0; currentValueIndex52 < currentValueListLen52; currentValueIndex52++) {
        var currentValueData52 = currentValueList52[currentValueIndex52];
        output += (currentValueData52 && currentValueData52.label) ? '<span class="option-selected">' + soy.$$escapeHtml(currentValueData52.label) + '</span>' : '';
      }
    } else {
      output += '<span class="option-selected option-selected-placeholder">' + soy.$$escapeHtml(opt_data.strings.chooseAnOption) + '</span>';
    }
  }
  output += '</div>' + ((! opt_data.readOnly) ? '<div class="drop-chosen ' + soy.$$escapeHtmlAttribute(opt_data.open ? '' : 'hide') + '"><div class="search-chosen"><div class="select-search-container">' + ((opt_data.selectSearchIcon) ? '<a class="" href="javascript:;">' + soy.$$escapeHtml(opt_data.selectSearchIcon) + '</a>' : '') + '</div><input autocomplete="off" class="drop-chosen-search" placeholder="' + soy.$$escapeHtmlAttribute(opt_data.strings.search) + '" type="text"></div><ul class="results-chosen">' + ddm.select_options(opt_data) + '</ul></div>' : '') + '</div>' + ((opt_data.childElementsHTML) ? soy.$$escapeHtml(opt_data.childElementsHTML) : '') + '</div></div>';
  return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
};
if (goog.DEBUG) {
  ddm.select.soyTemplateName = 'ddm.select';
}


ddm.badge_item = function(opt_data, opt_ignored) {
  return soydata.VERY_UNSAFE.ordainSanitizedHtml('<li><span class="badge badge-default badge-sm multiple-badge" data-original-title="' + soy.$$escapeHtmlAttribute(opt_data.label) + '" title="' + soy.$$escapeHtmlAttribute(opt_data.label) + '">' + soy.$$escapeHtml(opt_data.label) + '<a class="trigger-badge-item-close" data-badge-value="' + soy.$$escapeHtmlAttribute(opt_data.value) + '" href="javascript:void(0)">' + soy.$$escapeHtml(opt_data.badgeCloseIcon) + '</a></span></li>');
};
if (goog.DEBUG) {
  ddm.badge_item.soyTemplateName = 'ddm.badge_item';
}


ddm.select_label = function(opt_data, opt_ignored) {
  return soydata.VERY_UNSAFE.ordainSanitizedHtml('<label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : ''));
};
if (goog.DEBUG) {
  ddm.select_label.soyTemplateName = 'ddm.select_label';
}


ddm.hidden_select = function(opt_data, opt_ignored) {
  var output = '<select class="form-control hide" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" ' + ((opt_data.multiple) ? 'multiple size="' + soy.$$escapeHtmlAttribute(opt_data.options.length) + '"' : '') + '>' + ((! opt_data.readOnly) ? '<option dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" disabled ' + ((opt_data.value.length == 0) ? 'selected' : '') + ' value="">' + soy.$$escapeHtml(opt_data.strings.chooseAnOption) + '</option>' : '');
  var optionList134 = opt_data.options;
  var optionListLen134 = optionList134.length;
  for (var optionIndex134 = 0; optionIndex134 < optionListLen134; optionIndex134++) {
    var optionData134 = optionList134[optionIndex134];
    output += ddm.select_hidden_options({dir: opt_data.dir, option: optionData134, values: opt_data.value});
  }
  output += '</select>';
  return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
};
if (goog.DEBUG) {
  ddm.hidden_select.soyTemplateName = 'ddm.hidden_select';
}


ddm.select_hidden_options = function(opt_data, opt_ignored) {
  var output = '';
  var selected__soy138 = '';
  if (opt_data.values) {
    var currentValueList144 = opt_data.values;
    var currentValueListLen144 = currentValueList144.length;
    for (var currentValueIndex144 = 0; currentValueIndex144 < currentValueListLen144; currentValueIndex144++) {
      var currentValueData144 = currentValueList144[currentValueIndex144];
      selected__soy138 += (currentValueData144.value == opt_data.option.value) ? 'selected' : '';
    }
  }
  selected__soy138 = soydata.VERY_UNSAFE.$$ordainSanitizedAttributesForInternalBlocks(selected__soy138);
  output += '<option dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" ' + soy.$$filterHtmlAttributes(selected__soy138) + ' value="' + soy.$$escapeHtmlAttribute(opt_data.option.value) + '">' + soy.$$escapeHtml(opt_data.option.label) + '</option>';
  return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
};
if (goog.DEBUG) {
  ddm.select_hidden_options.soyTemplateName = 'ddm.select_hidden_options';
}


ddm.select_options = function(opt_data, opt_ignored) {
  var output = '';
  if (opt_data.options.length > 0) {
    var optionList189 = opt_data.options;
    var optionListLen189 = optionList189.length;
    for (var optionIndex189 = 0; optionIndex189 < optionListLen189; optionIndex189++) {
      var optionData189 = optionList189[optionIndex189];
      var selected__soy158 = '';
      if (opt_data.value) {
        var currentValueList164 = opt_data.value;
        var currentValueListLen164 = currentValueList164.length;
        for (var currentValueIndex164 = 0; currentValueIndex164 < currentValueListLen164; currentValueIndex164++) {
          var currentValueData164 = currentValueList164[currentValueIndex164];
          selected__soy158 += (currentValueData164.value == optionData189.value) ? 'selected' : '';
        }
      }
      selected__soy158 = soydata.VERY_UNSAFE.$$ordainSanitizedAttributesForInternalBlocks(selected__soy158);
      output += '<li class="select-option-item ' + ((selected__soy158) ? 'option-selected' : '') + '" data-option-index="' + soy.$$escapeHtmlAttribute(optionIndex189) + '" data-option-selected="' + ((selected__soy158) ? 'true' : '') + '" data-option-value="' + soy.$$escapeHtmlAttribute(optionData189.value) + '">' + ((opt_data.multiple) ? '<input type="checkbox" value="" ' + ((selected__soy158) ? 'checked' : '') + '>' : '') + '<span>' + soy.$$escapeHtml(optionData189.label) + '</span></li>';
    }
  } else {
    output += '<li class="no-results-list"><span>' + soy.$$escapeHtml(opt_data.strings.emptyList) + '</span></li>';
  }
  return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
};
if (goog.DEBUG) {
  ddm.select_options.soyTemplateName = 'ddm.select_options';
}
