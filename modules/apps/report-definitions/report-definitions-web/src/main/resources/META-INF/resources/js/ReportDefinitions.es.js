import 'clay-modal';
import {Config} from 'metal-state';
import autobind from 'autobind-decorator';
import Component from 'metal-component';
import dom from 'metal-dom';
import Soy from 'metal-soy';
import templates from './ReportDefinitions.soy.js';

class ReportDefinitions extends Component {
	static STATE = {
		formBuilderAppNode: Config.string().setter(val => document.querySelector(val)),
		formBuilderButtonNode: Config.string().setter(val => document.querySelector(val)),
		spritemap: Config.string().required(),
		strings: Config.object().value(
			{
				cancel: Liferay.Language.get('cancel'),
				ok: Liferay.Language.get('ok'),
				reportDefinitionParameters: Liferay.Language.get('report-definition-parameters')
			}
		)
	};

	attached() {
		const {formBuilderButtonNode} = this;

		this._eventListeners = [
			dom.on(formBuilderButtonNode, 'click', this._handleFormBuilderButtonClicked)
		];
	}

	openFormBuilderModal() {
		const {formBuilderAppNode} = this;

		formBuilderAppNode.classList.remove('hide');

		this.refs.modal.show();
	}

	@autobind
	_handleFormBuilderButtonClicked() {
		this.openFormBuilderModal();
	}

	@autobind
	_handleModalButtonClicked({currentTarget}) {
		if (currentTarget.classList.contains('btn-primary')) {
			this._handleModalOkButtonClicked();
		}
	}

	@autobind
	_handleModalCancelButtonClicked() {
		this.refs.modal.emit('hide');
	}

	@autobind
	_handleModalOkButtonClicked() {
		this.refs.modal.emit('hide');
	}

	@autobind
	_handleModalRendered() {
		const {formBuilderAppNode} = this;
		const body = this.refs.modal.element.querySelector('.modal-body');

		formBuilderAppNode.classList.remove('hide');

		dom.append(body, formBuilderAppNode);
	}
}

Soy.register(ReportDefinitions, templates);

export default ReportDefinitions;