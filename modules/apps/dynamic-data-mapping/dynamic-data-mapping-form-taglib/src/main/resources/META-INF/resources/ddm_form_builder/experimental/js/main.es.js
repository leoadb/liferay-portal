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

		dataSchemaInputId: Config.string(),

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

	_serializeDataSchema(pages) {
		const columnFields = ['indexable', 'label', 'localizable', 'name', 'repeatable', 'required', 'type'];
		const dataSchemaColumns = [];
		const pagesVisitor = new PagesVisitor(pages);

		pagesVisitor.mapFields(
			({settingsContext}) => {
				const columnConfig = {};
				const settingsContextVisitor = new PagesVisitor(settingsContext.pages);

				settingsContextVisitor.mapFields(
					({fieldName, value}) => {
						if (columnFields.includes(fieldName)) {
							columnConfig[fieldName] = value;
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
		const {dataSchemaInputId} = this.props;

		if (dataSchemaInputId) {
			const dataSchemaInput = document.querySelector(`#${dataSchemaInputId}`);

			dataSchemaInput.value = this._serializeDataSchema(newVal);

			console.log(dataSchemaInput.value);
		}
	}

	_setContext(context) {
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
										fields: [],
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
}

const formBuilderWithFields = (props, container, callback) => {
	loader(
		() => callback(new FormBuilderTagLib(props, container)),
		props.modules,
		[...props.dependencies]
	);
};

export default formBuilderWithFields;