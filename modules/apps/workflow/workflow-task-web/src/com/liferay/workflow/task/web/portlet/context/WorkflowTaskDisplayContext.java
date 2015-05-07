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

package com.liferay.workflow.task.web.portlet.context;

import java.io.Serializable;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.workflow.task.web.portlet.constants.WorkflowTaskConstants;
import com.liferay.workflow.task.web.portlet.context.util.WorkflowTaskRequestHelper;
import com.liferay.workflow.task.web.portlet.search.WorkflowTaskDisplayTerms;
import com.liferay.workflow.task.web.portlet.search.WorkflowTaskSearch;

/**
 * @author Leonardo Barros
 */
public class WorkflowTaskDisplayContext {

	public static final String HIDE_CONTROLS = "hideControls";

	public static final String REDIRECT = "redirect";

	public WorkflowTaskDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		this.dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			themeDisplay.getLocale(), themeDisplay.getTimeZone());
		
		this.renderResponse = renderResponse;
		this.renderRequest = renderRequest;
		
		this.workflowTaskRequestHelper = new WorkflowTaskRequestHelper(
			renderRequest);
	}
	
	public PortletURL getPortletURL() {
		return PortletURLUtil.getCurrent(renderRequest, renderResponse);
	}
	
	public List<WorkflowHandler<?>> getWorkflowHandlersOfSearchableAssets() {
		List<WorkflowHandler<?>> workflowHandlersOfSearchableAssets = 
			new ArrayList<>();

		List<WorkflowHandler<?>> workflowHandlers =
			WorkflowHandlerRegistryUtil.getWorkflowHandlers();

		for (WorkflowHandler<?> workflowHandler : workflowHandlers) {
			if (workflowHandler.isAssetTypeSearchable()) {
				workflowHandlersOfSearchableAssets.add(workflowHandler);
			}
		}
		
		return workflowHandlersOfSearchableAssets;
	}
	
	public String getSelectedTab() {
		return ParamUtil.getString(
			renderRequest, WorkflowTaskConstants.TABS1, 
			WorkflowTaskConstants.PENDING);
	}
	
	public WorkflowTaskDisplayTerms getDisplayTerms() {
		return new WorkflowTaskDisplayTerms(renderRequest);
	}

	public boolean isPendingTab() {
		return WorkflowTaskConstants.PENDING.equals(getSelectedTab());
	}
	
	public WorkflowTaskSearch getPendingTasksAssignedToMe() 
		throws PortalException {
		return searchTasksAssignedToMe(false);
	}
	
	public WorkflowTaskSearch getCompletedTasksAssignedToMe() 
		throws PortalException {
		return searchTasksAssignedToMe(true);
	}
	
	public boolean isCompletedTab() {
		return WorkflowTaskConstants.COMPLETED.equals(getSelectedTab());
	}
	
	public long[] getPooledActorsIds()
		throws PortalException {
		
		return WorkflowTaskManagerUtil.getPooledActorsIds(
			workflowTaskRequestHelper.getCompanyId(), getWorkflowTaskId());
	}
	
	public long getWorkflowTaskId() {
		return getWorkflowTask().getWorkflowTaskId();
	}
	
	public List<String> getTransitionNames() 
		throws PortalException {
		
		return WorkflowTaskManagerUtil.getNextTransitionNames(
			workflowTaskRequestHelper.getCompanyId(), 
			workflowTaskRequestHelper.getUserId(), getWorkflowTaskId());
	}
	
	public String getWorkflowTaskRandomId() {
		String randomId = StringPool.BLANK;
		
		ResultRow row = (ResultRow)renderRequest.getAttribute(
			WebKeys.SEARCH_CONTAINER_RESULT_ROW);
		
		if(Validator.isNotNull(row)) {
			randomId = StringUtil.randomId();
		}
		
		return randomId;
	}
	
	public String getTransitionMessage(String transitionName) {
		String message = WorkflowTaskConstants.PROCEED;

		if (Validator.isNotNull(transitionName)) {
			message = HtmlUtil.escape(transitionName);
		}
		
		return message;
	}
	
	public boolean isAssignedToUser() {
		WorkflowTask workflowTask = getWorkflowTask();
		
		if (workflowTask.getAssigneeUserId() == 
			workflowTaskRequestHelper.getUserId()) {
			return true;
		}

		return false;
	}
	
	public AssetRendererFactory getAssetRendererFactoryFromRequest() {
		
		String type = ParamUtil.getString(
			workflowTaskRequestHelper.getRequest(), "type");
		
		return AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByType(
			type);
	}
	
	public AssetEntry getAssetEntryFromRequest() 
		throws PortalException {
		
		long assetEntryId = ParamUtil.getLong(
			workflowTaskRequestHelper.getRequest(), "assetEntryId");
		
		AssetRendererFactory assetRendererFactory = 
			getAssetRendererFactoryFromRequest();
		
		return assetRendererFactory.getAssetEntry(assetEntryId);
	}
	
	public AssetRenderer getAssetRendererFromRequest() 
		throws PortalException {
		
		long assetEntryVersionId = ParamUtil.getLong(
			workflowTaskRequestHelper.getRequest(), "assetEntryVersionId");

		return getAssetRendererFactoryFromRequest().getAssetRenderer(
				assetEntryVersionId, AssetRendererFactory.TYPE_LATEST);
	}
	
	public boolean hasOtherAssignees() 
		throws PortalException {
		
		WorkflowTask workflowTask = getWorkflowTask();
		
		long[] pooledActorsIds = getPooledActorsIds();
		
		if (pooledActorsIds.length == 0) {
			return false;
		}

		if (workflowTask.isCompleted()) {
			return false;
		}

		if ((pooledActorsIds.length == 1) && 
			(pooledActorsIds[0] == workflowTaskRequestHelper.getUserId())) {
			return false;
		}

		return true;
	}
	
	public WindowState getWindowState() {
		return renderRequest.getWindowState();
	}
	
	public String getCurrentURL() {
		return getPortletURL().toString();
	}
	
	public String getActorName(long actorId) {
		return HtmlUtil.escape(
			PortalUtil.getUserName(actorId, StringPool.BLANK));
	}
	
	public long[] getActorsIds() 
		throws PortalException {
		
		long[] pooledActorsIds = getPooledActorsIds();
		List<Long> actors = new ArrayList<Long>();
		for (long pooledActorId : pooledActorsIds) {
			if (pooledActorId != workflowTaskRequestHelper.getUserId()) {
				actors.add(pooledActorId);
			}
		}
		return ArrayUtil.toLongArray(actors);
	}
	
	public WorkflowTaskSearch getPendingTasksAssignedToMyRoles() 
		throws PortalException {
		
		int total = 0;
		List<WorkflowTask> results = null;
		
		String[] assetTypes = WorkflowHandlerUtil.getSearchableAssetTypes();

		WorkflowTaskSearch searchContainer = new WorkflowTaskSearch(
			renderRequest, WorkflowTaskConstants.DEFAULT_CUR_PARAM2, 
			getPortletURL());
		
		WorkflowTaskDisplayTerms searchTerms =
			(WorkflowTaskDisplayTerms)searchContainer.getDisplayTerms();

		if (searchTerms.isAdvancedSearch()) {
			total = WorkflowTaskManagerUtil.searchCount(
				workflowTaskRequestHelper.getCompanyId(), 
				workflowTaskRequestHelper.getUserId(), searchTerms.getName(),
				searchTerms.getType(), null, null, null, false, true,
				searchTerms.isAndOperator());

			searchContainer.setTotal(total);

			results = WorkflowTaskManagerUtil.search(
				workflowTaskRequestHelper.getCompanyId(), 
				workflowTaskRequestHelper.getUserId(), searchTerms.getName(),
				searchTerms.getType(), null, null, null, false, true,
				searchTerms.isAndOperator(), searchContainer.getStart(),
				searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}
		else {
			total = WorkflowTaskManagerUtil.searchCount(
				workflowTaskRequestHelper.getCompanyId(), 
				workflowTaskRequestHelper.getUserId(),
				searchTerms.getKeywords(), assetTypes, false, true);

			searchContainer.setTotal(total);

			results = WorkflowTaskManagerUtil.search(
					workflowTaskRequestHelper.getCompanyId(), workflowTaskRequestHelper.getUserId(),
				searchTerms.getKeywords(), assetTypes, false, true,
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}

		searchContainer.setTotal(total);
		searchContainer.setResults(results);

		validateSearchTerms(searchContainer);

		searchContainer.setEmptyResultsMessage(
	WorkflowTaskConstants.THERE_ARE_NO_PENDING_TASKS_ASSIGNED_TO_YOUR_ROLES);

		return searchContainer;
	}
	
	public String getTaskName() {
		WorkflowTask workflowTask = getWorkflowTask();
		return HtmlUtil.escape(workflowTask.getName());
	}
	
	public String getAssetTitle() 
		throws PortalException {
		
		long classPK = getWorkflowContextEntryClassPK();
		
		WorkflowHandler<?> workflowHandler = getWorkflowHandler();

		return HtmlUtil.escape(workflowHandler.getTitle(classPK, 
			workflowTaskRequestHelper.getLocale()));
	}
	
	public String getAssetType() 
		throws PortalException {
		
		WorkflowHandler<?> workflowHandler = getWorkflowHandler();
		
		return workflowHandler.getType(workflowTaskRequestHelper.getLocale());
	}
	
	public String getLastActivityDate() 
		throws PortalException {
		
		WorkflowLog workflowLog = getWorkflowLog();

		if (workflowLog == null) {
			return LanguageUtil.get(
				workflowTaskRequestHelper.getRequest(), 
				WorkflowTaskConstants.NEVER);
		}
		else {
			return dateFormatDateTime.format(workflowLog.getCreateDate());
		}
	}
	
	public String getDueDate() {
		WorkflowTask workflowTask = getWorkflowTask();
		
		if (workflowTask.getDueDate() == null) {
			return LanguageUtil.get(
				workflowTaskRequestHelper.getRequest(), 
				WorkflowTaskConstants.NEVER);
		}
		else {
			return dateFormatDateTime.format(workflowTask.getDueDate());
		}
	}
	
	public WorkflowLog getWorkflowLog() 
		throws PortalException {
		
		List<WorkflowLog> workflowLogs =
			WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(
				workflowTaskRequestHelper.getCompanyId(),
				getWorkflowInstanceId(), null, 0, 1,
			WorkflowComparatorFactoryUtil.getLogCreateDateComparator());

		if (!workflowLogs.isEmpty()) {
			return workflowLogs.get(0);
		}
		
		return null;
	}
	
	public WorkflowInstance getWorkflowInstance()  
		throws PortalException {
		
		return WorkflowInstanceManagerUtil.getWorkflowInstance(
			workflowTaskRequestHelper.getCompanyId(),
			getWorkflowInstanceId());
	}
	
	public long getWorkflowCompanyId() 
		throws PortalException {
		
		WorkflowInstance workflowInstance = getWorkflowInstance();
		
		Map<String, Serializable> workflowContext = 
			workflowInstance.getWorkflowContext();
		
		return GetterUtil.getLong((String)workflowContext.get(
			WorkflowConstants.CONTEXT_COMPANY_ID));
	}
	
	public long getWorkflowGroupId() 
		throws PortalException {
		
		WorkflowInstance workflowInstance = getWorkflowInstance();
		
		Map<String, Serializable> workflowContext = 
			workflowInstance.getWorkflowContext();
		
		return GetterUtil.getLong((String)workflowContext.get(
			WorkflowConstants.CONTEXT_GROUP_ID));
	}
	
	public long getWorkflowContextEntryClassPK() 
		throws PortalException {
		
		Map<String, Serializable> workflowContext = getWorkflowContext();
		
		return GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
	}
	
	public String getWorkflowContextEntryClassName() 
		throws PortalException {
		
		Map<String, Serializable> workflowContext = getWorkflowContext();
		
		return (String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);
	}
	
	protected Map<String, Serializable> getWorkflowContext() 
		throws PortalException {
		
		return getWorkflowInstance().getWorkflowContext();
	}
		
	public WorkflowHandler<?> getWorkflowHandler() 
		throws PortalException {
		
		String className = getWorkflowContextEntryClassName();

		return WorkflowHandlerRegistryUtil.getWorkflowHandler(className);
	}
	
	public String getNameForAssignedToSingleUser() {
		WorkflowTask workflowTask = getWorkflowTask();
		
		return PortalUtil.getUserName(workflowTask.getAssigneeUserId(), 
			StringPool.BLANK);
	}
	
	public String getNameForAssignedToAnyone() {
		return LanguageUtil.get(workflowTaskRequestHelper.getRequest(), 
			WorkflowTaskConstants.NOBODY);
	}
	
	public String getCreateDate() {
		WorkflowTask workflowTask = getWorkflowTask();
		
		return dateFormatDateTime.format(workflowTask.getCreateDate());
	}
	
	public String getState() 
		throws PortalException {
		long companyId = getWorkflowCompanyId();
		long groupId = getWorkflowGroupId();
		String className = getWorkflowContextEntryClassName();
		long classPK = getWorkflowContextEntryClassPK();
		
		return LanguageUtil.get(workflowTaskRequestHelper.getRequest(), 
			HtmlUtil.escape(WorkflowInstanceLinkLocalServiceUtil.getState(
				companyId, groupId, className, classPK)));
	}
	
	public WorkflowTask getWorkflowTask() {
		ResultRow row = (ResultRow)renderRequest.getAttribute(
			WebKeys.SEARCH_CONTAINER_RESULT_ROW);

		WorkflowTask workflowTask = null;

		if (Validator.isNotNull(row)) {
			workflowTask = (WorkflowTask)row.getParameter(
				WorkflowTaskConstants.WORKFLOW_TASK);
		}
		else {
			workflowTask = (WorkflowTask)renderRequest.getAttribute(
				WebKeys.WORKFLOW_TASK);
		}

		return workflowTask;
	}
	
	public long getWorkflowInstanceId() {
		return getWorkflowTask().getWorkflowInstanceId();
	}
	
	public String getDescription() {
		WorkflowTask workflowTask = getWorkflowTask();
		return HtmlUtil.escape(workflowTask.getDescription());
	}
	
	public String getTaglibViewDiffsURL() 
		throws PortalException, PortletException {
		
		PortletURL viewDiffsPortletURL = getURLViewDiffs();
		
		viewDiffsPortletURL.setParameter(REDIRECT, getCurrentURL());
		viewDiffsPortletURL.setParameter(HIDE_CONTROLS, "true");
		viewDiffsPortletURL.setWindowState(LiferayWindowState.POP_UP);
		viewDiffsPortletURL.setPortletMode(PortletMode.VIEW);
		
		return "javascript:Liferay.Util.openWindow({id: '" + 
			renderResponse.getNamespace() + "viewDiffs', title: '" + 
			HtmlUtil.escapeJS(LanguageUtil.get(
				workflowTaskRequestHelper.getRequest(), 
				WorkflowTaskConstants.DIFFS)) + 
			"', uri:'" + HtmlUtil.escapeJS(viewDiffsPortletURL.toString()) + 
			"'});";
	}
	
	public AssetRenderer getAssetRenderer() 
		throws PortalException, PortletException {
		
		long classPK = getWorkflowContextEntryClassPK();
		
		WorkflowHandler<?> workflowHandler = getWorkflowHandler();
		
		return workflowHandler.getAssetRenderer(classPK);
	}
	
	public String getTaskContentTitle() 
		throws PortalException {
		
		WorkflowHandler<?> workflowHandler = getWorkflowHandler();
		
		long classPK = getWorkflowContextEntryClassPK();
		
		return HtmlUtil.escape(workflowHandler.getTitle(classPK, 
			workflowTaskRequestHelper.getLocale()));
	}
	
	public String[] getMetadataFields() {
		return new String[] {
			WorkflowTaskConstants.AUTHOR, 
			WorkflowTaskConstants.CATEGORIES, 
			WorkflowTaskConstants.TAGS
		};
	}
	
	public String getTaglibEditURL() 
		throws PortalException, PortletException {
		
		PortletURL editPortletURL = getEditPortletURL();
		
		editPortletURL.setWindowState(LiferayWindowState.POP_UP);
		editPortletURL.setPortletMode(PortletMode.VIEW);

		String editPortletURLString = editPortletURL.toString();
		
		AssetRenderer assetRenderer = getAssetRenderer();

		editPortletURLString = HttpUtil.addParameter(
			editPortletURLString, WorkflowTaskConstants.DO_AS_GROUP_ID, 
			assetRenderer.getGroupId());
		
		editPortletURLString = HttpUtil.addParameter(
			editPortletURLString, WorkflowTaskConstants.REFERER_PLID, 
			workflowTaskRequestHelper.getThemeDisplay().getPlid());
		
		return "javascript:Liferay.Util.openWindow({id: '" + 
			renderResponse.getNamespace() + "editAsset', title: '" + 
			HtmlUtil.escapeJS(
				LanguageUtil.format(
					workflowTaskRequestHelper.getRequest(), 
					WorkflowTaskConstants.EDIT_X, 
					HtmlUtil.escape(assetRenderer.getTitle(
						workflowTaskRequestHelper.getLocale())), false)) + 
					"', uri:'" + HtmlUtil.escapeJS(editPortletURLString) + 
					"'});";
	}
	
	public String getEditTaskName() {
		WorkflowTask workflowTask = getWorkflowTask();
		
		return LanguageUtil.get(workflowTaskRequestHelper.getRequest(), 
			HtmlUtil.escape(workflowTask.getName()));
	}
	
	public List<WorkflowLog> getWorkflowLogs()
		throws PortalException {
		
		List<Integer> logTypes = new ArrayList<Integer>();

		logTypes.add(WorkflowLog.TASK_ASSIGN);
		logTypes.add(WorkflowLog.TASK_COMPLETION);
		logTypes.add(WorkflowLog.TASK_UPDATE);
		logTypes.add(WorkflowLog.TRANSITION);

		return WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(
			workflowTaskRequestHelper.getCompanyId(), getWorkflowInstanceId(), 
			logTypes, QueryUtil.ALL_POS, QueryUtil.ALL_POS, 
			WorkflowComparatorFactoryUtil.getLogCreateDateComparator(true));
	}
	
	public boolean hasEditPortletURL() 
		throws PortalException {
		
		return Validator.isNotNull(getEditPortletURL());
	}
	
	public boolean hasViewDiffsPortletURL() 
		throws PortalException {
		
		return Validator.isNotNull(getURLViewDiffs());
	}
	
	public String getPreviewOfTitle() 
		throws PortalException {
		
		String className = getWorkflowContextEntryClassName();
		
		return LanguageUtil.format(workflowTaskRequestHelper.getRequest(), 
			WorkflowTaskConstants.PREVIEW_OF_X, 
			ResourceActionsUtil.getModelResource(
				workflowTaskRequestHelper.getLocale(), className), false);
	}
	
	public String getHeaderTitle() 
		throws PortalException {
		
		WorkflowTask workflowTask = getWorkflowTask();
		
		String headerTitle = LanguageUtil.get(
			workflowTaskRequestHelper.getRequest(), workflowTask.getName());
		
		WorkflowHandler<?> workflowHandler = getWorkflowHandler();
		
		long classPK = getWorkflowContextEntryClassPK();

		return headerTitle.concat(
			StringPool.COLON + StringPool.SPACE + workflowHandler.getTitle(
				classPK, workflowTaskRequestHelper.getLocale()));
	}
	
	public PortletURL getURLViewDiffs() 
		throws PortalException {
		
		WorkflowHandler<?> workflowHandler = getWorkflowHandler();
		
		long classPK = getWorkflowContextEntryClassPK();
		
		return workflowHandler.getURLViewDiffs(classPK, 
			(LiferayPortletRequest)renderRequest, 
			(LiferayPortletResponse)renderResponse);
	}
	
	public PortletURL getEditPortletURL()
		throws PortalException {
		
		WorkflowHandler<?> workflowHandler = getWorkflowHandler();
		
		long classPK = getWorkflowContextEntryClassPK();
		
		return workflowHandler.getURLEdit(classPK, 
			(LiferayPortletRequest)renderRequest, 
			(LiferayPortletResponse)renderResponse);
	}

	public boolean showEditURL() {
		boolean showEditURL = false;
		
		WorkflowTask workflowTask = getWorkflowTask();
		
		if ((workflowTask.getAssigneeUserId() == 
			workflowTaskRequestHelper.getUserId()) 
			&& !workflowTask.isCompleted()) {
			showEditURL = true;
		}
		
		return showEditURL;
	}
	
	protected WorkflowTaskSearch searchTasksAssignedToMe(boolean completedTasks)
		throws PortalException {

		int total = 0;
		List<WorkflowTask> results = null;

		String curParam = SearchContainer.DEFAULT_CUR_PARAM;

		if (!completedTasks) {
			curParam = WorkflowTaskConstants.DEFAULT_CUR_PARAM1;
		}
		
		String[] assetTypes = WorkflowHandlerUtil.getSearchableAssetTypes();
		
		WorkflowTaskSearch searchContainer = new WorkflowTaskSearch(
			renderRequest, curParam, getPortletURL());
		
		WorkflowTaskDisplayTerms searchTerms =
			(WorkflowTaskDisplayTerms)searchContainer.getDisplayTerms();

		if (searchTerms.isAdvancedSearch()) {
			total = WorkflowTaskManagerUtil.searchCount(
				workflowTaskRequestHelper.getCompanyId(), 
				workflowTaskRequestHelper.getUserId(), searchTerms.getName(),
				searchTerms.getType(), null, null, null, false, false,
				searchTerms.isAndOperator());

			searchContainer.setTotal(total);

			results = WorkflowTaskManagerUtil.search(
				workflowTaskRequestHelper.getCompanyId(), 
				workflowTaskRequestHelper.getUserId(), searchTerms.getName(),
				searchTerms.getType(), null, null, null, completedTasks, false,
				searchTerms.isAndOperator(), searchContainer.getStart(),
				searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}
		else {
			total = WorkflowTaskManagerUtil.searchCount(
				workflowTaskRequestHelper.getCompanyId(), 
				workflowTaskRequestHelper.getUserId(),
				searchTerms.getKeywords(), assetTypes, completedTasks, false);

			searchContainer.setTotal(total);

			results = WorkflowTaskManagerUtil.search(
				workflowTaskRequestHelper.getCompanyId(), 
				workflowTaskRequestHelper.getUserId(),
				searchTerms.getKeywords(), assetTypes, completedTasks, false,
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}

		searchContainer.setTotal(total);
		searchContainer.setResults(results);

		validateSearchTerms(searchContainer);

		if (completedTasks) {

			searchContainer.setEmptyResultsMessage(
				WorkflowTaskConstants.THERE_ARE_NO_COMPLETED_TASKS);
		
		}
		else {
		
			searchContainer.setEmptyResultsMessage(
			WorkflowTaskConstants.THERE_ARE_NO_PENDING_TASKS_ASSIGNED_TO_YOU);
			
		}

		return searchContainer;
	}
	
	protected void validateSearchTerms(WorkflowTaskSearch searchContainer) {

		WorkflowTaskDisplayTerms searchTerms =
			(WorkflowTaskDisplayTerms)searchContainer.getDisplayTerms();

		if (Validator.isNotNull(searchTerms.getKeywords()) ||
			Validator.isNotNull(searchTerms.getName()) ||
			Validator.isNotNull(searchTerms.getType())) {

			searchContainer.setEmptyResultsMessage(
				searchContainer.getEmptyResultsMessage() +
				WorkflowTaskConstants.WITH_THE_SPECIFIED_SEARCH_CRITERIA);
		}
	}
	
	private final Format dateFormatDateTime;
	
	private final RenderRequest renderRequest;
	
	private final RenderResponse renderResponse;
	
	private final WorkflowTaskRequestHelper workflowTaskRequestHelper;

}