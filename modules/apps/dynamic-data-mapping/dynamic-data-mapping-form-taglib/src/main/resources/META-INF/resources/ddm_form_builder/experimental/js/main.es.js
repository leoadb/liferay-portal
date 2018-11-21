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

	static STATE = {
		initialPages: Config.array().valueFn('_valueFnInitialPages')
	};

	render() {
		const {
			context,
			namespace
		} = this.props;
		const {initialPages} = this.state;

		const layoutProviderProps = {
			...this.props,
			events: {
				pagesChanged: this._handlePagesChanged
			},
			initialPages,
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

	_convertDataDefinitionColumnsToFormBuilderContext() {
		const {dataDefinitionColumns} = this.props;
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

	@autobind
	_handlePagesChanged({newVal}) {
		const {dataDefinitionInputId} = this.props;

		if (dataDefinitionInputId) {
			const dataSchemaInput = document.querySelector(`#${dataDefinitionInputId}`);

			dataSchemaInput.value = this._serializeDataSchema(newVal);

			console.log(dataSchemaInput.value);
		}
	}

	_setContext(context, fields = []) {
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
						rows: [
							{
								columns: [
									{
										fields,
										size: 12
									}
								]
							}
						],
						title: ''
					}
				],
				paginationMode: 'wizard',
				successPageSettings
			};
		}

		return context;
	}

	_valueFnInitialPages() {
		const {dataDefinitionColumns} = this.props;

		const context = this._setContext(
			dataDefinitionColumns.map(
				column => {
					return {
						...column,
						type: 'text'
					};
				}
			)
		);

		return context.pages;
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