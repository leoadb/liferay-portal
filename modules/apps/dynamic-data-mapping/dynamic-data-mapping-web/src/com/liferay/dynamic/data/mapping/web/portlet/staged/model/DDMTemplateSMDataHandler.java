package com.liferay.dynamic.data.mapping.web.portlet.staged.model;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.lar.DDMTemplateStagedModelDataHandler;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortletKeys.DYNAMIC_DATA_MAPPING
	},
	service = StagedModelDataHandler.class
)
public class DDMTemplateSMDataHandler 
	extends DDMTemplateStagedModelDataHandler {

}
