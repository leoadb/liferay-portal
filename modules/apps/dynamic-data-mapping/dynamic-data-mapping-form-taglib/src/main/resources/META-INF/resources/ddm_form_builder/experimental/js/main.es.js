import {pageStructure} from 'dynamic-data-mapping-form-builder/metal/js/util/config.es';
import autobind from 'autobind-decorator';
import Component, {Config} from 'metal-jsx';
import core from 'metal';
import FormBuilder from 'dynamic-data-mapping-form-builder/metal/js/components/FormBuilder/FormBuilder.es.js';
import LayoutProvider from 'dynamic-data-mapping-form-builder/metal/js/components/LayoutProvider/index.es';
import loader from 'dynamic-data-mapping-form-builder/metal/js/components/FieldsLoader/index.es';
import {PagesVisitor} from 'dynamic-data-mapping-form-builder/metal/js/util/visitors.es';

/**
 * Form Builder TagLib.
 * @extends Component
 */
class FormBuilderTagLib extends Component {
	static PROPS = {
		context: Config.shapeOf(
			{
				pages: Config.arrayOf(pageStructure),
				paginationMode: Config.string(),
				rules: Config.array(),
				successPageSettings: Config.object()
			}
		).required().setter('_setContext'),

		/**
		 * The columns for the data definition.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		dataDefinitionColumns: Config.array().value([]),

		/**
		 * The target input for the serialized data defintion columns.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		dataDefinitionInputId: Config.string(),

		/**
		 * The default language id of the form.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		defaultLanguageId: Config.string().value(themeDisplay.getDefaultLanguageId()),

		/**
		 * The default language id of the form.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		editingLocale: Config.string().value(themeDisplay.getDefaultLanguageId()),

		/**
		 * The available field types to display on the side bar.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		fieldTypes: Config.array().value([]),

		/**
		 * The namespace of the portlet.
		 * @default undefined
		 * @instance
		 * @memberof FormBuilderTagLib
		 * @type {!string}
		 */

		namespace: Config.string().required(),

		/**
		 * The path to the SVG spritemap file containing the icons.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!string}
		 */

		spritemap: Config.string().required()
	};

	render() {
		const {
			context,
			namespace
		} = this.props;

		const layoutProviderProps = {
			...this.props,
			events: {
				pagesChanged: this._handlePagesChanged
			},
			initialPages: context.pages,
			initialPaginationMode: context.paginationMode,
			initialSuccessPageSettings: context.successPageSettings,
			ref: 'layoutProvider'
		};

		return (
			<div class={'ddm-form-builder'}>
				<LayoutProvider {...layoutProviderProps}>
					<FormBuilder
						namespace={namespace}
						paginationMode={'wizard'}
						ref="builder"
					/>
				</LayoutProvider>
			</div>
		);
	}

	_getFieldType(type) {
		const {fieldTypes} = this.props;

		return fieldTypes.find(fieldType => fieldType.name === type);
	}

	@autobind
	_handlePagesChanged({newVal}) {
		const {dataDefinitionInputId} = this.props;

		if (dataDefinitionInputId) {
			const dataSchemaInput = document.querySelector(`#${dataDefinitionInputId}`);

			console.log(newVal, this._serializeDataSchema(newVal));

			dataSchemaInput.value = this._serializeDataSchema(newVal);
		}
	}

	_mapColumnsToFields(columns) {
		const fieldType = this._getFieldType('text');

		return columns.map(
			column => {
				return {
					...fieldType,
					...column,
					fieldName: column.name,
					label: column.label[themeDisplay.getLanguageId()],
					settingsContext: this._populateColumnSettingsContext(column, fieldType.settingsContext),
					type: 'text'
				};
			}
		);
	}

	_populateColumnSettingsContext(column, settingsContext) {
		const visitor = new PagesVisitor(settingsContext.pages);

		return {
			...settingsContext,
			pages: visitor.mapFields(
				field => {
					let {localizable, localizedValue, value} = field;

					if (column[field.fieldName]) {
						value = column[field.fieldName];

						if (field.fieldName === 'label') {
							value = value[themeDisplay.getLanguageId()];
						}

						if (field.fieldName === 'fieldName') {
							value = column.name;
						}
					}

					if (localizable) {
						localizedValue[themeDisplay.getLanguageId()] = value;
					}

					return {
						...field,
						localizedValue,
						value
					};
				}
			)
		};
	}

	_serializeDataSchema(pages) {
		const columnFields = ['indexable', 'label', 'localizable', 'name', 'repeatable', 'required', 'dataType'];
		const dataSchemaColumns = [];
		const pagesVisitor = new PagesVisitor(pages);

		pagesVisitor.mapFields(
			({settingsContext}) => {
				const columnConfig = {};
				const settingsContextVisitor = new PagesVisitor(settingsContext.pages);

				settingsContextVisitor.mapFields(
					({fieldName, localizable, localizedValue, value}) => {
						if (columnFields.includes(fieldName)) {
							if (localizable) {
								columnConfig[fieldName] = localizedValue;
							}
							else {
								if (fieldName === 'dataType') {
									fieldName = 'type';
								}

								columnConfig[fieldName] = value;
							}
						}
					}
				);

				dataSchemaColumns.push(columnConfig);
			}
		);

		return JSON.stringify(dataSchemaColumns);
	}

	_setContext(context) {
		const {dataDefinitionColumns} = this.props;
		let {successPageSettings} = context;
		const {successPage} = context;

		if (!successPageSettings) {
			successPageSettings = successPage;
		}

		if (core.isString(successPageSettings.title)) {
			successPageSettings.title = {};
			successPageSettings.title[themeDisplay.getLanguageId()] = '';
		}

		if (core.isString(successPageSettings.body)) {
			successPageSettings.body = {};
			successPageSettings.body[themeDisplay.getLanguageId()] = '';
		}

		if (!context.pages.length) {
			const fields = this._mapColumnsToFields(dataDefinitionColumns);

			context = {
				...context,
				pages: [
					{
						description: '',
						localizedDescription: {
							[themeDisplay.getLanguageId()]: ''
						},
						localizedTitle: {
							[themeDisplay.getLanguageId()]: ''
						},
						rows: fields.map(
							field => ({
								columns: [
									{
										fields: [field],
										size: 12
									}
								]
							})
						),
						title: ''
					}
				],
				paginationMode: 'wizard',
				successPageSettings
			};
		}

		return context;
	}
}

const formBuilderWithFields = (props, container, callback) => {
	loader(
		() => callback(new FormBuilderTagLib(props, container)),
		props.modules,
		[...props.dependencies]
	);
};

export default formBuilderWithFields;