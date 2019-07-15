package com.liferay.report.definitions.web.internal.portlet.action;

import com.liferay.data.engine.rest.client.resource.v1_0.DataDefinitionResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.report.definitions.model.ReportDefinition;
import com.liferay.report.definitions.service.ReportDefinitionLocalService;
import com.liferay.report.definitions.web.internal.constants.ReportDefinitionPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ReportDefinitionPortletKeys.PORTLET_NAME,
		"mvc.command.name=deleteReportDefinition"
	},
	service = MVCActionCommand.class
)
public class DeleteReportDefinitionMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long reportDefinitionId = ParamUtil.getLong(
			actionRequest, "reportDefinitionId");

		ReportDefinition reportDefinition =
			_reportDefinitionLocalService.fetchReportDefinition(
				reportDefinitionId);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).authentication(
				user.getEmailAddress(), user.getPassword()
			).endpoint(
				_portal.getHost(httpServletRequest),
				httpServletRequest.getServerPort(),
				httpServletRequest.getScheme()
			).build();

		dataDefinitionResource.deleteDataDefinition(
			reportDefinition.getDataDefinitionId());

		_reportDefinitionLocalService.deleteReportDefinition(
			reportDefinitionId);
	}

	@Reference
	private Portal _portal;

	@Reference
	private ReportDefinitionLocalService _reportDefinitionLocalService;

}