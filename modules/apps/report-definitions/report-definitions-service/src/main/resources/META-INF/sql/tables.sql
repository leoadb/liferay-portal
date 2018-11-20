create table ReportDefinition (
	uuid_ VARCHAR(75) null,
	reportDefinitionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	dataDefinitionId LONG,
	description VARCHAR(75) null,
	name VARCHAR(75) null,
	parametersDataSchemaId LONG,
	sortColumns VARCHAR(75) null
);