import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './calculate.soy';

/**
 * Calculate Component
 */
class Calculate extends Component {}

// Register component
Soy.register(Calculate, templates, 'render');

export default Calculate;