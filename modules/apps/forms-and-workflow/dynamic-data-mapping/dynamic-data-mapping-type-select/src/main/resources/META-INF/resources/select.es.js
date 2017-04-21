import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './select.soy';

/**
 * Select Component
 */
class Select extends Component {}

// Register component
Soy.register(Select, templates, 'select');

export default Select;