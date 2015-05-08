<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%
String randomId = StringUtil.randomId();

String redirect = ParamUtil.getString(request, "redirect");

WorkflowTask workflowTask = displayContext.getWorkflowTask();

long classPK = displayContext.getWorkflowContextEntryClassPK(workflowTask);

WorkflowHandler<?> workflowHandler = displayContext.getWorkflowHandler(workflowTask);

AssetRenderer assetRenderer = workflowHandler.getAssetRenderer(classPK);

AssetRendererFactory assetRendererFactory = workflowHandler.getAssetRendererFactory();

AssetEntry assetEntry = null;

if (assetRenderer != null) {
	assetEntry = assetRendererFactory.getAssetEntry(assetRendererFactory.getClassName(), assetRenderer.getClassPK());
}

String headerTitle = displayContext.getHeaderTitle(workflowTask);

boolean showEditURL = displayContext.showEditURL(workflowTask);

PortletURL editPortletURL = displayContext.getEditPortletURL(workflowTask);

%>

<portlet:renderURL var="backURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:renderURL>

<liferay-ui:header
	backURL="<%= backURL.toString() %>"
	localizeTitle="<%= false %>"
	title="<%= headerTitle %>"
/>

<aui:row>
	<aui:col cssClass="lfr-asset-column lfr-asset-column-details" width="<%= 75 %>">
		<liferay-ui:error exception="<%= WorkflowTaskDueDateException.class %>" message="please-enter-a-valid-due-date" />

		<aui:row>
			<aui:col width="<%= 50 %>">
				<div class="lfr-asset-assigned">
					<c:choose>
						<c:when test="<%= workflowTask.isAssignedToSingleUser() %>">
							<aui:input cssClass="assigned-to" inlineField="<%= true %>" name="assignedTo" type="resource" value="<%= displayContext.getNameForAssignedToSingleUser(workflowTask) %>" />
						</c:when>
						<c:otherwise>
							<aui:input cssClass="assigned-to" inlineField="<%= true %>" name="assignedTo" type="resource" value='<%= displayContext.getNameForAssignedToAnyone() %>' />
						</c:otherwise>
					</c:choose>

					<c:if test="<%= !workflowTask.isAssignedToSingleUser() %>">
						<portlet:actionURL name="assignWorkflowTask" var="assignToMeURL">
							<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
							<portlet:param name="assigneeUserId" value="<%= String.valueOf(user.getUserId()) %>" />
						</portlet:actionURL>

						<aui:a cssClass="icon-signin" href="<%= assignToMeURL %>" id='<%= randomId + "taskAssignToMeLink" %>' label="assign-to-me" />
					</c:if>

					&nbsp;

					<c:if test="<%= displayContext.hasOtherAssignees(workflowTask) %>">
						<%= StringPool.DASH %>

						<portlet:actionURL name="assignWorkflowTask" var="assignURL">
							<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
						</portlet:actionURL>

						<aui:a cssClass="icon-signin" href="<%= assignURL %>" id='<%= randomId + "taskAssignLink" %>' label="assign-to-..." />
					</c:if>
				</div>

				<aui:input name="state" type="resource" value="<%= displayContext.getState(workflowTask) %>" />
			</aui:col>

			<aui:col width="<%= 50 %>">
				<aui:input name="createDate" type="resource" value="<%= displayContext.getCreateDate(workflowTask) %>" />

				<aui:input inlineField="<%= true %>" name="dueDate" type="resource" value='<%= displayContext.getDueDate(workflowTask) %>' />

				<c:if test="<%= !workflowTask.isCompleted() %>">
					<portlet:actionURL name="updateWorkflowTask" var="updateDueDateURL">
						<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="workflowTaskId" value="<%= StringUtil.valueOf(workflowTask.getWorkflowTaskId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						iconCssClass="icon-time"
						id='<%= randomId + "taskDueDateLink" %>'
						label="<%= true %>"
						message="change"
						url="javascript:;"
					/>
				</c:if>
			</aui:col>
		</aui:row>

		<c:if test="<%= Validator.isNotNull(workflowTask.getDescription()) %>">
			<div class="lfr-asset-field">
				<aui:field-wrapper label="description">
					<%= displayContext.getDescription(workflowTask) %>
				</aui:field-wrapper>
			</div>
		</c:if>

		<liferay-ui:panel-container cssClass="task-panel-container" extended="<%= true %>">
			<c:if test="<%= assetRenderer != null %>">
				<liferay-ui:panel defaultState="open" title='<%= displayContext.getPreviewOfTitle(workflowTask) %>'>
					<div class="task-content-actions">
						<liferay-ui:icon-list>
							<c:if test="<%= assetRenderer.hasViewPermission(permissionChecker) %>">
								<portlet:renderURL var="viewFullContentURL">
									<portlet:param name="mvcPath" value="/view_content.jsp" />
									<portlet:param name="redirect" value="<%= currentURL %>" />

									<c:if test="<%= assetEntry != null %>">
										<portlet:param name="assetEntryId" value="<%= String.valueOf(assetEntry.getEntryId()) %>" />
										<portlet:param name="assetEntryVersionId" value="<%= String.valueOf(classPK) %>" />
									</c:if>

									<portlet:param name="type" value="<%= assetRendererFactory.getType() %>" />
									<portlet:param name="showEditURL" value="<%= String.valueOf(showEditURL) %>" />
								</portlet:renderURL>

								<liferay-ui:icon iconCssClass="icon-search" message="view[action]" method="get" target='<%= assetRenderer.isPreviewInContext() ? "_blank" : StringPool.BLANK %>' url="<%= assetRenderer.isPreviewInContext() ? assetRenderer.getURLViewInContext((LiferayPortletRequest)renderRequest, (LiferayPortletResponse)renderResponse, null) : viewFullContentURL.toString() %>" />

								<c:if test="<%= displayContext.hasViewDiffsPortletURL(workflowTask) %>">

									<liferay-ui:icon iconCssClass="icon-copy" message="diffs" url="<%= displayContext.getTaglibViewDiffsURL(workflowTask) %>" />
								</c:if>
							</c:if>

							<c:if test="<%= displayContext.hasEditPortletURL(workflowTask) %>">

								<c:choose>
									<c:when test="<%= assetRenderer.hasEditPermission(permissionChecker) && showEditURL %>">

										<liferay-ui:icon iconCssClass="icon-edit" message="edit" url="<%= displayContext.getTaglibEditURL(workflowTask) %>" />
									</c:when>
									<c:when test="<%= assetRenderer.hasEditPermission(permissionChecker) && !showEditURL && !workflowTask.isCompleted() %>">
										<liferay-ui:icon-help message="please-assign-the-task-to-yourself-to-be-able-to-edit-the-content" />
									</c:when>
								</c:choose>
							</c:if>
						</liferay-ui:icon-list>
					</div>

					<h3 class="task-content-title">
						<liferay-ui:icon
							iconCssClass="<%= workflowHandler.getIconCssClass() %>"
							label="<%= true %>"
							message="<%= displayContext.getTaskContentTitle(workflowTask) %>"
						/>
					</h3>

					<liferay-ui:asset-display
						assetRenderer="<%= assetRenderer %>"
						template="<%= AssetRenderer.TEMPLATE_ABSTRACT %>"
					/>

					<liferay-ui:asset-metadata
						className="<%= assetEntry.getClassName() %>"
						classPK="<%= assetEntry.getClassPK() %>"
						metadataFields="<%= displayContext.getMetadataFields() %>"
					/>
				</liferay-ui:panel>

				<liferay-ui:panel title="comments">
					<portlet:actionURL name="invokeTaglibDiscussion" var="discussionURL" />

					<portlet:resourceURL var="discussionPaginationURL">
						<portlet:param name="invokeTaglibDiscussion" value="true" />
					</portlet:resourceURL>

					<liferay-ui:discussion
						assetEntryVisible="<%= false %>"
						className="<%= assetRenderer.getClassName() %>"
						classPK="<%= assetRenderer.getClassPK() %>"
						formAction="<%= discussionURL %>"
						formName='<%= "fm" + assetRenderer.getClassPK() %>'
						paginationURL="<%= discussionPaginationURL %>"
						ratingsEnabled="<%= false %>"
						redirect="<%= currentURL %>"
						userId="<%= user.getUserId() %>"
					/>
				</liferay-ui:panel>
			</c:if>

			<liferay-ui:panel defaultState="closed" title="activities">

				<%
				List<WorkflowLog> workflowLogs = displayContext.getWorkflowLogs(workflowTask);
				%>

				<%@ include file="/workflow_logs.jspf" %>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</aui:col>

	<aui:col cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>" width="<%= 25 %>">
		<div class="lfr-asset-summary">
			<liferay-ui:icon
				cssClass="lfr-asset-avatar"
				image="../file_system/large/task"
				message="download"
			/>

			<div class="task-name">
				<%= displayContext.getEditTaskName(workflowTask) %>
			</div>
		</div>

		<%
		request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
		%>

		<liferay-util:include page="/workflow_task_action.jsp" servletContext="<%= application %>" />
	</aui:col>
</aui:row>

<aui:script use="liferay-workflow-tasks">
	var onTaskClickFn = A.rbind('onTaskClick', Liferay.WorkflowTasks, '');

	Liferay.delegateClick('<portlet:namespace /><%= randomId %>taskAssignToMeLink', onTaskClickFn);
	Liferay.delegateClick('<portlet:namespace /><%= randomId %>taskAssignLink', onTaskClickFn);
	Liferay.delegateClick('<portlet:namespace /><%= randomId %>taskDueDateLink', onTaskClickFn);
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, headerTitle, currentURL);
%>