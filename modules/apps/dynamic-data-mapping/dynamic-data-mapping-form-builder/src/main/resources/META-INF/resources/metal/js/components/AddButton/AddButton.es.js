import {Config} from 'metal-state';
import ClayButton from 'clay-button';
import Component from 'metal-jsx';

class AddButton extends Component {
	static PROPS = {
		spritemap: Config.string().required()
	};

	render() {
		const {spritemap} = this.props;

		return (
			<ClayButton
				elementClasses={'btn-default lfr-ddm-add-field lfr-ddm-plus-button nav-btn nav-btn-monospaced'}
				events={
					{
						click: this._handleButtonClicked.bind(this)
					}
				}
				icon={'plus'}
				ref={'button'}
				spritemap={spritemap}
			/>
		);
	}

	_handleButtonClicked(event) {
		this.emit('click', event);
	}
}

export default AddButton;