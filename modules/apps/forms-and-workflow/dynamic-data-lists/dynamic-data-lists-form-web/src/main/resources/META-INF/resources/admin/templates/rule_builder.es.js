import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './rule_builder.soy';

/**
 * RuleBuilder Component
 */
class RuleBuilder extends Component {}

// Register component
Soy.register(RuleBuilder, templates, 'rule_builder');

export default RuleBuilder;