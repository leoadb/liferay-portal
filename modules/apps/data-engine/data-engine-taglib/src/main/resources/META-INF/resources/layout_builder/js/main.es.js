import {pageStructure} from 'dynamic-data-mapping-form-builder/metal/js/util/config.es';
import autobind from 'autobind-decorator';
import Component, {Config} from 'metal-jsx';
import core from 'metal';
import FormBuilder from 'dynamic-data-mapping-form-builder/metal/js/components/FormBuilder/FormBuilder.es.js';
import LayoutProvider from 'dynamic-data-mapping-form-builder/metal/js/components/LayoutProvider/index.es';
import {PagesVisitor} from 'dynamic-data-mapping-form-builder/metal/js/util/visitors.es';

/**
 * Layout Builder TagLib.
 * @extends Component
 */
class LayoutBuilderTagLib extends Component {
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
		 * The target input for the serialized data layout.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		dataLayoutInputId: Config.string(),

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
							ref="builder" />
					</LayoutProvider>
				</div>
		);
	}

	@autobind
	_handlePagesChanged({newVal}) {
		const {dataLayoutInputId} = this.props;

		if (dataLayoutInputId) {
			const dataLayoutInput = document.querySelector(`#${dataLayoutInputId}`);

			dataLayoutInput.value = JSON.stringify(newVal);
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

		return context;
	}
}

export default LayoutBuilderTagLib;