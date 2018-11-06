import {Config} from 'metal-state';
import autobind from 'autobind-decorator';
import Component from 'metal-component';
import dom from 'metal-dom';

class ReportDefinitions extends Component {
	static STATE = {
		formBuilderAppNode: Config.string().setter(val => document.querySelector(val)),
		formBuilderButtonNode: Config.string().setter(val => document.querySelector(val))
	};

	attached() {
		const {formBuilderButtonNode} = this;

		this._eventListeners = [
			dom.on(formBuilderButtonNode, 'click', this._handleFormBuilderButtonClicked)
		];
	}

	@autobind
	_handleFormBuilderButtonClicked() {
		this.openFormBuilderModal();
	}

	openFormBuilderModal() {
		const {formBuilderAppNode} = this;

		const id = `formBuilder${(new Date()).getTime()}`;

		Liferay.Util.openWindow(
			{
				dialog: {
					bodyContent: formBuilderAppNode,
					centered: true,
					modal: true,
					toolbars: {
						footer: [
							{
								label: Liferay.Language.get('ok'),
								on: {
									click: function(event) {
										event.domEvent.preventDefault();

										const modal = Liferay.Util.getWindow(id);

										modal.hide();
									}
								},
								primary: true
							},
							{
								label: Liferay.Language.get('cancel'),
								on: {
									click: function(event) {
										event.domEvent.preventDefault();

										const modal = Liferay.Util.getWindow(id);

										modal.hide();
									}
								}
							}
						]
					}
				},
				id,
				title: 'Edit Parameters'
			}
		);

		formBuilderAppNode.classList.remove('hide');
	}
}

export default ReportDefinitions;