import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './autocomplete.soy';

/**
 * AutoComplete Component
 */
class AutoComplete extends Component {}

// Register component
Soy.register(AutoComplete, templates, 'autocomplete');

export default AutoComplete;