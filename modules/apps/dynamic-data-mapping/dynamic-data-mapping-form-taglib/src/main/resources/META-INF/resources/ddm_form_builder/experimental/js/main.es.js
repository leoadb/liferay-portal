import Component, {Config} from 'metal-jsx';
import core from 'metal';
import FormBuilder from 'dynamic-data-mapping-form-builder/metal/js/components/FormBuilder/FormBuilder.es.js';
import LayoutProvider from 'dynamic-data-mapping-form-builder/metal/js/components/LayoutProvider/index.es';
import loader from 'dynamic-data-mapping-form-builder/metal/js/components/FieldsLoader/index.es';
import {pageStructure} from 'dynamic-data-mapping-form-builder/metal/js/util/config.es';

/**
 * Form Builder.
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