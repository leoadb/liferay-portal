import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './rule.soy';

/**
 * Rule Component
 */
class Rule extends Component {}

// Register component
Soy.register(Rule, templates, 'render');

export default Rule;