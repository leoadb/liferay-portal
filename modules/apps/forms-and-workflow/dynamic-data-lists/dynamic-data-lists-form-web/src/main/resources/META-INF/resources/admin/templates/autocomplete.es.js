import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './autocomplete.soy';

/**
 * AutoComplete Component
 */
class AutoCompleteContainer extends Component {}

// Register component
Soy.register(AutoCompleteContainer, templates, 'container');

class AutoCompleteActionPanel extends Component {}

//Register component
Soy.register(AutoCompleteActionPanel, templates, 'actionPanel');

export { AutoCompleteContainer, AutoCompleteActionPanel };