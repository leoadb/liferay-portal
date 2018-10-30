package report.definition.portlet.web.portlet;

import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import report.definition.portlet.web.constants.ReportDefinitionNPMKeys;
import report.definition.portlet.web.constants.ReportDefinitionPortletKeys;

/**
 * @author brunobasto
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ReportDefinitionPortletKeys.PORTLET_NAME,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ReportDefinitionPortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		JSPackage jsPackage = _npmResolver.getJSPackage();

		renderRequest.setAttribute(
			ReportDefinitionNPMKeys.BOOTSTRAP_REQUIRE,
			jsPackage.getResolvedId());

		super.doView(renderRequest, renderResponse);
	}

	@Override
	protected void include(
			String path, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException, PortletException {

		super.include(path, actionRequest, actionResponse);
	}

	@Reference
	private NPMResolver _npmResolver;

}